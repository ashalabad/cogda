
angular.module('resources.AccountContactAddress', ['ngResource']).factory('AccountContactAddress', function($resource) {

        return $resource('/accountContactAddress/:action/:id', {id: '@id'},   {
            delete:   {method: 'POST', params:  {action: 'delete'} }
        });

});
