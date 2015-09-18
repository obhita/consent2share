
package echosign.api.clientv20.dto18;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import echosign.api.clientv20.dto19.ArrayOfPhoneInfo;


/**
 * <p>Java class for RecipientSecurityOption complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RecipientSecurityOption">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authenticationMethod" type="{http://dto18.api.echosign}AuthenticationMethod" minOccurs="0"/>
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="phoneInfos" type="{http://dto19.api.echosign}ArrayOfPhoneInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RecipientSecurityOption", propOrder = {
    "authenticationMethod",
    "password",
    "phoneInfos"
})
public class RecipientSecurityOption {

    @XmlElement(nillable = true)
    protected AuthenticationMethod authenticationMethod;
    @XmlElement(nillable = true)
    protected String password;
    @XmlElement(nillable = true)
    protected ArrayOfPhoneInfo phoneInfos;

    /**
     * Gets the value of the authenticationMethod property.
     * 
     * @return
     *     possible object is
     *     {@link AuthenticationMethod }
     *     
     */
    public AuthenticationMethod getAuthenticationMethod() {
        return authenticationMethod;
    }

    /**
     * Sets the value of the authenticationMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthenticationMethod }
     *     
     */
    public void setAuthenticationMethod(AuthenticationMethod value) {
        this.authenticationMethod = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the phoneInfos property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPhoneInfo }
     *     
     */
    public ArrayOfPhoneInfo getPhoneInfos() {
        return phoneInfos;
    }

    /**
     * Sets the value of the phoneInfos property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPhoneInfo }
     *     
     */
    public void setPhoneInfos(ArrayOfPhoneInfo value) {
        this.phoneInfos = value;
    }

}
