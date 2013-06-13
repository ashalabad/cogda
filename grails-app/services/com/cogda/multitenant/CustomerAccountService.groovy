package com.cogda.multitenant

import com.amazonaws.services.s3.model.AmazonS3Exception
import com.amazonaws.services.s3.model.GetObjectRequest
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.model.PutObjectResult
import com.cogda.domain.CompanyProfile
import com.cogda.domain.UserProfile
import com.cogda.domain.UserProfileService
import com.cogda.domain.CompanyProfileService
import com.cogda.domain.onboarding.Registration
import com.cogda.domain.security.Role
import com.cogda.domain.security.User
import com.cogda.errors.CustomerAccountCreationException
import com.cogda.security.SecurityService
import com.cogda.security.UserRoleService
import com.cogda.security.UserService
import grails.plugin.awssdk.AmazonWebService
import org.apache.commons.logging.LogFactory
import org.codehaus.groovy.grails.commons.GrailsApplication

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
    GrailsApplication grailsApplication
    AmazonWebService amazonWebService

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

        // sets up the customerAccount file system
        provisionCustomerAccountAmazonFileSystem(customerAccount, userProfile, companyProfile)

    }

    /**
     * Creates the default file system for a new customer account.
     */
    void provisionCustomerAccountAmazonFileSystem(CustomerAccount customerAccount, UserProfile userProfile, CompanyProfile companyProfile){
        final String defaultBucket = grailsApplication.config.grails.plugin.awssdk.default.bucket
        final String pathPrefix = "customerAccounts/"
        final String companiesFolder = "companies"
        final String clientsFolder = "clients"
        final String imagesFolder = "images"
        final String tempFolder = "temp"
        File file
        try {
            file = new File("a.txt")
            file.write("0")

            String customerAccountRoot = pathPrefix + customerAccount.accountId + "/"
            // create the customerAccountRoot - at the defaultBucket "customerAccounts/${customerAccount.accountId}/"
            amazonWebService.s3.putObject(new PutObjectRequest(defaultBucket, customerAccountRoot, file))

            // create the temp folder for the customerAccount "customerAccounts/${customerAccount.accountId}/temp"
            String customerAccountTemp = customerAccountRoot + tempFolder + "/"
            amazonWebService.s3.putObject(new PutObjectRequest(defaultBucket, customerAccountTemp, file))

            // create the images folder for the customerAccount "customerAccounts/${customerAccount.accountId}/images"
            String customerAccountImages = customerAccountRoot + imagesFolder + "/"
            amazonWebService.s3.putObject(new PutObjectRequest(defaultBucket, customerAccountImages, file))

            // create the companies folder for the customerAccount "customerAccounts/${customerAccount.accountId}/companies"
            String customerAccountCompanies = customerAccountRoot + companiesFolder + "/"
            amazonWebService.s3.putObject(new PutObjectRequest(defaultBucket, customerAccountCompanies, file))

            // create the company folder for the customerAccount "customerAccounts/${customerAccount.accountId}/companies/${companyProfile.company.accountId}/
            String customerAccountRootCompany = customerAccountCompanies + companyProfile.company.accountId
            amazonWebService.s3.putObject(new PutObjectRequest(defaultBucket, customerAccountRootCompany, file))

        } catch (AmazonS3Exception e){
            e.printStackTrace()
            log.error e.message

        } finally {

            file.delete()

        }
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
