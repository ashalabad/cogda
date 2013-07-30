angular.module('resources.suspect', ['resources.restApi']).factory('Suspect', ['RestApi', function(RestApi) {
    return RestApi.getRest('/suspect/');
}]);