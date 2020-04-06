import Exceptions.MinutesException;

public class Time {
        private int day;
        private int hour;
        private int minutes;

        private Time(int day, int hour,int minutes) throws MinutesException {
            this.day = day;
            if(hour>23 || hour<0)
                //
            this.hour = hour;
            if(minutes>59 || minutes<0)
                throw new MinutesException(); //@after it throws the Exceptions.MinutesException how we let the user enter new minute value
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

