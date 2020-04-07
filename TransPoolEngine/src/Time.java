import Exceptions.TimeException;

public class Time {
        private int day;
        private int hour;
        private int minutes;

        public Time(int day, int hour,int minutes) throws TimeException {
            this.day = day;
            if(hour>23 || hour<0)
                // throw new TimeException("Hours");
            this.hour = hour;
            if(minutes>59 || minutes<0)
                throw new TimeException(TimeException.TimeSection.MINUTES); //@after it throws the Exceptions.MinutesException how we let the user enter new minute value
            else
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

