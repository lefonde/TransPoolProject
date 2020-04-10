import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

import Exceptions.TimeException;
import Generated.Scheduling;
import Generated.TransPool;

public class Engine {
    private final static String JAXB_XML_GAME_PACKAGE_NAME = "Generated";
    private TransPool data;

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

    private static TransPool deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (TransPool) u.unmarshal(in);
    }

    private static class TransPoolDataController {

        private Scheduling createSchedule(int hour, int day, String recurrences) throws TimeException {
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
