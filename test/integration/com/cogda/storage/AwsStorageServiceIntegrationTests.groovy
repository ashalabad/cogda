package com.cogda.storage

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.AmazonS3Exception
import com.amazonaws.services.s3.model.ObjectListing
import grails.plugin.awssdk.AmazonWebService
import grails.plugins.springsecurity.SpringSecurityService
import grails.test.mixin.TestFor
import org.junit.After
import org.junit.Before

import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.SecureRandom

/**
 * Created with IntelliJ IDEA.
 * User: igor_pol
 * Date: 7/31/13
 * Time: 4:40 PM
 * To change this template use File | Settings | File Templates.
 */
@TestFor(AwsStorageService)
class AwsStorageServiceIntegrationTests {

    private def testFileName='aws-integration-tests/000001/000001/test.txt'
    private def text="A quick brown fox jumped over a lazy dog back. And ran away as fast as it could. what a smart fox"
    private KeyPair pair

    @Before
    void setUp()
    {
        defineBeans {
            springSecurityService(SpringSecurityService) { bean->bean.autowire = true}
            amazonWebService(AmazonWebService) { bean->bean.autowire=true }
        }
        grailsApplication.config.grails.plugin.awssdk.accessKey = "AKIAJIZ7I3U3MD3UB7YA"
        grailsApplication.config.grails.plugin.awssdk.secretKey = "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"
        grailsApplication.config.grails.plugin.awssdk.default.bucket="cogda-test"
        //
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(1024, new SecureRandom());
        pair = keyGenerator.generateKeyPair();

    }
    @After
    void tearDown()
    {
        getS3().deleteObject(getDefaultBasket(),testFileName)
    }

    private String getDefaultBasket(){grailsApplication.config.grails.plugin.awssdk.default.bucket="cogda-test"}
    private AmazonS3Client getS3(){service.amazonWebService.getS3()}

    void testUploadSmallZippedData() {

        ByteArrayInputStream ba=new ByteArrayInputStream(text.getBytes("UTF-8"))
        def result=service.upload('',testFileName,ba, pair)
        assert result
        assert result.isCompressed==true
        assert result.isEncrypted==true
        def metadata = getS3().getObjectMetadata(getDefaultBasket(), testFileName)
        assert  metadata.contentType=="application/x-compressed"
        assert  metadata.contentLength!=text.getBytes("UTF-8").length
    }

    void testUploadWithoutEncryption()
    {
        ByteArrayInputStream ba=new ByteArrayInputStream(text.getBytes("UTF-8"))
        def result=service.upload('',testFileName,ba)
        assert result.isCompressed==true
        assert result.isEncrypted==false
        def metadata = getS3().getObjectMetadata(getDefaultBasket(), testFileName)
        assert  metadata.contentType=="application/x-compressed"
        assert  metadata.contentLength!=text.getBytes("UTF-8").length
    }
    void testDownloadWithoutEncryption()
    {
        ByteArrayInputStream ba=new ByteArrayInputStream(text.getBytes("UTF-8"))
        service.upload('',testFileName,ba)
        def metadata = getS3().getObjectMetadata(getDefaultBasket(), testFileName)
        assert  metadata.contentType=="application/x-compressed"
        assert  metadata.contentLength!=text.getBytes("UTF-8").length
        def stream = service.download('',testFileName)
        def s=streamToString(stream)
        assert text==s

    }

    void testDownloadSmallZippedData()
    {
        ByteArrayInputStream ba=new ByteArrayInputStream(text.getBytes("UTF-8"))
        service.upload('',testFileName,ba, pair)
        def stream = service.download('',testFileName,pair)
        def s=streamToString(stream)
        assert text==s
    }

    void testUploadLargeZippedStream()
    {
        String data=buildLargeString(2048)
        ByteArrayInputStream ba=new ByteArrayInputStream(data.getBytes("UTF-8"))
        service.upload('',testFileName,ba, pair)
        def metadata = getS3().getObjectMetadata(getDefaultBasket(), testFileName)
        assert  metadata.contentType=="application/x-compressed"
        assert metadata.contentLength<data.getBytes("UTF-8").length

    }

    void testDownloadLargeZippedStream()
    {
        String data=buildLargeString(2048)
        ByteArrayInputStream ba=new ByteArrayInputStream(data.getBytes("UTF-8"))
        service.upload('',testFileName,ba, pair)
        def stream = service.download('',testFileName,pair)
        def s=streamToString(stream)
        assert data==s
    }

