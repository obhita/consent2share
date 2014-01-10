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
import gov.samhsa.consent2share.infrastructure.CodedConceptLookupService;
import gov.samhsa.consent2share.infrastructure.security.AuthenticatedUser;
import gov.samhsa.consent2share.infrastructure.security.UserContext;
import gov.samhsa.consent2share.service.consent.ConsentService;
import gov.samhsa.consent2share.service.consentexport.ConsentExportService;
import gov.samhsa.consent2share.service.dto.AbstractPdfDto;
import gov.samhsa.consent2share.service.dto.AddConsentFieldsDto;
import gov.samhsa.consent2share.service.dto.AddConsentOrganizationalProviderDto;
import gov.samhsa.consent2share.service.dto.ConsentDto;
import gov.samhsa.consent2share.service.dto.AddConsentIndividualProviderDto;
import gov.samhsa.consent2share.service.dto.ConsentPdfDto;
import gov.samhsa.consent2share.service.dto.ConsentListDto;
import gov.samhsa.consent2share.service.dto.ConsentRevokationPdfDto;
import gov.samhsa.consent2share.service.dto.SpecificMedicalInfoDto;
import gov.samhsa.consent2share.service.dto.SpecificMedicalInfosSetDto;
import gov.samhsa.consent2share.service.notification.NotificationService;
import gov.samhsa.consent2share.service.patient.PatientService;
import gov.samhsa.consent2share.service.reference.ClinicalDocumentSectionTypeCodeService;
import gov.samhsa.consent2share.service.reference.ClinicalDocumentTypeCodeService;
import gov.samhsa.consent2share.service.reference.PurposeOfUseCodeService;
import gov.samhsa.consent2share.service.reference.SensitivityPolicyCodeService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

	/** The hippa space coded concept lookup service. */
	@Autowired
	private CodedConceptLookupService hippaSpaceCodedConceptLookupService;

	/** The purpose of use code service. */
	@Autowired
	private PurposeOfUseCodeService purposeOfUseCodeService;

	@Autowired
	NotificationService notificationService;

	/** The sensitivity policy code service. */
	@Autowired
	private SensitivityPolicyCodeService sensitivityPolicyCodeService;

	/** The user context. */
	@Autowired
	private UserContext userContext;

	/** The consent export service. */
	@Autowired
	private ConsentExportService consentExportService;

	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(this.getClass());

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
	public String signConsent(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("consentId") long consentId) {
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		Long patientId = patientService.findIdByUsername(currentUser
				.getUsername());
		ConsentPdfDto consentPdfDto = consentService
				.findConsentPdfDto(consentId);
		if (consentService.isConsentBelongToThisUser(consentId, patientId)) {
			consentService.signConsent(consentPdfDto);
			return "redirect:listConsents.html?emailsent=true";
		}

		return "redirect:listConsents.html?emailsent=false";
	}

	@RequestMapping(value = "signConsentRevokation", method = RequestMethod.POST)
	public String signConsentRevokation(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("consentId") long consentId,
			@RequestParam("revokationType") String revokationType) {
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		Long patientId = patientService.findIdByUsername(currentUser
				.getUsername());

		consentService.addUnsignedConsentRevokationPdf(consentId,
				revokationType);
		ConsentRevokationPdfDto consentRevokationPdfDto = consentService
				.findConsentRevokationPdfDto(consentId);
		consentRevokationPdfDto.setRevokationType(revokationType);

		if (consentService.isConsentBelongToThisUser(consentId, patientId)) {
			consentService.signConsentRevokation(consentRevokationPdfDto);
			return "redirect:listConsents.html?emailsent=true";
		}

		return "redirect:listConsents.html?emailsent=false";
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
					if (consentListDto.getId() == consentId
							&& consentListDto.getConsentStage() == 2)
						isSigned = true;
				}
			}
		}
		
		if (consentPreRevokeStringList!= null) {
			for (String consentIdString : consentPreRevokeStringList) {
				long consentId = Long.parseLong(consentIdString);
				for (ConsentListDto consentListDto : consentListDtos) {
					if (consentListDto.getId() == consentId
							&& consentListDto.getRevokeStage() == 2)
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
		List<AddConsentIndividualProviderDto> individualProvidersDto = patientService
				.findAddConsentIndividualProviderDtoByUsername(currentUser
						.getUsername());
		List<AddConsentOrganizationalProviderDto> organizationalProvidersDto = patientService
				.findAddConsentOrganizationalProviderDtoByUsername(currentUser
						.getUsername());
		ConsentDto consentDto = consentService.makeConsentDto();

		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Calendar today = Calendar.getInstance();
		Calendar oneYearFromNow = Calendar.getInstance();
		oneYearFromNow.add(Calendar.YEAR, 1);

		List<AddConsentFieldsDto> sensitivityPolicyDto = sensitivityPolicyCodeService
				.findAllSensitivityPolicyCodesAddConsentFieldsDto();
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
		model.addAttribute("currentUser", currentUser);
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
		return "views/consents/addConsent";
	}

	@RequestMapping(value = "editConsent.html")
	public String consetEdit(Model model,
			@RequestParam("consentId") long consentId) {
		ConsentDto consentDto = consentService.findConsentById(consentId);

		AuthenticatedUser currentUser = userContext.getCurrentUser();
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

		List<AddConsentFieldsDto> sensitivityPolicyDto = sensitivityPolicyCodeService
				.findAllSensitivityPolicyCodesAddConsentFieldsDto();
		List<AddConsentFieldsDto> purposeOfUseDto = purposeOfUseCodeService
				.findAllPurposeOfUseCodesAddConsentFieldsDto();
		List<AddConsentFieldsDto> clinicalDocumentSectionTypeDto = clinicalDocumentSectionTypeCodeService
				.findAllClinicalDocumentSectionTypeCodesAddConsentFieldsDto();
		List<AddConsentFieldsDto> clinicalDocumentTypeDto = clinicalDocumentTypeCodeService
				.findAllClinicalDocumentTypeCodesAddConsentFieldsDto();
		
		Set<SpecificMedicalInfoDto> clinicalConceptCodes = consentDto.getDoNotShareClinicalConceptCodes();
		
		SpecificMedicalInfosSetDto clinicalConceptCodesSet = new SpecificMedicalInfosSetDto(clinicalConceptCodes);
		
		model.addAttribute("DoNotShareClinicalConceptCodes", clinicalConceptCodes);

		model.addAttribute("defaultStartDate",
				dateFormat.format(today.getTime()));
		model.addAttribute("defaultEndDate",
				dateFormat.format(oneYearFromNow.getTime()));
		model.addAttribute("currentUser", currentUser);
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

		return "views/consents/addConsent";
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

		AuthenticatedUser currentUser = userContext.getCurrentUser();
		model.addAttribute("currentUser", currentUser);

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
			consentDto.setUsername(currentUser.getUsername());
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
			@RequestParam("consentId") long consentId,
			@RequestParam("doctype") String docType) {
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		Long patientId = patientService.findIdByUsername(currentUser
				.getUsername());
		AbstractPdfDto pdfDto = getPdfDto(docType, consentId);
		if (consentService.isConsentBelongToThisUser(consentId, patientId)) {
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
	public String deleteConsent(@RequestParam("consentId") Long consentId) {
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		Long patientId = patientService.findIdByUsername(currentUser
				.getUsername());

		if (consentService.isConsentBelongToThisUser(consentId, patientId)) {
			consentService.deleteConsent(consentId);
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
			@PathVariable("consentId") Long consentId) throws ConsentGenException {
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		Long patientId = patientService.findIdByUsername(currentUser
				.getUsername());

		if (consentService.isConsentBelongToThisUser(consentId, patientId)) {
			String cdar2 = consentExportService.exportConsent2CDAR2(consentId);
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
			@PathVariable("consentId") Long consentId) throws ConsentGenException {
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		Long patientId = patientService.findIdByUsername(currentUser
				.getUsername());

		if (consentService.isConsentBelongToThisUser(consentId, patientId)) {
			String xacmal = consentExportService.exportConsent2XACML(consentId);
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

}
