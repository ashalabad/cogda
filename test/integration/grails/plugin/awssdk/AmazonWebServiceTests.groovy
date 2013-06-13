package grails.plugin.awssdk

import com.amazonaws.services.s3.model.AmazonS3Exception
import com.amazonaws.services.s3.model.Bucket
import com.amazonaws.services.s3.model.GetObjectRequest
import com.amazonaws.services.s3.model.ObjectListing
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.model.PutObjectResult
import com.amazonaws.services.s3.model.S3ObjectSummary
import org.apache.commons.logging.LogFactory
import org.codehaus.groovy.grails.commons.GrailsApplication

import static org.junit.Assert.*
import org.junit.*

class AmazonWebServiceTests {

    GrailsApplication grailsApplication

    private static final log = LogFactory.getLog(this)



    AmazonWebService amazonWebService
    private final static List AVAILABLE_BUCKET_NAMES = ["cogda-test", "cogda-production", "cogda-staging", "cogda-development"]
    private final static String FILE_PATH = "test/integration/"
    private final static String FILE_NAME = "testPutObject.txt"
    private final static String FILE_PATH_NAME = FILE_PATH + FILE_NAME
    private final static String FILE_NAME_TEMP = "tempObject.txt"

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testS3ListBuckets() {
        // TESTING LIST BUCKETS
        List buckets = amazonWebService.s3.listBuckets()
        buckets.each { Bucket bucket ->
            log.debug "bucketName: ${bucket.name}, creationDate: ${bucket.creationDate}, bucketOwner: ${bucket.owner}"
            assert bucket.name.contains("cogda")
        }
    }

    @Test
    void testS3ListObjects(){
        ObjectListing objectListing = amazonWebService.s3.listObjects(getEnvironmentSpecificBucket())
        objectListing.objectSummaries.each { S3ObjectSummary summary ->
            log.debug "key: ${summary.key}, lastModified: ${summary.lastModified}, size: ${summary.size}"
        }


    }

    @Test
    void testS3StoreFile(){
        File file = new File(FILE_NAME)
        file.write("SOMETHING IN HERE")
        Long fileSize = file.size()

        // Put the file on Amazon
        PutObjectResult putObjectResult = amazonWebService.s3.putObject(new PutObjectRequest(getEnvironmentSpecificBucket(), FILE_PATH_NAME, file))
        assert putObjectResult
        file.delete()

        // Download the file from Amazon
        File fileNameTemp = new File(FILE_NAME_TEMP)
        amazonWebService.s3.getObject(new GetObjectRequest(getEnvironmentSpecificBucket(), FILE_PATH_NAME), fileNameTemp)
        assert fileNameTemp.size() == fileSize
        fileNameTemp.delete()

        // Delete the file from Amazon
        amazonWebService.s3.deleteObject(getEnvironmentSpecificBucket(), FILE_PATH_NAME)

        Boolean objectNotFound = Boolean.FALSE
        try {
            amazonWebService.s3.getObject(new GetObjectRequest(getEnvironmentSpecificBucket(), FILE_PATH_NAME), fileNameTemp)
        }catch(AmazonS3Exception e){
            log.debug e.message
            objectNotFound = Boolean.TRUE
        }

        assert objectNotFound

    }

    private String getEnvironmentSpecificBucket(){
        return grailsApplication.config.grails.plugin.awssdk.default.bucket
    }
}
