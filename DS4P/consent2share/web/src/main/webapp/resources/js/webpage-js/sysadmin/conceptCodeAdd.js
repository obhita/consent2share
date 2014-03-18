var codeSystemsVersionsDataStore = (function() {
	var ary_codeSystemsVersionsList = new Array();
	
	return {
		pushVersion: function(in_cs_id, in_cs_version_key, in_cs_version_value){
			var tempValSet = new Array();
			tempValSet['cs_id'] = in_cs_id;
			tempValSet['cs_version_key'] = in_cs_version_key;
			tempValSet['cs_version_value'] = in_cs_version_value;
			ary_codeSystemsVersionsList.push(tempValSet);
		},
		getArySize: function(){
			var out_length = ary_codeSystemsVersionsList.length;
			return out_length;
		},
		getVersionByIndex: function(in_index){
			var out_valset = {};
			if((in_index >= 0) && (in_index < ary_codeSystemsVersionsList.length)){
				var temp_valset = ary_codeSystemsVersionsList.slice(in_index, in_index + 1);
				out_valset = deepCopy(temp_valset[0]);
			}else{
				window.alert("ERROR: An unknown error occured. Please reload the page.");
			}
			return out_valset;
		},
		getVersionsWithCsId: function(in_cs_id){
			var ary_length = ary_codeSystemsVersionsList.length;
			var tempAry = new Array();
			var isResults = false;
			for (var i = 0; i < ary_length; i++){
				var tempVarSet = ary_codeSystemsVersionsList.slice(i, i + 1);
				tempVarSet = tempVarSet[0];
				if(tempVarSet['cs_id'] == in_cs_id){
					tempAry.push(deepCopy(tempVarSet));
					isResults = true;
				}
			}
			
			if(isResults === true){
				return tempAry;
			}else{
				window.alert("ERROR: No matching versions with specified code system ID were found.");
				return null;
			}
		},
		emptyVersionsArray: function(){
			ary_codeSystemsVersionsList = new Array();
		},
		getAllVersions: function(){
			var tempAry = deepCopy(ary_codeSystemsVersionsList);
			return tempAry;
		}
	};
})();

$(document).ready(function(){
	$('input.cs_version_data').each(function(){
		var in_cs_id = $(this).data('cs-id');
		var in_cs_version_key = $(this).data('cs-version-key');
		var in_cs_version_value = $(this).data('cs-version-value');
		
		codeSystemsVersionsDataStore.pushVersion(in_cs_id, in_cs_version_key, in_cs_version_value);
	});
	
	$('select#select_codesys').change(function(evt){
		var sel_cs_id = $(this).val();
		
		if(sel_cs_id > 0){
			var resultAry = codeSystemsVersionsDataStore.getVersionsWithCsId(sel_cs_id);
			var resultAryLength = resultAry.length;
			
			$('select#select_version').empty();
			$('select#select_version').append("<option selected='selected' value=''>--- Please Select  ---</option>");
			
			for(var i = 0; i < resultAryLength; i++){
				$('select#select_version').append("<option value='" + resultAry[i]['cs_version_key'] + "'>" + resultAry[i]['cs_version_value'] + "</option>");
			}
		}
	});
	
	//display selected value set names once the modal is saved
	$('div#valueSetName-modal').on("hidden.bs.modal", function(){
		displayValSetNames();
		
	});
	
	
	$('input.valset_list_record').click(function(){
		displayValSetNames();
		
	});	
	
	$('#create_conceptCodesystem_action').validate({
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
				error.insertAfter(element);
			}
		},
        errorClass: 'valueSetErrMsg'
	});

	
});

function displayValSetNames(){
	clearDisplaySelectedValsets();
	
	$('input.valset_list_record').each(function(){
		
		var list_vs_id = $(this).data('valset-id');
		var list_vs_name = $(this).data('valset-name');
		
		if($(this).prop("checked")){
			showSelValSetNames(list_vs_name);
		}
		
	});
	
}


function showSelValSetNames(list_vs_name){
	$('div#selected_valusets_name_display_holder').append(
		"<div class='badge' > <span>" + list_vs_name +
					" </span> </div>");
}

function clearDisplaySelectedValsets(){
	$('div#selected_valusets_name_display_holder').empty();
}