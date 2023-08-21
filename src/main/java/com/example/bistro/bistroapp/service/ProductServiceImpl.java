package com.example.bistro.bistroapp.service;

import com.example.bistro.bistroapp.entity.Product;
import com.example.bistro.bistroapp.exception.ProductNotFoundException;
import com.example.bistro.bistroapp.repository.IngredientRepository;
import com.example.bistro.bistroapp.repository.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final IngredientRepository ingredientRepository;
    public ProductServiceImpl(ProductRepository productRepository, IngredientRepository ingredientRepository) {
        this.productRepository = productRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<Product> getTopMostWantedProducts(int count) {
        return productRepository.findTopMostWantedProducts(PageRequest.of(0, count));
    }


    @Override
    public Product getProductWithIngredients(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
    @Override
    public Product updateProductPrice(Long id, double newPrice) {
        Product product = getProductById(id);
        product.setPrice(newPrice);
        return productRepository.save(product);
    }
    @Override
    public void removeProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }
}

