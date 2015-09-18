package gov.samhsa.acs.contexthandler.pg;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.contexthandler.pg.RequestGenerator;

import java.io.InputStream;

import org.herasaf.xacml.core.SyntaxException;
import org.herasaf.xacml.core.context.impl.RequestType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class RequestGeneratorTest {
	
	RequestGenerator requestGenerator=new RequestGenerator (); 

	@Test
	public void testGetDate() {
		String date=requestGenerator.getDate();
		assertTrue (date.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}-\\d{4}"));
	}
	
	//@Test
	public void testGenerateRequestString(){
		RequestGenerator sut=spy(requestGenerator);
		when(sut.getDate()).thenReturn("2013-12-23T14:48:07-0500");
		String expected = "<xacml-context:Request xmlns:xacml-context=\"urn:oasis:names:tc:xacml:2.0:context:schema:os\"> " +
               " <xacml-context:Subject SubjectCategory=\"urn:oasis:names:tc:xacml:1.0:subject-category:access-subject\"> " +
                              "  <xacml-context:Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"> " +
                              "                 <xacml-context:AttributeValue>1568797520</xacml-context:AttributeValue> " +
                              "  </xacml-context:Attribute> " +
                              "  <xacml-context:Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"> " +
                              "                  <xacml-context:AttributeValue>1285969170</xacml-context:AttributeValue> " +
                              "  </xacml-context:Attribute> " +
                              "  <xacml-context:Attribute AttributeId=\"urn:oasis:names:tc:xspa:1.0:subject:purposeofuse\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"> " +
                              "                  <xacml-context:AttributeValue>TREATMENT</xacml-context:AttributeValue> " +
                               "  </xacml-context:Subject> " +
                              "  <Resource xmlns=\"urn:oasis:names:tc:xacml:2.0:context:schema:os\"> " +
                              "  <Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:resource:typeCode\" DataType=\"http://www.w3.org/2001/XMLSchema#string\" XdsId=\"urn:uuid:f0306f51-975f-434e-a61c-c59651d33983\"> " +
                              "                  <AttributeValue>34133-9</AttributeValue> " +
                              "  </Attribute> " +
                              "  <Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:resource:practiceSettingCode\" DataType=\"http://www.w3.org/2001/XMLSchema#string\" XdsId=\"urn:uuid:cccf5598-8b07-4b77-a05e-ae952c785ead\"> " +
                              "                  <AttributeValue>Psychiatry</AttributeValue> " +
                              "  </Attribute> " +
                              "  <Attribute AttributeId=\"xacml:status\" DataType=\"http://www.w3.org/2001/XMLSchema#string\" XdsId=\"status\"> " +
                              "                  <AttributeValue>urn:oasis:names:tc:ebxml-regrep:StatusType:Approved</AttributeValue> " +
                              "  </Attribute> " +
                              "  </Resource> " +
                              "  <xacml-context:Action> " +
                              "  <xacml-context:Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"> " +
                              "                 <xacml-context:AttributeValue>xdsquery</xacml-context:AttributeValue> " +
                              "  </xacml-context:Attribute> " +
                              "  </xacml-context:Action> " +
                              "  <xacml-context:Environment> " +
                              "  <xacml-context:Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime\" DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\"> " +
                              "                 <xacml-context:AttributeValue>2014-07-18T09:36:25.588Z</xacml-context:AttributeValue> " +
                              "  </xacml-context:Attribute> " +
                              "  </xacml-context:Environment> " +
                              "  </xacml-context:Request>";
		String actual = sut.generateRequestString("1285969170", "1568797520", "TREATMENT");
		String result = actual.replaceAll("\\s+", "");
		 expected = expected.replaceAll("\\s+", "");
		 Assert.assertEquals(expected, result);

	
	
	
	}
	
	@Test
	public void testGenerateRequest() throws SyntaxException{
		RequestGenerator sut=spy(requestGenerator);
		RequestType request=mock(RequestType.class);
		doReturn(request).when(sut).unmarshalRequest(any(InputStream.class));
		when(sut.getDate()).thenReturn("2013-12-23T14:48:07-0500");
		sut.generateRequest("123", "456", "TREAT");
		verify (sut,times(1)).unmarshalRequest(any(InputStream.class));
	}
	
	@Test
	public void testGenerateRequestWhenThereIsSyntaxErrorAndExceptionIsHandled() throws SyntaxException{
		RequestGenerator sut=spy(requestGenerator);
		doThrow(new SyntaxException()).when(sut).unmarshalRequest(any(InputStream.class));
		when(sut.getDate()).thenReturn("2013-12-23T14:48:07-0500");
		sut.generateRequest("123", "456", "TREAT");
	}
	

}
