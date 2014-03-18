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
package gov.samhsa.consent2share.web;

import flexjson.JSONDeserializer;
import gov.samhsa.consent.ConsentGenException;
import gov.samhsa.consent2share.dao.audit.JdbcAuditDao;
import gov.samhsa.consent2share.domain.reference.EntityType;
import gov.samhsa.consent2share.infrastructure.CodedConceptLookupService;
import gov.samhsa.consent2share.infrastructure.FieldValidator;
import gov.samhsa.consent2share.infrastructure.HashMapResultToProviderDtoConverter;
import gov.samhsa.consent2share.infrastructure.PixQueryService;
import gov.samhsa.consent2share.infrastructure.security.AuthenticatedUser;
import gov.samhsa.consent2share.infrastructure.security.AuthenticationFailedException;
import gov.samhsa.consent2share.infrastructure.security.UserContext;
import gov.samhsa.consent2share.service.admin.AdminService;
import gov.samhsa.consent2share.service.audit.AdminAuditService;
import gov.samhsa.consent2share.service.consent.ConsentNotFoundException;
import gov.samhsa.consent2share.service.consent.ConsentService;
import gov.samhsa.consent2share.service.dto.AbstractPdfDto;
import gov.samhsa.consent2share.service.dto.AddConsentFieldsDto;
import gov.samhsa.consent2share.service.dto.AddConsentIndividualProviderDto;
import gov.samhsa.consent2share.service.dto.AddConsentOrganizationalProviderDto;
import gov.samhsa.consent2share.service.dto.AdminProfileDto;
import gov.samhsa.consent2share.service.dto.BasicPatientAccountDto;
import gov.samhsa.consent2share.service.dto.ConsentDto;
import gov.samhsa.consent2share.service.dto.ConsentListDto;
import gov.samhsa.consent2share.service.dto.ConsentPdfDto;
import gov.samhsa.consent2share.service.dto.ConsentRevokationPdfDto;
import gov.samhsa.consent2share.service.dto.IndividualProviderDto;
import gov.samhsa.consent2share.service.dto.OrganizationalProviderDto;
import gov.samhsa.consent2share.service.dto.PatientAdminDto;
import gov.samhsa.consent2share.service.dto.PatientConnectionDto;
import gov.samhsa.consent2share.service.dto.PatientProfileDto;
import gov.samhsa.consent2share.service.dto.RecentAcctivityDto;
import gov.samhsa.consent2share.service.dto.SpecificMedicalInfoDto;
import gov.samhsa.consent2share.service.dto.StaffIndividualProviderDto;
import gov.samhsa.consent2share.service.dto.StaffOrganizationalProviderDto;
import gov.samhsa.consent2share.service.dto.SystemNotificationDto;
import gov.samhsa.consent2share.service.patient.PatientNotFoundException;
import gov.samhsa.consent2share.service.patient.PatientService;
import gov.samhsa.consent2share.service.provider.IndividualProviderService;
import gov.samhsa.consent2share.service.provider.OrganizationalProviderService;
import gov.samhsa.consent2share.service.provider.ProviderSearchLookupService;
import gov.samhsa.consent2share.service.reference.AdministrativeGenderCodeService;
import gov.samhsa.consent2share.service.reference.ClinicalDocumentSectionTypeCodeService;
import gov.samhsa.consent2share.service.reference.ClinicalDocumentTypeCodeService;
import gov.samhsa.consent2share.service.reference.LanguageCodeService;
import gov.samhsa.consent2share.service.reference.MaritalStatusCodeService;
import gov.samhsa.consent2share.service.reference.PurposeOfUseCodeService;
import gov.samhsa.consent2share.service.reference.RaceCodeService;
import gov.samhsa.consent2share.service.reference.ReligiousAffiliationCodeService;
import gov.samhsa.consent2share.service.reference.StateCodeService;
import gov.samhsa.consent2share.service.systemnotification.SystemNotificationService;
import gov.samhsa.consent2share.service.valueset.ValueSetCategoryService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Class AdminController.
 */
@Controller
@RequestMapping("/Administrator")
public class AdminController extends AbstractController {

	/** The patient service. */
	@Autowired
	PatientService patientService;

	/** The admin service. */
	@Autowired
	AdminService adminService;

	/** The consent service. */
	@Autowired
	ConsentService consentService;

	/** The user context. */
	@Autowired
	UserContext adminUserContext;

	/** The jdbc audit dao. */
	@Autowired
	JdbcAuditDao jdbcAuditDao;

	/** The patient audit service. */
	@Autowired
	AdminAuditService adminAuditService;
	
	@Autowired
	SystemNotificationService systemNotificationService;

	/** The individual provider service. */
	@Autowired
	IndividualProviderService individualProviderService;

	/** The organizational provider service. */
	@Autowired
	OrganizationalProviderService organizationalProviderService;

	/** The administrative gender code service. */
	@Autowired
	AdministrativeGenderCodeService administrativeGenderCodeService;

	/** The language code service. */
	@Autowired
	LanguageCodeService languageCodeService;

	/** The marital status code service. */
	@Autowired
	MaritalStatusCodeService maritalStatusCodeService;

