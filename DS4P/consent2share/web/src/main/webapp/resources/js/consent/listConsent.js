

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
					$("#consentRevokationForm").append('<input name="consentId" style="display: none;" hidden="true" value="'+consentRevokationId+'"/>');
					$("#consentRevokationForm").submit();
				}
				
			});
			
			function runSessionTimeout() {
				window.location.replace("../resources/j_spring_security_logout");
			}
		window.setTimeout(runSessionTimeout, 60000*10);
		
 		$(document).ready(function() {
			if (consentPreSignList.length!=0)
				{ valueInterval=0;
				
					setInterval(function() {
						
						$.ajax({
						url: "listConsents.html/checkConsentStatus",
						type:"GET",
						traditional: true,
						async:true, 
						data: {consentPreSignList:consentPreSignList},
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
						})},1000 * 60 *0.1);
							}
			
			})
			
			
});

