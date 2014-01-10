/**********************************************************************************
 * DOCUMENT.READY FUNCTION
**********************************************************************************/
$(document).ready(function() {
	var addConsent_tmp = null;
	var in_addConsent_tmp = $('#input_isAddConsent').val();

	if(in_addConsent_tmp === "true"){
		addConsent_tmp = true;
	}else if(in_addConsent_tmp === "false"){
		addConsent_tmp = false;
	}else{
		addConsent_tmp = null;
	}

	//Check if addConsent is a valid boolean value
	if((addConsent_tmp !== true) && (addConsent_tmp !== false)){
		console.log(addConsent_tmp);
		addConsent_tmp = null;
		throw new ReferenceError("addConsent_tmp variable is not a valid boolean value");
	}

	var specMedSetObj_tmp = null;

	if(addConsent_tmp === false){
		specMedSetObj_tmp = new Array();

		$('.specmedinfo-input').each(function(){
			var str_code = $(this).attr('id');
			var str_codesys = $(this).data('codesys');
			var str_dispname = $(this).data('dispname');

			var newEntry = createSpecMedInfoObj(str_code, str_codesys, str_dispname);
			specMedSetObj_tmp.push(newEntry);
			newEntry = null;
		});
	}

	setupPage(addConsent_tmp, specMedSetObj_tmp);
});

