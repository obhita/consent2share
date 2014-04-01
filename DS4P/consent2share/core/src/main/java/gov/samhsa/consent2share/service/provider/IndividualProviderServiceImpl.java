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
import gov.samhsa.consent2share.domain.provider.IndividualProvider;
import gov.samhsa.consent2share.domain.provider.IndividualProviderRepository;
import gov.samhsa.consent2share.domain.provider.StaffIndividualProvider;
import gov.samhsa.consent2share.domain.provider.StaffIndividualProviderRepository;
import gov.samhsa.consent2share.service.dto.IndividualProviderDto;
import gov.samhsa.consent2share.service.dto.StaffIndividualProviderDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class IndividualProviderServiceImpl.
 */
@Service
@Transactional
public class IndividualProviderServiceImpl implements IndividualProviderService {
	
	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The individual provider repository. */
	@Autowired
    IndividualProviderRepository individualProviderRepository;
	
	/** The model mapper. */
	@Autowired
	ModelMapper modelMapper;
	
	/** The patient repository. */
	@Autowired
	PatientRepository patientRepository;
	
	@Autowired
	StaffIndividualProviderRepository staffIndividualProviderRepository;

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#countAllIndividualProviders()
	 */
	public long countAllIndividualProviders() {
        return individualProviderRepository.count();
    }
	
	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#updateIndividualProvider(gov.samhsa.consent2share.service.dto.IndividualProviderDto)
	 */
	public void updateIndividualProvider(IndividualProviderDto individualProviderDto){
		Patient patient=patientRepository.findByUsername(individualProviderDto.getUsername());
		Set<IndividualProvider> individualProviders=patient.getIndividualProviders();
		IndividualProvider individualProvider=null;
		for (IndividualProvider o:individualProviders){
			if (o.getNpi().equals(individualProviderDto.getNpi())){
				individualProvider=o;
				break;
			}
				
		}
		if (individualProvider==null)
			individualProvider=new IndividualProvider();
		individualProvider.setFirstName(individualProviderDto.getFirstName());
		individualProvider.setMiddleName(individualProviderDto.getMiddleName());
		individualProvider.setLastName(individualProviderDto.getLastName());
		individualProvider.setNamePrefix(individualProviderDto.getNamePrefix());
		individualProvider.setNameSuffix(individualProviderDto.getNameSuffix());
		individualProvider.setCredential(individualProviderDto.getCredential());
		individualProvider.setNpi(individualProviderDto.getNpi());
		individualProvider.setEntityType(individualProviderDto.getEntityType());
		individualProvider.setFirstLineMailingAddress(individualProviderDto.getFirstLineMailingAddress());
		individualProvider.setSecondLineMailingAddress(individualProviderDto.getSecondLineMailingAddress());
		individualProvider.setMailingAddressCityName(individualProviderDto.getMailingAddressCityName());
		individualProvider.setMailingAddressStateName(individualProviderDto.getMailingAddressStateName());
		individualProvider.setMailingAddressPostalCode(individualProviderDto.getMailingAddressPostalCode());
		individualProvider.setMailingAddressCountryCode(individualProviderDto.getMailingAddressCountryCode());
		individualProvider.setMailingAddressTelephoneNumber(individualProviderDto.getMailingAddressTelephoneNumber());
		individualProvider.setMailingAddressFaxNumber(individualProviderDto.getMailingAddressFaxNumber());
		individualProvider.setFirstLinePracticeLocationAddress(individualProviderDto.getFirstLinePracticeLocationAddress());
		individualProvider.setSecondLinePracticeLocationAddress(individualProviderDto.getSecondLinePracticeLocationAddress()); 
		individualProvider.setPracticeLocationAddressCityName(individualProviderDto.getPracticeLocationAddressCityName());
		individualProvider.setPracticeLocationAddressStateName(individualProviderDto.getPracticeLocationAddressStateName());
		individualProvider.setPracticeLocationAddressPostalCode(individualProviderDto.getPracticeLocationAddressPostalCode()); 
		individualProvider.setPracticeLocationAddressCountryCode(individualProviderDto.getPracticeLocationAddressCountryCode()); 
		individualProvider.setPracticeLocationAddressTelephoneNumber(individualProviderDto.getPracticeLocationAddressTelephoneNumber());
		individualProvider.setPracticeLocationAddressFaxNumber(individualProviderDto.getPracticeLocationAddressFaxNumber());
		individualProvider.setEnumerationDate(individualProviderDto.getEnumerationDate());
		individualProvider.setLastUpdateDate(individualProviderDto.getLastUpdateDate());
		individualProvider.setProviderTaxonomyCode(individualProviderDto.getProviderTaxonomyCode());
		individualProvider.setProviderTaxonomyDescription(individualProviderDto.getProviderTaxonomyDescription());
		individualProviders.add(individualProvider);
		patient.setIndividualProviders(individualProviders);

		patientRepository.save(patient);
		
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#deleteIndividualProvider(gov.samhsa.consent2share.domain.provider.IndividualProvider)
	 */
	public void deleteIndividualProvider(IndividualProvider individualProvider) {
        individualProviderRepository.delete(individualProvider);
    }
	
	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#deleteIndividualProviderDto(gov.samhsa.consent2share.service.dto.IndividualProviderDto)
	 */
	public void deleteIndividualProviderDto(IndividualProviderDto individualProviderDto) {
		
		Patient patient = patientRepository.findByUsername(individualProviderDto.getUsername());
		Set<IndividualProvider> individualProviders=patient.getIndividualProviders();
		for (IndividualProvider o:individualProviders){
			if(o.getNpi().equals(individualProviderDto.getNpi())){
				individualProviders.remove(o);
				break;
			}
		}
		patient.setIndividualProviders(individualProviders);
		patientRepository.save(patient);
		
    }
	
	/**
	 * Find individual provider by npi.
	 *
	 * @param npi the npi
	 * @return the individual provider
	 */
	public IndividualProvider findIndividualProviderByNpi(String npi) {
		return individualProviderRepository.findByNpi(npi);
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#findIndividualProvider(java.lang.Long)
	 */
	public IndividualProvider findIndividualProvider(Long id) {
        return individualProviderRepository.findOne(id);
    }
	
	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#findAllIndividualProviders()
	 */
	public List<IndividualProvider> findAllIndividualProviders() {
        return individualProviderRepository.findAll();
    }

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#findIndividualProviderEntries(int, int)
	 */
	public List<IndividualProvider> findIndividualProviderEntries(int firstResult, int maxResults) {
        return individualProviderRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#saveIndividualProvider(gov.samhsa.consent2share.domain.provider.IndividualProvider)
	 */
	public void saveIndividualProvider(IndividualProvider individualProvider) {
        individualProviderRepository.save(individualProvider);
    }

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#updateIndividualProvider(gov.samhsa.consent2share.domain.provider.IndividualProvider)
	 */
	public IndividualProvider updateIndividualProvider(IndividualProvider individualProvider) {
        return individualProviderRepository.save(individualProvider);
    }
	
	public boolean addNewIndividualProvider(IndividualProviderDto individualProviderDto) {
		Patient patient;
		if(individualProviderDto.getUsername()==null&&individualProviderDto.getPatientId()!=null)
		patient=patientRepository.findOne(Long.valueOf(individualProviderDto.getPatientId()).longValue());
		else patient=patientRepository.findByUsername(individualProviderDto.getUsername());
		Set<IndividualProvider> individualProviders=patient.getIndividualProviders();
		IndividualProvider in_individualProvider=null;
		for (IndividualProvider o:individualProviders){
			if (o.getNpi().equals(individualProviderDto.getNpi())){
				in_individualProvider=o;
				break;
			}
				
		}
		if (in_individualProvider != null){
			return false;
		}else{
			IndividualProvider individualProvider=new IndividualProvider();
			
			individualProvider.setFirstName(individualProviderDto.getFirstName());
			individualProvider.setMiddleName(individualProviderDto.getMiddleName());
			individualProvider.setLastName(individualProviderDto.getLastName());
			individualProvider.setNamePrefix(individualProviderDto.getNamePrefix());
			individualProvider.setNameSuffix(individualProviderDto.getNameSuffix());
			individualProvider.setCredential(individualProviderDto.getCredential());
			individualProvider.setNpi(individualProviderDto.getNpi());
			individualProvider.setEntityType(individualProviderDto.getEntityType());
			individualProvider.setFirstLineMailingAddress(individualProviderDto.getFirstLineMailingAddress());
			individualProvider.setSecondLineMailingAddress(individualProviderDto.getSecondLineMailingAddress());
			individualProvider.setMailingAddressCityName(individualProviderDto.getMailingAddressCityName());
			individualProvider.setMailingAddressStateName(individualProviderDto.getMailingAddressStateName());
			individualProvider.setMailingAddressPostalCode(individualProviderDto.getMailingAddressPostalCode());
			individualProvider.setMailingAddressCountryCode(individualProviderDto.getMailingAddressCountryCode());
			individualProvider.setMailingAddressTelephoneNumber(individualProviderDto.getMailingAddressTelephoneNumber());
			individualProvider.setMailingAddressFaxNumber(individualProviderDto.getMailingAddressFaxNumber());
			individualProvider.setFirstLinePracticeLocationAddress(individualProviderDto.getFirstLinePracticeLocationAddress());
			individualProvider.setSecondLinePracticeLocationAddress(individualProviderDto.getSecondLinePracticeLocationAddress()); 
			individualProvider.setPracticeLocationAddressCityName(individualProviderDto.getPracticeLocationAddressCityName());
			individualProvider.setPracticeLocationAddressStateName(individualProviderDto.getPracticeLocationAddressStateName());
			individualProvider.setPracticeLocationAddressPostalCode(individualProviderDto.getPracticeLocationAddressPostalCode()); 
			individualProvider.setPracticeLocationAddressCountryCode(individualProviderDto.getPracticeLocationAddressCountryCode()); 
			individualProvider.setPracticeLocationAddressTelephoneNumber(individualProviderDto.getPracticeLocationAddressTelephoneNumber());
			individualProvider.setPracticeLocationAddressFaxNumber(individualProviderDto.getPracticeLocationAddressFaxNumber());
			individualProvider.setEnumerationDate(individualProviderDto.getEnumerationDate());
			individualProvider.setLastUpdateDate(individualProviderDto.getLastUpdateDate());
			individualProvider.setProviderTaxonomyCode(individualProviderDto.getProviderTaxonomyCode());
			individualProvider.setProviderTaxonomyDescription(individualProviderDto.getProviderTaxonomyDescription());
			individualProviders.add(individualProvider);
			patient.setIndividualProviders(individualProviders);
	
			patientRepository.save(patient);
			
			return true;
		}
	}
	

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#findAllIndividualProvidersDto()
	 */
	@Override
	public List<IndividualProviderDto> findAllIndividualProvidersDto() {
		List<IndividualProviderDto> providers = new ArrayList<IndividualProviderDto>();		
		
		for (IndividualProvider entity : individualProviderRepository.findAll()) {
			providers.add( modelMapper.map(entity, IndividualProviderDto.class));
		}
		 return providers;
	}
	
	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#findIndividualProviderDto(java.lang.Long)
	 */
	@Override
	public IndividualProviderDto findIndividualProviderDto(Long id) {
		IndividualProvider provider = individualProviderRepository.findOne(id);		
		IndividualProviderDto providerDto = modelMapper.map(provider, IndividualProviderDto.class);
		
		return providerDto;
    }

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#findAllStaffIndividualProviders()
	 */
	@Override
	public List<StaffIndividualProvider> findAllStaffIndividualProviders() {
		return staffIndividualProviderRepository.findAll();
	}
	
	
	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#findAllStaffIndividualProvidersDto()
	 */
	@Override
	public List<StaffIndividualProviderDto> findAllStaffIndividualProvidersDto() {
		List<StaffIndividualProviderDto> providers = new ArrayList<StaffIndividualProviderDto>();		
		
		for (StaffIndividualProvider entity : staffIndividualProviderRepository.findAll()) {
			providers.add( modelMapper.map(entity, StaffIndividualProviderDto.class));
		}
		 return providers;
	}
	
	
	@Override
	public void addFavouriteIndividualProvider(IndividualProvider individualProvider){
		StaffIndividualProvider staffIndividualProvider=new StaffIndividualProvider();
		staffIndividualProvider.setIndividualProvider(individualProvider);
		staffIndividualProvider.setId((long)0);
		staffIndividualProviderRepository.save(staffIndividualProvider);
	}
	public boolean isFavoriteIndividualProvider(long id) throws IllegalArgumentException {
		StaffIndividualProvider searchedProvider = null;
		IndividualProvider individualProvider = findIndividualProvider(id);
		searchedProvider = staffIndividualProviderRepository.findByIndividualProvider(individualProvider);
		
		if(searchedProvider == null){
			return false;
		}else{
			return true;
		}
	}
	
	@Override
	public boolean addFavoriteIndividualProvider(long id) throws IllegalArgumentException {
		boolean isAlreadyFavorite = false;
		isAlreadyFavorite = isFavoriteIndividualProvider(id);
		
		if(isAlreadyFavorite == true){
			logger.info("Individual Provider with id = '" + id + "' is already a favorite.");
			return false;
		}else{
			StaffIndividualProvider staffIndividualProvider=new StaffIndividualProvider();
			IndividualProvider individualProvider = findIndividualProvider(id);
			
			if(individualProvider == null){
				logger.warn("Unable to find an individual provider with id = '" + id + "'.");
				throw new NullPointerException("findIndividualProvider(" + id + ") in IndividualProviderService returned null");
			}
			
			staffIndividualProvider.setIndividualProvider(individualProvider);
			staffIndividualProviderRepository.save(staffIndividualProvider);
			return true;
		}
	}
	
	@Override
	public void deleteFavoriteIndividualProvider(long id) throws IllegalArgumentException {
		staffIndividualProviderRepository.delete(id);
	}
}
