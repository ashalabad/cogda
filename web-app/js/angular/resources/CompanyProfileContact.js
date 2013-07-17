/**
 * RestApi implementation of a CompanyProfileContact resource...
 */
angular.module('resources.companyProfileContact', ['resources.restApi']);
angular.module('resources.companyProfileContact', []).factory('CompanyProfileContact', function(RestApi) {
    return RestApi.getRest('/companyProfileContact/');
});
