
angular.module('resources.AccountContactLink', ['ngResource']).factory('AccountContactLink', function($resource) {

        return $resource('/accountContactLink/:action/:id', {id: '@id'},   {
            primaryContact:{method: 'GET',  params:  {action: 'primaryContact'}},
            accountContacts:{method: 'GET',  params:  {action: 'accountContacts'}},  // get one object
            save:     {method: 'POST', params:  {action: 'save'}   },                 // create a new object

        });

});
