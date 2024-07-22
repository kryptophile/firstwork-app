package kanishka.firstwork.firstwork_app.enumeration;

import java.util.Optional;
import java.util.stream.Stream;

public enum Models {
    MODEL_USER("User"),
    MODEL_COMPANY("Company"),
    MODEL_CONTRACT("Contract"),
    MODEL_RULE("Rule");


    public final String value;

    Models(String value){
        this.value = value;
    }

    public static Optional<Models> getMatchingModel(String val){
        return Stream.of(Models.values()).filter(x -> x.value.equals(val)).findFirst();
    }
}
