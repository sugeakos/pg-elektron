package diploma.pgelektron.tvcategory.customer.service;

import diploma.pgelektron.tvcategory.customer.entity.CustomerEntity;

import java.util.List;

public interface CustomerService {
    List<CustomerEntity> findAllCustomers();
    CustomerEntity saveCustomer(CustomerEntity customer);
    CustomerEntity getCustomerById(Long id);
}
