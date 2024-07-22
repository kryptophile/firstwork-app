package kanishka.firstwork.firstwork_app.exception;

public class InvalidRuleException extends Exception{
    public InvalidRuleException(String message) {
        super("Json invalid " + message);
    }
}
