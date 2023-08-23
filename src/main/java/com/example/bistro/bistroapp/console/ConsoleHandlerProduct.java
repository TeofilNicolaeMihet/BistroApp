package com.example.bistro.bistroapp.console;

import com.example.bistro.bistroapp.entity.Ingredient;
import com.example.bistro.bistroapp.entity.Product;
import com.example.bistro.bistroapp.entity.ProductType;
import com.example.bistro.bistroapp.service.CustomerService;
import com.example.bistro.bistroapp.service.IngredientService;
import com.example.bistro.bistroapp.service.ProductService;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class ConsoleHandlerProduct {
    private final ProductService productService;
    private final CustomerService customerService;
    private final IngredientService ingredientService;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleHandlerProduct(ProductService productService, CustomerService customerService, IngredientService ingredientService) {
        this.productService = productService;
        this.customerService = customerService;
        this.ingredientService = ingredientService;
    }

    public void addProduct() {
        System.out.println("Enter product type (CAKE, WAFFLES, CROISSANT, DONUT, PASTA, PIZZA, RISOTTO): ");
        String productTypeStr = scanner.nextLine();
        ProductType productType = ProductType.valueOf(productTypeStr);

        if (!isProductTypeAllowed(productType)) {
            System.out.println("Invalid product type. Please choose from: CAKE, WAFFLES, CROISSANT, DONUT, PASTA, PIZZA, RISOTTO");
            return;
        }
        System.out.println("Enter product name: ");
        String productName = scanner.nextLine();

        System.out.println("Enter product price: ");
        double productPrice = scanner.nextDouble();
        scanner.nextLine();

        Product newProduct = new Product();
        newProduct.setName(productName);
        newProduct.setPrice(productPrice);
        newProduct.setType(productType);

        System.out.println("Enter the number of ingredients for the product: ");
        int numIngredients = scanner.nextInt();
        scanner.nextLine();

        Set<Ingredient> ingredients = new HashSet<>();
        for (int i = 0; i < numIngredients; i++) {
            System.out.println("Enter ingredient ID: ");
            Long ingredientId = scanner.nextLong();
            scanner.nextLine();

            Ingredient ingredient = ingredientService.getIngredientById(ingredientId);
            if (ingredient != null) {
                ingredients.add(ingredient);
            } else {
                System.out.println("Ingredient with ID " + ingredientId + " not found. Skipping...");
            }
        }

        newProduct.setIngredients(ingredients);

        Product addedProduct = productService.addProduct(newProduct);
        System.out.println("Added product with ID: " + addedProduct.getId());
    }
    @Transactional
    public  void listAllProduct()
    {
        List<Product> allProducts = productService.getAllProducts();
        System.out.println("All Products:");
        for (Product product : allProducts) {
            System.out.println(product.getId() + ": " + product.getName() + " - $" + product.getPrice());
            Product productWithIngredients = productService.getProductWithIngredients(product.getId());
            Set<Ingredient> ingredients = product.getIngredients();
            Hibernate.initialize(ingredients);
            System.out.println("Ingredients:");
            for (Ingredient ingredient : productWithIngredients.getIngredients()) {
                System.out.println("- " + ingredient.getName());
            }
        }
    }
    public void getProductWithID()
    {
        System.out.println("Enter the product ID:");
        Long productId = scanner.nextLong();
        Product productById = productService.getProductById(productId);
        if (productById != null) {
            System.out.println("Product with ID " + productId + ": " + productById.getName() + " - $" + productById.getPrice());
        } else {
            System.out.println("Product with ID " + productId + " not found.");
        }
    }
    public void updateProductPrice()
    {
        System.out.println("Enter the product ID:");
        Long productIdToUpdate = scanner.nextLong();

        System.out.println("Enter the new price:");
        double newPrice = scanner.nextDouble();

        Product updatedProduct = productService.updateProductPrice(productIdToUpdate, newPrice);
        if (updatedProduct != null) {
            System.out.println("Updated price of Product with ID " + productIdToUpdate + " to $" + updatedProduct.getPrice());
        } else {
            System.out.println("Product with ID " + productIdToUpdate + " not found.");
        }
    }

    public void deleteProduct()
    {
        System.out.println("Enter the product ID:");
        Long productIdToRemove = scanner.nextLong();

        try {
            productService.removeProduct(productIdToRemove);
            System.out.println("Removed product with ID " + productIdToRemove);
        } catch (Exception e) {
            System.out.println("Error removing product: " + e.getMessage());
        }
    }
    public void printTopMostWantedProducts(int topCount) {
        List<Product> topProducts = productService.getTopMostWantedProducts(topCount);
        System.out.println("Top " + topCount + " Most Popular Products:");
        for (Product product : topProducts) {
            System.out.println(product.getName());
        }
    }
    private boolean isProductTypeAllowed(ProductType type) {
        Set<ProductType> allowedTypes = EnumSet.of(
                ProductType.CAKE, ProductType.WAFFLES, ProductType.CROISSANT,
                ProductType.DONUT, ProductType.PASTA, ProductType.PIZZA,
                ProductType.RISOTTO
        );

        return allowedTypes.contains(type);
    }
}