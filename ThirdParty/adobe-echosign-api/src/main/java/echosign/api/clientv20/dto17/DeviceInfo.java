
package echosign.api.clientv20.dto17;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import echosign.api.clientv20.dto16.DeviceLocation;


/**
 * <p>Java class for DeviceInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeviceInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="applicationDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deviceDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deviceLocation" type="{http://dto16.api.echosign}DeviceLocation" minOccurs="0"/>
 *         &lt;element name="deviceTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="deviceTimeZoneOffset" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeviceInfo", propOrder = {
    "applicationDescription",
    "deviceDescription",
    "deviceLocation",
    "deviceTime",
    "deviceTimeZoneOffset"
})
public class DeviceInfo {

    @XmlElement(nillable = true)
    protected String applicationDescription;
    @XmlElement(nillable = true)
    protected String deviceDescription;
    @XmlElement(nillable = true)
    protected DeviceLocation deviceLocation;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar deviceTime;
    @XmlElement(nillable = true)
    protected Integer deviceTimeZoneOffset;

    /**
     * Gets the value of the applicationDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicationDescription() {
        return applicationDescription;
    }

    /**
     * Sets the value of the applicationDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicationDescription(String value) {
        this.applicationDescription = value;
    }

    /**
     * Gets the value of the deviceDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeviceDescription() {
        return deviceDescription;
    }

    /**
     * Sets the value of the deviceDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeviceDescription(String value) {
        this.deviceDescription = value;
    }

    /**
     * Gets the value of the deviceLocation property.
     * 
     * @return
     *     possible object is
     *     {@link DeviceLocation }
     *     
     */
    public DeviceLocation getDeviceLocation() {
        return deviceLocation;
    }

    /**
     * Sets the value of the deviceLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeviceLocation }
     *     
     */
    public void setDeviceLocation(DeviceLocation value) {
        this.deviceLocation = value;
    }

    /**
     * Gets the value of the deviceTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDeviceTime() {
        return deviceTime;
    }

    /**
     * Sets the value of the deviceTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDeviceTime(XMLGregorianCalendar value) {
        this.deviceTime = value;
    }

    /**
     * Gets the value of the deviceTimeZoneOffset property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDeviceTimeZoneOffset() {
        return deviceTimeZoneOffset;
    }

    /**
     * Sets the value of the deviceTimeZoneOffset property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDeviceTimeZoneOffset(Integer value) {
        this.deviceTimeZoneOffset = value;
    }

}
