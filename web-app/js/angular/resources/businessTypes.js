angular.module('resources.businessTypes', ['resources.restApi']).factory('BusinessTypes', ['RestApi', function(RestApi) {
    return RestApi.getRest('/businessType/');
}]);


