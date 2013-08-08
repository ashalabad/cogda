package com.cogda.storage

import com.cogda.domain.UserProfile
import com.cogda.domain.storage.StorageReference
import com.cogda.storage.exceptions.InvalidCompanyRequestException
import com.cogda.storage.exceptions.KeyPairMissingException
import grails.plugins.springsecurity.SpringSecurityService
import org.apache.commons.codec.binary.Base64
import com.cogda.domain.security.User
import com.cogda.multitenant.Company
import com.cogda.multitenant.CompanySettings

import grails.test.mixin.*

import java.nio.file.FileAlreadyExistsException
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.SecureRandom

@TestFor(FileStorageService)
@Mock([StorageReference,User])
class FileStorageServiceSpec {


    private final static def text="A quick brown fox jumped over a lazy dog back. And ran away as fast as it could. what a smart fox"
    private final static def defaultFilePath="000/001/text.docx"
    private final static def defaultRegion="us-west-1"

    User user
    User wrongUser;

    Company company
    Company wrongCompany;

    CompanySettings companySettings
    /**
     * Setup and mock dependencies
     */
    void setUp()
    {
        companySettings=new CompanySettings(storageRegion:defaultRegion)
        generateKeyPair(companySettings)
        company=new Company(
                id:1,
                companyName: "ACME")
        company.companySettings =companySettings
        wrongCompany=new Company(
                id:2,
                companyName: "WRONG, INC"
        )
        wrongCompany.companySettings=new CompanySettings(storageRegion: defaultRegion)
        generateKeyPair(wrongCompany.companySettings)
        UserProfile.metaClass.static.findByUser={User user->
            switch (user.username){
                case "createdBy":return new UserProfile(company:company)
                case "wrong-createdBy":return new UserProfile(company: wrongCompany)
                default: throw new Exception(user.username)
            }
        }

        def springMock = mockFor(SpringSecurityService,false)
        springMock.demand.encodePassword(0..10){String password->
            "encodedPassword"
        }

        user=new User(
                username: "createdBy",
                password: "password",
                enabled: true,
                accountLocked: false
        )
        user.springSecurityService=springMock.createMock()
        user.save(
                failOnError: true,
                flush: true)

        wrongUser=new User(
                username: "wrong-createdBy",
                password: "password",
                enabled: true,
                accountLocked: false
        )
        wrongUser.springSecurityService=springMock.createMock()
        wrongUser.save(
                failOnError: true,
                flush: true)

    }

    void tearDown()
    {
        user.delete(flush: true)
    }

