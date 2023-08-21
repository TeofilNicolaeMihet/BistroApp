package com.example.bistro.bistroapp.console;


import com.example.bistro.bistroapp.entity.Customer;
import com.example.bistro.bistroapp.entity.Ingredient;
import com.example.bistro.bistroapp.entity.Order;
import com.example.bistro.bistroapp.entity.Product;
import com.example.bistro.bistroapp.repository.CustomerRepository;
import com.example.bistro.bistroapp.repository.IngredientRepository;
import com.example.bistro.bistroapp.repository.OrderRepository;
import com.example.bistro.bistroapp.repository.ProductRepository;
import com.example.bistro.bistroapp.service.CustomerService;
import com.example.bistro.bistroapp.service.IngredientService;
import com.example.bistro.bistroapp.service.OrderService;
import com.example.bistro.bistroapp.service.ProductService;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


import java.util.*;

@Component
public class BistroM {

    private final Scanner scanner = new Scanner(System.in);


    private final CustomerService customerService;
    private final ProductService productService;
    private final IngredientService ingredientService;
    private final CustomerRepository customerRepository;
    private final IngredientRepository ingredientRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final ApplicationContext context;

    @Autowired
    public BistroM(CustomerService customerService, ProductService productService, IngredientService ingredientService, CustomerRepository customerRepository, IngredientRepository ingredientRepository, ProductRepository productRepository, OrderRepository orderRepository, OrderService orderService, ApplicationContext context) {
        this.customerService = customerService;
        this.productService = productService;
        this.ingredientService = ingredientService;
        this.customerRepository = customerRepository;
        this.ingredientRepository = ingredientRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderService = orderService;
        this.context = context;
    }
    public void runConsole() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Bistro Management System");
            System.out.println("1. Add a product");
            System.out.println("2. List all products");
            System.out.println("3. List a product by ID");
            System.out.println("4. Update product price");
            System.out.println("5. Remove a product");
            System.out.println("6. Add a customer");
            System.out.println("7. List all customers");
            System.out.println("8. Remove a customer");
            System.out.println("9. List top 3 most wanted products");
            System.out.println("10. Add an order");
            System.out.println("11. List all orders");
            System.out.println("12. Add an ingredient");
            System.out.println("13. List all ingredients");

            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:

                    System.out.println("Enter product name: ");
                    String productName = scanner.nextLine();

                    System.out.println("Enter product price: ");
                    double productPrice = scanner.nextDouble();
                    scanner.nextLine();

                    Product newProduct = new Product();
                    newProduct.setName(productName);
                    newProduct.setPrice(productPrice);

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
                    break;

                case 2:

                    List<Product> allProducts = productService.getAllProducts();
                    System.out.println("All Products:");
                    for (Product product : allProducts) {
                        System.out.println(product.getId() + ": " + product.getName() + " - $" + product.getPrice());

                    }

                    break;

                case 3:
                    System.out.println("Enter the product ID:");
                    Long productId = scanner.nextLong();
                    Product productById = productService.getProductById(productId);
                    if (productById != null) {
                        System.out.println("Product with ID " + productId + ": " + productById.getName() + " - $" + productById.getPrice());
                    } else {
                        System.out.println("Product with ID " + productId + " not found.");
                    }
                    break;
                case 4:
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
                    break;
                case 5:
                    System.out.println("Enter the product ID:");
                    Long productIdToRemove = scanner.nextLong();

                    try {
                        productService.removeProduct(productIdToRemove);
                        System.out.println("Removed product with ID " + productIdToRemove);
                    } catch (Exception e) {
                        System.out.println("Error removing product: " + e.getMessage());
                    }
                    break;
                case 6:
                    System.out.println("Enter customer first name:");
                    String firstName = scanner.next();
                    System.out.println("Enter customer last name:");
                    String lastName = scanner.next();

                    Customer newCustomer = new Customer();
                    newCustomer.setFirstName(firstName);
                    newCustomer.setLastName(lastName);

                    Customer addedCustomer = customerService.addCustomer(newCustomer);
                    System.out.println("Added customer with ID: " + addedCustomer.getId());
                    break;
                case 7:
                    List<Customer> allCustomers = customerService.getAllCustomers();
                    System.out.println("All Customers:");
                    for (Customer customer : allCustomers) {
                        System.out.println(customer.getId() + ": " + customer.getFirstName() + " " + customer.getLastName());
                    }
                    break;
                case 8:
                    System.out.println("Enter the customer ID:");
                    Long customerIdToRemove = scanner.nextLong();

                    try {
                        customerService.removeCustomer(customerIdToRemove);
                        System.out.println("Removed customer with ID " + customerIdToRemove);
                    } catch (Exception e) {
                        System.out.println("Error removing customer: " + e.getMessage());
                    }
                    break;
                case 9:
    int topCount = 3;
    List<Product> topProducts = productService.getTopMostWantedProducts(topCount);
    System.out.println("Top " + topCount + " Most Popular Products:");
    for (Product product : topProducts) {
        System.out.println(product.getName());
    }
    break;

                case 10:
                    introducereComanda();
                    break;
                case 11:
                    afiseazaToateComenzile();
                    break;
                case 12:
                    Set<Ingredient> addedIngredients = introducereIngrediente();
                    for (Ingredient ingredient : addedIngredients) {
                        ingredientService.addIngredient(ingredient);
                    }
                    System.out.println("Ingredients added successfully.");
                    break;
                case 13:
                    List<Ingredient> allIngredients = ingredientService.getAllIngredients();
                    System.out.println("All Ingredients:");
                    for (Ingredient ingredient : allIngredients) {
                        System.out.println(ingredient.getId() + ": " + ingredient.getName());
                    }
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");

            }
        }
        scanner.close();
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
    private Set<Ingredient> introducereIngrediente() {
        Set<Ingredient> ingredients = new HashSet<>();
        boolean addingIngredients = true;
        while (addingIngredients) {
            System.out.println("Enter ingredient name (0 to finish adding ingredients): ");
            String ingredientName = scanner.nextLine();

            if (ingredientName.equals("0")) {
                addingIngredients = false;
            } else {
                System.out.println("Enter ingredient cost: ");
                double ingredientCost = scanner.nextDouble();
                scanner.nextLine();

                Ingredient ingredient = new Ingredient();
                ingredient.setName(ingredientName);
                ingredient.setCost(ingredientCost);

                ingredients.add(ingredient);
            }
        }

        return ingredients;
    }


    private void introducereComanda() {
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
            newOrder.setTotalAmount(totalAmount);

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
    private void afiseazaToateComenzile() {
        List<Order> allOrders = orderService.getAllOrders();
        System.out.println("All Orders:");
        for (Order order : allOrders) {
            Customer customer = order.getCustomer();
            String customerName = customer != null ? customer.getFirstName() + " " + customer.getLastName() : "Unknown Customer";
            System.out.println("Order ID: " + order.getId() + " | Customer: " + customerName + " | Total Amount: $" + order.getTotalAmount());

            ///////
        }
    }

}
