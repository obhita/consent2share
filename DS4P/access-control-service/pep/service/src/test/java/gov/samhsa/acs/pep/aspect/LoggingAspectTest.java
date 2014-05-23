package gov.samhsa.acs.pep.aspect;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.common.tool.SimpleMarshaller;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.CodeSignature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LoggingAspectTest {
	
	@Mock
	private SimpleMarshaller marshaller;
	
	@Mock
	private AspectUtils aspectUtils;
	
	@InjectMocks	
	LoggingAspect sut = new LoggingAspect();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testLogAndExecute() throws Throwable {
		//Arrange
		Object objMock = mock(Object.class);
		ProceedingJoinPoint pjp = mock(ProceedingJoinPoint.class);
		when(pjp.proceed()).thenReturn(objMock);
			
		//Act
		Object obj = sut.logAndExecute(pjp);
		
		//Assert
		assertNotNull(obj);
	}

	@Test
	public void testLogAndExecute_nullAsUtMar() throws Throwable {
		//Arrange
		Object objMock = mock(Object.class);
		aspectUtils = null;
		marshaller = null;
		sut.setAspectUtils(aspectUtils);
		sut.setMarshaller(marshaller);
		
		ProceedingJoinPoint pjp = mock(ProceedingJoinPoint.class);
		when(pjp.proceed()).thenReturn(objMock);
		
		CodeSignature csMock = mock(CodeSignature.class);
		when(pjp.getSignature()).thenReturn((CodeSignature)csMock);
		
		when(csMock.getName()).thenReturn("fakeName");
		when(csMock.getParameterNames()).thenReturn(new String[]{"fake1","fake2"});
		
		Object[] obj = new Object[]{objMock};
		when(pjp.getArgs()).thenReturn(obj);
		
		when(pjp.getTarget()).thenReturn(new LoggingAspect());
		//Act
		Object obj1 = sut.logAndExecute(pjp);
		
		//Assert
		assertNotNull(obj1);
	}	
	
	@Test
	public void testLogAndExecute_nullAsUt() throws Throwable {
		//Arrange
		Object objMock = mock(Object.class);
		aspectUtils = null;
		sut.setAspectUtils(aspectUtils);
		ProceedingJoinPoint pjp = mock(ProceedingJoinPoint.class);
		when(pjp.proceed()).thenReturn(objMock);
		
		CodeSignature csMock = mock(CodeSignature.class);
		when(pjp.getSignature()).thenReturn((CodeSignature)csMock);
		
		when(csMock.getName()).thenReturn("fakeName");
		when(csMock.getParameterNames()).thenReturn(new String[]{"fake1","fake2"});
		
		Object[] obj = new Object[]{objMock};
		when(pjp.getArgs()).thenReturn(obj);
		
		when(pjp.getTarget()).thenReturn(new LoggingAspect());
		//Act
		Object obj1 = sut.logAndExecute(pjp);
		
		//Assert
		assertNotNull(obj1);
	}	

	
	@Test
	public void testOtherMethods() throws Throwable {
		//Arrange
		//Act
		sut.allContextHandlerClassMethods();
		sut.allPepMethods();
		sut.loggableAnnotation();
		sut.noLogAnnotation();

		
		//Assert
		assertNotNull(sut.getMarshaller());
		assertNotNull(sut.getAspectUtils());
	}	

}
