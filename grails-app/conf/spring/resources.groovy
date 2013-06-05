import com.cogda.multitenant.CachingTenantRepository
import com.cogda.multitenant.DomainTenantResolver

// Place your Spring DSL code here
beans = {
	customPropertyEditorRegistrar(CustomDateEditorRegistrar)

    // Activate these bean definitions
    // Documentation http://grails.org/doc/latest/guide/single.html#14.2%20Configuring%20Additional%20Beans
    tenantResolver(DomainTenantResolver)
    tenantRepository(CachingTenantRepository)
}
