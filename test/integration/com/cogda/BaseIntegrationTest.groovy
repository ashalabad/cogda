package com.cogda

import com.cogda.common.MarkupLanguage
import com.cogda.common.RegistrationStatus
import com.cogda.domain.UserProfile
import com.cogda.domain.UserProfileEmailAddress
import com.cogda.domain.admin.CompanyType
import com.cogda.domain.admin.SupportedCountryCode
import com.cogda.domain.admin.SystemEmailMessageTemplate
import com.cogda.domain.onboarding.Registration
import grails.plugins.springsecurity.SpringSecurityService
import org.apache.commons.logging.LogFactory

/**
 * Created with IntelliJ IDEA.
 * User: christopher
 * Date: 6/10/13
 * Time: 3:15 PM
 * To change this template use File | Settings | File Templates.
 */
class BaseIntegrationTest {
    private static final log = LogFactory.getLog(this)

    /**
     * Creates a valid UserProfile object and returns it.
     * @return  UserProfile
     */
    public UserProfile createUserProfile(){
        UserProfile userProfile = new UserProfile()
        userProfile.firstName = "firstName"
        userProfile.lastName = "lastName"
        userProfile.published = false
        assert userProfile.save(), "UserProfile test domain class was not saved successfully"
        String primaryEmailAddress = "primary@cogda.com"
        List userProfileEmailAddresses = [new UserProfileEmailAddress(emailAddress: primaryEmailAddress, primaryEmailAddress: true, published:false, userProfile: userProfile),
                new UserProfileEmailAddress(emailAddress: "mezz@cogda.com", primaryEmailAddress: false, published:false, userProfile: userProfile)]
        userProfileEmailAddresses.each { UserProfileEmailAddress userProfileEmailAddress ->
            userProfileEmailAddress.userProfile = userProfile
            userProfile.addToUserProfileEmailAddresses(userProfileEmailAddress)
        }

        return userProfile
    }

    public void createCompanyTypes(){
        CompanyType agency = new CompanyType(code:"Agency/Retailer", intCode:0, description: "Agency/Retailer")
        if(!agency.save()){
            agency.errors.each {
                log.debug it
            }
        }
        CompanyType carrier = new CompanyType(code:"Carrier", intCode:1, description: "Carrier")
        if(!carrier.save()){
            carrier.errors.each {
                log.debug it
            }
        }
        CompanyType reinsurer = new CompanyType(code:"Reinsurer", intCode:2, description: "Reinsurer")
        if(!reinsurer.save()){
            reinsurer.errors.each {
                log.debug it
            }
        }
        CompanyType wholesaler = new CompanyType(code:"Wholesaler (MGA, Broker)", intCode:3, description: "Wholesaler (MGA, Broker)")
        if(!wholesaler.save()){
            wholesaler.errors.each {
                log.debug it
            }
        }
    }

    public void createSupportedCountryCodes(){
        SupportedCountryCode usa = new SupportedCountryCode(countryCode:"usa", countryDescription:"United States").save()
        SupportedCountryCode can = new SupportedCountryCode(countryCode:"can", countryDescription:"Canada").save()
    }

    /**
     * Creates a Valid Registration Domain Class.
     * @return Registration
     */
    public Registration createValidRegistration(){
        Registration registration = new Registration()

        registration.firstName = "Christopher"
        registration.lastName = "Kwiatkowski"
        registration.username = "ctk"
        registration.emailAddress = "chris@cogda.com"
        registration.password = "939020kiddko2"
        registration.companyName = "Cogda Solutions, LLC."
        registration.companyType = CompanyType.list().first()
        registration.existingCompany = null
        registration.companyTypeOther = null
        registration.phoneNumber = "706-255-9087"
        registration.streetAddressOne = "1 Press Place"
        registration.streetAddressTwo = "Suite 200"
        registration.streetAddressThree = "Office #17"
        registration.city = "Athens"
        registration.state = "GA"
        registration.zipcode = "30601"
        registration.county = "CLARKE"
        registration.registrationStatus = RegistrationStatus.APPROVED
        registration.subDomain = "rais"

        log.debug("Saving Registration Domain Class")

        assert registration.save(), "Registration save failed: ${registration.errors}"

        return registration
    }

