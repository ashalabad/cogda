angular.module('resources.leadContactPhoneNumber', ['resources.restApi']).factory('LeadContactPhoneNumber', ['RestApi', function(RestApi) {
    return RestApi.getRest('/leadContactPhoneNumber/');
}]);