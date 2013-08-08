angular.module('resources.noteType', ['resources.restApi']).factory('NoteType', ['RestApi', function(RestApi) {
    return RestApi.getRest('/noteType/');
}]);
