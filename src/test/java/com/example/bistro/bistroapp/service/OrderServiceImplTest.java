package com.example.bistro.bistroapp.service;


import com.example.bistro.bistroapp.entity.Customer;
import com.example.bistro.bistroapp.entity.Order;
import com.example.bistro.bistroapp.entity.Product;
import com.example.bistro.bistroapp.repository.CustomerRepository;
import com.example.bistro.bistroapp.repository.OrderRepository;
import com.example.bistro.bistroapp.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class OrderServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateOrder() {
        Long customerId = 1L;
        List<Long> productIds = Collections.singletonList(2L);

        Customer customer = new Customer();
        customer.setId(customerId);

        Product product = new Product();
        product.setId(2L);
        product.setPrice(10.0);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(productRepository.findAllById(productIds)).thenReturn(Collections.singletonList(product));
        when(orderRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0)); // Return the saved order

        Order order = orderService.createOrder(customerId, productIds);

        assertNotNull(order);
        assertEquals(10.0, order.getTotalAmount()); // Verify total amount
    }

    @Test
    public void testGetAllOrders() {
        Order order1 = new Order();
        Order order2 = new Order();

        when(orderRepository.findAll()).thenReturn(List.of(order1, order2));

        List<Order> orders = orderService.getAllOrders();

        assertEquals(2, orders.size());
    }

    @Test
    public void testGetOrderById() {
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        Order retrievedOrder = orderService.getOrderById(orderId);

        assertNotNull(retrievedOrder);
        assertEquals(orderId, retrievedOrder.getId());
    }

    @Test
    public void testRemoveOrder() {
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        orderService.removeOrder(orderId);

        verify(orderRepository, times(1)).delete(order);
    }
}
