package com.cogda.storage

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.AmazonS3EncryptionClient
import com.amazonaws.services.s3.model.EncryptionMaterials
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.model.PutObjectResult
import com.amazonaws.services.s3.model.S3Object
import grails.plugin.awssdk.AmazonWebService
import org.apache.commons.logging.LogFactory
import org.codehaus.groovy.grails.commons.GrailsApplication

import java.security.KeyPair
import java.util.zip.ZipInputStream

class StorageObjectMetadata {

    boolean isCompressed
    boolean isEncrypted
    long length
    String contentType
    String md5
}
/**
 * AwsStorageService
 * This service implementation uses Amazon S3 storage
 * A service class encapsulates the core business logic of a Grails application
 */
class AwsStorageService {

    private final static String contentCompressed="application/x-compressed"
    private final static String userContentType="content-type"

    private static final log=LogFactory.getLog(this)
    static transactional = false

    GrailsApplication grailsApplication
    AmazonWebService amazonWebService
    /**
     * Upload a stream to AWS S3 service
     * @param region AWS region
     * @param path   S3 key
     * @param zip    Zip the input stream if true. The default value is True
     * @param stream The Stream to uploadStream
     * @param keys   PKI Key pair
     * @return       basket+path
     */
    StorageObjectMetadata upload(String region, String path, boolean zip=true, InputStream stream, KeyPair keys) {

        EncryptionMaterials materials=new EncryptionMaterials(keys);
        AmazonS3EncryptionClient s3 = amazonWebService.getS3encryptionClient(region,materials)
        PutObjectResult result = uploadStream(path,zip,stream,s3)
        new StorageObjectMetadata(
            isCompressed: zip,
            isEncrypted: true,
            md5: result.contentMd5
        )
    }

    /**
     * Upload without client-side encryption
     * @param region
     * @param path
     * @param zip
     * @param stream
     * @return
     */
    StorageObjectMetadata upload(String region, String path, boolean zip=true, InputStream stream) {

        AmazonS3Client s3 = amazonWebService.getS3(region)
        PutObjectResult result = uploadStream(path,zip,stream,s3)
        new StorageObjectMetadata(
                isCompressed: zip,
                isEncrypted: false,
                md5: result.contentMd5
        )
    }
    private PutObjectResult uploadStream(String path, boolean zip, InputStream stream, AmazonS3Client s3) {

        ObjectMetadata metadata=new ObjectMetadata()
        InputStream input=stream;
        if(zip) {
            input=new ZippingPipe(stream);
            metadata.contentType=contentCompressed
        }
        else {
            metadata.contentType="application/octet-stream"
        }
        PutObjectRequest request=new PutObjectRequest(basket,path,input,metadata)
        s3.putObject(request)
    }

    /**
     * Download a stream from AWS S3
     * @param region  AWS region
     * @param path    element key
     * @param keys    PKI Key Pair
     * @return        Data Stream
     */
    InputStream download(String region, String path, KeyPair keys) {
        EncryptionMaterials materials=new EncryptionMaterials(keys);
        AmazonS3EncryptionClient s3 = amazonWebService.getS3encryptionClient(region,materials)
        return downloadStream(path,s3)
    }
    /**
     * Download without a client-side encryption
     * @param region
     * @param path
     * @return
     */
    InputStream download(String region, String path) {
        return downloadStream(path,amazonWebService.getS3(region))
    }

    private InputStream downloadStream(String path, AmazonS3Client s3) {
        S3Object s3obj = s3.getObject(basket, path)
        if(s3obj.objectMetadata.contentType==contentCompressed) {
            ZipInputStream zis=new ZipInputStream(s3obj.objectContent)
            zis.getNextEntry()
            zis
        }
        else s3obj.objectContent
    }

    /**
     * Delete a file
     * @param path  A file key in the basket
     */
    def delete(String path) {
        amazonWebService.s3.deleteObject(basket,path)
    }
    /**
     * Retrieve the ObjectMetadata
     * @param region
     * @param path
     */
    StorageObjectMetadata getInfo(String region='',String path)
    {
        ObjectMetadata meta = amazonWebService.getS3(region).getObjectMetadata(basket,path)
        StorageObjectMetadata som=new StorageObjectMetadata()
        som.length=meta.contentLength
        som.md5=meta.contentMD5
        som.isCompressed=meta.contentType==contentCompressed
        som.contentType=som.isCompressed?meta.userMetadata[userContentType]:meta.contentType
        som
    }

    protected String getBasket() {
        grailsApplication.config.grails.plugin.awssdk.default.bucket
    }
}
