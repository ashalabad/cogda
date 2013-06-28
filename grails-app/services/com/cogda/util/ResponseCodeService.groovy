package com.cogda.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import grails.plugin.gson.converters.GSON
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.web.mapping.LinkGenerator
import org.codehaus.groovy.grails.web.util.WebUtils
import org.springframework.context.MessageSource

import static javax.servlet.http.HttpServletResponse.*
import static org.codehaus.groovy.grails.web.servlet.HttpHeaders.*
import static grails.plugin.gson.http.HttpConstants.*

/**
 * ResponseCodeService
 * A service class encapsulates the core business logic of a Grails application
 */
class ResponseCodeService {

    static transactional = false
    LinkGenerator linkGenerator
    GsonBuilder gsonBuilder
    MessageSource messageSource
    GrailsApplication grailsApplication

    def webUtils = WebUtils.retrieveGrailsWebRequest()

    def boolean requestIsJson() {
        GSON.isJson(webUtils.getCurrentRequest())
    }

    def respondFound(object) {
        webUtils.getCurrentResponse().status = SC_OK
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(object);
        return gsonRetString
    }

    def respondCreated(object) {
        webUtils.getCurrentResponse().status = SC_CREATED // 201
        webUtils.getCurrentResponse().addHeader LOCATION, linkGenerator.link(controller: getClazzName(object).toLowerCase(), action: 'get', id: object.id)
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(object);
        return gsonRetString
    }

    def respondUpdated(object) {
        webUtils.getCurrentResponse().status = SC_OK // 200
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(object);
        return gsonRetString
    }

    def respondUnprocessableEntity(object) {
        def responseBody = [:]
        responseBody.errors = object.errors.allErrors.collect {
            messageSource.getMessage(it,null)
        }
        webUtils.getCurrentResponse().status = SC_UNPROCESSABLE_ENTITY // 422
        return responseBody as GSON
    }

    def respondNotFound(id,String className) {
        def responseBody = [:]
        responseBody.message = messageSource.getMessage("default.not.found.message",[getDefaultClassMessage(className), id])
        webUtils.getCurrentResponse().status = SC_NOT_FOUND // 404
        return responseBody as GSON
    }

    def respondConflict(object) {
        object.errors.rejectValue('version', 'default.optimistic.locking.failure',[getClazzName(object)] as Object[],'Another user has updated this ' + getClazzName(object) + ' while you were editing')
        def responseBody = [:]
        responseBody.errors = object.errors.allErrors.collect {
            messageSource.getMessage(it,null)
        }
        webUtils.getCurrentResponse().status = SC_CONFLICT // 409
        return responseBody as GSON
    }

    def respondDeleted(id,String className) {
        def responseBody = [:]
        responseBody.message = messageSource.getMessage("default.deleted.message",[getDefaultClassMessage(className), id])
        webUtils.getCurrentResponse().status = SC_OK  // 200
        return responseBody as GSON
    }

    def respondNotDeleted(id, String className) {
        def responseBody = [:]
        responseBody.message = messageSource.getMessage("default.not.deleted.message",[getDefaultClassMessage(className), id])
        webUtils.getCurrentResponse().status = SC_INTERNAL_SERVER_ERROR  // 500
        return responseBody as GSON
    }

    def respondNotAcceptable() {
        webUtils.getCurrentResponse().status = SC_NOT_ACCEPTABLE  // 406
        webUtils.getCurrentResponse().contentLength = 0
        webUtils.getCurrentResponse().outputStream.flush()
        webUtils.getCurrentResponse().outputStream.close()
    }

    private static String getClazzName(object){
        object.class.simpleName
    }

    // i.e. message(code: 'account.label',args:[], default: 'Account')
    private String getDefaultClassMessage(String className){
        messageSource.getMessage(className.toLowerCase() + ".label",null,className)
    }

}
