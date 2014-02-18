package gov.samhsa.consent2share.domain.valueset;

import java.io.Serializable;

public class ConceptCodeSystemMembershipId implements Serializable{

	private static final long serialVersionUID = -2516002120624816359L;
	private Long conceptId;
	private Long codeSystemId;	

	public int hashCode(){
		return (int) (conceptId + codeSystemId);
	}	
	
	public boolean equals(Object object){
		
		if(object instanceof ConceptCodeSystemMembershipId){
			ConceptCodeSystemMembershipId otherId = (ConceptCodeSystemMembershipId) object;
			return(otherId.conceptId == this.conceptId) && (otherId.codeSystemId == this.codeSystemId);
		}
		return false;
		
	}

	
	
}
