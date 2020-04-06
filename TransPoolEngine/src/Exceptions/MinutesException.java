package Exceptions;

public class MinutesException extends Exception {
    private final String EXCEPTION_MESSAGE = "The number you entered must present the minutes, therefor it must be between 00-59";
    public MinutesException() {
    }

    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }
}
