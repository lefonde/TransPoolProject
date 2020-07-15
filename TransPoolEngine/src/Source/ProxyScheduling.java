package Source;

import Exceptions.TimeException;
import Generated.Scheduling;


public class ProxyScheduling {
    private Scheduling scheduling = null;
    protected Recurrence recurrence;
    protected int hourStart;
    protected Integer dayStart;
    protected int minute;


    public ProxyScheduling(Scheduling scheduling)
    {
        this.recurrence = Recurrence.valueOf(scheduling.getRecurrences().toUpperCase());
        this.dayStart = scheduling.getDayStart();
        this.hourStart = scheduling.getHourStart();
        this.minute = 0;
    }

    public ProxyScheduling(int day, int hour, int minute) throws TimeException {
        if(day < 0 )
            throw new TimeException(TimeException.TimeSection.DAY);
        if(hour > 23 || hour < 0)
            throw new TimeException(TimeException.TimeSection.HOURS);
        if(minute > 59 || minute < 0)
            throw new TimeException(TimeException.TimeSection.MINUTES);

        this.dayStart = day;
        this.hourStart = hour;
        this.minute = round(minute);
        this.recurrence = Recurrence.ONETIME;
    }

    public ProxyScheduling(int day, int hour, int minute, Recurrence recurrence) throws TimeException {
        if(day < 0 )
            throw new TimeException(TimeException.TimeSection.DAY);
        if(hour > 23 || hour < 0)
            throw new TimeException(TimeException.TimeSection.HOURS);
        if(minute > 59 || minute < 0)
            throw new TimeException(TimeException.TimeSection.MINUTES);

        this.dayStart = day;
        this.hourStart = hour;
        this.minute = round(minute);
        this.recurrence = recurrence;
    }

    public Recurrence getRecurrence() {
        return recurrence;
    }

    public int getHourStart() {
        return hourStart;
    }
    
    public int getMinuteStart() { return minute; }

    public Integer getDayStart() {
        return dayStart;
    }


    private int round(int num) {
        int temp = num%5;
        if (temp<3)
            return num-temp;
        else
            return num+5-temp;
    }

    public ProxyScheduling addMinutes(int calculateTravelTimeToStop) {
        this.hourStart += calculateTravelTimeToStop / 60;
        //this.dayStart += calculateTravelTimeToStop % (60 * 24);
        this.minute += calculateTravelTimeToStop % 60;

        return this;
    }

    public boolean equals( ProxyScheduling schedule) {
        return hourStart == schedule.getHourStart() && minute == schedule.getMinuteStart();
    }
}

