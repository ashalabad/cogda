package com.cogda.util

/**
 * ErrorMessageResolverService
 * A service class encapsulates the core business logic of a Grails application
 */
class ErrorMessageResolverService {

    static transactional = true

    def messageSource

    /**
     *
     * @param validateableClass
     * @return
     */
    def Map<String, String> retrieveErrorStrings(validateableClass){
        def stringsByField = [:]
        if (validateableClass.hasErrors()){
            Locale locale = Locale.getDefault()

            for (fieldErrors in validateableClass.errors) {
                for (error in fieldErrors.allErrors) {
                    def resolved = stringsByField[error.field]
                    if (!resolved) {
                        resolved = []
                        stringsByField[error.field] = resolved
                    }
                    resolved << messageSource.getMessage(error, locale)
                }
            }
        }
        return stringsByField
    }
}
