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
package gov.samhsa.acs.xdsb.registry.wsclient;

import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.common.tool.exception.SimpleMarshallerException;
import gov.samhsa.acs.xdsb.registry.wsclient.exception.XdsbRegistryClientException;
import gov.samhsa.ds4p.xdsbregistry.DocumentRegistryService;
import ihe.iti.xds_b._2007.XDSRegistry;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;

import org.hl7.v3.PRPAIN201301UV02;
import org.hl7.v3.PRPAIN201302UV02;
import org.hl7.v3.PRPAIN201304UV02;
import org.springframework.util.StringUtils;

/**
 * The Class XdsbRegistryWebServiceClient.
 */
public class XdsbRegistryWebServiceClient {

	/** The endpoint address. */
	private final String endpointAddress;

	/** The wsdl url. */
	final URL wsdlURL = this.getClass().getClassLoader()
			.getResource("XDS.b_registry.net.wsdl");

	/** The service name. */
	final QName serviceName = new QName("http://samhsa.gov/ds4p/XDSbRegistry/",
			"DocumentRegistryService");

	/** The port name. */
	final QName portName = new QName("http://samhsa.gov/ds4p/XDSbRegistry/",
			"XDSRegistry_HTTP_Endpoint");
	
	/** The marshaller. */
	private SimpleMarshaller marshaller;

	/**
	 * Instantiates a new xdsb registry web service client.
	 *
	 * @param endpointAddress the endpoint address
	 * @param marshaller the marshaller
	 */
	public XdsbRegistryWebServiceClient(String endpointAddress, SimpleMarshaller marshaller) {
		this.endpointAddress = endpointAddress;
		this.marshaller = marshaller;
	}

	/**
	 * Registry stored query.
	 * 
	 * @param registryStoredQuery
	 *            the registry stored query
	 * @return the adhoc query response
	 */
	public AdhocQueryResponse registryStoredQuery(
			AdhocQueryRequest registryStoredQuery) {
		XDSRegistry port = createPort();

		return port.registryStoredQuery(registryStoredQuery);
	}

	/**
	 * Adds the patient registry record.
	 *
	 * @param input the input
	 * @return the string
	 * @throws XdsbRegistryClientException the xdsb registry client exception
	 */
	public String addPatientRegistryRecord(PRPAIN201301UV02 input) throws XdsbRegistryClientException{
		XDSRegistry port = createPort();
		try {
			return marshaller.marshal(port.patientRegistryRecordAdded(input));
		} catch (SimpleMarshallerException e) {
			throw new XdsbRegistryClientException(e);
		}
	}

	/**
	 * Resolve patient registry duplicates.
	 *
	 * @param input the input
	 * @return the string
	 * @throws XdsbRegistryClientException the xdsb registry client exception
	 */
	public String resolvePatientRegistryDuplicates(PRPAIN201304UV02 input) throws XdsbRegistryClientException{
		XDSRegistry port = createPort();
		try {
			return marshaller.marshal(port.patientRegistryDuplicatesResolved(input));
		} catch (SimpleMarshallerException e) {
			throw new XdsbRegistryClientException(e);
		}
	}

	/**
	 * Revise patient registry record.
	 *
	 * @param input the input
	 * @return the string
	 * @throws XdsbRegistryClientException the xdsb registry client exception
	 */
	public String revisePatientRegistryRecord(PRPAIN201302UV02 input) throws XdsbRegistryClientException{
		XDSRegistry port = createPort();
		try {
			return marshaller.marshal(port.patientRegistryRecordRevised(input));
		} catch (SimpleMarshallerException e) {
			throw new XdsbRegistryClientException(e);
		}
	}

	/**
	 * Creates the port.
	 * 
	 * @return the xDS registry
	 */
	private XDSRegistry createPort() {
		XDSRegistry port = new DocumentRegistryService(wsdlURL, serviceName)
				.getXDSRegistryHTTPEndpoint();

		if (StringUtils.hasText(this.endpointAddress)) {
			BindingProvider bp = (BindingProvider) port;
			bp.getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointAddress);
		}
		
		return port;
	}
}
