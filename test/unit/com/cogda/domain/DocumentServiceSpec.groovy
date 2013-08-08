package com.cogda.domain

import com.amazonaws.services.cloudfront.model.InvalidArgumentException
import com.cogda.domain.security.User
import com.cogda.domain.storage.StorageReference
import com.cogda.multitenant.Document
import com.cogda.multitenant.DocumentVersion
import grails.plugins.springsecurity.SpringSecurityService
import grails.test.mixin.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(DocumentService)
@Mock([Document,DocumentVersion,StorageReference,User])
class DocumentServiceSpec {

    User user
    void setUp() {
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
    }

    void tearDown() {
         user.delete()
    }

    void testCreateDocument() {
        // create a document
        StorageReference mainFile=new StorageReference(
                dateCreated: new Date(),
                lastUpdated: new Date(),
                user:user,
                md5:"12345",
                fileReference: "file.doc"
        )
        mainFile.save(failOnError: true,flush: true)
        StorageReference annotationFile=new StorageReference(
                dateCreated: new Date(),
                lastUpdated: new Date(),
                user:user,
                md5:"12345",
                fileReference: "annotation"
        )
        annotationFile.save(failOnError: true,flush: true)
        def map=[
                (DocumentDataStreamType.File):mainFile,
                (DocumentDataStreamType.Annotations):annotationFile
        ]
        def springMock = mockFor(SpringSecurityService,true)
        springMock.demand.getCurrentUser(1){->user}
        service.springSecurityService=springMock.createMock()
        Document document = service.createDocument("test","test","test",map)

        assertNotNull   document
        assertNotNull   document.id
        assertEquals    user,document.createdBy
        assertEquals    user,document.lastUpdatedBy
        assertEquals    "test",document.name
        assertEquals    "test",document.description
        assertEquals    "test",document.category
        assertEquals     0,document.currentVersion

        // load created version objects
        def versions = DocumentVersion.findAll()
        assertEquals 2,versions.size()
        versions.each {
            assertEquals 0,it.version
        }

        def file=versions.find { it.streamType==DocumentDataStreamType.File } as DocumentVersion
        assertNotNull file
        assertEquals "file.doc",file.storageReference.fileReference
        def annotation=versions.find {it.streamType==DocumentDataStreamType.Annotations} as DocumentVersion
        assertNotNull annotation
        assertEquals "annotation",annotation.storageReference.fileReference

    }

    void testGetDocumentStreamsAfterCreate() {
        StorageReference mainFile=new StorageReference(
                dateCreated: new Date(),
                lastUpdated: new Date(),
                user:user,
                md5:"12345",
                fileReference: "file.doc"
        )
        mainFile.save(failOnError: true,flush: true)
        StorageReference annotationFile=new StorageReference(
                dateCreated: new Date(),
                lastUpdated: new Date(),
                user:user,
                md5:"12345",
                fileReference: "annotation"
        )
        annotationFile.save(failOnError: true,flush: true)
        def map=[
                (DocumentDataStreamType.File):mainFile,
                (DocumentDataStreamType.Annotations):annotationFile
        ]
        def springMock = mockFor(SpringSecurityService,true)
        springMock.demand.getCurrentUser(1..2){->user}
        service.springSecurityService=springMock.createMock()
        Document document = service.createDocument("test","test","test",map)
        assertNotNull   document
        assertNotNull   document.id

        Map<DocumentDataStreamType,StorageReference>  streams = service.getDocumentStreams(document.id)
        assertNotNull   streams
        assertEquals    2,streams.size()
        StorageReference sr=streams.get(DocumentDataStreamType.File)
        assertNotNull   sr
        assertEquals    mainFile,sr
        sr=streams.get(DocumentDataStreamType.Annotations)
        assertNotNull   sr
        assertEquals    annotationFile,sr

    }

