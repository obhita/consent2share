package gov.samhsa.acs.pep.xdsb;

import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryErrorList;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponseType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RegistryErrorListSetterPepImplTest {
	

	@Test
	public void testSetRegistryErrorListWhenIsPartial() throws SecurityException, NoSuchFieldException {
		RegistryErrorListSetterPepImpl registryErrorListSetterPepImpl
			=new RegistryErrorListSetterPepImpl();
		RegistryErrorListSetterPepImpl sut=spy(registryErrorListSetterPepImpl);
		RetrieveDocumentSetResponse response=new RetrieveDocumentSetResponse();
		RegistryErrorList registryErrorList=mock(RegistryErrorList.class);
		RegistryResponseType errorResponse=mock(RegistryResponseType.class);
		when(sut.getNewRegistryResponseType()).thenReturn(errorResponse);
		boolean isPartial=true;
		sut.setRegistryErrorList(response, registryErrorList, isPartial);
		verify(errorResponse,times(1)).setStatus("urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:PartialSuccess");
	}
	
	@Test
	public void testSetRegistryErrorListWhenIsNotPartial() throws SecurityException, NoSuchFieldException {
		RegistryErrorListSetterPepImpl registryErrorListSetterPepImpl
			=new RegistryErrorListSetterPepImpl();
		RegistryErrorListSetterPepImpl sut=spy(registryErrorListSetterPepImpl);
		RetrieveDocumentSetResponse response=new RetrieveDocumentSetResponse();
		RegistryErrorList registryErrorList=mock(RegistryErrorList.class);
		RegistryResponseType errorResponse=mock(RegistryResponseType.class);
		doReturn(errorResponse).when(sut).getNewRegistryResponseType();
		boolean isPartial=false;
		sut.setRegistryErrorList(response, registryErrorList, isPartial);
		verify(errorResponse,times(1)).setStatus("urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Failure");
	}
	
}
