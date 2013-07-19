package com.cogda.domain

import com.cogda.GsonBaseController
import grails.plugin.gson.converters.GSON
import grails.plugins.springsecurity.Secured
import org.springframework.dao.DataIntegrityViolationException
import static grails.plugin.gson.http.HttpConstants.*

/**
 * CompanyProfileController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Secured(['IS_AUTHENTICATED_FULLY'])
class CompanyProfileController extends GsonBaseController{

    final static String COMPANY_PROFILE_LABEL = "companyProfile.label"

    def index(){

    }

    def showPartial(){
        render (view:'showPartial')
    }

    def editPartial(){
        render (view:'editPartial')
    }

    def addAddressPartial(){
        render (view:'addAddressPartial')
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        response.addIntHeader X_PAGINATION_TOTAL, CompanyProfile.count()
        render CompanyProfile.list(params) as GSON
    }

    def save() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def companyProfileInstance = new CompanyProfile(request.GSON)
        if (companyProfileInstance.save(flush: true)) {
            respondCreated companyProfileInstance
        } else {
            respondUnprocessableEntity companyProfileInstance
        }
    }

    def get() {
        CompanyProfile companyProfileInstance = CompanyProfile.rootCompanyProfile
        if (companyProfileInstance) {
            respondFound companyProfileInstance
        } else {
            respondNotFound COMPANY_PROFILE_LABEL
        }
    }

    def update() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def companyProfileInstance = CompanyProfile.get(params.id)
        if (!companyProfileInstance) {
            respondNotFound COMPANY_PROFILE_LABEL
            return
        }

        if (params.version != null) {
            if (companyProfileInstance.version > params.long('version')) {
                respondConflict(companyProfileInstance)
                return
            }
        }

        def jsonObject = request.GSON
        companyProfileInstance.companyWebsite = jsonObject.companyWebsite?.getAsString()
        companyProfileInstance.companyDescription = jsonObject.companyDescription?.getAsString()
        companyProfileInstance.businessSpecialties = jsonObject.businessSpecialties?.getAsString()
        companyProfileInstance.associations = jsonObject.associations?.getAsString()
        companyProfileInstance.published = jsonObject.published?.getAsBoolean()

        if (companyProfileInstance.save(flush: true)) {
            respondUpdated companyProfileInstance
        } else {
            respondUnprocessableEntity companyProfileInstance
        }
    }

    def delete() {
        def companyProfileInstance = CompanyProfile.get(params.id)
        if (!companyProfileInstance) {
            respondNotFound COMPANY_PROFILE_LABEL
            return
        }

        try {
            companyProfileInstance.delete(flush: true)
            respondDeleted()
        } catch (DataIntegrityViolationException e) {
            respondNotDeleted COMPANY_PROFILE_LABEL
        }
    }


}
