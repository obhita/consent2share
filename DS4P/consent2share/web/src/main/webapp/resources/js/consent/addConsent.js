
$(document).ready(function() {
				var specmedinfo = new Array();
				var specmedinfoid=0;
				var lastInfoState={};
				var lastPurposeState={};
				var lastProviderState={};
				var isSaveButtonClicked=false;
				$(".removeEntry").live("click",function(){
						var entryId=$(this).attr("id").substr(11,$(this).attr("id").length-11);
						delete specmedinfo[entryId];
						$("#TagSpec"+entryId).remove();
						$("#entry"+entryId).remove();
					});
				
				$(".isMadeToList").on("ifToggled",function(){
					var providerId=$(this).attr("id").substr(2,$(this).attr("id").length-2);
					if($("#from"+providerId).prop('disabled')==false){
						$("#from"+providerId).iCheck('disable');
						$("#from"+providerId).closest('label').addClass("joe");
					}
					else{
						$("#from"+providerId).iCheck('enable');
						$("#from"+providerId).closest('label').removeClass("joe");
					}
				});
				
				$(".toDiscloseList").on("ifToggled",function(){
					var providerId=$(this).attr("id").substr(4,$(this).attr("id").length-4);
					if($("#to"+providerId).prop('disabled')==false){
						$("#to"+providerId).iCheck('disable');
						$("#to"+providerId).closest('label').addClass("joe");
					}
					else{
						$("#to"+providerId).iCheck('enable');
						$("#to"+providerId).closest('label').removeClass("joe");
					}
				});
				
				
				function areAnyProvidersSelected(){
					var flag=0;
					$(".isMadeToList").each(function(){
						if($(this).prop("checked")==true){
							flag++;
							return false;
						}
						return true;
					});
					$(".toDiscloseList").each(function(){
						if($(this).prop("checked")==true){
							flag++;
							return false;
						}
						return true;
					});
					if (flag>1)
						return true;
					else
						return false;
				}
				
				
				$("#consent-add-save").click(function(){
					$('div.validation-alert').empty();
					
					if(areAnyProvidersSelected() === true){
						$(".inputformPerson input").each(function(){
							if($(this).prop("checked")==true){
								$(this).not(':submit').clone().hide().appendTo('#formToBeSubmitted');
							}
						});
						
						$(".purposeofshareform input").each(function(){
							if($(this).prop("checked")==true){
								$(this).not(':submit').clone().hide().appendTo('#formToBeSubmitted');
							}
						});
						
						$(".inputformDate").each(function(){
							$(this).find("input").not(':submit').clone().hide().appendTo('#formToBeSubmitted');
						});
						
						$(".inputform input").each(function(){
							if($(this).prop("checked")==true){
								$(this).not(':submit').clone().hide().appendTo('#formToBeSubmitted');	
							}
						});
						
						
						
						for (var i=0;i<specmedinfo.length;i++){
							if(specmedinfo[i]!=undefined){
								$('#formToBeSubmitted').append('<input type="text" name="'+
										specmedinfo[i].codeSystem+
										'" value="'+
										specmedinfo[i].code+";"+specmedinfo[i].description+
										'" />');
							}
						}
					
						$('#formToBeSubmitted').submit();
						//#ISSUE 138 Fix Start
						// Loop through all forms and reset the checked values by its id or class attributes
						$("form").each(function(e){
							var idN = this.id;
							var classN =  $(this).attr('class');							
							if( $(this).attr('id') !== undefined ) {
								clearConsent('#idN');														
							}
							if( $(this).attr('class') !== undefined ) {
								clearConsent('.classN');									
							}	
						});	
						//#ISSUE 138 Fix End						
					}else{
						$('div.navbar-inner-header').after("<div class='validation-alert'><span><div class='alert alert-error rounded'><button type='button' class='close' data-dismiss='alert'>&times;</button>You must add provider(s).</div></span></div>");
					}
					
					
				});
				
				$("#addspecmedi").click(function(){
					$("#specmedinfo").append('<li class="spacing" id="'+'entry'+specmedinfoid+
							'"><div><span>'+
							specmedinfo[specmedinfo.length-1].displayName+
							'</span><span>'+
							'<button id="specmedinfo'+specmedinfoid +'" class="btn btn-danger btn-mini list-btn removeEntry">'+
							'<span class="icon-minus icon-white"></span></button></span></div></li>');
					$("#condition").val("");
					
					var toAppendMain='<div id="TagSpec'+ 
					specmedinfoid+ 
					'" class="badge">'+
					specmedinfo[specmedinfo.length-1].displayName+
					"</div>";
					$("#notsharedmainpage").append(toAppendMain);
					
					specmedinfoid=specmedinfoid+1;					
				});
				
				//date picker start
				var dateTemp;
				var startDate;
				var endDate;
				
				if (addConsent == true) {				
			        dateTemp = new Date();
			        startDate = new Date(dateTemp.getFullYear(), dateTemp.getMonth(), dateTemp.getDate(), 0, 0, 0, 0);
			        endDate=new Date(dateTemp.getFullYear()+1, dateTemp.getMonth(), dateTemp.getDate(), 0, 0, 0, 0);
				} else {
					
					dateTemp = new Date($('#date-picker-start').attr('value'));
					startDate = new Date(dateTemp.getFullYear(), dateTemp.getMonth(), dateTemp.getDate(), 0, 0, 0, 0);
					dateTemp = new Date($('#date-picker-end').attr('value'));
					endDate =new Date(dateTemp.getFullYear(), dateTemp.getMonth(), dateTemp.getDate(), 0, 0, 0, 0);
				}
				
		        $('#date-picker-start').datepicker('setValue',startDate);
		      //  $('#date-picker-start').datepicker("option","dateFormat","dd/mm/yyyy");
		        $('#date-picker-start').attr('value',$('#date-picker-start').attr('value'));
		        $('#date-picker-start').attr('data-date-format',startDate);
			    $('#date-picker-end').datepicker('setValue',endDate);
			    $('#date-picker-end').attr('value',$('#date-picker-end').attr('value'));

			   //date picker End
				
				 $(function() {
					    $( "#condition" )
					      .autocomplete({appendTo:"#autocomplete"},
					      {source: function( request, response ) {
					          $.getJSON( "callHippaSpace.html", {
					            q: request.term,
					            domain:"icd9",
					            rt:"json"
					          }, function (data) {
					                 response($.map(data.ICD9, function (item) {
					                     return {
					                    	 codeSystem:"ICD9",
					                    	 description:item.Description.replace(",","^^^"),
					                    	 code:item.Code,
					                         label: item.Description.substring(0,item.Description.indexOf("(")),
					                         value:	item.Description.substring(0,item.Description.indexOf("(")),
					                     };
					                    
					                 }));
					             } );
					        },
					        search: function() {
					          // custom minLength
					          var term = this.value;
					          if ( term.length < 2 ) {
					            return false;
					          }
					        },
					        
					        focus: function( event, ui ) {
					        	 newEntry=new Object();
					        	 newEntry.displayName=ui.item.value;
					        	 newEntry.codeSystem=ui.item.codeSystem;
					        	 newEntry.code=ui.item.code;
					        	 newEntry.description=ui.item.description;
			                     specmedinfo[specmedinfoid]=newEntry;
					        }
					      });
					  });
				 
				 if (addConsent == true) {
					$("#allInfo").iCheck("check");
//					 $("#allPurposes").iCheck("check");
					 $("#edit1").hide();
//					 $("#edit2").hide();
				 }
				 // edit consent
				 else {
					 loadAllSharePreferences();
					 loadAllPurposeofshareform();
					 
					 // disable providers in made to list that are checked in to disclose list
					 $(".isMadeToList").each(function(){
						var providerId=$(this).attr("id").substr(2,$(this).attr("id").length-2);
						if($("#from"+providerId).prop('checked')==true){
							$("#to"+providerId).iCheck('disable');
							$("#to"+providerId).closest('label').addClass("joe");
						}
						else{
							$("#to"+providerId).iCheck('enable');
							$("#to"+providerId).closest('label').removeClass("joe");
						}
					 }); 
					 
					 // disable providers in to disclose list that are checked in to made list
					 $(".toDiscloseList").each(function(){
						var providerId=$(this).attr("id").substr(4,$(this).attr("id").length-4);
						if($("#to"+providerId).prop('checked')==true){
							$("#from"+providerId).iCheck('disable');
							$("#from"+providerId).closest('label').addClass("joe");
						}
						else{
							$("#from"+providerId).iCheck('enable');
							$("#from"+providerId).closest('label').removeClass("joe");
						}
					 });
					 
					 if (areAllInfoUnSelected()) {
						 $("#allInfo").iCheck("check");
						 $("#edit1").hide();
					 }
					 else {
						 $("#selectInfo").iCheck("check");

						 $("#sensitivityinfo input").each(function(){
								if ($(this).prop('checked') == true) {
									var toAppendMain='<div id="TagMain'+ 
									$(this).attr('id')+ 
									'" class="badge">'+
									$(this).parent().text()+
									"</div>";
									$("#notsharedmainpage").append(toAppendMain);
								}
							});
							
							$("#medicalinfo input").each(function(){
								if ($(this).prop('checked') == true) {
									var toAppendMain='<div id="TagMain'+ 
									$(this).attr('id')+ 
									'" class="badge">'+
									$(this).parent().text()+
									"</div>";
									$("#notsharedmainpage").append(toAppendMain);
								}
							});
							
							$("#clinicalDocumentType input").each(function(){
								if ($(this).prop('checked') == true) {
									var toAppendMain='<div id="TagMain'+ 
									$(this).attr('id')+ 
									'" class="badge">'+
									$(this).parent().text()+
									"</div>";
									$("#notsharedmainpage").append(toAppendMain);
								}
							});
							
							$("#purposeOfSharingInputs input").each(function(){
								if ($(this).prop('checked') == true) {
									var toAppendMain='<div id="TagMain'+ 
									$(this).attr('id')+ 
									'" class="badge">'+
									$(this).parent().text()+
									"</div>";
									$("#sharedpurpose").append(toAppendMain);
								}
							});
					 }
					 
					 if (areAllPurposesUnselected()) {
						 $("#allPurposes").iCheck("check");
					 }
					 else {
						 $("#selectPurposes").iCheck("check");
					 }
				 }

				
				$("#allInfo").on("ifChecked",function(){
						uncheckAllSharePreferences();
						$("#notsharedmainpage").empty();
						$('#edit1').hide();
						loadAllSharePreferences();
				});

				$("#date-picker-start").datepicker().on('changeDate', function(ev){
					$('#date-picker-start').attr('value',ev.target.value);
		        });

				$("#date-picker-end").datepicker().on('changeDate', function(ev){
					$('#date-picker-end').attr('value',ev.target.value);
		        });				
				
//				$("#allPurposes").on("ifChecked",function(){
//						checkAllPurposeofsharing();
//						$("#sharedpurpose").empty();
//						$('#edit2').hide();
//						loadAllPurposeofshareform();
//				});
				
				$("#selectInfo").on("ifChecked",function(){
					$('#edit1').show();
					showShareSettingsModal();
					loadAllSharePreferences();
					uncheckAllMedicalInfo();
				});
				
//				$("#selectPurposes").on("ifChecked",function(){
//					$('#edit2').show();
//					$("#selected-purposes-modal").modal('show');
//					uncheckAllPurposeofsharing();
//					loadAllPurposeofshareform();
//				});

				// check all only if page is add consent (not edit)
				if (addConsent == true) {
					uncheckAllSharePreferences(loadAllSharePreferences);
					checkRecommendedPurposeofsharing(loadAllPurposeofshareform);
				}
				else {
//					if(!areAllInfoSelected()) {
//						$('#selectInfo').prop('checked', true);
//					}
					loadAllSharePreferences();
					loadAllPurposeofshareform();
					
				}
				
				loadAllProviders();
				
				function loadAllSharePreferences(){
					$(".inputform input").each(function(){
						if($(this).prop("id")!=null)
							lastInfoState[$(this).prop(	"id")]=$(this).prop("checked");
					});
				}
				
				function loadAllProviders(){
					$(".inputformPerson input").each(function(){
						if($(this).prop("id")!=null)
							lastProviderState[$(this).prop("id")]=$(this).prop("checked");
				});
				}
				
				function loadAllPurposeofshareform(){
					$(".purposeofshareform input").each(function(){
						lastPurposeState[$(this).prop("id")]=$(this).prop("checked");
					});
				}
				
				function uncheckAllSharePreferences(callback){
					$(".checkedCheckbox1 input").iCheck('uncheck');
					if(typeof callback === 'function')
						callback();
				}
				
				function checkRecommendedPurposeofsharing(callback){
					$("#TREAT").iCheck('check');
					$("#ETREAT").iCheck('check');
					$("#CAREMGT").iCheck('check');
					if(typeof callback === 'function')
						callback();
				}
				
				function uncheckAllMedicalInfo(callback){
					$(".checkedCheckbox1 input").iCheck('uncheck');
					if(typeof callback === 'function')
						callback();
				}
				
				reAppendPurposeOfUse();
				function reAppendMediInfoBadges(){
					$("#notsharedmainpage").empty();
					for(var key in lastInfoState){
						if (lastInfoState.hasOwnProperty(key)) {
						    if (lastInfoState[key]==true){
						    	var description=$("#"+key).parent().text();
						    	if (description=="")
						    		description=$("#"+key).parent().parent().text();
						    	var toAppendMain='<div id="TagMain'+ 
								key+ 
								'" class="badge">'+
								description+
								"</div>";
								$("#notsharedmainpage").append(toAppendMain);
						    }
						}
					}
				}
				
				
				function reAppendPurposeOfUse(){
					$("#sharedpurpose").empty();
					for(var key in lastPurposeState){
						if (lastPurposeState.hasOwnProperty(key)) {
						    if (lastPurposeState[key]==true){
						    	var description=$("#"+key).parent().text();
						    	if (description=="")
						    		description=$("#"+key).parent().parent().text();
						    	var toAppendMain='<div id="TagMain'+ 
								key+ 
								'" class="badge">'+
								description+
								"</div>";
								$("#sharedpurpose").append(toAppendMain);
						    }
						}
					}
				}
				
				function revertAllStates(){
					for (var key in lastInfoState) {
						  if (lastInfoState.hasOwnProperty(key)) {
						    if (lastInfoState[key]==true)
						    	$("#"+key).iCheck('check');
						    else
						    	$("#"+key).iCheck('uncheck');
						  }
					}
					for (var key in lastPurposeState) {
						  if (lastPurposeState.hasOwnProperty(key)) {
						    if (lastPurposeState[key]==true)
						    	$("#"+key).iCheck('check');
						    else
						    	$("#"+key).iCheck('uncheck');
						  }
					}
					for (var key in lastProviderState) {
						  if (lastProviderState.hasOwnProperty(key)) {
						    if (lastProviderState[key]==true)
						    	$("#"+key).iCheck('check');
						    else
						    	$("#"+key).iCheck('uncheck');
						  }
					}
				}
				
				function handleLastStoredStates(callback){
					if (isSaveButtonClicked==true){
						loadAllProviders();
						loadAllSharePreferences();
						loadAllPurposeofshareform();
						isSaveButtonClicked=false;
					}
					else{
						revertAllStates();
					}
					
					if(typeof callback === 'function')
						callback();
				}
				
				function areAllInfoUnSelected(){
					for (var key in lastInfoState){
						if (key!="")
							if (lastInfoState[key]==true){
								return false;
						}
					}
					return true;
				}
				
				function areAllPurposesUnselected(){
					for (var key in lastPurposeState){
						if (key!="")
							if (lastPurposeState[key]==true){
								return false;
						}
					}
					return true;
				}
				
				$("#btn_save_selected_purposes").click(function(){
					isSaveButtonClicked=true;
					handleLastStoredStates(reAppendPurposeOfUse);
				});
				
				$("#btn_save_selected_medinfo").click(function(){
					isSaveButtonClicked=true;
					handleLastStoredStates(reAppendMediInfoBadges);
				});
				
				$("#btn_cancel_selected_medinfo,#btn_close_selected_medinfo").click(function(){
					setTimeout(function(){
						handleLastStoredStates();
						isSaveButtonClicked=false;
						if (areAllInfoUnSelected()==true)
							$("#allInfo").iCheck("check");
					},300);
				});
				
				$("#selected-purposes-modal").on('hidden',function(event){
					if(event.target.id == "selected-purposes-modal"){
						setTimeout(function(){
							handleLastStoredStates();
							isSaveButtonClicked=false;
							if (areAllPurposesUnselected()==true){
								$("#allPurposes").iCheck("check");
							}
						},300);
					}
				});
				
				$("#authorize-modal,#disclose-modal").on('hidden',function(event){
					if((event.target.id == "authorize-modal") || (event.target.id == "disclose-modal")){
						setTimeout(function(){
							handleLastStoredStates();
							isSaveButtonClicked=false;
						},300);
					}
				});
				
				$("#saveauthorizer").click(function(){
					isSaveButtonClicked=true;
					handleLastStoredStates();
					$("#authorizers").empty();
					$("#authorize-list input").each(function() {
						if($(this).attr("checked")=="checked"){
							$("#authorizers").append('<li class="uneditable-input"><span class="icon-user"></span>'
		 							+$(this).parent().parent().children("span").text()
		 							+'</li>');
						}
					});
				});
				
				$("#saveconsentmadeto").click(function(){
					isSaveButtonClicked=true;
					handleLastStoredStates();
					$("#consentmadetodisplay").empty();
					$("#disclose-list input").each(function() {
						if($(this).attr("checked")=="checked"){
							$("#consentmadetodisplay").append('<li class="uneditable-input" ><span class="icon-user"></span>'
		 							+$(this).parent().parent().children("span").text()
		 							+'</li>');
						}
					});
				});
				
				
				$('button.btn-sel-all-tab').click(function(e){
					var $target = $(e.target);
					
					switch($target.attr('id')){
						case "btn_sensitivity_select_all":
							$('input[name=doNotShareSensitivityPolicyCodes]').iCheck('check');
							break;
						case "btn_med_info_select_all":
							$('input[name=doNotShareClinicalDocumentSectionTypeCodes]').iCheck('check');
							break;
						case "btn_clinical_doc_select_all":
							$('input[name=doNotShareClinicalDocumentTypeCodes]').iCheck('check');
							break;
						case "btn_share_purposes_select_all":
							$('input[name=shareForPurposeOfUseCodes]').iCheck('check');
							break;
						default:
							break;
					}
					
				});
				
				$('button.btn-desel-all-tab').click(function(e){
					var $target = $(e.target);
					
					switch($target.attr('id')){
						case "btn_sensitivity_deselect_all":
							$('input[name=doNotShareSensitivityPolicyCodes]').iCheck('uncheck');
							break;
						case "btn_med_info_deselect_all":
							$('input[name=doNotShareClinicalDocumentSectionTypeCodes]').iCheck('uncheck');
							break;
						case "btn_clinical_doc_deselect_all":
							$('input[name=doNotShareClinicalDocumentTypeCodes]').iCheck('uncheck');
							break;
						case "btn_share_purposes_deselect_all":
							$('input[name=shareForPurposeOfUseCodes]').iCheck('uncheck');
							break;
						default:
							break;
					}
					
				});


});


//#ISSUE 138 Fix Start
//Resetting the Checked values to avoid saving duplicate consents 
function clearConsent(form){	
	$("form input").each(function(){
		if($(this).prop("checked")==true){
			$(this).prop('checked', false);
		}
	});		
	
}
//#ISSUE 138 Fix Start	
