angular.module('suspectApp', ['resources.restApi', 'common.helperFuncs', 'resources.logger', 'ngGrid', 'resources.suspect', 'resources.unitedStates', 'resources.SupportedCountryCodes', 'resources.leadSubTypes', 'resources.noteType', 'resources.businessTypes'])

    .config(function ($routeProvider) {
        $routeProvider.
            when('/list', {templateUrl: '/suspect/listPartial', controller: 'ListSuspectCtrl'}).
            when('/edit/:id', {templateUrl: '/suspect/editPartial', controller: 'EditSuspectCtrl' }).
            when('/create', {templateUrl: '/suspect/createPartial', controller: 'CreateSuspectCtrl' }).
            when('/show/:id', {templateUrl: '/suspect/showPartial', controller: 'ShowCtrl' }).
            otherwise({ redirectTo: '/list' });
    })
    .controller('ListSuspectCtrl', ['$scope', '$routeParams', '$location', 'RestApi', 'Logger',
        function ($scope, $routeParams, $location, RestApi, Logger) {
            $scope.suspects = [];
            $scope.showDetails = '<div class="ngCellText"><button id="showBtn" type="button" class="btn-mini btn-primary" ng-click="show(row.entity)" >Details</button></div>';
            $scope.editDetails = '<div class="ngCellText"><button id="editBtn" type="button" class="btn-mini btn-primary" ng-click="edit(row.entity)" >Edit</button></div>';
            $scope.rowTemplate = '<div ng-style="{\'cursor\': row.cursor, \'z-index\': col.zIndex() }" ng-repeat="col in renderedColumns" ng-class="col.colIndex()" data-ng-dblclick="edit(row)" class="ngCell {{col.cellClass}}" ng-cell></div>'
            $scope.selectedSuspects = []
            $scope.pagingOptions = {
                pageSizes: [10, 25, 50, 100],
                pageSize: 10,
                currentPage: 1
            };
            $scope.edit = function (item) {
                $location.path('/edit/' + item.id);
            }

            $scope.show = function (item) {
                $location.path('/show/' + item.id);
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
//                    {field:'businessType.description', displayName: 'Business Type'},
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
//            }, Logger.messageBuilder.curry($scope));
            }, angular.noop());
        }])
    .controller('EditSuspectCtrl', ['$scope', '$routeParams', '$location', 'RestApi',
        function ($scope, $routeParams, $location, RestApi) {

        }])
    .controller('CreateSuspectCtrl', ['$scope', '$routeParams', '$location', 'Suspect', 'Logger', 'UnitedStates', 'SupportedCountryCodes', 'LeadSubTypes', 'NoteType', 'BusinessTypes',
        function ($scope, $routeParams, $location, Suspect, Logger, UnitedStates, SupportedCountryCodes, LeadSubTypes, NoteType, BusinessTypes) {
            $scope.lead = new Suspect
//            $scope.lead = {};
            $scope.lead.leadAddresses = [];
            $scope.lead.leadAddresses.push({primaryAddress: true});
            $scope.lead.leadContacts = [];
            $scope.lead.leadContacts.push({primaryContact: true, leadContactEmailAddresses: [{primaryEmailAddress: true}], leadContactPhoneNumbers: [{primaryPhoneNumber: true}]});
            $scope.lead.leadNotes = []
            $scope.lead.leadNotes.push({});
            $scope.errors = [];
            $scope.message = '';
            $scope.states = UnitedStates.list();
            $scope.countryCodes = SupportedCountryCodes.list();
            $scope.leadSubTypes = LeadSubTypes.list()
            $scope.noteTypes = NoteType.list();
            $scope.businessTypes = BusinessTypes.list();

            var saveSuccessCallback = function (response) {
                Logger.success("Suspect Saved Successfully", "Success");
            }

            var saveErrorCallback = function (response) {
                Logger.formValidationMessageBuilder(response, $scope, $scope.leadForm);
            }

            $scope.canSave = function () {
                return $scope.leadForm.$valid;
            }

            $scope.saveSuspect = function (lead) {
                Suspect.save(lead).$then(saveSuccessCallback, saveErrorCallback);
            }

            $scope.leadNaicsChecked = function () {

            }
        }]);