	/** The race code service. */
	@Autowired
	RaceCodeService raceCodeService;

	/** The religious affiliation code service. */
	@Autowired
	ReligiousAffiliationCodeService religiousAffiliationCodeService;

	/** The state code service. */
	@Autowired
	StateCodeService stateCodeService;

	/** The clinical document section type code service. */
	@Autowired
	private ClinicalDocumentSectionTypeCodeService clinicalDocumentSectionTypeCodeService;

	/** The clinical document type code service. */
	@Autowired
	private ClinicalDocumentTypeCodeService clinicalDocumentTypeCodeService;

	/** The hippa space coded concept lookup service. */
	@Autowired
	private CodedConceptLookupService hippaSpaceCodedConceptLookupService;

	/** The purpose of use code service. */
	@Autowired
	private PurposeOfUseCodeService purposeOfUseCodeService;
	
	/** The value set category service. */
	@Autowired
	private ValueSetCategoryService valueSetCategoryService;

	/** The field validator. */
	@Autowired
	private FieldValidator fieldValidator;
	
	/** The provider search lookup service. */
	@Autowired
	private ProviderSearchLookupService providerSearchLookupService;

	// @Autowired
	// StaffIndividualProviderRepository staffIndividualProviderRepository;

	/** The maximum number of recent patient. */
	int maximumNumberOfRecentPatient = 5;

	/** The PIX Query Service. */
	@Autowired
	private PixQueryService pixQueryService;

	@Autowired
	HashMapResultToProviderDtoConverter hashMapResultToProviderDtoConverter;

	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Admin Home Page.
	 * 
	 * @param model
	 *            the model
	 * @param request
	 *            the request
	 * @param recentVisits
	 *            the recent visits
	 * @return the string
	 */
	@RequestMapping(value = "adminHome.html")
	public String adminHome(Model model, HttpServletRequest request) {
		AuthenticatedUser currentUser = adminUserContext.getCurrentUser();
		String notify = request.getParameter("notify");
		BasicPatientAccountDto basicPatientAccountDto = new BasicPatientAccountDto();

		List<RecentAcctivityDto> recentActivityDtos = adminAuditService
				.findAdminHistoryByUsername(currentUser.getUsername());
		AdminProfileDto adminProfileDto = adminService
				.findAdminProfileByUsername(currentUser.getUsername());
		model.addAttribute("adminProfileDto", adminProfileDto);

		model.addAttribute("notifyevent", notify);
		model.addAttribute(basicPatientAccountDto);
		model.addAttribute("recentActivityDtos", recentActivityDtos);

		return "views/Administrator/adminHome";
	}

	/**
	 * Admin Patient View Page.
	 * 
	 * @param model
	 *            the model
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param recentVisits
	 *            the recent visits
	 * @return the string
	 */
	@RequestMapping(value = "adminPatientView.html")
	public String adminPatientViewByPatientId(
			Model model,
			@RequestParam(value = "id", required = false, defaultValue = "-1") long patientId,
			@RequestParam(value = "username", required = false) String userName,
			@RequestParam(value = "notify", required = false) String notify,
			@RequestParam(value = "status", required = false) String status) {
		PatientProfileDto patientProfileDto;
		List<ConsentListDto> consentListDto;
		List<SystemNotificationDto> systemNotificationDtos = null;
		PatientConnectionDto patientConnectionDto = null;
		if (patientId != -1) {
			patientProfileDto = patientService.findPatient(patientId);
			consentListDto = consentService
					.findAllConsentsDtoByPatient(patientId);
			patientConnectionDto=patientService.findPatientConnectionById(patientId);
			systemNotificationDtos=systemNotificationService.findAllSystemNotificationDtosByPatient(patientId);
		} else if (userName != null) {
			patientProfileDto = patientService.findByUsername(userName);
			consentListDto = consentService
					.findAllConsentsDtoByUserName(userName);
		} else {
			return "redirect:adminHome.html";
		}
		AuthenticatedUser currentUser = adminUserContext.getCurrentUser();
		AdminProfileDto adminProfileDto = adminService
				.findAdminProfileByUsername(currentUser.getUsername());
		model.addAttribute("adminProfileDto", adminProfileDto);
		model.addAttribute("individualProviders", patientConnectionDto.getIndividualProviders());
		model.addAttribute("organizationalProviders", patientConnectionDto.getOrganizationalProviders());
		model.addAttribute("patientProfileDto", patientProfileDto);
		model.addAttribute("consentListDto", consentListDto);
		model.addAttribute("systemNotificationDtos", systemNotificationDtos);
		model.addAttribute("notifyEvent", notify);
		model.addAttribute("statusEvent", status);
		populateLookupCodes(model);

		return "views/Administrator/adminPatientView";
	}

