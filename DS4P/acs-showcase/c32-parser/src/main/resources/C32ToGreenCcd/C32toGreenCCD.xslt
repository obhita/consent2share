<?xml version="1.0" encoding="utf-8"?>

<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns="urn:hl7-org:v3"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:hl7="urn:hl7-org:v3" xmlns:sdtc="urn:hl7-org:sdtc"
                exclude-result-prefixes="xs fn" xpath-default-namespace="urn:hl7-org:v3">
  <xsl:output method="xml" indent="yes" />
  <xsl:include href="common.xsl" />
  <xsl:include href="results.xsl" />
  <xsl:include href="encounters.xsl" />
  <xsl:include href="medications.xsl" />
  <xsl:include href="immunizations.xsl" />
  <xsl:include href="allergies.xsl" />
  <xsl:include href="vitalsigns.xsl" />
  <xsl:include href="procedures.xsl" />
  <xsl:include href="conditions.xsl" />
  <xsl:include href="planofcare.xsl" />
  <xsl:include href="socialhistory.xsl" />
  <xsl:include href="personalInformation.xsl" />
  <xsl:include href="informationsource.xsl" />

  <xsl:template match="ClinicalDocument">
    <greenCCD xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="urn:hl7-org:v3">
      <header>
        <xsl:if test="fn:exists(id)">
          <xsl:variable name="extension" as="item()*" select="id/@extension" />
          <xsl:variable name="root" as="item()*" select="id/@root" />
          <xsl:variable name="nullFlavor" as="item()*" select="id/@nullFlavor" />
          <xsl:variable name="assigningName" as="item()*" select="id/@assigningAuthority" />
          <documentID>
            <xsl:if test="fn:exists($nullFlavor)">
              <xsl:attribute name="nullFlavor" select="fn:string($nullFlavor)" />
            </xsl:if>
            <xsl:if test="fn:exists($root)">
              <xsl:attribute name="root" select="fn:string($root)" />
            </xsl:if>
            <xsl:if test="fn:exists($extension)">
              <xsl:attribute name="extension" select="fn:string($extension)" />
            </xsl:if>
            <xsl:if test="fn:exists($assigningName)">
              <xsl:attribute name="assigningAuthority" select="fn:string($assigningName)" />
            </xsl:if>
          </documentID>
        </xsl:if>
        <xsl:if test="fn:exists(effectiveTime)">
          <documentTimestamp>
            <xsl:attribute name="value" select="fn:string(effectiveTime/@value)" />
          </documentTimestamp>
        </xsl:if>
        <xsl:if test="fn:exists(confidentialityCode)">
          <confidentiality>
            <xsl:call-template name="ConceptDescriptorCodeOutputType">
              <xsl:with-param name="input" select="confidentialityCode" />
            </xsl:call-template>
          </confidentiality>
        </xsl:if>
        <xsl:apply-templates select="recordTarget | author" />
      </header>
      <xsl:apply-templates select="component" />
    </greenCCD>
  </xsl:template>


  <xsl:template match="recordTarget">
    <xsl:apply-templates select="patientRole" />
  </xsl:template>

  <xsl:template match="component/structuredBody">
    <body>
      <!--Results-->
      <xsl:apply-templates select="component/section/code[@code = '30954-2']" />
      <!--Encounters-->
      <xsl:apply-templates select="component/section/code[@code = '46240-8']" />
      <!-- Medications -->
      <xsl:apply-templates select="component/section/code[@code = '10160-0']" />
      <!-- Immunizations -->
      <xsl:apply-templates select="component/section/code[@code = '11369-6']" />
      <!--Allergies-->
      <xsl:apply-templates select="component/section/code[@code = '48765-2']" />
      <!--Vital Signs-->
      <xsl:apply-templates select="component/section/code[@code = '8716-3']" />
      <!--Procedures-->
      <xsl:apply-templates select="component/section/code[@code = '47519-4']" />
      <!--Conditions-->
      <xsl:apply-templates select="component/section/code[@code = '11450-4']" />
      <!--Plan of Care-->
      <xsl:apply-templates select="component/section/code[@code = '18776-5']" />
      <!--Social History-->
      <xsl:apply-templates select="component/section/code[@code = '29762-2']" />
    </body>
  </xsl:template>
</xsl:stylesheet>