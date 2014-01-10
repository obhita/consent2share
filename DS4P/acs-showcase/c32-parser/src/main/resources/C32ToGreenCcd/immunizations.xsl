<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions"
	xmlns="urn:hl7-org:v3" xmlns:hl7="urn:hl7-org:v3" xmlns:sdtc="urn:hl7-org:sdtc"
	exclude-result-prefixes="xs fn" xpath-default-namespace="urn:hl7-org:v3"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <xsl:template match="component/section/code[@code = '11369-6']">
    <xsl:variable name="immunizations" select=".."/>
    <xsl:variable name="count" select="count($immunizations/entry)"/>
    <xsl:if test="$count>0">
      <immunizations>
        <xsl:for-each select="$immunizations/entry/substanceAdministration">
          <immunization>
            <xsl:if test="effectiveTime[@xsi:type='IVL_TS']">
              <administeredDate>
                <xsl:call-template name="timeIntervalType">
                  <xsl:with-param name="input" select="effectiveTime[@xsi:type='IVL_TS']" />
                </xsl:call-template>
              </administeredDate>
            </xsl:if>
            <xsl:if test="routeCode">
              <route>
                <xsl:if test="fn:exists(routeCode/@nullFlavor)">
                  <xsl:attribute name="nullFlavor" select="fn:string(routeCode/@nullFlavor)"/>
                </xsl:if>
                <xsl:if test="fn:exists(routeCode/@code)">
                  <xsl:attribute name="code" select="fn:string(routeCode/@code)"/>
                </xsl:if>
                <xsl:if test="fn:exists(routeCode/@codeSystem)">
                  <xsl:attribute name="codeSystem" select="fn:string(routeCode/@codeSystem)"/>
                </xsl:if>
                <xsl:if test="fn:exists(routeCode/@codeSystemName)">
                  <xsl:attribute name="codeSystemName"
                    select="fn:string(routeCode/@codeSystemName)"/>
                </xsl:if>
                <xsl:if test="fn:exists(routeCode/@displayName)">
                  <xsl:attribute name="displayName" select="fn:string(routeCode/@displayName)"/>
                </xsl:if>
              </route>
            </xsl:if>
            <xsl:if test="doseQuantity">
              <dose>
                <xsl:if test="fn:exists(doseQuantity/@nullFlavor)">
                  <xsl:attribute name="nullFlavor" select="fn:string(doseQuantity/@nullFlavor)"/>
                </xsl:if>
                <xsl:if test="fn:exists(doseQuantity/@value)">
                  <xsl:attribute name="value" select="fn:string(doseQuantity/@value)"/>
                </xsl:if>
                <xsl:if test="fn:exists(doseQuantity/@unit)">
                  <xsl:attribute name="unit" select="fn:string(doseQuantity/@unit)"/>
                </xsl:if>
              </dose>
            </xsl:if>
            <xsl:if test="consumable/manufacturedProduct/manufacturedMaterial/code">
              <medicationInformation>
                <codedProductName>
                  <xsl:call-template name="ConceptDescriptorCodeOutputType">
                    <xsl:with-param name="input" select="consumable/manufacturedProduct/manufacturedMaterial/code" />
                  </xsl:call-template>
                </codedProductName>
              </medicationInformation>
            </xsl:if>
            <xsl:if test="performer/assignedEntity/assignedPerson/name">
              <xsl:variable name="nameList" select="tokenize(performer/assignedEntity/assignedPerson/name/text(),' ')"/>
              <immunizationProvider>
                <providerName>
                  <xsl:if test="$nameList[3]">
                    <family>
                      <xsl:value-of select="$nameList[3]"/>
                    </family>
                  </xsl:if>
                  <xsl:if test="$nameList[2]">
                    <given>
                      <xsl:value-of select="$nameList[2]"/>
                    </given>
                  </xsl:if>
                  <xsl:if test="$nameList[1]">
                    <prefix>
                      <xsl:value-of select="$nameList[1]"/>
                    </prefix>
                  </xsl:if>
                </providerName>
              </immunizationProvider>
            </xsl:if>
            <xsl:for-each select="entryRelationship">
              <xsl:if test="act/templateId[@root = '2.16.840.1.113883.10.20.1.27']">
                <refusalReason>
                  <xsl:call-template name="ConceptDescriptorCodeOutputType">
                    <xsl:with-param name="input" select="act/entryRelationship/observation/code"/>
                  </xsl:call-template>
                </refusalReason>                
              </xsl:if>
            </xsl:for-each>
          </immunization>
        </xsl:for-each>
      </immunizations>
    </xsl:if>
  </xsl:template>
</xsl:stylesheet>
