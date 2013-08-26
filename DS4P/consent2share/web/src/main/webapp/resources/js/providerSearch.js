var individualJSON;
var organizationalJSON;
npiLists=new Array();


//Control the triangle in the expandable tags
$(function(){
	
	$("#expand1").click(function(){
		
	if ($("#expandTriangle1").text()=="\u25BC")
		$("#expandTriangle1").text("\u25B6");
		
	else
		$("#expandTriangle1").text("\u25BC");
		$("#expandTriangle2").text("\u25B6");
		
});
	
	$("#expand2").click(function(){
		
		if ($("#expandTriangle2").text()=="\u25BC")
			$("#expandTriangle2").text("\u25B6");
		else
			$("#expandTriangle2").text("\u25BC");
			$("#expandTriangle1").text("\u25B6");
	});
});

$(function(){
	
	$(".addIndividualProviderButton").live("click",function(){
		var entryId=$(this).attr("id").substr(27,$(this).attr("id").length-27);
		var serializedQueryResult=JSON.stringify(individualJSON["providers"][entryId]);
		$.ajax({
			  url: "connectionProviderAdd.html",
			  type: "POST",
			  data: {querySent:serializedQueryResult},
			  success:function() {
				  window.location.replace("connectionMain.html?notify=add");
			  }
			});
	});
});


$(function(){
	
	$(".addOrganizationalProviderButton").live("click",function(){
		var entryId=$(this).attr("id").substr(31,$(this).attr("id").length-31);
		var serializedQueryResult=JSON.stringify(organizationalJSON["providers"][entryId]);
		$.ajax({
			  url: "connectionProviderAdd.html",
			  type: "POST",
			  data: {querySent:serializedQueryResult},
			  success:function() {
			       window.location.replace("connectionMain.html?notify=add");
			  }
			});
	});
});


