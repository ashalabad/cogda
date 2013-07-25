import com.cogda.multitenant.CustomerAccount
import grails.plugin.multitenant.core.ast.MultiTenantAST

// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }
grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [
    all:           '*/*',
    atom:          'application/atom+xml',
    css:           'text/css',
    csv:           'text/csv',
    form:          'application/x-www-form-urlencoded',
    html:          ['text/html','application/xhtml+xml'],
    js:            'text/javascript',
    json:          ['application/json', 'text/json'],
    multipartForm: 'multipart/form-data',
    rss:           'application/rss+xml',
    text:          'text/plain',
    xml:           ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

environments {
    development {
        grails.logging.jul.usebridge = true
        grails.serverURL = "http://cogdalocal.com:8080"
        grails.domainURL = "cogdalocal.com:8080"
    }
    test {
        grails.logging.jul.usebridge = true
        grails.serverURL = "http://cogdalocal.com:8080"
        grails.domainURL = "cogdalocal.com:8080"
    }
    production {
        grails.logging.jul.usebridge = false
        grails.serverURL = "http://cogdatwo.com"
        grails.domainURL = "cogdatwo.com"
    }
}

// log4j configuration

//environments {
//    development {
        log4j = {
            // Example of changing the log pattern for the default console appender:
            //
            appenders {
                console name:'stdout', layout:pattern(conversionPattern: '%d [%t] %-5p %c - %m%n'), threshold: org.apache.log4j.Level.DEBUG
            }
            error  'org.codehaus.groovy.grails.web.servlet',        // controllers
                   'org.codehaus.groovy.grails.web.pages',          // GSP
                   'org.codehaus.groovy.grails.web.sitemesh',       // layouts
                   'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
                   'org.codehaus.groovy.grails.web.mapping',        // URL mapping
                   'org.codehaus.groovy.grails.commons',            // core / classloading
                   'org.codehaus.groovy.grails.plugins',            // plugins
                   'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
                   'org.springframework',
                   'org.hibernate',
                   'net.sf.ehcache.hibernate'

            error  'grails.app.controllers.com.cogda'
            error  'grails.app.domain.com.cogda'
            error  'grails.app.taglib'
            error  'grails.app.conf'
            debug  'grails.app.filters.cogda'
            error  'grails.app.taglib'
            error  'grails.app.services.com.cogda'
            error  'org.codehaus.groovy.grails.plugins'
            debug  'java.grails.plugin.multitenant.core'
            debug  'java.grails.plugin.multitenant.singledb.hibernate'
//            error  'com.cogda'
//            error  'cogda'
            root {
                error 'stdout'
//                info 'stdout'
//                warn 'stdout'
//                debug 'stdout'
                additivity = true
            }
        }
//    }
//    test {
//        log4j = {
//            // Example of changing the log pattern for the default console appender:
//            appenders {
//                console name:'stdout', layout:pattern(conversionPattern: '%d [%t] %-5p %c - %m%n'), threshold: org.apache.log4j.Level.DEBUG
//            }
//
//            // trace 'org.hibernate.type'
//            // debug 'org.hibernate.SQL'
//            warn  'org.codehaus.groovy.grails.web.servlet',        // controllers
//                    'org.codehaus.groovy.grails.web.pages',          // GSP
//                    'org.codehaus.groovy.grails.web.sitemesh',       // layouts
//                    'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
//                    'org.codehaus.groovy.grails.web.mapping',        // URL mapping
//                    'org.codehaus.groovy.grails.commons',            // core / classloading
//                    'org.codehaus.groovy.grails.plugins',            // plugins
//                    'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
//                    'org.springframework',
//                    'org.hibernate',
//                    'net.sf.ehcache.hibernate'
//            debug  'grails.app.services.com.cogda'
//            debug  'grails.app.controllers.com.cogda'
//            debug  'grails.app.filters'
//            debug  'grails.app.domain'
//            debug  'grails.app.taglib'
//            debug  'grails.app.conf'
//
//            root {
//                error 'stdout'
//                info 'stdout'
//                warn 'stdout'
//                debug 'stdout'
//                additivity = true
//            }
//        }
//    }
//    production {
//        log4j = {
//            // Example of changing the log pattern for the default console appender:
//            appenders {
//                console name:'stdout', layout:pattern(conversionPattern: '%d [%t] %-5p %c - %m%n'), threshold: org.apache.log4j.Level.DEBUG
//                null name:'stacktrace'
//            }
//
//            error  'org.codehaus.groovy.grails.web.servlet',        // controllers
//                    'org.codehaus.groovy.grails.web.pages',          // GSP
//                    'org.codehaus.groovy.grails.web.sitemesh',       // layouts
//                    'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
//                    'org.codehaus.groovy.grails.web.mapping',        // URL mapping
//                    'org.codehaus.groovy.grails.commons',            // core / classloading
//                    'org.codehaus.groovy.grails.plugins',            // plugins
//                    'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
//                    'org.springframework',
//                    'org.hibernate',
//                    'net.sf.ehcache.hibernate'
//
//            warn   'org.mortbay.log'
//
//            debug  'grails.app.services.com.cogda'
//            debug  'grails.app.controllers.com.cogda'
//            debug  'grails.app.filters'
//            debug  'grails.app.domain'
//            debug  'grails.app.taglib'
//            debug  'grails.app.conf'
//
//            root {
//                error 'stdout'
//                info 'stdout'
//                warn 'stdout'
//                debug 'stdout'
//                additivity = true
//            }
//        }
//    }
//}

grails {
    mail {
        host = "smtp.sendgrid.net"
        port = 465
        username = "cogdasendgrid"
        password = "EnvatoEnvato2u"
        props = ["mail.smtp.auth":"true",
                "mail.smtp.socketFactory.port":"465",
                "mail.smtp.socketFactory.class":"javax.net.ssl.SSLSocketFactory",
                "mail.smtp.socketFactory.fallback":"false"]
    }
}

environments {
    development {
        grails.mail.overrideAddress="chris@cogda.com"
    }
    test {
        grails.mail.overrideAddress="chris@cogda.com"
    }
    beestest {
        grails.mail.overrideAddress="chris@cogda.com"
    }
}
grails.mail.default.from="postmaster@cogda.com"


// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'com.cogda.domain.security.User'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'com.cogda.domain.security.UserRole'
grails.plugins.springsecurity.authority.className = 'com.cogda.domain.security.Role'
grails.plugins.springsecurity.logout.afterLogoutUrl = "/"
grails.plugins.springsecurity.failureHandler.useForward = true


// Start: MultiTenant plugin configuration
multiTenant {
    tenantClass = CustomerAccount
}
multiTenant.resolveTenantBeforeLogin=true
grails.app.context="/"
// End: MultiTenant plugin configuration

// Start: Kickstart plugin configuration
grails.config.defaults.locations = [KickstartResources]
// End: Kickstart plugin configuration


application.name="Cogda"

// Start: AWS-SDK Plugin Configuration - Eventually we will move this configuration to Java Properties files that
// are loaded at runtime.
// All of the users used to access AWS buckets are part of the Cogda_Web group
// A specific user is assigned to access a specific bucket.
environments {
    // development uses: cogda_development_web user security credentials
    development {
        grails.plugin.awssdk.accessKey = "AKIAILS5HGMNXGK47S7Q"
        grails.plugin.awssdk.secretKey = "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"
        grails.plugin.awssdk.default.bucket = "cogda-development"
    }
    test {
        grails.plugin.awssdk.accessKey = "AKIAIK4U45JFDKT6YPFQ"
        grails.plugin.awssdk.secretKey = "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"
        grails.plugin.awssdk.default.bucket = "cogda-test"
    }
    beestest {
        grails.plugin.awssdk.accessKey = "AKIAIK4U45JFDKT6YPFQ"
        grails.plugin.awssdk.secretKey = "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"
        grails.plugin.awssdk.default.bucket = "cogda-test"
    }
    production {
        grails.plugin.awssdk.accessKey = "AKIAISMZUW57B2VYTYEQ"
        grails.plugin.awssdk.secretKey = "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"
        grails.plugin.awssdk.default.bucket = "cogda-production"
    }
}

// Start: GSON Plugin

environments {
    development {
        grails.converters.gson.pretty.print=true
        grails.converters.default.pretty.print=true
    }
}
// CAUTION: the grails.converters.gson.exclusionStrategies.serialization has no effect on the gsonBuilder when applied to Domain Classes
grails.converters.gson.exclusionStrategies.serialization = [new com.cogda.security.SecuritySerializationExclusionStrategy()]

// The following applies a rudimentary serialization exclusion strategy for properties in our Domain Classes
grails.converters.gson.serialization.excludedPropertyNames = [MultiTenantAST.TENANT_ID_FIELD_NAME, "accountId"]

// End: GSON Plugin