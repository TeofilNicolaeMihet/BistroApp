package com.example.bistro.bistroapp.service;

import com.example.bistro.bistroapp.entity.Ingredient;
import java.util.List;

public interface IngredientService {
    Ingredient addIngredient(Ingredient ingredient);
    List<Ingredient> getAllIngredients();
    Ingredient getIngredientById(Long id);

}
