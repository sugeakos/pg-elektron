package com.example.pgelektron.customer;

import com.example.pgelektron.customer.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> findAllCustomers();
    Customer saveCustomer(Customer customer);
    Customer getCustomerById(Long id);
    void addRoleToCustomer(String email, String roleName);

}
