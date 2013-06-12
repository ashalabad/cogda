package com.cogda.security

import com.cogda.BaseController

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

    }

    /**
     * Imports a csv file from the user.
     *
     */
    def importUserFile(){

        /**************************
         check if file exists
         **************************/
        File file = request.getFile("file")
        Boolean firstRowContainsHeadings = params.firstRowContainsHeadings ? true : false

        if(file.size() == 0){
            def msg = messageSource.getMessage("fileupload.upload.nofile", null, request.locale)
            log.debug(msg)
            flash.message = msg
            redirect(url:generateRedirectLink("userImport", "index"))
            return
        }

        /***********************
         check extensions
         ************************/
        def fileExtension = file.originalFilename.substring(file.originalFilename.lastIndexOf('.')+1).toUpperCase()
        if(!ALLOWED_FILE_EXTENSIONS.contains(fileExtension)){
            def msg = messageSource.getMessage("fileupload.upload.unauthorizedExtension", [fileExtension, ALLOWED_FILE_EXTENSIONS] as Object[], request.locale)
            log.debug msg
            flash.message = msg
            redirect(url:generateRedirectLink("userImport", "index"))
            return
        }

        /*********************
         check file size
         **********************/
        if (file.size > MAX_FILE_SIZE) { //if filesize is bigger than allowed
            def maxSizeInKb = ((int) (MAX_FILE_SIZE/1024))
            log.debug "FileUploader plugin received a file bigger than allowed. Max file size is ${maxSizeInKb} kb"
            flash.message = messageSource.getMessage("fileupload.upload.fileBiggerThanAllowed", [maxSizeInKb] as Object[], request.locale)
            redirect(url:generateRedirectLink("userImport", "index"))
            return
        }



    }
}

class UserImportCommand {

    static constraints = {
        file()
    }
}
