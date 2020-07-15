import Source.TripRequest;

import java.util.List;

public class PrintAllTripRequest  extends Executable {

    private static final PrintAllTripRequest instance = new PrintAllTripRequest();

    private PrintAllTripRequest() {
    }

    @Override
    public void Execute() {
        List<TripRequest> tripRequestsList = engine.GetOpenTripRequests();
        int countTripRequests = tripRequestsList.size();
        
        System.out.println("There are: " + countTripRequests + " trip request in the system:\n");
        printTripRequestsTable(tripRequestsList);
    }

    public static Executable getInstance() {
        return instance;
    }

    private void printTripRequestsTable(List<TripRequest> tripRequests) {
        StringBuilder tableSB = new StringBuilder();

        tableSB.append("No.    Serial No.   Name                    From Stop               To Stop                 Time to Departure     \n");
        tableSB.append("-----  -----------  ----------------------  ----------------------  ----------------------  ----------------------\n");

        int requestNumber = 1;
        for (TripRequest tripRequest : tripRequests) {
            tableSB.append(String.format("%-6d %-12d %-23s %-23s %-23s Hour: %2d Minute: %2d Day: %2d\n"
                    , requestNumber++
                    , tripRequest.getSerialNumber()
                    , tripRequest.getNameOfApplicant()
                    , tripRequest.getFromStation()
                    , tripRequest.getToStation()
                    ,tripRequest.getRequestedTimeOfDeparture().getHourStart()
                    ,tripRequest.getRequestedTimeOfDeparture().getMinuteStart()
                    ,tripRequest.getRequestedTimeOfDeparture().getDayStart()));
        }

        System.out.println(tableSB);
    }
}