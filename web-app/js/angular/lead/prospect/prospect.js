angular.module('prospectApp', ['ui.bootstrap', 'resources.restApi', 'common.helperFuncs', 'resources.logger', 'ngGrid',
        'resources.prospect', 'resources.unitedStates', 'resources.SupportedCountryCodes', 'resources.leadSubTypes',
        'resources.noteType', 'resources.businessTypes', 'resources.leadAddress', 'resources.leadService',
        'resources.leadNote', 'resources.leadContactPhoneNumber', 'resources.leadContactEmailAddress',
        'resources.leadContact', 'resources.leadContactAddress'])

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
    .controller('EditProspectCtrl', ['$scope', '$routeParams', '$location', 'Prospect', 'Logger', 'UnitedStates', 'SupportedCountryCodes', 'LeadSubTypes', 'NoteType', 'BusinessTypes', 'LeadService',
        function ($scope, $routeParams, $location, Prospect, Logger, UnitedStates, SupportedCountryCodes, LeadSubTypes, NoteType, BusinessTypes, LeadService) {
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

        }])
    .controller('AddAddressController', ['$scope', 'LeadAddress', 'Logger', function ($scope, LeadAddress, Logger) {
        $scope.address = {};

        $scope.addingAddress = false;

        $scope.addAddress = function () {
            $scope.addingAddress = true;
        };

        $scope.cancelAddAddress = function () {
            $scope.addingAddress = false;
        };

        $scope.saveAddress = function (address) {
            address.lead = { id: $scope.lead.id };
            LeadAddress.save(address,function (data) {
                address.id = data.id;
                $scope.lead.leadAddresses.push(address);
                $scope.cancelAddAddress();
            }).$then(updateSuccessCallback, updateErrorCallback);
            $scope.address = {}; // clear the address after the save
        };

        var updateSuccessCallback = function (response) {
            Logger.success("Address Added Successfully", "Success");
        };

        var updateErrorCallback = function (response) {
            Logger.formValidationMessageBuilder(response, $scope, $scope.leadForm);
        };
    }])
    .controller('EditAddressController', ['$scope', '$routeParams', 'LeadAddress', 'Logger',
        function ($scope, $routeParams, LeadAddress, Logger) {
            $scope.editingAddress = false;

            $scope.editAddress = function () {
                $scope.editingAddress = true;
            };

            $scope.cancelEditAddress = function () {
                $scope.editingAddress = false;
            };

            $scope.updateAddress = function (address) {
                LeadAddress.update(address).$then(updateSuccessCallback, updateErrorCallback);
                $scope.cancelEditAddress();
            };

            $scope.deleteAddress = function (address, idx) {
                LeadAddress.delete(address, function () {
                    Logger.success("Address Deleted Successfully", "Success");
                    $scope.lead.leadAddresses.splice(idx, 1);
                }, function () {
                    Logger.error("Failed to Delete Address", "Error");
                })
            };

            var updateSuccessCallback = function (response) {
                Logger.success("Contact Updated Successfully", "Success");
            };

            var updateErrorCallback = function (response) {
                Logger.formValidationMessageBuilder(response, $scope, $scope.leadAddressForm);
            };
        }])
    .controller('EditContactController', ['$scope', 'LeadContact', 'Logger', function ($scope, LeadContact, Logger) {
        $scope.editingContact = false;

        $scope.editContact = function () {
            $scope.editingContact = true;
        };

        $scope.cancelEditContact = function () {
            $scope.editingContact = false;
        };

        $scope.updateContact = function (contact) {
            LeadContact.update(contact).$then(updateSuccessCallback, updateErrorCallback);
            $scope.cancelEditContact();
        };

        $scope.deleteContact = function (contact, idx) {
            LeadContact.delete(contact, function () {
                Logger.success("Contact Deleted Succesfully", "Success");
                $scope.lead.leadContacts.splice(idx, 1);
            }, function () {
                Logger.error("Failed to Delete Contact", "Error");
            });
        };

        var updateSuccessCallback = function (response) {
            Logger.success("Contact Updated Successfully", "Success");
        };

        var updateErrorCallback = function (response) {
            Logger.formValidationMessageBuilder(response, $scope, $scope.leadContactForm);
        };
    }])
    .controller('AddLeadContactAddressController', ['$scope', 'LeadContactAddress', 'Logger', function ($scope, LeadContactAddress, Logger) {
        $scope.address = {};

        $scope.addingContactAddress = false;

        $scope.addContactAddress = function () {
            $scope.addingContactAddress = true;
        };

        $scope.cancelAddContactAddress = function () {
            $scope.addingContactAddress = false;
        };

        $scope.saveContactAddress = function (contactAddress) {
            contactAddress.leadContact = {
                id: $scope.contact.id
            };
            LeadContactAddress.save(contactAddress,function (data) {
                contactAddress.id = data.id;
                $scope.contact.leadContactAddresses.push(contactAddress);
                $scope.cancelAddContactAddress();
            }).$then(updateSuccessCallback, updateErrorCallback);
            $scope.address = {}; // clear the address after the save
        };

        var updateSuccessCallback = function (response) {
            Logger.success("Contact Address Updated Successfully", "Success");
        };

        var updateErrorCallback = function (response) {
            Logger.formValidationMessageBuilder(response, $scope, $scope.leadContactAdressForm);
        }
    }])
    .controller('EditLeadContactAddressController', ['$scope', 'LeadContactAddress', 'Logger', function ($scope, LeadContactAddress, Logger) {
        $scope.editingContactAddress = false;

        $scope.editContactAddress = function () {
            $scope.editingContactAddress = true;
        };

        $scope.cancelEditContactAddress = function () {
            $scope.editingContactAddress = false;
        };

        $scope.updateContactAddress = function (address) {
            LeadContactAddress.update(address).$then(updateSuccessCallback, updateErrorCallback);
            $scope.cancelEditContactAddress();
        };

        $scope.deleteContactAddress = function (address, idx) {
            LeadContactAddress.delete(address, function () {
                Logger.success("Contact Address Deleted Successfully", "Success");
                $scope.contact.leadContactAddresses.splice(idx, 1);
            }, function () {
                Logger.error("Failed to Delete Contact Address", "Error");
            });
        };

        var updateSuccessCallback = function (response) {
            Logger.success("Contact Address Updated Successfully", "Success");
        };

        var updateErrorCallback = function (response) {
            Logger.formValidationMessageBuilder(response, $scope, $scope.leadContactAddressForm);
        };
    }])
    .controller('AddLeadContactController', ['$scope', 'LeadContact', 'Logger',
        function ($scope, LeadContact, Logger) {
        $scope.contact = {};

        $scope.addingContact = false;

        $scope.addContact = function () {
            $scope.addingContact = true;
        };

        $scope.cancelAddContact = function () {
            $scope.addingContact = false;
        };

        $scope.saveContact = function (contact) {
                contact.lead = {
                    id: $scope.lead.id
                };
                LeadContact.save(contact,function (data) {
                    contact.id = data.id;
                    $scope.lead.leadContacts.push(contact);
                    $scope.cancelAddContact();
                }).$then(updateSuccessCallback, updateErrorCallBack);
                $scope.contact = { };
            };

            var updateSuccessCallback = function (response) {
                Logger.success("Contact Updated Successfully", "Success");
            };

            var updateErrorCallBack = function (response) {
                Logger.formValidationMessageBuilder(response, $scope, $scope.leadContactForm);
        }
    }])
    .controller('EditContactController', ['$scope', 'LeadContact', 'Logger', function ($scope, LeadContact, Logger) {
        $scope.editingContact = false;

        $scope.editContact = function () {
            $scope.editingContact = true;
        };

        $scope.cancelEditContact = function () {
            $scope.editingContact = false;
        };

        $scope.updateContact = function (contact) {
            LeadContact.update(contact).$then(updateSuccessCallback, updateErrorCallback);
            $scope.cancelEditContact()
        };

        $scope.deleteContact = function (contact, idx) {
            LeadContact.delete(contact, function () {
                Logger.success("Contact Deleted Successfully", "Success");
                $scope.lead.leadContacts.splice(idx, 1);
            }, function () {
                Logger.error("Failed to Delete Contact", "Error");
            })
        };

        var updateSuccessCallback = function (response) {
            Logger.success("Contact Updated Successfully", "Success");
        };

        var updateErrorCallback = function (response) {
            Logger.formValidationMessageBuilder(response, $scope, $scope.leadContactForm);
        };
    }])
    .controller('AddContactEmailAddressController', ['$scope', 'LeadContactEmailAddress', 'Logger',
        function ($scope, LeadContactEmailAddress, Logger) {
        $scope.contactEmailAddress = {};

        $scope.addingContactEmailAddress = false;

        $scope.addContactEmailAddress = function () {
            $scope.addingContactEmailAddress = true;
        };

        $scope.cancelAddContactEmailAddress = function () {
            $scope.addingContactEmailAddress = false;
        };

        $scope.saveContactEmailAddress = function (contactEmailAddress) {
                var leadContact = {
                    id: $scope.contact.id
                };
                contactEmailAddress.leadContact = leadContact;
                LeadContactEmailAddress.save(contactEmailAddress,function (data) {
                    contactEmailAddress.id = data.id;
                    $scope.contact.leadContactEmailAddresses.push(contactEmailAddress);
                    $scope.cancelAddContactEmailAddress();
                }).$then(updateSuccessCallback, updateErrorCallback);
            $scope.contactEmailAddress = {}; // clear the address after the save
            };

            var updateSuccessCallback = function (response) {
                Logger.success("Contact Email Address Added Successfully", "Success");
            };

            var updateErrorCallback = function (response) {
                Logger.formValidationMessageBuilder(response, $scope, $scope.leadContactEmailAddressForm);
        }
    }])
    .controller('EditLeadContactEmailAddressController', ['$scope', 'LeadContactEmailAddress', 'Logger',
        function ($scope, LeadContactEmailAddress, Logger) {
        $scope.editingContactEmailAddress = false;

        $scope.editContactEmailAddress = function () {
            $scope.editingContactEmailAddress = true;
        };

        $scope.cancelEditContactEmailAddress = function () {
            $scope.editingContactEmailAddress = false;
        };

            $scope.updateContactEmailAddress = function (contactEmailAddress) {
                LeadContactEmailAddress.update(contactEmailAddress).$then(updateSuccessCallback, updateErrorCallback);
            $scope.cancelEditContactEmailAddress();
        };

            $scope.deleteContactEmailAddress = function (contactEmailAddress, idx) {
                LeadContactEmailAddress.delete(contactEmailAddress, function () {
                    Logger.success("Contact Email Address Deleted Successfully", "Success");
                    $scope.contact.leadContactEmailAddresses.splice(idx, 1);
                }, function () {
                    Logger.error("Failed to Delete Contact Email Address", "Error");
                });
        };

            var updateSuccessCallback = function (response) {
                Logger.success("Contact Email Address Updated Successfully", "Success");
            };

            var updateErrorCallback = function (response) {
                Logger.formValidationMessageBuilder(response, $scope, $scope.leadContactEmailAddressForm);
            };
    }])
    .controller('AddContactPhoneNumberController', ['$scope', 'LeadContactPhoneNumber', 'Logger',
        function ($scope, LeadContactPhoneNumber, Logger) {
        $scope.contactPhoneNumber = {};

        $scope.addingContactPhoneNumber = false;

        $scope.addContactPhoneNumber = function () {
            $scope.addingContactPhoneNumber = true;
        };

        $scope.cancelAddContactPhoneNumber = function () {
            $scope.addingContactPhoneNumber = false;
        };

        $scope.saveContactPhoneNumber = function (contactPhoneNumber) {
                var contact = {
                    id: $scope.contact.id
                };
                contactPhoneNumber.leadContact = contact;
                LeadContactPhoneNumber.save(contactPhoneNumber,function (data) {
                    contactPhoneNumber.id = data.id;
                    $scope.contact.leadContactPhoneNumbers.push(contactPhoneNumber);
                    $scope.cancelAddContactPhoneNumber();
                }).$then(updateSuccessCallback, updateErrorCallback);
            $scope.contactPhoneNumber = {}; // clear the address after the save
            };

            var updateSuccessCallback = function (response) {
                Logger.success("Successfully Added Contact Phone Number", "Success");
            };

            var updateErrorCallback = function (response) {
                Logger.formValidationMessageBuilder(response, $scope, $scope.leadContactPhoneNumberForm);
        }
    }])
    .controller('EditLeadContactPhoneNumberController', ['$scope', 'LeadContactPhoneNumber', 'Logger', function ($scope, LeadContactPhoneNumber, Logger) {
        $scope.editingContactPhoneNumber = false;

        $scope.editContactPhoneNumber = function () {
            $scope.editingContactPhoneNumber = true;
        };

        $scope.cancelEditContactPhoneNumber = function () {
            $scope.editingContactPhoneNumber = false;
        };

        $scope.updateContactPhoneNumber = function (contactPhoneNumber) {
            LeadContactPhoneNumber.update(contactPhoneNumber).$then(updateSuccessCallback, updateErrorCallBack);
            $scope.cancelEditContactPhoneNumber();
        };

        $scope.deleteContactPhoneNumber = function (contactPhoneNumber, idx) {
            LeadContactPhoneNumber.delete(contactPhoneNumber, function() {
                Logger.success("Contact Phone Number Deleted Successfully", "Success");
                $scope.contact.leadContactPhoneNumbers.splice(idx, 1);
            }, function() {
                Logger.error("Failed to Delete Contact Phone Number", "Error");
            });
        };

        var updateSuccessCallback = function (response) {
            Logger.success("Contact Phone Number Updated Successfully", "Success");
        };

        var updateErrorCallBack = function (response) {
            Logger.formValidationMessageBuilder(response, $scope, $scope.leadContactPhoneNumberForm);
        };
    }])
    .controller('EditLeadNoteController', ['$scope', 'LeadNote', 'Logger', function ($scope, LeadNote, Logger) {
        $scope.editingLeadNote = false;

        $scope.editLeadNote = function () {
            $scope.editingLeadNote = true;
        };

        $scope.cancelEditLeadNote = function () {
            $scope.editingLeadNote = false;
        };

        $scope.updateLeadNote = function (leadNote) {
            LeadNote.update(leadNote).$then(updateSuccessCallback, updateErrorCallBack);
            $scope.cancelEditLeadNote();
        };

        $scope.deleteLeadNote = function (leadNote, idx) {
            LeadNote.delete(leadNote, function() {
                Logger.success("Note Deleted Successfully", "Success");
                $scope.lead.leadNotes.splice(idx, 1);
            }, function() {
                Logger.error("Failed to Delete Note", "Error");
            });
        };

        var updateSuccessCallback = function (response) {
            Logger.success("Note Updated Successfully", "Success");
        };

        var updateErrorCallBack = function (response) {
            Logger.formValidationMessageBuilder(response, $scope, $scope.leadNoteForm);
        };
    }])
    .controller('AddLeadNoteController', ['$scope', 'LeadNote', 'Logger', function ($scope, LeadNote, Logger) {
        $scope.leadNote = { };

        $scope.addingLeadNote = false;

        $scope.addLeadNote = function () {
            $scope.addingLeadNote = true;
        };

        $scope.cancelAddLeadNote = function () {
            $scope.addingLeadNote = false;
        };

        $scope.saveLeadNote = function (leadNote) {
            leadNote.lead = {
                id: $scope.lead.id
            };
            LeadNote.save(leadNote,function (data) {
                leadNote.id = data.id;
                $scope.lead.leadNotes.push(leadNote);
                $scope.cancelAddLeadNote();
            }).$then(updateSuccessCallback, updateErrorCallBack);
            $scope.leadNote = { };
        };

        var updateSuccessCallback = function (response) {
            Logger.success("Note Added Successfully", "Success");
        };

        var updateErrorCallBack = function (response) {
            Logger.formValidationMessageBuilder(response, $scope, $scope.leadNoteForm);
        }
    }]);