/***********************************************************************************
 * START OF setupPage
************************************************************************************/
function setupPage(addConsent_tmp, specMedSetObj_tmp) {
	var addConsent = addConsent_tmp;
    
	//Check if addConsent is a valid boolean value
	if((addConsent !== true) && (addConsent !== false)){
		addConsent = null;
	    throw new ReferenceError("addConsent variable is not a valid boolean value");
    }
	
	//Set popover 'a' element data attributes based on dynamic hidden form values prior to popup initialization
	$('.input_i-text').each(function(){
		var linkID = $(this).data("linkid");
		var in_content = $(this).attr("value");
		var in_title = $(this).data("intitle");
		
		document.getElementById(linkID).setAttribute("data-content", in_content);
		document.getElementById(linkID).setAttribute("data-title", in_title);
	});
	
	var specMedSetObj = null;
	var specMedSet = null;
	
	if(addConsent === false){
	    specMedSetObj = specMedSetObj_tmp;
	    
	    try{
	    	specMedSet = specMedSetObj;
    	}catch(e){
		    if(e.name == "TypeError"){
		    	specMedSet = null;
			}else{
				throw e;
			}
		}
	}
	
	
	// set providers in consent being edited to be checked
	$('.prov-npi-checked-input').each(function(){
		var prov = $(this).val();
		document.getElementById(prov).checked = true;
	});
	
	// set sensitivity policy codes in consent being edited to be checked
	$('.sensitivity-policy-code-checked-input').each(function(){
		var sens_code = $(this).val();
		document.getElementById(sens_code).checked = true;
	});
	
	// set clinical document section type codes in consent being edited to be checked
	$('.doc-sec-type-code-checked-input').each(function(){
		var docsectyp_code = $(this).val();
		document.getElementById(docsectyp_code).checked = true;
	});
	
	// set clinical document section codes in consent being edited to be checked
	$('.doc-sec-code-checked-input').each(function(){
		var docsec_code = $(this).val();
		document.getElementById(docsec_code).checked = true;
	});
	
	// set purpose of use codes in consent being edited to be checked
	$('.purpose-use-code-checked-input').each(function(){
		var puruse_code = $(this).val();
		document.getElementById(puruse_code).checked = true;
	});
	
	
	
	$('input').iCheck({
		    checkboxClass: 'icheckbox_square-blue',
		    radioClass: 'iradio_square-blue',
		    increaseArea: '20%' // optional
	});
	
	//Initialize page for adding/editing consent
	initAddConsent(addConsent, specMedSet);

	//Initialize popovers
	$('[data-toggle=popover]').popover();
	
	//Close all currently showing popovers when user clicks the page outside of a popover
	$('html').on('mouseup', function(e) {
	    if((!$(e.target).closest('.popover').length)) {
	    	if((!$(e.target).closest('[data-toggle=popover]').length)){
	        	$('.popover-showing').each(function(){
	            	$(this).popover('toggle');
	        	});
			}
	    }
	});
	
	
	
	/* When show.bs.popover event is thrown, close any other visible popovers, then flag triggered
	 * popover element with popover-showing class.
	 * 		show.bs.popover event is thrown immediately when show instance method is called.
	 *   	It will not wait for the popover's CSS transitions to complete first.   */
	$('[data-toggle=popover]').on('show.bs.popover', function(e){
		$('[data-toggle=popover].popover-showing').not(this).popover('toggle'); //all but this
		$(this).addClass('popover-showing');
	});
	
	/* When hide.bs.popover event is thrown, remove popover-showing class from popover element.
	 * 		hide.bs.popover event is thrown immediately when hide instance method is called.
	 *   	It will not wait for the popover's CSS transitions to complete first.   */
	$('[data-toggle=popover]').on('hide.bs.popover', function(e){
		$(this).removeClass('popover-showing');
	});
	
	
	// populate for edit consent page
	$("#authorizers").empty();
	$("#authorize-list input").each(function() {
		if($(this).attr("checked")=="checked"){
			$("#authorizers").append('<li class="uneditable-input"><span class="fa fa-user"></span>'
					+$(this).parent().parent().children("span").text()
					+'</li>');
		}
	});
	$("#consentmadetodisplay").empty();
	$("#disclose-list input").each(function() {
		if($(this).attr("checked")=="checked"){
			$("#consentmadetodisplay").append('<li class="uneditable-input" ><span class="fa fa-user"></span>'
					+$(this).parent().parent().children("span").text()
					+'</li>');
		}
	});

	
	$("body").removeClass('fouc_loading');

	
	$("ul#pulldown_menu").sidebar({
		position:"top",
		open:"click",
		close:"click",
		//labelText:"GUIDE",
		callback: {
        	item : {
            	enter : function(){},
                leave : function(){}
        	},
        	sidebar : {
            	open : function(){},
                close : function(){}
        	}	
    	},
    	inject : $("<div><span>GUIDE</span></div>")
	});


	$("div.sidebar-container").addClass("guide-pulldown-tab");
	$("div.sidebar-inject.top > span").addClass("sidebar-label");
	
	$('#tourtest').joyride({
	    tipContainer: '#consent-add-main',
	    heightPadding: $('footer').outerHeight() + 10,
	    mode: 'focus',
	    autoStart: true,
	    'preStepCallback': function() {
		    var int_next_index = 0;
		    try{
		    	int_next_index = $('#tourtest').joyride('get_next_index');
		    }catch(e){
			    int_next_index = 0;
			}
		    
		    var li_field_id = $('#tourtest li').get(int_next_index);
		    var isShowDelayedNext = $(li_field_id).hasClass('show-delayed-next');
		    var str_field_id = $(li_field_id).data("id");
		    var tempVarIconUser = $('div#' + str_field_id).find('.fa-user');
		    var tempVarBadge = $('div#' + str_field_id).find('.badge');
		    var isDataPopulated = false;

		    if(tempVarIconUser.length > 0 || tempVarBadge.length > 0){
			    isDataPopulated = true;
		    }else{
		    	isDataPopulated = false;
			}

		    var obj_next_tip = $('#tourtest').joyride('get_next_tip');
		    var btn_next_button = $(obj_next_tip).find('.joyride-next-tip');

		    $(btn_next_button).addClass('hidden');
		    
		    if(isDataPopulated || isShowDelayedNext){					    	
		    	setTimeout(function(){
			    	$(btn_next_button).removeClass('hidden');
				}, 500);
		    }
		},
		'postRideCallback': function() {
			setGuideButtonStateOff();
		}
	});
	
	

	if(jQuery.storage.getItem('guideStatus', 'sessionStorage') == 'on'){
		turnGuideOn();
	}else if(jQuery.storage.getItem('guideStatus', 'sessionStorage') == 'off'){
		turnGuideOff();
	}else{
		if($('#btn_guide_OnOff').hasClass('guide-off-flag') === false){
			turnGuideOn();
		}else{
			turnGuideOff();
		}
	}
	
	
	$('#btn_guide_OnOff').click(function() {
		if($('#btn_guide_OnOff').hasClass('guide-off-flag') === true){
			turnGuideOn();
		}else{
			turnGuideOff();
		}
    });
    
	$('#saveauthorizer').click(function() {
		ifGuideOnGoNext();
    });

	$('#saveconsentmadeto').click(function() {
		ifGuideOnGoNext();
    });

    $('#btn_close_share_settings').click(function() {
    	ifGuideOnGoNext();
	});

    $('#btn_save_selected_purposes').click(function() {
    	ifGuideOnGoNext();
	});

	$('#selectInfo').change(function() {				    
	    if($('#selectInfo').prop('checked') == true){
	    	ifGuideOnGoNext();
		}
	});

    $('#selectPurposesToggle').change(function() {				    
	    if($('#selectPurposesToggle').prop('checked') == true){
	    	ifGuideOnGoNext();
		}
	});
    
	$("a[id*=i-icon]").bind('click', function() {
		var id = $(this).attr("id").split('_')[1];
		$("#message-block_"+id).css("display","block");

	});
	
	$("body").on("closeJoyrideClick", function(e){
		e.stopImmediatePropagation();
		
		$('#tourtest').joyride('end');
		turnGuideOff();
	});
	
	
	
	function ifGuideOnGoNext(){
		if(jQuery.storage.getItem('guideStatus', 'sessionStorage') == 'on'){
			$('#tourtest').joyride("go_next");
		}
	}
	
}
/**********************************************************************************
 * END OF setupPage
***********************************************************************************/


