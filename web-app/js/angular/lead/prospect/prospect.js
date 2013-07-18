angular.module('prospectApp', ['ui.bootstrap', 'resources.restApi', 'common.helperFuncs', 'resources.logger', 'ngGrid',
        'resources.prospect', 'resources.unitedStates', 'resources.SupportedCountryCodes', 'resources.leadSubTypes',
        'resources.noteType', 'resources.businessTypes', 'resources.leadService'])

    .config(function ($routeProvider) {
        $routeProvider.
            when('/list', {templateUrl: '/prospect/listPartial', controller: 'ListProspectCtrl'}).
            when('/edit/:id', {templateUrl: '/prospect/editPartial', controller: 'EditProspectCtrl' }).
            when('/create', {templateUrl: '/prospect/createPartial', controller: 'CreateProspectCtrl' }).
            when('/show/:id', {templateUrl: '/prospect/showPartial', controller: 'ShowProspectCtrl' }).
            otherwise({ redirectTo: '/list' });
    })
    .controller('ListProspectCtrl', ['$scope', '$routeParams', '$location', 'RestApi', 'Logger',
        function ($scope, $routeParams, $location, RestApi, Logger) {
            $scope.prospects = [];
            $scope.showDetails = '<div class="ngCellText"><button id="showBtn" type="button" class="btn-mini btn-primary" ng-click="show(row.entity)" ><i class="icon-eye-open"></i>Details</button></div>';
            $scope.editDetails = '<div class="ngCellText"><button id="editBtn" type="button" class="btn-mini btn-primary" ng-click="edit(row.entity)" ><i class="icon-edit"></i>Edit</button></div>';
            $scope.rowTemplate = '<div ng-style="{\'cursor\': row.cursor, \'z-index\': col.zIndex() }" ng-repeat="col in renderedColumns" ng-class="col.colIndex()" data-ng-dblclick="edit(row)" class="ngCell {{col.cellClass}}" ng-cell></div>';
            $scope.selectedProspects = [];
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
                data: 'prospects',
                columnDefs: [
                    {field: 'clientId', displayName: 'Client Id'},
                    {field: 'clientName', displayName: 'Client Name'},
                    {field: 'businessType', displayName: 'Business Type'},
                    {field: 'contactName', displayName: 'Contact Name'},
                    {field: 'phoneNumber', displayName: 'Phone Number'},
                    {field: 'email', displayName: 'Email'},
                    {field: 'createdOn', displayName: 'Created On'},
                    {cellTemplate: $scope.showDetails, displayName: "Details", width: '7%'},
                    {cellTemplate: $scope.editDetails, displayName: "Edit", width: 'auto'}
                ],
                selectedItems: $scope.selectedItems,
                enableColumnResize: true,
                multiSelect: false,
                enablePaging: true,
                showFooter: true,
                pagingOptions: $scope.pagingOptions,
//                cellTemplate: '<div style="word-wrap: normal" title="{{row.getProperty(col.field)}}">{{row.getProperty(col.field)}}</div>',
//                showFilter: true,
                filterOptions: $scope.filterOptions
//                rowTemplate: $scope.rowTemplate
            };


            var baseUrl = '/prospect/';
            var Prospect = RestApi.getRest(baseUrl);
            Prospect.list($routeParams, function (list, headers) {
                $scope.prospects = list;
                $scope.total = parseInt(headers('X-Pagination-Total'));
            }, angular.noop());
        }])
    .controller('EditProspectCtrl', ['$scope', '$routeParams', '$location', 'Prospect', 'Logger', 'UnitedStates', 'SupportedCountryCodes', 'LeadSubTypes', 'NoteType', 'BusinessTypes',
        function ($scope, $routeParams, $location, Prospect, Logger, UnitedStates, SupportedCountryCodes, LeadSubTypes, NoteType, BusinessTypes) {
            $scope.title = 'Prospect';
            $scope.editingLead = false;
            $scope.message = '';
            $scope.errors = [];
            $scope.lead = {};
            $scope.lead.leadAddresses = [];
            $scope.lead.leadContacts = [];

            $scope.countryCodes = SupportedCountryCodes.list();
            $scope.leadSubTypes = LeadSubTypes.list();
            $scope.noteTypes = NoteType.list();
            $scope.businessTypes = BusinessTypes.list();

            UnitedStates.list().$then(function (response) {
                $scope.states = response.data;
            });

            Prospect.get($routeParams, function (data) {
                $scope.lead = data;
            }, function () {
                Logger.error("Resource Not Found", "Error");
            });

            $scope.updateLead = function (lead) {
                Prospect.update(lead).$then(updateSuccessCallback, updateErrorCallBack);
                $scope.cancelEditLead();
            };


            $scope.cancelEditLead = function () {
                $scope.editingLead = false;
            };

            $scope.editLead = function () {
                $scope.editingLead = true;
            };

            $scope.clearSearch = function clearSearch() {
                $scope.searchString = "";
            };

            var updateSuccessCallback = function (response) {
                Logger.success("Prospect Updated Successfully", "Success");
            };

            var updateErrorCallBack = function (response) {
                Logger.formValidationMessageBuilder(response, $scope, $scope.leadForm);
            }
        }])
    .controller('CreateProspectCtrl', ['$scope', '$routeParams', '$location', 'Prospect', 'Logger', 'UnitedStates', 'SupportedCountryCodes', 'LeadSubTypes', 'NoteType', 'BusinessTypes',
        function ($scope, $routeParams, $location, Prospect, Logger, UnitedStates, SupportedCountryCodes, LeadSubTypes, NoteType, BusinessTypes) {
            $scope.lead = {};
            $scope.lead.leadAddresses = [];
            $scope.lead.leadAddresses.push({primaryAddress: true});
            $scope.lead.leadContacts = [];
            $scope.lead.leadContacts.push({primaryContact: true, leadContactEmailAddresses: [
                {primaryEmailAddress: true}
            ], leadContactPhoneNumbers: [
                {primaryPhoneNumber: true}
            ]});
            $scope.lead.leadNotes = [];
            $scope.lead.leadNotes.push({});
            $scope.errors = [];
            $scope.message = '';
            UnitedStates.list().$then(function (response) {
                $scope.states = response.data;
            });
            $scope.countryCodes = SupportedCountryCodes.list();
            $scope.leadSubTypes = LeadSubTypes.list();
            $scope.noteTypes = NoteType.list();
            $scope.businessTypes = BusinessTypes.list();

            var saveSuccessCallback = function (response) {
                Logger.success("Prospect Saved Successfully", "Success");
                $location.path('/show' + response.id);
            };

            var saveErrorCallback = function (response) {
                Logger.formValidationMessageBuilder(response, $scope, $scope.leadForm);
            };

            $scope.canSave = function () {
                return $scope.leadForm.$valid;
            };

            $scope.saveProspect = function (lead) {
                Prospect.save(lead).$then(saveSuccessCallback, saveErrorCallback);
            };

            $scope.leadNaicsChecked = function () {

            }
        }])
    .controller('ShowProspectCtrl', ['$scope', '$routeParams', '$location', 'Prospect', 'Logger',
        function ($scope, $routeParams, $location, Prospect, Logger) {

        }]);
