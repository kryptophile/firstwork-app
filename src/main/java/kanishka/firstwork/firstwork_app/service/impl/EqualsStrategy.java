package kanishka.firstwork.firstwork_app.service.impl;

import kanishka.firstwork.firstwork_app.service.EvaluateExpressionStrategy;
import org.springframework.data.jpa.domain.Specification;

public class EqualsStrategy implements EvaluateExpressionStrategy {
    @Override
    public <T extends Comparable<? super T>> Specification<T> evaluate(String fieldName, T val) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(fieldName), val);
    }
}