function lookup (){
		
		var lookupQueryIndividual = location.protocol + '//' +location.host+"/provider-web/providers"; 
		var organizationSearchFlag=1;
		var ajaxFinishedFlag=0;
		$("#resultList").hide();
		$("#noResult").hide();
		$("#noResponse").hide();
		$("#resultList").empty();

		if ($("#city_name").val() != ""){
			lookupQueryIndividual+="/city/"+$("#city_name").val();
		}
		if ($("#state_name").val() != ""){
			lookupQueryIndividual+="/usstate/"+$("#state_name").val();
		}
		if ($("#zip_code").val() != ""){
			lookupQueryIndividual+="/zipcode/"+$("#zip_code").val();
		}
		if ($("#gender").val() != ""){
			var gender=null;
			if ($("#gender").val()=="M")
				gender='MALE';
			if ($("#gender").val()=="F")
				gender="FEMALE";
			lookupQueryIndividual+="/gender/"+gender;
			organizationSearchFlag=0;
		}
		if ($("#specialty").val() != ""){
			lookupQueryIndividual+="/specialty/"+$("#specialty").val();
		}
		if ($("#phone1").val() != ""){
			lookupQueryIndividual+="/phone/"+$("#phone1").val()+$("#phone2").val()+$("#phone3").val();
		}
		if ($("#first_name").val() != ""){
			lookupQueryIndividual+="/firstname/"+$("#first_name").val();
			organizationSearchFlag=0;
		}
		
		var lookupQueryOrg=lookupQueryIndividual;
		
		if ($("#last_name").val() != ""){
			lookupQueryIndividual+="/lastname/"+$("#last_name").val();
			lookupQueryOrg+="/orgname/"+$("#last_name").val();
		}
		
		$("#provider_search_modal .search-loading").show();
		
        setTimeout( killAjaxCall, 20000); // if no response in 20 seconds, abort both getJson requests                 
		
		var myAjaxCall1 = jQuery.getJSON(lookupQueryIndividual+"/entitytype/Individual",function(queryResult){	
			individualJSON=queryResult;
			for (var i=0;i<queryResult["providers"].length;i++)
				{addable="true";
					for(var j=0;j<npiLists.length;j++)
						{
						if(queryResult["providers"][i]["npi"]==npiLists[j])
							addable="false";
						}
					if(addable=="true"){
						$("#resultList").append("<div class='provider_record_space'><div class='provider_record_box'><div class='provider_record_header'>"+"" +
							'<p class="result_row"><span class="result_field provider_name_field">' + queryResult["providers"][i]["providerLastName"] + ", " + queryResult["providers"][i]["providerFirstName"] + "</span>" +
							'<span class="result_field">[NPI:' + queryResult["providers"][i]["npi"] + ']</span></p>' + 
							'<p class="result_row add_button_space"><span class="result_field"><button class="addIndividualProviderButton btn btn-mini btn-success" id="addIndividualProviderButton'+i+'"><span class="icon-plus icon-white"></span></button></span> Add this provider.</p></div>' + 
							'<p class="result_row"><span class="result_field provider_specialty_field">' + queryResult["providers"][i]["healthcareProviderTaxonomy_1"] + '</span></p>' + 
							'<p class="result_row"><span class="result_field">' + queryResult["providers"][i]["providerFirstLineBusinessPracticeLocationAddress"] + ', ' +
								(queryResult["providers"][i]["providerSecondLineBusinessPracticeLocationAddress"]==""?(queryResult["providers"][i]["providerSecondLineBusinessPracticeLocationAddress"]):"") + 
								queryResult["providers"][i]["providerBusinessPracticeLocationAddressCityName"] + ", " +
								queryResult["providers"][i]["providerBusinessPracticeLocationAddressStateName"] + ", " + 
								zipCodeParser(queryResult["providers"][i]["providerBusinessPracticeLocationAddressPostalCode"]) + '</span></p>' + 
							'<p class="result_row"><span class="result_field">' + phoneNumberParser(queryResult["providers"][i]["providerBusinessPracticeLocationAddressTelephoneNumber"]) + "</span></p></div></div>");
					}else{
						$("#resultList").append("<div class='provider_record_space'><div class='provider_record_box'><div class='provider_record_header'>"+"" +
								'<p class="result_row"><span class="result_field provider_name_field">' + queryResult["providers"][i]["providerLastName"] + ", " + queryResult["providers"][i]["providerFirstName"] + "</span>" +
								'<span class="result_field">[NPI:' + queryResult["providers"][i]["npi"] + ']</span></p>' + 
								'<p class="result_row add_button_space" style="color: black;"><span class="result_field"><button class="btn btn-mini" disabled="true"><span class="icon-plus icon-white"></span></button></span> Provider already added.</p></div>' + 
								'<p class="result_row"><span class="result_field provider_specialty_field">' + queryResult["providers"][i]["healthcareProviderTaxonomy_1"] + '</span></p>' + 
								'<p class="result_row"><span class="result_field">' + queryResult["providers"][i]["providerFirstLineBusinessPracticeLocationAddress"] + ', ' +
									(queryResult["providers"][i]["providerSecondLineBusinessPracticeLocationAddress"]==""?(queryResult["providers"][i]["providerSecondLineBusinessPracticeLocationAddress"]):"") + 
									queryResult["providers"][i]["providerBusinessPracticeLocationAddressCityName"] + ", " +
									queryResult["providers"][i]["providerBusinessPracticeLocationAddressStateName"] + ", " + 
									zipCodeParser(queryResult["providers"][i]["providerBusinessPracticeLocationAddressPostalCode"]) + '</span></p>' + 
								'<p class="result_row"><span class="result_field">' + phoneNumberParser(queryResult["providers"][i]["providerBusinessPracticeLocationAddressTelephoneNumber"]) + "</span></p></div></div>");
					}
				}
				ajaxFinishedFlag++;
			if (ajaxFinishedFlag==2 || organizationSearchFlag==0 )
				showResult();

			});
		
		var myAjaxCall2 = null ;
		
		if (organizationSearchFlag==1){
			myAjaxCall2 = jQuery.getJSON(lookupQueryOrg+"/entitytype/Organization",function(queryResult){
			organizationalJSON=queryResult;
			for (var i=0;i<queryResult["providers"].length;i++) {
				addable="true";
				for(var j=0;j<npiLists.length;j++)
					{
					
					if(queryResult["providers"][i]["npi"]==npiLists[j])
						addable="false";
					}
				if(addable=="true"){
					$("#resultList").append("<div class='provider_record_space'><div class='provider_record_box'><div class='provider_record_header'>"+"" +
							'<p class="result_row"><span class="result_field  provider_name_field">' + queryResult["providers"][i]["providerOrganizationName"] + "</span>" +
							'<span class="result_field">[NPI:' + queryResult["providers"][i]["npi"] + ']</span></p>' + 
							'<p class="result_row add_button_space"><span class="result_field"><button class="addOrganizationalProviderButton btn btn-mini btn-success" id="addOrganizationalProviderButton'+i+'"><span class="icon-plus icon-white"></span></button></span> Add this provider.</p></div>' + 
							'<p class="result_row"><span class="result_field provider_specialty_field">' + queryResult["providers"][i]["healthcareProviderTaxonomy_1"] + '</span></p>' + 
							'<p class="result_row"><span class="result_field">' + queryResult["providers"][i]["providerFirstLineBusinessPracticeLocationAddress"] + ', ' +
								(queryResult["providers"][i]["providerSecondLineBusinessPracticeLocationAddress"]==""?(queryResult["providers"][i]["providerSecondLineBusinessPracticeLocationAddress"]):"") + 
								queryResult["providers"][i]["providerBusinessPracticeLocationAddressCityName"] + ", " +
								queryResult["providers"][i]["providerBusinessPracticeLocationAddressStateName"] + ", " + 
								zipCodeParser(queryResult["providers"][i]["providerBusinessPracticeLocationAddressPostalCode"]) + '</span></p>' + 
							'<p class="result_row"><span class="result_field">' + phoneNumberParser(queryResult["providers"][i]["providerBusinessPracticeLocationAddressTelephoneNumber"]) + "</span></p></div></div>");
				}else{
					$("#resultList").append("<div class='provider_record_space'><div class='provider_record_box'><div class='provider_record_header'>"+"" +
							'<p class="result_row"><span class="result_field provider_name_field">' + queryResult["providers"][i]["providerOrganizationName"] + "</span>" +
							'<span class="result_field">[NPI:' + queryResult["providers"][i]["npi"] + ']</span></p>' + 
							'<p class="result_row add_button_space" style="color: black;"><span class="result_field"><button class="btn btn-mini" disabled="true"><span class="icon-plus icon-white"></span></button></span> Provider already added.</p></div>' + 
							'<p class="result_row"><span class="result_field provider_specialty_field">' + queryResult["providers"][i]["healthcareProviderTaxonomy_1"] + '</span></p>' + 
							'<p class="result_row"><span class="result_field">' + queryResult["providers"][i]["providerFirstLineBusinessPracticeLocationAddress"] + ', ' +
								(queryResult["providers"][i]["providerSecondLineBusinessPracticeLocationAddress"]==""?(queryResult["providers"][i]["providerSecondLineBusinessPracticeLocationAddress"]):"") + 
								queryResult["providers"][i]["providerBusinessPracticeLocationAddressCityName"] + ", " +
								queryResult["providers"][i]["providerBusinessPracticeLocationAddressStateName"] + ", " + 
								zipCodeParser(queryResult["providers"][i]["providerBusinessPracticeLocationAddressPostalCode"]) + '</span></p>' + 
							'<p class="result_row"><span class="result_field">' + phoneNumberParser(queryResult["providers"][i]["providerBusinessPracticeLocationAddressTelephoneNumber"]) + "</span></p></div></div>");
				}
			}
			ajaxFinishedFlag++;
			if (ajaxFinishedFlag==2)
				showResult();
			
		});
		}
		
	    function killAjaxCall(){  // if no response, abort both getJson requests
            if( ajaxFinishedFlag == 0 ){
                myAjaxCall1.abort();
                if( myAjaxCall2 ) 
                	myAjaxCall2.abort();
              $("#noResponse").show();
            }
            $("#provider_search_modal .search-loading").fadeOut({ duration: 400});
            }
	 
	
	}

	function showResult(){
		setTimeout( function() { $("#provider_search_modal .search-loading").fadeOut({ duration: 400}); }, 200 );

		if ($("#resultList div.provider_record_space").length > 0){
			$("#resultList").show();
		}
		else{
			$("#noResult").show();
		}
		
	}

	
	function clearLocation(){
		//clear state_name value
		clearState();
		//trigger change event handler for state_name
		$("#state_name").triggerHandler("change");
		
		//clear city_name value
		clearCity();
		//trigger change event handler for city_name
		$("#city_name").triggerHandler("change");
		
		//clear zip_code value
		clearZip();
		//trigger change event handler for zip_code
		$("#zip_code").triggerHandler("propertychange");
	}
	
	function clearName(){
		$("#last_name").val('');
		$("#first_name").val('');
		$("#specialty").val('');
		$("#gender").val('');
		clearPhone();
	}
	
	function clearPhone(){
		$("#phone1").val('');
		$("#phone2").val('');
		$("#phone3").val('');
	}
	
	function clearAll(){
		clearLocation();
		clearName();
		clearPhone();
	}
	
	function clearCity(){
		$("#city_name").val('');
	}
	
	function clearState(){
		$("#state_name").val('');
	}
	
	function clearZip(){
		$("#zip_code").val('');
	}
	
	//enables or disables the state_name & city_name input boxes based on the current value of zip_code input box
	function city_stateEnableDisable(){
    	if($('#zip_code').val().length <= 0){
    		//if zip_code has no value entered, then re-enable the state_name input box
    		$('#state_name').prop('disabled', false);
        }else{
            //if zip_code does not have any value entered, then clear & disable state_name & city_name input boxes
        	clearState();
        	clearCity();
        	$('#state_name').prop('disabled', true);
        	$('#city_name').prop('disabled', true);
        }
    }
	
	
	//enables or disables the city_name input box based on the current selected value of state_name
	function cityEnableDisable(){
    	if($('#state_name').val().length <= 0){
            //if state_name does not have a valid state selected, then clear & disable city_name input box
    		clearCity();
            $('#city_name').prop('disabled', true);
        }else{
            //if state_name has valid state selected, then enable city_name input box
        	$('#city_name').prop('disabled', false);
        }
    }
	
	//enables or disables the zip_code input box based on the current selected value of state_name & city_name
	function zipEnableDisable(){
    	if(($('#state_name').val().length <= "") && ($('#city_name').val() <= "")){
            //if state_name & city name both do not have values, then enable zip_code input box
    		$('#zip_code').prop('disabled', false);
        }else{
            //if state_name or city name have a valid value, then clear and disable zip_code input box
        	clearZip();
            $('#zip_code').prop('disabled', true);
        }
    }
	