package com.example.bistro.bistroapp.service;

import com.example.bistro.bistroapp.entity.Product;
import com.example.bistro.bistroapp.entity.ProductType;
import com.example.bistro.bistroapp.exception.ProductNotFoundException;
import com.example.bistro.bistroapp.repository.IngredientRepository;
import com.example.bistro.bistroapp.repository.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.util.EnumSet;
import java.util.List;
import java.util.Set;

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
        List <Product> topProducts = productRepository.findTopMostWantedProducts(PageRequest.of(0, count));

        if (topProducts.isEmpty()) {
            System.out.println("There are no orders at the time.");
        }

        return topProducts;
    }


    @Override
    public Product getProductWithIngredients(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
    @Override
    public Product addProduct(Product product) {
        //return productRepository.save(product);
        if (!isValidProductType(product.getType())) {
            throw new IllegalArgumentException("Tipul de produs nu este permis.");
        }
        return productRepository.save(product);
    }
    public boolean isValidProductType(ProductType productType) {
        Set<ProductType> allowedProductTypes = EnumSet.of(
                ProductType.CAKE, ProductType.WAFFLES, ProductType.CROISSANT,
                ProductType.DONUT, ProductType.PASTA, ProductType.PIZZA,
                ProductType.RISOTTO
        );

        return allowedProductTypes.contains(productType);
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

