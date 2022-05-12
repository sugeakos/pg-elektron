package diploma.pgelektron.customer;

import java.util.List;

public interface CustomerService {
    List<Customer> findAllCustomers();
    Customer saveCustomer(Customer customer);
    Customer getCustomerById(Long id);
}
