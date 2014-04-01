INSERT INTO consent2share.code_system 
VALUES (1,'2014-03-07 10:59:04','2014-03-07 10:59:04','sysadmin','SNOMED CT ','SNOMED-Clinical Terms','2.16.840.1.113883.6.96','Systematized Nomenclature of Medicine-Clinical Terms')
		,(2,'2014-03-07 10:59:04','2014-03-07 10:59:04','sysadmin','ICD-10-CM ','ICD-10-Clinical Modification','2.16.840.1.113883.6.90','The International Classification of Diseases, 10th Revision, Clinical Modification')
		,(3,'2014-03-07 10:59:04','2014-03-07 10:59:04','sysadmin','ICD-9-CM ','ICD-9-Clinical Modification','2.16.840.1.113883.6.2','The International Classification of Diseases, 9th Revision, Clinical Modification')
		,(4,'2014-03-07 10:59:04','2014-03-07 10:59:04','sysadmin','LOINC ','LOINC','2.16.840.1.113883.6.1','Logical Observation Identifier Names and Codes')
		,(5,'2014-03-07 00:46:57','2014-03-07 00:49:40','consent2share.sysadmin','PH_RxNorm','RxNorm','2.16.840.1.113883.6.88','Catalog of the standard names given to clinical drugs and drug delivery devices');

INSERT INTO consent2share.code_system_version 
VALUES (1,'2014-03-07 10:59:04','2014-03-07 10:59:04','sysadmin','SNOMED-Clinical Terms Description','Version 1',1)
		,(2,'2014-03-07 10:59:04','2014-03-07 10:59:04','sysadmin','SNOMED-Clinical Terms Description','Version 2',1)
		,(3,'2014-03-07 10:59:04','2014-03-07 00:06:10','consent2share.sysadmin','SNOMED-Clinical Terms Description','Version 3',1)
		,(4,'2014-03-07 10:59:04','2014-03-07 10:59:04','sysadmin','ICD-10-Clinical Modification Description','Version 1',2)
		,(5,'2014-03-07 10:59:04','2014-03-07 10:59:04','sysadmin','ICD-10-Clinical Modification Description','Version 2',2)
		,(6,'2014-03-07 10:59:04','2014-03-07 10:59:04','sysadmin','ICD-10-Clinical Modification Description','Version 3',2)
		,(7,'2014-03-07 10:59:04','2014-03-07 10:59:04','sysadmin','ICD-10-Clinical Modification Description','Version 4',2)
		,(8,'2014-03-07 10:59:04','2014-03-07 10:59:04','sysadmin','ICD-9-Clinical Modification Description','Version 1',3)
		,(9,'2014-03-07 10:59:04','2014-03-07 10:59:04','sysadmin','ICD-9-Clinical Modification Description','Version 2',3)
		,(10,'2014-03-07 10:59:04','2014-03-07 10:59:04','sysadmin','ICD-9-Clinical Modification Description','Version 3',3)
		,(11,'2014-03-07 10:59:04','2014-03-07 10:59:04','sysadmin','ICD-9-Clinical Modification Description','Version 4',3)
		,(12,'2014-03-07 10:59:04','2014-03-07 10:59:04','sysadmin','LOINC Description','Version 1',4)
		,(13,'2014-03-07 10:59:04','2014-03-07 10:59:04','sysadmin','LOINC Description','Version 2',4)
		,(14,'2014-03-07 00:10:55','2014-03-07 00:10:55','consent2share.sysadmin','20130731','20130731',1)
		,(15,'2014-03-07 00:17:51','2014-03-07 00:17:51','consent2share.sysadmin','2.46','2.46',4)
		,(16,'2014-03-07 00:50:22','2014-03-07 00:50:22','consent2share.sysadmin','20100201','20100201',5)
		,(17,'2014-03-07 01:09:32','2014-03-07 01:09:46','consent2share.sysadmin','LOINC Description','2.30',4);
	
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

