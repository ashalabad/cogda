package com.cogda.api.admin

import com.cogda.BaseController
import com.cogda.domain.admin.LineOfBusiness
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
 * LineOfBusinessController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 * TODO: Add a LineOfBusinessService class that will handle the business logic behind the CRUD actions.
 */
class AdminLineOfBusinessController{

    GsonBuilder gsonBuilder

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {

    }

    /**
     * Passes back a JSON list of LineOfBusinesss
     *
     * @return
     */
    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)

        List lineOfBusinessInstanceList = LineOfBusiness.list()

        def dataToRender = [:]

        dataToRender.aaData = []
        lineOfBusinessInstanceList.each { LineOfBusiness lineOfBusiness ->
            Map map = [:]
            map.DT_RowId = "row_"+lineOfBusiness.id
            map.version = lineOfBusiness.version
            map.intCode = lineOfBusiness.intCode
            map.code = lineOfBusiness.code
            map.description = lineOfBusiness.description
            map.dateCreated = lineOfBusiness.dateCreated
            map.lastUpdated = lineOfBusiness.lastUpdated
            dataToRender.aaData.add(map)
        }
        dataToRender.sEcho = 1

        render dataToRender as JSON

        return
    }

    def jlist(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        response.status = SC_OK
        response.addIntHeader X_PAGINATION_TOTAL, LineOfBusiness.count()
        List lineOfBusinesss = LineOfBusiness.listOrderById(params)
        Gson gson = gsonBuilder.create()
        def jsonTree = gson.toJsonTree(lineOfBusinesss)
        render jsonTree
        return
    }

    def save() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }
        LineOfBusiness lineOfBusinessInstance = new LineOfBusiness(request.GSON)
        if (lineOfBusinessInstance.save(flush: true)) {
            respondCreated lineOfBusinessInstance
        } else {
            respondUnprocessableEntity lineOfBusinessInstance
        }
    }

    def get() {
        def lineOfBusinessInstance = LineOfBusiness.get(params.id)
        if (lineOfBusinessInstance) {
            respondFound lineOfBusinessInstance
        } else {
            respondNotFound params.id
        }
    }

    def update() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def lineOfBusinessInstance = LineOfBusiness.get(params.id)
        if (!lineOfBusinessInstance) {
            respondNotFound params.id
            return
        }

        if (params.version != null) {
            if (lineOfBusinessInstance.version > params.long('version')) {
                respondConflict(lineOfBusinessInstance)
                return
            }
        }

        JsonElement jsonElement = GSON.parse(request)
        lineOfBusinessInstance.properties = jsonElement

        if (lineOfBusinessInstance.save(flush: true)) {
            respondUpdated lineOfBusinessInstance
        } else {
            respondUnprocessableEntity lineOfBusinessInstance
        }
    }

    def delete() {
        def lineOfBusinessInstance = LineOfBusiness.get(params.id)
        if (!lineOfBusinessInstance) {
            respondNotFound params.id
            return
        }

        try {
            lineOfBusinessInstance.delete(flush: true)
            respondDeleted params.id
        } catch (DataIntegrityViolationException e) {
            respondNotDeleted params.id
        }
    }

    private boolean requestIsJson() {
        GSON.isJson(request)
    }

    private void respondFound(LineOfBusiness lineOfBusinessInstance) {
        response.status = SC_OK
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(lineOfBusinessInstance);
        render gsonRetString
    }

    private void respondCreated(LineOfBusiness lineOfBusinessInstance) {
        response.status = SC_CREATED // 201
        response.addHeader LOCATION, createLink(action: 'get', id: lineOfBusinessInstance.id)
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(lineOfBusinessInstance);
        render gsonRetString
    }

    private void respondUpdated(LineOfBusiness lineOfBusinessInstance) {
        response.status = SC_OK // 200
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(lineOfBusinessInstance);
        render gsonRetString
    }

    private void respondUnprocessableEntity(LineOfBusiness lineOfBusinessInstance) {
        def responseBody = [:]
        responseBody.errors = lineOfBusinessInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_UNPROCESSABLE_ENTITY // 422
        render responseBody as GSON
    }

    private void respondNotFound(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.found.message', args: [message(code: 'lineOfBusiness.label', default: 'LineOfBusiness'), id])
        response.status = SC_NOT_FOUND // 404
        render responseBody as GSON
    }

    private void respondConflict(LineOfBusiness lineOfBusinessInstance) {
        lineOfBusinessInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
                [message(code: 'lineOfBusiness.label', default: 'LineOfBusiness')] as Object[],
                'Another user has updated this LineOfBusiness while you were editing')
        def responseBody = [:]
        responseBody.errors = lineOfBusinessInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_CONFLICT // 409
        render responseBody as GSON
    }

    private void respondDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.deleted.message', args: [message(code: 'lineOfBusiness.label', default: 'LineOfBusiness'), id])
        response.status = SC_OK  // 200
        render responseBody as GSON
    }

    private void respondNotDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.deleted.message', args: [message(code: 'lineOfBusiness.label', default: 'LineOfBusiness'), id])
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

