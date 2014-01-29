package gov.samhsa.acs.common.util;

import static org.junit.Assert.*;

import java.io.IOException;
import javax.xml.transform.TransformerException;

import org.junit.Test;
import org.w3c.dom.Document;

import gov.samhsa.acs.common.util.XmlHelper;;

public class XmlHelperTest {
	
	@Test
	public void test() throws IOException, TransformerException, Exception{
		XmlHelper xmlHelper=new XmlHelper();
		Document doc=XmlHelper.loadDocument("<note><to>Tove</to><from>Jani</from><heading>Reminder</heading><body>Don't forget me this weekend!</body></note>");
		assertEquals(doc.getClass().getName(),"org.apache.xerces.dom.DeferredDocumentImpl");
		assertEquals(XmlHelper.converXmlDocToString(doc),"<?xml version=\"1.0\" encoding=\"UTF-8\"?><note>    <to>Tove</to>    <from>Jani</from>    <heading>Reminder</heading>    <body>Don't forget me this weekend!</body></note>");
	}

}
