import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import Exceptions.TimeException;
import Exceptions.NoSuchStopException;
import Generated.Scheduling;
import Generated.Stop;
import Generated.Stops;
import Generated.TransPool;

public class Engine {
    private final static String JAXB_XML_GAME_PACKAGE_NAME = "Generated";
    private TransPool data;
    private ArrayList<TripRequest> tripRequests= new ArrayList<TripRequest>();

    public Engine() {
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

    public void CreateNewTripRequest(String name, String fromStopName, String toStopName, int departureHour, int departureDay) throws NoSuchStopException, TimeException {

        if(findStop(fromStopName) == null) throw new NoSuchStopException(fromStopName);
        if(findStop(toStopName) == null) throw new NoSuchStopException(toStopName);
        Scheduling departureTime = TransPoolDataController.createSchedule(departureHour,departureDay,"");

        tripRequests.add(new TripRequest(name,fromStopName,toStopName,departureTime,false));
    }

    private Stop findStop(String stopName) {
        Stop result = null,
                tempStop;

        Stops stops = data.getMapDescriptor().getStops();
        Iterator<Stop> stopIterator = stops.getStop().iterator();

        do {
            tempStop = (Stop) stopIterator;
            if(tempStop.getName().equals(stopName)) result = tempStop;
        }while (stopIterator.hasNext() || result == null);

        return result;
    }

    private static TransPool deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (TransPool) u.unmarshal(in);
    }

    protected static class TransPoolDataController {

        protected static Scheduling createSchedule(int hour, int day, String recurrences) throws TimeException {
            if(day < 1 )
                throw new TimeException(TimeException.TimeSection.DAY);
            if(hour>23 || hour<0)
                throw new TimeException(TimeException.TimeSection.HOURS);

            Scheduling schedule = new Scheduling();
            schedule.setDayStart(day);
            schedule.setHourStart(hour);

            return schedule;
        }
    }
}
