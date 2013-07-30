angular.module('submissionBuilderApp', ['resources.restApi','resources.AccountContactLink','resources.Lead','resources.SubmissionBuilder', 'common.helperFuncs', 'resources.logger', 'ui.bootstrap', 'ui.utils'])

    .config(function ($routeProvider) {
        $routeProvider.
            when('/build/:id', {templateUrl: '/submissionBuilder/builderPartial', controller: 'submissionBuilderCtrl'}).
            when('/lead', {templateUrl: '/submissionBuilder/leadPartial', controller: 'submissionBuilderLeadCtrl' }).
            when('/prepare/:id', {templateUrl: '/submissionBuilder/preparePartial', controller: 'submissionBuilderPrepareCtrl' }).
//            when('/create', {templateUrl: '/account/createPartial', controller: 'CreateAccountCtrl' }).
//            when('/show/:id', {templateUrl: '/account/showPartial', controller: 'ShowAccountCtrl' }).
//            when('/accountContact/:id', {templateUrl: '/account/showAccountContactPartial', controller: 'AccountContactCtrl' }).
            otherwise({ redirectTo: '/lead' });
    })


    .controller('submissionBuilderLeadCtrl', ['$scope', '$location', '$filter','Lead',
        function($scope, $location,$filter,Lead){

            $scope.leadList = [];
            $scope.searchString='';

            $scope.$watch('searchString', function(val) {
                if(val.length >0)
                {
                    Lead.search({searchString:val},function(data){
                        $scope.leadList = $filter('filter')(data,val);
                        $scope.leadList = $filter('orderBy')($scope.leadList,'clientName');
                    },angular.noop());

                }
                else
                    $scope.clearSearch();
            });

            $scope.clearSearch = function clearSearch(){
                $scope.leadList = [];
                $scope.searchString='';
            };


            $scope.buildSubmission = function buildSubmission(leadInstance){
                $location.path('/prepare/' + leadInstance.id);
            };
        }
    ])

    .controller('submissionBuilderPrepareCtrl', ['$scope','SubmissionBuilder','$location','$routeParams',
        function($scope,SubmissionBuilder,$location,$routeParams){
            SubmissionBuilder.createParentSubmission({id:$routeParams.id},function (data) {
                $location.path('/build/' + data.id);
            },angular.noop());


        }
    ])


    .controller('submissionBuilderCtrl', ['$scope','AccountContactLink','SubmissionBuilder','$location','$routeParams','Lead','$filter','$dialog','Logger',
        function($scope,AccountContactLink,SubmissionBuilder,$location,$routeParams,Lead,$filter,$dialog,Logger){

            $scope.oneAtATime = true;
            $scope.editingSubmission = false;
            $scope.lobs = {};
            $scope.docs = {};
            $scope.markets = {};
            $scope.index = 1;
            $scope.master = {
                parentSubmission:"",
                childSubmissions: []
            };
//            $scope.master = {
//                parentSubmission:"",
//                childSubmissions: [
//                {
//                    "lobs": [
//                        {
//                            "1": true,
//                            "3": true,
//                            "4": true
//                        }
//                    ],
//                    "docs": [
//                        {}
//                    ],
//                    "markets": [
//                        {
//                            "10ded992789b4f5d9aa8a922858767e0": false,
//                            "8f25760a214b45a0bc4d0cca422b4c2f": false,
//                            "f363f27632634d52b59dd55cd16fcb27": false,
//                            "d2a868965be9409187c133dc04c759b2": true,
//                            "514135f222c246bf9b839ff95ed0459c": true
//                        }
//                    ],
//                    "index": 1
//                },
//                {
//                    "lobs": [
//                        {
//                            "3": true,
//                            "4": true
//                        }
//                    ],
//                    "docs": [
//                        {}
//                    ],
//                    "markets": [
//                        {
//                            "71f81ed490574741a9f35e7011c40a63": true
//                        }
//                    ],
//                    "index": 2
//                },
//                {
//                    "lobs": [
//                        {
//                            "1": false,
//                            "2": true,
//                            "3": true,
//                            "4": true
//                        }
//                    ],
//                    "docs": [
//                        {}
//                    ],
//                    "markets": [
//                        {
//                            "2b7d144cfa3c416daa94940078133690": true,
//                            "a370e0a44b5a4aa3b5eca76b2b24e415": true,
//                            "31653245e9574cb09e43d28c4c5f2a10": true,
//                            "6117f779717240759bf5453002582053": true
//                        }
//                    ],
//                    "index": 3
//                }
//            ]
//            };

            SubmissionBuilder.get({id:$routeParams.id},function (data) {
                $scope.master.parentSubmission = data;
            },angular.noop());

            $scope.marketCriteria = "";


            AccountContactLink.allMarketAccountContactLinks({},function (data) {
                $scope.allMarkets = data;
            },angular.noop());

            AccountContactLink.favoriteMarketAccountContactLinks({},function (data) {
                $scope.favoriteMarkets = data;
            },angular.noop());

            AccountContactLink.allAccountContactMarketLinks({},function (data) {
                $scope.accountContactMarkets = data;
            },angular.noop());

            $scope.formatLOBDetails = function formatLOBDetails(lob){
                var result = "";
                if(lob.targetDate)
                    result += " Target Date: " + $filter('date')(lob.targetDate,['MM/dd/yy @ h:mm a']);
                if(lob.targetPremium)
                    result += " Target Premium: " + $filter('currency')(lob.targetPremium);

                return result;
            };

            $scope.clear = function clear(){
                $scope.lobs = {};
                $scope.docs = {};
                $scope.markets = {};
            };

            $scope.buildSubmission = function buildSubmission(){
                var submission = {};
                submission.lobs = [];
                submission.docs = [];
                submission.markets = [];
                submission.index = $scope.index;

                angular.forEach($scope.lobs, function(value, key){
                    if(value==true)
                        submission.lobs.push(key);
                },angular.noop());
                angular.forEach($scope.docs, function(value, key){
                    if(value==true)
                        submission.docs.push(key);
                },angular.noop());
                angular.forEach($scope.markets, function(value, key){
                    if(value==true)
                        submission.markets.push(key);
                },angular.noop());
                $scope.master.childSubmissions.push(submission);
                $scope.index ++;
                $scope.clear();
                Logger.success("Success","Submission Created");
            };

            $scope.deleteSubmission = function deleteSubmission(submission){
                var title = 'Delete Submission';
                var msg = 'Are you sure you want to remove this submission?  It has not been sent.';
                var btns = [{result:'cancel', label: 'Cancel'}, {result:'delete', label: 'Delete', cssClass: 'btn-danger'}];
                $dialog.messageBox(title, msg, btns)
                    .open()
                    .then(function(result){
                        if(result=='delete'){
                            $scope.master.childSubmissions.splice($scope.master.childSubmissions.indexOf(submission),1);
                        }
                    });
            };

            $scope.editSubmission = function editSubmission(submission){
                $scope.clear();
                $scope.editingSubmission = true;
                $scope.tempArrayIndex = $scope.master.childSubmissions.indexOf(submission);
                $scope.tempIndex = $scope.index;
                $scope.index = submission.index;
                angular.forEach(submission.lobs, function(v,i){
                    $scope.lobs[v]=true;
                },angular.noop());
                angular.forEach(submission.docs, function(v,i){
                    $scope.docs[v]=true;
                },angular.noop());
                angular.forEach(submission.markets, function(v,i){
                    $scope.markets[v]=true;
                },angular.noop());

            };

            $scope.updateSubmission = function updateSubmission(){
                var submission = {};
                submission.lobs = [];
                submission.docs = [];
                submission.markets = [];
                submission.index = $scope.index;

                $scope.master.childSubmissions.splice($scope.tempArrayIndex,1);

                angular.forEach($scope.lobs, function(value, key){
                    if(value==true)
                        submission.lobs.push(key);
                },angular.noop());
                angular.forEach($scope.docs, function(value, key){
                    if(value==true)
                        submission.docs.push(key);
                },angular.noop());
                angular.forEach($scope.markets, function(value, key){
                    if(value==true)
                        submission.markets.push(key);
                },angular.noop());


                $scope.master.childSubmissions.push(submission);

                $scope.editingSubmission = false;
                $scope.index = $scope.tempIndex;
                $scope.clear();
                Logger.success("Success","Submission Updated");
            };

            $scope.cancelUpdate = function cancelUpdate(){
                $scope.editingSubmission = false;
                $scope.index = $scope.tempIndex;
                $scope.clear();
            };


            $scope.filterAndPrintLOB = function filterAndPrintLOB(lob){
                var lobInstance = $scope.filterItem(lob,$scope.master.parentSubmission.lead.linesOfBusiness);
                return lobInstance.lineOfBusiness.description + " - " + $filter('date')(lobInstance.expirationDate,['MM/dd/yy']);
            };

            $scope.filterAndPrintMarket = function filterAndPrintMarket(market){
                var result;
                angular.forEach($scope.allMarkets, function(v,i){
                    if(v.linkId == market) {result = v;}
                },angular.noop());
                return result.accountContactName;
            };

            $scope.filterItem = function filterItem(item,array){
                var result;
                angular.forEach(array, function(v,i){
                    if(v.id == item) {result = v;}
                },angular.noop());
                return result;
            };


        }
    ]);