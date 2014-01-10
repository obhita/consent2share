//Code to execute on document.ready event for connectionProviderAdd.html page
$(document).ready(function(){
	$('#phone1, #phone2, #phone3').autotab_magic().autotab_filter('numeric');
	cityEnableDisable();
	zipEnableDisable();

	/* Builds list of providers already added so that results in the search results
	 * modal can be disabled if that provider has already been added. */
	$('.npi-list-input').each(function(){
		var in_NPI = $(this).val();
		npiLists.push(in_NPI);
	});


	//Binds an event handler to the change event for the state_name element
	$('#state_name').change(function(e){
		e.stopPropagation();

		cityEnableDisable();
		zipEnableDisable();
	});            

	//Binds an event handler to the change event for the state_name element
	$('#zip_code').bind("propertychange keyup input", function(e){
		e.stopPropagation();

		city_stateEnableDisable();
	});
});
