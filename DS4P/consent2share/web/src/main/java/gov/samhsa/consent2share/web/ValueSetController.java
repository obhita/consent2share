package gov.samhsa.consent2share.web;

import gov.samhsa.consent2share.infrastructure.security.AuthenticatedUser;
import gov.samhsa.consent2share.infrastructure.security.UserContext;
import gov.samhsa.consent2share.service.dto.ValueSetDto;
import gov.samhsa.consent2share.service.valueset.ValueSetNotFoundException;
import gov.samhsa.consent2share.service.valueset.ValueSetService;

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
@SessionAttributes("valueSet")
@RequestMapping("/sysadmin") 
public class ValueSetController extends AbstractNodeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValueSetController.class);
    
    protected static final String ERROR_MESSAGE_KEY_DELETED_VALUESET_WAS_NOT_FOUND = "Deleted valueSet was not found.";
    protected static final String ERROR_MESSAGE_KEY_EDITED_VALUESET_WAS_NOT_FOUND = "Edited valueSet was not found.";
    
    protected static final String MODEL_ATTIRUTE_VALUESETDTO = "valueSetDto";
    protected static final String MODEL_ATTRIBUTE_VALUESETDTOS = "valueSetDtos";
    
    protected static final String VALUESET_ADD_FORM_VIEW = "views/sysadmin/valueSetAdd";
    protected static final String VALUESET_EDIT_FORM_VIEW = "views/sysadmin/valueSetEdit";
    protected static final String VALUESET_LIST_VIEW = "views/sysadmin/valueSetList";
    
    protected static final String REQUEST_MAPPING_LIST = "/valueSetList";
    protected static final String REDIRECT_MAPPING_LIST = "../valueSetList";   
    protected static final String REDIRECT_ID_MAPPING_LIST = "../../valueSetList";   
    
    @Resource
    private ValueSetService valueSetService;
    
	/** The user context. */
	@Autowired
	UserContext userContext;
	
    /**
     * Processes create valueSet requests.
     * @param model
     * @return  The name of the create valueSet form view.
     */
    @RequestMapping(value = REQUEST_MAPPING_LIST, method = RequestMethod.GET)
    public String showCreateValueSetForm(Model model, HttpServletRequest request){
    	
        LOGGER.debug("Rendering Value Set  list page");
        
        List<ValueSetDto> valueSets = valueSetService.findAll();
        	        
        model.addAttribute(MODEL_ATTRIBUTE_VALUESETDTOS, valueSets);
        return VALUESET_LIST_VIEW;
    }
    
	
    /**
     * Processes the submissions of create valueSet form.
     * @param created   The information of the created valueSets.
     * @param bindingResult
     * @param attributes
     * @return
     */
    @RequestMapping(value = "valueSetAdd.html", method = RequestMethod.GET)
    public String valueSetAddForm(Model model, HttpServletRequest request) {
        LOGGER.debug("Rendering Value Set  Add Form ");
        AuthenticatedUser currentUser = userContext.getCurrentUser();
		model.addAttribute("currentUser", currentUser);
		ValueSetDto valueSetDto = (ValueSetDto) model.asMap().get(MODEL_ATTIRUTE_VALUESETDTO);
		model.addAttribute(MODEL_ATTIRUTE_VALUESETDTO, (valueSetDto == null)? new ValueSetDto() : valueSetDto);
		return VALUESET_ADD_FORM_VIEW;
    }
    
    


    /**
     * Processes the submissions of create valueSet form.
     * @param created   The information of the created valueSets.
     * @param bindingResult
     * @param attributes
     * @return
     */
    @RequestMapping(value = "/valueSet/create", method = RequestMethod.POST)
    public String submitCreateValueSetForm(@Valid @ModelAttribute(MODEL_ATTIRUTE_VALUESETDTO) ValueSetDto created, RedirectAttributes redirectAttribute,  Model model) {
        LOGGER.debug("Create valueSet form was submitted with information: " + created);
		
        AuthenticatedUser currentUser = userContext.getCurrentUser();
        String notification= "";
        ValueSetDto valueSetDto;
        String path = REDIRECT_MAPPING_LIST; 
		try {
			created.setUserName(currentUser.getUsername());
			valueSetDto = valueSetService.create(created);
			created.setError(false);
			created.setSuccessMessage("Value Set   with code:" + valueSetDto.getCode() + " and Name: " + valueSetDto.getName() + " is Added Successfully");
		} catch (DataIntegrityViolationException ex) {
			LOGGER.info(ex.getLocalizedMessage());
			Throwable t = ex.getCause();
			String message = null;
			if (t != null) {
				message = "Cause: " + t.getMessage();
			}
			created.setError(true);
			created.setErrorMessage("Value Set  is not Added " + message);
			path = "../valueSetAdd.html";
		}
		model.addAttribute(MODEL_ATTIRUTE_VALUESETDTO, created);
		redirectAttribute.addFlashAttribute(MODEL_ATTIRUTE_VALUESETDTO, created);
		model.addAttribute("notification", notification);

		 
        return  createRedirectViewPath(path);
    }	    
    
	/**
     * Processes delete valueSet requests.
     * @param id    The id of the deleted valueSet.
     * @param attributes
     * @return
     */
    @RequestMapping(value = "/valueSet/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttribute) {
        LOGGER.debug("Deleting valueSet with id: " + id);
        ValueSetDto deleted = new ValueSetDto();
        try {
            deleted = valueSetService.delete(id);
            deleted.setError(false);
            deleted.setSuccessMessage(" ValueSet with Code: " + deleted.getCode() + " and Name: " + deleted.getName() +" is deleted Successfully. ");
        } catch (ValueSetNotFoundException e) {
            LOGGER.debug("No valueSet found with id: " + id);
            deleted.setError(true);
            deleted.setErrorMessage(ERROR_MESSAGE_KEY_DELETED_VALUESET_WAS_NOT_FOUND);
        }
        redirectAttribute.addFlashAttribute(MODEL_ATTIRUTE_VALUESETDTO, deleted);
        return createRedirectViewPath(REDIRECT_ID_MAPPING_LIST);
    }



    /**
     * Processes edit valueSet requests.
     * @param id    The id of the edited valueSet.
     * @param model
     * @param attributes
     * @return  The name of the edit valueSet form view.
     */
    @RequestMapping(value = "/valueSet/edit/{id}", method = RequestMethod.GET)
    public String showEditValueSetForm(@PathVariable("id") Long id, Model model, RedirectAttributes attributes) {
        LOGGER.debug("Rendering edit valueSet form for valueSet with id: " + id);
        ValueSetDto valueSetDto = valueSetService.findById(id);
        if (valueSetDto == null) {
            LOGGER.debug("No valueSet found with id: " + id);
            valueSetDto = new ValueSetDto();
            valueSetDto.setError(true);
            valueSetDto.setErrorMessage(ERROR_MESSAGE_KEY_EDITED_VALUESET_WAS_NOT_FOUND);
            return createRedirectViewPath(REDIRECT_ID_MAPPING_LIST);          
        }

        model.addAttribute(MODEL_ATTIRUTE_VALUESETDTO, valueSetDto);
        
        return VALUESET_EDIT_FORM_VIEW;
    }

    /**
     * Processes the submissions of edit valueSet form.
     * @param updated   The information of the edited valueSet.
     * @param bindingResult
     * @param attributes
     * @return
     */
    @RequestMapping(value = "/valueSet/edit/{id}", method = RequestMethod.POST)
    public String submitEditValueSetForm(@ModelAttribute(MODEL_ATTIRUTE_VALUESETDTO) ValueSetDto updated, @PathVariable("id") Long id) {
        LOGGER.debug("Edit valueSet form was submitted with information: " + updated + id);
        
        try {
	        AuthenticatedUser currentUser = userContext.getCurrentUser();
	        updated.setUserName(currentUser.getUsername());
	        updated.setId(id);
            updated = valueSetService.update(updated);
            updated.setError(false);
            updated.setSuccessMessage("Value Set   with code:" + updated.getCode() + " and Name: " + updated.getName() + " is Edited Successfully");

        } catch (ValueSetNotFoundException e) {
            LOGGER.debug("No valueSet was found with id: " + updated.getId());
			updated.setError(true);
			updated.setErrorMessage( "Edited Value Set  is not found");
        }
        
        return createRedirectViewPath(REDIRECT_ID_MAPPING_LIST);
    }

    /**
     * This setter method should only be used by unit tests
     * @param valueSetService
     */
    protected void setValueSetService(ValueSetService valueSetService) {
        this.valueSetService = valueSetService;
    }
}
