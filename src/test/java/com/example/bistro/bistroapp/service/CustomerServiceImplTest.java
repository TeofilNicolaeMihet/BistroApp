package com.example.bistro.bistroapp.service;

import com.example.bistro.bistroapp.entity.Customer;
import com.example.bistro.bistroapp.exception.CustomerNotFoundException;
import com.example.bistro.bistroapp.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
    }

    @Test
    @DisplayName("Adding Customer")
    public void testAddCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Teofil");
        customer.setLastName("Mihet");
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer result = customerService.addCustomer(customer);

        verify(customerRepository, times(1)).save(customer);
        Assertions.assertEquals(customer, result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("Teofil", result.getFirstName());
        Assertions.assertEquals("Mihet", result.getLastName());
    }

    @Test
    @DisplayName("Getting all customers")
    public void testGetAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> result = customerService.getAllCustomers();

        verify(customerRepository, times(1)).findAll();
        Assertions.assertEquals(customers, result);
    }

    @Test
    @DisplayName("Removing customer")
    public void testRemoveCustomer() {
        Long customerId = 1L;

        customerService.removeCustomer(customerId);

        verify(customerRepository, times(1)).deleteById(customerId);
    }

    @Test
    @DisplayName("Getting customer by Id")
    public void testGetCustomerById() {
        Long customerId = 1L;
        Customer customer = new Customer();
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        Customer result = customerService.getCustomerById(customerId);

        verify(customerRepository, times(1)).findById(customerId);
        Assertions.assertEquals(customer, result);
    }
    @Test
    @DisplayName("Gedding customer - negative case")
    public void testAddCustomerNegativeCase() {

        when(customerRepository.save(any(Customer.class))).thenThrow(new RuntimeException("Customer already exists"));

        Assertions.assertThrows(RuntimeException.class, () -> {
            customerService.addCustomer(new Customer());
        });
    }

    @Test
    @DisplayName("Getting customer by ID - negative case")
    public void testGetCustomerByIdNegativeCase() {

        Long customerId = 1L;
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        Assertions.assertThrows(CustomerNotFoundException.class, () -> {
            customerService.getCustomerById(customerId);
        });
    }
}
