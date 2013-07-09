'use strict';
angular.module('resources.logger', []).factory('Logger', function () {
    toastr.options.timeOut = 3000; // 2 second toast timeout
    toastr.options.positionClass = 'toast-top-right';

    var logger = {
        error: error,
        info: info,
        success: success,
        warning: warning,
        log: log, // straight to console; bypass toast
        messageBuilder:messageBuilder,
        formValidationMessageBuilder: formValidationMessageBuilder
    };

    /**
     * Throw a toastr error with a message and a title
     * @param message
     * @param title
     */
    function error(message, title) {
        toastr.error(message, title);
        log("Error: " + message);
    };
    /**
     * Show a toastr info with a message and a title
     * @param message
     * @param title
     */
    function info(message, title) {
        toastr.info(message, title);
        log("Info: " + message);
    };
    /**
     * Show a toastr success with a message and a title
     * @param message
     * @param title
     */
    function success(message, title) {
        toastr.success(message, title);
        log("Success: " + message);
    };
    /**
     * Show a toastr warning with a message and a title
     * @param message
     * @param title
     */
    function warning(message, title) {
        toastr.warning(message, title);
        log("Warning: " + message);
    };

    function messageBuilder(response, $scope){
        switch (response.status) {
            case 200:
                if(response.data.message){
                    success(response.data.message, "Success");
                }else{
                    log("Successful Read Update or Delete", "Success");
                }
                break;
            case 201:
                success("Successful Create", "Success");
                break;
            case 404: // resource not found - return to the list and display message returned by the controller
                if(response.data.message){
                    warning(response.data.message, "Warning");
                }else{
                    warning("The item you were looking for was not found", "Warning");
                }
                break;
            case 409: // optimistic locking failure - display error message on the page
                if(response.data.message){
                    error(response.data.message, "Error")
                }else{
                    error("Another user has updated this item while you were editing", "Error");
                }
                break;
            case 406: // optimistic locking failure - display error message on the page
                error("Unable to process your request", "Error");
                break;
            default: // TODO: general error handling
        }
    }

    function formValidationMessageBuilder(response, $scope, form){
        switch (response.status) {
            case 422: // validation error - display errors alongside form fields
                $scope.errors = response.data.errors;
                applyFormErrorValidity($scope, response.data.errors, form);
                if(response.data.message){
                    error(response.data.message, "Error");
                }else{
                    error("Errors", "Error");
                }
                break;
            default:
                error("Undhandled Error Thrown", "Error " + response.status);
        }
    }

    function applyFormErrorValidity($scope, errors, form){
        form.$setValidity("", false);
        for(var i in errors){
            form[i].$setValidity(i, false);
        }
    }


    // IE and google chrome workaround
    // http://code.google.com/p/chromium/issues/detail?id=48662
    function log() {
        var console = window.console;
        !!console && console.log && console.log.apply && console.log.apply(console, arguments);
    }

    return logger;
});
