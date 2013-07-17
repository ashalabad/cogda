angular.module('companyProfileApp', ['resources.restApi', 'common.helperFuncs', 'resources.logger', 'ngGrid', 'resources.companyProfile',
    'resources.companyProfileAddress', 'resources.unitedStates', 'resources.internalUserProfile', 'resources.companyProfileContact'])

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
    .controller('editCompanyProfileController', ['$scope', '$routeParams', '$location', 'CompanyProfile', 'Logger', '$window',
        function($scope, $routeParams, $location, CompanyProfile, Logger, $window){
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
            }, function(){
                Logger.error("Company Profile Was Not Found", "Error");
            });

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
                $scope.editingCompanyProfile = false;
                Logger.success("Company Profile Updated Successfully", "Success"); // apply the success message to toastr
                $window.scrollTo(0,0);
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


//            $scope.addCompanyProfilePhoneNumber = function(companyProfilePhoneNumber){
//
//            };
//
//            $scope.addCompanyProfileEmailAddress = function(companyProfileAddress){
//
//            };
        }])
    .controller('editCompanyProfileAddressController', ['$scope', '$routeParams', '$location',
                'CompanyProfileAddress', 'Logger', 'UnitedStates', '$anchorScroll', '$timeout',
        function($scope, $routeParams, $location,
                 CompanyProfileAddress, Logger, UnitedStates, $anchorScroll, $timeout){

            /*******************************************************************/
            /*                      init variables                             */
            /*******************************************************************/

            $scope.message = '';
            $scope.errors = [];
            $scope.companyProfileAddressForm = undefined;

            UnitedStates.list().$then(function(response){
                $scope.unitedStates = response.data;
            });

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

            $scope.cancelEditCompanyProfileAddress = function(){
                $scope.editingCompanyProfileAddress = false;
            };

            $scope.canSave = function(companyProfileAddressForm) {
                if(companyProfileAddressForm){
                    return companyProfileAddressForm.$valid;
                }else{
                    return true;
                }
            };

            var scrollToTarget = function(target){
                $location.hash(target);  // set the hash tag of the #CompanyProfileAddresses
                $anchorScroll(); // scroll to the #CompanyProfileAddresses location at the top of the CompanyProfileAddress(es) section
            }

            $scope.formActionsClickable = true;

            $scope.toggleFormActionsClickable = function(){
                $scope.formActionsClickable = !$scope.formActionsClickable;
            }

            /*******************************************************************/
            /*                      action methods                             */
            /*******************************************************************/

            /**
             * Updates an existing CompanyProfileAddress
             * CompanyProfileAddress objects are in a ng-repeat directive so
             * access to the $scope must be managed - hence the use of a callback function
             * that is non-promise based and the use of $then to process the actual response
             * when it is sent back from the server.
             * @param companyProfileAddress
             */
            $scope.updateCompanyProfileAddress = function(companyProfileAddress){
                $scope.formActionsClickable = false;  // set all of the form action buttons to disabled while the update processes.


                CompanyProfileAddress.update(companyProfileAddress, function(){
                        // success call back that knows the current $scope
                        $scope.editingCompanyProfileAddress = false;
                        scrollToTarget("CompanyProfileAddresses");
                    },
                    function(){
                        // error call back that knows the current $scope
                        $scope.editingCompanyProfileAddress = true;
                    }).$then(saveSuccessCallback, updateErrorCallback);

            };

            /**
             * updateSuccessCallback - success handler for successful update
             * @param response
             */
            var saveSuccessCallback = function(response){
                // apply the success message to toastr
                $scope.toggleFormActionsClickable();
                Logger.success("Company Profile Address Saved Successfully", "Success");
            };

            /**
             * updateErrorCallback - error handler for unsuccessful update
             * @param response
             */
            var updateErrorCallback = function(response){
                // apply errors to the $scope.errors object
                $scope.toggleFormActionsClickable();
                Logger.formValidationMessageBuilder(response, $scope, $scope.companyProfileAddressForm);
            }

            $scope.deleteCompanyProfileAddress = function(companyProfileAddress, idx){
                CompanyProfileAddress.delete(companyProfileAddress, function(){
                   // success
                   // Remove the CompanyProfileAddress from the array of companyProfileAddresses
                   Logger.success("Company Profile Address Deleted Successfully", "Success");
                   scrollToTarget("CompanyProfileAddresses");
                   $scope.companyProfile.companyProfileAddresses.splice(idx, 1);
                }, function(){
                   // failure
                    $scope.toggleFormActionsClickable();
                    Logger.error("Delete of Company Profile Address was Unsuccussful", "Error");
                });
            }
        }])
    .controller('addCompanyProfileAddressController', ['$scope', '$routeParams', '$location', 'CompanyProfileAddress', 'Logger', 'UnitedStates', '$anchorScroll',
        function($scope, $routeParams, $location, CompanyProfileAddress, Logger, UnitedStates, $anchorScroll){

            /*******************************************************************/
            /*                      init variables                             */
            /*******************************************************************/
            $scope.message = '';
            $scope.errors = [];

            UnitedStates.list().$then(function(response){
                $scope.unitedStates = response.data;
            });

            $scope.addingCompanyProfileAddress = false;

            $scope.addCompanyProfileAddress = function(){
                $scope.addingCompanyProfileAddress = true;
            };

            $scope.formActionsClickable = true;

            $scope.toggleFormActionsClickable = function(){
                $scope.formActionsClickable = !$scope.formActionsClickable;
            }

            // Use defaultForm to reset the Add Address Form
            var defaultForm = {
                published: false,
                primaryAddress: false,
                address: {
                    addressOne: "",
                    addressTwo: "",
                    addressThree: "",
                    zipcode: "",
                    city: "",
                    state: "",
                    country: ""
                }
            }

            $scope.cancelAddCompanyProfileAddress = function(){
                $scope.companyProfileAddressForm.$setPristine();
                $scope.companyProfileAddress = angular.copy(defaultForm);
                $scope.addingCompanyProfileAddress = false;
                $location.hash("CompanyProfileAddresses");  // set the hash tag of the #CompanyProfileAddresses
                $anchorScroll(); // scroll to the #CompanyProfileAddresses location at the top of the CompanyProfileAddress(es) section
            };

            $scope.canSave = function(companyProfileAddressForm) {
                if(companyProfileAddressForm){
                    return companyProfileAddressForm.$valid;
                }else{
                    return true;
                }
            };

            /*******************************************************************/
            /*                      action methods                             */
            /*******************************************************************/

            /**
             * Create a new Company Profile Address
             * @param companyProfileAddress
             */
            $scope.saveCompanyProfileAddress = function(companyProfileAddress){
                $scope.formActionsClickable = false;  // set all of the form action buttons to disabled while the save processes.
                var companyProfile = {
                    id:$scope.companyProfile.id
                };
                companyProfileAddress.companyProfile = companyProfile;  // make sure that the companyProfile is associated with this companyProfileAddress
                CompanyProfileAddress.save(companyProfileAddress, function(data){
                    companyProfileAddress.id = data.id;
                    $scope.companyProfile.companyProfileAddresses.push(companyProfileAddress);
                    $scope.cancelAddCompanyProfileAddress();

                }, function(){

                }).$then(saveSuccessCallback, updateErrorCallback);
            };

            /**
             * updateSuccessCallback - success handler for successful update
             * @param response
             */
            var saveSuccessCallback = function(response){
                $scope.toggleFormActionsClickable();
                // apply the success message to toastr
                Logger.success("Company Profile Address Added Successfully", "Success");
            };

            /**
             * updateErrorCallback - error handler for unsuccessful update
             * @param response
             */
            var updateErrorCallback = function(response){
                $scope.toggleFormActionsClickable();
                // apply errors to the $scope.errors object
                Logger.formValidationMessageBuilder(response, $scope, $scope.companyProfileAddressForm);
            }
        }])
    .controller('editCompanyProfileContactController', ['$scope', '$routeParams', '$location',  'InternalUserProfile', 'Logger', 'UnitedStates', '$anchorScroll', 'CompanyProfileContact',
        function($scope, $routeParams, $location,  InternalUserProfile, Logger, UnitedStates, $anchorScroll, CompanyProfileContact){

            $scope.toggleDisableDeleteButton = function(){
                $scope.disableDeleteButton = !$scope.disableDeleteButton;
            }
            $scope.disableDeleteButton = false;
            $scope.deleteCompanyProfileContact = function(companyProfileContact, idx){
                $scope.toggleDisableDeleteButton();
                CompanyProfileContact.delete(companyProfileContact, function(){
                    // success
                    $scope.companyProfile.companyProfileContacts.splice(idx, 1);
                }, function(){
                    // error
                    $scope.toggleDisableDeleteButton();
                }).$then(successfulDeleteCallback, errorDeleteCallback);
            };

            var successfulDeleteCallback = function(response){
                Logger.success("Company Profile Contact Removed Successfully", "Success");
            };

            var errorDeleteCallback = function(response){
                Logger.error("Error Removing Company Profile Contact", "Error");
            };
        }])
    .controller('addCompanyProfileContactController', ['$scope', '$routeParams', '$location', 'InternalUserProfile', 'Logger', 'UnitedStates', '$anchorScroll', 'limitToFilter', 'CompanyProfileContact',
                function($scope, $routeParams, $location, InternalUserProfile, Logger, UnitedStates, $anchorScroll, limitToFilter, CompanyProfileContact){

        /*******************************************************************/
        /*                      init variables                             */
        /*******************************************************************/
        $scope.message = '';
        $scope.errors = [];

        $scope.q = "";  // the userProfile search query
        $scope.searchResults = [];

        $scope.clearUserProfileSearch = function(){
            $scope.q = "";
            $scope.searchResults = [];
        };

        $scope.filterUserProfileResults = function(item) {
            var foundOne = false;
            for(var i = 0; i < $scope.companyProfile.companyProfileContacts.length; i++){
                if($scope.companyProfile.companyProfileContacts[i].userProfile.id == item.id){
                    foundOne = true;
                    break;
                }
            }
            return !foundOne;
        };

        /*******************************************************************/
        /*                      action methods                             */
        /*******************************************************************/

        $scope.userProfileSearch = function(){
            var searchObj = {
                q:$scope.q
            };

            InternalUserProfile.search(searchObj, function(data){
                $scope.searchResults = data;
            });
        };

        /**
         * Create a new Company Profile Contact
         * @param companyProfileContact
         */
        $scope.saveCompanyProfileContact = function(userProfile, idx){
            var companyProfileContact = {
                companyProfile: {
                    id:$scope.companyProfile.id
                },
                userProfile:userProfile
            };
            CompanyProfileContact.save(companyProfileContact, function(data){
                $scope.companyProfile.companyProfileContacts.push(data);
                $scope.searchResults.splice(idx, 1);
            }, function(){

            }).$then(saveSuccessCallback, updateErrorCallback);
        };

        /**
         * updateSuccessCallback - success handler for successful add
         * @param response
         */
        var saveSuccessCallback = function(response){
            // apply the success message to toastr
            Logger.success("Company Profile Contact Added Successfully", "Success");
        };

        /**
         * saveErrorCallback - error handler for unsuccessful add
         * @param response
         */
        var updateErrorCallback = function(response){
            // apply errors to the $scope.errors object
            Logger.error("Error adding Company Profile Contact", "Error");
        }
    }]);