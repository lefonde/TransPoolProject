import Generated.Path;

public class ProxyPath {

    private Path generatedPath;

    protected int length;
    protected int fuelConsumption;
    protected int speedLimit;
    protected String to;
    protected Boolean oneWay;
    protected String from;


    public ProxyPath(Generated.Path theGeneratedPath) {
       this.from=theGeneratedPath.getFrom();
       this.to=theGeneratedPath.getTo();
       this.oneWay=theGeneratedPath.isOneWay();
       this.fuelConsumption=theGeneratedPath.getFuelConsumption();
       this.speedLimit=theGeneratedPath.getSpeedLimit();
       this.length=theGeneratedPath.getLength();
    }

    public int getLength() {
        return length;
    }


}