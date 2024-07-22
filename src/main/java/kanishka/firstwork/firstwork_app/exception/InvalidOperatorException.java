package kanishka.firstwork.firstwork_app.exception;

public class InvalidOperatorException extends IllegalArgumentException {
    public InvalidOperatorException(String operator){
        super("Illegal argument operator found "+ operator);
    }
}
