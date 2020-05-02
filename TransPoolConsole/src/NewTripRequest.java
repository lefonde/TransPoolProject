import Exceptions.TimeException;
import Exceptions.NoSuchStopException;

import java.util.Scanner;

public class NewTripRequest extends Executable {
    private static final NewTripRequest instance = new NewTripRequest();
    private final String DETAILS_REQUEST_PROMPT = "Please enter new trip request details separated by commas and hit enter to finish:\n" +
            "[applicants name, departure-stop name, destination-stop name]\n" +
            "the optional stops are:";
    private final String TIME_REQUEST_PROMPT = "Do you want to set:\n" +
            "departure time (type 0)\n" +
            "arrival time (type 1) \n"+
            "Please enter request details separated by commas and hit enter to finish:\n" +
            "[your choice time set (0 or 1), departure/arrival hour, day]";

    private NewTripRequest() { }

    @Override
    public void Execute() {
        System.out.println(DETAILS_REQUEST_PROMPT);
        engine.GetStops().stream().forEach(stop -> System.out.print(stop.getName() + ", "));
        System.out.println();
        String usersInput = (new Scanner(System.in)).nextLine();
        String[] inputs = usersInput.replaceAll(" ","").split(",");

        System.out.println(TIME_REQUEST_PROMPT);
        String usersTimeInput = (new Scanner(System.in)).nextLine();
        String[] timeInputs = usersTimeInput.replaceAll(" ","").split(",");

        try {
            engine.CreateNewTripRequest(inputs[0], inputs[1], inputs[2],Boolean.parseBoolean(timeInputs[0]),Integer.parseInt(timeInputs[1]), Integer.parseInt(timeInputs[2]));
        } catch (TimeException e) {
            System.out.println(e.getMessage());
        } catch (NoSuchStopException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Executable getInstance() {
        return instance;
    }
}

