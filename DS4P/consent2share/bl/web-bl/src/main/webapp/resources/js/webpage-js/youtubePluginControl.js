$(document).ready(function(){
	// 1. This code loads the IFrame Player API code asynchronously.
	var tag = document.createElement('script');
	tag.src = "https://www.youtube.com/player_api";
	var firstScriptTag = document.getElementsByTagName('script')[0];
	firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
	var player;
});

//2. This function creates an <iframe> (and YouTube player)
// after the API code downloads.
function onYouTubePlayerAPIReady() {
	player = new YT.Player('player', {
		height : '315',
		width : '560',
		videoId : 'M7ttn21jVfI',
	});
	
//	Pause the video when modal is hidden
	$('#what-is-a-consent-modal').on('hidden.bs.modal', function() {
		player.pauseVideo();
	});

//	Play the video when model is shown
	$('#what-is-a-consent-modal').on('show.bs.modal', function() {
		player.playVideo();
	});
}
