import Exceptions.TimeException;
import Generated.Scheduling;


public class ProxyScheduling {
    private Scheduling scheduling=null;
    protected String recurrences;
    protected int hourStart;
    protected Integer dayStart;


    public ProxyScheduling(Scheduling scheduling)
    {
        this.recurrences=scheduling.getRecurrences();
        this.dayStart=scheduling.getDayStart();
        this.hourStart=scheduling.getHourStart();
    }
    public String getRecurrences() {
        return recurrences;
    }

    public int getHourStart() {
        return hourStart;
    }

    public Integer getDayStart() {
        return dayStart;
    }
        public ProxyScheduling(int day, int hour, int minutes) throws TimeException {
            if(day < 0 )
                throw new TimeException(TimeException.TimeSection.DAY);
            if(hour>23 || hour<0)
                throw new TimeException(TimeException.TimeSection.HOURS);
            if(minutes>59 || minutes<0)
                throw new TimeException(TimeException.TimeSection.MINUTES);

            this.dayStart = day;
            this.hourStart = hour;
           // this.minutes = round(minutes);
        }

        private int round(int num) {
            int temp = num%5;
            if (temp<3)
                return num-temp;
            else
                return num+5-temp;
        }
    }

