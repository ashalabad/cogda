import com.cogda.common.MarkupLanguage
import com.cogda.domain.admin.CompanyType
import com.cogda.domain.admin.SystemEmailMessageTemplate
import com.cogda.multitenant.Company
import com.cogda.multitenant.CustomerAccount
import com.cogda.domain.admin.SupportedCountryCode
import grails.util.Environment
import grails.util.GrailsUtil

class BootStrap {

    def init = { servletContext ->


        CompanyType agency = new CompanyType(code:"Agency/Retailer", intCode:0, description: "Agency/Retailer").save()
        CompanyType carrier = new CompanyType(code:"Carrier", intCode:1, description: "Carrier").save()
        CompanyType reinsurer = new CompanyType(code:"Reinsurer", intCode:2, description: "Reinsurer").save()
        CompanyType wholesaler = new CompanyType(code:"Wholesaler (MGA, Broker)", intCode:3, description: "Wholesaler (MGA, Broker)").save()

        CustomerAccount rpsCustomerAccount = CustomerAccount.findOrSaveBySubDomain("rps")
        CustomerAccount raisCustomerAccount = CustomerAccount.findOrSaveBySubDomain("renaissanceins")

        raisCustomerAccount.withThisTenant {
            Company company = new Company()
            company.companyName = "Renaissance Alliance"
            company.doingBusinessAs = "Renaissance Alliance"
            company.intCode = 0

            if(!company.save()){
                company.errors.each {
                    log.debug(it)
                }
            }
        }



        SupportedCountryCode usa = new SupportedCountryCode(countryCode:"usa", countryDescription:"United States").save()
        SupportedCountryCode can = new SupportedCountryCode(countryCode:"can", countryDescription:"Canada").save()
        //        SupportedCountryCode bra = new SupportedCountryCode(countryCode:"bra", countryDescription:"Brazil").save()


        if(!SystemEmailMessageTemplate.findByTitle("INITIAL_ACCOUNT_ACTIVATION_EMAIL")){
            SystemEmailMessageTemplate accountActivationEmailMessage = new SystemEmailMessageTemplate()
            accountActivationEmailMessage.markupLanguage = MarkupLanguage.MARKDOWN
            accountActivationEmailMessage.title = "INITIAL_ACCOUNT_ACTIVATION_EMAIL"
            accountActivationEmailMessage.description = "The email message that is sent to the User when activating a new account."
            accountActivationEmailMessage.subject = "Cogda Account Verification"
            accountActivationEmailMessage.fromEmail = "mail@cogda.com"
            accountActivationEmailMessage.body = """
Thank you for your interest in {appName}.  We sincerely look forward to serving you and your organization {organizationName}.

Please click the following verification code to activate your new {appName} account.

{activationUrl}

Upon successful activation of your account you will be directed to your organization's new {appName} installation!

Thank you!

{appName} Team"""
            accountActivationEmailMessage.acceptsParameters = true
            accountActivationEmailMessage.requiredParameterNames = ['appName', 'organizationName', 'activationUrl']
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

    }
    def destroy = {
    }
}
