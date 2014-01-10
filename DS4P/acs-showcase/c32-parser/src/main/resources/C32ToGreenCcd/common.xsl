<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns="urn:hl7-org:v3"
                xmlns:hl7="urn:hl7-org:v3" xmlns:sdtc="urn:hl7-org:sdtc" exclude-result-prefixes="xs fn"
                xpath-default-namespace="urn:hl7-org:v3">
  <xsl:template name="pointInTimeType">
    <xsl:param name="input" select="()" />
    <xsl:if test="$input/@nullFlavor">
      <xsl:attribute name="nullFlavor" select="fn:string($input/@nullFlavor)" />
    </xsl:if>
    <xsl:if test="$input/@value">
      <xsl:attribute name="value" select="fn:string($input/@value)" />
    </xsl:if>
  </xsl:template>
  <xsl:template name="timeIntervalType">
    <xsl:param name="input" />
      <xsl:if test="$input/@nullFlavor">
        <xsl:attribute name="nullFlavor" select="fn:string($input/@nullFlavor)" />
      </xsl:if>
      <xsl:if test="$input/@value">
        <xsl:attribute name="value" select="fn:string($input/@value)" />
      </xsl:if>
      <xsl:if test="$input/@operator">
        <xsl:attribute name="operator" select="fn:string($input/@operator)" />
      </xsl:if>
      <xsl:if test="$input/low">
        <low>
          <xsl:if test="$input/low/@nullFlavor">
            <xsl:attribute name="nullFlavor" select="fn:string($input/low/@nullFlavor)" />
          </xsl:if>
          <xsl:if test="$input/low/@value">
            <xsl:attribute name="value" select="fn:string($input/low/@value)" />
          </xsl:if>
        </low>
      </xsl:if>
      <xsl:if test="$input/width">
        <width>
          <xsl:if test="$input/width/@nullFlavor">
            <xsl:attribute name="nullFlavor" select="fn:string($input/width/@nullFlavor)" />
          </xsl:if>
          <xsl:if test="$input/width/@value">
            <xsl:attribute name="value" select="fn:string($input/width/@value)" />
          </xsl:if>
          <xsl:if test="$input/width/@unit">
            <xsl:attribute name="unit" select="fn:string($input/width/@unit)" />
          </xsl:if>
        </width>
      </xsl:if>
      <xsl:if test="$input/high">
        <high>
          <xsl:if test="$input/high/@nullFlavor">
            <xsl:attribute name="nullFlavor" select="fn:string($input/high/@nullFlavor)" />
          </xsl:if>
          <xsl:if test="$input/high/@value">
            <xsl:attribute name="value" select="fn:string($input/high/@value)" />
          </xsl:if>
        </high>
      </xsl:if>
      <xsl:if test="$input/center">
        <center>
          <xsl:call-template name="pointInTimeType">
            <xsl:with-param name="input" select="$input/center" as="node()" />
          </xsl:call-template>
        </center>
      </xsl:if>
  </xsl:template>
  <xsl:template name="generateCodedStructure">
    <xsl:param name="input" />
    <xsl:if test="$input/@nullFlavor">
      <xsl:attribute name="nullFlavor" select="fn:string($input/@nullFlavor)" />
    </xsl:if>
    <xsl:if test="$input/@code">
      <xsl:attribute name="code" select="fn:string($input/@code)" />
    </xsl:if>
    <xsl:if test="$input/@codeSystem">
      <xsl:attribute name="codeSystem" select="fn:string($input/@codeSystem)" />
    </xsl:if>
    <xsl:if test="$input/@codeSystemName">
      <xsl:attribute name="codeSystemName" select="fn:string($input/@codeSystemName)" />
    </xsl:if>
    <xsl:if test="$input/@displayName">
      <xsl:attribute name="displayName" select="fn:string($input/@displayName)" />
    </xsl:if>
    <xsl:if test="normalize-space($input/originalText)">
      <originalText>
        <xsl:sequence select="fn:string(normalize-space($input/originalText))" />
      </originalText>
    </xsl:if>
    <xsl:for-each select="$input/qualifier">
      <xsl:copy-of select="." />
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="ConceptDescriptorCodeOutputType">
    <xsl:param name="input" />
      <xsl:call-template name="generateCodedStructure">
        <xsl:with-param name="input" select="$input" />
      </xsl:call-template>
  </xsl:template>
  <xsl:template name="output-tokens">
    <xsl:param name="list" />
    <xsl:variable name="newlist">
      <xsl:choose>
        <xsl:when test="contains($list, ' ')"><xsl:value-of select="$list" /></xsl:when>
        <xsl:otherwise><xsl:value-of select="concat(normalize-space($list), ' ')"/></xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:variable name="first" select="substring-before($newlist, ' ')" />
    <xsl:variable name="remaining" select="substring-after($newlist, ' ')" />
    <num><xsl:value-of select="$first" /></num>
    <xsl:if test="$remaining">
      <xsl:call-template name="output-tokens">
        <xsl:with-param name="list" select="$remaining" />
        </xsl:call-template>
      </xsl:if>
   </xsl:template>
</xsl:stylesheet>
