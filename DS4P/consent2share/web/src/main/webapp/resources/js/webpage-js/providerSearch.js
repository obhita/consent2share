var result4ajaxJSON;

npiLists=new Array();


//Control the triangle in the expandable tags
$(function(){
	
	//Append csrf token to ajax call
	var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
	
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
		var serializedQueryResult=JSON.stringify(result4ajaxJSON["providers"][entryId]);
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
		var serializedQueryResult=JSON.stringify(result4ajaxJSON["providers"][entryId]);
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

jQuery.fn.buildPagingBar = function( arrHtmlStr, items_per_page, func2showPage )
{
	//alert( 'items_per_page = '+ items_per_page );
	var lnkCnt4most = 8 ;
	var lnkCnt4short = 2 ;
	var currentPage = 0 ;
	var txt_prev = "Prev" ;
	var txt_next = "Next" ;
	var ellipsis = "<span>......</span>" ;
	
	return this.each( function() 
	{
		var pageLinksBar = jQuery(this);	
		
		function pageLinkClicked( pageNo, evt ) // event handler for the pagination links
		{
			currentPage = pageNo;
			buildPagingLinks();
			func2showPage( arrHtmlStr, pageNo, items_per_page );
		}
		
		function buildPagingLinks() {	
			pageLinksBar.empty();
			var pageCnt = Math.ceil( arrHtmlStr.length / items_per_page );	
			var halfLinkCnt = Math.ceil(lnkCnt4most/2);
			var startPageLink = currentPage > halfLinkCnt ? Math.max( Math.min( currentPage-halfLinkCnt, pageCnt-lnkCnt4most), 0) : 0 ;
			var endPageLink = currentPage > halfLinkCnt ? Math.min(currentPage+halfLinkCnt, pageCnt) : Math.min( lnkCnt4most, pageCnt );
			
			var pgClickHandler = function( pageNo ) {	return function( evt ) { return pageLinkClicked( pageNo, evt ); }	}
			var addPageLink = function( pageNo, appendopts )
			{
				pageNo = pageNo<0 ? 0 : (pageNo<pageCnt?pageNo:pageCnt-1) ; 
				appendopts = jQuery.extend( {text:pageNo+1, classes:""}, appendopts||{} );
				
				var lnk = ( pageNo == currentPage )
					? jQuery("<span class='currentpage'>"+(appendopts.text)+"</span>")
					: jQuery("<a>"+(appendopts.text)+"</a>").bind("click", pgClickHandler(pageNo) ).attr('href', "#".replace(/__id__/,pageNo)) ; 
				
				if( appendopts.classes)
					lnk.addClass( appendopts.classes );
					
				pageLinksBar.append( lnk );
			}
			
			if( txt_prev && (currentPage > 0 ) )				// add "Previous" link
				addPageLink( currentPage-1, {text:txt_prev, classes:"prev"} );
			
			if (startPageLink > 0 && lnkCnt4short > 0 )			// add starting link
			{
				var end = Math.min( lnkCnt4short, startPageLink );
				for(var i=0; i<end; i++) 
					addPageLink(i);
				
				if( lnkCnt4short < startPageLink )				// add ellipsis
					jQuery( ellipsis ).appendTo( pageLinksBar );
			}
			
			for( var i=startPageLink; i<endPageLink; i++) 		// add interval links
				addPageLink(i);
			
			if (endPageLink < pageCnt && lnkCnt4short > 0)		// add ending link
			{
				if( pageCnt-lnkCnt4short > endPageLink )		// add ellipsis
					jQuery( ellipsis ).appendTo( pageLinksBar );
				
				var begin = Math.max( pageCnt-lnkCnt4short, endPageLink );
				
				for(var i=begin; i<pageCnt; i++) 
					addPageLink(i);
			}
			
			if( txt_next && (currentPage < pageCnt-1 ))			// add "Next" link
				addPageLink( currentPage+1, {text:txt_next, classes:"next"} );
		}
		
		buildPagingLinks();
        func2showPage( arrHtmlStr, currentPage, items_per_page );
	});
}

function lookup (){
	    var providerSearchForm="";
	    var ajaxFinishedFlag=0;

        var arrProviderHtmStr = new Array();
    	var items_per_page = 10 ;
        var rsHtmStrDeli = "<rsHtmStrDeli>" ;
		$("#Pagination").empty();
	    
		$("#resultList").hide();
		$("#noResult").hide();
		$("#noResponse").hide();
		$("#resultList").empty();

		if ($("#city_name").val() != ""){
			providerSearchForm+="&city="+$("#city_name").val();
		}
		if ($("#state_name").val() != ""){
			providerSearchForm+="&usstate="+$("#state_name").val();
		}
		if ($("#zip_code").val() != ""){
			providerSearchForm+="&zipcode="+$("#zip_code").val();
		}
		if ($("#gender").val() != ""){
			var gender=null;
			if ($("#gender").val()=="M")
				gender='MALE';
			if ($("#gender").val()=="F")
				gender="FEMALE";
			providerSearchForm+="&gender="+gender;
		}
		if ($("#specialty").val() != ""){
			providerSearchForm+="&specialty="+$("#specialty").val();
		}
		if ($("#phone1").val() != ""){
			providerSearchForm+="&phone="+$("#phone1").val()+$("#phone2").val()+$("#phone3").val();
		}
		if ($("#first_name").val() != ""){
			providerSearchForm+="&firstname="+$("#first_name").val();
		}
		
		if ($("#last_name").val() != ""){
			providerSearchForm+="&lastname="+$("#last_name").val();
		}
		$("#provider_search_modal .search-loading").show();
	    //alert( providerSearchForm );
		
		setTimeout( killAjaxCall, 10000); 
	    
		var myAjaxCall= $.ajax({
        	dataType: "json",
			url: "providerSearch.html",
			type:"GET",
			async:true, 
			data: providerSearchForm,
			success: function (queryResult) { 
				ajaxFinishedFlag++;
				if(queryResult==null) 
				{
					showResult( queryResult, items_per_page );
					return;
				}
				
				result4ajaxJSON=queryResult;
				
				for (var i=0;i<queryResult["providers"].length;i++) {
					addable="true";
					for(var j=0;j<npiLists.length;j++)
						{

						if(queryResult["providers"][i]["npi"]==npiLists[j])
							addable="false";
						}
					arrProviderHtmStr[i] = getResultRowHtmStr( i, queryResult["providers"][i], addable );
				}
				
				showResult( arrProviderHtmStr, items_per_page );
			}
        
			});
		
		function killAjaxCall(){  // if no response, abort both getJson requests
		    if(ajaxFinishedFlag==0){
		    myAjaxCall.abort();
		    setTimeout( function() { $("#provider_search_modal .search-loading").fadeOut({ duration: 400}); }, 200 );
		    $("#noResponse").show();
		    }
		
	}


}

function getResultRowHtmStr( i, rs, addable )
{
	var showOrg = ( rs["entityType"] == "Organization" );
	var ret = 
	"<div class='provider_record_space'><div class='provider_record_box'><div class='provider_record_header'><p class='result_row'><span class='result_field provider_name_field'>"+
	( showOrg== true  ? rs["providerOrganizationName"] : rs["providerLastName"] +", "+ rs["providerFirstName"] ) +
	'</span><span class="result_field">[NPI:' + rs["npi"] + ']</span></p>'+
	(
		addable=="true" ?	
			(
				showOrg== true  ?	
					'<p class="result_row add_button_space"><span class="result_field"><button class="addOrganizationalProviderButton btn btn-xs btn-success" id="addOrganizationalProviderButton'+i+'"><span class="fa fa-plus"></span></button></span> Add this provider.</p></div>'
				:
					'<p class="result_row add_button_space"><span class="result_field"><button class="addIndividualProviderButton btn btn-xs btn-success" id="addIndividualProviderButton'+i+'"><span class="fa fa-plus"></span></button></span> Add this provider.</p></div>'  
			)
		:
					'<p class="result_row add_button_space" style="color: black;"><span class="result_field"><button class="btn btn-xs" disabled="true"><span class="fa fa-plus"></span></button></span> Provider already added.</p></div>'  
	) +	
	'<p class="result_row"><span class="result_field provider_specialty_field">' + rs["healthcareProviderTaxonomy_1"] + '</span></p>' + 
	'<p class="result_row"><span class="result_field">' + rs["providerFirstLineBusinessPracticeLocationAddress"] + ', ' +
		( rs["providerSecondLineBusinessPracticeLocationAddress"]==""?( rs["providerSecondLineBusinessPracticeLocationAddress"]):"") + 
		rs["providerBusinessPracticeLocationAddressCityName"] + ", " +
		rs["providerBusinessPracticeLocationAddressStateName"] + ", " + 
		zipCodeParser( rs["providerBusinessPracticeLocationAddressPostalCode"]) +'</span></p>'+
	'<p class="result_row"><span class="result_field">' + phoneNumberParser( rs["providerBusinessPracticeLocationAddressTelephoneNumber"]) + "</span></p></div></div>" ;
	return ret ;
}

function showResult( arrHtmlStr, items_per_page )
{
	//if( arrHtmlStr ) alert( 'arrHtmlStr.length = '+ arrHtmlStr.length );
	setTimeout( function() { $("#provider_search_modal .search-loading").fadeOut({ duration: 400}); }, 200 );

    if( arrHtmlStr != null && arrHtmlStr.length > 0)
    {
		$("#Pagination").buildPagingBar( arrHtmlStr, items_per_page, showCurrentPage ); 
		//alert( 'b4 Pagination.show/hide' );
		( arrHtmlStr.length > items_per_page ) ? $("#Pagination").show() : $("#Pagination").hide() ; 
    	$("#resultList").show();
	}
	else{
		$("#Pagination").hide(); 
		$("#noResult").show();
	}
}

function showCurrentPage( arrHtmlStr, page_index, items_per_page )
{
	//alert( 'page_index = '+ page_index );
    var max_elem = Math.min((page_index+1) * items_per_page, arrHtmlStr.length );
    var newcontent = '';
 
    for(var i=page_index*items_per_page; i<max_elem; i++)
        newcontent += ( arrHtmlStr[i] ) ;
 
    $('#resultList').html( newcontent );
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
	$('#empty_client_error_text').attr('style', "display: none;");
}

function clearName(){
	$("#last_name").val('');
	$("#first_name").val('');
	$("#specialty").val('');
	$("#gender").val('');
	clearPhone();
	$('#lname_client_error_text').attr('style', "display: none;");
	$('#fname_client_error_text').attr('style', "display: none;");
	$('#empty_client_error_text').attr('style', "display: none;");
}

function clearPhone(){
	$("#phone1").val('');
	$("#phone2").val('');
	$("#phone3").val('');
	$('#phone_client_error_text').attr('style', "display: none;");
}

function clearAll(){
	clearLocation();
	clearName();
	clearPhone();
	$('#empty_client_error_text').attr('style', "display: none;");
}

function clearCity(){
	$("#city_name").val('');
	$('#city_client_error_text').attr('style', "display: none;");
}

function clearState(){
	$("#state_name").val('');
}

function clearZip(){
	$("#zip_code").val('');
	$('#zip_client_error_text').attr('style', "display: none;");
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
