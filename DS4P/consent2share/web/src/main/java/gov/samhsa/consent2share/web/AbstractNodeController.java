package gov.samhsa.consent2share.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.ResponseEntity;


public class AbstractNodeController {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractNodeController.class);

    private static final String FLASH_ERROR_MESSAGE = "errorMessage";
    private static final String FLASH_FEEDBACK_MESSAGE = "feedbackMessage";

    private static final String VIEW_REDIRECT_PREFIX = "redirect:";


    /**
     * Adds a new error message
     * @param model A model which stores the the error message.
     * @param code  A message code which is used to fetch the correct message from the message source.
     * @param params    The parameters attached to the actual error message.
     */
    protected void addErrorMessage(RedirectAttributes model, String code, Object... params) {
        LOGGER.debug("adding error message with code: " + code + " and params: " + params);
        model.addFlashAttribute(FLASH_ERROR_MESSAGE, code);
    }

    /**
     * Adds a new feedback message.
     * @param model A model which stores the feedback message.
     * @param code  A message code which is used to fetch the actual message from the message source.
     * @param params    The parameters which are attached to the actual feedback message.
     */
    protected void addFeedbackMessage(RedirectAttributes model, String code, Object... params) {
        LOGGER.debug("Adding feedback message with code: " + code + " and params: " + params);
        model.addFlashAttribute(FLASH_FEEDBACK_MESSAGE, code);
    }

    /**
     * Creates a redirect view path for a specific controller action
     * @param path  The path processed by the controller method.
     * @return  A redirect view path to the given controller method.
     */
    protected String createRedirectViewPath(String path) {
        StringBuilder builder = new StringBuilder();
        builder.append(VIEW_REDIRECT_PREFIX);
        builder.append(path);
        return builder.toString();
    }
    
    
    /**
     * Automatically Handles thrown AjaxExceptions
     */
    @ExceptionHandler (AjaxException.class)
	protected @ResponseBody ResponseEntity<String> handleAjaxException(AjaxException e){
		
		return new ResponseEntity<String>(e.getErrorMessage(), e.getHttpStatus());
	}
    
}
