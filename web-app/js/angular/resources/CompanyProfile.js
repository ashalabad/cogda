/**
 * RestApi implementation of a CompanyProfile resource...
 */
angular.module('resources.companyProfile', ['resources.restApi']);
angular.module('resources.companyProfile', []).factory('CompanyProfile', function(RestApi) {
    return RestApi.getRest('/companyProfile/');
});