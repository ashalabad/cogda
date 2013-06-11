package com.cogda.api.admin

import com.cogda.common.web.AjaxResponseDto
import com.cogda.domain.admin.AdminService
import com.cogda.domain.onboarding.RegisterCommand
import com.cogda.domain.onboarding.Registration
import com.cogda.security.SecurityService
import com.cogda.util.ErrorMessageResolverService
import grails.converters.JSON
import grails.converters.XML
import grails.plugins.springsecurity.Secured

/**
 * AdminRegisterController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Secured(SecurityService.ROLE_ADMINISTRATOR)
class AdminRegisterController {

    static allowedMethods = [list: 'GET',
            show: 'GET',
            edit: ['GET', 'POST'],
            save: 'POST',
            update: ['POST', 'PUT'],
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

    def show(Long id) {
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

    def update(RegisterCommand registerCommand) {
        AjaxResponseDto ajaxResponseDto = new AjaxResponseDto()
        if (registerCommand.hasErrors()) {
            ajaxResponseDto.success = Boolean.FALSE
            ajaxResponseDto.errors = errorMessageResolverService.retrieveErrorStrings(registerCommand)
            render ajaxResponseDto as JSON
            return
        } else {
            Registration registration = registerCommand.getRegistrationObject()
            adminService.updateRegistration(registration)
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

    def approve(Long id) {
        adminService.approve(id)
    }

    def save(Registration registration) {
            AjaxResponseDto ajaxResponseDto = new AjaxResponseDto()
            if (registration.validate()) {
                ajaxResponseDto.success = Boolean.FALSE
                ajaxResponseDto.errors = errorMessageResolverService.retrieveErrorStrings(registerCommand)
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

}
