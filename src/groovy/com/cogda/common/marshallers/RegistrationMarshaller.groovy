package com.cogda.common.marshallers

import com.cogda.domain.onboarding.Registration
import grails.converters.JSON

/**
 * Created with IntelliJ IDEA.
 * User: chewy
 * Date: 6/18/13
 * Time: 12:31 PM
 * To change this template use File | Settings | File Templates.
 */
class RegistrationMarshaller {

    void register() {
        JSON.registerObjectMarshaller(Registration) { Registration registration ->
            def returnValue =  [
                    class: registration.class.canonicalName,
                    id: registration.id,
                    dateCreated: registration.dateCreated,
                    lastUpdated: registration.lastUpdated,
                    firstName: registration.firstName,
                    lastName: registration.lastName,
                    username: registration.username,
                    emailAddress: registration.emailAddress,
                    companyName: registration.companyName,
                    companyType: registration.companyType,
                    companyTypeOther: registration.companyTypeOther,
                    phoneNumber: registration.phoneNumber,
                    streetAddressOne: registration.streetAddressOne,
                    streetAddressTwo: registration.streetAddressTwo,
                    streetAddressThree: registration.streetAddressThree,
                    zipcode: registration.zipcode,
                    city: registration.city,
                    state: registration.state,
                    county: registration.county,
                    country: registration.country,
                    newCompany: registration.newCompany,
                    token: registration.token,
                    subDomain: registration.subDomain,
                    registrationStatus: registration.registrationStatus,
                    emailConfirmationLogs: registration.emailConfirmationLogs
            ]
            if (registration?.existingCompany){
                returnValue.existingCompany = registration.existingCompany
            }
            return returnValue
        }
    }
}