/***********************************************************************************
 * FUNCTION TO INITIALIZE PAGE WHEN ADDING/EDITING A CONSENT
***********************************************************************************/
function initAddConsent(addConsent, specMedSet) {
	
				//Check if addConsent is a valid boolean value
				if((addConsent !== true) && (addConsent !== false)){
				    throw new ReferenceError("addConsent variable is not a valid boolean value");
			    }
	
				/*******************************************************************************************
				 * MAIN CODE
				*******************************************************************************************/
	
				var specmedinfoary = new Array();
				var specmedinfoid=0;
				var specmedinfoary_final = new Array();
				var lastInfoState={};
				var lastSpecificMedicalInfoState={};
				var lastPurposeState={};
				var lastProviderState={};
				var isSaveButtonClicked=false;
				
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
					
					var intAryLength = $(specMedSet).length;
					
					for(var i = 0; i < intAryLength; i++){
						initSpecMedInfoArray(specMedSet[i]);
					}
					
					
				}
				
		        $('#date-picker-start').datepicker('setValue',startDate);
		        $('#date-picker-start').attr('value',$('#date-picker-start').attr('value'));
		        $('#date-picker-start').attr('data-date-format',startDate);
			    $('#date-picker-end').datepicker('setValue',endDate);
			    $('#date-picker-end').attr('value',$('#date-picker-end').attr('value'));
			    
			    //Date Picker End
				 
			    //Check if adding or editing consent
			    if (addConsent == true) {
			    	//ADDING CONSENT:
			    	$("#allInfo").iCheck("check");
			    	$("#edit1").hide();
				}else{
					//EDITING CONSENT:
					
					loadAllSharePreferences();
					loadAllPurposeofshareform();
					loadAllLastSpecificMedicalInfoState();
					
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
					}else{
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
				 
				// check all only if page is add consent (not edit)
				if (addConsent == true) {
					uncheckAllSharePreferences(loadAllSharePreferences);
					checkRecommendedPurposeofsharing(loadAllPurposeofshareform);
				}
				else {
					loadAllSharePreferences();
					loadAllPurposeofshareform();
				}
				
				loadAllProviders();
				
				reAppendMediInfoBadges();
				reAppendPurposeOfUse();

					
				/*******************************************************************************************
				 * EVENT HANDLERS
				*******************************************************************************************/
				
				$(".removeEntry").on("click",function(){
						var entryId=$(this).attr("id").substr(11,$(this).attr("id").length-11);
						delete specmedinfoary[entryId];
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
						
						
						for (var i=0;i<specmedinfoary.length;i++){
							if(specmedinfoary[i]!=undefined){
								specmedinfoary_final.push(specmedinfoary[i]);
							}
						}
						
						
						for (var i=0;i<specmedinfoary_final.length;i++){
							if(specmedinfoary_final[i]!=undefined){
								var tempStr = '<input type="text" name="' + specmedinfoary_final[i].codeSystem + '" value="' + specmedinfoary_final[i].code + ";" + specmedinfoary_final[i].description + '" />';
								$('#formToBeSubmitted').append(tempStr.replace(/,/g,"^^^"));
							}
						}
					
						$('#formToBeSubmitted').submit();
						//#ISSUE 138 Fix Start
						// Loop through all forms and reset the checked values by its id or class attributes
						$("form").each(function(e){
							//var idN = this.id;
							//var classN =  $(this).attr('class');							
							if( $(this).attr('id') !== undefined ) {
								clearConsent('#idN');														
							}
							if( $(this).attr('class') !== undefined ) {
								clearConsent('.classN');									
							}	
						});	
						//#ISSUE 138 Fix End						
					}else{
						$('div.navbar-inner-header').after("<div class='validation-alert'><span><div class='alert alert-danger rounded'><button type='button' class='close' data-dismiss='alert'>&times;</button>You must add provider(s).</div></span></div>");
					}
					
					
				});
				
				
				$("#addspecmedi").click(function(){
					updateSpecMedInfo();
				});
				
				
				$("#allInfo").on("ifChecked",function(){
						uncheckAllSharePreferences();
						clearAllSpecificMedicalInfo();
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
				
				
				$("#selectInfo").on("ifChecked",function(){
					$('#edit1').show();
					showShareSettingsModal();
					loadAllSharePreferences();
					uncheckAllMedicalInfo();
				});

				
				$("#btn_save_selected_purposes").click(function(){
					isSaveButtonClicked=true;
					handleLastStoredStates(reAppendPurposeOfUse);
				});
				
				$("#btn_save_selected_medinfo").click(function(){
					isSaveButtonClicked=true;
					handleLastStoredStates(reAppendMediInfoBadges);
					if (areAllInfoUnSelected()==true)
						$("#allInfo").iCheck("check");
				});
				
				$("#btn_cancel_selected_medinfo,#btn_close_selected_medinfo").click(function(){
					setTimeout(function(){
						handleLastStoredStates();
						isSaveButtonClicked=false;
						if (areAllInfoUnSelected()==true){
							$("#allInfo").iCheck("check");
						}
						$('#condition').val("");
					},300);
				});
				
				$("#selected-purposes-modal").on('hide.bs.modal',function(event){
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
				
				$("#authorize-modal,#disclose-modal").on('hide.bs.modal',function(event){
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
							$("#authorizers").append('<li class="uneditable-input"><span class="fa fa-user"></span>'
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
							$("#consentmadetodisplay").append('<li class="uneditable-input"><span class="fa fa-user"></span>'
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
				
				
				
				/******************************************************************************************
				 * FUNCTION DEFINITIONS
				*******************************************************************************************/
				
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
				                    	 description:getDescriptionString(item.Description).replace(/,/g,"^^^"),
				                    	 code:item.Code,
				                         label: getDescriptionString(item.Description),
				                         value:	getDescriptionString(item.Description),
				                     };
				                    
				                 }));
				             } );
				        },
				        search: function() {
				          var term = this.value;
				          if ( term.length < 2 ) {
				            return false;
				          }
				        },
				        
				        select: function( event, ui ) {
				        	var isThisEntryAlreadyEntered=false;
				        	for(key in specmedinfoary){
				        		if (specmedinfoary[key].code==ui.item.code){
				        			isThisEntryAlreadyEntered=true;
				        			return;
				        		}
				        	}
				        	if(isThisEntryAlreadyEntered==false){
				        		addSpecMedInfoToArray(ui.item);
				        	}
				        	
				        }
				      });
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
				
				function getDescriptionString(rawString){
					var fatalErrorFlag = false;
					
					if (rawString.indexOf("(")!=-1 && rawString.substr(rawString.length-1)==")"){
						var num_open_paren = 0;
						var num_close_paren = 0;
						
						var isEqualNum = false;
						
						var strTemp = rawString;
						var subResult = "";
						
						var endPar = 0;
						var openPar = 0;
						
						try{
							endPar = strTemp.lastIndexOf(")");
							openPar = strTemp.lastIndexOf("(", endPar + 1);
							
							subResult = strTemp.substring(openPar, endPar + 1);
						}catch(e){
							fatalErrorFlag = true;
							endPar = 0;
							openPar = 0;
							subResult = "";
							throw e;
						}
						
						num_open_paren = countOpenParen(subResult);
						num_close_paren = countCloseParen(subResult);
						
						if(num_open_paren === num_close_paren){
							isEqualNum = true;
						}
						
						while((num_open_paren !== num_close_paren) && (isEqualNum === false) && (openPar > 0) && (fatalErrorFlag === false)){
							try{
								openPar = strTemp.lastIndexOf("(", openPar - 1);
								subResult = strTemp.substring(openPar, endPar + 1);
							}catch(e){
								fatalErrorFlag = true;
								endPar = 0;
								openPar = 0;
								subResult = "";
								throw e;
							}
							
							num_open_paren = countOpenParen(subResult);
							num_close_paren = countCloseParen(subResult);
							
							if(num_open_paren === num_close_paren){
								isEqualNum = true;
							}
						}
						
						if((isEqualNum === true) && (fatalErrorFlag === false)){
							subResult = subResult.slice(1, subResult.length - 1);
							return  subResult;
						}else if(fatalErrorFlag !== false){
							return "";
						}else{
							return rawString;
						}
					}
					
					return rawString;
				}
				
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
				
				function loadAllLastSpecificMedicalInfoState(){
					clearLastSpecificMedicalInfo();
					for (var i=0;i<specmedinfoary.length;i++){
						if($("#specmedinfo"+i).length==0)
							delete specmedinfoary[i];
						if(specmedinfoary[i]!=undefined){
							lastSpecificMedicalInfoState[specmedinfoary[i].code]=specmedinfoary[i].description.replace(/\^\^\^/g,",");
						}
					}
				}
				
				function loadAllPurposeofshareform(){
					$(".purposeofshareform input").each(function(){
						lastPurposeState[$(this).prop("id")]=$(this).prop("checked");
					});
				}
				
				function uncheckAllSharePreferences(callback){
					$("input.checkBoxClass1").iCheck('uncheck');
					if(typeof callback === 'function')
						callback();
				}
				
				function clearLastSpecificMedicalInfo(){
					for (var key in lastSpecificMedicalInfoState){
						delete lastSpecificMedicalInfoState[key];
					}
				}
				
				function clearAllSpecificMedicalInfo(callback){
					clearLastSpecificMedicalInfo();
					specmedinfoary.length=0;
					$(".removeEntry").each(function(){
						var entryId=$(this).attr("id").substr(11,$(this).attr("id").length-11);
						$("#entry"+entryId).remove();
					});
					
				}
				
				function checkRecommendedPurposeofsharing(callback){
					$("#TREAT").iCheck('check');
					$("#ETREAT").iCheck('check');
					$("#CAREMGT").iCheck('check');
					if(typeof callback === 'function')
						callback();
				}
				
				function uncheckAllMedicalInfo(callback){
					$("input.checkBoxClass1").iCheck('uncheck');
					if(typeof callback === 'function')
						callback();
				}
								
				function reAppendMediInfoBadges(){
					$("#notsharedmainpage").empty();
					for(var key in lastInfoState){
						if (lastInfoState.hasOwnProperty(key)) {
						    if (lastInfoState[key]==true){
						    	var description=$('label[for="' + key + '"]').text();
						    	var toAppendMain='<div id="TagMain'+ 
								key+ 
								'" class="badge">'+
								description+
								"</div>";
								$("#notsharedmainpage").append(toAppendMain);
						    }
						}
					}
					for(var key in lastSpecificMedicalInfoState){
						if (lastSpecificMedicalInfoState.hasOwnProperty(key)) {
						    if (lastSpecificMedicalInfoState[key]!=undefined){
						    	var description=lastSpecificMedicalInfoState[key];
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
						    	var description=$('label[for="' + key + '"]').text();
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
						loadAllLastSpecificMedicalInfoState();
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
					
					for (var key in lastSpecificMedicalInfoState){
						if (key!="")
							if (lastSpecificMedicalInfoState[key]!=undefined){
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
				
				
				function updateSpecMedInfo() {
					if(specmedinfoary[specmedinfoid]!=undefined){
						$("#specmedinfo").append('<li class="spacing" id="'+'entry'+specmedinfoid+
								'"><div><span>'+
								specmedinfoary[specmedinfoary.length-1].displayName+
								'</span><span>'+
								'<button id="specmedinfo'+specmedinfoid +'" class="btn btn-danger btn-xs list-btn removeEntry">'+
								'<span class="fa fa-minus fa-white"></span></button></span></div></li>');
						$("#condition").val("");
						specmedinfoary[specmedinfoid].added=true;
						specmedinfoid=specmedinfoid+1;
					}
				}
				
				function addSpecMedInfoToArray(item){
					 newEntry=new Object();
		        	 newEntry.displayName=item.value;
		        	 newEntry.codeSystem=item.codeSystem;
		        	 newEntry.code=item.code;
		        	 newEntry.description=item.description;
                     specmedinfoary[specmedinfoid]=newEntry;
				}
				
				function initSpecMedInfoArray(item){
					newEntry=new Object();
		        	newEntry.displayName=item.displayName;
		        	newEntry.codeSystem=item.codeSystem;
		        	newEntry.code=item.code;
		        	newEntry.description=item.displayName;
                    specmedinfoary[specmedinfoid]=newEntry;
                    updateSpecMedInfo();
				}
}



/***********************************************************************************
 * GLOBAL SCOPE FUNCTIONS
***********************************************************************************/

function createSpecMedInfoObj(str_code, str_codeSystem, str_displayName){
	newEntry = new Object();
	newEntry.displayName = str_displayName;
	newEntry.codeSystem = str_codeSystem;
	newEntry.code = str_code;	
	return newEntry;
}

function showShareSettingsModal(){
	$("#share-settings-modal").modal({
		  keyboard: false,
		  backdrop: 'static'
	});
}

//Turn Joyride Guide Off
function turnGuideOff(){
	$('#tourtest').joyride('end');
	setGuideButtonStateOff();
}

//Set Joyride Guide Button State Off
function setGuideButtonStateOff(){
	$('#btn_guide_OnOff').addClass('guide-off-flag');
	
	jQuery.storage.setItem('guideStatus', 'off', 'sessionStorage');

	$('#btn_guide_OnOff').text("Guide On");
}

//Turn Joyride Guide On
function turnGuideOn(){
	$('#tourtest').joyride('end');
	$('#tourtest').joyride();
	setGuideButtonStateOn();
}

//Set Joyride Guide Button State On
function setGuideButtonStateOn(){
	if($('#btn_guide_OnOff').hasClass('guide-off-flag') === true){
		$('#btn_guide_OnOff').removeClass('guide-off-flag');
	}

	jQuery.storage.setItem('guideStatus', 'on', 'sessionStorage');
	
	$('#btn_guide_OnOff').text("Guide Off");
}


//Count number of opening parentheses '(' in string
function countOpenParen(in_str){
	//regular expression pattern to specify a global search for the open parenthesis '(' character
	var open_paren_regexp =/\(/g;
	var num_open_paren = 0;
	
	//calls countChar function to count number of occurances of open parentheses
	num_open_paren = countChar(open_paren_regexp, in_str);
	
	return num_open_paren;
}

//Count number of closing parentheses ')' in string
function countCloseParen(in_str){
	//regular expression pattern to specify a global search for the close parenthesis ')' character
	var close_paren_regexp =/\)/g;
	var num_close_paren = 0;
	
	//calls countChar function to count number of occurances of close parentheses
	num_close_paren = countChar(close_paren_regexp, in_str);
	
	return num_close_paren;
}

//Count number of occurances of the character specified by the regular expression pattern
function countChar(in_regexp_patt, in_str){
	var num_char = 0;	
	
	//counts the number of occurances of the character specified by the regular expression pattern
	/*     NOTE: the .match() function returns null if no match is found. Since that would result
	           in the .length() function throwing a TypeError if called on a null value, "||[]"
	           is included after the .match() function call. This causes .match() to return
	           a 0 length array instead of null if no match is found, which allows .length()
	           to return a count of '0' instead of throwing a TypeError. */
	try{
		num_char = (in_str.match(in_regexp_patt)||[]).length;
	}catch(e){
		switch(e.name){
		case "TypeError":
			num_char = 0;
			break;
		default:
			throw e;
			num_char = 0;
			break;
		}
	}
	
	return num_char;
}

//#ISSUE 138 Fix Start
//Resetting the Checked values to avoid saving duplicate consents 
function clearConsent(form){	
	$("form input").each(function(){
		if($(this).prop("checked")==true){
			$(this).prop('checked', false);
		}
	});		
	
}
//#ISSUE 138 Fix End