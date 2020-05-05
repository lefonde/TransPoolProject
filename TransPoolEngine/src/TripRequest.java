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
    //private LocalDateTime RequestedTimeOfDeparture;

    TripRequest(String nameOfApplicant, String fromStation, String toStation,Scheduling requestedTime, boolean timeDefChoice, boolean isTripSegmented) {

        this.nameOfApplicant = nameOfApplicant;
        this.fromStation = fromStation;
        this.toStation = toStation;
        this.RequestedTimeOfDeparture = requestedTime;
        this.timeDefChoice=timeDefChoice;
        this.isTripSegmented = isTripSegmented;
        this.serialNumber=++counter;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public String getNameOfApplicant() {
        return nameOfApplicant;
    }

    public String getFromStation() {
        return fromStation;
    }

    public String getToStation() {
        return toStation;
    }

    public Scheduling getRequestedTimeOfDeparture() {
        return RequestedTimeOfDeparture;
    }

    public boolean isTripSegmented() {
        return isTripSegmented;
    }
}
