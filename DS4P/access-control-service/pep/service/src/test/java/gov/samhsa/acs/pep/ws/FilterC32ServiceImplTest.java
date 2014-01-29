package gov.samhsa.acs.pep.ws;

import gov.samhsa.acs.pep.Pep;
import gov.samhsa.ds4ppilot.schema.pep.RegisteryStoredQueryRequest;
import gov.va.ehtac.ds4p.ws.EnforcePolicy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

/**
 * The Class ConsentServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class FilterC32ServiceImplTest {
	
	@Mock
	Pep pep;
	
	@InjectMocks
	FilterC32ServiceImpl filterC32ServiceImpl=new FilterC32ServiceImpl();

	@Test
	public void testRegisteryStoredQuery() {
		filterC32ServiceImpl.setPep(pep);
		RegisteryStoredQueryRequest parameters=mock(RegisteryStoredQueryRequest.class);
		EnforcePolicy policy=mock(EnforcePolicy.class);
		when(parameters.getPatientId()).thenReturn("1");
		when(parameters.getEnforcePolicy()).thenReturn(policy);
		filterC32ServiceImpl.registeryStoredQuery(parameters);
		verify(pep,times(1)).registeryStoredQueryRequest("1", policy);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAfterPropertiesSetWhenPepIsNull() throws Exception{
		filterC32ServiceImpl.setPep(null);
		filterC32ServiceImpl.afterPropertiesSet();
	}
	
	@Test
	public void testAfterPropertiesSetWhenPepIsNotNull() throws Exception{
		filterC32ServiceImpl.setPep(pep);
		filterC32ServiceImpl.afterPropertiesSet();
	}

}
