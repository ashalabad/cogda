package com.cogda.multitenant

import com.amazonaws.AmazonClientException
import com.amazonaws.AmazonServiceException
import com.amazonaws.services.s3.model.ObjectListing

import com.amazonaws.services.s3.model.S3ObjectSummary
import com.cogda.BaseIntegrationTest
import com.cogda.common.RegistrationStatus
import com.cogda.domain.CompanyProfile
import com.cogda.domain.CompanyProfileAddress
import com.cogda.domain.CompanyProfilePhoneNumber
import com.cogda.domain.CompanyProfileService
import com.cogda.domain.UserProfile
import com.cogda.domain.UserProfileEmailAddress
import com.cogda.domain.UserProfileService
import com.cogda.domain.admin.CompanyType
import com.cogda.domain.security.Role
import com.cogda.domain.security.User
import com.cogda.domain.security.UserRole
import grails.plugin.awssdk.AmazonWebService
import grails.plugins.springsecurity.SpringSecurityService
import org.apache.commons.logging.LogFactory
import org.codehaus.groovy.grails.commons.GrailsApplication

import static org.junit.Assert.*
import org.junit.*

class CustomerAccountServiceTests extends BaseIntegrationTest{

    private static final log = LogFactory.getLog(this)

    SpringSecurityService springSecurityService
    CustomerAccountService customerAccountService
    CompanyService companyService
    UserProfileService userProfileService
    CompanyProfileService companyProfileService
    AmazonWebService amazonWebService
    GrailsApplication grailsApplication

    private void deleteAllObjectsInTestAmazonBucket(){
        final String defaultBucket = grailsApplication.config.grails.plugin.awssdk.default.bucket
        final String pathPrefix = "customerAccounts/"

        ObjectListing objectListing = amazonWebService.s3.listObjects(defaultBucket, pathPrefix)

        List objectSummaries = objectListing.objectSummaries

        objectSummaries.each { S3ObjectSummary objectSummary ->
            amazonWebService.s3.deleteObject(defaultBucket, objectSummary.key)
        }
    }

    @Before
    void setUp() {

        deleteAllObjectsInTestAmazonBucket()

        createCompanyTypes()
    }

    @After
    void tearDown() {

    }

    @Test
    void testCreateCustomerAccount(){
        String subDomain = "salsldfsfdlssalsfd"
        try {
            CustomerAccount customerAccount = customerAccountService.createCustomerAccount(subDomain)
            assert !customerAccount.hasErrors(), "CustomerAccount has Validation Errors."
        }catch(Exception e){
            assert !e, "Exception thrown ${e.message}"
        }
    }

    @Test
    void testCreateCustomerAccountThenCompany(){
        String subDomain = "djjjakka"
        CustomerAccount customerAccount
        try {
            customerAccount = customerAccountService.createCustomerAccount(subDomain)
            assert !customerAccount.hasErrors(), "CustomerAccount has Validation Errors."
        }catch(Exception e){
            assert !e, "Exception thrown ${e.message}"
        }
        Company company = new Company()
        company.companyName = "A New Company Name"
        company.doingBusinessAs = "A New Company Name"
        company.intCode = 0
        company.parentCompany = null

        customerAccount.withThisTenant {
            company.save(flush:true)
        }

        assert !company.hasErrors(), "Errors found on Company errors -> ${company.errors}"
        assert company.id, "Company id is null"
        log.debug ("Company was successfully created id: ${company.id}, companyName: ${company.companyName} ")

    }

    @Test
    void testCreateCustomerAccountThenCompanyAndUser(){
        String subDomain = "djjjaueueu"
        CustomerAccount customerAccount
        try {
            customerAccount = customerAccountService.createCustomerAccount(subDomain)
            assert !customerAccount.hasErrors(), "CustomerAccount has Validation Errors."
        }catch(Exception e){
            assert !e, "Exception thrown ${e.message}"
        }
        Company company = new Company()
        company.companyName = "A New Company Name"
        company.doingBusinessAs = "A New Company Name"
        company.intCode = 0
        company.parentCompany = null

        User user = new User()
        user.username = "alllwlwllwlsl"
        user.enabled = Boolean.TRUE
        user.password = "password"

        println User.list()

        customerAccount.withThisTenant {
            company.save()
            user.save()
        }

        assert !company.hasErrors(), "Errors found on Company errors -> ${company.errors}"
        assert company.id, "Company id is null"
        assert !user.hasErrors(), "Errors found on User errors -> ${user.errors}"
        assert user.id, "User id is null"



        log.debug ("Company was successfully created id: ${company.id}, companyName: ${company.companyName} ")
        log.debug ("User was successfully created id: ${user.id}, username: ${user.username}")
    }

