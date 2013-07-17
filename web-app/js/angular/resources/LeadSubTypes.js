angular.module('resources.leadSubTypes', ['resources.restApi']).factory('LeadSubTypes', ['RestApi', function(RestApi) {
    return RestApi.getRest('/leadSubType/');
}]);
