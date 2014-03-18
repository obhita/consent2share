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

import gov.samhsa.consent.ConsentGenException;
import gov.samhsa.consent2share.domain.clinicaldata.ClinicalDocument;
import gov.samhsa.consent2share.infrastructure.CodedConceptLookupService;
import gov.samhsa.consent2share.infrastructure.security.AccessReferenceMapper;
import gov.samhsa.consent2share.infrastructure.security.AuthenticatedUser;
import gov.samhsa.consent2share.infrastructure.security.UserContext;
import gov.samhsa.consent2share.service.clinicaldata.ClinicalDocumentService;
import gov.samhsa.consent2share.service.consent.ConsentService;
import gov.samhsa.consent2share.service.consentexport.ConsentExportService;
import gov.samhsa.consent2share.service.dto.AbstractPdfDto;
import gov.samhsa.consent2share.service.dto.AddConsentFieldsDto;
import gov.samhsa.consent2share.service.dto.AddConsentIndividualProviderDto;
import gov.samhsa.consent2share.service.dto.AddConsentOrganizationalProviderDto;
import gov.samhsa.consent2share.service.dto.ClinicalDocumentDto;
import gov.samhsa.consent2share.service.dto.ConsentDto;
import gov.samhsa.consent2share.service.dto.ConsentListDto;
import gov.samhsa.consent2share.service.dto.ConsentPdfDto;
import gov.samhsa.consent2share.service.dto.ConsentRevokationPdfDto;
import gov.samhsa.consent2share.service.dto.PatientProfileDto;
import gov.samhsa.consent2share.service.dto.SpecificMedicalInfoDto;
import gov.samhsa.consent2share.service.dto.TryMyPolicyDto;
import gov.samhsa.consent2share.service.notification.NotificationService;
import gov.samhsa.consent2share.service.patient.PatientNotFoundException;
import gov.samhsa.consent2share.service.patient.PatientService;
import gov.samhsa.consent2share.service.reference.AdministrativeGenderCodeService;
import gov.samhsa.consent2share.service.reference.ClinicalDocumentSectionTypeCodeService;
import gov.samhsa.consent2share.service.reference.ClinicalDocumentTypeCodeService;
import gov.samhsa.consent2share.service.reference.PurposeOfUseCodeService;
import gov.samhsa.consent2share.service.reference.StateCodeService;
import gov.samhsa.consent2share.service.valueset.ValueSetCategoryService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;

/**
 * The Class ConsentController.
 */
@Controller
@RequestMapping("/consents")
public class ConsentController {

	/** The consent service. */
	@Autowired
	private ConsentService consentService;

	/** The patient service. */
	@Autowired
	private PatientService patientService;

	/** The clinical document section type code service. */
	@Autowired
	private ClinicalDocumentSectionTypeCodeService clinicalDocumentSectionTypeCodeService;

	/** The clinical document type code service. */
	@Autowired
	private ClinicalDocumentTypeCodeService clinicalDocumentTypeCodeService;

	/** The clinical document type code service. */
	@Autowired
	private ClinicalDocumentService clinicalDocumentService;
	
	/** The hippa space coded concept lookup service. */
	@Autowired
	private CodedConceptLookupService hippaSpaceCodedConceptLookupService;

	/** The purpose of use code service. */
	@Autowired
	private PurposeOfUseCodeService purposeOfUseCodeService;

	@Autowired
	NotificationService notificationService;
	
	/** The value set category service. */
	@Autowired
	private ValueSetCategoryService valueSetCategoryService;
	
	/** The administrative gender code service. */
	@Autowired
	private AdministrativeGenderCodeService administrativeGenderCodeService;
	
	/** The state code service. */
	@Autowired
	private StateCodeService stateCodeService;

	/** The user context. */
	@Autowired
	private UserContext userContext;

	/** The consent export service. */
	@Autowired
	private ConsentExportService consentExportService;
	
	@Autowired
	private AccessReferenceMapper accessReferenceMapper;
	
	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/** The C32_ do c_ code. */
	public final String C32_DOC_CODE = "34133-9";
	private Properties properties;

