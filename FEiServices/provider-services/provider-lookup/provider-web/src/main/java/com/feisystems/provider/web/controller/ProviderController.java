package com.feisystems.provider.web.controller;

import static com.feisystems.provider.service.ProviderService.SEARCH_STRING;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.feisystems.provider.service.ProviderService;
import com.feisystems.provider.service.dto.ProviderDto;
import com.feisystems.provider.service.dto.ProvidersDto;
import com.feisystems.provider.web.util.URLArgument;
import com.feisystems.provider.web.util.URLArgument.URLHelper;

/**
 * Spring wired Controller serving as the remote calling layer for the Provider
 * RESTful Service.
 *
 * URLs for this service are constructed generally as
 * /<i>context-root</i><b>/providers/</b><i>name1</i><b>/</b><i>value1</i>
 * <b>/</
 * b><i>name2</i><b>/</b><i>value2</i>...<i>name6</i><b>/</b><i>value6</i>. The
 * <i>value{n}</i> search parameters match on upper and lower case and should
 * generally be given in lower case. The number of specified parameters is
 * optional and could be 0-6. In the case where the number of specified
 * parameters is 0, the following URLs are possible:
 *
 * <ul>
 * <li>/<i>context-root</i><b>/providers/</b><i>npi</i> - returns a single
 * provider with unique NPI.
 * </ul>
 *
 * @see com.feisystems.provider.domain.web.util.URLArgument
 *      com.feisystems.provider.domain.web.util.URLArgument
 * @see <a href="http://www.springsource.org/">Spring Framework</a>
 *
 */
@Controller
public class ProviderController {

