angular.module('resources.suspect', ['resources.restApi']);
angular.module('resources.suspect', []).factory('Suspect', function(RestApi) {
    return RestApi.getRest('/suspect/');
});