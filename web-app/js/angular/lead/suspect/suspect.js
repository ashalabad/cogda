angular.module('suspectApp', ['ui.bootstrap', '$strap.directives', 'resources.naicsCodeTree', 'resources.sicCodeTree',
        'resources.restApi', 'common.helperFuncs', 'resources.logger', 'ngGrid', 'resources.suspect',
        'resources.unitedStates', 'resources.SupportedCountryCodes', 'resources.leadSubTypes', 'resources.noteType',
        'resources.businessTypes', 'resources.leadService', 'lead.Utils', 'ui.unique'])

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
            $scope.showDetails = '<div class="ngCellText"><button id="showBtn" type="button" class="btn-mini" ng-click="show(row.entity)" ><i class="icon-eye-open"></i>Details</button></div>';
            $scope.editDetails = '<div class="ngCellText"><button id="editBtn" type="button" class="btn-mini" ng-click="edit(row.entity)" ><i class="icon-edit"></i>Edit</button></div>';
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
                    {cellTemplate: $scope.editDetails, displayName: "", width: 'auto'},
                    {cellTemplate: $scope.showDetails, displayName: "", width: '7%'}

                ],
                selectedItems: $scope.selectedItems,
                enableColumnResize: true,
                multiSelect: false,
//                enablePaging: true,
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
    .controller('EditSuspectCtrl', ['$scope', '$routeParams', '$location', 'Suspect', 'Logger', 'UnitedStates',
        'SupportedCountryCodes', 'LeadSubTypes', 'NoteType', 'BusinessTypes', 'LineOfBusiness', 'LeadLineOfBusiness',
        '$dialog', 'DateHelper', '$window', 'LeadUtils', 'LeadService', '$filter',
        function ($scope, $routeParams, $location, Suspect, Logger, UnitedStates, SupportedCountryCodes, LeadSubTypes, NoteType, BusinessTypes, LineOfBusiness, LeadLineOfBusiness, $dialog, DateHelper, $window, LeadUtils, LeadService, $filter) {
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
            $scope.shouldConvertToProspect = false;
            LineOfBusiness.list().$then(function (response) {
                $scope.linesOfBusiness = response.data;
            });

            Suspect.get($routeParams, function (data) {
                $scope.lead = data;
                $scope.undeterminedSicNodes = getUndeterminedNodeIds($scope.lead.sicCodes, 'parentSicCode');
                $scope.undeterminedNaicsNodes = getUndeterminedNodeIds($scope.lead.naicsCodes, 'parentNaicsCode');
            }, function () {
                Logger.error("Resource Not Found", "Error");
            });

            var getUndeterminedNodeIds = function (nodes, propertyName) {
                var undeterminedNodes = [];
                for (var i = 0; i < nodes.length; i++) {
                    var node = nodes[i];
                    var allParents = getAllParentIds(node, propertyName);
                    for (var j = 0; j < allParents.length; j++) {
                        var undeterminedParentId = allParents[j];
                        if (undeterminedNodes.indexOf(undeterminedParentId) === -1 && $filter('findById')(nodes, undeterminedParentId) === undefined) {
                            undeterminedNodes.push({id: undeterminedParentId});
                        }
                    }
                }
                return undeterminedNodes;
            };

            var getAllParentIds = function (node, propertyName) {
                var parents = [];
                if (node[propertyName] !== undefined) {
                    parents.push(node[propertyName].id);
                    var grandparentIds = getAllParentIds(node[propertyName], propertyName);
                    for (var i = 0; i < grandparentIds.length; i++) {
                        parents.push(grandparentIds[i]);
                    }
                }
                return parents
            };

            $scope.updateLead = function (lead) {
                lead.businessType = LeadUtils.getBusinessTypeFromSelect(lead.businessType, $scope.businessTypes);
                var formattedLead = angular.copy(lead);
                for (var i = 0; i < formattedLead.linesOfBusiness.length; i++) {
                    formattedLead.linesOfBusiness[i].targetDate = DateHelper.getFormattedDate(formattedLead.linesOfBusiness[i].targetDate);
                    formattedLead.linesOfBusiness[i].expirationDate = DateHelper.getFormattedDate(formattedLead.linesOfBusiness[i].expirationDate);
                }
                Suspect.update(formattedLead).$then(updateSuccessCallback, updateErrorCallBack);
            };

            $scope.cancelEditLead = function () {
                $scope.lead = $scope.leadCopy;
                $scope.leadCopy = {};
                closeEditLead();
            };

            var closeEditLead = function () {
                $scope.editingLead = false;
            };

            $scope.editLead = function () {
                $scope.leadCopy = angular.copy($scope.lead);
                $scope.editingLead = true;
            };

            $scope.clearSearch = function clearSearch() {
                $scope.searchString = "";
            };

            var updateSuccessCallback = function (response) {
                Logger.success("Suspect Updated Successfully", "Success");
                closeEditLead();
            };

            var updateErrorCallBack = function (response) {
                Logger.formValidationMessageBuilder(response, $scope, $scope.leadForm);
            };

            $scope.deleteSuspect = function (lead) {
                var title = "Delete Suspect";
                var msg = "Are you sure you want to delete this Suspect?";
                var btns = [
                    {result: 'cancel', label: 'Cancel'},
                    {result: 'delete', label: 'Delete', cssClass: 'btn-danger'}
                ];

                $dialog.messageBox(title, msg, btns)
                    .open()
                    .then(function (result) {
                        if (result == 'delete')
                            Suspect.delete({id: lead.id})
                                .$then(deleteSuspectSuccessCallback, deleteSuspectErrorCallback);
                    });
            };

            var deleteSuspectSuccessCallback = function (response) {
                Logger.success("Suspect Deleted Successfully");
                $location.path('/list/');
            };

            var deleteSuspectErrorCallback = function (response) {
                Logger.messageBuilder(response, $scope);
            };

            var convertToProspectSuccessCallback = function (response) {
                Logger.success("Suspect Converted to Prospect");
                console.log('absUrl:');
                console.log($location.absUrl());
                var currentUrl = $location.absUrl();
                $window.location.replace(currentUrl.replace('suspect', 'prospect'));
            };

            var convertToProspectErrorCallback = function (response) {
                LeadService.validateAllForms(response, $scope);
            };

            $scope.convertToProspect = function (lead) {
                $scope.shouldConvertToProspect = true;
            };

            $scope.opts = {
                dialogFade: true,
                backdropFade: true
            };

            $scope.performConvert =function (lead) {
                var formattedLead = angular.copy(lead);
                for (var i = 0; i < formattedLead.linesOfBusiness.length; i++) {
                    formattedLead.linesOfBusiness[i].targetDate = DateHelper.getFormattedDate(formattedLead.linesOfBusiness[i].targetDate);
                    formattedLead.linesOfBusiness[i].expirationDate = DateHelper.getFormattedDate(formattedLead.linesOfBusiness[i].expirationDate);
                }
                Suspect.update({id: formattedLead.id, leadType: "PROSPECT", linesOfBusiness: formattedLead.linesOfBusiness}).$then(convertToProspectSuccessCallback, convertToProspectErrorCallback);
            };

            $scope.cancelConvertToProspect = function() {
                $scope.shouldConvertToProspect = false;
                $scope.lead.linesOfBusiness = [];
            };

            $scope.lead.linesOfBusiness = [];

            $scope.addingLeadLineOfBusiness = false;

            $scope.addLeadLineOfBusiness = function () {
                var leadLineOfBusiness = {};
                if ($scope.lead.linesOfBusiness.length > 0) {
                    var modelLob = $scope.lead.linesOfBusiness[0];
                    if (modelLob.lineOfBusiness !== undefined) {
                        leadLineOfBusiness.lineOfBusiness = { lineOfBusinessCategory: modelLob.lineOfBusiness.lineOfBusinessCategory };
                    }
                    leadLineOfBusiness.targetDate = modelLob.targetDate;
                    leadLineOfBusiness.expirationDate = modelLob.expirationDate;
                    leadLineOfBusiness.currentCarrier = modelLob.currentCarrier;
                    leadLineOfBusiness.remarket = modelLob.remarket;
                }
                $scope.lead.linesOfBusiness.push(leadLineOfBusiness);
            };

            $scope.deleteLineOfBusiness = function (index) {
                $scope.lead.linesOfBusiness.splice(index);
            };
        }])
    .controller('CreateSuspectCtrl', ['$scope', '$routeParams', '$location', 'Suspect', 'Logger', 'UnitedStates',
        'SupportedCountryCodes', 'LeadSubTypes', 'NoteType', 'BusinessTypes', 'DateHelper', 'LineOfBusiness', '$filter',
        'LeadUtils',
        function ($scope, $routeParams, $location, Suspect, Logger, UnitedStates, SupportedCountryCodes, LeadSubTypes, NoteType, BusinessTypes, DateHelper, LineOfBusiness, $filter, LeadUtils) {
            $scope.lead = {};
            $scope.lead.leadAddresses = [];
            $scope.leadAddress = {primaryAddress: true, address: {}};
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

            LineOfBusiness.list().$then(function (response) {
                $scope.linesOfBusiness = response.data;
            });

            $scope.countryCodes = SupportedCountryCodes.list();
            $scope.leadSubTypes = LeadSubTypes.list();
            $scope.noteTypes = NoteType.list();
            $scope.businessTypes = BusinessTypes.list();

            var saveSuccessCallback = function () {
                Logger.success("Suspect Saved Successfully", "Success");
                $location.path('/edit/' + $scope.lead.id);
            };

            var saveErrorCallback = function (response) {
                Logger.formValidationMessageBuilder(response, $scope, $scope.leadForm);
            };

            $scope.canSave = function () {
                return $scope.leadForm.$valid;
            };

            $scope.saveSuspect = function (lead) {
                if (checkLeadAddress($scope.leadAddress))
                    lead.leadAddresses.push($scope.leadAddress);
                Suspect.save(lead,function (data) {
                    lead.id = data.id;
                }).$then(saveSuccessCallback, saveErrorCallback);
            };

            var checkLeadAddress = function (leadAddress) {
                return leadAddress.address.addressOne !== undefined ||
                    leadAddress.address.addressTwo !== undefined ||
                    leadAddress.address.addressThree !== undefined ||
                    leadAddress.address.city !== undefined ||
                    leadAddress.address.state != undefined ||
                    leadAddress.address.country != undefined ||
                    leadAddress.address.zipcode !== undefined;
            };

            var getLobFromSelect = function (leadLineOfBusiness) {
                return leadLineOfBusiness.lineOfBusiness === undefined ? undefined : $filter('findById')($scope.linesOfBusiness, leadLineOfBusiness.lineOfBusiness.id);
            };

        }])
    .controller('ShowSuspectCtrl', ['$scope', '$routeParams', '$location', 'Suspect', 'Logger',
        function ($scope, $routeParams, $location, Suspect, Logger) {

        }]);
