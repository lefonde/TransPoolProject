import Generated.PlannedTrips;
import Generated.TransPoolTrip;

import java.util.ArrayList;
import java.util.List;

public class ProxyPlannedTrips {

    protected List<ProxyTransPoolTrip> transPoolTrip;

     public ProxyPlannedTrips(PlannedTrips plannedTrips)
     {
         List<TransPoolTrip> transPoolTripList=plannedTrips.getTransPoolTrip();
         for (TransPoolTrip t: transPoolTripList)
               {
                   ProxyTransPoolTrip newTrip=new ProxyTransPoolTrip(t);
                   transPoolTrip.add(newTrip);
         }
     }
    public List<ProxyTransPoolTrip> getTransPoolTripList()  {
        if (transPoolTrip == null) transPoolTrip = new ArrayList<ProxyTransPoolTrip>();

        return this.transPoolTrip;
    }
}
