import com.cogda.domain.admin.CompanyType
import com.cogda.multitenant.Company
import com.cogda.multitenant.CustomerAccount
import com.cogda.domain.admin.SupportedCountryCode
import grails.util.Environment
import grails.util.GrailsUtil

class BootStrap {

    def init = { servletContext ->

        if(Environment.getCurrent() == Environment.DEVELOPMENT){
            CompanyType agency = new CompanyType(code:"Agency/Retailer", intCode:0, description: "Agency/Retailer").save()
            CompanyType carrier = new CompanyType(code:"Carrier", intCode:1, description: "Carrier").save()
            CompanyType reinsurer = new CompanyType(code:"Reinsurer", intCode:2, description: "Reinsurer").save()
            CompanyType wholesaler = new CompanyType(code:"Wholesaler (MGA, Broker)", intCode:3, description: "Wholesaler (MGA, Broker)").save()

            CustomerAccount rpsCustomerAccount = CustomerAccount.findOrSaveBySubDomain("rps")
            CustomerAccount raisCustomerAccount = CustomerAccount.findOrSaveBySubDomain("renaissanceins")

            raisCustomerAccount.withThisTenant {
                Company company = new Company()
                company.name = "Renaissance Alliance"
                company.doingBusinessAs = "Renaissance Alliance"
                company.companyType = wholesaler
                company.level = 0

                if(!company.save()){
                    company.errors.each {
                        println it
                    }
                }
            }

            SupportedCountryCode usa = new SupportedCountryCode(countryCode:"usa", countryDescription:"United States").save()
            SupportedCountryCode can = new SupportedCountryCode(countryCode:"can", countryDescription:"Canada").save()
    //        SupportedCountryCode bra = new SupportedCountryCode(countryCode:"bra", countryDescription:"Brazil").save()

        }

    }
    def destroy = {
    }
}
