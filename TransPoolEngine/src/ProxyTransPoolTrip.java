import Generated.TransPoolTrip;

import java.util.ArrayList;
import java.util.List;

public class ProxyTransPoolTrip {

    private static int counter = 0;
    private int serialNumber;


    private String owner;
    private int capacity;
    private List<TripRequest> members;
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
        this.members= new ArrayList<TripRequest>();
    }

    public void setMembers(List<TripRequest> members) {
        this.members = members;
    }
    public List<TripRequest> getMembers(){
        return members;
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


