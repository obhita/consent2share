
package echosign.api.clientv15;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv15.dto14.GetDocumentsResult;


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
 *         &lt;element name="getDocumentsResult" type="{http://dto14.api.echosign}GetDocumentsResult"/>
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
    "getDocumentsResult"
})
@XmlRootElement(name = "getDocumentsResponse")
public class GetDocumentsResponse {

    @XmlElement(required = true, nillable = true)
    protected GetDocumentsResult getDocumentsResult;

    /**
     * Gets the value of the getDocumentsResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetDocumentsResult }
     *     
     */
    public GetDocumentsResult getGetDocumentsResult() {
        return getDocumentsResult;
    }

    /**
     * Sets the value of the getDocumentsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetDocumentsResult }
     *     
     */
    public void setGetDocumentsResult(GetDocumentsResult value) {
        this.getDocumentsResult = value;
    }

}
