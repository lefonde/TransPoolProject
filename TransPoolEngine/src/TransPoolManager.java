import java.util.LinkedList;
import java.util.List;

public class TransPoolManager {

    List<TransPoolTrip> trips = new LinkedList<>();

    public List<TransPoolTrip> GetAllTrips() {
        return trips;
    }

    public void UIShowAllTrips() {
        for (TransPoolTrip trip : trips) {
            System.out.println("Serial number: " + trip.getTripNumber());
        }

    }

}
