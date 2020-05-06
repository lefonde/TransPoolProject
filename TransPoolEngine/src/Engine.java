import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import Exceptions.TimeException;
import Exceptions.NoSuchStopException;
import Generated.*;

import static java.lang.Math.round;

public class Engine {

    private final static String JAXB_XML_GAME_PACKAGE_NAME = "Generated";
    private TransPoolProxy data = new TransPoolProxy();
    private ArrayList<TripRequest> tripRequestsList= new ArrayList<TripRequest>();

    public ArrayList<TripRequest> getTripRequestsList() {
        return tripRequestsList;
    }

    public Engine() {
        //loadDataFromXml("Resources/ex1-small.xml");
    }

    public void loadDataFromXml(String xmlPath) throws FileNotFoundException {
        //InputStream inputStream = FileInputStream(xmlPath);
        FileInputStream inputStream = new FileInputStream(xmlPath);
        try {
            TransPool transPoolData = deserializeFrom(inputStream);
            data.loadData(transPoolData);
            data.initPlannedTrips();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void CreateNewTripRequest(String name, String fromStopName, String toStopName,boolean timeChoice, int Hour, int Day) throws NoSuchStopException, TimeException {

        if(data.GetStop(fromStopName) == null) throw new NoSuchStopException(fromStopName);
        if(data.GetStop(toStopName) == null) throw new NoSuchStopException(toStopName);

        //boolean isTripExist= checkTripExist(fromStopName, toStopName);

        Scheduling time= TransPoolDataController.createSchedule(Hour,Day,"");
        tripRequestsList.add(new TripRequest(name,fromStopName,toStopName,time,timeChoice,false));
    }

    /*private ProxyStop findStop(String stopName) {
        ProxyStop result = null,
                tempStop;

        List<ProxyStop> stops = data.getMapDescriptor().getStops().getTheStopList();

        try {
            result = stops.stream().filter(x -> x.getName().equalsIgnoreCase(stopName)).findFirst().get();
        }
        catch (Exception e){

        }
        return result;

    }*/

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
        double totalTripTimeInMinutes=0;
        String[] pathsNames = trip.getRoute().replaceAll(" ","").split(",");
        Path path;
        for(int i = 0 ; i < pathsNames.length - 1 ; i++) {
           path = data.GetPath(pathsNames[i], pathsNames[i+1]);
            totalTripTimeInMinutes+= (((double)path.getLength() /  (double)path.getSpeedLimit())*60);

        }

        return totalTripTimeInMinutes;
    }
    public List<ProxyTransPoolTrip> GetAllTripsInRoute(String fromStation, String toStation, Scheduling requestedTimeOfDeparture) {
        List<ProxyTransPoolTrip> result;

        result = data.GetAllPlannedTrips().stream()
                .filter(trip -> {
                    String [] stops = trip.getRoute().split("[ \\t\\n\\,\\?\\;\\.\\:\\!]");
                    return (stops[0].equalsIgnoreCase(fromStation) && stops[stops.length - 1].equalsIgnoreCase(toStation));
                })
                .collect(Collectors.toList());

        return result;
    }

    public String printDepartureTime(ProxyTransPoolTrip trip ) {
        int startHour = trip.getScheduling().getHourStart();
        if(startHour<10)
            return("0"+startHour+":00");
        else
            return (startHour+":00");
    }

    protected static class TransPoolDataController {

        protected static Scheduling createSchedule(int hour, int day, String recurrences) throws TimeException {
            if(day < 0 )
                throw new TimeException(TimeException.TimeSection.DAY);
            if(hour>23 || hour<0)
                throw new TimeException(TimeException.TimeSection.HOURS);

            Scheduling schedule = new Scheduling();
            schedule.setDayStart(day);
            schedule.setHourStart(hour);

            return schedule;
        }
    }

    public List<Stop> GetStops()    {
      return data.GetAllStops();
   }

    /*public List<ProxyPath> getPaths(){
        return data.getMapDescriptor().getPaths().getPathsList();
    }*/

    public List<ProxyTransPoolTrip> getPlannedTrips() {
        return data.getTransPoolTripList();
    }

    public int calculateTripCost(ProxyTransPoolTrip trip) {
        int tripLength = 0;
        String[] pathsNames = trip.getRoute().replaceAll(" ","").split(",");
        Path path;
        for(int i = 0 ; i < pathsNames.length - 1 ; i++) {
            path = data.GetPath(pathsNames[i], pathsNames[i+1]);
            tripLength += path != null ? path.getLength() : 0;
        }

        return tripLength * trip.getPPK();
    }
//TODO:
    public double averageFuelConsumption(ProxyTransPoolTrip trip) {
        double liters = 0;
        Path path = null;
        String[] pathsNames = trip.getRoute().replaceAll(" ","").split(",");
        for(int i = 0 ; i < pathsNames.length - 1 ; i++) {
            path = data.GetPath(pathsNames[i], pathsNames[i + 1]);
            liters += path != null ?  ((double)path.getLength()/ (double) path.getFuelConsumption()) : 0;

        } return (int) Math.round(liters);
    }
    public Map<String,List<String>> gettingOffMap(ProxyTransPoolTrip trip)
    {
        Map<String, List<String>> gettingOffMap = new HashMap<String, List<String>>();
      trip.getHitchhikers().stream().forEach(x-> {
          if(!gettingOffMap.containsKey(x.getToStation()))
                gettingOffMap.put(x.getToStation(), new ArrayList<String>());
          gettingOffMap.get(x.getToStation()).add(x.getNameOfApplicant());
      });
      return gettingOffMap;
    }
    public Map<String,List<String>> gettingOnMap(ProxyTransPoolTrip trip)
    {
        Map<String, List<String>> gettingOnMap = new HashMap<String, List<String>>();
        trip.getHitchhikers().stream().forEach(x-> {
            if(!gettingOnMap.containsKey(x.getFromStation()))
               gettingOnMap.put(x.getFromStation(), new ArrayList<String>());
            gettingOnMap.get(x.getFromStation()).add(x.getNameOfApplicant());
        });
        return gettingOnMap;
    }
    public boolean DoesPathExists(String fromStopName, String toStopName) {
        return data.GetPath(fromStopName, toStopName) != null;
    }

    public boolean IsRoutePossible(String fromStopName, String toStopName) {
        return false;
    }

    public List<TripRequest> GetAllTripRequests() {
        return tripRequestsList;
    }

    /*private boolean checkTripExist(String fromStopName, String toStopName) {
        boolean from = false;
        boolean to = false;

        List<ProxyTransPoolTrip> transPoolTrip = data.getPlannedTrips().getTransPoolTrip();
        for (ProxyTransPoolTrip t:transPoolTrip) {

            String[] stops = t.getRoute().getPath().split(",");
            from = false;
            to = false;

            for (String s:stops) {
                if (s.equalsIgnoreCase(fromStopName))
                {
                    from=true;
                }
                if (s.equalsIgnoreCase(toStopName))
                {
                    if(from)
                        to=true;
                }
            }
        }

        return (from && to);
    }*/
}

