package gov.samhsa.consent2share.domain.valueset;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.envers.Audited;
/**
 * An entity class which contains the information of a Code System.
 */
@Entity
@Audited
public class CodeSystem extends AbstractNode{
	
    /** The id. */
	@Id
	@GeneratedValue
	@Column(name = "codeSystem_id")
	private Long id;	
    
	@Column(name = "codeSystem_oid", unique=true, nullable=false)
	private String codeSystemOId;
	
	//optional
    private String displayName;
    
	@OneToMany(mappedBy="codeSystem")
	private List< ConceptCodeSystemMembership> conceptCodes;
	

	//it is required by Hibernate
	public CodeSystem(){
		
	}
	
	
	/**
     * A Builder class used to create new CodeSystem objects.
     */
    public static class Builder {
        CodeSystem built;

        /**
         * Creates a new Builder instance.
         * @param codeSystemOId The object identifier of the  created CodeSystem object.
         * @param code  The code of the created CodeSystem object.
         * @param name  The name of the created CodeSystem object.
         * @param userName  The User who created CodeSystem object.
         */
        Builder(String codeSystemOId, String code, String name, String userName) {
            built = new CodeSystem();
            built.codeSystemOId = codeSystemOId;
            built.code = code;
            built.name = name;
            built.userName = userName;
        }
        
        /**
         * Builds the new CodeSystem object.
         * @return  The created CodeSystem object.
         */
        public CodeSystem build() {
            return built;
        }

        //optional
        public Builder displayName(String displayName){
        	built.setDisplayName(displayName);
        	return this;
        	
        }
    
    }
	
    /**
     * Creates a new Builder instance.
     * @param codeSystemOId The object identifier of the  created CodeSystem object.
     * @param code  The code of the created CodeSystem object.
     * @param name  The name of the created CodeSystem object.
     * @param userName  The User who created CodeSystem object.
     */
    public static Builder getBuilder(String codeSystemOId, String code, String name, String userName){
        return new Builder(codeSystemOId, code, name, userName);
    }

    /**
     * Updates a Code System instance.
     * @param codeSystemOId The object identifier of the  created CodeSystem object.
     * @param code  The code of the created CodeSystem object.
     * @param name  The name of the created CodeSystem object.
     * @param displayName  The display name of the created CodeSystem object.
     * @param userName  The User who created CodeSystem object.
     *
     */
    public void update(String codeSystemOId, String code, String name, String displayName,  String userName) {
        this.codeSystemOId = codeSystemOId;
        this.code = code;
        this.name = name;
        this.displayName = displayName;
        this.userName = userName;
    }	

	public String getCodeSystemOId() {
		return codeSystemOId;
	}
	

	public List<ConceptCodeSystemMembership> getConceptCodes() {
		return conceptCodes;
	}



	public String getDisplayName() {
		return displayName;
	}

	public Long getId() {
		return id;
	}

	public void setCodeSystemOId(String codeSystemOId) {
		this.codeSystemOId = codeSystemOId;
	}

	public void setConceptCodes(List<ConceptCodeSystemMembership> conceptCodes) {
		this.conceptCodes = conceptCodes;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	/**
     * This setter method should only be used by unit tests.
     * @param id
     */	
    protected void setId(Long id) {
		this.id = id;
	}	
	

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }


}
