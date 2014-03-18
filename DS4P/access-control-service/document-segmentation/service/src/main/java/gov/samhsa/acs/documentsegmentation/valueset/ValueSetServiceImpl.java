/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 *   
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 *       * Redistributions of source code must retain the above copyright
 *         notice, this list of conditions and the following disclaimer.
 *       * Redistributions in binary form must reproduce the above copyright
 *         notice, this list of conditions and the following disclaimer in the
 *         documentation and/or other materials provided with the distribution.
 *       * Neither the name of the <organization> nor the
 *         names of its contributors may be used to endorse or promote products
 *         derived from this software without specific prior written permission.
 *   
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *   ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *   WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *   DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 *   DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *   (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *   LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *   ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *   (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *   SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.acs.documentsegmentation.valueset;

import gov.samhsa.acs.documentsegmentation.valueset.dto.ValueSetQueryDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * The Class ValueSetServiceImpl.
 */
public class ValueSetServiceImpl implements ValueSetService {

	/** The Constant PARAM_CODE. */
	public static final String PARAM_CODE = "code";

	/** The Constant PARAM_CODE_SYSTEM. */
	public static final String PARAM_CODE_SYSTEM = "codeSystemOid";

	/** The endpoint address. */
	private String endpointAddress;

	/** The rest url. */
	private String restUrl;

	/** The self signed ssl helper. */
	private SelfSignedSSLHelper selfSignedSSLHelper;

	/**
	 * Instantiates a new value set service impl.
	 */
	public ValueSetServiceImpl() {
		super();
	}

	/**
	 * Instantiates a new value set service impl.
	 * 
	 * @param endpointAddress
	 *            the endpoint address
	 */
	public ValueSetServiceImpl(String endpointAddress) {
		super();
		this.endpointAddress = endpointAddress;
		this.restUrl = null;
		this.selfSignedSSLHelper = null;
	}

	/**
	 * Instantiates a new value set service impl.
	 * 
	 * @param endpointAddress
	 *            the endpoint address
	 * @param selfSignedSSLHelper
	 *            the self signed ssl helper
	 */
	public ValueSetServiceImpl(String endpointAddress,
			SelfSignedSSLHelper selfSignedSSLHelper) {
		super();
		this.endpointAddress = endpointAddress;
		this.restUrl = null;
		this.selfSignedSSLHelper = selfSignedSSLHelper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.acs.documentsegmentation.valueset.ValueSetService#
	 * lookupValueSetCategories(java.lang.String, java.lang.String)
	 */
	@Override
	public Set<String> lookupValueSetCategories(String code, String codeSystem) {
		if (restUrl == null) {
			initRestUrl();
		}

		Map<String, String> parameterMap = createParameterMap(code, codeSystem);

		RestTemplate restTemplate = configureRestTemplate();

		if (selfSignedSSLHelper != null) {
			selfSignedSSLHelper.trustSelfSignedSSL();
		}

		ValueSetQueryDto resp = restTemplate.getForObject(restUrl,
				ValueSetQueryDto.class, parameterMap);

		return resp.getVsCategoryCodes();
	}
	
	/**
	 * Configures the rest template.
	 * 
	 * @return the rest template
	 */
	RestTemplate configureRestTemplate() {

		RestTemplate restTemplate = new RestTemplate();

		List<HttpMessageConverter<?>> messageConverters = restTemplate
				.getMessageConverters();

		List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>(
				messageConverters);

		MappingJacksonHttpMessageConverter jsonConverter = new MappingJacksonHttpMessageConverter();

		converters.add(jsonConverter);

		restTemplate.setMessageConverters(converters);

		return restTemplate;
	}

	/**
	 * Creates the parameter map.
	 * 
	 * @param code
	 *            the code
	 * @param codeSystem
	 *            the code system
	 * @return the map
	 */
	private Map<String, String> createParameterMap(String code,
			String codeSystem) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put(PARAM_CODE, code);
		paramMap.put(PARAM_CODE_SYSTEM, codeSystem);
		return paramMap;
	}

	/**
	 * Initializes the rest url.
	 */
	private void initRestUrl() {
		StringBuilder builder = new StringBuilder();
		builder.append(endpointAddress);
		builder.append("?");
		addParameter(builder, PARAM_CODE);
		builder.append("&");
		addParameter(builder, PARAM_CODE_SYSTEM);
		restUrl = builder.toString();
	}

	/**
	 * Adds the parameter.
	 * 
	 * @param builder
	 *            the builder
	 * @param param
	 *            the param
	 */
	private void addParameter(StringBuilder builder, String param) {
		builder.append(param);
		builder.append("={");
		builder.append(param);
		builder.append("}");
	}
}
