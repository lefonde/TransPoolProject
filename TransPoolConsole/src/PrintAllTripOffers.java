import Exceptions.TimeException;
import Generated.Stop;
import Generated.Stops;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PrintAllTripOffers extends Executable {
    private static final PrintAllTripOffers instance = new PrintAllTripOffers();

    private PrintAllTripOffers() { }

    @Override
    public void Execute() {
        System.out.println("Britney bitch!");
        engine.loadDataFromXml("Resources/ex1-small");


        List<Stop> stopList=engine.getStops();

        for (Stop stop:stopList) {
            System.out.println(stop.getName());
        }


        try {
            Time time = new Time(0, 0, 19);
        } catch (TimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Executable getInstance() {
        return instance;
    }
}