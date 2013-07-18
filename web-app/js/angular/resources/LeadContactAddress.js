angular.module('resources.leadContactAddress', ['resources.restApi']).factory('LeadContactAddress', ['RestApi', function (RestApi) {
    return RestApi.getRest('/leadContactAddress/');
}]);