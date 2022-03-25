package com.example.pgelektron.customer;

import com.example.pgelektron.tv.TV;
import com.example.pgelektron.tv.TvService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;


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
