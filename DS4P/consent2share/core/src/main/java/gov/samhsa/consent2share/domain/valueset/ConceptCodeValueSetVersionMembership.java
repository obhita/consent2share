package gov.samhsa.consent2share.domain.valueset;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

@Entity
@Audited
@IdClass(ConceptCodeValueSetVersionMembershipId.class)
public class ConceptCodeValueSetVersionMembership extends AbstractNodeVersion{
	
	/** The id. */
	@Id
	@GeneratedValue
	@Column(name = "concept_id")
	private Long conceptId;
	
	/** The id. */
	@Id
	@GeneratedValue
	@Column(name = "valueSet_version_id")
	private Long valueSetVersionId;
	
	
	@ManyToOne
	@PrimaryKeyJoinColumn(name="concept_id", referencedColumnName="id")
	@NotAudited 
	private ConceptCode conceptCode;
	
	@ManyToOne
	@PrimaryKeyJoinColumn(name="valueSet_version_id", referencedColumnName="id")
	@NotAudited 
	private ValueSetVersion valueSetVersion;

	/**
     * A Builder class used to create new CodeSystem objects.
     */
    public static class Builder {
        ConceptCodeValueSetVersionMembership built;

        /**
         * Creates a new Builder instance.
         * @param codeSystemOId The object identifier of the  created ConceptValueSetMembership object.
         * @param conceptCode  The code of the created ConceptValueSetMembership object.
         * @param valueSetVersion  The valueSetVersion of the created ConceptValueSetMembership object.
         * @param uploadEffectiveDate  The uploadEffectiveDate of the created ConceptValueSetMembership object.
         * @param userName  The User who created ConceptValueSetMembership object.
         */
        Builder(ConceptCode conceptCode, ValueSetVersion valueSetVersion, Date uploadEffectiveDate, String userName) {
            built = new ConceptCodeValueSetVersionMembership();
            built.conceptCode = conceptCode;
            built.valueSetVersion = valueSetVersion;
            built.uploadEffectiveDate = uploadEffectiveDate;
            built.userName = userName;
        }
        
        /**
         * Builds the new ConceptValueSetMembership object.
         * @return  The created ConceptValueSetMembership object.
         */
        public ConceptCodeValueSetVersionMembership build() {
            return built;
        }

   
    }
	
    /**
    * Creates a new Builder instance.
     * @param codeSystemOId The object identifier of the  created ConceptValueSetMembership object.
     * @param conceptCode  The code of the created ConceptValueSetMembership object.
     * @param valueSetVersion  The valueSetVersion of the created ConceptValueSetMembership object.
     * @param uploadEffectiveDate  The uploadEffectiveDate of the created ConceptValueSetMembership object.
     * @param userName  The User who created ConceptValueSetMembership object.
     */
    public static Builder getBuilder(ConceptCode conceptCode, ValueSetVersion valueSetVersion, Date uploadEffectiveDate, String userName) {
        return new Builder( conceptCode, valueSetVersion, uploadEffectiveDate, userName);
    }

	public ConceptCode getConceptCode() {
		return conceptCode;
	}

	public Long getConceptId() {
		return conceptId;
	}

	public ValueSetVersion getValueSetVersion() {
		return valueSetVersion;
	}

	public Long getValueSetVersionId() {
		return valueSetVersionId;
	}

	public void setConceptCode(ConceptCode conceptCode) {
		this.conceptCode = conceptCode;
	}

	public void setConceptId(Long conceptId) {
		this.conceptId = conceptId;
	}

	public void setValueSetVersion(ValueSetVersion valueSetVersion) {
		this.valueSetVersion = valueSetVersion;
	}

	public void setValueSetVersionId(Long valueSetVersionId) {
		this.valueSetVersionId = valueSetVersionId;
	}	
	

}
