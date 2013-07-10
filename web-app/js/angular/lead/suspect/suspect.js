angular.module('suspectApp', ['resources.restApi', 'common.helperFuncs', 'resources.logger', 'ngGrid'])

    .config(function ($routeProvider) {
        $routeProvider.
            when('/list', {templateUrl: '/suspect/listPartial', controller: 'ListSuspectCtrl'}).
            when('/edit/:id', {templateUrl: '/suspect/editPartial', controller: 'EditSuspectCtrl' }).
            when('/create', {templateUrl: '/suspect/createPartial', controller: 'CreateCtrl' }).
            when('/show/:id', {templateUrl: '/suspect/showPartial', controller: 'ShowCtrl' }).
            otherwise({ redirectTo: '/list' });
    })
    .controller('ListSuspectCtrl', ['$scope', '$routeParams', '$location', 'RestApi', 'Logger',
        function ($scope, $routeParams, $location, RestApi, Logger) {
            $scope.suspects = [];
            $scope.gridOptions = { data: 'suspects' };
            var baseUrl = '/suspect/';
            var Suspect = RestApi.getRest(baseUrl);
            Suspect.list($routeParams, function (list, headers) {
                $scope.suspects = list;
                $scope.total = parseInt(headers('X-Pagination-Total'));
            }, Logger.messageBuilder.(response, $scope));
        }])
    .controller('EditSuspectCtrl', ['$scope', '$routeParams', '$location', 'RestApi',
        function ($scope, $routeParams, $location, RestApi) {

        }]);
