/**
 * RestApi implementation of a CompanyProfileAddress resource...
 */
angular.module('resources.companyProfileAddress', ['resources.restApi']);
angular.module('resources.companyProfileAddress', []).factory('CompanyProfileAddress', function(RestApi) {
    return RestApi.getRest('/companyProfileAddress/');
});
