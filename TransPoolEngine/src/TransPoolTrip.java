import java.time.*;

public class TransPoolTrip {

    private static int tripsCounter = 0;
    private int tripNumber;
    private String DriversName;
    private int SeatsAvailable; // positive integer
    private String [] StopsNames;
    private double PricePerKM; // in shmekels, positive double only
    private DayOfWeek DayOfTrip;
    private LocalDateTime TripStartTime;
    private boolean isRepeatingTrip;
    private Duration TripDuration;
    private double TripCost; // in shmekels, positive double only
    private double AvgFuelConsumption; // in fuels per trip

    public static int getTripsCounter() {
        return tripsCounter;
    }

    public int getTripNumber() {
        return tripNumber;
    }

    public String getDriversName() {
        return DriversName;
    }

    public int getSeatsAvailable() {
        return SeatsAvailable;
    }

    public String[] getStopsNames() {
        return StopsNames;
    }

    public double getPricePerKM() {
        return PricePerKM;
    }

    public DayOfWeek getDayOfTrip() {
        return DayOfTrip;
    }

    public LocalDateTime getTripStartTime() {
        return TripStartTime;
    }

    public boolean isRepeatingTrip() {
        return isRepeatingTrip;
    }

    public Duration getTripDuration() {
        return TripDuration;
    }

    public double getTripCost() {
        return TripCost;
    }

    public double getAvgFuelConsumption() {
        return AvgFuelConsumption;
    }


}

