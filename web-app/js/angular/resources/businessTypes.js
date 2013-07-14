angular.module('resources.businessTypes', ['ngResource']);
angular.module('resources.businessTypes', []).factory('BusinessTypes', function($resource) {
    var baseUrl = "/businessType/";
    return $resource(baseUrl + ':action/:id', {id: '@id'}, {
        list: {method: 'GET', params: {action: 'list'}, isArray: true},
        get: {method: 'GET', params: {action: 'get'}}
    });
});


