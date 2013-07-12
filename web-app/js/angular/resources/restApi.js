/**
 * This module defines the resource mappings required by Angular JS to map to a
 * standard Grails CRUD URL scheme that uses `"/$controller/$action?/$id?"`.
 */
angular.module('resources.restApi', ['ngResource']).factory('RestApi', function($resource) {

    var restApi = {
        getRest:getRest
    }

    function getRest(baseUrl){
        return $resource(baseUrl + ':action/:id', {id: '@id'},   {
            list:     {method: 'GET',  params:  {action: 'list'}, isArray: true},     // list objects
            search:   {method: 'GET',  params:  {action: 'search'}, isArray: true},   // search objects with ?q=mysearchterm
            get:      {method: 'GET',  params:  {action: 'get'}    },                 // get one object
            save:     {method: 'POST', params:  {action: 'save'}   },                 // create a new object
            update:   {method: 'POST', params:  {action: 'update'} },                 // update an object
            delete:   {method: 'POST', params:  {action: 'delete'} }                  // delete an object
        });
    }

    return restApi;
});
