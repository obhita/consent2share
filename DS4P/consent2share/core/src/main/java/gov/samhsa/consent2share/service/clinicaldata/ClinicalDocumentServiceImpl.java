/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.consent2share.service.clinicaldata;

import gov.samhsa.consent2share.domain.clinicaldata.ClinicalDocument;
import gov.samhsa.consent2share.domain.clinicaldata.ClinicalDocumentRepository;
import gov.samhsa.consent2share.domain.patient.Patient;
import gov.samhsa.consent2share.domain.patient.PatientRepository;
import gov.samhsa.consent2share.domain.reference.ClinicalDocumentTypeCodeRepository;
import gov.samhsa.consent2share.infrastructure.security.AuthenticatedUser;
import gov.samhsa.consent2share.infrastructure.security.UserContext;
import gov.samhsa.consent2share.service.dto.ClinicalDocumentDto;
import gov.samhsa.consent2share.service.dto.LookupDto;
import gov.samhsa.consent2share.service.dto.PatientProfileDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

/**
 * The Class ClinicalDocumentServiceImpl.
 */
@Service
@Transactional
public class ClinicalDocumentServiceImpl implements ClinicalDocumentService, InitializingBean{

	/** The clinical document repository. */
	@Autowired
	ClinicalDocumentRepository clinicalDocumentRepository;

	/** The clinical document type code repository. */
	@Autowired
	ClinicalDocumentTypeCodeRepository clinicalDocumentTypeCodeRepository;

	/** The model mapper. */
	@Autowired
	ModelMapper modelMapper;

	/** The patient repository. */
	@Autowired
	PatientRepository patientRepository;

	/** The user context. */
	@Autowired
	UserContext userContext;
	
	@Autowired
	Validator validator;
	
	@Value("${maximum_upload_file_size}")
    private Long maxFileSize;
	
	@Value("${extensions_permitted_to_upload}")
	private String permittedExtensions;
	
	private String[] permittedExtensionsArray;

	
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	
	public ClinicalDocumentServiceImpl(
			ClinicalDocumentRepository clinicalDocumentRepository,
			ClinicalDocumentTypeCodeRepository clinicalDocumentTypeCodeRepository,
			ModelMapper modelMapper, PatientRepository patientRepository,
			UserContext userContext, Validator validator, Long maxFileSize,
			String permittedExtensions, String[] permittedExtensionsArray) {
		super();
		this.clinicalDocumentRepository = clinicalDocumentRepository;
		this.clinicalDocumentTypeCodeRepository = clinicalDocumentTypeCodeRepository;
		this.modelMapper = modelMapper;
		this.patientRepository = patientRepository;
		this.userContext = userContext;
		this.validator = validator;
		this.maxFileSize = maxFileSize;
		this.permittedExtensions = permittedExtensions;
		this.permittedExtensionsArray = permittedExtensionsArray;
	}
	
