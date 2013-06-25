package com.cogda.api.admin

import com.cogda.BaseController
import com.cogda.domain.admin.LineOfBusinessCategory
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
 * LineOfBusinessCategoryController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 * TODO: Add a LineOfBusinessCategoryService class that will handle the business logic behind the CRUD actions.
 */
class AdminLineOfBusinessCategoryController{

    GsonBuilder gsonBuilder

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {

    }

    /**
     * Passes back a JSON list of LineOfBusinessCategorys
     *
     * @return
     */
    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)

        List lineOfBusinessCategoryInstanceList = LineOfBusinessCategory.list()

        def dataToRender = [:]

        dataToRender.aaData = []
        lineOfBusinessCategoryInstanceList.each { LineOfBusinessCategory lineOfBusinessCategory ->
            Map map = [:]
            map.DT_RowId = "row_"+lineOfBusinessCategory.id
            map.version = lineOfBusinessCategory.version
            map.intCode = lineOfBusinessCategory.intCode
            map.code = lineOfBusinessCategory.code
            map.description = lineOfBusinessCategory.description
            map.dateCreated = lineOfBusinessCategory.dateCreated
            map.lastUpdated = lineOfBusinessCategory.lastUpdated
            dataToRender.aaData.add(map)
        }
        dataToRender.sEcho = 1

        render dataToRender as JSON

        return
    }

    def jlist(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        response.status = SC_OK
        response.addIntHeader X_PAGINATION_TOTAL, LineOfBusinessCategory.count()
        List lineOfBusinessCategorys = LineOfBusinessCategory.listOrderById(params)
        Gson gson = gsonBuilder.create()
        def jsonTree = gson.toJsonTree(lineOfBusinessCategorys)
        render jsonTree
        return
    }

    def save() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }
        LineOfBusinessCategory lineOfBusinessCategoryInstance = new LineOfBusinessCategory(request.GSON)
        if (lineOfBusinessCategoryInstance.save(flush: true)) {
            respondCreated lineOfBusinessCategoryInstance
        } else {
            respondUnprocessableEntity lineOfBusinessCategoryInstance
        }
    }

    def get() {
        def lineOfBusinessCategoryInstance = LineOfBusinessCategory.get(params.id)
        if (lineOfBusinessCategoryInstance) {
            respondFound lineOfBusinessCategoryInstance
        } else {
            respondNotFound params.id
        }
    }

    def update() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def lineOfBusinessCategoryInstance = LineOfBusinessCategory.get(params.id)
        if (!lineOfBusinessCategoryInstance) {
            respondNotFound params.id
            return
        }

        if (params.version != null) {
            if (lineOfBusinessCategoryInstance.version > params.long('version')) {
                respondConflict(lineOfBusinessCategoryInstance)
                return
            }
        }

        JsonElement jsonElement = GSON.parse(request)
        lineOfBusinessCategoryInstance.properties = jsonElement

        if (lineOfBusinessCategoryInstance.save(flush: true)) {
            respondUpdated lineOfBusinessCategoryInstance
        } else {
            respondUnprocessableEntity lineOfBusinessCategoryInstance
        }
    }

    def delete() {
        def lineOfBusinessCategoryInstance = LineOfBusinessCategory.get(params.id)
        if (!lineOfBusinessCategoryInstance) {
            respondNotFound params.id
            return
        }

        try {
            lineOfBusinessCategoryInstance.delete(flush: true)
            respondDeleted params.id
        } catch (DataIntegrityViolationException e) {
            respondNotDeleted params.id
        }
    }

    private boolean requestIsJson() {
        GSON.isJson(request)
    }

    private void respondFound(LineOfBusinessCategory lineOfBusinessCategoryInstance) {
        response.status = SC_OK
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(lineOfBusinessCategoryInstance);
        render gsonRetString
    }

    private void respondCreated(LineOfBusinessCategory lineOfBusinessCategoryInstance) {
        response.status = SC_CREATED // 201
        response.addHeader LOCATION, createLink(action: 'get', id: lineOfBusinessCategoryInstance.id)
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(lineOfBusinessCategoryInstance);
        render gsonRetString
    }

    private void respondUpdated(LineOfBusinessCategory lineOfBusinessCategoryInstance) {
        response.status = SC_OK // 200
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(lineOfBusinessCategoryInstance);
        render gsonRetString
    }

    private void respondUnprocessableEntity(LineOfBusinessCategory lineOfBusinessCategoryInstance) {
        def responseBody = [:]
        responseBody.errors = lineOfBusinessCategoryInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_UNPROCESSABLE_ENTITY // 422
        render responseBody as GSON
    }

    private void respondNotFound(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.found.message', args: [message(code: 'lineOfBusinessCategory.label', default: 'LineOfBusinessCategory'), id])
        response.status = SC_NOT_FOUND // 404
        render responseBody as GSON
    }

    private void respondConflict(LineOfBusinessCategory lineOfBusinessCategoryInstance) {
        lineOfBusinessCategoryInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
                [message(code: 'lineOfBusinessCategory.label', default: 'LineOfBusinessCategory')] as Object[],
                'Another user has updated this LineOfBusinessCategory while you were editing')
        def responseBody = [:]
        responseBody.errors = lineOfBusinessCategoryInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_CONFLICT // 409
        render responseBody as GSON
    }

    private void respondDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.deleted.message', args: [message(code: 'lineOfBusinessCategory.label', default: 'LineOfBusinessCategory'), id])
        response.status = SC_OK  // 200
        render responseBody as GSON
    }

    private void respondNotDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.deleted.message', args: [message(code: 'lineOfBusinessCategory.label', default: 'LineOfBusinessCategory'), id])
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

