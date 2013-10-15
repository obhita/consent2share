
    alter table individual_provider 
        drop 
        foreign key FKD12E4D6EB2036D5db6670d7
;

    alter table organizational_provider 
        drop 
        foreign key FKD12E4D6EB2036D529e59072
;

    alter table patient 
        drop 
        foreign key FKD0D3EB05185F3145
;

    alter table patient 
        drop 
        foreign key FKD0D3EB05A04C552A
;

    alter table patient 
        drop 
        foreign key FKD0D3EB05FBB203DF
;

    alter table patient 
        drop 
        foreign key FKD0D3EB05BE1326B1
;

    alter table patient 
        drop 
        foreign key FKD0D3EB05CD0E74CA
;

    alter table patient 
        drop 
        foreign key FKD0D3EB05C6E57D2B
;

    alter table patient 
        drop 
        foreign key FKD0D3EB052D37BD33
;

    alter table patient 
        drop 
        foreign key FKD0D3EB0590C96B08
;

    alter table patient 
        drop 
        foreign key FKD0D3EB059FF68A99
;

    alter table patient 
        drop 
        foreign key FKD0D3EB05CC866C68
;

    drop table if exists address_use_code
;

    drop table if exists administrative_gender_code
;

    drop table if exists country_code
;

    drop table if exists education_material
;

    drop table if exists ethnic_group_code
;

    drop table if exists facility_type_code
;

    drop table if exists individual_provider
;

    drop table if exists language_ability_code
;

    drop table if exists language_code
;

    drop table if exists language_proficiency_code
;

    drop table if exists marital_status_code
;

    drop table if exists organizational_provider
;

    drop table if exists patient
;

    drop table if exists patiental_relationship_role_code
;

    drop table if exists provider_taxononomy_code
;

    drop table if exists race_code
;

    drop table if exists religious_affiliation_code
;

    drop table if exists state_code
;

    drop table if exists telecom_use_code
;

    drop table if exists unit_of_measure_code
;

    drop table if exists hibernate_sequences
;
