angular.module('submissionDialogApp', ['resources.restApi','resources.AccountContactLink','resources.Lead',
        'resources.submission', 'common.helperFuncs', 'resources.logger', 'ui.bootstrap', 'ui.utils'])

    .config(function ($routeProvider) {
        $routeProvider
            .when('/conversation/:id', {templateUrl: '/submissionDialog/messagingPartial', controller: 'messageController'})
            .when('/summary', {templateUrl: '/submissionDialog/submissionSummaryPartial', controller: 'submissionSummaryController' })
            .otherwise({ redirectTo: '/conversation' });
    })

    .controller('submissionDialogMainController', ['$scope', '$location', '$filter', '$routeParams', 'AccountContactLink', 'Lead',
        function($scope, $location, $filter, $routeParams, AccountContactLink, Lead){
            console.log("In submissionDialogMainController");
            $scope.userProfile = {
                firstName: "Maria",
                lastName:  "Schiller"
            }
            $scope.lead = {clientName:'Transpro Logistics Inc.'};
            $scope.lead.linesOfBusiness = [
                {
                    code:'CPKG (CP & GL)',
                    description: 'Commercial Package (CP & GL)'
                },
                {
                    code: 'XAUC',
                    description: 'Commercial Excess Automobile'
                },
                {
                    code: 'CR-S',
                    description: 'Crime'
                }
            ]
            $scope.childSubmissions = [
                {
                    id: 1,
                    accountName: "A.I.M.",
                    contactName: "Brenda Hovey",
                    rfaStatus:   "Not Submitted (3)",
                    myStatus:    "Submitted (3)"
                },
                {
                    id: 2,
                    accountName: "CNA",
                    contactName: "Roger Moore",
                    rfaStatus:   "Not Submitted (3)",
                    myStatus:    "Submitted (3)"
                }
            ];

            $scope.showChildSubmission = function(childSubmission){
                $location.path('/conversation/' + childSubmission.id);
            }

            $scope.showSubmissionSummary = function(){
                $location.path('/summary');
            }
        }

    ])

    .controller('messageController', ['$scope', '$location', '$filter', '$routeParams', 'AccountContactLink',
        function($scope, $location, $filter, $routeParams, AccountContactLink){
            console.log("In messageController controller");
            $scope.leadLineOfBusinesses = [
                {
                    expirationDate: '12/31/2013',
                    currentCarrier: 'None',
                    targetPremium:  500,
                    quotedPremium:  0,
                    commission:     0,
                    myWorkflowStatus: "Not Submitted",
                    marketActivityStatus: "Not Submitted",
                    code:'CPKG (CP & GL)',
                    description: 'Commercial Package (CP & GL)'
                },
                {
                    expirationDate: '12/31/2013',
                    currentCarrier: 'None',
                    targetPremium:  500,
                    quotedPremium:  0,
                    commission:     0,
                    myWorkflowStatus: "Not Submitted",
                    marketActivityStatus: "Not Submitted",
                    code: 'XAUC',
                    description: 'Commercial Excess Automobile'
                },
                {
                    expirationDate: '12/31/2013',
                    currentCarrier: 'None',
                    targetPremium:  300,
                    quotedPremium:  0,
                    commission:     0,
                    myWorkflowStatus: "Not Submitted",
                    marketActivityStatus: "Not Submitted",
                    code: 'CR-S',
                    description: 'Crime'
                }
            ];

            $scope.requestForActions = [
                {
                    from:"Brenda Hovey",
                    to: "Me",
                    sentDate: "08/01/2013",
                    attachment: true,
                    subject: "Submission for Transpro Logistics Inc.",
                    message: "Here is the Transpro Logistics Inc.",
                    dueDate: "08/15/2013"
                },
                {
                    from:"Brenda Hovey",
                    to: "Me",
                    sentDate: "08/15/2013",
                    attachment: true,
                    subject: "Submission for Transpro Logistics Inc.",
                    message: "Here is the Transpro Logistics Inc.",
                    dueDate: ""
                }
            ];

            $scope.isCollapsed = false;

        }
    ])

    .controller('submissionSummaryController', ['$scope', '$location', '$filter', '$routeParams', 'AccountContactLink',
        function($scope, $location, $filter, $routeParams, AccountContactLink){
            console.log("In submissionSummaryController controller");


        }
    ])
;