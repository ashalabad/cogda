angular.module('accountApp', ['resources.Account','resources.AccountContact', 'common.helperFuncs', 'resources.logger', 'ngGrid', 'ui.bootstrap'])

    .config(function ($routeProvider) {
        $routeProvider.
            when('/', {templateUrl: '/account/listPartial', controller: 'ListAccountCtrl'}).
            when('/edit/:id', {templateUrl: '/account/editPartial', controller: 'EditAccountCtrl' }).
            when('/create', {templateUrl: '/account/createPartial', controller: 'CreateAccountCtrl' }).
            when('/show/:id', {templateUrl: '/account/showPartial', controller: 'ShowAccountCtrl' }).
            when('/contact/:id', {templateUrl: '/account/showAccountContactPartial', controller: 'AccountContactCtrl' }).
            otherwise({ redirectTo: '/' });
    })

    .controller('ListAccountCtrl', ['$scope','$dialog','$location','Account','Logger',
        function ($scope,$dialog,$location,Account,Logger) {

            $scope.listAccounts = function(){
                Account.list({},function (data,headers) {
                    $scope.accountData = data;
                    $scope.totalServerItems = parseInt(headers('X-Pagination-Total'));
                },angular.noop());

            };

            $scope.pageHeader = "Account Manager";
            $scope.editButton = '<button type="button" class="btn btn-mini" data-ng-click="editAccount(row)" ><i class="icon-edit"></i> Edit</button> '
            $scope.showButton = '<button type="button" class="btn btn-mini" data-ng-click="showAccount(row)" ><i class="icon-eye-open"></i> Show</button> '
            $scope.actionButtons = $scope.editButton + $scope.showButton;
            $scope.sortInfo = { fields:['accountName'], directions: ['asc']};
            $scope.accountGridOptions = {
                data: 'accountData',
                totalServerItems: 'totalServerItems',
                enableRowSelection: false,
                sortInfo:$scope.sortInfo,
                showFilter: true,
                showFooter: true,
                footerRowHeight: 30,
                columnDefs: [
                    {field:'accountName', displayName:'Account Name'},
                    {field:'accountCode', displayName:'Account Code'},
                    {field:'accountType', displayName:'Account Type'},
                    {field:'isMarket', displayName:'Is Market?'},
                    {field:'primaryContact', displayName:'Primary Contact',sortable:false},
                    {displayName:'', cellTemplate: $scope.actionButtons, sortable:false}
                ]
            };

            $scope.listAccounts();

            $scope.createAccount = function createAccount(){
                $location.path('/create');
            };

            $scope.showAccount = function showAccount(row){
                $location.path('/show/' + row.entity.ngRowId);
            };

            $scope.editAccount = function editAccount(row){
                Account.get({id:row.entity.ngRowId},function (data) {
                    $scope.editOpts = {
                        dialogFade: true,
                        backdropFade: true,
                        templateUrl:  'account/editPartial',
                        controller: 'EditAccountCtrl',
                        resolve: {
                            account: function() {
                                return angular.copy(data);
                            }
                        }

                    };
                    var d = $dialog.dialog($scope.editOpts);
                    d.open().then(function(){$scope.listAccounts();});
                });
            };

            var updateSuccessCallback = function(response){
                // apply the success message to toastr
                Logger.messageBuilder(response, $scope);
            };

            var updateErrorCallback = function(response){
                // apply errors to the $scope.errrors object
                Logger.formValidationMessageBuilder(response, $scope, $scope.accountEditForm);
            };



        }])

    .controller('EditAccountCtrl',['$scope','account','dialog','Account','Logger',
        function($scope, account,dialog,Account,Logger){

            $scope.message = '';
            $scope.errors = [];
            $scope.title = "Edit Account";
            $scope.account = account;
            $scope.cancel = function(){
                dialog.close();
            };


            $scope.updateAccount = function(accountInstance){
                Account.update(accountInstance).$then(updateSuccessCallback, updateErrorCallback);
            };

            /**
             * updateSuccessCallback - success handler for successful update
             * @param response
             */
            var updateSuccessCallback = function(response){
                // apply the success message to toastr
                Logger.messageBuilder(response, $scope);
                dialog.close();
            };

            /**
             * updateErrorCallback - error handler for unsuccessful update
             * @param response
             */
            var updateErrorCallback = function(response){
                // apply errors to the $scope.errrors object
                Logger.formValidationMessageBuilder(response, $scope, $scope.accountEditForm);
            };

            $scope.toggleMarket = function toggleMarket(val){
                $scope.account.isMarket = val;
            };
        }
    ])

    .controller('CreateAccountCtrl', ['$scope','$routeParams','$dialog','$location','Account','Logger',
        function ($scope,$routeParams, $dialog, $location, Account, Logger) {
            $scope.accountAddForm={};
            $scope.message = '';
            $scope.errors = [];
            $scope.title = "Create Account";
            $scope.account = {};
            $scope.account.isMarket = false;



            $scope.saveAccount = function(){
                Account.save($scope.account).$then(updateSuccessCallback, updateErrorCallback);
            };
            /**
             * updateSuccessCallback - success handler for successful update
             * @param response
             */
            var updateSuccessCallback = function(response){
                // apply the success message to toastr
                Logger.messageBuilder(response, $scope);
                $location.path('/show/' + response.data.id);
            };

            /**
             * updateErrorCallback - error handler for unsuccessful update
             * @param response
             */
            var updateErrorCallback = function(response){
                // apply errors to the $scope.errrors object
                Logger.formValidationMessageBuilder(response, $scope, $scope.accountAddForm);
            };

            $scope.toggleMarket = function toggleMarket(val){
                $scope.account.isMarket = val;
            };
//            $scope.$watch('account.isMarket', function () {
//                console.log($scope.account.isMarket);
//            });

        }])

    .controller('ShowAccountCtrl', ['$scope','$routeParams','$dialog','$location','Account','Logger',
        function ($scope,$routeParams, $dialog, $location, Account, Logger) {

            $scope.loadAccount = function(){
                Account.get({id:$routeParams.id},function (data) {
                    $scope.account = data;
//                    console.log($scope.account);

                    $.each($scope.account.accountContacts, function(i, v) {
                        if (v.primaryContact == true) {
                            $scope.primaryAccountContact = v;
                            $scope.formattedPrimaryContact = $scope.formatContact($scope.primaryAccountContact);
                        }
                    });

                    $.each($scope.account.accountAddresses, function(i, v) {
                        if (v.primaryAddress == true) {
                            $scope.primaryAccountAddress = v
                            $scope.formattedPrimaryAddress = $scope.formatAddress($scope.primaryAccountAddress);
                            $scope.mapPrimaryAddress = $scope.formatAddressForMapping($scope.formattedPrimaryAddress);
                        }
                    });
                })
            };
            $scope.clearSearch = function clearSearch(){
                $scope.searchString = "";
            };

            $scope.editAccount = function editAccount(){
                Account.get({id:$scope.account.id},function (data) {
                    $scope.editOpts = {
                        dialogFade: true,
                        backdropFade: true,
                        templateUrl:  'account/editPartial',
                        controller: 'EditAccountCtrl',
                        resolve: {
                            account: function() {
                                return angular.copy(data);
                            }
                        }

                    };
                    var d = $dialog.dialog($scope.editOpts);
                    d.open().then(function(){$scope.loadAccount();});
                });
            };
            $scope.addAccountAddress = function addAccountAddress(){
                var addOpts = {
                    dialogFade: true,
                    backdropFade: true,
                    templateUrl:  'account/createAccountAddressPartial',
                    controller: 'CreateAccountAddressCtrl',
                    resolve: {
                        account: function() {
                            return angular.copy($scope.account);
                        }
                    }

                };
                var d = $dialog.dialog(addOpts);
                d.open().then(function(){$scope.loadAccount();});
            };
            $scope.addAccountContact = function addAccountContact(){
                var addOpts = {
                    dialogFade: true,
                    backdropFade: true,
                    templateUrl:  'account/createAccountContactPartial',
                    controller: 'CreateAccountContactCtrl',
                    resolve: {
                        account: function() {
                            return angular.copy($scope.account);
                        }
                    }

                };
                var d = $dialog.dialog(addOpts);
                d.open().then(function(){$scope.loadAccount();});
            };
            $scope.addAccountNote = function addAccountNote(){
                var addOpts = {
                    dialogFade: true,
                    backdropFade: true,
                    templateUrl:  'account/createAccountNotePartial',
                    controller: 'CreateAccountNoteCtrl',
                    resolve: {
                        account: function() {
                            return angular.copy($scope.account);
                        }
                    }

                };
                var d = $dialog.dialog(addOpts);
                d.open().then(function(){$scope.loadAccount();});
            };
            $scope.editAccountAddress = function editAccountAddress(accountAddress){
                var editOpts = {
                    dialogFade: true,
                    backdropFade: true,
                    templateUrl:  'account/editAccountAddressPartial',
                    controller: 'EditAccountAddressCtrl',
                    resolve: {
                        accountAddressId: function() {
                            return angular.copy(accountAddress.id);
                        },
                        account: function() {
                            return angular.copy($scope.account);
                        }
                    }

                };
                var d = $dialog.dialog(editOpts);
                d.open().then(function(){$scope.loadAccount();});
            };

            $scope.editAccountNote = function editAccountNote(accountNote){
                var editOpts = {
                    dialogFade: true,
                    backdropFade: true,
                    templateUrl:  'account/editAccountNotePartial',
                    controller: 'EditAccountNoteCtrl',
                    resolve: {
                        accountNoteId: function() {
                            return angular.copy(accountNote.id);
                        },
                        account: function() {
                            return angular.copy($scope.account);
                        }
                    }

                };
                var d = $dialog.dialog(editOpts);
                d.open().then(function(){$scope.loadAccount();});
            };

            $scope.showAccountContact = function showAccountContact(accountContact){
                $location.path('/contact/' + accountContact.id);
            };

            $scope.formatContact= function(contact){
                var primaryAccountContactEmailAddress=null;
                var primaryAccountContactPhoneNumber=null;

                $.each(contact.accountContactEmailAddresses, function(i, v) {
                    if (v.primaryEmailAddress == true) {
                       primaryAccountContactEmailAddress = v;
                    }
                });

                $.each(contact.accountContactPhoneNumbers, function(i, v) {
                    if (v.primaryPhoneNumber == true) {
                        primaryAccountContactPhoneNumber = v;
                    }
                });
                var formattedContact="<strong>" + contact.lastName + ", " + contact.firstName;
                if(contact.middleName)
                    formattedContact += " " + contact.middleName;
                formattedContact += "</strong>";
                if(primaryAccountContactEmailAddress)
                    formattedContact += "<br /><a href=\"mailto:" + primaryAccountContactEmailAddress.emailAddress+ "\" >"
                        + primaryAccountContactEmailAddress.emailAddress + "</a>";
                if(primaryAccountContactPhoneNumber)
                    formattedContact += "<br />" + primaryAccountContactPhoneNumber.phoneNumber;
                return formattedContact;

            };

            $scope.formatAddress= function(accountAddress){
                var formattedAddress = "";
                if(accountAddress.accountAddressType)
                    formattedAddress += "<strong>" +accountAddress.accountAddressType.code + "</strong><br />";
                if(accountAddress.address.addressOne)
                    formattedAddress += accountAddress.address.addressOne;
                if(accountAddress.address.addressTwo)
                    formattedAddress += "<br />" + accountAddress.address.addressTwo;
                if(accountAddress.address.addressThree)
                    formattedAddress += "<br />" + accountAddress.address.addressThree;
                formattedAddress += "<br />" + accountAddress.address.city + ", " + accountAddress.address.state + " " + accountAddress.address.zipcode;
                if(accountAddress.address.county)
                    formattedAddress += "<br />" + accountAddress.address.county;
                if(accountAddress.address.country)
                    formattedAddress += "<br />" + accountAddress.address.country;
                return formattedAddress;

            };

            $scope.formatAddressForMapping = function(formattedAddress){
                var regex = /<br[^>]*>/gi;
                return formattedAddress.replace(regex, ' ');
            };

            $scope.clearSearch();
            $scope.loadAccount();

        }])

    .controller('CreateAccountContactCtrl', ['$scope','$routeParams','dialog','$location','Account','Logger','account',
        function ($scope,$routeParams, dialog, $location, Account, Logger,account) {
            $scope.message = '';
            $scope.errors = [];
            $scope.title = "Create Contact";
            $scope.account = account;

            $scope.cancel = function(){
                dialog.close();
            };

            $scope.togglePrimaryContact = function togglePrimaryContact(val){
                $scope.accountContact.primaryContact = val;
            };

            $scope.saveAccountContact = function(accountContact,accountContactAddress,accountContactEmailAddress,accountContactPhoneNumber){
                accountContact.accountContactEmailAddresses=[];
                accountContact.accountContactAddresses=[];
                accountContact.accountContactPhoneNumbers=[];
                accountContactEmailAddress.primaryEmailAddress = true;
                accountContactPhoneNumber.primaryPhoneNumber = true;
                accountContactAddress.primaryAddress = true;
                accountContact.accountContactEmailAddresses.push(accountContactEmailAddress);
                accountContact.accountContactAddresses.push(accountContactAddress);
                accountContact.accountContactPhoneNumbers.push(accountContactPhoneNumber);
                $scope.account.accountContacts.push(accountContact);
                Account.update($scope.account).$then(updateSuccessCallback, updateErrorCallback);
            };
            /**
             * updateSuccessCallback - success handler for successful update
             * @param response
             */
            var updateSuccessCallback = function(response){
                // apply the success message to toastr
                Logger.messageBuilder(response, $scope);
                dialog.close();
            };

            /**
             * updateErrorCallback - error handler for unsuccessful update
             * @param response
             */
            var updateErrorCallback = function(response){
                // apply errors to the $scope.errrors object
                Logger.formValidationMessageBuilder(response, $scope, $scope.accountContactAddForm);
            };

        }])

    .controller('CreateAccountAddressCtrl',['$scope','account','dialog','Account','Logger',
        function($scope, account ,dialog, Account, Logger){
            $scope.message = '';
            $scope.errors = [];
            $scope.title = "Add Address";
            $scope.account = account;

            $scope.cancel = function(){
                dialog.close();
            };

            $scope.saveAccountAddress = function(accountAddress){
                $scope.account.accountAddresses.push(accountAddress);
                Account.update($scope.account).$then(updateSuccessCallback, updateErrorCallback);
            };

            /**
             * updateSuccessCallback - success handler for successful update
             * @param response
             */
            var updateSuccessCallback = function(response){
                // apply the success message to toastr
                Logger.messageBuilder(response, $scope);
                dialog.close();
            };

            /**
             * updateErrorCallback - error handler for unsuccessful update
             * @param response
             */
            var updateErrorCallback = function(response){
                // apply errors to the $scope.errrors object
                Logger.formValidationMessageBuilder(response, $scope, $scope.accountAddressAddForm);
            };
        }])

    .controller('CreateAccountNoteCtrl',['$scope','account','dialog','Account','Logger',
        function($scope, account ,dialog, Account, Logger){
            $scope.message = '';
            $scope.errors = [];
            $scope.title = "Add Note";
            $scope.account = account;

            $scope.cancel = function(){
                dialog.close();
            };

            $scope.saveAccountNote = function(accountNote){
                $scope.account.accountNotes.push(accountNote);
                Account.update($scope.account).$then(updateSuccessCallback, updateErrorCallback);
            };

            /**
             * updateSuccessCallback - success handler for successful update
             * @param response
             */
            var updateSuccessCallback = function(response){
                // apply the success message to toastr
                Logger.messageBuilder(response, $scope);
                dialog.close();
            };

            /**
             * updateErrorCallback - error handler for unsuccessful update
             * @param response
             */
            var updateErrorCallback = function(response){
                // apply errors to the $scope.errrors object
                Logger.formValidationMessageBuilder(response, $scope, $scope.accountNoteAddForm);
            };
        }])

    .controller('EditAccountAddressCtrl',['$scope','account','accountAddressId','dialog','Account','Logger',
        function($scope, account, accountAddressId, dialog, Account, Logger){

            $scope.message = '';
            $scope.errors = [];
            $scope.title = "Edit Address";
            $scope.account = account;
//            console.log(accountAddressId);
//            console.log(account);
            $.each($scope.account.accountAddresses, function(i, v) {
                if (v.id == accountAddressId) {
                    $scope.addressIndex =i;
                }
            });

            $scope.updateAccount = function(){
                Account.update($scope.account).$then(updateSuccessCallback, updateErrorCallback);
            };

            $scope.cancel = function(){
                dialog.close();
            };

            /**
             * updateSuccessCallback - success handler for successful update
             * @param response
             */
            var updateSuccessCallback = function(response){
                // apply the success message to toastr
                Logger.messageBuilder(response, $scope);
                dialog.close();
            };

            /**
             * updateErrorCallback - error handler for unsuccessful update
             * @param response
             */
            var updateErrorCallback = function(response){
                // apply errors to the $scope.errrors object
                Logger.formValidationMessageBuilder(response, $scope, $scope.accountAddressEditForm);
            };
        }])

    .controller('EditAccountNoteCtrl',['$scope','account','accountNoteId','dialog','Account','Logger',
        function($scope, account, accountNoteId, dialog, Account, Logger){

            $scope.message = '';
            $scope.errors = [];
            $scope.title = "Edit Note";
            $scope.account = account;
//            console.log(accountNoteId);
//            console.log(account);
            $.each($scope.account.accountNotes, function(i, v) {
                if (v.id == accountNoteId) {
                    $scope.noteIndex =i;
                }
            });

            $scope.updateAccount = function(){
                Account.update($scope.account).$then(updateSuccessCallback, updateErrorCallback);
            };

            $scope.cancel = function(){
                dialog.close();
            };

            /**
             * updateSuccessCallback - success handler for successful update
             * @param response
             */
            var updateSuccessCallback = function(response){
                // apply the success message to toastr
                Logger.messageBuilder(response, $scope);
                dialog.close();
            };

            /**
             * updateErrorCallback - error handler for unsuccessful update
             * @param response
             */
            var updateErrorCallback = function(response){
                // apply errors to the $scope.errrors object
                Logger.formValidationMessageBuilder(response, $scope, $scope.accountNoteEditForm);
            };
        }])

    .controller('AccountContactCtrl',['$scope','$routeParams','$dialog','$location','Account','Logger','AccountContact',
        function ($scope,$routeParams, $dialog, $location, Account, Logger, AccountContact) {

            $scope.showAccountContact=true;

            $scope.loadAccountContact = function(){
                AccountContact.get({id:$routeParams.id},function (data) {
                    $scope.accountContact = data;
                    Account.get({id:$scope.accountContact.account.id},function (data) {
                        $scope.account = data;
                        $.each($scope.account.accountContacts, function(i, v) {
                            if (v.id == $scope.accountContact.id) {
                                $scope.contactIndex =i;
                            }
                        });
                    });


                });
            };

            $scope.formatAddress= function(accountContactAddress){
                var formattedAddress = "";
                if(accountContactAddress.address.addressOne)
                    formattedAddress += accountContactAddress.address.addressOne;
                if(accountContactAddress.address.addressTwo)
                    formattedAddress += "<br />" + accountContactAddress.address.addressTwo;
                if(accountContactAddress.address.addressThree)
                    formattedAddress += "<br />" + accountContactAddress.address.addressThree;
                formattedAddress += "<br />" + accountContactAddress.address.city + ", " + accountContactAddress.address.state + " " + accountContactAddress.address.zipcode;
                if(accountContactAddress.address.county)
                    formattedAddress += "<br />" + accountContactAddress.address.county;
                if(accountContactAddress.address.country)
                    formattedAddress += "<br />" + accountContactAddress.address.country;
                return formattedAddress;

            };

            $scope.showAccount = function showAccount(){
                $location.path('/show/' + $scope.account.id);
            };

            $scope.editAccountContact = function editAccountContact(){
                $scope.createTemp($scope.account);
                $scope.showAccountContact = false;

            };

            $scope.cancelAccountContactEdit = function cancelAccountContactEdit(){
                $scope.account = angular.copy($scope.temp);
                $scope.showAccountContact = true;

            };

            $scope.createTemp = function createTemp(item){
                $scope.temp = null;
                $scope.temp = angular.copy(item);
            };

            $scope.updateAccount = function(){
                Account.update($scope.account).$then(updateSuccessCallback, updateErrorCallback);
            };

            /**
             * updateSuccessCallback - success handler for successful update
             * @param response
             */
            var updateSuccessCallback = function(response){
                // apply the success message to toastr
                Logger.messageBuilder(response, $scope);
                $scope.showAccountContact = true;
            };

            /**
             * updateErrorCallback - error handler for unsuccessful update
             * @param response
             */
            var updateErrorCallback = function(response){
                // apply errors to the $scope.errrors object
                Logger.formValidationMessageBuilder(response, $scope, $scope.accountContactEditForm);
            };

            $scope.addAccountContactEmailAddress = function addAccountContactEmailAddress(){
                var addOpts = {
                    dialogFade: true,
                    backdropFade: true,
                    templateUrl:  'account/createAccountContactEmailAddressPartial',
                    controller: 'CreateAccountContactEmailAddressCtrl',
                    resolve: {
                        accountContactId: function() {
                            return angular.copy($scope.accountContact.id);
                        },
                        account: function() {
                            return angular.copy($scope.account);
                        }
                    }

                };
                console.log(addOpts);

                var d = $dialog.dialog(addOpts);
                d.open().then(function(){$scope.loadAccountContact();});
            };

            $scope.editAccountContactEmailAddress = function editAccountContactEmailAddress(accountContactEmailAddress){
                var editOpts = {
                    dialogFade: true,
                    backdropFade: true,
                    templateUrl:  'account/editAccountContactEmailAddressPartial',
                    controller: 'EditAccountContactEmailAddressCtrl',
                    resolve: {
                        accountContactEmailAddressId: function() {
                            return angular.copy(accountContactEmailAddress.id);
                        },
                        accountContactId: function() {
                            return angular.copy($scope.accountContact.id);
                        },
                        account: function() {
                            return angular.copy($scope.account);
                        }
                    }

                };
                var d = $dialog.dialog(editOpts);
                d.open().then(function(){$scope.loadAccountContact();});
            };

            $scope.addAccountContactPhoneNumber = function addAccountContactPhoneNumber(){
                var addOpts = {
                    dialogFade: true,
                    backdropFade: true,
                    templateUrl:  'account/createAccountContactPhoneNumberPartial',
                    controller: 'CreateAccountContactPhoneNumberCtrl',
                    resolve: {
                        accountContactId: function() {
                            return angular.copy($scope.accountContact.id);
                        },
                        account: function() {
                            return angular.copy($scope.account);
                        }
                    }

                };
                console.log(addOpts);

                var d = $dialog.dialog(addOpts);
                d.open().then(function(){$scope.loadAccountContact();});
            };

            $scope.editAccountContactPhoneNumber = function editAccountContactPhoneNumber(accountContactPhoneNumber){
                var editOpts = {
                    dialogFade: true,
                    backdropFade: true,
                    templateUrl:  'account/editAccountContactPhoneNumberPartial',
                    controller: 'EditAccountContactPhoneNumberCtrl',
                    resolve: {
                        accountContactPhoneNumberId: function() {
                            return angular.copy(accountContactPhoneNumber.id);
                        },
                        accountContactId: function() {
                            return angular.copy($scope.accountContact.id);
                        },
                        account: function() {
                            return angular.copy($scope.account);
                        }
                    }

                };
                var d = $dialog.dialog(editOpts);
                d.open().then(function(){$scope.loadAccountContact();});
            };

            $scope.addAccountContactAddress = function addAccountContactAddress(){
                var addOpts = {
                    dialogFade: true,
                    backdropFade: true,
                    templateUrl:  'account/createAccountContactAddressPartial',
                    controller: 'CreateAccountContactAddressCtrl',
                    resolve: {
                        accountContactId: function() {
                            return angular.copy($scope.accountContact.id);
                        },
                        account: function() {
                            return angular.copy($scope.account);
                        }
                    }

                };
                console.log(addOpts);

                var d = $dialog.dialog(addOpts);
                d.open().then(function(){$scope.loadAccountContact();});
            };

            $scope.editAccountContactAddress = function editAccountContactAddress(accountContactAddress){
                var editOpts = {
                    dialogFade: true,
                    backdropFade: true,
                    templateUrl:  'account/editAccountContactAddressPartial',
                    controller: 'EditAccountContactAddressCtrl',
                    resolve: {
                        accountContactAddressId: function() {
                            return angular.copy(accountContactAddress.id);
                        },
                        accountContactId: function() {
                            return angular.copy($scope.accountContact.id);
                        },
                        account: function() {
                            return angular.copy($scope.account);
                        }
                    }

                };
                var d = $dialog.dialog(editOpts);
                d.open().then(function(){$scope.loadAccountContact();});
            };

            $scope.loadAccountContact();
        }])

    .controller('CreateAccountContactEmailAddressCtrl', ['$scope','$routeParams','dialog','$location','Account','Logger','account','accountContactId',
        function ($scope,$routeParams, dialog, $location, Account, Logger,account,accountContactId) {
            $scope.message = '';
            $scope.errors = [];
            $scope.title = "Add Email Address";
            $scope.account = account;

            $scope.cancel = function(){
                dialog.close();
            };

            $.each($scope.account.accountContacts, function(i, v) {
                if (v.id == accountContactId) {
                    $scope.contactIndex =i;
                }
            });

            $scope.saveAccountContactEmailAddress = function(accountContactEmailAddress){
                $scope.account.accountContacts[$scope.contactIndex].accountContactEmailAddresses.push(accountContactEmailAddress);
                Account.update($scope.account).$then(updateSuccessCallback, updateErrorCallback);
            };

//                $scope.saveAccountContact = function(accountContactEmailAddress){
//                    accountContact.accountContactEmailAddresses.push(accountContactEmailAddress);
//                    $scope.account.accountContacts.push(accountContact);
//                    Account.update($scope.account).$then(updateSuccessCallback, updateErrorCallback);
//                };
            /**
             * updateSuccessCallback - success handler for successful update
             * @param response
             */
            var updateSuccessCallback = function(response){
                // apply the success message to toastr
                Logger.messageBuilder(response, $scope);
                dialog.close();
            };

            /**
             * updateErrorCallback - error handler for unsuccessful update
             * @param response
             */
            var updateErrorCallback = function(response){
                // apply errors to the $scope.errrors object
                Logger.formValidationMessageBuilder(response, $scope, $scope.accountContactEmailAddressAddForm);
            };

        }])

    .controller('EditAccountContactEmailAddressCtrl', ['$scope','$routeParams','dialog','$location','Account','Logger','account','accountContactId','accountContactEmailAddressId',
        function ($scope,$routeParams, dialog, $location, Account, Logger,account,accountContactId, accountContactEmailAddressId) {
            $scope.message = '';
            $scope.errors = [];
            $scope.title = "Edit Email Address";
            $scope.account = account;

            $scope.cancel = function(){
                dialog.close();
            };

            $.each($scope.account.accountContacts, function(i, v) {
                if (v.id == accountContactId) {
                    $scope.contactIndex =i;
                }
            });

            $.each($scope.account.accountContacts[$scope.contactIndex].accountContactEmailAddresses, function(i, v) {
                if (v.id == accountContactEmailAddressId) {
                    $scope.accountContactEmailAddressIndex =i;
                }
            });

            $scope.updateAccount = function(){
                Account.update($scope.account).$then(updateSuccessCallback, updateErrorCallback);
            };

            /**
             * updateSuccessCallback - success handler for successful update
             * @param response
             */
            var updateSuccessCallback = function(response){
                // apply the success message to toastr
                Logger.messageBuilder(response, $scope);
                dialog.close();
            };

            /**
             * updateErrorCallback - error handler for unsuccessful update
             * @param response
             */
            var updateErrorCallback = function(response){
                // apply errors to the $scope.errrors object
                Logger.formValidationMessageBuilder(response, $scope, $scope.accountContactEmailAddressEditForm);
            };

        }])

    .controller('CreateAccountContactPhoneNumberCtrl', ['$scope','$routeParams','dialog','$location','Account','Logger','account','accountContactId',
        function ($scope,$routeParams, dialog, $location, Account, Logger,account,accountContactId) {
            $scope.message = '';
            $scope.errors = [];
            $scope.title = "Add Phone Number";
            $scope.account = account;

            $scope.cancel = function(){
                dialog.close();
            };

            $.each($scope.account.accountContacts, function(i, v) {
                if (v.id == accountContactId) {
                    $scope.contactIndex =i;
                }
            });

            $scope.saveAccountContactPhoneNumber = function(accountContactPhoneNumber){
                $scope.account.accountContacts[$scope.contactIndex].accountContactPhoneNumbers.push(accountContactPhoneNumber);
                Account.update($scope.account).$then(updateSuccessCallback, updateErrorCallback);
            };

//                $scope.saveAccountContact = function(accountContactPhoneNumber){
//                    accountContact.accountContactPhoneNumberes.push(accountContactPhoneNumber);
//                    $scope.account.accountContacts.push(accountContact);
//                    Account.update($scope.account).$then(updateSuccessCallback, updateErrorCallback);
//                };
            /**
             * updateSuccessCallback - success handler for successful update
             * @param response
             */
            var updateSuccessCallback = function(response){
                // apply the success message to toastr
                Logger.messageBuilder(response, $scope);
                dialog.close();
            };

            /**
             * updateErrorCallback - error handler for unsuccessful update
             * @param response
             */
            var updateErrorCallback = function(response){
                // apply errors to the $scope.errrors object
                Logger.formValidationMessageBuilder(response, $scope, $scope.accountContactPhoneNumberAddForm);
            };

        }])

    .controller('EditAccountContactPhoneNumberCtrl', ['$scope','$routeParams','dialog','$location','Account','Logger','account','accountContactId','accountContactPhoneNumberId',
        function ($scope,$routeParams, dialog, $location, Account, Logger,account,accountContactId, accountContactPhoneNumberId) {
            $scope.message = '';
            $scope.errors = [];
            $scope.title = "Edit Phone Number";
            $scope.account = account;

            $scope.cancel = function(){
                dialog.close();
            };

            $.each($scope.account.accountContacts, function(i, v) {
                if (v.id == accountContactId) {
                    $scope.contactIndex =i;
                }
            });

            $.each($scope.account.accountContacts[$scope.contactIndex].accountContactPhoneNumbers, function(i, v) {
                if (v.id == accountContactPhoneNumberId) {
                    $scope.accountContactPhoneNumberIndex =i;
                }
            });

            $scope.updateAccount = function(accountContactPhoneNumber){
                Account.update($scope.account).$then(updateSuccessCallback, updateErrorCallback);
            };

            /**
             * updateSuccessCallback - success handler for successful update
             * @param response
             */
            var updateSuccessCallback = function(response){
                // apply the success message to toastr
                Logger.messageBuilder(response, $scope);
                dialog.close();
            };

            /**
             * updateErrorCallback - error handler for unsuccessful update
             * @param response
             */
            var updateErrorCallback = function(response){
                // apply errors to the $scope.errrors object
                Logger.formValidationMessageBuilder(response, $scope, $scope.accountContactPhoneNumberEditForm);
            };

        }])

    .controller('CreateAccountContactAddressCtrl', ['$scope','$routeParams','dialog','$location','Account','Logger','account','accountContactId',
    function ($scope,$routeParams, dialog, $location, Account, Logger,account,accountContactId) {
        $scope.message = '';
        $scope.errors = [];
        $scope.title = "Add Address";
        $scope.account = account;

        $scope.cancel = function(){
            dialog.close();
        };

        $.each($scope.account.accountContacts, function(i, v) {
            if (v.id == accountContactId) {
                $scope.contactIndex =i;
            }
        });

        $scope.saveAccountContactAddress = function(accountContactAddress){
            $scope.account.accountContacts[$scope.contactIndex].accountContactAddresses.push(accountContactAddress);
            Account.update($scope.account).$then(updateSuccessCallback, updateErrorCallback);
        };

        /**
         * updateSuccessCallback - success handler for successful update
         * @param response
         */
        var updateSuccessCallback = function(response){
            // apply the success message to toastr
            Logger.messageBuilder(response, $scope);
            dialog.close();
        };

        /**
         * updateErrorCallback - error handler for unsuccessful update
         * @param response
         */
        var updateErrorCallback = function(response){
            // apply errors to the $scope.errrors object
            Logger.formValidationMessageBuilder(response, $scope, $scope.accountContactAddressAddForm);
        };

    }])

    .controller('EditAccountContactAddressCtrl', ['$scope','$routeParams','dialog','$location','Account','Logger','account','accountContactId','accountContactAddressId',
        function ($scope,$routeParams, dialog, $location, Account, Logger,account,accountContactId, accountContactAddressId) {
            $scope.message = '';
            $scope.errors = [];
            $scope.title = "Edit Address";
            $scope.account = account;

            $scope.cancel = function(){
                dialog.close();
            };

            $.each($scope.account.accountContacts, function(i, v) {
                if (v.id == accountContactId) {
                    $scope.contactIndex =i;
                }
            });

            $.each($scope.account.accountContacts[$scope.contactIndex].accountContactAddresses, function(i, v) {
                if (v.id == accountContactAddressId) {
                    $scope.accountContactAddressIndex =i;
                }
            });

            $scope.updateAccount = function(accountContactAddress){
                Account.update($scope.account).$then(updateSuccessCallback, updateErrorCallback);
            };

            /**
             * updateSuccessCallback - success handler for successful update
             * @param response
             */
            var updateSuccessCallback = function(response){
                // apply the success message to toastr
                Logger.messageBuilder(response, $scope);
                dialog.close();
            };

            /**
             * updateErrorCallback - error handler for unsuccessful update
             * @param response
             */
            var updateErrorCallback = function(response){
                // apply errors to the $scope.errrors object
                Logger.formValidationMessageBuilder(response, $scope, $scope.accountContactAddressEditForm);
            };

        }]);


