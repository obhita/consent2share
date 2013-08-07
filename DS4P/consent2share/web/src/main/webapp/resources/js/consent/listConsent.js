$(document).ready(function() {
			var consentRevokationId=null;
			
			$(".revokation-button").live("click",function(){
				consentRevokationId=$(this).attr("id").substr(6,$(this).attr("id").length-6);
			});
			
			$("#signRevokation").click(function(){
				if(!($("#optionsRadio1").is(':checked')) && !($("#optionsRadio2").is(':checked'))){
					alert ("Please select one option.");
				}
				else{
					$("#consentRevokationForm").append('<input name="consentId" hidden="true" value="'+consentRevokationId+'"/>');
					$("#consentRevokationForm").submit();
				}
				
			});
			
});