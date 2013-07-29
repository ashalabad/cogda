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


    .controller('submissionBuilderCtrl', ['$scope','AccountContactLink','SubmissionBuilder','$location','$routeParams','Lead','$filter',
        function($scope,AccountContactLink,SubmissionBuilder,$location,$routeParams,Lead,$filter){

            $scope.oneAtATime = true;

            SubmissionBuilder.get({id:$routeParams.id},function (data) {
                $scope.submission = data;
                console.log($scope.submission);
                SubmissionBuilder.createChildSubmission({id:$scope.submission.id},function(data){
                    $scope.childSubmission = data;
                },angular.noop());
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

            $scope.toggleAccountContactLink = function toggleAccountContactLink(linkId, val){
//                var checked = $("input[name='"+linkId+"']").attr('checked') ? true : false;
//                $("input[name='"+linkId+"']").attr('checked',val);
//                console.log(linkId + " - checked: " + val);
            };

            $scope.formatLOBDetails = function formatLOBDetails(lob){
                var result = "";
                if(lob.targetDate)
                    result += " Target Date: " + $filter('date')(lob.targetDate,['M/d/yy @ h:mm a']);
                if(lob.targetPremium)
                    result += " Target Premium: " + $filter('currency')(lob.targetPremium);

                return result;
            }


        }]);