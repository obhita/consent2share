<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:sdtc="urn:hl7-org:sdtc"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" exclude-result-prefixes="xs" version="2.0"
    xpath-default-namespace="urn:hl7-org:v3" xmlns:ds4p="http://www.siframework.org/ds4p">
    
    <!-- Implements document, section, and entry level confidentiality tagging per DS4P implementation guide. -->
    
    <xsl:output indent="yes"/>
    
    <!--Retrieves Drools rules execution response, parses it, and returns its document node-->
    <xsl:variable name="ruleExecutionResponseContainer" select="document('ruleExecutionResponseContainer')"/>  
    
    <!-- DOCUMENT LEVEL CONFIDENTIALITY CODE -->
    <!--Get most restrictive entry-level confidentiality code in the execution response container. This returns a double-->
    <xsl:variable name="documentConfidentialityCodeNumeric"
        select="max(ds4p:mapConfCodeFromChartoNumber($ruleExecutionResponseContainer//impliedConfSection[following-sibling::itemAction != 'REDACT']))"/>
    
    <!--Map the most restrictive entry-level confidentiality code in the execution response container to V, R, or N-->
    <xsl:variable name="documentConfidentialityCode" select="ds4p:mapConfCodeFromNumtoChar($documentConfidentialityCodeNumeric)"/>
    
    <!-- SECTION LEVEL CONFIDENTIALITY CODE: PROBLEMS -->
    <!--Get most restrictive entry-level confidentiality code in the problems section. This returns a double-->
    <xsl:variable name="problemsSectionConfidentialityCodeNumeric"
        select="max(ds4p:mapConfCodeFromChartoNumber($ruleExecutionResponseContainer//impliedConfSection[preceding-sibling::c32SectionLoincCode='11450-4'  and following-sibling::itemAction != 'REDACT']))"/>
    
    <!--Map the most restrictive entry-level confidentiality code in the problems section to V, R, or N-->    
    <xsl:variable name="problemsSectionConfidentialityCode"
        select="ds4p:mapConfCodeFromNumtoChar($problemsSectionConfidentialityCodeNumeric)"/>
    
    <!-- SECTION LEVEL CONFIDENTIALITY CODE: MEDICATIONS -->
    <!--Get most restrictive entry-level confidentiality code in the medications section. This returns a double-->
    <xsl:variable name="medicationsSectionConfidentialityCodeNumeric"
        select="max(ds4p:mapConfCodeFromChartoNumber($ruleExecutionResponseContainer//impliedConfSection[preceding-sibling::c32SectionLoincCode='10160-0' and following-sibling::itemAction != 'REDACT']))"/>
    
    <!--Map the most restrictive entry-level confidentiality code in the medications section to V, R, or N-->    
    <xsl:variable name="medicationsSectionConfidentialityCode"
        select="ds4p:mapConfCodeFromNumtoChar($medicationsSectionConfidentialityCodeNumeric)"/>
    
    <!-- SECTION LEVEL CONFIDENTIALITY CODE: ALLERGIES -->
    <!--Get most restrictive entry-level confidentiality code in the problems section. This returns a double-->
    <xsl:variable name="allergiesSectionConfidentialityCodeNumeric"
        select="max(ds4p:mapConfCodeFromChartoNumber($ruleExecutionResponseContainer//impliedConfSection[preceding-sibling::c32SectionLoincCode='48765-2'  and following-sibling::itemAction != 'REDACT']))"/>
    
    <!--Confidentiality codes for allergies and labs not implemented yet. Defaults to R-->
    <xsl:variable name="allergiesSectionConfidentialityCode"
        select="ds4p:mapConfCodeFromNumtoChar($allergiesSectionConfidentialityCodeNumeric)"/>
    
    <!-- SECTION LEVEL CONFIDENTIALITY CODE: LAB RESULTS -->
    <xsl:variable name="labResultsSectionConfidentialityCodeNumeric"
        select="max(ds4p:mapConfCodeFromChartoNumber($ruleExecutionResponseContainer//impliedConfSection[preceding-sibling::c32SectionLoincCode='30954-2'  and following-sibling::itemAction != 'REDACT']))"/>
    
    <xsl:variable name="labResultsSectionConfidentialityCode"
        select="ds4p:mapConfCodeFromNumtoChar($labResultsSectionConfidentialityCodeNumeric)"/>
    
    <!-- mypolicies.xacml is just a default. Dynamic parameters values can be passed in at runtime -->
    <xsl:param name="privacyPoliciesExternalDocUrl" as="xs:string" select="'mypolicies.xacml'"/>
    
    <!-- FUNCTIONS -->
    <!--This function maps a numeric confidentiality code to V, N, or R-->
    <xsl:function name="ds4p:mapConfCodeFromNumtoChar" as="xs:string">
        <xsl:param name="confidentialityCodeNumeric" />
        <xsl:choose>
            <xsl:when test="$confidentialityCodeNumeric = 3">V</xsl:when>
            <xsl:when test="$confidentialityCodeNumeric = 2">R</xsl:when>
            <xsl:when test="$confidentialityCodeNumeric = 1">N</xsl:when>
            <xsl:otherwise>N</xsl:otherwise>
        </xsl:choose>
    </xsl:function>
    
    <!--This function maps a char (V, N, or R) confidentiality code to numeric.-->
    <xsl:function name="ds4p:mapConfCodeFromChartoNumber" as="xs:integer">
        <xsl:param name="confidentialityCodeChar" />
        <xsl:choose>
            <xsl:when test="$confidentialityCodeChar = 'V'">3</xsl:when>
            <xsl:when test="$confidentialityCodeChar = 'R'">2</xsl:when>
            <xsl:when test="$confidentialityCodeChar = 'N'">1</xsl:when>
            <xsl:otherwise>1</xsl:otherwise>
        </xsl:choose>
    </xsl:function>
    
    <xsl:function name="ds4p:mapConfCodeFromChartoText" as="xs:string">
        <xsl:param name="confidentialityCodeChar" />
        <xsl:choose>
            <xsl:when test="$confidentialityCodeChar = 'V'">Very Restricted</xsl:when>
            <xsl:when test="$confidentialityCodeChar = 'R'">Restricted</xsl:when>
            <xsl:when test="$confidentialityCodeChar = 'N'">Normal</xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$confidentialityCodeChar"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:function>
    
    <xsl:function name="ds4p:mapDocObligationPolicyFromCodetoText" as="xs:string">
        <xsl:param name="docObligationPolicyFromCode" />
        <xsl:choose>
            <xsl:when test="$docObligationPolicyFromCode = 'ENCRYPT'">Encrypt information</xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$docObligationPolicyFromCode"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:function>
    
    <xsl:function name="ds4p:mapDocRefrainPolicyFromCodetoText" as="xs:string">
        <xsl:param name="docRefrainPolicyFromCode" />
        <xsl:choose>
            <xsl:when test="$docRefrainPolicyFromCode = 'NORDSLCD'">Prohibition on redisclosure without patient consent directive</xsl:when>
            <xsl:when test="$docRefrainPolicyFromCode = 'NODSCLCD'">Prohibition on disclosure without patient consent directive</xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$docRefrainPolicyFromCode"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:function>    
    
    <!--This function gets the implied confidentiality and sensitivity for the given observation -->
    <xsl:function name="ds4p:getConfidentialityAndSensitivityCode" as="xs:string">
        <xsl:param name="observationCode" />
        <xsl:value-of select="concat(':', $ruleExecutionResponseContainer//impliedConfSection[preceding-sibling::code=$observationCode], ':', $ruleExecutionResponseContainer//sensitivity[preceding-sibling::code=$observationCode])"/>
    </xsl:function>
    
    <xsl:function name="ds4p:getConfidentialityCode" as="xs:string">
        <xsl:param name="observationCode" />
        <xsl:variable name="confCodes" select="$ruleExecutionResponseContainer//impliedConfSection[preceding-sibling::code=$observationCode]"/>
        <xsl:choose>
            <xsl:when test="$confCodes">
                <xsl:value-of select="ds4p:mapConfCodeFromNumtoChar(max(ds4p:mapConfCodeFromChartoNumber($ruleExecutionResponseContainer//impliedConfSection[preceding-sibling::code=$observationCode])))"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$confCodes"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:function>
    
    <xsl:function name="ds4p:getDocumentObligationCode" as="xs:string">
        <xsl:param name="observationCode" />
        <xsl:variable name="temp" select="$ruleExecutionResponseContainer//documentObligationPolicy[preceding-sibling::code=$observationCode]"/>
        <xsl:value-of select="$temp[1]"/>
    </xsl:function>
    
    <xsl:function name="ds4p:getDocumentRefrainCode" as="xs:string">
        <xsl:param name="observationCode" />
        <xsl:variable name="temp" select="$ruleExecutionResponseContainer//documentRefrainPolicy[preceding-sibling::code=$observationCode]"/>
        <xsl:value-of select="$temp[1]"/>
    </xsl:function>
    
    <!-- TEMPLATES -->
    <xsl:template match="@*|node()">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
        </xsl:copy>
    </xsl:template>
    
    <!--Document-level ConfidentialityCode code-->
    <xsl:template match="ClinicalDocument/confidentialityCode">
        <confidentialityCode code="{$documentConfidentialityCode}"
            codeSystem="2.16.840.1.113883.5.25" xmlns="urn:hl7-org:v3"/>
    </xsl:template>
    
    <!--Section-level confidentialityCode code is already specified in C32-->
    <xsl:template match="section/confidentialityCode">
        <xsl:choose>
            <!--Problems section-level documentConfidentialityCode code-->
            <xsl:when test="../code[@code='11450-4']">
                <confidentialityCode code="{$problemsSectionConfidentialityCode}"
                    codeSystem="2.16.840.1.113883.5.25" xmlns="urn:hl7-org:v3"/>
            </xsl:when>
            
            <!--Allergies section-level documentConfidentialityCode code-->
            <xsl:when test="../code[@code='48765-2']">
                <confidentialityCode code="{$allergiesSectionConfidentialityCode}"
                    codeSystem="2.16.840.1.113883.5.25" xmlns="urn:hl7-org:v3"/>
            </xsl:when>
            <!--Medications section-level documentConfidentialityCode code-->
            <xsl:when test="../code[@code='10160-0']">
                <confidentialityCode code="{$medicationsSectionConfidentialityCode}"
                    codeSystem="2.16.840.1.113883.5.25" xmlns="urn:hl7-org:v3"/>
            </xsl:when>
            <!--Lab Results section-level documentConfidentialityCode code-->
            <xsl:when test="../code[@code='30954-2']">
                <confidentialityCode code="{$labResultsSectionConfidentialityCode}"
                    codeSystem="2.16.840.1.113883.5.25" xmlns="urn:hl7-org:v3"/>
            </xsl:when>
            
            <xsl:otherwise>
                <xsl:copy-of select="."/>
            </xsl:otherwise>
        </xsl:choose>
        
    </xsl:template>
    
    <!--Section-level confidentialityCode code is not already specified in C32-->
    <xsl:template match="section/code">
        <xsl:choose>
            <!--Problems section-level documentConfidentialityCode code-->
            <xsl:when test="not(following-sibling::confidentialityCode) and @code='11450-4'">
                <xsl:copy-of select="."/>
                <confidentialityCode code="{$problemsSectionConfidentialityCode}"
                    codeSystem="2.16.840.1.113883.5.25" xmlns="urn:hl7-org:v3"/>
            </xsl:when>
            
            <!--Allergies section-level documentConfidentialityCode code-->
            <xsl:when test="not(following-sibling::confidentialityCode) and @code='48765-2'">
                <xsl:copy-of select="."/>
                <confidentialityCode code="{$allergiesSectionConfidentialityCode}"
                    codeSystem="2.16.840.1.113883.5.25" xmlns="urn:hl7-org:v3"/>
            </xsl:when>
            
            <!--Medications section-level documentConfidentialityCode code-->
            <xsl:when test="not(following-sibling::confidentialityCode) and @code='10160-0'">
                <xsl:copy-of select="."/>
                <confidentialityCode code="{$medicationsSectionConfidentialityCode}"
                    codeSystem="2.16.840.1.113883.5.25" xmlns="urn:hl7-org:v3"/>
            </xsl:when>
            
            <!--Lab results section-level documentConfidentialityCode code-->
            <xsl:when test="not(following-sibling::confidentialityCode) and @code='30954-2'">
                <xsl:copy-of select="."/>
                <confidentialityCode code="{$labResultsSectionConfidentialityCode}"
                    codeSystem="2.16.840.1.113883.5.25" xmlns="urn:hl7-org:v3"/>
            </xsl:when>
            
            <xsl:otherwise>
                <xsl:copy-of select="."/>
            </xsl:otherwise>
        </xsl:choose>
        
    </xsl:template>
    
    <!-- entry level tagging inside observation (Problems and Allergies) -->
    <xsl:template match="observation[parent::entryRelationship[@typeCode='SUBJ']]">
        <xsl:copy>
            <xsl:call-template name="entryLevelPrivacyAnnotationInsideObservation"/>
        </xsl:copy>
    </xsl:template>
    
    <!-- entry level tagging inside observation (Results) -->
    <xsl:template match="observation[ancestor::organizer]">
        <xsl:copy>
            <xsl:call-template name="entryLevelPrivacyAnnotationInsideObservation"/>
        </xsl:copy>
    </xsl:template>
    
    <!-- entry level tagging inside procedure (Results) -->
    <xsl:template match="procedure[ancestor::organizer]">
        <xsl:copy>
            <xsl:call-template name="entryLevelPrivacyAnnotationInsideProcedure"/>
        </xsl:copy>
    </xsl:template>
    
    <!-- entry level tagging inside substanceAdministration (Medications) -->
    <xsl:template match="substanceAdministration[descendant::manufacturedMaterial]">
        <xsl:copy>
            <xsl:call-template name="entryLevelPrivacyAnnotationInsideSubstanceAdministration"/>
        </xsl:copy>
    </xsl:template>
    
    <!-- template snippets -->
    <xsl:template name="entryLevelPrivacyAnnotationInsideObservation" exclude-result-prefixes="#all">
        <xsl:call-template name="observationChildrenBeforeEntryRelationship"/>        
        <xsl:call-template name="entryRelationshipWithPrivacyAnnotation"/>        
        <xsl:call-template name="observationChildrenAfterEntryRelationship"/>
    </xsl:template>
    
    <xsl:template name="entryLevelPrivacyAnnotationInsideProcedure" exclude-result-prefixes="#all">
        <xsl:call-template name="procedureChildrenBeforeEntryRelationship"/>        
        <xsl:call-template name="entryRelationshipWithPrivacyAnnotation"/>        
        <xsl:call-template name="procedureChildrenAfterEntryRelationship"/>
    </xsl:template>
    
    <xsl:template name="entryLevelPrivacyAnnotationInsideSubstanceAdministration" exclude-result-prefixes="#all">
        <xsl:call-template name="substanceAdministrationChildrenBeforeEntryRelationship"/>
        <xsl:call-template name="entryRelationshipWithPrivacyAnnotation"/>
        <xsl:call-template name="substanceAdministrationChildrenAfterEntryRelationship"/>
    </xsl:template>
    
    <xsl:template name="entryRelationshipWithPrivacyAnnotation">
        <xsl:variable name="conf" select="ds4p:getConfidentialityCode(.//@code)"/>
        <xsl:variable name="docRefrain" select="ds4p:getDocumentRefrainCode(.//@code)"/>
        <xsl:variable name="docObligation" select="ds4p:getDocumentObligationCode(.//@code)"/>

        <xsl:if test="$conf or $docObligation or $docRefrain">
            <entryRelationship typeCode="COMP" xmlns="urn:hl7-org:v3">
                <organizer classCode="CLUSTER" moodCode="DEF">
                    <templateId root="2.16.840.1.113883.3.3251.1.4"
                        assigningAuthorityName="HL7 Security"/>
                    <statusCode code="active"/>
                    <xsl:if test="$conf">
                        <component typeCode="COMP">
                            <observation classCode="OBS" moodCode="DEF">
                                <templateId root="2.16.840.1.113883.3.445.21"
                                    assigningAuthorityName="HL7 CBCC"/>
                                <templateId root="2.16.840.1.113883.3.445.12"
                                    assigningAuthorityName="HL7 CBCC"/>
                                <code code="SECCLASSOBS" codeSystem="2.16.840.1.113883.1.11.20457"
                                    codeSystemName="HL7 SecurityObservationTypeCodeSystem"
                                    displayName="Security Classification"/>
                                <value xsi:type="CE" code="{$conf}"
                                    codeSystem="2.16.840.1.113883.5.1063"
                                    codeSystemName="SecurityObservationValueCodeSystem"
                                    displayName="{ds4p:mapConfCodeFromChartoText($conf)}">
                                    <originalText>
                                        <xsl:value-of select="ds4p:mapConfCodeFromChartoText($conf)"
                                        /> Confidentiality </originalText>
                                </value>
                            </observation>
                        </component>
                    </xsl:if>
                    <xsl:if test="$docObligation">
                        <component typeCode="COMP">
                            <observation classCode="OBS" moodCode="DEF">
                                <templateId root="2.16.840.1.113883.3.445.21"
                                    assigningAuthorityName="HL7 CBCC"/>
                                <templateId root="2.16.840.1.113883.3.445.14"
                                    assigningAuthorityName="HL7 CBCC"/>
                                <code code="SECCONOBS" codeSystem="2.16.840.1.113883.1.11.20457"
                                    codeSystemName="HL7 SecurityObservationTypeCodeSystem"
                                    displayName="Security Classification"/>
                                <value xsi:type="CE" code="{$docObligation}"
                                    codeSystem="2.16.840.1.113883.5.1063"
                                    codeSystemName="SecurityObservationValueCodeSystem"
                                    displayName="{ds4p:mapDocObligationPolicyFromCodetoText($docObligation)}">
                                    <originalText>
                                        <xsl:value-of
                                            select="ds4p:mapDocObligationPolicyFromCodetoText($docObligation)"
                                        />
                                    </originalText>
                                </value>
                            </observation>
                        </component>
                    </xsl:if>
                    <xsl:if test="$docRefrain">
                        <component typeCode="COMP">
                            <observation classCode="OBS" moodCode="DEF">
                                <templateId root="2.16.840.1.113883.3.445.21"
                                    assigningAuthorityName="HL7 CBCC"/>
                                <templateId root="2.16.840.1.113883.3.445.23"
                                    assigningAuthorityName="HL7 CBCC"/>
                                <code code="SECCONOBS" codeSystem="2.16.840.1.113883.1.11.20457"
                                    codeSystemName="HL7 SecurityObservationTypeCodeSystem"
                                    displayName="Security Classification"/>
                                <value xsi:type="CE" code="{$docRefrain}"
                                    codeSystem="2.16.840.1.113883.5.1063"
                                    codeSystemName="SecurityObservationValueCodeSystem"
                                    displayName="{ds4p:mapDocRefrainPolicyFromCodetoText($docRefrain)}">
                                    <originalText>
                                        <xsl:value-of
                                            select="ds4p:mapDocRefrainPolicyFromCodetoText($docRefrain)"
                                        />
                                    </originalText>
                                </value>
                            </observation>
                        </component>
                    </xsl:if>
                </organizer>
            </entryRelationship>
        </xsl:if>
    </xsl:template>

    <xsl:template name="observationChildrenBeforeEntryRelationship">
        <xsl:apply-templates select="@*"/>
        <xsl:apply-templates select="realmCode"/>
        <xsl:apply-templates select="typeId"/>
        <xsl:apply-templates select="templateId"/>
        <xsl:apply-templates select="id"/>
        <xsl:apply-templates select="code"/>
        <xsl:apply-templates select="derivationExpr"/>
        <xsl:apply-templates select="text"/>
        <xsl:apply-templates select="statusCode"/>
        <xsl:apply-templates select="effectiveTime"/>
        <xsl:apply-templates select="priorityCode"/>
        <xsl:apply-templates select="repeatNumber"/>
        <xsl:apply-templates select="languageCode"/>
        <xsl:apply-templates select="value"/>
        <xsl:apply-templates select="interpretationCode"/>
        <xsl:apply-templates select="methodCode"/>
        <xsl:apply-templates select="targetSiteCode"/>
        <xsl:apply-templates select="subject"/>
        <xsl:apply-templates select="specimen"/>
        <xsl:apply-templates select="performer"/>
        <xsl:apply-templates select="author"/>
        <xsl:apply-templates select="informant"/>
        <xsl:apply-templates select="participant"/>
        <xsl:apply-templates select="entryRelationship"/>
    </xsl:template>
    <xsl:template name="observationChildrenAfterEntryRelationship">
        <xsl:apply-templates select="reference"/>
        <xsl:apply-templates select="precondition"/>
        <xsl:apply-templates select="referenceRange"/>
    </xsl:template>
    <xsl:template name="procedureChildrenBeforeEntryRelationship">
        <xsl:apply-templates select="@*"/>
        <xsl:apply-templates select="realmCode"/>
        <xsl:apply-templates select="typeId"/>
        <xsl:apply-templates select="templateId"/>
        <xsl:apply-templates select="id"/>
        <xsl:apply-templates select="code"/>
        <xsl:apply-templates select="text"/>
        <xsl:apply-templates select="statusCode"/>
        <xsl:apply-templates select="effectiveTime"/>
        <xsl:apply-templates select="priorityCode"/>
        <xsl:apply-templates select="languageCode"/>
        <xsl:apply-templates select="methodCode"/>
        <xsl:apply-templates select="approachSiteCode"/>
        <xsl:apply-templates select="targetSiteCode"/>
        <xsl:apply-templates select="subject"/>
        <xsl:apply-templates select="specimen"/>
        <xsl:apply-templates select="performer"/>
        <xsl:apply-templates select="author"/>
        <xsl:apply-templates select="informant"/>
        <xsl:apply-templates select="participant"/>
        <xsl:apply-templates select="entryRelationship"/>       
    </xsl:template>
    <xsl:template name="procedureChildrenAfterEntryRelationship">
        <xsl:apply-templates select="reference"/>
        <xsl:apply-templates select="precondition"/>
    </xsl:template>
    <xsl:template name="substanceAdministrationChildrenBeforeEntryRelationship">
        <xsl:apply-templates select="@*"/>
        <xsl:apply-templates select="realmCode"/>
        <xsl:apply-templates select="typeId"/>
        <xsl:apply-templates select="templateId"/>
        <xsl:apply-templates select="id"/>
        <xsl:apply-templates select="code"/>
        <xsl:apply-templates select="text"/>
        <xsl:apply-templates select="statusCode"/>
        <xsl:apply-templates select="effectiveTime"/>
        <xsl:apply-templates select="priorityCode"/>
        <xsl:apply-templates select="repeatNumber"/>
        <xsl:apply-templates select="routeCode"/>
        <xsl:apply-templates select="approachSiteCode"/>
        <xsl:apply-templates select="doseQuantity"/>
        <xsl:apply-templates select="rateQuantity"/>
        <xsl:apply-templates select="maxDoseQuantity"/>
        <xsl:apply-templates select="administrationUnitCode"/>
        <xsl:apply-templates select="subject"/>
        <xsl:apply-templates select="specimen"/>
        <xsl:apply-templates select="consumable"/>
        <xsl:apply-templates select="performer"/>
        <xsl:apply-templates select="author"/>
        <xsl:apply-templates select="informant"/>
        <xsl:apply-templates select="participant"/>
        <xsl:apply-templates select="entryRelationship"/>        
    </xsl:template>
    <xsl:template name="substanceAdministrationChildrenAfterEntryRelationship">
        <xsl:apply-templates select="reference"/>
        <xsl:apply-templates select="precondition"/>        
    </xsl:template>
    
</xsl:stylesheet>
