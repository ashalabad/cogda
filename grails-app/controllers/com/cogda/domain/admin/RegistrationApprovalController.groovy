package com.cogda.domain.admin

import com.cogda.GsonBaseController
import com.cogda.common.RegistrationStatus
import com.cogda.common.marshallers.HibernateProxyTypeAdapter
import com.cogda.domain.onboarding.Registration
import com.cogda.domain.onboarding.RegistrationService
import com.cogda.dto.RegistrationApprovalDto
import com.cogda.multitenant.CustomerAccount
import com.cogda.multitenant.CustomerAccountService
import com.google.gson.Gson
import com.google.gson.JsonElement
import grails.converters.JSON
import grails.plugin.gson.converters.GSON
import grails.plugin.multitenant.core.CurrentTenant
import grails.plugins.springsecurity.Secured
import grails.validation.Validateable
import org.apache.commons.beanutils.BeanUtils
import org.springframework.dao.DataIntegrityViolationException

import static javax.servlet.http.HttpServletResponse.*
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

    @Secured(['ROLE_ADMINISTRATOR'])
    def index(){

    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def listPartial(){
        render(view:"listPartial")
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def detailPartial(){
        render(view:"detailPartial")
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def processPartial(){
        render(view:"processPartial")
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
//        params.max = Math.min(params.max ? params.int('max') : 10, 100)  // always pass in max so you get a PagedResultSet returned.
//        params.offset = params.offset ? params.int('offset') : 0
        params.sort = params.sort ?: 'dateCreated'
        params.order  = params.order  ?: 'desc'

        List registrations = Registration.list(params)
        response.addIntHeader X_PAGINATION_TOTAL, Registration.count()

        // do the conversion
        List registrationDtos = registrations.collect { Registration registration ->
            RegistrationApprovalDto registrationApprovalDto = new RegistrationApprovalDto()
            registrationApprovalDto.companyTypeCode = registration?.companyType.code
            registrationApprovalDto.companyTypeDescription = registration?.companyType?.description
            registrationApprovalDto.existingCompanyId = registration?.existingCompany?.id
            registrationApprovalDto.registrationStatusValue = registration.registrationStatus.name()
            BeanUtils.copyProperties(registrationApprovalDto, registration)
            registrationApprovalDto
        }

        response.status = SC_OK  // 200
        render registrationDtos as GSON
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

        RegistrationApprovalCommand command = new RegistrationApprovalCommand()
        JsonElement jsonElement = request.GSON
        command.id = jsonElement.id.asLong
        command.subDomain = jsonElement.subDomain.asString

        if(command.validate()){
            Registration registrationInstance = Registration.get(params.id)
            registrationInstance.subDomain = command.subDomain
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
            response.status = SC_OK // 200
            Gson gson = gsonBuilder.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).create()
            def gsonRetString = gson.toJsonTree(registrationInstance)
            render gsonRetString
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

@Validateable
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

        id(nullable:false, blank:false, validator: { val, obj ->
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
        subDomain(nullable:false, blank:false, minSize:2, matches:"[A-Za-z]+", validator: { val, obj ->
            if(CustomerAccount.findBySubDomain(val.toLowerCase())){
                return ['registrationApproval.subDomain.already.exists']
            }
            if(CustomerAccount.DISALLOWED_SUBDOMAINS.collect { it.toUpperCase() }
                    .contains(val.toUpperCase())){
                return ['customerAccount.subDomain.invalid']
            }
        })
    }
}
