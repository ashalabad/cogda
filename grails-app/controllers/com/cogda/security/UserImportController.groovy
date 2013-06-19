package com.cogda.security

import com.cogda.BaseController
import com.cogda.domain.security.Role
import org.springframework.web.multipart.MultipartFile

/**
 * UserImportController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */

class UserImportController extends BaseController{

    final static List<String> ALLOWED_FILE_EXTENSIONS = ["CSV", "TXT"]
    final static Integer MAX_FILE_SIZE = 5 * 1024 * 1024  // 5MB


    UserImportService userImportService
    def messageSource

    def index(){
        List assignableRoleInstances = Role.adminAssignableAuthorities()

        render([view:"index", model:[assignableRoleInstances:assignableRoleInstances]])
    }

    /**
     * Imports a csv file from the user.
     *
     */
    def importUserFile(){

        /**************************
         check if file exists
         **************************/
        MultipartFile file = request.getFile("file")

        Boolean firstRowContainsHeadings = params?.firstRowContainsHeadings ? true : false

        if(file.empty){
            flash.error = message(code:"fileupload.upload.nofile")
            redirect(url:generateRedirectLink("userImport", "index"))
            return
        }

        /***********************
         check extensions
         ************************/
        def fileExtension = file.originalFilename.substring(file.originalFilename.lastIndexOf('.')+1).toUpperCase()
        if(!ALLOWED_FILE_EXTENSIONS.contains(fileExtension)){
            flash.error = message(code:"fileupload.upload.unauthorizedExtension", args:[fileExtension, ALLOWED_FILE_EXTENSIONS])
            redirect(url:generateRedirectLink("userImport", "index"))
            return
        }

        /*********************
         check file size
         **********************/
        if (file.size > MAX_FILE_SIZE) { //if filesize is bigger than allowed
            def maxSizeInKb = ((int) (MAX_FILE_SIZE/1024))
            log.debug "FileUploader plugin received a file bigger than allowed. Max file size is ${maxSizeInKb} kb"
            flash.error = message(code:"fileupload.upload.fileBiggerThanAllowed", args:[maxSizeInKb])
            redirect(url:generateRedirectLink("userImport", "index"))
            return
        }

        List importMessages = userImportService.loadUserData(file.inputStream)

        return ["importMessages":importMessages]
    }
}

class UserImportFileCommand {

    static constraints = {
        file()
    }
}
