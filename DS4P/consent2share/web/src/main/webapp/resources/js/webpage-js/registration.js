//Code to execute on document.ready event for registration.html page
$(document).ready(function() {
	var currentYear = (new Date).getFullYear();
	var minYear = currentYear - 125;

	if(1900 > minYear){
		minYear = 1900;
	}

	for (var i=currentYear; i >= minYear; i--){
		$('#year').append($("<option/>", { value: i, text: i}));
	}

	if(1 >= $('#date').val().length){
		preprocessInput();
	}

	setDOBfromTxt();
	
	$('#password').pstrength();
});