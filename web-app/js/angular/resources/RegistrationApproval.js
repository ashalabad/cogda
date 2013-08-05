angular.module('resources.registrationApproval', ['resources.restApi']).factory('RegistrationApproval', ['RestApi', function(RestApi) {
    return RestApi.getRest('/registrationApproval/');
}]);