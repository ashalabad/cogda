angular.module('suspectApp', ['ui.bootstrap', 'resources.restApi', 'common.helperFuncs', 'resources.logger', 'ngGrid', 'resources.suspect', 'resources.unitedStates', 'resources.SupportedCountryCodes', 'resources.leadSubTypes', 'resources.noteType', 'resources.businessTypes', 'resources.leadAddress', 'resources.leadService'])

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
    .controller('EditSuspectCtrl', ['$scope', '$routeParams', '$location', 'Suspect', 'Logger', 'UnitedStates', 'SupportedCountryCodes', 'LeadSubTypes', 'NoteType', 'BusinessTypes', 'LeadService',
        function ($scope, $routeParams, $location, Suspect, Logger, UnitedStates, SupportedCountryCodes, LeadSubTypes, NoteType, BusinessTypes, LeadService) {
            $scope.editingLead = false;
            $scope.message = '';
            $scope.errors = [];
            $scope.lead = {};
            $scope.lead.leadAddresses = [];
            $scope.lead.leadContacts = [];
            $scope.states = UnitedStates.list();
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

            $scope.$on('handleUpdateLead', function () {
                $scope.updateLead($scope.lead);
            });

            $scope.$on('handleAddAddress', function () {
                $scope.lead.leadAddresses.push(LeadService.entityToBroadCast);
                $scope.updateLead($scope.lead);
            });

            $scope.$on('addContactPhoneNumber', function () {
                if ($scope.lead.leadContacts[LeadService.parentIdx].leadContactPhoneNumbers === undefined) {
                    $scope.lead.leadContacts[LeadService.parentIdx].leadContactPhoneNumbers = [];
                }
                $scope.lead.leadContacts[LeadService.parentIdx].leadContactPhoneNumbers.push(LeadService.entityToBroadCast);
                $scope.updateLead($scope.lead);
            });

            $scope.$on('addContactAddress', function () {
                if ($scope.lead.leadContacts[LeadService.parentIdx].leadContactAddresses === undefined) {
                    $scope.lead.leadContacts[LeadService.parentIdx].leadContactAddresses = [];
                }
                $scope.lead.leadContacts[LeadService.parentIdx].leadContactAddresses.push(LeadService.entityToBroadCast);
                $scope.updateLead($scope.lead);
            });

            $scope.$on('addLeadContact', function () {
                if ($scope.lead.leadContacts === undefined) {
                    $scope.lead.leadContacts = [];
                }
                $scope.lead.leadContacts.push(LeadService.entityToBroadCast);
                $scope.updateLead($scope.lead);
            });

            $scope.$on('addContactEmailAddress', function () {
                if ($scope.lead.leadContacts[LeadService.parentIdx].leadContactEmailAddresses === undefined) {
                    $scope.lead.leadContacts[LeadService.parentIdx].leadContactEmailAddresses = [];
                }
                $scope.lead.leadContacts[LeadService.parentIdx].leadContactEmailAddresses.push(LeadService.entityToBroadCast);
                $scope.updateLead($scope.lead);
            });

            $scope.$on('addLeadNote', function () {
                if ($scope.lead.leadNotes === undefined) {
                    $scope.lead.leadNotes = [];
                }
                $scope.lead.leadNotes.push(LeadService.entityToBroadCast);
                $scope.updateLead($scope.lead);
            });

            $scope.$on('deleteAddress', function() {
                $scope.lead.leadAddresses.splice(LeadService.entityIdx, 1);
                $scope.updateLead($scope.lead);
            });

            $scope.$on('deleteContact', function() {
                $scope.lead.leadContacts.splice(LeadService.entityIdx, 1);
                $scope.updateLead($scope.lead);
            });

            $scope.$on('deleteContactAddress', function() {
                $scope.lead.leadContacts[LeadService.parentIdx].leadContactAddresses.splice(LeadService.entityIdx, 1);
                $scope.updateLead($scope.lead);
            });

            $scope.$on('deleteContact', function() {
                $scope.lead.leadContacts.splice(LeadService.entityIdx, 1);
                $scope.updateLead($scope.lead);
            });

            $scope.$on('deleteLeadContactEmailAddress', function() {
                $scope.lead.leadContacts[LeadService.parentIdx].leadContactEmailAddresses.splice(LeadService.entityIdx, 1);
                $scope.updateLead($scope.lead);
            });

            $scope.$on('deleteContactPhoneNumber', function() {
                $scope.lead.leadContacts[LeadService.parentIdx].leadContactPhoneNumbers.splice(LeadService.entityIdx, 1);
                $scope.updateLead($scope.lead);
            });

            $scope.$on('deleteLeadNote', function() {
                $scope.lead.leadNotes.splice(LeadService.entityIdx, 1);
            })

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
            $scope.lead.leadNotes = [];
            $scope.lead.leadNotes.push({});
            $scope.errors = [];
            $scope.message = '';
            $scope.states = UnitedStates.list();
            $scope.countryCodes = SupportedCountryCodes.list();
            $scope.leadSubTypes = LeadSubTypes.list();
            $scope.noteTypes = NoteType.list();
            $scope.businessTypes = BusinessTypes.list();

            var saveSuccessCallback = function (response) {
                Logger.success("Suspect Saved Successfully", "Success");
                $location.path('/show' + response.id);
            };

            var saveErrorCallback = function (response) {
                Logger.formValidationMessageBuilder(response, $scope, $scope.leadForm);
            };

            $scope.canSave = function () {
                return $scope.leadForm.$valid;
            };

            $scope.saveSuspect = function (lead) {
                Suspect.save(lead).$then(saveSuccessCallback, saveErrorCallback);
            };

            $scope.leadNaicsChecked = function () {

            }
        }])
    .controller('ShowSuspectCtrl', ['$scope', '$routeParams', '$location', 'Suspect', 'Logger',
        function ($scope, $routeParams, $location, Suspect, Logger) {

        }])
    .controller('AddAddressController', ['$scope', 'LeadService', function ($scope, LeadService) {
        $scope.address = {};

        $scope.addingAddress = false;

        $scope.addAddress = function () {
            $scope.addingAddress = true;
        };

        $scope.cancelAddAddress = function () {
            $scope.addingAddress = false;
        };

        $scope.saveAddress = function (address) {
            LeadService.addEntityBroadcast(address, 'handleAddAddress');
            $scope.address = {}; // clear the address after the save
            $scope.addingAddress = false;
        }
    }])
    .controller('EditAddressController', ['$scope', '$routeParams', 'LeadAddress', 'Logger', 'LeadService',
        function ($scope, $routeParams, LeadAddress, Logger, LeadService) {
            $scope.editingAddress = false;

            $scope.editAddress = function () {
                $scope.editingAddress = true;
            };

            $scope.cancelEditAddress = function () {
                $scope.editingAddress = false;
            };

            $scope.updateAddress = function (address, idx) {
                LeadService.updateEntityBroadcast(address, idx, 'handleUpdateAddress');
                $scope.cancelEditAddress();
            };

            $scope.deleteAddress = function (address, idx) {
                LeadService.deleteEntity('deleteAddress', idx);
            }
        }])
    .controller('EditContactController', ['$scope', 'LeadService', function ($scope, LeadService) {
        $scope.editingContact = false;

        $scope.editContact = function () {
            $scope.editingContact = true;
        };

        $scope.cancelEditContact = function () {
            $scope.editingContact = false;
        };

        $scope.updateContact = function (contact) {
            LeadService.save('handleUpdateLead');
            $scope.cancelEditContact();
        };

        $scope.deleteContact = function (contact, idx) {
            LeadService.deleteEntity('deleteContact', idx);
        };
    }])
    .controller('AddLeadContactAddressController', ['$scope', 'LeadService', function ($scope, LeadService) {
        $scope.contactAddress = {};

        $scope.addingContactAddress = false;

        $scope.addContactAddress = function () {
            $scope.addingContactAddress = true;
        };

        $scope.cancelAddContactAddress = function () {
            $scope.addingContactAddress = false;
        };

        $scope.saveContactAddress = function (contactAddress) {
            LeadService.addEntityBroadcast(contactAddress, 'addContactAddress', $scope.$parent.$index);
            $scope.contactAddress = {}; // clear the address after the save
            $scope.addingContactAddress = false;
        }
    }])
    .controller('EditLeadContactAddressController', ['$scope', 'LeadService', function ($scope, LeadService) {
        $scope.editingContactAddress = false;

        $scope.editContactAddress = function () {
            $scope.editingContactAddress = true;
        };

        $scope.cancelEditContactAddress = function () {
            $scope.editingContactAddress = false;
        };

        $scope.updateContactAddress = function (contact) {
            LeadService.save('handleUpdateLead');
            $scope.cancelEditContactAddress();
        };

        $scope.deleteContactAddress = function (contact, idx) {
            LeadService.deleteEntity('deleteContactAddress', $scope.$index, $scope.$parent.$index);
        };
    }])
    .controller('AddLeadContactController', ['$scope', 'LeadService', function ($scope, LeadService) {
        $scope.contact = {};

        $scope.addingContact = false;

        $scope.addContact = function () {
            $scope.addingContact = true;
        };

        $scope.cancelAddContact = function () {
            $scope.addingContact = false;
        };

        $scope.saveContact = function (contact) {
            LeadService.addEntityBroadcast(contact, 'addLeadContact');
            $scope.contact = {}; // clear the address after the save
            $scope.addingContact = false;
        }
    }])
    .controller('EditContactController', ['$scope', 'LeadService', function ($scope, LeadService) {
        $scope.editingContact = false;

        $scope.editContact = function () {
            $scope.editingContact = true;
        };

        $scope.cancelEditContact = function () {
            $scope.editingContact = false;
        };

        $scope.updateContact = function (contact) {
            LeadService.save('handleUpdateLead');
            $scope.cancelEditContact();
        };

        $scope.deleteContact = function (contact, idx) {
            LeadService.deleteEntity('deleteContact', $scope.$index);
        }
    }])
    .controller('AddContactEmailAddressController', ['$scope', 'LeadService', function ($scope, LeadService) {
        $scope.contactEmailAddress = {};

        $scope.addingContactEmailAddress = false;

        $scope.addContactEmailAddress = function () {
            $scope.addingContactEmailAddress = true;
        };

        $scope.cancelAddContactEmailAddress = function () {
            $scope.addingContactEmailAddress = false;
        };

        $scope.saveContactEmailAddress = function (contactEmailAddress) {
            LeadService.addEntityBroadcast(contactEmailAddress, 'addContactEmailAddress', $scope.$parent.$index);
            $scope.contactEmailAddress = {}; // clear the address after the save
            $scope.addingContactEmailAddress = false;
        }
    }])
    .controller('EditLeadContactEmailAddressController', ['$scope', 'LeadService', function ($scope, LeadService) {
        $scope.editingContactEmailAddress = false;

        $scope.editContactEmailAddress = function () {
            $scope.editingContactEmailAddress = true;
        };

        $scope.cancelEditContactEmailAddress = function () {
            $scope.editingContactEmailAddress = false;
        };

        $scope.updateContactEmailAddress = function (contact) {
            LeadService.save('handleUpdateLead');
            $scope.cancelEditContactEmailAddress();
        };

        $scope.deleteContactEmailAddress = function (contact, idx) {
            LeadService.deleteEntity('deleteLeadContactEmailAddress', $scope.$index, $scope.$parent.$index);
        };
    }])
    .controller('AddContactPhoneNumberController', ['$scope', 'LeadService', function ($scope, LeadService) {
        $scope.contactPhoneNumber = {};

        $scope.addingContactPhoneNumber = false;

        $scope.addContactPhoneNumber = function () {
            $scope.addingContactPhoneNumber = true;
        };

        $scope.cancelAddContactPhoneNumber = function () {
            $scope.addingContactPhoneNumber = false;
        };

        $scope.saveContactPhoneNumber = function (contactPhoneNumber) {
            LeadService.addEntityBroadcast(contactPhoneNumber, 'addContactPhoneNumber', $scope.$parent.$index);
            $scope.contactPhoneNumber = {}; // clear the address after the save
            $scope.addingContactPhoneNumber = false;
        }
    }])
    .controller('EditLeadContactPhoneNumberController', ['$scope', 'LeadService', function ($scope, LeadService) {
        $scope.editingContactPhoneNumber = false;

        $scope.editContactPhoneNumber = function () {
            $scope.editingContactPhoneNumber = true;
        };

        $scope.cancelEditContactPhoneNumber = function () {
            $scope.editingContactPhoneNumber = false;
        };

        $scope.updateContactPhoneNumber = function (contactPhoneNumber) {
            LeadService.save('handleUpdateLead');
            $scope.cancelEditContactPhoneNumber();
        };

        $scope.deleteContactPhoneNumber = function (contactPhoneNumber, idx) {
            LeadService.deleteEntity('deleteContactPhoneNumber', $scope.$index, $scope.$parent.$index);
        }
    }])
    .controller('EditLeadNoteController', ['$scope', 'LeadService', function ($scope, LeadService) {
        $scope.editingLeadNote = false;

        $scope.editLeadNote = function () {
            $scope.editingLeadNote = true;
        };

        $scope.cancelEditLeadNote = function () {
            $scope.editingLeadNote = false;
        };

        $scope.updateLeadNote = function (leadNote) {
            LeadService.save('handleUpdateLead');
            $scope.cancelEditLeadNote();
        };

        $scope.deleteLeadNote = function (leadNote, idx) {
            LeadService.deleteEntity('deleteLeadNote', $scope.$index);
        }
    }])
    .controller('AddLeadNoteController', ['$scope', 'LeadService', function ($scope, LeadService) {
        $scope.leadNote = {};

        $scope.addingLeadNote = false;

        $scope.addLeadNote = function () {
            $scope.addingLeadNote = true;
        };

        $scope.cancelAddLeadNote = function () {
            $scope.addingLeadNote = false;
        };

        $scope.saveLeadNote = function (leadNote) {
            LeadService.addEntityBroadcast(leadNote, 'addLeadNote');
            $scope.leadNote = {};
            $scope.addingLeadNote = false;
        }
    }]);
