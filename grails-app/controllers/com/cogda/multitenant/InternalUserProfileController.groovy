package com.cogda.multitenant

import com.cogda.GsonBaseController
import com.cogda.domain.CompanyProfileContact
import com.cogda.domain.UserProfile
import com.cogda.domain.UserProfileService
import grails.plugin.gson.converters.GSON
import grails.plugins.springsecurity.Secured
import org.springframework.beans.BeanUtils
import org.springframework.dao.DataIntegrityViolationException
import static grails.plugin.gson.http.HttpConstants.*

/**
 * InternalUserProfileController
 * Rest API Endpoint at:
 * /internalUserProfile/search?q=
 * /internalUserProfile/list
 *
 * <br>
 * This Rest EndPoint is used to provide to the logged in user of a Customer Account access to
 * all of the UserProfiles associated with the current Customer Account tenant.
 */
@Secured(['IS_AUTHENTICATED_FULLY'])
class InternalUserProfileController extends GsonBaseController {

    UserProfileService userProfileService

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        List userProfiles = userProfileService.internalUserProfileList(params)
        render UserProfile.list(userProfiles) as GSON
        return
    }

    def search(){
        if(!params.q?.trim()){
            def emptyList = []
            render emptyList as GSON
            return
        }

        params.max = 10

        // Sanitize the query
        params.q = params.q.toString().replaceAll("[^A-Za-z0-9]", "")
        List userProfiles = userProfileService.internalUserProfileSearch(params)


        render userProfiles as GSON
        return
    }

}
