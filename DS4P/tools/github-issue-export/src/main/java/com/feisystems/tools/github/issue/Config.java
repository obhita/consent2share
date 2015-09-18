package com.feisystems.tools.github.issue;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class Config {
	private String accessToken;
	private String user;
	private String repo;
	private String writeFilePath;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getRepo() {
		return repo;
	}

	public void setRepo(String repo) {
		this.repo = repo;
	}

	public String getWriteFilePath() {
		return writeFilePath;
	}

	public void setWriteFilePath(String writeFilePath) {
		this.writeFilePath = writeFilePath;
	}

	public String getFileAbsolutePath() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM-hh-mm-ss");
		String date = sdf.format(new Date());
		String fileName = "GitHubIssueExport-" + date + ".xlsx";
		return writeFilePath.endsWith("\\") ? writeFilePath + fileName
				: writeFilePath + "\\" + fileName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\n\nRunning Configuration:\n");
		builder.append("accessToken: ");
		builder.append(StringUtils.repeat("*", this.accessToken.length()));
		builder.append("\n");
		builder.append("user: ");
		builder.append(this.user);
		builder.append("\n");
		builder.append("repository: ");
		builder.append(this.repo);
		builder.append("\n");
		builder.append("folder for output file: ");
		builder.append(this.writeFilePath);
		builder.append("\n");
		return builder.toString();
	}
}
