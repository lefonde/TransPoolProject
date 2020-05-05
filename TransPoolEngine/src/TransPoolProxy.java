import Exceptions.NoSuchStopException;
import Generated.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class TransPoolProxy {

    protected TransPool data;
    protected ArrayList<ProxyTransPoolTrip> transPoolTrips = new ArrayList<>();

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
            transPoolTrips.add(newTrip);
        }
    }
    public int GetMapWidthBoundary() {
        return data.getMapDescriptor().getMapBoundries().getWidth();
    }

    public List<ProxyTransPoolTrip> getTransPoolTripList()  {
        if (transPoolTrips == null) transPoolTrips = new ArrayList<ProxyTransPoolTrip>();
        return this.transPoolTrips;
    }

    public List<Path> GetAllPaths() {
        return data.getMapDescriptor().getPaths().getPath();
    }

    public Path GetPath(String fromStopName, String toStopName) {
        Path result = null;
        List<Path> paths = GetAllPaths();

        try {
            List<Path> fiteredList= paths.stream()
                    .filter(x -> x.getFrom().equalsIgnoreCase(fromStopName)
                            && x.getTo().equalsIgnoreCase(toStopName))
                    .collect(Collectors.toList());
            result = fiteredList.isEmpty() ? null : fiteredList.get(0);
        }
        catch (Exception e){
            return result;
        }

        return result;
    }

    public List<ProxyTransPoolTrip> GetAllPlannedTrips() {
        return transPoolTrips;
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
