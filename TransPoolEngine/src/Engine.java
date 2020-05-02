import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.*;

import Exceptions.TimeException;
import Exceptions.NoSuchStopException;
import Generated.Scheduling;
import Generated.Stop;
import Generated.TransPool;
import Generated.TransPoolTrip;

public class Engine {

    private final static String JAXB_XML_GAME_PACKAGE_NAME = "Generated";
    private TransPoolProxy data = new TransPoolProxy();
    private ArrayList<TripRequest> tripRequestsList= new ArrayList<TripRequest>();

    public Engine() {
        loadDataFromXml("Resources/ex1-small");
    }

    public void loadDataFromXml(String xmlPath) {
        InputStream inputStream = Engine.class.getResourceAsStream("Resources/ex1-small.xml");

        try {
            TransPool transPoolData = deserializeFrom(inputStream);
            data.loadData(transPoolData);
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

    /*public List<ProxyPath> getPaths()    {
        return data.getMapDescriptor().getPaths().getPathsList();
    }*/

    public List<TransPoolTrip> getPlannedTrips() {
        return data.GetAllPlannedTrips();
    }

    public int calculateTripCost(TransPoolTrip trip) {
        int tripCost = 0;
        int ppk = trip.getPPK();

        String[] pathsNames = trip.getRoute().getPath().replaceAll(" ","").split(",");

        for(int i = 0 ; i < pathsNames.length - 1 ; i++) {
            tripCost = data.GetPath(pathsNames[i], pathsNames[i+1]).getLength();
        }

        tripCost = tripCost * ppk;
        return tripCost;
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

