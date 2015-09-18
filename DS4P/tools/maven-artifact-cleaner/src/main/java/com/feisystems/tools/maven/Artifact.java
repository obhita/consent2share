package com.feisystems.tools.maven;

public class Artifact {
	public static final String WILD_CARD = "*";
	private String groupId;
	private String artifactId;

	public Artifact(String combined) {
		super();
		if (!combined.contains(":") || combined.contains(" ")) {
			throw new RuntimeException(combined
					+ " is not in a valid 'groupId:artifactId' form.");
		}
		this.groupId = combined.substring(0, combined.indexOf(":"));
		this.artifactId = combined.substring(combined.indexOf(":") + 1);
		if (groupId.contains(WILD_CARD)) {
			throw new RuntimeException("groupId cannot contain wild card.");
		}
		if (artifactId.contains(WILD_CARD) && artifactId.length() > 1) {
			throw new RuntimeException(
					"Wild card cannot be used for partial match in artifactId.");
		}
	}

	public Artifact(String groupId, String artifactId) {
		super();
		this.groupId = groupId;
		this.artifactId = artifactId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	public boolean isWildCard() {
		return WILD_CARD.equals(artifactId);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.groupId);
		builder.append(":");
		builder.append(this.artifactId);
		return builder.toString();
	}
}
