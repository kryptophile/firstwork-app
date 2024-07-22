package kanishka.firstwork.firstwork_app.enumeration;

public enum Location {
    BANGALORE("Bangalore, India"),
    SAN_FRANCISCO("San Francisco, US"),
    DELHI("New Delhi, India"),
    BEIJING("Beijing, China"),
    MOSCOW("Monscow, Russia");

    public final String value;

    private Location(String value){
        this.value = value;
    }
}
