/**
 *  providerSearchCriteriaValidation.js
 *  Created date: 09/18/2013
 *  Created by: Andy Lee
 */
 
function validProviderSearchForm()
{
	var isFormValid = true;
	var len4lname = $('#search_last_name').val().length ; 
	var len4fname = $('#search_first_name').val().length ;
	var len4city = $('#search_city_name').val().length ; 
	var phoneStr = $('#search_phone1').val() + $('#search_phone2').val() + $('#search_phone3').val() ;
	var zipStr = $('#search_zip_code').val() ;
	
	if( len4fname == 0 && len4lname == 0 && phoneStr.length == 0 && zipStr.length == 0 &&
		$('#search_state_name').val().length == 0 && $('#search_specialty').val().length == 0 && 
		$('#search_gender').val().length == 0 
	  )	{
		isFormValid = false;
		$('#search_empty_client_error_text').attr('style', "");
	}else{
		$('#search_empty_client_error_text').attr('style', "display: none;");
	}

	if( ( len4fname > 0 ) && ( len4fname < 2 || len4fname > 30 ) ){
		isFormValid = false;
		$('#search_fname_client_error_text').attr('style', "");
	}else{
		$('#search_fname_client_error_text').attr('style', "display: none;");
	}

	if( ( len4lname > 0 ) && ( len4lname < 2 || len4lname > 30 ) ){
		isFormValid = false;
		$('#search_lname_client_error_text').attr('style', "");
	}else{
		$('#search_lname_client_error_text').attr('style', "display: none;");
	}

	var isValidPhone = chkPhone( phoneStr );
	if( isValidPhone !== true ){
		isFormValid = false;
		$('#search_phone_client_error_text').attr('style', "");
	}else{
		$('#search_phone_client_error_text').attr('style', "display: none;");
	}
	
	var isValidZip = chkZip( zipStr );
	if( isValidZip !== true ){
		isFormValid = false;
		$('#search_zip_client_error_text').attr('style', "");
	}else{
		$('#search_zip_client_error_text').attr('style', "display: none;");
	}

	if( len4city > 0 && len4city < 3 ){
		isFormValid = false;
		$('#search_city_client_error_text').attr('style', "");
	}else{
		$('#search_city_client_error_text').attr('style', "display: none;");
	}

	$("#searchButton").attr("href", isFormValid == true ? "#provider_search_modal" : "" );
	
	return isFormValid;
}


function validDashboardProviderSearchForm()
{
	var isFormValid = true;
	var len4lname = $('#search_last_name').val().length ; 
	var len4fname = $('#search_first_name').val().length ;
	var len4city = $('#search_city_name').val().length ; 
	var phoneStr = $('#search_phone1').val() + $('#search_phone2').val() + $('#search_phone3').val() ;
	var zipStr = $('#search_zip_code').val() ;
	
	if( len4fname == 0 && len4lname == 0 && phoneStr.length == 0 && zipStr.length == 0 &&
		$('#search_state_name').val().length == 0 && $('#search_specialty').val().length == 0 && 
		$('#search_gender').val().length == 0 
	  )	{
		isFormValid = false;
		$('#empty_client_error_text').attr('style', "");
	}else{
		$('#empty_client_error_text').attr('style', "display: none;");
	}

	if( ( len4fname > 0 ) && ( len4fname < 2 || len4fname > 30 ) ){
		isFormValid = false;
		$('#search_fname_client_error_text').attr('style', "");
	}else{
		$('#search_fname_client_error_text').attr('style', "display: none;");
	}

	if( ( len4lname > 0 ) && ( len4lname < 2 || len4lname > 30 ) ){
		isFormValid = false;
		$('#search_lname_client_error_text').attr('style', "");
	}else{
		$('#search_lname_client_error_text').attr('style', "display: none;");
	}

	var isValidPhone = chkPhone( phoneStr );
	if( isValidPhone !== true ){
		isFormValid = false;
		$('#search_phone_client_error_text').attr('style', "");
	}else{
		$('#search_phone_client_error_text').attr('style', "display: none;");
	}
	
	var isValidZip = chkZip( zipStr );
	if( isValidZip !== true ){
		isFormValid = false;
		$('#search_zip_client_error_text').attr('style', "");
	}else{
		$('#search_zip_client_error_text').attr('style', "display: none;");
	}

	if( len4city > 0 && len4city < 3 ){
		isFormValid = false;
		$('#search_city_client_error_text').attr('style', "");
	}else{
		$('#search_city_client_error_text').attr('style', "display: none;");
	}

	$("#searchButton").attr("href", isFormValid == true ? "#provider_search_modal" : "" );
	
	return isFormValid;
}