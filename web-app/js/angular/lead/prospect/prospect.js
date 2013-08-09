angular.module('prospectApp', ['ui.bootstrap', '$strap.directives', 'resources.naicsCodeTree', 'resources.sicCodeTree',
        'resources.restApi', 'common.helperFuncs', 'resources.logger', 'ngGrid', 'resources.lineOfBusiness',
        'resources.prospect', 'resources.unitedStates', 'resources.SupportedCountryCodes', 'resources.leadSubTypes',
        'resources.noteType', 'resources.businessTypes', 'resources.leadService', 'resources.leadLineOfBusiness',
        'lead.Utils', 'ui.unique'])

    .config(function ($routeProvider) {
        $routeProvider.
            when('/list', {templateUrl: '/prospect/listPartial', controller: 'ListProspectCtrl'}).
            when('/edit/:id', {templateUrl: '/prospect/editPartial', controller: 'EditProspectCtrl' }).
            when('/create', {templateUrl: '/prospect/createPartial', controller: 'CreateProspectCtrl' }).
            when('/show/:id', {templateUrl: '/prospect/showPartial', controller: 'ShowProspectCtrl' }).
            when('').
            otherwise({ redirectTo: '/list' });
    })
    .controller('ListProspectCtrl', ['$scope', '$routeParams', '$location', 'RestApi', 'Logger',
        function ($scope, $routeParams, $location, RestApi, Logger) {
            $scope.prospects = [];
            $scope.showDetails = '<div class="ngCellText"><button id="showBtn" type="button" class="btn-mini" data-ng-click="show(row.entity)" ><i class="icon-eye-open"></i>Details</button></div>';
            $scope.editDetails = '<div class="ngCellText"><button id="editBtn" type="button" class="btn-mini" data-ng-click="edit(row.entity)" ><i class="icon-edit"></i>Edit</button></div>';
            $scope.rowTemplate = '<div data-ng-style="{\'cursor\': row.cursor, \'z-index\': col.zIndex() }" data-ng-repeat="col in renderedColumns" data-ng-class="col.colIndex()" data-ng-dblclick="edit(row)" class="ngCell {{col.cellClass}}" data-ng-cell></div>';
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


            var baseUrl = '/prospect/';
            var Prospect = RestApi.getRest(baseUrl);
            Prospect.list($routeParams, function (list, headers) {
                $scope.prospects = list;
                $scope.total = parseInt(headers('X-Pagination-Total'));
            }, angular.noop());
        }])
    .controller('EditProspectCtrl', ['$scope', '$routeParams', '$location', 'Prospect', 'Logger', 'UnitedStates',
        'SupportedCountryCodes', 'LeadSubTypes', 'NoteType', 'BusinessTypes', 'LineOfBusiness', 'LeadLineOfBusiness',
        '$dialog', 'DateHelper', '$filter',
        function ($scope, $routeParams, $location, Prospect, Logger, UnitedStates, SupportedCountryCodes, LeadSubTypes, NoteType, BusinessTypes, LineOfBusiness, LeadLineOfBusiness, $dialog, DateHelper, $filter) {
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
            LineOfBusiness.list().$then(function (response) {
                $scope.linesOfBusiness = response.data;
            });

            UnitedStates.list().$then(function (response) {
                $scope.states = response.data;
            });

            Prospect.get($routeParams, function (data) {
                $scope.lead = data;
                for (var i = 0; i < $scope.lead.linesOfBusiness.length; i++) {
                    if ($scope.lead.linesOfBusiness[i].targetDate !== undefined)
                        $scope.lead.linesOfBusiness[i].targetDate = new Date($scope.lead.linesOfBusiness[i].targetDate);
                    if ($scope.lead.linesOfBusiness[i].expirationDate)
                        $scope.lead.linesOfBusiness[i].expirationDate = new Date($scope.lead.linesOfBusiness[i].expirationDate);
                }
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
                var formattedLead = angular.copy(lead);
                for (var i = 0; i < formattedLead.linesOfBusiness.length; i++) {
                    formattedLead.linesOfBusiness[i].targetDate = DateHelper.getFormattedDate(formattedLead.linesOfBusiness[i].targetDate);
                    formattedLead.linesOfBusiness[i].expirationDate = DateHelper.getFormattedDate(formattedLead.linesOfBusiness[i].expirationDate);
                }
                Prospect.update(formattedLead).$then(updateSuccessCallback, updateErrorCallBack);
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
                Logger.success("Prospect Updated Successfully", "Success");
                closeEditLead();
            };

            var updateErrorCallBack = function (response) {
                Logger.formValidationMessageBuilder(response, $scope, $scope.leadForm);
            };

            $scope.deleteProspect = function (lead) {
                var title = "Delete Prospect";
                var msg = "Are you sure you want to delete this Prospect?";
                var btns = [
                    {result: 'cancel', label: 'Cancel'},
                    {result: 'delete', label: 'Delete', cssClass: 'btn-danger'}
                ];

                $dialog.messageBox(title, msg, btns)
                    .open()
                    .then(function (result) {
                        if (result == 'delete')
                            Prospect.delete({id: lead.id})
                                .$then(deleteProspectSuccessCallback, deleteProspectErrorCallback);
                    });
            };

            var deleteProspectSuccessCallback = function (response) {
                Logger.success("Prospect Deleted Successfully");
                $location.path('/list/');
            };

            var deleteProspectErrorCallback = function (response) {
                Logger.messageBuilder(response, $scope);
            };
        }])
    .controller('CreateProspectCtrl', ['$scope', '$routeParams', '$location', 'Prospect', 'Logger', 'UnitedStates',
        'SupportedCountryCodes', 'LeadSubTypes', 'NoteType', 'BusinessTypes', 'DateHelper', 'LineOfBusiness', '$filter',
        'LeadUtils', 'LeadService',
        function ($scope, $routeParams, $location, Prospect, Logger, UnitedStates, SupportedCountryCodes, LeadSubTypes, NoteType, BusinessTypes, DateHelper, LineOfBusiness, $filter, LeadUtils, LeadService) {
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
            $scope.lead.linesOfBusiness = [
                {}
            ];

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

            var saveSuccessCallback = function () {
                Logger.success("Prospect Saved Successfully", "Success");
                $location.path('/edit/' + $scope.lead.id);
            };

            var saveErrorCallback = function (response) {
                console.log("broadcasting");
                LeadService.validateAllForms(response, $scope);
            };

            $scope.$on('validateAllForms', function () {
                Logger.formValidationMessageBuilder(LeadService.response, LeadService.scope, $scope.leadForm);
                console.log("CreateProspectCtrl handling");
                console.log($scope.leadForm);
            });

            $scope.canSave = function () {
                return $scope.leadForm.$valid;
            };

            $scope.saveProspect = function (lead) {
                var formattedLead = angular.copy(lead);
                for (var i = 0; i < formattedLead.linesOfBusiness.length; i++) {
                    formattedLead.linesOfBusiness[i].targetDate = DateHelper.getFormattedDate(formattedLead.linesOfBusiness[i].targetDate);
                    formattedLead.linesOfBusiness[i].expirationDate = DateHelper.getFormattedDate(formattedLead.linesOfBusiness[i].expirationDate);
                }
                if (checkLeadAddress($scope.leadAddress))
                    formattedLead.leadAddresses.push($scope.leadAddress);
                Prospect.save(formattedLead,function (data) {
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
            }

        }])
    .controller('ShowProspectCtrl', ['$scope', '$routeParams', '$location', 'Prospect', 'Logger',
        function ($scope, $routeParams, $location, Prospect, Logger) {

        }])
    .controller('CreateLobCtrl', ['$scope', 'Logger', 'LeadService', function ($scope, Logger, LeadService) {
        $scope.$on('validateAllForms', function () {
            console.log("CreateLobCtrl handling. Index: " + $scope.$index);
            console.log($scope.leadLineOfBusinessForm);
            var response = angular.copy(LeadService.response);
            console.log(LeadService.response);
            console.log(response);
            response.data.errors = {};
            var errorBaseName = 'linesOfBusiness[' + $scope.$index + '].';
            for (var i in LeadService.response.data.errors) {
                if (i.indexOf(errorBaseName) == 0) {
                    var newName = i.replace(errorBaseName, '');
                    response.data.errors[newName] = LeadService.response.data.errors[i];
                    console.log(i);
                }
            }
            Logger.formValidationMessageBuilder(response, $scope, $scope.leadLineOfBusinessForm);
        });

        var targetPremiumLastChanged = false;
        var commissionRateLastChanged = false;

        $scope.targetPremiumChangeHandler = function () {
            updateCommissions();
        };

        $scope.commissionRateChangeHandler = function () {
            targetPremiumLastChanged = false;
            commissionRateLastChanged = true;
            updateCommissions();
        };

        $scope.targetCommissionChangeHandler = function () {
            targetPremiumLastChanged = true;
            commissionRateLastChanged = false;
            updateCommissions();
        };

        var updateCommissions = function () {
            var commissions = LeadService.updateCommissions($scope.leadLineOfBusiness.targetPremium, $scope.leadLineOfBusiness.targetCommission, $scope.leadLineOfBusiness.commissionRate, targetPremiumLastChanged, commissionRateLastChanged);
            if (commissions !== undefined) {
                $scope.leadLineOfBusiness.targetCommission = commissions[0];
                $scope.leadLineOfBusiness.commissionRate = commissions[1];
            }
        }

    }]);
