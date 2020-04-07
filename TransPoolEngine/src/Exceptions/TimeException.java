package Exceptions;

public class TimeException extends Exception {
    private TimeSection section ;

    private String EXCEPTION_MESSAGE = "The number you entered must present the "
            + section.getSection()
            + " therefor it must be between"
            + section.getFrom() + "-"
            + section.getTo() ;

    public TimeException(TimeSection section) {
        this.section = section;
    }

    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }

    public enum TimeSection {
        MINUTES("Minutes", "00","59" ),
        HOURS("Hours", "00", "23");

        private String section, from, to;

        public String getFrom() {
            return from;
        }

        public String getTo() {
            return to;
        }

        public String getSection() {
            return section;
        }

        private TimeSection(String section, String from, String to) {

        }
    }
}
