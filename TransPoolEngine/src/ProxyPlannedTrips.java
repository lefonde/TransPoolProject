import Generated.TransPoolTrip;

import java.util.ArrayList;
import java.util.List;

public class ProxyPlannedTrips {

    protected List<TransPoolTrip> transPoolTrip;

    public List<TransPoolTrip> getTransPoolTrip() {
        if (transPoolTrip == null) {
            transPoolTrip = new ArrayList<TransPoolTrip>();
        }

        return this.transPoolTrip;
    }
}
