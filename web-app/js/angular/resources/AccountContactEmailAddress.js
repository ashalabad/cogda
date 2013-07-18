
angular.module('resources.AccountContactEmailAddress', ['ngResource']).factory('AccountContactEmailAddress', function($resource) {

        return $resource('/accountContactEmailAddress/:action/:id', {id: '@id'},   {
            delete:   {method: 'POST', params:  {action: 'delete'} }
        });

});
