package com.cogda.storage

import com.amazonaws.services.s3.AmazonS3EncryptionClient
import com.amazonaws.services.s3.model.EncryptionMaterials
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.model.S3Object
import grails.plugin.awssdk.AmazonWebService
import org.apache.commons.io.FilenameUtils
import org.apache.commons.logging.LogFactory
import org.codehaus.groovy.grails.commons.GrailsApplication

import java.nio.file.Path
import java.security.KeyPair
import java.util.zip.ZipInputStream

class StorageObjectMetadata {

    boolean isCompressed
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
     * @param stream The Stream to upload
     * @param keys   PKI Key pair
     * @return       basket+path
     */
    String upload(String region, String path, boolean zip=true, InputStream stream, KeyPair keys) {

        EncryptionMaterials materials=new EncryptionMaterials(keys);
        ObjectMetadata metadata=new ObjectMetadata()
        AmazonS3EncryptionClient s3 = amazonWebService.getS3encryptionClient(region,materials)
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
        "${basket}/$path"
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
        amazonWebService.getS3().deleteObject(basket,path)
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
