angular.module('submissionBuilderApp', ['resources.restApi','resources.AccountContactLink','resources.Lead','resources.SubmissionBuilder', 'common.helperFuncs', 'resources.logger', 'ui.bootstrap', 'ui.utils','ngGrid'])

    .config(function ($routeProvider) {
        $routeProvider.
            when('/', {templateUrl: '/submissionBuilder/listPartial', controller: 'submissionBuilderListCtrl'}).
            when('/build/:id', {templateUrl: '/submissionBuilder/builderPartial', controller: 'submissionBuilderCtrl'}).
            when('/lead', {templateUrl: '/submissionBuilder/leadPartial', controller: 'submissionBuilderLeadCtrl' }).
            when('/prepare/:id', {templateUrl: '/submissionBuilder/preparePartial', controller: 'submissionBuilderPrepareCtrl' }).
//            when('/create', {templateUrl: '/account/createPartial', controller: 'CreateAccountCtrl' }).
//            when('/show/:id', {templateUrl: '/account/showPartial', controller: 'ShowAccountCtrl' }).
//            when('/accountContact/:id', {templateUrl: '/account/showAccountContactPartial', controller: 'AccountContactCtrl' }).
            otherwise({ redirectTo: '/' });
    })

    .controller('submissionBuilderListCtrl', ['$scope','$location',
        function ($scope,$location) {

            $scope.submissionData = [
                {
                    dateSent:"07/28/13",
                    from:'Maria Schiller (Renaissance Alliance)',
                    prospect:'Pine Construction',
                    subject:'Submission for Pine Construction',
                    targetDate:'08/25/13',
                    targetDayDiff:'25',
                    expirationDate:'10/01/13',
                    lobs:'BUIL,CAMT,CROP,GLEX',
                    status:"Submitted",
                    response:"Not Submitted",
                    market:"A & M Services"
                },
                {
                    dateSent:"07/28/13",
                    from:'Joanna Christopoulos (Renaissance Alliance)',
                    prospect:'Smith Electronics Corp',
                    subject:'Submission for Smith Electronics Corp',
                    targetDate:'08/25/13',
                    targetDayDiff:'25',
                    expirationDate:'11/01/13',
                    lobs:'UM-S,STOC,VALU,POLL',
                    status:"Submitted",
                    response:"Submitted",
                    market:"AIG"
                },
                {
                    dateSent:"07/28/13",
                    from:'Laurie Lajzer (Renaissance Alliance)',
                    prospect:'Kone Elevators',
                    subject:'Submission for Kone Elevators',
                    targetDate:'08/25/13',
                    targetDayDiff:'25',
                    expirationDate:'12/20/13',
                    lobs:'BR-I,SIGN,CROP,SPEL',
                    status:"Submitted",
                    response:"Not Submitted",
                    market:"Litchfield Mutual"
                },
                {
                    dateSent:"07/28/13",
                    from:'Maria Schiller (Renaissance Alliance)',
                    prospect:'Phillips Networking',
                    subject:'Submission for Phillips Networking',
                    targetDate:'08/25/13',
                    targetDayDiff:'25',
                    expirationDate:'11/08/13',
                    lobs:'RCFL,NE&O,CROP,POLL',
                    status:"Submitted",
                    response:"Not Submitted",
                    market:"Hanover CT"
                },
                {
                    dateSent:"07/28/13",
                    from:'Beth Poplawski (Renaissance Alliance)',
                    prospect:'Brantley Trucking',
                    subject:'Submission for Brantley Trucking',
                    targetDate:'08/25/13',
                    targetDayDiff:'25',
                    expirationDate:'10/15/13',
                    lobs:'CBOP,PROF,SIGN,SPEL',
                    status:"Submitted",
                    response:"Not Submitted",
                    market:"Pilgrim Insurance Co."
                }
            ];

            $scope.totalServerItems = 5;

            $scope.pageHeader = "Submissions";
            $scope.actionButtons = '<button type="button" class="btn btn-mini" ><i class="icon-folder-open"></i> Open</button> ';
//            $scope.sortInfo = { fields:['accountName'], directions: ['asc']};
            $scope.submissionGridOptions = {
                data: 'submissionData',
                totalServerItems: 'totalServerItems',
                enableRowSelection: false,
//                sortInfo:$scope.sortInfo,
                showFilter: true,
                showFooter: true,
                footerRowHeight: 30,
                columnDefs: [
                    {field:'dateSent',displayName:'Date Sent'},
                    {field:'from',displayName:'From'},
                    {field:'prospect',displayName:'Prospect'},
                    {field:'subject',displayName:'Subject'},
                    {field:'targetDate',displayName:'Target Date'},
                    {field:'targetDayDiff',displayName:'+/- Days'},
                    {field:'expirationDate',displayName:'XDate'},
                    {field:'lobs',displayName:'LOBs'},
                    {field:'status',displayName:'My Status'},
                    {field:'response',displayName:'Market Status'},
                    {field:'market',displayName:'Market'},
                    {displayName:'', cellTemplate: $scope.actionButtons, sortable:false}
                ]
            };


//            $scope.showSubmission = function showSubmission(row){
//                $location.path('/show/' + row.entity.ngRowId);
//            };

        }])

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

            $scope.testDocs = [
                {
                    id:1,
                    docName:"AcordApp.pdf"
                },
                {
                    id:2,
                    docName:"LossHistory.pdf"
                },
                {
                    id:3,
                    docName:"SupplementalApp.pdf"
                }
            ];

            SubmissionBuilder.get({id:$routeParams.id},function (data) {
                $scope.master.parentSubmission = data.properties;
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
                    result += " Target Date: " + $filter('date')(lob.targetDate,['MM/dd/yy']);
                if(lob.targetPremium)
                    result += " Target Premium: " + $filter('currency')(lob.targetPremium);

                return result;
            };

            $scope.clear = function clear(){
                $scope.lobs = {};
                $scope.docs = {};
                $scope.markets = {};
            };

            $scope.buildSubmissions = function buildSubmissions(){
                angular.forEach($scope.markets, function(value, key){
                    if(value==true)
                        $scope.buildSubmission(key);
                },angular.noop());

                $scope.clear();
                Logger.success("Success","Submission Created");
            };

            $scope.buildSubmission = function buildSubmission(market){
                var submission = {};
                submission.lobs = [];
                submission.docs = [];
                submission.markets = [];
                submission.markets.push(market);
                submission.index = $scope.index;

                angular.forEach($scope.lobs, function(value, key){
                    if(value==true)
                        submission.lobs.push(key);
                },angular.noop());

                angular.forEach($scope.docs, function(value, key){
                    if(value==true)
                        submission.docs.push(key);
                },angular.noop());

                $scope.master.childSubmissions.push(submission);
                $scope.index ++;

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

            $scope.sendSubmissions = function sendSubmissions(){
                $location.path('/list/');
            };

//            $scope.editSubmission = function editSubmission(submission){
//                $scope.clear();
//                $scope.editingSubmission = true;
//                $scope.tempArrayIndex = $scope.master.childSubmissions.indexOf(submission);
//                $scope.tempIndex = $scope.index;
//                $scope.index = submission.index;
//                angular.forEach(submission.lobs, function(v,i){
//                    $scope.lobs[v]=true;
//                },angular.noop());
//                angular.forEach(submission.docs, function(v,i){
//                    $scope.docs[v]=true;
//                },angular.noop());
//                angular.forEach(submission.markets, function(v,i){
//                    $scope.markets[v]=true;
//                },angular.noop());
//
//            };

//            $scope.updateSubmission = function updateSubmission(){
//                var submission = {};
//                submission.lobs = [];
//                submission.docs = [];
//                submission.markets = [];
//                submission.index = $scope.index;
//
//                $scope.master.childSubmissions.splice($scope.tempArrayIndex,1);
//
//                angular.forEach($scope.lobs, function(value, key){
//                    if(value==true)
//                        submission.lobs.push(key);
//                },angular.noop());
//                angular.forEach($scope.docs, function(value, key){
//                    if(value==true)
//                        submission.docs.push(key);
//                },angular.noop());
//                angular.forEach($scope.markets, function(value, key){
//                    if(value==true)
//                        submission.markets.push(key);
//                },angular.noop());
//
//
//                $scope.master.childSubmissions.push(submission);
//
//                $scope.editingSubmission = false;
//                $scope.index = $scope.tempIndex;
//                $scope.clear();
//                Logger.success("Success","Submission Updated");
//            };

            $scope.cancelUpdate = function cancelUpdate(){
                $scope.editingSubmission = false;
                $scope.index = $scope.tempIndex;
                $scope.clear();
            };


            $scope.getLobDescription = function getLobDescription(lob){
                var lobInstance = $scope.filterItem(lob,$scope.master.parentSubmission.lead.linesOfBusiness);
                return lobInstance.description;
            };

            $scope.getLobPremium = function getLobPremium(lob){
                var lobInstance = $scope.filterItem(lob,$scope.master.parentSubmission.lead.linesOfBusiness);
                return $filter('currency')(lobInstance.targetPremium);
            };

            $scope.getLobTargetDate = function getLobTargetDate(lob){
                var lobInstance = $scope.filterItem(lob,$scope.master.parentSubmission.lead.linesOfBusiness);
                return $filter('date')(lobInstance.targetDate,['MM/dd/yy']);
            };

            $scope.filterAndPrintMarket = function filterAndPrintMarket(market){
                var result;
                angular.forEach($scope.allMarkets, function(v,i){
                    if(v.linkId == market) {result = v;}
                },angular.noop());
                return result.accountContactName + "  (" + result.accountName + ")";
            };

            $scope.filterItem = function filterItem(item,array){
                var result;
                angular.forEach(array, function(v,i){
                    if(v.id == item) {result = v;}
                },angular.noop());
                return result;
            };

            $scope.getTestDocName = function getTestDocName(doc){
                var result;
                angular.forEach($scope.testDocs, function(v,i){
                    if(v.id == doc) {result = v;}
                },angular.noop());
                return result.docName;
            };

            $scope.removeLOBFromSubmission = function removeLOBFromSubmission(submission,lob){
                $scope.master.childSubmissions[submission].lobs.splice($scope.master.childSubmissions[submission].lobs.indexOf(lob),1);
            };


        }
    ]);