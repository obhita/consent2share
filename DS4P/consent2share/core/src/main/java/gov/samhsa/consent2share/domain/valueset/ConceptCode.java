package gov.samhsa.consent2share.domain.valueset;


import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.envers.Audited;

@Entity
@Audited
@AttributeOverride(name = "code", column = @Column(name = "code", unique=true, nullable = false))
public class ConceptCode extends AbstractNode{
	/** The id. */
	@Id
	@GeneratedValue
	@Column(name = "concept_id")
	private Long id;

	//optional
	private String description;
	
	@OneToMany(mappedBy="conceptCode")
	private List< ConceptCodeSystemMembership> codeSystems;

	@OneToMany(mappedBy="conceptCode")
	private List<ConceptCodeValueSetVersionMembership> valueSetVersions;

	//it is required by Hibernate
	public ConceptCode(){
		
	}
	public List<ConceptCodeValueSetVersionMembership> getValueSetVersions() {
		return valueSetVersions;
	}

	public void setValueSetVersions(List<ConceptCodeValueSetVersionMembership> valueSetVersions) {
		this.valueSetVersions = valueSetVersions;
	}
	/**
     * A Builder class used to create new CodeSystem objects.
     */
    public static class Builder {
        ConceptCode built;

        /**
         * Creates a new Builder instance.
         * @param code  The code of the created ConceptCode object.
         * @param name  The name of the created  ConceptCode object.
         * @param userName  The User who created  ConceptCode object.
       */
        Builder(String code, String name, String userName) {
            built = new ConceptCode();
            built.code = code;
            built.name = name;
            built.userName = userName;
        }
        
        /**
         * Builds the new CodeSystem object.
         * @return  The created CodeSystem object.
         */
        public ConceptCode build() {
            return built;
        }

        //optional
        public Builder description(String description){
        	built.setDescription(description);
        	return this;
        	
        }
    
    }
	
	/**
     * Creates a new Builder instance.
     * @param code  The code of the created ConceptCode object.
     * @param name  The name of the created  ConceptCode object.
     * @param userName  The User who created  ConceptCode object.
     */
    public static Builder getBuilder(String code, String name, String userName) {
        return new Builder(code, name, userName);
    }
	
    /**
     * Updates a ConceptCode instance.
     * @param code  The code of the created ConceptCode object.
     * @param name  The name of the created  ConceptCode object.
     * @param userName  The User who created  ConceptCode object.
     *
     */
    public void update(String code, String name, String description,  String userName) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.userName = userName;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ConceptCodeSystemMembership> getCodeSystems() {
		return codeSystems;
	}

	public void setCodeSystems(List<ConceptCodeSystemMembership> codeSystems) {
		this.codeSystems = codeSystems;
	}



	
}
