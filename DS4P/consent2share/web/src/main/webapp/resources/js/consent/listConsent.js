/**
 * Extend jQuery Steps Plug-in with new showValidationPage function
 * 
 * @param {String} inContent - string containing the HTML code
 *                             to use as the content of the validation
 *                             page
 */
$.fn.steps.showValidationPage = function(inContent){
	var lastStepIndex = this.find('.steps li').length - 1;

	if(this.steps("getStep", lastStepIndex).title != "Validation Error"){
		this.steps("add", {title: "Validation Error", content: inContent});
	}
};

/**
 * Extend jQuery Steps Plug-in with new hideValidationPage function
 */
$.fn.steps.hideValidationPage = function(){
	var lastStepIndex = this.find('.steps li').length - 1;

	if(this.steps("getStep", lastStepIndex).title == "Validation Error"){
		this.steps("remove", lastStepIndex);
	}
};


$(document).ready(function() {
			$('#normal-toggle-button').toggleButtons();
			$('#info-toggle-button').toggleButtons();
			
			
			//Initialize jQuery Steps wizard plug-in
			initializeAddConsentWizard();
			
			//Bind event handler for when the wizard modal is hidden to reset the wizard plug-in
			$('div#addConsentWizardModal').on("hidden.bs.modal", function(){
				resetAddConsentWizard();
			});
			
			//Bind event handler for when the 'Add a Consent' button is clicked to show the wizard modal
			$('button#consent-main-plus-btn').click(function(){
				$('div#addConsentWizardModal').modal();
			});
			
			initializeDatepickers();
			
			$('body').removeClass('fouc_loading');

			$(".submit_buttons").click(function() {
				$(this).attr("disabled", "disabled");
			});
			
			var sign_revoke_arys = {
					consentPreSignList: [],
					consentPreRevokeList: []
			};

			initListConsent(sign_revoke_arys);
			
			var duplicate_consent_id = $('input#duplicate_consent_id').val();
			
			if((typeof duplicate_consent_id != 'undefined') && (typeof duplicate_consent_id != 'null')){
				$("section.consent-current-active#consent_current_active_" + duplicate_consent_id).children("div.summary-border").first().addClass("duplicate-consent");
			}
});

function initializeDatepickers() {
	$('input.datepicker').datepicker();
	resetDatepickers();
}

function resetDatepickers() {
	var dateTemp = new Date();
    var startDate = new Date(dateTemp.getFullYear(), dateTemp.getMonth(), dateTemp.getDate(), 0, 0, 0, 0);
    var endDate = new Date(dateTemp.getFullYear()+1, dateTemp.getMonth(), dateTemp.getDate(), 0, 0, 0, 0);
    
    //set end date as a day minus 1 year
    endDate.setDate(endDate.getDate() - 1);
    
    $('input.datepicker#date_picker_start').datepicker('setValue', startDate);
    $('input.datepicker#date_picker_end').datepicker('setValue', endDate);
}

function resetAddConsentWizard() {
	$('form#frm_add_consent_wizard_holder input').iCheck("uncheck");
	$('form#frm_add_consent_wizard_holder input').iCheck("destroy");
	$('form#frm_add_consent_wizard_holder').steps("destroy");
	
	$('form#frm_add_consent_wizard_holder div.error').attr("style", "");
	
	initializeAddConsentWizard();
	
	resetDatepickers();
}