    private static void generateKeyPair(CompanySettings cs){
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(1024, new SecureRandom());
        KeyPair pair = keyGenerator.generateKeyPair();
        cs.keyPrivateEncoded = Base64.encodeBase64String(pair.private.encoded)
        cs.keyPublicEncoded = Base64.encodeBase64String(pair.public.encoded)
        cs.cipherAlgorithm="RSA"
    }
    /**
     *
     * Get key material from a Company settings
     */
    void testGetKeyMaterial()
    {
        def springMock = mockFor(SpringSecurityService,true)
        springMock.demand.getCurrentUser(1){->user}
        service.securityService=(SpringSecurityService)springMock.createMock()
        def storageMock
        storageMock = mockFor(AwsStorageService, true)
        service.storageService=(AwsStorageService)storageMock.createMock()

        def values = service.keyPairAndRegion
        assert values
        assert values.region==defaultRegion
        assert  values.pair
        KeyPair pair= values.pair
        def profile=UserProfile.findByUser(user)
        assert profile.company.companySettings.keyPrivateEncoded==Base64.encodeBase64String(pair.private.encoded)
        assert profile.company.companySettings.keyPublicEncoded==Base64.encodeBase64String(pair.public.encoded)
    }
    /**
     * Create and upload new file
     */
    void testCreateFile()
    {
        def springMock = mockFor(SpringSecurityService,true)
        springMock.demand.getCurrentUser(1..2){->
            user.encodePassword=false
            user
        }
        service.securityService=(SpringSecurityService)springMock.createMock()
        def storageMock=mockFor(AwsStorageService,true)

        storageMock.demand.upload({
            region,filePath,stream,pair->
                assert region==defaultRegion
                assert filePath==defaultFilePath
                assert stream
                assert pair
                new StorageObjectMetadata(
                    isEncrypted: true,
                    isCompressed: true,
                    md5:"12345"
            )
        })
        service.storageService=(AwsStorageService)storageMock.createMock()
        ByteArrayInputStream bs=new ByteArrayInputStream(text.bytes)
        def ref=service.createFile(defaultFilePath,bs)
        assert ref
        assert ref.id>0
        assert ref.fileReference==defaultFilePath
        def storageRef = StorageReference.findById(ref.id)
        assert storageRef==ref
        storageMock.verify()
        springMock.verify()
    }
    /**
     * Download file test
     */
    void testDownloadFile(){
        def springMock = mockFor(SpringSecurityService,true)
        springMock.demand.getCurrentUser(1..2){->
            user
        }
        service.securityService=(SpringSecurityService)springMock.createMock()
        def storageMock=mockFor(AwsStorageService,true)

        storageMock.demand.download({
            region,path,keys->
                assert region==defaultRegion
                assert path==defaultFilePath
                assert keys
                new ByteArrayInputStream(text.bytes)
        })
        service.storageService=(AwsStorageService)storageMock.createMock()

        StorageReference sr=new StorageReference(
                dateCreated: new Date(),
                lastUpdated: new Date(),
                user:user,
                md5:"12345",
                fileReference: defaultFilePath
        )
        sr.save(failOnError: true,flush: true)
        InputStream stream=service.getFileById(sr.id)
        assert stream
        assertEquals text,streamToString(stream)
        springMock.verify()
        storageMock.verify()

    }
    /**
     * Trying to upload new file when a company settings don't have a PKI key pair
     * The upload operation should fail. We do not allow upload without key pair
     */
    void testCreateFileNoKeyPair()
    {
        companySettings.keyPrivateEncoded=null
        companySettings.keyPublicEncoded=null
        def springMock = mockFor(SpringSecurityService,true)
        springMock.demand.getCurrentUser{->
            user
        }
        service.securityService=(SpringSecurityService)springMock.createMock()
        def storageMock
        storageMock = mockFor(AwsStorageService, true)
        service.storageService=(AwsStorageService)storageMock.createMock()
        ByteArrayInputStream bs=new ByteArrayInputStream(text.bytes)

        shouldFail(KeyPairMissingException) {
            service.createFile(defaultFilePath,bs)
        }
        storageMock.verify()
        springMock.verify()

    }

    void testCreateFileFileExists()
    {
        def springMock = mockFor(SpringSecurityService,true)
        springMock.demand.getCurrentUser(1..2){->
            user.encodePassword=false
            user
        }
        service.securityService=(SpringSecurityService)springMock.createMock()
        def storageMock=mockFor(AwsStorageService,true)

        storageMock.demand.upload({
            region,filePath,stream,pair->
                assert region==defaultRegion
                assert filePath==defaultFilePath
                assert stream
                assert pair
                new StorageObjectMetadata(
                        isEncrypted: true,
                        isCompressed: true,
                        md5:"12345"
                )
        })
        service.storageService=(AwsStorageService)storageMock.createMock()
        service.createFile(defaultFilePath,new ByteArrayInputStream(text.bytes))
        shouldFail(FileAlreadyExistsException) {
            service.createFile(defaultFilePath,new ByteArrayInputStream(text.bytes))
        }
        storageMock.verify()
        springMock.verify()    }
    /**
     * Update a file by Id
     */
    void testUpdateFileById()
    {
        def springMock = mockFor(SpringSecurityService,true)
        springMock.demand.getCurrentUser(1..4){->
            user.encodePassword=false
            user
        }
        service.securityService= (SpringSecurityService)springMock.createMock()
        def storageMock=mockFor(AwsStorageService,true)

        storageMock.demand.upload(1..2){
            region,filePath,stream,pair->
                assert region==defaultRegion
                assert filePath==defaultFilePath
                assert stream
                assert pair
                new StorageObjectMetadata(
                        isEncrypted: true,
                        isCompressed: true,
                        md5:"12345"
                )
        }
        service.storageService= (AwsStorageService)storageMock.createMock()
        ByteArrayInputStream bs=new ByteArrayInputStream(text.bytes)
        def refInitial=service.createFile(defaultFilePath,bs)
        assert refInitial
        assert refInitial.id>0
        assert refInitial.fileReference==defaultFilePath
        service.updateFileById(refInitial.id,new ByteArrayInputStream(text.bytes))
        def storageRef = StorageReference.findById(refInitial.id)
        assert storageRef==refInitial
        storageMock.verify()
        springMock.verify()
    }

