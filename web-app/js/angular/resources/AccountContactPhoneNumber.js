
angular.module('resources.AccountContactPhoneNumber', ['ngResource']).factory('AccountContactPhoneNumber', function($resource) {

        return $resource('/accountContactPhoneNumber/:action/:id', {id: '@id'},   {
            delete:   {method: 'POST', params:  {action: 'delete'} }
        });

});
