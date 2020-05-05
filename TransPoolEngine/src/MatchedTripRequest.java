import Generated.Scheduling;

public class MatchedTripRequest extends TripRequest {
    MatchedTripRequest(String nameOfApplicant, String fromStation, String toStation, Scheduling requestedTime, boolean timeDefChoice, boolean isTripSegmented) {
        super(nameOfApplicant, fromStation, toStation, requestedTime, timeDefChoice, isTripSegmented);
    }
    private String nameOfTripOwner;
    private int tripCost;
    private int assumedArrivalTime;
    private int fuelAverageConsumption;

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

    public int getAssumedArrivalTime() {
        return assumedArrivalTime;
    }

    public void setAssumedArrivalTime(int assumedArrivalTime) {
        this.assumedArrivalTime = assumedArrivalTime;
    }

    public int getFuelAverageConsumption() {
        return fuelAverageConsumption;
    }

    public void setFuelAverageConsumption(int fuelAverageConsumption) {
        this.fuelAverageConsumption = fuelAverageConsumption;
    }
}
