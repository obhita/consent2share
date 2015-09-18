var gruntWatch = module.exports = {};

gruntWatch.consent2share_bl_dev = {
	files: ['DS4P/consent2share/bl/web-bl/src/main/frontend/less/custom/*.less',
			'DS4P/consent2share/bl/web-bl/src/main/frontend/less/modules/*.less'], // which files to watch
	tasks: ['newer:less:consent2share_bl_dev'],
	options: {
		nospawn: true
	}
};