package diploma.pgelektron.tvcategory.customer.implementation;

import diploma.pgelektron.tvcategory.customer.entity.CustomerEntity;
import diploma.pgelektron.tvcategory.customer.repository.CustomerRepository;
import diploma.pgelektron.tvcategory.customer.service.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

@Transactional
public class CustomerServiceImpl implements CustomerService {
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    private final CustomerRepository customerRepository;


    @Override
    public List<CustomerEntity> findAllCustomers(){
        return customerRepository.findAll();
    }

    @Override
    public CustomerEntity saveCustomer(CustomerEntity customer) {
        return customerRepository.save(customer);
    }

    @Override
    public CustomerEntity getCustomerById(Long id){
        return customerRepository.getById(id);
    }
}
