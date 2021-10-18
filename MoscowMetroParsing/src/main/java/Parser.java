import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Parser {

    private Document document;
    private Elements elements;

    public Parser(String path) throws IOException
    {
        //файл решил сразу в скаченно виде брать, что бы не ругался сайт
        //document = Jsoup.connect(path).userAgent("Chrome/4.0.249.0 Safari/532.5").maxBodySize(0).get();

        document = Jsoup.parse(parseHtmlFile(path));
        elements = document.select("div#metrodata");//общая инфа
    }

    private String parseHtmlFile(String path){
        StringBuilder stringBuilder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            lines.forEach(line -> stringBuilder.append(line + "\n"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public JSONArray getLines(){

        Elements lineNames = document.select("span.js-metro-line");//имена линий

        JSONArray lines = new JSONArray();

        int count = 1;
        for (Element e : lineNames){
            Line game = new Line();
            game.setName(e.text());
            game.setNumber(Integer.toString(count));
            lines.add(game);
            count++;
        }

        return lines;
    }

    public JSONObject getStations(){

        Document interimDocument = Jsoup.parse(elements.html());
        Elements lineEl = interimDocument.select("div.js-depend");//подробно о линиях(станции)

        int count2 = 1;
        JSONObject jsonObject = new JSONObject();

        for (Element el : lineEl)
        {
            String[] stationArray = new String[testingLinesForJson(el.text()).size()];
            jsonObject.put(count2, testingLinesForJson(el.text()).toArray(stationArray));
            count2++;
        }

        return jsonObject;
    }

    public JSONArray getConnection(){


        JSONArray interiorArray = new JSONArray();//внутренний массив из двух соеденительных станций

        //название линий
        //Сокольническая линия
        //Замоскворецкая линия
        //Арбатско-Покровская линия и тд
        Elements lines = document.select("div.js-toggle-depend");

        //списки станций
        //1. Бульвар Рокоссовского 2. Черкизовская 3. Преображенская площадь 4. Сокольники
        //1. Ховрино 2. Беломорская 3. Речной вокзал 4. Водный стадион 5. Войковская
        // и тд.
        Elements stationsByLines = document.select("div.js-metro-stations");

        Map<String, List<String>> stationMap = new HashMap<>();
        Map<String, String> lineMap = new HashMap<>();


        for (int i = 0; i < lines.size(); i++) {
            Element line = lines.get(i).selectFirst("span.js-metro-line");
            Elements stations = stationsByLines.get(i).select("p");
            String lineNum = line.attr("data-line");
            lineMap.put(lineNum, line.text());
            stationMap.put(lineNum, stations
                    .stream()
                    .map(e -> e.select("span.name").text())
                    .collect(Collectors.toList()));
            lineMap.put(lineNum, line.text());

            for (Element el : stations){

                InteriorConnection [] interiorConnectionArray = new InteriorConnection[2];

                InteriorConnection interiorConnectionObj = new InteriorConnection(lineNum, el.select("span.name").text());
                interiorConnectionArray [0] = interiorConnectionObj;

                el.select("span.t-icon-metroln").stream().map(e -> interiorConnectionArray[1] = new InteriorConnection(
                        e.attr("class")
                                .replaceAll(".+ln-", ""),
                        e.attr("title")
                                .replaceAll(".+«(.+)».+", "$1"))).collect(Collectors.toList());

                if (interiorConnectionArray[1] == null){
                    interiorConnectionArray[0] = null;
                }else {
                    interiorArray.add(interiorConnectionArray);
                }

            }

        }

        return interiorArray;
    }

    private static List<String> testingLinesForJson(String line)
    {
        String finalLine = line.replaceAll("[\\d]{1,2}[\\.]", ",");
        String[] stationTestArray = finalLine.split(",");

        for (int i = 0; i < stationTestArray.length; i++)
        {
            stationTestArray[i] = stationTestArray[i].trim();
        }

        ArrayList<String> list = new ArrayList<>(Arrays.asList(stationTestArray));
        list.remove(0);

        return list;
    }



}
