angular.module('suspectApp', ['ui.bootstrap', '$strap.directives', 'resources.naicsCodeTree', 'resources.sicCodeTree',
        'resources.restApi', 'common.helperFuncs', 'resources.logger', 'ngGrid',
        'resources.suspect', 'resources.unitedStates', 'resources.SupportedCountryCodes', 'resources.leadSubTypes',
        'resources.noteType', 'resources.businessTypes', 'resources.leadService'])

    .config(function ($routeProvider) {
        $routeProvider.
            when('/list', {templateUrl: '/suspect/listPartial', controller: 'ListSuspectCtrl'}).
            when('/edit/:id', {templateUrl: '/suspect/editPartial', controller: 'EditSuspectCtrl' }).
            when('/create', {templateUrl: '/suspect/createPartial', controller: 'CreateSuspectCtrl' }).
            when('/show/:id', {templateUrl: '/suspect/showPartial', controller: 'ShowSuspectCtrl' }).
            otherwise({ redirectTo: '/list' });
    })
    .controller('ListSuspectCtrl', ['$scope', '$routeParams', '$location', 'RestApi', 'Logger',
        function ($scope, $routeParams, $location, RestApi, Logger) {
            $scope.suspects = [];
            $scope.showDetails = '<div class="ngCellText"><button id="showBtn" type="button" class="btn-mini btn-primary" ng-click="show(row.entity)" ><i class="icon-eye-open"></i>Details</button></div>';
            $scope.editDetails = '<div class="ngCellText"><button id="editBtn" type="button" class="btn-mini btn-primary" ng-click="edit(row.entity)" ><i class="icon-edit"></i>Edit</button></div>';
            $scope.rowTemplate = '<div ng-style="{\'cursor\': row.cursor, \'z-index\': col.zIndex() }" ng-repeat="col in renderedColumns" ng-class="col.colIndex()" data-ng-dblclick="edit(row)" class="ngCell {{col.cellClass}}" ng-cell></div>';
            $scope.selectedSuspects = [];
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
                data: 'suspects',
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


            var baseUrl = '/suspect/';
            var Suspect = RestApi.getRest(baseUrl);
            Suspect.list($routeParams, function (list, headers) {
                $scope.suspects = list;
                $scope.total = parseInt(headers('X-Pagination-Total'));
            }, angular.noop());
        }])
    .controller('EditSuspectCtrl', ['$scope', '$routeParams', '$location', 'Suspect', 'Logger', 'UnitedStates', 'SupportedCountryCodes', 'LeadSubTypes', 'NoteType', 'BusinessTypes',
        function ($scope, $routeParams, $location, Suspect, Logger, UnitedStates, SupportedCountryCodes, LeadSubTypes, NoteType, BusinessTypes) {
            $scope.title = 'Suspect';
            $scope.editingLead = false;
            $scope.message = '';
            $scope.errors = [];
            $scope.lead = {};
            $scope.lead.leadAddresses = [];
            $scope.lead.leadContacts = [];
            UnitedStates.list().$then(function (response) {
                $scope.states = response.data;
            });
            $scope.countryCodes = SupportedCountryCodes.list();
            $scope.leadSubTypes = LeadSubTypes.list();
            $scope.noteTypes = NoteType.list();
            $scope.businessTypes = BusinessTypes.list();

            Suspect.get($routeParams, function (data) {
                $scope.lead = data;
            }, function () {
                Logger.error("Resource Not Found", "Error");
            });

            $scope.updateLead = function (lead) {
                Suspect.update(lead).$then(updateSuccessCallback, updateErrorCallBack);
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
                Logger.success("Suspect Updated Successfully", "Success");
            };

            var updateErrorCallBack = function (response) {
                Logger.formValidationMessageBuilder(response, $scope, $scope.leadForm);
            }
        }])
    .controller('CreateSuspectCtrl', ['$scope', '$routeParams', '$location', 'Suspect', 'Logger', 'UnitedStates', 'SupportedCountryCodes', 'LeadSubTypes', 'NoteType', 'BusinessTypes',
        function ($scope, $routeParams, $location, Suspect, Logger, UnitedStates, SupportedCountryCodes, LeadSubTypes, NoteType, BusinessTypes) {
            $scope.lead = {};
            $scope.lead.leadAddresses = [];
            $scope.lead.leadAddresses.push({primaryAddress: true});
            $scope.lead.leadContacts = [];
            $scope.lead.leadContacts.push({primaryContact: true, leadContactEmailAddresses: [
                {primaryEmailAddress: true}
            ], leadContactPhoneNumbers: [
                {primaryPhoneNumber: true}
            ]});
            $scope.errors = [];
            $scope.message = '';
            UnitedStates.list().$then(function (response) {
                $scope.states = response.data;
            });
            $scope.countryCodes = SupportedCountryCodes.list();
            $scope.leadSubTypes = LeadSubTypes.list();
            $scope.noteTypes = NoteType.list();
            $scope.businessTypes = BusinessTypes.list();
            $scope.lead.linesOfBusiness = [];

            $scope.addingLeadLineOfBusiness = false;

            $scope.addLeadLineOfBusiness = function () {
                $scope.addingLeadLineOfBusiness = true;
            };

            $scope.cancelAddLeadLineOfBusiness = function () {
                $scope.addingLeadLineOfBusiness = false;
            }

            $scope.saveLeadLineOfBusiness = function (leadLineOfBusiness) {
                $scope.lead.linesOfBusiness.push(leadLineOfBusiness);
                $scope.cancelAddLeadLineOfBusiness();
            }

            var saveSuccessCallback = function () {
                Logger.success("Prospect Saved Successfully", "Success");
                $location.path('/edit/' + $scope.lead.id);
            };

            var saveErrorCallback = function (response) {
                Logger.formValidationMessageBuilder(response, $scope, $scope.leadForm);
            };

            $scope.canSave = function () {
                return $scope.leadForm.$valid;
            };

            $scope.saveSuspect = function (lead) {
                Suspect.save(lead,function (data) {
                    lead.id = data.id;
                }).$then(saveSuccessCallback, saveErrorCallback);
            };

        }])
    .controller('ShowSuspectCtrl', ['$scope', '$routeParams', '$location', 'Suspect', 'Logger',
        function ($scope, $routeParams, $location, Suspect, Logger) {

        }]);
