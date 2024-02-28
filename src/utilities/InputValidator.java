package utilities;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * Input validator class handles all validations
 * and guarantees valid inputs are returned.
 * Static polymorphism is used via
 * multiple "isValid" methods which invoke according to
 * their the method signatures.
 *
 */
public class InputValidator {
    private static final Scanner sc = new Scanner(System.in);

    /**
     * @param prompt message to prompt for input
     * @param choice array of valid String inputs
     * @return valid String input
     */
    public static String isValid(String prompt, String[] choice){
        while(true) {
            try {
                System.out.print(prompt);

                String userInput = sc.nextLine().toUpperCase().trim(); // .trim() -> remove trailing and following whitespaces

                if (userInput.isEmpty()){
                    throw new EmptyInputException();
                }

                if (Arrays.asList(choice).contains(userInput)) {
                    return userInput;

                } else {
                    System.out.println("\u001B[31mPlease enter a valid input!\u001B[0m");
                }

            } catch (EmptyInputException e){
                System.out.println("\u001B[31mEmpty input!\u001B[0m");
            }
        }
    }


    /**
     * @param prompt message to prompt for input
     * @param validNums array of valid integer inputs
     * @return valid integer
     */
    public static int isValid(String prompt, int[] validNums){
        while(true) {
            try {
                System.out.print(prompt);
                String strInput = sc.nextLine().trim();

                // Check if the input is empty
                if (strInput.isEmpty()) {
                    System.out.println("\u001B[31mInput cannot be empty!\u001B[0m");
                    continue;
                }

                int userInput = Integer.parseInt(strInput);  // Parse string to int and assign to variable

                // Check for user input in array validNums
                for (int number: validNums){
                    if (userInput == number){
                        return userInput;  // If found return
                    }
                }

                System.out.println("\u001B[31mPlease select a valid option!\u001B[0m");

            } catch (NumberFormatException e) {
                System.out.println("\u001B[31mPlease enter a numeric value!\u001B[0m");
            }
        }
    }


    /**
     * @param prompt message to prompt for input
     * @param regex regular expression to validate user input
     * @return valid string according to regex
     */
    public static String isValid(String prompt, String regex){
        while(true) {
            System.out.print(prompt);
            try {
                String userInput = sc.nextLine().toUpperCase().trim();

                if (userInput.isEmpty()){
                    throw new EmptyInputException();
                }

                if (userInput.matches(regex)) {  // Check against regex and if valid returns input
                    return userInput;

                } else {
                    System.out.println("\u001B[31mInvalid format! \n" +
                            "\u001B[41m\u001B[1;30m[letter][number][number][number]\u001B[0m (eg: A001, z999)\u001B[0m");
                }

            } catch (EmptyInputException e){
                System.out.println("\u001B[31mInput cannot be empty!\u001B[0m");
            }
        }
    }


    /**
     * @param prompt message to prompt for input
     * @return valid integer
     */
    public static int isValid(String prompt){
        while(true){
            System.out.print(prompt);
            try{
                String strInput = sc.nextLine().trim();

                if (strInput.isEmpty()){
                    throw new EmptyInputException();
                }

                int userInput = Integer.parseInt(strInput);

                if (userInput > 0){
                    return userInput;

                } else {
                    throw new NegativeValueException();
                }

            } catch(InputMismatchException e) {
                System.out.println("\u001B[31mContains invalid characters!\u001B[0m");

            } catch (EmptyInputException e){
                System.out.println("\u001B[31mEmpty input!\u001B[0m");

            } catch (NegativeValueException e){
                System.out.println("\u001B[31mNegative inputs are invalid!\u001B[0m");

            } catch (NumberFormatException e){
                System.out.println("\u001B[31mPlease enter a numeric value!\u001B[0m");
            }
        }
    }


    /**
     * @param prompt message to prompt for product amount
     * @param MAX_LIMIT of products the shopping system can hold
     * @param totalNumOfProducts the shopping system contains
     * @return valid amount in accordance with available space
     */
    public static int isValidAmount(String prompt, int MAX_LIMIT, int totalNumOfProducts) {
        while(true){
            System.out.print(prompt);
            try {
                String strInput = sc.nextLine().trim();

                if (strInput.isEmpty()){  // Check whether user input is empty
                    throw new EmptyInputException();
                }

                int userInput = Integer.parseInt(strInput);  // Parse string to int and assign to variable

                if (userInput < 0){
                    throw new NegativeValueException();
                }

                if (userInput == 0){
                    System.out.println("\u001B[31mAmount cannot be 0!\u001B[0m");
                    continue;
                }

                if (userInput <= MAX_LIMIT - totalNumOfProducts) {  // Check whether there is enough space for the user entered amount
                    return userInput;

                } else {
                    System.out.println("\u001B[31mNot enough space available in list!\u001B[0m");
                    System.out.println("Total available space: \u001B[34m" + (MAX_LIMIT - totalNumOfProducts) + "\u001B[0m/50");
                }

            } catch (NumberFormatException e) {
                System.out.println("\u001B[31mPlease enter a numeric value!\u001B[0m");

            } catch (EmptyInputException e){
                System.out.println("\u001B[31mInput cannot be empty!\u001B[0m");

            } catch (NegativeValueException e){
                System.out.println("\u001B[31mAmount cannot be negative!\u001B[0m");
            }
        }
    }


    /**
     * @param prompt message to prompt for product price
     * @return valid price input
     */
    public static double isValidPrice(String prompt){
        while(true){
            System.out.print(prompt);
            try{
                String strInput = sc.nextLine().trim();

                if (strInput.isEmpty()){
                    throw new EmptyInputException();
                }

                float userInput = Float.parseFloat(strInput);  // Parse string to float and assign to variable

                if (userInput > 0){
                    return Math.round(userInput * 100.0) / 100.0;

                } else {
                    System.out.println("\u001B[31mPrice must be greater than 0!\u001B[0m");
                }

            } catch(NumberFormatException e) {
                System.out.println("\u001B[31mPlease enter numerical values only!\u001B[0m");

            } catch(EmptyInputException e){
                System.out.println("\u001B[31mInput cannot be empty!\u001B[0m");
            }
        }
    }


    /**
     * @param prompt message to prompt for string input
     * @return valid string input
     */
    public static String isValidString(String prompt){
        while(true){
            System.out.print(prompt);
            try {
                String userInput = sc.nextLine().trim();

                if (userInput.matches("\\d+")) {
                    System.out.println("\u001B[31mInput contains numerics only!\u001B[0m");
                    continue;
                }

                if (userInput.isEmpty()){
                    throw new EmptyInputException();
                }
                return userInput;

            } catch (EmptyInputException e){
                System.out.println("\u001B[31mInput cannot be empty!\u001B[0m");
            }
        }
    }

}