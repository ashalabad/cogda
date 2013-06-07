package com.cogda.security

import com.cogda.domain.security.Role

/**
 * SecurityService
 */
class SecurityService {

    static transactional = true

    /**
     * A ROLE_HOST_ADMINISTRATOR
     * ROLE_HOST_ADMINISTRATOR = 'ROLE_HOST_ADMINISTRATOR'
     */
    public static final String ROLE_HOST_ADMINISTRATOR = 'ROLE_HOST_ADMINISTRATOR'

    /**
     * A ROLE_UNDERWRITER
     * ROLE_UNDERWRITER = 'ROLE_UNDERWRITER'
     */
    public static final String ROLE_UNDERWRITER = 'ROLE_UNDERWRITER'

    /**
     * A ROLE_CUSTOMER_SERVICE_REP
     * ROLE_CUSTOMER_SERVICE_REP = 'ROLE_CUSTOMER_SERVICE_REP'
     */
    public static final String ROLE_CUSTOMER_SERVICE_REP = 'ROLE_CUSTOMER_SERVICE_REP'

    /**
     * A ROLE_BRANCH_MANAGER
     * ROLE_BRANCH_MANAGER = 'ROLE_BRANCH_MANAGER'
     */
    public static final String ROLE_BRANCH_MANAGER = 'ROLE_BRANCH_MANAGER'

    /**
     * A ROLE_MAREKETING_MANAGER
     * ROLE_MAREKETING_MANAGER = 'ROLE_MAREKETING_MANAGER'
     */
    public static final String ROLE_MAREKETING_MANAGER = 'ROLE_MAREKETING_MANAGER'

    /**
     * A Sales Manager's Role
     * SALES_MANAGER = 'SALES_MANAGER'
     */
    public static final String ROLE_SALES_MANAGER = 'ROLE_SALES_MANAGER'

    /**
     * A Marketer Role
     * ROLE_MARKETER = 'ROLE_MARKETER'
     */
    public static final String ROLE_MARKETER = 'ROLE_MARKETER'

    /**
     * Default role for ALL users.
     * ROLE_PRODUCER = 'ROLE_PRODUCER'
     */
    public static final String ROLE_PRODUCER = 'ROLE_PRODUCER'

    /**
     * Default role for ALL users.
     * ROLE_EVERYONE = 'ROLE_EVERYONE'
     */
    public static final String ROLE_EVERYONE = 'ROLE_EVERYONE'

    /**
     * Default role for ALL users.
     * This is synonomous with the old-cogda USERS role.
     * ROLE_USER = 'ROLE_USER'
     */
    public static final String ROLE_USER = 'ROLE_USER'

    /**
     * Default role for users that manage a company..
     * ROLE_COMPANY_MANAGER = 'ROLE_COMPANY_MANAGER'
     */
    public static final String ROLE_COMPANY_MANAGER = 'ROLE_COMPANY_MANAGER'

    /**
     * Default role for account owners - account
     * owners can handle billing/payment details.
     * ROLE_ACCOUNT_OWNER = 'ROLE_ACCOUNT_OWNER'
     */
    public static final String ROLE_ACCOUNT_OWNER = 'ROLE_ACCOUNT_OWNER'

    /**
     * Default role for administrator accounts - the top level account in the hierarchy of roles in the
     * system.
     * ROLE_ADMINISTRATOR = 'ROLE_ADMINISTRATOR'
     */
    public static final String ROLE_ADMINISTRATOR = 'ROLE_ADMINISTRATOR'

    /**
     * This method creates all of the security assets for the new
     * CustomerAccount.
     * This method should only be used when provisioning a new CustomerAccount.
     */
    void customerAccountSecuritySetup() {
        println "Tenant ID in SecurityService ${currentTenant.get()}"
        log.debug "Tenant ID in SecurityService ${currentTenant.get()}"
        Role hostAdministratorRole = new Role(authority:SecurityService.ROLE_HOST_ADMINISTRATOR)
        Role underwriterRole = new Role(authority:SecurityService.ROLE_UNDERWRITER)
        Role customerServiceRepRole = new Role(authority:SecurityService.ROLE_CUSTOMER_SERVICE_REP)
        Role branchManagerRole = new Role(authority:SecurityService.ROLE_BRANCH_MANAGER)
        Role marketingManagerRole = new Role(authority:SecurityService.ROLE_MAREKETING_MANAGER)
        Role salesManagerRole = new Role(authority:SecurityService.ROLE_SALES_MANAGER)
        Role marketerRole = new Role(authority:SecurityService.ROLE_MARKETER)
        Role producerRole = new Role(authority:SecurityService.ROLE_PRODUCER)
        Role everyoneRole = new Role(authority:SecurityService.ROLE_EVERYONE)
        Role userRole = new Role(authority: SecurityService.ROLE_USER)
        Role companyManagerRole = new Role(authority: SecurityService.ROLE_COMPANY_MANAGER)
        Role accountOwnerRole = new Role(authority: SecurityService.ROLE_ACCOUNT_OWNER)
        Role administratorRole = new Role(authority: SecurityService.ROLE_ADMINISTRATOR)

        hostAdministratorRole.save(failOnError:true)
        underwriterRole.save(failOnError:true)
        customerServiceRepRole.save(failOnError:true)
        branchManagerRole.save(failOnError:true)
        marketingManagerRole.save(failOnError:true)
        salesManagerRole.save(failOnError:true)
        marketerRole.save(failOnError:true)
        producerRole.save(failOnError:true)
        everyoneRole.save(failOnError:true)
        userRole.save(failOnError:true)
        companyManagerRole.save(failOnError:true)
        accountOwnerRole.save(failOnError:true)
        administratorRole.save(failOnError:true)
    }
}
