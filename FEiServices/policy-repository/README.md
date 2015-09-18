# 1. XACML Policy Repository Instructions

## 1.1. Quick Start

Please follow the steps below to quickly setup the environment and startup the application. For details and customization, please see the following topics.

1. Set a new system environment variable (using the exact values): `SPRING_CONFIG_LOCATION=file:/C:/java/polrep/conf/`
2. Create a new folder at `C:/java/polrep/conf/` and copy `src/main/resources/application.yml` file to this folder
3. Open the file `C:/java/polrep/conf/application.yml`, uncomment `url`, `username`, `password` and provide the correct values for the database instance
4. Create a new schema in the configured database instance
5. Completely shutdown and restart Eclipse (or Tomcat if running on a server)
6. There are 3 options to create the tables:

+ Set `spring.jpa.properties.hibernate.hbm2ddl.auto` in `C:/java/polrep/conf/application.yml` to `create` and start up the application; the tables will be created from the polrep domain when the application is starting up (you might want to set it back to `validate` in order to prevent rebuilding the database every time the application starts up)
+ Set `spring.datasource.initialize` to `true` to automatically create and build the schema from `src/main/resources/schema.sql`. In this case, you must have configured the database url with `polrep` schema name, because the current `schema.sql` uses this name (you might want to set it back to `false` in order to prevent rebuilding the database every time the application starts up)
+ Manually run the current reverse engineered database script provided in the `src/main/resources/schema.sql`. If you want to use a different schema name, you need to modify the script accordingly before running it.

## 1.2. Configuration

### 1.2.1. Default Configuration

In order to modify a default configuration (regardless of the environment that this application is running), `src/main/resources/application.yml` file can be directly modified. This is the first configuration file loaded by Spring Boot at runtime, so any other configuration that is loaded after this file (ie: an external YAML file, command line arguments...etc.) overrides the default values. Actually, the current state of this file has some database configuration missing, so it won't successfully start up until proper values for URL, username and password for the database connection are provided. This file can be used as a template to create environment specific versions of it and Spring Boot can be configured to load the external configuration file enabling the same build to run on different environments.

### 1.2.2. Externalized Configuration

There are several ways to do this with Spring Boot, but a couple of options are given as below:

#### 1.2.2.1. Using System Environment Variable

By adding `SPRING_CONFIG_LOCATION` system environment variable, Spring Boot can be instructed to scan and load `application.yml` from the specified location *(default configuration is still loaded, but can be overridden with external file)*.

Example:

+ Variable Name: `SPRING_CONFIG_LOCATION`
+ Variable Value: `file:/C:/java/polrep/conf/`

**IMPORTANT**:
When an external location is configured, the last character must be a slash (`/`) to specify that this is a folder location. Also, you may need to completely shutdown and restart your Eclipse, Tomcat...etc. to have the new system environment variable effective.

#### 1.2.2.2. Using Command Line Argument

By passing `-Dspring.config.location` command line argument, Spring Boot can be configured to load external configuration from an external location.

Example:

+ Building with Maven: `mvn clean install -Dspring.config.location=file:/C:/java/polrep/conf/`
+ Running as a Spring Boot application: `java -jar polrep.jar -Dspring.config.location=file:/C:/java/polrep/conf/`

### 1.1.3. Reference

Please see [Spring Boot -  Externalized Configuration](http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html "http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html") for details.

## 1.3. RESTful API

URL Pattern: `http://{host}:{port}/{context}/rest/{version}/{resource}`

Example: `http://localhost:8080/polrep-web/rest/lastest/policies/SomePolicyId`

+ **(GET) /policies**: returns policy metadata container dto having all policy IDs and validation results in the policy repository
+ **(GET) /policies/{policyId}**: returns policy container dto
	+ Parameters:
		+ **wildcard**: (String; optional) can be used to match multiple policies
+ **(GET) /policyCombiningAlgIds**: returns the currently loaded policy combining algorithms
+ **(GET) /policies/{policyId}/combined**: returns a policy set combining all matching policies
	+ Parameters:
		+ **wildcard**: (String; optional) can be used to match multiple policies
		+ **policySetId**: (String; optional) if not provided, a random policy set id is generated for the generated combining policy set
		+ **policyCombiningAlgId**: (String; required) it must be one of the available combining algorithms in the system, can be long or short version
+ **(POST) /policies**: adds new policy(ies) provided in the request payload (it can be used as addOrUpdate operation with force=true request parameter). the request payload must be a PolicyContentContainerDto
	+ Parameters:
		+ **force**: (boolean; optional; default: false) pass true to also update the policy if it already exists; if false and policy already exists, it throws an exception
+ **(PUT) /policies/{policyId}**: updates the policy, the policy content must be provided in the request payload as PolicyContentDto
+ **(DELETE) /policies/{policyId}**: deletes the policy