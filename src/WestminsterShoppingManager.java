import models.Product;
import models.User;
import utilities.QuickSort;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * WestminsterShoppingManager class
 * responsible for managing the shopping system.
 *.
 * ``````````````````````````````````````````````````````````````````````````````````````````````````````````````````
 * References:
 * <a href="https://stackoverflow.com/questions/29878237/java-how-to-clear-a-text-file-without-deleting-it">...</a>
 *.
 * ``````````````````````````````````````````````````````````````````````````````````````````````````````````````````
 * @version 2.0
 */
public class WestminsterShoppingManager implements ShoppingManager, Serializable {
    public static List<Product> productList = new ArrayList<>();   // Store all products (Electronics and Clothing)
    public static List<User> users = new ArrayList<>();  // Store all user information (Usernames and passwords)
    public static final int MAX_NUM_OF_PRODUCTS = 50;  // Maximum number of products the system can hold
    public static int totalNumOfProducts;  // Number of products in the shopping system


    /**
     * Adds products into the list of products
     * @param product product object to be added into product list
     */
    @Override
    public void addProduct(Product product) {
        if (totalNumOfProducts < MAX_NUM_OF_PRODUCTS) {  // Check if list has space
            productList.add(product);  // If Add product to list
            System.out.println("\u001B[34mProduct successfully added!\u001B[0m");
            totalNumOfProducts += product.getNumOfAvailableItems();  // Increment total number of products
            System.out.println("Total available space: " + (MAX_NUM_OF_PRODUCTS - totalNumOfProducts));  // Display space left

        } else {
            System.out.println("\u001B[0;93mNot enough space in the list!\u001B[0m");
        }
    }


    /**
     * Adds items into a product's stock
     * @param productId Get product id from the user
     * @param amount valid input amount
     */
    public void addToStock(String productId, int amount){
        if (totalNumOfProducts < MAX_NUM_OF_PRODUCTS) {  // Check if list has space
            Product product = findById(productId);

            // set the number of available products of the product according to user input amount
            product.setNumOfAvailableItems(product.getNumOfAvailableItems() + amount);

            totalNumOfProducts += amount;  // increment the system's total product count
            System.out.println("\u001B[34mProduct successfully added!\u001B[0m");
            System.out.println("Total available space: "
                    + (MAX_NUM_OF_PRODUCTS - totalNumOfProducts));  // Display space left

        } else {
            System.out.println("\u001B[31mNot enough space available in list!\u001B[0m");
        }
    }


    /**
     * Deletes a product from the list of products
     * @param productID Get product id of the product to be deleted
     */
    @Override
    public void deleteProduct(String productID) {
        if (isIdFound(productID)) {
            Product product = findById(productID);

            int total = 0;

            for (Product item: productList){

                // Calculates the total number of the same type of product left in
                // the system without the product to be deleted
                if (item.getClass().getSimpleName().equals(product.getClass().getSimpleName()) && (item != product) ) {
                    total += item.getNumOfAvailableItems();
                }
            }

            productList.remove(product);  // Remove product from list

            System.out.println("\u001B[34mProduct successfully deleted!\u001B[0m");

            totalNumOfProducts -= product.getNumOfAvailableItems();  // Decrement total number of products

            // Prints the total number of the same type of product left in the system
            System.out.println("Total number of " + product.getClass().getSimpleName() + " left: " + total);
            System.out.println("Total number of all items: " + totalNumOfProducts);

        } else {
            System.out.println("\u001B[31mProduct does not exist!\u001B[0m");
        }
    }


    /**
     * @param productID The product ID of the product to be found.
     * @return true if the product ID is found, false otherwise.
     */
    public boolean isIdFound(String productID) {
        for (Product product : productList) {  // Traverse through the list of products
            if (product.getProductID().equals(productID)) {  // Check if product ID is found
                System.out.println("\n\u001B[4;34mProduct Information\u001B[0m");
                System.out.println(product);
                return true;
            }
        }
        return false;
    }


    /**
     * @param productID to search for product in product list
     * @return product if found in product list
     */
    public static Product findById(String productID){
        for (Product product : productList) {
            if (product.getProductID().equals(productID)) {
                return product;
            }
        }
        return null;
    }


    /**
     * Prints info on all the products
     * currently in the product list
     */
    @Override
    public void listOfProducts() {
        List<Product> sortedArray = new ArrayList<>(productList);

        System.out.println("\n\u001B[4;34mSorted Product List (A-Z)\u001B[0m\n");

        for (Product product: QuickSort.quickSort(sortedArray, 0, sortedArray.size() - 1)){
            System.out.println("ProductType: " + product.getClass().getSimpleName());  // Display the product type
            System.out.println(product);  // Display the product info
        }
    }


    /**
     * Saves the list of products and the total number of products to a file
     * .
     * @param filepath the path for the file to be created
     */
    @Override
    public void saveProductsToFile(String filepath) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filepath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeInt(totalNumOfProducts);
            objectOutputStream.writeObject(productList);
            System.out.println("\u001B[34mProgram data saved successfully!\u001B[0m");

        } catch (FileNotFoundException e){
            System.out.println("File not found");

        } catch(IOException e) {
            System.out.println("error writing to file");
        }
    }


    /**
     * Loads the list of products and the total number of products from a file
     * .
     * @param filepath to locate the file to read data from
     */
    public void loadProductData(String filepath){
        try{
            FileInputStream fileInputStream = new FileInputStream(filepath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            totalNumOfProducts = objectInputStream.readInt();
            //noinspection unchecked
            productList = (List<Product>) objectInputStream.readObject();


        } catch (FileNotFoundException e){
            System.out.println("File cannot be located"+ e);

        } catch (EOFException e){
            System.out.println("File is empty: " + e.getMessage());

        } catch (IOException | ClassNotFoundException e){
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }


    /**
     * Clears all data from a save file
     * @param path locate the file to clear all written data
     */
    public void clearData(String path){
        try {
            PrintWriter writer = new PrintWriter(path);
            writer.print("");
            writer.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearProductList(){
        productList.clear();
        totalNumOfProducts = 0;
    }


    /**
     * Saves all user information to a file
     * .
     * @param filepath to locate the file to save data
     */
    public void saveUsersToFile(String filepath) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filepath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(users);

            System.out.println("\u001B[34mUser data saved successfully!\u001B[0m");

        } catch(IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }


    /**
     * Loads all user information from the file
     * .
     * @param filepath to locate the file to read data from
     */
    public void loadUsers(String filepath) {
        try{
            FileInputStream fileInputStream = new FileInputStream(filepath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            //noinspection unchecked
            users = (List<User>) objectInputStream.readObject();

        } catch (FileNotFoundException e){
            System.out.println("File not found: " + e.getMessage());

        } catch (EOFException e){
            System.out.println("File is empty: " + e.getMessage());

        } catch (IOException | ClassNotFoundException e){
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }


    /**
     * Checks if the user exists in the system
     * .
     * @param username to search for user in user list
     * @return user if found in user list
     */
    public static boolean isUserExist(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Getter
     * @return the list of products.
     */
    public List<Product> getProductList() {
        return productList;
    }


    /**
     * Stock of each product taking into account.
     * .
     * @return Total number of products in the system.
     */
    public int getTotalNumOfProducts() {
        return totalNumOfProducts;
    }
}