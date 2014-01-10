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

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ConsentBuilderImplTest {

	@InjectMocks
	ConsentBuilderImpl sut;
	
	@Mock
	ConsentDtoFactory consentDtoFactoryMock;
	
	@Mock
	ConsentTransformer consentTransformerMock;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	
	@Test
	public void testBuildConsent2Cdar2() throws ConsentGenException {

		// Arrange
		long consentId = 1;
		ConsentDto consentDtoMock = mock(ConsentDto.class);
		when(consentDtoFactoryMock.createConsentDto(anyLong())).thenReturn(consentDtoMock);
		String cdar2Mock = "cdar2";
		when(consentTransformerMock.transform(consentDtoMock,"c2cdar2.xsl",null)).thenReturn(cdar2Mock);
		
		// Act
		String cdar2 = sut.buildConsent2Cdar2(consentId);
		
		// Assert
		assertEquals(cdar2Mock, cdar2);		
	}

	@Test
	public void testBuildConsent2Cdar2_ConsentGenException() throws  ConsentGenException {
		// Arrange
		thrown.expect(ConsentGenException.class);
		long consentId = 1;
		ConsentDto consentDtoMock = mock(ConsentDto.class);
		when(consentDtoFactoryMock.createConsentDto(anyLong())).thenReturn(consentDtoMock);
		when(consentTransformerMock.transform(consentDtoMock,"c2cdar2.xsl",null)).thenThrow( new ConsentGenException("Error in saxon transform"));
		
		// Act
		sut.buildConsent2Cdar2(consentId);
		
		// Assert
	}

	
	
	@Test
	public void testBuildConsent2Xacml() throws ConsentGenException {
		// Arrange
		String eidMock = "eidMock";
		Long consentId = new Long(1);
		ConsentDto consentDtoMock = mock(ConsentDto.class);
		PatientDto patientDtoMock = mock(PatientDto.class);
		when(consentDtoFactoryMock.createConsentDto(consentId)).thenReturn(consentDtoMock);
		when(consentDtoMock.getPatientDto()).thenReturn(patientDtoMock);
		when(patientDtoMock.getEnterpriseIdentifier()).thenReturn(eidMock);
		String xacmlMock = "xacml";
		when(consentTransformerMock.transform(consentDtoMock,"c2xacml.xsl",eidMock)).thenReturn(xacmlMock);
		
		// Act
		String xacml = sut.buildConsent2Xacml(consentId);
		
		// Assert
		assertEquals(xacmlMock, xacml);		
	}
	
	@Test
	public void testBuildConsent2Xacml_ConsentGenException() throws  ConsentGenException {
		// Arrange
		String eidMock = "eidMock";
		thrown.expect(ConsentGenException.class);
		Long consentId = new Long(1);
		ConsentDto consentDtoMock = mock(ConsentDto.class);
		PatientDto patientDtoMock = mock(PatientDto.class);
		when(consentDtoMock.getPatientDto()).thenReturn(patientDtoMock);
		when(patientDtoMock.getEnterpriseIdentifier()).thenReturn(eidMock);
		when(consentDtoFactoryMock.createConsentDto(consentId)).thenReturn(consentDtoMock);
		when(consentTransformerMock.transform(consentDtoMock,"c2xacml.xsl",eidMock)).thenThrow( new ConsentGenException("Error in saxon transform"));
		
		// Act
		sut.buildConsent2Xacml(consentId);
		
		// Assert
	}


}
