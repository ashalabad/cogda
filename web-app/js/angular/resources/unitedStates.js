/**
 * RestApi implementation of a CompanyProfile resource...
 */
angular.module('resources.unitedStates', ['ngResource']);
angular.module('resources.unitedStates', []).factory('UnitedStates', function($resource) {

    var unitedStates = $resource('/unitedStates/:action/:id', {id:'@id'},   {
        list:     {method: 'GET',  params:  {action: 'list'}, isArray: true},     // list objects
        search:   {method: 'GET',  params:  {action: 'search'}, isArray: true},   // search objects with ?q=mysearchterm
        get:      {method: 'GET',  params:  {action: 'get'}    }                  // get one object based on the passed in ID
    });

    return unitedStates;
});
