//Code to execute on document.ready event for pages with the footer fragment from common.html included
$(document).ready(function() {
	//var tempDiv = document.getElementById("sidenavMenu");
	if(document.getElementById("sidenavMenu")){
		var curURI = window.location.pathname;
		var endIndex = curURI.indexOf(".html");
		var startIndex = curURI.lastIndexOf("/", endIndex) + 1;
		var curPage = curURI.substring(startIndex, endIndex);

		switch(curPage){
		case "home":
			document.getElementById("sidenav_home").className += " active";
			break;
		case "listConsents":
			document.getElementById("sidenav_consents").className += " active";
			break;
		case "connectionMain":
			document.getElementById("sidenav_providers").className += " active";
			break;
		case "profile":
			document.getElementById("sidenav_profile").className += " active";
			break;
		case "inboxMain":
			document.getElementById("sidenav_msgcenter").className += " active";
			break;
		case "activityHistory":
			document.getElementById("sidenav_activityhist").className += " active";
			break;
		case "medicalinfo":
			document.getElementById("sidenav_meddocs").className += " active";
			break;
		default:
			break;	
		}
	}
});