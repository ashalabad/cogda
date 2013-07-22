angular.module('accountApp', ['resources.Account','resources.AccountContact','resources.AccountContactLink','resources.AccountContactEmailAddress','resources.AccountContactPhoneNumber','resources.AccountContactAddress', 'common.helperFuncs', 'resources.logger', 'ngGrid', 'ui.bootstrap'])

    .config(function ($routeProvider) {
        $routeProvider.
            when('/', {templateUrl: '/account/listPartial', controller: 'ListAccountCtrl'}).
            when('/edit/:id', {templateUrl: '/account/editPartial', controller: 'EditAccountCtrl' }).
            when('/create', {templateUrl: '/account/createPartial', controller: 'CreateAccountCtrl' }).
            when('/show/:id', {templateUrl: '/account/showPartial', controller: 'ShowAccountCtrl' }).
            when('/accountContact/:id', {templateUrl: '/account/showAccountContactPartial', controller: 'AccountContactCtrl' }).
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
            $scope.editButton = '<button type="button" class="btn btn-mini" data-ng-click="editAccount(row)" ><i class="icon-edit"></i> Edit</button> ';
            $scope.showButton = '<button type="button" class="btn btn-mini" data-ng-click="showAccount(row)" ><i class="icon-eye-open"></i> Show</button> ';
            $scope.favoriteIcon = '<div class="gridIcon" data-ng-show="row.entity.favorite==true"><span class="label label-warning"><i class="icon-star"></i></span></div>';
            $scope.marketIcon = '<div class="gridIcon" data-ng-show="row.entity.isMarket==true"><span class="label label-info"><i class="icon-columns"></i></span></div>';
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
                    {field:'favorite', displayName:'',cellTemplate:$scope.favoriteIcon, width:'25px'},
                    {field:'isMarket', displayName:'',cellTemplate:$scope.marketIcon, width:'25px'},
                    {field:'accountName', displayName:'Account Name',width:'35%'},
                    {field:'accountCode', displayName:'Account Code',width:'10%'},
                    {field:'accountType', displayName:'Account Type',width:'10%'},
                    {field:'primaryContact', displayName:'Primary Contact',sortable:false,width:'25%'},
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

        }
    ])

    .controller('CreateAccountCtrl', ['$scope','$routeParams','$dialog','$location','Account','Logger',
        function ($scope,$routeParams, $dialog, $location, Account, Logger) {
            $scope.accountAddForm={};
            $scope.message = '';
            $scope.errors = [];
            $scope.title = "Create Account";
            $scope.account = {};
            $scope.account.active = true;
            $scope.account.isMarket = false;
            $scope.account.favorite = false;


            $scope.cancel = function cancel(){
                $location.path('/');
            };

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

        }])

    .controller('ShowAccountCtrl', ['$scope','$routeParams','$dialog','$location','Account','Logger','AccountContactLink',
        function ($scope,$routeParams, $dialog, $location, Account, Logger,AccountContactLink) {

            $scope.loadAccount = function(){
                Account.get({id:$routeParams.id},function (data) {
                    $scope.account = data;

                }).$then(function(){
                        $.each($scope.account.accountAddresses, function(i, v) {
                            if (v.primaryAddress == true) {
                                $scope.primaryAccountAddress = v
                                $scope.formattedPrimaryAddress = $scope.formatAddress($scope.primaryAccountAddress);
                                $scope.mapPrimaryAddress = $scope.formatAddressForMapping($scope.formattedPrimaryAddress);
                            }
                        });

                        AccountContactLink.accountContacts({id:$scope.account.id},function(result){
                            $scope.accountContacts = result.properties.contacts;
                        }).$then(function() {
                                AccountContactLink.primaryContact({id:$scope.account.id},function(primaryContact){
                                    $scope.primaryAccountContact = primaryContact;
                                    $scope.formattedPrimaryContact = $scope.formatContact($scope.primaryAccountContact);
                                });
                        });

                    });
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

            $scope.addAccountContact = function addAccountContact(primary){
                var addOpts = {
                    dialogFade: true,
                    backdropFade: true,
                    templateUrl:  'account/createAccountContactPartial',
                    controller: 'CreateAccountContactCtrl',
                    resolve: {
                        account: function() {
                            return angular.copy($scope.account);
                        },
                        primary: function() {
                            return primary
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
                $location.path('/accountContact/' + accountContact.id);
            };

            $scope.unlinkAccountContact = function unlinkAccountContact(accountContact){
                var title = 'Unlink Account Contact';
                var msg = 'Are you sure you want to remove this link?';
                var btns = [{result:'cancel', label: 'Cancel'}, {result:'delete', label: 'Unlink', cssClass: 'btn-danger'}];
                $dialog.messageBox(title, msg, btns)
                    .open()
                    .then(function(result){
                        if(result=='delete'){
                            AccountContactLink.delete({account:$scope.account,accountContact:accountContact}).$then(updateSuccessCallback, updateSuccessCallback);
                        }
                    });
            };

            $scope.designatePrimaryAccountContact = function designatePrimaryAccountContact(accountContact){
                var title = 'Primary Contact';
                var msg = 'Are you sure you want to make this the Primary Contact?';
                var btns = [{result:'cancel', label: 'Cancel'}, {result:'yes', label: 'Yes', cssClass: 'btn-primary'}];
                $dialog.messageBox(title, msg, btns)
                    .open()
                    .then(function(result){
                        if(result=='yes'){
                            AccountContactLink.designatePrimary({account:$scope.account,accountContact:accountContact}).$then(updateSuccessCallback, updateSuccessCallback);
                        }
                    });
            };

            var updateSuccessCallback = function(response){
                // apply the success message to toastr
                Logger.messageBuilder(response, $scope);
                $scope.loadAccount();
            };

            $scope.addAccountLink = function addAccountLink(){
                var addOpts = {
                    dialogFade: true,
                    backdropFade: true,
                    templateUrl:  'account/createAccountLinkPartial',
                    controller: 'CreateAccountLinkCtrl',
                    resolve: {
                        account: function() {
                            return angular.copy($scope.account);
                        }

                    }

                };
                var d = $dialog.dialog(addOpts);
                d.open().then(function(){$scope.loadAccount();});
            };

            $scope.formatContact= function(contact){
//                console.log(contact);
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

    .controller('CreateAccountContactCtrl', ['$scope','$routeParams','dialog','$location','AccountContact','Logger','account','AccountContactLink','primary',
        function ($scope,$routeParams, dialog, $location, AccountContact, Logger,account,AccountContactLink,primary) {
            $scope.message = '';
            $scope.errors = [];
            $scope.title = "Add Contact";
            $scope.accountContactLink = {
                account: account,
                primaryContact: primary,
                accountContact: {
                    firstName:"",
                    middleName:"",
                    lastName:"",
                    accountContactEmailAddresses: [],
                    accountContactPhoneNumbers: []
                }
            };
            $scope.accountContactEmailAddress = {
                primaryEmailAddress: true,
                    emailAddress:""
            };
            $scope.accountContactPhoneNumber = {
                primaryPhoneNumber: true,
                    phoneNumber:""
            };



            $scope.cancel = function(){
                dialog.close();
            };

            $scope.saveAccountContact = function(){
                $scope.accountContactLink.accountContact.accountContactEmailAddresses.push($scope.accountContactEmailAddress);
                $scope.accountContactLink.accountContact.accountContactPhoneNumbers.push($scope.accountContactPhoneNumber);
                AccountContact.save($scope.accountContactLink.accountContact).$then(saveAccountContactLink,updateErrorCallback);
            };

            var saveAccountContactLink = function(response){
//                console.log(response);
                AccountContact.get({id:response.resource.id},function (data) {
                    $scope.accountContactLink.accountContact = data;
                    AccountContactLink.save($scope.accountContactLink).$then(updateSuccessCallback, updateErrorCallback);
                },updateErrorCallback);
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

    .controller('AccountContactCtrl',['$scope','$routeParams','$dialog','$location','Account','Logger','AccountContact','AccountContactEmailAddress','AccountContactPhoneNumber','AccountContactAddress','AccountContactLink',
        function ($scope,$routeParams, $dialog, $location, Account, Logger, AccountContact, AccountContactEmailAddress, AccountContactPhoneNumber, AccountContactAddress,AccountContactLink) {

            $scope.showAccountContact=true;

            $scope.loadAccountContact = function(){
                AccountContact.get({id:$routeParams.id},function (data) {
                    $scope.accountContact = data;
                    AccountContactLink.accounts({id:$scope.accountContact.id},function(result){
                        $scope.accounts = result.properties.accounts;
//                        console.log(result);
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

            $scope.formatAccount= function(account){
                var formattedAccount = "<strong>" + account.accountName + "</strong><br>" + account.accountType.code;
                if(account.accountCode)
                    formattedAccount += "<br>" + account.accountCode;
                $.each(account.accountAddresses, function(i, v) {
                    if (v.primaryAddress == true) {
                        formattedAccount += "<br>" + $scope.formatAddress(v);
                    }
                });
                return formattedAccount;
            };

            $scope.showAccount = function showAccount(account){
                $location.path('/show/' + account.id);
            };

            $scope.editAccountContact = function editAccountContact(){
                $scope.createTemp($scope.accountContact);
                $scope.showAccountContact = false;

            };

            $scope.cancelAccountContactEdit = function cancelAccountContactEdit(){
                $scope.accountContact = angular.copy($scope.temp);
                $scope.showAccountContact = true;

            };

            $scope.createTemp = function createTemp(item){
                $scope.temp = null;
                $scope.temp = angular.copy(item);
            };

            $scope.updateAccountContact = function(){
                AccountContact.update($scope.accountContact).$then(updateSuccessCallback, updateErrorCallback);
            };

            $scope.unlinkAccount = function unlinkAccount(account){
                var title = 'Unlink Account';
                var msg = 'Are you sure you want to remove this link?';
                var btns = [{result:'cancel', label: 'Cancel'}, {result:'delete', label: 'Unlink', cssClass: 'btn-danger'}];
                $dialog.messageBox(title, msg, btns)
                    .open()
                    .then(function(result){
                        if(result=='delete'){
                            AccountContactLink.delete({account:account,accountContact:$scope.accountContact}).$then(deleteCallback, deleteCallback);
                        }
                    });
            };



            $scope.deleteAccountContactEmailAddress = function deleteAccountContactEmailAddress(accountContactEmailAddress){
                var title = 'Delete Email Address';
                var msg = 'Are you sure you want to delete this email address?';
                var btns = [{result:'cancel', label: 'Cancel'}, {result:'delete', label: 'Delete', cssClass: 'btn-danger'}];

                $dialog.messageBox(title, msg, btns)
                    .open()
                    .then(function(result){
                        if(result=='delete')
                            AccountContactEmailAddress.delete({id:accountContactEmailAddress.id}).$then(deleteCallback, deleteCallback);

                    });
            };

            $scope.deleteAccountContactPhoneNumber = function deleteAccountContactPhoneNumber(accountContactPhoneNumber){
                var title = 'Delete Phone Number';
                var msg = 'Are you sure you want to delete this phone number?';
                var btns = [{result:'cancel', label: 'Cancel'}, {result:'delete', label: 'Delete', cssClass: 'btn-danger'}];

                $dialog.messageBox(title, msg, btns)
                    .open()
                    .then(function(result){
                        if(result=='delete')
                            AccountContactPhoneNumber.delete({id:accountContactPhoneNumber.id}).$then(deleteCallback, deleteCallback);

                    });
            };

            $scope.deleteAccountContactAddress = function deleteAccountContactAddress(accountContactAddress){
                var title = 'Delete Address';
                var msg = 'Are you sure you want to delete this address?';
                var btns = [{result:'cancel', label: 'Cancel'}, {result:'delete', label: 'Delete', cssClass: 'btn-danger'}];

                $dialog.messageBox(title, msg, btns)
                    .open()
                    .then(function(result){
                        if(result=='delete')
                            AccountContactAddress.delete({id:accountContactAddress.id}).$then(deleteCallback, deleteCallback);

                    });
            };

            $scope.deleteAccountContact = function deleteAccountContact(){
                var title = 'Delete Account Contact';
                var msg = 'Are you sure you want to delete this account contact?';
                var btns = [{result:'cancel', label: 'Cancel'}, {result:'delete', label: 'Delete', cssClass: 'btn-danger'}];

                $dialog.messageBox(title, msg, btns)
                    .open()
                    .then(function(result){
                        if(result=='delete')
                            AccountContact.delete({id:$scope.accountContact.id}).$then(deleteAccountContactCallback, deleteAccountContactCallback);

                    });
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

            var deleteCallback = function(response){
                // apply the success message to toastr
                Logger.messageBuilder(response, $scope);
                $scope.loadAccountContact();
            };

            var deleteAccountContactCallback = function(response){
                // apply the success message to toastr
                Logger.messageBuilder(response, $scope);
                $location.path('/');
            };

            $scope.addAccountContactLink = function addAccountContactLink(){
                var addOpts = {
                    dialogFade: true,
                    backdropFade: true,
                    templateUrl:  'account/createAccountContactLinkPartial',
                    controller: 'CreateAccountContactLinkCtrl',
                    resolve: {
                        accountContact: function() {
                            return angular.copy($scope.accountContact);
                        }

                    }

                };
                var d = $dialog.dialog(addOpts);
                d.open().then(function(){$scope.loadAccountContact();});
            };

            $scope.addAccountContactEmailAddress = function addAccountContactEmailAddress(){
                var addOpts = {
                    dialogFade: true,
                    backdropFade: true,
                    templateUrl:  'account/createAccountContactEmailAddressPartial',
                    controller: 'CreateAccountContactEmailAddressCtrl',
                    resolve: {
                        accountContact: function() {
                            return angular.copy($scope.accountContact);
                        }

                    }

                };
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
                        accountContact: function() {
                            return angular.copy($scope.accountContact);
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
                        accountContact: function() {
                            return angular.copy($scope.accountContact);
                        }
                    }

                };
//                console.log(addOpts);

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
                        accountContact: function() {
                            return angular.copy($scope.accountContact);
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
                        accountContact: function() {
                            return angular.copy($scope.accountContact);
                        }
                    }

                };
//                console.log(addOpts);

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
                        accountContact: function() {
                            return angular.copy($scope.accountContact);
                        }
                    }

                };
                var d = $dialog.dialog(editOpts);
                d.open().then(function(){$scope.loadAccountContact();});
            };

            $scope.loadAccountContact();
        }])

    .controller('CreateAccountContactEmailAddressCtrl', ['$scope','$routeParams','dialog','AccountContact','Logger','accountContact',
        function ($scope,$routeParams, dialog, AccountContact, Logger, accountContact) {
            $scope.message = '';
            $scope.errors = [];
            $scope.title = "Add Email Address";
            $scope.accountContact = accountContact;

            $scope.cancel = function(){
                dialog.close();
            };

            $scope.saveAccountContactEmailAddress = function(accountContactEmailAddress){
                $scope.accountContact.accountContactEmailAddresses.push(accountContactEmailAddress);
                AccountContact.update($scope.accountContact).$then(updateSuccessCallback, updateErrorCallback);
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
                Logger.formValidationMessageBuilder(response, $scope, $scope.accountContactEmailAddressAddForm);
            };

        }])

    .controller('CreateAccountContactLinkCtrl', ['$scope','$routeParams','dialog','AccountContactLink','Logger','accountContact',
        function ($scope,$routeParams, dialog, AccountContactLink, Logger, accountContact) {
            $scope.message = '';
            $scope.errors = [];
            $scope.title = "Link Account";
            $scope.account = "";

            $scope.loadAccounts = function(){
                AccountContactLink.availableAccounts({id:accountContact.id}, function(data){
                    $scope.accounts = data;
                });
            };

            $scope.cancel = function(){
                dialog.close();
            };

            $scope.saveAccountContactLink = function(){
                AccountContactLink.create({accountContact:accountContact.id,account:$scope.account.id}).$then(updateSuccessCallback, updateErrorCallback);
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
                Logger.formValidationMessageBuilder(response, $scope, $scope.accountContactLinkAddForm);
            };

            $scope.loadAccounts();

        }])

    .controller('CreateAccountLinkCtrl', ['$scope','$routeParams','dialog','AccountContactLink','Logger','account',
        function ($scope,$routeParams, dialog, AccountContactLink, Logger, account) {
            $scope.message = '';
            $scope.errors = [];
            $scope.title = "Link Account Contact";
            $scope.accountContact = "";

            $scope.loadAccountContacts = function(){
                AccountContactLink.availableAccountContacts({id:account.id}, function(data){
                    $scope.accountContacts = data;
                });
            };

            $scope.cancel = function(){
                dialog.close();
            };

            $scope.saveAccountLink = function(){
                AccountContactLink.create({accountContact:$scope.accountContact.id,account:account.id}).$then(updateSuccessCallback, updateErrorCallback);
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
                Logger.formValidationMessageBuilder(response, $scope, $scope.accountLinkAddForm);
            };

            $scope.loadAccountContacts();

        }])

    .controller('EditAccountContactEmailAddressCtrl', ['$scope','$routeParams','dialog','AccountContact','Logger','accountContact','accountContactEmailAddressId',
        function ($scope,$routeParams, dialog, AccountContact, Logger,accountContact, accountContactEmailAddressId) {
            $scope.message = '';
            $scope.errors = [];
            $scope.title = "Edit Email Address";
            $scope.accountContact = accountContact;

            $scope.cancel = function(){
                dialog.close();
            };

            $.each($scope.accountContact.accountContactEmailAddresses, function(i, v) {
                if (v.id == accountContactEmailAddressId) {
                    $scope.accountContactEmailAddressIndex =i;
                }
            });

            $scope.updateAccount = function(){
                AccountContact.update($scope.accountContact).$then(updateSuccessCallback, updateErrorCallback);
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

    .controller('CreateAccountContactPhoneNumberCtrl', ['$scope','$routeParams','dialog','AccountContact','Logger','accountContact',
        function ($scope,$routeParams, dialog, AccountContact, Logger, accountContact) {
            $scope.message = '';
            $scope.errors = [];
            $scope.title = "Add Phone Number";
            $scope.accountContact = accountContact;

            $scope.cancel = function(){
                dialog.close();
            };


            $scope.saveAccountContactPhoneNumber = function(accountContactPhoneNumber){
                $scope.accountContact.accountContactPhoneNumbers.push(accountContactPhoneNumber);
                AccountContact.update($scope.accountContact).$then(updateSuccessCallback, updateErrorCallback);
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
                Logger.formValidationMessageBuilder(response, $scope, $scope.accountContactPhoneNumberAddForm);
            };

        }])

    .controller('EditAccountContactPhoneNumberCtrl', ['$scope','$routeParams','dialog','AccountContact','Logger','accountContact','accountContactPhoneNumberId',
        function ($scope,$routeParams, dialog, AccountContact, Logger,accountContact, accountContactPhoneNumberId) {
            $scope.message = '';
            $scope.errors = [];
            $scope.title = "Edit Phone Number";
            $scope.accountContact = accountContact;

            $scope.cancel = function(){
                dialog.close();
            };

            $.each($scope.accountContact.accountContactPhoneNumbers, function(i, v) {
                if (v.id == accountContactPhoneNumberId) {
                    $scope.accountContactPhoneNumberIndex =i;
                }
            });

            $scope.updateAccount = function(accountContactPhoneNumber){
                AccountContact.update($scope.accountContact).$then(updateSuccessCallback, updateErrorCallback);
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

    .controller('CreateAccountContactAddressCtrl', ['$scope','$routeParams','dialog','AccountContact','Logger','accountContact',
        function ($scope,$routeParams, dialog, AccountContact, Logger,accountContact) {
            $scope.message = '';
            $scope.errors = [];
            $scope.title = "Add Address";
            $scope.accountContact = accountContact;

            $scope.cancel = function(){
                dialog.close();
            };

            $scope.saveAccountContactAddress = function(accountContactAddress){
                $scope.accountContact.accountContactAddresses.push(accountContactAddress);
                AccountContact.update($scope.accountContact).$then(updateSuccessCallback, updateErrorCallback);
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

    .controller('EditAccountContactAddressCtrl', ['$scope','$routeParams','dialog','AccountContact','Logger','accountContact','accountContactAddressId',
        function ($scope,$routeParams, dialog, AccountContact, Logger,accountContact, accountContactAddressId) {
            $scope.message = '';
            $scope.errors = [];
            $scope.title = "Edit Address";
            $scope.accountContact = accountContact;

            $scope.cancel = function(){
                dialog.close();
            };

            $.each($scope.accountContact.accountContactAddresses, function(i, v) {
                if (v.id == accountContactAddressId) {
                    $scope.accountContactAddressIndex =i;
                }
            });

            $scope.updateAccount = function(accountContactAddress){
                AccountContact.update($scope.accountContact).$then(updateSuccessCallback, updateErrorCallback);
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


