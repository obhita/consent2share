<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions"
	xmlns="urn:hl7-org:v3" xmlns:hl7="urn:hl7-org:v3" xmlns:sdtc="urn:hl7-org:sdtc"
	exclude-result-prefixes="xs fn" xpath-default-namespace="urn:hl7-org:v3" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <xsl:template match="component/section/code[@code = '18776-5']">
    <xsl:variable name="planOfCare" select=".."/>
    <xsl:variable name="count" select="count($planOfCare/entry)"/>
    <xsl:if test="$count>0">
      <planOfCare>
        <xsl:for-each select="$planOfCare/entry/observation">
          <plannedObservation>
            <xsl:if test="code">
              <planType>
                <xsl:call-template name="ConceptDescriptorCodeOutputType">
                  <xsl:with-param name="input" select="code"/>
                </xsl:call-template>
              </planType>
            </xsl:if>
            <xsl:if test="effectiveTime[@xsi:type='IVL_TS']">
              <plannedTime>
                <xsl:call-template name="timeIntervalType">
                  <xsl:with-param name="input" select="effectiveTime[@xsi:type='IVL_TS']" />
                </xsl:call-template>
              </plannedTime>
            </xsl:if>
          </plannedObservation>
        </xsl:for-each>
      </planOfCare>
    </xsl:if>
  </xsl:template>
</xsl:stylesheet>
