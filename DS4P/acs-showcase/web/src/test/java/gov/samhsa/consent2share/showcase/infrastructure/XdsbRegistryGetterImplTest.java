package gov.samhsa.consent2share.showcase.infrastructure;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.consent2share.accesscontrolservice.common.tool.SimpleMarshaller;
import gov.samhsa.consent2share.accesscontrolservice.xdsb.registry.XdsbRegistryWebServiceClient;
import gov.samhsa.consent2share.showcase.exception.AcsShowCaseException;

import org.hl7.v3.PRPAIN201301UV02;
import org.hl7.v3.PRPAIN201302UV02;
import org.hl7.v3.PRPAIN201304UV02;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class XdsbRegistryGetterImplTest {

	@InjectMocks
	XdsbRegistryGetterImpl sut;
	
	@Mock
	XdsbRegistryWebServiceClient xdsbRegistryWebServiceClientMock;
	
	//private String endpointAddress;
	
	@Mock
	SimpleMarshaller marshallerMock;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testXdsbRegistryGetterImpl() {
		//fail("Not yet implemented");
	}

	@Test
	public void testAddPatientRegistryRecord() throws Throwable {
		//Arrange
		sut.setMarshaller(marshallerMock);
		sut.setXdsbRegistryWebServiceClient(xdsbRegistryWebServiceClientMock);
		PRPAIN201301UV02 requestMock =  mock(PRPAIN201301UV02.class);
		String hl7v3XmlMock = "hl7v3XmlMock";
		
		//Act
		when(marshallerMock.unmarshallFromXml(PRPAIN201301UV02.class, hl7v3XmlMock)).thenReturn(requestMock);
		when(xdsbRegistryWebServiceClientMock.addPatientRegistryRecord(requestMock)).thenReturn(hl7v3XmlMock);
		
		String actRet = sut.addPatientRegistryRecord(hl7v3XmlMock, "", "");
		//Asset
		assertEquals(hl7v3XmlMock, actRet);
	}
	

	@Test
	public void testAddPatientRegistryRecord_exception() throws Throwable {
		//Arrange
		sut.setMarshaller(marshallerMock);
		sut.setXdsbRegistryWebServiceClient(xdsbRegistryWebServiceClientMock);
		PRPAIN201301UV02 requestMock =  mock(PRPAIN201301UV02.class);
		String hl7v3XmlMock = "hl7v3XmlMock";
		thrown.expect(AcsShowCaseException.class);
		
		//Act
		when(marshallerMock.unmarshallFromXml(PRPAIN201301UV02.class, hl7v3XmlMock)).thenReturn(requestMock);
		when(xdsbRegistryWebServiceClientMock.addPatientRegistryRecord(requestMock)).thenThrow(AcsShowCaseException.class);
		
		sut.addPatientRegistryRecord(hl7v3XmlMock, "", "");
	}
	

	@Test
	public void testResolvePatientRegistryDuplicates() throws Throwable {
		//Arrange
		sut.setMarshaller(marshallerMock);
		sut.setXdsbRegistryWebServiceClient(xdsbRegistryWebServiceClientMock);
		PRPAIN201304UV02 requestMock =  mock(PRPAIN201304UV02.class);
		String hl7v3XmlMock = "hl7v3XmlMock";
		
		//Act
		when(marshallerMock.unmarshallFromXml(PRPAIN201304UV02.class, hl7v3XmlMock)).thenReturn(requestMock);
		when(xdsbRegistryWebServiceClientMock.resolvePatientRegistryDuplicates(requestMock)).thenReturn(hl7v3XmlMock);
		
		String actRet = sut.resolvePatientRegistryDuplicates(hl7v3XmlMock, "", "");
		//Asset
		assertEquals(hl7v3XmlMock, actRet);
	}
	
	@Test
	public void testResolvePatientRegistryDuplicates_exception() throws Throwable {
		//Arrange
		sut.setMarshaller(marshallerMock);
		sut.setXdsbRegistryWebServiceClient(xdsbRegistryWebServiceClientMock);
		PRPAIN201304UV02 requestMock =  mock(PRPAIN201304UV02.class);
		String hl7v3XmlMock = "hl7v3XmlMock";
		thrown.expect(AcsShowCaseException.class);
		
		//Act
		when(marshallerMock.unmarshallFromXml(PRPAIN201304UV02.class, hl7v3XmlMock)).thenReturn(requestMock);
		when(xdsbRegistryWebServiceClientMock.resolvePatientRegistryDuplicates(requestMock)).thenThrow(AcsShowCaseException.class);
		
		sut.resolvePatientRegistryDuplicates(hl7v3XmlMock, "", "");
		
	}	
	
	
	@Test
	public void testRevisePatientRegistryRecord() throws Throwable {
		//Arrange
		sut.setMarshaller(marshallerMock);
		sut.setXdsbRegistryWebServiceClient(xdsbRegistryWebServiceClientMock);
		PRPAIN201302UV02 requestMock =  mock(PRPAIN201302UV02.class);
		String hl7v3XmlMock = "hl7v3XmlMock";
		
		//Act
		when(marshallerMock.unmarshallFromXml(PRPAIN201302UV02.class, hl7v3XmlMock)).thenReturn(requestMock);
		when(xdsbRegistryWebServiceClientMock.revisePatientRegistryRecord(requestMock)).thenReturn(hl7v3XmlMock);
		
		String actRet = sut.revisePatientRegistryRecord(hl7v3XmlMock, "", "");
		//Asset
		assertEquals(hl7v3XmlMock, actRet);
	}
	
	@Test
	public void testRevisePatientRegistryRecord_exception() throws Throwable {
		//Arrange
		sut.setMarshaller(marshallerMock);
		sut.setXdsbRegistryWebServiceClient(xdsbRegistryWebServiceClientMock);
		PRPAIN201302UV02 requestMock =  mock(PRPAIN201302UV02.class);
		String hl7v3XmlMock = "hl7v3XmlMock";
		thrown.expect(AcsShowCaseException.class);
		
		//Act
		when(marshallerMock.unmarshallFromXml(PRPAIN201302UV02.class, hl7v3XmlMock)).thenReturn(requestMock);
		when(xdsbRegistryWebServiceClientMock.revisePatientRegistryRecord(requestMock)).thenThrow(AcsShowCaseException.class);
		
		sut.revisePatientRegistryRecord(hl7v3XmlMock, "", "");
		
	}	

	@Test
	public void testSetEidValues() {
		//fail("Not yet implemented");
	}

}
