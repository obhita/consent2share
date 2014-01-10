$(document).ready(function() {
			$('#normal-toggle-button').toggleButtons();
			$('#info-toggle-button').toggleButtons();
			$('.datepicker').datepicker();

			$('body').removeClass('fouc_loading');

			$("#signnow").click(function() {
				$(this).attr("disabled", "disabled");
			});
			
			var sign_revoke_arys = {
					consentPreSignList: [],
					consentPreRevokeList: []
			};

			initListConsent(sign_revoke_arys);
});

function initSessionTimeoutChecker() {
	function runSessionTimeout() {
		window.location.replace("../resources/j_spring_security_logout");
	}
	
	window.setTimeout(runSessionTimeout, 60000*10);
}

function initConsentPresignStatusChecker (sign_revoke_arys) {
	if (sign_revoke_arys.consentPreSignList.length!=0||sign_revoke_arys.consentPreRevokeList!=0) {
			valueInterval=0;
			
			setInterval(function() {						
				$.ajax({
				url: "listConsents.html/checkConsentStatus",
				type:"GET",
				traditional: true,
				async:true, 
				data: {consentPreSignList:sign_revoke_arys.consentPreSignList,
					consentPreRevokeList:sign_revoke_arys.consentPreRevokeList},
				beforeSend : function(){
					valueInterval=valueInterval+1;
			        if(valueInterval==100)
			        {
			        	window.location.replace("../resources/j_spring_security_logout");
			        }
			    },
				
				success: function (result) { 
				
					if(result.trim()=="true")
						 window.location.replace("listConsents.html");
				}
				});
			}, 1000 * 60 *0.1);
	}
}

function initRevokeModalListeners() {
	var consentRevokationId=null;
				
	$(".revokation-button").live("click",function(){
		consentRevokationId=$(this).attr("id").substr(6,$(this).attr("id").length-6);
	});
	
	$("#signRevokation").click(function(){
		if(!($("#optionsRadio1").is(':checked')) && !($("#optionsRadio2").is(':checked'))){
			alert ("Please select one option.");
		}
		else{
			$("#consentRevokationForm").append('<input name="consentId" style="display: none;" hidden="true" value="'+consentRevokationId+'"/>');
			$("#consentRevokationForm").submit();
		}
		
	});
	
	//Bind hidden.bs.modal event handler to clear modal fields
	$("#revoke-modal").on('hidden.bs.modal', function(){
		document.getElementById("consentRevokationForm").reset();
	});
}


function initListConsent(sign_revoke_arys) {
	initSessionTimeoutChecker();
	
	$('.consent-entry-input').each(function(){
		if($(this).data('consentstage')==1){
			sign_revoke_arys.consentPreSignList.push($(this).val());
		}
		
		if($(this).data('revokestage')==1){
			sign_revoke_arys.consentPreRevokeList.push($(this).val());
		}
	});
	
	initConsentPresignStatusChecker(sign_revoke_arys);
	initRevokeModalListeners();
};





