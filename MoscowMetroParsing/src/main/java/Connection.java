public class Connection {

    private int line;
    private String station;

    private Object [] connectionsArray;

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }


    public Object[] getConnectionsArray() {
        return connectionsArray;
    }

    public void setConnectionsArray(Object[] connectionsArray) {
        this.connectionsArray = connectionsArray;
    }
}
