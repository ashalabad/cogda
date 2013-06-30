package com.cogda.security

import com.cogda.domain.security.PendingUser
import com.cogda.domain.security.Role
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import grails.plugin.gson.converters.GSON
import grails.plugins.springsecurity.Secured
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.dao.DataIntegrityViolationException

import static javax.servlet.http.HttpServletResponse.*
import static grails.plugin.gson.http.HttpConstants.*
import static org.codehaus.groovy.grails.web.servlet.HttpHeaders.*

/**
 * PendingUserController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Secured(["hasAnyRole('ROLE_ADMINISTRATOR')"])
class PendingUserController {

    GsonBuilder gsonBuilder
    PendingUserService pendingUserService
    SpringSecurityService springSecurityService
    def index() {

    }

    /**
     * Passes back a JSON list of PendingUsers in the expected dataTables format.
     * @return JSON
     */
    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)

        List pendingUsers = PendingUser.findAllByPendingUserStatus(PendingUserStatus.PENDING)

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
            dataToRender.aaData.add(map)
        }

        dataToRender.sEcho = 1

        render dataToRender as GSON

        return
    }

    def save(){

        if(!requestIsJson()){
            respondNotAcceptable()
            return
        }

        PendingUser pendingUserInstance = new PendingUser()

        JsonElement jsonElement = GSON.parse(request)
        applyFormData(pendingUserInstance, jsonElement)

        createInstance(pendingUserInstance)
    }


    def update(){
        if(!requestIsJson()){
            respondNotAcceptable()
            return
        }

        def pendingUserInstance = PendingUser.get(params.id)
        if (!pendingUserInstance) {
            respondNotFound params.id
            return
        }

        JsonElement jsonElement = GSON.parse(request)
        applyFormData(pendingUserInstance, jsonElement)

        updateInstance(pendingUserInstance)
    }

    def delete(){
        PendingUser pendingUserInstance = PendingUser.get(params.id)
        if (!pendingUserInstance) {
            respondNotFound params.id
            return
        }

        try {
            pendingUserInstance.delete(flush: true)
            respondDeleted params.id
        } catch (DataIntegrityViolationException e) {
            respondNotDeleted params.id
        }
    }

    /**
     * Sends the User Invitations
     * @return
     */
    def sendUserInvitations() {
        if(!requestIsJson()){
            respondNotAcceptable()
            return
        }

        def ids = GSON.parse(request)

        if(ids){
            List pendingUserIds = collectUserIds(ids)

            // validate that each id exists
            pendingUserIds.each { Long id ->
                if(!PendingUser.exists(id)){
                    // throw an error that the client must refresh their list
                    // some of the ImportedUsers no longer exist
                    respondUnprocessableNotifications(id)
                    return
                }
            }

            pendingUserService.sendUserEmailInvitations(pendingUserIds)

        }else{
            // show error - no ids passed into this action
            respondSuccessMessage('pendingUser.invitations.notsent')
            return
        }

        respondNotificationsSent()
        return
    }

    def deleteUsers(){
        if(!requestIsJson()){
            respondNotAcceptable()
            return
        }

        def ids = GSON.parse(request)

        if(ids){
            List pendingUserIds = collectUserIds(ids)
            pendingUserService.delete(pendingUserIds)
            respondSuccessMessage('pendingUsers.deleted.successfully')
            return
        }else{
            respondSuccessMessage('pendingUsers.not.deleted')
            return
        }
    }

    def form(){
        def pendingUserInstance = PendingUser.get(params.id)
        if (pendingUserInstance) {
            response.status = SC_OK
            render(template:"/pendingUser/modalForm", model:[pendingUserInstance:pendingUserInstance, authorities: Role.ADMIN_ASSIGNABLE_AUTHORITIES])
            return
        } else {
            respondNotFound params.id
        }
    }

    def addForm(){
        PendingUser pendingUserInstance = new PendingUser()
        response.status = SC_OK
        render(template:"/pendingUser/modalForm", model:[pendingUserInstance:pendingUserInstance, authorities: Role.ADMIN_ASSIGNABLE_AUTHORITIES])
        return
    }

    private void updateInstance(instance){
        if (instance.save(flush: true)) {
            respondUpdated instance
        } else {
            respondUnprocessableEntity instance
        }
    }

    private void createInstance(instance){
        String username = springSecurityService.currentUser.username
        pendingUserService.createPendingUser(instance, username)
        if (!instance.hasErrors()) {
            respondCreated instance
        } else {
            respondUnprocessableEntity instance
        }
    }

    private void applyFormData(PendingUser pendingUserInstance, JsonElement jsonElement){
        pendingUserInstance.firstName = jsonElement.firstName.getAsString()
        pendingUserInstance.lastName = jsonElement.lastName.getAsString()
        pendingUserInstance.emailAddress = jsonElement.emailAddress.getAsString()
        pendingUserInstance.securityRoles = ""
        if(jsonElement.securityRoles){
            if(jsonElement.securityRoles.isJsonPrimitive()){
                pendingUserInstance.securityRoles = jsonElement.securityRoles.getAsString()
            }else{
                jsonElement.securityRoles.eachWithIndex { it, i ->
                    if(i != 0){
                        pendingUserInstance.securityRoles += ", "
                    }
                    pendingUserInstance.securityRoles += it.getAsString()
                }
            }
        }

    }

    private List collectUserIds(ids){
        List pendingUserIds = []
        if(ids.isJsonPrimitive()){
            pendingUserIds.add(ids.asLong)
        }else{
            ids.each {
                pendingUserIds.add(it.getAsLong())
            }
        }
        return pendingUserIds
    }

    private void respondCreated(instance) {
        response.status = SC_CREATED // 201
        response.addHeader LOCATION, createLink(action: 'get', id: instance.id)
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(instance);
        render gsonRetString
    }

    private void respondUpdated(PendingUser pendingUserInstance) {
        response.status = SC_OK // 200
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(pendingUserInstance);
        render gsonRetString
    }

    private void respondNotFound(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.found.message', args: [message(code: 'pendingUser.label'), id])
        response.status = SC_NOT_FOUND // 404
        render responseBody as GSON
    }

    private void respondSuccessMessage( String messageCode ) {
        def responseBody = [:]
        responseBody.message = message(code: messageCode)
        response.status = SC_OK  // 200
        render responseBody as GSON
    }

    private void respondNotificationsSent() {
        def responseBody = [:]
        responseBody.message = message(code: 'pendingUser.invitations.sent')
        response.status = SC_OK  // 200
        render responseBody as GSON
    }

    private boolean requestIsJson() {
        GSON.isJson(request)
    }

    private void respondNotAcceptable() {
        response.status = SC_NOT_ACCEPTABLE  // 406
        response.contentLength = 0
        response.outputStream.flush()
        response.outputStream.close()
    }

    private void respondUnprocessableNotifications(id){
        def responseBody = [:]
        responseBody.errors = [message('pendingUser.sendInvitation.notFound.error')]
        response.status = SC_UNPROCESSABLE_ENTITY // 422
        render responseBody as GSON
    }

    private void respondUnprocessableEntity(instance) {
        def responseBody = [:]
        responseBody.errors = instance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_UNPROCESSABLE_ENTITY // 422
        render responseBody as GSON
    }

    private void respondDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.deleted.message', args: [message(code: 'pendingUser.label'), id])
        response.status = SC_OK  // 200
        render responseBody as GSON
    }

    private void respondNotDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.deleted.message', args: [message(code: 'pendingUser.label'), id])
        response.status = SC_INTERNAL_SERVER_ERROR  // 500
        render responseBody as GSON
    }
}
