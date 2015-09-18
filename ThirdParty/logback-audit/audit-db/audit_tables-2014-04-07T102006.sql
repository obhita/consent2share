-- Schema and Mapping(the hibernate mapping file(/audit-parent/audit-server/src/main/resources/ch/qos/logback/audit/AuditEvent.hbm.xml)) must be synchronized.
-- first run mvn clean install at audit-parent level and verify the changes are reflected in the target folders
-- then go to audit-server-generator > AuditService5 and run mvn clean install and check the war file has the latest changes
-- follow instructions mentioned in the installtion document(hibernate.properties file change) and deploy the war in the appropriate servers
alter table PREDICATE_MAP drop foreign key FK7E366E94A7D7F477;
drop table if exists AUDIT_EVENT;
drop table if exists PREDICATE_MAP;
create table AUDIT_EVENT (EVENT_ID bigint not null auto_increment, TIMESTAMP datetime not null, SUBJECT varchar(128) not null, VERB varchar(128) not null, OBJECT varchar(128), ORG_APP_NAME varchar(128), ORG_APP_IP_ADDRESS varchar(64), CLI_APP_NAME varchar(128) not null, CLI_APP_IP_ADDRESS varchar(64) not null, primary key (EVENT_ID));
create table PREDICATE_MAP (EVENT_ID bigint not null, PREDICATE_VALUE mediumtext, PREDICATE_KEY varchar(128) not null, primary key (EVENT_ID, PREDICATE_KEY));
create index CLI_APP_NAME_IDX on AUDIT_EVENT (CLI_APP_NAME);
create index TIMESTAMP_IDX on AUDIT_EVENT (TIMESTAMP);
create index ORG_APP_NAME_IDX on AUDIT_EVENT (ORG_APP_NAME);
create index VERB_IDX on AUDIT_EVENT (VERB);
create index OBJECT_IDX on AUDIT_EVENT (OBJECT);
create index SUBJECT_IDX on AUDIT_EVENT (SUBJECT);
alter table PREDICATE_MAP add index FK7E366E94A7D7F477 (EVENT_ID), add constraint FK7E366E94A7D7F477 foreign key (EVENT_ID) references AUDIT_EVENT (EVENT_ID);
