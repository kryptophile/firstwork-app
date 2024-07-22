package kanishka.firstwork.firstwork_app.repository;

import kanishka.firstwork.firstwork_app.model.Rule;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface RuleRepository extends CrudRepository<Rule, Long>, JpaSpecificationExecutor {
}
