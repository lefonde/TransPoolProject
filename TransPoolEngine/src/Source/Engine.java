package Source;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import Exceptions.NoSuchPathException;
import Exceptions.TimeException;
import Exceptions.NoSuchStopException;
import Generated.*;

import static java.lang.Math.round;

public class Engine {

    private final static String JAXB_XML_GAME_PACKAGE_NAME = "Generated";
    private TransPoolProxy data = new TransPoolProxy();
    private ArrayList<TripRequest> tripRequestsList= new ArrayList<TripRequest>();

    public Engine() {
    }

    public void loadDataFromXml(String xmlPath) throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream(xmlPath);

        try {
            TransPool transPoolData = deserializeFrom(inputStream);
            data.loadData(transPoolData);
            data.initPlannedTrips();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public TripRequest CreateNewTripRequest(String name, String fromStopName, String toStopName,boolean timeChoice, int hour, int minute, int day) throws NoSuchStopException, TimeException {

        if(data.getStop(fromStopName) == null) throw new NoSuchStopException(fromStopName);
        if(data.getStop(toStopName) == null) throw new NoSuchStopException(toStopName);

        ProxyScheduling time= TransPoolDataController.createSchedule(hour, minute, day, "");
        TripRequest tripRequest = new TripRequest(name, fromStopName, toStopName, time, timeChoice, false);
        tripRequestsList.add(tripRequest);

        return tripRequest;
    }

    public ProxyTransPoolTrip CreateNewTripOffer(String name, int hour, int minute
            , int day, int ppk, String recurrence, int seatsAvailable, String route) throws NoSuchStopException, NoSuchPathException, TimeException {

        ProxyTransPoolTrip newTransPoolTrip = null;
        validateRoute(route);
        ProxyScheduling time = TransPoolDataController.createSchedule(hour, minute, day, recurrence);
        newTransPoolTrip = new ProxyTransPoolTrip(name, time, ppk, seatsAvailable, route);
        data.getTransPoolTripList().add(newTransPoolTrip);

        return newTransPoolTrip;
    }

    public TransPoolProxy getData() { return data;  }

    private static TransPool deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (TransPool) u.unmarshal(in);
    }

    public String calculateArrivalTime(ProxyTransPoolTrip trip) {
        double totalTripTime=calculateTripLength(trip);
        int departure= trip.getScheduling().getHourStart();
        int minutes = (int) (totalTripTime %60);
        int hour= (int) (totalTripTime/60 +departure);
        if (minutes<10)
            return (hour +":0"+minutes);
        else
            return (hour +":"+minutes);
    }

    public double calculateTripLength (ProxyTransPoolTrip trip) {

        double totalTripTimeInMinutes = 0;
        String[] pathsNames = trip.getRouteAsString().replaceAll(" ","").split(",");
        Path path;

        for(int i = 0 ; i < pathsNames.length - 1 ; i++) {
           path = data.getPath(pathsNames[i], pathsNames[i+1]);
            totalTripTimeInMinutes+= (((double)path.getLength() /  (double)path.getSpeedLimit())*60);
        }

        return totalTripTimeInMinutes;
    }

    public ArrayList<Stop> getStopsListFromRoute(String route) throws NoSuchStopException {
        ArrayList<Stop> result = new ArrayList<>();

        String [] stops = route.split("[ \\t\\n\\,\\?\\;\\.\\:\\!]");
        for(String stopName : stops) {
            result.add(getStop(stopName));
        }

        return result;
    }

    public List<ProxyTransPoolTrip> GetAllTripsInRoute(String fromStation, String toStation, ProxyScheduling requestedTimeOfDeparture) {
        List<ProxyTransPoolTrip> result;

        result = data.getAllPlannedTrips().stream()
                .filter(trip -> {
                    if(trip.isCarFull()) return false;

                    boolean isFromInRoute = false;
                    boolean isToInRoute = false;
                    boolean isOnTime = false;
                    String route = trip.getRouteAsString();
                    String [] stops = trip.getRouteAsString().split("[ \\t\\n\\,\\?\\;\\.\\:\\!]");

                    for(int i = 0 ; i < stops.length ; i++) {
                        if(stops[i].equalsIgnoreCase(fromStation)) {
                            isFromInRoute = true;
                            ProxyScheduling estimatedTripArrivingTime = trip.getScheduling().addMinutes(calculateTravelTimeToStop(route, stops[i]));
                            if(estimatedTripArrivingTime.equals(requestedTimeOfDeparture))
                                isOnTime = true;
                        }
                        if(isOnTime && stops[i].equalsIgnoreCase(toStation)) return true;
                    }

                    return isFromInRoute && isToInRoute && isOnTime;
                })
                .collect(Collectors.toList());

        return result;
    }

    protected static class TransPoolDataController {

