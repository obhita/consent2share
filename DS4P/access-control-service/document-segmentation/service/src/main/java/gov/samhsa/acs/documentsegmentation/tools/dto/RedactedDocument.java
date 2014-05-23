package gov.samhsa.acs.documentsegmentation.tools.dto;

import java.util.Set;

public class RedactedDocument {
	private String redactedDocument;
	private Set<String> redactedSectionSet;
	private Set<String> redactedCategorySet;
	public RedactedDocument(String redactedDocument,
			Set<String> redactedSectionSet, Set<String> redactedCategorySet) {
		super();
		this.redactedDocument = redactedDocument;
		this.redactedSectionSet = redactedSectionSet;
		this.redactedCategorySet = redactedCategorySet;
	}
	public String getRedactedDocument() {
		return redactedDocument;
	}
	public void setRedactedDocument(String redactedDocument) {
		this.redactedDocument = redactedDocument;
	}
	public Set<String> getRedactedSectionSet() {
		return redactedSectionSet;
	}
	public void setRedactedSectionSet(Set<String> redactedSectionSet) {
		this.redactedSectionSet = redactedSectionSet;
	}
	public Set<String> getRedactedCategorySet() {
		return redactedCategorySet;
	}
	public void setRedactedCategorySet(Set<String> redactedCategorySet) {
		this.redactedCategorySet = redactedCategorySet;
	}	
}
