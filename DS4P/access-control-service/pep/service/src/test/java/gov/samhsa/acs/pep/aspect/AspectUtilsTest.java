package gov.samhsa.acs.pep.aspect;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;
import gov.samhsa.acs.common.tool.exception.SimpleMarshallerException;

import java.lang.reflect.Method;
import java.util.List;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AspectUtilsTest {

	@Mock
	private SimpleMarshaller marshaller;
	
	@InjectMocks	
	AspectUtils sut = new AspectUtils();
	
	/** The adhoc query. */
	private AdhocQueryRequest adhocQuery;
	
	/** The simple marshaller. */
	private SimpleMarshallerImpl simpleMarshaller;

	/** The adhoc query string. */
	private String adhocQueryString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ns3:AdhocQueryRequest  	xmlns:ns2=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\"  	xmlns=\"urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0\"  	xmlns:ns3=\"urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0\"><ns3:ResponseOption returnType=\"LeafClass\" returnComposedObjects=\"true\"/><AdhocQuery id=\"urn:uuid:14d4debf-8f97-4251-9a74-a90016b0af0d\"><Slot name=\"$XDSDocumentEntryPatientId\"><ValueList><Value>'e46b6650-47ea-11e3-9d13-00155d84621a^^^&amp;2.16.840.1.113883.4.357&amp;ISO'</Value></ValueList></Slot><Slot name=\"$XDSDocumentEntryStatus\"><ValueList><Value>('urn:oasis:names:tc:ebxml-regrep:StatusType:Approved')</Value></ValueList></Slot><Slot name=\"$XDSDocumentEntryFormatCode\"><ValueList><Value>'2.16.840.1.113883.10.20.1^^HITSP'</Value></ValueList></Slot></AdhocQuery></ns3:AdhocQueryRequest>";

	@Before
	public void setUp() throws Exception {
		simpleMarshaller = new SimpleMarshallerImpl();
		adhocQuery = simpleMarshaller.unmarshallFromXml(
				AdhocQueryRequest.class, adhocQueryString);		
	}

	@Test
	public void testBeforeMethod() {

		//Arrange
	
		JoinPoint jpMock = mock(JoinPoint.class);
		
		CodeSignature csMock = mock(CodeSignature.class);
		when(jpMock.getSignature()).thenReturn((CodeSignature)csMock);
		
		when(csMock.getName()).thenReturn("fakeName");
		when(csMock.getParameterNames()).thenReturn(new String[]{"fake1","fake2"});
		
		Object[] obj = new Object[]{new Object(), new Object()};
		when(jpMock.getArgs()).thenReturn(obj);
		
		when(jpMock.getTarget()).thenReturn(new LoggingAspect());
		//Act
		 sut.beforeMethod(jpMock);
		
		//Assert
		assertNotNull(sut.getMarshaller());		
	}
	
	@Test
	public void testBeforeMethod_nullpname() {

		//Arrange
		Object objMock = mock(Object.class);
		
		JoinPoint jpMock = mock(JoinPoint.class);
		
		CodeSignature csMock = mock(CodeSignature.class);
		when(jpMock.getSignature()).thenReturn((CodeSignature)csMock);
		
		when(csMock.getName()).thenReturn("fakeName");
		when(csMock.getParameterNames()).thenReturn(new String[]{""});
		
		Object[] obj = new Object[]{objMock};
		when(jpMock.getArgs()).thenReturn(obj);
		
		when(jpMock.getTarget()).thenReturn(new LoggingAspect());
		//Act
		 sut.beforeMethod(jpMock);
		
		//Assert
		assertNotNull(sut.getMarshaller());
	
	}	
	
	@Test
	public void testAfterMethod() throws NoSuchMethodException, SecurityException {

		//Arrange
		JoinPoint jpMock = setJointPoint();
		
		String result = new String("resultString");
		//Act
		 sut.afterMethod(jpMock,result, 12l);
		
		//Assert
		assertNotNull(sut.getMarshaller());		
	}
	
	@Test
	public void testAfterMethod_objReturn() throws NoSuchMethodException, SecurityException {

		//Arrange
		JoinPoint jpMock = setJointPoint();
		
		//Act
		 sut.afterMethod(jpMock,adhocQuery, 12l);
		
		//Assert
		assertNotNull(sut.getMarshaller());		
	}	
	

	
	@Test
	public void testAfterMethod_objReturnBean() throws NoSuchMethodException, SecurityException {

		//Arrange
		JoinPoint jpMock = setJointPoint();
		ReturnTest testClass = new ReturnTest();
		testClass.setName("testName");
		testClass.setAge("testAget");
		//Act
		 sut.afterMethod(jpMock,testClass, 12l);
		
		//Assert
		assertNotNull(sut.getMarshaller());		
	}
	
	@Test
	public void testToString() {
		//arrage
		@SuppressWarnings("unchecked")
		List<String> listMock = mock(List.class);
		
		//assert
		assertNotNull(sut.toString(null));
		assertNotNull(sut.toString(new Long(10)));
		assertNotNull(sut.toString(new Boolean(true)));
		assertNotNull(sut.toString(new Double(2)));
		assertNotNull(sut.toString(new Integer(2)));
		assertNotNull(sut.toString(listMock));
		
	}
	
	
	
	@Test
	public void testGetObjectdetails() throws NoSuchMethodException, SecurityException {

		//Arrange
	
		ReturnTest testClass = new ReturnTest();
		testClass.setName("testName");
		testClass.setAge("testAget");
		//Act
		String result =  sut.getObjectdetails(adhocQuery);
		
		//Assert
		assertNotNull(result);		
	}	
	
	
	@Test
	public void testGetInXMLFormat() throws SimpleMarshallerException{
		//arrange
		when(marshaller.marshall("StringTest")).thenThrow(new SimpleMarshallerException("test"));
		//act
		sut.getInXMLFormat("StingTest");
	}
	
	
	private JoinPoint setJointPoint() throws NoSuchMethodException, SecurityException{
		JoinPoint jpMock = mock(JoinPoint.class);
		
		MethodSignature ms = mock(MethodSignature.class);
		Method method = this.getClass().getMethod("testBeforeMethod_nullpname");
		when(ms.getMethod()).thenReturn(method);
		
		when(jpMock.getSignature()).thenReturn(ms);
		
		when(ms.getName()).thenReturn("fakeName");
		when(ms.getReturnType()).thenReturn(String.class);
		
	
		when(jpMock.getTarget()).thenReturn(new LoggingAspect());
		
		return jpMock;
		
		
	}
	

}


class ReturnTest{
	
	private String name;
	private String age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	
	
	
}