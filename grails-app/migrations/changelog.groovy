databaseChangeLog = {

	changeSet(author: "christopher (generated)", id: "1371502173665-1") {
		dropForeignKeyConstraint(baseTableName: "user_profile_phone_number", baseTableSchemaName: "cogda_prod", constraintName: "FKAC49EA0492B433BC")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-2") {
		dropForeignKeyConstraint(baseTableName: "user_role", baseTableSchemaName: "cogda_prod", constraintName: "FK143BF46A6CFF89E1")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-3") {
		dropForeignKeyConstraint(baseTableName: "user_role", baseTableSchemaName: "cogda_prod", constraintName: "FK143BF46A122A4DC1")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-4") {
		dropIndex(indexName: "FK38A73C7D52AB5D81", schemaName: "cogda_prod", tableName: "company")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-5") {
		dropIndex(indexName: "account_id", schemaName: "cogda_prod", tableName: "company")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-6") {
		dropIndex(indexName: "FKF19768BE36353ECC", schemaName: "cogda_prod", tableName: "company_office")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-7") {
		dropIndex(indexName: "FKF19768BE4B012B16", schemaName: "cogda_prod", tableName: "company_office")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-8") {
		dropIndex(indexName: "FKB66CE91B231BD754", schemaName: "cogda_prod", tableName: "company_office_phone_number")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-9") {
		dropIndex(indexName: "FKB66CE91BF53D6690", schemaName: "cogda_prod", tableName: "company_office_phone_number")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-10") {
		dropIndex(indexName: "FK8B339FE736353ECC", schemaName: "cogda_prod", tableName: "company_profile")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-11") {
		dropIndex(indexName: "FK8B339FE75814B4EF", schemaName: "cogda_prod", tableName: "company_profile")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-12") {
		dropIndex(indexName: "FK3B0C0B9C5C0A9446", schemaName: "cogda_prod", tableName: "company_profile_address")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-13") {
		dropIndex(indexName: "FK755E43F95C0A9446", schemaName: "cogda_prod", tableName: "company_profile_email_address")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-14") {
		dropIndex(indexName: "FK6D275C925C0A9446", schemaName: "cogda_prod", tableName: "company_profile_phone_number")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-15") {
		dropIndex(indexName: "FK6C806DA536353ECC", schemaName: "cogda_prod", tableName: "company_settings")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-16") {
		dropIndex(indexName: "code", schemaName: "cogda_prod", tableName: "company_type")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-17") {
		dropIndex(indexName: "FK38B7242092B433BC", schemaName: "cogda_prod", tableName: "contact")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-18") {
		dropIndex(indexName: "FK38B72420C1435113", schemaName: "cogda_prod", tableName: "contact")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-19") {
		dropIndex(indexName: "FK38B72420DBD78BA1", schemaName: "cogda_prod", tableName: "contact")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-20") {
		dropIndex(indexName: "account_id", schemaName: "cogda_prod", tableName: "customer_account")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-21") {
		dropIndex(indexName: "sub_domain", schemaName: "cogda_prod", tableName: "customer_account")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-22") {
		dropIndex(indexName: "name", schemaName: "cogda_prod", tableName: "html_fragment")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-23") {
		dropIndex(indexName: "code", schemaName: "cogda_prod", tableName: "naics_code")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-24") {
		dropIndex(indexName: "FKAF83E8B91A0B0A00", schemaName: "cogda_prod", tableName: "registration")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-25") {
		dropIndex(indexName: "FKAF83E8B95814B4EF", schemaName: "cogda_prod", tableName: "registration")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-26") {
		dropIndex(indexName: "FK7DDBB4C3937D840F", schemaName: "cogda_prod", tableName: "registration_email_confirmation_log")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-27") {
		dropIndex(indexName: "FK7DDBB4C3C36D95D6", schemaName: "cogda_prod", tableName: "registration_email_confirmation_log")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-28") {
		dropIndex(indexName: "tenant_id", schemaName: "cogda_prod", tableName: "role")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-29") {
		dropIndex(indexName: "code", schemaName: "cogda_prod", tableName: "sic_code")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-30") {
		dropIndex(indexName: "FK111CF7512F11D279", schemaName: "cogda_prod", tableName: "sic_code_division_sic_code")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-31") {
		dropIndex(indexName: "FK111CF751AF7AF9AF", schemaName: "cogda_prod", tableName: "sic_code_division_sic_code")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-32") {
		dropIndex(indexName: "country_code", schemaName: "cogda_prod", tableName: "supported_country_code")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-33") {
		dropIndex(indexName: "title", schemaName: "cogda_prod", tableName: "system_email_message")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-34") {
		dropIndex(indexName: "title", schemaName: "cogda_prod", tableName: "system_email_message_template")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-35") {
		dropIndex(indexName: "account_id", schemaName: "cogda_prod", tableName: "user")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-36") {
		dropIndex(indexName: "tenant_id", schemaName: "cogda_prod", tableName: "user")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-37") {
		dropIndex(indexName: "FK487E2135122A4DC1", schemaName: "cogda_prod", tableName: "user_profile")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-38") {
		dropIndex(indexName: "FK487E213536353ECC", schemaName: "cogda_prod", tableName: "user_profile")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-39") {
		dropIndex(indexName: "FKB71F5EEA92B433BC", schemaName: "cogda_prod", tableName: "user_profile_address")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-40") {
		dropIndex(indexName: "FK1A8D64C792B433BC", schemaName: "cogda_prod", tableName: "user_profile_email_address")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-41") {
		dropTable(schemaName: "cogda_prod", tableName: "address")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-42") {
		dropTable(schemaName: "cogda_prod", tableName: "company")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-43") {
		dropTable(schemaName: "cogda_prod", tableName: "company_office")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-44") {
		dropTable(schemaName: "cogda_prod", tableName: "company_office_phone_number")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-45") {
		dropTable(schemaName: "cogda_prod", tableName: "company_profile")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-46") {
		dropTable(schemaName: "cogda_prod", tableName: "company_profile_address")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-47") {
		dropTable(schemaName: "cogda_prod", tableName: "company_profile_email_address")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-48") {
		dropTable(schemaName: "cogda_prod", tableName: "company_profile_phone_number")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-49") {
		dropTable(schemaName: "cogda_prod", tableName: "company_settings")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-50") {
		dropTable(schemaName: "cogda_prod", tableName: "company_type")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-51") {
		dropTable(schemaName: "cogda_prod", tableName: "contact")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-52") {
		dropTable(schemaName: "cogda_prod", tableName: "customer_account")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-53") {
		dropTable(schemaName: "cogda_prod", tableName: "email_confirmation_log")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-54") {
		dropTable(schemaName: "cogda_prod", tableName: "file_reference")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-55") {
		dropTable(schemaName: "cogda_prod", tableName: "html_fragment")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-56") {
		dropTable(schemaName: "cogda_prod", tableName: "naics_code")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-57") {
		dropTable(schemaName: "cogda_prod", tableName: "password_code")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-58") {
		dropTable(schemaName: "cogda_prod", tableName: "phone_number")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-59") {
		dropTable(schemaName: "cogda_prod", tableName: "registration")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-60") {
		dropTable(schemaName: "cogda_prod", tableName: "registration_email_confirmation_log")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-61") {
		dropTable(schemaName: "cogda_prod", tableName: "role")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-62") {
		dropTable(schemaName: "cogda_prod", tableName: "sic_code")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-63") {
		dropTable(schemaName: "cogda_prod", tableName: "sic_code_division")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-64") {
		dropTable(schemaName: "cogda_prod", tableName: "sic_code_division_sic_code")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-65") {
		dropTable(schemaName: "cogda_prod", tableName: "supported_country_code")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-66") {
		dropTable(schemaName: "cogda_prod", tableName: "system_email_message")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-67") {
		dropTable(schemaName: "cogda_prod", tableName: "system_email_message_required_parameter_names")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-68") {
		dropTable(schemaName: "cogda_prod", tableName: "system_email_message_template")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-69") {
		dropTable(schemaName: "cogda_prod", tableName: "system_email_message_template_required_parameter_names")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-70") {
		dropTable(schemaName: "cogda_prod", tableName: "user")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-71") {
		dropTable(schemaName: "cogda_prod", tableName: "user_profile")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-72") {
		dropTable(schemaName: "cogda_prod", tableName: "user_profile_address")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-73") {
		dropTable(schemaName: "cogda_prod", tableName: "user_profile_email_address")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-74") {
		dropTable(schemaName: "cogda_prod", tableName: "user_profile_phone_number")
	}

	changeSet(author: "christopher (generated)", id: "1371502173665-75") {
		dropTable(schemaName: "cogda_prod", tableName: "user_role")
	}
}
