import com.cogda.account.EmailSendReasonCode
import com.cogda.account.EmailSendStatusCode
import com.cogda.common.RegistrationStatus
import com.cogda.domain.admin.CompanyType
import com.cogda.domain.admin.EmailConfirmationLog
import com.cogda.domain.onboarding.Registration
import com.cogda.multitenant.CustomerAccount
import grails.converters.JSON

def raisCustomerAccount = CustomerAccount.get(1)
def agency = CompanyType.get(1)
EmailConfirmationLog emailConfirmationLog = new EmailConfirmationLog(
        emailSendReason: EmailSendReasonCode.ACCOUNT_ACTIVATION_EMAIL,
        emailSendStatus: EmailSendStatusCode.SUCCESS
)

Registration registration = new Registration(firstName: "Justin",
        lastName: "Clark",
        emailAddress: "jclark@cogda.com",
        password: "12345678",
        companyName: "Renaissance Alliance",
        username: "jclark",
        companyType: agency,
        subDomain: "whatevs",
        registrationStatus: RegistrationStatus.AWAITING_USER_EMAIL_CONFIRMATION,
        emailConfirmationLogs: [emailConfirmationLog]
)
println agency as JSON
//raisCustomerAccount.withThisTenant {
//
//    if (!emailConfirmationLog.save()) {
//        emailConfirmationLog.errors.each {
//            log.error(it)
//        }
//    }
//
//    if (!registration.save(flush: true)) {
//        registration.errors.each {
//            log.error(it)
//        }
//    }
//}