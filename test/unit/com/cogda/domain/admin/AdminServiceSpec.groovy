package com.cogda.domain.admin

import com.cogda.BaseRegistrationSpec
import com.cogda.domain.onboarding.RegisterService
import com.cogda.domain.onboarding.Registration
import com.cogda.security.UserService
import grails.test.mixin.Mock
import grails.test.mixin.TestMixin
import grails.test.mixin.domain.DomainClassUnitTestMixin

/**
 * Created with IntelliJ IDEA.
 * User: Justin
 * Date: 6/22/13
 * Time: 5:29 AM
 * To change this template use File | Settings | File Templates.
 */
@TestMixin([DomainClassUnitTestMixin])
@Mock([Registration])
class AdminServiceSpec extends BaseRegistrationSpec {

    UserService userService = Mock(UserService)
    RegisterService registerService = Mock(RegisterService)

    void setup() {
    }

    def 'list params'() {
        expect:
        1 == 1

    }

}
