angular.module('resources.leadNote', ['resources.restApi']).factory('LeadNote', ['RestApi', function(RestApi) {
    return RestApi.getRest('/leadNote/');
}]);