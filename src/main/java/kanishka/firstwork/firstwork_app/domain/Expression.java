package kanishka.firstwork.firstwork_app.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Expression<T> {
    private String fieldName;
    private String operator;
    private T value;

    public Boolean isEmpty(){
        if( (Objects.isNull(fieldName) || fieldName.isEmpty()) && (Objects.isNull(operator) || operator.isEmpty()) &&
                Objects.isNull(value))
            return Boolean.TRUE;
        return Boolean.FALSE;
    }

}
