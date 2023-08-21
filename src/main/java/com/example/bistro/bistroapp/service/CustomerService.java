package com.example.bistro.bistroapp.service;

import com.example.bistro.bistroapp.entity.Customer;
import java.util.List;

public interface CustomerService {
    Customer addCustomer(Customer customer);
    List<Customer> getAllCustomers();
    void removeCustomer(Long id);
}
