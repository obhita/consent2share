var origDataVals = (function() {
	var fname = "";
	var lname = "";
	var gender = "";
	var date = "";
	var email = "";
	var street = "";
	var city = "";
	var state = "";
	var zip = "";
	var phone = "";
	var ssn = "";
	var mrn = "";
	var marital_status = "";
	var religion = "";
	var lang = "";
	
	return {
		initVals: function(in_fname, in_lname, in_gender, in_date, in_email,
				in_street, in_city, in_state, in_zip, in_phone, in_ssn,
				in_mrn, in_marital_status, in_religion, in_lang) {
			fname = in_fname;
			lname = in_lname;
			gender = in_gender;
			date = in_date;
			email = in_email;
			street = in_street;
			city = in_city;
			state = in_state;
			zip = in_zip;
			phone = in_phone;
			ssn = in_ssn;
			mrn = in_mrn;
			marital_status = in_marital_status;
			religion = in_religion;
			lang = in_lang;
		},
		
		getFName: function() {
			return fname;
		},
		
		getLName: function() {
			return lname;
		},
		
		getGender: function() {
			return gender;
		},
		
		getDate: function() {
			return date;
		},
		
		getEmail: function() {
			return email;
		},
		
		getStreet: function() {
			return street;
		},
		
		getCity: function() {
			return city;
		},
		
		getState: function() {
			return state;
		},
		
		getZip: function() {
			return zip;
		},
		
		getPhone: function() {
			return phone;
		},
		
		getSSN: function() {
			return ssn;
		},
		
		getMRN: function() {
			return mrn;
		},
		
		getMaritalStatus: function() {
			return marital_status;
		},
		
		getReligion: function() {
			return religion;
		},
		
		getLang: function() {
			return lang;
		}
	};
})();

function initOrigDataVals() {
	var fname = $('input#first_name').val();
	var lname = $('input#last_name').val();
	var gender = $('select#gender').val();
	var date = $('input#date').val();
	var email = $('input#email').val();
	var street = $('input#street').val();
	var city = $('input#city').val();
	var state = $('select#state').val();
	var zip = $('input#zip').val();
	var phone = $('input#phone').val();
	var ssn = $('input#socialsecurity').val();
	var mrn = $('input#medical_record_number').val();
	var marital_status = $('select#marital_status').val();
	var religion = $('select#religious_affiliation').val();
	var lang = $('select#language').val("");
	
	origDataVals.initVals(fname, lname, gender, date, email, street, city,
			state, zip, phone, ssn, mrn, marital_status, religion, lang);
}

function initPhoneNumbers() {
	$('.prov-phone-input').each(function(){
		var provNPI = $(this).data('npi');
		var rawphonenum = $(this).val();
		var formatphonenum = phoneNumberParser(rawphonenum);

		$('span#prov-phone-display-NPI' + provNPI).text(formatphonenum);
		$('span#crd-prov-phone-display-NPI' + provNPI).text(formatphonenum);
	});
}

function initAddresses() {
	$('input.prov-address-input').each(function(){
		var provNPI = $(this).data('npi');
		var streetaddress = $(this).data('streetaddress');
		var city = $(this).data('city');
		var state = $(this).data('state');
		//Zip is retrieved via .attr instead of .data to overcome a type conversion issue when zip starts with 0
		var zip = $(this).attr('data-zip');

		var fulladdress;
		try{
			fulladdress = addressParserZip5(streetaddress, city, state, zip);
		}catch(e){
			fulladdress = addressParserZip5(streetaddress, city, state, "12345");
		}

		$('span#prov-address-display-NPI' + provNPI).text(fulladdress);
	});
}


