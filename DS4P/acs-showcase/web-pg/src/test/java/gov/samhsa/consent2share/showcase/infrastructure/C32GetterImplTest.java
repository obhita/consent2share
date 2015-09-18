package gov.samhsa.consent2share.showcase.infrastructure;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.c32.wsclient.C32WebServiceClient;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class C32GetterImplTest {

	@InjectMocks
	C32GetterImpl sut;
	
	@Mock
	C32WebServiceClient c32WebServiceClientMock;
	
	@Test
	public void testC32GetterImpl() {
	//	fail("Not yet implemented");
	}

	@Test
	public void testGetC32() {
		//Arrange
		sut.setC32WebServiceClient(c32WebServiceClientMock);
		String patientIdMock= "patientIdMock";
		String c32Mock= "c32Mock";
		
		//Act
		when(c32WebServiceClientMock.getC32(patientIdMock)).thenReturn(c32Mock);
			
		String actRet = sut.getC32(patientIdMock);
		//Asset
		assertEquals(c32Mock, actRet);		
	}

}
