import Generated.MapDescriptor;

public class ProxyMapDescriptor {

    private MapDescriptor mapDescriptor=null;
    protected ProxyMapBoundries mapBoundries;
    protected ProxyStops stops;
    protected ProxyPaths paths;

    public ProxyMapDescriptor(MapDescriptor theMapDescriptor) {
        this.mapBoundries= new ProxyMapBoundries(theMapDescriptor.getMapBoundries());
        this.paths= new ProxyPaths(theMapDescriptor.getPaths());
        this.stops=new ProxyStops(theMapDescriptor.getStops());
    }

    /**
     * Gets the value of the mapBoundries property.
     *
     */
    public ProxyMapBoundries getMapBoundries() {
        return mapBoundries;
    }
    /**
     * Sets the value of the mapBoundries property.
     *
     */
    public void setMapBoundries(ProxyMapBoundries value) {
        this.mapBoundries = value;
    }

    /**
     * Gets the value of the stops property.
     *
     */
    public ProxyStops getStops() {
        return stops;
    }

    /**
     * Sets the value of the stops property.
     *
     */
    public void setStops(ProxyStops value) {
        this.stops = value;
    }

    /**
     * Gets the value of the paths property.
     *
     */
    public ProxyPaths getPaths() {
        return paths;
    }

    /**
     * Sets the value of the paths property.
     *
     */
    public void setPaths(ProxyPaths value) {
        this.paths = value;
    }


}
