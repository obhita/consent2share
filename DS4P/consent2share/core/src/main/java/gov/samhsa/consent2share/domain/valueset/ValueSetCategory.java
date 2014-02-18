package gov.samhsa.consent2share.domain.valueset;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.envers.Audited;

@Entity
@Audited
@AttributeOverride(name = "code", column = @Column(name = "code", unique=true, nullable = false))
public class ValueSetCategory extends AbstractNode{
	
	@Id
	@GeneratedValue
	@Column(name = "vs_cat_id")
	private Long id;

	//optional
	private String description;
	
	//it is required by Hibernate
	public ValueSetCategory(){
		
	}
	
	/**
     * A Builder class used to create new CodeSystem objects.
     */
    public static class Builder {
        ValueSetCategory built;

        /**
         * Creates a new Builder instance.
         * @param code  The code of the created ValueSetCategory object.
         * @param name  The name of the created  ValueSetCategory object.
         * @param userName  The User who created  ValueSetCategory object.
       */
        Builder(String code, String name, String userName) {
            built = new ValueSetCategory();
            built.code = code;
            built.name = name;
            built.userName = userName;
        }
        
        /**
         * Builds the new CodeSystem object.
         * @return  The created CodeSystem object.
         */
        public ValueSetCategory build() {
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
     * @param code  The code of the created ValueSetCategory object.
     * @param name  The name of the created  ValueSetCategory object.
     * @param userName  The User who created  ValueSetCategory object.
     */
    public static Builder getBuilder(String code, String name, String userName) {
        return new Builder(code, name, userName);
    }
	
    /**
     * Updates a ValueSetCategory instance.
     * @param code  The code of the created ValueSetCategory object.
     * @param name  The name of the created  ValueSetCategory object.
     * @param userName  The User who created  ValueSetCategory object.
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

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

}
