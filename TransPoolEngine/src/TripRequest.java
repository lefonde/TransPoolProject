import Generated.Scheduling;

public class TripRequest {

    private static int counter = 0;
    private int serialNumber;
    private String nameOfApplicant;
    private String fromStation;
    private String toStation;
    private Scheduling RequestedTimeOfDeparture;

    private boolean isTripSegmented;
    private boolean timeDefChoice;//departure=0, arrival=1

    private boolean isRequestMatched;
    //private LocalDateTime RequestedTimeOfDeparture;

    TripRequest(String nameOfApplicant, String fromStation, String toStation,Scheduling requestedTime, boolean timeDefChoice, boolean isTripSegmented) {

        this.nameOfApplicant = nameOfApplicant;
        this.fromStation = fromStation;
        this.toStation = toStation;
        this.RequestedTimeOfDeparture = requestedTime;
        this.timeDefChoice=timeDefChoice;
        this.isTripSegmented = isTripSegmented;
        this.serialNumber=++counter;
        isRequestMatched = false;
    }

    public String getNameOfApplicant() {
        return nameOfApplicant;
    }

    public void setRequestAsMatched() { isRequestMatched = true; }

    public void setNameOfApplicant(String nameOfApplicant) {
        this.nameOfApplicant = nameOfApplicant;
    }

    public String getFromStation() {
        return fromStation;
    }

    public void setFromStation(String fromStation) {
        this.fromStation = fromStation;
    }

    public String getToStation() {
        return toStation;
    }

    public void setToStation(String toStation) {
        this.toStation = toStation;
    }

    public Scheduling getRequestedTimeOfDeparture() {
        return RequestedTimeOfDeparture;
    }

    public void setRequestedTimeOfDeparture(Scheduling requestedTimeOfDeparture) {
        RequestedTimeOfDeparture = requestedTimeOfDeparture;
    }

    public boolean isTripSegmented() {
        return isTripSegmented;
    }

    public void setTripSegmented(boolean tripSegmented) {
        isTripSegmented = tripSegmented;
    }

    public boolean isTimeDefChoice() {
        return timeDefChoice;
    }

    public void setTimeDefChoice(boolean timeDefChoice) {
        this.timeDefChoice = timeDefChoice;
    }

    public int getSerialNumber() {
        return serialNumber;
    }
}
