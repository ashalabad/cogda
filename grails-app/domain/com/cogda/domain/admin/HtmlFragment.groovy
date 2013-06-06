package com.cogda.domain.admin

import com.cogda.common.MarkupLanguage

import java.util.regex.Pattern

/**
 * HtmlFragment
 * A domain class describes the data object and it's mapping to the database
 */
class HtmlFragment {

    /* Default (injected) attributes of GORM */
    String name

    String description

    String html

    MarkupLanguage markupLanguage

    /* Automatic timestamping of GORM */
	Date	dateCreated
	Date	lastUpdated

//	static belongsTo	= []	// tells GORM to cascade commands: e.g., delete this object if the "parent" is deleted.
//	static hasOne		= []	// tells GORM to associate another domain object as an owner in a 1-1 mapping
//	static hasMany		= []	// tells GORM to associate other domain objects for a 1-n or n-m mapping
//	static mappedBy		= []	// specifies which property should be used in a mapping

    static constraints = {
        name(nullable:false, blank:false, unique:true)
        description(nullable:true)
        html(nullable:false)
        markupLanguage(nullable:false)
    }

    /*
     * Methods of the Domain Class
     */
	@Override	// Override toString for a nicer / more descriptive UI
	public String toString() {
		return "${name}";
	}
}
