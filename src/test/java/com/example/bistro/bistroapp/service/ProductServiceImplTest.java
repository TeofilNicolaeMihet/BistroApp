package com.example.bistro.bistroapp.service;

import com.example.bistro.bistroapp.entity.Product;
import com.example.bistro.bistroapp.entity.ProductType;
import com.example.bistro.bistroapp.exception.ProductNotFoundException;
import com.example.bistro.bistroapp.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Assertions;

public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Getting top most wanted products")
    public void testGetTopMostWantedProducts() {
        List<Product> products = new ArrayList<>();
        when(productRepository.findTopMostWantedProducts(PageRequest.of(0, 3))).thenReturn(products);

        List<Product> result = productService.getTopMostWantedProducts(3);

        verify(productRepository, times(1)).findTopMostWantedProducts(PageRequest.of(0, 3));
        Assertions.assertEquals(products, result);
    }

    @Test
    @DisplayName("Getting product with ID")
    public void testGetProductWithIngredients() {
        Long productId = 1L;
        Product product = new Product();
        when(productRepository.findById(productId)).thenReturn(java.util.Optional.of(product));

        Product result = productService.getProductWithIngredients(productId);

        verify(productRepository, times(1)).findById(productId);
        Assertions.assertEquals(product, result);
    }

    @Test
    @DisplayName("Adding product")
    public void testAddProduct_ValidType() {
        Product product = new Product();
        product.setType(ProductType.PIZZA);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.addProduct(product);

        verify(productRepository, times(1)).save(product);
        Assertions.assertEquals(product, result);
    }

    @Test
    @DisplayName("Getting all product")
    public void testGetAllProducts() {
        List<Product> products = new ArrayList<>();
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();

        verify(productRepository, times(1)).findAll();
        Assertions.assertEquals(products, result);
    }

    @Test
    @DisplayName("Getting product by ID")
    public void testGetProductById() {
        Long productId = 1L;
        Product product = new Product();
        when(productRepository.findById(productId)).thenReturn(java.util.Optional.of(product));

        Product result = productService.getProductById(productId);

        verify(productRepository, times(1)).findById(productId);
        Assertions.assertEquals(product, result);
    }

    @Test
    @DisplayName("Updating product price")
    public void testUpdateProductPrice() {
        Long productId = 1L;
        double newPrice = 15.0;

        Product product = new Product();
        when(productRepository.findById(productId)).thenReturn(java.util.Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.updateProductPrice(productId, newPrice);

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(product);
        Assertions.assertEquals(newPrice, result.getPrice());
    }

    @Test
    @DisplayName("Removing product")
    public void testRemoveProduct() {
        Long productId = 1L;

        productService.removeProduct(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }

}

