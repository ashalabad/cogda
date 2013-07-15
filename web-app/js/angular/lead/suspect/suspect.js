angular.module('suspectApp', ['resources.restApi', 'common.helperFuncs', 'resources.logger', 'ngGrid', 'resources.suspect', 'resources.unitedStates', 'resources.SupportedCountryCodes', 'resources.leadSubTypes', 'resources.noteType', 'resources.businessTypes'])

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
    .controller('EditSuspectCtrl', ['$scope', '$routeParams', '$location', 'Suspect', 'Logger', 'UnitedStates', 'SupportedCountryCodes', 'LeadSubTypes', 'NoteType', 'BusinessTypes',
        function ($scope, $routeParams, $location, Suspect, Logger, UnitedStates, SupportedCountryCodes, LeadSubTypes, NoteType, BusinessTypes) {
            $scope.editingLead = false;
            $scope.message = '';
            $scope.errors = [];
            var original = {};
            $scope.lead = {};
            $scope.lead.leadAddresses = [];
            $scope.lead.leadContacts =[];
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
            }

            $scope.cancelEditLead = function () {
                $scope.editingLead = false;
            }

            $scope.editLead = function () {
                $scope.editingLead = true;
            }


            var updateSuccessCallback = function (response) {
                Logger.success("Suspect Updated Successfully", "Success");
            }

            var updateErrorCallBack = function (response) {
                Logger.formValidationMessageBuilder(response, $scope, $scope.leadForm);
            }
        }])
    .controller('CreateSuspectCtrl', ['$scope', '$routeParams', '$location', 'Suspect', 'Logger', 'UnitedStates', 'SupportedCountryCodes', 'LeadSubTypes', 'NoteType', 'BusinessTypes',
        function ($scope, $routeParams, $location, Suspect, Logger, UnitedStates, SupportedCountryCodes, LeadSubTypes, NoteType, BusinessTypes) {
            $scope.lead = {}
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
        }])
    .controller('ShowSuspectCtrl', ['$scope', '$routeParams', '$location', 'Suspect', 'Logger',
        function ($scope, $routeParams, $location, Suspect, Logger) {

        }])
    .controller('AddAddressController', ['$scope', function ($scope) {
        $scope.address = {};

        $scope.addingAddress = false;

        $scope.addAddress = function () {
            $scope.addingAddress = true;
        }

        $scope.cancelAddAddress = function () {
            $scope.addingAddress = false;
        }

        $scope.saveAddress = function (address) {
            $scope.$parent.lead.leadAddresses.push(address);
            $scope.address = {}; // clear the address after the save
            $scope.addingAddress = false;
        }
    }])
    .controller('EditAddressController', ['$scope', function ($scope) {
        $scope.editingAddress = false;

        $scope.editAddress = function () {
            $scope.editingAddress = true;
        }

        $scope.cancelEditAddress = function () {
            $scope.editingAddress = false;
        }

        $scope.updateAddress = function (address) {
            // console.log("update address against persistent store" + address);
            // todo: need to add controller or action to suspect controller
            $scope.cancelEditAddress();
        }

        $scope.deleteAddress = function (address, idx) {
            // toss the actual address off to the API to delete
            //todo: not sure what this will do
            $scope.$parent.lead.leadAddresses.splice(idx);
        }
    }])
    .controller('AddContactController', ['$scope', function ($scope) {
        $scope.contact = {};

        $scope.addingContact = false;

        $scope.addContact = function () {
            $scope.addingContact = true;
        }

        $scope.cancelAddContact = function () {
            $scope.addingContact = false;
        }

        $scope.saveContact = function (contact) {
            $scope.$parent.lead.leadContacts.push(contact);
            $scope.contact = {}; // clear the address after the save
            $scope.addingContact = false;
        }
    }])
    .controller('EditContactController', ['$scope', function ($scope) {
        $scope.editingContact = false;

        $scope.editContact = function () {
            $scope.editingContact = true;
        }

        $scope.cancelEditContact = function () {
            $scope.editingContact = false;
        }

        $scope.updateContact = function (contact) {
            // console.log("update address against persistent store" + address);
            //todo: update contact...add controller for leadcontacts
            $scope.cancelEditContact();
        }

        $scope.deleteContact = function (contact, idx) {
            // toss the actual address off to the API to delete
            //todo: same as before
            $scope.$parent.lead.leadContacts.splice(idx);
        }
    }])
    .controller('AddContactAddressController', ['$scope', function ($scope) {
        $scope.address = {};

        $scope.addingContactAddress = false;

        $scope.addContactAddress = function () {
            $scope.addingContactAddress = true;
        }

        $scope.cancelAddContactAddress = function () {
            $scope.addingContactAddress = false;
        }

        $scope.saveContactAddress = function (contact) {
            $scope.$parent.contact.leadContactsAddress.push(contact);
            $scope.contactAddress = {}; // clear the address after the save
            $scope.addingContactAddress = false;
        }
    }])
    .controller('EditContactAddressController', ['$scope', function ($scope) {
        $scope.editingContact = false;

        $scope.editContact = function () {
            $scope.editingContact = true;
        }

        $scope.cancelEditContact = function () {
            $scope.editingContact = false;
        }

        $scope.updateContact = function (contact) {
            // console.log("update address against persistent store" + address);
            $scope.cancelEditContact();
        }

        $scope.deleteContact = function (contact, idx) {
            // toss the actual address off to the API to delete
            $scope.$parent.lead.leadContacts.splice(idx);
        }
    }])
    .controller('AddContactController', ['$scope', function ($scope) {
        $scope.contact = {};

        $scope.addingContact = false;

        $scope.addContact = function () {
            $scope.addingContact = true;
        }

        $scope.cancelAddContact = function () {
            $scope.addingContact = false;
        }

        $scope.saveContact = function (contact) {
            $scope.$parent.lead.leadContacts.push(contact);
            $scope.contact = {}; // clear the address after the save
            $scope.addingContact = false;
        }
    }])
    .controller('EditContactController', ['$scope', function ($scope) {
        $scope.editingContact = false;

        $scope.editContact = function () {
            $scope.editingContact = true;
        }

        $scope.cancelEditContact = function () {
            $scope.editingContact = false;
        }

        $scope.updateContact = function (contact) {
            // console.log("update address against persistent store" + address);
            $scope.cancelEditContact();
        }

        $scope.deleteContact = function (contact, idx) {
            // toss the actual address off to the API to delete
            $scope.$parent.lead.leadContacts.splice(idx);
        }
    }])
    .controller('AddContactEmailAddressController', ['$scope', function ($scope) {
        $scope.contactEmailAddress = {};

        $scope.addingContactEmailAddress = false;

        $scope.addContactEmailAddress = function () {
            $scope.addingContactEmailAddress = true;
        }

        $scope.cancelAddContactEmailAddress = function () {
            $scope.addingContactEmailAddress = false;
        }

        $scope.saveContactEmailAddress = function (contactEmailAddress) {
            $scope.$parent.lead.leadContacts.push(contactEmailAddress);
            $scope.contactEmailAddress = {}; // clear the address after the save
            $scope.addingContactEmailAddress = false;
        }
    }])
    .controller('EditLeadContactEmailAddressController', ['$scope', function ($scope) {
        $scope.editingContactEmailAddress = false;

        $scope.editContactEmailAddress = function () {
            $scope.editingContactEmailAddress = true;
        }

        $scope.cancelEditContactEmailAddress = function () {
            $scope.editingContactEmailAddress = false;
        }

        $scope.updateContactEmailAddress = function (contact) {
            // console.log("update address against persistent store" + address);
            $scope.cancelEditContact();
        }

        $scope.deleteContactEmailAddress = function (contact, idx) {
            // toss the actual address off to the API to delete
            $scope.$parent.lead.leadContacts.splice(idx);
        }
    }])
    .controller('AddContactPhoneNumberController', ['$scope', function ($scope) {
        $scope.contactPhoneNumber = {};

        $scope.addingContactPhoneNumber = false;

        $scope.addContactPhoneNumber = function () {
            $scope.addingContactPhoneNumber = true;
        }

        $scope.cancelAddContactPhoneNumber = function () {
            $scope.addingContactPhoneNumber = false;
        }

        $scope.saveContactPhoneNumber = function (contactPhoneNumber) {
            $scope.$parent.leadContact.leadContactPhoneNumbers.push(contactPhoneNumber);
            $scope.contactPhoneNumber = {}; // clear the address after the save
            $scope.addingContactPhoneNumber = false;
        }
    }])
    .controller('EditLeadContactPhoneNumberController', ['$scope', function ($scope) {
        $scope.editingContactPhoneNumber = false;

        $scope.editContactPhoneNumber = function () {
            $scope.editingContactPhoneNumber = true;
        }

        $scope.cancelEditContactPhoneNumber = function () {
            $scope.editingContactPhoneNumber = false;
        }

        $scope.updateContactPhoneNumber = function (contactPhoneNumber) {
            // console.log("update address against persistent store" + address);
            $scope.cancelEditContact();
        }

        $scope.deleteContactPhoneNumber = function (contactPhoneNumber, idx) {
            // toss the actual address off to the API to delete
            $scope.$parent.lead.leadContacts.splice(idx);
        }
    }]);
