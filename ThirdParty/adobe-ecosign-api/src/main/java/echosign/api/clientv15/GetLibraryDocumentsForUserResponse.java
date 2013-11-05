
package echosign.api.clientv15;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv15.dto9.GetLibraryDocumentsForUserResult;


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
 *         &lt;element name="getLibraryDocumentsForUserResult" type="{http://dto9.api.echosign}GetLibraryDocumentsForUserResult"/>
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
    "getLibraryDocumentsForUserResult"
})
@XmlRootElement(name = "getLibraryDocumentsForUserResponse")
public class GetLibraryDocumentsForUserResponse {

    @XmlElement(required = true, nillable = true)
    protected GetLibraryDocumentsForUserResult getLibraryDocumentsForUserResult;

    /**
     * Gets the value of the getLibraryDocumentsForUserResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetLibraryDocumentsForUserResult }
     *     
     */
    public GetLibraryDocumentsForUserResult getGetLibraryDocumentsForUserResult() {
        return getLibraryDocumentsForUserResult;
    }

    /**
     * Sets the value of the getLibraryDocumentsForUserResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetLibraryDocumentsForUserResult }
     *     
     */
    public void setGetLibraryDocumentsForUserResult(GetLibraryDocumentsForUserResult value) {
        this.getLibraryDocumentsForUserResult = value;
    }

}
