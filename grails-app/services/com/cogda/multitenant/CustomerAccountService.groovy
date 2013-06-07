package com.cogda.multitenant

import com.cogda.domain.CompanyProfile
import com.cogda.domain.UserProfile
import com.cogda.domain.UserProfileService
import com.cogda.domain.com.cogda.domain.CompanyProfileService
import com.cogda.domain.onboarding.Registration
import com.cogda.domain.security.Role
import com.cogda.domain.security.User
import com.cogda.errors.CustomerAccountCreationException
import com.cogda.security.SecurityService
import com.cogda.security.UserRoleService
import com.cogda.security.UserService
import org.apache.commons.logging.LogFactory

/**
 * CustomerAccountService
 * A service class encapsulates the core business logic of a Grails application
 */
class CustomerAccountService {
    private static final log = LogFactory.getLog(this)

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
        return customerAccount
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

        // Create the Customer Account
        create(customerAccount)

        // Create the Customer Account Security Profile
        createCustomerAccountSecurityProfile(customerAccount)

        // Create the First Company
        Company company = createFirstCompany(customerAccount, registration)

        // Create the First User
        User user = createFirstUser(customerAccount, registration)

        // create the UserProfile
        UserProfile userProfile = userProfileService.createUserProfile(user, registration)

        // create the CompanyProfile
        CompanyProfile companyProfile = companyProfileService.createCompanyProfile(company, registration)
    }

    /**
     * Create the Security Profile for this new CustomerAccount
     * @param customerAccount
     */
    void createCustomerAccountSecurityProfile(CustomerAccount customerAccount){
        // Create the default security roles for this CustomerAccount
        customerAccount.withThisTenant {
            securityService.customerAccountSecuritySetup()
        }
    }

    /**
     *
     * @param customerAccount
     * @param registration
     * @return Company
     */
    Company createFirstCompany(CustomerAccount customerAccount, Registration registration){
        customerAccount.withThisTenant {
            Company company = new Company()

            company.companyName = registration.companyName
            company.doingBusinessAs = registration.companyName
            company.parentCompany = null
            company.intCode = 0

            company.save()

            return company
        }
    }

    /**
     *
     * @param customerAccount
     * @param registration
     * @return User
     */
    User createFirstUser(CustomerAccount customerAccount, Registration registration){

        customerAccount.withThisTenant {

            User user = new User()

            // create the user based on the passed in registration parameters
            user = userService.createUser(registration.username, registration.password, false)

            // add the user roles for the host_administrator and user_role
            Role roleAdministrator = Role.findByAuthority(SecurityService.ROLE_ADMINISTRATOR)
            Role roleUser = Role.findByAuthority(SecurityService.ROLE_USER)
            userRoleService.createUserRoles(user, [roleAdministrator, roleUser])

            return user
        }
    }
}
