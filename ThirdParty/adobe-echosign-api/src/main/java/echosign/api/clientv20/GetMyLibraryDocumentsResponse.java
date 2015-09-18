
package echosign.api.clientv20;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv20.dto17.GetLibraryDocumentsForUserResult;


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
 *         &lt;element name="getMyLibraryDocumentsResult" type="{http://dto17.api.echosign}GetLibraryDocumentsForUserResult"/>
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
    "getMyLibraryDocumentsResult"
})
@XmlRootElement(name = "getMyLibraryDocumentsResponse")
public class GetMyLibraryDocumentsResponse {

    @XmlElement(required = true, nillable = true)
    protected GetLibraryDocumentsForUserResult getMyLibraryDocumentsResult;

    /**
     * Gets the value of the getMyLibraryDocumentsResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetLibraryDocumentsForUserResult }
     *     
     */
    public GetLibraryDocumentsForUserResult getGetMyLibraryDocumentsResult() {
        return getMyLibraryDocumentsResult;
    }

    /**
     * Sets the value of the getMyLibraryDocumentsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetLibraryDocumentsForUserResult }
     *     
     */
    public void setGetMyLibraryDocumentsResult(GetLibraryDocumentsForUserResult value) {
        this.getMyLibraryDocumentsResult = value;
    }

}
