
package gov.samhsa.ds4ppilot.schema.pep;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import gov.va.ehtac.ds4p.ws.EnforcePolicy;


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
 *         &lt;element name="homeCommunityId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="repositoryUniqueId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="documentUniqueId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="messageId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="enforcePolicy" type="{http://ws.ds4p.ehtac.va.gov/}enforcePolicy"/>
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
    "homeCommunityId",
    "repositoryUniqueId",
    "documentUniqueId",
    "messageId",
    "enforcePolicy"
})
@XmlRootElement(name = "RetrieveDocumentSetRequest")
public class RetrieveDocumentSetRequest {

    @XmlElement(required = true)
    protected String homeCommunityId;
    @XmlElement(required = true)
    protected String repositoryUniqueId;
    @XmlElement(required = true)
    protected String documentUniqueId;
    @XmlElement(required = true)
    protected String messageId;
    @XmlElement(required = true)
    protected EnforcePolicy enforcePolicy;

    /**
     * Gets the value of the homeCommunityId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHomeCommunityId() {
        return homeCommunityId;
    }

    /**
     * Sets the value of the homeCommunityId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHomeCommunityId(String value) {
        this.homeCommunityId = value;
    }

    /**
     * Gets the value of the repositoryUniqueId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRepositoryUniqueId() {
        return repositoryUniqueId;
    }

    /**
     * Sets the value of the repositoryUniqueId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRepositoryUniqueId(String value) {
        this.repositoryUniqueId = value;
    }

    /**
     * Gets the value of the documentUniqueId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentUniqueId() {
        return documentUniqueId;
    }

    /**
     * Sets the value of the documentUniqueId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentUniqueId(String value) {
        this.documentUniqueId = value;
    }

    /**
     * Gets the value of the messageId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * Sets the value of the messageId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageId(String value) {
        this.messageId = value;
    }

    /**
     * Gets the value of the enforcePolicy property.
     * 
     * @return
     *     possible object is
     *     {@link EnforcePolicy }
     *     
     */
    public EnforcePolicy getEnforcePolicy() {
        return enforcePolicy;
    }

    /**
     * Sets the value of the enforcePolicy property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnforcePolicy }
     *     
     */
    public void setEnforcePolicy(EnforcePolicy value) {
        this.enforcePolicy = value;
    }

}
