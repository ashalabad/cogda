package com.cogda.domain.admin

import com.cogda.GsonBaseController
import com.cogda.common.RegistrationStatus
import com.cogda.domain.onboarding.RegisterCommand
import com.cogda.domain.onboarding.Registration
import com.cogda.domain.onboarding.RegistrationService
import com.cogda.multitenant.CustomerAccount
import com.cogda.multitenant.CustomerAccountService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import grails.plugin.gson.converters.GSON
import grails.plugin.multitenant.core.CurrentTenant
import grails.plugins.springsecurity.Secured
import org.springframework.dao.DataIntegrityViolationException

import static javax.servlet.http.HttpServletResponse.*
import static org.codehaus.groovy.grails.web.servlet.HttpHeaders.*
import static grails.plugin.gson.http.HttpConstants.*
/**
 * RegistrationApprovalController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Secured(['ROLE_ADMINISTRATOR'])
class RegistrationApprovalController extends GsonBaseController {
    final static String INSTANCE_LABEL = "registration.label"

    CurrentTenant currentTenant

    CustomerAccountService customerAccountService

    RegistrationService registrationService

    /**
     * Very important - since this controller lives inside the Cogda
     * application this means that it is, theoretically, accessible to
     * all tenants in the system.  We lock down access via the beforeInterceptor
     * so that only CustomerAccount's that are internalCustomerAccounts are
     * allowed to access this controller's actions and data.
     */
    def beforeInterceptor = {
        return isValidRequest()
    }

    /**
     * Validate Request is used to determine if the current tenant maps to a CustomerAccount with
     * internalSystemAccount = true
     * @return true if this is a valid request
     */
    protected Boolean isValidRequest(){
        return customerAccountService.isInternalCustomerAccount(CustomerAccount.get(currentTenant.get()))
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def list(){
        params.max = Math.min(params.max ? params.int('max') : 10, 100)  // always pass in max so you get a PagedResultSet returned.
        List registrations = Registration.list(params)
        response.addIntHeader X_PAGINATION_TOTAL, registrations.totalCount
        response.status = SC_OK  // 200
        render registrations as GSON
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def reject(){
        Registration registrationInstance = Registration.get(params.id)
        if (!registrationInstance) {
            respondNotFound INSTANCE_LABEL
            return
        }

        registrationService.rejectRegistration(registrationInstance)

        if (!registrationInstance.hasErrors()) {
            respondUpdated registrationInstance
        } else {
            respondUnprocessableEntity registrationInstance
        }
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def approve(){
        if (!Registration.exists(params.id)) {
            respondNotFound INSTANCE_LABEL
            return
        }
        JsonElement jsonElement = GSON.parse(request)
        RegistrationApprovalCommand command = new RegistrationApprovalCommand()
        command.id = params.id
        command.subDomain = jsonElement.subDomain

        if(command.validate()){
            Registration registrationInstance = Registration.get(params.id)
            registrationService.approveRegistration(registrationInstance)
            if (!registrationInstance.hasErrors()) {
                respondUpdated registrationInstance
            } else {
                respondUnprocessableEntity registrationInstance
            }
        }else{
            respondUnprocessableEntity command
        }
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def get() {
        Registration registrationInstance = Registration.get(params.id)
        if (registrationInstance) {
            respondFound registrationInstance
        } else {
            respondNotFound INSTANCE_LABEL
        }
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def delete() {
        def registrationInstance = Registration.get(params.id)
        if (!registrationInstance) {
            respondNotFound INSTANCE_LABEL
            return
        }

        try {
            registrationInstance.delete(flush: true)
            respondDeleted(INSTANCE_LABEL)
        } catch (DataIntegrityViolationException e) {
            respondNotDeleted INSTANCE_LABEL
        }
    }
}

class RegistrationApprovalCommand{
    /**
     * The Registration id
     */
    Long id

    /**
     * The subDomain being issued
     */
    String subDomain

    static constraints = {

        id(nullable:false, validator: { val, obj ->
            if(!Registration.exists(val)){
                return ['registrationApproval.id.doesnotexist']
            }
            RegistrationStatus registrationStatus = Registration.get(val).registrationStatus
            if(registrationStatus == RegistrationStatus.APPROVED){
                return ['registrationApproval.registrationStatus.approved']
            }
            if(registrationStatus == RegistrationStatus.REJECTED){
                return ['registrationApproval.registrationStatus.rejected']
            }
        })
        subDomain(nullable:false, blank:false, validator: { val, obj ->

            if(CustomerAccount.findBySubDomain(val.toLowerCase())){
                return ['registrationApproval.subDomain.already.exists']
            }
        })
    }
}
