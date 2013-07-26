angular.module('resources.naicscode', ['resources.restApi']).factory('NaicsCode', ['RestApi', function(RestApi) {
    return RestApi.getRest('/naicsCode/');
}]);