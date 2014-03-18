$(document).ready(function(){
	$('input.valset_list_record').each(function(){
		var list_Vs_id = $(this).data('valset-id');	
		var list_name = $(this).data('valset-name');
		var curValsetListRecordElement = $(this);
		$('input.selected_valset_record').each(function(){
			var sel_vs_id = $(this).data('valset-id');
			if(sel_vs_id == list_Vs_id){
				curValsetListRecordElement.prop('checked', true);
				return false;
			}
		});
	});
	
	//display associated value set names on onload
	displaySelectedValsets();
	
	//display selected value set names once the modal is saved
	$('div#valueSetName-modal').on("hidden.bs.modal", function(){
		displaySelectedValsets();
	});
	

	$('input.valset_list_record').click(function(){
		displaySelectedValsets();
		
	});	
	
	$('#edit_valueset_action').validate({
		ignore: [],  // <-- allows for validation of hidden fields
		rules: {
			valueSetIds: {
	              required: true
	          }
		},
		errorPlacement: function(error, element) {
			if (element.attr("name") == "valueSetIds") {
				error.insertAfter("#selected_valusets_name_display_holder");
			} else {
				//error.insertAfter(element);
				error.insertAfter( element.parent("div"));
			}
		},
        errorClass: 'valueSetErrMsg'
	});	
	
	
	
});

function displaySelectedValsets(){
	clearDisplaySelectedValsets();
	
	$('input.valset_list_record').each(function(){
		var list_Vs_id = $(this).data('valset-id');	
		var list_name = $(this).data('valset-name');
		
		var isChecked = $(this).prop("checked");
		if(isChecked === true){
			displaySelectedValsetRecord(list_Vs_id, list_name);
		}
	});
}

function displaySelectedValsetRecord(sel_vs_id, list_name){
	$('div#selected_valusets_name_display_holder').append("<div id='display_selected_valset_'" + sel_vs_id + "' class='valset_display_div badge'>" +
			"<span>" + list_name + "</span>" +
		"</div>");
}

function clearDisplaySelectedValsets(){
	$('div#selected_valusets_name_display_holder').empty();
}