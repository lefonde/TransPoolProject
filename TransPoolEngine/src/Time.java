import Exceptions.TimeException;

public class Time {
        private int day=0;
        private int hour;
        private int minutes;

        public Time(int day, int hour,int minutes) throws TimeException {
            if(day < 0 )
                throw new TimeException(TimeException.TimeSection.DAY);
            if(hour>23 || hour<0)
                throw new TimeException(TimeException.TimeSection.HOURS);
            if(minutes>59 || minutes<0)
                throw new TimeException(TimeException.TimeSection.MINUTES);

            this.day = day;
            this.hour = hour;
            this.minutes = round(minutes);
        }

        private int round(int num) {
            int temp = num%5;
            if (temp<3)
                return num-temp;
            else
                return num+5-temp;
        }
    }

