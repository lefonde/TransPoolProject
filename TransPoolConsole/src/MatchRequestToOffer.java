import java.util.List;

public class MatchRequestToOffer extends Executable {

    private static final MatchRequestToOffer instance = new MatchRequestToOffer();

    private MatchRequestToOffer() { }

    @Override
    public void Execute() {
        // -prompt user with a list of available trip requests (name, from, to, time of departure)
        PromptTripRequests();
        // prompt user on selection
        // receive and process the selection
        // prompt user for how many options he'd like to see if there are more than one
        // prompt the user with all available optional

    }

    private void PromptTripRequests() {
        int numberOfRequests = engine.GetAllTripRequests().size();
        printTripRequestsTable();
        int userSelection = UserInputUtils.PromptUserInputFromRange(1, numberOfRequests);
        TripRequest selectedTripRequest = engine.GetAllTripRequests().get(userSelection - 1);
        List<ProxyTransPoolTrip> tripsList = engine.GetAllTripsInRoute(selectedTripRequest.getFromStation()
                , selectedTripRequest.getToStation()
                , selectedTripRequest.getRequestedTimeOfDeparture());

        if(tripsList.size() > 1) {
            System.out.println(String.format("There are %d relevant trip offers found. How many would you like to display?", tripsList.size()));
            userSelection = UserInputUtils.PromptUserInputFromRange(1, tripsList.size());
            tripsList = tripsList.subList(0, userSelection);
        }

        printTripOffersTable(tripsList);
        userSelection = UserInputUtils.PromptUserInputFromRange(1, tripsList.size());
        ProxyTransPoolTrip selectedTripOffer = tripsList.get(userSelection - 1);

        MatchedTripRequest match = new MatchedTripRequest(selectedTripRequest.getNameOfApplicant()
                , selectedTripRequest.getFromStation()
                , selectedTripRequest.getToStation()
                , selectedTripRequest.getRequestedTimeOfDeparture()
                ,false
                ,false,selectedTripOffer.getOwner()
                ,engine.calculateTripCost(selectedTripOffer)
                ,engine.calculateArrivalTime(selectedTripOffer)
                ,engine.averageFuelCost(selectedTripOffer));
    }

    private void printTripRequestsTable() {
        StringBuilder tableSB = new StringBuilder();

        tableSB.append("No.    Name                    From Stop               To Stop                 Time to Departure     \n");
        tableSB.append("-----  ----------------------  ----------------------  ----------------------  ----------------------\n");

        int requestNumber = 1;
        for (TripRequest tripRequest : engine.GetAllTripRequests()) {
            tableSB.append(String.format("%-6d %-23s %-23s %-23s Hour: %2d Day: %2d"
                    , requestNumber++
                    , tripRequest.getNameOfApplicant()
                    , tripRequest.getFromStation()
                    , tripRequest.getToStation()
                    ,tripRequest.getRequestedTimeOfDeparture().getHourStart()
                    ,tripRequest.getRequestedTimeOfDeparture().getDayStart()));
        }

        System.out.println(tableSB);
    }

    private void printTripOffersTable(List<ProxyTransPoolTrip> tripsList) {
        StringBuilder tableSB = new StringBuilder();

        tableSB.append("No.    Name                    Cost            ETA                 Avg. Fuel Consumption    \n");
        tableSB.append("-----  ----------------------  --------------  ------------------  -------------------------\n");

        int offerNumber = 1;
        for (ProxyTransPoolTrip tripOffer : tripsList) {
            tableSB.append(String.format("%-6d %-23s %-15d %-19s %d"
                    , offerNumber++
                    , tripOffer.getOwner()
                    , engine.calculateTripCost(tripOffer)
                    , engine.calculateArrivalTime(tripOffer)
                    , engine.averageFuelCost(tripOffer)));
        }

        System.out.println(tableSB);
    }
    public static Executable getInstance() {
        return instance;
    }
}
