package gov.samhsa.consent2share.showcase.web;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import gov.samhsa.consent2share.showcase.service.PixOperationsService;

import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponse;

import org.hl7.v3.types.MCCIIN000002UV01;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class XdsbRegistryControllerTest {

	@InjectMocks
	XdsbRegistryController sut;

	@Mock
	PixOperationsService pixOperationsServiceMock;

	@Before
	public void before() {
		sut.setPixOperationsService(pixOperationsServiceMock);
	}

	@Test
	public void testReqToXdsbRegistryAdd() {
		// Arrange
		String c32XmlMock = "c32XmlMock";
		MCCIIN000002UV01 addPatientMock = new MCCIIN000002UV01();
		when(pixOperationsServiceMock.addPatientRegistryRecord(c32XmlMock))
				.thenReturn(addPatientMock);

		// Act
		MCCIIN000002UV01 actualRetResponse = sut
				.reqToXdsbRegistryAdd(c32XmlMock);

		// Assert
		assertEquals(addPatientMock, actualRetResponse);
	}

	@Test
	public void testReqToXdsbRegistryUpdate() {
		// Arrange
		String c32XmlMock = "c32XmlMock";
		MCCIIN000002UV01 updatePatientMock = new MCCIIN000002UV01();
		when(pixOperationsServiceMock.revisePatientRegistryRecord(c32XmlMock))
				.thenReturn(updatePatientMock);

		// Act
		MCCIIN000002UV01 actualRetResponse = sut
				.reqToXdsbRegistryUpdate(c32XmlMock);

		// Assert
		assertEquals(updatePatientMock, actualRetResponse);
	}

	@Test
	public void testProvideAndRegisterClinicalDocument() {
		// Arrange
		String c32XmlMock = "c32XmlMock";
		RegistryResponse responseMock = new RegistryResponse();
		when(
				pixOperationsServiceMock
						.provideAndRegisterClinicalDocument(c32XmlMock))
				.thenReturn(responseMock);

		// Act
		RegistryResponse actualResponse = sut
				.provideAndRegisterClinicalDocument(c32XmlMock);

		// Assert
		assertEquals(responseMock, actualResponse);
	}
}
