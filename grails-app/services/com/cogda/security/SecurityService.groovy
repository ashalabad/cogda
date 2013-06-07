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
//        println "Tenant ID in SecurityService ${currentTenant.get()}"
//        log.debug "Tenant ID in SecurityService ${currentTenant.get()}"
        Role hostAdministratorRole = new Role(authority:SecurityService.ROLE_HOST_ADMINISTRATOR,
                description:"GOD MODE. Performs COGDA level system \n" +
                "configuration and administration. Also can access any company and act as \n" +
                "their administrator.")
        Role underwriterRole = new Role(authority:SecurityService.ROLE_UNDERWRITER, description:
                "People having access to Submission and Messaging Widget. \n" +
                "Also can do clearance.")
        Role customerServiceRepRole = new Role(authority:SecurityService.ROLE_CUSTOMER_SERVICE_REP, description: "Have " +
                "access to Pipeline, Submissions, Messaging, \n" +
                "Search clients and Client file")
        Role branchManagerRole = new Role(authority:SecurityService.ROLE_BRANCH_MANAGER, description: "Local office " +
                "admin. Like Maria at Rennaissance = Able to oversee \n" +
                "office functions, office level settings and office level reports.")
        Role marketingManagerRole = new Role(authority:SecurityService.ROLE_MAREKETING_MANAGER, description: "Have access " +
                "to CRM/Marketing widget and access to \n" +
                "Marketing reports.")
        Role salesManagerRole = new Role(authority:SecurityService.ROLE_SALES_MANAGER, description: "Have access to Sales " +
                "widget, is able to control \n" +
                "Pipeline assignments and able to set sales goals to employees.")
        Role marketerRole = new Role(authority:SecurityService.ROLE_MARKETER, description: "Have access to Submissions, " +
                "Messaging")
        Role producerRole = new Role(authority:SecurityService.ROLE_PRODUCER, description: "Has access to Prospect pipeline")
        Role userRole = new Role(authority: SecurityService.ROLE_USER, description: "All authenticated users.")
        Role companyManagerRole = new Role(authority: SecurityService.ROLE_COMPANY_MANAGER, description:"Provides access " +
                "to a dashboard and company level \n" +
                "reports.")
        Role administratorRole = new Role(authority: SecurityService.ROLE_ADMINISTRATOR, description:"Company level admin " +
                "- manages all aspects of the \n" +
                "Company in COGDA. Profile settings etc.")

        hostAdministratorRole.save(failOnError:true)
        underwriterRole.save(failOnError:true)
        customerServiceRepRole.save(failOnError:true)
        branchManagerRole.save(failOnError:true)
        marketingManagerRole.save(failOnError:true)
        salesManagerRole.save(failOnError:true)
        marketerRole.save(failOnError:true)
        producerRole.save(failOnError:true)
        userRole.save(failOnError:true)
        companyManagerRole.save(failOnError:true)
        administratorRole.save(failOnError:true)
    }
}
