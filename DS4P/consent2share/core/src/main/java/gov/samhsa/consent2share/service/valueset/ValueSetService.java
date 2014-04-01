package gov.samhsa.consent2share.service.valueset;

import gov.samhsa.consent2share.service.dto.ValueSetDto;
import gov.samhsa.consent2share.service.dto.ValueSetVSCDto;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

// TODO: Auto-generated Javadoc
/**
 * The Interface ValueSetService.
 */
@Transactional(readOnly = true)
public interface ValueSetService {
	
/**
 * Creates a new person.
 *
 * @param created   The information of the created person.
 * @return  The created person.
 * @throws ValueSetCategoryNotFoundException the value set category not found exception
 */
 public ValueSetDto create(ValueSetDto created) throws ValueSetCategoryNotFoundException;

 /**
  * Deletes a ValueSet.
  *
  * @param valueSetId the value set id
  * @return  The deleted ValueSet.
  * @throws ValueSetNotFoundException  if no ValueSet is found with the given id.
  */
 public ValueSetDto delete(Long valueSetId) throws ValueSetNotFoundException;

 /**
  * Finds all ValueSets.
  * @return  A list of ValueSets.
  */
 public List<ValueSetDto> findAll();

 /**
  * Find all by page number.
  *
  * @param pageNumber the page number
  * @return the list
  */
 public List<ValueSetDto> findAll(int pageNumber);
 
 /**
  * Finds ValueSet by id.
  * @param id    The id of the wanted ValueSet.
  * @return  The found ValueSet. If no ValueSet is found, this method returns null.
 * @throws ValueSetCategoryNotFoundException 
  */
 public ValueSetDto findById(Long id) throws ValueSetCategoryNotFoundException;

 /**
  * Updates the information of a ValueSet.
  * @param updated   The information of the updated ValueSet.
  * @return  The updated ValueSet.
  * @throws ValueSetNotFoundException  if no ValueSet is found with given id.
 * @throws ValueSetCategoryNotFoundException 
  */
 public ValueSetDto update(ValueSetDto updated) throws ValueSetNotFoundException, ValueSetCategoryNotFoundException;

	
 /**
  * Creates a new person.
  *
  * @return  The created person.
  * @throws ValueSetCategoryNotFoundException the value set category not found exception
  */
 public ValueSetVSCDto create() throws ValueSetCategoryNotFoundException;
 

 /**
  * Find by name.
  *
  * @param searchTerm the search term
  * @return the list
  */
 public List<ValueSetDto> findAllByName(String searchTerm);
 
 /**
  * Find by code.
  *
  * @param searchTerm the search term
  * @return the list
  */
 public List<ValueSetDto> findAllByCode(String searchTerm);
	
	/**
	 * Value set batch upload.
	 *
	 * @param valueSetDto the value set dto
	 * @param file            the bytes
	 * @return the value set dto
	 * @throws Exception the exception
	 */
	public ValueSetDto valueSetBatchUpload(ValueSetDto valueSetDto, MultipartFile file) throws Exception;

}
