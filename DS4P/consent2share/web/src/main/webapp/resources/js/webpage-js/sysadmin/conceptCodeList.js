var conceptCodeListDataStore = (function() {
	var ary_conceptCodeList = new Array();
	
	return {
		pushConceptCode: function(in_conceptcode_id, in_conceptcode_code, in_conceptcode_name, in_conceptcode_cs_name, in_conceptcode_cs_vs_name){
			var tempConceptCode = new Array();
			tempConceptCode['conceptcode_id'] = in_conceptcode_id;
			tempConceptCode['conceptcode_code'] = in_conceptcode_code;
			tempConceptCode['conceptcode_name'] = in_conceptcode_name;
			tempConceptCode['conceptcode_cs_name'] = in_conceptcode_cs_name;
			tempConceptCode['conceptcode_cs_vs_name'] = in_conceptcode_cs_vs_name;
			tempConceptCode['conceptcode_valsets_ary'] = new Array();
			
			ary_conceptCodeList.push(tempConceptCode);
			return true;
		},
		pushConceptCodeValSetByConceptCodeId: function(in_conceptcode_id, in_valset_key, in_valset_name){
			var ary_length = ary_conceptCodeList.length;
			var found_index = null;
			for (var i = 0; i < ary_length; i++){
				var tempConceptCode = ary_conceptCodeList.slice(i, i + 1);
				tempConceptCode = tempConceptCode[0];
				if(tempConceptCode['conceptcode_id'] == in_conceptcode_id){
					found_index = i;
					break;
				}
			}
			
			if(found_index === null){
				console.log("ERROR: found_index variable was null in pushConceptCodeValSetByConceptCodeId method.");
				window.alert("ERROR: An unknown error occured. Please reload the page.");
				return false;
			}else{
				if((in_valset_key === null) || (in_valset_key === undefined)){
					console.log("ERROR: in_valset_key was either null or undefined in pushConceptCodeValSetByConceptCodeId method.");
					console.log("     in_valset_key = " + in_valset_key);
					window.alert("ERROR: Invalid data passed to pushConceptCodeValSetByConceptCodeId method");
					return false;
				}
				
				if((in_valset_name === null) || (in_valset_name === undefined)){
					console.log("ERROR: in_valset_name was either null or undefined in pushConceptCodeValSetByConceptCodeId method.");
					console.log("     in_valset_name = " + in_valset_name);
					window.alert("ERROR: Invalid data passed to pushConceptCodeValSetByConceptCodeId method");
					return false;
				}
				
				var tempAry = new Array();
				tempAry['valset_key'] = in_valset_key;
				tempAry['valset_name'] = in_valset_name;
				
				try{
					ary_conceptCodeList[found_index]['conceptcode_valsets_ary'].push(tempAry);
				}catch(e){
					console.log("ERROR: An unknown error occured in pushConceptCodeValSetByConceptCodeId method.");
					console.log("    Error stack trace: ", e);
					window.alert("ERROR: An unknown error occured.");
					return false;
				}
				return true;
			}
		},
		getArySize: function(){
			var out_length = ary_conceptCodeList.length;
			return out_length;
		},
		getConceptCodeByIndex: function(in_index){
			var out_conceptcode = {};
			if((in_index >= 0) && (in_index < ary_conceptCodeList.length)){
				var temp_conceptcode = ary_conceptCodeList.slice(in_index, in_index + 1);
				out_conceptcode = deepCopy(temp_conceptcode[0]);
			}
			return out_conceptcode;
		},
		getConceptCodeValSetsAryByConceptCodeId: function(in_conceptcode_id){
			var ary_length = ary_conceptCodeList.length;
			var found_index = null;
			for (var i = 0; i < ary_length; i++){
				var tempConceptCode = ary_conceptCodeList.slice(i, i + 1);
				tempConceptCode = tempConceptCode[0];
				if(tempConceptCode['conceptcode_id'] == in_conceptcode_id){
					found_index = i;
					break;
				}
			}
			
			if(found_index === null){
				console.log("ERROR: found_index variable was null in getConceptCodeValSetsAryByConceptCodeId method.");
				window.alert("ERROR: An unknown error occured. Please reload the page.");
				return null;
			}else{
				var concept_code_record = ary_conceptCodeList.slice(found_index, found_index + 1);
				var out_ary = new Array();
				out_ary = deepCopy(concept_code_record[0]['conceptcode_valsets_ary']);
				return out_ary;
			}
		},
		removeConceptCodeByIndex: function(in_index){
			if((in_index >= 0) && (in_index < ary_conceptCodeList.length)){
				ary_conceptCodeList.splice(in_index, 1);
			}
		},
		removeConceptCodeByConceptCodeId: function(in_conceptcode_id){
			var ary_length = ary_conceptCodeList.length;
			var found_index = null;
			for (var i = 0; i < ary_length; i++){
				var tempConceptCode = ary_conceptCodeList.slice(i, i + 1);
				tempConceptCode = tempConceptCode[0];
				if(tempConceptCode['conceptcode_id'] == in_conceptcode_id){
					found_index = i;
					break;
				}
			}
			
			if(found_index === null){
				console.log("ERROR: found_index variable was null in removeConceptCodeByConceptCodeId method.");
				window.alert("ERROR: An unknown error occured. Please reload the page.");
			}else{
				ary_conceptCodeList.splice(found_index, 1);
			}
		},
		emptyConceptCodeArray: function(){
			ary_conceptCodeList = new Array();
		},
		getAllConceptCodes: function(){
			var tempAry = ary_conceptCodeList;
			return tempAry;
		}
	};
})();


