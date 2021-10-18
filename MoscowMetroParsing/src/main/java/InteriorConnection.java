public class InteriorConnection {

    private String line;
    private String station;


    public InteriorConnection(String line, String station){
        this.line = line;
        this.station = station;
    }

    public String getStation() {
        return station;
    }


    public String getLine() {
        return line;
    }

}
