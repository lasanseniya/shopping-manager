package models;

import java.io.Serializable;

/**
 * An abstract class that stores the product ID, product name,
 * number of available items and price of an item.
 *
 * @version 1.0
 * @since 2024-01-11
 */

public abstract class Product implements Serializable {
    private String productID;  // Stores productId of an item
    private String productName;  // Stores the product name of an item
    private int numOfAvailableItems;  // Available stock of each product under a product Id
    private double price;  // Price of an item

    /**
     * Constructs Models.Product with the specified details.
     *
     * @param productID The product ID of the product.
     * @param productName The name of the product.
     * @param numOfAvailableItems The number of available items of the product.
     * @param price The price of the product.
     *
     */
    public Product(String productID, String productName,
                   int numOfAvailableItems, double price) {
        this.productID = productID;
        this.productName = productName;
        this.numOfAvailableItems = numOfAvailableItems;
        this.price = price;
    }

    // Getters
    public String getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public int getNumOfAvailableItems() {
        return numOfAvailableItems;
    }

    public double getPrice() {
        return price;
    }

    // Setters
    public void setProductID(String productID) {
        this.productID = productID;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setNumOfAvailableItems(int numOfAvailableItems) {
        this.numOfAvailableItems = numOfAvailableItems;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return Common Product information regarding the
     * product ID, product name, number of available items
     * and price of an item.
     */
    @Override
    public String toString() {
        return  "ProductID: " + productID + "\n" +
                "ProductName: " + productName + "\n" +
                "Available Items: " + numOfAvailableItems + "\n" +
                "Price: " + price
                ;
    }
}