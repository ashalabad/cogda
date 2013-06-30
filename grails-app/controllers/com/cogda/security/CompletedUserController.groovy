package com.cogda.security

import com.cogda.domain.security.PendingUser
import com.google.gson.GsonBuilder
import grails.plugin.gson.converters.GSON
import grails.plugins.springsecurity.Secured

/**
 * CompletedUserController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Secured(["hasAnyRole('ROLE_ADMINISTRATOR')"])
class CompletedUserController {

    GsonBuilder gsonBuilder
    PendingUserService pendingUserService

    def index() {

    }

    /**
     * Passes back a JSON list of PendingUsers that have Pending User Status COMPLETED in the expected dataTables format.
     * @return JSON
     */
    def dlist() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)

        List pendingUsers = PendingUser.findAllByPendingUserStatus(PendingUserStatus.COMPLETED)

        def dataToRender = [:]

        dataToRender.aaData = []
        pendingUsers.each { PendingUser pendingUser ->
            Map map = [:]
            map.DT_RowId = "row_"+pendingUser.id
            map.id = pendingUser.id
            map.version = pendingUser.version
            map.firstName = pendingUser.firstName
            map.lastName = pendingUser.lastName
            map.emailAddress = pendingUser.emailAddress
            map.securityRoles = pendingUser.securityRoles
            map.loadedDate = pendingUser.loadedDate?.format("MM/dd/yyyy")
            map.loadedByUsername = pendingUser.loadedByUsername
            map.onboardedSuccessfully = pendingUser.onboardedSuccessfully ? "Yes" : "No"
            map.onboardedDate = pendingUser.onboardedDate?.format("MM/dd/yyyy") ?: " "
            map.pendingUserStatus = pendingUser.pendingUserStatus
            map.username = pendingUser.username
            dataToRender.aaData.add(map)
        }

        dataToRender.sEcho = 1

        render dataToRender as GSON

        return
    }
}
