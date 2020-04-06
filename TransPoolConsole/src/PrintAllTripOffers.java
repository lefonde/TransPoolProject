import java.util.List;

public class PrintAllTripOffers extends Executable {
    private static final PrintAllTripOffers instance = new PrintAllTripOffers();

    private PrintAllTripOffers() { }

    @Override
    public void Execute() {
        System.out.println("Britney bitch!");
    }

    public static Executable getInstance() {
        return instance;
    }
}