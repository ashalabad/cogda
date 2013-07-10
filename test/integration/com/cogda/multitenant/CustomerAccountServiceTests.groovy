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

    CustomerAccountService customerAccountService
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

        createCompanyTypes()  // only if needed
    }

    @After
    void tearDown() {

    }

    /**
     * tests the creation of a new Customer Account
     */
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

    /**
     * A Frustrating Example of what happens with a Domain Class that is not @MultiTenant and one that is.
     * When running this integration test save of the company will not work due to the fact that the save
     * happens within a new session and a new transaction.
     */
    @Test
    void testSaveCompanyTypeThenSaveCompany(){
        CustomerAccount customerAccount
        String subDomain = "testRais"
        try {
            customerAccount = customerAccountService.createCustomerAccount(subDomain)
            assert !customerAccount.hasErrors(), "CustomerAccount has Validation Errors."
        }catch(Exception e){
            assert !e, "Exception thrown ${e.message}"
        }
        CompanyType companyType
        CompanyType.withTransaction {
            companyType = new CompanyType(code:"Fake Company Type", intCode:999, description: "Agency/Retailer")
            companyType.save(flush:true, failOnError:true)
            assert companyType, "companyType is null"
            assert !companyType.hasErrors(), "companyType has errors - ${companyType.errors}"
        }

        Company company = new Company()
        company.companyName = "A New Company Name"
        company.doingBusinessAs = "A New Company Name"
        company.intCode = 0
        company.parentCompany = null
        company.companyType = companyType
        assert company.validate(), "Company is not valid - ${company.errors}"
        Boolean exceptionThrown = false
        Boolean flushExceptionThrown = false
        try {
            customerAccount.withThisTenant {
                try {
                    company.save(flush:true, failOnError:true)
                }catch(Exception e){
                    exceptionThrown = Boolean.TRUE
                }
            }
        }catch(Exception e2){
            // the save inside this method will fail - and in so doing the
            // multiTenantService will still attempt to flush the hibernate session
            // due to the fact that it runs inside of a transaction.

            flushExceptionThrown = true
        }
        assert exceptionThrown, "The expected behavior with the multi-tenant plugin was that this will throw an error that it cannotAcquireLockException due to the fact that all integration tests run inside a transaction and are rolled back at the completion of the transaction - the problem that arises is that adding a CompanyType to the database is not recognized since the save inside the withThisTenant closure is executed within a newSession and a newTransaction that is unaware of the outlying Hibernate Session"
        assert flushExceptionThrown, "The expected behavior is that the transaction inside MultiTenantService.doWithTenantId will be flushed even if there was an error saving the Company - this will trigger a org.hibernate.AssertionFailure"
        exceptionThrown = false
        customerAccount.withThisTenant {
            try {
                companyType = new CompanyType(code:"Fake Company ----", intCode:1, description: "Agency/Retailer")
                companyType.save(flush:true, failOnError:true)
                assert companyType, "companyType is null"
                assert !companyType.hasErrors(), "companyType has errors - ${companyType.errors}"

                company.companyType = companyType
                company.save(flush:true, failOnError:true)
            }catch(Exception e){
                exceptionThrown = Boolean.TRUE
            }
        }
        assert !exceptionThrown, "An Exception was thrown and it shouldn't have been thrown when saving the company"
        assert company, "company is null"
        assert !company.hasErrors(), "company has errors ${company.errors}"
    }

    void testCreateFirstCompanyCustomerAccountRegistration(){
        Map registration = getValidRegistrationMap()
        CustomerAccount customerAccount
        try {
            customerAccount = customerAccountService.createCustomerAccount(registration.subDomain)
            assert !customerAccount.hasErrors(), "CustomerAccount has Validation Errors."
        }catch(Exception e){
            assert !e, "Exception thrown ${e.message}"
        }

        Company company = customerAccountService.createFirstCompany(customerAccount, registration)
        assert company, "Company was not created successfully"
        assert company.id, "The company does not have an identifier"
        assert company.companyName.equals(registration.companyName), "Company Name does not match Registration Company Name"
        assert company.doingBusinessAs.equals(registration.companyName), "Doing Business As does not match Registration Company Name"
        assert company.parentCompany == null, "Parent Company should be null"
        assert company.intCode == 0, "Company intCode should be 0"
        assert company.companyType, "Company Type is null on the Company"
        assert company.companyType.code == registration.companyType.code, "CompanyType was not set properly to ${registration.companyType}"
        assert company.accountId, "Company accountId is null"
    }

    /**
     * Tests every method in the CustomerAccountService
     * since it uses every method in the CustomerAccountService
     */
    @Test
    void testOnboardCustomerAccount() {

        Map registration = getValidRegistrationMap()

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
            assert company.companyType, "Company Type is null on the Company"
            assert company.companyType.code == registration.companyType.code, "CompanyType was not set properly to ${registration.companyType}"
            assert company.accountId, "Company accountId is null"
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

    @Test
    void testCreateFirstCompanyProfile(){

    }



    private Map getValidRegistrationMap(){
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
        return registration
    }
}
