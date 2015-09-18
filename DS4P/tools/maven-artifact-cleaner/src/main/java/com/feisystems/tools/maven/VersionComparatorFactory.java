package com.feisystems.tools.maven;

import java.util.Comparator;

import org.apache.commons.lang.StringUtils;

public class VersionComparatorFactory {

	/**
	 * @see "Default Version comparison definition" at
	 *      http://docs.codehaus.org/display
	 *      /MAVEN/Dependency+Mediation+and+Conflict+Resolution
	 */
	public static Comparator<Version> createDefaultComparator() {
		return new Comparator<Version>() {
			@Override
			public int compare(Version o1, Version o2) {
				int majorDiff = o1.getMajor() - o2.getMajor();
				int minorDiff = o1.getMinor() - o2.getMinor();
				int revisionDiff = o1.getRevision() - o2.getRevision();
				int qualifierDiff;
				if (o1.getQualifier() == null && o2.getQualifier() != null)
					qualifierDiff = Integer.MAX_VALUE;
				else if (o1.getQualifier() != null && o2.getQualifier() == null)
					qualifierDiff = Integer.MIN_VALUE;
				else if (o1.getQualifier() == null && o2.getQualifier() == null)
					qualifierDiff = 0;
				else if (!StringUtils.isNumeric(o1.getQualifier())
						|| !StringUtils.isNumeric(o2.getQualifier()))
					qualifierDiff = o1.getQualifier().compareToIgnoreCase(
							o2.getQualifier());
				else
					qualifierDiff = Integer.parseInt(o1.getQualifier())
							- Integer.parseInt(o2.getQualifier());
				if (majorDiff > 0)
					return Integer.MAX_VALUE;
				else if (minorDiff > 0)
					return Integer.MAX_VALUE;
				else if (revisionDiff > 0)
					return Integer.MAX_VALUE;
				else if (qualifierDiff > 0)
					return Integer.MAX_VALUE;
				else if (majorDiff == 0 && minorDiff == 0 && revisionDiff == 0
						&& qualifierDiff == 0)
					return 0;
				else
					return Integer.MIN_VALUE;
			}
		};
	}

	/**
	 * This Comparator is designed to compare the custom versioning pattern used
	 * by C2S Team.
	 */
	public static Comparator<Version> createHandleQualifiersSeparatelyComparator() {
		return new Comparator<Version>() {
			@Override
			public int compare(Version o1, Version o2) {
				int majorDiff = o1.getMajor() - o2.getMajor();
				int minorDiff = o1.getMinor() - o2.getMinor();
				int revisionDiff = o1.getRevision() - o2.getRevision();
				if (majorDiff > 0)
					return Integer.MAX_VALUE;
				else if (minorDiff > 0)
					return Integer.MAX_VALUE;
				else if (revisionDiff > 0)
					return Integer.MAX_VALUE;
				else if (majorDiff == 0 && minorDiff == 0 && revisionDiff == 0)
					return 0;
				else
					return Integer.MIN_VALUE;
			}
		};
	}
}
