package com.cogda.domain

import com.cogda.GsonBaseController
import com.google.gson.JsonElement
import grails.plugin.gson.converters.GSON
import grails.plugins.springsecurity.Secured
import org.springframework.dao.DataIntegrityViolationException

import static javax.servlet.http.HttpServletResponse.*


/**
 * UserProfileEmailAddressController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Secured(['IS_AUTHENTICATED_FULLY'])
class UserProfileEmailAddressController extends GsonBaseController{

    final static String INSTANCE_LABEL = "userProfile.label"

    def save() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }
        UserProfileEmailAddress userProfileEmailAddressInstance = new UserProfileEmailAddress(request.GSON)
        if (userProfileEmailAddressInstance.save(flush: true)) {
            respondCreated userProfileEmailAddressInstance
        } else {
            respondUnprocessableEntity userProfileEmailAddressInstance
        }
    }

    def get() {
        def userProfileEmailAddressInstance = UserProfileEmailAddress.get(params.id)
        if (userProfileEmailAddressInstance) {
            respondFound userProfileEmailAddressInstance
        } else {
            respondNotFound INSTANCE_LABEL
        }
    }

    def update() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def userProfileEmailAddressInstance = UserProfileEmailAddress.get(params.id)
        if (!userProfileEmailAddressInstance) {
            respondNotFound INSTANCE_LABEL
            return
        }

        if (params.version != null) {
            if (userProfileEmailAddressInstance.version > params.long('version')) {
                respondConflict(userProfileEmailAddressInstance)
                return
            }
        }

        JsonElement jsonElement = GSON.parse(request)
        userProfileEmailAddressInstance.properties = jsonElement

        if (userProfileEmailAddressInstance.save(flush: true)) {
            respondUpdated userProfileEmailAddressInstance
        } else {
            respondUnprocessableEntity userProfileEmailAddressInstance
        }
    }

    def delete() {
        def userProfileEmailAddressInstance = UserProfileEmailAddress.get(params.id)
        if (!userProfileEmailAddressInstance) {
            respondNotFound INSTANCE_LABEL
            return
        }

        try {
            userProfileEmailAddressInstance.delete(flush: true)
            respondDeleted INSTANCE_LABEL
        } catch (DataIntegrityViolationException e) {
            respondNotDeleted INSTANCE_LABEL
        }
    }

    def formAdd(){
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        UserProfileEmailAddress userProfileEmailAddressInstance = new UserProfileEmailAddress(request.GSON)

        if(!userProfileEmailAddressInstance.userProfile){
            respondUserProfileNotFound()
        }

        Map uProfile = [
                id:null,
                userProfile: [id:userProfileEmailAddressInstance.userProfile.id]
        ]

        userProfileEmailAddressInstance.userProfile.discard()
        userProfileEmailAddressInstance.userProfile = null
        userProfileEmailAddressInstance.discard()
        userProfileEmailAddressInstance = null

        response.status = SC_OK
        render(template:"userProfileEmailAddressForm", model:[userProfileEmailAddressInstance:uProfile])
        return
    }

    def form(){
        def userProfileEmailAddressInstance = UserProfileEmailAddress.get(params.id)
        if (userProfileEmailAddressInstance) {
            response.status = SC_OK
            render(template:"userProfileEmailAddressForm", model:[userProfileEmailAddressInstance:userProfileEmailAddressInstance])
            return
        } else {
            respondNotFound INSTANCE_LABEL
        }
    }


    def show(){
        UserProfileEmailAddress userProfileEmailAddressInstance = UserProfileEmailAddress.get(params.id)
        if(!userProfileEmailAddressInstance){
            respondNotFound INSTANCE_LABEL
            return
        }

        render(template:"/userProfileEmailAddress/showUserProfileEmailAddress", model:[userProfileEmailAddressInstance: userProfileEmailAddressInstance])
        return
    }

    private void respondUserProfileNotFound(id){
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.found.message', args: [message(code: 'userProfile.label', default: 'UserProfile'), id])
        response.status = SC_NOT_FOUND // 404
        render responseBody as GSON
    }
}
