package com.example.bistro.bistroapp.console;

import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class BistroMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final ConsoleHandlerCustomer consolHandlerCustomer;
    private final ConsoleHandlerIngredient consolHandlerIngredient;
    private final ConsoleHandlerOrder consolHandlerOrder;
    private final ConsoleHandlerProduct consolHandlerProduct;

    public BistroMenu(ConsoleHandlerCustomer consolHandlerCustomer, ConsoleHandlerIngredient consolHandlerIngredient, ConsoleHandlerOrder consolHandlerOrder, ConsoleHandlerProduct consolHandlerProduct) {
        this.consolHandlerCustomer = consolHandlerCustomer;
        this.consolHandlerIngredient = consolHandlerIngredient;
        this.consolHandlerOrder = consolHandlerOrder;
        this.consolHandlerProduct = consolHandlerProduct;
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
                    consolHandlerProduct.addProduct();
                    break;

                case 2:
                    consolHandlerProduct.listAllProduct();
                    break;

                case 3:
                    consolHandlerProduct.getProductWithID();
                    break;
                case 4:
                    consolHandlerProduct.updateProductPrice();
                    break;
                case 5:
                    consolHandlerProduct.deleteProduct();
                    break;
                case 6:
                    consolHandlerCustomer.addCustomer();
                    break;
                case 7:
                    consolHandlerCustomer.listAllCustomer();
                    break;
                case 8:
                    consolHandlerCustomer.deleteCusomer();
                    break;
                case 9:
                    int topCount = 3;
                    consolHandlerProduct.printTopMostWantedProducts(topCount);
                    break;
                case 10:
                    consolHandlerOrder.addAnOrder();
                    break;
                case 11:
                    consolHandlerOrder.listAllOrder();
                    break;
                case 12:
                    consolHandlerIngredient.addIngredient();
                    break;
                case 13:
                    consolHandlerIngredient.listAllIngredient();
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
}
