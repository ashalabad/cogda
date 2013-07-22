package com.cogda.multitenant

import com.amazonaws.services.s3.model.AmazonS3Exception
import com.amazonaws.services.s3.model.GetObjectRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.model.PutObjectResult
import com.cogda.domain.Address
import com.cogda.domain.CompanyProfile
import com.cogda.domain.CompanyProfileAddress
import com.cogda.domain.CompanyProfilePhoneNumber
import com.cogda.domain.PhoneNumber
import com.cogda.domain.UserProfile
import com.cogda.domain.UserProfileEmailAddress
import com.cogda.domain.UserProfileService
import com.cogda.domain.CompanyProfileService
import com.cogda.domain.admin.CompanyType
import com.cogda.domain.onboarding.Registration
import com.cogda.domain.security.Role
import com.cogda.domain.security.User
import com.cogda.domain.security.UserRole
import com.cogda.errors.CustomerAccountCreationException
import com.cogda.security.SecurityService
import com.cogda.security.UserRoleService
import com.cogda.security.UserService
import grails.plugin.awssdk.AmazonWebService
import grails.plugin.multitenant.core.CurrentTenant
import grails.plugin.multitenant.core.MultiTenantDomainClass
import grails.plugin.multitenant.core.Tenant
import grails.validation.ValidationException
import org.apache.commons.logging.LogFactory
import org.codehaus.groovy.grails.commons.GrailsApplication

/**
 * CustomerAccountService
 * A service class encapsulates the core business logic of a Grails application
 */
class CustomerAccountService {

    CurrentTenant currentTenant

    private static final log = LogFactory.getLog(this)

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


    GrailsApplication grailsApplication
    AmazonWebService amazonWebService

    /**
     * Creates a new CustomerAccount
     * @param customerAccount
     */