    void testAddStreamToDocument() {

        StorageReference mainFile=new StorageReference(
                dateCreated: new Date(),
                lastUpdated: new Date(),
                user:user,
                md5:"12345",
                fileReference: "file.doc"
        )
        mainFile.save(failOnError: true,flush: true)
        StorageReference annotationFile=new StorageReference(
                dateCreated: new Date(),
                lastUpdated: new Date(),
                user:user,
                md5:"12345",
                fileReference: "annotation"
        )
        annotationFile.save(failOnError: true,flush: true)
        def map=[
                (DocumentDataStreamType.File):mainFile,
                (DocumentDataStreamType.Annotations):annotationFile
        ]
        def springMock = mockFor(SpringSecurityService,true)
        springMock.demand.getCurrentUser(1..2){->user}
        service.springSecurityService=springMock.createMock()

        Document document = service.createDocument("test","test","test",map)
        assertNotNull   document
        assertNotNull   document.id

        StorageReference replacedAnnotation=new StorageReference(
                dateCreated: new Date(),
                lastUpdated: new Date(),
                user:user,
                md5:"12345",
                fileReference: "new_annotation"
        )
        replacedAnnotation.save()
        StorageReference previewFile=new StorageReference(
                dateCreated: new Date(),
                lastUpdated: new Date(),
                user:user,
                md5:"12345",
                fileReference: "preview"
        )
        previewFile.save()
        map.clear()
        map.put(DocumentDataStreamType.Annotations,replacedAnnotation)
        map.put(DocumentDataStreamType.Preview,previewFile)
        def version=service.updateDocumentStreams(document.id,map)
        assertEquals 1,version
        def versions = DocumentVersion.findAll()
        assertEquals 5,versions.size()
        def v0=versions.findAll{it.documentVersion==0}
        assertEquals 2,v0.size()
        def v1=versions.findAll{it.documentVersion==1}
        assertEquals 3,v1.size()
        assertEquals 1, v0.findAll{it.streamType==DocumentDataStreamType.File}.size()
        assertEquals 1, v0.findAll{it.streamType==DocumentDataStreamType.Annotations}.size()
        assertEquals 0, v0.findAll{it.streamType==DocumentDataStreamType.Preview}.size()

        assertEquals 1, v1.findAll{it.streamType==DocumentDataStreamType.File}.size()
        assertEquals 1, v1.findAll{it.streamType==DocumentDataStreamType.Annotations}.size()
        assertEquals 1, v1.findAll{it.streamType==DocumentDataStreamType.Preview}.size()

        assertEquals v0.find{it.streamType==DocumentDataStreamType.File}.storageReference.fileReference,
                v1.find{it.streamType==DocumentDataStreamType.File}.storageReference.fileReference
        assert  v0.find{it.streamType==DocumentDataStreamType.Annotations}.storageReference.fileReference !=
                v1.find{it.streamType==DocumentDataStreamType.Annotations}.storageReference.fileReference
        assertEquals replacedAnnotation.fileReference,
                v1.find{it.streamType==DocumentDataStreamType.Annotations}.storageReference.fileReference
        assertEquals previewFile.fileReference,
                v1.find{it.streamType==DocumentDataStreamType.Preview}.storageReference.fileReference
    }

    void testRemoveStreamFromDocument()
    {
        StorageReference mainFile=new StorageReference(
                dateCreated: new Date(),
                lastUpdated: new Date(),
                user:user,
                md5:"12345",
                fileReference: "file.doc"
        )
        mainFile.save(failOnError: true,flush: true)
        StorageReference annotationFile=new StorageReference(
                dateCreated: new Date(),
                lastUpdated: new Date(),
                user:user,
                md5:"12345",
                fileReference: "annotation"
        )
        annotationFile.save(failOnError: true,flush: true)
        def map=[
                (DocumentDataStreamType.File):mainFile,
                (DocumentDataStreamType.Annotations):annotationFile
        ]
        def springMock = mockFor(SpringSecurityService,true)
        springMock.demand.getCurrentUser(1..2){->user}
        service.springSecurityService=springMock.createMock()

        Document document = service.createDocument("test","test","test",map)
        assertNotNull   document
        assertNotNull   document.id
        def version=service.deleteDocumentStreams(document.id,[DocumentDataStreamType.Annotations])
        assertEquals    1,version
        def versions = DocumentVersion.findAll()
        assertEquals 3,versions.size()
        def v0=versions.findAll{it.documentVersion==0}
        def v1=versions.findAll{it.documentVersion==1}
        assertEquals 2,v0.size()
        assertEquals 1,v1.size()
        assertEquals 0, v1.findAll{it.streamType==DocumentDataStreamType.Annotations}.size()
        assertEquals 1, v1.findAll{it.streamType==DocumentDataStreamType.File}.size()
    }

