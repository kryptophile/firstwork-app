package kanishka.firstwork.firstwork_app.repository;

import kanishka.firstwork.firstwork_app.model.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestUserRepository extends CrudRepository<User, Long>, JpaSpecificationExecutor {
}