INSERT INTO consent2share.value_set 
VALUES (2,'2014-03-07 10:59:04','2014-03-07 10:59:04','sysadmin','Drug Abuse','Cannabis Abuse, Dependence, Induced Disorders Value Set','Cannabis Abuse, Dependence, Induced Disorders Value Set',1)
	  ,(3,'2014-03-07 10:59:04','2014-03-07 10:59:04','sysadmin','Psychotic Disorders','Psychiatric Disorders Value Set','Psychiatric Disorders Value Set',4)
	  ,(4,'2014-03-07 10:59:04','2014-03-07 10:59:04','sysadmin','Antipsychotic','Antipsychotic Value Set','Antipsychotic Value Set',4)
	  ,(6,'2014-03-07 00:57:50','2014-03-07 01:02:43','consent2share.sysadmin','Lab Test Name (HIV)','Lab Test Name (HIV)','lab tests associated with HIV',3)
	  ,(9,'2014-03-07 02:13:29','2014-03-07 02:13:29','consent2share.sysadmin','2.16.840.1.113883.3.464.1003.120.11.1005','HIV','National Committee for Quality Assurance',3)
	  ,(10,'2014-03-10 02:49:30','2014-03-10 02:53:37','consent2share.sysadmin','2.16.840.1.113883.3.88.12.80.17','Medication Clinical Drug Name Value Set','RxNorm normal forms for concepts type of Ã¢ÂÂIngredient NameÃ¢ÂÂ  or Generic Packs. ',2)
	  ,(14,'2014-03-11 10:12:54','2014-03-11 10:12:54','consent2share.sysadmin','Alcohol Abuse1','Alcohol Abuse Test7','Substance abuse information sensitivity',1)
	  ,(15,'2014-03-11 10:12:54','2014-03-11 10:12:54','consent2share.sysadmin','Alcohol Abuse2','Alcohol Abuse Test3','Substance abuse information sensitivity',1)
	  ,(16,'2014-03-11 10:12:54','2014-03-11 10:12:54','consent2share.sysadmin','Alcohol Abuse3','Alcohol Abuse Test','Substance abuse information sensitivity',1)
	  ,(17,'2014-03-11 10:12:54','2014-03-11 10:12:54','consent2share.sysadmin','Alcohol Abuse4','Alcohol Abuse Test3','Substance abuse information sensitivity',1)
	  ,(18,'2014-03-11 10:12:54','2014-03-11 10:12:54','consent2share.sysadmin','Alcohol Abuse5','Alcohol Abuse Test7','Substance abuse information sensitivity',1)
	  ,(19,'2014-03-11 10:12:54','2014-03-11 10:12:54','consent2share.sysadmin','Psy disorders','Psychiatric Disorders Value Set','Psychiatry information sensitivity',4)
	  ,(20,'2014-03-11 10:12:54','2014-03-11 10:12:54','consent2share.sysadmin','Hiv disorders','Hiv test','HIV/AIDS information sensitivity',3);

