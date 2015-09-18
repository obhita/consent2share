# PCM Database Schema Generation Instructions

## 1.1. Quick Start

There are two ways to generate PCM database schema. Please follow the steps below to quickly startup the application. For details and customization, please see the following topics.

### Option One

Running in Spring Tool Suite(STS)

1. Import maven project `pcm-schema-export` from `\ds4p-prod\DS4P\tools`
2. Go to the directory `\pcm-schema-export\src\main\resources\META-INF\spring`
3. Open the file `applicationContext-dataAccess.xml`
4. Update database connection properties accordingly
5. Run Maven build from the `pcm-schema-export`project (run the `mvn clean install` command)
6. The tables will be generated after the project is built successfully.

Note: `consent2share`is set as the default schema name

### Option Two

Not required to start Spring Tool Suite(STS)

> Run the tool in project directory

1. Go to the root `pcm-schema-export` project  directory `\ds4p-prod\DS4P\tools\pcm-schema-export\`
2. Run the file `pcm-domain-schema-export.sh`
3. The tables will be generated after the project is built successfully.

Note: `consent2share`is set as the default schema name

## 1.2. pcm-schema-export

### 1.2.1. About pcm-schema-export

This tool adopt the framework Spring 4 with Hibernate 4 to generate PCM database schema, which is using "org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean" that is defined in applicationContext-dataAccess.xml to generate the tables at deployment time.

### 1.2.2. Default Configuration

The default configuration file `applicationContext-dataAccess.xml` uses the default values.
So, the tool cannot successfully start up until proper values for URL, username and password for the database connection are provided. Since this generated database may take other operations(like verification), here recommend not changing the default database name (consent2share)  

### 1.2.3. Dependency version

Since this tool uses `consent2share-domain` project as dependency and currently the project version is `2.1.0-SNAPSHOT`, this might be changed in future. The developers can change it in `pcm-schema-export` project related pom file

Example:

Change the following value in pom file
+ `<consent2share.version>:` `2.1.0-SNAPSHOT`


## 1.3. About pcm-domain-schema-export.sh

This tool can not only be running in the project directory, but also be running in any place. In order to do this, the developers need to modify this file as following.

1. Remove the comments that add to these two statement `Environment_Location=` and `cd $Environment_Location`(line 4 and 5)
2. Configure the Environment_Location according your application environment.

**Note:**
The developers might need to update database connection properties accordingly.


## 1.4. Related tools

There are several script tools to support this tool. Such as, using `dbdiff.bat` to compare generated database with others

### 1.4.1. Reference

Please see [Liquibase tools](https://tfs.feisystems.com/tfs/FEICollection10/Consent2Share/_git/c2s-resources#path=%2Fliquibase%2Fdbdiff.bat&version=GBmaster&_a=contents "https://tfs.feisystems.com/tfs/FEICollection10/Consent2Share/_git/c2s-resources#path=%2Fliquibase%2Fdbdiff.bat&version=GBmaster&_a=contents") for details.