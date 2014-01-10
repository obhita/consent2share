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


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import gov.samhsa.consent2share.dao.audit.JdbcAuditDao;
import gov.samhsa.consent2share.infrastructure.FieldValidator;
import gov.samhsa.consent2share.infrastructure.PixQueryService;
import gov.samhsa.consent2share.infrastructure.security.AuthenticatedUser;
import gov.samhsa.consent2share.infrastructure.security.AuthenticationFailedException;
import gov.samhsa.consent2share.infrastructure.security.UserContext;
import gov.samhsa.consent2share.service.admin.AdminService;
import gov.samhsa.consent2share.service.audit.AdminAuditService;
import gov.samhsa.consent2share.service.consent.ConsentService;
import gov.samhsa.consent2share.service.dto.AbstractPdfDto;
import gov.samhsa.consent2share.service.dto.AdminProfileDto;
import gov.samhsa.consent2share.service.dto.BasicPatientAccountDto;
import gov.samhsa.consent2share.service.dto.ConsentListDto;
import gov.samhsa.consent2share.service.dto.PatientAdminDto;
import gov.samhsa.consent2share.service.dto.PatientProfileDto;
import gov.samhsa.consent2share.service.dto.RecentAcctivityDto;
import gov.samhsa.consent2share.service.patient.PatientService;
import gov.samhsa.consent2share.service.reference.AdministrativeGenderCodeService;
import gov.samhsa.consent2share.service.reference.LanguageCodeService;
import gov.samhsa.consent2share.service.reference.MaritalStatusCodeService;
import gov.samhsa.consent2share.service.reference.RaceCodeService;
import gov.samhsa.consent2share.service.reference.ReligiousAffiliationCodeService;
import gov.samhsa.consent2share.service.reference.StateCodeService;

import javax.servlet.http.Cookie;
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
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

