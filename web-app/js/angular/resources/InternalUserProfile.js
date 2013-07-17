/**
 * RestApi implementation of a InternalUserProfile resource...
 */
angular.module('resources.internalUserProfile', ['ngResource']);
angular.module('resources.internalUserProfile', []).factory('InternalUserProfile', function($resource) {

    var internalUserProfile = $resource('/internalUserProfile/:action/:id', {id:'@id'},   {     // the id in this case is the state abbreviation e.g. GA, FL, etc.
        list:     {method: 'GET',  params:  {action: 'list'}, isArray: true},     // list objects
        search:   {method: 'GET',  params:  {action: 'search'}, isArray: true}   // search objects with ?q=mysearchterm
//        get:      {method: 'GET',  params:  {action: 'get'}    }                  // get one object based on the passed in ID
    });

    return internalUserProfile;
});