    void testDeleteFileStream()
    {
        StorageReference mainFile=new StorageReference(
                dateCreated: new Date(),
                lastUpdated: new Date(),
                user:user,
                md5:"12345",
                fileReference: "file.doc"
        )
        mainFile.save(failOnError: true,flush: true)
        StorageReference annotationFile=new StorageReference(
                dateCreated: new Date(),
                lastUpdated: new Date(),
                user:user,
                md5:"12345",
                fileReference: "annotation"
        )
        annotationFile.save(failOnError: true,flush: true)
        def map=[
                (DocumentDataStreamType.File):mainFile,
                (DocumentDataStreamType.Annotations):annotationFile
        ]
        def springMock = mockFor(SpringSecurityService,true)
        springMock.demand.getCurrentUser(1..10){->user}
        service.springSecurityService=springMock.createMock()

        Document document = service.createDocument("test","test","test",map)
        assertNotNull   document
        assertNotNull   document.id
        shouldFail(InvalidArgumentException){
            service.deleteDocumentStreams(document.id,[DocumentDataStreamType.File])
        }
        def versions = DocumentVersion.findAll()
        assertEquals 2,versions.size()
        assertEquals 2,versions.findAll{it.documentVersion==0}.size()
        assertEquals 0,versions.findAll{it.documentVersion>0}.size()
        shouldFail(InvalidArgumentException){
            service.deleteDocumentStreams(document.id,[DocumentDataStreamType.Annotations, DocumentDataStreamType.File])
        }
        versions = DocumentVersion.findAll()
        assertEquals 2,versions.size()
        assertEquals 2,versions.findAll{it.documentVersion==0}.size()
        assertEquals 0,versions.findAll{it.documentVersion>0}.size()
    }

    void testDeleteStreamNotExist()
    {
        StorageReference mainFile=new StorageReference(
                dateCreated: new Date(),
                lastUpdated: new Date(),
                user:user,
                md5:"12345",
                fileReference: "file.doc"
        )
        mainFile.save(failOnError: true,flush: true)
        def map=[
                (DocumentDataStreamType.File):mainFile,
        ]
        def springMock = mockFor(SpringSecurityService,true)
        springMock.demand.getCurrentUser(1..10){->user}
        service.springSecurityService=springMock.createMock()
        Document document = service.createDocument("test","test","test",map)
        assertNotNull   document
        assertNotNull   document.id
        shouldFail(IllegalArgumentException){
            service.deleteDocumentStreams(document.id,[DocumentDataStreamType.Annotations])
        }
    }

    void testGetAllVersionsStreams()
    {
        StorageReference mainFile=new StorageReference(dateCreated: new Date(),lastUpdated: new Date(),user:user,
                md5:"12345",fileReference: "file.doc")
        mainFile.save(failOnError: true,flush: true)
        StorageReference annotationFile=new StorageReference(dateCreated: new Date(),lastUpdated: new Date(),user:user,
                md5:"12345",fileReference: "annotation")
        annotationFile.save(failOnError: true,flush: true)
        def map=[
                (DocumentDataStreamType.File):mainFile,
                (DocumentDataStreamType.Annotations):annotationFile
        ]
        def springMock = mockFor(SpringSecurityService,true)
        springMock.demand.getCurrentUser(1..2){->user}
        service.springSecurityService=springMock.createMock()

        Document document = service.createDocument("test","test","test",map)
        assertNotNull   document
        assertNotNull   document.id

        StorageReference replacedAnnotation=new StorageReference(dateCreated: new Date(),lastUpdated: new Date(),
                user:user,md5:"12345",fileReference: "new_annotation")
        replacedAnnotation.save()
        StorageReference previewFile=new StorageReference(dateCreated: new Date(),lastUpdated: new Date(),user:user,
                md5:"12345",fileReference: "preview")
        previewFile.save()
        map.clear()
        map.put(DocumentDataStreamType.Annotations,replacedAnnotation)
        map.put(DocumentDataStreamType.Preview,previewFile)
        def version=service.updateDocumentStreams(document.id,map)
        assertEquals 1,version
        def versions=service.getDocumentStreamsAllVersions(document.id)
        assertNotNull(versions)
        assertEquals 2,versions.size()
        assertEquals 0, versions.head().version
        assertEquals 1, versions.tail().head().version
        assertEquals 2,versions.head().streams.size()
        assertEquals 3,versions.tail().head().streams.size()
        assertNull  versions.head().streams.get(DocumentDataStreamType.Preview)
        assertNotNull versions.tail().head().streams.get(DocumentDataStreamType.Preview)
    }

