import java.awt.*;
import Generated.Stop;

public class ProxyStop {
    private Stop generatedStop;
    private int from;
    private int to;
    protected String name;

    public ProxyStop(Stop generatedStop) {

       this.from = generatedStop.getX();
       this.to = generatedStop.getY();
       this.name=generatedStop.getName();
    }
    /**
     * Gets the value of the y property.
     *
     */
    public int getTo() {
        return to;
    }

    /**
     * Sets the value of the y property.
     *
     */
    public void setTo(int value) {
        this.to = value;
    }

    /**
     * Gets the value of the x property.
     *
     */
    public int getFrom() {
        return from;
    }

    /**
     * Sets the value of the x property.
     *
     */
    public void setFrom(int value) {
        this.from = value;
    }

    /**
     * Gets the value of the name property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setName(String value) {
        this.name = value;
    }


}