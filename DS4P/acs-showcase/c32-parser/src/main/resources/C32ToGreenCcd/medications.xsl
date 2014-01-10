<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns="urn:hl7-org:v3" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:hl7="urn:hl7-org:v3" xmlns:sdtc="urn:hl7-org:sdtc" exclude-result-prefixes="xs fn"
                xpath-default-namespace="urn:hl7-org:v3">
  <xsl:template match="component/structuredBody/component/section/code[@code = '10160-0']">
    <xsl:variable name="medications" select=".." />
    <xsl:variable name="count" select="count($medications/entry)" />
      <xsl:if test="$count>0">
        <medications>
          <xsl:for-each select="$medications/entry/substanceAdministration">
            <medication>
              <xsl:if test="effectiveTime[@xsi:type='IVL_TS']">
                <medicationDateRange>
                  <xsl:call-template name="timeIntervalType">
                    <xsl:with-param name="input" select="effectiveTime[@xsi:type='IVL_TS']" />
                  </xsl:call-template>
                </medicationDateRange>
              </xsl:if>
              <xsl:if test="effectiveTime[@xsi:type='PIVL_TS']">
                <xsl:variable name="period" as="node()" select="effectiveTime[@xsi:type='PIVL_TS']/period"/>
                <admissionTiming>
                  <period>
                    <xsl:attribute name="value" select="fn:string($period/@value)"/>
                    <xsl:attribute name="unit" select="fn:string($period/@unit)"/>
                  </period>
                </admissionTiming>
              </xsl:if>

              <!--The following route, dose and administrationUnit elements are HITSP/C83 Sig Components that are optional elements in a HITSP/C83 document. The dose and administrationUnit information is often inferable from the code (e.g. RxNorm code) of the consumable/manufacturedProduct. The route is often inferable from the administrativeUnit (e.g tablet implies oral route). -->
              <route>
                <xsl:if test="fn:exists(routeCode/@nullFlavor)">
                  <xsl:attribute name="nullFlavor" select="fn:string(routeCode/@nullFlavor)" />
                </xsl:if>
                <xsl:if test="fn:exists(routeCode/@code)">
                  <xsl:attribute name="code" select="fn:string(routeCode/@code)" />
                </xsl:if>
                <xsl:if test="fn:exists(routeCode/@codeSystem)">
                  <xsl:attribute name="codeSystem" select="fn:string(routeCode/@codeSystem)" />
                </xsl:if>
                <xsl:if test="fn:exists(routeCode/@codeSystemName)">
                  <xsl:attribute name="codeSystemName" select="fn:string(routeCode/@codeSystemName)" />
                </xsl:if>
                <xsl:if test="fn:exists(routeCode/@displayName)">
                  <xsl:attribute name="displayName" select="fn:string(routeCode/@displayName)" />
                </xsl:if>
              </route>
              <dose>
                <xsl:if test="fn:exists(doseQuantity/@nullFlavor)">
                  <xsl:attribute name="nullFlavor" select="fn:string(doseQuantity/@nullFlavor)" />
                </xsl:if>
                <xsl:if test="fn:exists(doseQuantity/@value)">
                  <xsl:attribute name="value" select="fn:string(doseQuantity/@value)" />
                </xsl:if>
                <xsl:if test="fn:exists(doseQuantity/@unit)">
                  <xsl:attribute name="unit" select="fn:string(doseQuantity/@unit)" />
                </xsl:if>
              </dose>
              <productForm>
                <xsl:if test="fn:exists(administrationUnitCode/@nullFlavor)">
                  <xsl:attribute name="nullFlavor" select="fn:string(administrationUnitCode/@nullFlavor)" />
                </xsl:if>
                <xsl:if test="fn:exists(administrationUnitCode/@code)">
                  <xsl:attribute name="code" select="fn:string(administrationUnitCode/@code)" />
                </xsl:if>
                <xsl:if test="fn:exists(administrationUnitCode/@codeSystem)">
                  <xsl:attribute name="codeSystem" select="fn:string(administrationUnitCode/@codeSystem)" />
                </xsl:if>
                <xsl:if test="fn:exists(administrationUnitCode/@codeSystemName)">
                  <xsl:attribute name="codeSystemName" select="fn:string(administrationUnitCode/@codeSystemName)" />
                </xsl:if>
                <xsl:if test="fn:exists(administrationUnitCode/@displayName)">
                  <xsl:attribute name="displayName" select="fn:string(administrationUnitCode/@displayName)" />
                </xsl:if>
              </productForm>
              <medicationInformation>
                <codedProductName>
                  <xsl:variable name="genericName" select="consumable/manufacturedProduct/manufacturedMaterial/code" />
                  <xsl:if test="$genericName/@nullFlavor">
                    <xsl:attribute name="nullFlavor" select="fn:string($genericName/@nullFlavor)" />
                  </xsl:if>
                  <xsl:if test="$genericName/@code">
                    <xsl:attribute name="code" select="fn:string($genericName/@code)" />
                  </xsl:if>
                  <xsl:if test="$genericName/@codeSystem">
                    <xsl:attribute name="codeSystem" select="fn:string($genericName/@codeSystem)" />
                  </xsl:if>
                  <xsl:if test="$genericName/@codeSystemName">
                    <xsl:attribute name="codeSystemName" select="fn:string($genericName/@codeSystemName)" />
                  </xsl:if>
                  <xsl:if test="$genericName/@displayName">
                    <xsl:attribute name="displayName" select="fn:string($genericName/@displayName)" />
                  </xsl:if>
                </codedProductName>

                <xsl:variable name="originalText" select="originalText" />
                <xsl:if test="$originalText">
                  <freeTextProductName>
                    <xsl:value-of select="fn:string($originalText)"/>
                  </freeTextProductName>
                </xsl:if>
              </medicationInformation>
              <typeOfMedication>
                <xsl:call-template name="ConceptDescriptorCodeOutputType">
                  <xsl:with-param name="input" select="entryRelationship/observation/templateId[@root = '2.16.840.1.113883.3.88.11.83.8.1']/../code" />
                </xsl:call-template>
              </typeOfMedication>
              <patientInstructions>
                <xsl:value-of select="concat(normalize-space(entryRelationship/act/code[@code = 'PINSTRUCT']/../text), '&#xA;')"/>
              </patientInstructions>
              <fulfillmentInstructions>
                <xsl:value-of select="concat(normalize-space(entryRelationship/act/code[@code = 'FINSTRUCT']/../text), '&#xA;')"/>
              </fulfillmentInstructions>
              <statusOfMedication>
                <xsl:call-template name="ConceptDescriptorCodeOutputType">
                  <xsl:with-param name="input" select="entryRelationship/observation/code[@code = '33999-4']/../value" />
                </xsl:call-template>
              </statusOfMedication>
            </medication>
          </xsl:for-each>
        </medications>
      </xsl:if>
    </xsl:template>
</xsl:stylesheet>
