import Generated.Scheduling;

public class TripRequest {

    private String nameOfApplicant;
    private String fromStation;
    private String toStation;
    private Scheduling requestedTime;
    private boolean isTripSegmented;
    //private LocalDateTime RequestedTimeOfArrival;
    //private LocalDateTime RequestedTimeOfDeparture;

    TripRequest(String nameOfApplicant, String fromStation, String toStation,Scheduling requestedTimeOfDeparture,boolean isTripSegmented) {

        this.nameOfApplicant = nameOfApplicant;
        this.fromStation = fromStation;
        this.toStation = toStation;
        this.requestedTime = requestedTimeOfDeparture;
        this.isTripSegmented = isTripSegmented;
    }


}
