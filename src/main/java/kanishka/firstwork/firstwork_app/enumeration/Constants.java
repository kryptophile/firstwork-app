package kanishka.firstwork.firstwork_app.enumeration;

public enum Constants {
    YYY_MM_DD("yyyy-MM-dd"),
    RULE_JSON_KEY("rule"),
    MODEL_CONSTANT("model"),
    FIELD_NAME_CONSTANT("fieldName"),
    INTEGER("Integer"),
    STRING("String"),
    DATE("Date"),
    LONG("Long"),
    PLACEHOLDER_START("{ {"),
    PLACEHOLDER_END("} }");

    public final String value;

    Constants(String value){
        this.value = value;
    }
}
