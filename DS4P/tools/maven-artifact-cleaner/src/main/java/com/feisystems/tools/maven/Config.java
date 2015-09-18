package com.feisystems.tools.maven;

import java.util.Arrays;
import java.util.List;

public class Config {
	// Defaults
	public static final int DEFAULT_NUMBER_OF_VERSIONS_TO_KEEP = 5;
	public static final boolean DEFAULT_USE_QUALIFIER_AS_BRANCH = true;
	public static final String DEFAULT_MAVEN_REPOSITORY_PATH = defaultMavenRepositoryPath();

	// Configuration
	private String[] args;
	private String mavenRepositoryPath;
	private int numberOfVersionsToKeep;
	private boolean useQualifierAsBranch;
	private List<Artifact> artifacts;
	public static int DEFAULT_VERSION = -1;

	public Config() {
		this.numberOfVersionsToKeep = DEFAULT_NUMBER_OF_VERSIONS_TO_KEEP;
		this.useQualifierAsBranch = DEFAULT_USE_QUALIFIER_AS_BRANCH;
		this.mavenRepositoryPath = DEFAULT_MAVEN_REPOSITORY_PATH;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	public String getMavenRepositoryPath() {
		return mavenRepositoryPath;
	}

	public void setMavenRepositoryPath(String mavenRepositoryPath) {
		this.mavenRepositoryPath = mavenRepositoryPath;
	}

	public int getNumberOfVersionsToKeep() {
		return numberOfVersionsToKeep;
	}

	public void setNumberOfVersionsToKeep(int numberOfVersionsToKeep) {
		this.numberOfVersionsToKeep = numberOfVersionsToKeep;
	}

	public boolean isUseQualifierAsBranch() {
		return useQualifierAsBranch;
	}

	public void setUseQualifierAsBranch(boolean useQualifierAsBranch) {
		this.useQualifierAsBranch = useQualifierAsBranch;
		DEFAULT_VERSION = useQualifierAsBranch ? -1 : 0;
	}

	public List<Artifact> getArtifacts() {
		return artifacts;
	}

	public void setArtifacts(List<Artifact> artifacts) {
		this.artifacts = artifacts;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\n\nRunning Configuration:\n");
		builder.append("args :");
		builder.append(Arrays.asList(args));
		builder.append("\n");
		builder.append("mavenRepositoryPath: ");
		builder.append(mavenRepositoryPath);
		builder.append("\n");
		builder.append("numberOfVersionsToKeep: ");
		builder.append(numberOfVersionsToKeep);
		builder.append("\n");
		builder.append("useQualifierAsBranch: ");
		builder.append(useQualifierAsBranch);
		builder.append("\n");
		builder.append("artifacts: ");
		builder.append(artifacts);
		builder.append("\n\n");
		return builder.toString();
	}

	private static String defaultMavenRepositoryPath() {
		StringBuilder pathBuilder = new StringBuilder();
		pathBuilder.append(System.getenv("HOME"));
		MavenArtifactCleaner.appendFolderToPath(pathBuilder, ".m2");
		MavenArtifactCleaner.appendFolderToPath(pathBuilder, "repository");
		return pathBuilder.toString();
	}
}
