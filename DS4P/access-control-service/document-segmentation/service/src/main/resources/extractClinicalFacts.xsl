<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:sdtc="urn:hl7-org:sdtc"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" exclude-result-prefixes="xs" version="2.0"
    xpath-default-namespace="urn:hl7-org:v3" xmlns:voc="urn:hl7-org:v3/voc">
    <xsl:output indent="yes" omit-xml-declaration="yes"/>
    <xsl:strip-space elements="*"/>

    <!-- Extracts clinical facts from the original document. A clinical fact contains a code, a displayName, a code system,
    the title and LOINC code of the section that contains the fact, and the observationId. The fact model also includes the XACML Result from the PDP.
    The fact model is inserted into the working memory of the Drools rules engine. The latter returns a set of directives for segmenting the clinical document.-->

    <xsl:variable name="xacmlResult" select="document('xacmlResult')"/>

    <xsl:template match="/">
        <FactModel xsl:exclude-result-prefixes="#all">
            <xsl:sequence select="$xacmlResult"/>

            <ClinicalFacts>

                <xsl:variable name="entry"
                    select="/ClinicalDocument/component/structuredBody/component/section/entry"/>

                <xsl:for-each select="$entry/act/entryRelationship/observation/value">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each
                    select="$entry/act/entryRelationship/observation/entryRelationship/observation/value">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each
                    select="$entry/act/entryRelationship/observation/entryRelationship/observation/entryRelationship/observation/value">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each
                    select="$entry/act/entryRelationship/observation/value/qualifier/value">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each select="$entry/procedure/code/qualifier/value">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each select="$entry/observation/value">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each
                    select="$entry/substanceAdministration/entryRelationship/observation/value">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each select="$entry/act/code">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each select="$entry/act/entryRelationship/act/code">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each
                    select="$entry/act/entryRelationship/act/performer/assignedEntity/code">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each
                    select="$entry/act/entryRelationship/act/participant/participantRole/code">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each select="$entry/act/entryRelationship/observation/code">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each
                    select="$entry/act/entryRelationship/observation/entryRelationship/observation/code">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each
                    select="$entry/act/entryRelationship/observation/entryRelationship/observation/entryRelationship/observation/code">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each select="$entry/observation/code">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each select="$entry/observation/subject/relatedSubject/code">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each
                    select="$entry/act/entryRelationship/observation/participant/participantRole/playingEntity/code">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each
                    select="$entry/substanceAdministration/consumable/manufacturedProduct/manufacturedMaterial/code">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each
                    select="$entry/substanceAdministration/entryRelationship/observation/code">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each select="$entry/organizer/code">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each select="$entry/organizer/component/observation/code">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each select="$entry/procedure/code">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each select="$entry/supply/participant/participantRole/playingDevice/code">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each
                    select="$entry/act/entryRelationship/act/entryRelationship/act/entryRelationship/procedure/code">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each
                    select="$entry/act/entryRelationship/act/participant/participantRole/code">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each select="$entry/substanceAdministration/code">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each select="$entry/act/entryRelationship/act/entryRelationship/act/code">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each
                    select="$entry/act/entryRelationship/observation/entryRelationship/organizer/component/observation/code">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each
                    select="$entry/act/entryRelationship/observation/entryRelationship/organizer/component/observation/value">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each select="$entry/encounter/code">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each select="$entry/observation/entryRelationship/observation/code">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each
                    select="$entry/organizer/component/observation/author/assignedAuthor/assignedAuthoringDevice/code">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each select="$entry/organizer/component/observation/value">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each select="$entry/substanceAdministration/entryRelationship/act/code">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each select="$entry/substanceAdministration/entryRelationship/supply/code">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each
                    select="$entry/substanceAdministration/entryRelationship/supply/entryRelationship/act/code">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each
                    select="$entry/substanceAdministration/participant/participantRole/code">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each
                    select="$entry/substanceAdministration/participant/participantRole/playingEntity/code">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each select="$entry/substanceAdministration/precondition/criterion/code">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

            </ClinicalFacts>
            <!--<xsl:result-document href="c32-out.xml">-->
            <EmbeddedClinicalDocument xsl:exclude-result-prefixes="#all" xmlns="urn:hl7-org:v3">
                <xsl:call-template name="clinicalDocument"/>
                <xsl:call-template name="entry"/>
            </EmbeddedClinicalDocument>
            <!--</xsl:result-document>-->
        </FactModel>

    </xsl:template>


    <xsl:template name="clinicalFacts" exclude-result-prefixes="#all">
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

        <entry>
            <xsl:value-of select="generate-id(ancestor::entry)"/>
        </entry>

    </xsl:template>

    <xsl:template name="clinicalDocument" match="@*|node()">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
        </xsl:copy>
    </xsl:template>
    <xsl:template name="entry" match="entry">
        <xsl:copy>
            <xsl:apply-templates select="@*"/>
            <xsl:apply-templates select="realmCode"/>
            <xsl:apply-templates select="typeId"/>
            <xsl:apply-templates select="templateId"/>
            <xsl:apply-templates select="act"/>
            <xsl:apply-templates select="encounter"/>
            <xsl:apply-templates select="observation"/>
            <xsl:apply-templates select="observationMedia"/>
            <xsl:apply-templates select="organizer"/>
            <xsl:apply-templates select="procedure"/>
            <xsl:apply-templates select="regionOfInterest"/>
            <xsl:apply-templates select="substanceAdministration"/>
            <xsl:apply-templates select="supply"/>
            <generatedEntryId xsl:exclude-result-prefixes="#all" xmlns="urn:hl7-org:v3">
                <xsl:value-of select="generate-id(.)"/>
            </generatedEntryId>
        </xsl:copy>
    </xsl:template>

</xsl:stylesheet>
