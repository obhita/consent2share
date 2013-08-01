
$(document).ready(function() {
				var specmedinfo = new Array();
				var specmedinfoid=0;
				$(".removeEntry").live("click",function(){
						var entryId=$(this).attr("id").substr(11,$(this).attr("id").length-11);
						delete specmedinfo[entryId];
						$("#TagSpec"+entryId).remove();
						$("#entry"+entryId).remove();
					});
				
				$(".isMadeToList").live("click",function(){
					var providerId=$(this).attr("id").substr(2,$(this).attr("id").length-2);
					if($(this).attr("checked")=="checked"){
						$("#from"+providerId).attr("disabled",true);
						$("#from"+providerId).parent().addClass("joe");
					}
					else{
						$("#from"+providerId).attr("disabled",false);
						$("#from"+providerId).parent().removeClass("joe");
					}
				});
				
				$(".toDiscloseList").live("click",function(){
					var providerId=$(this).attr("id").substr(4,$(this).attr("id").length-4);
					if($(this).attr("checked")=="checked"){
						$("#to"+providerId).attr("disabled",true);
						$("#to"+providerId).parent().addClass("joe");
					}
					else{
						$("#to"+providerId).attr("disabled",false);
						$("#to"+providerId).parent().removeClass("joe");
					}
				});

				
				$("#consent-add-save").click(function(){
					
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
						if($(this).prop("checked")==false){
							$(this).prop("checked",true);
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
				
//				date picker
		        var nowTemp = new Date();
		        var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);
		        $('#date-picker-start').datepicker('setValue',now);
		        var oneYearFromNow=new Date(nowTemp.getFullYear()+1, nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);
		        $("#date-picker-end").datepicker('setValue',oneYearFromNow);
				


				
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
				 
				
				checkCheckbox1();
//				checkCheckbox2();
				
				$("#mediInfoToggle").change(function(){
					if ($("#mediInfoToggle").attr("checked")=="checked"){
						checkCheckbox1();
						$("#notsharedmainpage").empty();
					}
						
				});
				
				$("#selectPurposesToggle").change(function(){
					if ($("#selectPurposesToggle").attr("checked")=="checked"){
						checkCheckbox2();
						$("#sharedpurpose").empty();
					}
						
				});
				
				
				
				function checkCheckbox1(){
					$(".checkedCheckbox1 input").prop('checked', true);
				}
				
				function checkCheckbox2(){
					$(".checkedCheckbox2 input").prop('checked', false);
				}
				
				$("#saveauthorizer").click(function(){
					$("#authorizers").empty();
					$("#authorize-list input").each(function() {
						if($(this).attr("checked")=="checked"){
							$("#authorizers").append('<li class="uneditable-input"><span class="icon-user"></span>'
		 							+$(this).parent().text()
		 							+'</li>');
						}
					});
				});
				
				$("#sensitivityinfo input").click(function(){
					var toAppendMain='<div id="TagMain'+ 
					$(this).attr('id')+ 
					'" class="badge">'+
					$(this).parent().text()+
					"</div>";
					
					if($(this).attr("checked")=="checked"){
						$("#TagMain"+$(this).attr('id')).remove();
					}
					else{
						$("#notsharedmainpage").append(toAppendMain);
					}
				});
				
				$("#medicalinfo input").click(function(){
					var toAppendMain='<div id="TagMain'+ 
					$(this).attr('id')+ 
					'" class="badge">'+
					$(this).parent().text()+
					"</div>";
					
					if($(this).attr("checked")=="checked"){
						$("#TagMain"+$(this).attr('id')).remove();
					}
					else{
						$("#notsharedmainpage").append(toAppendMain);
					}
				});
				
				$("#clinicalDocumentType input").click(function(){
					var toAppendMain='<div id="TagMain'+ 
					$(this).attr('id')+ 
					'" class="badge">'+
					$(this).parent().text()+
					"</div>";
					
					if($(this).attr("checked")=="checked"){
						$("#TagMain"+$(this).attr('id')).remove();
					}
					else{
						$("#notsharedmainpage").append(toAppendMain);
					}
				});
				
				$("#purposeOfSharingInputs input").click(function(){
					var toAppendMain='<div id="TagMain'+ 
					$(this).attr('id')+
					'" class="badge">'+
					$(this).parent().text()+
					"</div>";
					
					if($(this).attr("checked")!="checked"){
						$("#TagMain"+$(this).attr('id')).remove();
					}
					else{
						$("#sharedpurpose").append(toAppendMain);
					}
				});
				
				
				
				$("#saveconsentmadeto").click(function(){
					$("#consentmadetodisplay").empty();
					$("#disclose-list input").each(function() {
						if($(this).attr("checked")=="checked"){
							$("#consentmadetodisplay").append('<li class="uneditable-input" ><span class="icon-user"></span>'
		 							+$(this).parent().text()
		 							+'</li>');
						}
					});
				});


});
