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
package gov.samhsa.consent2share.service.patient;

import gov.samhsa.consent2share.domain.patient.Patient;
import gov.samhsa.consent2share.domain.patient.PatientLegalRepresentativeAssociation;
import gov.samhsa.consent2share.domain.patient.PatientLegalRepresentativeAssociationRepository;
import gov.samhsa.consent2share.domain.patient.PatientRepository;
import gov.samhsa.consent2share.domain.provider.IndividualProvider;
import gov.samhsa.consent2share.domain.provider.OrganizationalProvider;
import gov.samhsa.consent2share.infrastructure.DtoToDomainEntityMapper;
import gov.samhsa.consent2share.infrastructure.security.UserContext;
import gov.samhsa.consent2share.service.dto.AddConsentIndividualProviderDto;
import gov.samhsa.consent2share.service.dto.AddConsentOrganizationalProviderDto;
import gov.samhsa.consent2share.service.dto.PatientConnectionDto;
import gov.samhsa.consent2share.service.dto.PatientProfileDto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class PatientServiceImpl.
 */
@Service
@Transactional
public class PatientServiceImpl implements PatientService {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The patient repository. */
	@Autowired
	private PatientRepository patientRepository;

	/** The patient legal representative association repository. */
	@Autowired
	private PatientLegalRepresentativeAssociationRepository patientLegalRepresentativeAssociationRepository;

	/** The model mapper. */
	@Autowired
	private ModelMapper modelMapper;

	/** The user context. */
	@Autowired
	private UserContext userContext;
	
	/** The patient profile dto to patient mapper. */
	@Autowired
	private DtoToDomainEntityMapper<PatientProfileDto, Patient> patientProfileDtoToPatientMapper;

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.patient.PatientService#countAllPatients()
	 */
	public long countAllPatients() {
		return patientRepository.count();
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.patient.PatientService#findPatient(java.lang.Long)
	 */
	public PatientProfileDto findPatient(Long id) {
		Patient patient = patientRepository.findOne(id);
		PatientProfileDto patientDto = modelMapper.map(patient,
				PatientProfileDto.class);
		return patientDto;
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.patient.PatientService#findIdByUsername(java.lang.String)
	 */
	public Long findIdByUsername(String username) {
		return patientRepository.findByUsername(username).getId();
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.patient.PatientService#findPatientEmailByUsername(java.lang.String)
	 */
	public String findPatientEmailByUsername(String username) {
		return patientRepository.findByUsername(username).getEmail();
	}
	
	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.patient.PatientService#findPatientProfileByUsername(java.lang.String)
	 */
	public PatientProfileDto findPatientProfileByUsername(String username) {
		Patient patient = patientRepository.findByUsername(username);
		PatientProfileDto patientDto = modelMapper.map(patient,
				PatientProfileDto.class);

		return patientDto;
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.patient.PatientService#findPatientConnectionByUsername(java.lang.String)
	 */
	public PatientConnectionDto findPatientConnectionByUsername(String username) {
		Patient patient = patientRepository.findByUsername(username);
		PatientConnectionDto patientConnectionDto = modelMapper.map(patient,
				PatientConnectionDto.class);

		return patientConnectionDto;
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.patient.PatientService#findPatientEntries(int, int)
	 */
	public List<PatientProfileDto> findPatientEntries(int pageNumber,
			int pageSize) {
		List<Patient> patientList = patientRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();

		List<PatientProfileDto> patientDtoList = new ArrayList<PatientProfileDto>();

		for (Patient patient : patientList) {
			PatientProfileDto patientProfileDto = modelMapper.map(patient,
					PatientProfileDto.class);
			patientDtoList.add(patientProfileDto);
		}

		return patientDtoList;
	}
	
	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.patient.PatientService#savePatient(gov.samhsa.consent2share.service.dto.PatientProfileDto)
	 */
	@Override
	@Transactional
	public PatientProfileDto savePatient(PatientProfileDto patientDto) {
		Patient patient = patientProfileDtoToPatientMapper.map(patientDto);
		patientRepository.save(patient);
		PatientProfileDto patientProfileDto = modelMapper.map(patient,
				PatientProfileDto.class);

		return patientProfileDto;
	}
	
	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.patient.PatientService#updatePatient(gov.samhsa.consent2share.service.dto.PatientProfileDto)
	 */
	@Override
	@Transactional
	public void updatePatient(PatientProfileDto patientDto) {
		logger.info("{} being run...", "updatePatient");
		Patient patient = patientProfileDtoToPatientMapper.map(patientDto);
		patientRepository.save(patient);
	}
	
	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.patient.PatientService#findAddConsentIndividualProviderDtoByUsername(java.lang.String)
	 */
	public List<AddConsentIndividualProviderDto> findAddConsentIndividualProviderDtoByUsername(
			String username) {
		Patient patient = patientRepository.findByUsername(username);
		List<AddConsentIndividualProviderDto> individualProvidersDto = new ArrayList<AddConsentIndividualProviderDto>();
		Set<IndividualProvider> individualProviders = patient
				.getIndividualProviders();
		Iterator<IndividualProvider> individualProvidersIterator = individualProviders
				.iterator();
		while (individualProvidersIterator.hasNext()) {
			individualProvidersDto.add(modelMapper.map(
					individualProvidersIterator.next(),
					AddConsentIndividualProviderDto.class));
		}
		return individualProvidersDto;
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.patient.PatientService#findAddConsentOrganizationalProviderDtoByUsername(java.lang.String)
	 */
	public List<AddConsentOrganizationalProviderDto> findAddConsentOrganizationalProviderDtoByUsername(
			String username) {
		Patient patient = patientRepository.findByUsername(username);
		List<AddConsentOrganizationalProviderDto> organizationalProvidersDto = new ArrayList<AddConsentOrganizationalProviderDto>();
		Set<OrganizationalProvider> organizationalProviders = patient
				.getOrganizationalProviders();
		Iterator<OrganizationalProvider> organizationalProvidersIterator = organizationalProviders
				.iterator();
		while (organizationalProvidersIterator.hasNext()) {
			organizationalProvidersDto.add(modelMapper.map(
					organizationalProvidersIterator.next(),
					AddConsentOrganizationalProviderDto.class));
		}
		return organizationalProvidersDto;
	}

	
	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.patient.PatientService#isLegalRepForCurrentUser(java.lang.Long)
	 */
	@Override
	public boolean isLegalRepForCurrentUser(Long legalRepId) {

		String username = userContext.getCurrentUser().getUsername();
		Patient patient = patientRepository.findByUsername(username);
		List<PatientLegalRepresentativeAssociation> associations = patientLegalRepresentativeAssociationRepository
				.findByPatientLegalRepresentativeAssociationPkLegalRepresentativeId(legalRepId);
		for (PatientLegalRepresentativeAssociation association : associations) {
			if (association.getPatientLegalRepresentativeAssociationPk()
					.getPatient().getId().longValue() == patient.getId().longValue()) {
				return true;
			}
		}
		return false;
	}
}
