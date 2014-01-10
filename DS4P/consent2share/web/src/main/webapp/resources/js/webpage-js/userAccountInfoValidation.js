/**
 * Functions involved in validating input for user account fields
 */

function preprocessInput(){
	var selMonth = $('#month').val();
	var selDay = $('#day').val();
	var selYear = $('#year').val();

	if ($.isNumeric(selMonth) === false){
		selMonth = "  ";
	}

	if ($.isNumeric(selDay) === false){
		selDay = "  ";
	}

	if ($.isNumeric(selYear) === false){
		selYear = "    ";
	}
	
	$('#date').attr('value', selMonth + "/" + selDay + "/" + selYear);
	
}

function validateSignupForm(){				
	preprocessInput();

	var isFormValid = true;

	isFormValid = validateCommonFields(isFormValid);
	
	var isValidUname = chkUname($('#user_name').val());

	if(isValidUname !== true){
		isFormValid = false;
		$('#username_client_error_text').html(isValidUname);
		$('#username_client_error_text').attr('style', "");
	}else{
		$('#username_client_error_text').text("");
		$('#username_client_error_text').attr('style', "display: none;");
	}
	
	var isValidPword = chkPword($('#password').val(), $('#user_name').val());

	if(isValidPword !== true){
		isFormValid = false;
		$('#password_client_error_text').html(isValidPword);
		$('#password_client_error_text').attr('style', "");
	}else{
		$('#password_client_error_text').text("");
		$('#password_client_error_text').attr('style', "display: none;");
	}
	
	
	if(isFormValid !== true){
		return false;
	}
}


function validateProfileUpdateForm(){
	preprocessInput();

	var isFormValid = true;
	var flagUserPassInvalid = false;
	
	$('#updatedMessage').attr('style', "display: none;");
	
	isFormValid = validateCommonFields(isFormValid);
	
	var isValidUname = chkUname($('#user_name').val());
	
	if(isValidUname !== true){
		isFormValid = false;
		flagUserPassInvalid = true;
		$('#username_client_error_text').html(isValidUname);
		$('#username_client_error_text').attr('style', "");
	}else{
		$('#username_client_error_text').text("");
		$('#username_client_error_text').attr('style', "display: none;");
	}
	
	var isValidPword = chkPword($('#password').val(), "");
	
	if(isValidPword !== true){
		isFormValid = false;
		flagUserPassInvalid = true;
		$('#pwd_client_error_text').html("Invalid Password");
		$('#pwd_client_error_text').attr('style', "");
	}else{
		$('#pwd_client_error_text').text("");
		$('#pwd_client_error_text').attr('style', "display: none;");
	}
	
	var isValidPhone = chkPhone($('#phone').val());

	if(isValidPhone !== true){
		isFormValid = false;
		$('#phone_client_error_text').html(isValidPhone);
		$('#phone_client_error_text').attr('style', "");
	}else{
		$('#phone_client_error_text').text("");
		$('#phone_client_error_text').attr('style', "display: none;");
	}
	
	var isValidZip = chkZip($('#zip').val());

	if(isValidZip !== true){
		isFormValid = false;
		$('#zip_client_error_text').html(isValidZip);
		$('#zip_client_error_text').attr('style', "");
	}else{
		$('#zip_client_error_text').text("");
		$('#zip_client_error_text').attr('style', "display: none;");
	}
	
	var isValidSSN = chkSSN($('#socialsecurity').val());

	if(isValidSSN !== true){
		isFormValid = false;
		$('#ssn_client_error_text').html(isValidSSN);
		$('#ssn_client_error_text').attr('style', "");
	}else{
		$('#ssn_client_error_text').text("");
		$('#ssn_client_error_text').attr('style', "display: none;");
	}	
	
	var isValidMrn = chkMrn($('#medical_record_number').val());

	if(isValidMrn !== true){
		isFormValid = false;
		$('#mrn_client_error_text').html(isValidMrn);
		$('#mrn_client_error_text').attr('style', "");
	}else{
		$('#mrn_client_error_text').text("");
		$('#mrn_client_error_text').attr('style', "display: none;");
	}		
	
	if(isFormValid !== true){
		if(flagUserPassInvalid === false){
			$('#username_password_modal').modal('hide');
		}
		return false;
	}else{
		return true;
	}
}


