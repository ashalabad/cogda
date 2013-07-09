/**
 * This module defines the resource mappings required by Angular JS to map to a
 * standard Grails CRUD URL scheme that uses `"/$controller/$action?/$id?"`.
 */
angular.module('resources.restApi', ['ngResource']).factory('RestApi', function($resource) {

    var restApi = {
        getRest:getRest
    }

    function getRest(baseUrl){
        return $resource(baseUrl + ':action/:id', {id: '@id'}, {
            list: {method: 'GET', params: {action: 'list'}, isArray: true},
            get: {method: 'GET', params: {action: 'get'}},
            save: {method: 'POST', params: {action: 'save'}},
            update: {method: 'POST', params: {action: 'update'}},
            delete: {method: 'POST', params: {action: 'delete'}}
        });
    }

    return restApi;
});