	/**
	 * Sign consent.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param consentId
	 *            the consent id
	 * @return the string
	 */
	@RequestMapping(value = "signConsent.html", method = RequestMethod.POST)
	public String signConsent(Model model, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("consentId") String consentId) {
		AuthenticatedUser currentUser=userContext.getCurrentUser();
		Long patientId = patientService.findIdByUsername(currentUser.getUsername());
		Long directConsentId=accessReferenceMapper.getDirectReference(consentId);
		if(consentService.isConsentBelongToThisUser(directConsentId, patientId)){
			ConsentPdfDto consentPdfDto = consentService
					.findConsentPdfDto(directConsentId);
			String javascriptCode=consentService.createConsentEmbeddedWidget(consentPdfDto);
			model.addAttribute("returned_javascript", javascriptCode);
			return "views/consents/signConsent";
		}
		return "redirect:listConsents.html";
	}

	@RequestMapping(value = "signConsentRevokation", method = RequestMethod.POST)
	public String signConsentRevokation(Model model, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("consentId") String consentId,
			@RequestParam("revokationType") String revokationType) {
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		Long patientId = patientService.findIdByUsername(currentUser
				.getUsername());
		Long directConsentId=accessReferenceMapper.getDirectReference(consentId);
		consentService.addUnsignedConsentRevokationPdf(directConsentId,
				revokationType);
		if (consentService.isConsentBelongToThisUser(directConsentId, patientId)) {
			ConsentRevokationPdfDto consentRevokationPdfDto = consentService
					.findConsentRevokationPdfDto(directConsentId);
			consentRevokationPdfDto.setRevokationType(revokationType);
			String javascriptCode=consentService.createRevocationEmbeddedWidget(consentRevokationPdfDto);
			model.addAttribute("returned_javascript", javascriptCode);
			return "views/consents/signConsent";
		}

		return "redirect:listConsents.html";
	}

	/**
	 * Redirect to email login page.
	 * 
	 * @return the string
	 */
	@RequestMapping(value = "toEmail.html")
	public String redirectToEmailLoginPage() {
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		String email = patientService.findPatientEmailByUsername(currentUser
				.getUsername());
		String urlString = email.substring(email.indexOf('@') + 1);

		return "redirect:http://" + urlString;
	}

	/**
	 * Consent main page.
	 * 
	 * @param model
	 *            the model
	 * @param request
	 *            the request
	 * @return the string
	 */
	@RequestMapping(value = "listConsents.html")
	public String consentMainPage(Model model, HttpServletRequest request) {
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		String username = currentUser.getUsername();
		String notify = request.getParameter("notify");
		String notification = notificationService.notificationStage(username,
				notify);
		String emailSent = request.getParameter("emailsent");
		if (emailSent == null)
			emailSent = "false";
		List<ConsentListDto> consentListDtos = consentService
				.findAllConsentsDtoByPatient(patientService
						.findIdByUsername(currentUser.getUsername()));
		accessReferenceMapper.setupAccessReferenceMap(consentListDtos);
		model.addAttribute("listConsentDtos", consentListDtos);
		model.addAttribute("currentUser", currentUser);
		model.addAttribute("emailSent", emailSent);
		model.addAttribute("notification", notification);
		return "views/consents/listConsents";
	}
	

	@RequestMapping(value = "listConsents.html/checkConsentStatus", method = RequestMethod.GET)
	public void ajaxCheckConsentStatus(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		boolean isSigned = false;
		String[] consentPreSignStringList = request
				.getParameterValues("consentPreSignList");
		
		String[] consentPreRevokeStringList = request
				.getParameterValues("consentPreRevokeList");
		
		
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		List<ConsentListDto> consentListDtos = consentService
				.findAllConsentsDtoByPatient(patientService
						.findIdByUsername(currentUser.getUsername()));
		if (consentPreSignStringList!= null) {
			for (String consentIdString : consentPreSignStringList) {
				long consentId = Long.parseLong(consentIdString);
				for (ConsentListDto consentListDto : consentListDtos) {
					if (consentListDto.getId().equals(String.valueOf(consentId))&& consentListDto.getConsentStage().equals("CONSENT_SIGNED"))
						isSigned = true;
				}
			}
		}
		
		if (consentPreRevokeStringList!= null) {
			for (String consentIdString : consentPreRevokeStringList) {
				for (ConsentListDto consentListDto : consentListDtos) {
					if (consentListDto.getId().equals(consentIdString)
							&& consentListDto.getRevokeStage().equals("REVOCATION_REVOKED"))
						isSigned = true;
				}
			}
		}
		response.setContentType("text/plain;charset=utf-8");
		response.getWriter().println(isSigned);

	}

