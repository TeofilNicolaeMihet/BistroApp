package com.example.bistro.bistroapp.console;

import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class BistroMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final ConsoleHandlerCustomer consoleHandlerCustomer;
    private final ConsoleHandlerIngredient consoleHandlerIngredient;
    private final ConsoleHandlerOrder consoleHandlerOrder;
    private final ConsoleHandlerProduct consoleHandlerProduct;

    public BistroMenu(ConsoleHandlerCustomer consoleHandlerCustomer, ConsoleHandlerIngredient consoleHandlerIngredient, ConsoleHandlerOrder consoleHandlerOrder, ConsoleHandlerProduct consoleHandlerProduct) {
        this.consoleHandlerCustomer = consoleHandlerCustomer;
        this.consoleHandlerIngredient = consoleHandlerIngredient;
        this.consoleHandlerOrder = consoleHandlerOrder;
        this.consoleHandlerProduct = consoleHandlerProduct;
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
                    consoleHandlerProduct.addProduct();
                    break;

                case 2:
                    consoleHandlerProduct.listAllProduct();
                    break;

                case 3:
                    consoleHandlerProduct.getProductWithId();
                    break;
                case 4:
                    consoleHandlerProduct.updateProductPrice();
                    break;
                case 5:
                    consoleHandlerProduct.deleteProduct();
                    break;
                case 6:
                    consoleHandlerCustomer.addCustomer();
                    break;
                case 7:
                    consoleHandlerCustomer.listAllCustomer();
                    break;
                case 8:
                    consoleHandlerCustomer.deleteCusomer();
                    break;
                case 9:
                    int topCount = 3;
                    consoleHandlerProduct.printTopMostWantedProducts(topCount);
                    break;
                case 10:
                    consoleHandlerOrder.addAnOrder();
                    break;
                case 11:
                    consoleHandlerOrder.listAllOrder();
                    break;
                case 12:
                    consoleHandlerIngredient.addIngredient();
                    break;
                case 13:
                    consoleHandlerIngredient.listAllIngredient();
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