//Code to execute on document.ready event for Administrator/adminPatientView.html page
$(document).ready(function(){
	initPhoneNumbers();
	initAddresses();
	initAllDOBFields();
	initOrigDataVals();
	
	var duplicate_consent_id = $('input#duplicate_consent_id').val();
	
	if((typeof duplicate_consent_id != 'undefined') && (typeof duplicate_consent_id != 'null')){
		var trElement = $("table#patient_consents_list > tbody > tr > input#consentId_" + duplicate_consent_id).parent("tr");
		trElement.addClass("duplicate-consent");
	}
	
	
	/** 
	 * Bind Event Handlers: 
	 **/
	
	$('.cmd-view-consent').click(function(e){
		e.preventDefault();
		e.stopPropagation();
		
		var consentId = $(this).attr("value");
		var link = "downloadPdf.html?consentId=" + consentId;
		
		window.open(link,'_newtab');
		$('#consent_options_modal_' + consentId).modal('hide');
	});
	
	$('.cmd-edit-consent').click(function(e){
		e.preventDefault();
		e.stopPropagation();
		
		var consentId = $(this).attr("value");
		var patientId = $('#patientId').attr("value");
		var link = "adminPatientViewEditConsent.html?consentId=" + consentId +"&patientId="+patientId;
		
		window.open(link,'_self');
		$('#consent_options_modal_' + consentId).modal('hide');
	});
	
	$('.cmd-submit-consent').click(function(e){
		e.preventDefault();
		e.stopPropagation();
		
		var consentId = $(this).attr("value");
		
		$('#form_submit_consent_' + consentId).submit();
		$('#consent_options_modal_' + consentId).modal('hide');
	});
	
	$('.cmd-delete-consent').click(function(e){
		e.preventDefault();
		e.stopPropagation();
		
		var consentId = $(this).attr("value");
		
		$('#form_delete_consent_' + consentId).submit();
		$('#consent_options_modal_' + consentId).modal('hide');
	});
	
	$(".cmd-submit-revokation").click(function(){
		var consentId = $(this).attr("value");
		if(!($('#optionsRadio1_'+consentId).is(':checked')) && !($('#optionsRadio2_'+consentId).is(':checked'))){
			window.alert("Please select one option.");
		}
		else{
			$('#form_revoke_consent_' + consentId).submit();
		}
		
	});
	
	$('.cmd-revoke-choice-consent').click(function(e){
		e.preventDefault();
		e.stopPropagation();
		
		var consentId = $(this).attr("value");
		
		$('#consent_options_modal_' + consentId).modal('hide');
		$('#consent_revoke_modal_' + consentId).modal();
	});
	
	$('table#patient_consents_list > tbody > tr').click(function(e){
		e.preventDefault();
		e.stopPropagation();
		
		var consentId = $( this ).find("input[name='consentId']").attr("value");
		$('#consent_options_modal_' + consentId).modal();
	});
	
	
	$('table#patient_providers_list > tbody').delegate("tr", "click", function(e){
		e.preventDefault();
		e.stopPropagation();
		
		//crd_provider_modal_NPI
		var provNPI = $(this).data("npi");
		$('div#crd_provider_modal_NPI' + provNPI).modal();
	});
	
	
	$('#btn_add_providers').click(function(e){
		$('div#addProviderSearch-modal').modal();
	});
	
	$('#send_login_information').click(function(e){
		$('#send_login_information').attr("disabled", "disabled");
		var patientId=$(this).attr("value");
		$.ajax({
			url: "sendLoginInformation.html?patientId="+patientId,
			type: "GET",
			success: function(result){
				window.alert(result);
			},
			error: function(e){
				window.alert("ERROR: " + e.responseText);
			}
		});
	
	});
		
	//Bind hidden.bs.modal event handler to clear edit patient profile modal fields & error messages
	$('#editPatient').on('hidden.bs.modal', function(){
		resetEditPatientProfileModal();
	});
	
});

/*  Clear edit patient profile modal input field values & error messages. */
function resetEditPatientProfileModal(){
	$('input#first_name').val(origDataVals.getFName());
	$('#fname_client_error_text').text("");
	$('#fname_client_error_text').attr('style', "display: none;");
	
	$('input#last_name').val(origDataVals.getLName());
	$('#lname_client_error_text').text("");
	$('#lname_client_error_text').attr('style', "display: none;");
	
	$('select#gender').val(origDataVals.getGender());
	$('#gender_client_error_text').text("");
	$('#gender_client_error_text').attr('style', "display: none;");
	
	$('input#date').val(origDataVals.getDate());
	initAllDOBFields();
	$('#dob_client_error_text').text("");
	$('#dob_client_error_text').attr('style', "display: none;");
	
	$('input#email').val(origDataVals.getEmail());
	$('#email_client_error_text').text("");
	$('#email_client_error_text').attr('style', "display: none;");
	
	$('input#street').val(origDataVals.getStreet());
	$('#street_client_error_text').text("");
	$('#street_client_error_text').attr('style', "display: none;");
	
	$('input#city').val(origDataVals.getCity());
	$('#city_client_error_text').text("");
	$('#city_client_error_text').attr('style', "display: none;");
	
	$('select#state').val(origDataVals.getState());
	$('#state_client_error_text').text("");
	$('#state_client_error_text').attr('style', "display: none;");
	
	$('input#zip').val(origDataVals.getZip());
	$('#zip_client_error_text').text("");
	$('#zip_client_error_text').attr('style', "display: none;");
	
	$('input#phone').val(origDataVals.getPhone());
	$('#phone_client_error_text').text("");
	$('#phone_client_error_text').attr('style', "display: none;");
	
	$('input#socialsecurity').val(origDataVals.getSSN());
	$('#ssn_client_error_text').text("");
	$('#ssn_client_error_text').attr('style', "display: none;");
	
	$('input#medical_record_number').val(origDataVals.getMRN());
	$('#mrn_client_error_text').text("");
	$('#mrn_client_error_text').attr('style', "display: none;");
	
	$('select#marital_status').val(origDataVals.getMaritalStatus());
	$('#marital_client_error_text').text("");
	$('#marital_client_error_text').attr('style', "display: none;");
	
	$('select#religious_affiliation').val(origDataVals.getReligion());
	$('#religion_client_error_text').text("");
	$('#religion_client_error_text').attr('style', "display: none;");
	
	$('select#language').val(origDataVals.getLang());
	$('#language_client_error_text').text("");
	$('#language_client_error_text').attr('style', "display: none;");
	
}