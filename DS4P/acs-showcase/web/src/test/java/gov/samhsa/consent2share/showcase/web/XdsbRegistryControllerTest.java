package gov.samhsa.consent2share.showcase.web;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import gov.samhsa.consent2share.showcase.service.PixOperationsService;

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
		//Arrange
		String c32XmlMock = "c32XmlMock";
		String pixAddXmlMock = "pixAddXmlMock";
		
		//Act
		when(pixOperationsServiceMock.addPatientRegistryRecord(c32XmlMock)).thenReturn(pixAddXmlMock);
		String actualRet = sut.reqToXdsbRegistryAdd(c32XmlMock);
		
		//Assert
		assertEquals(pixAddXmlMock, actualRet);	}

}
