
package echosign.api.clientv15;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv15.dto.UserVerificationInfo;


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
 *         &lt;element name="userVerificationInfo" type="{http://dto.api.echosign}UserVerificationInfo"/>
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
    "userVerificationInfo"
})
@XmlRootElement(name = "verifyUserResponse")
public class VerifyUserResponse {

    @XmlElement(required = true, nillable = true)
    protected UserVerificationInfo userVerificationInfo;

    /**
     * Gets the value of the userVerificationInfo property.
     * 
     * @return
     *     possible object is
     *     {@link UserVerificationInfo }
     *     
     */
    public UserVerificationInfo getUserVerificationInfo() {
        return userVerificationInfo;
    }

    /**
     * Sets the value of the userVerificationInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserVerificationInfo }
     *     
     */
    public void setUserVerificationInfo(UserVerificationInfo value) {
        this.userVerificationInfo = value;
    }

}
