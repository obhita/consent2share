# 1. Externalizing and Encrypting

## 1.1. Externalization

A PropertyTemplate folder has been created which mimics our application specific properties e.g, providers.proerties.
Below is the structure of <b>provider-web</b> under <b>\ds4p-prod\PropertyTemplate</b>. All developers should be using this template for any changes in these application specific properties.

		PropertyTemplate
			- provider-web
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

There are two ways to test whether the logger is working. Please follow the steps below to quickly startup the application. For details and customization, please see the following topics.

### Option One

Running in Spring Tool Suite(STS)

1. Added two system property variables in catalina.properties under {CATALINA_HOME} or Pivotal “Servers” in STS
 
   - AUTO_SCAN=true

   - SCAN_PERIOD=30 seconds          

2. Start si-c2s-xds-web-bl server
3. Go to the directory `\ds4p-prod\PropertyTemplate\si\bl\config`
3. Open the file `instrumentation.properties`
4. Get the value of `instrumentationKey`
5. Go to the site `https://localhost:8443/si-c2s-xds-bl/api/instrumentation/loggerCheck?instrumentationKey=`
6. Paste the value of `instrumentationKey` in the end of above url.
7. The page will show currently logger information

### Option Two

Not required to start Spring Tool Suite(STS)

> Run the tools in project directory

1. Go to the directory C:\\java 
2. Create a file named catalina.properties
3. Update four system property variables accordingly as same as adding in CATALINA properties

   - C2S_KEY

   - C2S_PROPS  
  
   - AUTO_SCAN

   - SCAN_PERIOD  

4. Go to the root `si-c2s-xds-web-bl` project  directory `\ds4p-prod\DS4P\si-c2s-xds\bl\web-bl\`
5. Run the file `si-start-server.sh`
6. After server start up, run the file `si-logger-check.sh`.

Note: Need to configure accordingly location in files `si-start-server.sh` and `si-logger-check.sh`

## 2.2. Logback Configuration Externalization

### 2.2.1. About Logback Configuration Externalization

Logback allows you to redefine logging behavior without needing to recompile your code. Currently the application adopts externalize part of the configuration file. The logback.xml in the application package. It could only contain configuration element and include element which includes the externalized configuration file. Part or all of the included configuration file path can be externalized as system property variables defined .

### 2.2.2. Default Configuration
There are two variables need to be defined in CATALINA properties in configuration file `logback.xml` and the values set as default value.

1. `AUTO_SCAN=true`

2. `SCAN_PERIOD=30 seconds`

There are three main value in the externalized configuration file `logback_included.xml`.

1. `<property name="APP_LOG_FOLDER" value="C:\\java\\C2S_LOGS\\si" />`
	
2. `<property name="LOG_NAME" value="si-bl" />`

3. `<property name="gov.samhsa.consent2share.si_lOGGER.level" value="warn" />`

The 1st set the path of generated loggers and 2rd value named the logger file. The 3rd value define logging behavior, developers can change this value to redefine its behavior.  


## 2.3. About provider-start-server.sh

This tool must be configured the correct `Environment_Location`


## 2.4. About provider-logger-check.sh

This tool must be configured the correct `Config_Location`. As default, this tool uses google chrome as default browser. 