import Exceptions.TimeException;
import Exceptions.NoSuchStopException;

import java.util.Scanner;

public class NewTripRequest extends Executable {
    private static final NewTripRequest instance = new NewTripRequest();
    private final String DETAILS_REQUEST_PROMPT = "Please enter new trip request details separated by commas and hit enter to finish:\n" +
            "[applicants name, departure-stop name, destination-stop name, departure hour, departure day]";
    private NewTripRequest() { }

    @Override
    public void Execute() {
        System.out.println(DETAILS_REQUEST_PROMPT);

        String usersInput = (new Scanner(System.in)).nextLine();
        usersInput= usersInput.replaceAll(" ","");
        String[] inputs = usersInput.split(",");

        try {
            engine.CreateNewTripRequest(inputs[0], inputs[1], inputs[2], Integer.parseInt(inputs[3]), Integer.parseInt(inputs[4]));
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

