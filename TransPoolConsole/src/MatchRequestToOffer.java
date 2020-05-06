import java.util.List;

public class MatchRequestToOffer extends Executable {

    private static final MatchRequestToOffer instance = new MatchRequestToOffer();

    private MatchRequestToOffer() { }

    @Override
    public void Execute() {
        List<TripRequest> tripRequests = engine.GetOpenTripRequests();
        int numberOfRequests = tripRequests.size();

        printTripRequestsTable(tripRequests);

        int userSelection = UserInputUtils.PromptUserInputFromRange(1, numberOfRequests);
        if(userSelection == -1) return;
        TripRequest selectedTripRequest = tripRequests.get(userSelection - 1);

        List<ProxyTransPoolTrip> tripsList = engine.GetAllTripsInRoute(selectedTripRequest.getFromStation()
                , selectedTripRequest.getToStation()
                , selectedTripRequest.getRequestedTimeOfDeparture());

        if(tripsList.size() > 1) {
            System.out.println(String.format("There are %d relevant trip offers found. How many would you like to display?", tripsList.size()));
            userSelection = UserInputUtils.PromptUserInputFromRange(1, tripsList.size());
            if(userSelection == -1) return;
            tripsList = tripsList.subList(0, userSelection);
        }

        printTripOffersTable(tripsList);
        userSelection = UserInputUtils.PromptUserInputFromRange(1, tripsList.size());
        if(userSelection == -1) return;
        ProxyTransPoolTrip selectedTripOffer = tripsList.get(userSelection - 1);

        selectedTripOffer.addHitchhiker(selectedTripRequest);
        System.out.println("Match successful!");
    }

    private void printTripRequestsTable(List<TripRequest> tripRequests) {
        StringBuilder tableSB = new StringBuilder();

        tableSB.append("No.    Name                    From Stop               To Stop                 Time to Departure     \n");
        tableSB.append("-----  ----------------------  ----------------------  ----------------------  ----------------------\n");

        int requestNumber = 1;
        for (TripRequest tripRequest : tripRequests) {
            tableSB.append(String.format("%-6d %-23s %-23s %-23s Hour: %2d Day: %2d\n"
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
            tableSB.append(String.format("%-6d %-23s %-15d %-19s %f\n"
                    , offerNumber++
                    , tripOffer.getOwner()
                    , engine.calculateTripCost(tripOffer)
                    , engine.calculateArrivalTime(tripOffer)
                    , engine.averageFuelConsumption(tripOffer)));
        }

        System.out.println(tableSB);
    }

    public static Executable getInstance() {
        return instance;
    }
}
