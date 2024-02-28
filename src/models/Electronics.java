package models;

/**
 * Subclass of Product class.
 * Represents an Electronics product.
 *
 * @version 1.0
 * @since 2024-01-11
 */
public class Electronics extends Product{
    private String brand;
    private int warrantyPeriod;

    /**
     * Constructs Electronic products with the specified details.
     *
     * @param productID The product ID of the Electronics product.
     * @param productName The name of the Electronics product.
     * @param numOfAvailableItems The number of available items of the Electronics product.
     * @param price The price of the Electronics product.
     * @param brand The brand of the Electronics product.
     * @param warrantyPeriod The warranty period of the Electronics product.
     */
    public Electronics(String productID, String productName,
                       int numOfAvailableItems, double price,
                       String brand, int warrantyPeriod){

        super(productID, productName, numOfAvailableItems, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }


    // Getters
    public String getBrand() {
        return brand;
    }


    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }


    // Setters
    public void setBrand(String brand) {
        this.brand = brand;
    }


    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }


    /**
     * @return Electronics product's information alongside
     * common product information.(call to product class's
     * super.toString)
     */
    @Override
    public String toString() {
        return super.toString() + "\n" +
                "Brand: " + brand + "\n" +
                "Warranty Period: " + warrantyPeriod + "\n"
                ;
    }
}