package Source;

import Generated.Stop;
import Generated.TransPoolTrip;

import java.util.ArrayList;
import java.util.List;

public class ProxyTransPoolTrip {

    private static int counter = 0;
    private int serialNumber;

    private String owner;
    private int capacity;
    private boolean isCarFull;
    private List<TripRequest> hitchhikers;
    private int ppk;
    private String route;
    private ProxyScheduling scheduling;


    public ProxyTransPoolTrip(TransPoolTrip theTransPoolTrip)
    {

        this.capacity = theTransPoolTrip.getCapacity();
        this.owner = theTransPoolTrip.getOwner();
        this.ppk = theTransPoolTrip.getPPK();
        this.route = theTransPoolTrip.getRoute().getPath();
        this.scheduling = new ProxyScheduling(theTransPoolTrip.getScheduling());
        this.serialNumber = ++counter;
        this.hitchhikers = new ArrayList<>();
        isCarFull = false;
    }

    public ProxyTransPoolTrip(String name, ProxyScheduling schedule, int ppk, int seatsAvailable, String route) {
        this.capacity = seatsAvailable;
        this.owner = name;
        this.ppk = ppk;
        this.route = route;
        this.scheduling = schedule;
        this.serialNumber = ++counter;
        this.hitchhikers = new ArrayList<>();
        isCarFull = false;
    }

    public boolean isCarFull() {
        return isCarFull;
    }

    public void addHitchhiker(TripRequest hitchhiker) {
        this.hitchhikers.add(hitchhiker);
        isCarFull = hitchhikers.size() == capacity;
        hitchhiker.CloseRequest();
    }

    public List<TripRequest> getHitchhikers(){
        return hitchhikers;
    }

    public String getOwner() { return owner; }

    public int getSerialNumber() { return serialNumber; }

    public int getCapacity() {
        return capacity;
    }

    public int getPPK() { return ppk; }

    public String getRouteAsString() {
        return route;
    }



    public ProxyScheduling getScheduling() { return scheduling;}
}


