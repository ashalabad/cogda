angular.module('resources.leadAddress', ['resources.restApi']);
angular.module('resources.leadAddress', []).factory('LeadAddress', function(RestApi) {
    return RestApi.getRest('/leadAddress/');
});