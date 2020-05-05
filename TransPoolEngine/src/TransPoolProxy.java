import Exceptions.NoSuchStopException;
import Generated.*;

import java.util.ArrayList;
import java.util.List;

public class TransPoolProxy {

    protected TransPool data;
    protected List<ProxyTransPoolTrip> transPoolTrip;

    public void loadData(TransPool data) {
        this.data = data;
        // TODO: validate no duplicate stops
        // TODO: validate stops are inside the map
        // TODO: validate no two stops on the same coord
        // TODO: validate stops in paths exist
        // TODO: validate each path contains all relevant information
        // TODO: validate routes in TransPoolTrips go through existing sequential paths
        // TODO: validate each TransPoolTrip contain all relevant information
    }

    public int GetMapLengthBoundary() {
        return data.getMapDescriptor().getMapBoundries().getLength();
    }
    public void initPlannedTrips()
    {
        List<TransPoolTrip> transPoolTripList=data.getPlannedTrips().getTransPoolTrip();
        for (TransPoolTrip t: transPoolTripList)
        {
            ProxyTransPoolTrip newTrip=new ProxyTransPoolTrip(t);
            transPoolTrip.add(newTrip);
        }
    }
    public int GetMapWidthBoundary() {
        return data.getMapDescriptor().getMapBoundries().getWidth();
    }

    public List<ProxyTransPoolTrip> getTransPoolTripList()  {
        if (transPoolTrip == null) transPoolTrip = new ArrayList<ProxyTransPoolTrip>();
        return this.transPoolTrip;
    }

    public List<Path> GetAllPaths() {
        return data.getMapDescriptor().getPaths().getPath();
    }

    public Path GetPath(String fromStopName, String toStopName) {
        Path result = null;
        List<Path> paths = GetAllPaths();

        try {
            result = paths.stream()
                    .filter(x -> x.getFrom().equalsIgnoreCase(fromStopName)
                            && x.getTo().equalsIgnoreCase(toStopName))
                    .findFirst()
                    .get();
        }
        catch (Exception e){

        }

        return result;
    }

    public List<TransPoolTrip> GetAllPlannedTrips() {
        return data.getPlannedTrips().getTransPoolTrip();
    }

    /*public void GetPlannedTrip() {}*/

    /*public void GetRoute() {}*/

    public List<Stop> GetAllStops() {
        return data.getMapDescriptor().getStops().getStop();
    }

    public Stop GetStop(String stopName) {
        Stop result = null;

        List<Stop> stops = GetAllStops();

        try {
            result = stops.stream()
                    .filter(x -> x.getName().equalsIgnoreCase(stopName))
                    .findFirst()
                    .get();
        }
        catch (Exception e){

        }

        return result;
    }

}
