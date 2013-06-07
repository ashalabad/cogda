package com.cogda.multitenant

import com.cogda.domain.CompanyProfile
import com.cogda.domain.UserProfile
import com.cogda.domain.UserProfileService
import com.cogda.domain.com.cogda.domain.CompanyProfileService
import com.cogda.domain.onboarding.Registration
import com.cogda.domain.security.User
import com.cogda.errors.CustomerAccountCreationException
import com.cogda.security.SecurityService
import com.cogda.security.UserRoleService
import com.cogda.security.UserService

/**
 * CustomerAccountService
 * A service class encapsulates the core business logic of a Grails application
 */
class CustomerAccountService {

    SecurityService securityService // inject SecurityService into CustomerAccountService
    UserService userService  // inject UserService into CustomerAccountService
    UserProfileService userProfileService  // inject UserProfileService into CustomerAccountService
    CompanyService companyService // inject CompanyService into CustomerAccountService
    UserRoleService userRoleService  // inject UserRoleService into CustomerAccountService
    CompanyProfileService companyProfileService // inject CompanyProfileService into CustomerAccountService

    /**
     * Creates a new CustomerAccount
     * @param customerAccount
     */
    def create(CustomerAccount customerAccount){
        if(!customerAccount.save(insert:true)){
            throw new CustomerAccountCreationException("Error creating a new CustomerAccount", customerAccount.getErrors(), customerAccount)
        }
    }

    /**
     * This method is called when a client first signs up
     * at Cogda and provisions all of the assets.
     * Creates the CustomerAccount based on the passed in registration information.
     * Creates the security assets for this Organization
     * Creates the First Organization User based on the email address.
     * @param organizationName
     * @param emailAddress
     */
    void onboardCustomerAccount(Registration registration){
        // Create the new organization
        CustomerAccount customerAccount = new CustomerAccount(subDomain: registration.subDomain)
        create(customerAccount)

        // Wrap the following methods in the multi-tenant transaction with the
        // organization's current tenantId.
        User user
        Company company
        customerAccount.withThisTenant {
            println "Tenant ID in onboardCustomerAccount ${currentTenant.get()}"
            log.debug "Tenant ID in onboardCustomerAccount ${currentTenant.get()}"

            // Create the default security roles
            securityService.customerAccountSecuritySetup()

            // create the user based on the passed in registration parameters
            user = userService.createUser(registration.username, registration.password, false)

            // add the user roles for the host_administrator and user_role
            userRoleService.createUserRoles(user, [SecurityService.ROLE_HOST_ADMINISTRATOR, SecurityService.ROLE_USER])

            // create the company based on the passed in registration parameters
            company = companyService.createCompany(registration)
        }

        // create the UserProfile
        UserProfile userProfile = userProfileService.createUserProfile(user, registration)

        CompanyProfile companyProfile = companyProfileService.createCompanyProfile(company, registration)


    }
}
