package gov.samhsa.acs.contexthandler;

import gov.samhsa.acs.common.exception.DS4PException;
import gov.samhsa.acs.xdsb.common.XdsbDocumentType;
import gov.samhsa.acs.xdsb.registry.wsclient.adapter.XdsbRegistryAdapter;
import gov.samhsa.acs.xdsb.repository.wsclient.adapter.XdsbRepositoryAdapter;

import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse.DocumentResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;

import org.herasaf.xacml.core.SyntaxException;
import org.herasaf.xacml.core.context.impl.RequestType;
import org.herasaf.xacml.core.policy.Evaluatable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class RequestGeneratorTest {
	
	RequestGenerator requestGenerator=new RequestGenerator (); 

	@Test
	public void testGetDate() {
		String date=requestGenerator.getDate();
		assertTrue (date.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}-\\d{4}"));
	}
	
	@Test
	public void testGenerateRequestString(){
		RequestGenerator sut=spy(requestGenerator);
		when(sut.getDate()).thenReturn("2013-12-23T14:48:07-0500");
		assertEquals("<Request xmlns=\"urn:oasis:names:tc:xacml:2.0:context:schema:os\"     xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">     <Subject>      <Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject\"       DataType=\"http://www.w3.org/2001/XMLSchema#string\">       <AttributeValue>123</AttributeValue>      </Attribute>      <Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject\"       DataType=\"http://www.w3.org/2001/XMLSchema#string\">       <AttributeValue>456</AttributeValue>      </Attribute>\t  <Attribute AttributeId=\"gov.samhsa.consent2share.purpose-of-use-code\"       DataType=\"http://www.w3.org/2001/XMLSchema#string\">       <AttributeValue>TREAT</AttributeValue>      </Attribute>     </Subject>     <Resource>      <Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\"       DataType=\"http://www.w3.org/2001/XMLSchema#string\">       <AttributeValue>789</AttributeValue>      </Attribute>     </Resource>     <Action>      <Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\"       DataType=\"http://www.w3.org/2001/XMLSchema#string\">       <AttributeValue>write</AttributeValue>      </Attribute>     </Action>     <Environment>\t\t<Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime\"       DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\">       <AttributeValue>2013-12-23T14:48:07-0500</AttributeValue>      </Attribute>\t </Environment>    </Request>",sut.generateRequestString("123", "456", "TREAT", "789"));
	}
	
	@Test
	public void testGenerateRequest() throws SyntaxException{
		RequestGenerator sut=spy(requestGenerator);
		RequestType request=mock(RequestType.class);
		doReturn(request).when(sut).unmarshalRequest(any(InputStream.class));
		when(sut.getDate()).thenReturn("2013-12-23T14:48:07-0500");
		sut.generateRequest("123", "456", "TREAT", "789");
		verify (sut,times(1)).unmarshalRequest(any(InputStream.class));
	}
	
	@Test
	public void testGenerateRequestWhenThereIsSyntaxErrorAndExceptionIsHandled() throws SyntaxException{
		RequestGenerator sut=spy(requestGenerator);
		doThrow(new SyntaxException()).when(sut).unmarshalRequest(any(InputStream.class));
		when(sut.getDate()).thenReturn("2013-12-23T14:48:07-0500");
		sut.generateRequest("123", "456", "TREAT", "789");
	}
	

}
