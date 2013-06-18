package grails.plugin.awssdk

import com.amazonaws.AmazonClientException
import com.amazonaws.AmazonServiceException
import com.amazonaws.services.s3.model.AmazonS3Exception
import com.amazonaws.services.s3.model.Bucket
import com.amazonaws.services.s3.model.GetObjectRequest
import com.amazonaws.services.s3.model.ObjectListing
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.model.PutObjectResult
import com.amazonaws.services.s3.model.S3ObjectSummary
import com.cogda.multitenant.CustomerAccountService
import org.apache.commons.logging.LogFactory
import org.codehaus.groovy.grails.commons.GrailsApplication

import static org.junit.Assert.*
import org.junit.*

class AmazonWebServiceTests {

    GrailsApplication grailsApplication

    private static final log = LogFactory.getLog(this)

    AmazonWebService amazonWebService
    CustomerAccountService customerAccountService

    private final static List AVAILABLE_BUCKET_NAMES = ["cogda-test", "cogda-production", "cogda-staging", "cogda-development"]
    private final static String FILE_PATH = "test/integration/"
    private final static String FILE_NAME = "testPutObject.txt"
    private final static String FILE_PATH_NAME = FILE_PATH + FILE_NAME
    private final static String FILE_NAME_TEMP = "tempObject.txt"

    @Before
    void setUp() {
        // Setup logic here
        final String defaultBucket = grailsApplication.config.grails.plugin.awssdk.default.bucket
        final String pathPrefix = "customerAccounts/"
        ObjectListing objectListing = amazonWebService.s3.listObjects(defaultBucket, pathPrefix)
        List objectSummaries = objectListing.objectSummaries
        objectSummaries.each { S3ObjectSummary objectSummary ->
            try {
                amazonWebService.s3.deleteObject(defaultBucket, objectSummary.key)
            }catch(AmazonClientException ace){
                log.error ("Unable to clean the integration test's AmazonWebServices folder structure ${objectSummary.key}")
                log.error ("AmazonClientException ${ace.message}")
            }catch(AmazonServiceException ase){
                log.error ("Unable to clean the integration test's AmazonWebServices folder structure ${objectSummary.key}")
                log.error ("AmazonServiceException ${ase.message}")
            }
        }
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testProvisionCustomerAccountAmazonFileSystem(){
        final String defaultBucket = grailsApplication.config.grails.plugin.awssdk.default.bucket
        final String pathPrefix = "customerAccounts/"
        final String companiesFolder = "companies"
        final String clientsFolder = "clients"
        final String imagesFolder = "images"
        final String tempFolder = "temp"
        final String usersFolder = "users"
        String customerAccountUuid =  UUID.randomUUID().toString().replaceAll('-', '')
        String companyAccountId =  UUID.randomUUID().toString().replaceAll('-', '')
        String userAccountId = UUID.randomUUID().toString().replaceAll('-', '')

        // the provisionCustomerAccountAmazonFileSystem should create something like the following:
//        customerAccounts/bd75c3df9bd143748e8a5fd8d16333d2/a.txt
//        customerAccounts/bd75c3df9bd143748e8a5fd8d16333d2/companies/44b2748cb67b4cdaa9709673d4598085/a.txt
//        customerAccounts/bd75c3df9bd143748e8a5fd8d16333d2/companies/44b2748cb67b4cdaa9709673d4598085/documents/a.txt
//        customerAccounts/bd75c3df9bd143748e8a5fd8d16333d2/companies/a.txt
//        customerAccounts/bd75c3df9bd143748e8a5fd8d16333d2/documents/a.txt
//        customerAccounts/bd75c3df9bd143748e8a5fd8d16333d2/images/a.txt
//        customerAccounts/bd75c3df9bd143748e8a5fd8d16333d2/temp/a.txt
//        customerAccounts/bd75c3df9bd143748e8a5fd8d16333d2/users/1afe48188f7e48a58cca7eb926595606/a.txt
//        customerAccounts/bd75c3df9bd143748e8a5fd8d16333d2/users/1afe48188f7e48a58cca7eb926595606/images/a.txt
//        customerAccounts/bd75c3df9bd143748e8a5fd8d16333d2/users/a.txt


        customerAccountService.provisionCustomerAccountAmazonFileSystem(customerAccountUuid, companyAccountId, userAccountId)
        // delete everything under customerAccounts/
        ObjectListing objectListing = amazonWebService.s3.listObjects(defaultBucket, pathPrefix)
        List objectSummaries = objectListing.objectSummaries

        objectSummaries.each { S3ObjectSummary os ->
            log.debug os.getKey()
            println os.getKey()
        }

        assert objectSummaries.size() == 11
        assert amazonWebService.s3.listObjects(defaultBucket, "customerAccounts/${customerAccountUuid}/companies").objectSummaries.size() == 3
        assert amazonWebService.s3.listObjects(defaultBucket, "customerAccounts/${customerAccountUuid}/images").objectSummaries.size() == 1
        assert amazonWebService.s3.listObjects(defaultBucket, "customerAccounts/${customerAccountUuid}/temp").objectSummaries.size() == 1
        assert amazonWebService.s3.listObjects(defaultBucket, "customerAccounts/${customerAccountUuid}/users").objectSummaries.size() == 4


        objectSummaries.each { S3ObjectSummary objectSummary ->
            try {
                amazonWebService.s3.deleteObject(defaultBucket, objectSummary.key)
            }catch(AmazonClientException ace){
                log.error ("Unable to clean the integration test's AmazonWebServices folder structure ${objectSummary.key}")
                log.error ("AmazonClientException ${ace.message}")
            }catch(AmazonServiceException ase){
                log.error ("Unable to clean the integration test's AmazonWebServices folder structure ${objectSummary.key}")
                log.error ("AmazonServiceException ${ase.message}")
            }
        }

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
            log.debug e.getStatusCode()
            log.debug e.message
            objectNotFound = Boolean.TRUE
        }

        assert objectNotFound

    }

    private String getEnvironmentSpecificBucket(){
        return grailsApplication.config.grails.plugin.awssdk.default.bucket
    }
}
