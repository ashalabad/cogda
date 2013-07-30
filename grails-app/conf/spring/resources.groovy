import com.cogda.common.marshallers.CustomObjectMarshallers
import com.cogda.common.marshallers.RegistrationMarshaller
import com.cogda.multitenant.CachingTenantRepository
import com.cogda.multitenant.DomainTenantResolver
import grails.util.Environment

// Place your Spring DSL code here
beans = {
	customPropertyEditorRegistrar(CustomDateEditorRegistrar)

    // Activate these bean definitions
    // Documentation http://grails.org/doc/latest/guide/single.html#14.2%20Configuring%20Additional%20Beans
    tenantResolver(DomainTenantResolver){
        grailsApplication = ref('grailsApplication')
    }
    tenantRepository(CachingTenantRepository)
    customObjectMarshallers(CustomObjectMarshallers) {
        marshallers = [
                new RegistrationMarshaller()
        ]
    }


}
