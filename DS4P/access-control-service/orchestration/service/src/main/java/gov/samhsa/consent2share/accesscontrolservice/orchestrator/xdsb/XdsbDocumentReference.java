package gov.samhsa.consent2share.accesscontrolservice.orchestrator.xdsb;

/**
 * The Class XdsbDocumentReference.
 */
public class XdsbDocumentReference {

	/** The document unique id. */
	private String documentUniqueId;

	/** The repository unique id. */
	private String repositoryUniqueId;

	/**
	 * Instantiates a new xdsb document reference.
	 * 
	 * @param documentUniqueId
	 *            the document unique id
	 * @param repositoryUniqueId
	 *            the repository unique id
	 */
	public XdsbDocumentReference(String documentUniqueId,
			String repositoryUniqueId) {
		super();
		this.documentUniqueId = documentUniqueId;
		this.repositoryUniqueId = repositoryUniqueId;
	}

	/**
	 * Gets the document unique id.
	 * 
	 * @return the document unique id
	 */
	public String getDocumentUniqueId() {
		return documentUniqueId;
	}

	/**
	 * Sets the document unique id.
	 * 
	 * @param documentUniqueId
	 *            the new document unique id
	 */
	public void setDocumentUniqueId(String documentUniqueId) {
		this.documentUniqueId = documentUniqueId;
	}

	/**
	 * Gets the repository unique id.
	 * 
	 * @return the repository unique id
	 */
	public String getRepositoryUniqueId() {
		return repositoryUniqueId;
	}

	/**
	 * Sets the repository unique id.
	 * 
	 * @param repositoryUniqueId
	 *            the new repository unique id
	 */
	public void setRepositoryUniqueId(String repositoryUniqueId) {
		this.repositoryUniqueId = repositoryUniqueId;
	}

	/**
	 * To string.
	 * 
	 * @return the String representation of XdsbDocumentReference in
	 *         'repositoryId:documentUniqueId' format
	 */
	@Override
	public String toString() {
		return repositoryUniqueId + ":" + documentUniqueId;
	}

	/**
	 * Equals.
	 * 
	 * @param anObject
	 *            the an object
	 * @return true if the passed XdsbDocumentReference's documentUniqueId and
	 *         repositoryId is same with this XdsbDocumentReference
	 */
	@Override
	public boolean equals(Object anObject) {
		XdsbDocumentReference xdsbDocumentReference = (XdsbDocumentReference) anObject;
		return documentUniqueId.equals(xdsbDocumentReference
				.getDocumentUniqueId())
				&& repositoryUniqueId.equals(xdsbDocumentReference
						.getRepositoryUniqueId());
	}
}