function initializeAddConsentWizard(){
	$('form#frm_add_consent_wizard_holder').steps({
		stepsOrientation: 'vertical',
		onStepChanging: function(event, currentIndex, newIndex){
			return $(this).valid();
		},
		onStepChanged: function(event, currentIndex, priorIndex){
			var wizForm = $(this);
			var priorStepElement = wizForm.find('.steps li').eq(priorIndex);

			if(priorStepElement.hasClass('last') === true){
				wizForm.steps("hideValidationPage");
			}
		},
		onFinishing: function(){
			return $(this).valid();
		},
		onFinished: function(){
			var frm = $(this);
			
			$.ajax({
				url: "checkDuplicateConsent",
				type: "POST",
				data: frm.serialize(),
				success: function(){
					frm.submit();
				},
				error: function(jqXHR, textStatus, errorThrown) {
					//TODO (MH): Handle different error types from controller
					if(errorThrown == "Conflict"){
						frm.steps("showValidationPage", "<div>" +
								"<div id='consent_validation_error_holder' class='alert alert-danger'>" +
									"<h4 id='consent_validation_error_header'>Whoops!</h4>" +
									"<p id='consent_validation_error_text'>Please make changes to the consent being created to ensure that you are not creating a duplicate.</p>" +
								"</div>" +
							"</div>" +
							"<div id='pnl_existing_consent' class='panel panel-info nomargin'>" +
								"<div class='panel-heading'>" +
									"<h3 class='panel-title'>Existing Consent</h3>" +
								"</div>" +
								"<div class='panel-body'>" +
									"<div id='existing_authorized_providers'>" +
										"<label class='existing-consent-field-label'>Authorized Providers:</label>" +
										"<span id='existing_authorized_providers_data' class='existing-consent-field-data'></span>" +
									"</div>" +
									"<div id='existing_discloseTo_providers'>" +
										"<label class='existing-consent-field-label'>Disclose To:</label>" +
										"<span id='existing_discloseTo_providers_data' class='existing-consent-field-data'></span>" +
									"</div>" +
									"<div id='existing_purposeOf_use'>" +
										"<label class='existing-consent-field-label'>Purpose of Use:</label>" +
										"<span id='existing_purposeOf_use_data' class='existing-consent-field-data'></span>" +
									"</div>" +
									"<div id='existing_consent_start_date'>" +
										"<label class='existing-consent-field-label'>Start Date:</label>" +
										"<span id='existing_consent_start_date_data' class='existing-consent-field-data'></span>" +
									"</div>" +
									"<div id='existing_consent_end_date'>" +
										"<label class='existing-consent-field-label'>End Date:</label>" +
										"<span id='existing_consent_end_date_data' class='existing-consent-field-data'></span>" +
									"</div>" +
									"<div id='existing_consent_status'>" +
										"<label class='existing-consent-field-label'>Status:</label>" +
										"<span id='existing_consent_status_data' class='existing-consent-field-data'></span>" +
									"</div>" +
									"<div class='pull-right'>" +
										"<button id='btn_view_existing_consent' class='btn btn-primary'>View Existing Consent</button>" +
									"</div>" +
								"</div>" +
							"</div>");
						
						populateDuplicateValidateData(jqXHR.responseText);
						frm.steps("next");
					}else{
						console.log("UNKNOWN ERROR: An unknown error occured in the ajax call to checkDuplicateConsent");
						
						//TODO (MH): Remove the following console.log line when done testing
						console.log("  Details: " + jqXHR.responseText);
						window.alert("ERROR: An unknown error has occured.");
					}
			    }
			});
		}
	}).validate({
		errorLabelContainer: $('form#frm_add_consent_wizard_holder div.error'),
		rules: {
			providersPermittedToDisclose: {
				required: function(element){
					var numOrgProvidersChecked = $("[name='organizationalProvidersPermittedToDisclose']:checked").length;
					var isRequired = true;
					
					if(numOrgProvidersChecked > 0){
						isRequired = false;
					}else{
						isRequired = true;
					}
					
					return isRequired;
				}
			},
			providersDisclosureIsMadeTo: {
				required: function(element){
					var numOrgProvidersChecked = $("[name='organizationalProvidersDisclosureIsMadeTo']:checked").length;
					var isRequired = true;
					
					if(numOrgProvidersChecked > 0){
						isRequired = false;
					}else{
						isRequired = true;
					}
					
					return isRequired;
				}
			}
		},
		messages: {
			providersPermittedToDisclose: {
				required: "You must select at least one provider"
			},
			providersDisclosureIsMadeTo: {
				required: "You must select at least one provider"
			}
		}
	});
	
	//Add a cancel button to the add consent wizard modal
	$("form#frm_add_consent_wizard_holder > div.actions").prepend("<button id='btn_cancel_add_consent_wizard_modal'" +
												"type='button' class='btn btn-default' style='display: inline-block; float: left;'>Cancel</button>");
	
	//Bind click event handler to cancel button
	$("div#addConsentWizardModal form#frm_add_consent_wizard_holder > div.actions > button#btn_cancel_add_consent_wizard_modal").on("click", function(){
		$('div#addConsentWizardModal').modal("hide");
	});
	
	$('form#frm_add_consent_wizard_holder input').iCheck({
	    checkboxClass: 'icheckbox_square-blue',
	    radioClass: 'iradio_square-blue',
	    increaseArea: '20%'
	});
	
	$('button.btn-sel-all-tab').click(function(e){
		var $target = $(e.target);
		
		switch($target.attr('id')){
			case "btn_share_purposes_select_all":
				$('input[name=shareForPurposeOfUseCodes]').iCheck('check');
				break;
			default:
				break;
		}
		
	});
	
	$('button.btn-desel-all-tab').click(function(e){
		var $target = $(e.target);
		
		switch($target.attr('id')){
			case "btn_share_purposes_deselect_all":
				$('input[name=shareForPurposeOfUseCodes]').iCheck('uncheck');
				break;
			default:
				break;
		}
		
	});
	
	checkRecommendedPurposeOfUseFields();
	
	//reset form action URL & method type
	$('form#frm_add_consent_wizard_holder').attr("action", "/consent2share/consents/addNewConsent.html");
	$('form#frm_add_consent_wizard_holder').attr("method", "POST");
	
	/* Bind ifChecked event handler to listen for when authorize from
	 * providers are checked and disable that provider in the disclose to list */
	$('div#authorize_list_container').on("ifChecked", "input.toDiscloseList", function(){
		var toDiscloseNPI = $(this).val();
		var isMadeToElementId = "to" + toDiscloseNPI;
		
		$("input#" + isMadeToElementId).iCheck("disable");
		$("label[for='"+ isMadeToElementId +"']").addClass("grayout");
		
	});
	
	/* Bind ifUnchecked event handler to listen for when authorize from
	 * providers are unchecked and enable that provider in the disclose to list */
	$('div#authorize_list_container').on("ifUnchecked", "input.toDiscloseList", function(){
		var toDiscloseNPI = $(this).val();
		var isMadeToElementId = "to" + toDiscloseNPI;
		
		$("input#" + isMadeToElementId).iCheck("enable");
		$("label[for='"+ isMadeToElementId +"']").removeClass("grayout");
		
	});
	
	/* Bind ifChecked event handler to listen for when disclose to providers
	 * are checked and disable that provider in the authorize from list */
	$('div#disclose_list_container').on("ifChecked", "input.isMadeToList", function(){
		var isMadeToNPI = $(this).val();
		var toDiscloseElementId = "from" + isMadeToNPI;
		
		$("input#" + toDiscloseElementId).iCheck("disable");
		$("label[for='"+ toDiscloseElementId +"']").addClass("grayout");
		
	});
	
	/* Bind ifUnchecked event handler to listen for when disclose to providers
	 * are unchecked and enable that provider in the authorize from list */
	$('div#disclose_list_container').on("ifUnchecked", "input.isMadeToList", function(){
		var isMadeToNPI = $(this).val();
		var toDiscloseElementId = "from" + isMadeToNPI;
		
		$("input#" + toDiscloseElementId).iCheck("enable");
		$("label[for='"+ toDiscloseElementId +"']").removeClass("grayout");
		
	});
}

