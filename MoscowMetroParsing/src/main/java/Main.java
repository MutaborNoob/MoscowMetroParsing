
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import java.io.*;


public class Main {

    //public final String urlName = "https://www.moscowmap.ru/metro.html#lines";

    public static void main(String [] args){
        try {
            ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);


            Metro metro = new Metro();
            Parser parser = new Parser("data/code.html");

            metro.setLines(parser.getLines());
            metro.setStations(parser.getStations());
            metro.setConnections2(parser.getConnection());



            objectMapper.writeValue(
                    new FileOutputStream("src/main/resources/MosMetro.json"), metro);


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void readJ(Metro metro){
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            String carJson = objectMapper.writeValueAsString(metro);
            Metro newMetro = objectMapper.readValue(carJson, Metro.class);

            System.out.println("lines = " + newMetro.getLines());
            System.out.println("stations = " + newMetro.getStations());

            JSONParser parser = new JSONParser();

            Object object = parser.parse(new FileReader("src/main/resources/MosMetro.json"));

            JSONObject metroJsonObject = (JSONObject) object;
            JSONObject stationsObj = (JSONObject) metroJsonObject.get("stations");

            stationsObj.keySet().forEach(k -> {
                JSONArray stationsArray = (JSONArray) stationsObj.get(k);
                System.out.println("Линия " + k + ". Количество станций: " + stationsArray.size() + ".");
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
