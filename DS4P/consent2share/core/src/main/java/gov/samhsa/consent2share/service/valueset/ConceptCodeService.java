package gov.samhsa.consent2share.service.valueset;

import gov.samhsa.consent2share.service.dto.ConceptCodeDto;
import gov.samhsa.consent2share.service.dto.ConceptCodeVSCSDto;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ConceptCodeService {

	/**
	  * Creates a new concept code.
	  * @param created   The information of the created concept code.
	  * @return  The created concept code.
	 * @throws ValueSetNotFoundException 
	  */	
	public ConceptCodeDto create(ConceptCodeDto created) throws ValueSetNotFoundException;
	

	 /**
	  * Deletes a ConceptCode.
	  * @param ConceptCodeId  The id of the deleted ConceptCode.
	  * @return  The deleted ConceptCode.
	  * @throws ConceptCodeNotFoundException  if no ConceptCode is found with the given id.
	  */
	 public ConceptCodeDto delete(Long valueSetId) throws ConceptCodeNotFoundException;

	 /**
	  * Finds all ConceptCodes.
	  * @return  A list of ConceptCodes.
	  */
	 public List<ConceptCodeDto> findAll();

	 /**
	  * Finds ConceptCode by id.
	  * @param id    The id of the wanted ConceptCode.
	  * @return  The found ConceptCode. If no ConceptCode is found, this method returns null.
	  */
	 public ConceptCodeDto findById(Long id);

	 /**
	  * Updates the information of a ConceptCode.
	  * @param updated   The information of the updated ConceptCode.
	  * @return  The updated ConceptCode.
	  * @throws ConceptCodeNotFoundException  if no ConceptCode is found with given id.
	  */
	 public ConceptCodeDto update(ConceptCodeDto updated) throws ConceptCodeNotFoundException;

	 
	 public ConceptCodeVSCSDto create();
			
}
