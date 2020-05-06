import Exceptions.TimeException;
import Generated.Path;
import Generated.PlannedTrips;
import Generated.Stop;
import Generated.Stops;
import Generated.TransPoolTrip;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PrintAllTripOffers extends Executable {
    private static final PrintAllTripOffers instance = new PrintAllTripOffers();

    private PrintAllTripOffers() { }

    @Override
    public void Execute() {


        List<ProxyTransPoolTrip> plannedTripsList = engine.getPlannedTrips();
        int countTrips = plannedTripsList.size();
        System.out.println("There are: " + countTrips + " trips in the system:\n");

        for (ProxyTransPoolTrip trip : plannedTripsList) {
            System.out.println(" Trip serial number: " + trip.getSerialNumber() + "\n"
                    + "Trip owner: " + trip.getOwner() + "\n"
                    + "Trip route: " + trip.getRoute() + "\n"
                    + "Trip cost: " + engine.calculateTripCost(trip) + "\n"
                    + "Capacity: " + trip.getCapacity() + "\n"
                    + "Departure: " + engine.printDepartureTime(trip) + "\n"
                    + "Arrival: " + engine.calculateArrivalTime(trip) + "\n"
                    + "members serial number: ");
            printMembersSerialNumber(trip);
            System.out.println("The trip stops at:");
            printTripPlan(trip);
            System.out.println("Trip fuel average cost: " +engine.averageFuelConsumption(trip) +"\n");
        }

    }

    public void printMembersSerialNumber(ProxyTransPoolTrip trip) {

        trip.getMembers().stream().forEach(y -> System.out.println(y.getSerialNumber() + ","));
    }

    public void printTripPlan(ProxyTransPoolTrip plannedTrip) {
        Map<String, List<String>> gettingOnMap = engine.gettingOnMap(plannedTrip);
        Map<String, List<String>> gettingOffMap = engine.gettingOffMap(plannedTrip);

        for (String key : gettingOnMap.keySet()) {
            System.out.println(" stop number " + key.indexOf(key) + " is: " + key);
            System.out.println("getting on the ride: ");
            gettingOnMap.get(key).stream().forEach(x -> System.out.print(x + ", "));
            System.out.println("is getting off the ride: ");
            gettingOffMap.get(key).stream().forEach(x -> System.out.print(x + ", "));
        }

    }
    public static Executable getInstance() {
        return instance;
    }
}