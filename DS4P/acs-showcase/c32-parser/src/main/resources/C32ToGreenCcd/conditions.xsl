<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions"
	xmlns="urn:hl7-org:v3" xmlns:hl7="urn:hl7-org:v3" xmlns:sdtc="urn:hl7-org:sdtc"
	exclude-result-prefixes="xs fn" xpath-default-namespace="urn:hl7-org:v3" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <xsl:template match="component/section/code[@code = '11450-4']">
    <xsl:variable name="conditions" select=".."/>
    <xsl:variable name="count" select="count($conditions/entry)"/>
    <xsl:if test="$count>0">
      <conditions>
        <xsl:for-each select="$conditions/entry/act">
          <condition>
            <xsl:choose>
              <xsl:when test="effectiveTime[@xsi:type='IVL_TS']">
                <problemDate>
                <xsl:call-template name="timeIntervalType">
                  <xsl:with-param name="input" select="effectiveTime[@xsi:type='IVL_TS']" />
                </xsl:call-template>
                </problemDate>
              </xsl:when>
              <xsl:when test="entryRelationship/observation/templateId[@root='2.16.840.1.113883.10.20.1.28']/../effectiveTime[@xsi:type='IVL_TS']">
                <problemDate>
                  <xsl:call-template name="timeIntervalType">
                    <xsl:with-param name="input" select="entryRelationship/observation/effectiveTime[@xsi:type='IVL_TS']" />
                  </xsl:call-template>
                </problemDate>
              </xsl:when>
            </xsl:choose>
            <xsl:for-each select="entryRelationship">
              <xsl:if test="observation/templateId[@root='2.16.840.1.113883.10.20.1.28']">
                <xsl:if test="sequenceNumber">
                  <diagnosisPriority>
                    <xsl:value-of select="sequenceNumber/@value"/>
                  </diagnosisPriority>
                </xsl:if>
                <xsl:if test="observation/code">
                  <problemType>
                    <xsl:call-template name="ConceptDescriptorCodeOutputType">
                      <xsl:with-param name="input" select="observation/code"/>
                    </xsl:call-template>
                  </problemType>
                </xsl:if>
                <xsl:choose>
                  <xsl:when test="observation/value/translation">
                    <problemCode>
                      <xsl:call-template name="ConceptDescriptorCodeOutputType">
                        <xsl:with-param name="input" select="observation/value/translation"/>
                      </xsl:call-template>
                    </problemCode>
                  </xsl:when>
                  <xsl:when test="observation/value">
                    <problemCode>
                      <xsl:call-template name="ConceptDescriptorCodeOutputType">
                        <xsl:with-param name="input" select="observation/value"/>
                      </xsl:call-template>
                    </problemCode>
                  </xsl:when>
                </xsl:choose>
                <xsl:if test="observation/entryRelationship/observation/code[@code='33999-4']/../value">
                  <problemStatus>
                    <xsl:call-template name="ConceptDescriptorCodeOutputType">
                      <xsl:with-param name="input" select="observation/entryRelationship/observation/code[@code='33999-4']/../value"/>
                    </xsl:call-template>
                  </problemStatus>
                </xsl:if>
              </xsl:if>
              <xsl:if test="observation/templateId[@root='2.16.840.1.113883.10.20.1.38']">
                <xsl:if test="observation/value">
                  <ageAtOnset>
                    <xsl:value-of select="observation/value/@value"/>
                  </ageAtOnset>
                </xsl:if>
              </xsl:if>
              <xsl:if test="observation/templateId[@root='2.16.840.1.113883.10.20.1.42']">
                <causeOfDeath>
                  <xsl:if test="observation/effectiveTime">
                    <timeOfDeath>
                      <xsl:attribute name="value"
                        select="fn:string(observation/effectiveTime/@value)"/>
                    </timeOfDeath>
                  </xsl:if>
                  <xsl:if test="observation/value">
                    <problemCode>
                      <xsl:call-template name="ConceptDescriptorCodeOutputType">
                        <xsl:with-param name="input" select="observation/value"/>
                      </xsl:call-template>
                    </problemCode>
                  </xsl:if>
                  <xsl:if test="observation/entryRelationship[@type='SUBJ']/observation/value">
                    <ageAtDeath>
                      <xsl:value-of select="observation/entryRelationship[@type='SUBJ']/observation/value/@value"/>
                    </ageAtDeath>
                  </xsl:if>
                </causeOfDeath>
              </xsl:if>
            </xsl:for-each>
          </condition>
        </xsl:for-each>
      </conditions>
    </xsl:if>
  </xsl:template>
</xsl:stylesheet>