INSERT INTO consent2share.concept_code 
VALUES (1,'2014-03-07 00:59:29','2014-03-07 00:59:29','consent2share.sysadmin','51866-2','Deprecated HIV 1 Ab+Ag Ser Ql','Deprecated HIV 1 Ab+Ag [Presence] in Serum',15)
		,(2,'2014-03-07 01:03:24','2014-03-07 01:03:24','consent2share.sysadmin','48559-9','Deprecated HIV genotype Islt Genotyp','Deprecated HIV gentotype [Susceptibility]',15)
		,(3,'2014-03-07 01:04:00','2014-03-07 01:04:00','consent2share.sysadmin','49965-7','Deprecated HIV1 Ab/HIV 2 Ab Ser IB-Rto','Deprecated HIV 1 Ab/HIV 2 Ab Ab [Ratio] in Serum by Immunoblot (IB)',15)
		,(4,'2014-03-07 01:04:38','2014-03-07 01:04:38','consent2share.sysadmin','10682-3','Deprecated HIV1 RNA SerPl Amp Prb-aCnc','Deprecated HIV 1 RNA [Units/volume] in Serum or Plasma by Probe with amplification',15)
		,(5,'2014-03-07 01:06:21','2014-03-07 01:06:21','consent2share.sysadmin','56888-1','HIV 1+2 Ab+HIV1 p24 Ag Ser EIA-aCnc','HIV 1+2 Ab+HIV1 p24 Ag [Units/volume] in Serum by Immunoassay',15)
		,(6,'2014-03-07 01:10:32','2014-03-07 01:10:32','consent2share.sysadmin','69668-2','HIV 1 & 2 Ab SerPl EIA.rapid','HIV 1 and 2 Ab [Identifier] in Serum or Plasma by Rapid immunoassay',17)
		,(7,'2014-03-07 01:11:10','2014-03-07 01:11:10','consent2share.sysadmin','35564-4','HIV 1 p31+p32 Ab Ser Ql IB','HIV 1 p31+p32 Ab [Presence] in Serum by Immunoblot (IB)',17)
		,(8,'2014-03-07 01:11:45','2014-03-07 01:11:45','consent2share.sysadmin','35565-1','HIV 1 p40 Ab Ser Ql IB','HIV 1 p40 Ab [Presence] in Serum by Immunoblot (IB)',17)
		,(13,'2014-03-07 02:19:07','2014-03-07 02:19:07','consent2share.sysadmin','111880001','Acute HIV infection (disorder) ','Acute HIV infection (disorder)',14)
		,(14,'2014-03-07 02:22:08','2014-03-07 02:22:08','consent2share.sysadmin','186706006','Human immunodeficiency virus infection constitutional disease (disorder)','Human immunodeficiency virus infection constitutional disease (disorder)',14)
		,(15,'2014-03-10 02:54:58','2014-03-10 02:54:58','consent2share.sysadmin','227114','zotepine 100 MG Oral Tablet','zotepine 100 MG Oral Tablet',16)
		,(16,'2014-03-10 02:55:39','2014-03-10 02:55:39','consent2share.sysadmin','227112','zotepine 25 MG Oral Tablet','zotepine 25 MG Oral Tablet',16)
		,(17,'2014-03-10 02:56:15','2014-03-10 02:56:15','consent2share.sysadmin','227113','zotepine 50 MG Oral Tablet','zotepine 50 MG Oral Tablet',16)
		,(18,'2014-03-10 02:57:19','2014-03-10 02:57:19','consent2share.sysadmin','413759','Zuclopenthixol 25 MG Oral Tablet','Zuclopenthixol 25 MG Oral Tablet',16)
		,(20,'2014-03-10 06:14:38','2014-03-10 06:14:38','consent2share.sysadmin','73832-8','Adult depression screening assessment','Adult depression screening assessment',15)
		,(21,'2014-03-10 06:24:37','2014-03-10 06:24:37','consent2share.sysadmin','7200002','Alcoholism','Alcoholism',14)
		,(23,'2014-03-11 09:11:55','2014-03-11 09:11:55','consent2share.sysadmin','66214007','Substance Abuse Disorder','Substance Abuse Disorder',14)
		,(25,'2014-03-11 10:29:06','2014-03-11 10:29:06','consent2share.sysadmin','concept code1','concept code1 name','concept code1 desc',14)
		,(26,'2014-03-11 10:29:06','2014-03-11 10:29:06','consent2share.sysadmin','concept code2','concept code2 name','concept code2 desc',14)
		,(27,'2014-03-11 10:29:06','2014-03-11 10:29:06','consent2share.sysadmin','concept code3','concept code3 name','concept code3 desc',14)
		,(28,'2014-03-11 10:29:06','2014-03-11 10:29:06','consent2share.sysadmin','concept code4','concept code4 name','concept code4 desc',14)
		,(29,'2014-03-11 10:29:06','2014-03-11 10:29:06','consent2share.sysadmin','concept code5','concept code5 name','concept code5 desc',14);

INSERT INTO consent2share.conceptcode_valueset 
VALUES (1,6)
	 ,(2,6)
	 ,(3,6)
	 ,(4,6)
	 ,(5,6)
	 ,(6,6)
	 ,(7,6)
	 ,(8,6)
	 ,(13,9)
	 ,(14,9)
	 ,(15,10)
	 ,(16,10)
	 ,(17,10)
	 ,(18,10)
	 ,(20,3)
	 ,(23,2)
	 ,(25,2)
	 ,(25,3)
	 ,(26,2)
	 ,(26,3)
	 ,(27,2)
	 ,(27,3)
	 ,(28,2)
	 ,(28,3)
	 ,(29,2)
	 ,(29,3);
