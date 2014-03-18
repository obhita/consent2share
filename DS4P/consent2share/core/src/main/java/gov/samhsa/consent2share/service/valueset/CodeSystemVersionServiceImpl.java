package gov.samhsa.consent2share.service.valueset;

import gov.samhsa.consent2share.domain.valueset.CodeSystem;
import gov.samhsa.consent2share.domain.valueset.CodeSystemRepository;
import gov.samhsa.consent2share.domain.valueset.CodeSystemVersion;
import gov.samhsa.consent2share.domain.valueset.CodeSystemVersionRepository;
import gov.samhsa.consent2share.service.dto.CodeSystemVersionCSDto;
import gov.samhsa.consent2share.service.dto.CodeSystemVersionDto;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CodeSystemVersionServiceImpl implements CodeSystemVersionService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CodeSystemVersionService.class);

	@Resource
	private CodeSystemVersionRepository codeSystemVersionRepository;
	
	@Resource
	private CodeSystemRepository codeSystemRepository;
	
	@Autowired
	ValueSetMgmtHelper codeSystemVersionMgmtHelper;

	@Override
	@Transactional
	public CodeSystemVersionDto create(CodeSystemVersionDto created) throws CodeSystemNotFoundException {
		LOGGER.debug("Creating a new CodeSystemVersion with information: " + created);
		String description = (created.getDescription() != null) ? created.getDescription() : "";
		CodeSystemVersion codeSystemVersion = CodeSystemVersion.getBuilder(created.getCode(), created.getName(), created.getUserName()).description(description).build();
		// Step:1 Save the codesystemversion in the codesystemversion table.
		codeSystemVersion = codeSystemVersionRepository.save(codeSystemVersion);
		
		// get the codesystemv entity from chosen codesystem id
		Long codeSystemId = created.getCodeSystemId();

		CodeSystem selected = codeSystemRepository.findOne(codeSystemId);
		if (selected == null) {
			LOGGER.debug("No CodeSystem found with an id: " + codeSystemId);
			throw new CodeSystemNotFoundException();
		}
		
		// refere codesystemversion category id to the codesystemversion  entity object
		codeSystemVersion.setCodeSystem(selected);
		CodeSystemVersionDto codeSystemVersionDto  =  codeSystemVersionMgmtHelper.createCodeSystemVersionDtoFromEntity(codeSystemVersion);
		codeSystemVersionDto.setCodeSystemName(selected.getName());
		
		return codeSystemVersionDto;
	}


	@Override
	@Transactional(rollbackFor = CodeSystemVersionNotFoundException.class)
	public CodeSystemVersionDto delete(Long codeSystemVersionId)
			throws CodeSystemVersionNotFoundException {
		LOGGER.debug("Deleting CodeSystemVersion with id: " + codeSystemVersionId);
		CodeSystemVersion deleted = codeSystemVersionRepository.findOne(codeSystemVersionId);
		if(deleted == null){
			LOGGER.debug("No CodeSystemVersion found with an id: " + codeSystemVersionId);
			throw new CodeSystemVersionNotFoundException();			
		}
		codeSystemVersionRepository.delete(deleted);
		return codeSystemVersionMgmtHelper.createCodeSystemVersionDtoFromEntity(deleted);
	}

	@Override
	public List<CodeSystemVersionDto> findAll() {
		LOGGER.debug("Finding all codeSystemVersions");
		List<CodeSystemVersion> codeSystemVersions = codeSystemVersionRepository.findAll();
		return groupByCodeSystem(codeSystemVersionMgmtHelper.convertCodeSystemVersionEntitiesToDtos(codeSystemVersions));
	}

	@Override
	public CodeSystemVersionDto findById(Long id) throws CodeSystemNotFoundException {
		LOGGER.debug("Finding a CodeSystemVersion with id: " + id);
		CodeSystemVersion codeSystemVersion = codeSystemVersionRepository
				.findOne(id);
		CodeSystemVersionDto codeSystemVersionDto = codeSystemVersionMgmtHelper
				.createCodeSystemVersionDtoFromEntity(codeSystemVersion);
		// Get all code systems
		List<CodeSystem> codeSystems = codeSystemRepository.findAll();

		if (codeSystems == null || codeSystems.size() == 0) {
			LOGGER.debug("No Code Systems found in the system");
			throw new CodeSystemNotFoundException(
					"No Code Systems found in the system");

		}
		codeSystemVersionDto.setCodeSystemMap(codeSystemVersionMgmtHelper
				.convertCodeSystemEntitiesToMap(codeSystems));

		return codeSystemVersionDto;
	}

	@Override
	@Transactional(rollbackFor = CodeSystemVersionNotFoundException.class)
	public CodeSystemVersionDto update(CodeSystemVersionDto updated)
			throws CodeSystemVersionNotFoundException, CodeSystemNotFoundException {
		LOGGER.debug("Updating CodeSystemVersion with information" + updated);
		
		CodeSystemVersion codeSystemVersion = codeSystemVersionRepository.findOne(updated.getId());
		if(codeSystemVersion == null) {
			LOGGER.debug("No CodeSystemVersion found with an id: " + updated.getId());
			throw new CodeSystemVersionNotFoundException();
		}
		
		codeSystemVersion.update(updated.getCode(), updated.getName(), updated.getDescription(),updated.getUserName());

		//code system association change
		Long selCodeSystemId = updated.getCodeSystemId();
		Long origCodeSystemId = codeSystemVersion.getCodeSystem().getId();
		
		if( (null!= selCodeSystemId && null != origCodeSystemId) && selCodeSystemId.equals(origCodeSystemId)){
			LOGGER.debug("category association already exists");
		} else{
			CodeSystem codeSystem =  codeSystemRepository.findOne(selCodeSystemId);
			if(codeSystem == null){
				LOGGER.debug("No Code System found for the selected id" + selCodeSystemId);
				throw new CodeSystemNotFoundException("No Code System found for the selected id" + selCodeSystemId);
				
			}	
			//save the association
			codeSystemVersion.setCodeSystem(codeSystem);
		}
		return codeSystemVersionMgmtHelper.createCodeSystemVersionDtoFromEntity(codeSystemVersion);
	}

    /**
     * This setter method should be used only by unit tests.
     * @param CodeSystemVersionRepository
     */
	protected void setCodeSystemVersionRepository(CodeSystemVersionRepository codeSystemVersionRepository) {
		this.codeSystemVersionRepository = codeSystemVersionRepository;
	}


	@Override
	public CodeSystemVersionCSDto create() throws CodeSystemNotFoundException {
		CodeSystemVersionCSDto codeSystemVersionVSCDto = new CodeSystemVersionCSDto();
		// Get all valuesets
		List<CodeSystem> codeSystems = codeSystemRepository.findAll();
		
		if(codeSystems == null || codeSystems.size() == 0){
			LOGGER.debug("No Code Systems found in the system");
			throw new CodeSystemNotFoundException();
			
		}
		codeSystemVersionVSCDto.setCodeSystemDtoMap(codeSystemVersionMgmtHelper
				.convertCodeSystemEntitiesToMap(codeSystems));
		return codeSystemVersionVSCDto;
	}
	
	@Override
	 public List<CodeSystemVersionDto> groupByCodeSystem(List<CodeSystemVersionDto> codeSystemVersionDtos){
		 Collections.sort(codeSystemVersionDtos,new Comparator<CodeSystemVersionDto>(){
			 public int compare(CodeSystemVersionDto cd1, CodeSystemVersionDto cd2){
				 return cd1.getCodeSystemId().compareTo(cd2.getCodeSystemId());
				 
			 }
		 });
		 
		 return codeSystemVersionDtos;
	 }
	

}