import javax.management.openmbean.ArrayType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import Exceptions.TimeException;
import Exceptions.NoSuchStopException;
import Generated.Path;
import Generated.Scheduling;
import Generated.Stop;
import Generated.Stops;
import Generated.TransPool;
import Generated.TransPoolTrip;

public class Engine {
    private final static String JAXB_XML_GAME_PACKAGE_NAME = "Generated";
    private TransPool data;
    private ArrayList<TripRequest> tripRequests= new ArrayList<TripRequest>();
    private ArrayList<List<Stop>> pathsNetwork;

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
        tripRequests.add(new TripRequest(name,fromStopName,toStopName,time,timeChoice,false));
    }

    private Stop findStop(String stopName) {
        Stop result = null,
                tempStop;

        List<Stop> stops = data.getMapDescriptor().getStops().getStop();

        try {
            result = stops.stream().filter(x -> x.getName().equalsIgnoreCase(stopName)).findFirst().get();
        }
        catch (Exception e){

        }
        return result;

    }

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

    public List<Stop> getStops()    {
       return data.getMapDescriptor().getStops().getStop();
    }

    public List<Path> getPaths()    {
        return data.getMapDescriptor().getPaths().getPath();
    }

    public List<TransPoolTrip> getPlannedTrips()   {
        return data.getPlannedTrips().getTransPoolTrip();
    }

    private boolean checkTripExist(String fromStopName, String toStopName)
    {
        boolean from=false,to=false;
        List<TransPoolTrip> transPoolTrip = data.getPlannedTrips().getTransPoolTrip();
        for (TransPoolTrip t:transPoolTrip) {
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

