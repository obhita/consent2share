package gov.samhsa.consent2share.service.valueset;

import gov.samhsa.consent2share.service.dto.ValueSetDto;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ValueSetService {
	
/**
  * Creates a new person.
  * @param created   The information of the created person.
  * @return  The created person.
  */
 public ValueSetDto create(ValueSetDto created);

 /**
  * Deletes a ValueSet.
  * @param ValueSetId  The id of the deleted ValueSet.
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
  * Finds ValueSet by id.
  * @param id    The id of the wanted ValueSet.
  * @return  The found ValueSet. If no ValueSet is found, this method returns null.
  */
 public ValueSetDto findById(Long id);

 /**
  * Updates the information of a ValueSet.
  * @param updated   The information of the updated ValueSet.
  * @return  The updated ValueSet.
  * @throws ValueSetNotFoundException  if no ValueSet is found with given id.
  */
 public ValueSetDto update(ValueSetDto updated) throws ValueSetNotFoundException;

	
	

}
