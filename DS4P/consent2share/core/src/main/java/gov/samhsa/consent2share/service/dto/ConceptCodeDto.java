package gov.samhsa.consent2share.service.dto;

import org.apache.commons.lang.builder.ToStringBuilder;

public class ConceptCodeDto extends AbstractNodeDto {
	private String description;
	
	private Long valueSetId;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getValueSetId() {
		return valueSetId;
	}

	public void setValueSetId(Long valueSetId) {
		this.valueSetId = valueSetId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
