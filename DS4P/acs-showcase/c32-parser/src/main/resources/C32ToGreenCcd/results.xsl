<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions"
	xmlns="urn:hl7-org:v3" xmlns:hl7="urn:hl7-org:v3" xmlns:sdtc="urn:hl7-org:sdtc"
	exclude-result-prefixes="xs fn" xpath-default-namespace="urn:hl7-org:v3" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <xsl:template match="component/structuredBody/component/section/code[@code = '30954-2']">
    <xsl:variable name="results" select=".." />
    <xsl:variable name="count" select="count($results/entry)" />
    <xsl:if test="$count>0">
      <results>
        <xsl:for-each select="$results/entry/organizer">
          <result>
            <xsl:if test="effectiveTime[@xsi:type='IVL_TS']">
              <resultDateTime>
                <xsl:call-template name="timeIntervalType">
                  <xsl:with-param name="input" select="effectiveTime[@xsi:type='IVL_TS']" />
                </xsl:call-template>
              </resultDateTime>
            </xsl:if>
            <xsl:if test="code">
              <resultType>
                <xsl:call-template name="ConceptDescriptorCodeOutputType">
                  <xsl:with-param name="input" select="code"/>
                </xsl:call-template>
              </resultType>
            </xsl:if>
            <xsl:for-each select="component/procedure">
              <xsl:choose>
                <xsl:when test="code">
                  <procedureCode>
                    <xsl:call-template name="ConceptDescriptorCodeOutputType">
                      <xsl:with-param name="input" select="code"/>
                    </xsl:call-template>
                  </procedureCode>
                </xsl:when>
                <xsl:when test="text">
                  <procedureCode>
                    <xsl:attribute name="displayName" select="fn:string(text/@displayName)"/>
                  </procedureCode>
                </xsl:when>
              </xsl:choose>
            </xsl:for-each>
            <xsl:for-each select="component/observation">
              <xsl:if test="value">
                <resultValue>
                  <xsl:choose>
                    <xsl:when test="value[@xsi:type = 'PQ']">
                      <physicalQuantity>
                        <xsl:variable name="unitRV" select="value[@xsi:type = 'PQ']/@unit"/>
                        <xsl:variable name="nullFlavorRV" select="value[@xsi:type = 'PQ']/@nullFlavor"/>
                        <xsl:variable name="valueRV" select="value[@xsi:type = 'PQ']/@value"/>
                        <xsl:if test="fn:exists($nullFlavorRV)">
                          <xsl:attribute name="nullFlavor"
                            select="fn:string($nullFlavorRV)"/>
                        </xsl:if>
                        <xsl:if test="fn:exists($valueRV)">
                          <xsl:attribute name="value" select="fn:string($valueRV)"/>
                        </xsl:if>
                        <xsl:if test="fn:exists($unitRV)">
                          <xsl:attribute name="unit" select="fn:string($unitRV)"/>
                        </xsl:if>
                      </physicalQuantity>
                    </xsl:when>
                  </xsl:choose>
                </resultValue>
              </xsl:if>
              <xsl:if test="interpretationCode">
                <resultInterpretation>
                  <xsl:if test="fn:exists(interpretationCode/@nullFlavor)">
                    <xsl:attribute name="nullFlavor"
                      select="fn:string(interpretationCode/@nullFlavor)"/>
                  </xsl:if>
                  <xsl:if test="fn:exists(interpretationCode/@code)">
                    <xsl:attribute name="code"
                      select="fn:string(interpretationCode/@code)"/>
                  </xsl:if>
                  <xsl:if test="fn:exists(interpretationCode/@codeSystem)">
                    <xsl:attribute name="codeSystem"
                      select="fn:string(interpretationCode/@codeSystem)"/>
                  </xsl:if>
                  <xsl:if test="fn:exists(interpretationCode/@codeSystemName)">
                    <xsl:attribute name="codeSystemName"
                      select="fn:string(interpretationCode/@codeSystemName)"/>
                  </xsl:if>
                  <xsl:if test="fn:exists(interpretationCode/@displayName)">
                    <xsl:attribute name="displayName"
                      select="fn:string(interpretationCode/@displayName)"/>
                  </xsl:if>
                </resultInterpretation>
                <xsl:if test="referenceRange/observationRange/text">
                  <resultReferenceRange>
                    <xsl:value-of select="referenceRange/observationRange/text"/>
                  </resultReferenceRange>
                </xsl:if>
              </xsl:if>
            </xsl:for-each>
          </result>
        </xsl:for-each>
      </results>
    </xsl:if>
  </xsl:template>
</xsl:stylesheet>
