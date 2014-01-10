<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns="urn:hl7-org:v3"
                xmlns:hl7="urn:hl7-org:v3" xmlns:sdtc="urn:hl7-org:sdtc" exclude-result-prefixes="xs fn"
                xpath-default-namespace="urn:hl7-org:v3">
  <xsl:template match="author">
    <xsl:variable name="value" as="item()*" select="time/@value" />
    <xsl:variable name="nullFlavorTime" as="item()*" select="time/@nullFlavor" />
    <xsl:variable name="nullFlavorName" as="item()*" select="assignedAuthor/assignedPerson/name/@nullFlavor" />
    <informationSource>
      <author>
        <authorTime>
          <xsl:if test="fn:exists($nullFlavorTime)">
            <xsl:attribute name="nullFlavor" select="fn:string($nullFlavorTime)" />
          </xsl:if>
          <xsl:if test="fn:exists($value)">
            <xsl:attribute name="value" select="fn:string($value)" />
          </xsl:if>          
        </authorTime>
        <authorName>
          <xsl:if test="fn:exists($nullFlavorName)">
            <xsl:attribute name="nullFlavor" select="fn:string($nullFlavorName)" />
          </xsl:if>
          <family>
            <xsl:if test="assignedAuthor/assignedPerson/name/family">
              <xsl:sequence select="fn:string(assignedAuthor/assignedPerson/name/family)" />
            </xsl:if>
          </family>
          <given>
            <xsl:if test="assignedAuthor/assignedPerson/name/given">
              <xsl:sequence select="fn:string(assignedAuthor/assignedPerson/name/given)" />
            </xsl:if>
          </given>
          <prefix>
            <xsl:if test="assignedAuthor/assignedPerson/name/prefix">
              <xsl:sequence select="fn:string(assignedAuthor/assignedPerson/name/prefix)" />
            </xsl:if>
          </prefix>          
        </authorName>
      </author>
      <informationSourceName>
        <xsl:if test="assignedAuthor/representedOrganization/name">
          <xsl:variable name="organizationName_nullFlavor" as="item()*" select="assignedAuthor/representedOrganization/name/@nullFlavor"/>
          <organizationName>
                <xsl:if test="fn:exists($organizationName_nullFlavor)">
                  <xsl:attribute name="nullFlavor" namespace="" select="fn:string($organizationName_nullFlavor)"/>
                </xsl:if>
            <xsl:sequence select="fn:string(assignedAuthor/representedOrganization/name)"/>
              </organizationName>
          <xsl:if test="assignedAuthor/representedOrganization/id">
              <xsl:variable name="extension" as="item()*" select="assignedAuthor/representedOrganization/id/@extension" />
              <xsl:variable name="root" as="item()*" select="assignedAuthor/representedOrganization/id/@root" />
              <xsl:variable name="nullFlavor" as="item()*" select="assignedAuthor/representedOrganization/id/@nullFlavor" />
              <xsl:variable name="assigningName" as="item()*" select="assignedAuthor/representedOrganization/id/@assigningAuthority" />
              <organizationId>
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
              </organizationId>
            <xsl:if test="assignedAuthor/representedOrganization/telecom">
              <xsl:variable name="nullFlavorTelecom" as="item()*" select="assignedAuthor/representedOrganization/telecom/@nullFlavor" />
              <xsl:variable name="valueTelecom" as="item()*" select="assignedAuthor/representedOrganization/telecom/@value" />
                <organizationTelecom>
                  <xsl:if test="fn:exists($nullFlavorTelecom)">
                    <xsl:attribute name="nullFlavor" select="fn:string($nullFlavorTelecom)" />
                  </xsl:if>
                  <xsl:if test="fn:exists($valueTelecom)">
                    <xsl:attribute name="value" select="xs:string(xs:anyURI(fn:string($valueTelecom)))" />
                  </xsl:if>
                </organizationTelecom>
              </xsl:if>
            </xsl:if>
        </xsl:if>        
      </informationSourceName>
    </informationSource>
  </xsl:template>
</xsl:stylesheet>