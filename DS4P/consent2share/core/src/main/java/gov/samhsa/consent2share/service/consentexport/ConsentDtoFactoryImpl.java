package gov.samhsa.consent2share.service.consentexport;

import gov.samhsa.consent.ConsentDto;
import gov.samhsa.consent.ConsentDtoFactory;
import gov.samhsa.consent2share.domain.consent.Consent;
import gov.samhsa.consent2share.domain.consent.ConsentRepository;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsentDtoFactoryImpl implements ConsentDtoFactory{
	
	/** The consent repository. */
	@Autowired
	ConsentRepository consentRepository;
	
	/** The model mapper. */
	@Autowired
	ModelMapper modelMapper;
	
	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ConsentExportMapper consentExportMapper;
	

	@Override
	public ConsentDto createConsentDto(long consentId) {
		Consent consent = consentRepository.findOne(consentId);
		ConsentDto consentDto =  consentExportMapper.map(consent);
		return consentDto;	
	}
	



	@Override
	public ConsentDto createConsentDto(Object obj) {
		ConsentDto consentDto = null;
		
		if(obj != null){			
			if(obj instanceof Long) {	
				long consentId = ((Long)obj).longValue();
				Consent consent = consentRepository.findOne(consentId);
				consentDto = consentExportMapper.map(consent);
			} else if (obj instanceof Consent){
				consentDto = consentExportMapper.map((Consent)obj);	
			}
		}
		
		return consentDto;
	}
	
	

}
