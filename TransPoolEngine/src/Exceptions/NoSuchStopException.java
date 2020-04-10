package Exceptions;

public class NoSuchStopException extends Throwable {
    private String stopName;

    public NoSuchStopException(String stopName) {
        this.stopName = stopName;
    }

    @Override
    public String getMessage() {
        return "The stop " + stopName + "doesn't exist";
    }
}
