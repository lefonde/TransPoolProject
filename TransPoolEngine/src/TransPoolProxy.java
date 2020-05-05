import Exceptions.NoSuchStopException;
import Generated.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class TransPoolProxy {

    protected TransPool data;
    protected ArrayList<ProxyTransPoolTrip> transPoolTrip = new ArrayList<ProxyTransPoolTrip>();



    public void loadData(TransPool data) {
        this.data = data;
        try {
            if (!validateDuplicateStops(data)) {
                System.out.println("error in XML, There are duplicate stops");
                Thread.sleep(5000);
                System.exit(0);
            }
            if (!validateStopsInMap(data)) {
                System.out.println("error in XML, The stops are outside the map");
                Thread.sleep(5000);
                System.exit(0);
            }
            if (!validateUniqueStopsInMap(data)){
                System.out.println("error in XML, There are two stops in the same coordinates");
                Thread.sleep(5000);
                System.exit(0);
            }
            if (!validateExistingPaths(data)){
                System.out.println("error in XML, The is a path in the trans pool trips that are not exist");
                Thread.sleep(5000);
                System.exit(0);
            }
        }
        catch (InterruptedException e) {}

        // TODO: validate no two stops on the same coord
        // TODO: validate stops in paths exist
        // TODO: validate each path contains all relevant information
        // TODO: validate routes in TransPoolTrips go through existing sequential paths
        // TODO: validate each TransPoolTrip contain all relevant information
    }

    private boolean validateExistingPaths(TransPool data) {

    }

   /* private boolean validateUniqueStopsInMap(TransPool data) {
        List<Stop> stopList=data.getMapDescriptor().getStops().getStop();
       int currX,currY;
        for (Stop s:stopList) {
           currX = s.getX();
           currY = s.getY();
           for

        }
    }*/

    private boolean validateStopsInMap(TransPool data) {
        List<Stop> stopList=data.getMapDescriptor().getStops().getStop();
        int width= data.getMapDescriptor().getMapBoundries().getWidth();
        int length= data.getMapDescriptor().getMapBoundries().getLength();
        AtomicReference<Boolean> valid= new AtomicReference<>(true);
        stopList.stream().forEach(x-> {
            if( 0>x.getX() || x.getX()>width || 0>x.getY() || x.getY()>length) {
            valid.set(false);
        }});
        return valid.get();
    }

    private boolean validateDuplicateStops(TransPool data) {
        List<Stop> stopList=data.getMapDescriptor().getStops().getStop();
        return(areAllUnique(stopList));
    }

    public static <T> boolean areAllUnique(List<T> list){
        Set<T> set = new HashSet<>();

        for (T t: list){
            if (!set.add(t))
                return false;
        }

        return true;
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
