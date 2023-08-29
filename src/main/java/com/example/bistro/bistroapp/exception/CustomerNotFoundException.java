package com.example.bistro.bistroapp.exception;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(Long customerId) {
        super("Customer not found with ID: " + customerId);
    }
}
