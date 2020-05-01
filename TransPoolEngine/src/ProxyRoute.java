import Generated.Route;

public class ProxyRoute {

    protected String path;

    //constructor
    public ProxyRoute(Route r){
        this.path = r.getPath();
    }
    public String getPath() {
        return path;
    }

}
