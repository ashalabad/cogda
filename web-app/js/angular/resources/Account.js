angular.module('resources.Account', ['resources.restApi']).factory('Account', function(RestApi) {
    return RestApi.getRest('/account/');
});
