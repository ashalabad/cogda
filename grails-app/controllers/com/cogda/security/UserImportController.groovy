package com.cogda.security

import com.cogda.BaseController
import com.cogda.command.UserImportCommand
import com.cogda.command.UserImportCommandSerializer
import com.cogda.command.UserImportCommandsSerializer
import com.cogda.domain.UserProfile
import com.cogda.domain.security.PendingUser
import com.cogda.domain.security.Role
import com.cogda.domain.security.User
import com.cogda.domain.security.UserRole
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import grails.plugin.gson.converters.GSON
import grails.plugins.springsecurity.Secured
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.RequestContextUtils as RCU
import static javax.servlet.http.HttpServletResponse.*
import static grails.plugin.gson.http.HttpConstants.*

/**
 * UserImportController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Secured(["hasAnyRole('ROLE_ADMINISTRATOR')"])
class UserImportController extends BaseController{

    final static List<String> ALLOWED_FILE_EXTENSIONS = ["CSV", "TXT"]
    final static Integer MAX_FILE_SIZE = 5 * 1024 * 1024  // 5MB

    SpringSecurityService springSecurityService
    UserImportService userImportService
    def messageSource
    GsonBuilder gsonBuilder

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index(){
        List assignableRoleInstances = Role.adminAssignableAuthorities()

        render([view:"index", model:[assignableRoleInstances:assignableRoleInstances]])
    }

    /**
     * Handles the import of PendingUsers from a user supplied CSV file.
     * If the file is valid then this action calls on the userImportService
     * to process the file and store the data from the file into the
     * PendingUser domain class object.
     * Returns a JSON list of the UserImportCommand objects used to validate the
     * contents of the file.
     * @return JSON
     */
    def importFromUserFile(){
        /**************************
         * check if file exists
         **************************/
        MultipartFile file = request.getFile("file")

        if(file.empty){
            respondUnprocessableFile(message(code:"fileupload.upload.nofile"))
            return
        }

        /***********************
         * check extensions
         ************************/
        def fileExtension = file.originalFilename.substring(file.originalFilename.lastIndexOf('.')+1).toUpperCase()
        if(!ALLOWED_FILE_EXTENSIONS.contains(fileExtension)){
            respondUnprocessableFile(message(code:"fileupload.upload.unauthorizedExtension", args:[fileExtension, ALLOWED_FILE_EXTENSIONS]))
            return
        }

        /*********************
         * check file size
         **********************/
        if (file.size > MAX_FILE_SIZE) { //if filesize is bigger than allowed
            def maxSizeInKb = ((int) (MAX_FILE_SIZE/1024))
            log.debug "FileUploader plugin received a file bigger than allowed. Max file size is ${maxSizeInKb} kb"
            respondUnprocessableFile(message(code:"fileupload.upload.fileBiggerThanAllowed", args:[maxSizeInKb]))
            return
        }

        List<UserImportCommand> userImportCommands = userImportService.processUserImport(file.inputStream)
        userImportCommands.each { UserImportCommand userImportCommand ->
            if(userImportCommand.hasErrors()){
                for (fieldErrors in userImportCommand.errors) {
                    for (error in fieldErrors.allErrors) {
                        String message = messageSource.getMessage(error, RCU.getLocale(request))
                        if(userImportCommand.resolvedErrorsByField.containsKey(error.field)){
                            userImportCommand.resolvedErrorsByField.get(error.field).add(message)
                        }else{
                            userImportCommand.resolvedErrorsByField.put(error.field, [message])
                        }
                    }
                }
            }
        }

        // display userImports that had errors first
        userImportCommands = userImportCommands.sort {
            it.hasErrors()
        }
        userImportCommands = userImportCommands.reverse()

        gsonBuilder.registerTypeAdapter(UserImportCommand.class, new UserImportCommandSerializer())
        gsonBuilder.registerTypeAdapter(List.class, new UserImportCommandsSerializer())
        Gson gson = gsonBuilder.create()

        response.status = SC_OK
        String jsonReturn = gson.toJson(userImportCommands)
        render jsonReturn
        return
    }

    private boolean requestIsJson() {
        GSON.isJson(request)
    }

    private void respondUnprocessableFile(String message) {
        def responseBody = [:]
        responseBody.errors = []
        responseBody.errors.add(message)
        response.status = SC_UNPROCESSABLE_ENTITY // 422
        render responseBody as GSON
    }

    private void respondNotAcceptable() {
        response.status = SC_NOT_ACCEPTABLE  // 406
        response.contentLength = 0
        response.outputStream.flush()
        response.outputStream.close()
    }


}
