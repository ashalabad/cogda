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

        var getBsFormattedDate = function (date) {
            var dateFormat = "MM/dd/yyyy";
            return date === undefined ? date : $filter('date')(new Date(date), dateFormat);
        };

        return {
            getFormattedDate: getFormattedDate,
            getBsFormattedDate: getBsFormattedDate
        };
    }])
    .filter('bsDateFilter', function () {
        return function (d) {
            if (d instanceof Date) {
                return new Date(d.getTime() + (new Date()).getTimezoneOffset() * 60000);
            } else {
                var date = new Date(d);
                if (isNaN(date.getTime()))
                    return d;
                else {
                    return new Date(date.getTime() + (new Date()).getTimezoneOffset() * 60000);
                }
            }
        }
    })
    .filter('findById', function () {
        return function (input, id) {
            var i = 0, len = input.length;
            for (; i < len; i++) {
                if (+input[i].id == +id) {
                    return input[i];
                }
            }
            return undefined;
        }
    });