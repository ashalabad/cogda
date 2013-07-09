angular.module('resources.companyTypes', ['ngResource']);
angular.module('resources.companyTypes', []).factory('CompanyTypes', function($resource) {
    var baseUrl = "/companyType/";
    return $resource(baseUrl + ':action/:id', {id: '@id'}, {
        list: {method: 'GET', params: {action: 'list'}, isArray: true},
        get: {method: 'GET', params: {action: 'get'}}
    });
});


