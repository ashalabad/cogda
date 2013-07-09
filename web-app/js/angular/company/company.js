angular.module('companyApp', ['resources.restApi', 'common.helperFuncs', 'resources.logger', 'ngGrid'])

    .config(function ($routeProvider) {
        $routeProvider
            .when(
                '/',
                {
                    controller: 'listCompanyController',
                    templateUrl: '/company/listPartial'
                }
            )
//            ).when('/editProfile',
//                {
//                    controller: 'editCompanyProfileController',
//                    templateUrl: '/grailsangularjs/companyProfile/editPartial'
//                }
//            )
            .otherwise({ redirectTo: '/' });
    })
    .controller('listCompanyController', ['$scope', '$routeParams', '$location', 'RestApi',
        function($scope, $routeParams, $location, RestApi){

            $scope.toJSON = function(obj) {
                return JSON.stringify(obj, null, 2);
            };

            // RestApi knows based on where this page is right now based on the page's data-base-url where to route the get() request
            $scope.companies = [];
            $scope.gridOptions = { data: 'companies' };
            var baseUrl = '/company/';
            var Company = RestApi.getRest(baseUrl);
            Company.list($routeParams, function(list, headers) {
                $scope.companies = list;
                $scope.total = parseInt(headers('X-Pagination-Total'));
            }, angular.noop());
    }]);
