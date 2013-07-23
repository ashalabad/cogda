package com.cogda.multitenant
import com.cogda.multitenant.DocumentStorageService
import com.cogda.multitenant.CustomerAccount
import grails.plugin.spock.IntegrationSpec
import org.codehaus.groovy.grails.commons.GrailsApplication

class DocumentStorageServiceSpec extends IntegrationSpec {

    DocumentStorageService documentStorageService

    CustomerAccountService customerAccountService

    GrailsApplication grailsApplication

    void setupSpec(){

    }

    void setup(){
        documentStorageService.grailsApplication = grailsApplication
        documentStorageService.customerAccountService = customerAccountService
    }

    void "assert that this testing class has the needed test data"(){
        given:
        CustomerAccount customerAccount

        when:
        customerAccount = CustomerAccount.first()

        then:
        assert customerAccount
        assert customerAccount.accountId
        assert customerAccount.tenantId()
        assert customerAccount.subDomain
    }


    void "getCurrentCustomerAccountFolder returns the proper S3 folder location"() {
        given:
        CustomerAccount customerAccount = CustomerAccount.first()
        String currentCustomerAccountFolder = null

        when:
        customerAccount.withThisTenant {
            currentCustomerAccountFolder = documentStorageService.currentCustomerAccountFolder
        }

        then:
        assert currentCustomerAccountFolder.startsWith(documentStorageService.pathPrefix)
        assert currentCustomerAccountFolder.endsWith(customerAccount.accountId)
    }

    void "attempts to change folder names fail"(){
        when:

    }
}