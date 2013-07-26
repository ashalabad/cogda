angular.module('lead.Utils', ['common.helperFuncs']).factory('LeadUtils', ['$filter', function ($filter) {

    var getLobFromSelect = function (leadLineOfBusiness, linesOfBusiness) {
        return leadLineOfBusiness.lineOfBusiness === undefined ? undefined : $filter('findById')(linesOfBusiness, leadLineOfBusiness.lineOfBusiness.id);
    }

    var getLobFromSelect = function (leadLineOfBusiness, linesOfBusiness) {
        return leadLineOfBusiness.lineOfBusiness === undefined ? undefined : $filter('findById')(linesOfBusiness, leadLineOfBusiness.lineOfBusiness.id);
    }

    var entityFromSelect = function(entity, propertyName, selectOptions) {
        return entity[propertyName] === undefined ? undefined : $filter('findById')(selectOptions, entity[propertyName].id);
    }

    return {
        getLobFromSelect: getLobFromSelect
    }
}]);