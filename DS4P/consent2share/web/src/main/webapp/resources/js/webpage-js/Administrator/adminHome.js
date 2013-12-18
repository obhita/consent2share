//Code to execute on document.ready event for Administrator/adminHome.html page
$(document).ready(function(){
	initDOBYearField();
	setDOBfromTxt();
	
	$( "#searchTextBox" )
    .autocomplete({source: function( request, response ) {
        $.getJSON( "patientlookup/query", {
          firstname: getFirstName(request.term),
          lastname:getLastName(request.term)
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
	
	function getFirstName(fullName){
		if(fullName.indexOf(" ")>=0)
			return fullName.substring(0,fullName.indexOf(" "));
		else
			return fullName;
	}
	
	function getLastName(fullName){
		if(fullName.indexOf(" ")>=0)
			return fullName.substring(fullName.indexOf(" ")+1);
		else
			return "";
	}
	
	//Bind hidden.bs.modal event handler to clear create patient account modal fields & error messages
	$('#createPatient').on('hidden.bs.modal', function(){
		clearCreatePatientAccountModal();
	});
});

/*  Clear create patient account modal input field values & error messages. */
function clearCreatePatientAccountModal(){
	$('input#first_name').val("");
	/* FIXME: Add code to clear error message value & set display:none
	 * for first_name once the element for that field's error message
	 * is created.
	 */
	
	$('input#last_name').val("");
	/* FIXME: Add code to clear error message value & set display:none
	 * for last_name once the element for that field's error message
	 * is created.
	 */
	
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
