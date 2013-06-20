package com.cogda.multitenant

import com.cogda.domain.admin.AccountType
import grails.plugin.multitenant.core.annotation.MultiTenant

/**
 * Account
 * A domain class describes the data object and it's mapping to the database
 */
@MultiTenant
class Account {

    String accountName

    String accountCode

    /**
     * AccountType accountType
     *
     */
    AccountType accountType

    static hasMany = [accountContacts:AccountContact]

    static constraints = {
        accountName(nullable:false, blank:false, unique:"tenantId")
        accountCode(nullable:true)

    }
}
