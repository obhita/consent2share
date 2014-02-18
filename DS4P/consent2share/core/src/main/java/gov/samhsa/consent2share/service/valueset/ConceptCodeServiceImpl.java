package gov.samhsa.consent2share.service.valueset;

import gov.samhsa.consent2share.domain.valueset.ConceptCode;
import gov.samhsa.consent2share.domain.valueset.ConceptCodeRepository;
import gov.samhsa.consent2share.domain.valueset.ConceptCodeValueSetVersionMembershipRepository;
import gov.samhsa.consent2share.domain.valueset.ValueSet;
import gov.samhsa.consent2share.domain.valueset.ValueSetRepository;
import gov.samhsa.consent2share.domain.valueset.ValueSetVersion;
import gov.samhsa.consent2share.domain.valueset.ValueSetVersionRepository;
import gov.samhsa.consent2share.service.dto.ConceptCodeDto;
import gov.samhsa.consent2share.service.dto.ConceptCodeVSCSDto;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConceptCodeServiceImpl implements ConceptCodeService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ConceptCodeService.class);

	@Resource
	private ConceptCodeRepository conceptCodeRepository;

	@Resource
	private ValueSetRepository valueSetRepository;

	@Resource
	ValueSetVersionRepository valueSetVersionRepository;

	@Resource
	ConceptCodeValueSetVersionMembershipRepository cCodeVSVersionRepository;

	@Autowired
	ValueSetMgmtHelper valueSetMgmtHelper;

	@Override
	@Transactional
	public ConceptCodeDto create(ConceptCodeDto created)
			throws ValueSetNotFoundException {
		LOGGER.debug("Creating a new ConceptCode with information: " + created);
		String description = (created.getDescription() != null) ? created
				.getDescription() : "";
		ConceptCode conceptCode = ConceptCode
				.getBuilder(created.getCode(), created.getName(),
						created.getUserName()).description(description).build();

		// Step:1 Save the code in the concept_code table.
		conceptCode = conceptCodeRepository.save(conceptCode);

		// Step:2 insert a row in valueset_version for the selected value set
		Long valueSetId = created.getValueSetId();

		// get the value set entity from chosen value set id
		ValueSet selected = valueSetRepository.findOne(valueSetId);
		if (selected == null) {
			LOGGER.debug("No ValueSet found with an id: " + valueSetId);
			throw new ValueSetNotFoundException();
		}
		// Create valueSetVersion entity object
		ValueSetVersion valueSetVersion = ValueSetVersion.getBuilder(selected,
				created.getUserName()).build();
		valueSetVersionRepository.save(valueSetVersion);

		// Step:3 create a mapping between code and Value set version
/*		ConceptCodeValueSetVersionMembership cCodeVSVersionM = ConceptCodeValueSetVersionMembership
				.getBuilder(conceptCode, valueSetVersion,
						valueSetVersion.getUploadEffectiveDate(),
						valueSetVersion.getUserName()).build();
		cCodeVSVersionRepository.save(cCodeVSVersionM);*/
		
		

		return valueSetMgmtHelper.createConceptCodeDtoFromEntity(conceptCode);
	}

	@Override
	@Transactional(rollbackFor = ConceptCodeNotFoundException.class)
	public ConceptCodeDto delete(Long conceptCodeId)
			throws ConceptCodeNotFoundException {
		LOGGER.debug("Deleting ConceptCode with id: " + conceptCodeId);
		ConceptCode deleted = conceptCodeRepository.findOne(conceptCodeId);
		if (deleted == null) {
			LOGGER.debug("No ConceptCode found with an id: " + conceptCodeId);
			throw new ConceptCodeNotFoundException();
		}
		conceptCodeRepository.delete(deleted);
		return valueSetMgmtHelper.createConceptCodeDtoFromEntity(deleted);
	}

	@Override
	public List<ConceptCodeDto> findAll() {
		LOGGER.debug("Finding all conceptCodes");
		List<ConceptCode> conceptCodes = conceptCodeRepository.findAll();
		return valueSetMgmtHelper
				.convertConceptCodeEntitiesToDtos(conceptCodes);
	}

	@Override
	public ConceptCodeDto findById(Long id) {
		LOGGER.debug("Finding a ConceptCode with id: " + id);
		ConceptCode conceptCode = conceptCodeRepository.findOne(id);
		return valueSetMgmtHelper.createConceptCodeDtoFromEntity(conceptCode);
	}

	@Override
	@Transactional(rollbackFor = ConceptCodeNotFoundException.class)
	public ConceptCodeDto update(ConceptCodeDto updated)
			throws ConceptCodeNotFoundException {
		LOGGER.debug("Updating ConceptCode with information" + updated);

		ConceptCode conceptCode = conceptCodeRepository
				.findOne(updated.getId());
		if (conceptCode == null) {
			LOGGER.debug("No ConceptCode found with an id: " + updated.getId());
			throw new ConceptCodeNotFoundException();
		}

		conceptCode.update(updated.getCode(), updated.getName(),
				updated.getDescription(), updated.getUserName());
		return valueSetMgmtHelper.createConceptCodeDtoFromEntity(conceptCode);
	}

	/**
	 * This setter method should be used only by unit tests.
	 * 
	 * @param ConceptCodeRepository
	 */
	protected void setConceptCodeRepository(
			ConceptCodeRepository conceptCodeRepository) {
		this.conceptCodeRepository = conceptCodeRepository;
	}

	@Override
	public ConceptCodeVSCSDto create() {
		ConceptCodeVSCSDto conceptCodeVSCSDto = new ConceptCodeVSCSDto();
		// Get all valuesets
		List<ValueSet> valueSets = valueSetRepository.findAll();
		conceptCodeVSCSDto.setValueSetsMap(valueSetMgmtHelper
				.convertValueSetEntitiesToMap(valueSets));
		return conceptCodeVSCSDto;
	}

}
