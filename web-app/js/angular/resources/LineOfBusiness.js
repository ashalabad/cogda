angular.module('resources.lineOfBusiness', ['resources.restApi']).factory('LineOfBusiness', ['RestApi', function(RestApi) {
    return RestApi.getRest('/lineOfBusiness/');
}]);