	@Autowired
	private ProviderService providerService;

	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/index}}", method = RequestMethod.GET)
	public String getProvider() {
		return "index";

	}

	@RequestMapping(value = "/providers/pageNumber/{pageNumber}/{npi}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody ProviderDto findProviderByNPI(
			@PathVariable("pageNumber") String pageNumber,
			@PathVariable("npi") String npi) {
		// FIXME (AO)
		return providerService.getProvider(npi);
	}

	@RequestMapping(value = "/providers/pageNumber/{pageNumber}/city/{city}/usstate/{usstate}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody ProvidersDto findbyCityAndState(
			@PathVariable("pageNumber") String pageNumber,
			@PathVariable("city") String city,
			@PathVariable("usstate") String usStateAbbreviation) {
		return new ProvidersDto(
				providerService
						.getByGenderCodeAndUSStateAbbreviationAndCityAndSpecialityAndTelephoneNumberAndLastNameAndFirstNameAndEntityTypeAndProviderOrganizationName(
								SEARCH_STRING, usStateAbbreviation, city,
								SEARCH_STRING, SEARCH_STRING, SEARCH_STRING,
								SEARCH_STRING, SEARCH_STRING, SEARCH_STRING,
								pageNumber));
	}

	@RequestMapping(value = "/providers/pageNumber/{pageNumber}/zipcode/{zipcode}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody ProvidersDto findbyPostalCode(
			@PathVariable("pageNumber") String pageNumber,
			@PathVariable("zipcode") String postalCode) {
		return new ProvidersDto(
				providerService
						.getByGenderCodeAndPostalCodeAndSpecialityAndTelephoneNumberAndLastNameAndFirstNameAndEntityTypeAndProviderOrganizationName(
								SEARCH_STRING, postalCode, SEARCH_STRING,
								SEARCH_STRING, SEARCH_STRING, SEARCH_STRING,
								SEARCH_STRING, SEARCH_STRING, pageNumber));
	}

	@RequestMapping(value = "/providers/pageNumber/{pageNumber}/usstate/{usstate}/city/{city}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody ProvidersDto findbyCityAndState2(
			@PathVariable("pageNumber") String pageNumber,
			@PathVariable("usstate") String usStateAbbreviation,
			@PathVariable("city") String city) {
		return new ProvidersDto(
				providerService
						.getByGenderCodeAndUSStateAbbreviationAndCityAndSpecialityAndTelephoneNumberAndLastNameAndFirstNameAndEntityTypeAndProviderOrganizationName(
								SEARCH_STRING, usStateAbbreviation, city,
								SEARCH_STRING, SEARCH_STRING, SEARCH_STRING,
								SEARCH_STRING, SEARCH_STRING, SEARCH_STRING,
								pageNumber));
	}

	@RequestMapping(value = "/providers/pageNumber/{pageNumber}/{name1}/{value1}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody ProvidersDto searchWithOneArguments(
			@PathVariable("pageNumber") String pageNumber,
			@PathVariable("name1") String name1,
			@PathVariable("value1") String value1, HttpServletRequest request) {
		String methodType = getMethodType(request);
		String[] args = URLArgument.createArgumentArray(methodType);
		fillArray(args, methodType, "pageNumber", pageNumber, name1, value1);
		return new ProvidersDto(executeProvidersMethod(methodType, args,
				isLastNameAndFacilityNameTogether(request)));
	}

	@RequestMapping(value = "/providers/pageNumber/{pageNumber}/{name1}/{value1}/{name2}/{value2}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody ProvidersDto searchWithTwoArguments(
			@PathVariable("pageNumber") String pageNumber,
			@PathVariable("name1") String name1,
			@PathVariable("value1") String value1,
			@PathVariable("name2") String name2,
			@PathVariable("value2") String value2, HttpServletRequest request) {
		String methodType = getMethodType(request);
		String[] args = URLArgument.createArgumentArray(methodType);
		fillArray(args, methodType, "pageNumber", pageNumber, name1, value1,
				name2, value2);
		return new ProvidersDto(executeProvidersMethod(methodType, args,
				isLastNameAndFacilityNameTogether(request)));
	}

	@RequestMapping(value = "/providers/pageNumber/{pageNumber}/{name1}/{value1}/{name2}/{value2}/{name3}/{value3}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody ProvidersDto searchWithThreeArguments(
			@PathVariable("pageNumber") String pageNumber,
			@PathVariable("name1") String name1,
			@PathVariable("value1") String value1,
			@PathVariable("name2") String name2,
			@PathVariable("value2") String value2,
			@PathVariable("name3") String name3,
			@PathVariable("value3") String value3, HttpServletRequest request) {
		String methodType = getMethodType(request);
		String[] args = URLArgument.createArgumentArray(methodType);
		fillArray(args, methodType, "pageNumber", pageNumber, name1, value1,
				name2, value2, name3, value3);
		return new ProvidersDto(executeProvidersMethod(methodType, args,
				isLastNameAndFacilityNameTogether(request)));
	}

	@RequestMapping(value = "/providers/pageNumber/{pageNumber}/{name1}/{value1}/{name2}/{value2}/{name3}/{value3}/{name4}/{value4}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody ProvidersDto searchWithFourArguments(
			@PathVariable("pageNumber") String pageNumber,
			@PathVariable("name1") String name1,
			@PathVariable("value1") String value1,
			@PathVariable("name2") String name2,
			@PathVariable("value2") String value2,
			@PathVariable("name3") String name3,
			@PathVariable("value3") String value3,
			@PathVariable("name4") String name4,
			@PathVariable("value4") String value4, HttpServletRequest request) {
		String methodType = getMethodType(request);
		String[] args = URLArgument.createArgumentArray(methodType);
		fillArray(args, methodType, "pageNumber", pageNumber, name1, value1,
				name2, value2, name3, value3, name4, value4);
		return new ProvidersDto(executeProvidersMethod(methodType, args,
				isLastNameAndFacilityNameTogether(request)));
	}

	@RequestMapping(value = "/providers/pageNumber/{pageNumber}/{name1}/{value1}/{name2}/{value2}/{name3}/{value3}/{name4}/{value4}/{name5}/{value5}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody ProvidersDto searchWithFiveArguments(
			@PathVariable("pageNumber") String pageNumber,
			@PathVariable("name1") String name1,
			@PathVariable("value1") String value1,
			@PathVariable("name2") String name2,
			@PathVariable("value2") String value2,
			@PathVariable("name3") String name3,
			@PathVariable("value3") String value3,
			@PathVariable("name4") String name4,
			@PathVariable("value4") String value4,
			@PathVariable("name5") String name5,
			@PathVariable("value5") String value5, HttpServletRequest request) {
		String methodType = getMethodType(request);
		String[] args = URLArgument.createArgumentArray(methodType);
		fillArray(args, methodType, "pageNumber", pageNumber, name1, value1,
				name2, value2, name3, value3, name4, value4, name5, value5);
		return new ProvidersDto(executeProvidersMethod(methodType, args,
				isLastNameAndFacilityNameTogether(request)));
	}

	@RequestMapping(value = "/providers/pageNumber/{pageNumber}/{name1}/{value1}/{name2}/{value2}/{name3}/{value3}/{name4}/{value4}/{name5}/{value5}/{name6}/{value6}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody ProvidersDto searchWithSixArguments(
			@PathVariable("pageNumber") String pageNumber,
			@PathVariable("name1") String name1,
			@PathVariable("value1") String value1,
			@PathVariable("name2") String name2,
			@PathVariable("value2") String value2,
			@PathVariable("name3") String name3,
			@PathVariable("value3") String value3,
			@PathVariable("name4") String name4,
			@PathVariable("value4") String value4,
			@PathVariable("name5") String name5,
			@PathVariable("value5") String value5,
			@PathVariable("name6") String name6,
			@PathVariable("value6") String value6, HttpServletRequest request) {
		String methodType = getMethodType(request);
		String[] args = URLArgument.createArgumentArray(methodType);
		fillArray(args, methodType, "pageNumber", pageNumber, name1, value1,
				name2, value2, name3, value3, name4, value4, name5, value5,
				name6, value6);
		return new ProvidersDto(executeProvidersMethod(methodType, args,
				isLastNameAndFacilityNameTogether(request)));
	}

	@RequestMapping(value = "/providers/pageNumber/{pageNumber}/{name1}/{value1}/{name2}/{value2}/{name3}/{value3}/{name4}/{value4}/{name5}/{value5}/{name6}/{value6}/{name7}/{value7}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody ProvidersDto searchWithSevenArguments(
			@PathVariable("pageNumber") String pageNumber,
			@PathVariable("name1") String name1,
			@PathVariable("value1") String value1,
			@PathVariable("name2") String name2,
			@PathVariable("value2") String value2,
			@PathVariable("name3") String name3,
			@PathVariable("value3") String value3,
			@PathVariable("name4") String name4,
			@PathVariable("value4") String value4,
			@PathVariable("name5") String name5,
			@PathVariable("value5") String value5,
			@PathVariable("name6") String name6,
			@PathVariable("value6") String value6,
			@PathVariable("name7") String name7,
			@PathVariable("value7") String value7, HttpServletRequest request) {
		String methodType = URLArgument.URLHelper.CITY_STATE_METHOD_TYPE;
		String[] args = URLArgument.createArgumentArray(methodType);
		fillArray(args, methodType, "pageNumber", pageNumber, name1, value1,
				name2, value2, name3, value3, name4, value4, name5, value5,
				name6, value6, name7, value7);
		return new ProvidersDto(executeProvidersMethod(methodType, args,
				isLastNameAndFacilityNameTogether(request)));
	}

	private String getMethodType(HttpServletRequest request) {
		String uri = request.getRequestURI();
		String methodType = URLArgument.getMethodType(uri);
		return methodType;
	}

	private void fillArray(String[] args, String methodType, String... argPair) {
		int numOfArgs = argPair.length / 2;
		for (int inx = 0; inx < numOfArgs; inx++) {
			String argName = argPair[inx * 2];
			String argValue = argPair[(inx * 2) + 1];
			URLArgument argEnum = URLArgument.getURLArgumentInstance(argName);
			argEnum.fillArgument(args, methodType, argValue);
		}

	}

	private Map<String, Object> executeProvidersMethod(String methodType,
			String[] args, boolean isLastNameAndFacilityNameTogether) {
		if (URLHelper.CITY_STATE_METHOD_TYPE.equals(methodType)) {
			if (isLastNameAndFacilityNameTogether) {
				return providerService
						.getByGenderCodeAndUSStateAbbreviationAndCityAndSpecialityAndTelephoneNumberAndLastNameOrProviderOrganizationNameAndFirstNameAndEntityType(
								args[0], args[1], args[2], args[3], args[4],
								args[10], args[6], args[7], args[9]);
			} else {
				return providerService
						.getByGenderCodeAndUSStateAbbreviationAndCityAndSpecialityAndTelephoneNumberAndLastNameAndFirstNameAndEntityTypeAndProviderOrganizationName(
								args[0], args[1], args[2], args[3], args[4],
								args[5], args[6], args[7], args[8], args[9]);
			}

		}
		if (isLastNameAndFacilityNameTogether) {
			return providerService
					.getByGenderCodeAndPostalCodeAndSpecialityAndTelephoneNumberAndLastNameOrProviderOrganizationNameAndFirstNameAndEntityType(
							args[0], args[1], args[2], args[3], args[9],
							args[5], args[6], args[8]);
		}
		return providerService
				.getByGenderCodeAndPostalCodeAndSpecialityAndTelephoneNumberAndLastNameAndFirstNameAndEntityTypeAndProviderOrganizationName(
						args[0], args[1], args[2], args[3], args[4], args[5],
						args[6], args[7], args[8]);

	}

	private boolean isLastNameAndFacilityNameTogether(HttpServletRequest request) {
		return URLArgument.isLastNameAndFacilityNameTogether(request
				.getRequestURI());
	}
}
