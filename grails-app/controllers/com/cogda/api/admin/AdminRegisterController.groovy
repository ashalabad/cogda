package com.cogda.api.admin

import com.cogda.domain.admin.AdminService
import com.cogda.domain.admin.CompanyType
import com.cogda.domain.onboarding.Registration
import com.cogda.util.ErrorMessageResolverService
import grails.converters.XML
import grails.plugin.gson.converters.GSON
import grails.plugins.springsecurity.Secured

import static grails.plugin.gson.http.HttpConstants.SC_UNPROCESSABLE_ENTITY
import static grails.plugin.gson.http.HttpConstants.X_PAGINATION_TOTAL
import static javax.servlet.http.HttpServletResponse.*
import static org.codehaus.groovy.grails.web.servlet.HttpHeaders.LOCATION

/**
 * AdminRegisterController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class AdminRegisterController {

    static allowedMethods = [list: 'GET',
            show: 'GET',
            edit: ['GET', 'POST'],
            save: 'POST',
            update: ['POST', 'PUT'],
            approve: ['POST', 'PUT'],
            updateSubdomain: ['POST'],
            companyTypes: ['GET']
    ]

    AdminService adminService
    ErrorMessageResolverService errorMessageResolverService

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        response.addIntHeader X_PAGINATION_TOTAL, adminService.registrationCount()
        def registrations = adminService.listRegistrations(params)
        withFormat {
            json { render registrations as GSON }
            xml { render registrations as XML }
        }
    }

    def create() {
        Registration registration = new Registration()
        withFormat {
            json { render registration as GSON }
            xml { render registration as XML }
        }
    }

    def show(long id) {
        def registration = adminService.findRegistrationById(id)
        if (!registration) {
            respondNotFound(id)
            return
        }
        withFormat {
            json { render registration as GSON }
            xml { render registration as XML }
        }
    }

    def update(long id) {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }
        Registration registrationInstance = Registration.get(id)

        if (!registrationInstance) {
            respondNotFound(id)
            return
        }

        if (params.version != null) {
            if (registrationInstance.version > params.long('version')) {
                respondConflict(registrationInstance)
                return
            }
        }

        registrationInstance.properties = request.GSON

        if (adminService.saveRegistration(registrationInstance)) {
            respondUpdated(registrationInstance)
        } else {
            registrationInstance.discard()
            respondUnprocessableEntity registrationInstance
        }
    }

    def save() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def registrationInstance = new Registration(request.GSON)
        if (adminService.saveRegistration(registrationInstance)) {
            respondCreated registrationInstance
        } else {
            respondUnprocessableEntity registrationInstance
        }
    }

    def approve(long id) {
        Registration registrationInstance = Registration.get(id)
        if (!registrationInstance) {
            respondNotFound(id)
            return
        }
        adminService.approveRegistration(registrationInstance)
        if (registrationInstance.hasErrors() || registrationInstance.errors.errorCount > 0) {
            respondRegistrationApprovalConflict(registrationInstance)
        } else {
            respondUpdated(registrationInstance)
        }
    }

    def companyTypes() {
        def companyTypes = CompanyType.list()
        if (!companyTypes) {
            withFormat {
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
        }
        withFormat {
            json { render companyTypes as GSON }
            xml { render companyTypes as XML }
        }
    }

    private void respondNotFound(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.found.message', args: [message(code: 'registration.label', default: 'Registration'), id])
        response.status = SC_NOT_FOUND
        withFormat {
            json { render responseBody as GSON }
            xml { render responseBody as XML }
        }
    }

    private boolean requestIsJson() {
        GSON.isJson(request)
    }

    private void respondNotAcceptable() {
        response.status = SC_NOT_ACCEPTABLE
        response.contentLength = 0
        response.outputStream.flush()
        response.outputStream.close()
    }

    private void respondConflict(Registration registrationInstance) {
        registrationInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
                [message(code: 'registration.label', default: 'Album')] as Object[],
                'Another user has updated this Registration while you were editing')
        def responseBody = [:]
        responseBody.errors = registrationInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_CONFLICT
        render responseBody as GSON
    }

    private respondRegistrationApprovalConflict(Registration registrationInstance) {
        def responseBody = [:]
        responseBody.errors = registrationInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_CONFLICT
        render responseBody as GSON
    }

    private void respondUpdated(Registration registrationInstance) {
        response.status = SC_OK
        render registrationInstance as GSON
    }

    private void respondUnprocessableEntity(Registration registrationInstance) {
        def responseBody = [:]
        responseBody.errors = registrationInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_UNPROCESSABLE_ENTITY
        render responseBody as GSON
    }

    private void respondCreated(Registration registrationInstance) {
        response.status = SC_CREATED
        response.addHeader LOCATION, createLink(action: 'show', id: registrationInstance.id)
        render registrationInstance as GSON
    }
}