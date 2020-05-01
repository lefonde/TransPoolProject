import Generated.Scheduling;

public class TripRequest {

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
    }


}
