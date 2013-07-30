angular.module('resources.Lead', ['resources.restApi']).factory('Lead', ['RestApi', function(RestApi) {
    return RestApi.getRest('/lead/');
}]);
