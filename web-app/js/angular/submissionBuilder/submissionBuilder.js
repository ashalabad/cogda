angular.module('submissionBuilderApp', ['resources.restApi','resources.AccountContactLink', 'common.helperFuncs', 'resources.logger', 'ui.bootstrap', 'ui.utils'])

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


    .controller('submissionBuilderLeadCtrl', ['$scope', '$location', '$filter',
        function($scope, $location,$filter){

            $scope.leadList = [];
            $scope.searchString='';
            $scope.leadListFromController =  [  //TODO: Replace this with the controller call
                {
                    id:1,
                    clientName: "Jaynes Construction, Co."
                },
                {
                    id:2,
                    clientName: "Semper Auc"
                },
                {
                    id:3,
                    clientName: "Donec Mauris"
                },
                {
                    id:4,
                    clientName: "Vestibulum"
                },
                {
                    id:5,
                    clientName: "VitaeT"
                }
            ];

            $scope.$watch('searchString', function(val) {
                if(val.length >0)
                {
                    $scope.leadList = $filter('filter')($scope.leadListFromController,val);
                    $scope.leadList = $filter('orderBy')($scope.leadList,'clientName');
                }
                else
                    $scope.clearSearch();
            });

            $scope.clearSearch = function clearSearch(){
                $scope.leadList = [];
                $scope.searchString='';
            };


//            $scope.buildSubmission = function buildSubmission(leadInstance){
//                $location.path('/prepare/' + leadInstance.id);
//            };
            $scope.buildSubmission = function buildSubmission(leadInstance){
                $location.path('/build/' + leadInstance.id);
            };
        }
    ])

//    .controller('submissionBuilderPrepareCtrl', ['$scope','Submission','$location','$routeParams',
//        function($scope,Submission,$location,$routeParams){
//
//            Submission.create({id:$routeParams.id},function (data) {
//                $location.path('/build' + data.id);
//            },angular.noop());
//
//
//        }
//    ])


    .controller('submissionBuilderCtrl', ['$scope','AccountContactLink',
        function($scope,AccountContactLink){

            $scope.oneAtATime = true;
            $scope.testMe = false;

            $scope.lead = {
                id: 1,
                businessType: {
                    id: 4,
                    code: "Construction",
                    codeFrom: 11,
                    codeTo: 11,
                    description: "Construction"
                },
                clientId: "123456789",
                clientName: "Jaynes Construction, Co.",
                leadType: "PROSPECT",
                leadSubType: "BUSINESS",
                customerServiceRepresentative:"",
                leadNotes: [],
                files: [],
                leadContacts: [],
                leadAddresses: [],
                leadLineOfBusinesses: [
                    {
                        expirationDate: '12/31/2013 11:59:59',
                        targetDate:     '10/31/2013 11:59:59',
                        targetPremium:  5000.00,
                        targetCommission: 500.00,
                        lineOfBusiness:{
                            code: "AR-S",
                            description: "Accts Rec/Val Papers",
                            intCode: 1
                        },
                        renewal: true,
                        remarket: true
                    },
                    {
                        expirationDate: '12/31/2013 11:59:59',
                        targetDate:     '10/31/2013 11:59:59',
                        targetPremium:  5000.00,
                        targetCommission: 500.00,
                        lineOfBusiness:{
                            code: "TRAN",
                            description: "Transportation",
                            intCode: 58
                        },
                        renewal: true,
                        remarket: true
                    }
                ],
                naicsCodes: [
                    {
                        id: 4,
                        code: "23",
                        description: "Construction"
                    }
                ],
                sicCodes: [],
                files: [
                    {
                        id: 1,
                        fileName: "AccordApp.pdf"
                    },
                    {
                        id: 2,
                        fileName: "AOR.pdf"
                    },
                    {
                        id: 3,
                        fileName: "Trailer.jpg"
                    }
                ]
            };

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

            $scope.toggleAccountContactLink = function toggleAccountContactLink(linkId, val){
//                var checked = $("input[name='"+linkId+"']").attr('checked') ? true : false;
//                $("input[name='"+linkId+"']").attr('checked',val);
//                console.log(linkId + " - checked: " + val);
            };


        }]);