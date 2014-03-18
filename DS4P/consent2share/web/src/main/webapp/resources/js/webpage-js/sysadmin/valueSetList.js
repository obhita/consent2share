var valueSetListDataStore = (function() {
	var ary_valueSetList = new Array();
	
	return {
		pushValSet: function(in_valset_id, in_valset_code, in_valset_name, in_valset_cat_code, in_valset_cat_name){
			var tempValSet = new Array();
			tempValSet['valset_id'] = in_valset_id;
			tempValSet['valset_code'] = in_valset_code;
			tempValSet['valset_name'] = in_valset_name;
			tempValSet['valset_cat_code'] = in_valset_cat_code;
			tempValSet['valset_cat_name'] = in_valset_cat_name;
			ary_valueSetList.push(tempValSet);
		},
		getArySize: function(){
			var out_length = ary_valueSetList.length;
			return out_length;
		},
		getValSetByIndex: function(in_index){
			var out_valset = {};
			if((in_index >= 0) && (in_index < ary_valueSetList.length)){
				var temp_valset = ary_valueSetList.slice(in_index, in_index + 1);
				out_valset = deepCopy(temp_valset[0]);
			}
			return out_valset;
		},
		removeValSetByIndex: function(in_index){
			if((in_index >= 0) && (in_index < ary_valueSetList.length)){
				ary_valueSetList.splice(in_index, 1);
			}
		},
		removeValSetByValsetId: function(in_valset_id){
			var ary_length = ary_valueSetList.length;
			var found_index = null;
			for (var i = 0; i < ary_length; i++){
				var tempVarSet = ary_valueSetList.slice(i, i + 1);
				tempVarSet = tempVarSet[0];
				if(tempVarSet['valset_id'] == in_valset_id){
					found_index = i;
					break;
				}
			}
			
			if(found_index == null){
				window.alert("ERROR: An unknown error occured. Please reload the page.");
			}else{
				ary_valueSetList.splice(found_index, 1);
			}
		},
		emptyValSetArray: function(){
			ary_valueSetList = new Array();
		},
		getAllValSets: function(){
			var tempAry = ary_valueSetList;
			return tempAry;
		}
	};
})();


