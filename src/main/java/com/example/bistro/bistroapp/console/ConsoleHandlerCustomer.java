package com.example.bistro.bistroapp.console;

import com.example.bistro.bistroapp.entity.Customer;
import com.example.bistro.bistroapp.service.CustomerService;
import com.example.bistro.bistroapp.service.IngredientService;
import com.example.bistro.bistroapp.service.ProductService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleHandlerCustomer {
    private final ProductService productService;
    private final CustomerService customerService;
    private final IngredientService ingredientService;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleHandlerCustomer(ProductService productService, CustomerService customerService, IngredientService ingredientService) {
        this.productService = productService;
        this.customerService = customerService;
        this.ingredientService = ingredientService;
    }
    public void addCustomer()
    {
        System.out.println("Enter customer first name:");
        String firstName = scanner.next();
        System.out.println("Enter customer last name:");
        String lastName = scanner.next();

        Customer newCustomer = new Customer();
        newCustomer.setFirstName(firstName);
        newCustomer.setLastName(lastName);

        Customer addedCustomer = customerService.addCustomer(newCustomer);
        System.out.println("Added customer with ID: " + addedCustomer.getId());
    }

    public void listAllCustomer()
    {
        List<Customer> allCustomers = customerService.getAllCustomers();
        System.out.println("All Customers:");
        for (Customer customer : allCustomers) {
            System.out.println(customer.getId() + ": " + customer.getFirstName() + " " + customer.getLastName());
        }
    }

    public void deleteCusomer()
    {
        System.out.println("Enter the customer ID:");
        Long customerIdToRemove = scanner.nextLong();

        try {
            customerService.removeCustomer(customerIdToRemove);
            System.out.println("Removed customer with ID " + customerIdToRemove);
        } catch (Exception e) {
            System.out.println("Error removing customer: " + e.getMessage());
        }
    }
}