	/**
	 * Submit Consent
	 * 
	 * @param consentId
	 * @param patientId
	 * @return string
	 */
	@RequestMapping(value = "adminPatientViewSubmitConsent.html", method = RequestMethod.POST)
	public String adminPatientViewSubmitConsent(
			@RequestParam(value = "consentId") long consentId,
			@RequestParam(value = "patientId") long patientId) {

		ConsentPdfDto consentPdfDto = consentService
				.findConsentPdfDto(consentId);
		if (consentService.isConsentBelongToThisUser(consentId, patientId)) {
			if(consentService.signConsent(consentPdfDto)==true);
			return "redirect:adminPatientView.html?notify=submit&status=success&id="
			+ patientId;
		}
		else {
			logger.warn("Unable to submit consent...");
			logger.warn("...consentService.signConsent(consentPdfDto) did not return true.");
			return "redirect:adminPatientView.html?notify=submit&status=fail&id="
					+ patientId;
		}

	}

	/**
	 * Delete consent.
	 * 
	 * @param consentId
	 *            the consent id
	 * @return the string
	 */
	@RequestMapping(value = "adminPatientViewDeleteConsent", method = RequestMethod.POST)
	public String adminPatientViewDeleteConsent(
			@RequestParam(value = "consentId") long consentId,
			@RequestParam(value = "patientId") long patientId) {

		boolean isDeleteSuccess = false;
		
		if (consentService.isConsentBelongToThisUser(consentId, patientId)) {
			isDeleteSuccess = consentService.deleteConsent(consentId);
			return "redirect:adminPatientView.html?notify=delete&status=success&id="
					+ patientId;
		} else {
			logger.warn("Unable to delete consent...");
			logger.warn("...consentService.deleteConsent(consentId) did not return true.");
			return "redirect:adminPatientView.html?notify=delete&status=fail&id="
					+ patientId;
		}
		
	}

	/**
	 * Admin edit patient profile.
	 * 
	 * @param patientProfileDto
	 *            the patient profile dto
	 * @param bindingResult
	 *            the binding result
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "adminEditPatientProfile.html", method = RequestMethod.POST)
	public String adminEditPatientProfile(
			@Valid PatientProfileDto patientProfileDto,
			BindingResult bindingResult, Model model) {
		fieldValidator.validate(patientProfileDto, bindingResult);
		Long patientId = patientProfileDto.getId();

		if (bindingResult.hasErrors()) {

			return "redirect:/Administrator/adminPatientView.html?notify=editpatientprofile&status=fail&id="
					+ patientId;
		} else {

			patientProfileDto.setAddressCountryCode("US");
			patientProfileDto.setUsername(patientService.findPatient(patientId)
					.getUsername());
			String mrn = patientProfileDto.getMedicalRecordNumber();
			String eId = null;
			if (mrn != null && !"".equals(mrn)) {
				eId = pixQueryService.getEid(patientProfileDto
						.getMedicalRecordNumber());
			}
			patientProfileDto.setEnterpriseIdentifier(eId);

			adminService.updatePatient(patientProfileDto);

			return "redirect:/Administrator/adminPatientView.html?notify=editpatientprofile&status=success&id="
					+ patientId;
		}

	}

	/**
	 * Admin Patient View Create Consent Page.
	 * 
	 * @param model
	 *            the model
	 * @param request
	 *            the request
	 * @return the string
	 */
	@RequestMapping(value = "adminPatientViewCreateConsent.html")
	public String adminPatientViewCreateConsent(
			@RequestParam(value = "id", defaultValue = "-1") long patientId,
			Model model) {
		if (patientId <= -1) {
			throw new IllegalArgumentException(
					"Invalid id passed in query string to adminPatientViewCreateConsent.html");
		} else {
			PatientProfileDto currentPatient = patientService
					.findPatient(patientId);

			if (currentPatient == null) {
				throw new PatientNotFoundException("Patient not found by id");
			}

			List<AddConsentIndividualProviderDto> individualProvidersDto = patientService
					.findAddConsentIndividualProviderDtoByUsername(currentPatient
							.getUsername());
			List<AddConsentOrganizationalProviderDto> organizationalProvidersDto = patientService
					.findAddConsentOrganizationalProviderDtoByUsername(currentPatient
							.getUsername());
			
			List<StaffIndividualProviderDto> favoriteIndividualProviders = individualProviderService.findAllStaffIndividualProvidersDto();
			List<StaffOrganizationalProviderDto> favoriteOrganizationalProviders = organizationalProviderService.findAllStaffOrganizationalProvidersDto();
			
			ConsentDto consentDto = consentService.makeConsentDto();

			consentDto.setUsername(currentPatient.getUsername());

			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Calendar today = Calendar.getInstance();
			Calendar oneYearFromNow = Calendar.getInstance();
			oneYearFromNow.add(Calendar.YEAR, 1);

			List<AddConsentFieldsDto> sensitivityPolicyDto = valueSetCategoryService.findAllValueSetCategoriesAddConsentFieldsDto();
			List<AddConsentFieldsDto> purposeOfUseDto = purposeOfUseCodeService
					.findAllPurposeOfUseCodesAddConsentFieldsDto();
			List<AddConsentFieldsDto> clinicalDocumentSectionTypeDto = clinicalDocumentSectionTypeCodeService
					.findAllClinicalDocumentSectionTypeCodesAddConsentFieldsDto();
			List<AddConsentFieldsDto> clinicalDocumentTypeDto = clinicalDocumentTypeCodeService
					.findAllClinicalDocumentTypeCodesAddConsentFieldsDto();
			
			populateLookupCodes(model);
			AuthenticatedUser currentUser = adminUserContext.getCurrentUser();
			AdminProfileDto adminProfileDto = adminService
					.findAdminProfileByUsername(currentUser.getUsername());
			model.addAttribute("adminProfileDto", adminProfileDto);
			
			model.addAttribute("defaultStartDate",
					dateFormat.format(today.getTime()));
			model.addAttribute("defaultEndDate",
					dateFormat.format(oneYearFromNow.getTime()));
			model.addAttribute("patientId", currentPatient.getId());
			model.addAttribute("patient_lname", currentPatient.getLastName());
			model.addAttribute("patient_fname", currentPatient.getFirstName());
			model.addAttribute("consentDto", consentDto);
			model.addAttribute("individualProvidersDto", individualProvidersDto);
			model.addAttribute("favoriteIndividualProviders", favoriteIndividualProviders);
			model.addAttribute("favoriteOrganizationalProviders", favoriteOrganizationalProviders);
			model.addAttribute("clinicalDocumentSectionType",
					clinicalDocumentSectionTypeDto);
			model.addAttribute("clinicalDocumentType", clinicalDocumentTypeDto);
			model.addAttribute("sensitivityPolicy", sensitivityPolicyDto);
			model.addAttribute("purposeOfUse", purposeOfUseDto);
			model.addAttribute("organizationalProvidersDto",
					organizationalProvidersDto);
			model.addAttribute("addConsent", true);
			model.addAttribute("isProviderAdminUser", true);
			return "views/Administrator/adminPatientViewCreateConsent";
		}
	}

