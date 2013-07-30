angular.module('resources.SubmissionBuilder', ['ngResource']).factory('SubmissionBuilder', function($resource) {

    return $resource('/submissionBuilder/:action/:id', {id: '@id'},   {
        list:     {method: 'GET',  params:  {action: 'list'}, isArray: true},     // list objects
        search:   {method: 'GET',  params:  {action: 'search'}, isArray: true},   // search objects with ?q=mysearchterm
        get:      {method: 'GET',  params:  {action: 'get'}    },                 // get one object
        save:     {method: 'POST', params:  {action: 'save'}   },                 // create a new object
        update:   {method: 'POST', params:  {action: 'update'} },                 // update an object
        delete:   {method: 'POST', params:  {action: 'delete'} },                  // delete an object
        createParentSubmission:     {method: 'POST', params:  {action: 'createParentSubmission'}   },
        createChildSubmission:     {method: 'POST', params:  {action: 'createChildSubmission'}   },
        saveChildSubmission:     {method: 'POST', params:  {action: 'saveChildSubmission'}   }
});

});
