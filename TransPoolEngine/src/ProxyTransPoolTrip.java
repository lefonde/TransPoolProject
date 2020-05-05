import Generated.Route;
import Generated.Scheduling;
import Generated.TransPoolTrip;

import java.util.List;
import java.util.stream.Stream;

public class ProxyTransPoolTrip {

    private static int counter = 0;
    protected TransPoolTrip transPoolTrip;
    protected String owner;
    protected int capacity;
    protected int serialNumber;
    protected List<String> members;
    protected int ppk;
    protected String route;
    protected ProxyScheduling scheduling;


    public ProxyTransPoolTrip(TransPoolTrip theTransPoolTrip)
    {

        this.capacity=theTransPoolTrip.getCapacity();
        this.owner= theTransPoolTrip.getOwner();
        this.ppk=theTransPoolTrip.getPPK();
        this.route= theTransPoolTrip.getRoute().getPath();
        this.scheduling= new ProxyScheduling(theTransPoolTrip.getScheduling());
        this.serialNumber= ++counter;
    }
    public String getOwner() {        return transPoolTrip.getOwner();    }

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


