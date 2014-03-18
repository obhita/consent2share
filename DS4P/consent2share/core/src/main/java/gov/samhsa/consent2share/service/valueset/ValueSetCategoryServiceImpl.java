package gov.samhsa.consent2share.service.valueset;

import gov.samhsa.consent2share.domain.valueset.ValueSetCategory;
import gov.samhsa.consent2share.domain.valueset.ValueSetCategoryRepository;
import gov.samhsa.consent2share.service.dto.AddConsentFieldsDto;
import gov.samhsa.consent2share.service.dto.ValueSetCategoryDto;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ValueSetCategoryServiceImpl implements ValueSetCategoryService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ValueSetCategoryService.class);

	@Resource
	private ValueSetCategoryRepository valueSetCategoryRepository;
	
	@Autowired
	ValueSetMgmtHelper valueSetMgmtHelper;

	@Override
	@Transactional
	public ValueSetCategoryDto create(ValueSetCategoryDto created) {
		LOGGER.debug("Creating a new ValueSetCategory with information: " + created);
		String description = (created.getDescription() != null) ? created.getDescription() : "";
		ValueSetCategory valueSetCategory = ValueSetCategory.getBuilder(created.getCode(), created.getName(), created.getUserName()).description(description).build();
		valueSetCategory = valueSetCategoryRepository.save(valueSetCategory);
		return valueSetMgmtHelper.createValuesetCategoryDtoFromEntity(valueSetCategory);
	}

	@Override
	@Transactional(rollbackFor = ValueSetCategoryNotFoundException.class)
	public ValueSetCategoryDto delete(Long valueSetCategoryId)
			throws ValueSetCategoryNotFoundException {
		LOGGER.debug("Deleting ValueSetCategory with id: " + valueSetCategoryId);
		ValueSetCategory deleted = valueSetCategoryRepository.findOne(valueSetCategoryId);
		if(deleted == null){
			LOGGER.debug("No ValueSetCategory found with an id: " + valueSetCategoryId);
			throw new ValueSetCategoryNotFoundException();			
		}
		valueSetCategoryRepository.delete(deleted);
		return valueSetMgmtHelper.createValuesetCategoryDtoFromEntity(deleted);
	}

	@Override
	public List<ValueSetCategoryDto> findAll() {
		LOGGER.debug("Finding all valueSetCategories");
		List<ValueSetCategory> valueSetCategories = valueSetCategoryRepository.findAll();
		return valueSetMgmtHelper.convertValueSetCategoryEntitiesToDtos(valueSetCategories);
	}

	@Override
	public ValueSetCategoryDto findById(Long id) {
		LOGGER.debug("Finding a ValueSetCategory with id: " + id);
		ValueSetCategory valueSetCategory = valueSetCategoryRepository.findOne(id);
		return valueSetMgmtHelper.createValuesetCategoryDtoFromEntity(valueSetCategory);
	}

	@Override
	@Transactional(rollbackFor = ValueSetCategoryNotFoundException.class)
	public ValueSetCategoryDto update(ValueSetCategoryDto updated)
			throws ValueSetCategoryNotFoundException {
		LOGGER.debug("Updating ValueSetCategory with information" + updated);
		
		ValueSetCategory valueSetCategory = valueSetCategoryRepository.findOne(updated.getId());
		if(valueSetCategory == null) {
			LOGGER.debug("No ValueSetCategory found with an id: " + updated.getId());
			throw new ValueSetCategoryNotFoundException();
		}
		
		valueSetCategory.update(updated.getCode(), updated.getName(), updated.getDescription(),updated.getUserName());
		return valueSetMgmtHelper.createValuesetCategoryDtoFromEntity(valueSetCategory);
	}
	
	@Override
	public List<AddConsentFieldsDto> findAllValueSetCategoriesAddConsentFieldsDto() {
		List<ValueSetCategory> valueSetCategoryList = valueSetCategoryRepository.findAll();
		List<AddConsentFieldsDto> sensitivityPolicyDto = new ArrayList<AddConsentFieldsDto>();
		for (ValueSetCategory valueSetCategory : valueSetCategoryList) {
			AddConsentFieldsDto sensitivityPolicyDtoItem = new AddConsentFieldsDto();
			sensitivityPolicyDtoItem.setCode(valueSetCategory.getCode());
			sensitivityPolicyDtoItem.setDisplayName(valueSetCategory.getName());
			sensitivityPolicyDtoItem.setDescription(valueSetCategory.getDescription());
			sensitivityPolicyDto.add(sensitivityPolicyDtoItem);
		}
		return sensitivityPolicyDto;
	}

    /**
     * This setter method should be used only by unit tests.
     * @param ValueSetCategoryRepository
     */
	protected void setValueSetCategoryRepository(ValueSetCategoryRepository valueSetCategoryRepository) {
		this.valueSetCategoryRepository = valueSetCategoryRepository;
	}
	
}
