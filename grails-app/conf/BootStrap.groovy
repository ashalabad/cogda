import au.com.bytecode.opencsv.CSVReader
import com.amazonaws.services.s3.model.GetObjectRequest
import com.cogda.common.GenderEnum
import com.cogda.common.MarkupLanguage
import com.cogda.common.RegistrationStatus
import com.cogda.domain.Address
import com.cogda.domain.Contact
import com.cogda.domain.ContactAddress
import com.cogda.domain.ContactEmailAddress
import com.cogda.domain.ContactPhoneNumber
import com.cogda.domain.UserProfile
import com.cogda.domain.UserProfileEmailAddress
import com.cogda.domain.admin.AccountType
import com.cogda.domain.admin.BusinessType
import com.cogda.domain.admin.CompanyType
import com.cogda.domain.admin.NaicsCode
import com.cogda.domain.admin.LineOfBusiness
import com.cogda.domain.admin.LineOfBusinessCategory
import com.cogda.domain.admin.NoteType
import com.cogda.domain.admin.SicCode
import com.cogda.domain.admin.SicNaicsCodeCrosswalk
import com.cogda.domain.admin.SystemEmailMessageTemplate
import com.cogda.domain.onboarding.Registration
import com.cogda.domain.security.Role
import com.cogda.domain.security.User
import com.cogda.domain.security.UserRole
import com.cogda.multitenant.Account
import com.cogda.multitenant.AccountAddress
import com.cogda.multitenant.AccountAddressType
import com.cogda.multitenant.AccountCompanyOwner
import com.cogda.multitenant.AccountContact
import com.cogda.multitenant.AccountContactAddress
import com.cogda.multitenant.AccountContactEmailAddress
import com.cogda.multitenant.AccountContactLink
import com.cogda.multitenant.AccountContactPhoneNumber
import com.cogda.multitenant.AccountNote
import com.cogda.multitenant.Company
import com.cogda.multitenant.CustomerAccount
import com.cogda.domain.admin.SupportedCountryCode
import com.cogda.multitenant.CustomerAccountService
import com.cogda.domain.Note
import com.cogda.util.DataPopulatorService
import grails.plugin.awssdk.AmazonWebService
import grails.plugins.springsecurity.SpringSecurityService
import grails.util.Environment
import grails.util.GrailsUtil
import org.apache.commons.logging.LogFactory
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.springframework.transaction.interceptor.TransactionAspectSupport
import org.springframework.transaction.support.TransactionSynchronizationManager
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.context.support.WebApplicationContextUtils

import java.util.regex.Matcher
import java.util.regex.Pattern

class BootStrap {
    private static final log = LogFactory.getLog(this)

    CustomerAccountService customerAccountService
    SpringSecurityService springSecurityService
    GrailsApplication grailsApplication
    AmazonWebService amazonWebService
    DataPopulatorService dataPopulatorService
    def messageSource

    def init = { servletContext ->
        
        for (dc in grailsApplication.domainClasses) {
            dc.metaClass.getErrorStrings = { Locale locale = Locale.getDefault() ->
                def stringsByField = [:].withDefault { [] }
                for (fieldErrors in delegate.errors) {
                    for (error in fieldErrors.allErrors) {
                        String message = messageSource.getMessage(error, locale)
                        stringsByField[error.field] << message
                    }
                }
                stringsByField
            }
        }


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
            metaClass.getCurrentTransactionStatus = {->
                if (!delegate.isTransactionActive()) {
                    return null
                }
                TransactionAspectSupport.currentTransactionStatus()
            }
            // void, throws NoTransactionException
            metaClass.setRollbackOnly = {->
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()
            }
            // returns boolean
            metaClass.isRollbackOnly = {->
                if (!delegate.isTransactionActive()) {
                    return false
                }
                delegate.getCurrentTransactionStatus().isRollbackOnly()

            }
            // returns boolean
            metaClass.isTransactionActive = {->
                TransactionSynchronizationManager.isSynchronizationActive()
            }
        }
        def springContext = WebApplicationContextUtils.getWebApplicationContext(servletContext)
        springContext.getBean("customObjectMarshallers").register()

