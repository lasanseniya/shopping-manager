package utilities;

public class EmptyInputException extends Exception {

    /**
     * EmptyInputException with
     * the specified error message.
     *
     * Utilised whenever an empty user input is given.
     */
    public EmptyInputException() {
        super("\u001B[31mInput is empty!\u001B[0m");
    }
}