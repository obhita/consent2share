package gov.samhsa.consent2share.service.valueset;

import gov.samhsa.consent2share.domain.valueset.ConceptCode;
import gov.samhsa.consent2share.domain.valueset.ValueSet;
import gov.samhsa.consent2share.service.dto.ConceptCodeDto;
import gov.samhsa.consent2share.service.dto.ValueSetDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ValueSetMgmtHelper {
	
	public List<ValueSetDto> convertValueSetEntitiesToDtos(List<ValueSet> valueSets){
		List<ValueSetDto> valueSetDtos = new ArrayList<ValueSetDto>();
		for(ValueSet valueSet : valueSets){
			ValueSetDto valueSetDto = createValuesetDtoFromEntity(valueSet);
			valueSetDtos.add(valueSetDto);			
		}		
		return valueSetDtos;
	}

	public ValueSetDto createValuesetDtoFromEntity(ValueSet valueSet) {
		ValueSetDto valueSetDto = new ValueSetDto();
		valueSetDto.setCode(valueSet.getCode());
		valueSetDto.setDescription(valueSet.getDescription());
		valueSetDto.setName(valueSet.getName());
		valueSetDto.setId(valueSet.getId());		
		return valueSetDto;		
	}
	
	public Map<Long, String>  convertValueSetEntitiesToMap(List<ValueSet> valueSets){
		Map<Long, String> valueSetsMap = new HashMap<Long, String>();
		for(ValueSet valueSet : valueSets){
			valueSetsMap.put(valueSet.getId(), valueSet.getCode());
		}		
		return valueSetsMap;
	}	
	
	public ConceptCodeDto createConceptCodeDtoFromEntity(ConceptCode conceptCode){		
		ConceptCodeDto conceptCodeDto = new ConceptCodeDto();
		conceptCodeDto.setCode(conceptCode.getCode());
		conceptCodeDto.setDescription(conceptCode.getDescription());
		conceptCodeDto.setName(conceptCode.getName());
		conceptCodeDto.setId(conceptCode.getId());		
		return conceptCodeDto;		
	} 
	
	public List<ConceptCodeDto> convertConceptCodeEntitiesToDtos(List<ConceptCode> conceptCodes){
		List<ConceptCodeDto> conceptCodeDtos = new ArrayList<ConceptCodeDto>();
		for(ConceptCode conceptCode : conceptCodes){
			ConceptCodeDto conceptCodeDto = createConceptCodeDtoFromEntity(conceptCode);
			conceptCodeDtos.add(conceptCodeDto);			
		}		
		return conceptCodeDtos;
	}	
	
	

}
