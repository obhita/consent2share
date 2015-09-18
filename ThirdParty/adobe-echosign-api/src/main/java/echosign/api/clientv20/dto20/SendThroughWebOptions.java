
package echosign.api.clientv20.dto20;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SendThroughWebOptions complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SendThroughWebOptions">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fileUploadOptions" type="{http://dto20.api.echosign}FileUploadOptions" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SendThroughWebOptions", propOrder = {
    "fileUploadOptions"
})
public class SendThroughWebOptions {

    @XmlElement(nillable = true)
    protected FileUploadOptions fileUploadOptions;

    /**
     * Gets the value of the fileUploadOptions property.
     * 
     * @return
     *     possible object is
     *     {@link FileUploadOptions }
     *     
     */
    public FileUploadOptions getFileUploadOptions() {
        return fileUploadOptions;
    }

    /**
     * Sets the value of the fileUploadOptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link FileUploadOptions }
     *     
     */
    public void setFileUploadOptions(FileUploadOptions value) {
        this.fileUploadOptions = value;
    }

}
