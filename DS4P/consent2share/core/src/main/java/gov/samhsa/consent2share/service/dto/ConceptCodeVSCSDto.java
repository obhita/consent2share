package gov.samhsa.consent2share.service.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

public class ConceptCodeVSCSDto extends AbstractNodeDto {
	
	ConceptCodeDto conceptCodeDto = new ConceptCodeDto();
	
	Map<Long, String> valueSetsMap = new HashMap<Long, String>();
	
	List<CodeSystemDto> codeSystemDtos = new ArrayList<CodeSystemDto>();
	
	public ConceptCodeDto getConceptCodeDto() {
		return conceptCodeDto;
	}
	public void setConceptCodeDto(ConceptCodeDto conceptCodeDto) {
		this.conceptCodeDto = conceptCodeDto;
	}

	public Map<Long, String> getValueSetsMap() {
		return valueSetsMap;
	}
	public void setValueSetsMap(Map<Long, String> valueSetsMap) {
		this.valueSetsMap = valueSetsMap;
	}
	public List<CodeSystemDto> getCodeSystemDtos() {
		return codeSystemDtos;
	}
	public void setCodeSystemDtos(List<CodeSystemDto> codeSystemDtos) {
		this.codeSystemDtos = codeSystemDtos;
	}
	

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}	

}
