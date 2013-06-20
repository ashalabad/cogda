import com.cogda.common.MarkupLanguage
import com.cogda.common.RegistrationStatus
import com.cogda.domain.Contact
import com.cogda.domain.admin.AccountType
import com.cogda.domain.admin.CompanyType
import com.cogda.domain.admin.NoteType
import com.cogda.domain.admin.SystemEmailMessageTemplate
import com.cogda.domain.onboarding.Registration
import com.cogda.multitenant.Company
import com.cogda.multitenant.CustomerAccount
import com.cogda.domain.admin.SupportedCountryCode
import com.cogda.multitenant.CustomerAccountService
import grails.plugins.springsecurity.SpringSecurityService
import grails.util.Environment
import grails.util.GrailsUtil
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.springframework.transaction.interceptor.TransactionAspectSupport
import org.springframework.transaction.support.TransactionSynchronizationManager
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.context.support.WebApplicationContextUtils

class BootStrap {
    CustomerAccountService customerAccountService
    SpringSecurityService springSecurityService
    GrailsApplication grailsApplication
    def init = { servletContext ->

        /*
         * Add some convenience currentTransactionStatus methods
         * to the service classes so that we do not have to throw
         * RuntimeExceptions to rollback a transaction.
         * Instead, we can setRollbackOnly by executing the
         * setRollbackOnly method in the transaction upon hitting
         * a case where the continuation of the execution will
         * corrupt the data.
         */
        def ctx = grailsApplication.mainContext
        for (sc in ctx.grailsApplication.serviceClasses) {
            def metaClass = sc.clazz.metaClass
            // returns TransactionStatus
            metaClass.getCurrentTransactionStatus = { ->
                if (!delegate.isTransactionActive()) {
                    return null
                }
                TransactionAspectSupport.currentTransactionStatus()
            }
            // void, throws NoTransactionException
            metaClass.setRollbackOnly = { ->
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()
            }
            // returns boolean
            metaClass.isRollbackOnly = { ->
                if (!delegate.isTransactionActive()) {
                    return false
                }
                delegate.getCurrentTransactionStatus().isRollbackOnly()

            }
            // returns boolean
            metaClass.isTransactionActive = { ->
                TransactionSynchronizationManager.isSynchronizationActive()
            }
        }
        def springContext = WebApplicationContextUtils.getWebApplicationContext(servletContext)
        springContext.getBean("customObjectMarshallers").register()

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

            if(!AccountType.findByCode("Agency")){
                new AccountType(code:"Agency", intCode:0, description: "Agency").save()
            }
            if(!AccountType.findByCode("MGA")){
                new AccountType(code:"MGA", intCode:1, description: "MGA").save()
            }
            if(!AccountType.findByCode("Carrier")){
                new AccountType(code:"Carrier", intCode:2, description: "Carrier").save()
            }
            if(!AccountType.findByCode("Reinsurer")){
                new AccountType(code:"Reinsurer", intCode:3, description: "Reinsurer").save()
            }

            if(!NoteType.findByCode("Visit")){
                new NoteType(code:"Visit", intCode:0, description: "Visit").save()
            }
            if(!NoteType.findByCode("Call")){
                new NoteType(code:"Call", intCode:1, description: "Call").save()
            }
            if(!AccountType.findByCode("Other")){
                new NoteType(code:"Other", intCode:2, description: "Other").save()
            }

            if(!SupportedCountryCode.findByCountryCode("usa")){
                new SupportedCountryCode(countryCode:"usa", countryDescription:"United States").save()
            }
            if(!SupportedCountryCode.findByCountryCode("can")){
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

                CustomerAccount customerAccount = CustomerAccount.findBySubDomain(registration.subDomain)


                customerAccount.withThisTenant {

                    BootStrap.FAKE_CONTACTS.each { List parent ->

                        Contact contact = new Contact()
                        contact.firstName = parent.get(0)
                        contact.lastName = parent.get(1)
                        contact.save()

                    }
                }

            }

            if(!Registration.findBySubDomain("libertymutual")){
                Registration registration
                Registration.withTransaction {
                    registration = new Registration()

                    registration.firstName = "Bill"
                    registration.lastName = "Alexander"
                    registration.username = "administrator"
                    registration.emailAddress = "chris@cogda.com"
                    registration.password = springSecurityService.encodePassword("password")
                    registration.companyName = "Liberty Mutual"
                    registration.companyType = CompanyType.findByCode("Carrier")
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
                    registration.subDomain = "libertymutual"

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
                accountActivationEmailMessage.save() ?: log.error("Error saving AccountActivationEmailMessage errors -> ${accountActivationEmailMessage.errors}")
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
                accountReminderEmailMessage.save() ?: log.error("Error saving accountReminderEmailMessage errors -> ${accountReminderEmailMessage.errors}")
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
                accountReminderEmailMessage.save() ?: log.error("Error saving accountReminderEmailMessage errors -> ${accountReminderEmailMessage.errors}")
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

            if(!SystemEmailMessageTemplate.findByTitle("RESET_PASSWORD_EMAIL")){
                SystemEmailMessageTemplate accountActivationEmailMessage = new SystemEmailMessageTemplate()
                accountActivationEmailMessage.markupLanguage = MarkupLanguage.MARKDOWN
                accountActivationEmailMessage.title = "RESET_PASSWORD_EMAIL"
                accountActivationEmailMessage.description = "The email message that is sent to the User when they are attempting to reset their password."
                accountActivationEmailMessage.subject = "Cogda Reset Forgotten Password"
                accountActivationEmailMessage.fromEmail = "mail@cogda.com"
                accountActivationEmailMessage.body = """
    You are receiving this message because you had completed the Forgot Password form in {appName}.

    Please click the following verification link to reset your {appName} password from within {appName}.

    {resetPasswordUrl}

    Thank you!

    {appName} Team"""
                accountActivationEmailMessage.acceptsParameters = true
                accountActivationEmailMessage.requiredParameterNames = ['appName', 'resetPasswordUrl']
                accountActivationEmailMessage.save() ?: log.error("Error saving accountActivationEmailMessage errors -> ${accountActivationEmailMessage.errors}")
            }
        }
    }
    def destroy = {
    }

    public static final List FAKE_CONTACTS = [
            [
                    "Tarik",
                    "Craig",
                    "P.O. Box 363, 4353 Vestibulum Av.",
                    "Batiscan"
            ],
            [
                    "Blossom",
                    "Church",
                    "7346 Nunc St.",
                    "Schepdaal"
            ],
            [
                    "Eugenia",
                    "Sparks",
                    "P.O. Box 211, 4731 Arcu. Ave",
                    "Scarborough"
            ],
            [
                    "Amena",
                    "Carrillo",
                    "P.O. Box 779, 1443 Enim. Rd.",
                    "Warwick"
            ],
            [
                    "Mary",
                    "Glenn",
                    "Ap #909-8811 Sit Rd.",
                    "Ranst"
            ],
            [
                    "Addison",
                    "Bean",
                    "9984 Inceptos St.",
                    "Vaulx-lez-Chimay"
            ],
            [
                    "Damian",
                    "Sosa",
                    "3398 Ligula. Avenue",
                    "Kimberly"
            ],
            [
                    "Hilel",
                    "Hamilton",
                    "8154 Sapien, Rd.",
                    "Jemappes"
            ],
            [
                    "Anjolie",
                    "Owen",
                    "425-9797 Vestibulum Road",
                    "Gembloux"
            ],
            [
                    "Daquan",
                    "Berger",
                    "377-2305 Tempus Street",
                    "Sint-Amandsberg"
            ],
            [
                    "Kirestin",
                    "Hughes",
                    "194-1045 Ac Ave",
                    "Renfrew"
            ],
            [
                    "Kaitlin",
                    "Jenkins",
                    "328-2819 Est St.",
                    "Brandon"
            ],
            [
                    "Warren",
                    "Cohen",
                    "Ap #845-4539 Netus Street",
                    "Camborne"
            ],
            [
                    "Tara",
                    "Murphy",
                    "3129 Odio. Street",
                    "Biarritz"
            ],
            [
                    "Karyn",
                    "Rhodes",
                    "P.O. Box 871, 3819 Nec Street",
                    "Zwettl-Niederösterreich"
            ],
            [
                    "Griffin",
                    "Singleton",
                    "P.O. Box 765, 6587 Egestas Road",
                    "Heerlen"
            ],
            [
                    "Nadine",
                    "Sheppard",
                    "432-3920 Nec, Avenue",
                    "Comblain-Fairon"
            ],
            [
                    "Dexter",
                    "Barrett",
                    "P.O. Box 608, 620 Class Rd.",
                    "Caplan"
            ],
            [
                    "Frances",
                    "Russo",
                    "Ap #262-5633 Nonummy Rd.",
                    "South Perth"
            ],
            [
                    "Ali",
                    "Barry",
                    "734 Sem Street",
                    "Fort Worth"
            ],
            [
                    "Jemima",
                    "Travis",
                    "889-7974 Amet, Road",
                    "Aklavik"
            ],
            [
                    "Cadman",
                    "Shelton",
                    "3003 Faucibus Rd.",
                    "Apeldoorn"
            ],
            [
                    "Guy",
                    "Merritt",
                    "P.O. Box 238, 3615 Mattis St.",
                    "Weert"
            ],
            [
                    "Channing",
                    "Hardin",
                    "Ap #570-6585 Scelerisque Rd.",
                    "Bad Vilbel"
            ],
            [
                    "Zephania",
                    "Manning",
                    "196 Tempor Road",
                    "Grand-Manil"
            ],
            [
                    "Fitzgerald",
                    "Carter",
                    "8828 Rutrum. St.",
                    "Daknam"
            ],
            [
                    "Jena",
                    "Doyle",
                    "190-1275 Sed Avenue",
                    "Cardigan"
            ],
            [
                    "Remedios",
                    "Ford",
                    "8268 Blandit Rd.",
                    "Finkenstein am Faaker See"
            ],
            [
                    "Kibo",
                    "Howell",
                    "Ap #510-9605 Libero St.",
                    "Karlsruhe"
            ],
            [
                    "May",
                    "Fernandez",
                    "577-5790 Velit Ave",
                    "L"
            ],
            [
                    "Abel",
                    "Schultz",
                    "766-957 Pede. Avenue",
                    "Hard"
            ],
            [
                    "Kendall",
                    "Hardy",
                    "120-1386 Auctor. St.",
                    "Kircudbright"
            ],
            [
                    "Jena",
                    "Cobb",
                    "Ap #904-6523 Felis, St.",
                    "Termes"
            ],
            [
                    "Cedric",
                    "Harris",
                    "5673 Amet, Rd.",
                    "Hollogne-sur-Geer"
            ],
            [
                    "Ursula",
                    "Harmon",
                    "Ap #607-9807 Sed Avenue",
                    "Naumburg"
            ],
            [
                    "Bert",
                    "Guerra",
                    "Ap #903-3101 Sit St.",
                    "Cambridge Bay"
            ],
            [
                    "Griffin",
                    "Juarez",
                    "P.O. Box 370, 807 A Road",
                    "Sundrie"
            ],
            [
                    "Jeremy",
                    "Hyde",
                    "973-1756 Orci Avenue",
                    "Gistel"
            ],
            [
                    "Grace",
                    "Workman",
                    "Ap #268-6749 In, Avenue",
                    "Ingelheim"
            ],
            [
                    "Kitra",
                    "Long",
                    "219-6801 A St.",
                    "Sherborne"
            ],
            [
                    "Macaulay",
                    "Boone",
                    "365-1710 Fermentum Ave",
                    "Melrose"
            ],
            [
                    "Portia",
                    "Mathis",
                    "Ap #111-4651 Pellentesque St.",
                    "Millport"
            ],
            [
                    "Abel",
                    "Nichols",
                    "5767 Amet Rd.",
                    "Bal"
            ],
            [
                    "Hop",
                    "Buckner",
                    "Ap #258-2976 Sapien. Rd.",
                    "Massemen"
            ],
            [
                    "Regina",
                    "Hahn",
                    "8690 Tellus Rd.",
                    "Loksbergen"
            ],
            [
                    "Macy",
                    "Miles",
                    "8140 Imperdiet Rd.",
                    "East Gwillimbury"
            ],
            [
                    "Dalton",
                    "Pearson",
                    "Ap #135-7573 Suspendisse Avenue",
                    "Fallais"
            ],
            [
                    "Declan",
                    "Chapman",
                    "8508 Sit Street",
                    "Arviat"
            ],
            [
                    "Ashely",
                    "Cook",
                    "8077 Ut Road",
                    "Moignelee"
            ],
            [
                    "Mari",
                    "Kline",
                    "2965 Pharetra, Street",
                    "Krems an der Donau"
            ],
            [
                    "Leo",
                    "Richardson",
                    "P.O. Box 256, 9172 Morbi Street",
                    "Messancy"
            ],
            [
                    "Vance",
                    "Bright",
                    "855-8739 Eleifend St.",
                    "Thuillies"
            ],
            [
                    "Fallon",
                    "Ward",
                    "301-3935 Eu Rd.",
                    "Belfort"
            ],
            [
                    "Slade",
                    "Tate",
                    "Ap #732-1778 Leo. St.",
                    "Bergen Mons"
            ],
            [
                    "Jeanette",
                    "Hopper",
                    "Ap #530-184 Ipsum Ave",
                    "Salon-de-Provence"
            ],
            [
                    "Leah",
                    "Lyons",
                    "559-2454 At, Avenue",
                    "Diepenbeek"
            ],
            [
                    "Jada",
                    "Orr",
                    "6764 Adipiscing. Av.",
                    "Colwood"
            ],
            [
                    "Rafael",
                    "Martinez",
                    "P.O. Box 590, 846 Erat Avenue",
                    "Windsor"
            ],
            [
                    "Aiko",
                    "Lang",
                    "P.O. Box 582, 7467 Phasellus Ave",
                    "Henstedt-Ulzburg"
            ],
            [
                    "Brenda",
                    "Flowers",
                    "Ap #845-3471 Eget, St.",
                    "Criciúma"
            ],
            [
                    "Eagan",
                    "Baker",
                    "3554 Cum St.",
                    "Lens"
            ],
            [
                    "Octavia",
                    "Valentine",
                    "P.O. Box 943, 7038 Est. Rd.",
                    "Recklinghausen"
            ],
            [
                    "Whitney",
                    "Dalton",
                    "319-4386 Ut Ave",
                    "Götzis"
            ],
            [
                    "Graiden",
                    "Pittman",
                    "259-8074 Lacus. Rd.",
                    "Southend"
            ],
            [
                    "Jamal",
                    "Sharpe",
                    "8307 Lobortis Rd.",
                    "Loupoigne"
            ],
            [
                    "Joy",
                    "Pugh",
                    "4643 Nascetur St.",
                    "Margate"
            ],
            [
                    "Kiayada",
                    "Herman",
                    "P.O. Box 457, 5040 Nunc Street",
                    "Boulogne-Billancourt"
            ],
            [
                    "Imogene",
                    "Soto",
                    "708-9574 Ad Av.",
                    "Tubeke Tubize"
            ],
            [
                    "Jessamine",
                    "Herring",
                    "7488 Mi. Avenue",
                    "Newquay"
            ],
            [
                    "Brianna",
                    "Dominguez",
                    "488-1143 Placerat. Rd.",
                    "Belgrade"
            ],
            [
                    "Yoko",
                    "Lane",
                    "Ap #672-7352 Eu Street",
                    "Brussegem"
            ],
            [
                    "Aristotle",
                    "Mcfarland",
                    "998 Vehicula Av.",
                    "Haddington"
            ],
            [
                    "Anastasia",
                    "Matthews",
                    "Ap #291-7677 Mauris St.",
                    "Otegem"
            ],
            [
                    "Galvin",
                    "Allen",
                    "358-989 Nullam Avenue",
                    "Amiens"
            ],
            [
                    "Dieter",
                    "Mcmahon",
                    "P.O. Box 502, 2402 Mauris Rd.",
                    "Jaboatão dos Guararapes"
            ],
            [
                    "Kane",
                    "Schwartz",
                    "Ap #894-1674 In Road",
                    "Clydebank"
            ],
            [
                    "Jade",
                    "Haley",
                    "Ap #624-3149 Eu Avenue",
                    "Hilo"
            ],
            [
                    "Lester",
                    "Chaney",
                    "102-5502 Et Avenue",
                    "Nerem"
            ],
            [
                    "Lucius",
                    "Graham",
                    "4563 Non Av.",
                    "Rivi"
            ],
            [
                    "Fay",
                    "Farley",
                    "P.O. Box 900, 8340 Integer Ave",
                    "Portree"
            ],
            [
                    "Gavin",
                    "Crane",
                    "2304 Pede. Street",
                    "Anklam"
            ],
            [
                    "Olympia",
                    "Santana",
                    "2616 Lacus. Rd.",
                    "Wismar"
            ],
            [
                    "Fulton",
                    "Wilkins",
                    "P.O. Box 178, 4998 Placerat, Ave",
                    "Stralsund"
            ],
            [
                    "Erasmus",
                    "Sanchez",
                    "948-2050 Ut Street",
                    "Kortrijk"
            ],
            [
                    "Quemby",
                    "Long",
                    "3318 Fringilla Road",
                    "Friedrichsdorf"
            ],
            [
                    "Kamal",
                    "Beach",
                    "Ap #308-5714 Felis Street",
                    "Zwijnaarde"
            ],
            [
                    "Lacota",
                    "Lloyd",
                    "Ap #706-7542 Et, Street",
                    "Ilhéus"
            ],
            [
                    "Yasir",
                    "Marquez",
                    "P.O. Box 310, 4851 At Street",
                    "Contagem"
            ],
            [
                    "Zorita",
                    "Atkins",
                    "2230 Libero Road",
                    "Bludenz"
            ],
            [
                    "Hilda",
                    "Emerson",
                    "7100 Molestie Ave",
                    "Mussy-la-Ville"
            ],
            [
                    "Elton",
                    "Estrada",
                    "Ap #597-5588 Egestas. Av.",
                    "Buzenol"
            ],
            [
                    "Urielle",
                    "Leon",
                    "8929 Quam. St.",
                    "Flin Flon"
            ],
            [
                    "Elmo",
                    "Duke",
                    "P.O. Box 865, 2146 Amet, Street",
                    "Ribeirão Preto"
            ],
            [
                    "Sydney",
                    "Mcneil",
                    "629-742 Vel, Road",
                    "F"
            ],
            [
                    "Sydnee",
                    "Giles",
                    "9851 Nunc Ave",
                    "Kortrijk"
            ],
            [
                    "Callum",
                    "Turner",
                    "P.O. Box 693, 8875 Morbi Avenue",
                    "Saint-Quentin"
            ],
            [
                    "Wesley",
                    "Cardenas",
                    "884-4779 Erat. Street",
                    "Bottrop"
            ],
            [
                    "Phoebe",
                    "Bonner",
                    "879-1852 Duis St.",
                    "Belgrave"
            ],
            [
                    "Penelope",
                    "Lancaster",
                    "491-8292 Cras Rd.",
                    "Whitehorse"
            ],
            [
                    "Gavin",
                    "Nolan",
                    "472-523 Enim. Rd.",
                    "Saint-Mard"
            ]
    ]
}