	/**
	 * Consent add.
	 * 
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "addConsent.html")
	public String consentAdd(Model model) {
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		PatientProfileDto currentPatient = null;
		
		if (currentUser.getIsProviderAdmin() == false) {
			currentPatient = patientService.findPatientProfileByUsername(currentUser.getUsername());
			
			if (currentPatient == null){
				throw new PatientNotFoundException("Patient not found by username");
			}
			
		}else{
			throw new IllegalStateException("ProviderAdmin users cannot access the ConsentController");
		}
		
		List<AddConsentIndividualProviderDto> individualProvidersDto = patientService
				.findAddConsentIndividualProviderDtoByUsername(currentPatient
						.getUsername());
		List<AddConsentOrganizationalProviderDto> organizationalProvidersDto = patientService
				.findAddConsentOrganizationalProviderDtoByUsername(currentPatient
						.getUsername());
		ConsentDto consentDto = consentService.makeConsentDto();
		
		consentDto.setUsername(currentPatient.getUsername());

		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Calendar today = Calendar.getInstance();
		Calendar oneYearFromNow = Calendar.getInstance();
		oneYearFromNow.add(Calendar.YEAR, 1);
		
		populateLookupCodes(model);

		List<AddConsentFieldsDto> sensitivityPolicyDto = valueSetCategoryService.findAllValueSetCategoriesAddConsentFieldsDto();
		List<AddConsentFieldsDto> purposeOfUseDto = purposeOfUseCodeService
				.findAllPurposeOfUseCodesAddConsentFieldsDto();
		List<AddConsentFieldsDto> clinicalDocumentSectionTypeDto = clinicalDocumentSectionTypeCodeService
				.findAllClinicalDocumentSectionTypeCodesAddConsentFieldsDto();
		List<AddConsentFieldsDto> clinicalDocumentTypeDto = clinicalDocumentTypeCodeService
				.findAllClinicalDocumentTypeCodesAddConsentFieldsDto();
		model.addAttribute("defaultStartDate",
				dateFormat.format(today.getTime()));
		model.addAttribute("defaultEndDate",
				dateFormat.format(oneYearFromNow.getTime()));
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
		model.addAttribute("addConsent", true);
		model.addAttribute("isProviderAdminUser", false);
		return "views/consents/addConsent";
	}

	@RequestMapping(value = "editConsent.html")
	public String consetEdit(@RequestParam(value = "patientId", defaultValue = "-1") long in_patientId, Model model,
			@RequestParam("consentId") String consentId) {

		AuthenticatedUser currentUser = userContext.getCurrentUser();
		PatientProfileDto currentPatient = null;
		Long directConsentId=accessReferenceMapper.getDirectReference(consentId);
		if (currentUser.getIsProviderAdmin() == true) {
			try{
				currentPatient = patientService.findPatient(in_patientId);
			}catch(IllegalArgumentException e){
				logger.warn("in_patientId was null when consentAdd called by a providerAdmin user.");
				logger.warn("Exception Stack Trace: " + e);
			}
			
			if (currentPatient == null){
				throw new PatientNotFoundException("Patient not found by id");
			}
		}else{
			currentPatient = patientService.findPatientProfileByUsername(currentUser.getUsername());
			
			if (currentPatient == null){
				throw new PatientNotFoundException("Patient not found by username");
			}
		}
		
		ConsentDto consentDto = consentService.findConsentById(directConsentId);
		consentDto.setId(consentId);
		
		//Make sure current patient and patient listed in consentDto match
		if(!consentDto.getUsername().equals(currentPatient.getUsername())){
			throw new IllegalStateException("Current patient username and username from consentDto do not match.");
		}else{
		
			List<AddConsentIndividualProviderDto> individualProvidersDto = patientService
					.findAddConsentIndividualProviderDtoByUsername(currentUser
							.getUsername());
			List<AddConsentOrganizationalProviderDto> organizationalProvidersDto = patientService
					.findAddConsentOrganizationalProviderDtoByUsername(currentUser
							.getUsername());
	
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Calendar today = Calendar.getInstance();
			Calendar oneYearFromNow = Calendar.getInstance();
			oneYearFromNow.add(Calendar.YEAR, 1);
			
			populateLookupCodes(model);
	
			List<AddConsentFieldsDto> sensitivityPolicyDto = valueSetCategoryService.findAllValueSetCategoriesAddConsentFieldsDto();
			List<AddConsentFieldsDto> purposeOfUseDto = purposeOfUseCodeService
					.findAllPurposeOfUseCodesAddConsentFieldsDto();
			List<AddConsentFieldsDto> clinicalDocumentSectionTypeDto = clinicalDocumentSectionTypeCodeService
					.findAllClinicalDocumentSectionTypeCodesAddConsentFieldsDto();
			List<AddConsentFieldsDto> clinicalDocumentTypeDto = clinicalDocumentTypeCodeService
					.findAllClinicalDocumentTypeCodesAddConsentFieldsDto();
			
			Set<SpecificMedicalInfoDto> clinicalConceptCodes = consentDto.getDoNotShareClinicalConceptCodes();
			
			model.addAttribute("DoNotShareClinicalConceptCodes", clinicalConceptCodes);
	
			model.addAttribute("defaultStartDate",
					dateFormat.format(today.getTime()));
			model.addAttribute("defaultEndDate",
					dateFormat.format(oneYearFromNow.getTime()));
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
			model.addAttribute("isProviderAdminUser", false);
			model.addAttribute("addConsent", false);
	
			return "views/consents/addConsent";
		}
	}

	/**
	 * Consent add post.
	 * 
	 * @param consentDto
	 *            the consent dto
	 * @param bindingResult
	 *            the binding result
	 * @param model
	 *            the model
	 * @param icd9
	 *            the icd9
	 * @return the string
	 * @throws ConsentGenException 
	 */
	@RequestMapping(value = "addConsent.html", method = RequestMethod.POST)
	public String consentAddPost(@Valid ConsentDto consentDto,
			BindingResult bindingResult, Model model,
			@RequestParam(value = "ICD9", required = false) HashSet<String> icd9) throws ConsentGenException {
		if (consentDto.getId()!=null) {
			String directConsentId=String.valueOf(accessReferenceMapper.getDirectReference(consentDto.getId()));
			consentDto.setId(directConsentId);
		}
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
			
			//Make sure username from consentDto matches a valid patient username
			if(patientService.findPatientProfileByUsername(consentDto.getUsername()) == null){
				throw new IllegalArgumentException("Username from consentDto does not match any valid patient usernames");
			}else{
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
	
				return "redirect:listConsents.html?notify=add";
			}
		}

