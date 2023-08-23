package com.example.bistro.bistroapp.console;

import com.example.bistro.bistroapp.entity.Customer;
import com.example.bistro.bistroapp.entity.Order;
import com.example.bistro.bistroapp.entity.Product;
import com.example.bistro.bistroapp.repository.CustomerRepository;
import com.example.bistro.bistroapp.repository.OrderRepository;
import com.example.bistro.bistroapp.repository.ProductRepository;
import com.example.bistro.bistroapp.service.CustomerService;
import com.example.bistro.bistroapp.service.IngredientService;
import com.example.bistro.bistroapp.service.OrderService;
import com.example.bistro.bistroapp.service.ProductService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class ConsoleHandlerOrder {

    private final ProductService productService;
    private final CustomerService customerService;
    private final IngredientService ingredientService;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderService orderService;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleHandlerOrder(ProductService productService, CustomerService customerService, IngredientService ingredientService, CustomerRepository customerRepository, OrderRepository orderRepository, ProductRepository productRepository, OrderService orderService) {
        this.productService = productService;
        this.customerService = customerService;
        this.ingredientService = ingredientService;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderService = orderService;
    }

    public void addAnOrder()
    {
        System.out.println("Introduceti ID-ul clientului pentru comanda:");
        Long customerId = scanner.nextLong();
        scanner.nextLine();

        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();

            Set<Long> selectedProductIds = new HashSet<>();
            double totalAmount = 0;

            boolean addingProducts = true;
            while (addingProducts) {
                System.out.println("Introduceti ID-ul produsului (0 pentru a finaliza comanda):");
                Long productId = scanner.nextLong();
                scanner.nextLine();

                if (productId == 0) {
                    addingProducts = false;
                } else {
                    Optional<Product> productOptional = productRepository.findById(productId);
                    if (productOptional.isPresent()) {
                        selectedProductIds.add(productId);
                        Product product = productOptional.get();
                        totalAmount += product.getPrice();
                    } else {
                        System.out.println("Produsul cu ID-ul " + productId + " nu exista.");
                    }
                }
            }

            Order newOrder = orderService.createOrder(customerId, new ArrayList<>(selectedProductIds));

            newOrder.setCustomer(customer);
            newOrder.setTotalAmount(BigDecimal.valueOf(totalAmount));

            Set<Product> selectedProducts = new HashSet<>();
            for (Long productId : selectedProductIds) {
                Optional<Product> productOptional = productRepository.findById(productId);
                productOptional.ifPresent(selectedProducts::add);
            }
            newOrder.setProducts(selectedProducts);

            orderRepository.save(newOrder);

            System.out.println("Comanda cu ID-ul " + newOrder.getId() + " a fost adaugata.");
        } else {
            System.out.println("Clientul cu ID-ul " + customerId + " nu exista.");
        }
    }

    public void listAllOrder()
    {
        List<Order> allOrders = orderService.getAllOrders();
        System.out.println("All Orders:");
        for (Order order : allOrders) {
            Customer customer = order.getCustomer();
            String customerName = customer != null ? customer.getFirstName() + " " + customer.getLastName() : "Unknown Customer";
            System.out.println("Order ID: " + order.getId() + " | Customer: " + customerName + " | Total Amount: $" + order.getTotalAmount());
        }
    }
}
