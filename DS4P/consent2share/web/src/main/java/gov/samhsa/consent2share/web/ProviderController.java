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
import gov.samhsa.consent2share.domain.reference.EntityType;
import gov.samhsa.consent2share.infrastructure.HashMapResultToProviderDtoConverter;
import gov.samhsa.consent2share.infrastructure.security.AuthenticatedUser;
import gov.samhsa.consent2share.infrastructure.security.UserContext;
import gov.samhsa.consent2share.service.dto.IndividualProviderDto;
import gov.samhsa.consent2share.service.dto.LegalRepresentativeDto;
import gov.samhsa.consent2share.service.dto.LookupDto;
import gov.samhsa.consent2share.service.dto.OrganizationalProviderDto;
import gov.samhsa.consent2share.service.dto.PatientConnectionDto;
import gov.samhsa.consent2share.service.notification.NotificationService;
import gov.samhsa.consent2share.service.patient.PatientLegalRepresentativeAssociationService;
import gov.samhsa.consent2share.service.patient.PatientService;
import gov.samhsa.consent2share.service.provider.IndividualProviderService;
import gov.samhsa.consent2share.service.provider.OrganizationalProviderService;
import gov.samhsa.consent2share.service.provider.ProviderSearchLookupService;
import gov.samhsa.consent2share.service.reference.AdministrativeGenderCodeService;
import gov.samhsa.consent2share.service.reference.LanguageCodeService;
import gov.samhsa.consent2share.service.reference.LegalRepresentativeTypeCodeService;
import gov.samhsa.consent2share.service.reference.MaritalStatusCodeService;
import gov.samhsa.consent2share.service.reference.RaceCodeService;
import gov.samhsa.consent2share.service.reference.ReligiousAffiliationCodeService;
import gov.samhsa.consent2share.service.reference.StateCodeService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The Class ProviderController.
 */
@Controller
@RequestMapping("/patients")
public class ProviderController extends AbstractController {


	/** The patient service. */
	@Autowired
	PatientService patientService;
	
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
	
	@Autowired
	NotificationService notificationService;
	
	/** The legal representative type code service. */
	@Autowired
	LegalRepresentativeTypeCodeService legalRepresentativeTypeCodeService;
	
	/** The patient legal representative association service. */
	@Autowired
	PatientLegalRepresentativeAssociationService patientLegalRepresentativeAssociationService;
	
	/** The user context. */
	@Autowired
	UserContext userContext;
	
	@Autowired
	ProviderSearchLookupService providerSearchLookupService;
	
	@Autowired
	HashMapResultToProviderDtoConverter hashMapResultToProviderDtoConverter;
	
	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(this.getClass());


