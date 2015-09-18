package com.feisystems.tools.maven;

import java.io.File;
import java.util.Scanner;

public class Version {
	private int major;
	private int minor;
	private int revision;
	private String qualifier;
	private File file;

	public Version() {
		super();
		this.major = Config.DEFAULT_VERSION;
		this.minor = Config.DEFAULT_VERSION;
		this.revision = Config.DEFAULT_VERSION;
		this.qualifier = null;
		this.file = null;
	}

	public int getMajor() {
		return major;
	}

	public void setMajor(int major) {
		this.major = major;
	}

	public int getMinor() {
		return minor;
	}

	public void setMinor(int minor) {
		this.minor = minor;
	}

	public int getRevision() {
		return revision;
	}

	public void setRevision(int revision) {
		this.revision = revision;
	}

	public String getQualifier() {
		return qualifier;
	}

	public void setQualifier(String qualifier) {
		this.qualifier = qualifier;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public static Version newVersion(File file) {
		String unparsedVersion = file.getName();
		final String environmentDelimiter = "-";
		final String versionDelimiter = ".";
		int major = Config.DEFAULT_VERSION;
		int minor = Config.DEFAULT_VERSION;
		int revision = Config.DEFAULT_VERSION;
		String truncated = unparsedVersion;
		String qualifier = null;
		if (unparsedVersion.contains(environmentDelimiter)) {
			int environmentDelimiterIndex = unparsedVersion
					.indexOf(environmentDelimiter);
			qualifier = unparsedVersion
					.substring(environmentDelimiterIndex + 1);
			truncated = unparsedVersion.substring(0, environmentDelimiterIndex);
		}
		Scanner versionScanner = new Scanner(truncated.replace(
				versionDelimiter, environmentDelimiter));
		versionScanner.useDelimiter(environmentDelimiter);

		try {
			if (versionScanner.hasNext())
				major = Integer.parseInt(versionScanner.next());
			if (versionScanner.hasNext())
				minor = Integer.parseInt(versionScanner.next());
			if (versionScanner.hasNext())
				revision = Integer.parseInt(versionScanner.next());
		} catch (NumberFormatException e) {
			// do nothing
		}
		versionScanner.close();
		Version version = new Version();
		version.setMajor(major);
		version.setMinor(minor);
		version.setRevision(revision);
		version.setQualifier(qualifier);
		version.setFile(file);
		return version;
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		if (this.major != Config.DEFAULT_VERSION)
			append(b, this.major);
		if (this.minor != Config.DEFAULT_VERSION)
			append(b, this.minor);
		if (this.revision != Config.DEFAULT_VERSION)
			append(b, this.revision);
		if (this.qualifier != null)
			append(b, this.qualifier);
		return b.toString();
	}

	private void append(StringBuilder stringBuilder, int i) {
		stringBuilder.append((stringBuilder.length() > 0 ? "-" : "") + i);
	}

	private void append(StringBuilder stringBuilder, String s) {
		stringBuilder.append((stringBuilder.length() > 0 ? "-" : "") + s);
	}
}
