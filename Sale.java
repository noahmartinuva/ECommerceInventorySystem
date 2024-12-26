package com.inventory;

public class Sale {
    private int productId;
    private String productName;
    private int quantitySold;
    private double saleAmount;

    public Sale(int productId, String productName, int quantitySold, double saleAmount) {
        this.productId = productId;
        this.productName = productName;
        this.quantitySold = quantitySold;
        this.saleAmount = saleAmount;
    }

    @Override
    public String toString() {
        return String.format("%-5d | %-12s | %-8d | $%-8.2f",
                productId, productName, quantitySold, saleAmount);
    }
}