    @Test
    void testCreateCustomerAccountThenCompanyAndUserAndRoles(){
        String subDomain = "djjjaueueu"
        CustomerAccount customerAccount
        try {
            customerAccount = customerAccountService.createCustomerAccount(subDomain)
            assert !customerAccount.hasErrors(), "CustomerAccount has Validation Errors."
        }catch(Exception e){
            assert !e, "Exception thrown ${e.message}"
        }
        Company company = new Company()
        company.companyName = "A New Company Name"
        company.doingBusinessAs = "A New Company Name"
        company.intCode = 0
        company.parentCompany = null

        User user = new User()
        user.username = "akkdjsk"
        user.enabled = Boolean.TRUE
        user.password = "password"

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
            company.save()
            user.save()
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

        assert !company.hasErrors(), "Errors found on Company errors -> ${company.errors}"
        assert company.id, "Company id is null"
        assert !user.hasErrors(), "Errors found on User errors -> ${user.errors}"
        assert user.id, "User id is null"
        assert !hostAdministratorRole.hasErrors()
        assert !underwriterRole.hasErrors()
        assert !customerServiceRepRole.hasErrors()
        assert !branchManagerRole.hasErrors()
        assert !marketingManagerRole.hasErrors()
        assert !salesManagerRole.hasErrors()
        assert !marketerRole.hasErrors()
        assert !producerRole.hasErrors()
        assert !userRole.hasErrors()
        assert !companyManagerRole.hasErrors()
        assert !administratorRole.hasErrors()


        log.debug ("Company was successfully created id: ${company.id}, companyName: ${company.companyName} ")
        log.debug ("User was successfully created id: ${user.id}, username: ${user.username}")
    }

    @Test
    void testCreateCustomerAccountThenCompanyUserRolesAndCompanyUserProfiles(){
        String subDomain = "djjjaueueu"
        CustomerAccount customerAccount
        try {
            customerAccount = customerAccountService.createCustomerAccount(subDomain)
            assert !customerAccount.hasErrors(), "CustomerAccount has Validation Errors."
        }catch(Exception e){
            assert !e, "Exception thrown ${e.message}"
        }
        Company company = new Company()
        company.companyName = "A New Company Name"
        company.doingBusinessAs = "A New Company Name"
        company.intCode = 0
        company.parentCompany = null

        User user = new User()
        user.username = "availableusername"
        user.enabled = Boolean.TRUE
        user.password = "password"

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
            company.save()
            user.save()
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

        // Post @MultiTenant object saves we should be able to associate the User with a UserProfile and a Company with a CompanyProfile
        UserProfile userProfile = new UserProfile()
        userProfile.user = user
        userProfile.firstName = "Test"
        userProfile.lastName = "Test"
        userProfile.published = true

        userProfile.save(flush:true) ?: log.error ("Unable to save userProfile - Failed with ${userProfile.errors}")

        CompanyProfile companyProfile = new CompanyProfile()
        companyProfile.company = company
        companyProfile.companyType = CompanyType.findByCode("Carrier")

        companyProfile.save(flush:true) ?: log.error ("Unable to save companyProfile - Failed with ${companyProfile.errors}")

        assert !company.hasErrors(), "Errors found on Company errors -> ${company.errors}"
        assert company.id, "Company id is null"
        assert !user.hasErrors(), "Errors found on User errors -> ${user.errors}"
        assert user.id, "User id is null"
        assert !hostAdministratorRole.hasErrors()
        assert hostAdministratorRole.id
        assert !underwriterRole.hasErrors()
        assert underwriterRole.id
        assert !customerServiceRepRole.hasErrors()
        assert customerServiceRepRole.id
        assert !branchManagerRole.hasErrors()
        assert branchManagerRole.id
        assert !marketingManagerRole.hasErrors()
        assert marketingManagerRole.id
        assert !salesManagerRole.hasErrors()
        assert salesManagerRole.id
        assert !marketerRole.hasErrors()
        assert marketerRole.id
        assert !producerRole.hasErrors()
        assert producerRole.id
        assert !userRole.hasErrors()
        assert userRole.id
        assert !companyManagerRole.hasErrors()
        assert companyManagerRole.id
        assert !administratorRole.hasErrors()
        assert administratorRole.id
        assert !userProfile.hasErrors()
        assert userProfile.id
        assert userProfile.user.id
        assert !companyProfile.hasErrors()
        assert companyProfile.id
        assert companyProfile.company.id


        log.debug ("Company was successfully created id: ${company.id}, companyName: ${company.companyName} ")
        log.debug ("User was successfully created id: ${user.id}, username: ${user.username}")
        log.debug ("Company was successfully created id: ${company.id}, companyName: ${company.companyName}")
        log.debug ("UserProfile was successfully created id: ${userProfile.id}, firstName: ${userProfile.firstName}")
        log.debug ("CompanyProfile was successfully created id: ${companyProfile.id}, companyType: ${companyProfile.companyType.code}")




    }

