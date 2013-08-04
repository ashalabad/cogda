package com.cogda.storage

import com.cogda.domain.UserProfile
import com.cogda.domain.security.User
import com.cogda.domain.storage.StorageReference
import com.cogda.multitenant.Company
import com.cogda.storage.exceptions.InvalidCompanyRequestException
import com.cogda.storage.exceptions.KeyPairMissingException
import grails.plugins.springsecurity.SpringSecurityService
import org.apache.commons.codec.binary.Base64

import java.nio.file.FileAlreadyExistsException
import java.security.KeyFactory
import java.security.KeyPair
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec

/**
 * FileStorageService
 * A service class encapsulates the core business logic of a Grails application
 */
class FileStorageService {


    AwsStorageService storageService
    SpringSecurityService securityService

    static transactional = true

    /**
     * Create new Storage File
     * @param filePath
     * @param stream
     * @return
     */
    def StorageReference createFile(String filePath,InputStream stream){
        StorageReference item=StorageReference.findByFileReference(filePath)
        if(item!=null)
            throw new FileAlreadyExistsException(filePath)
        StorageObjectMetadata meta=uploadFile(filePath,stream);
        def user=securityService.currentUser as User
        def created=new Date()
        StorageReference sr=new StorageReference(
                user:user,
                md5:meta.md5,
                fileReference: filePath,
                dateCreated: created,
                lastUpdated: created
        )
        sr.save()
    }

    def updateFileById(Long id,InputStream stream){
        StorageReference item=StorageReference.findById(id)
        if(item==null)
            throw new FileNotFoundException("Storage reference $id does not exist.")
        def user=securityService.currentUser as User
        verifyCompany(user,item.user)
        StorageObjectMetadata meta=uploadFile(item.fileReference,stream);
        item.user=user
        item.md5=meta.md5
        item.lastUpdated=new Date()
        item.save()
    }

    /**
     * Load the file stream
     * @param id
     * @return
     */
    InputStream getFileById(Long id){
        StorageReference item=StorageReference.findById(id)
        def settings = getKeyPairAndRegion()
        if(settings.pair==null) {
            throw new KeyPairMissingException("The comany $settings.company.companyName does not have encryption keys.")
        }
        storageService.download(settings.region,item.fileReference,settings.pair)
    }

    /**
     * Delete the item
     * @param id
     */
    void deleteById(Long id) {
        StorageReference item=StorageReference.findById(id)
        if(item==null)
            throw new FileNotFoundException(id.toString())
        def user=securityService.currentUser as User
        verifyCompany(user,item.user)
        storageService.delete(item.fileReference)
        item.delete()
    }
    /**
     * Retrieve the region and the PKI materials from current user company profile
     */
    CompanyStorageSettings getKeyPairAndRegion() {
        def user=securityService.currentUser as User
        def company=UserProfile.findByUser(user)?.company
        def companySettings = company?.companySettings;
        KeyPair keyPair=null
        if(companySettings.keyPublicEncoded!=null && companySettings.keyPrivateEncoded!=null){
            byte[] privateMaterial=Base64.decodeBase64(companySettings.keyPrivateEncoded)
            byte[] publicMaterial=Base64.decodeBase64(companySettings.keyPublicEncoded)
            def factory = KeyFactory.getInstance(companySettings.cipherAlgorithm)
            keyPair=new KeyPair(factory.generatePublic(new X509EncodedKeySpec(publicMaterial)),
                                    factory.generatePrivate(new PKCS8EncodedKeySpec(privateMaterial)))

        }
        new CompanyStorageSettings(
                region:!companySettings.storageRegion ? '' : companySettings.storageRegion,
                pair: keyPair,
                company: company
        )
    }

    /**
     * Private method to upload a file
     * @param filePath
     * @param stream
     * @return
     */
    private def uploadFile(String filePath,InputStream stream){
        def settings = getKeyPairAndRegion()
        if(settings.pair==null) {
            throw new KeyPairMissingException("The comany $settings.company.companyName does not have encryption keys.")
        }
        // check if the file with the same name does not exist for the same company
        storageService.upload(settings.region,filePath,stream,settings.pair)
    }

    /**
     * Verify company settings of the current user and a user that was saved with the item
     * @param currentUser
     * @param savedUser
     */
    private def verifyCompany(User currentUser,User savedUser) {
        def currentCompany=UserProfile.findByUser(currentUser)?.company
        def savedCompany=UserProfile.findByUser(savedUser)?.company
        if(currentCompany!=savedCompany)
            throw new InvalidCompanyRequestException(currentCompany.companyName,savedCompany.companyName)
    }

    /**
     * An inner class that incapsulates storage-related settings from the company settings
     */
    public static class CompanyStorageSettings {
        String region
        KeyPair pair
        Company company
    }
}