function populateDuplicateValidateData(jsonObj) {
	//TODO (MH): Add try/catch block for JSON.parse
	var validationDataObj = JSON.parse(jsonObj);
	
	var selected_authorized_providers_ary = validationDataObj.selectedAuthorizedProviders;
	var selected_discloseTo_providers_ary = validationDataObj.selectedDiscloseToProviders;
	var selected_purposeOf_use_ary = validationDataObj.selectedPurposeOfUse;
	
	var existing_authorized_providers_ary = validationDataObj.existingAuthorizedProviders;
	var existing_discloseTo_providers_ary = validationDataObj.existingDiscloseToProviders;
	var existing_purposeOf_use_ary = validationDataObj.existingPurposeOfUse;
	
	var selected_authorized_providers_string = "";
	var selected_discloseTo_providers_string = "";
	var selected_purposeOf_use_string = "";
	
	try{
		selected_authorized_providers_string = arrayToUlString(selected_authorized_providers_ary);
		selected_discloseTo_providers_string = arrayToUlString(selected_discloseTo_providers_ary);
		selected_purposeOf_use_string = arrayToUlString(selected_purposeOf_use_ary);
	}catch(err){
		if(err.name === "RangeError"){
			selected_authorized_providers_string = "<ul><li>ERROR DISPLAYING DATA</li></ul>";
			selected_discloseTo_providers_string = "<ul><li>ERROR DISPLAYING DATA</li></ul>";
			selected_purposeOf_use_string = "<ul><li>ERROR DISPLAYING DATA</li></ul>";
		}
	}
	
	var selected_consent_start_date_string = validationDataObj.selectedConsentStartDate;
	var selected_consent_end_date_string = validationDataObj.selectedConsentEndDate;
	
	var existing_authorized_providers_string = "";
	var existing_discloseTo_providers_string = "";
	var existing_purposeOf_use_string = "";
	
	try{
		existing_authorized_providers_string = arrayToUlString(existing_authorized_providers_ary);
		existing_discloseTo_providers_string = arrayToUlString(existing_discloseTo_providers_ary);
		existing_purposeOf_use_string = arrayToUlString(existing_purposeOf_use_ary);
	}catch(err){
		existing_authorized_providers_string = "<ul><li>ERROR DISPLAYING DATA</li></ul>";
		existing_discloseTo_providers_string = "<ul><li>ERROR DISPLAYING DATA</li></ul>";
		existing_purposeOf_use_string = "<ul><li>ERROR DISPLAYING DATA</li></ul>";
	}
	
	var existing_consent_start_date_string = validationDataObj.existingConsentStartDate;
	var existing_consent_end_date_string = validationDataObj.existingConsentEndDate;
	var existing_consent_status_string = validationDataObj.existingConsentStatus;
	
	var existing_consent_id = validationDataObj.existingConsentId;
	
	var selectedConsentPanelElement = $('form#frm_add_consent_wizard_holder div#pnl_selected_consent');
	var existingConsentPanelElement = $('form#frm_add_consent_wizard_holder div#pnl_existing_consent');
	
	selectedConsentPanelElement.find('div#selected_authorized_providers > span.selected-consent-field-data#selected_authorized_providers_data').empty().html(selected_authorized_providers_string);
	selectedConsentPanelElement.find('div#selected_discloseTo_providers > span.selected-consent-field-data#selected_discloseTo_providers_data').empty().html(selected_discloseTo_providers_string);
	selectedConsentPanelElement.find('div#selected_purposeOf_use > span.selected-consent-field-data#selected_purposeOf_use_data').empty().html(selected_purposeOf_use_string);
	selectedConsentPanelElement.find('div#selected_consent_start_date > span.selected-consent-field-data#selected_consent_start_date_data').text(selected_consent_start_date_string);
	selectedConsentPanelElement.find('div#selected_consent_end_date > span.selected-consent-field-data#selected_consent_end_date_data').text(selected_consent_end_date_string);
	
	existingConsentPanelElement.find('div#existing_authorized_providers > span.existing-consent-field-data#existing_authorized_providers_data').empty().html(existing_authorized_providers_string);
	existingConsentPanelElement.find('div#existing_discloseTo_providers > span.existing-consent-field-data#existing_discloseTo_providers_data').empty().html(existing_discloseTo_providers_string);
	existingConsentPanelElement.find('div#existing_purposeOf_use > span.existing-consent-field-data#existing_purposeOf_use_data').empty().html(existing_purposeOf_use_string);
	existingConsentPanelElement.find('div#existing_consent_start_date > span.existing-consent-field-data#existing_consent_start_date_data').text(existing_consent_start_date_string);
	existingConsentPanelElement.find('div#existing_consent_end_date > span.existing-consent-field-data#existing_consent_end_date_data').text(existing_consent_end_date_string);
	existingConsentPanelElement.find('div#existing_consent_status > span.existing-consent-field-data#existing_consent_status_data').text(existing_consent_status_string);
	
	//Bind new click event handler to "View Existing Consent" button
	$('div#pnl_existing_consent button#btn_view_existing_consent').on("click", function(e){
		e.preventDefault();
		window.location.href="listConsents.html?duplicateconsent=" + existing_consent_id + "#jump_consent_" + existing_consent_id;
	});
}

