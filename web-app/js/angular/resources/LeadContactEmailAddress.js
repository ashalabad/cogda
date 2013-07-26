angular.module('resources.leadContactEmailAddress', ['resources.restApi']).factory('LeadContactEmailAddress', ['RestApi', function(RestApi) {
    return RestApi.getRest('/leadContactEmailAddress/');
}]);