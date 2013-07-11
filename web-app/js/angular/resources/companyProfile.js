/**
 * RestApi implementation of a CompanyProfile resource...
 * Work in progress
 */
angular.module('resources.companyProfile', ['ngResource', 'resources.restApi', 'resources.logger']);
angular.module('resources.companyProfile', []).factory('CompanyProfile', function($resource, RestApi, Logger) {
    var baseUrl = "/companyProfile/";

    var companyProfile = {
        get:getCompanyProfile,
        update:updateCompanyProfile
    }

    function getCompanyProfile(){
        var CompanyProfile = RestApi.getRest(baseUrl);
        return CompanyProfile.get({}, $routeParams, {});
    }

    function updateCompanyProfile(companyProfile){
        console.log("Implement me!");
        return companyProfile;
    }

    return companyProfile;
});