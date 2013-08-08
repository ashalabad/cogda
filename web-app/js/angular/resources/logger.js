'use strict';
angular.module('resources.logger', []).factory('Logger', function () {
    toastr.options.timeOut = 4000; // 2 second toast timeout
    toastr.options.positionClass = 'toast-top-right';

    var logger = {
        error: error,
        info: info,
        success: success,
        warning: warning,
        log: log, // straight to console; bypass toast
        messageBuilder:messageBuilder,
        formValidationMessageBuilder: formValidationMessageBuilder,
        formsValidationMessageBuilder: formsValidationMessageBuilder,
        errorValidationMessageBuilder: errorValidationMessageBuilder
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

    function messageBuilder(response){
        switch (response.status) {
            case 200:
                if(response.data.message){
                    success(response.data.message, "Success");
                }else{
                    success("Success", "Successful Operation");
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
                    error(response.data.message, "Error");
                }else{
                    error("Another user has updated this item while you were editing", "Error");
                }
                break;
            case 406: // optimistic locking failure - display error message on the page
                error("Unable to process your request", "Error");
                break;
            case 500:
                error("Unable to process request", "This was due to an issue on the server - please file a support ticket");
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
                    error("Data Validation Failed", "Error");
                }
                break;
            default:
                error("Unhandled Error Thrown", "Error " + response.status);
        }
    }

    function formsValidationMessageBuilder(response, $scope, forms){
        switch (response.status) {
            case 422: // validation error - display errors alongside form fields
                $scope.errors = response.data.errors;
                applyFormsErrorValidity($scope, response.data.errors, forms);
                if(response.data.message){
                    error(response.data.message, "Error");
                }else{
                    error("Errors", "Error");
                }
                break;
            default:
                error("Unhandled Error Thrown", "Error " + response.status);
        }
    }

    function applyFormsErrorValidity($scope, errors, forms){
        form.$setValidity("", false);
        for(var i in errors){
            console.log(i);
            for (var j in forms){
                if (j[i] !== undefined) {
                    j[i].$setValidity(i, false);
                }
            }

        }
    }

    function applyFormErrorValidity($scope, errors, form){
        form.$setValidity("", false);
        for(var i in errors){
            console.log(i)
            form[i].$setValidity(i, false);
        }
    }

    /**
     * Displays validation error messages in toastr
     * @param response
     * @param $scope
     */
    function errorValidationMessageBuilder(response, $scope){
        switch (response.status) {
            case 422: // validation error - display errors alongside form fields
                $scope.errors = response.data.errors;
                for(var i in $scope.errors){
                    for(var j = 0; j < $scope.errors[i].length; j++){
                        error($scope.errors[i][j], "Field Error");
                    }
                }
                break;
            default:
                error("Unhandled Error Thrown", "Error " + response.status);
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
