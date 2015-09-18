package com.feisystems.tools.maven;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CLI {
	public static final String TOOL_NAME = "Maven Artifact Cleaner";

	public static final String ARG_HELP = "h";
	public static final String ARG_HELP_LONG = "help";
	public static final String ARG_HELP_DESC = "show help.";

	public static final String ARG_USE_QUALIFIER_AS_BRANCH_ARGNAME = "true/false";
	public static final String ARG_USE_QUALIFIER_AS_BRANCH = "q";
	public static final String ARG_USE_QUALIFIER_AS_BRANCH_LONG = "use-qualifier-as-branch";
	public static final String ARG_USE_QUALIFIER_AS_BRANCH_DESC = "enter true to keep separate versions for each qualifier. default: "
			+ Config.DEFAULT_USE_QUALIFIER_AS_BRANCH;

	public static final String ARG_NUMBER_OF_VERSIONS_TO_KEEP_ARGNAME = "int";
	public static final String ARG_NUMBER_OF_VERSIONS_TO_KEEP = "n";
	public static final String ARG_NUMBER_OF_VERSIONS_TO_KEEP_LONG = "number-of-versions-to-keep";
	public static final String ARG_NUMBER_OF_VERSIONS_TO_KEEP_DESC = "enter the number of versions to keep. default: "
			+ Config.DEFAULT_NUMBER_OF_VERSIONS_TO_KEEP;

	public static final String ARG_ARTIFACT_ARGNAME = "groupdId:artifactId";
	public static final String ARG_ARTIFACT = "a";
	public static final String ARG_ARTIFACT_LONG = "artifact";
	public static final String ARG_ARTIFACT_DESC = "enter artifacts. you can pass this argument multiple times with different values and/or include a whole groupId using a wild card like 'groupId:"
			+ Artifact.WILD_CARD + "'";

	public static final String ARG_MAVEN_REPO_PATH_ARGNAME = "\"absolute path\"";
	public static final String ARG_MEVEN_REPO_PATH = "m";
	public static final String ARG_MEVEN_REPO_PATH_LONG = "maven-repo-path";
	public static final String ARG_MEVEN_REPO_PATH_DESC = "enter the absolute path to the maven repository folder. default: \""
			+ Config.DEFAULT_MAVEN_REPOSITORY_PATH + "\"";

	@SuppressWarnings("static-access")
	public static Config getConfig(String[] args) throws ParseException {
		CommandLineParser parser = new GnuParser();
		Options options = new Options();
		options.addOption(ARG_HELP, ARG_HELP_LONG, false, ARG_HELP_DESC);
		Option artifactOption = OptionBuilder.withArgName(ARG_ARTIFACT_ARGNAME)
				.hasArgs().withDescription(ARG_ARTIFACT_DESC)
				.withLongOpt(ARG_ARTIFACT_LONG).create(ARG_ARTIFACT);
		options.addOption(artifactOption);
		Option useQualifierAsBranchOption = OptionBuilder
				.withArgName(ARG_USE_QUALIFIER_AS_BRANCH_ARGNAME).hasArg()
				.withDescription(ARG_USE_QUALIFIER_AS_BRANCH_DESC)
				.withLongOpt(ARG_USE_QUALIFIER_AS_BRANCH_LONG)
				.create(ARG_USE_QUALIFIER_AS_BRANCH);
		options.addOption(useQualifierAsBranchOption);
		Option numberOfVersionsToKeepOption = OptionBuilder
				.withArgName(ARG_NUMBER_OF_VERSIONS_TO_KEEP_ARGNAME).hasArg()
				.withDescription(ARG_NUMBER_OF_VERSIONS_TO_KEEP_DESC)
				.withLongOpt(ARG_NUMBER_OF_VERSIONS_TO_KEEP_LONG)
				.create(ARG_NUMBER_OF_VERSIONS_TO_KEEP);
		options.addOption(numberOfVersionsToKeepOption);
		Option mavenRepositoryPathOption = OptionBuilder
				.withArgName(ARG_MAVEN_REPO_PATH_ARGNAME).hasArg()
				.withDescription(ARG_MEVEN_REPO_PATH_DESC)
				.withLongOpt(ARG_MEVEN_REPO_PATH_LONG)
				.create(ARG_MEVEN_REPO_PATH);
		options.addOption(mavenRepositoryPathOption);
		try {
			CommandLine cmd = parser.parse(options, args);
			if (cmd.hasOption(ARG_HELP_LONG)) {
				help(options);
			}
			String mavenRepositoryPath = cmd
					.getOptionValue(ARG_MEVEN_REPO_PATH_LONG);
			if (mavenRepositoryPath == null
					|| "".equalsIgnoreCase(mavenRepositoryPath)
					|| !(new File(mavenRepositoryPath).exists())) {
				mavenRepositoryPath = Config.DEFAULT_MAVEN_REPOSITORY_PATH;
			}
			int numberOfVersionsToKeep = cmd
					.hasOption(ARG_NUMBER_OF_VERSIONS_TO_KEEP_LONG) ? Integer
					.parseInt(cmd
							.getOptionValue(ARG_NUMBER_OF_VERSIONS_TO_KEEP_LONG))
					: Config.DEFAULT_NUMBER_OF_VERSIONS_TO_KEEP;
			boolean useQualifierAsBranch = cmd
					.hasOption(ARG_USE_QUALIFIER_AS_BRANCH_LONG) ? Boolean
					.parseBoolean(cmd
							.getOptionValue(ARG_USE_QUALIFIER_AS_BRANCH_LONG))
					: Config.DEFAULT_USE_QUALIFIER_AS_BRANCH;
			String[] artifactsArray = cmd.getOptionValues(ARG_ARTIFACT_LONG);
			List<Artifact> artifacts = new ArrayList<Artifact>();
			if (artifactsArray != null) {
				for (int i = 0; i < artifactsArray.length; i++) {
					artifacts.add(new Artifact(artifactsArray[i]));
				}
			}
			Config config = new Config();
			config.setArtifacts(artifacts);
			config.setNumberOfVersionsToKeep(numberOfVersionsToKeep);
			config.setUseQualifierAsBranch(useQualifierAsBranch);
			config.setMavenRepositoryPath(mavenRepositoryPath);
			config.setArgs(args);

			return config;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static void help(Options options) {
		HelpFormatter formater = new HelpFormatter();
		formater.printHelp(TOOL_NAME, options);
	}
}
