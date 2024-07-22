package kanishka.firstwork.firstwork_app.enumeration;

import java.util.Optional;
import java.util.stream.Stream;

public enum Operator {
    LESS_THAN("<"),
    GREATER_THAN(">"),
    EQUALS("=="),
    GREATER_EQUALS(">="),
    LESS_EQUALS("<="),
    NOT_EQUALS("!="),
    OR("||"),
    AND("&&");

    public final String value;

    Operator(String value){
        this.value = value;
    }

    public static Optional<Operator> getMatchingOperator(String val){
        return Stream.of(Operator.values()).filter(x -> x.value.equals(val)).findFirst();
    }
}
