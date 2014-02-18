package gov.samhsa.consent2share.domain.valueset;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Entity
public class ValueSetVersion extends AbstractNodeVersion{
	/** The id. */
	@Id
	@GeneratedValue
	@Column(name = "valueSet_version_id")
	private Long id;	
	
	@ManyToOne
	@JoinColumn(name="valueset_id")
	private ValueSet valueSet;
	
	@OneToMany(mappedBy="valueSetVersion")
	private List<ConceptCodeValueSetVersionMembership> conceptCodes;

	//it is required by Hibernate
	public ValueSetVersion(){
		
	}
	
	/**
     * A Builder class used to create new CodeSystem objects.
     */
    public static class Builder {
        ValueSetVersion built;

        /**
         * Creates a new Builder instance.
         * @param codeSystemOId The object identifier of the  created ValueSetVersion object.
         * @param code  The code of the created ValueSetVersion object.
         * @param name  The name of the created ValueSetVersion object.
         * @param userName  The User who created ValueSetVersion object.
         */
        Builder(ValueSet valueSet, String userName) {
            built = new ValueSetVersion();
            built.valueSet = valueSet;
            built.userName = userName;
        }
        
        /**
         * Builds the new ValueSetVersion object.
         * @return  The created ValueSetVersion object.
         */
        public ValueSetVersion build() {
            return built;
        }

   
    }
	
    /**
     * Creates a new Builder instance.
     * @param codeSystemOId The object identifier of the  created ValueSetVersion object.
     * @param code  The code of the created ValueSetVersion object.
     * @param name  The name of the created ValueSetVersion object.
     * @param userName  The User who created ValueSetVersion object.
     */
    public static Builder getBuilder(ValueSet valueSet, String userName) {
        return new Builder(valueSet, userName);
    }

	@PrePersist
    public void prePersist() {
		try {
			uploadEffectiveDate = getCurrentDate();
		} catch (ParseException e) {
			uploadEffectiveDate = new Date();
		}     		
    }

	@PreUpdate
    public void preUpdate() {
		try {
			uploadEffectiveDate = getCurrentDate();
		} catch (ParseException e) {
			uploadEffectiveDate = new Date();
		}    	
    }
	
	
	public List<ConceptCodeValueSetVersionMembership> getConceptCodes() {
		return conceptCodes;
	}


	public Long getId() {
		return id;
	}

	public ValueSet getValueSet() {
		return valueSet;
	}


	public void setConceptCodes(List<ConceptCodeValueSetVersionMembership> conceptCodes) {
		this.conceptCodes = conceptCodes;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setValueSet(ValueSet valueSet) {
		this.valueSet = valueSet;
	}	

}
