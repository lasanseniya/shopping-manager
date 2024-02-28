import models.Clothing;
import models.Electronics;
import models.Product;
import utilities.EmptyInputException;
import utilities.InputValidator;
import java.util.Scanner;

/**
 * Entry point of the Shopping application.
 * `````````````````````````````````````````````````````````````````````````````````````````````````````````````````````
 * References:
 * <a href="https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println">...</a>
 * <a href="https://www.geeksforgeeks.org/jvm-shutdown-hook-java/">...</a>
 *.
 * `````````````````````````````````````````````````````````````````````````````````````````````````````````````````````
 *.
 * Author:  Lasan Seniya Ranatunge
 * Date  :  01/20/2024
 *`
 * @version 2.0
 */



public class Main extends InputValidator {
    public static Scanner scanner = new Scanner(System.in);  // Initializing the scanner object
    public static WestminsterShoppingManager sys = new WestminsterShoppingManager();  // Initializing the shopping manager object
    public static boolean isUserDataCleared;  // To check if user data is cleared
    public static boolean isProductDataCleared;  // To check if product data is cleared
    public static boolean isAutoSaveEnabled;  // To check if auto save is enabled


    /**
     * Entry point of the application
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        // Loading product and user data into the program at start
        sys.loadProductData("./src/datastore/ProgramData.txt");
        sys.loadUsers("./src/datastore/UserData.txt");

        addShutdownHook();

        new InitialPage();  // Display initial page before displaying CLI menu
    }


    /**
     * Adding a shutdown hook for saving data
     * to files upon program termination
     */
    private static void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (!isUserDataCleared  && isAutoSaveEnabled) {
                sys.saveUsersToFile("./src/datastore/UserData.txt");
            }

