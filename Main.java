package com.inventory;

import java.util.Comparator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        InventoryManager inventoryManager = new InventoryManager();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        if (!inventoryManager.loadFromCSV("inventory.csv")) {
            System.out.println("No inventory file found. Starting with an empty inventory.");
        }

        while (!exit) {
            System.out.println("\n=== E-Commerce Inventory System ===");
            System.out.println("1. Add Product");
            System.out.println("2. Display Products");
            System.out.println("3. Remove Product");
            System.out.println("4. Purchase Product");
            System.out.println("5. Search Products");
            System.out.println("6. Sort Products");
            System.out.println("7. Display Sales Report");
            System.out.println("8. Save Inventory");
            System.out.println("9. Load Inventory");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.println("Enter Product ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter Product Name: ");
                    String name = scanner.nextLine();
                    System.out.println("Enter Product Category: ");
                    String category = scanner.nextLine();
                    System.out.println("Enter Product Price: ");
                    double price = scanner.nextDouble();
                    System.out.println("Enter Product Quantity: ");
                    int quantity = scanner.nextInt();

                    Product product = new Product(id, name, category, price, quantity);
                    inventoryManager.addProduct(product);
                }
                case 2 -> inventoryManager.displayProducts();
                case 3 -> {
                    System.out.println("Enter Product ID to remove: ");
                    int removeId = scanner.nextInt();
                    inventoryManager.removeProduct(removeId);
                }
                case 4 -> {
                    System.out.println("Enter Product ID to purchase: ");
                    int purchaseId = scanner.nextInt();
                    System.out.println("Enter Quantity to purchase: ");
                    int purchaseQuantity = scanner.nextInt();
                    System.out.println("Enter Sale Percentage (0 for no discount): ");
                    double salePercentage = scanner.nextDouble();
                    inventoryManager.purchaseProduct(purchaseId, purchaseQuantity, salePercentage);
                }
                case 5 -> {
                    System.out.println("Search by: 1) Name, 2) Category, 3) Price Range");
                    int searchChoice = scanner.nextInt();
                    scanner.nextLine();
                    if (searchChoice == 1) {
                        System.out.println("Enter name to search: ");
                        String name = scanner.nextLine();
                        inventoryManager.searchByName(name);
                    } else if (searchChoice == 2) {
                        System.out.println("Enter category to search: ");
                        String category = scanner.nextLine();
                        inventoryManager.searchByCategory(category);
                    } else if (searchChoice == 3) {
                        System.out.println("Enter minimum price: ");
                        double minPrice = scanner.nextDouble();
                        System.out.println("Enter maximum price: ");
                        double maxPrice = scanner.nextDouble();
                        inventoryManager.searchByPriceRange(minPrice, maxPrice);
                    } else {
                        System.out.println("Invalid choice.");
                    }
                }
                case 6 -> {
                    System.out.println("Sort by: 1) Name, 2) Price, 3) Quantity");
                    int sortChoice = scanner.nextInt();
                    switch (sortChoice) {
                        case 1 -> inventoryManager.sortProducts(Comparator.comparing(Product::getName));
                        case 2 -> inventoryManager.sortProducts(Comparator.comparingDouble(Product::getPrice));
                        case 3 -> inventoryManager.sortProducts(Comparator.comparingInt(Product::getQuantity));
                        default -> System.out.println("Invalid choice.");
                    }
                }
                case 7 -> inventoryManager.displaySalesReport();
                case 8 -> {
                    if (inventoryManager.saveToCSV("inventory.csv")) {
                        System.out.println("Inventory saved successfully.");
                    } else {
                        System.out.println("Failed to save inventory.");
                    }
                }
                case 9 -> {
                    if (inventoryManager.loadFromCSV("inventory.csv")) {
                        System.out.println("Inventory loaded successfully.");
                    } else {
                        System.out.println("Failed to load inventory.");
                    }
                }
                case 10 -> {
                    exit = true;
                    System.out.println("Exiting the system. Goodbye!");
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
