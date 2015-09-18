package com.feisystems.polrep.service.xacml;

import java.util.Iterator;

import javax.xml.namespace.NamespaceContext;

public class XACMLNamespaceContext {

	public static final String XACML_2_PREFIX = "xacml2";
	public static final String XACML_2_URI = "urn:oasis:names:tc:xacml:2.0:policy:schema:os";

	public static final NamespaceContext CONTEXT = new NamespaceContext() {

		@Override
		public String getNamespaceURI(String prefix) {
			if (XACML_2_PREFIX.equals(prefix)) {
				return XACML_2_URI;
			} else {
				return null;
			}
		}

		@Override
		public String getPrefix(String namespaceURI) {
			throw new UnsupportedOperationException();
		}

		@SuppressWarnings("rawtypes")
		@Override
		public Iterator getPrefixes(String namespaceURI) {
			throw new UnsupportedOperationException();
		}
	};

	private XACMLNamespaceContext() {
	};
}
