package kanishka.firstwork.firstwork_app.repository;

import kanishka.firstwork.firstwork_app.model.Company;;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends CrudRepository<Company, Long>, JpaSpecificationExecutor {
}
