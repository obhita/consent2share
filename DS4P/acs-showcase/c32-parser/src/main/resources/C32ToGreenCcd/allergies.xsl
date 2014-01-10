<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns="urn:hl7-org:v3" xmlns:hl7="urn:hl7-org:v3" xmlns:sdtc="urn:hl7-org:sdtc" exclude-result-prefixes="xs fn" xpath-default-namespace="urn:hl7-org:v3" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <xsl:template match="component/structuredBody/component/section/code[@code = '48765-2']">
    <xsl:variable name="allergies" select=".." />
    <xsl:variable name="count" select="count($allergies/entry)" />
    <xsl:if test="$count>0">
      <allergies>
        <xsl:for-each select="$allergies/entry/act">
          <allergy>
            <xsl:if test="effectiveTime[@xsi:type='IVL_TS']">
              <adverseEventDate>
                <xsl:call-template name="timeIntervalType">
                  <xsl:with-param name="input" select="effectiveTime[@xsi:type='IVL_TS']" />
                </xsl:call-template>
              </adverseEventDate>
            </xsl:if>
            <xsl:variable name="observation" select="entryRelationship/observation"/>
            <xsl:if test="$observation/code">
              <adverseEventType>
                <xsl:call-template name="ConceptDescriptorCodeOutputType">
                  <xsl:with-param name="input" select="$observation/code" />
                </xsl:call-template>
              </adverseEventType>
            </xsl:if>
            <xsl:variable name="productValue" select="$observation/value"/>
            <xsl:variable name="productCode" select="$observation/participant/participantRole/playingEntity/code"/>
            <xsl:choose>
              <xsl:when test="$productValue">
                <product>
                  <xsl:call-template name="ConceptDescriptorCodeOutputType">
                    <xsl:with-param name="input" select="$productValue" />
                  </xsl:call-template>
                </product>
              </xsl:when>
              <xsl:when test="$productCode">
                <product>
                  <xsl:call-template name="ConceptDescriptorCodeOutputType">
                    <xsl:with-param name="input" select="$productCode" />
                  </xsl:call-template>
                  <xsl:if test="$observation/participant/participantRole/playingEntity/name">
                    <xsl:attribute name="displayName" select="fn:string($observation/participant/participantRole/playingEntity/name)" />
                  </xsl:if>
                </product>
              </xsl:when>
            </xsl:choose>
            <xsl:for-each select="$observation/entryRelationship/observation/templateId[@root='2.16.840.1.113883.10.20.1.54']/..">
              <xsl:if test="value">
                <reaction>
                  <xsl:call-template name="ConceptDescriptorCodeOutputType">
                    <xsl:with-param name="input" select="value" />
                  </xsl:call-template>
                </reaction>
              </xsl:if>
            </xsl:for-each>
            <xsl:variable name="allergyStatus" select="$observation/entryRelationship/observation/code[@code='33999-4']/../value"/>
            <xsl:if test="$allergyStatus">
              <allergyStatus>
                <xsl:call-template name="ConceptDescriptorCodeOutputType">
                  <xsl:with-param name="input" select="$allergyStatus" />
                </xsl:call-template>
              </allergyStatus>
            </xsl:if>
          </allergy>
        </xsl:for-each>
      </allergies>
    </xsl:if>
  </xsl:template>
</xsl:stylesheet>