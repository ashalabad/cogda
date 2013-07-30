package com.cogda
import com.google.gson.Gson
import grails.plugin.gson.converters.GSON
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import grails.plugin.gson.converters.GSON

import org.springframework.dao.DataIntegrityViolationException
import static javax.servlet.http.HttpServletResponse.*
import static org.codehaus.groovy.grails.web.servlet.HttpHeaders.*
import static grails.plugin.gson.http.HttpConstants.*

/**
 * GsonBaseController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class GsonBaseController {
    GsonBuilder gsonBuilder

    private String getClassNameLabel(instance){
        return instance.class.name + ".label"
    }

    private boolean requestIsJson() {
        GSON.isJson(request)
    }

    private void respondFound(instance) {
        response.status = SC_OK  // 200
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(instance);
        render gsonRetString
        return
    }

    private void respondCreated(instance) {
        response.status = SC_CREATED // 201
        response.addHeader LOCATION, createLink(action: 'get', id: instance.id)
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(instance);
        render gsonRetString
        return
    }

    private void respondUpdated(instance) {
        response.status = SC_OK // 200
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(instance);
        render gsonRetString
        return
    }

    private Map getErrorStringsByField(instance){
        Map stringsByField = [:].withDefault { [] }
        for(fieldErrors in instance.errors){
            for(error in fieldErrors.allErrors){
                String message = message(error:error)
                stringsByField[error.field] << message
            }
        }
        return stringsByField
    }

    private void respondUnprocessableEntity(instance) {
        def responseBody = [:]
        responseBody.errors = getErrorStringsByField(instance)
        response.status = SC_UNPROCESSABLE_ENTITY // 422
        render responseBody as GSON
        return
    }

    private void respondNotFound(String entityLabel) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.found.message', args: [message(code:entityLabel)])
        response.status = SC_NOT_FOUND // 404
        render responseBody as GSON
        return
    }

    private void respondConflict(instance) {
        instance.errors.rejectValue('version', 'default.optimistic.locking.failure',
                'Another user has updated this item while you were editing')
        def responseBody = [:]
        responseBody.errors = getErrorStringsByField(instance)
        response.status = SC_CONFLICT // 409
        render responseBody as GSON
        return
    }

    private void respondDeleted(String entityLabel) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.deleted.message', args: [message(code:  entityLabel)])
        response.status = SC_OK  // 200
        render responseBody as GSON
        return
    }

    private void respondNotDeleted(String entityLabel) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.deleted.message', args: [message(code:  entityLabel)])
        response.status = SC_INTERNAL_SERVER_ERROR  // 500
        render responseBody as GSON
    }

    private void respondNotAcceptable() {
        response.status = SC_NOT_ACCEPTABLE  // 406
        response.contentLength = 0
        response.outputStream.flush()
        response.outputStream.close()
        return
    }
}
