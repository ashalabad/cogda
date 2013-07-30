package com.cogda.multitenant

import grails.plugin.multitenant.core.CurrentTenant
import org.codehaus.groovy.grails.commons.GrailsApplication

/**
 * DocumentStorageService
 * A service class encapsulates the core business logic of a Grails application
 */
class DocumentStorageService {
    /**
     * The character used to separate the Folders
     */
    protected static final String FOLDER_SEPARATOR = "/"

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
    private static final String PATH_PREFIX = "customerAccounts/"

    /**
     * The companiesFolder contains all of the companies for a customerAccount
     */
    private static final String COMPANIES_FOLDER = "companies"

    /**
     * The clients folder is a storage location for storing client specific information for a customerAccount
     */
    private static final String CLIENTS_FOLDER = "clients"

    /**
     * the leads folder is a storage location for storing Lead information for a customerAccount
     */
    private static final String LEADS_FOLDER = "leads"

    /**
     * The imagesFolder contains images for a customerAccount
     */
    private static final String IMAGES_FOLDER = "images"

    /**
     * The tempFolder contains temporary files for a customerAccount
     */
    private static final String TEMP_FOLDER = "temp"

    /**
     * The usersFolder contains user specific files for a customerAccount
     */
    private static final String USERS_FOLDER = "users"

    /**
     * The customerAccountRoot exists on the defaultBucket "customerAccounts/${customerAccount.accountId}/"
     * @return String
     */
    static String getPathPrefix() {
        return PATH_PREFIX
    }

    /**
     * The companiesFolder contains all of the companies associated with a customerAccount
     * <br>
     *     "customerAccounts/${customerAccount.accountId}/companies"
     * @return String
     */
    static String getCompaniesFolder() {
        return COMPANIES_FOLDER
    }
    /**
     * The companiesFolder contains all of the clients associated with a customerAccount
     * @return String
     */
    static String getClientsFolder() {
        return CLIENTS_FOLDER
    }
    /**
     * The leadsFolder contains all of the leads associated with a customerAccount
     * @return String
     */
    static String getLeadsFolder() {
        return LEADS_FOLDER
    }
    /**
     * The imagesFolder contains all of the image files associated with a customerAccount
     * @return String
     */
    static String getImagesFolder() {
        return IMAGES_FOLDER
    }
    /**
     * The tempFolder contains all of the temporary files associated with a customerAccount
     * @return String
     */
    static String getTempFolder() {
        return TEMP_FOLDER
    }

    /**
     * The usersFolder contains all of the user files associated with a customerAccount
     * @return String
     */
    static String getUsersFolder() {
        return USERS_FOLDER
    }

    /**
     * Appends the FOLDER_SEPARATOR to the passed in String if one does not currently
     * exist at the end of the passed in string.
     * @param folder
     * @return String
     */
    protected String appendSlash(String folder){
        if(!folder.endsWith(FOLDER_SEPARATOR)){
            folder += FOLDER_SEPARATOR
        }
        return folder
    }

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

    /**
     * Will return customerAccounts/${customerAccount.accountId}/companies
     * @return
     */
    String getCurrentAccountCompaniesFolder(){
        String folder = appendSlash(currentCustomerAccountFolder)
        folder += DocumentStorageService.getCompaniesFolder()
        return folder
    }
}
