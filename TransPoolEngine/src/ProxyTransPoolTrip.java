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

        this.capacity=theTransPoolTrip.getCapacity();
        this.owner= theTransPoolTrip.getOwner();
        this.ppk=theTransPoolTrip.getPPK();
        this.route= theTransPoolTrip.getRoute().getPath();
        this.scheduling= new ProxyScheduling(theTransPoolTrip.getScheduling());
        this.serialNumber= ++counter;
        this.hitchhikers = new ArrayList<TripRequest>();
        isCarFull = false;
    }

    public boolean isCarFull() {
        return isCarFull;
    }

    public void addHitchhiker(TripRequest hitchhiker) {
        this.hitchhikers.add(hitchhiker);
        isCarFull = hitchhikers.size() == capacity ? true : false;
        hitchhiker.setRequestAsMatched();
    }
    public List<TripRequest> getHitchhikers(){
        return hitchhikers;
    }

    public String getOwner() {        return owner;    }

    public void setOwner(String value) {        this.owner = value;    }

    public int getSerialNumber() {        return serialNumber;    }

    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int value) {this.capacity = value;    }

    public int getPPK() {return ppk;    }

    public void setPPK(int value) { this.ppk = value;    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String value) {       this.route = value;    }

    public ProxyScheduling getScheduling() {       return scheduling;    }

    public void setScheduling(ProxyScheduling value) {        this.scheduling = value;    }

}


