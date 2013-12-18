//Code to execute on document.ready event for Administrator/adminPatientView.html page
$(document).ready(function(){
	
	//Bind event handlers:
	$('table#patient_consents_list > tbody > tr').click(function(e){
		e.preventDefault();
		e.stopPropagation();
		consentId=$( this ).find("input").attr("value");
				
		/* FIXME: This URL being set for document.location should be changed
		 * to point to the admin patient consent view and/or edit page once
		 * that page is created (also add the appropriate entries in the
		 * AdminController class to handle the new URL once changed). */
		//document.location =  'adminPatientViewConsent.html';
		
		var link='downloadPdf.html?consentId='+consentId;
		window.open(link,'_newtab');
	});
	
});