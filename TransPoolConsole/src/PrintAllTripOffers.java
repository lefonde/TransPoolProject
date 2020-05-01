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


        List<ProxyTransPoolTrip> plannedTripsList= engine.getPlannedTrips();
        int countTrips=plannedTripsList.size();
        System.out.println("There are:" + countTrips + "trips");
        for (ProxyTransPoolTrip t: plannedTripsList) {
            System.out.println("Trip owner:"+ t.getOwner());
            System.out.println("Trip route:"+ t.getRoute().getPath().toString());
            int cost=calculetaTripCost(t);
            System.out.println("Trip cost: "+ cost);
            System.out.println("Capacity: "+ t.getCapacity());


            //t.getScheduling();

        }

    }

    private int calculetaTripCost(ProxyTransPoolTrip t) {
        int ppk=t.getPPK();
        List<ProxyPath> paths=engine.getPaths();
        int sum=0;
        for (ProxyPath p: paths) {
            sum+= p.getLength()*ppk;

        }
        return sum;



    }


    public static Executable getInstance() {
        return instance;
    }
}