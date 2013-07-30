angular.module('resources.lead', ['resources.restApi']).factory('Lead', ['RestApi', function(RestApi) {
    return RestApi.getRest('/lead/');
}]);
