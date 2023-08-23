package com.example.bistro.bistroapp.exception;

public class IngredientNotFoundException extends RuntimeException {

    public IngredientNotFoundException(Long ingredientId) {
        super("Ingredient not found with ID: " + ingredientId);
    }
}
