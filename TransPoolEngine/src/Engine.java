import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.*;

import Exceptions.TimeException;
import Exceptions.NoSuchStopException;
import Generated.Scheduling;

public class Engine {

    private final static String JAXB_XML_GAME_PACKAGE_NAME = "Generated";
    private ProxyTransPool data;
    private ArrayList<TripRequest> tripRequestsList= new ArrayList<TripRequest>();


    public Engine() {
        loadDataFromXml("Resources/ex1-small");
    }

    public void loadDataFromXml(String xmlPath) {
        InputStream inputStream = Engine.class.getResourceAsStream("Resources/ex1-small.xml");

        try {
            data = deserializeFrom(inputStream);
            System.out.println("name of first country is: " + data.getPlannedTrips().getTransPoolTrip().get(0).getOwner());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void CreateNewTripRequest(String name, String fromStopName, String toStopName,boolean timeChoice, int Hour, int Day) throws NoSuchStopException, TimeException {

        if(findStop(fromStopName) == null) throw new NoSuchStopException(fromStopName);
        if(findStop(toStopName) == null) throw new NoSuchStopException(toStopName);

        boolean isTripExist= checkTripExist(fromStopName, toStopName);

        Scheduling time= TransPoolDataController.createSchedule(Hour,Day,"");
        tripRequestsList.add(new TripRequest(name,fromStopName,toStopName,time,timeChoice,false));
    }

    private ProxyStop findStop(String stopName) {
        ProxyStop result = null,
                tempStop;

        List<ProxyStop> stops = data.getMapDescriptor().getStops().getTheStopList();

        try {
            result = stops.stream().filter(x -> x.getName().equalsIgnoreCase(stopName)).findFirst().get();
        }
        catch (Exception e){

        }
        return result;

    }

    private static ProxyTransPool deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (ProxyTransPool) u.unmarshal(in);
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

   public List<ProxyStop> getStops()    {
      return data.getMapDescriptor().getStops().getTheStopList();
   }

   public List<ProxyPath> getPaths()    {
        return data.getMapDescriptor().getPaths().getPathsList();
    }

    public List<ProxyTransPoolTrip> getPlannedTrips()   {
        return data.getPlannedTrips().getTransPoolTrip();
    }

    private boolean checkTripExist(String fromStopName, String toStopName)
    {
        boolean from=false,to=false;
        List<ProxyTransPoolTrip> transPoolTrip = data.getPlannedTrips().getTransPoolTrip();
        for (ProxyTransPoolTrip t:transPoolTrip) {
            String[] stops = t.getRoute().getPath().split(",");
            from=false; to=false;
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
    }



}

