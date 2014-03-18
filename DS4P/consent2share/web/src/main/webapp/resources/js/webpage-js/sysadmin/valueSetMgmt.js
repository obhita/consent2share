//Append csrf token to ajax call
$(function(){
	var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
});

//Initialize popovers
function initPopovers(){
	
	$('[data-toggle=popover]').popover();
	
	$('html').on('mouseup', function(e) {
		if((!$(e.target).closest('.popover').length)) {
			if((!$(e.target).closest('[data-toggle=popover]').length)){
				$('.popover').each(function(){
					$(this.previousSibling).popover('hide');
				});
			}
		}
	});
	
}
