angular.module('resources.SupportedCountryCodes', ['resources.restApi']).factory('SupportedCountryCodes', ['RestApi', function(RestApi) {
    return RestApi.getRest(/supportedCountryCode/);
}]);
