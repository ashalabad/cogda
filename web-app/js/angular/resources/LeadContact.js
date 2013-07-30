angular.module('resources.leadContact', ['resources.restApi']).factory('LeadContact', ['RestApi', function(RestApi) {
    return RestApi.getRest('/leadContact/');
}]);