function validateAdminEditPatientProfileForm(){
	preprocessInput();

	var isFormValid = true;
	
	isFormValid = validateCommonFields(isFormValid);
	
	var isValidPhone = chkPhone($('#phone').val());

	if(isValidPhone !== true){
		isFormValid = false;
		$('#phone_client_error_text').html(isValidPhone);
		$('#phone_client_error_text').attr('style', "");
	}else{
		$('#phone_client_error_text').text("");
		$('#phone_client_error_text').attr('style', "display: none;");
	}
	
	var isValidZip = chkZip($('#zip').val());

	if(isValidZip !== true){
		isFormValid = false;
		$('#zip_client_error_text').html(isValidZip);
		$('#zip_client_error_text').attr('style', "");
	}else{
		$('#zip_client_error_text').text("");
		$('#zip_client_error_text').attr('style', "display: none;");
	}
			
	
	return isFormValid;
}


function validateCommonFields(inFormValid){
	var isFormValidSub = inFormValid;
	
	var isValidDate = isDate($('#date').val());

	if(isValidDate !== true){
		isFormValidSub = false;
		$('#dob_client_error_text').html(isValidDate);
		$('#dob_client_error_text').attr('style', "");
	}else{
		$('#dob_client_error_text').text("");
		$('#dob_client_error_text').attr('style', "display: none;");
	}

	var isValidFname = chkFname($('#first_name').val());

	if(isValidFname !== true){
		isFormValidSub = false;
		$('#fname_client_error_text').html(isValidFname);
		$('#fname_client_error_text').attr('style', "");
	}else{
		$('#fname_client_error_text').text("");
		$('#fname_client_error_text').attr('style', "display: none;");
	}

	var isValidLname = chkLname($('#last_name').val());

	if(isValidLname !== true){
		isFormValidSub = false;
		$('#lname_client_error_text').html(isValidLname);
		$('#lname_client_error_text').attr('style', "");
	}else{
		$('#lname_client_error_text').text("");
		$('#lname_client_error_text').attr('style', "display: none;");
	}

	var isValidGender = chkGender($('#gender').val());

	if(isValidGender !== true){
		isFormValidSub = false;
		$('#gender_client_error_text').html(isValidGender);
		$('#gender_client_error_text').attr('style', "");
	}else{
		$('#gender_client_error_text').text("");
		$('#gender_client_error_text').attr('style', "display: none;");
	}
	
	var isValidEmail = chkEmail($('#email').val());

	if(isValidEmail !== true){
		isFormValidSub = false;
		$('#email_client_error_text').html(isValidEmail);
		$('#email_client_error_text').attr('style', "");
	}else{
		$('#email_client_error_text').text("");
		$('#email_client_error_text').attr('style', "display: none;");
	}
	
	return isFormValidSub;
}




/* Function to validate Create Patient Account form on Administrator/adminHome.html page */
function validateAdminCreatePatientAccount(){
	preprocessInput();

	var isFormValid = true;
	
	var isValidDate = isDate($('#date').val());

	if(isValidDate !== true){
		isFormValid = false;
		$('#dob_client_error_text').html(isValidDate);
		$('#dob_client_error_text').attr('style', "");
	}else{
		$('#dob_client_error_text').text("");
		$('#dob_client_error_text').attr('style', "display: none;");
	}

	var isValidFname = chkFname($('#first_name').val());

	if(isValidFname !== true){
		isFormValid = false;
		$('#fname_client_error_text').html(isValidFname);
		$('#fname_client_error_text').attr('style', "");
	}else{
		$('#fname_client_error_text').text("");
		$('#fname_client_error_text').attr('style', "display: none;");
	}

	var isValidLname = chkLname($('#last_name').val());

	if(isValidLname !== true){
		isFormValid = false;
		$('#lname_client_error_text').html(isValidLname);
		$('#lname_client_error_text').attr('style', "");
	}else{
		$('#lname_client_error_text').text("");
		$('#lname_client_error_text').attr('style', "display: none;");
	}

	var isValidGender = chkGender($('#gender').val());

	if(isValidGender !== true){
		isFormValid = false;
		$('#gender_client_error_text').html(isValidGender);
		$('#gender_client_error_text').attr('style', "");
	}else{
		$('#gender_client_error_text').text("");
		$('#gender_client_error_text').attr('style', "display: none;");
	}
	
	return isFormValid;
	
}


