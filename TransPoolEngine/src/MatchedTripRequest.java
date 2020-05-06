import Generated.Scheduling;

public class MatchedTripRequest extends TripRequest {
    private String nameOfTripOwner;
    private int tripCost;
    private String assumedArrivalTime;
    private int fuelAverageConsumption;

    public MatchedTripRequest(String nameOfApplicant, String fromStation, String toStation, Scheduling requestedTime, boolean timeDefChoice, boolean isTripSegmented, String nameOfTripOwner, int tripCost, String assumedArrivalTime, int fuelAverageConsumption) {
        super(nameOfApplicant, fromStation, toStation, requestedTime, timeDefChoice, isTripSegmented);
        this.nameOfTripOwner = nameOfTripOwner;
        this.tripCost = tripCost;
        this.assumedArrivalTime = assumedArrivalTime;
        this.fuelAverageConsumption = fuelAverageConsumption;
    }

    MatchedTripRequest(String nameOfApplicant, String fromStation, String toStation, Scheduling requestedTime, boolean timeDefChoice, boolean isTripSegmented) {
        super(nameOfApplicant, fromStation, toStation, requestedTime, timeDefChoice, isTripSegmented);
    }

    public String getNameOfTripOwner() {
        return nameOfTripOwner;
    }

    public void setNameOfTripOwner(String nameOfTripOwner) {
        this.nameOfTripOwner = nameOfTripOwner;
    }

    public int getTripCost() {
        return tripCost;
    }

    public void setTripCost(int tripCost) {
        this.tripCost = tripCost;
    }

    public int getFuelAverageConsumption() {
        return fuelAverageConsumption;
    }

    public String getAssumedArrivalTime() {
        return assumedArrivalTime;
    }

    public void setAssumedArrivalTime(String assumedArrivalTime) {
        this.assumedArrivalTime = assumedArrivalTime;
    }

    public void setFuelAverageConsumption(int fuelAverageConsumption) {
        this.fuelAverageConsumption = fuelAverageConsumption;
    }
}
