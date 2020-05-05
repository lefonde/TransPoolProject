import Exceptions.TimeException;
import Generated.Path;
import Generated.PlannedTrips;
import Generated.Stop;
import Generated.Stops;
import Generated.TransPoolTrip;

import java.util.List;
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

        plannedTripsList.stream().
                forEach(x -> System.out.println(" Trip serial number: "+ x.getSerialNumber()+
                        " Trip owner: " + x.getOwner()
                        + " Trip route: "
                        + x.getRoute().getPath().toString()
                        + " Trip cost: " + engine.calculateTripCost(x)
                        + " Capacity: " + x.getCapacity()
                        + "Departure: " + x.getScheduling().getHourStart()));
            //t.getScheduling();
    }

    public static Executable getInstance() {
        return instance;
    }
}