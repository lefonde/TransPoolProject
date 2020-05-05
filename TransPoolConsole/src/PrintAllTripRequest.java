import java.util.ArrayList;
import java.util.List;

public class PrintAllTripRequest  extends Executable {

    private static final PrintAllTripRequest instance = new PrintAllTripRequest();

    private PrintAllTripRequest() {
    }

    @Override
    public void Execute() {


        ArrayList<TripRequest> tripRequestsList = engine.getTripRequestsList();
        int countTripRequests = tripRequestsList.size();
        System.out.println("There are: " + countTripRequests + " trip request in the system:\n");

        for (TripRequest trip : tripRequestsList) {
            System.out.println(" Trip serial number: " + trip.getSerialNumber() + "\n"
                    + "Name of Applicant: " + trip.getNameOfApplicant() + "\n"
                    + "Departure stop: " + trip.getFromStation() + "\n"
                    + "Arrival stop: " + trip.getToStation() + "\n"
                    + "Departure Time: "+ trip.getRequestedTimeOfDeparture().getHourStart());
        }
    }
}