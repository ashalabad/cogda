package com.cogda.domain.onboarding

import com.cogda.common.web.AjaxResponseDto
import com.cogda.domain.admin.CompanyType
import com.cogda.domain.admin.SupportedCountryCode
import com.cogda.multitenant.Company
import com.cogda.multitenant.CompanyService
import com.cogda.security.UserService
import com.cogda.util.ErrorMessageResolverService
import grails.converters.JSON
import grails.gsp.PageRenderer
import grails.plugins.springsecurity.Secured
import grails.plugins.springsecurity.SpringSecurityService
import org.codehaus.groovy.grails.plugins.web.taglib.CountryTagLib

/**
 * RegisterController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class RegisterController {

    /**
     * Auto-wiring registerService from
     * service layer into the RegisterController
     */
    RegisterService registerService
    ErrorMessageResolverService errorMessageResolverService
    SpringSecurityService springSecurityService
    AccountActivationService accountActivationService
    PageRenderer groovyPageRenderer

    static defaultAction = "index"

    /**
     * Generates JSON needed for a DataTables table.
     */
    def dataTablesSource(){

        def propertiesToRender = ['lastName', 'firstName', 'emailAddress', 'companyName', 'companyType.code']

        def filters = []
        propertiesToRender.each { prop ->
            filters << "p.${prop} like :filter"
        }
        def filter = filters.join(" OR ")

        def dataToRender = [:]
        dataToRender.sEcho = params.sEcho
        dataToRender.aaData = []                // Array of registrations.

        dataToRender.iTotalRecords = Registration.count(true)
        dataToRender.iTotalDisplayRecords = dataToRender.iTotalRecords

        def query = new StringBuilder("from Registration as r where ")
        if (params.sSearch) {
            query.append(" and (${filter})")
        }
        def sortDir = params.sSortDir_0?.equalsIgnoreCase('asc') ? 'asc' : 'desc'
        def sortProperty = propertiesToRender[params.iSortCol_0 as int]
        query.append(" order by p.${sortProperty} ${sortDir}")

        def registrations = []
        if (params.sSearch) {
            // Revise the number of total display records after applying the filter
            def countQuery = new StringBuilder("select count(*) from Registration as r where ")
            if (params.sSearch) {
                countQuery.append(" and (${filter})")
            }
            def result = Registration.executeQuery(countQuery.toString(),
                    [filter: "%${params.sSearch}%"])
            if (result) {
                dataToRender.iTotalDisplayRecords = result[0]
            }
            registrations = Registration.findAll(query.toString(),
                    [filter: "%${params.sSearch}%"],
                    [max: params.iDisplayLength as int, offset: params.iDisplayStart as int])
        } else {
            registrations = Registration.findAll(query.toString(),
                    [max: params.iDisplayLength as int, offset: params.iDisplayStart as int])
        }

        registrations?.each { prod ->
            dataToRender.aaData << [prod.itemNumber,
                    prod.title,
                    prod.price,
                    prod.link,
                    prod.manufacturer?.name ?: 'Unknown']
        }

        render dataToRender as JSON
    }


    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)

    }

    def index() {
    }

    /**
     * Handles AjaxRequest posts
     * @param registerCommand
     */
    def save(RegisterCommand registerCommand) {

        AjaxResponseDto ajaxResponseDto = new AjaxResponseDto()

        if (registerCommand.hasErrors()) {
            ajaxResponseDto.success = Boolean.FALSE
            ajaxResponseDto.errors = errorMessageResolverService.retrieveErrorStrings(registerCommand)
            render ajaxResponseDto as JSON
            return  // someone is most likely fooling around with the request headers
        } else {
            Registration registration = registerCommand.getRegistrationObject()
            registerService.save(registration)
            if (registration.hasErrors()) {
                ajaxResponseDto.success = Boolean.FALSE
                ajaxResponseDto.errors = errorMessageResolverService.retrieveErrorStrings(registration)
                render ajaxResponseDto as JSON
                return  // someone is most likely fooling around with the request headers
            } else {

                // send the registration verification link to the user
                String emailVerificationUrl = generateLink('verify', [t: registration.token])
                accountActivationService.prepareEmailVerification(registration, emailVerificationUrl)

                ajaxResponseDto.success = true
                // Add a success message for this section type
                ajaxResponseDto.addMessage(message(code: "registration.successful", args: []))
                ajaxResponseDto.modelObject = [emailAddress: registerCommand.emailAddress]
                ajaxResponseDto.htmlTemplate = groovyPageRenderer.render(template: "/register/successfulRegistration", model:[registrationInstance:registration])
                render ajaxResponseDto as JSON
                return
            }
        }
    }

    /**
     * Renders the string response true if the Username is available.
     * Renders the string response "false" if the Username is unavailable.
     * @param availableUsernameCommand
     * @return String
     */
    def availableUsername(AvailableUsernameCommand availableUsernameCommand) {
        println params
        boolean valid = Boolean.FALSE
        if (!availableUsernameCommand.hasErrors()) {
            valid = Boolean.TRUE
        }
        render valid
        return
    }

    protected String generateLink(String action, linkParams) {
        createLink(base: "$request.scheme://$request.serverName:$request.serverPort$request.contextPath",
                controller: 'emailVerification', action: action,
                params: linkParams)
    }

}

