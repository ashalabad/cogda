angular.module('resources.unitedStates', ['resources.restApi']).factory('UnitedStates', ['RestApi', function(RestApi) {
    return RestApi.getRest('/unitedStates/');
}]);
