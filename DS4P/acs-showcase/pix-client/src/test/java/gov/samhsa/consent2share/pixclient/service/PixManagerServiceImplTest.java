package gov.samhsa.consent2share.pixclient.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import org.hl7.v3.types.MCCIIN000002UV01;
import org.hl7.v3.types.PRPAIN201301UV02;
import org.hl7.v3.types.PRPAIN201302UV02;
import org.hl7.v3.types.PRPAIN201304UV02;
import org.hl7.v3.types.PRPAIN201309UV02;
import org.hl7.v3.types.PRPAIN201310UV02;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openhie.openpixpdq.services.PIXManagerPortType;
import org.openhie.openpixpdq.services.PIXManagerService;

@RunWith(MockitoJUnitRunner.class)
public class PixManagerServiceImplTest {

	private URL wsdlURL;
	private String address;
	private QName serviceName;

	@InjectMocks
	PixManagerServiceImpl sut;

	@Mock
	PIXManagerPortType pIXManagerPortTypeMock;

	@Before
	public void setup() throws MalformedURLException {
		serviceName = new QName("urn:org:openhie:openpixpdq:services",
				"PIXManager_Service");

		address = "http://localhost:9000/services/PIXManager_Service";
		wsdlURL = ClassLoader.getSystemResource("PIXPDQManager.wsdl");
		sut.setPort(pIXManagerPortTypeMock);
	}

	@Test
	public void testPixManagerServiceImpl() {
		// Act
		PixManagerServiceImpl pmsImpl = new PixManagerServiceImpl("");

		// Assert
		assertEquals(sut.getClass(), pmsImpl.getClass());
	}

	@Test
	public void testPixManagerServiceImplString() {
		// Arrange
		String endPtAddr = address;

		// Act
		PixManagerServiceImpl pmsImpl = new PixManagerServiceImpl(endPtAddr);

		pmsImpl.setPort(pIXManagerPortTypeMock);

		// Assert
		assertEquals(sut.getClass(), pmsImpl.getClass());
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

	private PIXManagerPortType createPort() {
		PIXManagerPortType port = new PIXManagerService(wsdlURL, serviceName)
				.getPIXManagerPortSoap12();
		BindingProvider bp = (BindingProvider) port;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
				address);
		return port;
	}
}
