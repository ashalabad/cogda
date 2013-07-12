angular.module('companyProfileApp', ['resources.restApi', 'common.helperFuncs', 'resources.logger', 'ngGrid', 'resources.companyProfile'])

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
    .controller('editCompanyProfileController', ['$scope', '$routeParams', '$location', 'CompanyProfile', 'Logger',
        function($scope, $routeParams, $location, CompanyProfile, Logger){
            /*******************************************************************/
            /*                      init variables                             */
            /*******************************************************************/
            $scope.editingCompanyProfile = false; // default this to false
            $scope.message = '';
            $scope.errors = [];
            var original = {};

            $scope.companyProfile = {};
            CompanyProfile.get($routeParams, function(data) {
                $scope.companyProfile = data;
            }, function(){ Logger.error("Resource Not Found", "Error"); });

            /*******************************************************************/
            /*                      action methods                             */
            /*******************************************************************/

            /**
             * Update the Company Profile using the injected CompanyProfile factory
             * @param companyProfile
             */
            $scope.updateCompanyProfile = function(companyProfile){
                CompanyProfile.update(companyProfile).$then(updateSuccessCallback, updateErrorCallback);
            };

            /**
             * updateSuccessCallback - success handler for successful update
             * @param response
             */
            var updateSuccessCallback = function(response){
                // apply the success message to toastr
                $scope.editingCompanyProfile = false;
                Logger.success("Company Profile Updated Successfully", "Success");
            };

            /**
             * updateErrorCallback - error handler for unsuccessful update
             * @param response
             */
            var updateErrorCallback = function(response){
                // apply errors to the $scope.errors object
                Logger.formValidationMessageBuilder(response, $scope, $scope.companyProfileForm);
            }

            /**
             * Go back to the root path at /companyProfile
             */
            $scope.showCompanyProfile = function(){
                $location.path('/');
            }

            /**
             * Prepare the CompanyDetail form for editing
             */
            $scope.editCompanyProfile = function(){
                $scope.editingCompanyProfile = true;
            }

            /**
             * Dispense with the editing and go back to showing the CompanyProfile detail information
             */
            $scope.showCompanyProfile = function(){
                $scope.editingCompanyProfile = false;
            }

            $scope.canSave = function() {
                return $scope.companyProfileDetailForm.$valid;
            };


//            $scope.addCompanyProfilePhoneNumber = function(companyProfilePhoneNumber){
//
//            };
//
//            $scope.addCompanyProfileEmailAddress = function(companyProfileAddress){
//
//            };
        }])
    .controller('editCompanyProfileAddressController', ['$scope', '$routeParams', '$location', 'RestApi', 'Logger',
        function($scope, $routeParams, $location, RestApi, Logger){
            $scope.message = '';
            $scope.errors = [];
            var original = {};

            $scope.editCompanyProfileAddressForm = {};


            // This method is here so that we can display the full JSON of the passed in object
            // The built in json filter removes anythign starting with a $ so you don't get to see the
            // $dirty, $pristine, etc flags
            $scope.toJSON = function(obj) {
                return JSON.stringify(obj, null, 2);
            };

            $scope.editingCompanyProfileAddress = false; // default this to false

            $scope.editCompanyProfileAddress = function(){
                $scope.editingCompanyProfileAddress = true;
            };

            $scope.canSave = function() {
                return $scope.editCompanyProfileAddressForm.$valid;
            };

            $scope.updateCompanyProfileAddress = function(companyProfileAddress){
                $scope.editingCompanyProfileAddress = false;
            };

            /**
             * Create a new Company Profile Address
             * @param companyProfileAddress
             */
            $scope.saveCompanyProfileAddress = function(companyProfileAddress){
                if(!$scope.companyProfile.companyProfileAddresses){
                    $scope.companyProfile.companyProfileAddresses = [];
                }

                $scope.companyProfile.companyProfileAddresses.push(companyProfileAddress);
                CompanyProfile.save($scope.companyProfile, $routeParams,
                    function(data){
                        for(i in arguments){
                            console.log(i + ": " + arguments[i]);
                        }
                        Logger.messageBuilder(data, $scope);
                    },
                    function(){
                        for(i in arguments){
                            console.log(i + ": " + arguments[i]);
                        }
                        console.log("Error throwing!");
                    });
            };

        }])
    .controller('addCompanyProfileAddressController', ['$scope', '$routeParams', '$location', 'RestApi', 'Logger',
        function($scope, $routeParams, $location, RestApi, Logger){
            $scope.message = '';
            $scope.errors = [];
            var original = {};

            $scope.editCompanyProfileAddressForm = {};



        }]);


