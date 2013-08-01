package gov.samhsa.consent2share.service.notification;

import java.util.Set;

import gov.samhsa.consent2share.domain.consent.Consent;
import gov.samhsa.consent2share.domain.patient.Patient;
import gov.samhsa.consent2share.domain.patient.PatientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

	/** The patient repository. */
	@Autowired
	private PatientRepository patientRepository;

	public String notificationStage(String username, String notify) {
		Patient patient = patientRepository.findByUsername(username);
		int providerscount=patient.getIndividualProviders().size()+patient.getOrganizationalProviders().size();
		int consentscount=patient.getConsents().size();
		boolean consentReviewStatus=checkConsentReviewStatus(patient.getConsents());
		boolean consentSignedStatus=checkConsentSignedStatus(patient.getConsents());
		
		switch(consentscount)
				{case 0: {  switch(providerscount){
								case 0: return "notification_add_provider";
								case 1: { if(notify!=null)
												{if(notify.equals("add")) 
													return "notification_add_one_provider_successed";
												}
										else
											return "notification_add_second_provider";
											}
								case 2: {  if (notify != null) 
												{if (notify.equals("add"))
													return "notification_add_second_provider_successed";
												} 
											else
												 return "notification_add_consent";
											}
								default:  return "notification_add_consent";
			
			                    }
						}
				case 1: { if (notify != null) 
							{if (notify.equals("add"))
									return "notification_add_consent_successed";
								} 
							else
								 if(consentReviewStatus==false) return "notification_review_sign_consent";
								 else {if (consentSignedStatus==false) return "notification_sign_consent";}
				}
				default: {
					 if(consentReviewStatus==false) return "notification_review_sign_consent";
					 else {if (consentSignedStatus==false) return "notification_sign_consent";}	
					  
						 return null;
					
				}
			}
		
		
	}
	

	public boolean checkConsentReviewStatus(Set<Consent> consents) {
		if (consents.size()==0)
			return false;
		for (Consent consent : consents) {
			if (consent.getSignedPdfConsent() != null)
				return true;
		}
		return false;
	}
	
	public boolean checkConsentSignedStatus(Set<Consent> consents) {
		if (consents.size()==0)
			return false;
		for (Consent consent : consents) {
			if (consent.getSignedPdfConsent() != null)
			{ if(consent.getSignedPdfConsent().getSignedPdfConsentContent() != null)
				return true;
			}
		}
		return false;
	}


	
	
}


