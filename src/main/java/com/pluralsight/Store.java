package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Store {

    public static void main(String[] args) {
        // Initializes the inventory and cart, loads products from file, and handles user interaction
        ArrayList<Product> inventory = new ArrayList<>();
        ArrayList<Product> cart = new ArrayList<>();
        double totalAmount = 0.0;

        loadInventory("products.csv", inventory);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nWelcome to the Online Store!");
            System.out.println("1. Show Products");
            System.out.println("2. Show Cart");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    displayProducts(inventory, cart, scanner);
                    break;
                case 2:
                    displayCart(cart, scanner);
                    break;
                case 3:
                    System.out.println("Thank you for shopping with us!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
                    break;
            }
        }
    }

    /**
     * Loads product data from a file into the inventory list.
     * Each line in the file should contain product info in the format: SKU|Name|Price
     */
    public static void loadInventory(String fileName, ArrayList<Product> inventory) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    String id = parts[0];
                    String name = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    inventory.add(new Product(id, name, price));
                }
            }
        } catch (Exception e) {
            System.out.println("Invalid file");
        }
    }

    /**
     * Displays the list of available products and allows the user to add one to the cart by SKU.
     */
    public static void displayProducts(ArrayList<Product> inventory, ArrayList<Product> cart, Scanner scanner) {
        System.out.println("\nAvailable Products:");
        for (Product product : inventory) {
            System.out.println(product);
        }

        System.out.print("\nEnter the SKU of the product to add to cart (or type 'back' to return): ");
        String input = scanner.nextLine();

        if (!input.equalsIgnoreCase("back")) {
            Product selectedProduct = findProductById(input, inventory);
            if (selectedProduct != null) {
                cart.add(selectedProduct);
                System.out.println(selectedProduct.getName() + " added to cart!");
            } else {
                System.out.println("Product not found!");
            }
        }
    }

    /**
     * Displays all products in the user's cart, calculates total price,
     * and gives the user the option to proceed to checkout.
     */
    public static void displayCart(ArrayList<Product> cart, Scanner scanner) {
        if (cart.isEmpty()) {
            System.out.println("\nYour cart is empty.");
            return;
        }

        System.out.println("\nItems in your cart:");
        double totalAmount = 0.0;
        for (Product product : cart) {
            System.out.println(product);
            totalAmount += product.getPrice();
        }

        System.out.printf("Total Amount: $%.2f\n", totalAmount);

        System.out.println("\nOptions:");
        System.out.println("1. Check Out");
        System.out.println("2. Go Back");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            checkOut(cart, totalAmount, scanner);
        }
    }

    /**
     * Handles the checkout process by accepting payment,
     * verifying sufficient amount, printing receipt, and clearing the cart.
     */
    public static void checkOut(ArrayList<Product> cart, double totalAmount, Scanner scanner) {
        System.out.printf("\nYour total is $%.2f\n", totalAmount);
        System.out.print("Enter payment amount: $");

        double payment = scanner.nextDouble();
        scanner.nextLine();

        if (payment >= totalAmount) {
            double change = payment - totalAmount;
            System.out.printf("Payment accepted! Your change is $%.2f\n", change);

            System.out.println("\nReceipt:");
            for (Product product : cart) {
                System.out.printf("%s - $%.2f\n", product.getName(), product.getPrice());
            }
            System.out.printf("Total: $%.2f\n", totalAmount);
            System.out.printf("Payment: $%.2f\n", payment);
            System.out.printf("Change: $%.2f\n", change);

            cart.clear(); // Empty the cart after successful checkout
        } else {
            System.out.println("Insufficient payment. Transaction cancelled.");
        }
    }

    /**
     * Searches the inventory for a product by its SKU (ID).
     * Returns the product if found, or null if not.
     */
    public static Product findProductById(String id, ArrayList<Product> inventory) {
        for (Product product : inventory) {
            if (product.getSku().equalsIgnoreCase(id)) {
                return product;
            }
        }
        return null;
    }
}
