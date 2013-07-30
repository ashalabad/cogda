import com.cogda.common.ErrorController
import com.cogda.common.HomeController
import grails.test.mixin.web.UrlMappingsUnitTestMixin
import spock.lang.Specification
import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(UrlMappings)
@TestMixin([UrlMappingsUnitTestMixin])
@Mock([HomeController, ErrorController])
class UrlMappingsSpec extends Specification {

    void setup() {
        // Setup logic here
    }

    void cleanup() {
        // Tear down logic here
    }

    def "'/' forwards user to home controller"() {
        expect:
        assertForwardUrlMapping("/", controller:"home", action:"index")
    }

    def "assert error codes forward to error controller"(){
        expect:
        assertForwardUrlMapping(403, controller:"error")
        assertForwardUrlMapping(404, controller:"error")
        assertForwardUrlMapping(500, controller:"error")
        assertForwardUrlMapping(503, controller:"error")
    }




}
