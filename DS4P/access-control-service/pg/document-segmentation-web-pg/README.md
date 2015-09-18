# 1. Externalizing and Encrypting

## 1.1. Externalization

A PropertyTemplate folder has been created which mimics our application specific properties e.g, database.proerties.
Below is the structure of <b>PCM</b> under <b>\ds4p-prod\PropertyTemplate</b>. All developers should be using this template for any changes in these application specific properties.
	
		PropertyTemplate
			- dss
				- pg
					- config
						*.properties

#### Setting Up System Property Variables (Ignore this step if variables are already defined for another project)
Create two new System property variables in catalina.properties under {CATALINA_HOME} or Pivotal “Servers” in STS:

		C2S_KEY=9HPcr8z634

		C2S_PROPS=C:\\eclipse-workspaces\\ds4p-workspace\\ds4p-prod\\PropertyTemplate



NOTES:

	1.	Path of C2S_PROPS must point to PropertyTemplate directory of your current workspace branch.

	2.	Restart STS to pick up newly created System Property variables.



## 1.2.	Encryption

While adding a new variable in application specific properties e.g, providers.proerties or creating a new properties file from scratch, ensure that all the sensitive information is encrypted using Jasypt.

#### Using Jasypt
An open source product called Java Simplified Encryption (JASYPT) is used to replace clear text passwords in files with encrypted strings that are decrypted at run time. Encrypting credentials Jasypt provides a command line utility that can be used to encrypt the values of your properties.

Download the Jasypt distribution and unpack it. The utilities reside in the bin directory.

	C:\Users\himalay.majumdar\Downloads\jasypt-1.9.2\bin>encrypt input=admin password=9HPcr8z634
	----ENVIRONMENT-----------------
	Runtime: Oracle Corporation Java HotSpot(TM) 64-Bit Server VM 25.25-b02
	----ARGUMENTS-------------------
	input: admin
	password: 9HPcr8z634
	----OUTPUT----------------------
	VSWiYdKWUgxQGzQw7WjEAA==

Update your properties file(under PropertyTemplate folder) putting the out inside ENC().

Example:

	database.password=ENC(VSWiYdKWUgxQGzQw7WjEAA==)
--------------------------------------------------------------------------------------------------
# 2. Logback Configuration Externalization Instructions

## 2.1. Quick Start

Please follow the steps below to quickly startup the application. For details and customization, please see the following topics.

### Steps

Running in Spring Tool Suite(STS)

1. Added two system property variables in catalina.properties under {CATALINA_HOME} or Pivotal “Servers” in STS
 
   - AUTO_SCAN=true

   - SCAN_PERIOD=30 seconds       
   
2. Start document-segmentation-web-bl server
3. Go to the directory `\ds4p-prod\PropertyTemplate\dss\bl\config`
3. Open the file `instrumentation.properties`
4. Get the value of `instrumentationKey`
5. Go to the site 
`http://localhost:8080/DocumentSegmentation-pg/services/instrumentation/loggerCheck?instrumentationKey=`
6. Paste the value of `instrumentationKey` in the end of above url.
7. The page will show currently logger information    

## 2.2. Logback Configuration Externalization

### 2.2.1. About Logback Configuration Externalization

Logback allows you to redefine logging behavior without needing to recompile your code. Currently the application adopts externalize part of the configuration file. The logback.xml in the application package. It could only contain configuration element and include element which includes the externalized configuration file. Part or all of the included configuration file path can be externalized as system property variables defined .

### 2.2.2. Default Configuration
There are two variables need to be defined in CATALINA properties in configuration file `logback.xml` and the values set as default value.

1. `AUTO_SCAN=true`

2. `SCAN_PERIOD=30 seconds`

There are three main value in the externalized configuration file `logback_included.xml`.

1. `<property name="APP_LOG_FOLDER" value="C:\\java\\C2S_LOGS\\dss" />`
	
2. `<property name="LOG_NAME" value="dss-pg" />`

3. `<property name="gov.samhsa.consent2share_lOGGER.level" value="warn" />`

The 1st set the path of generated loggers and 2rd value named the logger file. The 3rd value define logging behavior, developers can change this value to redefine its behavior.  
