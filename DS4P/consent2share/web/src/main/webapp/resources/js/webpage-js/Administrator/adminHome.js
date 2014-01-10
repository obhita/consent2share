//Code to execute on document.ready event for Administrator/adminHome.html page
$(document).ready(function(){
	initDOBYearField();
	setDOBfromTxt();
	
	$( "#searchTextBox" )
    .autocomplete({source: function( request, response ) {
        $.getJSON( "patientlookup/query", {
          token:request.term
        }, function (data) {
               response($.map(data, function (item) {
                   return {
                	   id:item.id,
                       label: item.firstName+" "+item.lastName,
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
});

/*  Clear create patient account modal input field values & error messages. */
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
