package com.example.bistro.bistroapp.service;

import com.example.bistro.bistroapp.entity.Customer;
import com.example.bistro.bistroapp.repository.CustomerRepository;
import com.example.bistro.bistroapp.service.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Assertions;

public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(customerService, "customerRepository", customerRepository);
    }

    @Test
    public void testAddCustomer() {
        Customer customer = new Customer();
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer result = customerService.addCustomer(customer);

        verify(customerRepository, times(1)).save(customer);
        Assertions.assertEquals(customer, result);
    }

    @Test
    public void testGetAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> result = customerService.getAllCustomers();

        verify(customerRepository, times(1)).findAll();
        Assertions.assertEquals(customers, result);
    }

    @Test
    public void testRemoveCustomer() {
        Long customerId = 1L;

        customerService.removeCustomer(customerId);

        verify(customerRepository, times(1)).deleteById(customerId);
    }

    @Test
    public void testGetCustomerById() {
        Long customerId = 1L;
        Customer customer = new Customer();
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        Customer result = customerService.getCustomerById(customerId);

        verify(customerRepository, times(1)).findById(customerId);
        Assertions.assertEquals(customer, result);
    }
}
