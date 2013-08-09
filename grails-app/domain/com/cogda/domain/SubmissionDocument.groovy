package com.cogda.domain

/**
 * SubmissionDocument
 * A domain class describes the data object and it's mapping to the database
 */
class SubmissionDocument {

    /* Default (injected) attributes of GORM */
	Long	id
	Long	version

    /**
     * Original file name
     */
    String fileName

    /**
     * fileSize of the document
     */
    Long fileSize

    /**
     * The last time this document was modified
     */
    Date lastModified

    /**
     * The content type of the document for downloading and viewing purposes
     */
    String contentType

    /**
     * The amazon key for this document
     */
    String awsKey

    /* Automatic timestamping of GORM */
	Date	dateCreated
	Date	lastUpdated

	static belongsTo	= [submission:Submission]

    static constraints = {
        fileName(nullable:false, blank:false)
        fileSize(nullable:false, blank:false)
        lastModified(nullable:true)
        contentType(nullable:false, blank:false)
        awsKey(nullable:true)
    }
}
