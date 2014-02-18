package gov.samhsa.consent2share.service.valueset;

import gov.samhsa.consent2share.domain.valueset.CodeSystem;
import gov.samhsa.consent2share.domain.valueset.CodeSystemRepository;
import gov.samhsa.consent2share.service.dto.CodeSystemDto;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CodeSystemServiceImpl implements CodeSystemService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CodeSystemService.class);

	@Resource
	private CodeSystemRepository codeSystemRepository;

	@Override
	@Transactional
	public CodeSystemDto create(CodeSystemDto created) {
		LOGGER.debug("Creating a new CodeSystem with information: " + created);
		String displayName = (created.getDisplayName() != null) ? created.getDisplayName() : "";
		CodeSystem codeSystem = CodeSystem.getBuilder(created.getCodeSystemOId(),
				created.getCode(), created.getName(), created.getUserName()).displayName(displayName).build();
		
		// step:1 Insert concept code into conceptcode table
		codeSystem = codeSystemRepository.save(codeSystem);
		
		// step:2  concept code with valueset mapping
		
		
		
		
		
		return createDtoFromEntity(codeSystem);
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
		return createDtoFromEntity(deleted);
	}

	@Override
	public List<CodeSystemDto> findAll() {
		LOGGER.debug("Finding all codeSystems");
		List<CodeSystem> codeSystems = codeSystemRepository.findAll();
		List<CodeSystemDto> codeSystemDtos = new ArrayList<CodeSystemDto>();
		for(CodeSystem codeSystem : codeSystems){
			CodeSystemDto codeSystemDto = createDtoFromEntity(codeSystem);
			codeSystemDtos.add(codeSystemDto);			
		}		
		return codeSystemDtos;
	}

	@Override
	public CodeSystemDto findById(Long id) {
		LOGGER.debug("Finding a CodeSystem with id: " + id);
		CodeSystem codeSystem = codeSystemRepository.findOne(id);
		return createDtoFromEntity(codeSystem);
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
		return createDtoFromEntity(codeSystem);
	}

    /**
     * This setter method should be used only by unit tests.
     * @param CodeSystemRepository
     */
	protected void setCodeSystemRepository(CodeSystemRepository codeSystemRepository) {
		this.codeSystemRepository = codeSystemRepository;
	}
	
	
	private CodeSystemDto createDtoFromEntity(CodeSystem codeSystem){		
		CodeSystemDto codeSystemDto = new CodeSystemDto();
		codeSystemDto.setCode(codeSystem.getCode());
		codeSystemDto.setCodeSystemOId(codeSystem.getCodeSystemOId());
		codeSystemDto.setDisplayName(codeSystem.getDisplayName());
		codeSystemDto.setName(codeSystem.getName());
		codeSystemDto.setId(codeSystem.getId());		
		return codeSystemDto;		
	}
	
	
	
	
	
}
