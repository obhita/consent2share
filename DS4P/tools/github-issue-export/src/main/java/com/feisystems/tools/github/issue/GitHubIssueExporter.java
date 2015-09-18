package com.feisystems.tools.github.issue;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHIssue.Label;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GitHubIssueExporter {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final String SHEET_NAME = "GitHubIssues";
	public static final String COL0 = "Issue";
	public static final String COL1 = "Title";
	public static final String COL2 = "AssigneeLoginName";
	public static final String COL3 = "Labels";
	public static final String COL4 = "Status";
	public static final String COL5 = "Link";

	private Map<Integer, String> cols;

	public static void main(String[] args) throws IOException {
		GitHubIssueExporter g = new GitHubIssueExporter();
		g.run(args);
	}

	public GitHubIssueExporter() {
		logger.info("Initializing...");
		cols = new HashMap<Integer, String>();
		cols.put(0, COL0);
		cols.put(1, COL1);
		cols.put(2, COL2);
		cols.put(3, COL3);
		cols.put(4, COL4);
		cols.put(5, COL5);
	}

	private void run(String[] args) throws IOException {
		// Load config
		logger.info("Loading the configuration...");
		Config config = CLI.getConfig(args);
		logger.info(config.toString());

		// Connect to GitHub
		logger.info("Connecting to GitHub...");
		GitHub gitHub = GitHub.connectUsingOAuth(config.getAccessToken());

		// Get the repository
		logger.info("Getting the repository...");
		GHRepository repository = gitHub.getUser(config.getUser())
				.getRepository(config.getRepo());

		// Set up workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(SHEET_NAME);
		AtomicInteger counter = new AtomicInteger(0);

		// Create column headers
		XSSFRow headerRow = sheet.createRow(counter.getAndIncrement());
		createCellAndSetValue(headerRow, 0, cols.get(0));
		createCellAndSetValue(headerRow, 1, cols.get(1));
		createCellAndSetValue(headerRow, 2, cols.get(2));
		createCellAndSetValue(headerRow, 3, cols.get(3));
		createCellAndSetValue(headerRow, 4, cols.get(4));
		createCellAndSetValue(headerRow, 5, cols.get(5));

		// Fetch and add issues to the workbook/worksheet
		logger.info("Fetching the OPEN issues...");
		addIssuesToSheet(repository, sheet, counter, GHIssueState.OPEN);
		logger.info("Fetching the CLOSED issues...");
		addIssuesToSheet(repository, sheet, counter, GHIssueState.CLOSED);

		logger.info("Total number of fetched issues: "
				+ (workbook.getSheetAt(0).getPhysicalNumberOfRows() - 1));

		// Auto-size columns
		for (int colIndex : cols.keySet()) {
			sheet.autoSizeColumn(colIndex);
		}

		// Write workbook to the file
		String fileName = config.getFileAbsolutePath();
		logger.info("Writing the file: " + fileName);
		FileOutputStream fos = new FileOutputStream(fileName);
		BufferedOutputStream bos = new BufferedOutputStream(fos, 8 * 1024);
		workbook.write(bos);
		bos.close();
		logger.info("Done...");
	}

	private void addIssuesToSheet(GHRepository repository, XSSFSheet sheet,
			AtomicInteger counter, GHIssueState state) throws IOException {
		for (GHIssue issue : repository.getIssues(state)) {
			// Create new row
			XSSFRow row = sheet.createRow(counter.getAndIncrement());

			// Get values
			String issueNumber = Integer.toString(issue.getNumber());
			String issueTitle = issue.getTitle();
			String issueAssigneeLoginName = issue.getAssignee() != null ? issue
					.getAssignee().getLogin() : null;
			String issueLabels = issue.getLabels() != null ? toString(issue
					.getLabels()) : null;
			String issueState = state.toString();
			String issueUrl = issue.getUrl().toString();

			// Create cells and set values
			createCellAndSetValue(row, 0, issueNumber);
			createCellAndSetValue(row, 1, issueTitle);
			createCellAndSetValue(row, 2, issueAssigneeLoginName);
			createCellAndSetValue(row, 3, issueLabels);
			createCellAndSetValue(row, 4, issueState);
			createCellAndSetValue(row, 5, issueUrl);
		}
	}

	private void createCellAndSetValue(XSSFRow row, int columnIndex,
			String value) {
		row.createCell(columnIndex);
		row.getCell(columnIndex).setCellValue(value);
	}

	private String toString(Collection<Label> labels) {
		StringBuilder builder = new StringBuilder();
		for (Label label : labels) {
			if (builder.length() > 0) {
				builder.append(";");
			}
			builder.append(label.getName());
		}
		return builder.toString();
	}
}