// TODO: Auto-generated Javadoc
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
	
	/** The field validator. */
	@Autowired
	private FieldValidator fieldValidator;
	
	/** The maximum number of recent patient. */
	int maximumNumberOfRecentPatient=5;
	
	
	/** The PIX Query Service. */
	@Autowired
	private PixQueryService pixQueryService;
	
	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * Admin Home Page.
	 * 
	 * NOTE: THIS FUNCTION IS A TEMPORARY FUNCTION TO DISPLAY THE ADMIN HOME PAGE
	 * IT MUST BE MODIFIED BEFORE IT IS INTEGREATED WITH THE BACK-END CODE
	 *
	 * @param model the model
	 * @param request the request
	 * @param recentVisits the recent visits
	 * @return the string
	 */
	@RequestMapping(value = "adminHome.html")
	public String adminHome(Model model, HttpServletRequest request) {
		AuthenticatedUser currentUser = adminUserContext.getCurrentUser();
		String notify = request.getParameter("notify");
		BasicPatientAccountDto basicPatientAccountDto = new BasicPatientAccountDto();
		
		List<RecentAcctivityDto> recentActivityDtos = adminAuditService.findAdminHistoryByUsername(currentUser.getUsername());
		
		model.addAttribute("notifyevent", notify);
		model.addAttribute(basicPatientAccountDto);
		model.addAttribute("recentActivityDtos", recentActivityDtos);
		
		
		return "views/Administrator/adminHome";
	}
	
	
	/**
	 * Admin Patient View Page.
	 * 
	 * NOTE: THIS FUNCTION IS A TEMPORARY FUNCTION TO DISPLAY THE ADMIN PATIENT VIEW PAGE.
	 * IT MUST BE MODIFIED BEFORE IT IS INTEGREATED WITH THE BACK-END CODE
	 *
	 * @param model the model
	 * @param request the request
	 * @param response the response
	 * @param recentVisits the recent visits
	 * @return the string
	 */
	@RequestMapping(value = "adminPatientView.html")
	public String adminPatientView(Model model, @RequestParam("id") long patientId) {
		    PatientProfileDto patientProfileDto=patientService.findPatient(patientId);
			List<ConsentListDto> consentListDto=consentService.findAllConsentsDtoByPatient(patientId);
							
			model.addAttribute("patientProfileDto", patientProfileDto);
			model.addAttribute("consentListDto", consentListDto);
			populateLookupCodes(model);
							
			return "views/Administrator/adminPatientView";
		
	}
	
	/**
	 * Admin edit patient profile.
	 *
	 * @param patientProfileDto the patient profile dto
	 * @param bindingResult the binding result
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "adminEditPatientProfile.html", method = RequestMethod.POST)
	public String adminEditPatientProfile(@Valid PatientProfileDto patientProfileDto, BindingResult bindingResult, Model model) {
			fieldValidator.validate(patientProfileDto, bindingResult);
			Long pateintId=patientProfileDto.getId();
		
		if (bindingResult.hasErrors()) {

			return "redirect:/Administrator/adminPatientView.html?id="+pateintId;
		} else {
			
			patientProfileDto.setAddressCountryCode("US");
			patientProfileDto.setUsername(patientService.findPatient(pateintId).getUsername());
			String mrn = patientProfileDto.getMedicalRecordNumber();
			String eId = null;
			if(mrn != null && !"".equals(mrn)){
				eId = pixQueryService.getEid(patientProfileDto.getMedicalRecordNumber());
			}
			patientProfileDto.setEnterpriseIdentifier(eId);
			
				adminService.updatePatient(patientProfileDto);
				model.addAttribute("updatedMessage", "Updated your profile successfully!");
			
			
		    return "redirect:/Administrator/adminPatientView.html?id="+pateintId;
		}
		
	}
	
	/**
	 * Admin Patient View Create Consent Page.
	 * 
	 * NOTE: THIS FUNCTION IS A TEMPORARY FUNCTION TO DISPLAY THE ADMIN PATIENT VIEW CREATE CONSENT PAGE.
	 * IT MUST BE MODIFIED BEFORE IT IS INTEGREATED WITH THE BACK-END CODE
	 *
	 * @param model the model
	 * @param request the request
	 * @return the string
	 */
	@RequestMapping(value = "adminPatientViewCreateConsent.html")
	public String adminPatientViewCreateConsent(@RequestParam(value = "id", defaultValue = "-1") long patientID, Model model) {
		if(patientID <= -1){
			throw new ResourceNotFoundException();
		}else{
			model.addAttribute("patientID", patientID);
			return "views/Administrator/adminPatientViewCreateConsent";
		}
	}
	
	
	/**
	 * Edits the admin profile.
	 *
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "editAdminProfile.html")
	public String editAdminProfile(Model model) {
		AuthenticatedUser currentUser = adminUserContext.getCurrentUser();
		AdminProfileDto adminProfileDto = adminService.findAdminProfileByUsername(currentUser
				.getUsername());
		model.addAttribute("adminProfileDto", adminProfileDto);
		model.addAttribute("currentUser", currentUser);
		
		populateLookupCodes(model);
		
		return "views/Administrator/editAdminProfile";
	}
	
	
	/**
	 * Profile.
	 *
	 * @param adminProfileDto the admin profile dto
	 * @param bindingResult the binding result
	 * @param model the model
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
				model.addAttribute("updatedMessage", "Updated your profile successfully!");
			} catch (AuthenticationFailedException e) {
				model.addAttribute("updatedMessage", "Failed. Please check your username and password and try again.");
				AdminProfileDto originalAdminProfileDto=adminService.findAdminProfileByUsername(currentUser
						.getUsername());
				model.addAttribute("adminProfileDto", originalAdminProfileDto);
			}
			
			populateLookupCodes(model);

			return "views/Administrator/editAdminProfile";
		}
	}
	
	
	
	/**
	 * Download consent pdf file.
	 *
	 * @param request the request
	 * @param response the response
	 * @param consentId the consent id
	 * @return the string
	 */
	@RequestMapping(value = "/downloadPdf.html", method = RequestMethod.GET)
	public String downloadConsentPdfFile(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("consentId") long consentId)
			 {  	
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
	 * NOTE: THIS FUNCTION IS A TEMPORARY FUNCTION TO DISPLAY THE ADMIN HOME PAGE
	 * IT MUST BE MODIFIED BEFORE IT IS INTEGREATED WITH THE BACK-END CODE.
	 *
	 * @param basicPatientAccountDto the basic patient account dto
	 * @param request the request
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "adminCreatePatientAccount.html", method = RequestMethod.POST)
	public String adminCreatePatientAccount(BasicPatientAccountDto basicPatientAccountDto, HttpServletRequest request, Model model) {
		System.out.println("FUNCTION NOT YET CREATED TO PROCESS THIS FORM");
		return "redirect:/Administrator/adminHome.html";
	}
	
	
	
	/**
	 * Gets the by first and last name.
	 *
	 * @param firstName the first name
	 * @param lastName the last name
	 * @return the by first and last name
	 */
	@RequestMapping("/patientlookup/query")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<PatientAdminDto> getByFirstAndLastName(@RequestParam(value="token",required=true) String token){
		String[] tokens=token.split("\\s*(=>|,|\\s)\\s*");
		return patientService.findAllPatientByFirstNameAndLastName(tokens);
	}
	
	//For prove of concept purpose
	/**
	 * Test read patient audit rev.
	 *
	 * @return the string
	 */
	@RequestMapping("/testjdbc/readPatientAuditRev")
	public String testReadPatientAuditRev(){
		jdbcAuditDao.readPatientAuditRev();
		return "redirect:/Administrator/adminHome.html";
	}
	
	/**
	 * Populate lookup codes.
	 *
	 * @param model the model
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

}