	/**
	 * Admin Patient View Create Consent Form Submit.
	 * 
	 * @param consentDto
	 * @param bindingResult
	 * @param model
	 * @param icd9
	 * @return the string
	 * @throws ConsentGenException
	 */
	@RequestMapping(value = "adminPatientViewCreateConsent.html", method = RequestMethod.POST)
	public String adminPatientViewCreateConsent(
			@Valid ConsentDto consentDto,
			BindingResult bindingResult,
			Model model,
			@RequestParam(value = "ICD9", required = false) HashSet<String> icd9,
			@RequestParam(value = "isAddConsent") boolean isAddConsent)
			throws ConsentGenException {

		Set<String> isMadeTo = new HashSet<String>();
		Set<String> isMadeFrom = new HashSet<String>();
		if (consentDto.getOrganizationalProvidersDisclosureIsMadeTo() != null)
			isMadeTo.addAll(consentDto
					.getOrganizationalProvidersDisclosureIsMadeTo());
		if (consentDto.getProvidersDisclosureIsMadeTo() != null)
			isMadeTo.addAll(consentDto.getProvidersDisclosureIsMadeTo());
		if (consentDto.getOrganizationalProvidersPermittedToDisclose() != null)
			isMadeFrom.addAll(consentDto
					.getOrganizationalProvidersPermittedToDisclose());
		if (consentDto.getProvidersPermittedToDisclose() != null)
			isMadeFrom.addAll(consentDto.getProvidersPermittedToDisclose());
		if ((!isMadeTo.isEmpty())
				&& (!isMadeFrom.isEmpty())
				&& consentService.areThereDuplicatesInTwoSets(isMadeTo,
						isMadeFrom) == false) {

			long patientId = -1;

			try {
				patientId = patientService.findIdByUsername(consentDto
						.getUsername());
			} catch (NullPointerException e) {
				patientId = -1;
				logger.warn("patientService.findIdByUsername method unable to find patient record...");
				logger.warn("...username from consentDto does not match any valid patient usernames");
				logger.warn("The exception stack trace is: " + e);
				return "redirect:/Administrator/adminHome.html?notify=unknownerror";
			}

			Set<SpecificMedicalInfoDto> doNotShareClinicalConceptCodes = new HashSet<SpecificMedicalInfoDto>();
			if (icd9 != null)
				for (String item : icd9) {
					String icd9Item = item.replace("^^^", ",");
					SpecificMedicalInfoDto specificMedicalInfoDto = new SpecificMedicalInfoDto();
					specificMedicalInfoDto.setCodeSystem("ICD9");

					specificMedicalInfoDto.setCode(icd9Item.substring(0,
							icd9Item.indexOf(";")));
					specificMedicalInfoDto.setDisplayName(icd9Item
							.substring(icd9Item.indexOf(";") + 1));
					doNotShareClinicalConceptCodes.add(specificMedicalInfoDto);
				}
			consentDto
					.setDoNotShareClinicalConceptCodes(doNotShareClinicalConceptCodes);

			consentService.saveConsent(consentDto);

			if (isAddConsent == false) {
				return "redirect:/Administrator/adminPatientView.html?notify=editpatientconsent&status=success&id="
						+ patientId;
			} else {
				return "redirect:/Administrator/adminPatientView.html?notify=createpatientconsent&status=success&id="
						+ patientId;
			}
		}

		return "views/resourceNotFound";
	}

