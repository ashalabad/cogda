angular.module('companyProfileApp', ['resources.restApi', 'common.helperFuncs', 'resources.logger', 'ngGrid'])

    .config(function ($routeProvider) {
        $routeProvider
            .when(
            '/',
                {
                    controller: 'showCompanyProfileController',
                    templateUrl: '/companyProfile/showPartial'
                }
            ).when('/editProfile',
                {
                    controller: 'editCompanyProfileController',
                    templateUrl: '/companyProfile/editPartial'
                }
            )
            .otherwise({ redirectTo: '/' });
    })
//    .run(function($rootScope) {
//        $rootScope.$on('$routeChangeSuccess', function(e, current){
//            var direction = current && previous && current.depth
//            $rootScope.viewSlideAnimation = {
//                enter: direction ? 'slide-left-enter' : 'slide-right-leave',
//                leave: direction ? 'slide-right-leave' : 'slide-left-enter'
//
//            }
//        });
//    })
    .controller('showCompanyProfileController', ['$scope', '$routeParams', '$location', 'RestApi', 'Logger',
        function($scope, $routeParams, $location, RestApi, Logger){

            $scope.toJSON = function(obj) {
                return JSON.stringify(obj, null, 2);
            };

            $scope.editCompanyProfile = function(){
                $location.path('/editProfile')
            }

            $scope.companyProfile = {};
            var baseUrl = '/companyProfile/';
            var CompanyProfile = RestApi.getRest(baseUrl);
            CompanyProfile.get({}, $routeParams, function(data) {
                $scope.companyProfile = data;
            }, {});
        }])
    .controller('editCompanyProfileController', ['$scope', '$routeParams', '$location', 'RestApi', 'Logger',
        function($scope, $routeParams, $location, RestApi, Logger){

            $scope.toJSON = function(obj) {
                return JSON.stringify(obj, null, 2);
            };

            $scope.showCompanyProfile = function(){
                $location.path('/')
            }

            $scope.companyProfile = {};
            var baseUrl = '/companyProfile/';
            var CompanyProfile = RestApi.getRest(baseUrl);
            CompanyProfile.get({}, $routeParams, function(data) {
                $scope.companyProfile = data;
            }, {});
        }])
    .controller('editCompanyProfileAddressController', ['$scope', '$routeParams', '$location', 'RestApi', 'Logger',
        function($scope, $routeParams, $location, RestApi, Logger){
            console.log("implement me");
        }
    ]);


