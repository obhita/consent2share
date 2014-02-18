package gov.samhsa.consent2share.domain.valueset;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

@Entity
@Audited
@AttributeOverride(name = "code", column = @Column(name = "code", unique=true, nullable = false))
public class ValueSet extends AbstractNode{
	
	/** The id. */
	@Id
	@GeneratedValue
	@Column(name = "valueset_id")
	private Long id;

	//optional
	private String description;

	/*	
	 * ValueSetVersion has Composition Relation with ValueSet
	 * If ValueSet is destroyed or ValueSetVersion is removed from ValueSet's collection
	 * Then ValueSetVersion is destroyed automatically
	*/	
	@OneToMany(mappedBy="valueSet", orphanRemoval=true)
	@NotAudited 
	private List<ValueSetVersion> valueSetVersions;

	//it is required by Hibernate
	public ValueSet(){
		
	}
	/**
     * A Builder class used to create new CodeSystem objects.
     */
    public static class Builder {
        ValueSet built;

        /**
         * Creates a new Builder instance.
         * @param code  The code of the created ValueSet object.
         * @param name  The name of the created  ValueSet object.
         * @param userName  The User who created  ValueSet object.
       */
        Builder(String code, String name, String userName) {
            built = new ValueSet();
            built.code = code;
            built.name = name;
            built.userName = userName;
        }
        
        /**
         * Builds the new CodeSystem object.
         * @return  The created CodeSystem object.
         */
        public ValueSet build() {
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
     * @param code  The code of the created ValueSet object.
     * @param name  The name of the created  ValueSet object.
     * @param userName  The User who created  ValueSet object.
     */
    public static Builder getBuilder(String code, String name, String userName) {
        return new Builder(code, name, userName);
    }
	
    /**
     * Updates a ValueSet instance.
     * @param code  The code of the created ValueSet object.
     * @param name  The name of the created  ValueSet object.
     * @param userName  The User who created  ValueSet object.
     *
     */
    public void update(String code, String name, String description,  String userName) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.userName = userName;
    }
    
   

	public String getDescription() {
		return description;
	}

	public Long getId() {
		return id;
	}

	public List<ValueSetVersion> getValueSetVersions() {
		return valueSetVersions;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setValueSetVersions(List<ValueSetVersion> valueSetVersions) {
		this.valueSetVersions = valueSetVersions;
	}
		
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }	
	

	
}
