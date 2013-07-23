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

    void "appendSlash correctly appends slash to the end of a String folder"(){
        expect:
        documentStorageService.appendSlash(paramfolder) == returnFolder

        where:
        paramfolder       | returnFolder
        "something"       | "something/"
        "something/"      | "something/"
        "something/s"     | "something/s/"
        "something/1"     | "something/1/"
        "something/_"     | "something/_/"
        "something/_/"    | "something/_/"
        "something/1/"    | "something/1/"
        "something/*/"    | "something/*/"
        "something/@/"    | "something/@/"
        "something/@"     | "something/@/"


    }

    void "test that the 'folder' names have not been changed"(){
        expect:
        assert DocumentStorageService.getPathPrefix()         == "customerAccounts/"
        assert DocumentStorageService.getCompaniesFolder()    == "companies"
        assert DocumentStorageService.getClientsFolder()      == "clients"
        assert DocumentStorageService.getLeadsFolder()        == "leads"
        assert DocumentStorageService.getImagesFolder()       == "images"
        assert DocumentStorageService.getTempFolder()         == "temp"
        assert DocumentStorageService.getUsersFolder()        == "users"
    }



}