function checkRecommendedPurposeOfUseFields(){
	$('form#frm_add_consent_wizard_holder div#purposeOfSharingInputs input#TREAT').iCheck("check");
	$('form#frm_add_consent_wizard_holder div#purposeOfSharingInputs input#ETREAT').iCheck("check");
	$('form#frm_add_consent_wizard_holder div#purposeOfSharingInputs input#CAREMGT').iCheck("check");
}

function initSessionTimeoutChecker(){
	function runSessionTimeout() {
		window.location.replace("../resources/j_spring_security_logout");
	}
	
	window.setTimeout(runSessionTimeout, 60000*10);
}

function openSignConsentPage(id){
	
	
	$.ajax({
		  type: "POST",
		  url: 'signConsent.html',
		  consentId:id,
		  success:function(data){
			  if(data == 'OK') {
			        window.open(url);
			  }
		  }
		  
		});
}

function initConsentPresignStatusChecker (sign_revoke_arys) {
	if (sign_revoke_arys.consentPreSignList.length!=0||sign_revoke_arys.consentPreRevokeList!=0) {
			valueInterval=0;
			
			setInterval(function() {						
				$.ajax({
				url: "listConsents.html/checkConsentStatus",
				type:"GET",
				traditional: true,
				async:true, 
				data: {consentPreSignList:sign_revoke_arys.consentPreSignList,
					consentPreRevokeList:sign_revoke_arys.consentPreRevokeList},
						
				success: function (result) { 
				
					if(result.trim()=="true")
						 window.location.replace("listConsents.html");
				}
				});
			}, 1000 * 60 *0.1);
	}
}

