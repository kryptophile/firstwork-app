package kanishka.firstwork.firstwork_app.utils;

import kanishka.firstwork.firstwork_app.model.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {
    public static Specification<User> ageMoreThan(Integer ageLimit) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("age"), ageLimit);
    }

    public static Specification<User> ageLessThan(Integer ageLimit) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("age"), ageLimit);
    }

    public static Specification<User> ageEquals(Integer ageLimit) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("age"), ageLimit);
    }
}
