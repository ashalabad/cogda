package com.cogda.api.admin

import com.cogda.common.web.AjaxResponseDto
import com.cogda.domain.admin.AdminService
import com.cogda.domain.onboarding.Registration
import com.cogda.errors.RegistrationException
import com.cogda.security.SecurityService
import com.cogda.util.ErrorMessageResolverService
import grails.converters.JSON
import grails.converters.XML
import grails.plugins.springsecurity.Secured

/**
 * AdminRegisterController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Secured('ROLE_ADMINISTRATOR')
class AdminRegisterController {

    static allowedMethods = [list: 'GET',
            show: 'GET',
            edit: ['GET', 'POST'],
            save: 'POST',
            update: ['POST', 'PUT'],
            approve: ['POST', 'PUT'],
            updateSubdomain: ['POST']
    ]

    AdminService adminService
    ErrorMessageResolverService errorMessageResolverService

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        AjaxResponseDto ajaxResponseDto = new AjaxResponseDto()
        try {
            def list = adminService.listRegistrations(params)
            ajaxResponseDto.success = Boolean.TRUE
            ajaxResponseDto.modelObject = [registrationList: list]
            render ajaxResponseDto as JSON
            return
        } catch (Exception e) {
            ajaxResponseDto.success = Boolean.FALSE
            ajaxResponseDto.errors = [exception: e.message, stackTrace: e.stackTrace.toArrayString()]
            render ajaxResponseDto as JSON
            return
        }
    }

    def create() {
        Registration registration = new Registration()
        AjaxResponseDto ajaxResponseDto = new AjaxResponseDto()
        ajaxResponseDto.success = Boolean.TRUE
        ajaxResponseDto.modelObject = [registration: registration]
        render ajaxResponseDto as JSON
        return
    }

    def show(long id) {
        def registration = adminService.findRegistrationById(id)
        if (!registration) {
            withFormat {
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }
        def object = [registration: registration]
        withFormat {
            json { render object as JSON }
            xml { render object as XML }
        }
    }

    def update(long id) {
        Registration registration = Registration.get(id)
        AjaxResponseDto ajaxResponseDto = new AjaxResponseDto()
        if (!registration)
            ajaxResponseDto.success = Boolean.FALSE
            ajaxResponseDto.errors = [error: message(code: "registration.failure.failedToFind", args: [])]
            render ajaxResponseDto as JSON
            return
        if (registration.hasErrors()) {
            ajaxResponseDto.success = Boolean.FALSE
            ajaxResponseDto.errors = errorMessageResolverService.retrieveErrorStrings(registration)
            render ajaxResponseDto as JSON
            return
        } else {
            adminService.updateRegistration(id, registration)
            if (registration.hasErrors()) {
                ajaxResponseDto.success = Boolean.FALSE
                ajaxResponseDto.errors = errorMessageResolverService.retrieveErrorStrings(registration)
                render ajaxResponseDto as JSON
                return
            } else {
                ajaxResponseDto.success = true
                ajaxResponseDto.addMessage(message(code: "registration.update.successful", args: []))
                ajaxResponseDto.modelObject = [registration: registration]
                render ajaxResponseDto as JSON
                return
            }
        }
    }

    def approve(long id) {
        AjaxResponseDto ajaxResponseDto = new AjaxResponseDto()
        ajaxResponseDto.modelObject = [id: id]
        try {
            adminService.approveRegistration(id)
            ajaxResponseDto.success = Boolean.TRUE
            ajaxResponseDto.messages = [g.message(code:'registration.status.adminapproved')]
        } catch(RegistrationException e) {
            ajaxResponseDto.success = Boolean.FALSE
            ajaxResponseDto.errors = [error0: g.message(code: 'registration.status.adminapprovedfailed'), error1: e.message]
        }
        render ajaxResponseDto as JSON
    }

    def save() {
        Registration registration = new Registration(params.registration)
        AjaxResponseDto ajaxResponseDto = new AjaxResponseDto()
        if (!registration.validate()) {
            ajaxResponseDto.success = Boolean.FALSE
            ajaxResponseDto.errors = errorMessageResolverService.retrieveErrorStrings(registration)
            render ajaxResponseDto as JSON
            return
        } else {
            adminService.saveRegistration(registration)
            if (registration.hasErrors()) {
                ajaxResponseDto.success = Boolean.FALSE
                ajaxResponseDto.errors = errorMessageResolverService.retrieveErrorStrings(registration)
                render ajaxResponseDto as JSON
                return
            } else {
                ajaxResponseDto.success = true
                ajaxResponseDto.addMessage(message(code: "registration.successful", args: []))
                ajaxResponseDto.modelObject = [registration: registration]
                render ajaxResponseDto as JSON
                return
            }
        }
    }

    def updateSubdomain(long id, String subDomain){
        AjaxResponseDto ajaxResponseDto = new AjaxResponseDto()
        String updatedSubDomain = adminService.updateSubdomain(id, subDomain)
        if (!updatedSubDomain){
            ajaxResponseDto.success = Boolean.FALSE
            ajaxResponseDto.errors = [error: "Failed to update registration"]
        } else {
            ajaxResponseDto.success = Boolean.TRUE
            ajaxResponseDto.modelObject = [subDomain: updatedSubDomain]
            ajaxResponseDto.addMessage(message(code: "registration.subdomain.successful", args: []))
        }
        render ajaxResponseDto as JSON
    }
}