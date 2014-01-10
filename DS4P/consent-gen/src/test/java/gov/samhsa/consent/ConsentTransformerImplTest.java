/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.consent;

import static org.mockito.Mockito.mock;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.ElementNameAndAttributeQualifier;
import org.custommonkey.xmlunit.ElementNameAndTextQualifier;
import org.custommonkey.xmlunit.ElementNameQualifier;
import org.custommonkey.xmlunit.XMLUnit;
import org.custommonkey.xmlunit.examples.RecursiveElementNameAndTextQualifier;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.xml.sax.SAXException;

/**
 * @author sadhana.chandra
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class ConsentTransformerImplTest {

	private static final String SAMPLE_DATE_START = "2013-09-04T08:00:00-0500";
	private static final String SAMPLE_DATE_END = "2014-09-04T08:00:00-0500";
	private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

	@InjectMocks
	ConsentTransformerImpl cstl = new ConsentTransformerImpl();

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

	}

	/**
	 * Test method for
	 * {@link gov.samhsa.consent.ConsentTransformerImpl#transform(gov.samhsa.consent.ConsentDto, java.lang.String)}
	 * .
	 * 
	 * @throws TransformerException
	 * @throws JAXBException
	 * @throws ConsentGenException
	 */
	@Test
	public void testTransform() throws ConsentGenException {
		ConsentDto consentDto = new ConsentDto();
		try {
			consentDto = getConsentDto();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String actualTransform = cstl
				.transform(consentDto, "c2cdar2.xsl", null);

		String expectedResult = convertXMLFileToString("consentSaxTransformSample.xml");

		/**
		 * list of regular expressions that custom difference listener used
		 * during xml comparison.
		 */
		final List<String> ignorableXPathsRegex = new ArrayList<String>();
		ignorableXPathsRegex
				.add("\\/ClinicalDocument\\[1\\]/effectiveTime\\[1\\]\\/@value");

		DetailedDiff diff = compareXMLs(expectedResult, actualTransform,
				ignorableXPathsRegex);
		// Diff provides two methods for comparison identical and similar.
		// Identical expects content and order of elements to be same.
		// Similar is less stricter and allows change in order
		Assert.assertEquals(true, diff.similar());
	}

	/**
	 * Test method for
	 * {@link gov.samhsa.consent.ConsentTransformerImpl#jaxbMarshall(gov.samhsa.consent.ConsentDto)}
	 * .
	 * 
	 * @throws JAXBException
	 */
	@Test
	public void testJaxbMarshall() throws JAXBException {
		ConsentDto consentDto = new ConsentDto();
		try {
			consentDto = getConsentDto();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		ByteArrayOutputStream marshalresult = cstl.jaxbMarshall(consentDto);

		String xmlContent = convertXMLFileToString("consentJaxBTransformSample.xml");

		// consentStart and consentEnd are java.util.Date type, so they are
		// affected by daylight saving and hard to test jaxbMarshall operation
		// by comparing the output with a static file (based on the current
		// daylight saving status, it changes the time zone).
		// Therefore, they are ignored as below.
		final List<String> ignorableXPathsRegex = new ArrayList<String>();
		ignorableXPathsRegex
				.add("\\/ConsentExport\\[1\\]/consentStart\\[1\\]/");
		ignorableXPathsRegex.add("\\/ConsentExport\\[1\\]/consentEnd\\[1\\]/");
		DetailedDiff diff = compareXMLs(xmlContent, marshalresult.toString(),
				ignorableXPathsRegex);

		// Diff provides two methods for comparison identical and similar.
		// Identical expects content and order of elements to be same.
		// Similar is less stricter and allows change in order
		Assert.assertEquals(true, diff.similar());
	}

	@Test
	public void testJaxbMarshall_Empty_consent() throws JAXBException {
		String xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n";

		ConsentDto consentDto = mock(ConsentDto.class);

		ByteArrayOutputStream marshalresult = cstl.jaxbMarshall(consentDto);

		String xmlContent = "<ConsentExport/>\n";

		Assert.assertEquals(xmlHeader + xmlContent, marshalresult.toString());
	}

	@Test
	public void testJaxbMarshall_by_version() throws JAXBException {
		String xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
		Integer version = 1;
		ConsentDto consentDto = new ConsentDto();
		consentDto.setVersion(version);
		ByteArrayOutputStream marshalresult = cstl.jaxbMarshall(consentDto);
		String xmlContent = "<ConsentExport><version>1</version></ConsentExport>";
		String result = marshalresult.toString().replaceAll("\\s+", "");
		String expected = (xmlHeader + xmlContent).replaceAll("\\s+", "");

		Assert.assertEquals(expected, result);
	}

	@Test
	public void testJaxbMarshall_by_StartDate() throws JAXBException {

		String xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		ConsentDto consentDto = new ConsentDto();
		try {
			consentDto.setConsentStart(dateFormat.parse(SAMPLE_DATE_START));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ByteArrayOutputStream marshalresult = cstl.jaxbMarshall(consentDto);
		String xmlContent = "<ConsentExport> <consentStart>"
				+ dateFormat.format(consentDto.getConsentStart())
				+ "</consentStart></ConsentExport>";
		String result = marshalresult.toString().replaceAll("\\s+", "");
		String expected = (xmlHeader + xmlContent).replaceAll("\\s+", "");

		Assert.assertEquals(expected, result);
	}

	@Test
	public void testJaxbMarshall_by_EndDate() throws JAXBException {

		String xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		ConsentDto consentDto = new ConsentDto();
		try {
			consentDto.setConsentEnd(dateFormat.parse(SAMPLE_DATE_START));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ByteArrayOutputStream marshalresult = cstl.jaxbMarshall(consentDto);
		String xmlContent = "<ConsentExport> <consentEnd>"
				+ dateFormat.format(consentDto.getConsentEnd())
				+ "</consentEnd></ConsentExport>";
		String result = marshalresult.toString().replaceAll("\\s+", "");
		String expected = (xmlHeader + xmlContent).replaceAll("\\s+", "");

		Assert.assertEquals(expected, result);
	}

	@Test
	public void testJaxbMarshall_by_SignedDate() throws JAXBException {

		String xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		ConsentDto consentDto = new ConsentDto();
		try {
			consentDto.setSignedDate(dateFormat.parse(SAMPLE_DATE_START));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ByteArrayOutputStream marshalresult = cstl.jaxbMarshall(consentDto);
		String xmlContent = "<ConsentExport> <signedDate>"
				+ dateFormat.format(consentDto.getSignedDate())
				+ "</signedDate></ConsentExport>";
		String result = marshalresult.toString().replaceAll("\\s+", "");
		String expected = (xmlHeader + xmlContent).replaceAll("\\s+", "");

		Assert.assertEquals(expected, result);
	}

	@Test
	public void testJaxbMarshall_by_RevocationDate() throws JAXBException {

		String xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		ConsentDto consentDto = new ConsentDto();
		try {
			consentDto.setRevocationDate(dateFormat.parse(SAMPLE_DATE_START));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ByteArrayOutputStream marshalresult = cstl.jaxbMarshall(consentDto);
		String xmlContent = "<ConsentExport> <revocationDate>"
				+ dateFormat.format(consentDto.getRevocationDate())
				+ "</revocationDate></ConsentExport>";
		String result = marshalresult.toString().replaceAll("\\s+", "");
		String expected = (xmlHeader + xmlContent).replaceAll("\\s+", "");

		Assert.assertEquals(expected, result);
	}

	/*
	 * @Test public void testJaxbMarshall_Marshal_invalid_Object_Exception()
	 * throws JAXBException {
	 * 
	 * thrown.expect(JAXBException.class);
	 * 
	 * cstl.jaxbMarshall();
	 * 
	 * }
	 */

	/**
	 * Test method for
	 * {@link gov.samhsa.consent.ConsentTransformerImpl#saxonTransform(java.lang.String, javax.xml.transform.stream.StreamSource)}
	 * .
	 * 
	 * @throws TransformerException
	 * @throws JAXBException
	 */
	@Test
	public void testSaxonTransform() throws TransformerException, JAXBException {

		String xslID = "";
		StreamSource streamSource;

		ConsentDto consentDto = new ConsentDto();
		try {
			consentDto = getConsentDto();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		ByteArrayOutputStream marshalresult = cstl.jaxbMarshall(consentDto);

		streamSource = new StreamSource(new ByteArrayInputStream(
				marshalresult.toByteArray()));
		URL cd2 = getClass().getClassLoader().getResource("c2cdar2.xsl");
		xslID = cd2.toString();

		StreamResult srcdar = cstl.saxonTransform(xslID, streamSource, null);

		String expectedResult = convertXMLFileToString("consentSaxTransformSample.xml");

		/**
		 * list of regular expressions that custom difference listener used
		 * during xml comparison.
		 */
		final List<String> ignorableXPathsRegex = new ArrayList<String>();
		ignorableXPathsRegex
				.add("\\/ClinicalDocument\\[1\\]/effectiveTime\\[1\\]\\/@value");

		DetailedDiff diff = compareXMLs(expectedResult, srcdar
				.getOutputStream().toString(), ignorableXPathsRegex);
		// Diff provides two methods for comparison identical and similar.
		// Identical expects content and order of elements to be same.
		// Similar is less stricter and allows change in order
		Assert.assertEquals(true, diff.similar());

	}

	@Test
	public void testSaxonTransform_Exception() throws TransformerException,
			JAXBException {

		thrown.expect(TransformerConfigurationException.class);
		// thrown.expectMessage("Error in SAXON Transfroming");

		String xslID = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <xsl:stylesheet "
				+ " xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" "
				+ "   xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" exclude-result-prefixes=\"xs\" version=\"2.0\">";

		StreamSource streamSource;

		ConsentDto consentDto = new ConsentDto();
		try {
			consentDto = getConsentDto();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		ByteArrayOutputStream marshalresult = cstl.jaxbMarshall(consentDto);

		streamSource = new StreamSource(new ByteArrayInputStream(
				marshalresult.toByteArray()));
		// URL cd2 = getClass().getClassLoader().getResource("c2cdar2.xsl");
		// xslID = cd2.toString();

		cstl.saxonTransform(xslID, streamSource, null);
		System.out.println("inside");
	}

	private void setXMLUnitConfig() {

		XMLUnit.setIgnoreWhitespace(Boolean.TRUE);
		XMLUnit.setIgnoreComments(Boolean.TRUE);
		XMLUnit.setIgnoreDiffBetweenTextAndCDATA(Boolean.TRUE);
		XMLUnit.setIgnoreAttributeOrder(Boolean.TRUE);
	}

	private DetailedDiff compareXMLs(String expectedResult,
			String actualResult, List<String> ignorableXPathsRegex) {

		DetailedDiff diff = null;
		try {
			setXMLUnitConfig();

			diff = new DetailedDiff((XMLUnit.compareXML(expectedResult,
					actualResult)));
			diff.overrideElementQualifier(new ElementNameAndTextQualifier());
			diff.overrideElementQualifier(new ElementNameQualifier());
			diff.overrideElementQualifier(new ElementNameAndAttributeQualifier());
			diff.overrideElementQualifier(new RecursiveElementNameAndTextQualifier());

			if (ignorableXPathsRegex != null) {
				RegexBasedDifferenceListener ignorableElementsListener = new RegexBasedDifferenceListener(
						ignorableXPathsRegex);
				/** setting our custom difference listener */
				diff.overrideDifferenceListener(ignorableElementsListener);
			}

			@SuppressWarnings("unchecked")
			List<Difference> differences = diff.getAllDifferences();
			for (Object object : differences) {
				Difference difference = (Difference) object;
				System.out.println("***********************");
				System.out.println(difference);
				System.out.println("***********************");
			}

		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return diff;

	}

	private ConsentDto getConsentDto() throws ParseException {

		ConsentDto consentDto = new ConsentDto();
		consentDto
				.setConsentReferenceid("0f498c11-9211-4cf2-89e0-1f968d5dce7e");

		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		SimpleDateFormat dateIntegerFormat = new SimpleDateFormat("yyyyMMdd");
		// consentDto.setConsentStart(dateIntegerFormat.parse("20130904"));
		// consentDto.setConsentEnd(dateIntegerFormat.parse("20140904"));
		consentDto.setConsentStart(dateFormat.parse(SAMPLE_DATE_START));
		consentDto.setConsentEnd(dateFormat.parse(SAMPLE_DATE_END));
		// null
		// consentDto.setSignedDate();
		consentDto.setVersion(new Integer("53"));
		// null
		// consentDto.setRevocationDate();

		setPatientDto(consentDto, dateIntegerFormat);
		setProvidersPermittedToDisclose(consentDto);
		setProvidersDisclosureIsMadeTo(consentDto);
		setOrganizationalProvidersPermittedToDisclose(consentDto);
		setOrganizationalProvidersDisclosureIsMadeTo(consentDto);
		setDoNotShareClinicalDocumentTypeCodes(consentDto);
		setDoNotShareClinicalDocumentSectionTypeCodes(consentDto);
		setDoNotShareSensitivityPolicyCodes(consentDto);
		setShareForPurposeOfUseCodes(consentDto);
		setDoNotShareClinicalConceptCodes(consentDto);

		return consentDto;

	}

	private void setPatientDto(ConsentDto consentDto,
			SimpleDateFormat dateFormat) throws ParseException {
		// Create patientdto
		PatientDto patientDto = new PatientDto();
		patientDto.setAddressCity("columbia");
		patientDto.setAddressCountryCode("US");
		patientDto.setAddressPostalCode("21246");
		patientDto.setAddressStateCode("MD");
		patientDto.setAddressStreetAddressLine("7175 Columbia Gateway Dr");
		patientDto.setAdministrativeGenderCode("M");
		patientDto.setBirthDate(dateFormat.parse("19830218"));
		patientDto.setEmail("consent2share@outlook.com");
		patientDto.setFirstName("Albert");
		patientDto.setLastName("Smith");
		patientDto.setPatientIdNumber("1");

		consentDto.setPatientDto(patientDto);

	}

	private void setProvidersPermittedToDisclose(ConsentDto consentDto) {

		// Create individualProviderDto
		Set<IndividualProviderDto> indiprovidersPermittedToDisclose = new HashSet<IndividualProviderDto>();

		IndividualProviderDto individualProviderDto = new IndividualProviderDto();

		individualProviderDto.setEnumerationDate("2009-10-08T00:00:00-0500");
		individualProviderDto
				.setFirstLinePracticeLocationAddress("26520 CACTUS AVE");
		individualProviderDto.setFirstName("DAN");
		individualProviderDto.setLastName("HOANG");
		individualProviderDto.setMiddleName("TAN");
		individualProviderDto.setNamePrefix("MR.");
		individualProviderDto.setNameSuffix("");
		individualProviderDto.setNpi("1740515725");
		individualProviderDto
				.setPracticeLocationAddressCityName("MORENO VALLEY");
		individualProviderDto.setPracticeLocationAddressCountryCode("US");
		individualProviderDto.setPracticeLocationAddressPostalCode("925553927");
		individualProviderDto.setPracticeLocationAddressStateName("CA");
		individualProviderDto
				.setPracticeLocationAddressTelephoneNumber("9514864490");
		individualProviderDto.setSecondLinePracticeLocationAddress("");

		indiprovidersPermittedToDisclose.add(individualProviderDto);

		// second one
		individualProviderDto = new IndividualProviderDto();
		individualProviderDto.setEnumerationDate("2009-10-08T00:00:00-0500");
		individualProviderDto
				.setFirstLinePracticeLocationAddress("5570 STERRETT PL");
		individualProviderDto.setFirstName("GEORGE");
		individualProviderDto.setLastName("CARLSON");
		individualProviderDto.setMiddleName("LEWIS");
		individualProviderDto.setNamePrefix("");
		individualProviderDto.setNameSuffix("JR.");
		individualProviderDto.setNpi("1346575297");
		individualProviderDto.setPracticeLocationAddressCityName("COLUMBIA");
		individualProviderDto.setPracticeLocationAddressCountryCode("US");
		individualProviderDto.setPracticeLocationAddressPostalCode("210442641");
		individualProviderDto.setPracticeLocationAddressStateName("MD");
		individualProviderDto
				.setPracticeLocationAddressTelephoneNumber("4107300552");
		individualProviderDto.setSecondLinePracticeLocationAddress("SUITE 101");
		indiprovidersPermittedToDisclose.add(individualProviderDto);

		consentDto
				.setProvidersPermittedToDisclose(indiprovidersPermittedToDisclose);

	}

	private void setProvidersDisclosureIsMadeTo(ConsentDto consentDto) {

		Set<IndividualProviderDto> indiprovidersDisclosureIsMadeTo = new HashSet<IndividualProviderDto>();

		IndividualProviderDto individualProviderDto = new IndividualProviderDto();

		individualProviderDto.setEnumerationDate("2009-10-08T00:00:00-0500");
		individualProviderDto
				.setFirstLinePracticeLocationAddress("600 N WOLFE ST");
		individualProviderDto.setFirstName("MONICA");
		individualProviderDto.setLastName("VAN DONGEN");
		individualProviderDto.setMiddleName("LYNNE");
		individualProviderDto.setNamePrefix("MS.");
		individualProviderDto.setNameSuffix("");
		individualProviderDto.setNpi("1083949036");
		individualProviderDto.setPracticeLocationAddressCityName("BALTIMORE");
		individualProviderDto.setPracticeLocationAddressCountryCode("US");
		individualProviderDto.setPracticeLocationAddressPostalCode("212870005");
		individualProviderDto.setPracticeLocationAddressStateName("MD");
		individualProviderDto
				.setPracticeLocationAddressTelephoneNumber("4106141937");
		individualProviderDto
				.setSecondLinePracticeLocationAddress("BLALOCK 412");

		indiprovidersDisclosureIsMadeTo.add(individualProviderDto);

		// second one

		individualProviderDto = new IndividualProviderDto();
		individualProviderDto.setEnumerationDate("2009-10-08T00:00:00-0500");
		individualProviderDto
				.setFirstLinePracticeLocationAddress("107 S 5TH ST");
		individualProviderDto.setFirstName("TERESA");
		individualProviderDto.setLastName("LUQUIN");
		individualProviderDto.setMiddleName("INEZ");
		individualProviderDto.setNamePrefix("");
		individualProviderDto.setNameSuffix("");
		individualProviderDto.setNpi("1568797520");
		individualProviderDto.setPracticeLocationAddressCityName("EL CENTRO");
		individualProviderDto.setPracticeLocationAddressCountryCode("US");
		individualProviderDto.setPracticeLocationAddressPostalCode("922433024");
		individualProviderDto.setPracticeLocationAddressStateName("CA");
		individualProviderDto
				.setPracticeLocationAddressTelephoneNumber("7603536151");
		individualProviderDto.setSecondLinePracticeLocationAddress("STE. 210");
		indiprovidersDisclosureIsMadeTo.add(individualProviderDto);

		consentDto
				.setProvidersDisclosureIsMadeTo(indiprovidersDisclosureIsMadeTo);

	}

	private void setOrganizationalProvidersPermittedToDisclose(
			ConsentDto consentDto) {

		Set<OrganizationalProviderDto> orgaprovidersPermittedToDisclose = new HashSet<OrganizationalProviderDto>();
		// setting to empty
		consentDto
				.setOrganizationalProvidersPermittedToDisclose(orgaprovidersPermittedToDisclose);

	}

	private void setOrganizationalProvidersDisclosureIsMadeTo(
			ConsentDto consentDto) {

		Set<OrganizationalProviderDto> orgaprovidersDisclosureIsMadeTo = new HashSet<OrganizationalProviderDto>();
		// setting to empty
		consentDto
				.setOrganizationalProvidersDisclosureIsMadeTo(orgaprovidersDisclosureIsMadeTo);

	}

	private void setDoNotShareClinicalDocumentTypeCodes(ConsentDto consentDto) {

		Set<TypeCodesDto> consentDoNotShareClinicalDocumentTypeCode = new HashSet<TypeCodesDto>();
		// setting to empty
		consentDto
				.setDoNotShareClinicalDocumentTypeCodes(consentDoNotShareClinicalDocumentTypeCode);

	}

	private void setDoNotShareClinicalDocumentSectionTypeCodes(
			ConsentDto consentDto) {

		Set<TypeCodesDto> consentDoNotShareClinicalDocumentSectionTypeCode = new HashSet<TypeCodesDto>();
		// setting to empty
		consentDto
				.setDoNotShareClinicalDocumentSectionTypeCodes(consentDoNotShareClinicalDocumentSectionTypeCode);
	}

	private void setDoNotShareSensitivityPolicyCodes(ConsentDto consentDto) {

		Set<TypeCodesDto> consentDoNotShareSensitivityPolicyCode = new HashSet<TypeCodesDto>();
		// setting to empty
		consentDto
				.setDoNotShareSensitivityPolicyCodes(consentDoNotShareSensitivityPolicyCode);
	}

	private void setShareForPurposeOfUseCodes(ConsentDto consentDto) {

		Set<TypeCodesDto> consentShareForPurposeOfUseCode = new HashSet<TypeCodesDto>();

		TypeCodesDto tcd3 = new TypeCodesDto();
		tcd3.setDisplayName("Emergency Treatment");
		tcd3.setCode("ETREAT");
		tcd3.setCodeSystem("2.16.840.1.113883.1.11.20448");
		tcd3.setCodeSystemName("PurposeOfUse");

		consentShareForPurposeOfUseCode.add(tcd3);

		tcd3 = new TypeCodesDto();
		tcd3.setDisplayName("Care Management Treatment");
		tcd3.setCode("CAREMGT");
		tcd3.setCodeSystem("2.16.840.1.113883.1.11.20448");
		tcd3.setCodeSystemName("PurposeOfUse");

		consentShareForPurposeOfUseCode.add(tcd3);

		tcd3 = new TypeCodesDto();
		tcd3.setDisplayName("Treatment");
		tcd3.setCode("TREAT");
		tcd3.setCodeSystem("2.16.840.1.113883.1.11.20448");
		tcd3.setCodeSystemName("PurposeOfUse");

		consentShareForPurposeOfUseCode.add(tcd3);

		consentDto
				.setShareForPurposeOfUseCodes(consentShareForPurposeOfUseCode);
	}

	private void setDoNotShareClinicalConceptCodes(ConsentDto consentDto) {

		Set<TypeCodesDto> consentDoNotShareClinicalConceptCodes = new HashSet<TypeCodesDto>();
		// empty
		consentDto
				.setDoNotShareClinicalConceptCodes(consentDoNotShareClinicalConceptCodes);
	}

	public String convertXMLFileToString(String fileName) {
		try {
			// DocumentBuilderFactory documentBuilderFactory =
			// DocumentBuilderFactory.newInstance();
			InputStream inputStream = getClass().getClassLoader()
					.getResourceAsStream(fileName);
			// String StringFromInputStream = IOUtils.toString(inputStream,
			// "UTF-8");

			/*
			 * org.w3c.dom.Document doc =
			 * documentBuilderFactory.newDocumentBuilder().parse(inputStream);
			 * StringWriter stw = new StringWriter(); Transformer serializer =
			 * TransformerFactory.newInstance().newTransformer();
			 * serializer.transform(new DOMSource(doc), new StreamResult(stw));
			 * return stw.toString();
			 */

			StringWriter writer = new StringWriter();
			String encoding = "UTF-8";
			IOUtils.copy(inputStream, writer, encoding);
			System.out.println(writer.toString());
			return writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