    void testUpdateFileByIdEmptyKeyPair(){

        def springMock = mockFor(SpringSecurityService,true)
        springMock.demand.getCurrentUser(1..4){->
            user.encodePassword=false
            user
        }
        service.securityService= (SpringSecurityService)springMock.createMock()
        def storageMock=mockFor(AwsStorageService,true)

        storageMock.demand.upload(1..2){
            region,filePath,stream,pair->
                assert region==defaultRegion
                assert filePath==defaultFilePath
                assert stream
                assert pair
                new StorageObjectMetadata(
                        isEncrypted: true,
                        isCompressed: true,
                        md5:"12345"
                )
        }
        service.storageService= (AwsStorageService)storageMock.createMock()
        ByteArrayInputStream bs=new ByteArrayInputStream(text.bytes)
        def refInitial=service.createFile(defaultFilePath,bs)
        // set key pair to null
        companySettings.keyPrivateEncoded=null
        companySettings.keyPublicEncoded=null
        shouldFail(KeyPairMissingException) {
            service.updateFileById(refInitial.id,new ByteArrayInputStream(text.bytes))
        }
        storageMock.verify()
        springMock.verify()

    }
    /**
     *
     */
    void testUpdateFileDoesNotExits()
    {
        shouldFail(FileNotFoundException) {
            service.updateFileById(100,new ByteArrayInputStream(text.bytes))
        }
    }

    /**
     *
     */
    void testUpdateFileByIdWrongCompany()
    {
        def springMock = mockFor(SpringSecurityService,true)
        springMock.demand.getCurrentUser(1..4){->
            user
        }
        service.securityService= (SpringSecurityService)springMock.createMock()
        def storageMock=mockFor(AwsStorageService,true)

        storageMock.demand.upload(1..2){
            region,filePath,stream,pair->
                assert region==defaultRegion
                assert filePath==defaultFilePath
                assert stream
                assert pair
                new StorageObjectMetadata(
                        isEncrypted: true,
                        isCompressed: true,
                        md5:"12345"
                )
        }
        service.storageService= (AwsStorageService)storageMock.createMock()
        def refInitial=service.createFile(defaultFilePath,new ByteArrayInputStream(text.bytes))
        // Change the company profile
        springMock.demand.getCurrentUser(1..4){->
            wrongUser
        }
        service.securityService= (SpringSecurityService)springMock.createMock()

        shouldFail(InvalidCompanyRequestException) {
            service.updateFileById(refInitial.id,new ByteArrayInputStream(text.bytes))
        }
        storageMock.verify()

    }

    /**
     *
     */
    void testDeleteFile()
    {
        def springMock = mockFor(SpringSecurityService,true)
        springMock.demand.getCurrentUser(1..3){->
            user.encodePassword=false
            user
        }
        service.securityService=(SpringSecurityService)springMock.createMock()
        def storageMock=mockFor(AwsStorageService,true)

        storageMock.demand.upload({
            region,filePath,stream,pair->
                assert region==defaultRegion
                assert filePath==defaultFilePath
                assert stream
                assert pair
                new StorageObjectMetadata(
                        isEncrypted: true,
                        isCompressed: true,
                        md5:"12345"
                )
        })
        storageMock.demand.delete({
            String path->
        })
        service.storageService=(AwsStorageService)storageMock.createMock()
        def ref=service.createFile(defaultFilePath,new ByteArrayInputStream(text.bytes))
        assert ref
        assert ref.id>0
        assert ref.fileReference==defaultFilePath
        service.deleteById(ref.id)
        assertNull StorageReference.findById(ref.id)
        storageMock.verify()
        springMock.verify()
    }

