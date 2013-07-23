package com.cogda.multitenant

import grails.plugin.multitenant.core.CurrentTenant
import org.codehaus.groovy.grails.commons.GrailsApplication

/**
 * DocumentStorageService
 * A service class encapsulates the core business logic of a Grails application
 */
class DocumentStorageService {
    /**
     * CustomerAccountService customerAccountService
     */
    CustomerAccountService customerAccountService

    /**
     * GrailsApplication grailsApplication
     */
    GrailsApplication grailsApplication

    /**
     * The root path prefix -
     * The customerAccountRoot exists on the defaultBucket "customerAccounts/${customerAccount.accountId}/"
     */
    private final String pathPrefix = "customerAccounts/"

    /**
     * The companiesFolder contains all of the companies for a customerAccount
     */
    private final String companiesFolder = "companies"

    /**
     * The clients folder is a storage location for storing client specific information for a customerAccount
     */
    private final String clientsFolder = "clients"

    /**
     * the leads folder is a storage location for storing Lead information for a customerAccount
     */
    private final String leadsFolder = "leads"

    /**
     * The imagesFolder contains images for a customerAccount
     */
    private final String imagesFolder = "images"

    /**
     * The tempFolder contains temporary files for a customerAccount
     */
    private final String tempFolder = "temp"

    /**
     * The usersFolder contains user specific files for a customerAccount
     */
    private final String usersFolder = "users"

    /**
     * The customerAccountRoot exists on the defaultBucket "customerAccounts/${customerAccount.accountId}/"
     * @return String
     */
    String getPathPrefix() {
        return pathPrefix
    }

    /**
     * The companiesFolder contains all of the companies associated with a customerAccount
     * <br>
     *     "customerAccounts/${customerAccount.accountId}/companies"
     * @return String
     */
    String getCompaniesFolder() {
        return companiesFolder
    }
    /**
     * The companiesFolder contains all of the clients associated with a customerAccount
     * @return String
     */
    String getClientsFolder() {
        return clientsFolder
    }
    /**
     * The leadsFolder contains all of the leads associated with a customerAccount
     * @return String
     */
    String getLeadsFolder() {
        return leadsFolder
    }
    /**
     * The imagesFolder contains all of the image files associated with a customerAccount
     * @return String
     */
    String getImagesFolder() {
        return imagesFolder
    }
    /**
     * The tempFolder contains all of the temporary files associated with a customerAccount
     * @return String
     */
    String getTempFolder() {
        return tempFolder
    }

    /**
     * The usersFolder contains all of the user files associated with a customerAccount
     * @return String
     */
    String getUsersFolder() {
        return usersFolder
    }

    static transactional = true

    /**
     * Returns the folder from S3 for the currently active CustomerAccount based on
     * the CurrentTenant.
     * <br>
     *     e.g. for CustomerAccount with accountId "12345678910" this method will return
     *     "customerAccounts/1234567890"
     * @return
     */
    public String getCurrentCustomerAccountFolder(){
        CustomerAccount customerAccount = customerAccountService.activeCustomerAccount
        if(customerAccount){
            return pathPrefix + customerAccount.accountId
        }
        return null
    }

    /**
     * The defaultBucket is the bucket that is currently being used to store S3 objects.
     */
    public String getDefaultBucket(){
        return grailsApplication.config.grails.plugin.awssdk.default.bucket
    }
}
