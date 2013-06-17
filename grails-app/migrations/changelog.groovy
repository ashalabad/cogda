databaseChangeLog = {

	changeSet(author: "christopher (generated)", id: "1371476834454-1") {
		createTable(tableName: "address") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "address_one", type: "VARCHAR(255)")

			column(name: "address_three", type: "VARCHAR(255)")

			column(name: "address_two", type: "VARCHAR(255)")

			column(name: "city", type: "VARCHAR(255)")

			column(name: "country", type: "VARCHAR(255)")

			column(name: "county", type: "VARCHAR(255)")

			column(name: "latitude", type: "DECIMAL(19,2)")

			column(name: "longitude", type: "DECIMAL(19,2)")

			column(name: "state", type: "VARCHAR(255)")

			column(name: "zipcode", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-2") {
		createTable(tableName: "company") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "account_id", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "company_name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "doing_business_as", type: "VARCHAR(255)")

			column(name: "int_code", type: "INT")

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "parent_company_id", type: "BIGINT")

			column(name: "tenant_id", type: "INT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-3") {
		createTable(tableName: "company_office") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "company_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "main_office", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "office_address_id", type: "BIGINT")

			column(name: "office_name", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-4") {
		createTable(tableName: "company_office_phone_number") {
			column(name: "company_office_phone_numbers_id", type: "BIGINT")

			column(name: "phone_number_id", type: "BIGINT")
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-5") {
		createTable(tableName: "company_profile") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "am_best_number", type: "VARCHAR(255)")

			column(name: "company_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "company_description", type: "LONGTEXT")

			column(name: "company_type_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "company_website", type: "VARCHAR(255)")

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "published", type: "BIT")

			column(name: "year_founded", type: "DATETIME")
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-6") {
		createTable(tableName: "company_profile_address") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "address_address_one", type: "VARCHAR(255)")

			column(name: "address_address_three", type: "VARCHAR(255)")

			column(name: "address_address_two", type: "VARCHAR(255)")

			column(name: "address_city", type: "VARCHAR(255)")

			column(name: "address_country", type: "VARCHAR(255)")

			column(name: "address_county", type: "VARCHAR(255)")

			column(name: "address_latitude", type: "DECIMAL(19,2)")

			column(name: "address_longitude", type: "DECIMAL(19,2)")

			column(name: "address_state", type: "VARCHAR(255)")

			column(name: "address_zipcode", type: "VARCHAR(255)")

			column(name: "address_type", type: "VARCHAR(255)")

			column(name: "company_profile_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "primary_address", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "published", type: "BIT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-7") {
		createTable(tableName: "company_profile_email_address") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "company_profile_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "primary_email_address", type: "BIT")

			column(name: "published", type: "BIT")
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-8") {
		createTable(tableName: "company_profile_phone_number") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "company_profile_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "phone_number_phone_number", type: "VARCHAR(255)")

			column(name: "primary_phone_number", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "published", type: "BIT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-9") {
		createTable(tableName: "company_settings") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "allow_view_of_child_submissions", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "business_type_required", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "client_id_required", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "company_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "contact_email_required", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "contact_first_name_required", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "contact_last_name_required", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "contact_phone_required", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "customer_service_required", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "fein_number_required", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "logout_notification", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "make_sub_status_notification_as_not_read", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "naics_code_required", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "remind_user_to_update_my_status", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "sic_code_required", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "target_date_required", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "target_premium_required", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "tenant_id", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "writing_company_required", type: "BIT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-10") {
		createTable(tableName: "company_type") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "code", type: "VARCHAR(50)") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "VARCHAR(255)")

			column(name: "int_code", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-11") {
		createTable(tableName: "contact") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "company_name", type: "VARCHAR(255)")

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "date_of_birth", type: "DATETIME")

			column(name: "first_name", type: "VARCHAR(255)")

			column(name: "gender", type: "VARCHAR(255)")

			column(name: "home_address_id", type: "BIGINT")

			column(name: "initials", type: "VARCHAR(255)")

			column(name: "job_title", type: "VARCHAR(255)")

			column(name: "last_name", type: "VARCHAR(255)")

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "middle_name", type: "VARCHAR(255)")

			column(name: "title", type: "VARCHAR(255)")

			column(name: "user_profile_id", type: "BIGINT")

			column(name: "website", type: "VARCHAR(255)")

			column(name: "work_address_id", type: "BIGINT")
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-12") {
		createTable(tableName: "customer_account") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "account_id", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "sub_domain", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-13") {
		createTable(tableName: "email_confirmation_log") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "email_body", type: "LONGTEXT")

			column(name: "email_errors", type: "LONGTEXT")

			column(name: "email_from", type: "VARCHAR(255)")

			column(name: "email_send_reason", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "email_send_status", type: "VARCHAR(255)")

			column(name: "email_subject", type: "VARCHAR(255)")

			column(name: "email_to", type: "VARCHAR(255)")

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-14") {
		createTable(tableName: "file_reference") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-15") {
		createTable(tableName: "html_fragment") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "VARCHAR(255)")

			column(name: "html", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "markup_language", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-16") {
		createTable(tableName: "naics_code") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "code", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-17") {
		createTable(tableName: "password_code") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "token", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "username", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-18") {
		createTable(tableName: "phone_number") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "phone_number", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-19") {
		createTable(tableName: "registration") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "city", type: "VARCHAR(255)")

			column(name: "company_name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "company_type_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "company_type_other", type: "VARCHAR(255)")

			column(name: "country", type: "VARCHAR(255)")

			column(name: "county", type: "VARCHAR(255)")

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "email_address", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "existing_company_id", type: "BIGINT")

			column(name: "first_name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "new_company", type: "BIT")

			column(name: "password", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "phone_number", type: "VARCHAR(255)")

			column(name: "registration_status", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "state", type: "VARCHAR(255)")

			column(name: "street_address_one", type: "VARCHAR(255)")

			column(name: "street_address_three", type: "VARCHAR(255)")

			column(name: "street_address_two", type: "VARCHAR(255)")

			column(name: "sub_domain", type: "VARCHAR(255)")

			column(name: "token", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "username", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "zipcode", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-20") {
		createTable(tableName: "registration_email_confirmation_log") {
			column(name: "registration_email_confirmation_logs_id", type: "BIGINT")

			column(name: "email_confirmation_log_id", type: "BIGINT")
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-21") {
		createTable(tableName: "role") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "authority", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "VARCHAR(250)")

			column(name: "system_role", type: "BIT")

			column(name: "tenant_id", type: "INT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-22") {
		createTable(tableName: "sic_code") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "code", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-23") {
		createTable(tableName: "sic_code_division") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "division_code", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "division_description", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-24") {
		createTable(tableName: "sic_code_division_sic_code") {
			column(name: "sic_code_division_sic_codes_id", type: "BIGINT")

			column(name: "sic_code_id", type: "BIGINT")
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-25") {
		createTable(tableName: "supported_country_code") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "country_code", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "country_description", type: "VARCHAR(255)")

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-26") {
		createTable(tableName: "system_email_message") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "accepts_parameters", type: "BIT")

			column(name: "body", type: "LONGTEXT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "from_email", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "markup_language", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "subject", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "title", type: "VARCHAR(35)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-27") {
		createTable(tableName: "system_email_message_required_parameter_names") {
			column(name: "system_email_message_id", type: "BIGINT")

			column(name: "required_parameter_names_string", type: "VARCHAR(255)")

			column(name: "required_parameter_names_idx", type: "INT")
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-28") {
		createTable(tableName: "system_email_message_template") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "accepts_parameters", type: "BIT")

			column(name: "body", type: "LONGTEXT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "from_email", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "markup_language", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "subject", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "title", type: "VARCHAR(35)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-29") {
		createTable(tableName: "system_email_message_template_required_parameter_names") {
			column(name: "system_email_message_template_id", type: "BIGINT")

			column(name: "required_parameter_names_string", type: "VARCHAR(255)")

			column(name: "required_parameter_names_idx", type: "INT")
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-30") {
		createTable(tableName: "user") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "account_expired", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "account_id", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "account_locked", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "enabled", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "encode_password", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "password", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "password_expired", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "tenant_id", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "username", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-31") {
		createTable(tableName: "user_profile") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "about_desc", type: "LONGTEXT")

			column(name: "associations_desc", type: "LONGTEXT")

			column(name: "business_specialties_desc", type: "LONGTEXT")

			column(name: "company_id", type: "BIGINT")

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "first_name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "middle_name", type: "VARCHAR(255)")

			column(name: "published", type: "BIT")

			column(name: "user_id", type: "BIGINT")
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-32") {
		createTable(tableName: "user_profile_address") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "address_address_one", type: "VARCHAR(255)")

			column(name: "address_address_three", type: "VARCHAR(255)")

			column(name: "address_address_two", type: "VARCHAR(255)")

			column(name: "address_city", type: "VARCHAR(255)")

			column(name: "address_country", type: "VARCHAR(255)")

			column(name: "address_county", type: "VARCHAR(255)")

			column(name: "address_latitude", type: "DECIMAL(19,2)")

			column(name: "address_longitude", type: "DECIMAL(19,2)")

			column(name: "address_state", type: "VARCHAR(255)")

			column(name: "address_zipcode", type: "VARCHAR(255)")

			column(name: "address_type", type: "VARCHAR(255)")

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "primary_address", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "published", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "user_profile_id", type: "BIGINT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-33") {
		createTable(tableName: "user_profile_email_address") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "email_address", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "primary_email_address", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "published", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "user_profile_id", type: "BIGINT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-34") {
		createTable(tableName: "user_profile_phone_number") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "VARCHAR(255)")

			column(name: "phone_number_phone_number", type: "VARCHAR(255)")

			column(name: "primary_phone_number", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "published", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "user_profile_id", type: "BIGINT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-35") {
		createTable(tableName: "user_role") {
			column(name: "role_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "user_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "tenant_id", type: "INT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-36") {
		addPrimaryKey(columnNames: "role_id, user_id", tableName: "user_role")
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-64") {
		createIndex(indexName: "account_id", tableName: "company", unique: "true") {
			column(name: "account_id")
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-65") {
		createIndex(indexName: "code", tableName: "company_type", unique: "true") {
			column(name: "code")
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-66") {
		createIndex(indexName: "account_id", tableName: "customer_account", unique: "true") {
			column(name: "account_id")
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-67") {
		createIndex(indexName: "sub_domain", tableName: "customer_account", unique: "true") {
			column(name: "sub_domain")
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-68") {
		createIndex(indexName: "name", tableName: "html_fragment", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-69") {
		createIndex(indexName: "code", tableName: "naics_code", unique: "true") {
			column(name: "code")
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-70") {
		createIndex(indexName: "tenant_id", tableName: "role", unique: "true") {
			column(name: "tenant_id")

			column(name: "authority")
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-71") {
		createIndex(indexName: "code", tableName: "sic_code", unique: "true") {
			column(name: "code")
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-72") {
		createIndex(indexName: "country_code", tableName: "supported_country_code", unique: "true") {
			column(name: "country_code")
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-73") {
		createIndex(indexName: "title", tableName: "system_email_message", unique: "true") {
			column(name: "title")
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-74") {
		createIndex(indexName: "title", tableName: "system_email_message_template", unique: "true") {
			column(name: "title")
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-75") {
		createIndex(indexName: "account_id", tableName: "user", unique: "true") {
			column(name: "account_id")
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-76") {
		createIndex(indexName: "tenant_id", tableName: "user", unique: "true") {
			column(name: "tenant_id")

			column(name: "username")
		}
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-37") {
		addForeignKeyConstraint(baseColumnNames: "parent_company_id", baseTableName: "company", baseTableSchemaName: "cogda_dev", constraintName: "FK38A73C7D52AB5D81", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "company", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-38") {
		addForeignKeyConstraint(baseColumnNames: "company_id", baseTableName: "company_office", baseTableSchemaName: "cogda_dev", constraintName: "FKF19768BE36353ECC", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "company", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-39") {
		addForeignKeyConstraint(baseColumnNames: "office_address_id", baseTableName: "company_office", baseTableSchemaName: "cogda_dev", constraintName: "FKF19768BE4B012B16", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "address", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-40") {
		addForeignKeyConstraint(baseColumnNames: "company_office_phone_numbers_id", baseTableName: "company_office_phone_number", baseTableSchemaName: "cogda_dev", constraintName: "FKB66CE91B231BD754", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "company_office", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-41") {
		addForeignKeyConstraint(baseColumnNames: "phone_number_id", baseTableName: "company_office_phone_number", baseTableSchemaName: "cogda_dev", constraintName: "FKB66CE91BF53D6690", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "phone_number", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-42") {
		addForeignKeyConstraint(baseColumnNames: "company_id", baseTableName: "company_profile", baseTableSchemaName: "cogda_dev", constraintName: "FK8B339FE736353ECC", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "company", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-43") {
		addForeignKeyConstraint(baseColumnNames: "company_type_id", baseTableName: "company_profile", baseTableSchemaName: "cogda_dev", constraintName: "FK8B339FE75814B4EF", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "company_type", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-44") {
		addForeignKeyConstraint(baseColumnNames: "company_profile_id", baseTableName: "company_profile_address", baseTableSchemaName: "cogda_dev", constraintName: "FK3B0C0B9C5C0A9446", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "company_profile", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-45") {
		addForeignKeyConstraint(baseColumnNames: "company_profile_id", baseTableName: "company_profile_email_address", baseTableSchemaName: "cogda_dev", constraintName: "FK755E43F95C0A9446", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "company_profile", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-46") {
		addForeignKeyConstraint(baseColumnNames: "company_profile_id", baseTableName: "company_profile_phone_number", baseTableSchemaName: "cogda_dev", constraintName: "FK6D275C925C0A9446", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "company_profile", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-47") {
		addForeignKeyConstraint(baseColumnNames: "company_id", baseTableName: "company_settings", baseTableSchemaName: "cogda_dev", constraintName: "FK6C806DA536353ECC", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "company", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-48") {
		addForeignKeyConstraint(baseColumnNames: "home_address_id", baseTableName: "contact", baseTableSchemaName: "cogda_dev", constraintName: "FK38B72420C1435113", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "address", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-49") {
		addForeignKeyConstraint(baseColumnNames: "user_profile_id", baseTableName: "contact", baseTableSchemaName: "cogda_dev", constraintName: "FK38B7242092B433BC", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "user_profile", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-50") {
		addForeignKeyConstraint(baseColumnNames: "work_address_id", baseTableName: "contact", baseTableSchemaName: "cogda_dev", constraintName: "FK38B72420DBD78BA1", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "address", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-51") {
		addForeignKeyConstraint(baseColumnNames: "company_type_id", baseTableName: "registration", baseTableSchemaName: "cogda_dev", constraintName: "FKAF83E8B95814B4EF", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "company_type", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-52") {
		addForeignKeyConstraint(baseColumnNames: "existing_company_id", baseTableName: "registration", baseTableSchemaName: "cogda_dev", constraintName: "FKAF83E8B91A0B0A00", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "company", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-53") {
		addForeignKeyConstraint(baseColumnNames: "email_confirmation_log_id", baseTableName: "registration_email_confirmation_log", baseTableSchemaName: "cogda_dev", constraintName: "FK7DDBB4C3C36D95D6", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "email_confirmation_log", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-54") {
		addForeignKeyConstraint(baseColumnNames: "registration_email_confirmation_logs_id", baseTableName: "registration_email_confirmation_log", baseTableSchemaName: "cogda_dev", constraintName: "FK7DDBB4C3937D840F", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "registration", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-55") {
		addForeignKeyConstraint(baseColumnNames: "sic_code_division_sic_codes_id", baseTableName: "sic_code_division_sic_code", baseTableSchemaName: "cogda_dev", constraintName: "FK111CF7512F11D279", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "sic_code_division", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-56") {
		addForeignKeyConstraint(baseColumnNames: "sic_code_id", baseTableName: "sic_code_division_sic_code", baseTableSchemaName: "cogda_dev", constraintName: "FK111CF751AF7AF9AF", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "sic_code", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-57") {
		addForeignKeyConstraint(baseColumnNames: "company_id", baseTableName: "user_profile", baseTableSchemaName: "cogda_dev", constraintName: "FK487E213536353ECC", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "company", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-58") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "user_profile", baseTableSchemaName: "cogda_dev", constraintName: "FK487E2135122A4DC1", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "user", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-59") {
		addForeignKeyConstraint(baseColumnNames: "user_profile_id", baseTableName: "user_profile_address", baseTableSchemaName: "cogda_dev", constraintName: "FKB71F5EEA92B433BC", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "user_profile", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-60") {
		addForeignKeyConstraint(baseColumnNames: "user_profile_id", baseTableName: "user_profile_email_address", baseTableSchemaName: "cogda_dev", constraintName: "FK1A8D64C792B433BC", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "user_profile", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-61") {
		addForeignKeyConstraint(baseColumnNames: "user_profile_id", baseTableName: "user_profile_phone_number", baseTableSchemaName: "cogda_dev", constraintName: "FKAC49EA0492B433BC", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "user_profile", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-62") {
		addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "user_role", baseTableSchemaName: "cogda_dev", constraintName: "FK143BF46A6CFF89E1", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "role", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1371476834454-63") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "user_role", baseTableSchemaName: "cogda_dev", constraintName: "FK143BF46A122A4DC1", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "user", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}
}
