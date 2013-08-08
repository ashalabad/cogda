angular.module('resources.registrationApproval', ['ngResource']).factory('RegistrationApproval', function($resource) {
    var restApi = {
        getRest:getRest
    }

    function getRest(baseUrl){
        return $resource(baseUrl + ':action/:id', {id: '@id'},   {
            list:     {method: 'GET',  params:  {action: 'list'}, isArray: true},       // list objects
            get:      {method: 'GET',  params:  {action: 'get'}     },                  // get one object
            delete:   {method: 'POST', params:  {action: 'delete'}  },                  // delete an object
            approve:  {method: 'POST', params:  {action:  'approve'} },
            reject:   {method: 'POST', params:  {action:  'reject'}  }
        });
    }

    return getRest('/registrationApproval/');
});