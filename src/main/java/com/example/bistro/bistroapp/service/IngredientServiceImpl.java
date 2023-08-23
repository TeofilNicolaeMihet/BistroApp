package com.example.bistro.bistroapp.service;

import com.example.bistro.bistroapp.entity.Ingredient;
import com.example.bistro.bistroapp.exception.IngredientNotFoundException;
import com.example.bistro.bistroapp.repository.IngredientRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }
    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    @Override
    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    @Override
    public Ingredient getIngredientById(Long id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new IngredientNotFoundException(id));
    }

}
