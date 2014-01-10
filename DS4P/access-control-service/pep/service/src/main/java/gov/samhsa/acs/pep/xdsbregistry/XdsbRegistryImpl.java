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
package gov.samhsa.acs.pep.xdsbregistry;

import gov.samhsa.acs.common.exception.DS4PException;
import gov.samhsa.acs.xdsb.registry.wsclient.XdsbRegistryWebServiceClient;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;

import org.hl7.v3.PRPAIN201301UV02;
import org.hl7.v3.PRPAIN201302UV02;

public class XdsbRegistryImpl implements XdsbRegistry {

	private final String endpointAddress;

	public XdsbRegistryImpl(String endpointAddress) {
		this.endpointAddress = endpointAddress;
	}

	@Override
	public AdhocQueryResponse registryStoredQuery(AdhocQueryRequest input) {
		XdsbRegistryWebServiceClient client = new XdsbRegistryWebServiceClient(
				endpointAddress);
		AdhocQueryResponse result = client.registryStoredQuery(input);
		return result;
	}

	@Override
	public String addPatientRegistryRecord(PRPAIN201301UV02 input) {
		XdsbRegistryWebServiceClient client = new XdsbRegistryWebServiceClient(
				endpointAddress);
		String result = null;
		try {
			result = client.addPatientRegistryRecord(input);
		} catch (Throwable e) {
			throw new DS4PException(e.toString(), e);
		}
		return result;
	}

	@Override
	public String revisePatientRegistryRecord(PRPAIN201302UV02 input) {
		XdsbRegistryWebServiceClient client = new XdsbRegistryWebServiceClient(
				endpointAddress);
		String result = null;
		try {
			result = client.revisePatientRegistryRecord(input);
		} catch (Throwable e) {
			throw new DS4PException(e.toString(), e);
		}
		return result;
	}
}