	@SuppressWarnings("unused")
	private ClinicalDocumentServiceImpl() {}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		permittedExtensionsArray=permittedExtensions.split(",");
		
	}
	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.clinicaldata.ClinicalDocumentService#countAllClinicalDocuments()
	 */
	public long countAllClinicalDocuments() {
		return clinicalDocumentRepository.count();
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.clinicaldata.ClinicalDocumentService#deleteClinicalDocument(gov.samhsa.consent2share.service.dto.ClinicalDocumentDto)
	 */
	public void deleteClinicalDocument(ClinicalDocumentDto clinicalDocumentDto) {
		ClinicalDocument clinicalDocument = getClinicalDocumenFromDto(clinicalDocumentDto);
		clinicalDocumentRepository.delete(clinicalDocument);
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.clinicaldata.ClinicalDocumentService#findClinicalDocument(java.lang.Long)
	 */
	public ClinicalDocument findClinicalDocument(Long id) {
		return clinicalDocumentRepository.findOne(id);
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.clinicaldata.ClinicalDocumentService#findAllClinicalDocuments()
	 */
	public List<ClinicalDocument> findAllClinicalDocuments() {
		return clinicalDocumentRepository.findAll();
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.clinicaldata.ClinicalDocumentService#findClinicalDocumentEntries(int, int)
	 */
	public List<ClinicalDocument> findClinicalDocumentEntries(int firstResult,
			int maxResults) {
		return clinicalDocumentRepository.findAll(
				new org.springframework.data.domain.PageRequest(firstResult
						/ maxResults, maxResults)).getContent();
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.clinicaldata.ClinicalDocumentService#saveClinicalDocument(gov.samhsa.consent2share.service.dto.ClinicalDocumentDto)
	 */
	public void saveClinicalDocument(ClinicalDocumentDto clinicalDocumentDto) {
			if (validateClinicalDocumentDtoFields(clinicalDocumentDto)==true) {
				ClinicalDocument clinicalDocument = getClinicalDocumenFromDto(clinicalDocumentDto);
				clinicalDocumentRepository.save(clinicalDocument);
			}
	}
	
	boolean validateClinicalDocumentDtoFields(ClinicalDocumentDto clinicalDocumentDto) {
		Set<ConstraintViolation<ClinicalDocumentDto>> violations = validator.validate(clinicalDocumentDto);
		if(violations.isEmpty()==true)
			return true;
		throw new ConstraintViolationException("Field validation error.", new HashSet<ConstraintViolation<?>>(violations));
	}

	/**
	 * Gets the clinical document from dto.
	 *
	 * @param clinicalDocumentDto the clinical document dto
	 * @return the clinical document from dto
	 */
	private ClinicalDocument getClinicalDocumenFromDto(
			ClinicalDocumentDto clinicalDocumentDto) {

		// required fields
		ClinicalDocument clinicalDocument = new ClinicalDocument();
		if(clinicalDocumentDto.getId()!=null)
			clinicalDocument.setId(Long.parseLong(clinicalDocumentDto.getId()));
		clinicalDocument.setName(clinicalDocumentDto.getName());
		clinicalDocument.setFilename(clinicalDocumentDto.getFilename());
		clinicalDocument.setContent(clinicalDocumentDto.getContent());
		clinicalDocument.setContentType(clinicalDocumentDto.getContentType());
		clinicalDocument.setDocumentSize(clinicalDocumentDto.getDocumentSize());

		// optional fields
		if (clinicalDocumentDto.getClinicalDocumentTypeCode() != null) {
			clinicalDocument
					.setClinicalDocumentTypeCode(clinicalDocumentTypeCodeRepository
							.findByCode(clinicalDocumentDto
									.getClinicalDocumentTypeCode().getCode()));
		} else {
			clinicalDocument.setClinicalDocumentTypeCode(null);
		}

		if (StringUtils.hasText(clinicalDocumentDto.getDescription())) {
			clinicalDocument.setDescription(clinicalDocumentDto
					.getDescription());
		} else {
			clinicalDocument.setDescription(null);
		}

		if (StringUtils.hasText(clinicalDocumentDto.getDocumentUrl())) {
			clinicalDocument.setDocumentUrl(clinicalDocumentDto
					.getDocumentUrl());
		} else {
			clinicalDocument.setDocumentUrl(null);
		}

		if (patientRepository.findOne(clinicalDocumentDto.getPatientId()) != null) {
			clinicalDocument.setPatient(patientRepository
					.findOne(clinicalDocumentDto.getPatientId()));
		} else {
			clinicalDocument.setPatient(null);
		}

		if (clinicalDocumentDto.getVersion() != null) {
			clinicalDocument.setVersion(clinicalDocumentDto.getVersion());
		} else {
			clinicalDocument.setVersion(null);
		}

		return clinicalDocument;
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.clinicaldata.ClinicalDocumentService#updateClinicalDocument(gov.samhsa.consent2share.domain.clinicaldata.ClinicalDocument)
	 */
	public ClinicalDocument updateClinicalDocument(
			ClinicalDocument clinicalDocument) {
		return clinicalDocumentRepository.save(clinicalDocument);
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.clinicaldata.ClinicalDocumentService#findClinicalDocumentDto(long)
	 */
	public ClinicalDocumentDto findClinicalDocumentDto(long documentId) {
		ClinicalDocument clinicalDocument = findClinicalDocument(documentId);
		ClinicalDocumentDto clinicalDocumentDto = modelMapper.map(
				clinicalDocument, ClinicalDocumentDto.class);

		// manual mapping without using model mapper
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		String username = currentUser.getUsername();
		clinicalDocumentDto.setPatientId(patientRepository.findByUsername(
				username).getId());
		return clinicalDocumentDto;
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.clinicaldata.ClinicalDocumentService#findByPatient(gov.samhsa.consent2share.domain.patient.Patient)
	 */
	public List<ClinicalDocument> findByPatient(Patient patient) {
		return clinicalDocumentRepository.findByPatientId(patient.getId());
	}
	
	/**
	 * Find clinical documents by patient Id
	 */
	public List<ClinicalDocument> findByPatientId(long patientId) {
		return clinicalDocumentRepository.findByPatientId(patientId);
	}
	
	/**
	 * Find dto by patient.
	 *
	 * @param patient the patient
	 * @return the list
	 */
	List<ClinicalDocumentDto> findDtoByPatient(Patient patient) {
		List<ClinicalDocument> documents = clinicalDocumentRepository
				.findByPatientId(patient.getId());
		List<ClinicalDocumentDto> dtos = new ArrayList<ClinicalDocumentDto>();
		for (ClinicalDocument doc : documents) {
			ClinicalDocumentDto clinicalDocumentDto = modelMapper.map(doc,
					ClinicalDocumentDto.class);

			// manual mapping without using model mapper
			clinicalDocumentDto.setPatientId(patient.getId());
			dtos.add(clinicalDocumentDto);
		}

		return dtos;
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.clinicaldata.ClinicalDocumentService#findDtoByPatientDto(gov.samhsa.consent2share.service.dto.PatientProfileDto)
	 */
	@Override

	public List<ClinicalDocumentDto> findDtoByPatientDto(
			PatientProfileDto patientDto) {
		Patient patient = patientRepository.findByUsername(patientDto
				.getUsername());
		List<ClinicalDocumentDto> dtos = findDtoByPatient(patient);
		for(ClinicalDocumentDto dto : dtos) {
        	if(dto.getClinicalDocumentTypeCode()==null) {
        		LookupDto typeCode = new LookupDto();
        		typeCode.setDisplayName("Not Specified");
        		dto.setClinicalDocumentTypeCode(typeCode);
        	}
        }
		return dtos;
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.clinicaldata.ClinicalDocumentService#isDocumentBelongsToThisUser(gov.samhsa.consent2share.service.dto.ClinicalDocumentDto)
	 */
	@Override
	public boolean isDocumentBelongsToThisUser(
			ClinicalDocumentDto clinicalDocumentDto) {
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		String username = currentUser.getUsername();
		Patient patient = patientRepository.findByUsername(username);
		List<ClinicalDocumentDto> clinicaldocumentDtos = findDtoByPatient(patient);
		for (ClinicalDocumentDto documentDto : clinicaldocumentDtos) {
			if (documentDto.getId().equals(clinicalDocumentDto.getId())) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isDocumentOversized(MultipartFile file) {
		if (file.getSize()>maxFileSize)
			return true;
		return false;
	}
	
	@Override
	public boolean isDocumentExtensionPermitted(MultipartFile file) {
		boolean result=false;
		int indexOfDot=file.getOriginalFilename().indexOf(".");
		if(indexOfDot>=0) {
			String extension=file.getOriginalFilename().substring(indexOfDot+1);
			for(String permittedExtension:permittedExtensionsArray) {
				if(extension.equals(permittedExtension)) {
					result=true;
					break;
				}
					
			}

		}
		return result;
	}
}
