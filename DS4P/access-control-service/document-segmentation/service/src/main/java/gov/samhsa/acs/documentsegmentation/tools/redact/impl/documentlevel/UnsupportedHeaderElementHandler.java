package gov.samhsa.acs.documentsegmentation.tools.redact.impl.documentlevel;

import java.util.List;
import java.util.Set;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import gov.samhsa.acs.common.tool.DocumentAccessor;
import gov.samhsa.acs.documentsegmentation.tools.redact.base.AbstractDocumentLevelRedactionHandler;

public class UnsupportedHeaderElementHandler extends
		AbstractDocumentLevelRedactionHandler {

	private Set<String> headersToRedact;

	private static final String XPATH_HEADERS_PREFIX = "/hl7:ClinicalDocument/hl7:%1";

	public UnsupportedHeaderElementHandler(DocumentAccessor documentAccessor,
			Set<String> c32Headers) {
		super(documentAccessor);
		headersToRedact = c32Headers;
	}

	@Override
	public void execute(Document xmlDocument,
			Set<String> redactSectionCodesAndGeneratedEntryIds,
			List<Node> listOfNodes) throws XPathExpressionException {
		for (String header : headersToRedact) {
			addNodesToList(xmlDocument, listOfNodes,
					redactSectionCodesAndGeneratedEntryIds,
					XPATH_HEADERS_PREFIX, header);
		}
	}

}