        protected static ProxyScheduling createSchedule(int hour, int minute, int day, String recurrence) throws TimeException {
            if(day < 0 )
                throw new TimeException(TimeException.TimeSection.DAY);
            if(hour>23 || hour<0)
                throw new TimeException(TimeException.TimeSection.HOURS);
            if(minute > 59 || minute < 0)
                throw new TimeException(TimeException.TimeSection.MINUTES);

            ProxyScheduling schedule = new ProxyScheduling(day, hour, minute, recurrence.isEmpty() ? Recurrence.ONETIME : Recurrence.valueOf(recurrence.toUpperCase()));

            return schedule;
        }
    }

    private void validateRoute(String route) throws NoSuchStopException, NoSuchPathException {

        String [] stops = route.split("[ \\t\\n\\,\\?\\;\\.\\:\\!]");

        for(String stop : stops)
            if(data.getStop(stop) == null) throw new NoSuchStopException(stop);

        for(int i = 1 ; i < stops.length ; i++)
            if(!DoesPathExists(stops[i - 1], stops[i])) throw new NoSuchPathException(stops[i - 1], stops[i]);
    }

    public List<Stop> getStops()    {
      return data.getAllStops();
   }

   public Stop getStop(String stopName) throws NoSuchStopException {
        Stop result = data.getStop(stopName);

       if(result == null) throw new NoSuchStopException(stopName);

       return result;
   }

   public List<Path> getPaths() {
        return data.getAllPaths();
   }

    public List<ProxyTransPoolTrip> getPlannedTrips() {
        return data.getTransPoolTripList();
    }

    public int calculateTripCost(ProxyTransPoolTrip trip) {
        int tripLength = 0;
        String[] pathsNames = trip.getRouteAsString().replaceAll(" ","").split(",");
        Path path;

        for(int i = 0 ; i < pathsNames.length - 1 ; i++) {
            path = data.getPath(pathsNames[i], pathsNames[i+1]);
            tripLength += path != null ? path.getLength() : 0;
        }

        return tripLength * trip.getPPK();
    }

    public double averageFuelConsumption(ProxyTransPoolTrip trip) {
        double liters = 0;
        Path path = null;
        String[] pathsNames = trip.getRouteAsString().replaceAll(" ","").split(",");

        for(int i = 0 ; i < pathsNames.length - 1 ; i++) {
            path = data.getPath(pathsNames[i], pathsNames[i + 1]);
            liters += path != null ?  ((double)path.getLength()/ (double) path.getFuelConsumption()) : 0;

        } return (int) Math.round(liters);

    }

    public Map<String,List<String>> gettingOffMap(ProxyTransPoolTrip trip) {
        Map<String, List<String>> gettingOffMap = new HashMap<String, List<String>>();

      trip.getHitchhikers().stream().forEach(x-> {
          if(!gettingOffMap.containsKey(x.getToStation()))
                gettingOffMap.put(x.getToStation(), new ArrayList<String>());
          gettingOffMap.get(x.getToStation()).add(x.getNameOfApplicant());
      });

      return gettingOffMap;
    }

    public Map<String,List<String>> gettingOnMap(ProxyTransPoolTrip trip) {
        Map<String, List<String>> gettingOnMap = new HashMap<String, List<String>>();

        trip.getHitchhikers().stream().forEach(x-> {
            if(!gettingOnMap.containsKey(x.getFromStation()))
               gettingOnMap.put(x.getFromStation(), new ArrayList<String>());
            gettingOnMap.get(x.getFromStation()).add(x.getNameOfApplicant());
        });

        return gettingOnMap;
    }

    public boolean DoesPathExists(String fromStopName, String toStopName) {
        return data.getPath(fromStopName, toStopName) != null;
    }

    public boolean IsRoutePossible(String fromStopName, String toStopName) {
        return false;
    }

    public List<TripRequest> GetAllTripRequests() {
        return tripRequestsList;
    }

    public List<TripRequest> GetOpenTripRequests() {
        return tripRequestsList.stream().filter(trip -> trip.IsOpenRequest()).collect(Collectors.toList());
    }

    // returns travel time from start of route to stop in minutes
    private int calculateTravelTimeToStop(String route, String stop) {
        int commutativeTime = 0;

        String[] stops = route.split("[ \\t\\n\\,\\?\\;\\.\\:\\!]");

        for (int i = 1 ; i < stops.length ; i++) {
            if(stops[i - 1].equals(stop)) break;

            Path path = data.getPath(stops[i - 1], stops[i]);
            commutativeTime += path != null ? (path.getLength() * 60) / path.getSpeedLimit() : 0; // time in minutes
        }

        return commutativeTime;
    }

    public int getMapWidth() {
        return data.getMapWidth();
    }

    public int getMapLength() {
        return data.getMapLength();
    }

}

