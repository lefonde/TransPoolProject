package Exceptions;

public class TimeException extends Exception {
    private TimeSection section ;
    private String EXCEPTION_MESSAGE;

    public TimeException(TimeSection section) {
        this.section = section;
        this.EXCEPTION_MESSAGE = section.message;
    }

    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }

    public enum TimeSection {
        DAY("The number you entered must represent the day therefor it must be positive"),
        MINUTES("The number you entered must represent the minutes therefor it must be between 00 - 59" ),
        HOURS("The number you entered must represent the hours therefor it must be between 00 - 23");

        private String message;

        private TimeSection(String message) {
            this.message = message;
        }
    }
}
