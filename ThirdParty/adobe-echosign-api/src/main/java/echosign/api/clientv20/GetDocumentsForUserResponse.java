
package echosign.api.clientv20;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv20.dto17.GetDocumentsForUserResult;


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
 *         &lt;element name="getDocumentsForUserResult" type="{http://dto17.api.echosign}GetDocumentsForUserResult"/>
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
    "getDocumentsForUserResult"
})
@XmlRootElement(name = "getDocumentsForUserResponse")
public class GetDocumentsForUserResponse {

    @XmlElement(required = true, nillable = true)
    protected GetDocumentsForUserResult getDocumentsForUserResult;

    /**
     * Gets the value of the getDocumentsForUserResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetDocumentsForUserResult }
     *     
     */
    public GetDocumentsForUserResult getGetDocumentsForUserResult() {
        return getDocumentsForUserResult;
    }

    /**
     * Sets the value of the getDocumentsForUserResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetDocumentsForUserResult }
     *     
     */
    public void setGetDocumentsForUserResult(GetDocumentsForUserResult value) {
        this.getDocumentsForUserResult = value;
    }

}
