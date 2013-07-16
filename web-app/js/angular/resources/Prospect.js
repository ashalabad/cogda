angular.module('resources.prospect', ['resources.restApi']);
angular.module('resources.prospect', []).factory('Prospect', function(RestApi) {
    return RestApi.getRest('/prospect/');
});