package diploma.pgelektron.tvcategory.customer.repository;

import diploma.pgelektron.tvcategory.customer.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
