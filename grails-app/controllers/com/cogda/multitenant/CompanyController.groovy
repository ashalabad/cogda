package com.cogda.multitenant
import com.cogda.GsonBaseController
import grails.plugin.gson.converters.GSON
import org.springframework.dao.DataIntegrityViolationException
import static grails.plugin.gson.http.HttpConstants.*
/**
 * CompanyController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class CompanyController extends GsonBaseController {

    final static String COMPANY_LABEL = "company.label"

    def index(){

    }

    def listPartial(){
        render (view:'listPartial')
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        response.addIntHeader X_PAGINATION_TOTAL, Company.count()
        render Company.list(params) as GSON
    }

    def save() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def companyInstance = new Company(request.GSON)
        if (companyInstance.save(flush: true)) {
            respondCreated companyInstance
        } else {
            respondUnprocessableEntity companyInstance
        }
    }

    def get() {
        def companyInstance = Company.get(params.id)
        if (companyInstance) {
            respondFound companyInstance
        } else {
            respondNotFound COMPANY_LABEL
        }
    }

    def update() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def companyInstance = Company.get(params.id)
        if (!companyInstance) {
            respondNotFound COMPANY_LABEL
            return
        }

        if (params.version != null) {
            if (companyInstance.version > params.long('version')) {
                respondConflict(companyInstance)
                return
            }
        }

        companyInstance.properties = request.GSON

        if (companyInstance.save(flush: true)) {
            respondUpdated companyInstance
        } else {
            respondUnprocessableEntity companyInstance
        }
    }

    def delete() {
        def companyInstance = Company.get(params.id)
        if (!companyInstance) {
            respondNotFound COMPANY_LABEL
            return
        }

        try {
            companyInstance.delete(flush: true)
            respondDeleted()
        } catch (DataIntegrityViolationException e) {
            respondNotDeleted COMPANY_LABEL
        }
    }
}
