package gov.samhsa.acs.brms.guvnor;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;

public class GuvnorServiceImplTest {
	private static final String GUNVOR_REST_URL = "http://obhidevacs001/guvnor-5.5.0.Final-tomcat-6.0/rest/packages/AnnotationRules/source";
	private static final String USERNAME = "admin";
	private static final String PASSWORD = "admin";

	private GuvnorServiceImpl sut;

	@Test
	public void testGetVersionedRulesFromPackage() throws IOException {
		// Arrange
		sut = new GuvnorServiceImpl(GUNVOR_REST_URL, USERNAME, PASSWORD);

		// Act
		String response = sut.getVersionedRulesFromPackage();

		// Assert
		assertNotNull(response);
		assertNotEquals("", response);
	}
}
