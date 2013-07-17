angular.module('resources.prospect', ['resources.restApi']).factory('Prospect', ['RestApi', function(RestApi) {
    return RestApi.getRest('/prospect/');
}]);