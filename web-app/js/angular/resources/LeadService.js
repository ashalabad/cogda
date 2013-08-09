angular.module('resources.leadService', ['resources.logger', 'ngGrid', 'common.helperFuncs',
        'resources.unitedStates', 'resources.SupportedCountryCodes', 'resources.leadSubTypes',
        'resources.noteType', 'resources.businessTypes', 'resources.leadAddress', 'resources.leadService',
        'resources.leadNote', 'resources.leadContactPhoneNumber', 'resources.leadContactEmailAddress',
        'resources.leadContact', 'resources.leadContactAddress', 'resources.leadLineOfBusiness',
        'resources.lineOfBusiness', 'resources.Lead', 'ui.bootstrap', 'lead.Utils'])
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
    .controller('EditAddressController', ['$scope', '$routeParams', 'LeadAddress', 'Logger', "$dialog",
        function ($scope, $routeParams, LeadAddress, Logger, $dialog) {
            $scope.editingAddress = false;

            $scope.editAddress = function () {
                $scope.addressCopy = angular.copy($scope.address);
                $scope.editingAddress = true;
            };

            $scope.cancelEditAddress = function () {
                $scope.address = $scope.addressCopy;
                closeEditAddress();
            };

            var closeEditAddress = function () {
                $scope.editingAddress = false;
            };

            $scope.updateAddress = function (address) {
                LeadAddress.update(address).$then(updateSuccessCallback, updateErrorCallback);
                closeEditAddress();
            };

            $scope.deleteAddress = function (address) {
                var title = "Delete Address";
                var msg = "Are you sure you want to delete this Address";
                var btns = [
                    {result: 'cancel', label: 'Cancel'},
                    {result: 'delete', label: 'Delete', cssClass: 'btn-danger'}
                ];

                $dialog.messageBox(title, msg, btns)
                    .open()
                    .then(function (result) {
                        if (result == 'delete')
                            LeadAddress.delete({id: address.id}).$then(deleteAddressSuccessCallback, deleteAddressErrorCallback);
                    });
            };

            var deleteAddressSuccessCallback = function (response) {
                Logger.success("Address Deleted Successfully", "Success");
                var index = $scope.lead.leadAddresses.indexOf($scope.leadAddresses);
                $scope.lead.leadAddresses.splice(index, 1);
            };

            var deleteAddressErrorCallback = function (response) {
                Logger.messageBuilder(response, $scope);
            };

            var updateSuccessCallback = function (response) {
                Logger.success("Contact Updated Successfully", "Success");
            };

            var updateErrorCallback = function (response) {
                Logger.formValidationMessageBuilder(response, $scope, $scope.leadAddressForm);
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
                if ($scope.contact.leadContactAddresses === undefined)
                    $scope.contact.leadContactAddresses = [];
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
    .controller('EditLeadContactAddressController', ['$scope', 'LeadContactAddress', 'Logger', '$dialog',
        function ($scope, LeadContactAddress, Logger, $dialog) {
            $scope.editingContactAddress = false;

            $scope.editContactAddress = function () {
                $scope.contactAddressCopy = angular.copy($scope.address);
                $scope.editingContactAddress = true;
            };

            $scope.cancelEditContactAddress = function () {
                $scope.address = $scope.contactAddressCopy;
                closeEditContactAddress();
            };

            var closeEditContactAddress = function () {
                $scope.editingContactAddress = false;
            };

            $scope.updateContactAddress = function (address) {
                LeadContactAddress.update(address).$then(updateSuccessCallback, updateErrorCallback);
                closeEditContactAddress();
            };

            $scope.deleteContactAddress = function (address) {
                var title = "Delete Contact Address";
                var msg = "Are you sure you want to delete this Contact Address?";
                var btns = [
                    {result: 'cancel', label: 'Cancel'},
                    {result: 'delete', label: 'Delete', cssClass: 'btn-danger'}
                ];

                $dialog.messageBox(title, msg, btns)
                    .open()
                    .then(function (result) {
                        if (result == 'delete')
                            LeadContactAddress.delete({id: address.id}).$then(deleteContactAddressSuccessCallback, deleteContactAddressErrorCallback);
                    });
            };

            var deleteContactAddressSuccessCallback = function (response) {
                var index = $scope.contact.leadContactAddresses.indexOf($scope.address);
                $scope.contact.leadContactAddresses.splice(index, 1);
                Logger.success("Contact Address Deleted Successfully", "Success");
            };

            var deleteContactAddressErrorCallback = function (response) {
                Logger.messageBuilder(response, $scope);
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
    .controller('EditContactController', ['$scope', 'LeadContact', 'Logger', "$dialog",
        function ($scope, LeadContact, Logger, $dialog) {
            $scope.editingContact = false;

            $scope.editContact = function () {
                $scope.contactCopy = angular.copy($scope.contact);
                $scope.editingContact = true;
            };

            $scope.cancelEditContact = function () {
                $scope.contact = $scope.contactCopy;
                closeEditContact()
            };

            var closeEditContact = function () {
                $scope.editingContact = false;
            };

            $scope.updateContact = function (contact) {
                LeadContact.update(contact).$then(updateSuccessCallback, updateErrorCallback);
                closeEditContact();
            };

            $scope.deleteContact = function (contact, idx) {
                var title = "Delete Contact";
                var msg = "Are you sure you want to delete this Contact?";
                var btns = [
                    {result: 'cancel', label: 'Cancel'},
                    {result: 'delete', label: 'Delete', cssClass: 'btn-danger'}
                ];

                $dialog.messageBox(title, msg, btns)
                    .open()
                    .then(function (result) {
                        if (result == 'delete')
                            LeadContact.delete({id: contact.id}).$then(deleteContactSuccessCallback, deleteContactErrorCallback);
                    });
            };

            var deleteContactSuccessCallback = function (response) {
                var index = $scope.lead.leadContacts.indexOf($scope.contact);
                $scope.lead.leadContacts.splice(index, 1);
                Logger.success("Successfully deleted Contact", "Success");
            };

            var deleteContactErrorCallback = function (response) {
                Logger.messageBuilder(response, $scope);
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
                    if ($scope.contact.leadContactEmailAddresses === undefined)
                        $scope.contact.leadContactEmailAddresses = [];
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
    .controller('EditLeadContactEmailAddressController', ['$scope', 'LeadContactEmailAddress', 'Logger', '$dialog',
        function ($scope, LeadContactEmailAddress, Logger, $dialog) {
            $scope.editingContactEmailAddress = false;

            $scope.editContactEmailAddress = function () {
                $scope.copyContactEmailAddress = angular.copy($scope.contactEmailAddress);
                $scope.editingContactEmailAddress = true;
            };

            $scope.cancelEditContactEmailAddress = function () {
                $scope.contactEmailAddress = $scope.copyContactEmailAddress;
                $scope.editingContactEmailAddress = false;
            };

            var closeEditContactEmailAddress = function () {
                $scope.editingContactEmailAddress = false;
            };

            $scope.updateContactEmailAddress = function (contactEmailAddress) {
                LeadContactEmailAddress.update(contactEmailAddress).$then(updateSuccessCallback, updateErrorCallback);
                closeEditContactEmailAddress();
            };

            $scope.deleteContactEmailAddress = function (contactEmailAddress, idx) {
                var title = "Delete Contact Email Address";
                var msg = "Are you sure you want to delete this Contact Email Address?";
                var btns = [
                    {result: 'cancel', label: 'Cancel'},
                    {result: 'delete', label: 'Delete', cssClass: 'btn-danger'}
                ];

                $dialog.messageBox(title, msg, btns)
                    .open()
                    .then(function (result) {
                        if (result == 'delete')
                            LeadContactEmailAddress.delete({id: contactEmailAddress.id})
                                .$then(deleteContactEmailAddressSuccessCallback, deleteContactEmailAddressErrorCallback);
                    });
            };

            var deleteContactEmailAddressSuccessCallback = function (response) {
                var index = $scope.contact.leadContactEmailAddresses.indexOf($scope.contactEmailAddress);
                $scope.contact.leadContactEmailAddresses.splice(index, 1);
                Logger.success("Contact Email Address Deleted Successfully", "Success");
            };

            var deleteContactEmailAddressErrorCallback = function (response) {
                Logger.messageBuilder(response, $scope);
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
                contactPhoneNumber.leadContact = {
                    id: $scope.contact.id
                };
                LeadContactPhoneNumber.save(contactPhoneNumber,function (data) {
                    contactPhoneNumber.id = data.id;
                    if ($scope.contact.leadContactPhoneNumbers === undefined)
                        $scope.contact.leadContactPhoneNumbers = [];
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
    .controller('EditLeadContactPhoneNumberController', ['$scope', 'LeadContactPhoneNumber', 'Logger', '$dialog',
        function ($scope, LeadContactPhoneNumber, Logger, $dialog) {
            $scope.editingContactPhoneNumber = false;

            $scope.editContactPhoneNumber = function () {
                $scope.contactPhoneNumberCopy = angular.copy($scope.contactPhoneNumber);
                $scope.editingContactPhoneNumber = true;
            };

            $scope.cancelEditContactPhoneNumber = function () {
                $scope.contactPhoneNumber = $scope.contactPhoneNumberCopy;
                closeEditContactPhoneNumber();
            };

            var closeEditContactPhoneNumber = function () {
                $scope.editingContactPhoneNumber = false;
            };

            $scope.updateContactPhoneNumber = function (contactPhoneNumber) {
                LeadContactPhoneNumber.update(contactPhoneNumber).$then(updateSuccessCallback, updateErrorCallBack);
                closeEditContactPhoneNumber();
            };

            $scope.deleteContactPhoneNumber = function (contactPhoneNumber) {
                var title = "Delete Contact Phone Number";
                var msg = "Are you sure you want to delete this Contact Phone Number";
                var btns = [
                    {result: 'cancel', label: 'Cancel'},
                    {result: 'delete', label: 'Delete', cssClass: 'btn-danger'}
                ];

                $dialog.messageBox(title, msg, btns)
                    .open()
                    .then(function (result) {
                        if (result == 'delete')
                            LeadContactPhoneNumber.delete({id: contactPhoneNumber.id})
                                .$then(deleteContactPhoneNumberSuccessCallback, deleteContactPhoneNumberErrorCallback);
                    });
            };

            var deleteContactPhoneNumberSuccessCallback = function (response) {
                var index = $scope.contact.leadContactPhoneNumbers.indexOf($scope.contactPhoneNumber);
                $scope.contact.leadContactPhoneNumbers.splice(index, 1);
                Logger.success("Contact Phone Number Delete Successfully", "Success");
            };

            var deleteContactPhoneNumberErrorCallback = function (response) {
                Logger.messageBuilder(response, $scope);
            };

            var updateSuccessCallback = function (response) {
                Logger.success("Contact Phone Number Updated Successfully", "Success");
            };

            var updateErrorCallBack = function (response) {
                Logger.formValidationMessageBuilder(response, $scope, $scope.leadContactPhoneNumberForm);
            };
        }])
    .controller('EditLeadNoteController', ['$scope', 'LeadNote', 'Logger', '$dialog',
        function ($scope, LeadNote, Logger, $dialog) {
            $scope.editingLeadNote = false;

            $scope.editLeadNote = function () {
                $scope.leadNoteCopy = angular.copy($scope.leadNote);
                $scope.editingLeadNote = true;
            };

            $scope.cancelEditLeadNote = function () {
                $scope.leadNote = $scope.leadNoteCopy;
                closeEditLeadNote();
            };

            var closeEditLeadNote = function () {
                $scope.editingLeadNote = false;
            };

            $scope.updateLeadNote = function (leadNote) {
                LeadNote.update(leadNote).$then(updateSuccessCallback, updateErrorCallBack);
                closeEditLeadNote();
            };

            $scope.deleteLeadNote = function (leadNote) {
                var title = "Delete Note";
                var msg = "Are you sure you want to delete this Note?";
                var btns = [
                    {result: 'cancel', label: 'Cancel'},
                    {result: 'delete', label: 'Delete', cssClass: 'btn-danger'}
                ];

                $dialog.messageBox(title, msg, btns)
                    .open()
                    .then(function (result) {
                        if (result == 'delete')
                            LeadNote.delete({id: leadNote.id})
                                .$then(deleteLeadNoteSuccessCallback, deleteLeadNoteErrorCallback);
                    });
            };

            var deleteLeadNoteSuccessCallback = function (response) {
                var index = $scope.lead.leadNotes.indexOf($scope.leadNote);
                $scope.lead.leadNotes.splice(index, 1);
                Logger.success("Note Deleted Successfully", "Error");
            };

            var deleteLeadNoteErrorCallback = function (response) {
                Logger.messageBuilder(response, $scope);
            };

            var updateSuccessCallback = function (response) {
                Logger.success("Note Updated Successfully", "Success");
            };

            var updateErrorCallBack = function (response) {
                Logger.formValidationMessageBuilder(response, $scope, $scope.leadNoteForm);
            };
        }])
    .controller('AddLeadNoteController', ['$scope', 'LeadNote', 'Logger', function ($scope, LeadNote, Logger) {
        $scope.leadNote = {};

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
                leadNote = data;
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
    }])
    .controller('ModalCtrl', ['$scope', function ($scope) {
        $scope.saveData = false;
        var sicCodeCopy = [];
        var naicsCodeCopy = [];

        $scope.open = function () {
            sicCodeCopy = angular.copy($scope.lead.sicCodes);
            naicsCodeCopy = angular.copy($scope.lead.naicsCodes);
            $scope.shouldBeOpen = true;
        };

        $scope.close = function () {
            $scope.shouldBeOpen = false;
            $scope.saveData = false;
            $scope.lead.naicsCodes = naicsCodeCopy;
            $scope.lead.sicCodes = sicCodeCopy;
        };

        $scope.save = function () {
            $scope.saveData = true;
            $scope.$parent.relatedSicCodes = $scope.relatedSicCodes;
            $scope.$parent.relatedNaicsCodes = $scope.relatedNaicsCodes;
            $scope.shouldBeOpen = false;
        };

        $scope.opts = {
            dialogFade: true,
            backdropFade: true,
            controller: 'ModalCtrl'
        };
        $scope.showNaicsCodes = false;
        $scope.toggleShowNaicsCodes = function () {
            $scope.showNaicsCodes = !$scope.showNaicsCodes;
        };
    }])
    .controller('ModalEditCtrl', ['$scope', 'Lead', '$http', 'Logger', function ($scope, Lead, $http, Logger) {
        $scope.saveData = false;
        var sicCodeCopy = [];
        var naicsCodeCopy = [];

        $scope.open = function () {
            sicCodeCopy = angular.copy($scope.lead.sicCodes);
            naicsCodeCopy = angular.copy($scope.lead.naicsCodes);
            $scope.shouldBeOpen = true;
        };

        $scope.close = function () {
            $scope.shouldBeOpen = false;
            $scope.saveData = false;
            $scope.lead.naicsCodes = naicsCodeCopy;
            $scope.lead.sicCodes = sicCodeCopy;
            $scope.$parent.lead.naicsCodes = naicsCodeCopy;
            $scope.$parent.lead.sicCodes = sicCodeCopy;
        };

        $scope.save = function () {
            $scope.saveData = true;
            $scope.$parent.relatedSicCodes = $scope.relatedSicCodes;
            $scope.$parent.relatedNaicsCodes = $scope.relatedNaicsCodes;
            $scope.shouldBeOpen = false;
            var naicsCodes = [];
            var sicCodes = [];
            for (var i = 0; i < $scope.lead.naicsCodes.length; i++) {
                naicsCodes.push({id: $scope.lead.naicsCodes[i].id});
            }
            for (var i = 0; i < $scope.lead.sicCodes.length; i++) {
                sicCodes.push({id: $scope.lead.sicCodes[i].id});
            }
            $http.post("/lead/updateNaicsSicCodes/" + $scope.lead.id, JSON.stringify({naicsCodes: naicsCodes, sicCodes: sicCodes})).then(updateSuccessCallback, updateErrorCallBack);
        };

        $scope.opts = {
            dialogFade: true,
            backdropFade: true,
            controller: 'ModalCtrl'
        };
        $scope.showNaicsCodes = false;
        $scope.toggleShowNaicsCodes = function () {
            $scope.showNaicsCodes = !$scope.showNaicsCodes;
        };

        var updateSuccessCallback = function (response) {
            Logger.success("Successfully Updated", "Success");
        };

        var updateErrorCallBack = function (response) {
            Logger.messageBuilder(response);
        }
    }])
    .controller('EditLeadLineOfBusinessCtrl', ['$scope', 'Logger', 'LineOfBusiness', 'LeadLineOfBusiness', '$dialog', 'DateHelper', 'LeadUtils', 'LeadService',
        function ($scope, Logger, LineOfBusiness, LeadLineOfBusiness, $dialog, DateHelper, LeadUtils, LeadService) {
            LineOfBusiness.list().$then(function (response) {
                $scope.linesOfBusiness = response.data;
            });

//            $scope.editingLineOfBusiness = false;
            // two scopes up
            // since inside ng-repeat
            $scope.editLineOfBusiness = function () {
                $scope.$parent.$parent.editingLineOfBusiness = true;
                $scope.$parent.$parent.leadLineOfBusiness = angular.copy($scope.leadLineOfBusiness);
            };

            $scope.cancelEditLineOfBusiness = function () {
//                $scope.editingLineOfBusiness = false;
                $scope.$parent.editingLineOfBusiness = false;
            };

            $scope.updateLineOfBusiness = function (lineOfBusiness) {
                lineOfBusiness.lineOfBusiness = LeadUtils.getLobFromSelect(lineOfBusiness, $scope.linesOfBusiness);
                var formattedLobCopy = angular.copy(lineOfBusiness);
                formattedLobCopy.targetDate = DateHelper.getFormattedDate(formattedLobCopy.targetDate);
                formattedLobCopy.expirationDate = DateHelper.getFormattedDate(formattedLobCopy.expirationDate);
                LeadLineOfBusiness.update(formattedLobCopy).$then(updateSuccessCallback, updateErrorCallBack);
            };

            $scope.deleteLineOfBusiness = function (lineOfBusiness) {
                var title = "Delete Line of Business";
                var msg = "Are you sure you want to delete this Line Of Business?";
                var btns = [
                    {result: 'cancel', label: 'Cancel'},
                    {result: 'delete', label: 'Delete', cssClass: 'btn-danger'}
                ];

                $dialog.messageBox(title, msg, btns)
                    .open()
                    .then(function (result) {
                        if (result == 'delete')
                            LeadLineOfBusiness.delete({id: lineOfBusiness.id})
                                .$then(deleteLineOfBusinessSuccessCallback, deleteLineOfBusinessErrorCallback);
                    });
            };

            var deleteLineOfBusinessSuccessCallback = function (response) {
                var index = $scope.lead.linesOfBusiness.indexOf($scope.leadLineOfBusiness);
                $scope.lead.linesOfBusiness.splice(index, 1);
                Logger.messageBuilder(response, $scope);
                $scope.cancelEditLineOfBusiness();
            };

            var deleteLineOfBusinessErrorCallback = function (response) {
                Logger.messageBuilder(response, $scope);
            };

            var updateSuccessCallback = function (response) {
                for (var i = 0; i < $scope.$parent.lead.linesOfBusiness.length; i++) {
                    if ($scope.$parent.lead.linesOfBusiness[i].id == $scope.leadLineOfBusiness.id) {
                        $scope.$parent.lead.linesOfBusiness[i] = $scope.leadLineOfBusiness;
                        break;
                    }
                }
                Logger.success("Line Of Business Updated Successfully", "Success");
                $scope.$parent.editingLineOfBusiness = false;
            };

            var updateErrorCallBack = function (response) {
                Logger.formValidationMessageBuilder(response, $scope, $scope.leadLineOfBusinessForm);
            };

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
            };

            $scope.cannotDeleteLineOfBusiness = function() {
                return LeadService.cannotDeleteLineOfBusiness($scope.lead.linesOfBusiness);
            };

        }])
    .controller('AddLeadLineOfBusinessCtrl', ['$scope', 'Logger', 'LeadLineOfBusiness', 'LineOfBusiness', 'DateHelper', 'LeadUtils', 'LeadService',
        function ($scope, Logger, LeadLineOfBusiness, LineOfBusiness, DateHelper, LeadUtils, LeadService) {
            $scope.leadLineOfBusiness = {};
            $scope.addingLeadLineOfBusiness = false;

            LineOfBusiness.list().$then(function (response) {
                $scope.linesOfBusiness = response.data;
            });

            $scope.addLeadLineOfBusiness = function () {
                if ($scope.lead.linesOfBusiness.length > 0) {
                    var modelLob = $scope.lead.linesOfBusiness[0];
                    $scope.leadLineOfBusiness.lineOfBusiness = { lineOfBusinessCategory: modelLob.lineOfBusiness.lineOfBusinessCategory };
                    $scope.leadLineOfBusiness.targetDate = modelLob.targetDate;
                    $scope.leadLineOfBusiness.expirationDate = modelLob.expirationDate;
                    $scope.leadLineOfBusiness.currentCarrier = modelLob.currentCarrier;
                    $scope.leadLineOfBusiness.remarket = modelLob.remarket;
                }
                $scope.addingLeadLineOfBusiness = true;
            };

            $scope.cancelAddLeadLineOfBusiness = function () {
                $scope.addingLeadLineOfBusiness = false;
            };

            $scope.saveLeadLineOfBusiness = function (leadLineOfBusiness) {
                leadLineOfBusiness.lead = {id: $scope.lead.id};
                leadLineOfBusiness.lineOfBusiness = LeadUtils.getLobFromSelect(leadLineOfBusiness, $scope.linesOfBusiness);
                var formattedLobCopy = angular.copy(leadLineOfBusiness);
                formattedLobCopy.targetDate = DateHelper.getFormattedDate(formattedLobCopy.targetDate);
                formattedLobCopy.expirationDate = DateHelper.getFormattedDate(formattedLobCopy.expirationDate);
                LeadLineOfBusiness.save(formattedLobCopy,function (data) {
                    leadLineOfBusiness.id = data.id;
                    $scope.lead.linesOfBusiness.push(leadLineOfBusiness);
                    $scope.cancelAddLeadLineOfBusiness();
                }).$then(updateSuccessCallback, updateErrorCallback);
            };

            var updateSuccessCallback = function (response) {
                $scope.leadLineOfBusiness = {};
                Logger.success("Line Of Business Added Successfully", "Success");
            };

            var updateErrorCallback = function (response) {
                Logger.formValidationMessageBuilder(response, $scope, $scope.leadLineOfBusinessForm);
            };

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
            };
        }])
    .controller('CreateLobCtrl', ['$scope', 'Logger', 'LeadService', function ($scope, Logger, LeadService) {
        $scope.$on('validateAllForms', function () {
            var response = angular.copy(LeadService.response);
            response.data.errors = {};
            var errorBaseName = 'linesOfBusiness[' + $scope.$index + '].';
            for (var i in LeadService.response.data.errors) {
                if (i.indexOf(errorBaseName) == 0) {
                    var newName = i.replace(errorBaseName, '');
                    response.data.errors[newName] = LeadService.response.data.errors[i];
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

        $scope.cannotDeleteLineOfBusiness = function() {
            return LeadService.cannotDeleteLineOfBusiness($scope.$parent.lead.linesOfBusiness);
        };

    }])
    .factory('LeadService', ['$rootScope', function ($rootScope) {
        var leadService = {};

        leadService.validateAllForms = function (response, scope) {
            this.response = response;
            this.scope = scope;
            $rootScope.$broadcast('validateAllForms');
        };

        leadService.updateCommissions = function (targetPremium, targetCommission, commissionRate, targetPremiumLastChanged, commissionRateLastChanged) {
            if (targetPremium === undefined || isNaN(targetPremium)) {
                return;
            }
            if (targetCommission !== undefined && targetPremiumLastChanged && !isNaN(targetCommission)) {
                var newCommissionRate = targetCommission / targetPremium * 100;
                commissionRate = Math.round(newCommissionRate * 100) / 100;
            }
            if (commissionRate !== undefined && commissionRateLastChanged && !isNaN(commissionRate)) {
                var newTargetCommission = commissionRate / 100 * targetPremium;
                targetCommission = Math.round(newTargetCommission * 100) / 100;
            }
            return [targetCommission, commissionRate];
        };

        leadService.cannotDeleteLineOfBusiness = function(linesOfBusiness) {
            return linesOfBusiness === undefined ? true : linesOfBusiness.length <= 1;
        };

        return leadService;
    }]);
