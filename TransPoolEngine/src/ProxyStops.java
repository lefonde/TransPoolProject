import Generated.Stop;
import Generated.Stops;

import java.util.ArrayList;
import java.util.List;

public class ProxyStops {

    protected List<ProxyStop> theStopList = null;

    public ProxyStops(Stops stops)
    {
        List<Stop> stopsList= stops.getStop();

        for (Stop s:stopsList) {
            ProxyStop newStop = new ProxyStop(s);
            theStopList.add(newStop);
        }
    }
    public List<ProxyStop> getTheStopList() {
        if (theStopList == null) theStopList = new ArrayList<ProxyStop>();

        return this.theStopList;
    }


}