	/**
	 * Sign consent revokation.
	 * 
	 * @param consentId
	 *            the consent id
	 * @param revokationType
	 *            the revokation type
	 * @param patientId
	 *            the patient id
	 * @return the string
	 */
	@RequestMapping(value = "adminPatientViewRevokeConsent.html", method = RequestMethod.POST)
	public String signConsentRevokation(
			@RequestParam("consentId") long consentId,
			@RequestParam("revokationType") String revokationType,
			@RequestParam("patientId") long patientId) {

		consentService.addUnsignedConsentRevokationPdf(consentId,
				revokationType);
		ConsentRevokationPdfDto consentRevokationPdfDto = consentService
				.findConsentRevokationPdfDto(consentId);
		consentRevokationPdfDto.setRevokationType(revokationType);

		if (consentService.isConsentBelongToThisUser(consentId, patientId)) {
			consentService.signConsentRevokation(consentRevokationPdfDto);
			return "redirect:adminPatientView.html?notify=revokepatientconsent&status=success&id="
					+ patientId;
		}
		return "redirect:adminPatientView.html?notify=revokepatientconsent&status=fail&id="
				+ patientId;
	}

	/**
	 * Admin Patient View Edit Consent Page.
	 * 
	 * @param model
	 *            the model
	 * @param request
	 *            the request
	 * @return the string
	 */
	@RequestMapping(value = "adminPatientViewEditConsent.html")
	public String adminPatientViewEditConsent(
			@RequestParam(value = "consentId", defaultValue = "-1") long consentId,
			Model model) {
		if (consentId <= -1) {
			throw new IllegalArgumentException(
					"Invalid id passed in query string to adminPatientViewEditConsent.html");
		} else {
			ConsentDto consentDto = consentService.findConsentById(consentId);

			if (consentDto == null) {
				throw new ConsentNotFoundException("Consent not found by id");
			}

			PatientProfileDto currentPatient = patientService
					.findByUsername(consentDto.getUsername());

			if (currentPatient == null) {
				throw new PatientNotFoundException(
						"Patient not found by username");
			}

			List<AddConsentIndividualProviderDto> individualProvidersDto = patientService
					.findAddConsentIndividualProviderDtoByUsername(consentDto
							.getUsername());
			List<AddConsentOrganizationalProviderDto> organizationalProvidersDto = patientService
					.findAddConsentOrganizationalProviderDtoByUsername(consentDto
							.getUsername());

			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Calendar today = Calendar.getInstance();
			Calendar oneYearFromNow = Calendar.getInstance();
			oneYearFromNow.add(Calendar.YEAR, 1);

			List<AddConsentFieldsDto> sensitivityPolicyDto = valueSetCategoryService.findAllValueSetCategoriesAddConsentFieldsDto();
			List<AddConsentFieldsDto> purposeOfUseDto = purposeOfUseCodeService
					.findAllPurposeOfUseCodesAddConsentFieldsDto();
			List<AddConsentFieldsDto> clinicalDocumentSectionTypeDto = clinicalDocumentSectionTypeCodeService
					.findAllClinicalDocumentSectionTypeCodesAddConsentFieldsDto();
			List<AddConsentFieldsDto> clinicalDocumentTypeDto = clinicalDocumentTypeCodeService
					.findAllClinicalDocumentTypeCodesAddConsentFieldsDto();
			
			populateLookupCodes(model);
			AuthenticatedUser currentUser = adminUserContext.getCurrentUser();
			AdminProfileDto adminProfileDto = adminService
					.findAdminProfileByUsername(currentUser.getUsername());
			model.addAttribute("adminProfileDto", adminProfileDto);
			
			model.addAttribute("defaultStartDate",
					dateFormat.format(today.getTime()));
			model.addAttribute("defaultEndDate",
					dateFormat.format(oneYearFromNow.getTime()));
			model.addAttribute("patientId", currentPatient.getId());
			model.addAttribute("patient_lname", currentPatient.getLastName());
			model.addAttribute("patient_fname", currentPatient.getFirstName());
			model.addAttribute("consentDto", consentDto);
			model.addAttribute("individualProvidersDto", individualProvidersDto);
			model.addAttribute("clinicalDocumentSectionType",
					clinicalDocumentSectionTypeDto);
			model.addAttribute("clinicalDocumentType", clinicalDocumentTypeDto);
			model.addAttribute("sensitivityPolicy", sensitivityPolicyDto);
			model.addAttribute("purposeOfUse", purposeOfUseDto);
			model.addAttribute("organizationalProvidersDto",
					organizationalProvidersDto);
			model.addAttribute("addConsent", false);
			model.addAttribute("isProviderAdminUser", true);
			return "views/Administrator/adminPatientViewCreateConsent";
		}
	}

