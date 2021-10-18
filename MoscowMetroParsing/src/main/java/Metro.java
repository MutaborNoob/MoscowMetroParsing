import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Metro {

    private JSONArray lines;

    private JSONObject stations;

    private JSONArray connections;

    //Set<Object> connections = new HashSet<>();

    //public Set<Object> getConnections() {
     //   return connections;
    //}

    //public void setConnections(Set<Object> connections) {
      //  this.connections = connections;
    //}

    public JSONObject getStations() {
        return stations;
    }

    public void setStations(JSONObject stations) {
        this.stations = stations;
    }

    public JSONArray getLines() {
        return lines;
    }

    public void setLines(JSONArray lines) {
        this.lines = lines;
    }

    public JSONArray getConnections() {
        return connections;
    }

    public void setConnections2(JSONArray connections) {
        this.connections = connections;
    }
}
