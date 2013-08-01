var individualJSON;
var organizationalJSON;



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
		$("#resultTable").hide();
		$("#noResult").hide();
		
		
		$("#resultTableBody").empty();
		$("#searchedby").empty();
		if ($("#city_name").val() != ""){
			lookupQueryIndividual+="/city/"+$("#city_name").val();
			$("#searchedby").show();
			$("#searchedby").append('<span class="badge">City Name</span>');
		}
		if ($("#state_name").val() != ""){
			lookupQueryIndividual+="/usstate/"+$("#state_name").val();
			$("#searchedby").show();
			$("#searchedby").append('<span class="badge">State Name</span>');
		}
		if ($("#zip_code").val() != ""){
			lookupQueryIndividual+="/zipcode/"+$("#zip_code").val();
			$("#searchedby").show();
			$("#searchedby").append('<span class="badge">Zip Code</span>');
		}
		if ($("#gender").val() != ""){
			var gender=null;
			if ($("#gender").val()=="M")
				gender='MALE';
			if ($("#gender").val()=="F")
				gender="FEMALE";
			lookupQueryIndividual+="/gender/"+gender;
			$("#searchedby").show();
			$("#searchedby").append('<span class="badge">Gender</span>');
			organizationSearchFlag=0;
		}
		if ($("#specialty").val() != ""){
			lookupQueryIndividual+="/specialty/"+$("#specialty").val();
			$("#searchedby").show();
			$("#searchedby").append('<span class="badge">Specialty</span>');
		}
		if ($("#phone1").val() != ""){
			lookupQueryIndividual+="/phone/"+$("#phone1").val()+$("#phone2").val()+$("#phone3").val();
			$("#searchedby").show();
			$("#searchedby").append('<span class="badge">Phone Number</span>');
		}
		if ($("#first_name").val() != ""){
			lookupQueryIndividual+="/firstname/"+$("#first_name").val();
			$("#searchedby").show();
			$("#searchedby").append('<span class="badge">First Name</span>');
			organizationSearchFlag=0;
		}
		
		var lookupQueryOrg=lookupQueryIndividual;
		
		if ($("#last_name").val() != ""){
			lookupQueryIndividual+="/lastname/"+$("#last_name").val();
			lookupQueryOrg+="/orgname/"+$("#last_name").val();
			$("#searchedby").show();
			$("#searchedby").append('<span class="badge">Last Name/Facility Name</span>');
		}
		
		
		$.getJSON(lookupQueryIndividual+"/entitytype/Individual",function(queryResult){	
			individualJSON=queryResult;
			for (var i=0;i<queryResult["providers"].length;i++)
				$("#resultTableBody").append("<tr>"+"" +
						'<td><button class="addIndividualProviderButton btn btn-mini btn-success"'+'id="addIndividualProviderButton'+i+'"><span class="icon-plus icon-white"></span></button></td>'+
						"<td>"+queryResult["providers"][i]["providerLastName"]+", "+
						queryResult["providers"][i]["providerFirstName"]+"</td>"+
						"<td>"+queryResult["providers"][i]["healthcareProviderTaxonomy_1"]+"</td>"+
						"<td>"+	queryResult["providers"][i]["npi"]+"</td>"+
						'<td nowrap="nowrap">'+	queryResult["providers"][i]["providerFirstLineBusinessPracticeLocationAddress"]+"</br>"+
								(queryResult["providers"][i]["providerSecondLineBusinessPracticeLocationAddress"]==""?(queryResult["providers"][i]["providerSecondLineBusinessPracticeLocationAddress"]):"")+
								queryResult["providers"][i]["providerBusinessPracticeLocationAddressCityName"]+", "+
								queryResult["providers"][i]["providerBusinessPracticeLocationAddressStateName"]+", "+
								zipCodeParser(queryResult["providers"][i]["providerBusinessPracticeLocationAddressPostalCode"])+
						'<td nowrap="nowrap">'+	phoneNumberParser(queryResult["providers"][i]["providerBusinessPracticeLocationAddressTelephoneNumber"])+"</td></tr>");	
			ajaxFinishedFlag++;
			if (ajaxFinishedFlag==2)
				showResult();
			else if (organizationSearchFlag==0)
				showResult();
		});
		
		if (organizationSearchFlag==1){
		$.getJSON(lookupQueryOrg+"/entitytype/Organization",function(queryResult){
			organizationalJSON=queryResult;
			for (var i=0;i<queryResult["providers"].length;i++)
				$("#resultTableBody").append("<tr>"+""+
						'<td><button class="addOrganizationalProviderButton btn btn-mini btn-success"'+'id="addOrganizationalProviderButton'+i+'"><span class="icon-plus icon-white"></span></button></td>'+
						"<td>"+queryResult["providers"][i]["providerOrganizationName"]+"</td>"+
						"<td>"+queryResult["providers"][i]["healthcareProviderTaxonomy_1"]+"</td>"+
						"<td>"+	queryResult["providers"][i]["npi"]+"</td>"+
						'<td nowrap="nowrap">'+	queryResult["providers"][i]["providerFirstLineBusinessPracticeLocationAddress"]+"</br>"+
								(queryResult["providers"][i]["providerSecondLineBusinessPracticeLocationAddress"]==""?(queryResult["providers"][i]["providerSecondLineBusinessPracticeLocationAddress"]):"")+
								queryResult["providers"][i]["providerBusinessPracticeLocationAddressCityName"]+", "+
								queryResult["providers"][i]["providerBusinessPracticeLocationAddressStateName"]+", "+
								zipCodeParser(queryResult["providers"][i]["providerBusinessPracticeLocationAddressPostalCode"])+
						'<td nowrap="nowrap">'+	phoneNumberParser(queryResult["providers"][i]["providerBusinessPracticeLocationAddressTelephoneNumber"])+"</td></tr>");
						
			ajaxFinishedFlag++;
			if (ajaxFinishedFlag==2)
				showResult();
		});
		}
		
	}

	function showResult(){
		if ($("#resultTable tr").length>2){
			$("#resultTable").show();
		}
		else{
			$("#noResult").show();
		}
	}

	
	function clearLocation(){
		$("#city_name").val('');
		$("#state_name").val('');
		$("#zip_code").val('');
	}
	
	function clearName(){
		$("#last_name").val('');
		$("#first_name").val('');
		$("#specialty").val('');
		$("#gender").val('');
		$("#phone").val('');
	}
	
	function clearAll(){
		clearLocation();
		clearName();
	}
