package gov.samhsa.ds4ppilot.pep;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;
import gov.samhsa.consent2share.accesscontrolservice.common.tool.FileReaderImpl;
import gov.samhsa.consent2share.accesscontrolservice.common.tool.SimpleMarshallerImpl;
import gov.samhsa.consent2share.accesscontrolservice.documentsegmentation.DocumentSegmentationImpl;
import gov.samhsa.consent2share.accesscontrolservice.xdsb.common.XdsbErrorFactory;
import gov.samhsa.consent2share.accesscontrolservice.xdsb.registry.adapter.XdsbRegistryAdapter;
import gov.samhsa.consent2share.accesscontrolservice.xdsb.repository.adapter.XdsbRepositoryAdapter;
import gov.samhsa.consent2share.schema.documentsegmentation.SegmentDocumentResponse;
import gov.samhsa.ds4ppilot.pep.contexthandler.ContextHandlerImpl;
import gov.samhsa.ds4ppilot.pep.dto.XacmlRequest;
import gov.samhsa.ds4ppilot.pep.dto.XacmlResponse;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;

import java.util.Arrays;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PolicyEnforcementPointImplTest {

	private String adhocQueryString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ns3:AdhocQueryRequest  	xmlns:ns2=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\"  	xmlns=\"urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0\"  	xmlns:ns3=\"urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0\"><ns3:ResponseOption returnType=\"LeafClass\" returnComposedObjects=\"true\"/><AdhocQuery id=\"urn:uuid:14d4debf-8f97-4251-9a74-a90016b0af0d\"><Slot name=\"$XDSDocumentEntryPatientId\"><ValueList><Value>'e46b6650-47ea-11e3-9d13-00155d84621a^^^&amp;2.16.840.1.113883.4.357&amp;ISO'</Value></ValueList></Slot><Slot name=\"$XDSDocumentEntryStatus\"><ValueList><Value>('urn:oasis:names:tc:ebxml-regrep:StatusType:Approved')</Value></ValueList></Slot><Slot name=\"$XDSDocumentEntryFormatCode\"><ValueList><Value>'2.16.840.1.113883.10.20.1^^HITSP'</Value></ValueList></Slot></AdhocQuery></ns3:AdhocQueryRequest>";
	private AdhocQueryRequest adhocQuery;
	private String adhocQueryResponseString = "<ns5:AdhocQueryResponse status=\"urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success\" xmlns:ns2=\"http://www.samhsa.gov/ds4ppilot/schema/pep\" xmlns:ns3=\"urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0\" xmlns:ns4=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" xmlns:ns5=\"urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0\" xmlns:ns6=\"urn:ihe:iti:xds-b:2007\" xmlns:ns7=\"urn:oasis:names:tc:ebxml-regrep:xsd:lcm:3.0\" xmlns:ns8=\"urn:hl7-org:v3\" xmlns:ns9=\"http://schema.samhsa.gov/ds4p/XDSbRegistry/Message\"><ns3:RegistryObjectList><ns3:ExtrinsicObject mimeType=\"text/xml\" isOpaque=\"false\" lid=\"urn:uuid:6f3a347c-e827-422c-8834-254cc390705d\" objectType=\"urn:uuid:7edca82f-054d-47f2-a032-9b2a5b5186c1\" status=\"urn:oasis:names:tc:ebxml-regrep:StatusType:Approved\" id=\"urn:uuid:6f3a347c-e827-422c-8834-254cc390705d\"><ns3:Slot name=\"creationTime\"><ns3:ValueList><ns3:Value>20131111180143</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Slot name=\"hash\"><ns3:ValueList><ns3:Value>9472a4b78728abc98e2c0ce16c894d9e97dacedb</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Slot name=\"languageCode\"><ns3:ValueList><ns3:Value>en-US</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Slot name=\"repositoryUniqueId\"><ns3:ValueList><ns3:Value>1.3.6.1.4.1.21367.2010.1.2.1040</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Slot name=\"size\"><ns3:ValueList><ns3:Value>82974</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Slot name=\"sourcePatientId\"><ns3:ValueList><ns3:Value>e46b6650-47ea-11e3-9d13-00155d84621a^^^&amp;2.16.840.1.113883.4.357&amp;ISO</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Slot name=\"sourcePatientInfo\"><ns3:ValueList><ns3:Value>PID-3|e46b6650-47ea-11e3-9d13-00155d84621a^^^&amp;2.16.840.1.113883.4.357&amp;ISO</ns3:Value><ns3:Value>PID-5|FamilyName^FirstName^^^</ns3:Value><ns3:Value>PID-7|19840704</ns3:Value><ns3:Value>PID-8|F</ns3:Value><ns3:Value>PID-11|First Address Line^^CityName^StateName^ZipCode(5or9)^USA</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Name><ns3:LocalizedString xml:lang=\"en-us\" charset=\"UTF-8\" value=\"Clinic Personal Health Record Extract\"/></ns3:Name><ns3:Description/><ns3:VersionInfo versionName=\"1.1\"/><ns3:Classification classificationScheme=\"urn:uuid:f0306f51-975f-434e-a61c-c59651d33983\" classifiedObject=\"urn:uuid:6f3a347c-e827-422c-8834-254cc390705d\" nodeRepresentation=\"34133-9\" lid=\"urn:uuid:22fe3c48-53d2-41cd-94e4-2ba61b8d3511\" objectType=\"urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Classification\" status=\"urn:oasis:names:tc:ebxml-regrep:StatusType:Submitted\" id=\"urn:uuid:22fe3c48-53d2-41cd-94e4-2ba61b8d3511\"><ns3:Slot name=\"codingScheme\"><ns3:ValueList><ns3:Value>LOINC</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Name><ns3:LocalizedString xml:lang=\"en-us\" charset=\"UTF-8\" value=\"Summarization of episode note\"/></ns3:Name><ns3:Description/><ns3:VersionInfo versionName=\"1.1\"/></ns3:Classification><ns3:Classification classificationScheme=\"urn:uuid:93606bcf-9494-43ec-9b4e-a7748d1a838d\" classifiedObject=\"urn:uuid:6f3a347c-e827-422c-8834-254cc390705d\" nodeRepresentation=\"\" lid=\"urn:uuid:42fb2ba0-22fc-4df5-abaf-f586aa39e366\" objectType=\"urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Classification\" status=\"urn:oasis:names:tc:ebxml-regrep:StatusType:Submitted\" id=\"urn:uuid:42fb2ba0-22fc-4df5-abaf-f586aa39e366\"><ns3:Slot name=\"authorPerson\"><ns3:ValueList><ns3:Value>1346575297^Welby^Marcus^^^Dr^^^&amp;2.16.840.1.113883.4.6&amp;ISO</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Name/><ns3:Description/><ns3:VersionInfo versionName=\"1.1\"/></ns3:Classification><ns3:Classification classificationScheme=\"urn:uuid:cccf5598-8b07-4b77-a05e-ae952c785ead\" classifiedObject=\"urn:uuid:6f3a347c-e827-422c-8834-254cc390705d\" nodeRepresentation=\"Psychiatry\" lid=\"urn:uuid:a6066aff-98ef-4020-b3e3-d8a3d512a02d\" objectType=\"urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Classification\" status=\"urn:oasis:names:tc:ebxml-regrep:StatusType:Submitted\" id=\"urn:uuid:a6066aff-98ef-4020-b3e3-d8a3d512a02d\"><ns3:Slot name=\"codingScheme\"><ns3:ValueList><ns3:Value>Connect-a-thon practiceSettingCodes</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Name><ns3:LocalizedString xml:lang=\"en-us\" charset=\"UTF-8\" value=\"Psychiatry\"/></ns3:Name><ns3:Description/><ns3:VersionInfo versionName=\"1.1\"/></ns3:Classification><ns3:Classification classificationScheme=\"urn:uuid:f4f85eac-e6cb-4883-b524-f2705394840f\" classifiedObject=\"urn:uuid:6f3a347c-e827-422c-8834-254cc390705d\" nodeRepresentation=\"N\" lid=\"urn:uuid:b5736101-b7d6-43fa-bb9f-1b79125fed76\" objectType=\"urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Classification\" status=\"urn:oasis:names:tc:ebxml-regrep:StatusType:Submitted\" id=\"urn:uuid:b5736101-b7d6-43fa-bb9f-1b79125fed76\"><ns3:Slot name=\"codingScheme\"><ns3:ValueList><ns3:Value>2.16.840.1.113883.5.25</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Name><ns3:LocalizedString xml:lang=\"en-us\" charset=\"UTF-8\" value=\"N\"/></ns3:Name><ns3:Description/><ns3:VersionInfo versionName=\"1.1\"/></ns3:Classification><ns3:Classification classificationScheme=\"urn:uuid:41a5887f-8865-4c09-adf7-e362475b143a\" classifiedObject=\"urn:uuid:6f3a347c-e827-422c-8834-254cc390705d\" nodeRepresentation=\"Consult\" lid=\"urn:uuid:dfdd13d6-a0a2-4735-a729-1564254653fb\" objectType=\"urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Classification\" status=\"urn:oasis:names:tc:ebxml-regrep:StatusType:Submitted\" id=\"urn:uuid:dfdd13d6-a0a2-4735-a729-1564254653fb\"><ns3:Slot name=\"codingScheme\"><ns3:ValueList><ns3:Value>Connect-a-thon classCodes</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Name><ns3:LocalizedString xml:lang=\"en-us\" charset=\"UTF-8\" value=\"Consult Notes\"/></ns3:Name><ns3:Description/><ns3:VersionInfo versionName=\"1.1\"/></ns3:Classification><ns3:Classification classificationScheme=\"urn:uuid:a09d5840-386c-46f2-b5ad-9c3699a4309d\" classifiedObject=\"urn:uuid:6f3a347c-e827-422c-8834-254cc390705d\" nodeRepresentation=\"2.16.840.1.113883.10.20.1\" lid=\"urn:uuid:ecad0817-46e6-4008-b5e2-99a9f4eb22a4\" objectType=\"urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Classification\" status=\"urn:oasis:names:tc:ebxml-regrep:StatusType:Submitted\" id=\"urn:uuid:ecad0817-46e6-4008-b5e2-99a9f4eb22a4\"><ns3:Slot name=\"codingScheme\"><ns3:ValueList><ns3:Value>HITSP</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Name><ns3:LocalizedString xml:lang=\"en-us\" charset=\"UTF-8\" value=\"HL7 CCD Document\"/></ns3:Name><ns3:Description/><ns3:VersionInfo versionName=\"1.1\"/></ns3:Classification><ns3:Classification classificationScheme=\"urn:uuid:f33fb8ac-18af-42cc-ae0e-ed0b0bdb91e1\" classifiedObject=\"urn:uuid:6f3a347c-e827-422c-8834-254cc390705d\" nodeRepresentation=\"OF\" lid=\"urn:uuid:efb54ec3-d0df-443e-97fa-3576d8389f85\" objectType=\"urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Classification\" status=\"urn:oasis:names:tc:ebxml-regrep:StatusType:Submitted\" id=\"urn:uuid:efb54ec3-d0df-443e-97fa-3576d8389f85\"><ns3:Slot name=\"codingScheme\"><ns3:ValueList><ns3:Value>2.16.840.1.113883.5.11</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Name><ns3:LocalizedString xml:lang=\"en-us\" charset=\"UTF-8\" value=\"Outpatient facility\"/></ns3:Name><ns3:Description/><ns3:VersionInfo versionName=\"1.1\"/></ns3:Classification><ns3:ExternalIdentifier registryObject=\"urn:uuid:6f3a347c-e827-422c-8834-254cc390705d\" identificationScheme=\"urn:uuid:2e82c1f6-a085-4c72-9da3-8640a32e42ab\" value=\"44212111915.14792.41394.914113.0152268155381113\" lid=\"urn:uuid:249d9532-a450-4bf9-ab64-4b2b30b30992\" objectType=\"urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ExternalIdentifier\" status=\"urn:oasis:names:tc:ebxml-regrep:StatusType:Submitted\" id=\"urn:uuid:249d9532-a450-4bf9-ab64-4b2b30b30992\"><ns3:Name><ns3:LocalizedString xml:lang=\"en-us\" charset=\"UTF-8\" value=\"XDSDocumentEntry.uniqueId\"/></ns3:Name><ns3:Description/><ns3:VersionInfo versionName=\"1.1\"/></ns3:ExternalIdentifier><ns3:ExternalIdentifier registryObject=\"urn:uuid:6f3a347c-e827-422c-8834-254cc390705d\" identificationScheme=\"urn:uuid:58a6f841-87b3-4a3e-92fd-a8ffeff98427\" value=\"e46b6650-47ea-11e3-9d13-00155d84621a^^^&amp;2.16.840.1.113883.4.357&amp;ISO\" lid=\"urn:uuid:79b9b357-e87b-493b-a11b-0b5f9029a2e2\" objectType=\"urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ExternalIdentifier\" status=\"urn:oasis:names:tc:ebxml-regrep:StatusType:Submitted\" id=\"urn:uuid:79b9b357-e87b-493b-a11b-0b5f9029a2e2\"><ns3:Name><ns3:LocalizedString xml:lang=\"en-us\" charset=\"UTF-8\" value=\"XDSDocumentEntry.patientId\"/></ns3:Name><ns3:Description/><ns3:VersionInfo versionName=\"1.1\"/></ns3:ExternalIdentifier></ns3:ExtrinsicObject><ns3:ExtrinsicObject mimeType=\"text/xml\" isOpaque=\"false\" lid=\"urn:uuid:e4fdc150-166e-42b4-9f99-c00bfa37618c\" objectType=\"urn:uuid:7edca82f-054d-47f2-a032-9b2a5b5186c1\" status=\"urn:oasis:names:tc:ebxml-regrep:StatusType:Approved\" id=\"urn:uuid:e4fdc150-166e-42b4-9f99-c00bfa37618c\"><ns3:Slot name=\"creationTime\"><ns3:ValueList><ns3:Value>20131111175513</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Slot name=\"hash\"><ns3:ValueList><ns3:Value>9472a4b78728abc98e2c0ce16c894d9e97dacedb</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Slot name=\"languageCode\"><ns3:ValueList><ns3:Value>en-US</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Slot name=\"repositoryUniqueId\"><ns3:ValueList><ns3:Value>1.3.6.1.4.1.21367.2010.1.2.1040</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Slot name=\"size\"><ns3:ValueList><ns3:Value>82974</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Slot name=\"sourcePatientId\"><ns3:ValueList><ns3:Value>e46b6650-47ea-11e3-9d13-00155d84621a^^^&amp;2.16.840.1.113883.4.357&amp;ISO</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Slot name=\"sourcePatientInfo\"><ns3:ValueList><ns3:Value>PID-3|e46b6650-47ea-11e3-9d13-00155d84621a^^^&amp;2.16.840.1.113883.4.357&amp;ISO</ns3:Value><ns3:Value>PID-5|FamilyName^FirstName^^^</ns3:Value><ns3:Value>PID-7|19840704</ns3:Value><ns3:Value>PID-8|F</ns3:Value><ns3:Value>PID-11|First Address Line^^CityName^StateName^ZipCode(5or9)^USA</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Name><ns3:LocalizedString xml:lang=\"en-us\" charset=\"UTF-8\" value=\"Clinic Personal Health Record Extract\"/></ns3:Name><ns3:Description/><ns3:VersionInfo versionName=\"1.1\"/><ns3:Classification classificationScheme=\"urn:uuid:f4f85eac-e6cb-4883-b524-f2705394840f\" classifiedObject=\"urn:uuid:e4fdc150-166e-42b4-9f99-c00bfa37618c\" nodeRepresentation=\"N\" lid=\"urn:uuid:07c6c78a-d0ce-46fd-8e73-dcfb05f87960\" objectType=\"urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Classification\" status=\"urn:oasis:names:tc:ebxml-regrep:StatusType:Submitted\" id=\"urn:uuid:07c6c78a-d0ce-46fd-8e73-dcfb05f87960\"><ns3:Slot name=\"codingScheme\"><ns3:ValueList><ns3:Value>2.16.840.1.113883.5.25</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Name><ns3:LocalizedString xml:lang=\"en-us\" charset=\"UTF-8\" value=\"N\"/></ns3:Name><ns3:Description/><ns3:VersionInfo versionName=\"1.1\"/></ns3:Classification><ns3:Classification classificationScheme=\"urn:uuid:f33fb8ac-18af-42cc-ae0e-ed0b0bdb91e1\" classifiedObject=\"urn:uuid:e4fdc150-166e-42b4-9f99-c00bfa37618c\" nodeRepresentation=\"OF\" lid=\"urn:uuid:14be0ddc-f8cd-42d0-abda-bb9e70353701\" objectType=\"urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Classification\" status=\"urn:oasis:names:tc:ebxml-regrep:StatusType:Submitted\" id=\"urn:uuid:14be0ddc-f8cd-42d0-abda-bb9e70353701\"><ns3:Slot name=\"codingScheme\"><ns3:ValueList><ns3:Value>2.16.840.1.113883.5.11</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Name><ns3:LocalizedString xml:lang=\"en-us\" charset=\"UTF-8\" value=\"Outpatient facility\"/></ns3:Name><ns3:Description/><ns3:VersionInfo versionName=\"1.1\"/></ns3:Classification><ns3:Classification classificationScheme=\"urn:uuid:a09d5840-386c-46f2-b5ad-9c3699a4309d\" classifiedObject=\"urn:uuid:e4fdc150-166e-42b4-9f99-c00bfa37618c\" nodeRepresentation=\"2.16.840.1.113883.10.20.1\" lid=\"urn:uuid:41f90bbd-7590-4769-ae5a-810c9312ba3c\" objectType=\"urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Classification\" status=\"urn:oasis:names:tc:ebxml-regrep:StatusType:Submitted\" id=\"urn:uuid:41f90bbd-7590-4769-ae5a-810c9312ba3c\"><ns3:Slot name=\"codingScheme\"><ns3:ValueList><ns3:Value>HITSP</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Name><ns3:LocalizedString xml:lang=\"en-us\" charset=\"UTF-8\" value=\"HL7 CCD Document\"/></ns3:Name><ns3:Description/><ns3:VersionInfo versionName=\"1.1\"/></ns3:Classification><ns3:Classification classificationScheme=\"urn:uuid:93606bcf-9494-43ec-9b4e-a7748d1a838d\" classifiedObject=\"urn:uuid:e4fdc150-166e-42b4-9f99-c00bfa37618c\" nodeRepresentation=\"\" lid=\"urn:uuid:467e58d5-581a-42ec-af2b-261c7a5cc8cb\" objectType=\"urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Classification\" status=\"urn:oasis:names:tc:ebxml-regrep:StatusType:Submitted\" id=\"urn:uuid:467e58d5-581a-42ec-af2b-261c7a5cc8cb\"><ns3:Slot name=\"authorPerson\"><ns3:ValueList><ns3:Value>1346575297^Welby^Marcus^^^Dr^^^&amp;2.16.840.1.113883.4.6&amp;ISO</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Name/><ns3:Description/><ns3:VersionInfo versionName=\"1.1\"/></ns3:Classification><ns3:Classification classificationScheme=\"urn:uuid:cccf5598-8b07-4b77-a05e-ae952c785ead\" classifiedObject=\"urn:uuid:e4fdc150-166e-42b4-9f99-c00bfa37618c\" nodeRepresentation=\"Psychiatry\" lid=\"urn:uuid:640a05ac-02db-4b14-9645-163c3f15cb54\" objectType=\"urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Classification\" status=\"urn:oasis:names:tc:ebxml-regrep:StatusType:Submitted\" id=\"urn:uuid:640a05ac-02db-4b14-9645-163c3f15cb54\"><ns3:Slot name=\"codingScheme\"><ns3:ValueList><ns3:Value>Connect-a-thon practiceSettingCodes</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Name><ns3:LocalizedString xml:lang=\"en-us\" charset=\"UTF-8\" value=\"Psychiatry\"/></ns3:Name><ns3:Description/><ns3:VersionInfo versionName=\"1.1\"/></ns3:Classification><ns3:Classification classificationScheme=\"urn:uuid:41a5887f-8865-4c09-adf7-e362475b143a\" classifiedObject=\"urn:uuid:e4fdc150-166e-42b4-9f99-c00bfa37618c\" nodeRepresentation=\"Consult\" lid=\"urn:uuid:c1b04c57-5d06-437c-9f6b-1698fd8fe2ce\" objectType=\"urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Classification\" status=\"urn:oasis:names:tc:ebxml-regrep:StatusType:Submitted\" id=\"urn:uuid:c1b04c57-5d06-437c-9f6b-1698fd8fe2ce\"><ns3:Slot name=\"codingScheme\"><ns3:ValueList><ns3:Value>Connect-a-thon classCodes</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Name><ns3:LocalizedString xml:lang=\"en-us\" charset=\"UTF-8\" value=\"Consult Notes\"/></ns3:Name><ns3:Description/><ns3:VersionInfo versionName=\"1.1\"/></ns3:Classification><ns3:Classification classificationScheme=\"urn:uuid:f0306f51-975f-434e-a61c-c59651d33983\" classifiedObject=\"urn:uuid:e4fdc150-166e-42b4-9f99-c00bfa37618c\" nodeRepresentation=\"34133-9\" lid=\"urn:uuid:faac1a4b-1f39-4c86-b6cf-8486ca5142c1\" objectType=\"urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Classification\" status=\"urn:oasis:names:tc:ebxml-regrep:StatusType:Submitted\" id=\"urn:uuid:faac1a4b-1f39-4c86-b6cf-8486ca5142c1\"><ns3:Slot name=\"codingScheme\"><ns3:ValueList><ns3:Value>LOINC</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Name><ns3:LocalizedString xml:lang=\"en-us\" charset=\"UTF-8\" value=\"Summarization of episode note\"/></ns3:Name><ns3:Description/><ns3:VersionInfo versionName=\"1.1\"/></ns3:Classification><ns3:ExternalIdentifier registryObject=\"urn:uuid:e4fdc150-166e-42b4-9f99-c00bfa37618c\" identificationScheme=\"urn:uuid:2e82c1f6-a085-4c72-9da3-8640a32e42ab\" value=\"1111048131312.1312011.43613.91155.1111312013196213\" lid=\"urn:uuid:6e100d8a-c6cb-4535-b9a0-0635bccbf1ac\" objectType=\"urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ExternalIdentifier\" status=\"urn:oasis:names:tc:ebxml-regrep:StatusType:Submitted\" id=\"urn:uuid:6e100d8a-c6cb-4535-b9a0-0635bccbf1ac\"><ns3:Name><ns3:LocalizedString xml:lang=\"en-us\" charset=\"UTF-8\" value=\"XDSDocumentEntry.uniqueId\"/></ns3:Name><ns3:Description/><ns3:VersionInfo versionName=\"1.1\"/></ns3:ExternalIdentifier><ns3:ExternalIdentifier registryObject=\"urn:uuid:e4fdc150-166e-42b4-9f99-c00bfa37618c\" identificationScheme=\"urn:uuid:58a6f841-87b3-4a3e-92fd-a8ffeff98427\" value=\"e46b6650-47ea-11e3-9d13-00155d84621a^^^&amp;2.16.840.1.113883.4.357&amp;ISO\" lid=\"urn:uuid:7a4817d6-120d-44fd-a899-e3be83ed13c1\" objectType=\"urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ExternalIdentifier\" status=\"urn:oasis:names:tc:ebxml-regrep:StatusType:Submitted\" id=\"urn:uuid:7a4817d6-120d-44fd-a899-e3be83ed13c1\"><ns3:Name><ns3:LocalizedString xml:lang=\"en-us\" charset=\"UTF-8\" value=\"XDSDocumentEntry.patientId\"/></ns3:Name><ns3:Description/><ns3:VersionInfo versionName=\"1.1\"/></ns3:ExternalIdentifier></ns3:ExtrinsicObject><ns3:ExtrinsicObject mimeType=\"text/xml\" isOpaque=\"false\" lid=\"urn:uuid:d114326d-c78f-4f54-b57f-b7f2dd005b6f\" objectType=\"urn:uuid:7edca82f-054d-47f2-a032-9b2a5b5186c1\" status=\"urn:oasis:names:tc:ebxml-regrep:StatusType:Approved\" id=\"urn:uuid:d114326d-c78f-4f54-b57f-b7f2dd005b6f\"><ns3:Slot name=\"creationTime\"><ns3:ValueList><ns3:Value>20131111175555</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Slot name=\"hash\"><ns3:ValueList><ns3:Value>9472a4b78728abc98e2c0ce16c894d9e97dacedb</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Slot name=\"languageCode\"><ns3:ValueList><ns3:Value>en-US</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Slot name=\"repositoryUniqueId\"><ns3:ValueList><ns3:Value>1.3.6.1.4.1.21367.2010.1.2.1040</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Slot name=\"size\"><ns3:ValueList><ns3:Value>82974</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Slot name=\"sourcePatientId\"><ns3:ValueList><ns3:Value>e46b6650-47ea-11e3-9d13-00155d84621a^^^&amp;2.16.840.1.113883.4.357&amp;ISO</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Slot name=\"sourcePatientInfo\"><ns3:ValueList><ns3:Value>PID-3|e46b6650-47ea-11e3-9d13-00155d84621a^^^&amp;2.16.840.1.113883.4.357&amp;ISO</ns3:Value><ns3:Value>PID-5|FamilyName^FirstName^^^</ns3:Value><ns3:Value>PID-7|19840704</ns3:Value><ns3:Value>PID-8|F</ns3:Value><ns3:Value>PID-11|First Address Line^^CityName^StateName^ZipCode(5or9)^USA</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Name><ns3:LocalizedString xml:lang=\"en-us\" charset=\"UTF-8\" value=\"Clinic Personal Health Record Extract\"/></ns3:Name><ns3:Description/><ns3:VersionInfo versionName=\"1.1\"/><ns3:Classification classificationScheme=\"urn:uuid:f4f85eac-e6cb-4883-b524-f2705394840f\" classifiedObject=\"urn:uuid:d114326d-c78f-4f54-b57f-b7f2dd005b6f\" nodeRepresentation=\"N\" lid=\"urn:uuid:547c5f71-5530-4e5f-a68d-aea1ea8affbb\" objectType=\"urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Classification\" status=\"urn:oasis:names:tc:ebxml-regrep:StatusType:Submitted\" id=\"urn:uuid:547c5f71-5530-4e5f-a68d-aea1ea8affbb\"><ns3:Slot name=\"codingScheme\"><ns3:ValueList><ns3:Value>2.16.840.1.113883.5.25</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Name><ns3:LocalizedString xml:lang=\"en-us\" charset=\"UTF-8\" value=\"N\"/></ns3:Name><ns3:Description/><ns3:VersionInfo versionName=\"1.1\"/></ns3:Classification><ns3:Classification classificationScheme=\"urn:uuid:cccf5598-8b07-4b77-a05e-ae952c785ead\" classifiedObject=\"urn:uuid:d114326d-c78f-4f54-b57f-b7f2dd005b6f\" nodeRepresentation=\"Psychiatry\" lid=\"urn:uuid:917f6c4e-d1e8-4e6f-a7eb-d260846bd9ac\" objectType=\"urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Classification\" status=\"urn:oasis:names:tc:ebxml-regrep:StatusType:Submitted\" id=\"urn:uuid:917f6c4e-d1e8-4e6f-a7eb-d260846bd9ac\"><ns3:Slot name=\"codingScheme\"><ns3:ValueList><ns3:Value>Connect-a-thon practiceSettingCodes</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Name><ns3:LocalizedString xml:lang=\"en-us\" charset=\"UTF-8\" value=\"Psychiatry\"/></ns3:Name><ns3:Description/><ns3:VersionInfo versionName=\"1.1\"/></ns3:Classification><ns3:Classification classificationScheme=\"urn:uuid:f0306f51-975f-434e-a61c-c59651d33983\" classifiedObject=\"urn:uuid:d114326d-c78f-4f54-b57f-b7f2dd005b6f\" nodeRepresentation=\"34133-9\" lid=\"urn:uuid:a2072b35-c42c-45cc-97e8-1c754b6ccfcd\" objectType=\"urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Classification\" status=\"urn:oasis:names:tc:ebxml-regrep:StatusType:Submitted\" id=\"urn:uuid:a2072b35-c42c-45cc-97e8-1c754b6ccfcd\"><ns3:Slot name=\"codingScheme\"><ns3:ValueList><ns3:Value>LOINC</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Name><ns3:LocalizedString xml:lang=\"en-us\" charset=\"UTF-8\" value=\"Summarization of episode note\"/></ns3:Name><ns3:Description/><ns3:VersionInfo versionName=\"1.1\"/></ns3:Classification><ns3:Classification classificationScheme=\"urn:uuid:f33fb8ac-18af-42cc-ae0e-ed0b0bdb91e1\" classifiedObject=\"urn:uuid:d114326d-c78f-4f54-b57f-b7f2dd005b6f\" nodeRepresentation=\"OF\" lid=\"urn:uuid:a3aafe37-d4f0-4346-a62a-5732dc389366\" objectType=\"urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Classification\" status=\"urn:oasis:names:tc:ebxml-regrep:StatusType:Submitted\" id=\"urn:uuid:a3aafe37-d4f0-4346-a62a-5732dc389366\"><ns3:Slot name=\"codingScheme\"><ns3:ValueList><ns3:Value>2.16.840.1.113883.5.11</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Name><ns3:LocalizedString xml:lang=\"en-us\" charset=\"UTF-8\" value=\"Outpatient facility\"/></ns3:Name><ns3:Description/><ns3:VersionInfo versionName=\"1.1\"/></ns3:Classification><ns3:Classification classificationScheme=\"urn:uuid:a09d5840-386c-46f2-b5ad-9c3699a4309d\" classifiedObject=\"urn:uuid:d114326d-c78f-4f54-b57f-b7f2dd005b6f\" nodeRepresentation=\"2.16.840.1.113883.10.20.1\" lid=\"urn:uuid:c496b93c-410a-4069-9280-0ba472401776\" objectType=\"urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Classification\" status=\"urn:oasis:names:tc:ebxml-regrep:StatusType:Submitted\" id=\"urn:uuid:c496b93c-410a-4069-9280-0ba472401776\"><ns3:Slot name=\"codingScheme\"><ns3:ValueList><ns3:Value>HITSP</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Name><ns3:LocalizedString xml:lang=\"en-us\" charset=\"UTF-8\" value=\"HL7 CCD Document\"/></ns3:Name><ns3:Description/><ns3:VersionInfo versionName=\"1.1\"/></ns3:Classification><ns3:Classification classificationScheme=\"urn:uuid:93606bcf-9494-43ec-9b4e-a7748d1a838d\" classifiedObject=\"urn:uuid:d114326d-c78f-4f54-b57f-b7f2dd005b6f\" nodeRepresentation=\"\" lid=\"urn:uuid:e89bc7e2-da79-4743-91d5-d2f954463514\" objectType=\"urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Classification\" status=\"urn:oasis:names:tc:ebxml-regrep:StatusType:Submitted\" id=\"urn:uuid:e89bc7e2-da79-4743-91d5-d2f954463514\"><ns3:Slot name=\"authorPerson\"><ns3:ValueList><ns3:Value>1346575297^Welby^Marcus^^^Dr^^^&amp;2.16.840.1.113883.4.6&amp;ISO</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Name/><ns3:Description/><ns3:VersionInfo versionName=\"1.1\"/></ns3:Classification><ns3:Classification classificationScheme=\"urn:uuid:41a5887f-8865-4c09-adf7-e362475b143a\" classifiedObject=\"urn:uuid:d114326d-c78f-4f54-b57f-b7f2dd005b6f\" nodeRepresentation=\"Consult\" lid=\"urn:uuid:f3d90710-68ab-480b-b68f-2d6eaf510993\" objectType=\"urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Classification\" status=\"urn:oasis:names:tc:ebxml-regrep:StatusType:Submitted\" id=\"urn:uuid:f3d90710-68ab-480b-b68f-2d6eaf510993\"><ns3:Slot name=\"codingScheme\"><ns3:ValueList><ns3:Value>Connect-a-thon classCodes</ns3:Value></ns3:ValueList></ns3:Slot><ns3:Name><ns3:LocalizedString xml:lang=\"en-us\" charset=\"UTF-8\" value=\"Consult Notes\"/></ns3:Name><ns3:Description/><ns3:VersionInfo versionName=\"1.1\"/></ns3:Classification><ns3:ExternalIdentifier registryObject=\"urn:uuid:d114326d-c78f-4f54-b57f-b7f2dd005b6f\" identificationScheme=\"urn:uuid:58a6f841-87b3-4a3e-92fd-a8ffeff98427\" value=\"e46b6650-47ea-11e3-9d13-00155d84621a^^^&amp;2.16.840.1.113883.4.357&amp;ISO\" lid=\"urn:uuid:cad08ca1-323f-4358-aee0-1d5cef09f8ae\" objectType=\"urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ExternalIdentifier\" status=\"urn:oasis:names:tc:ebxml-regrep:StatusType:Submitted\" id=\"urn:uuid:cad08ca1-323f-4358-aee0-1d5cef09f8ae\"><ns3:Name><ns3:LocalizedString xml:lang=\"en-us\" charset=\"UTF-8\" value=\"XDSDocumentEntry.patientId\"/></ns3:Name><ns3:Description/><ns3:VersionInfo versionName=\"1.1\"/></ns3:ExternalIdentifier><ns3:ExternalIdentifier registryObject=\"urn:uuid:d114326d-c78f-4f54-b57f-b7f2dd005b6f\" identificationScheme=\"urn:uuid:2e82c1f6-a085-4c72-9da3-8640a32e42ab\" value=\"11114981204.1210414.44143.9659.1511331497751295\" lid=\"urn:uuid:fae982c1-9cfb-4320-b900-6ea6fed741ef\" objectType=\"urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ExternalIdentifier\" status=\"urn:oasis:names:tc:ebxml-regrep:StatusType:Submitted\" id=\"urn:uuid:fae982c1-9cfb-4320-b900-6ea6fed741ef\"><ns3:Name><ns3:LocalizedString xml:lang=\"en-us\" charset=\"UTF-8\" value=\"XDSDocumentEntry.uniqueId\"/></ns3:Name><ns3:Description/><ns3:VersionInfo versionName=\"1.1\"/></ns3:ExternalIdentifier></ns3:ExtrinsicObject></ns3:RegistryObjectList></ns5:AdhocQueryResponse>";
	private AdhocQueryResponse adhocQueryResponse;
	private String retrieveDocumentSetRequestString = "<urn:RetrieveDocumentSetRequest xmlns:urn=\"urn:ihe:iti:xds-b:2007\"><urn:DocumentRequest><urn:RepositoryUniqueId>1.3.6.1.4.1.21367.2010.1.2.1040</urn:RepositoryUniqueId><urn:DocumentUniqueId>1111048131312.1312011.43613.91155.1111312013196213</urn:DocumentUniqueId></urn:DocumentRequest><urn:DocumentRequest><urn:RepositoryUniqueId>1.3.6.1.4.1.21367.2010.1.2.1040</urn:RepositoryUniqueId><urn:DocumentUniqueId>44212111915.14792.41394.914113.0152268155381113</urn:DocumentUniqueId></urn:DocumentRequest></urn:RetrieveDocumentSetRequest>";
	private RetrieveDocumentSetRequest retrieveDocumentSetRequest;
	private String retrieveDocumentSetResponseString;
	private RetrieveDocumentSetResponse retrieveDocumentSetResponse;
	private String formatCode = "'2.16.840.1.113883.10.20.1^^HITSP'";
	private String docEntryStatus = "('urn:oasis:names:tc:ebxml-regrep:StatusType:Approved')";
	private String patientUniqueId = "'e46b6650-47ea-11e3-9d13-00155d84621a^^^&amp;2.16.840.1.113883.4.357&amp;ISO'";
	private String responseOptionType = "LeafClass";
	private String patientId = "e46b6650-47ea-11e3-9d13-00155d84621a";
	private String recepientSubjectNPI = "1083949036";
	private String intermediarySubjectNPI = "1346575297";
	private String domainId = "2.16.840.1.113883.4.357";
	private String subjectPurposeOfUse = "TREAT";
	private String enforcementPolicies = "enforcementPolicies";
	private XacmlResponse xacmlResponse;
	private SegmentDocumentResponse segmentDocumentResponse1;
	private SegmentDocumentResponse segmentDocumentResponse2;
	private String segmentDocumentResponse1String = "Segmented document 1";
	private String segmentDocumentResponse2String = "Segmented document 2";

	// Mocks
	@Mock
	private XdsbRegistryAdapter xdsbRegistry;
	@Mock
	private XdsbRepositoryAdapter xdsbRepository;
	@Mock
	private XdsbErrorFactory xdsbErrorFactory;
	@Mock
	private ContextHandlerImpl contextHandler;
	@Mock
	private DocumentSegmentationImpl documentSegmentation;
	@Mock
	private SimpleMarshallerImpl marshaller;
	
	// Services
	private FileReaderImpl fileReader;
	private SimpleMarshallerImpl simpleMarshaller;

	// Sut
	@InjectMocks
	private PolicyEnforcementPointImpl pep;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		fileReader = new FileReaderImpl();
		simpleMarshaller = new SimpleMarshallerImpl();
		adhocQuery = simpleMarshaller.unmarshallFromXml(
				AdhocQueryRequest.class, adhocQueryString);
		adhocQueryResponse = simpleMarshaller.unmarshallFromXml(
				AdhocQueryResponse.class, adhocQueryResponseString);
		retrieveDocumentSetRequest = simpleMarshaller.unmarshallFromXml(
				RetrieveDocumentSetRequest.class,
				retrieveDocumentSetRequestString);
		retrieveDocumentSetResponseString = fileReader.readFile("testRetrieveDocumentSetResponse.xml");
		retrieveDocumentSetResponse = simpleMarshaller.unmarshallFromXml(
				RetrieveDocumentSetResponse.class,
				retrieveDocumentSetResponseString);
		xacmlResponse = new XacmlResponse();
		xacmlResponse.setPdpDecision("PERMIT");
		segmentDocumentResponse1 = new SegmentDocumentResponse();
		segmentDocumentResponse1
				.setMaskedDocument(segmentDocumentResponse1String);
		segmentDocumentResponse2 = new SegmentDocumentResponse();
		segmentDocumentResponse2
				.setMaskedDocument(segmentDocumentResponse2String);
		pep.setHomeCommunityId(domainId);
		pep.setIntermediarySubjectNPI(intermediarySubjectNPI);
		pep.setPatientId(patientId);
		pep.setRecepientSubjectNPI(recepientSubjectNPI);
		pep.setSubjectPurposeOfUse(subjectPurposeOfUse);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRegistryStoredQuery() throws Throwable {
		// Arrange
		when(xdsbRegistry.extractFormatCode(adhocQuery)).thenReturn(formatCode)
				.thenReturn(formatCode);
		when(xdsbRegistry.extractDocumentEntryStatus(adhocQuery)).thenReturn(
				docEntryStatus);
		when(xdsbRegistry.extractPatientId(adhocQuery)).thenReturn(
				patientUniqueId).thenReturn(patientUniqueId);
		when(xdsbRegistry.getPatientUniqueId(anyString(), anyString()))
				.thenReturn(patientUniqueId);
		when(xdsbRegistry.extractResponseOptionReturnType(adhocQuery))
				.thenReturn(responseOptionType);
		when(
				xdsbRegistry.registryStoredQuery(adhocQuery,
						intermediarySubjectNPI)).thenReturn(adhocQueryResponse);
		when(contextHandler.enforcePolicy(isA(XacmlRequest.class))).thenReturn(
				xacmlResponse);

		// Act
		AdhocQueryResponse response = pep.registryStoredQuery(adhocQuery);

		// Assert
		assertEquals(adhocQueryResponse, response);
	}

	@Test
	public void testRetrieveDocumentSet() throws Throwable {
		// Arrange
		when(xdsbRegistry.getPatientUniqueId(anyString(), anyString()))
				.thenReturn(patientUniqueId);
		when(contextHandler.enforcePolicy(isA(XacmlRequest.class))).thenReturn(
				xacmlResponse).thenReturn(xacmlResponse);
		when(
				xdsbRepository.retrieveDocumentSet(retrieveDocumentSetRequest,
						patientId, intermediarySubjectNPI)).thenReturn(
				retrieveDocumentSetResponse);
		when(marshaller.marshall(isA(Object.class))).thenReturn(
				enforcementPolicies).thenReturn(enforcementPolicies);
		when(
				documentSegmentation.segmentDocument(anyString(),
						eq(enforcementPolicies), eq(false), eq(false), eq(""),
						eq(""), anyString())).thenReturn(
				segmentDocumentResponse1).thenReturn(segmentDocumentResponse2);

		// Act
		RetrieveDocumentSetResponse response = pep
				.retrieveDocumentSet(retrieveDocumentSetRequest);

		// Assert
		assertTrue(Arrays.equals(segmentDocumentResponse1String.getBytes(),
				response.getDocumentResponse().get(0).getDocument()));
		assertTrue(Arrays.equals(segmentDocumentResponse2String.getBytes(),
				response.getDocumentResponse().get(1).getDocument()));
	}

	//TODO: Create test case when directEmailSend is implemented
	@Ignore("Not yet implemented")
	@Test
	public void testDirectEmailSend() {
		fail("Not yet implemented");
	}
}