        if (Environment.current == Environment.TEST) {
            createCompanyTypes()
            if(!CustomerAccount.findBySubDomain("CHOCOTACO")){
                CustomerAccount customerAccount = new CustomerAccount()
                customerAccount.subDomain = "CHOCOTACO"
                assert customerAccount.validate()
                assert customerAccount.save()
                assert CustomerAccount.findBySubDomain("CHOCOTACO")
            }
        }

        if (Environment.current != Environment.TEST) {

            // create admin domain classes
            if(NoteType.count() == 0){
                createNoteTypes()
            }
            if(CompanyType.count() == 0){
                createCompanyTypes()
            }
            if(AccountType.count() == 0){
                createAccountTypes()
            }
            if(AccountAddressType.count() == 0){
                createAccountAddressTypes()
            }
            if(SupportedCountryCode.count() == 0){
                createSupportedCountryCodes()
            }
            if(LineOfBusiness.count() == 0 && LineOfBusinessCategory.count() == 0){
                dataPopulatorService.createLinesOfBusiness()
            }
            if(BusinessType.count() == 0){
                dataPopulatorService.createBusinessTypes()
            }
            if(NaicsCode.count() == 0){
                importNaicsCodes()
            }
            if(SicCode.count() == 0){
                importSicCodes()
            }
            if(SicNaicsCodeCrosswalk.count() == 0){
                buildSicNaicsCrosswalk()
            }

            if(!Registration.findBySubDomain("rais")){
                createRennaissanceRegistration()
                def raisRegistration = Registration.findBySubDomain("rais")
                if(raisRegistration){
                    customerAccountService.onboardCustomerAccount(raisRegistration)
                    createRennaissanceDummyData(raisRegistration)
                    createRennaissanceAccountDummyData(raisRegistration)
                    createRennaissanceUserDummyData(raisRegistration)
                    createRennaissanceUserProfileDummyData(raisRegistration)
                }
            }

            if(!Registration.findBySubDomain("libertymutual")){
                createLibertyMutualRegistration()
                def libertyRegistration = Registration.findBySubDomain("libertymutual")
                if(libertyRegistration){
                    customerAccountService.onboardCustomerAccount(libertyRegistration)
                }
            }

            if(!Registration.findBySubDomain("cochraneandporter")){
                createCochranePorterRegistration()
                def cochraneRegistration = Registration.findBySubDomain("cochraneandporter")
                if(cochraneRegistration){
                    customerAccountService.onboardCustomerAccount(cochraneRegistration)
                }
            }

            if(!Registration.findBySubDomain("gaudreaugroup")){
                createGaudreauGroupRegistration()
                def gaudreauRegistration = Registration.findBySubDomain("gaudreaugroup")
                if(gaudreauRegistration){
                    customerAccountService.onboardCustomerAccount(gaudreauRegistration)
                }
            }
            //Create the email templates
            createInitialAccountActivationEmail()
            createReminderAccountActivationEmail()
            createTimeoutActivationEmail()
            createNewAccountWelcomeEmail()
            createVerifiedSuccessfullyEmail()
            createResetPasswordEmail()
            createUserInvitationEmail()


        }
    }
    def destroy = {
    }

    def createRennaissanceUserDummyData(Registration registration){
        CustomerAccount customerAccount = CustomerAccount.findBySubDomain(registration.subDomain)
        customerAccount.withThisTenant {
            def role = Role.findByAuthority(CustomerAccountService.ROLE_USER)
            110.times { Integer i ->
                String username =  "rais" + i
                User user = new User()
                user.username = username
                user.password = "password"
                user.enabled = true
                user.accountExpired = false
                assert user.validate()
                assert user.save()
                UserRole.create(user, role)
            }
        }
    }

    def createRennaissanceUserProfileDummyData(Registration registration){
        CustomerAccount customerAccount = CustomerAccount.findBySubDomain(registration.subDomain)
        customerAccount.withThisTenant {
            Company company = Company.retrieveRootCompany()
            def role = Role.findByAuthority(CustomerAccountService.ROLE_USER)
            110.times { Integer i ->
                String username =  "rais" + i
                User user = User.findByUsername(username)
                assert user, "User was not found with username $username"

                UserProfile userProfile = new UserProfile()
                userProfile.user = user
                userProfile.company = company
                userProfile.published = true
                userProfile.firstName = "Rais First Name " + i
                userProfile.lastName = "Rais Last Name " + i
                userProfile.aboutDesc = "About Description " + i
                userProfile.associationsDesc = "Associations Description " + i
                userProfile.businessSpecialtiesDesc = "Business Specialties " + i

                UserProfileEmailAddress userProfileEmailAddress = new UserProfileEmailAddress()
                userProfileEmailAddress.published = true
                userProfileEmailAddress.emailAddress = "${username}@cogda.com"
                userProfileEmailAddress.primaryEmailAddress = true

                assert userProfile.validate()
                assert userProfile.save()

                userProfileEmailAddress.userProfile = userProfile
                assert userProfileEmailAddress.validate()
                assert userProfileEmailAddress.save()
                userProfile.addToUserProfileEmailAddresses(userProfileEmailAddress)
            }
        }
    }

    def createRennaissanceAccountDummyData(Registration registration){
        CustomerAccount customerAccount = CustomerAccount.findBySubDomain(registration.subDomain)
        InputStream is = amazonWebService.s3.getObject(new GetObjectRequest(grailsApplication.config.grails.plugin.awssdk.default.bucket, "testingfiles/AccountData.csv")).getObjectContent()
        CSVReader reader = new CSVReader(new InputStreamReader(is))
        String[] nextLine;
        Integer count = 0;
        Company company = Company.first()
        while ((nextLine = reader.readNext()) != null) {
            if(nextLine[0]?.trim() != "accountName"){
                customerAccount.withThisTenant {

                    Account.withTransaction {
                        Account testAccount = new Account()
                        testAccount.accountName= nextLine[0]?.trim()
                        testAccount.accountCode= nextLine[1]?.trim()
                        testAccount.accountType= AccountType.findByCode(nextLine[2]?.trim())
                        testAccount.isMarket = Boolean.TRUE;
                        testAccount.active = Boolean.TRUE;
                        testAccount.accountCode= nextLine[1]?.trim()
                        if (testAccount.hasErrors() || !testAccount.validate() ) {
                            log.error("Could not import testAccount with AccountCode: ${testAccount.accountCode}  ${testAccount.errors}")
                        }
                        else{
                            testAccount.save()

                            def testAccountContact = new AccountContact(
                                    firstName: nextLine[3]?.trim(),
                                    middleName: nextLine[4]?.trim(),
                                    lastName: nextLine[5]?.trim()
                            )
                            if (testAccountContact.hasErrors() || !testAccountContact.validate() ) {
                                log.error("Could not import testAccountContact ${testAccountContact.firstName} ${testAccountContact.lastName}  ${testAccountContact.errors}")
                            }
                            else
                            {
                                testAccountContact.save()

                                def testAccountPhoneNumber = new AccountContactPhoneNumber(accountContact: testAccountContact,phoneNumber:nextLine[7]?.trim() ,primaryPhoneNumber: true)
                                if (testAccountPhoneNumber.hasErrors() || !testAccountPhoneNumber.validate() ) {
                                    log.error("Could not import testAccountPhoneNumber ${testAccountPhoneNumber.phoneNumber} ${testAccountPhoneNumber.errors}")
                                }
                                else
                                    testAccountPhoneNumber.save()

                                def testAccountEmail = new AccountContactEmailAddress(accountContact: testAccountContact,emailAddress:nextLine[6]?.trim()?.toString()?.replace(';',''),primaryEmailAddress: true).save()
                                if (testAccountEmail.hasErrors() || !testAccountEmail.validate() ) {
                                    log.error("Could not import testAccountEmail ${testAccountEmail.emailAddress} ${testAccountEmail.errors}")
                                }
                                else
                                    testAccountEmail.save()

                                AccountContactAddress accountContactAddress = new AccountContactAddress()
                                accountContactAddress.address = new Address()
                                accountContactAddress.address.addressOne = "address line 1"
                                accountContactAddress.address.addressTwo = "address line 2"
                                accountContactAddress.address.addressThree = "address line 3"
                                accountContactAddress.address.city = "Athens"
                                accountContactAddress.address.state = "Georgia"
                                accountContactAddress.address.zipcode = "30602"
                                accountContactAddress.primaryAddress = Boolean.TRUE
                                testAccountContact.addToAccountContactAddresses(accountContactAddress)
                                accountContactAddress.save()
                                if(accountContactAddress.hasErrors()){
                                    println "accountContactAddress ${accountContactAddress} failed to save ${accountContactAddress.errors}"
                                }

                                testAccountContact.addToAccountContactEmailAddresses(testAccountEmail)
                                testAccountContact.addToAccountContactPhoneNumbers(testAccountPhoneNumber)
                                testAccountContact.save()

                                AccountContactLink.create testAccount, testAccountContact, true, true
                                testAccount.save()
                            }


                            AccountAddress accountAddress = new AccountAddress()
                            accountAddress.address = new Address()
                            accountAddress.address.addressOne = "address line 1"
                            accountAddress.address.addressTwo = "address line 2"
                            accountAddress.address.addressThree = "address line 3"
                            accountAddress.accountAddressType = AccountAddressType.findByCode("Mailing")
                            accountAddress.address.city = "Athens"
                            accountAddress.address.state = "Georgia"
                            accountAddress.address.zipcode = "30602"
                            accountAddress.primaryAddress = Boolean.TRUE
                            testAccount.addToAccountAddresses(accountAddress)
                            accountAddress.save()

                            accountAddress = new AccountAddress()
                            accountAddress.address = new Address()
                            accountAddress.address.addressOne = "address line 1"
                            accountAddress.address.addressTwo = "address line 2"
                            accountAddress.address.addressThree = "address line 3"
                            accountAddress.accountAddressType = AccountAddressType.findByCode("Billing")
                            accountAddress.address.city = "Athens"
                            accountAddress.address.state = "Georgia"
                            accountAddress.address.zipcode = "30602"
                            accountAddress.primaryAddress = Boolean.FALSE
                            testAccount.addToAccountAddresses(accountAddress)
                            accountAddress.save()

                            if(accountAddress.hasErrors()){
                                println "accountAddress ${accountAddress} failed to save ${accountAddress.errors}"
                            }
                            def noteText = """Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer volutpat pretium dictum. Praesent consequat sollicitudin est at fringilla. Morbi lacinia, mauris a gravida imperdiet, mi nulla scelerisque diam, eu rutrum massa neque non mauris. Nulla est felis, mattis non hendrerit vitae, accumsan id metus. Fusce ac leo nec velit venenatis tincidunt. In in ante id sapien ultrices mollis. Vivamus consequat sit amet odio vel ullamcorper. Maecenas sit amet mollis justo. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi quis bibendum nisl. Nunc aliquet accumsan nulla vel porttitor. Donec vestibulum mattis odio, a auctor nunc pulvinar dignissim. Aenean molestie dignissim tempus. Aenean nec tellus urna. Mauris blandit auctor eros, sit amet luctus diam blandit sit amet. Suspendisse consequat consectetur consequat.
                                                Nullam vitae neque commodo, mattis purus non, varius diam. Nam ac sodales diam. Curabitur augue ligula, auctor eu vestibulum ut, lacinia non lacus. Etiam sodales tincidunt malesuada. Nunc ultricies consectetur congue. Sed ornare elit tellus, sed vulputate velit tincidunt nec. Suspendisse pretium nisi convallis risus convallis, quis faucibus nulla ornare. Etiam eget mauris nec odio posuere pellentesque. Curabitur nisl neque, elementum quis ipsum ac, scelerisque lobortis orci. Sed porta orci arcu, id mollis tellus ullamcorper ut. In egestas elit sit amet consequat mollis."""

                            def tempNote = new Note(notes:noteText).save()
                            def tempAccountNote = new AccountNote(note: tempNote,account: testAccount,noteType: NoteType.get(1)).save()

                            log.debug("Importing testAccount  ${testAccount}")
                            AccountCompanyOwner.create testAccount, company, true
                        }
                    }
                }
            }
            count ++
        }
    }


    def createRennaissanceDummyData(Registration registration){
        CustomerAccount customerAccount = CustomerAccount.findBySubDomain(registration.subDomain)

        List<String> companyNames = ["Cogda Solutions, LLC", "Sombra Technologies", "Rennaissance Alliance", "Hartford", "AIG", "QBE", "HBA", "ABC", "123", "456"]
        List<String> jobTitles = ["Thane", "King", "Queen", "Prince", "Princess", "Queen Mother", "Regent", "Prior", "Dean", "Bishop"]
        InputStream is = amazonWebService.s3.getObject(new GetObjectRequest(grailsApplication.config.grails.plugin.awssdk.default.bucket, "testingfiles/ContactTestDataBigFile.csv")).getObjectContent()
        CSVReader reader = new CSVReader(new InputStreamReader(is))
        String[] nextLine;
        Integer count = 0;
        while ((nextLine = reader.readNext()) != null) {
            // nextLine[] is an array of values from the line
            int randomIndex = (int)(Math.random()*10)
            Contact contact = new Contact(
                    companyName: companyNames.get(randomIndex),
                    firstName: nextLine[0]?.trim(),
                    middleName: nextLine[1]?.trim(),
                    lastName: nextLine[2]?.trim(),
                    dateOfBirth: Date.parse("MM/dd/yyyy", nextLine[3]?.trim()),
                    jobTitle: jobTitles.get(randomIndex),
                    gender: (nextLine[4] == "M" ? GenderEnum.MALE : GenderEnum.FEMALE)
            )
            customerAccount.withThisTenant {

                Contact.withTransaction {
                    if(contact.save()){

                        ContactEmailAddress contactEmailAddress = new ContactEmailAddress()
                        contactEmailAddress.emailAddress = sanitizeEmailAddress(nextLine[5]?.trim())
                        contactEmailAddress.primaryEmailAddress = Boolean.TRUE
                        contactEmailAddress.contact = contact
                        contact.addToContactEmailAddresses(contactEmailAddress)
                        contactEmailAddress.save()

                        if(contactEmailAddress.hasErrors()){
                            println "contactEmailAddress ${contactEmailAddress} failed to save ${contactEmailAddress.errors}"
                        }

                        ContactEmailAddress contactEmailAddressTwo = new ContactEmailAddress()
                        contactEmailAddressTwo.emailAddress = sanitizeEmailAddress(nextLine[6]?.trim())
                        contactEmailAddressTwo.contact = contact
                        contact.addToContactEmailAddresses(contactEmailAddressTwo)
                        contactEmailAddressTwo.save()

                        if(contactEmailAddressTwo.hasErrors()){
                            println "contactEmailAddressTwo ${contactEmailAddressTwo} failed to save ${contactEmailAddressTwo.errors}"
                        }

                        ContactPhoneNumber contactPhoneNumber = new ContactPhoneNumber()
                        contactPhoneNumber.phoneNumber = nextLine[7]?.trim()
                        contactPhoneNumber.primaryPhoneNumber = Boolean.TRUE
                        contact.addToContactPhoneNumbers(contactPhoneNumber)
                        contactPhoneNumber.save()

                        if(contactPhoneNumber.hasErrors()){
                            println "contactPhoneNumber ${contactPhoneNumber} failed to save ${contactPhoneNumber.errors}"
                        }

                        ContactPhoneNumber contactPhoneNumberTwo = new ContactPhoneNumber()
                        contactPhoneNumberTwo.phoneNumber = nextLine[8]?.trim()
                        contact.addToContactPhoneNumbers(contactPhoneNumberTwo)
                        contactPhoneNumberTwo.save()

                        if(contactPhoneNumberTwo.hasErrors()){
                            println "contactPhoneNumberTwo ${contactPhoneNumberTwo} failed to save ${contactPhoneNumberTwo.errors}"
                        }

                        ContactAddress contactAddress = new ContactAddress()
                        contactAddress.address = new Address()
                        contactAddress.address.addressOne = nextLine[9]?.trim()
                        contactAddress.address.addressTwo = nextLine[10]?.trim()
                        contactAddress.address.addressThree = nextLine[11]?.trim()
                        contactAddress.addressType = "WORK"
                        contactAddress.address.city = nextLine[12]?.trim()
                        contactAddress.address.state = nextLine[13]?.trim()
                        contactAddress.address.zipcode = nextLine[14]?.trim()
                        contactAddress.primaryAddress = Boolean.TRUE
                        contact.addToContactAddresses(contactAddress)
                        contactAddress.save()

                        if(contactAddress.hasErrors()){
                            println "contactAddress ${contactAddress} failed to save ${contactAddress.errors}"
                        }

                    }else{
                        println "Contact $contact was NOT saved successfully ${contact.errors}"
                    }
                }
            }
            count++
        }
    }

    def createSupportedCountryCodes(){
        SupportedCountryCode.withTransaction {
            if (!SupportedCountryCode.findByCountryCode("usa")) {
                new SupportedCountryCode(countryCode: "usa", countryDescription: "United States").save()
            }
            if (!SupportedCountryCode.findByCountryCode("can")) {
                new SupportedCountryCode(countryCode: "can", countryDescription: "Canada").save()
            }
        }
//        SupportedCountryCode bra = new SupportedCountryCode(countryCode:"bra", countryDescription:"Brazil").save()
    }

    def createNoteTypes(){
        NoteType.withTransaction {
            if (!NoteType.findByCode("Visit")) {
                new NoteType(code: "Visit", intCode: 0, description: "Visit").save()
            }
            if (!NoteType.findByCode("Call")) {
                new NoteType(code: "Call", intCode: 1, description: "Call").save()
            }
            if (!AccountType.findByCode("Other")) {
                new NoteType(code: "Other", intCode: 2, description: "Other").save()
            }
        }
    }

    def createAccountTypes(){
        AccountType.withTransaction {
            if (!AccountType.findByCode("Agency")) {
                new AccountType(code: "Agency", intCode: 0, description: "Agency").save()
            }
            if (!AccountType.findByCode("MGA")) {
                new AccountType(code: "MGA", intCode: 1, description: "MGA").save()
            }
            if (!AccountType.findByCode("Carrier")) {
                new AccountType(code: "Carrier", intCode: 2, description: "Carrier").save()
            }
            if (!AccountType.findByCode("Reinsurer")) {
                new AccountType(code: "Reinsurer", intCode: 3, description: "Reinsurer").save()
            }
        }
    }

    def createAccountAddressTypes(){
        AccountAddressType.withTransaction {
            if (!AccountAddressType.findByCode("Mailing")) {
                new AccountAddressType(code: "Mailing", description: "Mailing").save()
            }
            if (!AccountAddressType.findByCode("Billing")) {
                new AccountAddressType(code: "Billing", description: "Billing").save()
            }
        }
    }



    def createRennaissanceRegistration(){
        if (!Registration.findBySubDomain("rais")) {

            Registration.withTransaction {

                Registration registration = new Registration()

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

                registration.validate()

                if(!registration.hasErrors() && registration.save()){
                    println "Registration ${registration.subDomain} save succeeded!"
                }else{
                    registration.errors.each {
                        println it
                    }
                }
            }
        }
    }

    def createLibertyMutualRegistration(){

        if (!Registration.findBySubDomain("libertymutual")) {

            Registration.withTransaction {

                Registration registration = new Registration()

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

                registration.validate()

                if(!registration.hasErrors() && registration.save()){
                    println "Registration ${registration.subDomain} save succeeded!"
                }else{
                    println "Registration save failed: ${registration.errors}"
                    registration.errors.each {
                        println it
                    }
                }
            }
        }
    }

    def createCochranePorterRegistration(){

        if (!Registration.findBySubDomain("cochraneandporter")) {

            Registration.withTransaction {

                Registration registration = new Registration()

                registration.firstName = "Christian"
                registration.lastName = "Jaynes"
                registration.username = "cochrane"
                registration.emailAddress = "cjaynes@cogda.com"
                registration.password = springSecurityService.encodePassword("password")
                registration.companyName = "Cochrane & Porter Insurance Agency. Inc."
                registration.companyType = CompanyType.findByCode("Agency/Retailer")
                registration.existingCompany = null
                registration.companyTypeOther = null
                registration.phoneNumber = "800-514-2667"
                registration.streetAddressOne = "981 Worcester Street"
                registration.streetAddressTwo = "Suite 200"
                registration.streetAddressThree = ""
                registration.city = "Wellesley"
                registration.state = "MA"
                registration.zipcode = "02842"
                registration.county = "NORFOLK USA"
                registration.registrationStatus = RegistrationStatus.APPROVED
                registration.subDomain = "cochraneandporter"

                registration.validate()

                if(!registration.hasErrors() && registration.save()){
                    println "Registration ${registration.subDomain} save succeeded!"
                }else{
                    println "Registration save failed: ${registration.errors}"
                    registration.errors.each {
                        println it
                    }
                }
            }
        }
    }

    // createGaudreauGroupRegistration
    def createGaudreauGroupRegistration(){

        if (!Registration.findBySubDomain("gaudreaugroup")) {

            Registration.withTransaction {

                Registration registration = new Registration()

                registration.firstName = "Christian"
                registration.lastName = "Jaynes"
                registration.username = "gaudreau"
                registration.emailAddress = "cjaynes@cogda.com"
                registration.password = springSecurityService.encodePassword("password")
                registration.companyName = "The Gaudreau Group"
                registration.companyType = CompanyType.findByCode("Agency/Retailer")
                registration.existingCompany = null
                registration.companyTypeOther = null
                registration.phoneNumber = "413.543.3534"
                registration.streetAddressOne = "1984 Boston Road"
                registration.streetAddressTwo = ""
                registration.streetAddressThree = ""
                registration.city = "Willbraham"
                registration.state = "MA"
                registration.zipcode = "01095"
                registration.county = ""
                registration.registrationStatus = RegistrationStatus.APPROVED
                registration.subDomain = "gaudreaugroup"

                registration.validate()

                if(!registration.hasErrors() && registration.save()){
                    println "Registration ${registration.subDomain} save succeeded!"
                }else{
                    println "Registration save failed: ${registration.errors}"
                    registration.errors.each {
                        println it
                    }
                }
            }
        }
    }

    def createInitialAccountActivationEmail(){
        if (!SystemEmailMessageTemplate.findByTitle("INITIAL_ACCOUNT_ACTIVATION_EMAIL")) {
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
    }

    def createReminderAccountActivationEmail(){
        if (!SystemEmailMessageTemplate.findByTitle("REMINDER_ACCOUNT_ACTIVATION_EMAIL")) {
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
    }

    def createTimeoutActivationEmail(){
        if (!SystemEmailMessageTemplate.findByTitle("TIMEOUT_ACCOUNT_ACTIVATION_EMAIL")) {
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
    }

    def createNewAccountWelcomeEmail(){
        if (!SystemEmailMessageTemplate.findByTitle("NEW_ACCOUNT_WELCOME_EMAIL")) {
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

    def createUserInvitationEmail(){
        if (!SystemEmailMessageTemplate.findByTitle("USER_INVITATION_EMAIL")) {
            SystemEmailMessageTemplate accountWelcomeEmailMessage = new SystemEmailMessageTemplate()
            accountWelcomeEmailMessage.markupLanguage = MarkupLanguage.MARKDOWN
            accountWelcomeEmailMessage.title = "USER_INVITATION_EMAIL"
            accountWelcomeEmailMessage.description = "The email message that is sent to the User when the admin invites them into the system."
            accountWelcomeEmailMessage.subject = "Join Cogda - User Invitation"
            accountWelcomeEmailMessage.fromEmail = "mail@cogda.com"
            accountWelcomeEmailMessage.body = """
{firstName},

You have been invited to join {appName}!  Use the following link to create your user account:

{newUserCreationUrl}

Thank you!

{appName} Team"""
            accountWelcomeEmailMessage.acceptsParameters = true
            accountWelcomeEmailMessage.requiredParameterNames = ['appName', 'newUserCreationUrl', 'firstName']
            accountWelcomeEmailMessage.save()
        }
    }

    def createVerifiedSuccessfullyEmail(){
        if (!SystemEmailMessageTemplate.findByTitle("VERIFIED_SUCCESSFULLY_EMAIL")) {
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

    def createResetPasswordEmail(){
        if (!SystemEmailMessageTemplate.findByTitle("RESET_PASSWORD_EMAIL")) {
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

    /**
     * The only reason this is here is because the generated data
     * may have errors in the email address that make the email address
     * fail during the validation process.
     * This just takes the email adresses provided and makes sure that the
     * only characters used in the email address are a-zA-Z0-9@.
     * @param emailAddress
     * @return String
     */
    def String sanitizeEmailAddress(String emailAddress){

        Pattern pt = Pattern.compile("[^a-zA-Z0-9@.]")
        Matcher match= pt.matcher(emailAddress)
        while(match.find())
        {
            String s= match.group()
            emailAddress=emailAddress.replaceAll("\\"+s, "")
        }

        return emailAddress
    }


    def importNaicsCodes() {
        InputStream is = amazonWebService.s3.getObject(new GetObjectRequest(grailsApplication.config.grails.plugin.awssdk.default.bucket, "testingfiles/updatedCodes/NaicsCode.csv")).getObjectContent()
        CSVReader reader = new CSVReader(new InputStreamReader(is))
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            def code = nextLine[0]?.trim()
            def desc = nextLine[1]?.trim()
            if(code && code != ""){

                if(code.length() == 2 || code.contains("-")) //root level
                    new NaicsCode(code: code,description: desc).save()

                else{
                    def parentCode = nextLine.length == 3 ? nextLine[2].trim() : code[0..code.length()-2]
                    new NaicsCode(
                        code: code,
                        description: desc,
                        parentNaicsCode: NaicsCode.findByCode(parentCode)
                    ).save()
                }

            }
        }
    }

    def importSicCodes() {
        InputStream is = amazonWebService.s3.getObject(new GetObjectRequest(grailsApplication.config.grails.plugin.awssdk.default.bucket, "testingfiles/SicCode.csv")).getObjectContent()
        CSVReader reader = new CSVReader(new InputStreamReader(is))
        String[] nextLine;
        SicCode grandParent, parent
        while ((nextLine = reader.readNext()) != null) {
            def code = nextLine[0]?.trim()
            def desc = nextLine[1]?.trim()
            if(code && code != ""){
                if(code.length() == 1) //root level
                    grandParent = new SicCode(code: code,description: desc).save()
                else if(code[code.length()-1] == "0")
                    parent = new SicCode(code: code,description: desc,parentSicCode: grandParent).save()
                else
                    new SicCode(code: code,description: desc,parentSicCode: parent).save()
            }
        }
    }

    def buildSicNaicsCrosswalk(){
        InputStream is = amazonWebService.s3.getObject(new GetObjectRequest(grailsApplication.config.grails.plugin.awssdk.default.bucket, "testingfiles/updatedCodes/SIC_to_NAICS_Crosswalk.csv")).getObjectContent()
        CSVReader reader = new CSVReader(new InputStreamReader(is))
        String[] nextLine;
        SicCode sicCode
        NaicsCode naicsCode
        while ((nextLine = reader.readNext()) != null) {
            sicCode = SicCode.findByCode(nextLine[0]?.trim())
            naicsCode = NaicsCode.findByCode(nextLine[1]?.trim())
            if(!sicCode)
                log.error "Crosswalk SIC CODE ${nextLine[0]?.trim()} was not found"

            if(!naicsCode)
                log.error "Crosswalk NAICS CODE ${nextLine[1]?.trim()} was not found"

            if(sicCode && naicsCode){
                SicNaicsCodeCrosswalk.create sicCode, naicsCode
            }
        }
    }

    def createCompanyTypes(){

        List codes = ["Agency/Retailer", "Carrier", "Reinsurer", "Wholesaler (MGA, Broker)"]

        codes.eachWithIndex { String code, int i ->
            if(!CompanyType.findByCode(code)){
                CompanyType companyType = new CompanyType(code:code, intCode:i.toInteger(), description: code)
                if(!companyType.save(flush:true)){
                    companyType.errors.each {
                        log.debug it
                    }
                }
            }
        }
    }
}
