import models.Clothing;
import models.Electronics;
import models.Product;
import models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the shopping cart in the system.
 * Implemented as a singleton class, to ensure
 * that only one instance of the shopping cart exists.
 *.
 * `````````````````````````````````````````````````````````````````````````````````````````````````````````````````
 * References:
 *  <a href="https://medium.com/@thecodebean/singleton-design-pattern-implementation-in-java-1fba4ecc959f">...</a>
 *..................................................................................................................
 */
public class ShoppingCart {
    private static ShoppingCart instance;

    private User currentUser;

    private CartPage cartPage;

    private final List<Product> cartProducts = new ArrayList<>();

    /**
     * Sets the current cart page.
     * @param cartPage The cart page to be set.
     */
    public void setCartPage(CartPage cartPage) {
        this.cartPage = cartPage;
    }


    /**
     * Private constructor to prevent the creation
     * of multiple instances of the shopping cart.
     */
    private ShoppingCart() {}


    /**
     * Returns the single instance of the ShoppingCart class
     * Creates an instance if there is none.
     * @return The single instance of the ShoppingCart class.
     */
    public static ShoppingCart getInstance() {
        if (instance == null) {
            instance = new ShoppingCart();
        }
        return instance;
    }


    /**
     * Adds a product to the shopping cart.
     * @param product The product to be added to the cart.
     * @return True if product was added, false otherwise
     */
    public boolean addToCart(Product product){
        if (product.getNumOfAvailableItems() > 0){
            cartProducts.add(product);
            product.setNumOfAvailableItems(product.getNumOfAvailableItems() - 1);  // Updating the stock of the specific product

            if (cartPage != null) {
                cartPage.updatePriceLabels();
                cartPage.updateTableWithProducts(); // update the table
            }
            return true;

        } else {
            return false;
        }
    }


    /**
     * @param user The user to calculate the discount.
     * @return An array containing the 10% new user
     * discount and the additional 30% discount.
     */
    public double[] calculateDiscount(User user) {
        double discount = 0.0;
        double additionalDiscount = 0.0;

        // Check if the user is new
        if (!user.hasPurchaseHistory()) {
            discount = 0.10;  // 10% discount
        }

        // Count the number of Electronics and Clothing items in the cart for discount calculation
        int electronicsCount = 0;
        int clothingCount = 0;
        for (Product product : cartProducts) {
            if (product instanceof Electronics) {
                electronicsCount++;
            } else if (product instanceof Clothing) {
                clothingCount++;
            }
        }

        // Check if the cart has at least three items of the same category
        if (electronicsCount >= 3 || clothingCount >= 3) {
            additionalDiscount = 0.20;  // 20% additional discount
        }
        return new double[]{discount, additionalDiscount};
    }


    /**
     * Returns the list of products in cart.
     * @return The list of products in cart.
     */
    public List<Product> getCartProducts() {
        return cartProducts;
    }


    /**
     * @return The current user logged
     * into the system
     */
    public User getCurrentUser() {
        return this.currentUser;
    }


    /**
     * @param currentUser the user to be considered
     *                    as the current user
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}