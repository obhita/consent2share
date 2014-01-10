<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns="urn:hl7-org:v3"
                xmlns:hl7="urn:hl7-org:v3" xmlns:sdtc="urn:hl7-org:sdtc" exclude-result-prefixes="xs fn"
                xpath-default-namespace="urn:hl7-org:v3">
  <xsl:template match="patientRole">
    <personalInformation>
      <patientInformation>
        <xsl:variable name="extension" as="item()*" select="id/@extension" />
        <xsl:variable name="root" as="item()*" select="id/@root" />
        <xsl:variable name="nullFlavor" as="item()*" select="id/@nullFlavor" />
        <personID>
          <xsl:if test="fn:exists($nullFlavor)">
            <xsl:attribute name="nullFlavor" select="fn:string($nullFlavor)" />
          </xsl:if>
          <xsl:if test="fn:exists($root)">
            <xsl:attribute name="root" select="fn:string($root)" />
          </xsl:if>
          <xsl:if test="fn:exists($extension)">
            <xsl:attribute name="extension" select="fn:string($extension)" />
          </xsl:if>
        </personID>
        <xsl:for-each select="addr">
          <xsl:variable name="nullFlavor2" as="item()*" select="@nullFlavor" />
          <personAddress >
            <xsl:if test="fn:exists($nullFlavor2)">
              <xsl:attribute name="nullFlavor" select="fn:string($nullFlavor2)" />
            </xsl:if>
            <xsl:copy-of select="./*" />
          </personAddress>
        </xsl:for-each>
        <xsl:for-each select="telecom">
          <xsl:variable name="nullFlavor3" as="item()*" select="@nullFlavor" />
          <xsl:variable name="value" as="item()*" select="@value" />
          <personPhone>
            <xsl:if test="fn:exists($nullFlavor3)">
              <xsl:attribute name="nullFlavor" select="fn:string($nullFlavor3)" />
            </xsl:if>
            <xsl:if test="fn:exists($value)">
              <xsl:attribute name="value" select="xs:string(xs:anyURI(fn:string($value)))" />
            </xsl:if>
          </personPhone>
        </xsl:for-each>
        <xsl:apply-templates select="patient" />
      </patientInformation>
    </personalInformation>
  </xsl:template>
  <xsl:template match="patient">
    <personInformation>
      <xsl:for-each select="name">
        <xsl:variable name="nullFlavor" as="item()*" select="@nullFlavor" />
        <personName>
          <xsl:if test="fn:exists($nullFlavor)">
            <xsl:attribute name="nullFlavor" select="fn:string($nullFlavor)" />
          </xsl:if> 
          <xsl:if test="family">         
            <family>            
                <xsl:sequence select="fn:string(family)" />            
            </family>
          </xsl:if>
          <xsl:if test="given">
            <given>            
              <xsl:sequence select="fn:string(given)" />
            </given>
          </xsl:if>
          <xsl:if test="prefix">
            <prefix>            
              <xsl:sequence select="fn:string(prefix)" />
            </prefix>
          </xsl:if>          
          <xsl:if test="suffix">
            <suffix>            
              <xsl:sequence select="fn:string(suffix)" />
            </suffix>
          </xsl:if>          
        </personName>
      </xsl:for-each>
      <xsl:if test="fn:exists(administrativeGenderCode)">
      <gender>
        <xsl:if test="fn:exists(administrativeGenderCode/@nullFlavor)">
          <xsl:attribute name="nullFlavor" select="fn:string(administrativeGenderCode/@nullFlavor)" />
        </xsl:if>
        <xsl:if test="fn:exists(administrativeGenderCode/@code)">
          <xsl:attribute name="code" select="fn:string(administrativeGenderCode/@code)" />
        </xsl:if>
        <xsl:if test="fn:exists(administrativeGenderCode/@codeSystem)">
          <xsl:attribute name="codeSystem" select="fn:string(administrativeGenderCode/@codeSystem)" />
        </xsl:if>
        <xsl:if test="fn:exists(administrativeGenderCode/@codeSystemName)">
          <xsl:attribute name="codeSystemName" select="fn:string(administrativeGenderCode/@codeSystemName)" />
        </xsl:if>
        <xsl:if test="fn:exists(administrativeGenderCode/@displayName)">
          <xsl:attribute name="displayName" select="fn:string(administrativeGenderCode/@displayName)" />
        </xsl:if>
      </gender>
      </xsl:if>
      <personDateOfBirth>
        <xsl:if test="fn:exists(birthTime/@nullFlavor)">
          <xsl:attribute name="nullFlavor" select="fn:string(birthTime/@nullFlavor)" />
        </xsl:if>
        <xsl:if test="fn:exists(birthTime/@value)">
          <xsl:attribute name="value" select="fn:string(birthTime/@value)" />
        </xsl:if>
      </personDateOfBirth>
      <maritalStatus>
        <xsl:if test="fn:exists(maritalStatusCode/@nullFlavor)">
          <xsl:attribute name="nullFlavor" select="fn:string(maritalStatusCode/@nullFlavor)" />
        </xsl:if>
        <xsl:if test="fn:exists(maritalStatusCode/@code)">
          <xsl:attribute name="code" select="fn:string(maritalStatusCode/@code)" />
        </xsl:if>
        <xsl:if test="fn:exists(maritalStatusCode/@codeSystem)">
          <xsl:attribute name="codeSystem" select="fn:string(maritalStatusCode/@codeSystem)" />
        </xsl:if>
        <xsl:if test="fn:exists(maritalStatusCode/@codeSystemName)">
          <xsl:attribute name="codeSystemName" select="fn:string(maritalStatusCode/@codeSystemName)" />
        </xsl:if>
        <xsl:if test="fn:exists(maritalStatusCode/@displayName)">
          <xsl:attribute name="displayName" select="fn:string(maritalStatusCode/@displayName)" />
        </xsl:if>
      </maritalStatus>
      <religiousAffiliation>
        <xsl:if test="fn:exists(religiousAffiliationCode/@nullFlavor)">
          <xsl:attribute name="nullFlavor" select="fn:string(religiousAffiliationCode/@nullFlavor)" />
        </xsl:if>
        <xsl:if test="fn:exists(religiousAffiliationCode/@code)">
          <xsl:attribute name="code" select="fn:string(religiousAffiliationCode/@code)" />
        </xsl:if>
        <xsl:if test="fn:exists(religiousAffiliationCode/@codeSystem)">
          <xsl:attribute name="codeSystem" select="fn:string(religiousAffiliationCode/@codeSystem)" />
        </xsl:if>
        <xsl:if test="fn:exists(religiousAffiliationCode/@codeSystemName)">
          <xsl:attribute name="codeSystemName" select="fn:string(religiousAffiliationCode/@codeSystemName)" />
        </xsl:if>
        <xsl:if test="fn:exists(religiousAffiliationCode/@displayName)">
          <xsl:attribute name="displayName" select="fn:string(religiousAffiliationCode/@displayName)" />
        </xsl:if>
      </religiousAffiliation>
      <race>
        <xsl:if test="fn:exists(raceCode/@nullFlavor)">
          <xsl:attribute name="nullFlavor" select="fn:string(raceCode/@nullFlavor)" />
        </xsl:if>
        <xsl:if test="fn:exists(raceCode/@code)">
          <xsl:attribute name="code" select="fn:string(raceCode/@code)" />
        </xsl:if>
        <xsl:if test="fn:exists(raceCode/@codeSystem)">
          <xsl:attribute name="codeSystem" select="fn:string(raceCode/@codeSystem)" />
        </xsl:if>
        <xsl:if test="fn:exists(raceCode/@codeSystemName)">
          <xsl:attribute name="codeSystemName" select="fn:string(raceCode/@codeSystemName)" />
        </xsl:if>
        <xsl:if test="fn:exists(raceCode/@displayName)">
          <xsl:attribute name="displayName" select="fn:string(raceCode/@displayName)" />
        </xsl:if>
      </race>
      <ethnicGroup>
        <xsl:if test="fn:exists(ethnicityCode/@nullFlavor)">
          <xsl:attribute name="nullFlavor" select="fn:string(ethnicityCode/@nullFlavor)" />
        </xsl:if>
        <xsl:if test="fn:exists(ethnicityCode/@code)">
          <xsl:attribute name="code" select="fn:string(ethnicityCode/@code)" />
        </xsl:if>
        <xsl:if test="fn:exists(ethnicityCode/@codeSystem)">
          <xsl:attribute name="codeSystem" select="fn:string(ethnicityCode/@codeSystem)" />
        </xsl:if>
        <xsl:if test="fn:exists(ethnicityCode/@codeSystemName)">
          <xsl:attribute name="codeSystemName" select="fn:string(ethnicityCode/@codeSystemName)" />
        </xsl:if>
        <xsl:if test="fn:exists(ethnicityCode/@displayName)">
          <xsl:attribute name="displayName" select="fn:string(ethnicityCode/@displayName)" />
        </xsl:if>
      </ethnicGroup>
      <xsl:if test="birthPlace">
        <xsl:variable name="birthPlaceName" as="item()*" select="birthPlace/place/name" />
        <xsl:variable name="birthPlaceNullFlavor" as="item()*" select="birthPlace/@nullFlavor" />
        <birthplace>
          <xsl:if test="fn:exists($birthPlaceNullFlavor)">
            <xsl:attribute name="nullFlavor" select="fn:string($birthPlaceNullFlavor)" />
          </xsl:if>
          <xsl:if test="fn:exists($birthPlaceName)">
            <xsl:attribute name="name" select="fn:string($birthPlaceName)" />
          </xsl:if>
          <streetAddressLine>
            <xsl:if test="birthPlace/place/streetAddresseLine">
              <xsl:sequence select="fn:string(birthPlace/streetAddresseLine)" />
            </xsl:if>
          </streetAddressLine>
          <city>
            <xsl:if test="birthPlace/place/city">
              <xsl:sequence select="fn:string(birthPlace/city)" />
            </xsl:if>
          </city>
          <state>
            <xsl:if test="birthPlace/place/state">
              <xsl:sequence select="fn:string(birthPlace/state)" />
            </xsl:if>
          </state>
          <postalCode>
            <xsl:if test="birthPlace/place/state">
              <xsl:sequence select="fn:string(birthPlace/state)" />
            </xsl:if>
          </postalCode>
        </birthplace>
      </xsl:if>
    </personInformation>
  </xsl:template>
</xsl:stylesheet>