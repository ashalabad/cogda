package com.cogda.domain.onboarding

import com.cogda.common.web.AjaxResponseDto
import com.cogda.domain.admin.CompanyType
import com.cogda.domain.admin.SupportedCountryCode
import com.cogda.multitenant.Company
import com.cogda.security.UserService
import com.cogda.util.ErrorMessageResolverService
import grails.converters.JSON
import grails.plugins.springsecurity.Secured

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


    static defaultAction = "index"

    def index(){
        if(CompanyType.list()){
            println "found "
        }
        else {
            println "not found"
        }
        println CompanyType.list()
         CompanyType.list().each {
             println it.intCode + " " + it.code
         }
    }

    /**
     * Handles AjaxRequest posts
     * @param registerCommand
     */
    def save(RegisterCommand registerCommand){
        AjaxResponseDto ajaxResponseDto = new AjaxResponseDto()

        if(registerCommand.hasErrors()){
            ajaxResponseDto.success = Boolean.FALSE
            ajaxResponseDto.errors = errorMessageResolverService.retrieveErrorStrings(registerCommand)
            render ajaxResponseDto as JSON
            return  // someone is most likely fooling around with the request headers
        }else{
            Registration registration = registerCommand.getRegistrationObject()
            registerService.save(registration)
            if(registration.hasErrors()){
                ajaxResponseDto.success = Boolean.FALSE
                ajaxResponseDto.errors = errorMessageResolverService.retrieveErrorStrings(registration)
                render ajaxResponseDto as JSON
                return  // someone is most likely fooling around with the request headers
            }else{
                ajaxResponseDto.success = true
                // Add a success message for this section type
                ajaxResponseDto.addMessage(message(code: "registration.successful", args: [section.sectionTitle]))
                ajaxResponseDto.modelObject = [emailAddress:registerCommand.emailAddress]
                render ajaxResponseDto as JSON
                return
            }
        }
    }

    def availableUsername(AvailableUsernameCommand availableUsernameCommand){
        println params
        boolean valid = Boolean.FALSE
        if(!availableUsernameCommand.hasErrors()){
            valid = Boolean.TRUE
        }
        render valid as JSON
    }
}

class AvailableUsernameCommand {
    UserService userService

    String username

    static constraints = {
        username(validator: { val, obj ->
            if(!obj.userService.availableUsername(val)){
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

    static final String companyTypeQuery = "select ct.intCode from CompanyType ct"

    String firstName
    String lastName
    String emailAddress
    String password
    String passwordTwo
    Company existingCompany
    Boolean newCompany
    String companyName
    Integer companyType
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
    public Registration getRegistrationObject(){
        Registration registration = new Registration()
        registration.properties = this.properties["firstName", "lastName", "emailAddress", "password", "newCompany",
                "companyName", "phoneNumber", "streetAddressOne", "streetAddressTwo", "zipcode", "city", "state",
                "county", "country"]
        registration.companyType = CompanyType.findByIntCode(this.companyType)
        return registration
    }

    static constraints = {
        importFrom Registration, include: ["firstName", "lastName", "emailAddress", "newCompany",
                "companyName", "companyTypeOther", "phoneNumber", "streetAddressOne",
                "streetAddressTwo", "zipcode", "city", "state", "county", "country"]
        password(blank:false, minSize:6, maxSize: 20)
        passwordTwo(validator: { val, obj ->
            if(!obj.password.equals(password)){
                return ['registerCommand.passwordTwo.nomatch']
            }
        })
        companyType(blank:false, inList:CompanyType.executeQuery(companyTypeQuery, [cache:true]))
        newCompany(validator: { val, obj ->
            if(val){
                if(!obj.companyType){
                     return ['registration.companyType.blank']
                }else{
                    if(!CompanyType.retrieveIntCodes().contains(obj.companyType)){
                        return ['registration.companyType.inList']
                    }
                }
                if(!obj.phoneNumber?.trim()){
                    return ['registration.phoneNumber.blank']
                }
                if(!obj.streetAddressOne?.trim()){
                    return ['registration.streetAddressOne.blank']
                }
                if(!obj.zipcode?.trim()){
                    return ['registration.zipcode.blank']
                }
                if(!obj.city?.trim()){
                    return ['registration.city.blank']
                }
                if(!obj.state?.trim()){
                    return ['registration.state.blank']
                }
                if(!obj.country?.trim()){
                    return ['registration.country.blank']
                }else{
                    if(!SupportedCountryCode.executeQuery("select sc.countryCode from SupportedCountryCode sc where sc.countryCode = :country", [country:obj.country], [cache:true])){
                        return ['registration.country.notfound']
                    }
                }
            }
        })
    }
}
