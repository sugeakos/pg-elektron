package com.example.pgelektron.customer;

import com.example.pgelektron.tv.TvServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final TvServiceImpl tvService;

    @Override
    public List<Customer> findAllCustomers(){
        return customerRepository.findAll();
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerById(Long id){
       return customerRepository.getById(id);
    }

    @Override
    public void addRoleToCustomer(String email, String roleName) {

    }


}
