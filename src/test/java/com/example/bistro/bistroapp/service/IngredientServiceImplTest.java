package com.example.bistro.bistroapp.service;

import com.example.bistro.bistroapp.entity.Ingredient;
import com.example.bistro.bistroapp.repository.IngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class IngredientServiceImplTest {
    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private IngredientServiceImpl ingredientService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientServiceImpl(ingredientRepository);
    }

    @Test
    @DisplayName("Adding ingredients")
    public void testAddIngredient() {
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Garlic");

        Mockito.when(ingredientRepository.save(Mockito.any(Ingredient.class))).thenReturn(ingredient);

        Ingredient savedIngredient = ingredientService.addIngredient(ingredient);

        assertNotNull(savedIngredient);
        assertEquals("Garlic", savedIngredient.getName());
    }

    @Test
    @DisplayName("Getting all ingredients")
    public void testGetAllIngredients() {
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setName("Onion");

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setName("Garlic");

        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(ingredient1);
        ingredientList.add(ingredient2);

        Mockito.when(ingredientRepository.findAll()).thenReturn(ingredientList);

        List<Ingredient> retrievedIngredients = ingredientService.getAllIngredients();

        assertNotNull(retrievedIngredients);
        assertEquals(2, retrievedIngredients.size());
    }

    @Test
    @DisplayName("Getting ingredient by ID")
    public void testGetIngredientById() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setName("Onion");

        Mockito.when(ingredientRepository.findById(1L)).thenReturn(java.util.Optional.of(ingredient));

        Ingredient retrievedIngredient = ingredientService.getIngredientById(1L);

        assertNotNull(retrievedIngredient);
        assertEquals("Onion", retrievedIngredient.getName());
    }
}
