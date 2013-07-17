package com.cogda.domain

import com.cogda.domain.security.User
import com.cogda.multitenant.CustomerAccount
import grails.plugin.multitenant.core.Tenant
import grails.plugin.spock.IntegrationSpec

class UserProfileServiceSpec extends IntegrationSpec {

    UserProfileService userProfileService
    String subDomainOne = "subDomainOne"
    String subDomainTwo = "subDomainTwo"


    def setup() {

        if(UserProfile.count() == 0){
            // create a couple CustomerAccounts
            List subdomains =  [subDomainOne, subDomainTwo]
            subdomains.each { String subDomain ->
                CustomerAccount customerAccount = new CustomerAccount()
                customerAccount.subDomain = subDomain
                customerAccount.save(failOnError: true)
            }

            // create the underlying User and UserProfile data
            subdomains.each { String subDomain ->
                CustomerAccount customerAccount = CustomerAccount.findBySubDomain(subDomain)

                customerAccount.withThisTenant {

                    List users = createUsers(customerAccount.subDomain)
                    users.each { User user ->
                        UserProfile userProfile = createValidUserProfile(user, subDomain)
                    }
                }

            }
        }
    }

    def cleanup() {
        CustomerAccount.withoutTenantRestriction {
            UserProfile.executeUpdate("delete from UserProfile")
            User.executeUpdate("delete from User")
        }
    }

    void "test internalUserProfileSearch finds only UserProfiles associated with the active tenant"() {
        given:
        Map searchMap = [:]
        searchMap.max = 10
        searchMap.offset = 0
        searchMap.q = "subDomain"
        when:
        CustomerAccount customerAccount = CustomerAccount.findBySubDomain(subDomainOne)
        List userProfiles = []
        customerAccount.withThisTenant {
            userProfiles = userProfileService.internalUserProfileSearch(searchMap)
        }

        then:
        assert userProfiles, "User Profiles were not found"
        assert userProfiles.size() == 10
        userProfiles.each { UserProfile userProfile ->
            assert userProfile.firstName.contains(subDomainOne)
            assert userProfile.lastName.contains(subDomainOne)
        }

    }

    void "test internalUserProfileSearch is case insensitive on the query term"() {
        given:
        Map searchMap = [:]
        searchMap.max = 10
        searchMap.offset = 0
        searchMap.q = "subdomainone"
        when:
        CustomerAccount customerAccount = CustomerAccount.findBySubDomain(subDomainOne)
        List userProfiles = []
        customerAccount.withThisTenant {
            userProfiles = userProfileService.internalUserProfileSearch(searchMap)
        }

        then:
        assert userProfiles, "User Profiles were not found"
        assert userProfiles.size() == 10
        userProfiles.each { UserProfile userProfile ->
            assert userProfile.firstName.contains(subDomainOne)
            assert userProfile.lastName.contains(subDomainOne)
        }
    }

    void "test internalUserProfileSearch returns empty list if q is not found or is null "() {
        given:
        Map searchMap = [:]
        searchMap.max = 10
        searchMap.offset = 0
        searchMap.q = ""
        when:
        CustomerAccount customerAccount = CustomerAccount.findBySubDomain(subDomainOne)
        List userProfiles = []
        customerAccount.withThisTenant {
            userProfiles = userProfileService.internalUserProfileSearch(searchMap)
        }

        then:
        assert !userProfiles, "User Profiles were found and shouldn't have been"

    }

    void "test internalUserProfileSearch max and offset work "() {
        given:
        Map searchMap = [:]
        searchMap.max = 20
        searchMap.offset = 10
        searchMap.q = "sub"
        when:
        CustomerAccount customerAccount = CustomerAccount.findBySubDomain(subDomainOne)
        List userProfiles = []
        customerAccount.withThisTenant {
            userProfiles = userProfileService.internalUserProfileSearch(searchMap)
        }

        then:
        assert userProfiles, "User Profiles were not found"
        assert userProfiles.size() == 20
        userProfiles.each { UserProfile userProfile ->
            assert userProfile.firstName.contains(subDomainOne)
            assert userProfile.lastName.contains(subDomainOne)
        }

    }

    void "test internalUserProfileList overwrites passed in q "() {
        given:
        Map searchMap = [:]
        searchMap.max = 20
        searchMap.offset = 10
        searchMap.q = "subdomaintwo"
        when:
        CustomerAccount customerAccount = CustomerAccount.findBySubDomain(subDomainOne)
        List userProfiles = []
        customerAccount.withThisTenant {
            userProfiles = userProfileService.internalUserProfileList(searchMap)
        }

        then:
        assert userProfiles, "User Profiles were not found"
        assert userProfiles.size() == 20
        userProfiles.each { UserProfile userProfile ->
            assert userProfile.firstName.contains(subDomainOne)
            assert userProfile.lastName.contains(subDomainOne)
        }

    }



    private List createUsers(String subDomain){
        List users = []
        100.times { Integer i ->
            User user = new User()
            user.username = subDomain + i

            if(i < 90){
                user.enabled = true
                user.accountExpired = false
            }else{

                if(i < 95){
                    user.enabled = false
                    user.accountExpired = false
                }else{
                    user.enabled = true
                    user.accountExpired = true
                }
            }
            user.password = "password"
            users.add(user)
        }
        users.each { User user ->
            assert user.validate()
            assert user.save()
        }
        return users
    }

    private UserProfile createValidUserProfile(User user, String subDomain){
        UserProfile userProfile = new UserProfile()
        userProfile.firstName = subDomain + "firstName"
        userProfile.lastName = subDomain + "lastName"
        userProfile.user = user
        userProfile.aboutDesc = subDomain + "aboutDesc"
        userProfile.associationsDesc = subDomain + "associationsDesc"
        userProfile.middleName = subDomain + "middleName"
        userProfile.published = true
        assert userProfile.validate()
        assert userProfile.save()

        return userProfile
    }
}