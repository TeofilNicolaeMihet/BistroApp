package com.example.bistro.bistroapp.repository;

import com.example.bistro.bistroapp.entity.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {




    @Query("SELECT p FROM Products p LEFT JOIN p.orders o GROUP BY p ORDER BY COUNT(o) DESC")
    List<Product> findTopMostWantedProducts(Pageable pageable);
    
}