    /**
     * Creates a Valid SystemEmailMessageTemplate
     * @return SystemEmailMessageTemplate
     */
    public SystemEmailMessageTemplate createValidSystemEmailMessageTemplate(){
        SystemEmailMessageTemplate accountActivationEmailMessage = new SystemEmailMessageTemplate()
        accountActivationEmailMessage.markupLanguage = MarkupLanguage.MARKDOWN
        accountActivationEmailMessage.title = "INITIAL_ACCOUNT_ACTIVATION_EMAIL"
        accountActivationEmailMessage.description = "The email message that is sent to the User when activating a new account."
        accountActivationEmailMessage.subject = "Cogda Email Verification"
        accountActivationEmailMessage.fromEmail = "mail@cogda.com"
        accountActivationEmailMessage.body = """
Thank you for your interest in {appName}.  We sincerely look forward to serving you and your company.

Please click the following verification link to activate your new {appName} account.

{activationUrl}

Upon successful activation of your account your company information will be verified and your company's account provisioned on {appName}!

Thank you!

{appName} Team"""
        accountActivationEmailMessage.acceptsParameters = true
        accountActivationEmailMessage.requiredParameterNames = ['appName', 'activationUrl']
        assert accountActivationEmailMessage.save(failOnError:true), "AccountActivationEmailMessage save failed: ${accountActivationEmailMessage.errors}"

        log.debug "Successfully saved AccountActivationEmailMessage"

        return accountActivationEmailMessage
    }

    /**
     * Creates the VERIFIED_SUCCESSFULLY_EMAIL template.
     * @return SystemEmailMessageTemplate
     */
    public SystemEmailMessageTemplate createConfirmEmailVerificationMessageTemplate(){
        SystemEmailMessageTemplate verifiedEmailMessage = new SystemEmailMessageTemplate()
        verifiedEmailMessage.markupLanguage = MarkupLanguage.MARKDOWN
        verifiedEmailMessage.title = "VERIFIED_SUCCESSFULLY_EMAIL"
        verifiedEmailMessage.description = "The email message that is sent to the User after they verify their email with us. "
        verifiedEmailMessage.subject = "Your email has been verified by Cogda"
        verifiedEmailMessage.fromEmail = "mail@cogda.com"
        verifiedEmailMessage.body = """
    Thank you for validating your email address with {appName}!

    Sincerely,

    {appName} Team"""
        verifiedEmailMessage.acceptsParameters = true
        verifiedEmailMessage.requiredParameterNames = ['appName', 'organizationUrl']
        verifiedEmailMessage.save()
        return verifiedEmailMessage
    }

    /**
     * Creates the RESET_PASSWORD_EMAIL template.
     * @return SystemEmailMessageTemplate
     */
    public SystemEmailMessageTemplate createResetPasswordMessageTemplate(){
        SystemEmailMessageTemplate resetPasswordEmailMessage = new SystemEmailMessageTemplate()
        resetPasswordEmailMessage.markupLanguage = MarkupLanguage.MARKDOWN
        resetPasswordEmailMessage.title = "RESET_PASSWORD_EMAIL"
        resetPasswordEmailMessage.description = "The email message that is sent to the User when they are attempting to reset their password."
        resetPasswordEmailMessage.subject = "Cogda Reset Forgotten Password"
        resetPasswordEmailMessage.fromEmail = "mail@cogda.com"
        resetPasswordEmailMessage.body = """
    You are receiving this message because you had completed the Forgot Password form in {appName}.

    Please click the following verification link to reset your {appName} password from within {appName}.

    {resetPasswordUrl}

    Thank you!

    {appName} Team"""
        resetPasswordEmailMessage.acceptsParameters = true
        resetPasswordEmailMessage.requiredParameterNames = ['appName', 'resetPasswordUrl']
        resetPasswordEmailMessage.save(failOnError:true)

        return resetPasswordEmailMessage
    }

    void testNothing(){
        assert 1 == 1
    }
}