	/**
	 * Connection main.
	 *
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "connectionMain.html")
	public String connectionMain(Model model, HttpServletRequest request){
		
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		String username=currentUser.getUsername();
		String notify=request.getParameter("notify");
		String notification=notificationService.notificationStage(username,notify);
		PatientConnectionDto patientConnectionDto=patientService.findPatientConnectionByUsername(currentUser
				.getUsername());
		
		List<LookupDto> legalRepresentativeTypeCodes = legalRepresentativeTypeCodeService.findAllLegalRepresentativeTypeCodes();
		List<LegalRepresentativeDto> legalRepresentativeDtos = patientLegalRepresentativeAssociationService.getAllLegalRepresentativeDto();
		
		model.addAttribute("patientConnectionDto", patientConnectionDto);
		model.addAttribute("individualProviders", patientConnectionDto.getIndividualProviders());
		model.addAttribute("organizationalProviders", patientConnectionDto.getOrganizationalProviders());
		model.addAttribute("legalRepresentativeTypeCodes", legalRepresentativeTypeCodes);
		model.addAttribute("legalRepresentativeDtos", legalRepresentativeDtos);
		model.addAttribute("notification", notification);
		populateLookupCodes(model);
		
		return "views/patients/connectionMain";
	}
	
	
	 
	
	/**
	 * Delete individual provider.
	 *
	 * @param individualProviderid the individual providerid
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "connectionMain/deleteIndividualProvider", method = RequestMethod.POST)
	public String deleteIndividualProvider(@RequestParam("individualProviderid") long individualProviderid, Model model) {
			 AuthenticatedUser currentUser = userContext.getCurrentUser();
			 PatientConnectionDto patientConnectionDto=patientService.findPatientConnectionByUsername(currentUser
						.getUsername());
			for (IndividualProviderDto individualProviderDto: patientConnectionDto.getIndividualProviders())
				{
					if(individualProviderDto.getId()==individualProviderid)
					{  
						if(individualProviderDto.isDeletable()==false)
						return "redirect:/patients/connectionMain.html";
					}
				}
				
		    if(individualProviderService
					.findIndividualProvider(individualProviderid)!=null){
			IndividualProviderDto individualProviderDto = individualProviderService
					.findIndividualProviderDto(individualProviderid);
			individualProviderDto.setUsername(currentUser.getUsername());
			individualProviderService
					.deleteIndividualProviderDto(individualProviderDto);
		    }
			return "redirect:/patients/connectionMain.html";
		    
	
	}
	
	
	
	/**
	 * Delete organizational provider.
	 *
	 * @param organizationalProviderid the organizational providerid
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "connectionMain/deleteOrganizationalProvider", method = RequestMethod.POST)
	public String deleteOrganizationalProvider( @RequestParam("organizationalProviderid") long organizationalProviderid, Model model) {
		   
			AuthenticatedUser currentUser = userContext.getCurrentUser();
			PatientConnectionDto patientConnectionDto=patientService.findPatientConnectionByUsername(currentUser
						.getUsername());
			
			for (OrganizationalProviderDto organizationalProviderDto: patientConnectionDto.getOrganizationalProviders())
			{
				if(organizationalProviderDto.getId()==organizationalProviderid)
				{   
					if(organizationalProviderDto.isDeletable()==false)
					return "redirect:/patients/connectionMain.html";
				}
			}
			 if(organizationalProviderService
						.findOrganizationalProvider(organizationalProviderid)!=null){
			OrganizationalProviderDto organizationalProviderDto=organizationalProviderService.findOrganizationalProviderDto(organizationalProviderid);
		    organizationalProviderDto.setUsername(currentUser.getUsername());
		    organizationalProviderService.deleteOrganizationalProviderDto(organizationalProviderDto);
			 }
  		   return "redirect:/patients/connectionMain.html";
			
	
	}

	
	/**
	 * Provider search.
	 *
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "connectionProviderAdd.html")
	public String providerSearch(Model model) {
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		PatientConnectionDto patientConnectionDto=patientService.findPatientConnectionByUsername(currentUser
				.getUsername());
		Set<String> npiList=new HashSet<String>();
		for(IndividualProviderDto individualProviderDto:patientConnectionDto.getIndividualProviders())
		{
			npiList.add(individualProviderDto.getNpi());
		}
		for(OrganizationalProviderDto organizationalProviderDto:patientConnectionDto.getOrganizationalProviders())
		{
			npiList.add(organizationalProviderDto.getNpi());
		}
				
		model.addAttribute("currentUser", currentUser);
		model.addAttribute("npiList", npiList);
		populateLookupCodes(model);
		return "views/patients/connectionProviderAdd";
	}
	
	
	@RequestMapping(value = "providerSearch.html",  method = RequestMethod.GET)
	public String ajaxProviderSearch(HttpServletRequest request,
			HttpServletResponse response)
	{
		try {
			response.setHeader("Content-Type", "application/json");
			OutputStream out = response.getOutputStream();
			String usstate=request.getParameter("usstate");
			String city=request.getParameter("city");
			String zipcode=request.getParameter("zipcode");
			String gender=request.getParameter("gender");
			String specialty=request.getParameter("specialty");
			String phone=request.getParameter("phone");
			String firstname=request.getParameter("firstname");
			String lastname=request.getParameter("lastname");
			
			if(providerSearchLookupService.isValidatedSearch(usstate,city,zipcode,gender,specialty,phone,firstname,lastname)==true)
			{
				IOUtils.copy(new ByteArrayInputStream(providerSearchLookupService.providerSearch(usstate,city,zipcode,gender,specialty,phone,firstname,lastname).getBytes()), out);
				out.flush();
				out.close();
			}

		} catch (IOException e) {
			logger.error("Error when calling provider search. The exception is:", e);
		}

		return null;
		
		}
	
	/**
	 * Provider search.
	 *
	 * @param providerDtoJSON the provider dto json
	 * @return the string
	 */
	@RequestMapping(value = "connectionProviderAdd.html", method = RequestMethod.POST )
	public String providerSearch(@RequestParam("querySent") String providerDtoJSON) {
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		
		HashMap<String,String> Result=deserializeResult(providerDtoJSON);
		boolean isSuccess = false;
	
		if ((EntityType.valueOf(Result.get("entityType"))==EntityType.Organization)){
			OrganizationalProviderDto providerDto=new OrganizationalProviderDto();
			hashMapResultToProviderDtoConverter.setProviderDto(providerDto,Result);
			providerDto.setOrgName(Result.get("providerOrganizationName"));
			providerDto.setAuthorizedOfficialLastName(Result.get("authorizedOfficialLastName"));
			providerDto.setAuthorizedOfficialFirstName(Result.get("authorizedOfficialFirstName"));
			providerDto.setAuthorizedOfficialTitle(Result.get("authorizedOfficialTitleorPosition"));
			providerDto.setAuthorizedOfficialNamePrefix(Result.get("authorizedOfficialNamePrefixText"));
			providerDto.setAuthorizedOfficialTelephoneNumber(Result.get("authorizedOfficialTelephoneNumber"));
			providerDto.setUsername(currentUser.getUsername());
			
			isSuccess = organizationalProviderService.addNewOrganizationalProvider(providerDto);
		}
		else{
			IndividualProviderDto providerDto=new IndividualProviderDto();
			hashMapResultToProviderDtoConverter.setProviderDto(providerDto,Result);
			providerDto.setFirstName(Result.get("providerFirstName"));
			providerDto.setMiddleName(Result.get("providerMiddleName"));
			providerDto.setLastName(Result.get("providerLastName"));
			providerDto.setNamePrefix(Result.get("providerNamePrefixText"));
			providerDto.setNameSuffix(Result.get("providerNameSuffixText"));
			providerDto.setCredential(Result.get("providerCredentialText"));
			providerDto.setUsername(currentUser.getUsername());
			
			isSuccess = individualProviderService.addNewIndividualProvider(providerDto);
		}
		
		return "redirect:/patients/connectionMain.html";
	}
	
	
	
	
	@RequestMapping(value = "connectionProviderAdd_AJAX.html", method = RequestMethod.POST )
	public @ResponseBody String providerSearch_AJAX(@RequestParam("querySent") String providerDtoJSON) {
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		
		HashMap<String,String> Result=deserializeResult(providerDtoJSON);
		boolean isSuccess = false;
	
		if ((EntityType.valueOf(Result.get("entityType"))==EntityType.Organization)){
			OrganizationalProviderDto providerDto=new OrganizationalProviderDto();
			hashMapResultToProviderDtoConverter.setProviderDto(providerDto,Result);
			providerDto.setOrgName(Result.get("providerOrganizationName"));
			providerDto.setAuthorizedOfficialLastName(Result.get("authorizedOfficialLastName"));
			providerDto.setAuthorizedOfficialFirstName(Result.get("authorizedOfficialFirstName"));
			providerDto.setAuthorizedOfficialTitle(Result.get("authorizedOfficialTitleorPosition"));
			providerDto.setAuthorizedOfficialNamePrefix(Result.get("authorizedOfficialNamePrefixText"));
			providerDto.setAuthorizedOfficialTelephoneNumber(Result.get("authorizedOfficialTelephoneNumber"));
			providerDto.setUsername(currentUser.getUsername());
			
			isSuccess = organizationalProviderService.addNewOrganizationalProvider(providerDto);
		}
		else{
			IndividualProviderDto providerDto=new IndividualProviderDto();
			hashMapResultToProviderDtoConverter.setProviderDto(providerDto,Result);
			providerDto.setFirstName(Result.get("providerFirstName"));
			providerDto.setMiddleName(Result.get("providerMiddleName"));
			providerDto.setLastName(Result.get("providerLastName"));
			providerDto.setNamePrefix(Result.get("providerNamePrefixText"));
			providerDto.setNameSuffix(Result.get("providerNameSuffixText"));
			providerDto.setCredential(Result.get("providerCredentialText"));
			providerDto.setUsername(currentUser.getUsername());
			
			isSuccess = individualProviderService.addNewIndividualProvider(providerDto);
		}
		
		if(isSuccess){
			return "Success";
		}else{
			throw new AjaxException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to add this new provider because this provider already exists.");
		}
	}
	
	
	
	
	
	
	
	/**
	 * Deserialize result.
	 *
	 * @param providerDtoJSON the provider dto json
	 * @return the hash map
	 */
	public HashMap<String,String> deserializeResult(String providerDtoJSON){
		return new JSONDeserializer<HashMap<String,String>>().deserialize(providerDtoJSON);
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
