package com.cogda.security

import com.amazonaws.services.s3.model.GetObjectRequest
import com.cogda.BaseIntegrationTest
import com.cogda.domain.security.Role
import com.cogda.domain.security.User
import com.cogda.multitenant.CustomerAccount
import grails.plugin.awssdk.AmazonWebService
import org.apache.commons.logging.LogFactory
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.junit.After
import org.junit.Before
import static org.junit.Assert.*
import org.junit.*

class UserImportServiceTests extends BaseIntegrationTest {

    private static final log = LogFactory.getLog(this)

    AmazonWebService amazonWebService
    GrailsApplication grailsApplication
    UserImportService userImportService

    @Before
    void setUp() {

    }

    @After
    void tearDown() {

    }

    @Test
    void testLoadUserData(){
        InputStream is = getAmazonTestFile("CogdaUserImportTestFile.csv")  // this is the csv with invalid data
        CustomerAccount customerAccount = new CustomerAccount(subDomain:"fakeSubdomain")
        assert customerAccount.save()


        List userImports = []

        customerAccount.withThisTenant {
            Role.ADMIN_ASSIGNABLE_AUTHORITIES.each {
                Role role = new Role(authority:it)
                assert role.save()
            }

            userImports = userImportService.loadUserData(is)
        }

        assert userImports, "userImports is null and shouldn't be"
        userImports.each {
            log.debug (it)
            println it
        }
    }

    @Test
    void testParseInputStream(){
        InputStream is = getAmazonTestFile("CogdaUserImportTestFile.csv")
        List userImports = userImportService.parseInputStream(is)

        userImports.each {
            log.debug (it)
        }

        assert userImports, "userImports should not be null"
        assert userImports.size() == 10, "userImports should have a size of 10"

    }

    @Test
    void testCreateDefaultUser(){
        String username = "username"

        User user = userImportService.createDefaultUser(username)
        assert user.username.equals(username), "username was not set properly ${username}"
        assert user.password, "User's password is null"
        assert user.enabled, "User.enabled is false"
        assert !user.accountExpired, "User accountExpired is true"
        assert !user.accountLocked, "User accountLocked is true "
        assert user.passwordExpired, "User passwordExpired shoulb be true"
    }

    private InputStream getAmazonTestFile(String fileName){
        String defaultBucket = grailsApplication.config.grails.plugin.awssdk.default.bucket
        InputStream is = amazonWebService.s3.getObject(new GetObjectRequest(defaultBucket, "testingfiles/$fileName")).getObjectContent()
        assert is, "The inputStream that was returned from amazon was null!  Check that someone hasn't deleted the testingfiles/$fileName file."
        return is
    }
}