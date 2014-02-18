package gov.samhsa.consent2share.web;

import gov.samhsa.consent2share.infrastructure.security.AuthenticatedUser;
import gov.samhsa.consent2share.infrastructure.security.UserContext;
import gov.samhsa.consent2share.service.dto.ConceptCodeDto;
import gov.samhsa.consent2share.service.dto.ConceptCodeVSCSDto;
import gov.samhsa.consent2share.service.valueset.ConceptCodeNotFoundException;
import gov.samhsa.consent2share.service.valueset.ConceptCodeService;
import gov.samhsa.consent2share.service.valueset.ValueSetNotFoundException;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("conceptCode")
@RequestMapping("/sysadmin") 
public class ConceptCodeController extends AbstractNodeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConceptCodeController.class);
    
    protected static final String ERROR_MESSAGE_KEY_DELETED_CONCEPTCODE_WAS_NOT_FOUND = "Deleted conceptCode was not found.";
    protected static final String ERROR_MESSAGE_KEY_EDITED_CONCEPTCODE_WAS_NOT_FOUND = "Edited conceptCode was not found.";
    
    protected static final String MODEL_ATTIRUTE_CONCEPTCODEDTO = "conceptCodeDto";
    protected static final String MODEL_ATTRIBUTE_CONCEPTCODEDTOS = "conceptCodeDtos";
    
    protected static final String CONCEPTCODE_ADD_FORM_VIEW = "views/sysadmin/conceptCodeAdd";
    protected static final String CONCEPTCODE_EDIT_FORM_VIEW = "views/sysadmin/conceptCodeEdit";
    protected static final String CONCEPTCODE_LIST_VIEW = "views/sysadmin/conceptCodeList";
    
    protected static final String REQUEST_MAPPING_LIST = "/conceptCodeList";
    protected static final String REDIRECT_MAPPING_LIST = "../conceptCodeList";   
    protected static final String REDIRECT_ID_MAPPING_LIST = "../../conceptCodeList";   

    protected static final String MODEL_ATTIRUTE_CONCEPTCODEVSCSDTO = "conceptCodeVSCSDto";

    
    @Resource
    private ConceptCodeService conceptCodeService;
    
	/** The user context. */
	@Autowired
	UserContext userContext;
	
    /**
     * Processes create conceptCode requests.
     * @param model
     * @return  The name of the create conceptCode form view.
     */
    @RequestMapping(value = REQUEST_MAPPING_LIST, method = RequestMethod.GET)
    public String showCreateConceptCodeForm(Model model, HttpServletRequest request){
    	
        LOGGER.debug("Rendering Concept Code list page");
        
        List<ConceptCodeDto> conceptCodes = conceptCodeService.findAll();
        	        
        model.addAttribute(MODEL_ATTRIBUTE_CONCEPTCODEDTOS, conceptCodes);
        return CONCEPTCODE_LIST_VIEW;
    }
    
	
    /**
     * Processes the submissions of create conceptCode form.
     * @param created   The information of the created conceptCodes.
     * @param bindingResult
     * @param attributes
     * @return
     */
    @RequestMapping(value = "conceptCodeAdd.html", method = RequestMethod.GET)
    public String conceptCodeAddForm(Model model, HttpServletRequest request) {
        LOGGER.debug("Rendering Concept Code Add Form ");
        ConceptCodeVSCSDto conceptCodeVSCSDto = conceptCodeService.create();
       
		ConceptCodeDto conceptCodeDto = (ConceptCodeDto) model.asMap().get(MODEL_ATTIRUTE_CONCEPTCODEDTO);
        if(conceptCodeDto != null)
        	 conceptCodeVSCSDto.setConceptCodeDto(conceptCodeDto);
        
        model.addAttribute(MODEL_ATTIRUTE_CONCEPTCODEVSCSDTO, conceptCodeVSCSDto);
		model.addAttribute(MODEL_ATTIRUTE_CONCEPTCODEDTO, conceptCodeDto);

		return CONCEPTCODE_ADD_FORM_VIEW;
    }
    
       
    /*
     * Processes the submissions of create conceptCode form.
     * @param created   The information of the created conceptCodes.
     * @param bindingResult
     * @param attributes
     * @return
     */
    @RequestMapping(value = "/conceptCode/create", method = RequestMethod.POST)
    public String ceateConceptCodeForm(@Valid @ModelAttribute(MODEL_ATTIRUTE_CONCEPTCODEDTO) ConceptCodeDto created, RedirectAttributes redirectAttribute,  Model model) {
        LOGGER.debug("Create conceptCode form was submitted with information: " + created);
		
        AuthenticatedUser currentUser = userContext.getCurrentUser();
        String path = REDIRECT_MAPPING_LIST; 
		try {
			created.setUserName(currentUser.getUsername());
			created = conceptCodeService.create(created);
			created.setError(false);
			created.setSuccessMessage("Concept Code  with code:" + created.getCode() + " and Name: " + created.getName() + " is Added Successfully");
		} catch (DataIntegrityViolationException ex) {
			LOGGER.info(ex.getLocalizedMessage());
			Throwable t = ex.getCause();
			String message = null;
			if (t != null) {
				message = "Cause: " + t.getMessage();
			}
			created.setError(true);
			created.setErrorMessage("Concept Code is not Added " + message);
			path = "../conceptCodeAdd.html";
		} catch (ValueSetNotFoundException e) {
            LOGGER.debug("No value set found with id: " + created.getValueSetId());
            created.setError(true);
            created.setErrorMessage(ERROR_MESSAGE_KEY_DELETED_CONCEPTCODE_WAS_NOT_FOUND);
        }
		model.addAttribute(MODEL_ATTIRUTE_CONCEPTCODEDTO, created);
		redirectAttribute.addFlashAttribute(MODEL_ATTIRUTE_CONCEPTCODEDTO, created);
        return  createRedirectViewPath(path);
    }	    
    
	/**
     * Processes delete conceptCode requests.
     * @param id    The id of the deleted conceptCode.
     * @param attributes
     * @return
     */
    @RequestMapping(value = "/conceptCode/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttribute) {
        LOGGER.debug("Deleting conceptCode with id: " + id);
        ConceptCodeDto deleted = new ConceptCodeDto();
        try {
            deleted = conceptCodeService.delete(id);
            deleted.setError(false);
            deleted.setSuccessMessage(" ConceptCode with Code: " + deleted.getCode() + " and Name: " + deleted.getName() +" is deleted Successfully. ");
        } catch (ConceptCodeNotFoundException e) {
            LOGGER.debug("No conceptCode found with id: " + id);
            deleted.setError(true);
            deleted.setErrorMessage(ERROR_MESSAGE_KEY_DELETED_CONCEPTCODE_WAS_NOT_FOUND);
        }
        redirectAttribute.addFlashAttribute(MODEL_ATTIRUTE_CONCEPTCODEDTO, deleted);
        return createRedirectViewPath(REDIRECT_ID_MAPPING_LIST);
    }



    /**
     * Processes edit conceptCode requests.
     * @param id    The id of the edited conceptCode.
     * @param model
     * @param attributes
     * @return  The name of the edit conceptCode form view.
     */
    @RequestMapping(value = "/conceptCode/edit/{id}", method = RequestMethod.GET)
    public String showEditConceptCodeForm(@PathVariable("id") Long id, Model model, RedirectAttributes attributes) {
        LOGGER.debug("Rendering edit conceptCode form for conceptCode with id: " + id);
        ConceptCodeDto conceptCodeDto = conceptCodeService.findById(id);
        if (conceptCodeDto == null) {
            LOGGER.debug("No conceptCode found with id: " + id);
            conceptCodeDto = new ConceptCodeDto();
            conceptCodeDto.setError(true);
            conceptCodeDto.setErrorMessage(ERROR_MESSAGE_KEY_EDITED_CONCEPTCODE_WAS_NOT_FOUND);
            return createRedirectViewPath(REDIRECT_ID_MAPPING_LIST);          
        }

        model.addAttribute(MODEL_ATTIRUTE_CONCEPTCODEDTO, conceptCodeDto);
        
        return CONCEPTCODE_EDIT_FORM_VIEW;
    }

    /**
     * Processes the submissions of edit conceptCode form.
     * @param updated   The information of the edited conceptCode.
     * @param bindingResult
     * @param attributes
     * @return
     */
    @RequestMapping(value = "/conceptCode/edit/{id}", method = RequestMethod.POST)
    public String submitEditConceptCodeForm(@ModelAttribute(MODEL_ATTIRUTE_CONCEPTCODEDTO) ConceptCodeDto updated, @PathVariable("id") Long id) {
        LOGGER.debug("Edit conceptCode form was submitted with information: " + updated + id);
        
        try {
	        AuthenticatedUser currentUser = userContext.getCurrentUser();
	        updated.setUserName(currentUser.getUsername());
	        updated.setId(id);
            updated = conceptCodeService.update(updated);
            updated.setError(false);
            updated.setSuccessMessage("Concept Code  with code:" + updated.getCode() + " and Name: " + updated.getName() + " is Edited Successfully");

        } catch (ConceptCodeNotFoundException e) {
            LOGGER.debug("No conceptCode was found with id: " + updated.getId());
			updated.setError(true);
			updated.setErrorMessage( "Edited Concept Code is not found");
        }
        
        return createRedirectViewPath(REDIRECT_ID_MAPPING_LIST);
    }

    /**
     * This setter method should only be used by unit tests
     * @param conceptCodeService
     */
    protected void setConceptCodeService(ConceptCodeService conceptCodeService) {
        this.conceptCodeService = conceptCodeService;
    }
}
