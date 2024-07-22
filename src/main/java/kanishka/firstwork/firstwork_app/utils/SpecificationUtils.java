package kanishka.firstwork.firstwork_app.utils;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import kanishka.firstwork.firstwork_app.enumeration.Operator;
import kanishka.firstwork.firstwork_app.exception.InvalidOperatorException;
import kanishka.firstwork.firstwork_app.model.Company;
import kanishka.firstwork.firstwork_app.model.Contract;
import kanishka.firstwork.firstwork_app.model.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.List;

public class SpecificationUtils {

    public static <T extends Comparable<? super T>> Specification<T> getSpecificationForOperator(Operator operator,
                                                                                                 String fieldName, T val){
        switch(operator){
            case EQUALS:
                return equals(fieldName, val);
            case LESS_THAN:
                return lessThan(fieldName, val);
            case GREATER_THAN:
                return greaterThan(fieldName, val);
            case LESS_EQUALS:
                return lessThanOrEqual(fieldName, val);
            case GREATER_EQUALS:
                return greaterThanOrEqual(fieldName, val);
            case NOT_EQUALS:
                return notEqual(fieldName, val);
            default:
                throw new InvalidOperatorException(operator.value);
        }
    }

    public static <T extends Comparable<? super T>> Specification<T> equals(String fieldName, T val) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(fieldName), val);
    }

    public static <T extends Comparable<? super T>> Specification<T> lessThan(String fieldName, T val) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get(fieldName), val);
    }

    public static <T extends Comparable<? super T>> Specification<T> greaterThan(String fieldName, T val) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get(fieldName), val); //fieldName), val);
    }

    public static <T extends Comparable<? super T>> Specification<T> notEqual(String fieldName, T val) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get(fieldName), val);
    }

    public static <T extends Comparable<? super T>> Specification<T> lessThanOrEqual(String fieldName, T val) {
        return SpecificationUtils.lessThan(fieldName, val).or(SpecificationUtils.equals(fieldName, val));
    }

    public static <T extends Comparable<? super T>> Specification<T> greaterThanOrEqual(String fieldName, T val) {
        return SpecificationUtils.greaterThan(fieldName, val).or(SpecificationUtils.equals(fieldName, val));
    }

    public static <T extends Comparable<? super T>> Specification<T> in(String fieldName, List<T> val) {
        return (root, query, criteriaBuilder) -> {
            Expression<String> companyExpression = root.get(fieldName);
            return companyExpression.in(val);
        };
    }

    public static Specification<Object> stringLike(String fieldName, String val) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(fieldName), "%" + val + "%");
    }


    public static Specification<Object> lessThanDate(String fieldName, Date val) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get(fieldName), val);
    }

    public static Specification<Object> greaterThanDate(String fieldName, Date val) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get(fieldName), val);
    }

    public static Specification<Object> equalsFilterOnContractCompanyJoin(
            String joinField, String fieldName, String val) {

        return (root, query, criteriaBuilder) -> {
            Join<Contract, Company> contractCompanyJoin = root.join(joinField);
            return criteriaBuilder.equal(contractCompanyJoin.get(fieldName), val);
        };
    }

    public static Specification<Object> equalsFilterOnContractUserJoin(
            String joinField, String fieldName, String val) {

        return (root, query, criteriaBuilder) -> {
            Join<Contract, User> contractUserJoin = root.join(joinField);
            return criteriaBuilder.equal(contractUserJoin.get(fieldName), val);
        };
    }

}
