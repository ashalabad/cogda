angular.module('registrationApprovalApp', ['ui.bootstrap', 'resources.restApi', 'common.helperFuncs', 'resources.logger', 'ngGrid', 'resources.registrationApproval'])

    .config(function ($routeProvider) {
        $routeProvider
            .when('/list', {templateUrl: '/registrationApproval/listPartial', controller: 'ListRegistrationApprovalController'})
            .when('/process/:id', {templateUrl: '/registrationApproval/processPartial', controller: 'ApproveRegistrationApprovalController'})
            .when('/show/:id', {templateUrl: '/registrationApproval/detailPartial', controller: 'ShowRegistrationApprovalController'})
            .otherwise({ redirectTo: '/list' });
    })
    .controller('ListRegistrationApprovalController', ['$scope', '$routeParams', '$location', 'RestApi', 'Logger', 'RegistrationApproval',
        function ($scope, $routeParams, $location, RestApi, Logger, RegistrationApproval) {

            $scope.registrationApprovals = [];
            $scope.showDetails = '<div class="ngCellText"><button  type="button" class="btn btn-primary btn-mini" data-ng-click="show(row.entity)" ><i class="icon-eye-open"></i>Details</button></div>';
            $scope.processDetails = '<div class="ngCellText"><button  type="button" class="btn btn-info btn-mini" data-ng-click="process(row.entity)" data-ng-hide="isApproved(row.entity)" ><i class="icon-edit"></i>Process</button></div>';
            $scope.rowTemplate = '<div data-ng-style="{\'cursor\': row.cursor, \'z-index\': col.zIndex() }" ng-repeat="col in renderedColumns" ng-class="col.colIndex()" data-ng-dblclick="show(row)" class="ngCell {{col.cellClass}}" ng-cell></div>';
            $scope.selectedRegistrationApprovals = [];
            $scope.pagingOptions = {
                pageSizes: [10, 25, 50, 100],
                pageSize: 10,
                currentPage: 1
            };
            $scope.process = function (item) {
                $location.path('/process/' + item.id);
            };

            $scope.show = function (item) {
                $location.path('/show/' + item.id);
            };

            $scope.isApproved = function(item) {
                return (item.registrationStatusValue == "APPROVED");
            };

            $scope.filteringText = '';

            $scope.filterOptions = {
                filterText: $scope.filteringText,
                useExternalFilter: false
            };

            $scope.gridOptions = {
                data: 'registrationApprovals',
                columnDefs: [
                    {field: 'companyName', displayName: 'Company Name'},
                    {field: 'companyTypeCode', displayName: 'Company Type'},
                    {field: 'firstName', displayName: 'First'},
                    {field: 'lastName', displayName: 'Last'},
                    {field: 'emailAddress', displayName: 'Email'},
                    {field: 'phoneNumber', displayName: 'Phone #'},
                    {field: 'registrationStatusValue', displayName: 'Status'},
                    {cellTemplate: $scope.processDetails, displayName: "", width: '10%'},
                    {cellTemplate: $scope.showDetails, displayName: "", width: '7%'}
                ],
                selectedItems: $scope.selectedItems,
                enableColumnResize: true,
                multiSelect: false,
                showFooter: true,
                pagingOptions: $scope.pagingOptions,
                filterOptions: $scope.filterOptions
            };

            var baseUrl = '/registrationApproval/';
            RegistrationApproval.list($routeParams, function (list, headers) {
                $scope.registrationApprovals = list;
                $scope.total = parseInt(headers('X-Pagination-Total'));
            }, angular.noop());


        }])

    .controller('ApproveRegistrationApprovalController', ['$scope', '$routeParams', '$location', 'RestApi', 'Logger', 'RegistrationApproval',
        function ($scope, $routeParams, $location, RestApi, Logger, RegistrationApproval) {
            $scope.errors = {};
            $scope.registration = {};
            $scope.approveButtonClickable = false;
            $scope.rejectButtonClickable = true;
            $scope.registration = RegistrationApproval.get({id:$routeParams.id});

            $scope.isRejectButtonClickable = function(){
                return $scope.rejectButtonClickable;
            }

            $scope.toggleRejectButtonClickable = function(){
                $scope.rejectButtonClickable = !$scope.rejectButtonClickable;
            }

            $scope.isApproveButtonClickable = function(){
                return $scope.processRegistrationForm.$valid;
            };

            $scope.toggleApproveButtonClickable = function(){
                $scope.approveButtonClickable = !$scope.approveButtonClickable;
            };

            var processingApproval = false;
            $scope.isProcessingApproval = function(){
                return processingApproval;
            }
            $scope.toggleProcessingApproval = function(){
                processingApproval = !processingApproval;
            }

            $scope.approveRegistration = function(processRegistrationForm) {
                $scope.toggleProcessingApproval();

                var approvalRequest = {
                    id: $routeParams.id,
                    subDomain: $scope.subDomain
                };

                RegistrationApproval.approve(approvalRequest).$then(approveSuccessCallback, approveErrorCallback);
            }

            /**
             * approveSuccessCallback - success handler for successful approval
             * @param response
             */
            var approveSuccessCallback = function(response){
                Logger.success("Registration Approved Successfully - Customer On-boarding Process Completed", "Success"); // apply the success message to toastr
                $location.path('/list');
            };

            /**
             * approveErrorCallback - error handler for unsuccessful approval
             * @param response
             */
            var approveErrorCallback = function(response){
                $scope.toggleProcessingApproval();
                Logger.error("Rejected Sub Domain Value " + $scope.subDomain, "Field Error");
                $scope.subDomain = "";
                // apply errors to the $scope.errors object
                Logger.errorValidationMessageBuilder(response, $scope);
            }

            $scope.rejectRegistration = function(processRegistrationForm) {
                $scope.toggleApproveButtonClickable();
                $scope.toggleRejectButtonClickable();
                RegistrationApproval.reject($routeParams).$then(rejectSuccessCallback, rejectErrorCallback);
            }

            /**
             * rejectSuccessCallback - success handler for successful approval
             * @param response
             */
            var rejectSuccessCallback = function(response){
                Logger.success("Registration Rejected Successfully", "Success"); // apply the success message to toastr
                $location.path('/list');
            };

            /**
             * approveErrorCallback - error handler for unsuccessful approval
             * @param response
             */
            var rejectErrorCallback = function(response){
                $scope.toggleApproveButtonClickable();
                $scope.toggleRejectButtonClickable();
                Logger.error("Rejected Sub Domain Value " + $scope.subDomain, "Field Error");
                $scope.subDomain = "";
                // apply errors to the $scope.errors object
                Logger.errorValidationMessageBuilder(response, $scope);
            }

        }])
;
