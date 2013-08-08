package com.cogda.domain

import com.cogda.GsonBaseController
import grails.plugin.gson.converters.GSON
import grails.plugins.springsecurity.Secured
import org.springframework.dao.DataIntegrityViolationException
import static grails.plugin.gson.http.HttpConstants.*

/**
 * SubmissionController
 * The SubmissionController works with the Submission Domain Class Object.
 */
@Secured(['IS_AUTHENTICATED_FULLY'])
class SubmissionController extends GsonBaseController {

    final static String INSTANCE_LABEL = "submission.label"

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        response.addIntHeader X_PAGINATION_TOTAL, Submission.count()
        render Submission.list(params) as GSON
    }

    def save() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def submissionInstance = new Submission(request.GSON)
        if (submissionInstance.save(flush: true)) {
            respondCreated submissionInstance
        } else {
            respondUnprocessableEntity submissionInstance
        }
    }

    def get() {
        Submission submissionInstance = Submission.get(params.id)
        if (submissionInstance) {
            respondFound submissionInstance
        } else {
            respondNotFound INSTANCE_LABEL
        }
    }

    def update() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def submissionInstance = Submission.get(params.id)
        if (!submissionInstance) {
            respondNotFound INSTANCE_LABEL
            return
        }

        if (params.version != null) {
            if (submissionInstance.version > params.long('version')) {
                respondConflict(submissionInstance)
                return
            }
        }

        submissionInstance.properties = request.GSON

        if (submissionInstance.save(flush: true)) {
            respondUpdated submissionInstance
        } else {
            respondUnprocessableEntity submissionInstance
        }
    }

    def delete() {
        def submissionInstance = Submission.get(params.id)
        if (!submissionInstance) {
            respondNotFound INSTANCE_LABEL
            return
        }
        try {
            submissionInstance.delete(flush: true)
            respondDeleted INSTANCE_LABEL
        } catch (DataIntegrityViolationException e) {
            respondNotDeleted INSTANCE_LABEL
        }
    }

}
