package gov.samhsa.consent2share.service.valueset;

import gov.samhsa.consent2share.domain.valueset.ValueSet;
import gov.samhsa.consent2share.domain.valueset.ValueSetRepository;
import gov.samhsa.consent2share.service.dto.ValueSetDto;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ValueSetServiceImpl implements ValueSetService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ValueSetService.class);

	@Resource
	private ValueSetRepository valueSetRepository;
	
	@Autowired
	ValueSetMgmtHelper valueSetMgmtHelper;

	@Override
	@Transactional
	public ValueSetDto create(ValueSetDto created) {
		LOGGER.debug("Creating a new ValueSet with information: " + created);
		String description = (created.getDescription() != null) ? created.getDescription() : "";
		ValueSet valueSet = ValueSet.getBuilder(created.getCode(), created.getName(), created.getUserName()).description(description).build();
		valueSet = valueSetRepository.save(valueSet);
		return valueSetMgmtHelper.createValuesetDtoFromEntity(valueSet);
	}


	@Override
	@Transactional(rollbackFor = ValueSetNotFoundException.class)
	public ValueSetDto delete(Long valueSetId)
			throws ValueSetNotFoundException {
		LOGGER.debug("Deleting ValueSet with id: " + valueSetId);
		ValueSet deleted = valueSetRepository.findOne(valueSetId);
		if(deleted == null){
			LOGGER.debug("No ValueSet found with an id: " + valueSetId);
			throw new ValueSetNotFoundException();			
		}
		valueSetRepository.delete(deleted);
		return valueSetMgmtHelper.createValuesetDtoFromEntity(deleted);
	}

	@Override
	public List<ValueSetDto> findAll() {
		LOGGER.debug("Finding all valueSets");
		List<ValueSet> valueSets = valueSetRepository.findAll();
		List<ValueSetDto> valueSetDtos = valueSetMgmtHelper.convertValueSetEntitiesToDtos(valueSets);
		return valueSetDtos;
	}

	@Override
	public ValueSetDto findById(Long id) {
		LOGGER.debug("Finding a ValueSet with id: " + id);
		ValueSet valueSet = valueSetRepository.findOne(id);
		return valueSetMgmtHelper.createValuesetDtoFromEntity(valueSet);
	}

	@Override
	@Transactional(rollbackFor = ValueSetNotFoundException.class)
	public ValueSetDto update(ValueSetDto updated)
			throws ValueSetNotFoundException {
		LOGGER.debug("Updating ValueSet with information" + updated);
		
		ValueSet valueSet = valueSetRepository.findOne(updated.getId());
		if(valueSet == null) {
			LOGGER.debug("No ValueSet found with an id: " + updated.getId());
			throw new ValueSetNotFoundException();
		}
		
		valueSet.update(updated.getCode(), updated.getName(), updated.getDescription(),updated.getUserName());
		return valueSetMgmtHelper.createValuesetDtoFromEntity(valueSet);
	}

    /**
     * This setter method should be used only by unit tests.
     * @param ValueSetRepository
     */
	protected void setValueSetRepository(ValueSetRepository valueSetRepository) {
		this.valueSetRepository = valueSetRepository;
	}

	
}
