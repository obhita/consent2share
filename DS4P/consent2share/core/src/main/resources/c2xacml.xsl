<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" exclude-result-prefixes="xs"
	version="2.0">

	<xsl:output indent="yes" />


	<xsl:template match="/">

		<xsl:variable name="consentId" select="ConsentExport/id" />
		<xsl:variable name="patientId" select="ConsentExport/Patient/email" />
		<xsl:variable name="policyId"
			select="concat('urn:samhsa:names:tc:consent2share:1.0:policyid:', $patientId, ':', $consentId)" />


		<Policy xmlns="urn:oasis:names:tc:xacml:3.0:core:schema:wd-17"
			PolicyId="{$consentId}"
			RuleCombiningAlgId="urn:oasis:names:tc:xacml:3.0:rule-combining-algorithm:deny-overrides"
			Version="1.0">
			<Description>
				This is a reference policy for
				<xsl:value-of select="$patientId" />
			</Description>

			<Target>
				<AnyOf>
					<AllOf>
						<Match MatchId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
							<AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">
								<!--This could also be a patient unique identifer. Using email for 
									now. -->
								<xsl:value-of select="ConsentExport/Patient/email" />
							</AttributeValue>
							<AttributeDesignator MustBePresent="false"
								Category="urn:oasis:names:tc:xacml:3.0:attribute-category:resource"
								AttributeId="urn:oasis:names:tc:xacml:1.0:resource:resource-id"
								DataType="http://www.w3.org/2001/XMLSchema#string"></AttributeDesignator>
						</Match>
					</AllOf>
				</AnyOf>
			</Target>
			<Rule Effect="Permit" RuleId="a07478e8-3642-42ff-980e-911e26ec3f47">
				<Condition>
					<Apply FunctionId="urn:oasis:names:tc:xacml:1.0:function:and">
						<Apply FunctionId="urn:oasis:names:tc:xacml:1.0:function:or">

							<xsl:for-each
								select="//IndividualProvidersPermittedToDiscloseList/IndividualProvidersPermittedToDisclose">

								<Apply FunctionId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
									<Apply
										FunctionId="urn:oasis:names:tc:xacml:1.0:function:string-one-and-only">
										<AttributeDesignator MustBePresent="false"
											Category="urn:oasis:names:tc:xacml:1.0:subject-category:access-subject"
											AttributeId="urn:samhsa:names:tc:consent2share:1.0:subject:provider-npi"
											DataType="http://www.w3.org/2001/XMLSchema#string" />
									</Apply>
									<AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">
										<xsl:value-of select="npi" />
									</AttributeValue>
								</Apply>

							</xsl:for-each>


							<xsl:for-each
								select="//OrganizationalProvidersPermittedToDiscloseList/OrganizationalProvidersPermittedToDisclose">

								<Apply FunctionId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
									<Apply
										FunctionId="urn:oasis:names:tc:xacml:1.0:function:string-one-and-only">
										<AttributeDesignator MustBePresent="false"
											Category="urn:oasis:names:tc:xacml:1.0:subject-category:access-subject"
											AttributeId="urn:samhsa:names:tc:consent2share:1.0:subject:provider-npi"
											DataType="http://www.w3.org/2001/XMLSchema#string" />
									</Apply>
									<AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">
										<xsl:value-of select="npi" />
									</AttributeValue>
								</Apply>

							</xsl:for-each>

						</Apply>
						<Apply FunctionId="urn:oasis:names:tc:xacml:1.0:function:or">

							<xsl:for-each
								select="//IndividualProvidersDisclosureIsMadeToList/IndividualProvidersDisclosureIsMadeTo">
								<Apply FunctionId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
									<Apply
										FunctionId="urn:oasis:names:tc:xacml:1.0:function:string-one-and-only">
										<AttributeDesignator MustBePresent="false"
											Category="urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject"
											AttributeId="urn:samhsa:names:tc:consent2share:1.0:subject:provider-npi"
											DataType="http://www.w3.org/2001/XMLSchema#string" />
									</Apply>
									<AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">
										<xsl:value-of select="npi" />
									</AttributeValue>
								</Apply>

							</xsl:for-each>
							<xsl:for-each
								select="//OrganizationalProvidersDisclosureIsMadeToList/OrganizationalProvidersDisclosureIsMadeTo">
								<Apply FunctionId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
									<Apply
										FunctionId="urn:oasis:names:tc:xacml:1.0:function:string-one-and-only">
										<AttributeDesignator MustBePresent="false"
											Category="urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject"
											AttributeId="urn:samhsa:names:tc:consent2share:1.0:subject:provider-npi"
											DataType="http://www.w3.org/2001/XMLSchema#string" />
									</Apply>
									<AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">
										<xsl:value-of select="npi" />
									</AttributeValue>
								</Apply>


							</xsl:for-each>

						</Apply>

						<Apply FunctionId="urn:oasis:names:tc:xacml:1.0:function:or">

							<xsl:for-each
								select="//shareForPurposeOfUseCodesList/shareForPurposeOfUseCodes">

								<Apply FunctionId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
									<Apply
										FunctionId="urn:oasis:names:tc:xacml:1.0:function:string-one-and-only">
										<AttributeDesignator MustBePresent="false"
                                        Category="urn:oasis:names:tc:xacml:1.0:subject-category:purpose-of-use"
                                        AttributeId="urn:samhsa:names:tc:consent2share:1.0:purpose-of-use-code"
                                        DataType="http://www.w3.org/2001/XMLSchema#string"/>
									</Apply>
									<AttributeValue
                                        DataType="http://www.w3.org/2001/XMLSchema#string">
                                        <xsl:value-of select="code"/>
                                    </AttributeValue>
								</Apply>


							</xsl:for-each>
						</Apply>


						<Apply FunctionId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
							<Apply
								FunctionId="urn:oasis:names:tc:xacml:1.0:function:string-one-and-only">
								<AttributeDesignator MustBePresent="false"
									Category="urn:oasis:names:tc:xacml:3.0:attribute-category:action"
									AttributeId="urn:oasis:names:tc:xacml:1.0:action:action-id"
									DataType="http://www.w3.org/2001/XMLSchema#string" />
							</Apply>
							<AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">write</AttributeValue>
						</Apply>



						<xsl:for-each select="//consentStart">




							<Apply
								FunctionId="urn:oasis:names:tc:xacml:1.0:function:dateTime-greater-than-or-equal">
								<Apply
									FunctionId="urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only">
									<AttributeDesignator MustBePresent="false"
										Category="urn:oasis:names:tc:xacml:3.0:attribute-category:environment"
										AttributeId="urn:oasis:names:tc:xacml:1.0:environment:current-dateTime"
										DataType="http://www.w3.org/2001/XMLSchema#dateTime" />
								</Apply>
								<AttributeValue DataType="http://www.w3.org/2001/XMLSchema#dateTime">
									<xsl:value-of select="." />
								</AttributeValue>
							</Apply>

						</xsl:for-each>



						<xsl:for-each select="//consentEnd">
							<Apply
								FunctionId="urn:oasis:names:tc:xacml:1.0:function:dateTime-less-than-or-equal">
								<Apply
									FunctionId="urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only">
									<AttributeDesignator MustBePresent="false"
										Category="urn:oasis:names:tc:xacml:3.0:attribute-category:environment"
										AttributeId="urn:oasis:names:tc:xacml:1.0:environment:current-dateTime"
										DataType="http://www.w3.org/2001/XMLSchema#dateTime" />
								</Apply>
								<AttributeValue DataType="http://www.w3.org/2001/XMLSchema#dateTime">
									<xsl:value-of select="." />
								</AttributeValue>
							</Apply>

						</xsl:for-each>


					</Apply>
				</Condition>

			</Rule>
			<ObligationExpressions>

				<xsl:for-each
					select="//doNotShareClinicalDocumentSectionTypeCodesList/doNotShareClinicalDocumentSectionTypeCodes">
					<ObligationExpression
						ObligationId="urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code"
						FulfillOn="Permit">
						<AttributeAssignmentExpression
							AttributeId="urn:oasis:names:tc:xacml:3.0:example:attribute:text">
							<AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">
								<xsl:value-of select="code" />
							</AttributeValue>
						</AttributeAssignmentExpression>
					</ObligationExpression>
				</xsl:for-each>

				<xsl:for-each
					select="//doNotShareSensitivityPolicyCodesList/doNotShareSensitivityPolicyCodes">
					<ObligationExpression
						ObligationId="urn:samhsa:names:tc:consent2share:1.0:obligation:redact-sensitivity-code"
						FulfillOn="Permit">
						<AttributeAssignmentExpression
							AttributeId="urn:oasis:names:tc:xacml:3.0:example:attribute:text">
							<AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">
								<xsl:value-of select="code" />
							</AttributeValue>
						</AttributeAssignmentExpression>
					</ObligationExpression>
				</xsl:for-each>


				<xsl:for-each
					select="//doNotShareClinicalConceptCodesList/doNotShareClinicalConceptCodes">
					<ObligationExpression
						ObligationId="urn:samhsa:names:tc:consent2share:1.0:obligation:redact-clinical-concept-code"
						FulfillOn="Permit">
						<AttributeAssignmentExpression
							AttributeId="urn:oasis:names:tc:xacml:3.0:example:attribute:text">
							<AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">
								<xsl:value-of select="code" />
							</AttributeValue>
						</AttributeAssignmentExpression>
					</ObligationExpression>
				</xsl:for-each>

			</ObligationExpressions>
		</Policy>

	</xsl:template>


</xsl:stylesheet>