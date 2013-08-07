package gov.samhsa.consent2share.infrastructure;

import static org.junit.Assert.*;
import gov.samhsa.consent2share.domain.consent.Consent;
import gov.samhsa.consent2share.domain.consent.ConsentRepository;
import gov.samhsa.consent2share.domain.patient.Patient;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ConsentRevokationPdfGeneratorTest {
	
	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testGenerateConsentRevokationPdf() {
		
//		Consent consent=mock (Consent.class);
//		Patient patient=mock (Patient.class);
//		when(consent.getPatient()).thenReturn(patient);
//		when(patient.getFirstName()).thenReturn("John");
//		when(patient.getLastName()).thenReturn("Doe");
//		new ConsentRevokationPdfGenerator().generateConsentRevokationPdf(consent);
	}

}
