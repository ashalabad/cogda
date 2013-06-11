import com.cogda.common.MarkupLanguage
import com.cogda.common.RegistrationStatus
import com.cogda.domain.admin.CompanyType
import com.cogda.domain.admin.SystemEmailMessageTemplate
import com.cogda.domain.onboarding.Registration
import com.cogda.multitenant.Company
import com.cogda.multitenant.CustomerAccount
import com.cogda.domain.admin.SupportedCountryCode
import com.cogda.multitenant.CustomerAccountService
import grails.plugins.springsecurity.SpringSecurityService
import grails.util.Environment
import grails.util.GrailsUtil

class BootStrap {
    CustomerAccountService customerAccountService
    SpringSecurityService springSecurityService

    def init = { servletContext ->



        if(Environment.current != Environment.TEST){

            if(!CompanyType.findByCode("Agency/Retailer")){
                new CompanyType(code:"Agency/Retailer", intCode:0, description: "Agency/Retailer").save()
            }
            if(!CompanyType.findByCode("Carrier")){
                new CompanyType(code:"Carrier", intCode:1, description: "Carrier").save()
            }
            if(!CompanyType.findByCode("Reinsurer")){
                new CompanyType(code:"Reinsurer", intCode:2, description: "Reinsurer").save()
            }
            if(!CompanyType.findByCode("Wholesaler (MGA, Broker)")){
                new CompanyType(code:"Wholesaler (MGA, Broker)", intCode:3, description: "Wholesaler (MGA, Broker)").save()
            }


            if(!SupportedCountryCode.findByCountryCode("usa")){
                new SupportedCountryCode(countryCode:"usa", countryDescription:"United States").save()
            }
            if(!SupportedCountryCode.findByCountryCode("usa")){
                new SupportedCountryCode(countryCode:"can", countryDescription:"Canada").save()
            }
//        SupportedCountryCode bra = new SupportedCountryCode(countryCode:"bra", countryDescription:"Brazil").save()

            if(!Registration.findBySubDomain("rais")){
                Registration registration
                Registration.withTransaction {
                    registration = new Registration()

                    registration.firstName = "Maria"
                    registration.lastName = "Schiller"
                    registration.username = "admin"
                    registration.emailAddress = "chris@cogda.com"
                    registration.password = springSecurityService.encodePassword("password")
                    registration.companyName = "Renaissance Alliance"
                    registration.companyType = CompanyType.findByCode("Wholesaler (MGA, Broker)")
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

                    assert registration.save(), "Registration save failed: ${registration.errors}"
                }

                customerAccountService.onboardCustomerAccount(registration)
            }


            if(!SystemEmailMessageTemplate.findByTitle("INITIAL_ACCOUNT_ACTIVATION_EMAIL")){
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
                accountActivationEmailMessage.save(failOnError:true)
            }

            if(!SystemEmailMessageTemplate.findByTitle("REMINDER_ACCOUNT_ACTIVATION_EMAIL")){
                SystemEmailMessageTemplate accountReminderEmailMessage = new SystemEmailMessageTemplate()
                accountReminderEmailMessage.markupLanguage = MarkupLanguage.MARKDOWN
                accountReminderEmailMessage.title = "REMINDER_ACCOUNT_ACTIVATION_EMAIL"
                accountReminderEmailMessage.description = "The email message that is sent to the User as a reminder to activate their account."
                accountReminderEmailMessage.subject = "Cogda Account Verification Reminder"
                accountReminderEmailMessage.fromEmail = "mail@cogda.com"
                accountReminderEmailMessage.body = """
    We recently sent an account activation email to you in response to your request to begin using {appName} at your organization "{organizationName}".
    If you have already activated your account at {appName} then please disregard the following message.

    Please click the following verification code to activate your new {appName} account.

    {activationUrl}

    Upon successful activation of your account you will be directed to your organization's new {appName} installation!

    Thank you!

    {appName} Team"""
                accountReminderEmailMessage.acceptsParameters = true
                accountReminderEmailMessage.requiredParameterNames = ['appName', 'organizationName', 'activationUrl']
                accountReminderEmailMessage.save(failOnError:true)
            }

            if(!SystemEmailMessageTemplate.findByTitle("TIMEOUT_ACCOUNT_ACTIVATION_EMAIL")){
                SystemEmailMessageTemplate accountReminderEmailMessage = new SystemEmailMessageTemplate()
                accountReminderEmailMessage.markupLanguage = MarkupLanguage.MARKDOWN
                accountReminderEmailMessage.title = "TIMEOUT_ACCOUNT_ACTIVATION_EMAIL"
                accountReminderEmailMessage.description = "The email message that is sent to the User to tell them that we have deactivated their request to use Cogda."
                accountReminderEmailMessage.subject = "Cogda Account Verification Timeout"
                accountReminderEmailMessage.fromEmail = "mail@cogda.com"
                accountReminderEmailMessage.body = """
    We recently sent an account activation email to you in response to your request to begin using {appName} at your organization "{organizationName}".

    Unfortunately we did not hear back from you and we have deactivated your request to use {appName}.

    If you would like to establish a new account then please visit us again at {appUrl}.

    Sincerely,

    {appName} Team"""
                accountReminderEmailMessage.acceptsParameters = true
                accountReminderEmailMessage.requiredParameterNames = ['appName', 'organizationName', 'appUrl']
                accountReminderEmailMessage.save(failOnError:true)
            }

            if(!SystemEmailMessageTemplate.findByTitle("NEW_ACCOUNT_WELCOME_EMAIL")){
                SystemEmailMessageTemplate accountWelcomeEmailMessage = new SystemEmailMessageTemplate()
                accountWelcomeEmailMessage.markupLanguage = MarkupLanguage.MARKDOWN
                accountWelcomeEmailMessage.title = "NEW_ACCOUNT_WELCOME_EMAIL"
                accountWelcomeEmailMessage.description = "The email message that is sent to the User after they verify their email with us.  Welcoming a new account to Cogda."
                accountWelcomeEmailMessage.subject = "Welcome to Cogda"
                accountWelcomeEmailMessage.fromEmail = "mail@cogda.com"
                accountWelcomeEmailMessage.body = """
    Thanks for validating your account with {appName}!  You are now all set to begin using and enjoying {appName}!

    {organizationUrl}

    Thank you!

    {appName} Team"""
                accountWelcomeEmailMessage.acceptsParameters = true
                accountWelcomeEmailMessage.requiredParameterNames = ['appName', 'organizationUrl']
                accountWelcomeEmailMessage.save()
            }

            if(!SystemEmailMessageTemplate.findByTitle("VERIFIED_SUCCESSFULLY_EMAIL")){
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
            }
        }
    }
    def destroy = {
    }
}
