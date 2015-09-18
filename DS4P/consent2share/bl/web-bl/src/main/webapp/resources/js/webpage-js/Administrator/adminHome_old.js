//Code to execute on document.ready event for Administrator/adminHome.html page
$(document).ready(function(){
	initDOBYearField();
	setDOBfromTxt();
	
	$( "#searchTextBox" )
    .autocomplete({source: function( request, response ) {
        $.getJSON( "patientlookup/query", {
          token:request.term
        }, function (data) {
        	var dateRegEx='((?:(?:[1]{1}\\d{1}\\d{1}\\d{1})|(?:[2]{1}\\d{3}))[-:\\/.](?:[0]?[1-9]|[1][012])[-:\\/.](?:(?:[0-2]?\\d{1})|(?:[3][01]{1})))(?![\\d])';
        	var p = new RegExp(dateRegEx,["i"]);
               response($.map(data, function (item) {
            	   var dob=p.exec(item.birthDay);
                   return {
                	   id:item.id,
                       label: item.firstName
                       		+" "+item.lastName
                       		+", "+dob[0]
                       		+", ***-**-"+item.socialSecurityNumber,
                       value:	item.firstName+" "+item.lastName,
                   };
                  
               }));
           } );
      },
      search: function() {
        var term = this.value;
        if ( term.length < 2 ) {
          return false;
        }
      },
      
      select: function( event, ui ) {
      	document.location.href="adminPatientView.html?id="+ui.item.id;
      }
    });
	
	//Bind hidden.bs.modal event handler to clear create patient account modal fields & error messages
	$('#createPatient').on('hidden.bs.modal', function(){
		clearCreatePatientAccountModal();
	});
	
	$("form#admin_create_patient_acct_form").submit(function(evt){
		var isFormValid = false;
		
		//Disable create account button
		$("form#admin_create_patient_acct_form button#adminCreatePatientAccountFormSubmitButton").prop("disabled", true);
		
		isFormValid = validateAdminCreatePatientAccount();
		
		if(isFormValid === true){
			//On validation success, return true to proceed with form submission
			return true;
		}else{
			//On validation failure, prevent form submission
			evt.preventDefault();
			
			//Re-enable create account button
			$("form#admin_create_patient_acct_form button#adminCreatePatientAccountFormSubmitButton").prop("disabled", false);
			
			return false;
		}
	});
	
});

/**
 * Clear create patient account modal input field values & error messages
 */
function clearCreatePatientAccountModal(){
	$('input#first_name').val("");
	$('#fname_client_error_text').text("");
	$('#fname_client_error_text').attr('style', "display: none;");
	
	$('input#last_name').val("");
	$('#lname_client_error_text').text("");
	$('#lname_client_error_text').attr('style', "display: none;");
	
	$('select#gender').val("");
	$('#gender_client_error_text').text("");
	$('#gender_client_error_text').attr('style', "display: none;");
	
	$('input#date').val("");
	$('select#month').val("  ");
	$('select#day').val("  ");
	$('select#year').val("    ");
	$('#dob_client_error_text').text("");
	$('#dob_client_error_text').attr('style', "display: none;");
}
