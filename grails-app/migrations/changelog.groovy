databaseChangeLog = {

	changeSet(author: "christopher (generated)", id: "1375269875934-1") {
		createTable(tableName: "account") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "account_code", type: "VARCHAR(255)")

			column(name: "account_name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "account_type_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "active", type: "BIT")

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "favorite", type: "BIT")

			column(name: "is_market", type: "BIT")

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "tenant_id", type: "INT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-2") {
		createTable(tableName: "account_address") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "account_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "account_address_type_id", type: "BIGINT") {
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

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "primary_address", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "tenant_id", type: "INT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-3") {
		createTable(tableName: "account_address_type") {
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

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-4") {
		createTable(tableName: "account_company_owner") {
			column(name: "company_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "account_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "tenant_id", type: "INT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-5") {
		createTable(tableName: "account_contact") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "active", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "display_as_market_on_builder", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "favorite", type: "BIT") {
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

			column(name: "tenant_id", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "user_profile_id", type: "BIGINT")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-6") {
		createTable(tableName: "account_contact_address") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "account_contact_id", type: "BIGINT") {
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

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "primary_address", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "tenant_id", type: "INT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-7") {
		createTable(tableName: "account_contact_email_address") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "account_contact_id", type: "BIGINT") {
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

			column(name: "tenant_id", type: "INT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-8") {
		createTable(tableName: "account_contact_link") {
			column(name: "account_contact_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "account_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "link_id", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "primary_contact", type: "BIT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-9") {
		createTable(tableName: "account_contact_phone_number") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "account_contact_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "VARCHAR(255)")

			column(name: "phone_number", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "primary_phone_number", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "tenant_id", type: "INT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-10") {
		createTable(tableName: "account_note") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "account_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "note_notes", type: "LONGTEXT") {
				constraints(nullable: "false")
			}

			column(name: "note_type_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "tenant_id", type: "INT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-11") {
		createTable(tableName: "account_type") {
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

	changeSet(author: "christopher (generated)", id: "1375269875934-12") {
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

	changeSet(author: "christopher (generated)", id: "1375269875934-13") {
		createTable(tableName: "business_type") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "code", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "code_from", type: "INT")

			column(name: "code_to", type: "INT")

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

	changeSet(author: "christopher (generated)", id: "1375269875934-14") {
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

			column(name: "am_best_number", type: "VARCHAR(255)")

			column(name: "company_name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "company_settings_id", type: "BIGINT")

			column(name: "company_type_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "doing_business_as", type: "VARCHAR(255)")

			column(name: "federal_id_number", type: "VARCHAR(255)")

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

	changeSet(author: "christopher (generated)", id: "1375269875934-15") {
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

	changeSet(author: "christopher (generated)", id: "1375269875934-16") {
		createTable(tableName: "company_office_phone_number") {
			column(name: "company_office_phone_numbers_id", type: "BIGINT")

			column(name: "phone_number_id", type: "BIGINT")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-17") {
		createTable(tableName: "company_profile") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "associations", type: "LONGTEXT")

			column(name: "business_specialties", type: "LONGTEXT")

			column(name: "company_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "company_description", type: "LONGTEXT")

			column(name: "company_website", type: "VARCHAR(255)")

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "published", type: "BIT")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-18") {
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

	changeSet(author: "christopher (generated)", id: "1375269875934-19") {
		createTable(tableName: "company_profile_contact") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "company_profile_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "user_profile_id", type: "BIGINT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-20") {
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

	changeSet(author: "christopher (generated)", id: "1375269875934-21") {
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

			column(name: "country_code", type: "VARCHAR(255)")

			column(name: "label", type: "VARCHAR(255)")

			column(name: "phone_number", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "primary_phone_number", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "published", type: "BIT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-22") {
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

	changeSet(author: "christopher (generated)", id: "1375269875934-23") {
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

	changeSet(author: "christopher (generated)", id: "1375269875934-24") {
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

			column(name: "first_name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "gender", type: "VARCHAR(255)")

			column(name: "initials", type: "VARCHAR(255)")

			column(name: "job_title", type: "VARCHAR(255)")

			column(name: "last_name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "middle_name", type: "VARCHAR(255)")

			column(name: "title", type: "VARCHAR(255)")

			column(name: "user_profile_id", type: "BIGINT")

			column(name: "website", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-25") {
		createTable(tableName: "contact_address") {
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

			column(name: "contact_id", type: "BIGINT") {
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

	changeSet(author: "christopher (generated)", id: "1375269875934-26") {
		createTable(tableName: "contact_email_address") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "contact_id", type: "BIGINT") {
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
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-27") {
		createTable(tableName: "contact_phone_number") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "contact_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "VARCHAR(255)")

			column(name: "phone_number", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "primary_phone_number", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "published", type: "BIT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-28") {
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

	changeSet(author: "christopher (generated)", id: "1375269875934-29") {
		createTable(tableName: "document") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-30") {
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

	changeSet(author: "christopher (generated)", id: "1375269875934-31") {
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

	changeSet(author: "christopher (generated)", id: "1375269875934-32") {
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

	changeSet(author: "christopher (generated)", id: "1375269875934-33") {
		createTable(tableName: "lead") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "business_type_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "client_id", type: "VARCHAR(50)") {
				constraints(nullable: "false")
			}

			column(name: "client_name", type: "VARCHAR(50)") {
				constraints(nullable: "false")
			}

			column(name: "customer_service_representative", type: "VARCHAR(255)")

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "lead_type", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "sub_type", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "tenant_id", type: "INT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-34") {
		createTable(tableName: "lead_address") {
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

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "lead_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "lead_address_type_id", type: "BIGINT")

			column(name: "primary_address", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "tenant_id", type: "INT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-35") {
		createTable(tableName: "lead_address_type") {
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

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-36") {
		createTable(tableName: "lead_contact") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "first_name", type: "VARCHAR(50)") {
				constraints(nullable: "false")
			}

			column(name: "last_name", type: "VARCHAR(50)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "lead_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "middle_name", type: "VARCHAR(255)")

			column(name: "primary_contact", type: "BIT")

			column(name: "tenant_id", type: "INT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-37") {
		createTable(tableName: "lead_contact_address") {
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

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "lead_contact_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "primary_address", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "tenant_id", type: "INT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-38") {
		createTable(tableName: "lead_contact_email_address") {
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

			column(name: "lead_contact_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "primary_email_address", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "tenant_id", type: "INT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-39") {
		createTable(tableName: "lead_contact_phone_number") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "VARCHAR(255)")

			column(name: "lead_contact_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "phone_number", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "primary_phone_number", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "tenant_id", type: "INT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-40") {
		createTable(tableName: "lead_file_reference") {
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

			column(name: "lead_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "tenant_id", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "lead_files_id", type: "BIGINT")

			column(name: "file_reference_id", type: "BIGINT")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-41") {
		createTable(tableName: "lead_line_of_business") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "commission_rate", type: "DECIMAL(19,2)")

			column(name: "current_carrier", type: "VARCHAR(255)")

			column(name: "expiration_date", type: "DATETIME")

			column(name: "lead_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "line_of_business_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "remarket", type: "BIT")

			column(name: "target_commission", type: "DECIMAL(19,2)")

			column(name: "target_date", type: "DATETIME")

			column(name: "target_premium", type: "DECIMAL(19,2)")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-42") {
		createTable(tableName: "lead_naics_code") {
			column(name: "lead_naics_codes_id", type: "BIGINT")

			column(name: "naics_code_id", type: "BIGINT")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-43") {
		createTable(tableName: "lead_note") {
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

			column(name: "lead_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "note_notes", type: "LONGTEXT") {
				constraints(nullable: "false")
			}

			column(name: "note_type_id", type: "BIGINT")

			column(name: "tenant_id", type: "INT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-44") {
		createTable(tableName: "lead_sic_code") {
			column(name: "lead_sic_codes_id", type: "BIGINT")

			column(name: "sic_code_id", type: "BIGINT")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-45") {
		createTable(tableName: "line_of_business") {
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

			column(name: "line_of_business_category_id", type: "BIGINT")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-46") {
		createTable(tableName: "line_of_business_category") {
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

	changeSet(author: "christopher (generated)", id: "1375269875934-47") {
		createTable(tableName: "naics_code") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "active", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "code", type: "VARCHAR(255)") {
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

			column(name: "level", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "parent_naics_code_id", type: "BIGINT")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-48") {
		createTable(tableName: "note") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "notes", type: "LONGTEXT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-49") {
		createTable(tableName: "note_type") {
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

	changeSet(author: "christopher (generated)", id: "1375269875934-50") {
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

	changeSet(author: "christopher (generated)", id: "1375269875934-51") {
		createTable(tableName: "pending_user") {
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

			column(name: "first_name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "loaded_by_username", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "loaded_date", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "onboarded_date", type: "DATETIME")

			column(name: "onboarded_successfully", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "pending_user_status", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "security_roles", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "tenant_id", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "token", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "username", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-52") {
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

	changeSet(author: "christopher (generated)", id: "1375269875934-53") {
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

	changeSet(author: "christopher (generated)", id: "1375269875934-54") {
		createTable(tableName: "registration_email_confirmation_log") {
			column(name: "registration_email_confirmation_logs_id", type: "BIGINT")

			column(name: "email_confirmation_log_id", type: "BIGINT")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-55") {
		createTable(tableName: "request_for_action") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "created_by_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_completed", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "due_date", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "message", type: "LONGTEXT") {
				constraints(nullable: "false")
			}

			column(name: "parent_request_for_action_id", type: "BIGINT")

			column(name: "request_for_action_status_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "submission_id", type: "BIGINT")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-56") {
		createTable(tableName: "request_for_action_document") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-57") {
		createTable(tableName: "request_for_action_log") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "assignee_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "request_for_action_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "request_for_action_status_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "response", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-58") {
		createTable(tableName: "request_for_action_request_for_action_type") {
			column(name: "request_for_action_request_for_action_types_id", type: "BIGINT")

			column(name: "request_for_action_type_id", type: "BIGINT")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-59") {
		createTable(tableName: "request_for_action_status") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "code", type: "VARCHAR(30)") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "VARCHAR(255)")

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-60") {
		createTable(tableName: "request_for_action_type") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "code", type: "VARCHAR(30)") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "VARCHAR(255)")

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-61") {
		createTable(tableName: "request_for_action_user") {
			column(name: "request_for_action_assignees_id", type: "BIGINT")

			column(name: "user_id", type: "BIGINT")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-62") {
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

	changeSet(author: "christopher (generated)", id: "1375269875934-63") {
		createTable(tableName: "sic_code") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "active", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "code", type: "VARCHAR(255)") {
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

			column(name: "level", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "parent_sic_code_id", type: "BIGINT")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-64") {
		createTable(tableName: "sic_naics_code_crosswalk") {
			column(name: "naics_code_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "sic_code_id", type: "BIGINT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-65") {
		createTable(tableName: "submission") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "created_by_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "lead_id", type: "BIGINT")

			column(name: "parent_submission_id", type: "BIGINT")

			column(name: "submission_id", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-66") {
		createTable(tableName: "submission_account_contact_link") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "account_contact_link_account_contact_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "account_contact_link_account_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "submission_id", type: "BIGINT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-67") {
		createTable(tableName: "submission_lead_line_of_business") {
			column(name: "submission_lead_line_of_businesses_id", type: "BIGINT")

			column(name: "lead_line_of_business_id", type: "BIGINT")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-68") {
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

	changeSet(author: "christopher (generated)", id: "1375269875934-69") {
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

	changeSet(author: "christopher (generated)", id: "1375269875934-70") {
		createTable(tableName: "system_email_message_required_parameter_names") {
			column(name: "system_email_message_id", type: "BIGINT")

			column(name: "required_parameter_names_string", type: "VARCHAR(255)")

			column(name: "required_parameter_names_idx", type: "INT")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-71") {
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

	changeSet(author: "christopher (generated)", id: "1375269875934-72") {
		createTable(tableName: "system_email_message_template_required_parameter_names") {
			column(name: "system_email_message_template_id", type: "BIGINT")

			column(name: "required_parameter_names_string", type: "VARCHAR(255)")

			column(name: "required_parameter_names_idx", type: "INT")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-73") {
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

	changeSet(author: "christopher (generated)", id: "1375269875934-74") {
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

	changeSet(author: "christopher (generated)", id: "1375269875934-75") {
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

	changeSet(author: "christopher (generated)", id: "1375269875934-76") {
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

	changeSet(author: "christopher (generated)", id: "1375269875934-77") {
		createTable(tableName: "user_profile_phone_number") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "VARCHAR(255)")

			column(name: "phone_number", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

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

	changeSet(author: "christopher (generated)", id: "1375269875934-78") {
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

	changeSet(author: "christopher (generated)", id: "1375269875934-79") {
		addPrimaryKey(columnNames: "company_id, account_id", tableName: "account_company_owner")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-80") {
		addPrimaryKey(columnNames: "account_contact_id, account_id", tableName: "account_contact_link")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-81") {
		addPrimaryKey(columnNames: "naics_code_id, sic_code_id", tableName: "sic_naics_code_crosswalk")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-82") {
		addPrimaryKey(columnNames: "role_id, user_id", tableName: "user_role")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-165") {
		createIndex(indexName: "code", tableName: "account_address_type", unique: "true") {
			column(name: "code")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-166") {
		createIndex(indexName: "link_id", tableName: "account_contact_link", unique: "true") {
			column(name: "link_id")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-167") {
		createIndex(indexName: "code", tableName: "account_type", unique: "true") {
			column(name: "code")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-168") {
		createIndex(indexName: "code", tableName: "business_type", unique: "true") {
			column(name: "code")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-169") {
		createIndex(indexName: "account_id", tableName: "company", unique: "true") {
			column(name: "account_id")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-170") {
		createIndex(indexName: "code", tableName: "company_type", unique: "true") {
			column(name: "code")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-171") {
		createIndex(indexName: "account_id", tableName: "customer_account", unique: "true") {
			column(name: "account_id")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-172") {
		createIndex(indexName: "sub_domain", tableName: "customer_account", unique: "true") {
			column(name: "sub_domain")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-173") {
		createIndex(indexName: "name", tableName: "html_fragment", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-174") {
		createIndex(indexName: "code", tableName: "lead_address_type", unique: "true") {
			column(name: "code")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-175") {
		createIndex(indexName: "code", tableName: "line_of_business", unique: "true") {
			column(name: "code")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-176") {
		createIndex(indexName: "code", tableName: "line_of_business_category", unique: "true") {
			column(name: "code")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-177") {
		createIndex(indexName: "parent_naics_code_id", tableName: "naics_code", unique: "true") {
			column(name: "parent_naics_code_id")

			column(name: "code")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-178") {
		createIndex(indexName: "code", tableName: "note_type", unique: "true") {
			column(name: "code")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-179") {
		createIndex(indexName: "code", tableName: "request_for_action_status", unique: "true") {
			column(name: "code")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-180") {
		createIndex(indexName: "code", tableName: "request_for_action_type", unique: "true") {
			column(name: "code")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-181") {
		createIndex(indexName: "tenant_id", tableName: "role", unique: "true") {
			column(name: "tenant_id")

			column(name: "authority")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-182") {
		createIndex(indexName: "parent_sic_code_id", tableName: "sic_code", unique: "true") {
			column(name: "parent_sic_code_id")

			column(name: "code")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-183") {
		createIndex(indexName: "submission_id", tableName: "submission", unique: "true") {
			column(name: "submission_id")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-184") {
		createIndex(indexName: "country_code", tableName: "supported_country_code", unique: "true") {
			column(name: "country_code")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-185") {
		createIndex(indexName: "title", tableName: "system_email_message", unique: "true") {
			column(name: "title")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-186") {
		createIndex(indexName: "title", tableName: "system_email_message_template", unique: "true") {
			column(name: "title")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-187") {
		createIndex(indexName: "account_id", tableName: "user", unique: "true") {
			column(name: "account_id")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-188") {
		createIndex(indexName: "username", tableName: "user", unique: "true") {
			column(name: "username")
		}
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-83") {
		addForeignKeyConstraint(baseColumnNames: "account_type_id", baseTableName: "account", baseTableSchemaName: "cogda_dev", constraintName: "FKB9D38A2DC39E484F", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "account_type", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-84") {
		addForeignKeyConstraint(baseColumnNames: "account_address_type_id", baseTableName: "account_address", baseTableSchemaName: "cogda_dev", constraintName: "FK72D84FE27F25DC52", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "account_address_type", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-85") {
		addForeignKeyConstraint(baseColumnNames: "account_id", baseTableName: "account_address", baseTableSchemaName: "cogda_dev", constraintName: "FK72D84FE2BA0620CC", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "account", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-86") {
		addForeignKeyConstraint(baseColumnNames: "account_id", baseTableName: "account_company_owner", baseTableSchemaName: "cogda_dev", constraintName: "FKC2DF09DFBA0620CC", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "account", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-87") {
		addForeignKeyConstraint(baseColumnNames: "company_id", baseTableName: "account_company_owner", baseTableSchemaName: "cogda_dev", constraintName: "FKC2DF09DF36353ECC", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "company", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-88") {
		addForeignKeyConstraint(baseColumnNames: "user_profile_id", baseTableName: "account_contact", baseTableSchemaName: "cogda_dev", constraintName: "FKEFF7D80E92B433BC", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "user_profile", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-89") {
		addForeignKeyConstraint(baseColumnNames: "account_contact_id", baseTableName: "account_contact_address", baseTableSchemaName: "cogda_dev", constraintName: "FK2A852CC3A92BD5AD", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "account_contact", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-90") {
		addForeignKeyConstraint(baseColumnNames: "account_contact_id", baseTableName: "account_contact_email_address", baseTableSchemaName: "cogda_dev", constraintName: "FKE2CDABE0A92BD5AD", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "account_contact", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-91") {
		addForeignKeyConstraint(baseColumnNames: "account_contact_id", baseTableName: "account_contact_link", baseTableSchemaName: "cogda_dev", constraintName: "FK2680698BA92BD5AD", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "account_contact", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-92") {
		addForeignKeyConstraint(baseColumnNames: "account_id", baseTableName: "account_contact_link", baseTableSchemaName: "cogda_dev", constraintName: "FK2680698BBA0620CC", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "account", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-93") {
		addForeignKeyConstraint(baseColumnNames: "account_contact_id", baseTableName: "account_contact_phone_number", baseTableSchemaName: "cogda_dev", constraintName: "FKD3C7DBCBA92BD5AD", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "account_contact", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-94") {
		addForeignKeyConstraint(baseColumnNames: "account_id", baseTableName: "account_note", baseTableSchemaName: "cogda_dev", constraintName: "FK410B32C4BA0620CC", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "account", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-95") {
		addForeignKeyConstraint(baseColumnNames: "note_type_id", baseTableName: "account_note", baseTableSchemaName: "cogda_dev", constraintName: "FK410B32C42B863065", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "note_type", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-96") {
		addForeignKeyConstraint(baseColumnNames: "company_settings_id", baseTableName: "company", baseTableSchemaName: "cogda_dev", constraintName: "FK38A73C7D4E239DE7", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "company_settings", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-97") {
		addForeignKeyConstraint(baseColumnNames: "company_type_id", baseTableName: "company", baseTableSchemaName: "cogda_dev", constraintName: "FK38A73C7D5814B4EF", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "company_type", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-98") {
		addForeignKeyConstraint(baseColumnNames: "parent_company_id", baseTableName: "company", baseTableSchemaName: "cogda_dev", constraintName: "FK38A73C7D52AB5D81", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "company", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-99") {
		addForeignKeyConstraint(baseColumnNames: "company_id", baseTableName: "company_office", baseTableSchemaName: "cogda_dev", constraintName: "FKF19768BE36353ECC", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "company", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-100") {
		addForeignKeyConstraint(baseColumnNames: "office_address_id", baseTableName: "company_office", baseTableSchemaName: "cogda_dev", constraintName: "FKF19768BE4B012B16", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "address", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-101") {
		addForeignKeyConstraint(baseColumnNames: "company_office_phone_numbers_id", baseTableName: "company_office_phone_number", baseTableSchemaName: "cogda_dev", constraintName: "FKB66CE91B231BD754", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "company_office", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-102") {
		addForeignKeyConstraint(baseColumnNames: "phone_number_id", baseTableName: "company_office_phone_number", baseTableSchemaName: "cogda_dev", constraintName: "FKB66CE91BF53D6690", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "phone_number", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-103") {
		addForeignKeyConstraint(baseColumnNames: "company_id", baseTableName: "company_profile", baseTableSchemaName: "cogda_dev", constraintName: "FK8B339FE736353ECC", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "company", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-104") {
		addForeignKeyConstraint(baseColumnNames: "company_profile_id", baseTableName: "company_profile_address", baseTableSchemaName: "cogda_dev", constraintName: "FK3B0C0B9C5C0A9446", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "company_profile", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-105") {
		addForeignKeyConstraint(baseColumnNames: "company_profile_id", baseTableName: "company_profile_contact", baseTableSchemaName: "cogda_dev", constraintName: "FKB82B93C85C0A9446", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "company_profile", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-106") {
		addForeignKeyConstraint(baseColumnNames: "user_profile_id", baseTableName: "company_profile_contact", baseTableSchemaName: "cogda_dev", constraintName: "FKB82B93C892B433BC", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "user_profile", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-107") {
		addForeignKeyConstraint(baseColumnNames: "company_profile_id", baseTableName: "company_profile_email_address", baseTableSchemaName: "cogda_dev", constraintName: "FK755E43F95C0A9446", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "company_profile", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-108") {
		addForeignKeyConstraint(baseColumnNames: "company_profile_id", baseTableName: "company_profile_phone_number", baseTableSchemaName: "cogda_dev", constraintName: "FK6D275C925C0A9446", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "company_profile", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-109") {
		addForeignKeyConstraint(baseColumnNames: "user_profile_id", baseTableName: "contact", baseTableSchemaName: "cogda_dev", constraintName: "FK38B7242092B433BC", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "user_profile", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-110") {
		addForeignKeyConstraint(baseColumnNames: "contact_id", baseTableName: "contact_address", baseTableSchemaName: "cogda_dev", constraintName: "FK20846D5AEBE6233", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "contact", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-111") {
		addForeignKeyConstraint(baseColumnNames: "contact_id", baseTableName: "contact_email_address", baseTableSchemaName: "cogda_dev", constraintName: "FKE6457072AEBE6233", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "contact", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-112") {
		addForeignKeyConstraint(baseColumnNames: "contact_id", baseTableName: "contact_phone_number", baseTableSchemaName: "cogda_dev", constraintName: "FKB2DC3CF9AEBE6233", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "contact", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-113") {
		addForeignKeyConstraint(baseColumnNames: "business_type_id", baseTableName: "lead", baseTableSchemaName: "cogda_dev", constraintName: "FK329F5CB2910801", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "business_type", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-114") {
		addForeignKeyConstraint(baseColumnNames: "lead_address_type_id", baseTableName: "lead_address", baseTableSchemaName: "cogda_dev", constraintName: "FKBFE2C611C3C47096", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "lead_address_type", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-115") {
		addForeignKeyConstraint(baseColumnNames: "lead_id", baseTableName: "lead_address", baseTableSchemaName: "cogda_dev", constraintName: "FKBFE2C611BA21E748", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "lead", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-116") {
		addForeignKeyConstraint(baseColumnNames: "lead_id", baseTableName: "lead_contact", baseTableSchemaName: "cogda_dev", constraintName: "FK3D024E3DBA21E748", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "lead", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-117") {
		addForeignKeyConstraint(baseColumnNames: "lead_contact_id", baseTableName: "lead_contact_address", baseTableSchemaName: "cogda_dev", constraintName: "FK914A03F2FEA08CB3", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "lead_contact", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-118") {
		addForeignKeyConstraint(baseColumnNames: "lead_contact_id", baseTableName: "lead_contact_email_address", baseTableSchemaName: "cogda_dev", constraintName: "FK389A23CFFEA08CB3", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "lead_contact", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-119") {
		addForeignKeyConstraint(baseColumnNames: "lead_contact_id", baseTableName: "lead_contact_phone_number", baseTableSchemaName: "cogda_dev", constraintName: "FK39A529FCFEA08CB3", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "lead_contact", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-120") {
		addForeignKeyConstraint(baseColumnNames: "file_reference_id", baseTableName: "lead_file_reference", baseTableSchemaName: "cogda_dev", constraintName: "FK96982F8B2B5AE35A", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "file_reference", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-121") {
		addForeignKeyConstraint(baseColumnNames: "lead_files_id", baseTableName: "lead_file_reference", baseTableSchemaName: "cogda_dev", constraintName: "FK96982F8B10724BD0", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "lead", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-122") {
		addForeignKeyConstraint(baseColumnNames: "lead_id", baseTableName: "lead_file_reference", baseTableSchemaName: "cogda_dev", constraintName: "FK96982F8BBA21E748", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "lead", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-123") {
		addForeignKeyConstraint(baseColumnNames: "lead_id", baseTableName: "lead_line_of_business", baseTableSchemaName: "cogda_dev", constraintName: "FK4DF2BE0BA21E748", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "lead", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-124") {
		addForeignKeyConstraint(baseColumnNames: "line_of_business_id", baseTableName: "lead_line_of_business", baseTableSchemaName: "cogda_dev", constraintName: "FK4DF2BE0254795AE", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "line_of_business", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-125") {
		addForeignKeyConstraint(baseColumnNames: "lead_naics_codes_id", baseTableName: "lead_naics_code", baseTableSchemaName: "cogda_dev", constraintName: "FKA6817749C64A059A", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "lead", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-126") {
		addForeignKeyConstraint(baseColumnNames: "naics_code_id", baseTableName: "lead_naics_code", baseTableSchemaName: "cogda_dev", constraintName: "FKA681774917766421", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "naics_code", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-127") {
		addForeignKeyConstraint(baseColumnNames: "lead_id", baseTableName: "lead_note", baseTableSchemaName: "cogda_dev", constraintName: "FK440502F5BA21E748", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "lead", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-128") {
		addForeignKeyConstraint(baseColumnNames: "note_type_id", baseTableName: "lead_note", baseTableSchemaName: "cogda_dev", constraintName: "FK440502F52B863065", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "note_type", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-129") {
		addForeignKeyConstraint(baseColumnNames: "lead_sic_codes_id", baseTableName: "lead_sic_code", baseTableSchemaName: "cogda_dev", constraintName: "FK9008BBA257757573", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "lead", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-130") {
		addForeignKeyConstraint(baseColumnNames: "sic_code_id", baseTableName: "lead_sic_code", baseTableSchemaName: "cogda_dev", constraintName: "FK9008BBA2AF7AF9AF", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "sic_code", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-131") {
		addForeignKeyConstraint(baseColumnNames: "line_of_business_category_id", baseTableName: "line_of_business", baseTableSchemaName: "cogda_dev", constraintName: "FKABE9455DD04F8EA9", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "line_of_business_category", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-132") {
		addForeignKeyConstraint(baseColumnNames: "parent_naics_code_id", baseTableName: "naics_code", baseTableSchemaName: "cogda_dev", constraintName: "FKFF87B2862D35CD4C", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "naics_code", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-133") {
		addForeignKeyConstraint(baseColumnNames: "company_type_id", baseTableName: "registration", baseTableSchemaName: "cogda_dev", constraintName: "FKAF83E8B95814B4EF", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "company_type", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-134") {
		addForeignKeyConstraint(baseColumnNames: "existing_company_id", baseTableName: "registration", baseTableSchemaName: "cogda_dev", constraintName: "FKAF83E8B91A0B0A00", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "company", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-135") {
		addForeignKeyConstraint(baseColumnNames: "email_confirmation_log_id", baseTableName: "registration_email_confirmation_log", baseTableSchemaName: "cogda_dev", constraintName: "FK7DDBB4C3C36D95D6", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "email_confirmation_log", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-136") {
		addForeignKeyConstraint(baseColumnNames: "registration_email_confirmation_logs_id", baseTableName: "registration_email_confirmation_log", baseTableSchemaName: "cogda_dev", constraintName: "FK7DDBB4C3937D840F", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "registration", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-137") {
		addForeignKeyConstraint(baseColumnNames: "created_by_id", baseTableName: "request_for_action", baseTableSchemaName: "cogda_dev", constraintName: "FK2B342E1C8F46E59E", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "user", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-138") {
		addForeignKeyConstraint(baseColumnNames: "parent_request_for_action_id", baseTableName: "request_for_action", baseTableSchemaName: "cogda_dev", constraintName: "FK2B342E1C35A97A80", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "request_for_action", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-139") {
		addForeignKeyConstraint(baseColumnNames: "request_for_action_status_id", baseTableName: "request_for_action", baseTableSchemaName: "cogda_dev", constraintName: "FK2B342E1C83629F2E", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "request_for_action_status", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-140") {
		addForeignKeyConstraint(baseColumnNames: "submission_id", baseTableName: "request_for_action", baseTableSchemaName: "cogda_dev", constraintName: "FK2B342E1C361BB21", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "submission", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-141") {
		addForeignKeyConstraint(baseColumnNames: "assignee_id", baseTableName: "request_for_action_log", baseTableSchemaName: "cogda_dev", constraintName: "FKFB2492A182C3BD3D", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "user", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-142") {
		addForeignKeyConstraint(baseColumnNames: "request_for_action_id", baseTableName: "request_for_action_log", baseTableSchemaName: "cogda_dev", constraintName: "FKFB2492A155E46C55", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "request_for_action", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-143") {
		addForeignKeyConstraint(baseColumnNames: "request_for_action_status_id", baseTableName: "request_for_action_log", baseTableSchemaName: "cogda_dev", constraintName: "FKFB2492A183629F2E", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "request_for_action_status", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-144") {
		addForeignKeyConstraint(baseColumnNames: "request_for_action_request_for_action_types_id", baseTableName: "request_for_action_request_for_action_type", baseTableSchemaName: "cogda_dev", constraintName: "FKE0582A5AF1FE53F8", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "request_for_action", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-145") {
		addForeignKeyConstraint(baseColumnNames: "request_for_action_type_id", baseTableName: "request_for_action_request_for_action_type", baseTableSchemaName: "cogda_dev", constraintName: "FKE0582A5A8FE2172E", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "request_for_action_type", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-146") {
		addForeignKeyConstraint(baseColumnNames: "request_for_action_assignees_id", baseTableName: "request_for_action_user", baseTableSchemaName: "cogda_dev", constraintName: "FK6971E80EF5C97E30", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "request_for_action", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-147") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "request_for_action_user", baseTableSchemaName: "cogda_dev", constraintName: "FK6971E80E122A4DC1", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "user", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-148") {
		addForeignKeyConstraint(baseColumnNames: "parent_sic_code_id", baseTableName: "sic_code", baseTableSchemaName: "cogda_dev", constraintName: "FKAEEA21F21C8B19A", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "sic_code", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-149") {
		addForeignKeyConstraint(baseColumnNames: "naics_code_id", baseTableName: "sic_naics_code_crosswalk", baseTableSchemaName: "cogda_dev", constraintName: "FKC6E717C217766421", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "naics_code", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-150") {
		addForeignKeyConstraint(baseColumnNames: "sic_code_id", baseTableName: "sic_naics_code_crosswalk", baseTableSchemaName: "cogda_dev", constraintName: "FKC6E717C2AF7AF9AF", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "sic_code", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-151") {
		addForeignKeyConstraint(baseColumnNames: "created_by_id", baseTableName: "submission", baseTableSchemaName: "cogda_dev", constraintName: "FK84363B4C8F46E59E", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "user", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-152") {
		addForeignKeyConstraint(baseColumnNames: "lead_id", baseTableName: "submission", baseTableSchemaName: "cogda_dev", constraintName: "FK84363B4CBA21E748", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "lead", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-153") {
		addForeignKeyConstraint(baseColumnNames: "parent_submission_id", baseTableName: "submission", baseTableSchemaName: "cogda_dev", constraintName: "FK84363B4C1921244C", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "submission", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-154") {
		addForeignKeyConstraint(baseColumnNames: "account_contact_link_account_contact_id, account_contact_link_account_id", baseTableName: "submission_account_contact_link", baseTableSchemaName: "cogda_dev", constraintName: "FKEE83909E5D9C881C", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "account_contact_id, account_id", referencedTableName: "account_contact_link", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-155") {
		addForeignKeyConstraint(baseColumnNames: "submission_id", baseTableName: "submission_account_contact_link", baseTableSchemaName: "cogda_dev", constraintName: "FKEE83909E361BB21", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "submission", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-156") {
		addForeignKeyConstraint(baseColumnNames: "lead_line_of_business_id", baseTableName: "submission_lead_line_of_business", baseTableSchemaName: "cogda_dev", constraintName: "FK3D40E72D49BEBCAF", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "lead_line_of_business", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-157") {
		addForeignKeyConstraint(baseColumnNames: "submission_lead_line_of_businesses_id", baseTableName: "submission_lead_line_of_business", baseTableSchemaName: "cogda_dev", constraintName: "FK3D40E72DED286372", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "submission", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-158") {
		addForeignKeyConstraint(baseColumnNames: "company_id", baseTableName: "user_profile", baseTableSchemaName: "cogda_dev", constraintName: "FK487E213536353ECC", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "company", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-159") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "user_profile", baseTableSchemaName: "cogda_dev", constraintName: "FK487E2135122A4DC1", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "user", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-160") {
		addForeignKeyConstraint(baseColumnNames: "user_profile_id", baseTableName: "user_profile_address", baseTableSchemaName: "cogda_dev", constraintName: "FKB71F5EEA92B433BC", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "user_profile", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-161") {
		addForeignKeyConstraint(baseColumnNames: "user_profile_id", baseTableName: "user_profile_email_address", baseTableSchemaName: "cogda_dev", constraintName: "FK1A8D64C792B433BC", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "user_profile", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-162") {
		addForeignKeyConstraint(baseColumnNames: "user_profile_id", baseTableName: "user_profile_phone_number", baseTableSchemaName: "cogda_dev", constraintName: "FKAC49EA0492B433BC", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "user_profile", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-163") {
		addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "user_role", baseTableSchemaName: "cogda_dev", constraintName: "FK143BF46A6CFF89E1", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "role", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}

	changeSet(author: "christopher (generated)", id: "1375269875934-164") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "user_role", baseTableSchemaName: "cogda_dev", constraintName: "FK143BF46A122A4DC1", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "user", referencedTableSchemaName: "cogda_dev", referencesUniqueColumn: "false")
	}
}
