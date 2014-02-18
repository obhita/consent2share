package gov.samhsa.consent2share.domain.valueset;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Audited
@IdClass(ConceptCodeSystemMembershipId.class)
public class ConceptCodeSystemMembership {

	/** The id. */
	@Id
	@GeneratedValue
	@Column(name = "concept_id")
	private Long conceptId;
	
	/** The id. */
	@Id
	@GeneratedValue
	@Column(name = "codeSystem_id")
	private Long codeSystemId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date uploadEffectiveDate;	
	
	
	@ManyToOne
	@PrimaryKeyJoinColumn(name="concept_id", referencedColumnName="id")
	@NotAudited 
	private ConceptCode conceptCode;
	
	@ManyToOne
	@PrimaryKeyJoinColumn(name="codeSystem_id", referencedColumnName="id")
	@NotAudited 
	private CodeSystem codeSystem;

	public CodeSystem getCodeSystem() {
		return codeSystem;
	}

	public Long getCodeSystemId() {
		return codeSystemId;
	}

	public ConceptCode getConceptCode() {
		return conceptCode;
	}

	public Long getConceptId() {
		return conceptId;
	}


	public Date getUploadEffectiveDate() {
		return uploadEffectiveDate;
	}

	public void setCodeSystem(CodeSystem codeSystem) {
		this.codeSystem = codeSystem;
	}

	public void setCodeSystemId(Long codeSystemId) {
		this.codeSystemId = codeSystemId;
	}

	public void setConceptCode(ConceptCode conceptCode) {
		this.conceptCode = conceptCode;
	}

	public void setConceptId(Long conceptId) {
		this.conceptId = conceptId;
	}

	public void setUploadEffectiveDate(Date uploadEffectiveDate) {
		this.uploadEffectiveDate = uploadEffectiveDate;
	}


	
	
}
