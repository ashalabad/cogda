angular.module('resources.leadLineOfBusiness', ['resources.restApi']).factory('LeadLineOfBusiness', ['RestApi', function(RestApi) {
    return RestApi.getRest('/leadLineOfBusiness/');
}]);
