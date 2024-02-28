package utilities;

public class NegativeValueException extends Exception {
    /**
     * NegativeValueException constructor with the error message.
     *
     * Utilised whenever a negative value is detected.
     */
    public NegativeValueException() {
        super("\u001B[31mNegative value detected. Please enter a positive value.\u001B[0m");
    }
}