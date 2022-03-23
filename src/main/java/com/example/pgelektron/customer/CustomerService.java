package com.example.pgelektron.customer;

import com.example.pgelektron.tv.TvService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final TvService tvService;

    public List<Customer> findAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Long id){
       return customerRepository.getById(id);
    }


}
