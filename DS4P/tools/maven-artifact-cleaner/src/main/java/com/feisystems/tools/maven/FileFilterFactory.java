package com.feisystems.tools.maven;

import java.io.File;
import java.io.FileFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileFilterFactory {
	private final static Logger logger = LoggerFactory
			.getLogger(FileFilterFactory.class);

	/**
	 * FileFilter implementation that only accepts directories.
	 */
	public static FileFilter DIRECTORY_FILTER = new FileFilter() {
		@Override
		public boolean accept(File pathname) {
			boolean accepted = pathname.isDirectory();
			logPathname(pathname, accepted);
			return accepted;
		}
	};

	/**
	 * FileFilter implementation that only accepts the directory name with given
	 * artifact's artifactId.
	 * 
	 * @param artifact
	 *            the artifact folder that is desired to be selected
	 */
	public static FileFilter createFileFilterForArtifact(final Artifact artifact) {
		return new FileFilter() {
			final Artifact a = artifact;

			@Override
			public boolean accept(File pathname) {
				boolean accepted = a.isWildCard() ? pathname.isDirectory()
						: pathname.isDirectory()
								&& pathname.getName().equals(a.getArtifactId());
				logPathname(pathname, accepted);
				return accepted;
			}
		};
	}

	private static void logPathname(File pathname, boolean accepted) {
		StringBuilder builder = new StringBuilder();
		builder.append(accepted ? "ACCEPTED: " : "REJECTED: ");
		builder.append(pathname.getAbsolutePath());
		logger.info(builder.toString());
	}
}