	/**
	 * Edits the admin profile.
	 * 
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "editAdminProfile.html")
	public String editAdminProfile(Model model) {
		AuthenticatedUser currentUser = adminUserContext.getCurrentUser();
		AdminProfileDto adminProfileDto = adminService
				.findAdminProfileByUsername(currentUser.getUsername());
		model.addAttribute("adminProfileDto", adminProfileDto);
		model.addAttribute("currentUser", currentUser);

		populateLookupCodes(model);

		return "views/Administrator/editAdminProfile";
	}

	/**
	 * Profile.
	 * 
	 * @param adminProfileDto
	 *            the admin profile dto
	 * @param bindingResult
	 *            the binding result
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "editAdminProfile.html", method = RequestMethod.POST)
	public String profile(@Valid AdminProfileDto adminProfileDto,
			BindingResult bindingResult, Model model) {

		fieldValidator.validate(adminProfileDto, bindingResult);

		if (bindingResult.hasErrors()) {

			populateLookupCodes(model);
			return "views/Administrator/editAdminProfile";
		} else {
			AuthenticatedUser currentUser = adminUserContext.getCurrentUser();
			model.addAttribute("currentUser", currentUser);
			try {
				adminService.updateAdministrator(adminProfileDto);
				model.addAttribute("updatedMessage",
						"Updated your profile successfully!");
			} catch (AuthenticationFailedException e) {
				model.addAttribute("updatedMessage",
						"Failed. Please check your username and password and try again.");
				AdminProfileDto originalAdminProfileDto = adminService
						.findAdminProfileByUsername(currentUser.getUsername());
				model.addAttribute("adminProfileDto", originalAdminProfileDto);
			}

			populateLookupCodes(model);

			return "views/Administrator/editAdminProfile";
		}
	}

	/**
	 * Download consent pdf file.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param consentId
	 *            the consent id
	 * @return the string
	 */
	@RequestMapping(value = "/downloadPdf.html", method = RequestMethod.GET)
	public String downloadConsentPdfFile(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("consentId") long consentId) {
		AbstractPdfDto pdfDto = consentService.findConsentContentDto(consentId);

		try {
			OutputStream out = response.getOutputStream();
			IOUtils.copy(new ByteArrayInputStream(pdfDto.getContent()), out);
			out.flush();
			out.close();

		} catch (IOException e) {
			logger.warn("Error while reading pdf file.");
			logger.warn("The exception is: ", e);
		}

		return null;
	}

	/**
	 * NOTE: THIS FUNCTION IS A TEMPORARY FUNCTION TO PROCESS THE ADMIN CREATE
	 * PATIENT ACCOUNT FORM WHEN IT IS SUBMITTED. THIS FUNCTION MUST BE MODIFIED
	 * BEFORE IT IS INTEGREATED WITH THE BACK-END CODE.
	 * 
	 * @param basicPatientAccountDto
	 *            the basic patient account dto
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "adminCreatePatientAccount.html", method = RequestMethod.POST)
	public String adminCreatePatientAccount(
			BasicPatientAccountDto basicPatientAccountDto, Model model) {
		System.out.println("FUNCTION NOT YET CREATED TO PROCESS THIS FORM");
		return "redirect:/Administrator/adminHome.html";
	}

	/**
	 * Gets the patient by first and last name.
	 * 
	 * @param firstName
	 *            the first name
	 * @param lastName
	 *            the last name
	 * @return the by first and last name
	 */
	@RequestMapping("/patientlookup/query")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	List<PatientAdminDto> getByFirstAndLastName(
			@RequestParam(value = "token", required = true) String token) {
		String[] tokens = token.split("\\s*(=>|,|\\s)\\s*");
		return patientService.findAllPatientByFirstNameAndLastName(tokens);
	}

	/**
	 * Search for a provider
	 * 
	 * @param providerDtoJSON
	 * @param patientUserName
	 * @param patientId
	 * @return
	 */
	/*TODO: Remove patientusername from list of @RequestParam's, and from all calls to this function from other files */
	@RequestMapping(value = "connectionProviderAdd.html", method = RequestMethod.POST)
	public @ResponseBody String addProvider(
			@RequestParam("querySent") String providerDtoJSON,
			@RequestParam(value = "patientusername", defaultValue = "") String patientUserName,
			@RequestParam("patientId") String patientId) {
		HashMap<String, String> Result = deserializeResult(providerDtoJSON);
		boolean isSuccess = false;
		
		try{
			patientUserName = patientService.findUsernameById(Long.valueOf(patientId).longValue());
		}catch(IllegalArgumentException e){
			/* this catches the exception thrown by Long.valueOf() in case
			   the input patientId string value cannot be converted to a long type,
			   and then throws an AjaxException to trigger the 400 HTTP Status Code
			   error to be returned to the client-side Ajax listener */
			isSuccess = false;
			throw new AjaxException(HttpStatus.BAD_REQUEST, "Unable to add this new provider because the request parameters contained invalid data.");
		}catch(RuntimeException e){
			logger.warn("An exception was caught in addProvider() method.");
			logger.warn("The exception stack trace is: ", e);
			throw new AjaxException(HttpStatus.INTERNAL_SERVER_ERROR, "An unknown error has occurred.");			
		}
		
		if ((EntityType.valueOf(Result.get("entityType")) == EntityType.Organization)) {
			OrganizationalProviderDto providerDto = new OrganizationalProviderDto();
			hashMapResultToProviderDtoConverter.setProviderDto(providerDto,
					Result);
			providerDto.setOrgName(Result.get("providerOrganizationName"));
			providerDto.setAuthorizedOfficialLastName(Result
					.get("authorizedOfficialLastName"));
			providerDto.setAuthorizedOfficialFirstName(Result
					.get("authorizedOfficialFirstName"));
			providerDto.setAuthorizedOfficialTitle(Result
					.get("authorizedOfficialTitleorPosition"));
			providerDto.setAuthorizedOfficialNamePrefix(Result
					.get("authorizedOfficialNamePrefixText"));
			providerDto.setAuthorizedOfficialTelephoneNumber(Result
					.get("authorizedOfficialTelephoneNumber"));
			providerDto.setUsername(patientUserName);
			
			isSuccess = organizationalProviderService.addNewOrganizationalProvider(providerDto);
		} else {
			IndividualProviderDto providerDto = new IndividualProviderDto();
			hashMapResultToProviderDtoConverter.setProviderDto(providerDto,
					Result);
			providerDto.setFirstName(Result.get("providerFirstName"));
			providerDto.setMiddleName(Result.get("providerMiddleName"));
			providerDto.setLastName(Result.get("providerLastName"));
			providerDto.setNamePrefix(Result.get("providerNamePrefixText"));
			providerDto.setNameSuffix(Result.get("providerNameSuffixText"));
			providerDto.setCredential(Result.get("providerCredentialText"));
			providerDto.setUsername(patientUserName);
			
			isSuccess = individualProviderService.addNewIndividualProvider(providerDto);
		}

		if(isSuccess){
			return "Success";
		}else{
			throw new AjaxException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to add this new provider because this provider already exists.");
		}
	}

