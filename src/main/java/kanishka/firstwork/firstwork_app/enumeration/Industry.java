package kanishka.firstwork.firstwork_app.enumeration;

public enum Industry {
    IT("IT"),
    AGRICULTURE("Agriculture"),
    MANUFACTURE("Manufacture"),
    CONSULTANCY("Consultancy");

    public final String value;

    private Industry(String value){
        this.value = value;
    }
}