/* Function to validate update admin profile form
 * on Administrator/editAdminProfile.html page */
function validateUpdateAdminProfile(){

	var isFormValid = true;
	var flagUserPassInvalid = false;

	var isValidFname = chkFname($('#first_name').val());

	if(isValidFname !== true){
		isFormValid = false;
		$('#fname_client_error_text').html(isValidFname);
		$('#fname_client_error_text').attr('style', "");
	}else{
		$('#fname_client_error_text').text("");
		$('#fname_client_error_text').attr('style', "display: none;");
	}

	var isValidLname = chkLname($('#last_name').val());

	if(isValidLname !== true){
		isFormValid = false;
		$('#lname_client_error_text').html(isValidLname);
		$('#lname_client_error_text').attr('style', "");
	}else{
		$('#lname_client_error_text').text("");
		$('#lname_client_error_text').attr('style', "display: none;");
	}

	var isValidGender = chkGender($('#gender').val());

	if(isValidGender !== true){
		isFormValid = false;
		$('#gender_client_error_text').html(isValidGender);
		$('#gender_client_error_text').attr('style', "");
	}else{
		$('#gender_client_error_text').text("");
		$('#gender_client_error_text').attr('style', "display: none;");
	}
	
	var isValidEmail = chkEmail($('#email').val());

	if(isValidEmail !== true){
		isFormValid = false;
		$('#email_client_error_text').html(isValidEmail);
		$('#email_client_error_text').attr('style', "");
	}else{
		$('#email_client_error_text').text("");
		$('#email_client_error_text').attr('style', "display: none;");
	}
	
	var isValidUname = chkUname($('#user_name').val());
	
	if(isValidUname !== true){
		isFormValid = false;
		flagUserPassInvalid = true;
		$('#username_client_error_text').html(isValidUname);
		$('#username_client_error_text').attr('style', "");
	}else{
		$('#username_client_error_text').text("");
		$('#username_client_error_text').attr('style', "display: none;");
	}
	
	/*
	var isValidPword = chkPword($('#password').val(), "");
	
	if(isValidPword !== true){
		isFormValid = false;
		flagUserPassInvalid = true;
		$('#pwd_client_error_text').html("Invalid Password");
		$('#pwd_client_error_text').attr('style', "");
	}else{
		$('#pwd_client_error_text').text("");
		$('#pwd_client_error_text').attr('style', "display: none;");
	}
	
	*/
	
	if(isFormValid !== true){
		if(flagUserPassInvalid === false){
			$('#username_password_modal').modal('hide');
		}
		return false;
	}else{
		return true;
	}
	
}



function chkFname(inFname){
	if(2 > inFname.length || inFname.length > 30){
		if(0 >= inFname.length){
			return "First Name is required";
		}else{
			return "First Name must be between 2 and 30 characters long";
		}
	}else{
		return true;
	}
}

function chkLname(inLname){
	if(2 > inLname.length || inLname.length > 30){
		if(0 >= inLname.length){
			return "Last Name is required";
		}else{
			return "Last Name must be between 2 and 30 characters long";
		}
	}else{
		return true;
	}
}

function chkMrn(inMrn){
	var errorString = "MRN must be between 0 and 30 characters long";
	if(0 > inMrn.length || inMrn.length > 30){
		return errorString;
	}else{
		return true;
	}
}

function chkGender(inGender){
	if(0 >= inGender.length){
		return "Gender is required";
	}else{
		return true;
	}
}

function chkEmail(inEmail){
	var emailRegEx = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
	var isPass = emailRegEx.test(inEmail);
	
	if(0 >= inEmail.length){
		return "Email is required";
	}else{
		if(isPass !== true){	
			return "Please provide a valid email address";
		}else{
			return true;
		}
	}
}

