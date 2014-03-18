INSERT INTO consent2share.code_system (code_system_id, code, creation_time, modification_time, name, user_name, code_system_oid, display_name) 
VALUES (1,'SNOMED CT ', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'SNOMED-Clinical Terms', 'sysadmin', '2.16.840.1.113883.6.96', 'Systematized Nomenclature of Medicine-Clinical Terms');
INSERT INTO consent2share.code_system (code_system_id, code, creation_time, modification_time, name, user_name, code_system_oid, display_name) 
VALUES (2,'ICD-10-CM ', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ICD-10-Clinical Modification', 'sysadmin', '2.16.840.1.113883.6.90', 'The International Classification of Diseases, 10th Revision, Clinical Modification');
INSERT INTO consent2share.code_system (code_system_id, code, creation_time, modification_time, name, user_name, code_system_oid, display_name) 
VALUES (3,'ICD-9-CM ', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ICD-9-Clinical Modification', 'sysadmin', '2.16.840.1.113883.6.2', 'The International Classification of Diseases, 9th Revision, Clinical Modification');
INSERT INTO consent2share.code_system (code_system_id, code, creation_time, modification_time, name, user_name, code_system_oid, display_name) 
VALUES (4,'LOINC ', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'LOINC', 'sysadmin', '2.16.840.1.113883.6.1', 'Logical Observation Identifier Names and Codes');

INSERT INTO consent2share.code_system_version(code_system_version_id,creation_time,modification_time,user_name,description,version_name,fk_code_system_id)
VALUES(1,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'sysadmin','SNOMED-Clinical Terms Description','Version 1','1');
INSERT INTO consent2share.code_system_version(code_system_version_id,creation_time,modification_time,user_name,description,version_name,fk_code_system_id)
VALUES(2,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'sysadmin','SNOMED-Clinical Terms Description','Version 2','1');
INSERT INTO consent2share.code_system_version(code_system_version_id,creation_time,modification_time,user_name,description,version_name,fk_code_system_id)
VALUES(3,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'sysadmin','SNOMED-Clinical Terms Description','Version 3','1');
INSERT INTO consent2share.code_system_version(code_system_version_id,creation_time,modification_time,user_name,description,version_name,fk_code_system_id)
VALUES(4,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'sysadmin','ICD-10-Clinical Modification Description','Version 1','2');
INSERT INTO consent2share.code_system_version(code_system_version_id,creation_time,modification_time,user_name,description,version_name,fk_code_system_id)
VALUES(5,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'sysadmin','ICD-10-Clinical Modification Description','Version 2','2');
INSERT INTO consent2share.code_system_version(code_system_version_id,creation_time,modification_time,user_name,description,version_name,fk_code_system_id)
VALUES(6,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'sysadmin','ICD-10-Clinical Modification Description','Version 3','2');
INSERT INTO consent2share.code_system_version(code_system_version_id,creation_time,modification_time,user_name,description,version_name,fk_code_system_id)
VALUES(7,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'sysadmin','ICD-10-Clinical Modification Description','Version 4','2');
INSERT INTO consent2share.code_system_version(code_system_version_id,creation_time,modification_time,user_name,description,version_name,fk_code_system_id)
VALUES(8,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'sysadmin','ICD-9-Clinical Modification Description','Version 1','3');
INSERT INTO consent2share.code_system_version(code_system_version_id,creation_time,modification_time,user_name,description,version_name,fk_code_system_id)
VALUES(9,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'sysadmin','ICD-9-Clinical Modification Description','Version 2','3');
INSERT INTO consent2share.code_system_version(code_system_version_id,creation_time,modification_time,user_name,description,version_name,fk_code_system_id)
VALUES(10,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'sysadmin','ICD-9-Clinical Modification Description','Version 3','3');
INSERT INTO consent2share.code_system_version(code_system_version_id,creation_time,modification_time,user_name,description,version_name,fk_code_system_id)
VALUES(11,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'sysadmin','ICD-9-Clinical Modification Description','Version 4','3');
INSERT INTO consent2share.code_system_version(code_system_version_id,creation_time,modification_time,user_name,description,version_name,fk_code_system_id)
VALUES(12,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'sysadmin','LOINC Description','Version 1','4');
INSERT INTO consent2share.code_system_version(code_system_version_id,creation_time,modification_time,user_name,description,version_name,fk_code_system_id)
VALUES(13,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'sysadmin','LOINC Description','Version 2','4');

INSERT INTO consent2share.value_set_category(valueset_cat_id,code,creation_time,modification_time,name,user_name,description)
VALUES(1,'ETH',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'Substance abuse information sensitivity','sysadmin','Substance abuse information sensitivity');
INSERT INTO consent2share.value_set_category(valueset_cat_id,code,creation_time,modification_time,name,user_name,description)
VALUES(2,'GDIS',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'Genetic disease information sensitivity','sysadmin','Genetic disease information sensitivity');
INSERT INTO consent2share.value_set_category(valueset_cat_id,code,creation_time,modification_time,name,user_name,description)
VALUES(3,'HIV',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'HIV/AIDS information sensitivity','sysadmin','HIV/AIDS information sensitivity');
INSERT INTO consent2share.value_set_category(valueset_cat_id,code,creation_time,modification_time,name,user_name,description)
VALUES(4,'PSY',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'Psychiatry information sensitivity','sysadmin','Psychiatry information sensitivity');
INSERT INTO consent2share.value_set_category(valueset_cat_id,code,creation_time,modification_time,name,user_name,description)
VALUES(5,'SDV',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'Sexual assault, abuse, or domestic violence information sensitivity','sysadmin','Sexual assault, abuse, or domestic violence information sensitivity');
INSERT INTO consent2share.value_set_category(valueset_cat_id,code,creation_time,modification_time,name,user_name,description)
VALUES(6,'SEX',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'Sexuality and reproductive health information sensitivity','sysadmin','Sexuality and reproductive health information sensitivity');
INSERT INTO consent2share.value_set_category(valueset_cat_id,code,creation_time,modification_time,name,user_name,description)
VALUES(7,'STD',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'Sexually transmitted disease information sensitivity','sysadmin','Sexually transmitted disease information sensitivity');

INSERT INTO consent2share.value_set (valueset_id,code,creation_time,modification_time,name,user_name,description,fk_valueset_cat_id)
VALUES ('1', 'Alcohol Abuse', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Alcohol  Value Sets  Abuse, Dependence, Induced Disorders', 'sysadmin', 'Alcohol  Value Sets  Abuse, Dependence, Induced Disorders', '1');
INSERT INTO consent2share.value_set (valueset_id,code,creation_time,modification_time,name,user_name,description,fk_valueset_cat_id)
VALUES ('2', 'Drug Abuse', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Cannabis Abuse, Dependence, Induced Disorders Value Set', 'sysadmin', 'Cannabis Abuse, Dependence, Induced Disorders Value Set', '1');
INSERT INTO consent2share.value_set (valueset_id,code,creation_time,modification_time,name,user_name,description,fk_valueset_cat_id)
VALUES ('3', 'Psychotic Disorders', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Psychiatric Disorders Value Set', 'sysadmin', 'Psychiatric Disorders Value Set', '4');
INSERT INTO consent2share.value_set (valueset_id,code,creation_time,modification_time,name,user_name,description,fk_valueset_cat_id)
VALUES ('4', 'Antipsychotic', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Antipsychotic Value Set', 'sysadmin', 'Antipsychotic Value Set', '4');
