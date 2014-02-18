package gov.samhsa.acs.pep;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

public interface PolicyTrying {
	String tryPolicy(String c32Xml, String xacmlPolicy) throws ParserConfigurationException, SAXException, IOException, TransformerException;
}
