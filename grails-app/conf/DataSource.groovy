dataSource {
    pooled = true
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    // development database for grails run-app
    development {
        dataSource {
            username = "dev"
            password = "dev"
            dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
            pooled = true
            driverClassName = "com.mysql.jdbc.Driver"
            url = "jdbc:mysql://localhost/cogda_dev"
            dialect = org.hibernate.dialect.MySQL5InnoDBDialect
            properties {
                validationQuery = "SELECT 1"
                testOnBorrow = true
                testOnReturn = true
                testWhileIdle = true
                timeBetweenEvictionRunsMillis = 1000 * 60 * 30
                numTestsPerEvictionRun = 3
                minEvictableIdleTimeMillis = 1000 * 60 * 30
            }
        }
    }
    // local testing database - for running integration tests
    test {
        dataSource {
            username = "dev"
            password = "dev"
            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
            pooled = true
            driverClassName = "com.mysql.jdbc.Driver"
            url = "jdbc:mysql://localhost/cogda_test"
            dialect = org.hibernate.dialect.MySQL5InnoDBDialect
            properties {
                validationQuery = "SELECT 1"
                testOnBorrow = true
                testOnReturn = true
                testWhileIdle = true
                timeBetweenEvictionRunsMillis = 1000 * 60 * 30
                numTestsPerEvictionRun = 3
                minEvictableIdleTimeMillis = 1000 * 60 * 30
            }
        }
    }
    // beestest is the environment used by Cloudbees to run our tests in Jenkins.
    beestest {
        dataSource {
            username = "testingbees"
            password = "beescloudtesting"
            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
            pooled = true
            driverClassName = "com.mysql.jdbc.Driver"
            url = "jdbc:mysql://ec2-50-19-213-178.compute-1.amazonaws.com/cogda_test"
            dialect = org.hibernate.dialect.MySQL5InnoDBDialect
            properties {
                validationQuery = "SELECT 1"
                testOnBorrow = true
                testOnReturn = true
                testWhileIdle = true
                timeBetweenEvictionRunsMillis = 1000 * 60 * 30
                numTestsPerEvictionRun = 3
                minEvictableIdleTimeMillis = 1000 * 60 * 30
            }
        }
    }
    // develop is the environment used by Cloudbees to run our cogda-develop.cogda.cloudbees.net application
    develop {
        dataSource {
            username = "cogdadevelop"
            password = "Sm00thCRiMaNail"
            dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
            pooled = true
            driverClassName = "com.mysql.jdbc.Driver"
            url = "jdbc:mysql://ec2-50-19-213-178.compute-1.amazonaws.com/cogda_develop"
            dialect = org.hibernate.dialect.MySQL5InnoDBDialect
            properties {
                validationQuery = "SELECT 1"
                testOnBorrow = true
                testOnReturn = true
                testWhileIdle = true
                timeBetweenEvictionRunsMillis = 1000 * 60 * 30
                numTestsPerEvictionRun = 3
                minEvictableIdleTimeMillis = 1000 * 60 * 30
            }
        }
    }
    // staging is the environment used by Cloudbees to run our cogda-staging.cogda.cloudbees.net application
    staging {
        dataSource {
            username = "cogdastaging"
            password = "Sh0K0Tini"
            dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
            pooled = true
            driverClassName = "com.mysql.jdbc.Driver"
            url = "jdbc:mysql://ec2-50-19-213-178.compute-1.amazonaws.com/cogda_staging"
            dialect = org.hibernate.dialect.MySQL5InnoDBDialect
            properties {
                validationQuery = "SELECT 1"
                testOnBorrow = true
                testOnReturn = true
                testWhileIdle = true
                timeBetweenEvictionRunsMillis = 1000 * 60 * 30
                numTestsPerEvictionRun = 3
                minEvictableIdleTimeMillis = 1000 * 60 * 30
            }
        }
    }
    // production is the environment used by Cloudbees to run our Cogda application
    production {
        dataSource {
            username = "cogdaprod"
            password = "10sllow21"
            dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
            pooled = true
            driverClassName = "com.mysql.jdbc.Driver"
            url = "jdbc:mysql://ec2-50-19-213-178.compute-1.amazonaws.com/cogda_prod"
            dialect = org.hibernate.dialect.MySQL5InnoDBDialect
            properties {
                validationQuery = "SELECT 1"
                testOnBorrow = true
                testOnReturn = true
                testWhileIdle = true
                timeBetweenEvictionRunsMillis = 1000 * 60 * 30
                numTestsPerEvictionRun = 3
                minEvictableIdleTimeMillis = 1000 * 60 * 30
            }
        }
    }
}