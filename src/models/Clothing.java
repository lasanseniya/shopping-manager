package models;

import java.io.Serializable;

/**
 * Subclass of the Models.Product class.
 * Represents a Models.Clothing product.
 *
 * @version 1.0
 * @since 2024-01-11
 */

public class Clothing extends Product implements Serializable {
    private String color;
    private String size;

    /**
     * Constructs Models.Clothing products with the specified details.
     *
     * @param productID The product ID of the Models.Clothing product.
     * @param productName The name of the Models.Clothing product.
     * @param numOfAvailableItems The number of available items of the Models.Clothing product.
     * @param price The price of the Models.Clothing product.
     * @param color The colour of the Models.Clothing product.
     * @param size The size of the Models.Clothing product.
     *
     */

    public Clothing(String productID, String productName,
             int numOfAvailableItems, double price, String color, String size){
        super(productID, productName, numOfAvailableItems, price);
        this.color = color;
        this.size = size;
    }


    /**
     * @return The color value of the Clothing product
     */
    // Getters
    public String getColor() {
        return color;
    }


    /**
     * @return The size value of the Clothing product
     */
    public String getSize() {
        return size;
    }


    // Setters
    public void setColor(String color) {
        this.color = color;
    }


    public void setSize(String size) {
        this.size = size;
    }


    /**
     * @return Clothing product's information alongside
     * common product information. (call to product class's
     * super.toString)
     */
    @Override
    public String toString() {
        return super.toString() + "\n" +
                "Colour: " + color + "\n" +
                "Size: " + size + "\n"
                ;
    }
}