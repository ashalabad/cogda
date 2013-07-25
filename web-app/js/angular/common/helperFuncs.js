angular.module('common.helperFuncs', []).factory('SelectHelper',function () {
    var buildIndex = function (source, property) {
        var tempArray = [];
        for (var i = 0, len = source.length; i < len; ++i) {
            tempArray[source[i][property]] = source[i];
        }
        return tempArray;
    };

    return {
        buildIndex: buildIndex

    };
}).factory("DateHelper", ['$filter', function ($filter) {
        var getFormattedDate = function (date) {
            var dateFormat = "MMM d, yyyy hh:mm:ss a";
            return date === undefined ? date : $filter('date')(date, dateFormat);
        };

        return {
            getFormattedDate: getFormattedDate
        };
    }]);