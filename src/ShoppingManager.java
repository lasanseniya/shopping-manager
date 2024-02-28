import models.Product;

/**
 * Interface for ShoppingManager
 *
 * @version 1.0
 * @since 2024-01-11
 *
 */
public interface ShoppingManager {
    void addProduct(Product product);
    void deleteProduct(String productID);
    void listOfProducts();
    void saveProductsToFile(String filePath);
}