angular.module('registrationApprovalApp', ['ui.bootstrap', 'resources.restApi', 'common.helperFuncs', 'resources.logger', 'ngGrid', 'resources.registrationApproval'])

    .config(function ($routeProvider) {
        $routeProvider
            .when('/list', {templateUrl: '/registrationApproval/listPartial', controller: 'ListRegistrationApprovalController'})
            .otherwise({ redirectTo: '/list' });
    })
    .controller('ListRegistrationApprovalController', ['$scope', '$routeParams', '$location', 'RestApi', 'Logger', 'RegistrationApproval',
        function ($scope, $routeParams, $location, RestApi, Logger, RegistrationApproval) {

            $scope.registrationApprovals = [];
            $scope.showDetails = '<div class="ngCellText"><button id="showBtn" type="button" class="btn btn-primary btn-mini" ng-click="show(row.entity)" ><i class="icon-eye-open"></i>Details</button></div>';
            $scope.editDetails = '<div class="ngCellText"><button id="editBtn" type="button" class="btn btn-primary btn-mini" ng-click="edit(row.entity)" ><i class="icon-edit"></i>Edit</button></div>';
            $scope.rowTemplate = '<div ng-style="{\'cursor\': row.cursor, \'z-index\': col.zIndex() }" ng-repeat="col in renderedColumns" ng-class="col.colIndex()" data-ng-dblclick="edit(row)" class="ngCell {{col.cellClass}}" ng-cell></div>';
            $scope.selectedRegistrationApprovals = [];
            $scope.pagingOptions = {
                pageSizes: [10, 25, 50, 100],
                pageSize: 10,
                currentPage: 1
            };
            $scope.edit = function (item) {
                $location.path('/edit/' + item.id);
            };

            $scope.show = function (item) {
                $location.path('/edit/' + item.id);
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
                    {field: 'companyType.code', displayName: 'Company Type'},
                    {field: 'firstName', displayName: 'First'},
                    {field: 'lastName', displayName: 'Last'},
                    {field: 'emailAddress', displayName: 'Email'},
                    {field: 'phoneNumber', displayName: 'Phone #'},
                    {field: 'registrationStatus', displayName: 'Status'},
                    {cellTemplate: $scope.editDetails, displayName: "", width: 'auto'},
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


        }]);