function initRevokeModalListeners() {
	var consentRevokationId=null;
				
	$(".revokation-button").live("click",function(){
		consentRevokationId=$(this).attr("id").substr(6,$(this).attr("id").length-6);
	});
	
	$("#signRevokation").click(function(){
		if(!($("#optionsRadio1").is(':checked')) && !($("#optionsRadio2").is(':checked'))){
			window.alert("Please select one option.");
		}
		else{
			$("#consentRevokationForm").append('<input name="consentId" style="display: none;" hidden="true" value="'+consentRevokationId+'"/>');
			$("#consentRevokationForm").submit();
		}
		
	});
	
	//Bind hidden.bs.modal event handler to clear modal fields
	$("#revoke-modal").on('hidden.bs.modal', function(){
		document.getElementById("consentRevokationForm").reset();
	});
}


function initListConsent(sign_revoke_arys) {
	initSessionTimeoutChecker();
	
	$('.consent-entry-input').each(function(){
		if($(this).data('consentstage')=="CONSENT_SUBMITTED"){
			sign_revoke_arys.consentPreSignList.push($(this).val());
		}
		
		if($(this).data('revokestage')=="REVOCATION_SUBMITTED"){
			sign_revoke_arys.consentPreRevokeList.push($(this).val());
		}
	});
	
	initConsentPresignStatusChecker(sign_revoke_arys);
	initRevokeModalListeners();
};


function loadTryMyPolicy(consentId) {
	var tryMyPocilyurl = "tryMyPolicyLookupC32Documents/"+consentId; 
	$.ajax({
		url: tryMyPocilyurl,
		type:"GET",
		traditional: true,
		async:true, 
		data: {consentId: consentId},
		success: function (data) { 
			$("#tryMyPolicy_c32Docs").empty();
			$("#consentId").remove();
			$("#applyMyPolicyForm").append("<input id='consentId' type='hidden' name='consentId' value='" + consentId + "' />");
			
			// populate c32 documents
			$.each(data.c32Documents, function(index, element) {
				$("#tryMyPolicy_c32Docs").append('<option value='+element.id+' name="c32Id">'+element.filename+'</option>');

	        });
			
			$("#tryMyPolicy_purposeOfUse").empty();
			
			// populate share for purpose of use codes
			$.each(data.shareForPurposeOfUseCodesAndValues, function(index, element){
				$("#tryMyPolicy_purposeOfUse").append("<option value='"+index+"'>"+element+"</option>"); 
			});
		}
	});
}

$('#tryMyPolicyApplyButton').click(function(){
    var consentId = $('#consentId').val();
    var c32Id = $("#tryMyPolicy_c32Docs").val();
    var purposeOfUse = $("#tryMyPolicy_purposeOfUse").val();
    
	var url = "tryMyPolicyApply/consentId/" + consentId + "/c32Id/" + c32Id + "/purposeOfUse/" + purposeOfUse;
	window.open(url);
});

$("#applyMyPolicyForm").submit(function() {

    var url = "tryMyPolicyApply"; // the script where you handle the form input.
    
//    var jqxhr = $.ajax({
//           type: "POST",
//           url: url,
//           data: $("#applyMyPolicyForm").serialize(), // serializes the form's elements.
//           success: function(data)
//           {
//        	   window.alert("SUCCESS");
//               //window.alert("PASS: " + data);
//               
//               //var win=window.open('about:blank');
//               //with(win.document)
//               //{
//                 //open();
//                 //write(data);
//                 //close();
//              // }
//               
//               //window.open('data:text/xml,' + encodeURIComponent(data) );
//               
//              // var win=window.open('Try My Policy');
//             // $(win.document.body).html(data);
//           },
//           error: function(e){
//        	   window.alert("ERROR: " + e.responseText);
//           }
//         });
    
//    jqxhr.always(function(){
//    	window.alert("JQXHR TRIGGER");
//    	
//    	window.alert(jqxhr.responseXML);
//    	
//    });

    return false; // avoid to execute the actual submit of the form.
});
