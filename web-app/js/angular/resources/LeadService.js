angular.module('resources.leadService', ['resources.logger', 'ngGrid', 'common.helperFuncs',
        'resources.unitedStates', 'resources.SupportedCountryCodes', 'resources.leadSubTypes',
        'resources.noteType', 'resources.businessTypes', 'resources.leadAddress', 'resources.leadService',
        'resources.leadNote', 'resources.leadContactPhoneNumber', 'resources.leadContactEmailAddress',
        'resources.leadContact', 'resources.leadContactAddress', 'resources.leadLineOfBusiness',
        'resources.lineOfBusiness', 'resources.lead', 'ui.bootstrap'])
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
                $scope.editingAddress = true;
            };

            $scope.cancelEditAddress = function () {
                $scope.editingAddress = false;
            };

            $scope.updateAddress = function (address) {
                LeadAddress.update(address).$then(updateSuccessCallback, updateErrorCallback);
                $scope.cancelEditAddress();
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
                $scope.editingContactAddress = true;
            };

            $scope.cancelEditContactAddress = function () {
                $scope.editingContactAddress = false;
            };

            $scope.updateContactAddress = function (address) {
                LeadContactAddress.update(address).$then(updateSuccessCallback, updateErrorCallback);
                $scope.cancelEditContactAddress();
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
                Logger.success("Contact Address Deleted Successfully", "Success");
                var index = $scope.contact.leadContactAddresses.indexOf($scope.address);
                $scope.contact.leadContactAddresses.splice(index, 1);
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
            }

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
                $scope.editingContactPhoneNumber = true;
            };

            $scope.cancelEditContactPhoneNumber = function () {
                $scope.editingContactPhoneNumber = false;
            };

            $scope.updateContactPhoneNumber = function (contactPhoneNumber) {
                LeadContactPhoneNumber.update(contactPhoneNumber).$then(updateSuccessCallback, updateErrorCallBack);
                $scope.cancelEditContactPhoneNumber();
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
            LeadNote.delete(leadNote, function () {
                Logger.success("Note Deleted Successfully", "Success");
                $scope.lead.leadNotes.splice(idx, 1);
            }, function () {
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
    .controller('CreateLineOfBusinessCtrl', ['$scope', 'Logger',
        function ($scope, Logger) {
            $scope.today = function () {
                $scope.dt = new Date();
            };

            $scope.today();

            $scope.clear = function () {
                $scope.dt = null;
            };

            $scope.toggleMin = function () {
                $scope.minDate = ( $scope.minDate ) ? null : new Date();
            };

            $scope.toggleMin();

        }])
    .controller('EditLeadLineOfBusinessCtrl', ['$scope', 'Logger', 'LineOfBusiness', 'LeadLineOfBusiness', '$dialog',
        function ($scope, Logger, LineOfBusiness, LeadLineOfBusiness, $dialog) {
            LineOfBusiness.list().$then(function (response) {
                $scope.linesOfBusiness = response.data;
            });

//            $scope.editingLineOfBusiness = false;

            $scope.editLineOfBusiness = function () {
//                $scope.editingLineOfBusiness = true;
                $scope.$parent.$parent.editingLineOfBusiness = true;
                $scope.$parent.$parent.leadLineOfBusiness = $scope.leadLineOfBusiness;
            };

            $scope.cancelEditLineOfBusiness = function () {
//                $scope.editingLineOfBusiness = false;
                $scope.$parent.editingLineOfBusiness = false;
            };

            $scope.updateLineOfBusiness = function (lineOfBusiness) {
                var result = $scope.$parent.updateLineOfBusiness($scope.leadLineOfBusiness);
                if (!result) {
                    $scope.cancelEditLineOfBusiness();
                }
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
                var index = $scope.lead.linesOfBusiness.indexOf($scope.linesOfBusiness);
                $scope.lead.linesOfBusiness.splice(index, 1);
                Logger.messageBuilder(response, $scope);
            };

            var deleteLineOfBusinessErrorCallback = function (response) {
                Logger.messageBuilder(response, $scope);
            };

            var updateSuccessCallback = function (response) {
                Logger.success("Contact Phone Number Updated Successfully", "Success");
            };

            var updateErrorCallBack = function (response) {
                Logger.formValidationMessageBuilder(response, $scope, $scope.leadLineOfBusinessForm);
            };

        }])
    .controller('AddLeadLineOfBusinessCtrl', ['$scope', 'Logger', 'LeadLineOfBusiness', 'LineOfBusiness', 'DateHelper',
        function ($scope, Logger, LeadLineOfBusiness, LineOfBusiness, DateHelper) {
            $scope.leadLineOfBusiness = {};
            $scope.addingLeadLineOfBusiness = false;

            LineOfBusiness.list().$then(function (response) {
                $scope.linesOfBusiness = response.data;
            });

            $scope.addLeadLineOfBusiness = function () {
                $scope.addingLeadLineOfBusiness = true;
            };

            $scope.cancelAddLeadLineOfBusiness = function () {
                $scope.addingLeadLineOfBusiness = false;
            };

            $scope.saveLeadLineOfBusiness = function (leadLineOfBusiness) {
                leadLineOfBusiness.lead = {id: $scope.lead.id};
                leadLineOfBusiness.targetDate = DateHelper.getFormattedDate(leadLineOfBusiness.targetDate);
                leadLineOfBusiness.expirationDate = DateHelper.getFormattedDate(leadLineOfBusiness.expirationDate);
                LeadLineOfBusiness.save(leadLineOfBusiness,function (data) {
                    leadLineOfBusiness.id = data.id;
                    $scope.lead.linesOfBusiness.push(leadLineOfBusiness);
                    $scope.cancelAddLeadLineOfBusiness();
                }).$then(updateSuccessCallback, updateErrorCallback);
                $scope.leadLineOfBusiness = {};
            };

            var updateSuccessCallback = function (response) {
                Logger.success("Line Of Business Added Successfully", "Success");
            };

            var updateErrorCallback = function (response) {
                Logger.formValidationMessageBuilder(response, $scope, $scope.leadLineOfBusinessForm);
            };

        }]);
