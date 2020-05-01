import Generated.MapDescriptor;
import Generated.PlannedTrips;
import Generated.TransPool;

public class ProxyTransPool {

    protected ProxyMapDescriptor mapDescriptor;
    protected ProxyPlannedTrips plannedTrips;

   public ProxyTransPool(TransPool transPool){
        this.mapDescriptor= new ProxyMapDescriptor(transPool.getMapDescriptor());
        this.plannedTrips= new ProxyPlannedTrips(transPool.getPlannedTrips());
        }



    /**
     * Gets the value of the mapDescriptor property.
     *
     */
    public ProxyMapDescriptor getMapDescriptor() {
        return mapDescriptor;
    }

    /**
     * Sets the value of the mapDescriptor property.
     *
     *
     */
    public void setMapDescriptor(ProxyMapDescriptor value) {
        this.mapDescriptor = value;
    }

    /**
     * Gets the value of the plannedTrips property.
     *
     */
    public ProxyPlannedTrips getPlannedTrips() {
        return plannedTrips;
    }

    /**
     * Sets the value of the plannedTrips property.
     */
    public void setPlannedTrips(ProxyPlannedTrips value) {
        this.plannedTrips = value;
    }
}
