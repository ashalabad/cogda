package com.cogda.domain

import com.amazonaws.services.cloudfront.model.InvalidArgumentException
import com.cogda.domain.security.User
import com.cogda.domain.storage.StorageReference
import com.cogda.multitenant.Document
import com.cogda.multitenant.DocumentVersion
import grails.plugins.springsecurity.SpringSecurityService

/**
 * A Document associates with four data streams
 */
enum DocumentDataStreamType {
    /**
     * The file data stream. Each document must have one of this stream types
     */
    File,
    /**
     * Generated preview data stream (optional)
     */
    Preview,
    /**
     * Associated Icon data stream ( optional)
     */
    Icon,
    /**
     * Annotations data stream (optional)
     */
    Annotations,
    /**
     * Other
     */
    Other
}
/**
 * A simple Stream Version container
 */
class StreamsVersion {
    private def Integer version
    private Map<DocumentDataStreamType,StorageReference> streams

    StreamsVersion(Integer version, Map<DocumentDataStreamType, StorageReference> streams) {
        this.version = version
        this.streams = streams
    }

    Integer getVersion() {
        return version
    }

    Map<DocumentDataStreamType, StorageReference> getStreams() {
        return streams
    }
}
/**
 * DocumentService
 * A service class encapsulates the core business logic of a Grails application
 */
class DocumentService {

    static transactional = true
    SpringSecurityService springSecurityService

    def createDocument(
            String name,
            String description,
            String category,
            Date originationDate=null,
            Map<DocumentDataStreamType,StorageReference> streams) {
        if(!streams.containsKey(DocumentDataStreamType.File)) {
            throw new IllegalArgumentException("Streams don't contain a File stream.")
        }
        def user=springSecurityService.currentUser as User
        Document document=new Document(
                currentVersion: 0,
                name: name,
                description: description,
                category: category,
                originationDate: originationDate,
                createdBy:user,
                lastUpdatedBy: user

        )
        document.save()
        streams.each {
           DocumentVersion version=new DocumentVersion(
                   documentVersion: 0,
                   streamType: it.key,
                   document:document,
                   createdBy: user,
                   storageReference: it.value
           )
            version.save()
        }
        document
    }
    /**
     * Get the document streams of the latest document version
     * @return Map of Document Streams
     */
    Map<DocumentDataStreamType,StorageReference> getDocumentStreams(Long documentId) {
        Document document=Document.findById(documentId)
        if(document==null)
            throw new IllegalArgumentException("a document with id=${documentId} does not exist.")
        def versions = DocumentVersion.findAllByDocumentAndDocumentVersion(document,document.currentVersion)
        versions.collectEntries {
           [(it.streamType):it.storageReference]
        } as Map<DocumentDataStreamType, StorageReference>
    }

    /**
     * Get all streams of the documents, all versions
     * @param documentId
     * @return
     */
    List<StreamsVersion> getDocumentStreamsAllVersions(Long documentId){
        List<StreamsVersion> sv=[]
        Document document=Document.findById(documentId)
        if(document==null)
            throw new IllegalArgumentException("a document with id=${documentId} does not exist.")
        def versions = DocumentVersion.findAllByDocument(document)
        for (int i=0; i<=document.currentVersion; i++) {
             def map=versions.findAll{it.documentVersion==i}.
                     collectEntries{[(it.streamType):it.storageReference]} as Map<DocumentDataStreamType, StorageReference>

            sv.add(new StreamsVersion(i,map))
        }
        sv
    }
    /**
     * Creates new version of the document with added or replaced streams
     * @param documentId
     * @param streams
     * @return
     */
    def updateDocumentStreams(Long documentId,Map<DocumentDataStreamType,StorageReference> streams) {
        Document document=Document.findById(documentId)
        if(document==null)
            throw new IllegalArgumentException("a document with id=${documentId} does not exist.")
        def user=springSecurityService.currentUser as User
        def nextVersion=document.currentVersion+1
        def versions = DocumentVersion.findAllByDocumentAndDocumentVersion(document,document.currentVersion)
        Closure createVersion={DocumentDataStreamType streamType,StorageReference storageRef, User who->
            DocumentVersion version=new DocumentVersion(
                    document: document,
                    streamType: streamType,
                    storageReference: storageRef,
                    documentVersion: nextVersion,
                    createdBy: who
            )
            version.save(failOnError: true)
        }
        // copy previous unchanged streams
       versions.findAll{!streams.containsKey(it.streamType)}.each {
           createVersion.call(it.streamType,it.storageReference,it.createdBy)
        }
        streams.each{
            createVersion.call(it.key,it.value,user)
        }
        document.currentVersion=nextVersion
        document.lastUpdatedBy=user
        document.save(failOnError: true)
        nextVersion
    }
    /**
     * Delete one of the document streams
     * @param documentId
     * @param streams
     * @return
     */
    def deleteDocumentStreams(Long documentId,List<DocumentDataStreamType> streams) {
        Document document=Document.findById(documentId)
        if(document==null)
            throw new IllegalArgumentException("A document with id=${documentId} does not exist.")
        if(streams.contains(DocumentDataStreamType.File))
            throw new InvalidArgumentException("Deletion of the File stream is prohibited.")
        def user=springSecurityService.currentUser as User
        def nextVersion=document.currentVersion+1
        def versions = DocumentVersion.findAllByDocumentAndDocumentVersion(document,document.currentVersion)
        if(versions.findAll{streams.contains(it.streamType)}.size()==0) {
            throw new IllegalArgumentException("passed streams do not exist.")
        }
        for (version in versions) {
            if (streams.contains(version.streamType))
                continue
            new DocumentVersion(
                    document: document,
                    streamType: version.streamType,
                    documentVersion: nextVersion,
                    createdBy: version.createdBy,
                    storageReference: version.storageReference
            ).save(failOnError: true)
        }
        document.currentVersion=nextVersion
        document.lastUpdatedBy=user
        document.save(failOnError: true)
        nextVersion
    }
    /**
     * Gets all version records for a document
     * @param documentId
     * @return
     */
    def getDocumentVersions(Long documentId){
        Document document=Document.findById(documentId)
        if(document==null)
            throw new IllegalArgumentException("A document with id=${documentId} does not exist.")
        DocumentVersion.findAllByDocument(document)

    }
    /**
     * Update a document properties
     * @param documentId
     * @param docUpdate
     */
    def updateDocument(Long documentId,Closure docUpdate) {
        Document document=Document.findById(documentId)
        if(document==null)
            throw new IllegalArgumentException("A document with id=${documentId} does not exist.")
        Document clone=new Document(
                name: document.name,
                description: document.description,
                category: document.category,
                originationDate: document.originationDate
        )
        docUpdate.call(clone)
        document.name=clone.name
        document.description=clone.description
        document.category=clone.category
        document.originationDate=clone.originationDate
        document.save(failOnError: true)
    }
    /**
     * Delete a document
     */
    def deleteDocument(Long documentId)
    {
        Document document=Document.findById(documentId)
        if(document==null)
            throw new IllegalArgumentException("A document with id=${documentId} does not exist.")
        def versions=DocumentVersion.findAllByDocument(document)
        versions.each {
            it.delete()
        }
        document.delete()
    }
}
