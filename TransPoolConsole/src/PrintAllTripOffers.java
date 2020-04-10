import Exceptions.TimeException;

public class PrintAllTripOffers extends Executable {
    private static final PrintAllTripOffers instance = new PrintAllTripOffers();

    private PrintAllTripOffers() { }

    @Override
    public void Execute() {
        System.out.println("Britney bitch!");
        engine.loadDataFromXml("Resources/ex1-small");
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