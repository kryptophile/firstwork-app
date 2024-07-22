package kanishka.firstwork.firstwork_app.service;

import org.springframework.data.jpa.domain.Specification;

public interface EvaluateExpressionStrategy {
    <T extends Comparable<? super T>>Specification<T> evaluate(String fieldName, T val);
}
