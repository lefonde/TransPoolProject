import Generated.MapBoundries;

public class ProxyMapBoundries {

    protected int width;
    protected int length;

    //constructor
    public ProxyMapBoundries(MapBoundries mapBoundries)
    {
        this.width=mapBoundries.getWidth();
        this.length=mapBoundries.getLength();
    }
    /**
     * Gets the value of the width property.
     *
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the value of the width property.
     *
     */
    public void setWidth(int value) {
        this.width = value;
    }

    /**
     * Gets the value of the length property.
     *
     */
    public int getLength() {
        return length;
    }

    /**
     * Sets the value of the length property.
     *
     */
    public void setLength(int value) {
        this.length = value;
    }
}
