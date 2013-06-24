package com.cogda.api.admin

import com.cogda.security.UserService

/**
 * UserController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class AdminUserController {

    static allowedMethods = {
        availableUserName : 'GET'
    }

    UserService userService

    def availableUserName(String userName) {
        def result = userService.availableUsername(userName)
        render result
    }
}
