
package echosign.api.clientv15;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv15.dto14.GetSupportingDocumentsResult;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="getSupportingDocumentsResult" type="{http://dto14.api.echosign}GetSupportingDocumentsResult"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getSupportingDocumentsResult"
})
@XmlRootElement(name = "getSupportingDocumentsResponse")
public class GetSupportingDocumentsResponse {

    @XmlElement(required = true, nillable = true)
    protected GetSupportingDocumentsResult getSupportingDocumentsResult;

    /**
     * Gets the value of the getSupportingDocumentsResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetSupportingDocumentsResult }
     *     
     */
    public GetSupportingDocumentsResult getGetSupportingDocumentsResult() {
        return getSupportingDocumentsResult;
    }

    /**
     * Sets the value of the getSupportingDocumentsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetSupportingDocumentsResult }
     *     
     */
    public void setGetSupportingDocumentsResult(GetSupportingDocumentsResult value) {
        this.getSupportingDocumentsResult = value;
    }

}
