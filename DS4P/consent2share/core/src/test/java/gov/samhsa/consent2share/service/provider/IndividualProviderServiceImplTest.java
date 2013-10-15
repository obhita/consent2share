package gov.samhsa.consent2share.service.provider;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import gov.samhsa.consent2share.domain.patient.Patient;
import gov.samhsa.consent2share.domain.patient.PatientRepository;
import gov.samhsa.consent2share.domain.provider.IndividualProvider;
import gov.samhsa.consent2share.domain.provider.IndividualProviderRepository;
import gov.samhsa.consent2share.service.dto.IndividualProviderDto;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

@RunWith(MockitoJUnitRunner.class)
public class IndividualProviderServiceImplTest {

	@InjectMocks
	IndividualProviderServiceImpl individualProviderService = new IndividualProviderServiceImpl();
	
	@Mock
    IndividualProviderRepository individualProviderRepository;
	
	@Mock
	ModelMapper modelMapper;
	
	@Mock
	PatientRepository patientRepository;
	
	@Before
	public void before() {
		IndividualProviderDto individualProviderDto = mock(IndividualProviderDto.class);
		Patient patient = mock(Patient.class);
		when(individualProviderDto.getUsername()).thenReturn("albert.smith");
		when(patientRepository.findByUsername(anyString())).thenReturn(patient);
	}
	
	@Test
	public void testCountAllIndividualProviders() {
		individualProviderService.countAllIndividualProviders();
		verify(individualProviderRepository).count();
	}

	@Test
	public void testUpdateIndividualProvider() {
		individualProviderService.updateIndividualProvider(mock(IndividualProvider.class));
		verify(individualProviderRepository).save(any(IndividualProvider.class));
	}
	
	@Test
	public void testUpdateIndividualProviderDto() {
		IndividualProviderDto individualProviderDto = mock(IndividualProviderDto.class);
		individualProviderService.updateIndividualProvider(individualProviderDto);
		verify(individualProviderRepository).findByPatientAndNpi(patientRepository.findByUsername(individualProviderDto.getUsername()), individualProviderDto.getNpi());
		verify(individualProviderRepository).save(any(IndividualProvider.class));
	}
	
	@Test
	public void testFindIndividualProviderByNpi() {
		individualProviderService.findIndividualProviderByNpi(anyString());
		verify(individualProviderRepository).findByNpi(anyString());
	}

	@Test
	public void testDeleteIndividualProvider() {
		individualProviderService.deleteIndividualProvider(mock(IndividualProvider.class));
		verify(individualProviderRepository).delete(any(IndividualProvider.class));
	}

	@Test
	public void testDeleteIndividualProviderDto() {
		IndividualProviderDto individualProviderDto = mock(IndividualProviderDto.class);
		individualProviderService.deleteIndividualProviderDto(mock(IndividualProviderDto.class));
		verify(individualProviderRepository).findByPatientAndNpi(patientRepository.findByUsername(individualProviderDto.getUsername()), individualProviderDto.getNpi());
	}

	@Test
	public void testFindIndividualProvider() {
		individualProviderService.findIndividualProvider(anyLong());
		verify(individualProviderRepository).findOne(anyLong());
	}

	@Test
	public void testFindAllIndividualProviders() {
		individualProviderService.findAllIndividualProviders();
		verify(individualProviderRepository).findAll();
	}

	@Test
	public void testSaveIndividualProvider() {
		individualProviderService.saveIndividualProvider(mock(IndividualProvider.class));
		verify(individualProviderRepository).save(any(IndividualProvider.class));
	}

	@Test
	public void testFindAllIndividualProvidersDto() {
		individualProviderService.findAllIndividualProvidersDto();
		verify(individualProviderRepository).findAll();
	}

	@Test
	public void testFindIndividualProviderDto() {
		individualProviderService.findIndividualProviderDto(anyLong());
		verify(individualProviderRepository).findOne(anyLong());
		verify(modelMapper).map(any(IndividualProvider.class), eq(IndividualProviderDto.class));
	}

}
