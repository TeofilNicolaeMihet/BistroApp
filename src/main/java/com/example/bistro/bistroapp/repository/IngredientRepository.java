package com.example.bistro.bistroapp.repository;

import com.example.bistro.bistroapp.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

}