$(document).ready(function(){
	initPopovers();
	
	$('tr.valset-record').each(function(){
		var in_valset_id = $(this).data("valset-id");
		var in_valset_code = $(this).data("valset-code");
		var in_valset_name = $(this).data("valset-name");
		var in_valset_cat_code = $(this).data("valset-cat-code");
		var in_valset_cat_name = $(this).data("valset-cat-name");
		
		valueSetListDataStore.pushValSet(in_valset_id, in_valset_code, in_valset_name, in_valset_cat_code, in_valset_cat_name);
	});
	
	$('table#current_valuesets_table > tbody').on("click", "tr.valset-record .delete-record-trigger", function(evt){
		var isConfirmed = confirm('Are you sure you want to delete this value set?');
		
		if(isConfirmed === true){
			var clickedElement = $(this);
			var clickedValsetId = $(this).parents("tr").data("valset-id");
			
			$.ajax({url: "valueSet/delete/" + clickedValsetId,
					type: "DELETE",
					success: function(response){
						var cur_valset_id = clickedElement.parents("tr").data("valset-id");
						valueSetListDataStore.removeValSetByValsetId(cur_valset_id);
						clickedElement.parents("tr").remove();
					},
					error: function(e) {
						window.alert("ERROR: Unable to delete value-set.");
					}
			});
		}
	});
	
	$('select#select_filter_on_category').change(function(evt){
		var selCatCode = $(this).val();
		
		if(selCatCode == ""){
			//Hide table while rebuilding
			$('table#current_valuesets_table > tbody').addClass("hidden");
			
			$('tr#valueset_table_header').removeClass("hidden");
			$('tr#valueset_categoryfilter_table_header').addClass("hidden");
			
			//Clear and rebuild table
			rebuildTableFromAry();
			
			//Show table once rebuild complete
			$('table#current_valuesets_table > tbody').removeClass("hidden");
		}else{
			//Hide table while rebuilding
			$('table#current_valuesets_table > tbody').addClass("hidden");
			
			$('tr#valueset_categoryfilter_table_header').removeClass("hidden");
			$('tr#valueset_table_header').addClass("hidden");
			
			//Clear and rebuild table
			rebuildTableFromAry();
			
			//Remove rows matching filter criteria from HTML table
			$('tr.valset-record').each(function(){
				if(selCatCode != $(this).data("valset-cat-code")){
					$(this).remove();
				}
			});
			
			//Show table once rebuild complete
			$('table#current_valuesets_table > tbody').removeClass("hidden");
		}
	});
	

	$('#btn_search_valset_code_reset').click(function(e){
		e.preventDefault();
		e.stopPropagation();
		
		window.location.href = "valueSetList.html";
	});
	
	$('#btn_search_valset_name_submit').click(function(e){
		e.preventDefault();
		e.stopPropagation();
		
		var in_searchParam = $('input#txt_search_valset2').val();
		
		if(in_searchParam.length > 0){
			var responseObj = {};
			
			$.ajax({url: "valueSet/ajaxSearchValueSet",
					method: "GET",
					data: {searchCategory: "name",
						   searchTerm: in_searchParam},
					success: function(response){
						responseObj = response;
						
						var responseLength = responseObj.length;
						
						if(responseLength > 0){
							valueSetListDataStore.emptyValSetArray();
							
							for(var i = 0; i < responseLength; i++){
								var in_valset_id = responseObj[i].id;
								var in_valset_code = responseObj[i].code;
								var in_valset_name = responseObj[i].name;
								var in_valset_cat_code = responseObj[i].valueSetCatCode;
								var in_valset_cat_name = responseObj[i].valueSetCatName;
								
								valueSetListDataStore.pushValSet(in_valset_id, in_valset_code, in_valset_name, in_valset_cat_code, in_valset_cat_name);
							}
							
							$('tr#valueset_table_header').addClass("hidden");
							$('tr#valueset_categoryfilter_table_header').addClass("hidden");
							$('tr#valueset_codesearch_table_header').addClass("hidden");
							$('tr#valueset_namesearch_table_header').removeClass("hidden");
							
							rebuildTableFromAry();
						}else{
							window.alert("No results found");
						}
					},
					error: function(e){
						window.alert("ERROR - An error occured while search for value set name: " + e.responseText);
					}
			});
		}
	});
	
	$('#btn_search_valset_code_submit').click(function(e){
		e.preventDefault();
		e.stopPropagation();
		
		var in_searchParam = $('input#txt_search_valset').val();
		
		if(in_searchParam.length > 0){
			var responseObj = {};
			
			$.ajax({url: "valueSet/ajaxSearchValueSet",
					method: "GET",
					data: {searchCategory: "code",
						   searchTerm: in_searchParam},
					success: function(response){
						responseObj = response;
						
						var responseLength = responseObj.length;
						
						if(responseLength > 0){
							valueSetListDataStore.emptyValSetArray();
							
							for(var i = 0; i < responseLength; i++){
								var in_valset_id = responseObj[i].id;
								var in_valset_code = responseObj[i].code;
								var in_valset_name = responseObj[i].name;
								var in_valset_cat_code = responseObj[i].valueSetCatCode;
								var in_valset_cat_name = responseObj[i].valueSetCatName;
								
								valueSetListDataStore.pushValSet(in_valset_id, in_valset_code, in_valset_name, in_valset_cat_code, in_valset_cat_name);
							}
							
							$('tr#valueset_table_header').addClass("hidden");
							$('tr#valueset_categoryfilter_table_header').addClass("hidden");
							$('tr#valueset_namesearch_table_header').addClass("hidden");
							$('tr#valueset_codesearch_table_header').removeClass("hidden");
							
							rebuildTableFromAry();
						}else{
							window.alert("No results found");
						}
					},
					error: function(e){
						window.alert("ERROR - An error occured while search for value set code: " + e.responseText);
					}
			});
		}
	});
	
	$("#valueset_batchuplad_form").validate({
	      rules: {
	          batch_file: { 
	        	  required: true,
	        	  extension: "xlsx"
	          }
	      },
	      errorPlacement: function(error, element) {
		     if (element.attr("name") == "batch_file") {
		       error.insertAfter("#file_err_msg");
		     } else {
		       error.insertAfter(element);
		     }
		  },
		  errorClass: 'valueSetErrMsg'
		});
});


/**
 * Clear HTML table rows and rebuild from stored array 
 */
function rebuildTableFromAry(){
	clearTable();
	
	var ary_len = valueSetListDataStore.getArySize();
	
	for(var i = 0; i < ary_len; i++){
		var curRecord = valueSetListDataStore.getValSetByIndex(i);
		
		var temp_valset_id = curRecord["valset_id"];
		var temp_valset_code = curRecord["valset_code"];
		var temp_valset_name = curRecord["valset_name"];
		var temp_valset_cat_code = curRecord["valset_cat_code"];
		var temp_valset_cat_name = curRecord["valset_cat_name"];
		
		insertTableRow(temp_valset_id, temp_valset_code, temp_valset_name, temp_valset_cat_code, temp_valset_cat_name);
	}
}

function insertTableRow(temp_valset_id, temp_valset_code, temp_valset_name, temp_valset_cat_code, temp_valset_cat_name){
	$('table#current_valuesets_table > tbody').append("<tr class='valset-record' data-valset-code='" + temp_valset_code + "' data-valset-id='" + temp_valset_id + "' data-valset-cat-name='" + temp_valset_cat_name + "' data-valset-cat-code='" + temp_valset_cat_code + "' data-valset-name='" + temp_valset_name + "'>" +
			"<td>" +
				"<span class='delete-record-trigger btn btn-danger btn-xs'>" +
					"<span class='fa fa-minus fa-white'></span>" +
				"</span>" +
			"</td>" +
			"<td>" +
				"<a href='valueSet/edit/" + temp_valset_id + "'>" +
					"<span>" + temp_valset_code + "</span>" +
				"</a>" +
			"</td>" +
			"<td>" + temp_valset_name + "</td>" +
			"<td>" + temp_valset_cat_name + "</td>" +
		"</tr>");
}

function clearTable(){
	$('table#current_valuesets_table > tbody > tr.valset-record').remove();
}

