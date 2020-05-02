import Exceptions.NoSuchStopException;
import Generated.Path;
import Generated.Stop;
import Generated.TransPool;
import Generated.TransPoolTrip;

import java.util.List;

public class TransPoolProxy {

    TransPool data;

    public void loadData(TransPool data) {
        this.data = data;
        for(Path path : data.getMapDescriptor().getPaths().getPath()) {
            if(!isPathValid(path.getFrom(), path.getTo())) System.out.println("path error");
        }
    }

    public int GetMapLengthBoundary() {
        return data.getMapDescriptor().getMapBoundries().getLength();
    }

    public int GetMapWidthBoundary() {
        return data.getMapDescriptor().getMapBoundries().getWidth();
    }

    public List<Path> GetAllPaths() {
        return data.getMapDescriptor().getPaths().getPath();
    }

    public Path GetPath(String fromStopName, String toStopName) {
        Path result = null;
        List<Path> paths = GetAllPaths();

        try {
            result = paths.stream()
                    .filter(x -> x.getFrom().equalsIgnoreCase(fromStopName))
                    .filter(x -> x.getTo().equalsIgnoreCase(toStopName))
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

    /*public void GetAllRoutes() {
        return data.getMapDescriptor().g
    }*/

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

    private boolean isPathValid(String fromStopName, String toStopName) {
        return GetStop(fromStopName) != null && GetStop(toStopName) != null;
    }
}