	/**
	 * Delete Individual Provider
	 * 
	 * @param individualProviderid
	 * @param username
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/deleteIndividualProvider", method = RequestMethod.POST)
	public String deleteIndividualProvider(
			@RequestParam("individualProviderid") long individualProviderid,
			@RequestParam("username") String username, Model model) {
		if (individualProviderService
				.findIndividualProvider(individualProviderid) != null) {
			IndividualProviderDto individualProviderDto = individualProviderService
					.findIndividualProviderDto(individualProviderid);
			individualProviderDto.setUsername(username);
			try {
				individualProviderService
						.deleteIndividualProviderDto(individualProviderDto);
			} catch (Exception e) {
				return "redirect:adminPatientView.html?delete_provider_success=fail&username="
						+ username;
			}
		}
		return "redirect:adminPatientView.html?delete_provider_success=success&username="
				+ username;

	}

	/**
	 * Delete Organization Provider
	 * 
	 * @param organizationalProviderid
	 * @param username
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/deleteOrganizationalProvider", method = RequestMethod.POST)
	public String deleteOrganizationalProvider(
			@RequestParam("organizationalProviderid") long organizationalProviderid,
			@RequestParam("username") String username, Model model) {
		if (organizationalProviderService
				.findOrganizationalProvider(organizationalProviderid) != null) {
			OrganizationalProviderDto organizationalProviderDto = organizationalProviderService
					.findOrganizationalProviderDto(organizationalProviderid);
			organizationalProviderDto.setUsername(username);
			try {
				organizationalProviderService
						.deleteOrganizationalProviderDto(organizationalProviderDto);
			} catch (Exception e) {
				return "redirect:adminPatientView.html?delete_provider_success=fail&username="
						+ username;
			}
		}
		return "redirect:adminPatientView.html?delete_provider_success=success&username="
				+ username;

	}
	
	@RequestMapping(value = "/addStaffFavoriteIndividualProvider", method = RequestMethod.POST)
	public @ResponseBody String addStaffFavoriteIndividualProvider(@RequestParam("providerid") long providerId){
		boolean isSuccess = false;
		
		try{
			isSuccess = individualProviderService.addFavoriteIndividualProvider(providerId);
		}catch(IllegalArgumentException e){
			isSuccess = false;
			throw new AjaxException(HttpStatus.BAD_REQUEST, "Unable to add this provider to favorites because the request parameters contained invalid data.");
		}catch(RuntimeException e){
			logger.warn("An exception was caught in addStaffFavoriteIndividualProvider() method.");
			logger.warn("The exception stack trace is: ", e);
			throw new AjaxException(HttpStatus.INTERNAL_SERVER_ERROR, "An unknown error has occurred.");			
		}
		
		if(isSuccess == true){
			return "Success";
		}else{
			throw new AjaxException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to add this provider to favorites because this provider is already a favorite.");
		}
	}
	
	
	@RequestMapping(value = "/isStaffFavoriteIndividualProvider", method = RequestMethod.GET)
	public @ResponseBody String isStaffFavoriteIndividualProvider(@RequestParam("providerid") long providerId){
		boolean isFav = false;
		
		
		try{
			isFav = individualProviderService.isFavoriteIndividualProvider(providerId);
		}catch(IllegalArgumentException e){
			throw new AjaxException(HttpStatus.BAD_REQUEST, "Unable to determine if this provider is a favorite because the request parameters contained invalid data.");
		}catch(RuntimeException e){
			logger.warn("An exception was caught in isStaffFavoriteIndividualProvider() method.");
			logger.warn("The exception stack trace is: ", e);
			throw new AjaxException(HttpStatus.INTERNAL_SERVER_ERROR, "An unknown error has occurred.");			
		}
		
		return Boolean.toString(isFav);
	}
	
	
	@RequestMapping(value = "/isStaffFavoriteOrganizationalProvider", method = RequestMethod.GET)
	public @ResponseBody String isStaffFavoriteOrganizationalProvider(@RequestParam("providerid") long providerId){
		boolean isFav = false;
		
		
		try{
			isFav = organizationalProviderService.isFavoriteOrganizationalProvider(providerId);
		}catch(IllegalArgumentException e){
			throw new AjaxException(HttpStatus.BAD_REQUEST, "Unable to determine if this provider is a favorite because the request parameters contained invalid data.");
		}catch(RuntimeException e){
			logger.warn("An exception was caught in isStaffFavoriteOrganizationalProvider() method.");
			logger.warn("The exception stack trace is: ", e);
			throw new AjaxException(HttpStatus.INTERNAL_SERVER_ERROR, "An unknown error has occurred.");			
		}
		
		return Boolean.toString(isFav);
	}
	
	
	@RequestMapping(value = "/addStaffFavoriteOrganizationalProvider", method = RequestMethod.POST)
	public @ResponseBody String addStaffFavoriteOrganizationalProvider(@RequestParam("providerid") long providerId){
		boolean isSuccess = false;
		
		try{
			isSuccess = organizationalProviderService.addFavoriteOrganizationalProvider(providerId);
		}catch(IllegalArgumentException e){
			isSuccess = false;
			throw new AjaxException(HttpStatus.BAD_REQUEST, "Unable to add this provider to favorites because the request parameters contained invalid data.");
		}catch(RuntimeException e){
			logger.warn("An exception was caught in addStaffFavoriteOrganizationalProvider() method.");
			logger.warn("The exception stack trace is: ", e);
			throw new AjaxException(HttpStatus.INTERNAL_SERVER_ERROR, "An unknown error has occurred.");			
		}
		
		if(isSuccess == true){
			return "Success";
		}else{
			throw new AjaxException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to add this provider to favorites because this provider is already a favorite.");
		}
	}
	

	/**
	 * AJAX search for a provider
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "providerSearch.html", method = RequestMethod.GET)
	public String ajaxProviderSearch(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			response.setHeader("Content-Type", "application/json");
			OutputStream out = response.getOutputStream();
			String usstate = request.getParameter("usstate");
			String city = request.getParameter("city");
			String zipcode = request.getParameter("zipcode");
			String gender = request.getParameter("gender");
			String specialty = request.getParameter("specialty");
			String phone = request.getParameter("phone");
			String firstname = request.getParameter("firstname");
			String lastname = request.getParameter("lastname");

			if (providerSearchLookupService.isValidatedSearch(usstate, city,zipcode, gender, specialty, phone, firstname, lastname) == true) 
			{
				IOUtils.copy(
						new ByteArrayInputStream(providerSearchLookupService
								.providerSearch(usstate, city, zipcode, gender,
										specialty, phone, firstname, lastname)
								.getBytes()), out);
				out.flush();
				out.close();
			}

		} catch (IOException e) {
			logger.error(
					"Error when calling provider search. The exception is:", e);
		}

		return null;

	}

//	 For prove of concept purpose
//	 @RequestMapping("/test")
//	 public String testReadPatientAuditRev(){
//		 IndividualProvider individualProvider=individualProviderService.findIndividualProvider((long)1);
//		 individualProviderService.addFavouriteIndividualProvider(individualProvider);
//		 return "redirect:/Administrator/adminHome.html";
//	 }
//	 
//	 @RequestMapping("/test2")
//	 public String testReadPatientAuditRev2(){
//		 individualProviderService.deleteFavouriteIndividualProvider((long)1);
//		 return "redirect:/Administrator/adminHome.html";
//	 }

	/**
	 * Populate lookup codes.
	 * 
	 * @param model
	 *            the model
	 */
	private void populateLookupCodes(Model model) {

		model.addAttribute("administrativeGenderCodes",
				administrativeGenderCodeService
						.findAllAdministrativeGenderCodes());
		model.addAttribute("maritalStatusCodes",
				maritalStatusCodeService.findAllMaritalStatusCodes());
		model.addAttribute("religiousAffiliationCodes",
				religiousAffiliationCodeService
						.findAllReligiousAffiliationCodes());
		model.addAttribute("raceCodes", raceCodeService.findAllRaceCodes());
		model.addAttribute("languageCodes",
				languageCodeService.findAllLanguageCodes());

		model.addAttribute("stateCodes", stateCodeService.findAllStateCodes());
	}

	/**
	 * Deserialize result.
	 * 
	 * @param providerDtoJSON
	 *            the provider dto json
	 * @return the hash map
	 */
	public HashMap<String, String> deserializeResult(String providerDtoJSON) {
		return new JSONDeserializer<HashMap<String, String>>()
				.deserialize(providerDtoJSON);
	}

}
