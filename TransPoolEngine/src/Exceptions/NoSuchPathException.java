package Exceptions;

public class NoSuchPathException extends Throwable {
    private String fromStop;
    private String toStop;

    public NoSuchPathException(String fromStop, String toStop) {
        this.fromStop = fromStop;
        this.toStop = toStop;
    }

    @Override
    public String getMessage() {
        return "There is no direct route from "
                + fromStop
                + " to "
                + toStop
                +".";
    }
}
