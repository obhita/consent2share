package com.feisystems.polrep.service.xacml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class XACMLNamespaceContextTest {

	@Test
	public void testXACMLNamespaceContextInitialized() {
		assertNotNull(XACMLNamespaceContext.CONTEXT);
		assertEquals(XACMLNamespaceContext.XACML_2_URI,
				XACMLNamespaceContext.CONTEXT
						.getNamespaceURI(XACMLNamespaceContext.XACML_2_PREFIX));
	}
}