    public CustomerAccount createCustomerAccount(String subDomain){

        // Create the new CustomerAccount
        CustomerAccount customerAccount = new CustomerAccount(subDomain: subDomain)

        // Create the Customer Account
        if(!customerAccount.save(insert:true, flush:true)){
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
    void onboardCustomerAccount(registration){

        CustomerAccount customerAccount = createCustomerAccount(registration.subDomain)

        String customerAccountUuid = customerAccount.accountId

        // Create the First Company
        Company company = createFirstCompany(customerAccount, registration)

        // Create the First Company Profile
        CompanyProfile companyProfile = createFirstCompanyProfile(customerAccount, registration, company)

        // Create the Customer Account Security Profile
        customerAccountSecuritySetup(customerAccount)

        // Create the First User
        User user = createFirstUser(customerAccount, registration)

        // Create the First UserProfile and associate it with the User and the Company
        // and the Corresponding UserProfile and the UserProfile's primaryEmailAddress
        UserProfile userProfile = createFirstUserProfile(registration, user, company)

        // sets up the customerAccount file system
        provisionCustomerAccountAmazonFileSystem(customerAccountUuid, company.accountId, user.accountId)
    }

    /**
     * This method creates all of the security assets for the new
     * CustomerAccount.
     * This method should only be used when provisioning a new CustomerAccount.
     */
    void customerAccountSecuritySetup(CustomerAccount customerAccount) {
        Role hostAdministratorRole = new Role(authority:CustomerAccountService.ROLE_HOST_ADMINISTRATOR, description:"GOD MODE. Performs COGDA level system configuration and administration. Also can access any company and act as their administrator.", systemRole: Boolean.TRUE)
        Role underwriterRole = new Role(authority:CustomerAccountService.ROLE_UNDERWRITER, description: "People having access to Submission and Messaging Widget. Also can do clearance.", systemRole: Boolean.TRUE)
        Role customerServiceRepRole = new Role(authority:CustomerAccountService.ROLE_CUSTOMER_SERVICE_REP, description: "Have access to Pipeline, Submissions, Messaging, Search clients and Client file", systemRole: Boolean.TRUE)
        Role branchManagerRole = new Role(authority:CustomerAccountService.ROLE_BRANCH_MANAGER, description: "Local office admin. Like Maria at Rennaissance = Able to oversee office functions, office level settings and office level reports.", systemRole: Boolean.TRUE)
        Role marketingManagerRole = new Role(authority:CustomerAccountService.ROLE_MAREKETING_MANAGER, description: "Have access to CRM/Marketing widget and access to Marketing reports.", systemRole: Boolean.TRUE)
        Role salesManagerRole = new Role(authority:CustomerAccountService.ROLE_SALES_MANAGER, description: "Have access to Sales widget, is able to control Pipeline assignments and able to set sales goals to employees.", systemRole: Boolean.TRUE)
        Role marketerRole = new Role(authority:CustomerAccountService.ROLE_MARKETER, description: "Have access to Submissions, Messaging")
        Role producerRole = new Role(authority:CustomerAccountService.ROLE_PRODUCER, description: "Has access to Prospect pipeline", systemRole: Boolean.TRUE)
        Role userRole = new Role(authority: CustomerAccountService.ROLE_USER, description: "All authenticated users.", systemRole: Boolean.TRUE)
        Role companyManagerRole = new Role(authority: CustomerAccountService.ROLE_COMPANY_MANAGER, description:"Provides access to a dashboard and company level reports.", systemRole: Boolean.TRUE)
        Role administratorRole = new Role(authority: CustomerAccountService.ROLE_ADMINISTRATOR, description:"Company level admin - manages all aspects of the Company in COGDA. Profile settings etc.", systemRole: Boolean.TRUE)

        customerAccount.withThisTenant {
            hostAdministratorRole.save() ?: log.error ("Unable to save hostAdministratorRole - Failed with ${hostAdministratorRole.errors}")
            underwriterRole.save() ?: log.error ("Unable to save underwriterRole - Failed with ${underwriterRole.errors}")
            customerServiceRepRole.save() ?: log.error ("Unable to save customerServiceRepRole - Failed with ${customerServiceRepRole.errors}")
            branchManagerRole.save() ?: log.error ("Unable to save branchManagerRole - Failed with ${branchManagerRole.errors}")
            marketingManagerRole.save() ?: log.error ("Unable to save marketingManagerRole - Failed with ${marketingManagerRole.errors}")
            salesManagerRole.save() ?: log.error ("Unable to save salesManagerRole - Failed with ${salesManagerRole.errors}")
            marketerRole.save() ?: log.error ("Unable to save marketerRole - Failed with ${marketerRole.errors}")
            producerRole.save() ?: log.error ("Unable to save producerRole - Failed with ${producerRole.errors}")
            userRole.save() ?: log.error ("Unable to save userRole - Failed with ${userRole.errors}")
            companyManagerRole.save() ?: log.error ("Unable to save companyManagerRole - Failed with ${companyManagerRole.errors}")
            administratorRole.save() ?: log.error ("Unable to save administratorRole - Failed with ${administratorRole.errors}")
        }
    }


    /**
     * Creates the default file system for a new customer account.
     * <br/>
     *
     * <ul>
     * <li>customerAccounts/bd75c3df9bd143748e8a5fd8d16333d2/a.txt
     * <li>customerAccounts/bd75c3df9bd143748e8a5fd8d16333d2/companies/44b2748cb67b4cdaa9709673d4598085/a.txt
     * <li>customerAccounts/bd75c3df9bd143748e8a5fd8d16333d2/companies/44b2748cb67b4cdaa9709673d4598085/documents/a.txt
     * <li>customerAccounts/bd75c3df9bd143748e8a5fd8d16333d2/companies/a.txt
     * <li>customerAccounts/bd75c3df9bd143748e8a5fd8d16333d2/documents/a.txt
     * <li>customerAccounts/bd75c3df9bd143748e8a5fd8d16333d2/images/a.txt
     * <li>customerAccounts/bd75c3df9bd143748e8a5fd8d16333d2/temp/a.txt
     * <li>customerAccounts/bd75c3df9bd143748e8a5fd8d16333d2/users/1afe48188f7e48a58cca7eb926595606/a.txt
     * <li>customerAccounts/bd75c3df9bd143748e8a5fd8d16333d2/users/1afe48188f7e48a58cca7eb926595606/images/a.txt
     * <li>customerAccounts/bd75c3df9bd143748e8a5fd8d16333d2/users/a.txt
     * </ul>
     */
    void provisionCustomerAccountAmazonFileSystem(String customerAccountUuid, String companyAccountId, String userAccountId){
        final String defaultBucket = grailsApplication.config.grails.plugin.awssdk.default.bucket
        final String pathPrefix = "customerAccounts/"
        final String companiesFolder = "companies"
        final String clientsFolder = "clients"
        final String leadsFolder = "leads"
        final String imagesFolder = "images"
        final String documentsFolder = "documents"
        final String tempFolder = "temp"
        final String usersFolder = "users"

        try {
            String s = "a"
            InputStream is = new ByteArrayInputStream(s.getBytes("UTF-8"));
            String fileName = "a.txt"

            ObjectMetadata objectMetadata = new ObjectMetadata()
            objectMetadata.setServerSideEncryption(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION);

            String customerAccountRoot = pathPrefix + customerAccountUuid + "/"
            // create the customerAccountRoot - at the defaultBucket "customerAccounts/${customerAccount.accountId}/"
            amazonWebService.s3.putObject(defaultBucket, customerAccountRoot + fileName, is, objectMetadata)

            // create the customerAccountDocuments - at the defaultBucket "customerAccounts/${customerAccount.accountId}/documents"
            String customerAccountDocuments = customerAccountRoot + documentsFolder + "/"
            amazonWebService.s3.putObject(defaultBucket, customerAccountDocuments + fileName, is, objectMetadata)

            // create the temp folder for the customerAccount "customerAccounts/${customerAccount.accountId}/temp"
            String customerAccountTemp = customerAccountRoot + tempFolder + "/"
            amazonWebService.s3.putObject(defaultBucket, customerAccountTemp + fileName, is, objectMetadata)

            // create the images folder for the customerAccount "customerAccounts/${customerAccount.accountId}/images"
            String customerAccountImages = customerAccountRoot + imagesFolder + "/"
            amazonWebService.s3.putObject(defaultBucket, customerAccountImages + fileName, is, objectMetadata)

            // create the companies folder for the customerAccount "customerAccounts/${customerAccount.accountId}/companies"
            String customerAccountCompanies = customerAccountRoot + companiesFolder + "/"
            amazonWebService.s3.putObject(defaultBucket, customerAccountCompanies + fileName, is, objectMetadata)

            // create the company folder for the customerAccount "customerAccounts/${customerAccount.accountId}/companies/${company.accountId}/
            String customerAccountRootCompany = customerAccountCompanies + companyAccountId + "/"
            amazonWebService.s3.putObject(defaultBucket, customerAccountRootCompany + fileName, is, objectMetadata)

            // create the company folder for the customerAccount "customerAccounts/${customerAccount.accountId}/companies/${company.accountId}/documents
            String customerAccountRootCompanyDocuments = customerAccountRootCompany + documentsFolder + "/"
            amazonWebService.s3.putObject(defaultBucket, customerAccountRootCompanyDocuments + fileName, is, objectMetadata)

            // create the users folder for the customerAccount "customerAccounts/${customerAccount.accountId}/users"
            String customerAccountRootUsers = customerAccountRoot + usersFolder + "/"
            amazonWebService.s3.putObject(defaultBucket, customerAccountRootUsers + fileName, is, objectMetadata)

            // create the folder for the customerAccount's first user "customerAccounts/${customerAccount.accountId}/users/${user.accountId}/"
            String customerAccountFirstUser = customerAccountRootUsers + userAccountId + "/"
            amazonWebService.s3.putObject(defaultBucket, customerAccountFirstUser + fileName, is, objectMetadata)

            // create the folder for the customerAccount's first user "customerAccounts/${customerAccount.accountId}/users/${user.accountId}/images/"
            String customerAccountFirstUserImages = customerAccountFirstUser + imagesFolder + "/"
            amazonWebService.s3.putObject(defaultBucket, customerAccountFirstUserImages + fileName, is, objectMetadata)

            // create the folder for the customerAccount's first user "customerAccounts/${customerAccount.accountId}/users/${user.accountId}/documents/"
            String customerAccountFirstUserDocuments = customerAccountFirstUser + documentsFolder + "/"
            amazonWebService.s3.putObject(defaultBucket, customerAccountFirstUserDocuments + fileName, is, objectMetadata)

            // create the folder for the customerAccount's leads "customerAccounts/${customerAccount.accountId}/leads/"
            String customerAccountLeadsDocuments = customerAccountRoot + leadsFolder + "/"
            amazonWebService.s3.putObject(defaultBucket, customerAccountLeadsDocuments + fileName, is, objectMetadata)

        } catch (AmazonS3Exception e){
            e.printStackTrace()
            log.error e.message

        }
    }

    /**
     * Creates the first company for the purposes of onboarding the
     * new CustomerAccount.
     * @param customerAccount
     * @param registration
     * @return Company
     */
    Company createFirstCompany(CustomerAccount customerAccount, registration){
        customerAccount.withThisTenant {
            Company company = new Company()
            company.companyName = registration.companyName
            company.doingBusinessAs = registration.companyName
            company.parentCompany = null
            company.intCode = 0
            company.companyType = registration.companyType

            try {
                company.save(flush:true, failOnError:true)
            }catch(ValidationException ve){
                log.error("Save of Company failed with errors ${company.errors}")
                log.error(ve.errors.allErrors)
            }catch(Exception e){
                log.error("Save of Company failed due to unknown errors: " + e.message)
                e.printStackTrace()
            }
            return company
        }
    }

    /**
     * Creates the first company profile which is considered the root company from the
     * information provided on the registration map or the actual Registration domain class.
     * @param customerAccount
     * @param registration
     * @return Company
     */
    CompanyProfile createFirstCompanyProfile(CustomerAccount customerAccount, registration, Company company){

        CompanyProfile companyProfile = new CompanyProfile()
        companyProfile.company = company

        // Save the Company Profile
        companyProfile.save() ?: log.error ("Error saving CompanyProfile errors -> ${companyProfile.errors}")

        CompanyProfileAddress companyProfileAddress = new CompanyProfileAddress()
        companyProfileAddress.address = new Address()
        companyProfileAddress.address.addressOne = registration.streetAddressOne
        companyProfileAddress.address.addressTwo = registration.streetAddressTwo
        companyProfileAddress.address.city = registration.city
        companyProfileAddress.address.country = registration.country
        companyProfileAddress.address.county = registration.county
        companyProfileAddress.address.state = registration.state
        companyProfileAddress.address.zipcode = registration.zipcode
        companyProfileAddress.primaryAddress = true
        companyProfileAddress.companyProfile = companyProfile

        companyProfileAddress.validate() ?: log.error ("Error saving CompanyProfileAddress errors -> ${companyProfileAddress.errors}")

        companyProfile.addToCompanyProfileAddresses(companyProfileAddress)

        CompanyProfilePhoneNumber companyProfilePhoneNumber = new CompanyProfilePhoneNumber()
        companyProfilePhoneNumber.companyProfile = companyProfile
        companyProfilePhoneNumber.phoneNumber = registration.phoneNumber
        companyProfilePhoneNumber.primaryPhoneNumber = true

        companyProfilePhoneNumber.validate() ?: log.error ("Error saving CompanyProfilePhoneNumber errors -> ${companyProfilePhoneNumber.errors}")

        companyProfile.addToCompanyProfilePhoneNumbers(companyProfilePhoneNumber)

        return companyProfile
    }

    /**
     * Creates the first user for a brand new CustomerAccount during the onBoardCustomer process.
     * @param customerAccount
     * @param registration
     * @return User
     */
    User createFirstUser(CustomerAccount customerAccount, registration){
        // create the user based on the passed in registration parameters
        User user = new User()
        user.username = registration.username
        user.password = registration.password
        user.enabled = true
        user.accountExpired = false
        user.accountLocked = false
        user.passwordExpired = false
        user.encodePassword = false  // do not allow the password to be re-encoded

        customerAccount.withThisTenant {

            try {
                user.save(failOnError:true)
            }catch(ValidationException ve){
                log.error ("Error saving User errors -> ${user.errors}")
            }

            // add the user roles for the host_administrator and user_role
            Role roleAdministrator = Role.findByAuthority(CustomerAccountService.ROLE_ADMINISTRATOR)
            assert roleAdministrator, "roleAdministrator CustomerAccountService.ROLE_ADMINISTRATOR was not found - fix this"
            Role roleUser = Role.findByAuthority(CustomerAccountService.ROLE_USER)
            assert roleUser, "roleUser CustomerAccountService.ROLE_USER was not found - fix this "

            // Add the roles to the User
            UserRole.create(user, roleAdministrator)
            UserRole.create(user, roleUser)
        }

        return user
    }
    /**
     * Creates the first UserProfile
     * @param registration
     * @param user
     * @param company
     * @return UserProfile
     */
    UserProfile createFirstUserProfile(registration, User user, Company company){
        // Add the UserProfile for this User
        UserProfile userProfile = new UserProfile()
        userProfile.user = user
        userProfile.firstName = registration.firstName
        userProfile.lastName = registration.lastName
        userProfile.company = company

        try {
            userProfile.save(failOnError: true)
        }catch(ValidationException ve){
            log.error ("Error saving UserProfile errors -> ${userProfile.errors}")
        }

        UserProfileEmailAddress userProfileEmailAddress = new UserProfileEmailAddress()
        userProfileEmailAddress.emailAddress = registration.emailAddress
        userProfileEmailAddress.primaryEmailAddress = Boolean.TRUE
        userProfileEmailAddress.userProfile = userProfile
        try {
            userProfileEmailAddress.save(failOnError:true)
            userProfile.addToUserProfileEmailAddresses(userProfileEmailAddress)
        }catch(ValidationException ve){
            log.error ("Error saving UserProfileEmailAddress errors -> ${userProfileEmailAddress.errors}")
        }
        return userProfile
    }

    /**
     * A method for getting the currently active CurrentTenant's base URL
     * for the purposes of sending email activation links and invitations to the
     * cogda system.
     *
     * <br>
     * Returns baseURLs based on the serverURL setting in config with the subdomain
     * of the CustomerAccount object that is the currentTenant.
     *
     * <br>
     * e.g. http://rais.cogda.com
     *
     * @return String
     */
    public String getCustomerAccountBaseUrl(Integer tenantId){
        CustomerAccount customerAccount = CustomerAccount.get(tenantId)
        String customerAccountBaseUrl = grailsApplication.config.grails.domainURL

        if(customerAccount){
            customerAccountBaseUrl = customerAccount.subDomain + "." + customerAccountBaseUrl
        }

        String protocol = grailsApplication.config.grails.serverURL
        String searchString = "://"
        Integer end = protocol.indexOf(searchString) + searchString.size()
        protocol = protocol.substring(0, end)
        customerAccountBaseUrl = protocol + customerAccountBaseUrl
        return customerAccountBaseUrl
    }

    /**
     * Retrieves the currently active CustomerAccount via the CurrentTenant that is
     * injected into this service.
     * This method Will return null if there is no active CustomerAccount
     * @return CustomerAccount
     */
    public CustomerAccount getActiveCustomerAccount(){
        if(currentTenant){
            CustomerAccount customerAccount = CustomerAccount.get(currentTenant.get())
            return customerAccount
        }
        return null
    }
}
