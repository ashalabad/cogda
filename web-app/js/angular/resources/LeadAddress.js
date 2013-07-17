angular.module('resources.leadAddress', ['resources.restApi']).factory('LeadAddress', ['RestApi', function (RestApi) {
    return RestApi.getRest('/leadAddress/');
}]);