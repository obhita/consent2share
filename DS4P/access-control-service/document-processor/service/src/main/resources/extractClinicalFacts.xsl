<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:sdtc="urn:hl7-org:sdtc"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" exclude-result-prefixes="xs" version="2.0"
    xpath-default-namespace="urn:hl7-org:v3">
    <xsl:output indent="yes" omit-xml-declaration="yes" />
    <xsl:strip-space elements="*"/>
    
    <!-- Extracts clinical facts from the original document. A clinical fact contains a code, a displayName, a code system,
    the title and LOINC code of the section that contains the fact, and the observationId. The fact model also includes the XACML Result from the PDP.
    The fact model is inserted into the working memory of the Drools rules engine. The latter returns a set of directives for segmenting the clinical document.-->

    <xsl:param name="xacmlResult" exclude-result-prefixes="#all"  />
    
    <xsl:template match="/">
        <FactModel xsl:exclude-result-prefixes="#all" >
            <xsl:sequence select="$xacmlResult"/>
            <ClinicalFacts>
                <xsl:for-each select="//observation/value">
                    <xsl:if test="ancestor::entryRelationship[1][@typeCode='SUBJ']">
                        <ClinicalFact>
                            <xsl:call-template name="clinicalFacts" exclude-result-prefixes="#all"/>
                        </ClinicalFact>
                    </xsl:if>
                </xsl:for-each>

                <xsl:for-each
                    select="//substanceAdministration/consumable/manufacturedProduct/manufacturedMaterial/code">

                    <ClinicalFact>
                        <code>
                            <xsl:value-of select="@code"/>
                        </code>
                        <displayName>
                            <xsl:value-of select="@displayName"/>
                        </displayName>
                        <codeSystem>
                            <xsl:value-of select="@codeSystem"/>
                        </codeSystem>
                        <codeSystemName>
                            <xsl:value-of select="@codeSystemName"/>
                        </codeSystemName>
                        <c32SectionTitle>
                            <xsl:value-of select="ancestor::section[1]/title"/>
                        </c32SectionTitle>
                        <c32SectionLoincCode>
                            <xsl:value-of select="ancestor::section[1]/code/@code"/>
                        </c32SectionLoincCode>
                        <observationId>
                            <xsl:value-of select="ancestor::substanceAdministration[1]/id/@root"/>
                        </observationId>
                    </ClinicalFact>

                </xsl:for-each>

                <xsl:for-each select="//organizer//observation/code">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>
                <xsl:for-each select="//organizer//procedure/code">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>
            </ClinicalFacts>
        </FactModel>
    </xsl:template>
    <xsl:template name="clinicalFacts" exclude-result-prefixes="#all" >
        <code>
            <xsl:value-of select="@code"/>
        </code>
        <displayName>
            <xsl:value-of select="@displayName"/>
        </displayName>
        <codeSystem>
            <xsl:value-of select="@codeSystem"/>
        </codeSystem>
        <codeSystemName>
            <xsl:value-of select="@codeSystemName"/>
        </codeSystemName>
        <c32SectionTitle>
            <xsl:value-of select="ancestor::section[1]/title"/>
        </c32SectionTitle>
        <c32SectionLoincCode>
            <xsl:value-of select="ancestor::section[1]/code/@code"/>
        </c32SectionLoincCode>
        <observationId>
            <xsl:value-of select="../id/@root"/>
        </observationId>
    </xsl:template>

</xsl:stylesheet>
