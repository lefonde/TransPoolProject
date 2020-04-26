import Exceptions.TimeException;
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


        List<TransPoolTrip> plannedTripsList= engine.getPlannedTrips();
        int length=plannedTripsList.size();
        System.out.println("trip number:");
        for (TransPoolTrip t: plannedTripsList) {
            System.out.println("Trip owner:"+ t.getOwner());
            System.out.println("Trip route:"+ t.getRoute().getPath().toString());
            int cost=calculetaTripCost(t);

            t.getPPK();

            t.getCapacity();
            t.getScheduling();

        }

    }

    private int calculetaTripCost(TransPoolTrip t) {
        int ppk=t.getPPK();
        String[] stops=t.getRoute().getPath().split(",");



        return 0;
    }


    public static Executable getInstance() {
        return instance;
    }
}