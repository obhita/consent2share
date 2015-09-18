var path = require('path');
var gruntLess = module.exports = {};

gruntLess.consent2share_bl_dev = {
	files: [{
		expand: true,
		cwd: 'DS4P/consent2share/bl/web-bl/src/main/frontend/less/',
		src: ['custom/*.less', 'modules/*.less'],
		dest: 'DS4P/consent2share/bl/web-bl/src/main/webapp/resources/css/',
		rename: function(dest, src) {
			return path.join(dest, src.replace(/\/less\//g, '/css/'));
		},
		ext: '.css'
	}]
};
