//Code to execute on document.ready event for registration.html page

	function clearField(){
		//clear state_name value
		clearVerificationCode();
		//trigger change event handler for state_name
		$("#verificationCode").triggerHandler("change");

		//clear city_name value
		clearDOB();
		//trigger change event handler for city_name
		$("#dob").triggerHandler("change");

	}
	
	function clearVerificationCode(){
		$("#verificationCode").val('');
		$('#verificationCode_client_error_text').attr('style', "display: none;");
	}
	
	function clearDOB(){
		$("#month").val('');
		$("#day").val('');
		$("#year").val('');
		$('#dob_client_error_text').attr('style', "display: none;");
	}
	
	
	
	
