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
