package com.example.bistro.bistroapp.service;

import com.example.bistro.bistroapp.entity.Customer;
import com.example.bistro.bistroapp.entity.Order;
import com.example.bistro.bistroapp.entity.Product;
import com.example.bistro.bistroapp.exception.CustomerNotFoundException;
import com.example.bistro.bistroapp.exception.OrderNotFoundException;
import com.example.bistro.bistroapp.repository.CustomerRepository;
import com.example.bistro.bistroapp.repository.IngredientRepository;
import com.example.bistro.bistroapp.repository.OrderRepository;
import com.example.bistro.bistroapp.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final IngredientRepository ingredientRepository;

    public OrderServiceImpl(
            CustomerRepository customerRepository,
            ProductRepository productRepository,
            OrderRepository orderRepository,
            IngredientRepository ingredientRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.ingredientRepository = ingredientRepository;
    }

        @Override
    public Order createOrder(Long customerId, List<Long> productIds) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

        List<Product> selectedProducts = productRepository.findAllById(productIds);

        double totalAmount = selectedProducts.stream()
                .mapToDouble(Product::getPrice)
                .sum();

        Order order = new Order();
        order.setTotalAmount(BigDecimal.valueOf(totalAmount));

        return orderRepository.save(order);
    }
    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }
    @Override
    public void removeOrder(Long id) {
        Order order = getOrderById(id);
        orderRepository.delete(order);
    }
}
