/**
 * profile_registration.js must be included in the HTML
 * before this script file is included in order to function properly.
 */

//Code to execute on document.ready event for profile.html page
$(document).ready(function() {
	initAllDOBFields();
	initUserPassModal();
	
	//Bind event listener for click on profileForm submit button
	$("form#profileForm button#profileFormSubmitButton").on("click", function(evt){
		//Trigger form submit
		$("form#profileForm").submit();
	});
	
	//Submit event handler for profileForm
	$("form#profileForm").on("submit", function(evt){
		var isFormValid = false;
		
		//Disable submit button
		$("form#profileForm button#profileFormSubmitButton").prop("disabled", true);
		
		isFormValid = validateProfileUpdateForm();
		
		if(isFormValid === true){
			//On validation success, return true to proceed with form submission
			return true;
		}else{
			//On validation failure, prevent form submission
			evt.preventDefault();
			
			//Re-enable submit button
			$("form#profileForm button#profileFormSubmitButton").prop("disabled", false);
			
			return false;
		}
	});
});

