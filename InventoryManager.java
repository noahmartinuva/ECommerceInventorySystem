package com.inventory;

import java.io.*;
import java.util.*;

public class InventoryManager {
    private List<Product> products;
    private List<Sale> sales;
    private double totalRevenue;

    public InventoryManager() {
        products = new ArrayList<>();
        sales = new ArrayList<>();
        totalRevenue = 0.0;
    }

    public void addProduct(Product product) {
        for (Product p : products) {
            if (p.getId() == product.getId()) {
                System.out.println("Error: A product with ID " + product.getId() + " already exists.");
                return;
            }
        }
        products.add(product);
        System.out.println("Product added: " + product);
    }

    public void removeProduct(int id) {
        Product product = findProductById(id);
        if (product != null) {
            products.remove(product);
            System.out.println("Product removed: " + product.getName());
        } else {
            System.out.println("No product found with ID: " + id);
        }
    }

    public Product findProductById(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    public void displayProducts() {
        System.out.println("ID    | Name         | Category   | Price    | Quantity ");
        System.out.println("---------------------------------------------------------");
        for (Product product : products) {
            System.out.println(product);
        }
    }

    public void purchaseProduct(int id, int quantity, double salePercentage) {
        Product product = findProductById(id);
        if (product == null) {
            System.out.println("Product not found with ID: " + id);
            return;
        }
        if (product.getQuantity() < quantity) {
            System.out.println("Not enough stock available.");
            return;
        }

        product.setQuantity(product.getQuantity() - quantity);
        double originalPrice = quantity * product.getPrice();
        double discount = originalPrice * (salePercentage / 100);
        double saleAmount = originalPrice - discount;

        totalRevenue += saleAmount;
        sales.add(new Sale(id, product.getName(), quantity, saleAmount));

        System.out.printf("Purchase successful! Revenue added: $%.2f (%.2f%% discount applied)%n", saleAmount, salePercentage);
    }

    public void displayRevenue() {
        System.out.printf("Total Revenue: $%.2f%n", totalRevenue);
    }

    public boolean saveToCSV(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (Product product : products) {
                String line = product.getId() + "," +
                        product.getName() + "," +
                        product.getCategory() + "," +
                        product.getPrice() + "," +
                        product.getQuantity();
                writer.write(line + "\n");
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean loadFromCSV(String fileName) {
        products.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String category = parts[2];
                    double price = Double.parseDouble(parts[3]);
                    int quantity = Integer.parseInt(parts[4]);
                    products.add(new Product(id, name, category, price, quantity));
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }



    public void sortProducts(Comparator<Product> comparator) {
        products.sort(comparator);
        System.out.println("Products sorted successfully.");
    }

    public void searchByName(String name) {
        System.out.println("Search Results:");
        for (Product product : products) {
            if (product.getName().toLowerCase().contains(name.toLowerCase())) {
                System.out.println(product);
            }
        }
    }

    public void searchByCategory(String category) {
        System.out.println("Search Results:");
        for (Product product : products) {
            if (product.getCategory().equalsIgnoreCase(category)) {
                System.out.println(product);
            }
        }
    }

    public void searchByPriceRange(double minPrice, double maxPrice) {
        System.out.println("Search Results:");
        for (Product product : products) {
            if (product.getPrice() >= minPrice && product.getPrice() <= maxPrice) {
                System.out.println(product);
            }
        }
    }

    public void displaySalesReport() {
        System.out.println("ID    | Name         | Quantity | Revenue  ");
        System.out.println("--------------------------------------------");
        for (Sale sale : sales) {
            System.out.println(sale);
        }
        displayRevenue();
    }
}

