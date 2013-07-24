angular.module('resources.AccountContact', ['resources.restApi']).factory('AccountContact', function(RestApi) {
    return RestApi.getRest('/accountContact/');
});
