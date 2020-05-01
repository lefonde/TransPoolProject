import Generated.Route;
import Generated.Scheduling;
import Generated.TransPoolTrip;

public class ProxyTransPoolTrip {

    private TransPoolTrip transPoolTrip;
    protected String owner;
    protected int capacity;
    protected int ppk;
    protected ProxyRoute route;
    protected ProxyScheduling scheduling;

    public ProxyTransPoolTrip(TransPoolTrip theTransPoolTrip)
    {
        this.capacity=theTransPoolTrip.getCapacity();
        this.owner= theTransPoolTrip.getOwner();
        this.ppk=theTransPoolTrip.getPPK();
        this.route= new ProxyRoute(theTransPoolTrip.getRoute());
        this.scheduling= new ProxyScheduling(theTransPoolTrip.getScheduling());
    }
    public String getOwner() {
        return transPoolTrip.getOwner();
    }

    public void setOwner(String value) {
        this.owner = value;
    }

    /**
     * Gets the value of the capacity property.
     *
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the value of the capacity property.
     *
     */
    public void setCapacity(int value) {
        this.capacity = value;
    }

    /**
     * Gets the value of the ppk property.
     *
     */
    public int getPPK() {
        return ppk;
    }

    /**
     * Sets the value of the ppk property.
     *
     */
    public void setPPK(int value) {
        this.ppk = value;
    }

    /**
     * Gets the value of the route property.
     *
     * @return
     *     possible object is
     *     {@link Route }
     *
     */
    public ProxyRoute getRoute() {
        return route;
    }

    /**
     * Sets the value of the route property.
     *
     * @param value
     *     allowed object is
     *     {@link Route }
     *
     */
    public void setRoute(ProxyRoute value) {
        this.route = value;
    }

    /**
     * Gets the value of the scheduling property.
     *
     * @return
     *     possible object is
     *     {@link Scheduling }
     *
     */
    public ProxyScheduling getScheduling() {
        return scheduling;
    }

    /**
     * Sets the value of the scheduling property.
     *
     * @param value
     *     allowed object is
     *     {@link Scheduling }
     *
     */
    public void setScheduling(ProxyScheduling value) {
        this.scheduling = value;
    }


}


