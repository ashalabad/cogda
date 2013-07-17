/**
 * RestApi implementation of a CompanyProfilePhoneNumber resource...
 */
angular.module('resources.companyProfilePhoneNumber', ['resources.restApi']);
angular.module('resources.companyProfilePhoneNumber', []).factory('CompanyProfilePhoneNumber', function(RestApi) {
    return RestApi.getRest('/companyProfilePhoneNumber/');
});
