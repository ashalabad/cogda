/**
 * RestApi implementation of a CompanyProfile resource...
 */
angular.module('resources.noteType', ['ngResource']);
angular.module('resources.noteType', []).factory('NoteType', function($resource) {

    var noteType = $resource('/noteType/:action/:id', {id:'@id'},   {
        list:     {method: 'GET',  params:  {action: 'list'}, isArray: true},     // list objects
        search:   {method: 'GET',  params:  {action: 'search'}, isArray: true},   // search objects with ?q=mysearchterm
        get:      {method: 'GET',  params:  {action: 'get'}    }                  // get one object based on the passed in ID
    });

    return noteType;
});
