package gov.samhsa.consent2share.service.valueset;

import gov.samhsa.consent2share.service.dto.ConceptCodeDto;
import gov.samhsa.consent2share.service.dto.ConceptCodeVSCSDto;

import java.io.IOException;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional(readOnly = true)
public interface ConceptCodeService {

	/**
	  * Creates a new concept code.
	  * @param created   The information of the created concept code.
	  * @return  The created concept code.
	 * @throws ValueSetNotFoundException 
	 * @throws CodeSystemNotFoundException 
	 * @throws DuplicateConceptCodeException 
	  */	
	public ConceptCodeDto create(ConceptCodeDto created) throws ValueSetNotFoundException, CodeSystemNotFoundException, DuplicateConceptCodeException;
	

	 /**
	  * Deletes a ConceptCode.
	  * @param ConceptCodeId  The id of the deleted ConceptCode.
	  * @return  The deleted ConceptCode.
	  * @throws ConceptCodeNotFoundException  if no ConceptCode is found with the given id.
	  */
	 public ConceptCodeDto delete(Long conceptCodeId) throws ConceptCodeNotFoundException;

	 /**
	  * Finds all ConceptCodes.
	  * @return  A list of ConceptCodes.
	  */
	 public List<ConceptCodeDto> findAll();
	 
	 public List<ConceptCodeDto> findAllByCode(String searchTerm);
	 
	 public List<ConceptCodeDto> findAllByName(String searchTerm);
	 

	 /**
	  * Finds ConceptCode by id.
	  * @param id    The id of the wanted ConceptCode.
	  * @return  The found ConceptCode. If no ConceptCode is found, this method returns null.
	 * @throws ValueSetNotFoundException 
	 * @throws ConceptCodeNotFoundException 
	  */
	 public ConceptCodeDto findById(Long id) throws ValueSetNotFoundException, ConceptCodeNotFoundException;

	 /**
	  * Updates the information of a ConceptCode.
	  * @param updated   The information of the updated ConceptCode.
	  * @return  The updated ConceptCode.
	  * @throws ConceptCodeNotFoundException  if no ConceptCode is found with given id.
	 * @throws ValueSetNotFoundException 
	  */
	 public ConceptCodeDto update(ConceptCodeDto updated) throws ConceptCodeNotFoundException, ValueSetNotFoundException;

	 
	 public ConceptCodeVSCSDto create() throws ValueSetNotFoundException, CodeSystemVersionNotFoundException, CodeSystemNotFoundException;

	public ConceptCodeDto conceptCodeBatchUpload(ConceptCodeDto conceptCodeDto, MultipartFile file, String codeSystemId,
			Long codeSystemVersionId, List<Long> valueSetIds) throws IOException, ValueSetNotFoundException, CodeSystemNotFoundException;
			
}
