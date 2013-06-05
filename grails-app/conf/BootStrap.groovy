import com.cogda.domain.admin.CompanyType
import com.cogda.multitenant.CustomerAccount
import com.cogda.domain.admin.SupportedCountryCode

class BootStrap {

    def init = { servletContext ->

        CompanyType agency = new CompanyType(code:"Agency/Retailer", intCode:0, description: "Agency/Retailer").save()
        CompanyType carrier = new CompanyType(code:"Carrier", intCode:1, description: "Carrier").save()
        CompanyType reinsurer = new CompanyType(code:"Reinsurer", intCode:2, description: "Reinsurer").save()
        CompanyType wholesaler = new CompanyType(code:"Wholesaler (MGA, Broker)", intCode:3, description: "Wholesaler (MGA, Broker)").save()

        CustomerAccount.findOrSaveBySubDomain("rps")
        CustomerAccount.findOrSaveBySubDomain("renaissanceins")

        SupportedCountryCode usa = new SupportedCountryCode(countryCode:"usa", countryDescription:"United States").save()
        SupportedCountryCode can = new SupportedCountryCode(countryCode:"can", countryDescription:"Canada").save()
//        SupportedCountryCode bra = new SupportedCountryCode(countryCode:"bra", countryDescription:"Brazil").save()



    }
    def destroy = {
    }
}