class AvailableUsernameCommand {
    UserService userService

    String username

    static constraints = {
        username(validator: { val, obj ->
            if (!obj.userService.availableUsername(val)) {
                return ['validation.username.notavailable']
            }
        })
    }
}

/**
 * RegisterCommand validates the data prior to the sign-on of a new
 * COGDA user.                                       a
 */
class RegisterCommand {
    SpringSecurityService springSecurityService
    CompanyService companyService
    UserService userService

    String firstName
    String lastName
    String emailAddress
    String username
    String password
    String passwordTwo
    Company existingCompany
    Long existingCompanyId
    Boolean newCompany
    String companyName
    Long companyTypeId
    String companyTypeOther
    String phoneNumber
    String streetAddressOne
    String streetAddressTwo
    String zipcode
    String city
    String state
    String county
    String country

    /**
     * Populates and returns a Registration domain class based on the
     * RegisterCommand
     * @return Registration
     */
    public Registration getRegistrationObject() {

        Registration registration = new Registration()
        registration.firstName = this.firstName
        registration.lastName = this.lastName
        registration.emailAddress = this.emailAddress
        registration.password = springSecurityService.encodePassword(this.password)
        registration.companyType = CompanyType.get(this.companyTypeId)
        registration.phoneNumber = this.phoneNumber
        registration.newCompany = this.newCompany
        registration.companyName = this.companyName
        registration.username = this.username


        if (registration.newCompany) {
            registration.streetAddressOne = this.streetAddressOne
            registration.streetAddressTwo = this.streetAddressTwo
            registration.zipcode = this.zipcode
            registration.city = this.city
            registration.state = this.state
            registration.county = this.county
            registration.country = this.country
        } else {
//            registration.existingCompany =
        }
        return registration
    }

    static constraints = {
        importFrom Registration, include: ["firstName", "lastName", "emailAddress", "username", "newCompany",
                "companyName"]
        password(blank: false, minSize: 6, maxSize: 84)
        passwordTwo(blank:false, minSize: 6, maxSize: 84, validator: { val, obj ->
            if (!obj.password.equals(val)) {
                return ['registerCommand.passwordTwo.nomatch']
            }
        })
        companyTypeId(blank: false, inList: CompanyType.retrieveIds())
        newCompany(validator: { val, obj ->
            // if this is a new company then validate that the address information is provided.
            if (val) {
                if (!obj.companyTypeId) {
                    return ['registration.companyTypeId.blank']
                } else {
                    if (!CompanyType.retrieveIds().contains(obj.companyTypeId)) {
                        return ['registration.companyTypeId.inList']
                    }
                }
                if (!obj.phoneNumber?.trim()) {
                    return ['registration.phoneNumber.blank']
                }
                if (!obj.country?.trim()) {
                    return ['registration.country.blank']
                } else {
                    if (!SupportedCountryCode.retrieveSupportedCountryCodes().contains(obj.country)) {
                        return ['registration.country.notfound']
                    }
                }
                if (!obj.streetAddressOne?.trim()) {
                    return ['registration.streetAddressOne.blank']
                }
                if (!obj.zipcode?.trim()) {
                    return ['registration.zipcode.blank']
                }
                // if the country is "usa" then we must validate the city and the state fields.
                if(obj.country?.equals("usa")){
                    if (!obj.city?.trim()) {
                        return ['registration.city.blank']
                    }
                    if (!obj.state?.trim()) {
                        return ['registration.state.blank']
                    }
                }
            }
        })
    }

    String toString(){
        """
        RegisterCommand:
        firstName: $firstName
        lastName: $lastName
        emailAddress: $emailAddress
        password: *******
        passwordTwo: *******
        existingCompany: ${existingCompany?.id}
        existingCompanyId: $existingCompanyId
        newCompany: $newCompany
        companyName: $companyName
        companyTypeId: $companyTypeId
        companyTypeOther: $companyTypeOther
        phoneNumber: $phoneNumber
        streetAddressOne: $streetAddressOne
        streetAddressTwo: $streetAddressTwo
        zipcode: $zipcode
        city: $city
        state: $state
        county: $county
        country: $country
        """
    }

}
