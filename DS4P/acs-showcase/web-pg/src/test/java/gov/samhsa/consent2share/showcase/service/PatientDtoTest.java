package gov.samhsa.consent2share.showcase.service;

import static org.junit.Assert.assertNotNull;
import gov.samhsa.consent2share.c32.dto.GreenCCD;
import gov.samhsa.consent2share.pixclient.util.PixManagerBean;

import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponse;

import org.hl7.v3.types.MCCIIN000002UV01;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PatientDtoTest {

	@Test
	public void testPatientDto() {

		// Arrange
		PatientDto sut = new PatientDto();
		// Act
		sut.setC32Xml("c32Xml");
		sut.setDomain("domain");
		sut.setError(true);
		sut.setFullName("fullName");
		sut.setGreenCcd(new GreenCCD());
		sut.setHieDomain("hieDomain");
		sut.setHieId("hieId");
		sut.setHl7v3Xml("hl7v3Xml");
		sut.setId("id");
		sut.setPixAddMsg("pixAddMsg");
		sut.setPixManagerBean(new PixManagerBean());
		sut.setPixUpdateMsg("pixUpdateMsg");
		sut.setXdsbRegAddMsg(new MCCIIN000002UV01());
		sut.setXdsbRegUpdateMsg(new MCCIIN000002UV01());
		sut.seteId("eId");
		sut.setXdsbRepoProvideMsg(new RegistryResponse());

		// Assert
		assertNotNull(sut.getC32Xml());
		assertNotNull(sut.getDomain());
		assertNotNull(sut.isError());
		assertNotNull(sut.getFullName());
		assertNotNull(sut.getGreenCcd());
		assertNotNull(sut.getHieDomain());
		assertNotNull(sut.getHieId());
		assertNotNull(sut.getHl7v3Xml());
		assertNotNull(sut.getId());
		assertNotNull(sut.getPixAddMsg());
		assertNotNull(sut.getPixManagerBean());
		assertNotNull(sut.getPixUpdateMsg());
		assertNotNull(sut.getXdsbRegAddMsg());
		assertNotNull(sut.getXdsbRegUpdateMsg());
		assertNotNull(sut.geteId());
		assertNotNull(sut.getXdsbRepoProvideMsg());
	}
}
