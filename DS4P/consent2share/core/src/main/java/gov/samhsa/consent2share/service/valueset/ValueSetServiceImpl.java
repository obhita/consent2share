package gov.samhsa.consent2share.service.valueset;

import gov.samhsa.consent2share.domain.valueset.ConceptCode;
import gov.samhsa.consent2share.domain.valueset.ConceptCodeValueSet;
import gov.samhsa.consent2share.domain.valueset.ConceptCodeValueSetRepository;
import gov.samhsa.consent2share.domain.valueset.ValueSet;
import gov.samhsa.consent2share.domain.valueset.ValueSetCategory;
import gov.samhsa.consent2share.domain.valueset.ValueSetCategoryRepository;
import gov.samhsa.consent2share.domain.valueset.ValueSetRepository;
import gov.samhsa.consent2share.service.dto.ConceptCodeDto;
import gov.samhsa.consent2share.service.dto.ValueSetDto;
import gov.samhsa.consent2share.service.dto.ValueSetVSCDto;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.POIXMLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * The Class ValueSetServiceImpl.
 */
@Service
public class ValueSetServiceImpl implements ValueSetService {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ValueSetService.class);

	/** The value set repository. */
	@Resource
	private ValueSetRepository valueSetRepository;
	
	/** The value set category repository. */
	@Resource
	private ValueSetCategoryRepository valueSetCategoryRepository;
	
	/** The concept code value set repository. */
	@Resource
	ConceptCodeValueSetRepository conceptCodeValueSetRepository;
	
	/** The value set mgmt helper. */
	@Autowired
	ValueSetMgmtHelper valueSetMgmtHelper;

	/** The Constant VALUE_SET_PAGE_SIZE. */
	private static final int VALUE_SET_PAGE_SIZE = 20;
	
	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.valueset.ValueSetService#create(gov.samhsa.consent2share.service.dto.ValueSetDto)
	 */
	@Override
	@Transactional
	public ValueSetDto create(ValueSetDto created) throws ValueSetCategoryNotFoundException {
		LOGGER.debug("Creating a new ValueSet with information: " + created);
		String description = (created.getDescription() != null) ? created.getDescription() : "";
		ValueSet valueSet = ValueSet.getBuilder(created.getCode(), created.getName(), created.getUserName()).description(description).build();
		// Step:1 Save the value set in the value_set table.
		valueSet = valueSetRepository.save(valueSet);
		
		// get the value set category entity from chosen value set cat id
		Long valueSetCategoryId = created.getValueSetCategoryId();

		ValueSetCategory selected = valueSetCategoryRepository.findOne(valueSetCategoryId);
		if (selected == null) {
			LOGGER.debug("No ValueSet category found with an id: " + valueSetCategoryId);
			throw new ValueSetCategoryNotFoundException();
		}
		
		// refere value set category id to the value set  entity object
		valueSet.setValueSetCategory(selected);
		ValueSetDto valueSetDto  =  valueSetMgmtHelper.createValuesetDtoFromEntity(valueSet);
		valueSetDto.setValueSetCatName(selected.getName());
		
		return valueSetDto;
	}


	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.valueset.ValueSetService#delete(java.lang.Long)
	 */
	@Override
	@Transactional(rollbackFor = ValueSetNotFoundException.class)
	public ValueSetDto delete(Long valueSetId)
			throws ValueSetNotFoundException {
		LOGGER.debug("Deleting ValueSet with id: " + valueSetId);
		ValueSet deleted = valueSetRepository.findOne(valueSetId);
		if(deleted == null){
			LOGGER.debug("No ValueSet found with an id: " + valueSetId);
			throw new ValueSetNotFoundException("No ValueSet found with an id: " + valueSetId);			
		}
		
		// is it eligible to delete
		// check if any concept codes associated to this version
		List<ConceptCodeValueSet> conceptCodeValueSets = deleted.getConceptCodes();	
		if(null != conceptCodeValueSets && conceptCodeValueSets.size() > 0) {
			LOGGER.debug("You can not delete a Value Set attached to an active Concept Code. " + valueSetId);
			throw new ValueSetNotFoundException("You can not delete a Value Set attached to an active Concept Code. " + valueSetId);			
		}		
		valueSetRepository.delete(deleted);
		return valueSetMgmtHelper.createValuesetDtoFromEntity(deleted);
	}


	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.valueset.ValueSetService#findById(java.lang.Long)
	 */
	@Override
	public ValueSetDto findById(Long id) throws ValueSetCategoryNotFoundException {
		LOGGER.debug("Finding a ValueSet with id: " + id);
		ValueSet valueSet = valueSetRepository.findOne(id);
		ValueSetDto valueSetDto = valueSetMgmtHelper.createValuesetDtoFromEntity(valueSet);
		// Get all valuesets
		List<ValueSetCategory> valueSetCategories = valueSetCategoryRepository.findAll();
		
		if(valueSetCategories == null || valueSetCategories.size() == 0){
			LOGGER.debug("No Valueset Categories found in the system");
			throw new ValueSetCategoryNotFoundException();
			
		}
		valueSetDto.setValueSetCategoryMap(valueSetMgmtHelper
				.convertValueSetCategoryEntitiesToMap(valueSetCategories));
		
		return valueSetDto;
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.valueset.ValueSetService#update(gov.samhsa.consent2share.service.dto.ValueSetDto)
	 */
	@Override
	@Transactional(rollbackFor = ValueSetNotFoundException.class)
	public ValueSetDto update(ValueSetDto updated)
			throws ValueSetNotFoundException, ValueSetCategoryNotFoundException {
		LOGGER.debug("Updating ValueSet with information" + updated);
		
		ValueSet valueSet = valueSetRepository.findOne(updated.getId());
		if(valueSet == null) {
			LOGGER.debug("No ValueSet found with an id: " + updated.getId());
			throw new ValueSetNotFoundException();
		}
		
		valueSet.update(updated.getCode(), updated.getName(), updated.getDescription(),updated.getUserName());
		
		// category association change
		Long selValSetId = updated.getValueSetCategoryId();
		Long origValSetId = valueSet.getValueSetCategory().getId();
		if( (null!= selValSetId && null != origValSetId) && selValSetId.equals(origValSetId)){
			LOGGER.debug("category association already exists");
		} else{
			ValueSetCategory valueSetCategory =  valueSetCategoryRepository.findOne(selValSetId);
			if(valueSetCategory == null){
				LOGGER.debug("No Valueset Category found for the selected id" + selValSetId);
				throw new ValueSetCategoryNotFoundException("No Valueset Category found for the selected id" + selValSetId);
				
			}	
			//save the association
			valueSet.setValueSetCategory(valueSetCategory);
		}
		
		return valueSetMgmtHelper.createValuesetDtoFromEntity(valueSet);
	}

    /**
     * This setter method should be used only by unit tests.
     *
     * @param valueSetRepository the new value set repository
     */
	protected void setValueSetRepository(ValueSetRepository valueSetRepository) {
		this.valueSetRepository = valueSetRepository;
	}


	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.valueset.ValueSetService#create()
	 */
	@Override
	public ValueSetVSCDto create() throws ValueSetCategoryNotFoundException {
		ValueSetVSCDto valueSetVSCDto = new ValueSetVSCDto();
		// Get all valueset categories
		List<ValueSetCategory> valueSetCategories = valueSetCategoryRepository.findAll();
		
		if(valueSetCategories == null || valueSetCategories.size() == 0){
			LOGGER.debug("No Valueset Categories found in the system");
			throw new ValueSetCategoryNotFoundException();
			
		}
		valueSetVSCDto.setValueSetCategoryMap(valueSetMgmtHelper
				.convertValueSetCategoryEntitiesToMap(valueSetCategories));
		return valueSetVSCDto;
		}


	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.valueset.ValueSetService#findAll()
	 */
	@Override
	public List<ValueSetDto> findAll() {		
		LOGGER.debug("Finding all valueSets");
		List<ValueSet> valueSets = valueSetRepository.findAll();
		return setDeletableToValueSetDto(valueSets);
	}	

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.valueset.ValueSetService#findAllWithoutDeletable()
	 */
	@Override
	public List<ValueSetDto> findAllWithoutDeletable() {		
		LOGGER.debug("Finding all valueSets");
		
    	List<ValueSet> valueSets = valueSetRepository.findAll();
		
    	return valueSetMgmtHelper.convertValueSetEntitiesToDtosWithoutDeletable(valueSets);
	}	
	
	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.valueset.ValueSetService#findAll(int)
	 */
	@Override
	public Map<String, Object> findAll(int pageNumber) {
		LOGGER.debug("Finding all valueSets with paging");
		Sort sort = new Sort(new Order(Direction.ASC, "code"));
		PageRequest pageRequest = new PageRequest(pageNumber, VALUE_SET_PAGE_SIZE, sort);
		
		Page<ValueSet> valueSets = valueSetRepository.findAll(pageRequest);
		
		Map<String, Object> pageResultsMap = new HashMap<String, Object>();
		pageResultsMap.put("valueSets", setDeletableToValueSetDto((valueSets.getContent())));
		pageResultsMap.put("totalNumberOfValueSets", valueSets.getTotalElements());
		pageResultsMap.put("totalPages", valueSets.getTotalPages());
		pageResultsMap.put("itemsPerPage", valueSets.getSize());
		pageResultsMap.put("currentPage", valueSets.getNumber());
		pageResultsMap.put("numberOfElements", valueSets.getNumberOfElements());
		
		return pageResultsMap;
	}
	
	
	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.valueset.ValueSetService#findAllByName(java.lang.String)
	 */
	@Override
	public Map<String, Object> findAllByName(String searchTerm, String valueSetCategory, int pageNumber){
		
		Sort sort = new Sort(new Order(Direction.ASC, "code"));
		PageRequest pageRequest = new PageRequest(pageNumber, VALUE_SET_PAGE_SIZE, sort);
		
		Page<ValueSet> pagedValueSets = valueSetRepository.findAllByNameLike("%"+searchTerm+"%", "%"+valueSetCategory+"%", pageRequest);
		
		Map<String, Object> pageResultsMap = new HashMap<String, Object>();
		pageResultsMap.put("valueSets", setDeletableToValueSetDto(pagedValueSets.getContent()));
		pageResultsMap.put("totalNumberOfValueSets", pagedValueSets.getTotalElements());
		pageResultsMap.put("totalPages", pagedValueSets.getTotalPages());
		pageResultsMap.put("itemsPerPage", pagedValueSets.getSize());
		pageResultsMap.put("currentPage", pagedValueSets.getNumber());
		pageResultsMap.put("numberOfElements", pagedValueSets.getNumberOfElements());
		
		return pageResultsMap;
		
	}


	protected List<ValueSetDto>  setDeletableToValueSetDto(List<ValueSet> valueSets) {
		List<ValueSetDto> valueSetDtos = valueSetMgmtHelper.convertValueSetEntitiesToDtos(valueSets);
		// setting deletable flag to each dto
		for(ValueSetDto valueSetDto : valueSetDtos){
			List<ConceptCodeValueSet> cValueSets = conceptCodeValueSetRepository.findAllByPkValueSetId(valueSetDto.getId());
			if(null != cValueSets && cValueSets.size() > 0){
				valueSetDto.setDeletable(false);
			}			
		}
		return valueSetDtos;
	}
	
	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.valueset.ValueSetService#findAllByCode(java.lang.String)
	 */
	@Override
	public Map<String, Object> findAllByCode(String searchTerm, String valueSetCategory, int pageNumber){
		
		Sort sort = new Sort(new Order(Direction.ASC, "code"));
		PageRequest pageRequest = new PageRequest(pageNumber, VALUE_SET_PAGE_SIZE, sort);
		
		Page<ValueSet> pagedValueSets = valueSetRepository.findAllByCodeLike("%"+searchTerm+"%", "%"+valueSetCategory+"%", pageRequest);
		
		Map<String, Object> pageResultsMap = new HashMap<String, Object>();
		pageResultsMap.put("valueSets", setDeletableToValueSetDto(pagedValueSets.getContent()));
		pageResultsMap.put("totalNumberOfValueSets", pagedValueSets.getTotalElements());
		pageResultsMap.put("totalPages", pagedValueSets.getTotalPages());
		pageResultsMap.put("itemsPerPage", pagedValueSets.getSize());
		pageResultsMap.put("currentPage", pagedValueSets.getNumber());
		pageResultsMap.put("numberOfElements", pagedValueSets.getNumberOfElements());
		
		return pageResultsMap;
	}


	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.valueset.ValueSetService#valueSetBatchUpload(gov.samhsa.consent2share.service.dto.ValueSetDto, org.springframework.web.multipart.MultipartFile)
	 */
	@Override
	@Transactional(rollbackFor = DataIntegrityViolationException.class)
	public ValueSetDto valueSetBatchUpload(ValueSetDto valueSetDto, MultipartFile file) throws IOException,
			ValueSetNotFoundException {

		String userName = valueSetDto.getUserName();
		int rowsUpdated = 0;
		int rowNum = 1;
		
		validateCSV(file);
		
		try {
			
			List<ValueSetDto> listOfValueSetsDtos = ValueSetMgmtHelper.readValueSetsFromFile(file, userName);

			for (int i = 0; i < listOfValueSetsDtos.size(); i++) {
				ValueSetDto batchValueSetDto = listOfValueSetsDtos.get(i);
				ValueSet valueSet = new ValueSet();

				ValueSetCategory valueSetCategory = valueSetCategoryRepository.findByName(batchValueSetDto.getValueSetCatName());

				if (valueSetCategory == null) {
					throw new ValueSetNotFoundException("Invalid Value Set Category for row: " + (i + 1));

				}

				valueSet.setUserName(userName);
				valueSet.setCode(batchValueSetDto.getCode());
				valueSet.setName(batchValueSetDto.getName());
				valueSet.setDescription(batchValueSetDto.getDescription());
				valueSet.setValueSetCategory(valueSetCategory);

				valueSetRepository.save(valueSet);
				rowsUpdated++;
				rowNum++;
			}

			valueSetDto.setRowsUpdated(rowsUpdated);
		} catch (DataIntegrityViolationException ex) {
			LOGGER.debug("Cannot add value set. There is an error with file at row: " + ex.getMessage());
			valueSetDto.setError(true);
			valueSetDto.setErrorMessage("Cannot add value set. There is an error with file at row: " + (rowNum + 1));
			throw ex;
		} catch (ValueSetNotFoundException ex) {
			LOGGER.debug("Missing required field while doing batch upload: " + ex.getMessage());
			valueSetDto.setError(true);
			valueSetDto.setErrorMessage(ex.getMessage());
			throw ex;
		} catch (InvalidCSVException ex) {
			valueSetDto.setError(true);
			valueSetDto.setErrorMessage(ex.getMessage());
			throw ex;
		} catch (POIXMLException ex) {
			LOGGER.debug("Incorrect file format: " + ex.getMessage());
			valueSetDto.setError(true);
			valueSetDto.setErrorMessage("Incorrect file format. File should be a correct .xslx file");
		} catch (IOException ex) {
			LOGGER.debug("Unable to open file: " + ex.getMessage());
			valueSetDto.setError(true);
			valueSetDto.setErrorMessage("Unable to open file");
		} catch (Exception ex) {
			LOGGER.debug("Exception thrown: " + ex.getMessage());
			valueSetDto.setError(true);
			valueSetDto.setErrorMessage("An error occurred. Please check with administrator: " + ex.getMessage());
		}

		return valueSetDto;
	}


	/**
	 * Validate csv.
	 *
	 * @param file the file
	 */
	private void validateCSV(MultipartFile file) {
		if (file.isEmpty() ) {
			throw new InvalidCSVException("File cannot be empty");
		}
	}

	
}
