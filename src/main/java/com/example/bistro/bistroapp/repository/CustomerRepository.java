package com.example.bistro.bistroapp.repository;

import com.example.bistro.bistroapp.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, Long> {
}