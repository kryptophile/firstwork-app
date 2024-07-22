package kanishka.firstwork.firstwork_app.repository;


import kanishka.firstwork.firstwork_app.model.Contract;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ContractRepository extends CrudRepository<Contract, Long>, JpaSpecificationExecutor {
}
