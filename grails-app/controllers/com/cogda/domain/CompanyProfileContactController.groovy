package com.cogda.domain

import com.cogda.GsonBaseController
import com.cogda.domain.CompanyProfileContact
import grails.plugin.gson.converters.GSON
import org.springframework.dao.DataIntegrityViolationException

import static grails.plugin.gson.http.HttpConstants.*
/**
 * CompanyProfileContactController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class CompanyProfileContactController extends GsonBaseController {

    final static String INSTANCE_LABEL = "companyProfileContact.label"

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        response.addIntHeader X_PAGINATION_TOTAL, CompanyProfileContact.count()

        render CompanyProfileContact.list(params) as GSON
    }

    def save() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        Long companyProfileId = request.GSON.companyProfile.id.getAsLong()
        Long userProfileId =    request.GSON.userProfile.id.getAsLong()
        CompanyProfile companyProfileInstance = CompanyProfile.get(companyProfileId)
        UserProfile userProfileInstance = UserProfile.get(userProfileId)
        CompanyProfileContact companyProfileContactInstance = new CompanyProfileContact(companyProfile: companyProfileInstance, userProfile: userProfileInstance)
        if (companyProfileContactInstance.save(flush: true)) {
            respondCreated companyProfileContactInstance
        } else {
            respondUnprocessableEntity companyProfileContactInstance
        }
    }

    def get() {
        CompanyProfileContact companyProfileContactInstance = CompanyProfileContact.get(params.id)
        if (companyProfileContactInstance) {
            respondFound companyProfileContactInstance
        } else {
            respondNotFound INSTANCE_LABEL
        }
    }

    def update() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def companyProfileContactInstance = CompanyProfileContact.get(params.id)
        if (!companyProfileContactInstance) {
            respondNotFound INSTANCE_LABEL
            return
        }

        if (params.version != null) {
            if (companyProfileContactInstance.version > params.long('version')) {
                respondConflict(companyProfileContactInstance)
                return
            }
        }

        companyProfileContactInstance.properties = request.GSON

        if (companyProfileContactInstance.save(flush: true)) {
            respondUpdated companyProfileContactInstance
        } else {
            respondUnprocessableEntity companyProfileContactInstance
        }
    }

    def delete() {
        def companyProfileContactInstance = CompanyProfileContact.get(params.id)
        if (!companyProfileContactInstance) {
            respondNotFound INSTANCE_LABEL
            return
        }

        try {
            companyProfileContactInstance.delete(flush: true)
            respondDeleted(INSTANCE_LABEL)
        } catch (DataIntegrityViolationException e) {
            respondNotDeleted INSTANCE_LABEL
        }
    }

}

