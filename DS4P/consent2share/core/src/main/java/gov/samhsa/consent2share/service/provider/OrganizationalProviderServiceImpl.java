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
package gov.samhsa.consent2share.service.provider;

import gov.samhsa.consent2share.domain.patient.Patient;
import gov.samhsa.consent2share.domain.patient.PatientRepository;
import gov.samhsa.consent2share.domain.provider.OrganizationalProvider;
import gov.samhsa.consent2share.domain.provider.OrganizationalProviderRepository;
import gov.samhsa.consent2share.service.dto.OrganizationalProviderDto;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * The Class OrganizationalProviderServiceImpl.
 */
@Service
@Transactional
public class OrganizationalProviderServiceImpl implements OrganizationalProviderService {
	
	/** The patient repository. */
	@Autowired
	PatientRepository patientRepository;
	
	/** The model mapper. */
	@Autowired
	ModelMapper modelMapper;

	/** The organizational provider repository. */
	@Autowired
    OrganizationalProviderRepository organizationalProviderRepository;

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.provider.OrganizationalProviderService#countAllOrganizationalProviders()
	 */
	public long countAllOrganizationalProviders() {
        return organizationalProviderRepository.count();
    }

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.provider.OrganizationalProviderService#deleteOrganizationalProvider(gov.samhsa.consent2share.domain.provider.OrganizationalProvider)
	 */
	public void deleteOrganizationalProvider(OrganizationalProvider organizationalProvider) {
        organizationalProviderRepository.delete(organizationalProvider);
    }
	
	
	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.provider.OrganizationalProviderService#deleteOrganizationalProviderDto(gov.samhsa.consent2share.service.dto.OrganizationalProviderDto)
	 */
	public void deleteOrganizationalProviderDto(OrganizationalProviderDto organizationalProviderDto){
		OrganizationalProvider organizationalProvider = findOrganizationalProviderByPatientAndNpi(
				patientRepository.findByUsername(organizationalProviderDto
						.getUsername()), organizationalProviderDto.getNpi());
		if(organizationalProvider!=null)
		organizationalProviderRepository.delete(organizationalProvider);

    }
	

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.provider.OrganizationalProviderService#findOrganizationalProvider(java.lang.Long)
	 */
	public OrganizationalProvider findOrganizationalProvider(Long id) {
		return organizationalProviderRepository.findOne(id);
       
    }
	
	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.provider.OrganizationalProviderService#findOrganizationalProviderByNpi(java.lang.String)
	 */
	public OrganizationalProvider findOrganizationalProviderByNpi(String npi) {
        return organizationalProviderRepository.findByNpi(npi);
    }
	
	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.provider.OrganizationalProviderService#findOrganizationalProviderByPatientAndNpi(gov.samhsa.consent2share.domain.patient.Patient, java.lang.String)
	 */
	public OrganizationalProvider findOrganizationalProviderByPatientAndNpi(Patient patient, String npi) {
        return organizationalProviderRepository.findByPatientAndNpi(patient,npi);
    }
	
	
	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.provider.OrganizationalProviderService#findOrganizationalProviderDto(java.lang.Long)
	 */
	@Override
	public OrganizationalProviderDto findOrganizationalProviderDto(Long id) {
		OrganizationalProvider provider = organizationalProviderRepository.findOne(id);		
		OrganizationalProviderDto providerDto = modelMapper.map(provider, OrganizationalProviderDto.class);
		
		return providerDto;
    }

	

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.provider.OrganizationalProviderService#findAllOrganizationalProviders()
	 */
	public List<OrganizationalProvider> findAllOrganizationalProviders() {
        return organizationalProviderRepository.findAll();
    }

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.provider.OrganizationalProviderService#findOrganizationalProviderEntries(int, int)
	 */
	public List<OrganizationalProvider> findOrganizationalProviderEntries(int firstResult, int maxResults) {
        return organizationalProviderRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.provider.OrganizationalProviderService#saveOrganizationalProvider(gov.samhsa.consent2share.domain.provider.OrganizationalProvider)
	 */
	public void saveOrganizationalProvider(OrganizationalProvider organizationalProvider) {
        organizationalProviderRepository.save(organizationalProvider);
    }

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.provider.OrganizationalProviderService#updateOrganizationalProvider(gov.samhsa.consent2share.domain.provider.OrganizationalProvider)
	 */
	public OrganizationalProvider updateOrganizationalProvider(OrganizationalProvider organizationalProvider) {
        return organizationalProviderRepository.save(organizationalProvider);
    }
	
	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.provider.OrganizationalProviderService#updateOrganizationalProvider(gov.samhsa.consent2share.service.dto.OrganizationalProviderDto)
	 */
	public void updateOrganizationalProvider(OrganizationalProviderDto organizationalProviderDto){
	
		
		OrganizationalProvider organizationalProvider=organizationalProviderRepository.findByPatientAndNpi(
				patientRepository.findByUsername(organizationalProviderDto.getUsername()), organizationalProviderDto.getNpi());
		if (organizationalProvider==null)
			organizationalProvider=new OrganizationalProvider();
		organizationalProvider.setOrgName(organizationalProviderDto.getOrgName());
		organizationalProvider.setAuthorizedOfficialFirstName(organizationalProviderDto.getAuthorizedOfficialFirstName());
		organizationalProvider.setAuthorizedOfficialLastName(organizationalProviderDto.getAuthorizedOfficialLastName());
		organizationalProvider.setAuthorizedOfficialTitle(organizationalProviderDto.getAuthorizedOfficialTitle());
		organizationalProvider.setAuthorizedOfficialNamePrefix(organizationalProviderDto.getAuthorizedOfficialNamePrefix());
		organizationalProvider.setAuthorizedOfficialTelephoneNumber(organizationalProviderDto.getAuthorizedOfficialTelephoneNumber());
		organizationalProvider.setNpi(organizationalProviderDto.getNpi());
		organizationalProvider.setEntityType(organizationalProviderDto.getEntityType());
		organizationalProvider.setFirstLineMailingAddress(organizationalProviderDto.getFirstLineMailingAddress());
		organizationalProvider.setSecondLineMailingAddress(organizationalProviderDto.getSecondLineMailingAddress());
		organizationalProvider.setMailingAddressCityName(organizationalProviderDto.getMailingAddressCityName());
		organizationalProvider.setMailingAddressStateName(organizationalProviderDto.getMailingAddressStateName());
		organizationalProvider.setMailingAddressPostalCode(organizationalProviderDto.getMailingAddressPostalCode());
		organizationalProvider.setMailingAddressCountryCode(organizationalProviderDto.getMailingAddressCountryCode());
		organizationalProvider.setMailingAddressTelephoneNumber(organizationalProviderDto.getMailingAddressTelephoneNumber());
		organizationalProvider.setMailingAddressFaxNumber(organizationalProviderDto.getMailingAddressFaxNumber());
		organizationalProvider.setFirstLinePracticeLocationAddress(organizationalProviderDto.getFirstLinePracticeLocationAddress());
		organizationalProvider.setSecondLinePracticeLocationAddress(organizationalProviderDto.getSecondLinePracticeLocationAddress()); 
		organizationalProvider.setPracticeLocationAddressCityName(organizationalProviderDto.getPracticeLocationAddressCityName());
		organizationalProvider.setPracticeLocationAddressStateName(organizationalProviderDto.getPracticeLocationAddressStateName());
		organizationalProvider.setPracticeLocationAddressPostalCode(organizationalProviderDto.getPracticeLocationAddressPostalCode()); 
		organizationalProvider.setPracticeLocationAddressCountryCode(organizationalProviderDto.getPracticeLocationAddressCountryCode()); 
		organizationalProvider.setPracticeLocationAddressTelephoneNumber(organizationalProviderDto.getPracticeLocationAddressTelephoneNumber());
		organizationalProvider.setPracticeLocationAddressFaxNumber(organizationalProviderDto.getPracticeLocationAddressFaxNumber());
		organizationalProvider.setEnumerationDate(organizationalProviderDto.getEnumerationDate());
		organizationalProvider.setLastUpdateDate(organizationalProviderDto.getLastUpdateDate());
		organizationalProvider.setProviderTaxonomyCode(organizationalProviderDto.getProviderTaxonomyCode());
		organizationalProvider.setProviderTaxonomyDescription(organizationalProviderDto.getProviderTaxonomyDescription());
		organizationalProvider.setPatient(patientRepository.findByUsername(organizationalProviderDto.getUsername()));
		
		organizationalProviderRepository.save(organizationalProvider);
		
	}
}
