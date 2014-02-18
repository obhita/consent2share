package gov.samhsa.consent2share.service.valueset;

import gov.samhsa.consent2share.domain.valueset.ValueSetCategory;
import gov.samhsa.consent2share.domain.valueset.ValueSetCategoryRepository;
import gov.samhsa.consent2share.service.dto.ValueSetCategoryDto;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ValueSetCategoryServiceImpl implements ValueSetCategoryService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ValueSetCategoryService.class);

	@Resource
	private ValueSetCategoryRepository valueSetCategoryRepository;

	@Override
	@Transactional
	public ValueSetCategoryDto create(ValueSetCategoryDto created) {
		LOGGER.debug("Creating a new ValueSetCategory with information: " + created);
		String description = (created.getDescription() != null) ? created.getDescription() : "";
		ValueSetCategory valueSetCategory = ValueSetCategory.getBuilder(created.getCode(), created.getName(), created.getUserName()).description(description).build();
		valueSetCategory = valueSetCategoryRepository.save(valueSetCategory);
		return createDtoFromEntity(valueSetCategory);
	}

	@Transactional(rollbackFor = ValueSetCategoryNotFoundException.class)
	@Override
	public ValueSetCategoryDto delete(Long valueSetCategoryId)
			throws ValueSetCategoryNotFoundException {
		LOGGER.debug("Deleting ValueSetCategory with id: " + valueSetCategoryId);
		ValueSetCategory deleted = valueSetCategoryRepository.findOne(valueSetCategoryId);
		if(deleted == null){
			LOGGER.debug("No ValueSetCategory found with an id: " + valueSetCategoryId);
			throw new ValueSetCategoryNotFoundException();			
		}
		valueSetCategoryRepository.delete(deleted);
		return createDtoFromEntity(deleted);
	}

	@Override
	public List<ValueSetCategoryDto> findAll() {
		LOGGER.debug("Finding all valueSetCategories");
		List<ValueSetCategory> valueSetCategories = valueSetCategoryRepository.findAll();
		List<ValueSetCategoryDto> valueSetCategoryDtos = new ArrayList<ValueSetCategoryDto>();
		for(ValueSetCategory valueSetCategory : valueSetCategories){
			ValueSetCategoryDto valueSetCategoryDto = createDtoFromEntity(valueSetCategory);
			valueSetCategoryDtos.add(valueSetCategoryDto);			
		}		
		return valueSetCategoryDtos;
	}

	@Override
	public ValueSetCategoryDto findById(Long id) {
		LOGGER.debug("Finding a ValueSetCategory with id: " + id);
		ValueSetCategory valueSetCategory = valueSetCategoryRepository.findOne(id);
		return createDtoFromEntity(valueSetCategory);
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
		return createDtoFromEntity(valueSetCategory);
	}

    /**
     * This setter method should be used only by unit tests.
     * @param ValueSetCategoryRepository
     */
	protected void setValueSetCategoryRepository(ValueSetCategoryRepository valueSetCategoryRepository) {
		this.valueSetCategoryRepository = valueSetCategoryRepository;
	}
	
	private ValueSetCategoryDto createDtoFromEntity(ValueSetCategory valueSetCategory){		
		ValueSetCategoryDto valueSetCategoryDto = new ValueSetCategoryDto();
		valueSetCategoryDto.setCode(valueSetCategory.getCode());
		valueSetCategoryDto.setDescription(valueSetCategory.getDescription());
		valueSetCategoryDto.setName(valueSetCategory.getName());
		valueSetCategoryDto.setId(valueSetCategory.getId());		
		return valueSetCategoryDto;		
	}
}
