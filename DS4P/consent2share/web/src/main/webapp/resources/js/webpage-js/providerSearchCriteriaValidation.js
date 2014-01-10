/**
 *  providerSearchCriteriaValidation.js
 *  Created date: 09/18/2013
 *  Created by: Andy Lee
 */
 
function validProviderSearchForm()
{
	var isFormValid = true;
	var len4lname = $('#last_name').val().length ; 
	var len4fname = $('#first_name').val().length ;
	var len4city = $('#city_name').val().length ; 
	var phoneStr = $('#phone1').val() + $('#phone2').val() + $('#phone3').val() ;
	var zipStr = $('#zip_code').val() ;
	
	if( len4fname == 0 && len4lname == 0 && phoneStr.length == 0 && zipStr.length == 0 &&
		$('#state_name').val().length == 0 && $('#specialty').val().length == 0 && 
		$('#gender').val().length == 0 
	  )	{
		isFormValid = false;
		$('#empty_client_error_text').attr('style', "");
	}else{
		$('#empty_client_error_text').attr('style', "display: none;");
	}

	if( ( len4fname > 0 ) && ( len4fname < 2 || len4fname > 30 ) ){
		isFormValid = false;
		$('#fname_client_error_text').attr('style', "");
	}else{
		$('#fname_client_error_text').attr('style', "display: none;");
	}

	if( ( len4lname > 0 ) && ( len4lname < 2 || len4lname > 30 ) ){
		isFormValid = false;
		$('#lname_client_error_text').attr('style', "");
	}else{
		$('#lname_client_error_text').attr('style', "display: none;");
	}

	var isValidPhone = chkPhone( phoneStr );
	if( isValidPhone !== true ){
		isFormValid = false;
		$('#phone_client_error_text').attr('style', "");
	}else{
		$('#phone_client_error_text').attr('style', "display: none;");
	}
	
	var isValidZip = chkZip( zipStr );
	if( isValidZip !== true ){
		isFormValid = false;
		$('#zip_client_error_text').attr('style', "");
	}else{
		$('#zip_client_error_text').attr('style', "display: none;");
	}

	if( len4city > 0 && len4city < 3 ){
		isFormValid = false;
		$('#city_client_error_text').attr('style', "");
	}else{
		$('#city_client_error_text').attr('style', "display: none;");
	}

	$("#searchButton").attr("href", isFormValid == true ? "#provider_search_modal" : "" );
	
	return isFormValid;
}
