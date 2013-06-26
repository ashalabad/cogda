package com.cogda.api.admin

import com.cogda.BaseController
import com.cogda.domain.admin.BusinessType
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import grails.converters.JSON
import grails.plugin.gson.converters.GSON
import org.springframework.dao.DataIntegrityViolationException
import static javax.servlet.http.HttpServletResponse.*
import static org.codehaus.groovy.grails.web.servlet.HttpHeaders.*
import static grails.plugin.gson.http.HttpConstants.*

/**
 * BusinessTypeController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 * TODO: Add a BusinessTypeService class that will handle the business logic behind the CRUD actions.
 */
class AdminBusinessTypeController{

    GsonBuilder gsonBuilder

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    /**
     * Passes back a JSON list of BusinessTypes
     *
     * @return
     */
    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)

        List businessTypeInstanceList = BusinessType.list()

        def dataToRender = [:]

        dataToRender.aaData = []
        businessTypeInstanceList.each { BusinessType businessType ->
            Map map = [:]
            map.DT_RowId = "row_"+businessType.id
            map.version = businessType.version
            map.intCode = businessType.intCode
            map.code = businessType.code
            map.description = businessType.description
            map.dateCreated = businessType.dateCreated
            map.lastUpdated = businessType.lastUpdated
            dataToRender.aaData.add(map)
        }
        dataToRender.sEcho = 1

        render dataToRender as JSON

        return
    }

    def jlist(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        response.status = SC_OK
        response.addIntHeader X_PAGINATION_TOTAL, BusinessType.count()
        List businessTypes = BusinessType.listOrderById(params)
        Gson gson = gsonBuilder.create()
        def jsonTree = gson.toJsonTree(businessTypes)
        render jsonTree
        return
    }

    def save() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }
        BusinessType businessTypeInstance = new BusinessType(request.GSON)
        if (businessTypeInstance.save(flush: true)) {
            respondCreated businessTypeInstance
        } else {
            respondUnprocessableEntity businessTypeInstance
        }
    }

    def get() {
        def businessTypeInstance = BusinessType.get(params.id)
        if (businessTypeInstance) {
            respondFound businessTypeInstance
        } else {
            respondNotFound params.id
        }
    }

    def update() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def businessTypeInstance = BusinessType.get(params.id)
        if (!businessTypeInstance) {
            respondNotFound params.id
            return
        }

        if (params.version != null) {
            if (businessTypeInstance.version > params.long('version')) {
                respondConflict(businessTypeInstance)
                return
            }
        }

        JsonElement jsonElement = GSON.parse(request)
        businessTypeInstance.properties = jsonElement

        if (businessTypeInstance.save(flush: true)) {
            respondUpdated businessTypeInstance
        } else {
            respondUnprocessableEntity businessTypeInstance
        }
    }

    def delete() {
        def businessTypeInstance = BusinessType.get(params.id)
        if (!businessTypeInstance) {
            respondNotFound params.id
            return
        }

        try {
            businessTypeInstance.delete(flush: true)
            respondDeleted params.id
        } catch (DataIntegrityViolationException e) {
            respondNotDeleted params.id
        }
    }

    private boolean requestIsJson() {
        GSON.isJson(request)
    }

    private void respondFound(BusinessType businessTypeInstance) {
        response.status = SC_OK
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(businessTypeInstance);
        render gsonRetString
    }

    private void respondCreated(BusinessType businessTypeInstance) {
        response.status = SC_CREATED // 201
        response.addHeader LOCATION, createLink(action: 'get', id: businessTypeInstance.id)
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(businessTypeInstance);
        render gsonRetString
    }

    private void respondUpdated(BusinessType businessTypeInstance) {
        response.status = SC_OK // 200
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(businessTypeInstance);
        render gsonRetString
    }

    private void respondUnprocessableEntity(BusinessType businessTypeInstance) {
        def responseBody = [:]
        responseBody.errors = businessTypeInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_UNPROCESSABLE_ENTITY // 422
        render responseBody as GSON
    }

    private void respondNotFound(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.found.message', args: [message(code: 'businessType.label', default: 'BusinessType'), id])
        response.status = SC_NOT_FOUND // 404
        render responseBody as GSON
    }

    private void respondConflict(BusinessType businessTypeInstance) {
        businessTypeInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
                [message(code: 'businessType.label', default: 'BusinessType')] as Object[],
                'Another user has updated this BusinessType while you were editing')
        def responseBody = [:]
        responseBody.errors = businessTypeInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_CONFLICT // 409
        render responseBody as GSON
    }

    private void respondDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.deleted.message', args: [message(code: 'businessType.label', default: 'BusinessType'), id])
        response.status = SC_OK  // 200
        render responseBody as GSON
    }

    private void respondNotDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.deleted.message', args: [message(code: 'businessType.label', default: 'BusinessType'), id])
        response.status = SC_INTERNAL_SERVER_ERROR  // 500
        render responseBody as GSON
    }

    private void respondNotAcceptable() {
        response.status = SC_NOT_ACCEPTABLE  // 406
        response.contentLength = 0
        response.outputStream.flush()
        response.outputStream.close()
    }

}