    @Test
    void testOnboardCustomerAccount() {

        Map registration = [:]
        registration.firstName = "Christopher"
        registration.lastName = "Kwiatkowski"
        registration.username = "ctk"
        registration.emailAddress = "chris@cogda.com"
        registration.password = "939020kiddko2"
        registration.companyName = "Cogda Solutions, LLC."
        registration.companyType = CompanyType.list().first()
        registration.existingCompany = null
        registration.companyTypeOther = null
        registration.phoneNumber = "706-255-9087"
        registration.streetAddressOne = "1 Press Place"
        registration.streetAddressTwo = "Suite 200"
        registration.streetAddressThree = "Office #17"
        registration.city = "Athens"
        registration.state = "GA"
        registration.zipcode = "30601"
        registration.county = "CLARKE"
        registration.registrationStatus = RegistrationStatus.APPROVED
        registration.subDomain = "rais"

        log.debug("Calling customerAccountService.onboardCustomerAccount(registration)")
        customerAccountService.onboardCustomerAccount(registration)

        // verify CustomerAccount setup - check that the CustomerAccount was saved successfully
        CustomerAccount customerAccount = CustomerAccount.findBySubDomain(registration.subDomain)
        assert customerAccount, "CustomerAccount with subDomain ${registration.subDomain} was not created successfully"

        Long companyId = null

        // verify Security setup - check that all Role objects were created successfully
        customerAccount.withThisTenant {

            // verify First User setup - check that the initial administrator of the root Company was created successfully
            [
                    CustomerAccountService.ROLE_HOST_ADMINISTRATOR,
                    CustomerAccountService.ROLE_UNDERWRITER,
                    CustomerAccountService.ROLE_CUSTOMER_SERVICE_REP,
                    CustomerAccountService.ROLE_BRANCH_MANAGER,
                    CustomerAccountService.ROLE_MAREKETING_MANAGER,
                    CustomerAccountService.ROLE_SALES_MANAGER,
                    CustomerAccountService.ROLE_MARKETER,
                    CustomerAccountService.ROLE_PRODUCER,
                    CustomerAccountService.ROLE_USER,
                    CustomerAccountService.ROLE_COMPANY_MANAGER,
                    CustomerAccountService.ROLE_ADMINISTRATOR
            ].each { String role ->
                assert Role.findByAuthority(role), "$role was not created successfully"
            }

            // verify Company setup - Check that the root Company was setup appropriately
            Company company = Company.findByCompanyName(registration.companyName)
            assert company, "Company was not created successfully"
            assert company.companyName.equals(registration.companyName), "Company Name does not match Registration Company Name"
            assert company.doingBusinessAs.equals(registration.companyName), "Doing Business As does not match Registration Company Name"
            assert company.parentCompany == null, "Parent Company should be null"
            assert company.intCode == 0, "Company intCode should be 0"
            companyId = company.id

            // verify First User setup - check that the initial administrator of the root Company was created successfully
            User user = User.findByUsername(registration.username)
            assert user, "User is null"

            List userRoles = UserRole.findAllByUser(user)
            List<String> roles = userRoles.collect { UserRole userRole ->
                userRole.role.authority
            }

            assert roles.size() == 2, "UserRoles associated with User should be 2"
            assert roles.containsAll([CustomerAccountService.ROLE_ADMINISTRATOR, CustomerAccountService.ROLE_USER]), "Roles were not assigned successfully for the first User"

        }



        // Verify the UserProfile is associated with the initial administrator and the properties
        assert UserProfile.count() == 1, "UserProfile count should be 1"
        UserProfile userProfile = UserProfile.list().first()
        assert userProfile, "UserProfile was not found"
        assert userProfile.firstName.equals(registration.firstName)
        assert userProfile.lastName.equals(registration.lastName)
        assert userProfile.userProfileEmailAddresses.size() == 1, "UserProfileEmailAddress was not added successfully"
        assert userProfile.user.username.equals(registration.username), "Username of the Registration did not match the UserProfile.user.username"

        UserProfileEmailAddress upea = userProfile.userProfileEmailAddresses.first()
        assert upea.primaryEmailAddress, "primaryEmailAddress should be true"
        assert upea.emailAddress.equals(registration.emailAddress), "emailAddress does not match registration email address"

        // Verify the CompanyProfile is associated with the initial Company and its properties
        CompanyProfile companyProfile = CompanyProfile.executeQuery("from CompanyProfile cp where cp.company.id = ? ", [companyId]).first()

        assert companyProfile.company, "CompanyProfile does not have an association to a company"
        assert companyProfile.company.companyName.equals(registration.companyName)
        assert companyProfile.companyType.equals(registration.companyType)
        assert companyProfile.companyProfileAddresses.size() == 1
        CompanyProfileAddress companyProfileAddress = companyProfile.companyProfileAddresses.first()
        assert companyProfileAddress, "CompanyProfileAddress was not found"
        assert companyProfileAddress.address.addressOne.equals(registration.streetAddressOne)
        assert companyProfileAddress.address.addressTwo.equals(registration.streetAddressTwo)
        assert companyProfileAddress.address.city.equals(registration.city)
        assert companyProfileAddress.address.country.equals(registration.country)
        assert companyProfileAddress.address.county.equals(registration.county)
        assert companyProfileAddress.address.state.equals(registration.state)
        assert companyProfileAddress.address.zipcode.equals(registration.zipcode)
        assert companyProfileAddress.primaryAddress == Boolean.TRUE


        assert companyProfile.companyProfilePhoneNumbers.size() == 1
        CompanyProfilePhoneNumber companyProfilePhoneNumber = companyProfile.companyProfilePhoneNumbers.first()
        assert companyProfilePhoneNumber.primaryPhoneNumber == Boolean.TRUE
        assert companyProfilePhoneNumber.phoneNumber.phoneNumber.equals(registration.phoneNumber)


        // Test that the Amazon s3 folders were created successfully
        final String defaultBucket = grailsApplication.config.grails.plugin.awssdk.default.bucket
        final String pathPrefix = "customerAccounts/"
        final String companiesFolder = "companies"
        final String clientsFolder = "clients"
        final String imagesFolder = "images"
        final String tempFolder = "temp"
        final String usersFolder = "users"

        // delete everything under customerAccounts/
        ObjectListing objectListing = amazonWebService.s3.listObjects(defaultBucket, pathPrefix)

        List objectSummaries = objectListing.objectSummaries

        assert objectSummaries.size() == 11

        objectSummaries.each { S3ObjectSummary objectSummary ->
            try {
                amazonWebService.s3.deleteObject(defaultBucket, objectSummary.key)
            }catch(AmazonClientException ace){
                log.error ("Unable to clean the integration test's AmazonWebServices folder structure ${objectSummary.key}")
                log.error ("AmazonClientException ${ace.message}")
            }catch(AmazonServiceException ase){
                log.error ("Unable to clean the integration test's AmazonWebServices folder structure ${objectSummary.key}")
                log.error ("AmazonServiceException ${ase.message}")
            }
        }

    }


}
