package gov.samhsa.consent2share.pixclient.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.consent2share.pixclient.ws.MCCIIN000002UV01;
import gov.samhsa.consent2share.pixclient.ws.PIXManagerPortType;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201301UV02;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201302UV02;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201304UV02;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201309UV02;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201310UV02;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PixManagerServiceImplTest {

	@InjectMocks
	PixManagerServiceImpl sut;

	@Mock
	PIXManagerPortType pIXManagerPortTypeMock;

	@Before
	public void setup() {
		sut.setPort(pIXManagerPortTypeMock);
	}

	@Test
	public void testPixManagerServiceImpl() {
		// Act
		PixManagerServiceImpl pmsImpl = new PixManagerServiceImpl();

		// Assert
		assertEquals(sut.getClass(), pmsImpl.getClass());
	}

	@Test
	public void testPixManagerServiceImplString() {
		// Arrange
		String endPtAddr = "http://obhitademoacs01:8090/openempi-admin/services/PIXManager_Port_Soap12";

		// Act
		PixManagerServiceImpl pmsImpl = new PixManagerServiceImpl(endPtAddr);
		sut.setEndpoint(endPtAddr);
		
		pmsImpl.setPort(pIXManagerPortTypeMock);

		// Assert
		assertEquals(sut.getClass(), pmsImpl.getClass());
		assertEquals(pmsImpl.getEndpoint(), sut.getEndpoint());
		assertEquals(pmsImpl.getPort(), sut.getPort());
		
	}

	@Test
	public void testPixManagerPRPAIN201301UV02() {
		// Arrange
		PRPAIN201301UV02 pRPAIN201301UV02Mock = mock(PRPAIN201301UV02.class);
		MCCIIN000002UV01 mCCIIN000002UV01Mock = mock(MCCIIN000002UV01.class);
		when(
				pIXManagerPortTypeMock
						.pixManagerPRPAIN201301UV02(pRPAIN201301UV02Mock))
				.thenReturn(mCCIIN000002UV01Mock);

		// Act
		MCCIIN000002UV01 actualObj = sut
				.pixManagerPRPAIN201301UV02(pRPAIN201301UV02Mock);

		// Assert
		assertEquals(mCCIIN000002UV01Mock, actualObj);
	}

	@Test
	public void testPixManagerPRPAIN201302UV02() {
		// Arrange
		PRPAIN201302UV02 pRPAIN201302UV02Mock = mock(PRPAIN201302UV02.class);
		MCCIIN000002UV01 mCCIIN000002UV01Mock = mock(MCCIIN000002UV01.class);
		when(
				pIXManagerPortTypeMock
						.pixManagerPRPAIN201302UV02(pRPAIN201302UV02Mock))
				.thenReturn(mCCIIN000002UV01Mock);

		// Act
		MCCIIN000002UV01 actualObj = sut
				.pixManagerPRPAIN201302UV02(pRPAIN201302UV02Mock);

		// Assert
		assertEquals(mCCIIN000002UV01Mock, actualObj);
	}

	@Test
	public void testPixManagerPRPAIN201304UV02() {
		// Arrange
		PRPAIN201304UV02 pRPAIN201304UV02Mock = mock(PRPAIN201304UV02.class);
		MCCIIN000002UV01 mCCIIN000002UV01Mock = mock(MCCIIN000002UV01.class);
		when(
				pIXManagerPortTypeMock
						.pixManagerPRPAIN201304UV02(pRPAIN201304UV02Mock))
				.thenReturn(mCCIIN000002UV01Mock);

		// Act
		MCCIIN000002UV01 actualObj = sut
				.pixManagerPRPAIN201304UV02(pRPAIN201304UV02Mock);

		// Assert
		assertEquals(mCCIIN000002UV01Mock, actualObj);
	}

	@Test
	public void testPixManagerPRPAIN201309UV02() {
		// Arrange
		PRPAIN201309UV02 pRPAIN201309UV02Mock = mock(PRPAIN201309UV02.class);
		PRPAIN201310UV02 pRPAIN201310UV02Mock = mock(PRPAIN201310UV02.class);
		when(
				pIXManagerPortTypeMock
						.pixManagerPRPAIN201309UV02(pRPAIN201309UV02Mock))
				.thenReturn(pRPAIN201310UV02Mock);

		// Act
		PRPAIN201310UV02 actualObj = sut
				.pixManagerPRPAIN201309UV02(pRPAIN201309UV02Mock);

		// Assert
		assertEquals(pRPAIN201310UV02Mock, actualObj);
	}
	

}
