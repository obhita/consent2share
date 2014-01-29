package gov.samhsa.acs.brms.tool;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.googlecode.sardine.Sardine;
import com.googlecode.sardine.util.SardineException;

public class FactModelGuvnorUploaderTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testIsValid() {
		// Arrange
		String[] args0 = {};
		String[] args1 = { "a" };
		String[] args2 = { "a", "a" };
		String[] args3 = { "a", "a", "a" };
		String[] args4 = { "a", "a", "a", "a" };
		String[] args5 = { "a", "a", "a", "a", "a" };

		// Act
		boolean isValid0 = FactModelGuvnorUploader.isValid(args0);
		boolean isValid1 = FactModelGuvnorUploader.isValid(args1);
		boolean isValid2 = FactModelGuvnorUploader.isValid(args2);
		boolean isValid3 = FactModelGuvnorUploader.isValid(args3);
		boolean isValid4 = FactModelGuvnorUploader.isValid(args4);
		boolean isValid5 = FactModelGuvnorUploader.isValid(args5);

		// Assert
		assertFalse(isValid0);
		assertFalse(isValid1);
		assertFalse(isValid2);
		assertTrue(isValid3);
		assertFalse(isValid4);
		assertFalse(isValid5);
	}

	@Test
	public void testBegin() {
		// Act
		Sardine sardine = FactModelGuvnorUploader.begin("a", "a");

		// Assert
		assertNotNull(sardine);
	}

	@Test
	public void testGetFileBytes() {
		// Arrange
		String testFilePath = "src/test/resources/testFile.txt";

		// Act
		byte[] bytes = FactModelGuvnorUploader.getFileBytes(testFilePath);

		// Assert
		assertEquals(116, bytes[0]);
		assertEquals(101, bytes[1]);
		assertEquals(115, bytes[2]);
		assertEquals(116, bytes[3]);
	}
	
	@Test
	public void testGetFileBytes_Throws_FileNotFoundException() {
		// Arrange
		String testFilePath = "src/test/resources/DoesntExist.txt";

		// Act
		byte[] bytes = FactModelGuvnorUploader.getFileBytes(testFilePath);

		// Assert
		assertNull(bytes);
	}

	@Test
	public void testPut() throws SardineException {
		// Arrange
		Sardine sardineMock = mock(Sardine.class);
		String putUrlMock = "putUrlMock";
		byte[] dataMock = { 116, 101, 115, 116 };
		doNothing().when(sardineMock).put(putUrlMock, dataMock);

		// Act
		FactModelGuvnorUploader.put(sardineMock, putUrlMock, dataMock);
	}

	@Test
	public void testPut_Throws_SardineException() throws SardineException {
		// Arrange
		Sardine sardineMock = mock(Sardine.class);
		String putUrlMock = "putUrlMock";
		byte[] dataMock = { 116, 101, 115, 116 };
		doThrow(SardineException.class).when(sardineMock).put(putUrlMock,
				dataMock);

		// Act
		FactModelGuvnorUploader.put(sardineMock, putUrlMock, dataMock);
	}
}
