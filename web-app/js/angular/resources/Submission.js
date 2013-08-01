angular.module('resources.submission', ['resources.restApi']).factory('Submission', ['RestApi', function(RestApi) {
    return RestApi.getRest('/submission/');
}]);