    void testGetAllDocumentVersions()
    {
        def springMock = mockFor(SpringSecurityService,true)
        springMock.demand.getCurrentUser(1..2){->user}
        service.springSecurityService=springMock.createMock()

        StorageReference mainFile=new StorageReference(dateCreated: new Date(),lastUpdated: new Date(),user:user,
                md5:"12345",fileReference: "file.doc")
        mainFile.save(failOnError: true,flush: true)
        StorageReference annotationFile=new StorageReference(dateCreated: new Date(),lastUpdated: new Date(),user:user,
                md5:"12345",fileReference: "annotation")
        annotationFile.save(failOnError: true,flush: true)
        def map=[
                (DocumentDataStreamType.File):mainFile,
                (DocumentDataStreamType.Annotations):annotationFile
        ]
        Document document = service.createDocument("test","test","test",map)
        assertNotNull   document
        assertNotNull   document.id

        StorageReference replacedAnnotation=new StorageReference(dateCreated: new Date(),lastUpdated: new Date(),
                user:user, md5:"12345",fileReference: "new_annotation")
        replacedAnnotation.save()
        StorageReference previewFile=new StorageReference(
                dateCreated: new Date(),
                lastUpdated: new Date(),
                user:user,
                md5:"12345",
                fileReference: "preview"
        )
        previewFile.save()
        map.clear()
        map.put(DocumentDataStreamType.Annotations,replacedAnnotation)
        map.put(DocumentDataStreamType.Preview,previewFile)
        def version=service.updateDocumentStreams(document.id,map)
        assertEquals 1,version
        def versions = service.getDocumentVersions(document.id)
        assertNotNull versions
        assertEquals 5,versions.size()
    }

    void testUpdateDocumentProperties()
    {
        def springMock = mockFor(SpringSecurityService,true)
        springMock.demand.getCurrentUser(1..2){->user}
        service.springSecurityService=springMock.createMock()

        StorageReference mainFile=new StorageReference(dateCreated: new Date(),lastUpdated: new Date(),user:user,
                md5:"12345",fileReference: "file.doc")
        mainFile.save(failOnError: true,flush: true)
        StorageReference annotationFile=new StorageReference(dateCreated: new Date(),lastUpdated: new Date(),user:user,
                md5:"12345",fileReference: "annotation")
        annotationFile.save(failOnError: true,flush: true)
        def map=[
                (DocumentDataStreamType.File):mainFile,
                (DocumentDataStreamType.Annotations):annotationFile
        ]
        Document document = service.createDocument("test","test","test",map)
        assertNotNull   document
        assertNotNull   document.id
        Date now=new Date()
        service.updateDocument(document.id,{Document doc->
            doc.name="new name"
            doc.category="new category"
            doc.description="new description"
            doc.originationDate=now
        })
        def updated=Document.findById(document.id)
        assertEquals document.id,updated.id
        assertEquals "new name",updated.name
        assertEquals "new category",updated.category
        assertEquals "new description",updated.description
        assertEquals now,updated.originationDate
    }

    void testDeleteDocument()
    {
        def springMock = mockFor(SpringSecurityService,true)
        springMock.demand.getCurrentUser(1..2){->user}
        service.springSecurityService=springMock.createMock()

        StorageReference mainFile=new StorageReference(dateCreated: new Date(),lastUpdated: new Date(),user:user,
                md5:"12345",fileReference: "file.doc")
        mainFile.save(failOnError: true,flush: true)
        StorageReference annotationFile=new StorageReference(dateCreated: new Date(),lastUpdated: new Date(),user:user,
                md5:"12345",fileReference: "annotation")
        annotationFile.save(failOnError: true,flush: true)
        def map=[
                (DocumentDataStreamType.File):mainFile,
                (DocumentDataStreamType.Annotations):annotationFile
        ]
        Document document = service.createDocument("test","test","test",map)
        assertNotNull   document
        assertNotNull   document.id
        service.deleteDocument(document.id)
        assertEquals 0, Document.findAll().size()
        assertEquals 0, DocumentVersion.findAllByDocument(document).size()
    }



}