    /**
     *
     */
    void testDeleteFileDoesNotExist()
    {
        shouldFail(FileNotFoundException) {
            service.deleteById(10)
        }
    }

    void testDeleteFileWrongCompany()
    {
        def springMock = mockFor(SpringSecurityService,true)
        springMock.demand.getCurrentUser(1..4){->
            user
        }
        service.securityService= (SpringSecurityService)springMock.createMock()
        def storageMock=mockFor(AwsStorageService,true)

        storageMock.demand.upload(1..2){
            region,filePath,stream,pair->
                assert region==defaultRegion
                assert filePath==defaultFilePath
                assert stream
                assert pair
                new StorageObjectMetadata(
                        isEncrypted: true,
                        isCompressed: true,
                        md5:"12345"
                )
        }
        service.storageService= (AwsStorageService)storageMock.createMock()
        def refInitial=service.createFile(defaultFilePath,new ByteArrayInputStream(text.bytes))
        // Change the company profile
        springMock.demand.getCurrentUser(1..4){->
            wrongUser
        }
        service.securityService= (SpringSecurityService)springMock.createMock()

        shouldFail(InvalidCompanyRequestException) {
            service.deleteById(refInitial.id)
        }
        storageMock.verify()
    }

    void testCreateFail()
    {
        def springMock = mockFor(SpringSecurityService,true)
        springMock.demand.getCurrentUser(1..2){->
            user.encodePassword=false
            user
        }
        service.securityService=(SpringSecurityService)springMock.createMock()
        def storageMock=mockFor(AwsStorageService,true)

        storageMock.demand.upload({
            region,filePath,stream,pair->
                throw new Exception()
        })
        service.storageService=(AwsStorageService)storageMock.createMock()
        shouldFail(Exception) {
            service.createFile(defaultFilePath,new ByteArrayInputStream(text.bytes))
        }

    }
    /**
     *
     */
    void testDownloadFail()
    {
        def springMock = mockFor(SpringSecurityService,true)
        springMock.demand.getCurrentUser(1..2){->
            user
        }
        service.securityService=(SpringSecurityService)springMock.createMock()
        def storageMock=mockFor(AwsStorageService,true)

        storageMock.demand.download({
            region,path,keys->
                throw new Exception()
        })
        service.storageService=(AwsStorageService)storageMock.createMock()

        StorageReference sr=new StorageReference(
                dateCreated: new Date(),
                lastUpdated: new Date(),
                user:user,
                md5:"12345",
                fileReference: defaultFilePath
        )
        sr.save(failOnError: true,flush: true)
        shouldFail(Exception) {
            service.getFileById(sr.id)
        }
        springMock.verify()
        storageMock.verify()
    }

    void testDeleteFail()
    {
        def springMock = mockFor(SpringSecurityService,true)
        springMock.demand.getCurrentUser(1..3){->
            user.encodePassword=false
            user
        }
        service.securityService=(SpringSecurityService)springMock.createMock()
        def storageMock=mockFor(AwsStorageService,true)

        storageMock.demand.upload({
            region,filePath,stream,pair->
                assert region==defaultRegion
                assert filePath==defaultFilePath
                assert stream
                assert pair
                new StorageObjectMetadata(
                        isEncrypted: true,
                        isCompressed: true,
                        md5:"12345"
                )
        })
        storageMock.demand.delete({
            String path-> throw new Exception()
        })
        service.storageService=(AwsStorageService)storageMock.createMock()
        def ref=service.createFile(defaultFilePath,new ByteArrayInputStream(text.bytes))
        assert ref
        assert ref.id>0
        assert ref.fileReference==defaultFilePath
        shouldFail(Exception) {
            service.deleteById(ref.id)
        }
        assertNotNull StorageReference.findById(ref.id)
        storageMock.verify()
        springMock.verify()
    }
    /**
     * The utility method to build a string from a stream
     * @param stream
     * @return
     */
    private static String streamToString(InputStream stream) {
        ByteArrayOutputStream bs=new ByteArrayOutputStream()
        byte[] buff=new byte[8192]
        int count
        while ((count=stream.read(buff))!=-1){
            bs.write(buff,0,count)
        }
        new String(bs.toByteArray(),"UTF-8")
    }

}