            if (!isProductDataCleared  && isAutoSaveEnabled) {
                sys.saveProductsToFile("./src/datastore/ProgramData.txt");
            }
        }));
    }


    /**
     * @param start Boolean value to check if CLI should be activated
     */
    public static void isActivate(boolean start){
        if (start) {
            runCLI();
        }
    }


    /**
     * Displays and handles the admin command line interface
     */
    public static void runCLI() {
        String choice;  // Initialize choice variable
        while (true) {

            // Calculate available space and store to be used in menu display
            int spaceLeft = WestminsterShoppingManager.MAX_NUM_OF_PRODUCTS -
                    WestminsterShoppingManager.totalNumOfProducts;

            String autosave = isAutoSaveEnabled ? "\u001B[34mON\u001B[0m " : "\u001B[31mOFF\u001B[0m";

            System.out.println("\n");
            System.out.println("╭─────────────────────────────────────────╮");
            System.out.println("│\u001B[30m\u001B[44m             Shopping Manager            \u001B[0m│");
            System.out.println("│             Admin interface             │");
            System.out.println("╰─────────────────────────────────────────╯");
            System.out.println("╭─────────────────────────────────────────╮");
            System.out.println("│                                         │");
            System.out.println("│     [1] - Add product                   │");
            System.out.println("│     [2] - Delete product                │");
            System.out.println("│     [3] - Display list of products      │");
            System.out.println("│     [4] - Store/clear program data      │");
            System.out.println("│     [5] - Exit                          │");
            System.out.println("│                                         │");

            // Display the available space in the menu dynamically.
            String spaceLeftFixed = String.format("\u001B[34m%-" + 2 + "s\u001B[0m", spaceLeft);  // This formats the string to be displayed without any extra spaces
            System.out.println("│                    Available space: \u001B[34m" + spaceLeftFixed + "\u001B[0m  │");
            String autoSaveFixed = String.format("%-" + 3 + "s", autosave);  // This formats the string to be displayed without any extra spaces
            System.out.println("│                    AutoSave state: " + autoSaveFixed + "  │");
            System.out.println("╰─────────────────────────────────────────╯");

            System.out.print("\nEnter your choice: ");  // Prompt for menu choice
            try {
                choice = scanner.nextLine().trim();

                if (choice.isEmpty()) {  // If user enters empty string
                    throw new EmptyInputException();
                }

                // Enhanced switch statement
                switch (choice) {
                    case "1" -> addProduct();
                    case "2" -> deleteProduct();
                    case "3" -> listOfProducts();
                    case "4" -> fileHandling();
                    case "5" -> System.exit(0);

                    default -> System.out.println("\u001B[31mPlease enter a valid option from the menu!\u001B[0m");
                }

            } catch (EmptyInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    /**
     * Displays and handles the product adding functionality
     * .
     * User can add either an electronics or clothing product or
     * go back to the main menu
     */
    private static void addProduct() {
        isProductDataCleared = false; // this ensures the auto save functionality is working properly
                                            // after clearing data and adding again in the same session
        while (true) {
            // Check for available space before adding product
            if (sys.getTotalNumOfProducts() == WestminsterShoppingManager.MAX_NUM_OF_PRODUCTS) {
                System.out.println("\u001B[31mNo space available! Remove some items first\u001B[0m");
                break;
            }

            // Printing the menu
            System.out.println("\n╭───────────────────────────────╮");
            System.out.println("│      Choose product type      │");
            System.out.println("│\u001B[1;34m°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°\u001B[0m│");
            System.out.println("│     [1]  -  Electronics       │");
            System.out.println("│     [2]  -  Clothing          │");
            System.out.println("│     [3]  -  Main menu         │");
            System.out.println("╰───────────────────────────────╯");
            System.out.println();

            int choice = isValid("Enter your choice: ", new int[]{1, 2, 3});  // Invoke isValid method to get validated user input

            if (choice == 3) {  // If user chooses to go back to main menu
                System.out.println();  // print one line space before menu display
                break;
            }

            String productID = isValid("Enter productID: ", "[A-Z]\\d{3}");  // Get validated user input (A001, B999, etc)

            // Check if product ID already exists
            if (sys.isIdFound(productID)) {
                // If so, Ask user if they want to add to stock instead
                System.out.println("\u001B[31mProduct with same ID already exists!\u001B[0m");
                System.out.print("Do you want to add to stock instead?\n");
                System.out.println("╭───────────────────────────────╮");
                System.out.println("│    \u001B[1;34m[Y]\u001B[0m - Yes      \u001B[1;34m[N]\u001B[0m - No    │   ");
                System.out.println("╰───────────────────────────────╯");

                String ans = isValid("Enter your choice: ", new String[]{"Y", "N"});  // Get validated user input for (Y/N)

                // If yes, ask user for amount to add to stock
                if (ans.equals("Y")) {
                    System.out.print("Enter amount to add to stock: ");
                    int amount = scanner.nextInt();
                    sys.addToStock(productID, amount);
                }
                // If no, continue on adding product menu
                continue;
            }

            // If no, continue with adding product
            // catch the new line character only if there is a next line

            String productName = isValidString("Enter product name: ");  // Get validated user input for product name

            int numOfAvailableItems = isValidAmount("Enter number of available items: ",
                    WestminsterShoppingManager.MAX_NUM_OF_PRODUCTS, WestminsterShoppingManager.totalNumOfProducts);  // Get validated user input for available items

            double productPrice = isValidPrice("Enter product price: ");  // Get validated user input for product price

            // If user chose [1] - Electronics
            // Prompt for brand and warranty period
            if (choice == 1) {
                String brand = isValidString("Enter product brand: ");

                int Warranty = isValid("Enter warranty period: ");

                // Create new electronics object and add to product list
                Product product = new Electronics(productID, productName, numOfAvailableItems, productPrice, brand, Warranty);
                sys.addProduct(product);
                continue;
            }

            // If user chose [2] - Clothing
            // Prompt for color and size
            if (choice == 2) {
                System.out.print("Enter product color: ");
                String color = scanner.nextLine();

                // Menu for choosing clothing item size
                System.out.println("╭───────────────────────────────╮");
                System.out.println("│     \u001B[1;34m[S]\u001B[0m   -  Small            │");
                System.out.println("│     \u001B[1;34m[M]\u001B[0m   -  Medium           │");
                System.out.println("│     \u001B[1;34m[L]\u001B[0m   -  Large            │");
                System.out.println("│     \u001B[1;34m[XL]\u001B[0m  -  Extra Large      │");
                System.out.println("╰───────────────────────────────╯");

                String size = isValid("Enter product size: ", new String[]{"S", "M", "L", "XL"});

                // Create new clothing object and add to product list
                Product product = new Clothing(productID, productName, numOfAvailableItems, productPrice, color, size);
                sys.addProduct(product);
            }
        }
    }


    /**
     * Handles the deletion and removal of products by their ID
     * .
     * Deletion will delete the product and its stock from the system.
     *
     */
    private static void deleteProduct() {
        while (true) {
            if (sys.getProductList().isEmpty()) {  // Checking if there are products to delete
                System.out.println("\u001B[93mNo products to delete!\u001B[0m");
                break;
            }

            String regex = "[A-Z]\\d{3}";  // Regex for product ID - [letter][number][number][number]
            String productID = isValid("Enter product ID: ", regex);  // Get validated input from InputValidator
            sys.deleteProduct(productID);  // Delete product from system

            System.out.println("\nDo you want to delete another product?");  // Prompt user if they want to delete another product
            System.out.println("╭───────────────────────────────╮");
            System.out.println("│    \u001B[1;34m[Y]\u001B[0m - Yes      \u001B[1;34m[N]\u001B[0m - No    │   ");
            System.out.println("╰───────────────────────────────╯");

            String choice = isValid("Enter your choice: ", new String[]{"Y", "N"});  // Get validated user input from InputValidator

            // If user chooses no, then go back to main menu
            if (choice.equals("N")) {
                break;
            }
        }
    }


    /**
     * Gets the list of products from the system to display.
     */
    private static void listOfProducts() {
        if (sys.getProductList().isEmpty()) {
            System.out.println("\u001B[93mNo products to display!\u001B[0m");
            return;

        }
        sys.listOfProducts();
    }


    /**
     * Clear all saved data from the
     * Does not delete the save files, only clears the data
     * written into the files.
     */
    private static void fileHandling() {
        while (true) {
            System.out.println("╭──────────────────────────────────╮");
            System.out.println("│    \u001B[1;34m[1]\u001B[0m   -  Save data to file    │");
            System.out.println("│    \u001B[1;34m[2]\u001B[0m   -  AutoSave             │");
            System.out.println("│    \u001B[1;34m[3]\u001B[0m   -  Clear product data   │");
            System.out.println("│    \u001B[1;34m[4]\u001B[0m   -  Clear user data      │");
            System.out.println("│    \u001B[1;34m[5]\u001B[0m   -  Main Menu            │");
            System.out.println("╰──────────────────────────────────╯");

            int choice = isValid("Enter your choice: ", new int[]{1, 2, 3, 4, 5});  // Get validated user input

            // If user choose [3] go to main menu
            if (choice == 5) {
                System.out.println("Returning to main menu...");
                break;


            } if (choice == 1){
                sys.saveProductsToFile("./src/datastore/ProgramData.txt");  // Save product data to file34mProgram data saved successfully!\u001B[0m\n\n");
                break;

            } if (choice == 2){
                System.out.println("Turning on auto save will ensure all data is saved whenever the program is terminated.");
                System.out.println("``````````````````````````````````````````````````````````````````````````````````````");
                System.out.println("╭───────────────────────────────╮");
                System.out.println("│    \u001B[1;34m[1]\u001B[0m - Turn on auto save    │   ");
                System.out.println("│    \u001B[1;34m[2]\u001B[0m - Turn off auto save   │   ");
                System.out.println("╰───────────────────────────────╯");

                String confirmation = isValid("Enter your choice: ", new String[]{"1", "2"});  // Get validated user input


                if (confirmation.equals("1")) {
                    isAutoSaveEnabled = true;  // Update state of auto save
                    System.out.println("\u001B[34mAutoSave is turned on!\u001B[0m!\n\n");
                    break;

                } else {
                    isAutoSaveEnabled = false;  // Update state of auto save
                    System.out.println("\u001B[34mAutoSave is turned off!\u001B[0m!\n\n");
                    break;
                }


            } else if (choice == 3) {

                // Ask for confirmation before clearing data
                System.out.println("\u001B[93mAre you sure you want to clear product data?\u001B[0m");
                System.out.println("╭───────────────────────────────╮");
                System.out.println("│    \u001B[1;34m[Y]\u001B[0m - Yes      \u001B[1;34m[N]\u001B[0m - No    │   ");
                System.out.println("╰───────────────────────────────╯");

                String confirmation = isValid("Enter your choice: ", new String[]{"Y", "N"});  // Get validated user input

                if (confirmation.equals("Y")) {
                    sys.clearData("./src/datastore/ProgramData.txt");  // Clear all data in the save file
                    sys.clearProductList();
                    isProductDataCleared = true;  // Update state of program data clearance
                    System.out.println("\u001B[34mAll product data cleared\u001B[0m!\n\n");
                    break;
                }


            } else {
                // Ask for confirmation before clearing data
                System.out.println("\u001B[93mAre you sure you want to clear user data?\u001B[0m");
                System.out.println("╭───────────────────────────────╮");
                System.out.println("│    \u001B[1;34m[Y]\u001B[0m - Yes      \u001B[1;34m[N]\u001B[0m - No    │   ");
                System.out.println("╰───────────────────────────────╯");

                String confirmation = isValid("Enter your choice: ", new String[]{"Y", "N"});  // Get validated user input

                if (confirmation.equals("Y")) {
                    sys.clearData("./src/datastore/UserData.txt");  // Clear all data in the save file
                    isUserDataCleared = true;  // Update state of user data clearance
                    System.out.println("\u001B[34mAll user data cleared\u001B[0m!\n\n");
                    break;

                }
            }
        }
    }
}