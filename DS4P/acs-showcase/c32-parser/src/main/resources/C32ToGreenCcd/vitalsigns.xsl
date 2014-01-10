<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions"
	xmlns="urn:hl7-org:v3" xmlns:hl7="urn:hl7-org:v3" xmlns:sdtc="urn:hl7-org:sdtc"
	exclude-result-prefixes="xs fn" xpath-default-namespace="urn:hl7-org:v3" 
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <xsl:template match="component/structuredBody/component/section/code[@code = '8716-3']">
    <xsl:variable name="vitalSigns" select=".."/>
    <xsl:variable name="count" select="count($vitalSigns/entry)"/>
    <xsl:if test="$count>0">
      <vitalSigns>
        <xsl:for-each select="$vitalSigns/entry/organizer">
          <vitalSign>
            <xsl:if test="effectiveTime[@xsi:type='IVL_TS']">
              <resultDateTime>
                <xsl:call-template name="timeIntervalType">
                  <xsl:with-param name="input" select="effectiveTime[@xsi:type='IVL_TS']" />
                </xsl:call-template>
              </resultDateTime>
            </xsl:if>
            <xsl:if test="component/observation/code">
              <resultType>
                <xsl:call-template name="ConceptDescriptorCodeOutputType">
                  <xsl:with-param name="input" select="component/observation/code"/>
                </xsl:call-template>                
              </resultType>
            </xsl:if>
            <xsl:choose>
              <xsl:when test="component/observation/value[@xsi:type='RTO_PQ_PQ']">
                <resultValue>
                  <physicalQuantity>
                    <xsl:attribute name="value"
                      select="fn:string(component/observation/value/numerator/@value)"/>
                  </physicalQuantity>
                </resultValue>
              </xsl:when>
              <xsl:when test="component/observation/value[@xsi:type='PQ']">
                <resultValue>
                  <physicalQuantity>
                    <xsl:attribute name="value"
                      select="fn:string(component/observation/value/@value)"/>
                    <xsl:attribute name="unit"
                      select="fn:string(component/observation/value/@unit)"/>
                  </physicalQuantity>
                </resultValue>
              </xsl:when>
            </xsl:choose>
            <xsl:if test="component/observation/interpretationCode">
              <resultInterpretation>
                <xsl:call-template name="ConceptDescriptorCodeOutputType">
                  <xsl:with-param name="input" select="component/observation/interpretationCode"/>
                </xsl:call-template>
              </resultInterpretation>
            </xsl:if>
            <xsl:if test="component/observation/referenceRange/observationRange/text">
              <resultReferenceRange>
                <xsl:value-of select="component/observation/referenceRange/observationRange/text"/>
              </resultReferenceRange>
            </xsl:if>
            <xsl:if test="component/observation/entryRelationship[@typeCode='RSON']/entryRelationship/observation/code">
              <refusalReason>
                <xsl:call-template name="ConceptDescriptorCodeOutputType">
                  <xsl:with-param name="input" select="component/observation/entryRelationship[@typeCode='RSON']/entryRelationship/observation/code"/>
                </xsl:call-template>
              </refusalReason>
            </xsl:if>           
          </vitalSign>
        </xsl:for-each>
      </vitalSigns>
    </xsl:if>
  </xsl:template>
</xsl:stylesheet>
