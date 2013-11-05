//Code to execute on document.ready event for profile.html page
$(document).ready(function() {
	initDOBYearField();
	setDOBfromTxt();

	$('.toggleButton').toggleButtons();
	$('#tourtest').joyride({
		tipAnimation : 'pop',
		tipLocation : 'right',
		autoStart : true
	});
	$('#tourtest').hide();
	
	$('#saveProfile').click(function(){
		if (validateProfileUpdateForm()!==false)
		$('#username_password_modal').modal('show');
	});
});


//Initialize date of birth fields
function initDOBYearField(){
	var currentYear = (new Date).getFullYear();
	var minYear = currentYear - 125;

	if(minYear < 1900){
		minYear = 1900;
	}

	for (var i=currentYear; i >= minYear; i--){
		$('#year').append($("<option/>", { value: i, text: i}));
	}
}



/**
 * FUNCTIONS USED ON PROFILE.HTML PAGE
 */

function setDOBfromTxt(){
	var inString = $('#date').attr('value');
	
	var inMonth = inString.substring(0, 2);
	var inDay = inString.substring(3, 5);
	var inYear = inString.substring(6, 10);
	
	setDOBMonth(inMonth);
	setDOBDay(inDay);
	setDOBYear(inYear);
}

function setDOBMonth(inMonth){
	$('#month').attr('value', inMonth);
}

function setDOBDay(inDay){
	$('#day').attr('value', inDay);
}

function setDOBYear(inYear){
	$('#year').attr('value', inYear);
}