import Generated.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TransPoolProxy {

    protected TransPool data;
    protected ArrayList<ProxyTransPoolTrip> transPoolTrips = new ArrayList<ProxyTransPoolTrip>();

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

            if (!validateUniqueStopsInMap(data)) {
                System.out.println("error in XML, There are two stops in the same coordinates");
                Thread.sleep(5000);
                System.exit(0);
            }

            if (!validateExistingPaths(data)) {
                System.out.println("error in XML, There is a path in the trans pool trips that are not exist");
                Thread.sleep(5000);
                System.exit(0);
            }
            if (!(validMapBoundaries(data))) {
                System.out.println("error in XML, There is a path in the trans pool trips that are not exist");
                Thread.sleep(5000);
                System.exit(0);
            }
            if (!(validStopsInAllPaths(data))) {
                System.out.println("error in XML, There are paths that include stops that are not exist");
                Thread.sleep(5000);
                System.exit(0);
            }
        } catch (InterruptedException e) {
        }
    }

    private boolean validateUniqueStopsInMap (TransPool data){
        ArrayList<Stop> stopList = (ArrayList<Stop>) data.getMapDescriptor().getStops().getStop();
        int currX, currY, size = stopList.size();

        for (int i = 0; i < size - 1; i++) {
            currX = stopList.get(i).getX();
            currY = stopList.get(i).getY();

            for (int j = i + 1; j < size - 1; j++) {
                if (currX == stopList.get(j).getX())
                    if (currY == stopList.get(j).getY())
                        return false;
            }
        }

        return true;
     }

    private boolean validStopsInAllPaths(TransPool data) {
        List<TransPoolTrip> transPoolTripList = data.getPlannedTrips().getTransPoolTrip();
        List<String> allStopNames = data.getMapDescriptor().getStops().getStop().stream().map((stop) -> stop.getName()).collect(Collectors.toList());

        String[] stopsNames;
        for (TransPoolTrip trip : transPoolTripList) {
            stopsNames = trip.getRoute().getPath().replaceAll(" ", "").split(",");
            for (String s:stopsNames)
                if (!allStopNames.contains(s))
                    return false;
        }

        return true;
    }

    private boolean validMapBoundaries(TransPool data) {
        int width = data.getMapDescriptor().getMapBoundries().getWidth();
        int lenght = data.getMapDescriptor().getMapBoundries().getLength();

        return (width>=6 && width<=100 && lenght>=6 && lenght<=100);
    }

    private boolean validateExistingPaths(TransPool data) {
       List<TransPoolTrip> transPoolTripList= data.getPlannedTrips().getTransPoolTrip();
        String[] stopsNames;
        for (TransPoolTrip trip: transPoolTripList) {
            {
                stopsNames = trip.getRoute().getPath().replaceAll(" ", "").split(",");
                for (int i = 0; i < stopsNames.length - 1; i++) {
                    if( GetPath(stopsNames[i], stopsNames[i + 1])==null)
                        return false;
                }
            }
       }
        return true;
    }



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