    void testUploadDataNoZipping()
    {
        byte[] bytes=text.getBytes("UTF-8")
        ByteArrayInputStream ba=new ByteArrayInputStream(bytes)
        service.upload('',testFileName,false,ba, pair)
        def metadata = getS3().getObjectMetadata(getDefaultBasket(), testFileName)
        //
        // Because of the block cipher, the content length can be greater or equal of the original length
        //
        assert  metadata.contentType=="application/octet-stream"
        assert metadata.contentLength>bytes.length

    }

    void testDownloadDataNoZipping()
    {
        ByteArrayInputStream ba=new ByteArrayInputStream(text.getBytes("UTF-8"))
        service.upload('',testFileName,false,ba, pair)
        def stream = service.download('',testFileName,pair)
        def s=streamToString(stream)
        assert text==s
    }

    void testDeleteFile()
    {
        ByteArrayInputStream ba=new ByteArrayInputStream(text.getBytes("UTF-8"))
        service.upload('',testFileName,false,ba, pair)
        ObjectListing objects = getS3().listObjects(getDefaultBasket(),testFileName)
        assert objects.objectSummaries?.size()==1
        assert objects.objectSummaries.get(0).key==testFileName
        service.delete(testFileName)
        objects = getS3().listObjects(getDefaultBasket(),testFileName)
        assert objects.objectSummaries?.size()==0
    }

    void testGetInformation()
    {
        ByteArrayInputStream ba=new ByteArrayInputStream(text.getBytes("UTF-8"))
        service.upload('',testFileName,ba, pair)
        def metadata=getS3().getObjectMetadata(getDefaultBasket(),testFileName)
        def se=service.getInfo(testFileName)
        assert metadata.contentLength==se.length
        assert metadata.contentMD5==se.md5
    }

    void testReplaceUploadedFile() {
        def replace= "Completely new text. Nothing about dogs and foxes"
        service.upload('',testFileName,false,new ByteArrayInputStream(text.getBytes("UTF-8")), pair)
        service.upload('',testFileName,false,new ByteArrayInputStream(replace.getBytes("UTF-8")), pair)
        def stream = service.download('',testFileName,pair)
        assertEquals replace,streamToString(stream)

    }

    void testUploadBasketDoesNotExist()
    {
        grailsApplication.config.grails.plugin.awssdk.default.bucket=UUID.randomUUID().toString().replace('-','')
        ByteArrayInputStream ba=new ByteArrayInputStream(text.getBytes("UTF-8"))
        def message = shouldFail(AmazonS3Exception) {
            service.upload('',testFileName,ba, pair)
        }
        assertEquals "The specified bucket does not exist",message
    }

    void testDeleteBasketDoesNotExist()
    {
        ByteArrayInputStream ba=new ByteArrayInputStream(text.getBytes("UTF-8"))
        service.upload('',testFileName,ba, pair)
        grailsApplication.config.grails.plugin.awssdk.default.bucket=UUID.randomUUID().toString().replace('-','')
        def message = shouldFail(AmazonS3Exception) {
            service.delete(testFileName)
        }
        assertEquals"The specified bucket does not exist",message

    }

    void testDownloadBasketDoesNotExist()
    {
        ByteArrayInputStream ba=new ByteArrayInputStream(text.getBytes("UTF-8"))
        service.upload('',testFileName,ba, pair)
        grailsApplication.config.grails.plugin.awssdk.default.bucket=UUID.randomUUID().toString().replace('-','')
        def message=shouldFail(AmazonS3Exception) {
            service.download('',testFileName,pair)
        }
        assertEquals "The specified bucket does not exist",message
    }

    void testDownloadFileDoesNotExist()
    {
        def message=shouldFail(AmazonS3Exception) {
            service.download('',UUID.randomUUID().toString().replace('-',''),pair)
        }
        assertEquals "The specified key does not exist.",message
    }

    void testGetInformationBasketDoesNotExist()
    {
        grailsApplication.config.grails.plugin.awssdk.default.bucket=UUID.randomUUID().toString().replace('-','')
        def message=shouldFail(AmazonS3Exception) {
            service.getInfo(testFileName)
        }
        assertEquals "Not Found",message
    }

    void testGetInformationFileDoesNotExist()
    {
        def message=shouldFail(AmazonS3Exception) {
            service.getInfo(UUID.randomUUID().toString().replace('-',''))
        }
        assertEquals "Not Found",message
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

    private String buildLargeString(int count)
    {
        StringBuffer sb=new StringBuffer()
        for(int i in 0 .. count)
            sb.append(text)
        sb.toString()
    }

}
