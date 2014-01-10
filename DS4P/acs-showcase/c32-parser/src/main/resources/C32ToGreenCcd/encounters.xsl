<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions"
	xmlns="urn:hl7-org:v3" xmlns:hl7="urn:hl7-org:v3" xmlns:sdtc="urn:hl7-org:sdtc"
	exclude-result-prefixes="xs fn" xpath-default-namespace="urn:hl7-org:v3" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <xsl:template match="component/section/code[@code = '46240-8']">
    <xsl:variable name="encounters" select=".."/>
    <xsl:variable name="count" select="count($encounters/entry)"/>
    <xsl:if test="$count>0">
      <encounters>
        <xsl:for-each select="$encounters/entry/encounter">
          <encounter>
            <xsl:if test="code">
              <encounterType>
                <xsl:call-template name="ConceptDescriptorCodeOutputType">
                  <xsl:with-param name="input" select="code"/>
                </xsl:call-template>
              </encounterType>
            </xsl:if>
            <xsl:if test="effectiveTime[@xsi:type='IVL_TS']">
              <encounterDateTime>
                <xsl:call-template name="timeIntervalType">
                  <xsl:with-param name="input" select="effectiveTime[@xsi:type='IVL_TS']" />
                </xsl:call-template>
              </encounterDateTime>
            </xsl:if>
            <xsl:if test="performer/assignedEntity/assignedPerson/name | participant/playingEntity/name">
              <encounterProvider>
                <xsl:if test="performer/assignedEntity/assignedPerson/name">
                  <providerName>
                    <xsl:variable name="nameList" select="tokenize(performer/assignedEntity/assignedPerson/name/text(),' ')"/>
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
                </xsl:if>
                <xsl:if test="participant/participantRole/playingEntity/name">
                  <providerOrganizationName>
                    <xsl:value-of select="participant/participantRole/playingEntity/name"/>
                  </providerOrganizationName>
                </xsl:if>
              </encounterProvider>
            </xsl:if>
          </encounter>
        </xsl:for-each>
      </encounters>
    </xsl:if>
  </xsl:template>
</xsl:stylesheet>
