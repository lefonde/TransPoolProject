//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.04.10 at 03:55:10 PM IDT 
//


package Generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="recurrences" default="OneTime">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="OneTime"/>
 *             &lt;enumeration value="Daily"/>
 *             &lt;enumeration value="BiDaily"/>
 *             &lt;enumeration value="Weekly"/>
 *             &lt;enumeration value="Monthly"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="hour-start" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="day-start" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "Scheduling")
public class Scheduling {

    @XmlAttribute(name = "recurrences")
    protected String recurrences;
    @XmlAttribute(name = "hour-start", required = true)
    protected int hourStart;
    @XmlAttribute(name = "day-start")
    protected Integer dayStart;

    /**
     * Gets the value of the recurrences property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecurrences() {
        if (recurrences == null) {
            return "OneTime";
        } else {
            return recurrences;
        }
    }

    /**
     * Sets the value of the recurrences property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecurrences(String value) {
        this.recurrences = value;
    }

    /**
     * Gets the value of the hourStart property.
     * 
     */
    public int getHourStart() {
        return hourStart;
    }

    /**
     * Sets the value of the hourStart property.
     * 
     */
    public void setHourStart(int value) {
        this.hourStart = value;
    }

    /**
     * Gets the value of the dayStart property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDayStart() {
        return dayStart;
    }

    /**
     * Sets the value of the dayStart property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDayStart(Integer value) {
        this.dayStart = value;
    }

}
