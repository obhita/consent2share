package com.feisystems.tools.maven;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.cli.ParseException;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MavenArtifactCleaner {
	private final static Logger logger = LoggerFactory
			.getLogger(MavenArtifactCleaner.class);

	/**
	 * Maven Artifact Cleaner tool with CLI.
	 * 
	 * @param args
	 *            see -h or -help for usage details
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void main(String[] args) throws IOException, ParseException {
		// Load configuration
		Config config = null;
		try {
			config = CLI.getConfig(args);
			logger.info(config.toString());
			if (config.getArtifacts().size() == 0) {
				logger.info("Please see help (-h or -help) for the usage of this tool...");
			}
		} catch (Exception e) {
			logger.error("Error occured:");
			logger.error(e.getMessage(), e);
			logger.error("Cannot load the configuration, stopping the tool...");
			System.exit(1);
		}
		// For each artifact passed in the arguments
		for (Artifact artifact : config.getArtifacts()) {
			// Find groupId folder
			StringBuilder pathBuilder = new StringBuilder();
			pathBuilder.append(config.getMavenRepositoryPath());
			String groupId = artifact.getGroupId();
			appendFolderToPath(pathBuilder, groupId.replace(".", "\\"));
			String groupdIdAbsolutePath = buildEscapedPath(pathBuilder);
			File groupFolder = new File(groupdIdAbsolutePath);

			Map<String, HashMap<String, ArrayList<Version>>> versionFoldersToClean = new HashMap<String, HashMap<String, ArrayList<Version>>>();
			// Find/Filter requested artifact folders
			for (File artifactFolder : groupFolder.listFiles(FileFilterFactory
					.createFileFilterForArtifact(artifact))) {
				// Version folders in an artifact folder
				File[] versionFolders = artifactFolder
						.listFiles(FileFilterFactory.DIRECTORY_FILTER);
				HashMap<String, ArrayList<Version>> versions = mapToVersionsByQualifier(
						versionFolders, config.isUseQualifierAsBranch());
				// Collect version folders to be cleaned
				versionFoldersToClean.put(artifactFolder.getAbsolutePath(),
						versions);
			}
			// Log version folders to be cleaned
			logVersionFoldersToClean(versionFoldersToClean);
			for (String artifactFolderAbsolutePath : versionFoldersToClean
					.keySet()) {
				HashMap<String, ArrayList<Version>> qualifierToVersionsMap = versionFoldersToClean
						.get(artifactFolderAbsolutePath);
				// if config.isUseQualifierAsBranch()==false, there will be only
				// 1 qualifier ("*")
				for (String qualifier : qualifierToVersionsMap.keySet()) {
					ArrayList<Version> versions = qualifierToVersionsMap
							.get(qualifier);
					// Recursively delete the files/folders starting from the
					// beginning of version list.
					if (versions.size() > config.getNumberOfVersionsToKeep()) {
						int deleteBeforeIndex = versions.size()
								- config.getNumberOfVersionsToKeep();
						for (int i = 0; i < deleteBeforeIndex; i++) {
							// Recursive method to delete folders with all files
							// in it.
							delete(versions.get(i).getFile());
						}
					}
				}
			}
		}
	}

	public static String buildEscapedPath(StringBuilder pathBuilder) {
		return StringEscapeUtils.escapeJava(pathBuilder.toString());
	}

	public static void appendFolderToPath(StringBuilder pathBuilder,
			String folder) {
		pathBuilder.append("\\");
		pathBuilder.append(folder);
	}

	/**
	 * Recursive delete method that deletes a folder its all contents.
	 * 
	 * @param file
	 *            If this is just a file, it deletes the file. If this is a
	 *            folder, it recursively deletes the folder with everything in
	 *            it.
	 * @throws IOException
	 */
	private static void delete(File file) throws IOException {
		if (file.isDirectory()) {
			// directory is empty, then delete it
			if (file.list().length == 0) {
				file.delete();
				logger.info("Directory is deleted : " + file.getAbsolutePath());
			} else {
				// list all the directory contents
				String files[] = file.list();

				for (String temp : files) {
					// construct the file structure
					File fileDelete = new File(file, temp);

					// recursive delete
					delete(fileDelete);
				}

				// check the directory again, if empty then delete it
				if (file.list().length == 0) {
					file.delete();
					logger.info("Directory is deleted : "
							+ file.getAbsolutePath());
				}
			}
		} else {
			// if file, then delete it
			file.delete();
			logger.info("File is deleted : " + file.getAbsolutePath());
		}
	}

	/**
	 * Logs the discovered and filtered version folders.
	 * 
	 * @param map
	 *            versionFoldersToClean
	 */
	private static void logVersionFoldersToClean(
			Map<String, HashMap<String, ArrayList<Version>>> map) {
		for (String key : map.keySet()) {
			for (String key2 : map.get(key).keySet()) {
				logger.info(key + "\t" + map.get(key).get(key2));
			}
		}
	}

	/**
	 * If useQualifierAsBranch==true, this method returns a map from qualifiers
	 * (key) to version folders (value). If useQualifierAsBranch==false, this
	 * method returns a map with only one key ("*") with all version folders as
	 * the value.
	 * 
	 * @param versionFolders
	 * @param useQualifierAsBranch
	 * @return
	 */
	private static HashMap<String, ArrayList<Version>> mapToVersionsByQualifier(
			File[] versionFolders, boolean useQualifierAsBranch) {

		HashMap<String, ArrayList<Version>> map = new HashMap<String, ArrayList<Version>>();
		for (File file : versionFolders) {
			if (!file.getName().contains("$")) {
				Version version = Version.newVersion(file);
				ArrayList<Version> list = new ArrayList<Version>();
				// If useQualifierAsBranch=true, collect the version folders by
				// qualifier; Else collect all version folders in the same list
				// with map key="*".
				String qualifier = useQualifierAsBranch ? version
						.getQualifier() : "*";
				if (map.containsKey(qualifier)) {
					list = map.get(qualifier);
				} else {
					map.put(qualifier, list);
				}

				list.add(version);
			}
		}
		// For each version list (there will be only 1 list if
		// useQualifierAsBranch==false), sort them accordingly from lower
		// version to higher version (so we can start deleting folders from
		// beginning of the list).
		for (String version : map.keySet()) {
			Collections.sort(
					map.get(version),
					useQualifierAsBranch ? VersionComparatorFactory
							.createHandleQualifiersSeparatelyComparator()
							: VersionComparatorFactory
									.createDefaultComparator());
		}

		return map;
	}
}
