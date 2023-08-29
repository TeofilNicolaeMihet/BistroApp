package com.example.bistro.bistroapp.repository;

import com.example.bistro.bistroapp.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
