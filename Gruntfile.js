var _ = require('lodash');

var C2S_bl_less = require('./DS4P/consent2share/bl/consent2share-bl-less.js');
var C2S_pg_less = require('./DS4P/consent2share/pg/consent2share-pg-less.js');

var C2S_bl_watch = require('./DS4P/consent2share/bl/consent2share-bl-watch.js');
var C2S_pg_watch = require('./DS4P/consent2share/pg/consent2share-pg-watch.js');

var gruntConf = {};

gruntConf.less = {};
gruntConf.watch = {};

_.extend(gruntConf.less, C2S_bl_less);
_.extend(gruntConf.less, C2S_pg_less);

_.extend(gruntConf.watch, C2S_bl_watch);
_.extend(gruntConf.watch, C2S_pg_watch);

module.exports = function(grunt) {
	require('jit-grunt')(grunt);

	// Project configuration.
	grunt.initConfig(gruntConf);
	
	grunt.registerTask('default', ['newer:less', 'watch']);
};
