# Note: There are two sections in this file. You can only override the section two using generated Sql from domain.

# Section one: The following section is NOT generated
# DO NOT override
create table users(
username varchar(200) not null primary key,
password varchar(256) not null,
enabled boolean not null,
failed_attempts tinyint not null,
lockout_time varchar(30)
);
create table authorities (
username varchar(200) not null,
authority varchar(50) not null,
constraint fk_authorities_users
foreign key(username) references users(username)
);
create unique index ix_auth_username on authorities
(username,authority);


# Section two: The following is generated from domain using JPA mapping and JSR303 validation annotations

    create table address_use_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table administrative_gender_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table adverse_event_type_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table allergy (
        id bigint not null auto_increment,
        code varchar(255),
        code_system varchar(255),
        code_system_name varchar(255),
        display_name varchar(255),
        original_text varchar(255),
        allergy_end_date datetime,
        allergy_start_date datetime not null,
        version integer,
        adverse_event_type_code bigint,
        allergy_reaction bigint,
        allergy_severity_code bigint,
        allergy_status_code bigint,
        patient bigint,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table allergy_reaction_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table allergy_severity_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table allergy_status_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table body_site_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table clinical_concept_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table clinical_document (
        id bigint not null auto_increment,
        content longblob not null,
        content_type varchar(255) not null,
        description varchar(500),
        document_size bigint not null,
        document_url varchar(100),
        filename varchar(255) not null,
        name varchar(30) not null,
        version integer,
        clinical_document_type_code bigint,
        patient bigint,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table clinical_document_audit (
        id bigint not null,
        rev bigint not null,
        revtype tinyint,
        content longblob,
        content_type varchar(255),
        description varchar(500),
        document_size bigint,
        document_url varchar(100),
        filename varchar(255),
        name varchar(30),
        clinical_document_type_code bigint,
        patient bigint,
        primary key (id, rev)
    ) ENGINE=InnoDB
;

    create table clinical_document_section_type_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table clinical_document_type_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table confidentiality_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table consent (
        id bigint not null auto_increment,
        consent_reference_id varchar(255),
        consent_revokation_type varchar(255),
        consent_revoked boolean,
        description varchar(250) not null,
        end_date datetime,
        exportedcdar2consent longblob,
        exportedxacmlconsent longblob,
        name varchar(30) not null,
        revocation_date datetime,
        signed_date datetime,
        start_date datetime,
        unsigned_pdf_consent longblob not null,
        unsigned_pdf_consent_revoke longblob,
        version integer,
        xacml_policy_file longblob,
        legal_representative bigint,
        patient bigint,
        signed_pdf_consent bigint,
        signed_pdf_consent_revoke bigint,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table consent_aud (
        id bigint not null,
        rev bigint not null,
        revtype tinyint,
        consent_reference_id varchar(255),
        consent_revokation_type varchar(255),
        consent_revoked boolean,
        description varchar(250),
        end_date datetime,
        exportedcdar2consent longblob,
        exportedxacmlconsent longblob,
        name varchar(30),
        revocation_date datetime,
        signed_date datetime,
        start_date datetime,
        unsigned_pdf_consent longblob,
        unsigned_pdf_consent_revoke longblob,
        xacml_policy_file longblob,
        legal_representative bigint,
        patient bigint,
        primary key (id, rev)
    ) ENGINE=InnoDB
;

    create table consent_directive_type_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table consent_do_not_share_clinical_concept_codes (
        consent_id bigint not null,
        do_not_share_clinical_concept_codes bigint not null,
        primary key (consent_id, do_not_share_clinical_concept_codes),
        unique (do_not_share_clinical_concept_codes)
    ) ENGINE=InnoDB
;

    create table consent_do_not_share_clinical_document_section_type_code (
        consent_id bigint not null,
        clinical_document_section_type_code bigint
    ) ENGINE=InnoDB
;

    create table consent_do_not_share_clinical_document_type_code (
        consent_id bigint not null,
        clinical_document_type_code bigint
    ) ENGINE=InnoDB
;

    create table consent_do_not_share_sensitivity_policy_code (
        consent_id bigint not null,
        sensitivity_policy_code bigint
    ) ENGINE=InnoDB
;

    create table consent_individual_provider_disclosure_is_made_to (
        consent_id bigint not null,
        individual_provider bigint
    ) ENGINE=InnoDB
;

    create table consent_individual_provider_permitted_to_disclose (
        consent_id bigint not null,
        individual_provider bigint
    ) ENGINE=InnoDB
;

    create table consent_organizational_provider_disclosure_is_made_to (
        consent_id bigint not null,
        organizational_provider bigint
    ) ENGINE=InnoDB
;

    create table consent_organizational_provider_permitted_to_disclose (
        consent_id bigint not null,
        organizational_provider bigint
    ) ENGINE=InnoDB
;

    create table consent_share_for_purpose_of_use_code (
        consent_id bigint not null,
        purpose_of_use_code bigint
    ) ENGINE=InnoDB
;

    create table country_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table education_material (
        id bigint not null auto_increment,
        content varchar(250) not null,
        content_type varchar(255) not null,
        description varchar(500),
        document_size bigint not null,
        document_url varchar(250),
        filename varchar(255) not null,
        name varchar(30) not null,
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table email_token (
        id bigint not null auto_increment,
        expire_in_hours integer not null,
        is_token_used boolean,
        request_date_time datetime not null,
        token varchar(100) not null,
        token_type integer not null,
        username varchar(30) not null,
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table ethnic_group_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table facility_type_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table individual_provider (
        id bigint not null auto_increment,
        entity_type integer,
        enumeration_date varchar(30) not null,
        first_line_mailing_address varchar(255) not null,
        first_line_practice_location_address varchar(255) not null,
        last_update_date varchar(30) not null,
        mailing_address_city_name varchar(30) not null,
        mailing_address_country_code varchar(30) not null,
        mailing_address_fax_number varchar(30) not null,
        mailing_address_postal_code varchar(30) not null,
        mailing_address_state_name varchar(30) not null,
        mailing_address_telephone_number varchar(30) not null,
        npi varchar(30),
        practice_location_address_city_name varchar(30) not null,
        practice_location_address_country_code varchar(30) not null,
        practice_location_address_fax_number varchar(30) not null,
        practice_location_address_postal_code varchar(30) not null,
        practice_location_address_state_name varchar(30) not null,
        practice_location_address_telephone_number varchar(30) not null,
        provider_taxonomy_code varchar(30) not null,
        provider_taxonomy_description varchar(255) not null,
        second_line_mailing_address varchar(255) not null,
        second_line_practice_location_address varchar(255) not null,
        version integer,
        credential varchar(30) not null,
        first_name varchar(30) not null,
        last_name varchar(30) not null,
        middle_name varchar(30) not null,
        name_prefix varchar(30) not null,
        name_suffix varchar(30) not null,
        patient bigint,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table individual_provider_audit (
        id bigint not null,
        rev bigint not null,
        revtype tinyint,
        entity_type integer,
        enumeration_date varchar(30),
        first_line_mailing_address varchar(255),
        first_line_practice_location_address varchar(255),
        last_update_date varchar(30),
        mailing_address_city_name varchar(30),
        mailing_address_country_code varchar(30),
        mailing_address_fax_number varchar(30),
        mailing_address_postal_code varchar(30),
        mailing_address_state_name varchar(30),
        mailing_address_telephone_number varchar(30),
        npi varchar(30),
        practice_location_address_city_name varchar(30),
        practice_location_address_country_code varchar(30),
        practice_location_address_fax_number varchar(30),
        practice_location_address_postal_code varchar(30),
        practice_location_address_state_name varchar(30),
        practice_location_address_telephone_number varchar(30),
        provider_taxonomy_code varchar(30),
        provider_taxonomy_description varchar(255),
        second_line_mailing_address varchar(255),
        second_line_practice_location_address varchar(255),
        credential varchar(30),
        first_name varchar(30),
        last_name varchar(30),
        middle_name varchar(30),
        name_prefix varchar(30),
        name_suffix varchar(30),
        patient bigint,
        primary key (id, rev)
    ) ENGINE=InnoDB
;

    create table language_ability_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table language_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table language_proficiency_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table legal_representative_type_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table marital_status_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table medication (
        id bigint not null auto_increment,
        measured_value double precision,
        free_text_sig varchar(250) not null,
        medication_end_date datetime,
        code varchar(255),
        code_system varchar(255),
        code_system_name varchar(255),
        display_name varchar(255),
        original_text varchar(255),
        medication_start_date datetime not null,
        version integer,
        body_site_code bigint,
        unit_of_measure_code bigint,
        medication_status_code bigint,
        patient bigint,
        product_form_code bigint,
        route_code bigint,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table medication_status_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table modified_entity_type_entity (
        id bigint not null auto_increment,
        entity_class_name varchar(255),
        revision_type tinyint,
        revision bigint,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table obligation_policy_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table organizational_provider (
        id bigint not null auto_increment,
        entity_type integer,
        enumeration_date varchar(30) not null,
        first_line_mailing_address varchar(255) not null,
        first_line_practice_location_address varchar(255) not null,
        last_update_date varchar(30) not null,
        mailing_address_city_name varchar(30) not null,
        mailing_address_country_code varchar(30) not null,
        mailing_address_fax_number varchar(30) not null,
        mailing_address_postal_code varchar(30) not null,
        mailing_address_state_name varchar(30) not null,
        mailing_address_telephone_number varchar(30) not null,
        npi varchar(30),
        practice_location_address_city_name varchar(30) not null,
        practice_location_address_country_code varchar(30) not null,
        practice_location_address_fax_number varchar(30) not null,
        practice_location_address_postal_code varchar(30) not null,
        practice_location_address_state_name varchar(30) not null,
        practice_location_address_telephone_number varchar(30) not null,
        provider_taxonomy_code varchar(30) not null,
        provider_taxonomy_description varchar(255) not null,
        second_line_mailing_address varchar(255) not null,
        second_line_practice_location_address varchar(255) not null,
        version integer,
        authorized_official_first_name varchar(30) not null,
        authorized_official_last_name varchar(30) not null,
        authorized_official_name_prefix varchar(30) not null,
        authorized_official_telephone_number varchar(30) not null,
        authorized_official_title varchar(30) not null,
        org_name varchar(255) not null,
        other_org_name varchar(30),
        patient bigint,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table organizational_provider_audit (
        id bigint not null,
        rev bigint not null,
        revtype tinyint,
        entity_type integer,
        enumeration_date varchar(30),
        first_line_mailing_address varchar(255),
        first_line_practice_location_address varchar(255),
        last_update_date varchar(30),
        mailing_address_city_name varchar(30),
        mailing_address_country_code varchar(30),
        mailing_address_fax_number varchar(30),
        mailing_address_postal_code varchar(30),
        mailing_address_state_name varchar(30),
        mailing_address_telephone_number varchar(30),
        npi varchar(30),
        practice_location_address_city_name varchar(30),
        practice_location_address_country_code varchar(30),
        practice_location_address_fax_number varchar(30),
        practice_location_address_postal_code varchar(30),
        practice_location_address_state_name varchar(30),
        practice_location_address_telephone_number varchar(30),
        provider_taxonomy_code varchar(30),
        provider_taxonomy_description varchar(255),
        second_line_mailing_address varchar(255),
        second_line_practice_location_address varchar(255),
        authorized_official_first_name varchar(30),
        authorized_official_last_name varchar(30),
        authorized_official_name_prefix varchar(30),
        authorized_official_telephone_number varchar(30),
        authorized_official_title varchar(30),
        org_name varchar(255),
        other_org_name varchar(30),
        patient bigint,
        primary key (id, rev)
    ) ENGINE=InnoDB
;

    create table patient (
        id bigint not null auto_increment,
        city varchar(255),
        postal_code varchar(255),
        street_address_line varchar(255),
        birth_day datetime,
        email varchar(255),
        enterprise_identifier varchar(255),
        first_name varchar(30) not null,
        last_name varchar(30) not null,
        medical_record_number varchar(30),
        prefix varchar(30),
        social_security_number varchar(255),
        telephone varchar(255),
        username varchar(30),
        version integer,
        address_use_code bigint,
        country_code bigint,
        state_code bigint,
        administrative_gender_code bigint,
        ethnic_group_code bigint,
        language_code bigint,
        marital_status_code bigint,
        race_code bigint,
        religious_affiliation_code bigint,
        telecom_use_code bigint,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table patient_audit (
        id bigint not null,
        rev bigint not null,
        revtype tinyint,
        city varchar(255),
        postal_code varchar(255),
        street_address_line varchar(255),
        birth_day datetime,
        email varchar(255),
        enterprise_identifier varchar(255),
        first_name varchar(30),
        last_name varchar(30),
        medical_record_number varchar(30),
        prefix varchar(30),
        social_security_number varchar(255),
        telephone varchar(255),
        username varchar(30),
        address_use_code bigint,
        country_code bigint,
        state_code bigint,
        administrative_gender_code bigint,
        ethnic_group_code bigint,
        language_code bigint,
        marital_status_code bigint,
        race_code bigint,
        religious_affiliation_code bigint,
        telecom_use_code bigint,
        primary key (id, rev)
    ) ENGINE=InnoDB
;

    create table patient_legal_representative_association (
        id bigint not null auto_increment,
        relationship_end_date datetime,
        relationship_start_date datetime,
        version integer,
        legal_representative_type_code bigint,
        legal_representative bigint,
        patient bigint,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table patient_legal_representative_association_aud (
        id bigint not null,
        rev bigint not null,
        revtype tinyint,
        relationship_end_date datetime,
        relationship_start_date datetime,
        legal_representative_type_code bigint,
        legal_representative bigint,
        patient bigint,
        primary key (id, rev)
    ) ENGINE=InnoDB
;

    create table patient_patient_legal_representative_associations (
        patient bigint not null,
        patient_legal_representative_associations bigint not null,
        primary key (patient, patient_legal_representative_associations),
        unique (patient_legal_representative_associations)
    ) ENGINE=InnoDB
;

    create table patient_patient_legal_representative_associations_aud (
        rev bigint not null,
        patient bigint not null,
        patient_legal_representative_associations bigint not null,
        revtype tinyint,
        primary key (rev, patient, patient_legal_representative_associations)
    ) ENGINE=InnoDB
;

    create table privacy_law_policy_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table problem (
        id bigint not null auto_increment,
        age_at_on_set integer,
        code varchar(255),
        code_system varchar(255),
        code_system_name varchar(255),
        display_name varchar(255),
        original_text varchar(255),
        problem_end_date datetime,
        problem_start_date datetime not null,
        version integer,
        patient bigint,
        problem_status_code bigint,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table problem_status_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table procedure_observation (
        id bigint not null auto_increment,
        procedure_end_date datetime,
        procedure_start_date datetime not null,
        code varchar(255),
        code_system varchar(255),
        code_system_name varchar(255),
        display_name varchar(255),
        original_text varchar(255),
        version integer,
        patient bigint,
        procedure_status_code bigint,
        target_site_code bigint,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table procedure_observation_procedure_performer (
        procedure_observation bigint not null,
        procedure_performer bigint not null,
        primary key (procedure_observation, procedure_performer)
    ) ENGINE=InnoDB
;

    create table procedure_status_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table product_form_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table provider_admin (
        id bigint not null auto_increment,
        email varchar(255),
        employeeid varchar(255),
        first_name varchar(30) not null,
        last_name varchar(30) not null,
        username varchar(30),
        version integer,
        administrative_gender_code bigint,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table provider_admin_aud (
        id bigint not null,
        rev bigint not null,
        revtype tinyint,
        email varchar(255),
        employeeid varchar(255),
        first_name varchar(30),
        last_name varchar(30),
        username varchar(30),
        administrative_gender_code bigint,
        primary key (id, rev)
    ) ENGINE=InnoDB
;

    create table provider_taxononomy_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table purpose_of_use_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table race_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table refrain_policy_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table religious_affiliation_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table result_interpretation_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table result_observation (
        id bigint not null auto_increment,
        result_date_time datetime not null,
        result_reference_range varchar(255),
        code varchar(255),
        code_system varchar(255),
        code_system_name varchar(255),
        display_name varchar(255),
        original_text varchar(255),
        measured_value double precision,
        version integer,
        result_status_code bigint,
        patient bigint,
        result_interpretation_code bigint,
        unit_of_measure_code bigint,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table result_status_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table revinfo (
        rev bigint not null auto_increment,
        revtstmp bigint,
        username varchar(255),
        primary key (rev)
    ) ENGINE=InnoDB
;

    create table route_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table sensitivity_policy_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table signedpdfconsent (
        id bigint not null auto_increment,
        document_created_by varchar(255),
        document_created_version_key varchar(255),
        document_creation_date_time datetime,
        document_esigned_by varchar(255),
        document_esigned_date_time datetime,
        document_esigned_version_key varchar(255),
        document_id varchar(255) not null,
        document_locale varchar(255),
        document_message_by_sender varchar(255) not null,
        document_name_by_sender varchar(255) not null,
        document_sent_out_for_signature_date_time datetime,
        document_signed_status varchar(255) not null,
        document_viewed_by varchar(255),
        document_viewed_date_time datetime,
        documentlast_version_key varchar(255),
        signer_email varchar(255) not null,
        version integer,
        signed_pdf_consent_content longblob,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table signedpdfconsent_revocation (
        id bigint not null auto_increment,
        document_created_by varchar(255),
        document_created_version_key varchar(255),
        document_creation_date_time datetime,
        document_esigned_by varchar(255),
        document_esigned_date_time datetime,
        document_esigned_version_key varchar(255),
        document_id varchar(255) not null,
        document_locale varchar(255),
        document_message_by_sender varchar(255) not null,
        document_name_by_sender varchar(255) not null,
        document_sent_out_for_signature_date_time datetime,
        document_signed_status varchar(255) not null,
        document_viewed_by varchar(255),
        document_viewed_date_time datetime,
        documentlast_version_key varchar(255),
        signer_email varchar(255) not null,
        version integer,
        signed_pdf_consent_revocation_content longblob,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table social_history (
        id bigint not null auto_increment,
        social_history_end_date datetime,
        social_history_free_text varchar(255),
        social_history_start_date datetime not null,
        version integer,
        patient bigint,
        social_history_status_code bigint,
        social_history_type_code bigint,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table social_history_status_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table social_history_type_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table state_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table target_site_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table telecom_use_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    create table unit_of_measure_code (
        id bigint not null auto_increment,
        code varchar(250) not null,
        code_system varchar(250),
        code_system_name varchar(250) not null,
        display_name varchar(250) not null,
        original_text varchar(250),
        version integer,
        primary key (id)
    ) ENGINE=InnoDB
;

    alter table allergy 
        add index FKC9A96380B2036D5 (patient), 
        add constraint FKC9A96380B2036D5 
        foreign key (patient) 
        references patient (id)
;

    alter table allergy 
        add index FKC9A963803FD715DE (adverse_event_type_code), 
        add constraint FKC9A963803FD715DE 
        foreign key (adverse_event_type_code) 
        references adverse_event_type_code (id)
;

    alter table allergy 
        add index FKC9A96380563ABE2B (allergy_severity_code), 
        add constraint FKC9A96380563ABE2B 
        foreign key (allergy_severity_code) 
        references allergy_severity_code (id)
;

    alter table allergy 
        add index FKC9A96380DED2E7EF (allergy_reaction), 
        add constraint FKC9A96380DED2E7EF 
        foreign key (allergy_reaction) 
        references allergy_reaction_code (id)
;

    alter table allergy 
        add index FKC9A963806CD3D14B (allergy_status_code), 
        add constraint FKC9A963806CD3D14B 
        foreign key (allergy_status_code) 
        references allergy_status_code (id)
;

    alter table clinical_document 
        add index FKC35BC0C7B2036D5 (patient), 
        add constraint FKC35BC0C7B2036D5 
        foreign key (patient) 
        references patient (id)
;

    alter table clinical_document 
        add index FKC35BC0C7C03F481E (clinical_document_type_code), 
        add constraint FKC35BC0C7C03F481E 
        foreign key (clinical_document_type_code) 
        references clinical_document_type_code (id)
;

    alter table clinical_document_audit 
        add index FK62DCE63CDA971CE (rev), 
        add constraint FK62DCE63CDA971CE 
        foreign key (rev) 
        references revinfo (rev)
;

    alter table consent 
        add index FK38B6C01AB2036D5 (patient), 
        add constraint FK38B6C01AB2036D5 
        foreign key (patient) 
        references patient (id)
;

    alter table consent 
        add index FK38B6C01AD5FBA41D (signed_pdf_consent_revoke), 
        add constraint FK38B6C01AD5FBA41D 
        foreign key (signed_pdf_consent_revoke) 
        references signedpdfconsent_revocation (id)
;

    alter table consent 
        add index FK38B6C01A17D0C7B3 (legal_representative), 
        add constraint FK38B6C01A17D0C7B3 
        foreign key (legal_representative) 
        references patient (id)
;

    alter table consent 
        add index FK38B6C01A5FD7634E (signed_pdf_consent), 
        add constraint FK38B6C01A5FD7634E 
        foreign key (signed_pdf_consent) 
        references signedpdfconsent (id)
;

    alter table consent_aud 
        add index FK80F1CD0BCDA971CE (rev), 
        add constraint FK80F1CD0BCDA971CE 
        foreign key (rev) 
        references revinfo (rev)
;

    alter table consent_do_not_share_clinical_concept_codes 
        add index FK7E12513EF48C72D6 (do_not_share_clinical_concept_codes), 
        add constraint FK7E12513EF48C72D6 
        foreign key (do_not_share_clinical_concept_codes) 
        references clinical_concept_code (id)
;

    alter table consent_do_not_share_clinical_concept_codes 
        add index FK7E12513EFCE8945A (consent_id), 
        add constraint FK7E12513EFCE8945A 
        foreign key (consent_id) 
        references consent (id)
;

    alter table consent_do_not_share_clinical_document_section_type_code 
        add index FKFA0F9785835183AF (clinical_document_section_type_code), 
        add constraint FKFA0F9785835183AF 
        foreign key (clinical_document_section_type_code) 
        references clinical_document_section_type_code (id)
;

    alter table consent_do_not_share_clinical_document_section_type_code 
        add index FKFA0F9785FCE8945A (consent_id), 
        add constraint FKFA0F9785FCE8945A 
        foreign key (consent_id) 
        references consent (id)
;

    alter table consent_do_not_share_clinical_document_type_code 
        add index FK2938955FC03F481E (clinical_document_type_code), 
        add constraint FK2938955FC03F481E 
        foreign key (clinical_document_type_code) 
        references clinical_document_type_code (id)
;

    alter table consent_do_not_share_clinical_document_type_code 
        add index FK2938955FFCE8945A (consent_id), 
        add constraint FK2938955FFCE8945A 
        foreign key (consent_id) 
        references consent (id)
;

    alter table consent_do_not_share_sensitivity_policy_code 
        add index FKC819CBFFCE8945A (consent_id), 
        add constraint FKC819CBFFCE8945A 
        foreign key (consent_id) 
        references consent (id)
;

    alter table consent_do_not_share_sensitivity_policy_code 
        add index FKC819CBF81CE4DC9 (sensitivity_policy_code), 
        add constraint FKC819CBF81CE4DC9 
        foreign key (sensitivity_policy_code) 
        references sensitivity_policy_code (id)
;

    alter table consent_individual_provider_disclosure_is_made_to 
        add index FK769946155967D092 (individual_provider), 
        add constraint FK769946155967D092 
        foreign key (individual_provider) 
        references individual_provider (id)
;

    alter table consent_individual_provider_disclosure_is_made_to 
        add index FK76994615FCE8945A (consent_id), 
        add constraint FK76994615FCE8945A 
        foreign key (consent_id) 
        references consent (id)
;

    alter table consent_individual_provider_permitted_to_disclose 
        add index FK7568587A5967D092 (individual_provider), 
        add constraint FK7568587A5967D092 
        foreign key (individual_provider) 
        references individual_provider (id)
;

    alter table consent_individual_provider_permitted_to_disclose 
        add index FK7568587AFCE8945A (consent_id), 
        add constraint FK7568587AFCE8945A 
        foreign key (consent_id) 
        references consent (id)
;

    alter table consent_organizational_provider_disclosure_is_made_to 
        add index FKF2A069F01D9980B2 (organizational_provider), 
        add constraint FKF2A069F01D9980B2 
        foreign key (organizational_provider) 
        references organizational_provider (id)
;

    alter table consent_organizational_provider_disclosure_is_made_to 
        add index FKF2A069F0FCE8945A (consent_id), 
        add constraint FKF2A069F0FCE8945A 
        foreign key (consent_id) 
        references consent (id)
;

    alter table consent_organizational_provider_permitted_to_disclose 
        add index FKF16F7C551D9980B2 (organizational_provider), 
        add constraint FKF16F7C551D9980B2 
        foreign key (organizational_provider) 
        references organizational_provider (id)
;

    alter table consent_organizational_provider_permitted_to_disclose 
        add index FKF16F7C55FCE8945A (consent_id), 
        add constraint FKF16F7C55FCE8945A 
        foreign key (consent_id) 
        references consent (id)
;

    alter table consent_share_for_purpose_of_use_code 
        add index FK41F36B31BC15E53A (purpose_of_use_code), 
        add constraint FK41F36B31BC15E53A 
        foreign key (purpose_of_use_code) 
        references purpose_of_use_code (id)
;

    alter table consent_share_for_purpose_of_use_code 
        add index FK41F36B31FCE8945A (consent_id), 
        add constraint FK41F36B31FCE8945A 
        foreign key (consent_id) 
        references consent (id)
;

    alter table individual_provider 
        add index FKDB6670D7B2036D5 (patient), 
        add constraint FKDB6670D7B2036D5 
        foreign key (patient) 
        references patient (id)
;

    alter table individual_provider_audit 
        add index FK5D83273CDA971CE (rev), 
        add constraint FK5D83273CDA971CE 
        foreign key (rev) 
        references revinfo (rev)
;

    alter table medication 
        add index FK7725CACF5AC0E7AD (body_site_code), 
        add constraint FK7725CACF5AC0E7AD 
        foreign key (body_site_code) 
        references body_site_code (id)
;

    alter table medication 
        add index FK7725CACFB2036D5 (patient), 
        add constraint FK7725CACFB2036D5 
        foreign key (patient) 
        references patient (id)
;

    alter table medication 
        add index FK7725CACF71897B67 (medication_status_code), 
        add constraint FK7725CACF71897B67 
        foreign key (medication_status_code) 
        references medication_status_code (id)
;

    alter table medication 
        add index FK7725CACF9758ABCA (route_code), 
        add constraint FK7725CACF9758ABCA 
        foreign key (route_code) 
        references route_code (id)
;

    alter table medication 
        add index FK7725CACF7196209C (unit_of_measure_code), 
        add constraint FK7725CACF7196209C 
        foreign key (unit_of_measure_code) 
        references unit_of_measure_code (id)
;

    alter table medication 
        add index FK7725CACFC5136529 (product_form_code), 
        add constraint FK7725CACFC5136529 
        foreign key (product_form_code) 
        references product_form_code (id)
;

    alter table modified_entity_type_entity 
        add index FK9D17762BE1C7106 (revision), 
        add constraint FK9D17762BE1C7106 
        foreign key (revision) 
        references revinfo (rev)
;

    alter table organizational_provider 
        add index FK29E59072B2036D5 (patient), 
        add constraint FK29E59072B2036D5 
        foreign key (patient) 
        references patient (id)
;

    alter table organizational_provider_audit 
        add index FK21A9F1CECDA971CE (rev), 
        add constraint FK21A9F1CECDA971CE 
        foreign key (rev) 
        references revinfo (rev)
;

    alter table patient 
        add index FKD0D3EB05185F3145 (administrative_gender_code), 
        add constraint FKD0D3EB05185F3145 
        foreign key (administrative_gender_code) 
        references administrative_gender_code (id)
;

    alter table patient 
        add index FKD0D3EB05A04C552A (country_code), 
        add constraint FKD0D3EB05A04C552A 
        foreign key (country_code) 
        references country_code (id)
;

    alter table patient 
        add index FKD0D3EB05FBB203DF (address_use_code), 
        add constraint FKD0D3EB05FBB203DF 
        foreign key (address_use_code) 
        references address_use_code (id)
;

    alter table patient 
        add index FKD0D3EB05BE1326B1 (religious_affiliation_code), 
        add constraint FKD0D3EB05BE1326B1 
        foreign key (religious_affiliation_code) 
        references religious_affiliation_code (id)
;

    alter table patient 
        add index FKD0D3EB05CD0E74CA (state_code), 
        add constraint FKD0D3EB05CD0E74CA 
        foreign key (state_code) 
        references state_code (id)
;

    alter table patient 
        add index FKD0D3EB05C6E57D2B (ethnic_group_code), 
        add constraint FKD0D3EB05C6E57D2B 
        foreign key (ethnic_group_code) 
        references ethnic_group_code (id)
;

    alter table patient 
        add index FKD0D3EB052D37BD33 (marital_status_code), 
        add constraint FKD0D3EB052D37BD33 
        foreign key (marital_status_code) 
        references marital_status_code (id)
;

    alter table patient 
        add index FKD0D3EB0590C96B08 (race_code), 
        add constraint FKD0D3EB0590C96B08 
        foreign key (race_code) 
        references race_code (id)
;

    alter table patient 
        add index FKD0D3EB059FF68A99 (telecom_use_code), 
        add constraint FKD0D3EB059FF68A99 
        foreign key (telecom_use_code) 
        references telecom_use_code (id)
;

    alter table patient 
        add index FKD0D3EB05CC866C68 (language_code), 
        add constraint FKD0D3EB05CC866C68 
        foreign key (language_code) 
        references language_code (id)
;

    alter table patient_audit 
        add index FKAF20D221CDA971CE (rev), 
        add constraint FKAF20D221CDA971CE 
        foreign key (rev) 
        references revinfo (rev)
;

    alter table patient_legal_representative_association 
        add index FKE2CB081FB2036D5 (patient), 
        add constraint FKE2CB081FB2036D5 
        foreign key (patient) 
        references patient (id)
;

    alter table patient_legal_representative_association 
        add index FKE2CB081F17D0C7B3 (legal_representative), 
        add constraint FKE2CB081F17D0C7B3 
        foreign key (legal_representative) 
        references patient (id)
;

    alter table patient_legal_representative_association 
        add index FKE2CB081F19521D24 (legal_representative_type_code), 
        add constraint FKE2CB081F19521D24 
        foreign key (legal_representative_type_code) 
        references legal_representative_type_code (id)
;

    alter table patient_legal_representative_association_aud 
        add index FKF7E88A90CDA971CE (rev), 
        add constraint FKF7E88A90CDA971CE 
        foreign key (rev) 
        references revinfo (rev)
;

    alter table patient_patient_legal_representative_associations 
        add index FKD92A077A31918E0F (patient_legal_representative_associations), 
        add constraint FKD92A077A31918E0F 
        foreign key (patient_legal_representative_associations) 
        references patient_legal_representative_association (id)
;

    alter table patient_patient_legal_representative_associations 
        add index FKD92A077AB2036D5 (patient), 
        add constraint FKD92A077AB2036D5 
        foreign key (patient) 
        references patient (id)
;

    alter table patient_patient_legal_representative_associations_aud 
        add index FK9DB2646BCDA971CE (rev), 
        add constraint FK9DB2646BCDA971CE 
        foreign key (rev) 
        references revinfo (rev)
;

    alter table problem 
        add index FKED8CC29FB2036D5 (patient), 
        add constraint FKED8CC29FB2036D5 
        foreign key (patient) 
        references patient (id)
;

    alter table problem 
        add index FKED8CC29F1FA8BE49 (problem_status_code), 
        add constraint FKED8CC29F1FA8BE49 
        foreign key (problem_status_code) 
        references problem_status_code (id)
;

    alter table procedure_observation 
        add index FK6C3B14C0B2036D5 (patient), 
        add constraint FK6C3B14C0B2036D5 
        foreign key (patient) 
        references patient (id)
;

    alter table procedure_observation 
        add index FK6C3B14C0EAEC320B (target_site_code), 
        add constraint FK6C3B14C0EAEC320B 
        foreign key (target_site_code) 
        references target_site_code (id)
;

    alter table procedure_observation 
        add index FK6C3B14C0A1E2EF71 (procedure_status_code), 
        add constraint FK6C3B14C0A1E2EF71 
        foreign key (procedure_status_code) 
        references procedure_status_code (id)
;

    alter table procedure_observation_procedure_performer 
        add index FKB39BB363D5ED511D (procedure_performer), 
        add constraint FKB39BB363D5ED511D 
        foreign key (procedure_performer) 
        references individual_provider (id)
;

    alter table procedure_observation_procedure_performer 
        add index FKB39BB363561D6A1E (procedure_observation), 
        add constraint FKB39BB363561D6A1E 
        foreign key (procedure_observation) 
        references procedure_observation (id)
;

    alter table provider_admin 
        add index FK4DAC7381185F3145 (administrative_gender_code), 
        add constraint FK4DAC7381185F3145 
        foreign key (administrative_gender_code) 
        references administrative_gender_code (id)
;

    alter table provider_admin_aud 
        add index FKF38174F2CDA971CE (rev), 
        add constraint FKF38174F2CDA971CE 
        foreign key (rev) 
        references revinfo (rev)
;

    alter table result_observation 
        add index FKF0BB0AB2036D5 (patient), 
        add constraint FKF0BB0AB2036D5 
        foreign key (patient) 
        references patient (id)
;

    alter table result_observation 
        add index FKF0BB0A6F829FC3 (result_interpretation_code), 
        add constraint FKF0BB0A6F829FC3 
        foreign key (result_interpretation_code) 
        references result_interpretation_code (id)
;

    alter table result_observation 
        add index FKF0BB0AACA94703 (result_status_code), 
        add constraint FKF0BB0AACA94703 
        foreign key (result_status_code) 
        references result_status_code (id)
;

    alter table result_observation 
        add index FKF0BB0A7196209C (unit_of_measure_code), 
        add constraint FKF0BB0A7196209C 
        foreign key (unit_of_measure_code) 
        references unit_of_measure_code (id)
;

    alter table social_history 
        add index FKCD73E5C2B2036D5 (patient), 
        add constraint FKCD73E5C2B2036D5 
        foreign key (patient) 
        references patient (id)
;

    alter table social_history 
        add index FKCD73E5C23100CBB4 (social_history_type_code), 
        add constraint FKCD73E5C23100CBB4 
        foreign key (social_history_type_code) 
        references social_history_type_code (id)
;

    alter table social_history 
        add index FKCD73E5C22534E7F4 (social_history_status_code), 
        add constraint FKCD73E5C22534E7F4 
        foreign key (social_history_status_code) 
        references social_history_status_code (id)
;

