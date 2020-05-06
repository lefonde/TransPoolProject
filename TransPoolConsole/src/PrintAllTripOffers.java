import java.util.List;
import java.util.Map;

public class PrintAllTripOffers extends Executable {
    private static final PrintAllTripOffers instance = new PrintAllTripOffers();

    private PrintAllTripOffers() { }

    @Override
    public void Execute() {


        List<ProxyTransPoolTrip> plannedTripsList = engine.getPlannedTrips();
        int countTrips = plannedTripsList.size();
        System.out.println("There are: " + countTrips + " trips in the system:\n");
        printTripOffersTable(plannedTripsList);

    }

    private void printTripOffersTable(List<ProxyTransPoolTrip> tripsList) {
        StringBuilder tableSB = new StringBuilder();

        tableSB.append("No.    Serial No.   Name             Route                 Cost       ETA        Avg. Fuel   Seats Avail   Hitchhikers No.   Hitchhikers Name   From                   To\n");
        tableSB.append("-----  -----------  ---------------  --------------------  ---------  ---------  ----------  ------------  ----------------  -----------------  ---------------------  ---------------------\n");

        int offerNumber = 1;
        for (ProxyTransPoolTrip tripOffer : tripsList) {
            String [] stopsNames = tripOffer.getRoute().replaceAll(" ", "").split(",");
            List<TripRequest> hitchhikers = tripOffer.getHitchhikers();

            tableSB.append(String.format("%-6d %-12d %-16s %-21s %-10d %-10s %-11f %-14d"
                    , offerNumber++
                    , tripOffer.getSerialNumber()
                    , tripOffer.getOwner()
                    , stopsNames[0]
                    , engine.calculateTripCost(tripOffer)
                    , engine.calculateArrivalTime(tripOffer)
                    , engine.averageFuelConsumption(tripOffer)
                    , tripOffer.getCapacity() - hitchhikers.size()));

            if(hitchhikers.size() != 0)
                tableSB.append(String.format("%-17d %-18s %-22s %-22s\n"
                        , hitchhikers.get(0).getSerialNumber()
                        , hitchhikers.get(0).getNameOfApplicant()
                        , hitchhikers.get(0).getFromStation()
                        , hitchhikers.get(0).getToStation()
                        ));

            AppendLeftOvers(tableSB, stopsNames, hitchhikers);
            tableSB.append("\n");
        }

        System.out.println(tableSB);
    }

    private StringBuilder AppendLeftOvers(StringBuilder tableSB, String[] stopsNames, List<TripRequest> hitchhikers) {
        int i;

        for(i = 1 ; i < Math.min(stopsNames.length, hitchhikers.size()) ; i++) {
            tableSB.append(String.format("                                     %-69s %-17d %-18s %-22s %-22s\n"
                    , stopsNames[i]
                    , hitchhikers.get(i).getSerialNumber()
                    , hitchhikers.get(i).getNameOfApplicant()
                    , hitchhikers.get(i).getFromStation()
                    , hitchhikers.get(i).getToStation()));
        }

        for(i = i ; i < stopsNames.length ; i++) {
            tableSB.append(String.format("                                     %-69s\n", stopsNames[i]));
        }

        for(i = i ; i < hitchhikers.size() ; i++) {
            tableSB.append(String.format("%104s %-17d %-18s %-21s %-22s\n"
                    , ""
                    , hitchhikers.get(i).getSerialNumber()
                    , hitchhikers.get(i).getNameOfApplicant()
                    , hitchhikers.get(i).getFromStation()
                    , hitchhikers.get(i).getToStation()));
        }

        return tableSB;
    }

    public static Executable getInstance() {
        return instance;
    }
}