$(document).ready(function(){
	initPopovers();
	
	$('form').on("click", "button", function(e){
		e.preventDefault();
	});

	$('tr.conceptcode-record').each(function(){
		var in_conceptcode_id = $(this).data("conceptcode-id");
		var in_conceptcode_code = $(this).data("conceptcode-code");
		var in_conceptcode_name = $(this).data("conceptcode-name");
		var in_conceptcode_cs_name = $(this).data("conceptcode-cs-name");
		var in_conceptcode_cs_vs_name = $(this).data("conceptcode-cs-vs-name");
		
		conceptCodeListDataStore.pushConceptCode(in_conceptcode_id, in_conceptcode_code, in_conceptcode_name, in_conceptcode_cs_name, in_conceptcode_cs_vs_name);
		
		$(this).find('div.conceptcode-valset-record').each(function(){
			var in_valset_key = $(this).data("conceptcode-valset-key");
			var in_valset_name = $(this).data("conceptcode-valset-name");
			
			try{
				conceptCodeListDataStore.pushConceptCodeValSetByConceptCodeId(in_conceptcode_id, in_valset_key, in_valset_name);
			}catch(e){
				console.log("ERROR: An unknown error occured while attempting to call conceptCodeListDataStore.pushConceptCodeValSetByConceptCodeId() from within the $(this).find('div.conceptcode-valset-record').each() loop, inside the document.ready() method.");
				console.log("    Error stack trace: ", e);
				window.alert("ERROR: An unknown error occured.");
			}
		});
	});
	
	$('table#current_conceptcodes_table > tbody').on("click", "tr.conceptcode-record .delete-record-trigger", function(evt){
		var isConfirmed = confirm('Are you sure you want to delete this concept code?');
		
		if(isConfirmed === true){
			var clickedElement = $(this);
			var clickedConceptCodeId = $(this).parents("tr").data("conceptcode-id");
			
			$.ajax({url: "conceptCode/delete/" + clickedConceptCodeId,
					type: "DELETE",
					success: function(response){
						var cur_conceptcode_id = clickedElement.parents("tr").data("conceptcode-id");
						conceptCodeListDataStore.removeConceptCodeByConceptCodeId(cur_conceptcode_id);
						clickedElement.parents("tr").remove();
					},
					error: function(jqXHRobj, err, errThrown) {
						console.log("ERROR: Unable to delete concept code.");
						console.log("     Server returned HTTP Status Code: " + errThrown);
						console.log("     Error details: " + jqXHRobj.responseText);
						window.alert("ERROR: Unable to delete concept code.");
					}
			});
		}
	});
	
	$('select#select_filter_on_cs').change(function(evt){
		var selCodeSysName = $(this).val();
		
		//Hide table while rebuilding
		$('table#current_conceptcodes_table > tbody').addClass("hidden");
		
		resetSelectFilterOnCsVs();
		resetSelectFilterOnValSetName();
		
		//Clear and rebuild table
		rebuildTableFromAry();
		
		if(selCodeSysName != ""){
			$('input.cs_version_data').each(function(){
				if($(this).data('cs-name') == selCodeSysName){
					var tempVal = $(this).data('cs-version-value');
					appendOptionToSelect("select_filter_on_cs_vs", tempVal, tempVal);
				}
			});
			
			//Remove rows matching filter criteria from HTML table
			filterCodeSysName(selCodeSysName);
			
			//Change table header text
			setHeaderForFilter();
		}else{
			//Change table header text
			setHeaderForNoFilter();
		}
		
		//Show table once rebuild complete
		$('table#current_conceptcodes_table > tbody').removeClass("hidden");
	});
	
	
	// batch
	$('select#batch_select_codesys').change(function(evt){
		var selCodeSysName = $(this).val();
		
		resetBatchSelectVersionDropdown();
		
		if(selCodeSysName != ""){
			$('input.batch_cs_version_data').each(function(){
				if($(this).data('cs-name') == selCodeSysName){
					var tempVal = $(this).data('cs-version-value');
					var tempKey = $(this).data('cs-version-key');
					//alert("tempval: " + tempVal);
					//alert("tempkey: " + tempKey);
					$('select#batch_select_version').append("<option value='" + tempKey + "'>" + tempVal + "</option>");
				}
			});
		}
	});
	
	
	
	$('select#select_filter_on_cs_vs').change(function(evt){
		var selCodeSysVersionName = $(this).val();
		var selCodeSysName = $('select#select_filter_on_cs').val();
		
		//Hide table while rebuilding
		$('table#current_conceptcodes_table > tbody').addClass("hidden");
		
		resetSelectFilterOnValSetName();
		
		//Clear and rebuild table
		rebuildTableFromAry();
		
		filterCodeSysName(selCodeSysName);
		
		if(selCodeSysVersionName != ""){
			filterCodeSysVersionName(selCodeSysVersionName);
			
			//Change table header text
			setHeaderForFilter();
		}else{
			if(selCodeSysName == ""){
				setHeaderForNoFilter();
			}
		}
		
		//Show table once rebuild complete
		$('table#current_conceptcodes_table > tbody').removeClass("hidden");
	});
	
	
	$('select#select_filter_on_valset_name').change(function(evt){
		var selValSetNameId = $(this).val();
		var selCodeSysName = $('select#select_filter_on_cs').val();
		var selCodeSysVersionName = $('select#select_filter_on_cs_vs').val();
		
		//Hide table while rebuilding
		$('table#current_conceptcodes_table > tbody').addClass("hidden");
		
		//Clear and rebuild table
		rebuildTableFromAry();
		
		//Remove rows matching filter criteria from HTML table
		filterCodeSysName(selCodeSysName);
		filterCodeSysVersionName(selCodeSysVersionName);
		
		if(selValSetNameId != ""){
			//Remove rows matching filter criteria from HTML table
			filterValueSetName(selValSetNameId);
			
			//Change table header text
			setHeaderForFilter();
		}else{
			if(selCodeSysName == ""){
				setHeaderForNoFilter();
			}
		}
		
		//Show table once rebuild complete
		$('table#current_conceptcodes_table > tbody').removeClass("hidden");
	});

	
	$('#btn_search_conceptcode_reset').click(function(e){
		window.location.href = "conceptCodeList.html";
	});
	
	
	$('#btn_search_conceptcode_name_submit').click(function(e){
		e.preventDefault();
		e.stopPropagation();
		
		var in_searchParam = $('input#txt_search_conceptcode_name').val();
		
		if(in_searchParam.length > 0){
			var responseObj = {};
			
			$.ajax({url: "conceptCode/ajaxSearchConceptCode",
					method: "GET",
					data: {searchCategory: "name",
						   searchTerm: in_searchParam},
					success: function(response){
						responseObj = response;
						
						var responseLength = responseObj.length;
						
						if(responseLength > 0){
							conceptCodeListDataStore.emptyConceptCodeArray();
							
							for(var i = 0; i < responseLength; i++){
								var in_conceptcode_id = responseObj[i].id;
								var in_conceptcode_code = responseObj[i].code;
								var in_conceptcode_name = responseObj[i].name;
								var in_conceptcode_cs_name = responseObj[i].codeSystemName;
								var in_conceptcode_cs_vs_name = responseObj[i].codeSystemVersionName;
								
								conceptCodeListDataStore.pushConceptCode(in_conceptcode_id, in_conceptcode_code, in_conceptcode_name, in_conceptcode_cs_name, in_conceptcode_cs_vs_name);
								
								var in_conceptcode_valsets_ary = new Array();
								in_conceptcode_valsets_ary = responseObj[i].valueSetMap;
								
								for (curValSet in in_conceptcode_valsets_ary){
									var in_valset_key = curValSet;
									var in_valset_name = in_conceptcode_valsets_ary[curValSet];
								
									try{
										conceptCodeListDataStore.pushConceptCodeValSetByConceptCodeId(in_conceptcode_id, in_valset_key, in_valset_name);
									}catch(e){
										console.log("ERROR: An unknown error has occured while attempting to call conceptCodeListDataStore.pushConceptCodeValSetByConceptCodeId() inside ajax success function for ajaxSearchConceptCode by name.");
										console.log("      Error details: " + e);
										window.alert("ERROR: An unknown error has occured.");
									}
								}
								
							}
							
							resetSelectFilterOnCs();
							resetSelectFilterOnCsVs();
							resetSelectFilterOnValSetName();
							
							//Change table header text
							setHeaderForSearchName();
							
							rebuildTableFromAry();
						}else{
							window.alert("No results found");
						}
						
						clearSearchConceptCodeNameInput();
					},
					error: function(e){
						clearSearchConceptCodeNameInput();
						window.alert("ERROR - An error occured while search for concept code by name: " + e.responseText);
					}
			});
		}
	});
	
	
	$('#btn_search_conceptcode_code_submit').click(function(e){
		e.preventDefault();
		e.stopPropagation();
		
		var in_searchParam = $('input#txt_search_conceptcode_code').val();
		
		if(in_searchParam.length > 0){
			var responseObj = {};
			
			$.ajax({url: "conceptCode/ajaxSearchConceptCode",
					method: "GET",
					data: {searchCategory: "code",
						   searchTerm: in_searchParam},
					success: function(response){
						responseObj = response;
						
						var responseLength = responseObj.length;
						
						if(responseLength > 0){
							conceptCodeListDataStore.emptyConceptCodeArray();
							
							for(var i = 0; i < responseLength; i++){
								var in_conceptcode_id = responseObj[i].id;
								var in_conceptcode_code = responseObj[i].code;
								var in_conceptcode_name = responseObj[i].name;
								var in_conceptcode_cs_name = responseObj[i].codeSystemName;
								var in_conceptcode_cs_vs_name = responseObj[i].codeSystemVersionName;
								
								conceptCodeListDataStore.pushConceptCode(in_conceptcode_id, in_conceptcode_code, in_conceptcode_name, in_conceptcode_cs_name, in_conceptcode_cs_vs_name);
								
								var in_conceptcode_valsets_ary = new Array();
								in_conceptcode_valsets_ary = responseObj[i].valueSetMap;
								
								for (curValSet in in_conceptcode_valsets_ary){
									var in_valset_key = curValSet;
									var in_valset_name = in_conceptcode_valsets_ary[curValSet];
								
									try{
										conceptCodeListDataStore.pushConceptCodeValSetByConceptCodeId(in_conceptcode_id, in_valset_key, in_valset_name);
									}catch(e){
										console.log("ERROR: An unknown error has occured while attempting to call conceptCodeListDataStore.pushConceptCodeValSetByConceptCodeId() inside ajax success function for ajaxSearchConceptCode by code.");
										console.log("      Error details: " + e);
										window.alert("ERROR: An unknown error has occured.");
									}
								}
								
							}
							
							resetSelectFilterOnCs();
							resetSelectFilterOnCsVs();
							resetSelectFilterOnValSetName();
							
							//Change table header text
							setHeaderForSearchCode();
							
							rebuildTableFromAry();
						}else{
							window.alert("No results found");
						}
						
						clearSearchConceptCodeCodeInput();
					},
					error: function(e){
						clearSearchConceptCodeCodeInput();
						window.alert("ERROR - An error occured while search for concept code by code: " + e.responseText);
					}
			});
		}
	});
	
	//display selected value set names once the modal is saved
	$('div#valueSetName-modal').on("hidden.bs.modal", function(){
		displayValSetNames();
		
	});
	
	$('input.valset_list_record').click(function(){
		displayValSetNames();
		
	});	
	
	$("#batch_conceptcode_upload_form").validate({
	  ignore: [],  // <-- allows for validation of hidden fields
      rules: {
          valueSetIds: {
              required: true
          },
          batch_file: { 
        	  required: true,
        	  extension: "xlsx"
          }
      },
      errorPlacement: function(error, element) {
	     if (element.attr("name") == "valueSetIds") {
	       error.insertAfter("#selected_valusets_name_display_holder");
	     } else if (element.attr("name") == "batch_file") {
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
	
	var ary_len = conceptCodeListDataStore.getArySize();
	
	for(var i = 0; i < ary_len; i++){
		var curRecord = conceptCodeListDataStore.getConceptCodeByIndex(i);
		
		var temp_conceptcode_id = curRecord["conceptcode_id"];
		var temp_conceptcode_code = curRecord["conceptcode_code"];
		var temp_conceptcode_name = curRecord["conceptcode_name"];
		var temp_conceptcode_cs_name = curRecord["conceptcode_cs_name"];
		var temp_conceptcode_cs_vs_name = curRecord["conceptcode_cs_vs_name"];
		var temp_conceptcode_valsets_ary = conceptCodeListDataStore.getConceptCodeValSetsAryByConceptCodeId(temp_conceptcode_id);
		
		insertTableRow(temp_conceptcode_id, temp_conceptcode_code, temp_conceptcode_name, temp_conceptcode_cs_name, temp_conceptcode_cs_vs_name, temp_conceptcode_valsets_ary);
	}
}

function insertTableRow(temp_conceptcode_id, temp_conceptcode_code, temp_conceptcode_name, temp_conceptcode_cs_name, temp_conceptcode_cs_vs_name, temp_conceptcode_valsets_ary){
	var valset_string = "";
	
	var ary_len = temp_conceptcode_valsets_ary.length;
	
	for(var i = 0; i < ary_len; i++){
		var temp_valset_key = temp_conceptcode_valsets_ary[i]['valset_key'];
		var temp_valset_name = temp_conceptcode_valsets_ary[i]['valset_name'];
		
		valset_string = valset_string + "<div class='conceptcode-valset-record' data-conceptcode-valset-key='" + temp_valset_key + "' data-conceptcode-valsetcat-code='" + temp_valset_name + "' id='conceptcode_valset_data_" + temp_conceptcode_id + "_" + temp_valset_key + "'>" +
											"<span>" + temp_valset_name + "</span>" +
										"</div>";
	}
	
	$('table#current_conceptcodes_table > tbody').append("<tr class='conceptcode-record' data-conceptcode-cs-name='" + temp_conceptcode_cs_name + "' data-conceptcode-id='" + temp_conceptcode_id + "' data-conceptcode-cs-vs-name='" + temp_conceptcode_cs_vs_name + "' data-conceptcode-name='" + temp_conceptcode_name + "' data-conceptcode-code='" + temp_conceptcode_code + "'>" +
							"<td>" +
								"<span>" +
									"<span class='delete-record-trigger btn btn-danger btn-xs'>" +
										"<span class='fa fa-minus fa-white'></span>" +
									"</span>" +
								"</span>" +
							"</td>" +
							"<td>" +
								"<a href='conceptCode/edit/" + temp_conceptcode_id + "' >" +
									"<span>" + temp_conceptcode_code + "</span>" +
								"</a>" +
							"</td>" +
							"<td>" + temp_conceptcode_name + "</td>" +
							"<td>" + valset_string + "</td>" +
							"<td>" + temp_conceptcode_cs_name + "    :   " + temp_conceptcode_cs_vs_name + "</td>" +
						"</tr>");
}

function clearTable(){
	$('table#current_conceptcodes_table > tbody > tr.conceptcode-record').remove();
}

function filterCodeSysName(selCodeSysName){
	//Remove rows matching filter criteria from HTML table
	if(selCodeSysName != ""){
		$('tr.conceptcode-record').each(function(){
			if(selCodeSysName != $(this).data("conceptcode-cs-name")){
				$(this).remove();
			}
		});
	}
}

function filterCodeSysVersionName(selCodeSysVersionName){
	//Remove rows matching filter criteria from HTML table
	if(selCodeSysVersionName != ""){
		$('tr.conceptcode-record').each(function(){
			if(selCodeSysVersionName != $(this).data("conceptcode-cs-vs-name")){
				$(this).remove();
			}
		});
	}
}

function filterValueSetName(selValSetNameId){
	//Remove rows matching filter criteria from HTML table
	if(selValSetNameId != ""){
		$('tr.conceptcode-record').each(function(){
			var tr_element = $(this);
			var found_match = false;
			
			tr_element.find('div.conceptcode-valset-record').each(function(){
				if(selValSetNameId == $(this).data("conceptcode-valset-key")){
					found_match = true;
					/*'return false' causes jQuery to exit the .each loop, similar to how 'break' exits a normal JavaScript loop.
					  It does not cause the filterValueSetName function to return with a value of false here. */
					return false;
				}
			});
			
			if(found_match !== true){
				tr_element.remove();
			}
		});
	}
}

function setHeaderForFilter(){
	//Change table header text
	$('tr#conceptcodes_table_header').addClass("hidden");
	$('tr#conceptcodes_filter_table_header').removeClass("hidden");
}

function setHeaderForSearchCode(){
	//Change table header text
	$('tr#conceptcodes_table_header').addClass("hidden");
	$('tr#conceptcodes_filter_table_header').addClass("hidden");
	$('tr#conceptcodes_namesearch_table_header').addClass("hidden");
	$('tr#conceptcodes_codesearch_table_header').removeClass("hidden");
}

function setHeaderForSearchName(){
	//Change table header text
	$('tr#conceptcodes_table_header').addClass("hidden");
	$('tr#conceptcodes_filter_table_header').addClass("hidden");
	$('tr#conceptcodes_codesearch_table_header').addClass("hidden");
	$('tr#conceptcodes_namesearch_table_header').removeClass("hidden");
}

function setHeaderForNoFilter(){
	//Change table header text
	$('tr#conceptcodes_filter_table_header').addClass("hidden");
	$('tr#conceptcodes_table_header').removeClass("hidden");
}

function setHeaderForNoSearch(){
	//Change table header text
	$('tr#conceptcodes_codesearch_table_header').addClass("hidden");
	$('tr#conceptcodes_namesearch_table_header').addClass("hidden");
	setHeaderForNoFilter();
}

function resetBatchSelectVersionDropdown(){
	$('select#batch_select_version').empty();
	//$('select#batch_select_version').append("<option selected='selected' value=''>- Please Select -</option>");
	appendOptionToSelect("batch_select_version", "", "- Please Select -", true);
}

function appendOptionToSelect(in_select_id, in_value, in_text){
	if(in_value == ""){
		$('select#' + in_select_id).append("<option selected='selected' value=''>- Please Select -</option>");
	}else{
		$('select#' + in_select_id).append("<option value='" + in_value + "'>" + in_text + "</option>");
	}
}

function resetSelectFilterOnCs(){
	$('select#select_filter_on_cs').val("");
	$('select#select_filter_on_cs').trigger("change");
}

function resetSelectFilterOnCsVs(){
	$('select#select_filter_on_cs_vs').empty();
	appendOptionToSelect("select_filter_on_cs_vs", "", "- Please Select -");
	$('select#select_filter_on_cs_vs').trigger("change");
}

function resetSelectFilterOnValSetName(){
	$('select#select_filter_on_valset_name').val("");
	$('select#select_filter_on_valset_name').trigger("change");
}

function clearSearchConceptCodeCodeInput(){
	$('input#txt_search_conceptcode_code').val("");
}

function clearSearchConceptCodeNameInput(){
	$('input#txt_search_conceptcode_name').val("");
}

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