		return "views/resourceNotFound";

	}

	/**
	 * Call hippa space.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param domain
	 *            the domain
	 * @param query
	 *            the query
	 * @param dataformat
	 *            the dataformat
	 * @return the string
	 */
	@RequestMapping(value = "/callHippaSpace.html")
	public String callHippaSpace(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("domain") String domain,
			@RequestParam("q") String query,
			@RequestParam("rt") String dataformat) {
		try {
			response.setHeader("Content-Type", "application/json");
			OutputStream out = response.getOutputStream();
			IOUtils.copy(
					new ByteArrayInputStream(
							hippaSpaceCodedConceptLookupService.searchCodes(
									domain, dataformat, query).getBytes()), out);
			out.flush();
			out.close();

		} catch (IOException e) {
			logger.warn("Error when calling HipaaSpace. The exception is:", e);
		}

		return null;
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
	@RequestMapping(value = "/downloadPdf.html", method = RequestMethod.POST)
	public String downloadConsentPdfFile(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("consentId") String consentId,
			@RequestParam("doctype") String docType) {
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		Long patientId = patientService.findIdByUsername(currentUser
				.getUsername());
		Long directConsentId=accessReferenceMapper.getDirectReference(consentId);
		AbstractPdfDto pdfDto = getPdfDto(docType, directConsentId);
		if (consentService.isConsentBelongToThisUser(directConsentId, patientId)) {
			try {
				response.setHeader("Content-Disposition",
						"attachment;filename=\"" + pdfDto.getFilename() + "\"");
				OutputStream out = response.getOutputStream();
				IOUtils.copy(new ByteArrayInputStream(pdfDto.getContent()), out);
				out.flush();
				out.close();

			} catch (IOException e) {
				logger.warn("Error while reading pdf file.");
				logger.warn("The exception is: ", e);
			}
		}

		return null;
	}

	private AbstractPdfDto getPdfDto(String docType, long consentId) {
		if (docType.equals("revokation"))
			return consentService.findConsentRevokationPdfDto(consentId);
		return consentService.findConsentPdfDto(consentId);
	}

	/**
	 * Delete consent.
	 * 
	 * @param consentId
	 *            the consent id
	 * @return the string
	 */
	@RequestMapping(value="/deleteConsents", method = RequestMethod.POST)
	public String deleteConsent(@RequestParam("consentId") String consentId) {
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		Long patientId = patientService.findIdByUsername(currentUser
				.getUsername());
		
		//TODO: Add code to display notice to user on delete failure
		boolean isDeleteSuccess = false;
		Long directConsentId=accessReferenceMapper.getDirectReference(consentId);
		if (consentService.isConsentBelongToThisUser(directConsentId, patientId)) {
			isDeleteSuccess = consentService.deleteConsent(directConsentId);
			return "redirect:/consents/listConsents.html";
		}
		return "redirect:/consents/listConsents.html";
	}

	/**
	 * Export consents.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param consentId
	 *            the consent id
	 * @return the string
	 * @throws ConsentGenException 
	 */
	@RequestMapping("exportCDAR2Consents/{consentId}")
	public String exportCDAR2Consents(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("consentId") String consentId) throws ConsentGenException {
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		Long patientId = patientService.findIdByUsername(currentUser
				.getUsername());
		Long directConsentId=accessReferenceMapper.getDirectReference(consentId);
		if (consentService.isConsentBelongToThisUser(directConsentId, patientId)) {
			String cdar2 = consentExportService.exportConsent2CDAR2(directConsentId);
			response.setContentType("application/xml");
			response.setHeader("Content-Disposition",
					"attachment;filename=CDAR2consent" + consentId);
			if (cdar2 != null) {
				try {
					OutputStream out = response.getOutputStream();
					IOUtils.copy(new ByteArrayInputStream(cdar2.getBytes()),
							out);

					out.flush();
					out.close();

				} catch (IOException e) {

					logger.warn("Error in exporting CDAR2 consent");
					logger.warn("The exception is", e);
				}

			}
			return null;
		}
		return "views/resourceNotFound";
	}

	@RequestMapping("exportXACMLConsents/{consentId}")
	public String exportXACMLConsents(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("consentId") String consentId) throws ConsentGenException {
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		Long patientId = patientService.findIdByUsername(currentUser
				.getUsername());
		Long directConsentId=accessReferenceMapper.getDirectReference(consentId);
		if (consentService.isConsentBelongToThisUser(directConsentId, patientId)) {
			String xacmal = consentExportService.exportConsent2XACML(directConsentId);

			response.setContentType("application/xml");
			response.setHeader("Content-Disposition",
					"attachment;filename=XACMLconsent" + consentId);
			if (xacmal != null) {
				try {
					OutputStream out = response.getOutputStream();
					IOUtils.copy(new ByteArrayInputStream(xacmal.getBytes()),
							out);

					out.flush();
					out.close();

				} catch (IOException e) {

					logger.warn("Error in exporting XACML consent");
					logger.warn("The exception is", e);
				}

			}
			return null;
		}
		return "views/resourceNotFound";
	}
	
	/**
	 * Return list of C32 documents patient uploaded
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @param consentId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "tryMyPolicyLookupC32Documents/{consentId}", method = RequestMethod.GET)
	public @ResponseBody TryMyPolicyDto tryMyPolicyLookupC32Documents(Model model, HttpServletRequest request,
			HttpServletResponse response, @PathVariable("consentId") String consentId) throws IOException {
		
		// TODO (AO): validate file
		
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		Long patientId = patientService.findIdByUsername(currentUser
				.getUsername());
		TryMyPolicyDto tryMyPolicyDto = new TryMyPolicyDto();
		Long directConsentId=accessReferenceMapper.getDirectReference(consentId);
		List<ClinicalDocument> clinicalDocuments = clinicalDocumentService.findByPatientId(patientId);
		List<ClinicalDocumentDto> c32Documents = new ArrayList<ClinicalDocumentDto>();
		
		for (int i = 0; i < clinicalDocuments.size(); i++) {
			
			// return c32 documents only
			if (clinicalDocuments.get(i).getClinicalDocumentTypeCode().getCode().equals(C32_DOC_CODE)) {
				ClinicalDocumentDto dto = new ClinicalDocumentDto();
				dto.setId(String.valueOf(clinicalDocuments.get(i).getId()));
				dto.setFilename(clinicalDocuments.get(i).getFilename());
				
				c32Documents.add(dto);
			}
		}
		
		// TODO: move dto to service 
		tryMyPolicyDto.setC32Documents(c32Documents);
		tryMyPolicyDto.setShareForPurposeOfUseCodesAndValues(consentService.findConsentById(directConsentId).getPurposeOfUseCodesAndValues());

	    return tryMyPolicyDto;
	}
	
	/**
	 * Return tagged C32 transformed wtih xslt
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @param consentId
	 * @param c32Id
	 * @return
	 * @throws ConsentGenException
	 * @throws IOException
	 * @throws TransformerConfigurationException
	 * @throws TransformerException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	@RequestMapping(value = "tryMyPolicyApply/consentId/{consentId}/c32Id/{c32Id}/purposeOfUse/{purposeOfUse}", method = RequestMethod.GET)
	public @ResponseBody String tryMyPolicyApply(Model model, HttpServletRequest request,
			HttpServletResponse response, @PathVariable("consentId") String consentId, @PathVariable("c32Id") Long c32Id, @PathVariable("purposeOfUse") String purposeOfUse) throws ConsentGenException, IOException, TransformerConfigurationException, TransformerException, ParserConfigurationException, SAXException {
		
		//String c32Id = request.getParameter("c32Id");
		//Long consentId = Long.parseLong(request.getParameter("consentId"));
		
		Long directConsentId=accessReferenceMapper.getDirectReference(consentId);
		
		logger.info("Consent id: " + consentId);
		logger.info("C32 id: " + c32Id);
		logger.info("Purpose of use: " + purposeOfUse);
		
		// move to service
		ClinicalDocument document = clinicalDocumentService.findClinicalDocument(c32Id);
		
		String originalC32 = new String(document.getContent());
		
		// get tagged c32 
		String taggedC32xml = consentService.getTaggedC32(originalC32, directConsentId, purposeOfUse);
		
		//response.setContentType("text/xml");
		response.setContentType("application/xml");
		
		logger.info("tagged c32: " + taggedC32xml);
	    
		return taggedC32xml;
	}
	
	/**
	 * Populate lookup codes.
	 * 
	 * @param model
	 *            the model
	 */
	private void populateLookupCodes(Model model) {
		model.addAttribute("administrativeGenderCodes", administrativeGenderCodeService.findAllAdministrativeGenderCodes());
		model.addAttribute("stateCodes", stateCodeService.findAllStateCodes());
	}
	

}