function chkUname(inUname){
	if(3 > inUname.length || inUname.length > 30){
		if(0 >= inUname.length){
			return "Username is required";
		}else{
			return "Username must be between 3 and 30 characters long";
		}
	}else{
		return true;
	}
}

function chkPword(inPword, inUname){
	var strErr = "";
	
	var numRegEx = /.*\d.*/;
	var lcaseRegEx = /.*[a-z].*/;
	var ucaseRegEx = /.*[A-Z].*/;
	var pwordSpecCharRegEx = /.*[,~,!,@,#,$,%,^,&,*,(,),\-,_,=,+,[,{,\],},|,;,:,<,>,\/,?].*/;
	
	if(8 > inPword.length || inPword.length > 30){
		if(0 >= inPword.length){
			strErr = strErr + "Password is required";
		}else{
			strErr = strErr + "Password must be between 8 and 30 characters long";
		}
	}
	
	if(numRegEx.test(inPword) !== true){
		if(strErr.length > 0){
			strErr = strErr + "<br />";
		}
		strErr = strErr + "Password must contain at least one number";
	}
	
	if(lcaseRegEx.test(inPword) !== true){
		if(strErr.length > 0){
			strErr = strErr + "<br />";
		}
		strErr = strErr + "Password must contain at least one lowercase alphabetic character";
	}
	
	if(ucaseRegEx.test(inPword) !== true){
		if(strErr.length > 0){
			strErr = strErr + "<br />";
		}
		strErr = strErr + "Password must contain at least one uppercase alphabetic character";
	}
	
	if(pwordSpecCharRegEx.test(inPword) !== true){
		if(strErr.length > 0){
			strErr = strErr + "<br />";
		}
		strErr = strErr + "Password must contain at least one special character";
	}
	
	if(inPword == inUname){
		if(strErr.length > 0){
			strErr = strErr + "<br />";
		}
		strErr = strErr + "Password can not be username";
	}
	
	if(strErr.length > 0){
		return strErr;
	}else{
		return true;
	}
	
}

/** FUNCTION CHECKS FOR PHONE NUMBER
 * Uses regular expression to check for valid
 * phone number value and length.
 * 
 * NOTE: As phone number is not a required field, an input
 * value with a length of zero is considered valid input.
 * 
 * @param inPhone
 * @returns Boolean true (on successful validation)
 * @returns String errorString (on failed validation)
 */
function chkPhone(inPhone){
	var phoneRegEx = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
	var errorString = "Please provide a valid phone number. For example 123-456-7890.";
	
	if(inPhone.length == 0){
		return true;
	}else{
		if(phoneRegEx.test(inPhone) !== true){
			return errorString;
		}else{
			return true;
		}
	}
}

/** FUNCTION CHECKS FOR VALID ZIP CODE
 * Uses regular expression to check for valid
 * zip code value and length. Accepts both 5 digit
 * zip codes & full 5+4 zip codes (5 digits plus
 * four digits with a hyphen after the first 5 digits).
 * 
 * NOTE: As zip code is not a required field, an input
 * value with a length of zero is considered valid input.
 * 
 * @param inZip
 * @returns Boolean true (on successful validation)
 * @returns String errorString (on failed validation)
 */
function chkZip(inZip){
	var zipRegEx = /^\d{5}$|^\d{5}-\d{4}$/;
	var errorString = "Please provide a valid zip code";
	
	if(inZip.length == 0){
		return true;
	}else{
		if(zipRegEx.test(inZip) !== true){
			return errorString;
		}else{
			return true;
		}
	}
}

/** FUNCTION CHECKS FOR SSN
 * Uses regular expression to check for valid
 * SSN value and length.
 * 
 * NOTE: As SSN is not a required field, an input
 * value with a length of zero is considered valid input.
 * 
 * @param inSSN
 * @returns Boolean true (on successful validation)
 * @returns String errorString (on failed validation)
 */
function chkSSN(inSSN){
	var ssnRegEx = /^\d{3}-?\d{2}-?\d{4}$/;
	var errorString = "Please provide a valid social security number. For example 123-45-6789.";
	
	if(inSSN.length == 0){
		return true;
	}else{
		if(ssnRegEx.test(inSSN) !== true){
			return errorString;
		}else{
			return true;
		}
	}
}
