package gov.samhsa.consent2share.service.valueset;

import gov.samhsa.consent2share.domain.valueset.CodeSystemRepository;
import gov.samhsa.consent2share.domain.valueset.CodeSystemVersion;
import gov.samhsa.consent2share.domain.valueset.CodeSystemVersionRepository;
import gov.samhsa.consent2share.domain.valueset.ConceptCode;
import gov.samhsa.consent2share.domain.valueset.ConceptCodeRepository;
import gov.samhsa.consent2share.domain.valueset.ConceptCodeValueSet;
import gov.samhsa.consent2share.domain.valueset.ConceptCodeValueSetRepository;
import gov.samhsa.consent2share.domain.valueset.ValueSetRepository;
import gov.samhsa.consent2share.service.dto.ValueSetLookUpDto;
import gov.samhsa.consent2share.service.dto.ValueSetQueryDto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValueSetLookupServiceImpl implements ValueSetLookupService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CodeSystemVersionService.class);
	
	@Resource
	private ConceptCodeRepository conceptCodeRepository;

	@Resource
	private ValueSetRepository valueSetRepository;

	@Resource
	CodeSystemRepository codeSystemRepository;

	@Resource
	CodeSystemVersionRepository codeSystemVersionRepository;

	@Resource
	ConceptCodeValueSetRepository conceptCodeValueSetRepository;
	
	@Autowired
	ValueSetMgmtHelper valueSetMgmtHelper;
	

	private Set<String> ValueSetCategoriesInSet(String code, String codeSystemOid) throws CodeSystemVersionNotFoundException, ConceptCodeNotFoundException, ValueSetNotFoundException {
		
		Set<String> vsCategories = new HashSet<String>();
		
		//validate the inputs
		if(null ==  code || code.length() <= 0){
			throw new ConceptCodeNotFoundException();
		}
		
		// 1.Get latest version of Code System version for the given code system oid
		List<CodeSystemVersion> codeSystemVersions = codeSystemVersionRepository.findAllByCodeSystemCodeSystemOIdOrderByIdDesc(codeSystemOid);
		if (codeSystemVersions == null || codeSystemVersions.size() <= 0) {
			LOGGER.debug("No CodeSystem found with the oid: " + codeSystemOid);
			throw new CodeSystemVersionNotFoundException("No Code System Versions found for the given codesystem oid" + codeSystemOid);
		}	
		//Get the latest version 
		CodeSystemVersion codeSystemVersion = codeSystemVersions.get(0);
				
		// 2.Get the concept code for the given code and the latest code system version
		ConceptCode conceptCode = conceptCodeRepository.findByCodeAndCodeSystemVersionId(code.trim(), codeSystemVersion.getId());
		if( conceptCode == null){
			throw new ConceptCodeNotFoundException("No Concept Code found for the given Code System  oid: " + codeSystemOid +" And its latest version name: " + codeSystemVersion.getName());
		}
		
		// 3.Get the value sets associated to the concept code
		List<ConceptCodeValueSet> cValueSets = conceptCodeValueSetRepository.findAllByPkConceptCodeId(conceptCode.getId());
		if( cValueSets == null){
			throw new ValueSetNotFoundException("No Valusets associated to the given codes" + conceptCode);
		}
		for(ConceptCodeValueSet codeValueSet: cValueSets){
			//get the category code for the associated valuesets
			vsCategories.add(codeValueSet.getValueSet().getValueSetCategory().getCode());
		}
		
		return vsCategories;
	}


	@Override
	public ValueSetLookUpDto lookupValueSetCategories(String code,
			String codeSystemOid) throws CodeSystemVersionNotFoundException,
			ConceptCodeNotFoundException, ValueSetNotFoundException {
		
		ValueSetLookUpDto valueSetLookUpDto = new ValueSetLookUpDto();
		valueSetLookUpDto.setCodeSystemOid(codeSystemOid);
		valueSetLookUpDto.setConceptCode(code);
		valueSetLookUpDto.setVsCategoryCodes(ValueSetCategoriesInSet(code, codeSystemOid));
		
		return valueSetLookUpDto;
	}


	@Override
	public ValueSetQueryDto RestfulValueSetCategories(String code,
			String codeSystemOid) throws CodeSystemVersionNotFoundException,
			ConceptCodeNotFoundException, ValueSetNotFoundException {
		
		ValueSetQueryDto valueSetQueryDto = new ValueSetQueryDto();
		valueSetQueryDto.setCodeSystemOid(codeSystemOid);
		valueSetQueryDto.setConceptCode(code);
		valueSetQueryDto.setVsCategoryCodes(ValueSetCategoriesInSet(code, codeSystemOid));
		
		return valueSetQueryDto;
	}
	
	
	

}
