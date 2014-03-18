package gov.samhsa.consent2share.service.valueset;

import gov.samhsa.consent2share.domain.valueset.CodeSystem;
import gov.samhsa.consent2share.domain.valueset.CodeSystemRepository;
import gov.samhsa.consent2share.service.dto.CodeSystemDto;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CodeSystemServiceImpl implements CodeSystemService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CodeSystemService.class);

	@Resource
	private CodeSystemRepository codeSystemRepository;
	
	@Autowired
	ValueSetMgmtHelper valueSetMgmtHelper;

	@Override
	@Transactional
	public CodeSystemDto create(CodeSystemDto created) {
		LOGGER.debug("Creating a new CodeSystem with information: " + created);
		String displayName = (created.getDisplayName() != null) ? created.getDisplayName() : "";
		CodeSystem codeSystem = CodeSystem.getBuilder(created.getCodeSystemOId(),
				created.getCode(), created.getName(), created.getUserName()).displayName(displayName).build();
		
		codeSystem = codeSystemRepository.save(codeSystem);
		
		return valueSetMgmtHelper.createCodeSystemDtoFromEntity(codeSystem);
	}


	@Override
	@Transactional(rollbackFor = CodeSystemNotFoundException.class)
	public CodeSystemDto delete(Long codeSystemId)
			throws CodeSystemNotFoundException {
		LOGGER.debug("Deleting CodeSystem with id: " + codeSystemId);
		CodeSystem deleted = codeSystemRepository.findOne(codeSystemId);
		if(deleted == null){
			LOGGER.debug("No CodeSystem found with an id: " + codeSystemId);
			throw new CodeSystemNotFoundException();			
		}
		codeSystemRepository.delete(deleted);
		return valueSetMgmtHelper.createCodeSystemDtoFromEntity(deleted);
	}

	@Override
	public List<CodeSystemDto> findAll() {
		LOGGER.debug("Finding all codeSystems");
		List<CodeSystem> codeSystems = codeSystemRepository.findAll();
		return valueSetMgmtHelper.convertCodeSystemEntitiesToDtos(codeSystems);
	}

	@Override
	public CodeSystemDto findById(Long id) {
		LOGGER.debug("Finding a CodeSystem with id: " + id);
		CodeSystem codeSystem = codeSystemRepository.findOne(id);
		return valueSetMgmtHelper.createCodeSystemDtoFromEntity(codeSystem);
	}

	@Override
	@Transactional(rollbackFor = CodeSystemNotFoundException.class)
	public CodeSystemDto update(CodeSystemDto updated)
			throws CodeSystemNotFoundException {
		LOGGER.debug("Updating CodeSystem with information" + updated);
		
		CodeSystem codeSystem = codeSystemRepository.findOne(updated.getId());
		if(codeSystem == null) {
			LOGGER.debug("No CodeSystem found with an id: " + updated.getId());
			throw new CodeSystemNotFoundException();
		}
		
		codeSystem.update(updated.getCodeSystemOId(),
				updated.getCode(), updated.getName(), updated.getDisplayName(),updated.getUserName());
		return valueSetMgmtHelper.createCodeSystemDtoFromEntity(codeSystem);
	}

    /**
     * This setter method should be used only by unit tests.
     * @param CodeSystemRepository
     */
	protected void setCodeSystemRepository(CodeSystemRepository codeSystemRepository) {
		this.codeSystemRepository = codeSystemRepository;
	}
	
	
	
}
