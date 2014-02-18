package gov.samhsa.consent2share.domain.valueset;

import java.io.Serializable;

public class ConceptCodeValueSetVersionMembershipId implements Serializable{
	private static final long serialVersionUID = -4900262319794837292L;
	private Long conceptId;
	private Long valueSetVersionId;
	
	public boolean equals(Object object){
		
		if(object instanceof ConceptCodeValueSetVersionMembershipId){
			ConceptCodeValueSetVersionMembershipId otherId = (ConceptCodeValueSetVersionMembershipId) object;
			return(otherId.conceptId == this.conceptId) && (otherId.valueSetVersionId == this.valueSetVersionId);
		}
		return false;
		
	}
	
	public Long getConceptId() {
		return conceptId;
	}

	public Long getValueSetVersionId() {
		return valueSetVersionId;
	}

	public int hashCode(){
		return (int) (conceptId + valueSetVersionId);
	}

	public void setConceptId(Long conceptId) {
		this.conceptId = conceptId;
	}

	public void setValueSetVersionId(Long valueSetVersionId) {
		this.valueSetVersionId = valueSetVersionId;
	}

}
