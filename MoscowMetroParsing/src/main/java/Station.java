import java.util.HashMap;
import java.util.TreeMap;

public class Station {

    private String name;
    private String number;

    private HashMap<Integer, String[]> stations;

    public HashMap<Integer, String[]> getStations() {
        return stations;
    }

    public void setStations(HashMap<Integer, String[]> stations) {
        this.stations = stations;
    }

}
