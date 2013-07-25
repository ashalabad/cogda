
angular.module('resources.AccountContactLink', ['ngResource']).factory('AccountContactLink', function($resource) {

        return $resource('/accountContactLink/:action/:id', {id: '@id'},   {
            primaryContact:{method: 'GET',  params:  {action: 'primaryContact'}},
            availableAccounts:{method: 'GET',  params:  {action: 'availableAccounts'}, isArray: true},
            accounts:{method: 'GET',  params:  {action: 'accounts'}},
            accountContacts:{method: 'GET',  params:  {action: 'accountContacts'}},
            allMarketAccountContactLinks:{method: 'GET',  params:  {action: 'allMarketAccountContactLinks'}, isArray: true},
            favoriteMarketAccountContactLinks:{method: 'GET',  params:  {action: 'favoriteMarketAccountContactLinks'}, isArray: true},
            allAccountContactMarketLinks:{method: 'GET',  params:  {action: 'allAccountContactMarketLinks'}, isArray: true},
            availableAccountContacts:{method: 'GET',  params:  {action: 'availableAccountContacts'}, isArray: true},
            designatePrimary:     {method: 'POST', params:  {action: 'designatePrimary'}   },
            create:     {method: 'POST', params:  {action: 'create'}   },
            get:      {method: 'GET',  params:  {action: 'get'}    },
            save:     {method: 'POST', params:  {action: 'save'}   },
            update:   {method: 'POST', params:  {action: 'update'} },
            delete:   {method: 'POST', params:  {action: 'delete'} }

        });

});
