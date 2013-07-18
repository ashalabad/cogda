angular.module('submissionBuilderApp', ['resources.restApi', 'common.helperFuncs', 'resources.logger', 'ui.bootstrap'])

    .config(function ($routeProvider) {
        $routeProvider
            .when(
            '/',
            {
                controller: 'submissionBuilderController',
                templateUrl: '/submissionBuilder/showBuilder'
            }
        )
        .otherwise({ redirectTo: '/' });
    })
    .controller('submissionBuilderController', ['$scope', '$routeParams', '$location', 'RestApi', 'Logger',
        function($scope, $routeParams, $location, RestApi, Logger){

            $scope.oneAtATime = false;

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
            }

            $scope.marketCriteria = "";

            var indexedAccounts = [];

            $scope.accountContactLinksToFilter = function(){
                indexedAccounts = [];
                return $scope.accountContactLinks;
            };

            $scope.filterAccounts = function(accountContactLink){
                var accountIsNew = indexedAccounts.indexOf(accountContactLink.companyName) == -1;
                if (accountIsNew) {
                    indexedAccounts.push(accountContactLink.companyName);
                }
                return accountIsNew;
            };

            $scope.accountContactLinks = [
                {
                    "companyName": "Hanover CT",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Hanover CT",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Hanover CT",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Hanover CT",
                    "contactFirstName": "Michael",
                    "contactLastName": "Kass",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "michael.kass@renaissanceins.com"
                },
                {
                    "companyName": "Hanover CT",
                    "contactFirstName": "RSP",
                    "contactLastName": "Rating TZ",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "renrp2@renaissanceins.com"
                },
                {
                    "companyName": "Hanover CT",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "Hanover CT",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Hanover CT",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Hanover CT",
                    "contactFirstName": "c",
                    "contactLastName": "j",
                    "contactCompanyName": "NULL",
                    "contactEmail": "christian.jaynes@gmail.com"
                },
                {
                    "companyName": "Hanover CT",
                    "contactFirstName": "maria",
                    "contactLastName": "Schiller",
                    "contactCompanyName": "Cochrane & Porter Insurance Agency, Inc.",
                    "contactEmail": "maria@renaissanceins.com"
                },
                {
                    "companyName": "Hanover CT",
                    "contactFirstName": "maria",
                    "contactLastName": "Schiller",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "maria_rais@renaissanceins.com"
                },
                {
                    "companyName": "Hanover MA",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Hanover MA",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Hanover MA",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Hanover MA",
                    "contactFirstName": "Michael",
                    "contactLastName": "Kass",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "michael.kass@renaissanceins.com"
                },
                {
                    "companyName": "Hanover MA",
                    "contactFirstName": "RSP",
                    "contactLastName": "Rating TZ",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "renrp2@renaissanceins.com"
                },
                {
                    "companyName": "Hanover MA",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "Hanover MA",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Hanover MA",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "CNA",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "CNA",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "CNA",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "CNA",
                    "contactFirstName": "RSP",
                    "contactLastName": "Rating TZ",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "renrp2@renaissanceins.com"
                },
                {
                    "companyName": "CNA",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "CNA",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "CNA",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "CNA",
                    "contactFirstName": "c",
                    "contactLastName": "j",
                    "contactCompanyName": "NULL",
                    "contactEmail": "christian.jaynes@gmail.com"
                },
                {
                    "companyName": "Commerce",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Commerce",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Commerce",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Commerce",
                    "contactFirstName": "RSP",
                    "contactLastName": "Rating TZ",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "renrp2@renaissanceins.com"
                },
                {
                    "companyName": "Commerce",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "Commerce",
                    "contactFirstName": "Beth",
                    "contactLastName": "Poplawski",
                    "contactCompanyName": "NULL",
                    "contactEmail": "bepoplawski@mapfreusa.com"
                },
                {
                    "companyName": "Commerce",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Commerce",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Great American-non profit",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Great American-non profit",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "NULL",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "Great American-non profit",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Great American-non profit",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Great American-non profit",
                    "contactFirstName": "RSP",
                    "contactLastName": "Rating TZ",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "renrp2@renaissanceins.com"
                },
                {
                    "companyName": "Great American-non profit",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Great American-non profit",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Great American Property",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Great American Property",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Great American Property",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Great American Property",
                    "contactFirstName": "RSP",
                    "contactLastName": "Rating TZ",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "renrp2@renaissanceins.com"
                },
                {
                    "companyName": "Great American Property",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "Great American Property",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Great American Property",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Harleysville",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Harleysville",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Harleysville",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Harleysville",
                    "contactFirstName": "Michael",
                    "contactLastName": "Kass",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "michael.kass@renaissanceins.com"
                },
                {
                    "companyName": "Harleysville",
                    "contactFirstName": "RSP",
                    "contactLastName": "Rating TZ",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "renrp2@renaissanceins.com"
                },
                {
                    "companyName": "Harleysville",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "Harleysville",
                    "contactFirstName": "Michelle",
                    "contactLastName": "Abasciano",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "michelle.abasciano@renaissanceins.com"
                },
                {
                    "companyName": "Harleysville",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Harleysville",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Hartford",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Hartford",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Hartford",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Hartford",
                    "contactFirstName": "Michael",
                    "contactLastName": "Kass",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "michael.kass@renaissanceins.com"
                },
                {
                    "companyName": "Hartford",
                    "contactFirstName": "RSP",
                    "contactLastName": "Rating TZ",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "renrp2@renaissanceins.com"
                },
                {
                    "companyName": "Hartford",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "Hartford",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Hartford",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "MiddleOak",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "MiddleOak",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "MiddleOak",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "MiddleOak",
                    "contactFirstName": "RSP",
                    "contactLastName": "Rating TZ",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "renrp2@renaissanceins.com"
                },
                {
                    "companyName": "MiddleOak",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "MiddleOak",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "MiddleOak",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "NGM  MA",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "NGM  MA",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "NGM  MA",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "NGM  MA",
                    "contactFirstName": "Michele",
                    "contactLastName": "Szlajen",
                    "contactCompanyName": "NULL",
                    "contactEmail": "szlajenm@msagroup.com"
                },
                {
                    "companyName": "NGM  MA",
                    "contactFirstName": "RSP",
                    "contactLastName": "Rating TZ",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "renrp2@renaissanceins.com"
                },
                {
                    "companyName": "NGM  MA",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "NGM  MA",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "NGM  MA",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "NGM CT",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "NGM CT",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "NGM CT",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "NGM CT",
                    "contactFirstName": "Joseph",
                    "contactLastName": "Pelkey",
                    "contactCompanyName": "NULL",
                    "contactEmail": "pelkeyj@msagroup.com"
                },
                {
                    "companyName": "NGM CT",
                    "contactFirstName": "RSP",
                    "contactLastName": "Rating TZ",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "renrp2@renaissanceins.com"
                },
                {
                    "companyName": "NGM CT",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "NGM CT",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "NGM CT",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Liberty Mutual CT",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Liberty Mutual CT",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Liberty Mutual CT",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Liberty Mutual CT",
                    "contactFirstName": "RSP",
                    "contactLastName": "Rating TZ",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "renrp2@renaissanceins.com"
                },
                {
                    "companyName": "Liberty Mutual CT",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "Liberty Mutual CT",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Liberty Mutual CT",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Liberty Mutual MA",
                    "contactFirstName": "Peerless-MA",
                    "contactLastName": "Submissions",
                    "contactCompanyName": "NULL",
                    "contactEmail": "nero-maro.support@peerless-ins.com"
                },
                {
                    "companyName": "Liberty Mutual MA",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Liberty Mutual MA",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Liberty Mutual MA",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Liberty Mutual MA",
                    "contactFirstName": "RSP",
                    "contactLastName": "Rating",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "renrp2@renaissanceins.com"
                },
                {
                    "companyName": "Liberty Mutual MA",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "Liberty Mutual MA",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Liberty Mutual MA",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Liberty Mutual RI",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Liberty Mutual RI",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Liberty Mutual RI",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Liberty Mutual RI",
                    "contactFirstName": "RSP",
                    "contactLastName": "Rating TZ",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "renrp2@renaissanceins.com"
                },
                {
                    "companyName": "Liberty Mutual RI",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "Liberty Mutual RI",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Liberty Mutual RI",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Philadelphia",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Philadelphia",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Philadelphia",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Philadelphia",
                    "contactFirstName": "RSP",
                    "contactLastName": "Rating TZ",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "renrp2@renaissanceins.com"
                },
                {
                    "companyName": "Philadelphia",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "Philadelphia",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Philadelphia",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Preferred Mutual",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Preferred Mutual",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Preferred Mutual",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Preferred Mutual",
                    "contactFirstName": "RSP",
                    "contactLastName": "Rating TZ",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "renrp2@renaissanceins.com"
                },
                {
                    "companyName": "Preferred Mutual",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "Preferred Mutual",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Preferred Mutual",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "QBE",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "QBE",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "QBE",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "QBE",
                    "contactFirstName": "RSP",
                    "contactLastName": "Rating TZ",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "renrp2@renaissanceins.com"
                },
                {
                    "companyName": "QBE",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "QBE",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "QBE",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "QBE",
                    "contactFirstName": "Christian",
                    "contactLastName": "Jaynes",
                    "contactCompanyName": "NULL",
                    "contactEmail": "christian.jaynes@gmail.com"
                },
                {
                    "companyName": "Quincy Mutual",
                    "contactFirstName": "Meghan",
                    "contactLastName": "Lavery",
                    "contactCompanyName": "NULL",
                    "contactEmail": "mlavery@quincymutual.com"
                },
                {
                    "companyName": "Quincy Mutual",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Quincy Mutual",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Quincy Mutual",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Quincy Mutual",
                    "contactFirstName": "RSP",
                    "contactLastName": "Rating TZ",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "renrp2@renaissanceins.com"
                },
                {
                    "companyName": "Quincy Mutual",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "Quincy Mutual",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Quincy Mutual",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Safety",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Safety",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Safety",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Safety",
                    "contactFirstName": "RSP",
                    "contactLastName": "Rating TZ",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "renrp2@renaissanceins.com"
                },
                {
                    "companyName": "Safety",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "Safety",
                    "contactFirstName": "Laurie",
                    "contactLastName": "Lajzer",
                    "contactCompanyName": "NULL",
                    "contactEmail": "laurielajzer@safetyinsurance.com"
                },
                {
                    "companyName": "Safety",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Safety",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "State Auto  MA",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "State Auto  MA",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "State Auto  MA",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "State Auto  MA",
                    "contactFirstName": "RSP",
                    "contactLastName": "Rating TZ",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "renrp2@renaissanceins.com"
                },
                {
                    "companyName": "State Auto  MA",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "State Auto  MA",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "State Auto  MA",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Tower",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Tower",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Tower",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Tower",
                    "contactFirstName": "RSP",
                    "contactLastName": "Rating TZ",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "renrp2@renaissanceins.com"
                },
                {
                    "companyName": "Tower",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "Tower",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Tower",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Travelers CT",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Travelers CT",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Travelers CT",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Travelers CT",
                    "contactFirstName": "RSP",
                    "contactLastName": "Rating TZ",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "renrp2@renaissanceins.com"
                },
                {
                    "companyName": "Travelers CT",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "Travelers CT",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Travelers CT",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Travelers MA",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Travelers MA",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Travelers MA",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Travelers MA",
                    "contactFirstName": "RSP",
                    "contactLastName": "Rating TZ",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "renrp2@renaissanceins.com"
                },
                {
                    "companyName": "Travelers MA",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "Travelers MA",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Travelers MA",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Travelers MA",
                    "contactFirstName": "maria",
                    "contactLastName": "Schiller",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "maria_rais@renaissanceins.com"
                },
                {
                    "companyName": "Utica",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Utica",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Utica",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Utica",
                    "contactFirstName": "RSP",
                    "contactLastName": "Rating TZ",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "renrp2@renaissanceins.com"
                },
                {
                    "companyName": "Utica",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "Utica",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Utica",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "EZPayIns-Utica",
                    "contactFirstName": "Joanna",
                    "contactLastName": "Christopoulos",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "joanna.christopoulos@renaissanceins.com"
                },
                {
                    "companyName": "EZPayIns-Star Insurance Company",
                    "contactFirstName": "Joanna",
                    "contactLastName": "Christopoulos",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "joanna.christopoulos@renaissanceins.com"
                },
                {
                    "companyName": "EZPayIns-St. Paul Travelers",
                    "contactFirstName": "Joanna",
                    "contactLastName": "Christopoulos",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "joanna.christopoulos@renaissanceins.com"
                },
                {
                    "companyName": "EZPayIns-Liberty Mutual",
                    "contactFirstName": "Joanna",
                    "contactLastName": "Christopoulos",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "joanna.christopoulos@renaissanceins.com"
                },
                {
                    "companyName": "EZPayIns-National Grange Mutual Ins. Co.",
                    "contactFirstName": "Joanna",
                    "contactLastName": "Christopoulos",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "joanna.christopoulos@renaissanceins.com"
                },
                {
                    "companyName": "EZPayIns-Cennairus-V3",
                    "contactFirstName": "Joanna",
                    "contactLastName": "Christopoulos",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "joanna.christopoulos@renaissanceins.com"
                },
                {
                    "companyName": "EZPayIns-Cennairus-Chartis",
                    "contactFirstName": "Joanna",
                    "contactLastName": "Christopoulos",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "joanna.christopoulos@renaissanceins.com"
                },
                {
                    "companyName": "EZPayIns-Cennairus, LLC",
                    "contactFirstName": "Joanna",
                    "contactLastName": "Christopoulos",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "joanna.christopoulos@renaissanceins.com"
                },
                {
                    "companyName": "EZPayIns-AmTrust North America",
                    "contactFirstName": "Joanna",
                    "contactLastName": "Christopoulos",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "joanna.christopoulos@renaissanceins.com"
                },
                {
                    "companyName": "CNA GraphiComm",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "CNA GraphiComm",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "CNA GraphiComm",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "CNA GraphiComm",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "CNA GraphiComm",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Zurich",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Zurich",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Zurich",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Zurich",
                    "contactFirstName": "RSP",
                    "contactLastName": "Rating TZ",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "renrp2@renaissanceins.com"
                },
                {
                    "companyName": "Zurich",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "Zurich",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Zurich",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Renaissance Alliance",
                    "contactFirstName": "Joanna",
                    "contactLastName": "Christopoulos",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "joanna.christopoulos@renaissanceins.com"
                },
                {
                    "companyName": "Renaissance Alliance",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Renaissance Alliance",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Renaissance Alliance",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Renaissance Alliance",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Renaissance Alliance",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "XS Brokers Insurance Agency Inc.",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "XS Brokers Insurance Agency Inc.",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "XS Brokers Insurance Agency Inc.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "XS Brokers Insurance Agency Inc.",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "XS Brokers Insurance Agency Inc.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "zzzDO NOT USE RESOURCE PRO",
                    "contactFirstName": "DO NOT",
                    "contactLastName": "USE",
                    "contactCompanyName": "NULL",
                    "contactEmail": "fake@fake.com"
                },
                {
                    "companyName": "ZZ EZPAY MARKETING",
                    "contactFirstName": "Maria",
                    "contactLastName": "Schiller",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "joanna.christopoulos@renaissanceins.com"
                },
                {
                    "companyName": "Markel Insurance Co.",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Markel Insurance Co.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Markel Insurance Co.",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Markel Insurance Co.",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "Markel Insurance Co.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Markel Insurance Co.",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "ZZzz Peerless DO NOT USE",
                    "contactFirstName": "c",
                    "contactLastName": "j",
                    "contactCompanyName": "NULL",
                    "contactEmail": "christian.jaynes@gmail.com"
                },
                {
                    "companyName": "Pilgrim Insurance Co.",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Pilgrim Insurance Co.",
                    "contactFirstName": "Tim",
                    "contactLastName": "O'Connor",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "tim.oconnor@renaissanceins.com"
                },
                {
                    "companyName": "Pilgrim Insurance Co.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Pilgrim Insurance Co.",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Pilgrim Insurance Co.",
                    "contactFirstName": "Michael",
                    "contactLastName": "Kass",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "michael.kass@renaissanceins.com"
                },
                {
                    "companyName": "Pilgrim Insurance Co.",
                    "contactFirstName": "RSP",
                    "contactLastName": "Rating TZ",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "renrp2@renaissanceins.com"
                },
                {
                    "companyName": "Pilgrim Insurance Co.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Boston Insurance",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Boston Insurance",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Boston Insurance",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Boston Insurance",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "Boston Insurance",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Boston Insurance",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Chubb Group",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Chubb Group",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Chubb Group",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Chubb Group",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "Chubb Group",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Chubb Group",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Target Insurance Services",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Target Insurance Services",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Target Insurance Services",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Target Insurance Services",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "Target Insurance Services",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Target Insurance Services",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "S.H. Smith & Company",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "S.H. Smith & Company",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "S.H. Smith & Company",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "S.H. Smith & Company",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "S.H. Smith & Company",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "S.H. Smith & Company",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "PMA",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "PMA",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "PMA",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "PMA",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "PMA",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "PMA",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Applied Underwriters",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Applied Underwriters",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Applied Underwriters",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Applied Underwriters",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "Applied Underwriters",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "AmWINS Brokerage of New England",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "AmWINS Brokerage of New England",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "AmWINS Brokerage of New England",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "AmWINS Brokerage of New England",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "AmWINS Brokerage of New England",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "AmWINS Brokerage of New England",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Quaker Special Risk",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Quaker Special Risk",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Quaker Special Risk",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Quaker Special Risk",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "Quaker Special Risk",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Quaker Special Risk",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "The Fairway Agency Inc",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "The Fairway Agency Inc",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "The Fairway Agency Inc",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "The Fairway Agency Inc",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "The Fairway Agency Inc",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "The Fairway Agency Inc",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "A I Risk Specialists Insurance",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "A I Risk Specialists Insurance",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "A I Risk Specialists Insurance",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "A I Risk Specialists Insurance",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "A I Risk Specialists Insurance",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "A I Risk Specialists Insurance",
                    "contactFirstName": "maria",
                    "contactLastName": "Schiller",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "maria_rais@renaissanceins.com"
                },
                {
                    "companyName": "A.I.M.",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "A.I.M.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "A.I.M.",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "A.I.M.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "A.I.M.",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "A.I.M.",
                    "contactFirstName": "maria",
                    "contactLastName": "Schiller",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "maria_rais@renaissanceins.com"
                },
                {
                    "companyName": "American European Insurance Group",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "American European Insurance Group",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "American European Insurance Group",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "American European Insurance Group",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "American European Insurance Group",
                    "contactFirstName": "maria",
                    "contactLastName": "Schiller",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "maria_rais@renaissanceins.com"
                },
                {
                    "companyName": "Risk Placement Services, Inc.",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Risk Placement Services, Inc.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Risk Placement Services, Inc.",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Risk Placement Services, Inc.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Risk Placement Services, Inc.",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Cardinal Comp, LLC",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Cardinal Comp, LLC",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Cardinal Comp, LLC",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Cardinal Comp, LLC",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Cardinal Comp, LLC",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Brownstone Insurance Agency, Inc.",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Brownstone Insurance Agency, Inc.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Brownstone Insurance Agency, Inc.",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Brownstone Insurance Agency, Inc.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Brownstone Insurance Agency, Inc.",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "AON Assn Services",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "AON Assn Services",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "AON Assn Services",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "AON Assn Services",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "AON Assn Services",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "AmTrust North America",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "AmTrust North America",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "AmTrust North America",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "AmTrust North America",
                    "contactFirstName": "ResourcePro",
                    "contactLastName": "Terry Zhang",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "renrp2@renaissanceins.com"
                },
                {
                    "companyName": "AmTrust North America",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "AmTrust North America",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "AmTrust North America",
                    "contactFirstName": "maria",
                    "contactLastName": "Schiller",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "maria_rais@renaissanceins.com"
                },
                {
                    "companyName": "A & M Services",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "A & M Services",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "A & M Services",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "A & M Services",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "A & M Services",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "A & M Services",
                    "contactFirstName": "maria",
                    "contactLastName": "Schiller",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "maria_rais@renaissanceins.com"
                },
                {
                    "companyName": "American Specialty Insurance & Risk Services",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "American Specialty Insurance & Risk Services",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "American Specialty Insurance & Risk Services",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "American Specialty Insurance & Risk Services",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "American Specialty Insurance & Risk Services",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "American Specialty Insurance & Risk Services",
                    "contactFirstName": "maria",
                    "contactLastName": "Schiller",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "maria_rais@renaissanceins.com"
                },
                {
                    "companyName": "American Modern Insurance Group",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "American Modern Insurance Group",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "American Modern Insurance Group",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "American Modern Insurance Group",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "American Modern Insurance Group",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "American Modern Insurance Group",
                    "contactFirstName": "maria",
                    "contactLastName": "Schiller",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "maria_rais@renaissanceins.com"
                },
                {
                    "companyName": "Distinguished Programs Insurance Brokerage",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Distinguished Programs Insurance Brokerage",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Distinguished Programs Insurance Brokerage",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Distinguished Programs Insurance Brokerage",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Distinguished Programs Insurance Brokerage",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Distel Group",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Distel Group",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Distel Group",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Distel Group",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Distel Group",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "DeCotis Insurance Associates of Massachusetts",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "DeCotis Insurance Associates of Massachusetts",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "DeCotis Insurance Associates of Massachusetts",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "DeCotis Insurance Associates of Massachusetts",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "DeCotis Insurance Associates of Massachusetts",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Connecticut Underwriters, Inc.",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Connecticut Underwriters, Inc.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Connecticut Underwriters, Inc.",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Connecticut Underwriters, Inc.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Connecticut Underwriters, Inc.",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Conexco Insurance Agency Inc.",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Conexco Insurance Agency Inc.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Conexco Insurance Agency Inc.",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Conexco Insurance Agency Inc.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Conexco Insurance Agency Inc.",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Cluett Commercial Insurance Agency",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Cluett Commercial Insurance Agency",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Cluett Commercial Insurance Agency",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Cluett Commercial Insurance Agency",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Cluett Commercial Insurance Agency",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Century Insurance Group",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Century Insurance Group",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Century Insurance Group",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Century Insurance Group",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Century Insurance Group",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Cennairus, LLC",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Cennairus, LLC",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Cennairus, LLC",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Cennairus, LLC",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Cennairus, LLC",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Fireman's Fund Insurance Co.",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Fireman's Fund Insurance Co.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Fireman's Fund Insurance Co.",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Fireman's Fund Insurance Co.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Fireman's Fund Insurance Co.",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Farmers Insurance Group Co",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Farmers Insurance Group Co",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Farmers Insurance Group Co",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Farmers Insurance Group Co",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Farmers Insurance Group Co",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Ian H. Graham Insurance",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Ian H. Graham Insurance",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Ian H. Graham Insurance",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Ian H. Graham Insurance",
                    "contactFirstName": "Michael",
                    "contactLastName": "Kass",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "michael.kass@renaissanceins.com"
                },
                {
                    "companyName": "Ian H. Graham Insurance",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Ian H. Graham Insurance",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "H. T. Bailey So/Customhouse",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "H. T. Bailey So/Customhouse",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "H. T. Bailey So/Customhouse",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "H. T. Bailey So/Customhouse",
                    "contactFirstName": "Michael",
                    "contactLastName": "Kass",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "michael.kass@renaissanceins.com"
                },
                {
                    "companyName": "H. T. Bailey So/Customhouse",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "H. T. Bailey So/Customhouse",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "H. T. Bailey Ins. Agency",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "H. T. Bailey Ins. Agency",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "H. T. Bailey Ins. Agency",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "H. T. Bailey Ins. Agency",
                    "contactFirstName": "Michael",
                    "contactLastName": "Kass",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "michael.kass@renaissanceins.com"
                },
                {
                    "companyName": "H. T. Bailey Ins. Agency",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "H. T. Bailey Ins. Agency",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Howden Insurance Brokers Inc",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Howden Insurance Brokers Inc",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Howden Insurance Brokers Inc",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Howden Insurance Brokers Inc",
                    "contactFirstName": "Michael",
                    "contactLastName": "Kass",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "michael.kass@renaissanceins.com"
                },
                {
                    "companyName": "Howden Insurance Brokers Inc",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Howden Insurance Brokers Inc",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Holyoke Mutual Insurance Co",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Holyoke Mutual Insurance Co",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Holyoke Mutual Insurance Co",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Holyoke Mutual Insurance Co",
                    "contactFirstName": "Michael",
                    "contactLastName": "Kass",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "michael.kass@renaissanceins.com"
                },
                {
                    "companyName": "Holyoke Mutual Insurance Co",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Holyoke Mutual Insurance Co",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Greater New York Mutual Insurance",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Greater New York Mutual Insurance",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Greater New York Mutual Insurance",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Greater New York Mutual Insurance",
                    "contactFirstName": "Michael",
                    "contactLastName": "Kass",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "michael.kass@renaissanceins.com"
                },
                {
                    "companyName": "Greater New York Mutual Insurance",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Greater New York Mutual Insurance",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Global Aerospace",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Global Aerospace",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Global Aerospace",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Global Aerospace",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Global Aerospace",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "First Cardinal Corporation",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "First Cardinal Corporation",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "First Cardinal Corporation",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "First Cardinal Corporation",
                    "contactFirstName": "Michael",
                    "contactLastName": "Kass",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "michael.kass@renaissanceins.com"
                },
                {
                    "companyName": "First Cardinal Corporation",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "First Cardinal Corporation",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Liquor Liability Joint UW",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Liquor Liability Joint UW",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Liquor Liability Joint UW",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Liquor Liability Joint UW",
                    "contactFirstName": "Michael",
                    "contactLastName": "Kass",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "michael.kass@renaissanceins.com"
                },
                {
                    "companyName": "Liquor Liability Joint UW",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Liquor Liability Joint UW",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Lancer Insurance Company",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Lancer Insurance Company",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Lancer Insurance Company",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Lancer Insurance Company",
                    "contactFirstName": "Michael",
                    "contactLastName": "Kass",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "michael.kass@renaissanceins.com"
                },
                {
                    "companyName": "Lancer Insurance Company",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Lancer Insurance Company",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Kimball Associates",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Kimball Associates",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Kimball Associates",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Kimball Associates",
                    "contactFirstName": "Michael",
                    "contactLastName": "Kass",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "michael.kass@renaissanceins.com"
                },
                {
                    "companyName": "Kimball Associates",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Kimball Associates",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Keating Group",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Keating Group",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Keating Group",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Keating Group",
                    "contactFirstName": "Michael",
                    "contactLastName": "Kass",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "michael.kass@renaissanceins.com"
                },
                {
                    "companyName": "Keating Group",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Keating Group",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "K&K Insurance Group, Inc.",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "K&K Insurance Group, Inc.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "K&K Insurance Group, Inc.",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "K&K Insurance Group, Inc.",
                    "contactFirstName": "Michael",
                    "contactLastName": "Kass",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "michael.kass@renaissanceins.com"
                },
                {
                    "companyName": "K&K Insurance Group, Inc.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "K&K Insurance Group, Inc.",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Hospitality Mutual Insurance Company",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Hospitality Mutual Insurance Company",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Hospitality Mutual Insurance Company",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Hospitality Mutual Insurance Company",
                    "contactFirstName": "Michael",
                    "contactLastName": "Kass",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "michael.kass@renaissanceins.com"
                },
                {
                    "companyName": "Hospitality Mutual Insurance Company",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Hospitality Mutual Insurance Company",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Joseph Chiarello & Co.,Inc.",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Joseph Chiarello & Co.,Inc.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Joseph Chiarello & Co.,Inc.",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Joseph Chiarello & Co.,Inc.",
                    "contactFirstName": "Michael",
                    "contactLastName": "Kass",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "michael.kass@renaissanceins.com"
                },
                {
                    "companyName": "Joseph Chiarello & Co.,Inc.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Joseph Chiarello & Co.,Inc.",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Joseph Krar & Assoc, Inc.",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Joseph Krar & Assoc, Inc.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Joseph Krar & Assoc, Inc.",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Joseph Krar & Assoc, Inc.",
                    "contactFirstName": "Michael",
                    "contactLastName": "Kass",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "michael.kass@renaissanceins.com"
                },
                {
                    "companyName": "Joseph Krar & Assoc, Inc.",
                    "contactFirstName": "Michelle",
                    "contactLastName": "Abasciano",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "michelle.abasciano@renaissanceins.com"
                },
                {
                    "companyName": "Joseph Krar & Assoc, Inc.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Joseph Krar & Assoc, Inc.",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "IMU",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "IMU",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "IMU",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "IMU",
                    "contactFirstName": "Michael",
                    "contactLastName": "Kass",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "michael.kass@renaissanceins.com"
                },
                {
                    "companyName": "IMU",
                    "contactFirstName": "Michelle",
                    "contactLastName": "Abasciano",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "michelle.abasciano@renaissanceins.com"
                },
                {
                    "companyName": "IMU",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "IMU",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Insurance Innovators Agcy",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Insurance Innovators Agcy",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Insurance Innovators Agcy",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Insurance Innovators Agcy",
                    "contactFirstName": "Michael",
                    "contactLastName": "Kass",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "michael.kass@renaissanceins.com"
                },
                {
                    "companyName": "Insurance Innovators Agcy",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Insurance Innovators Agcy",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Mt Washington Assurance Corp",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Mt Washington Assurance Corp",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Mt Washington Assurance Corp",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Mt Washington Assurance Corp",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Mt Washington Assurance Corp",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "MPIUA-Mass Property Ins.",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "MPIUA-Mass Property Ins.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "MPIUA-Mass Property Ins.",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "MPIUA-Mass Property Ins.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "MPIUA-Mass Property Ins.",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Marine MGA Inc.",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Marine MGA Inc.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Marine MGA Inc.",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Marine MGA Inc.",
                    "contactFirstName": "Michael",
                    "contactLastName": "Kass",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "michael.kass@renaissanceins.com"
                },
                {
                    "companyName": "Marine MGA Inc.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Marine MGA Inc.",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Meadowbrook Insurance Group",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Meadowbrook Insurance Group",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Meadowbrook Insurance Group",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Meadowbrook Insurance Group",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Meadowbrook Insurance Group",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Meadowbrook of Saginaw",
                    "contactFirstName": "Bradford",
                    "contactLastName": "Beers",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "bradford.beers@renaissanceins.com"
                },
                {
                    "companyName": "Meadowbrook of Saginaw",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Meadowbrook of Saginaw",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Meadowbrook of Saginaw",
                    "contactFirstName": "Michael",
                    "contactLastName": "Kass",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "michael.kass@renaissanceins.com"
                },
                {
                    "companyName": "Meadowbrook of Saginaw",
                    "contactFirstName": "Michelle",
                    "contactLastName": "Abasciano",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "michelle.abasciano@renaissanceins.com"
                },
                {
                    "companyName": "Meadowbrook of Saginaw",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Meadowbrook of Saginaw",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "McGowan & Company, Inc.",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "McGowan & Company, Inc.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "McGowan & Company, Inc.",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "McGowan & Company, Inc.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "McGowan & Company, Inc.",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Marketing Associates Ins Agency",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Marketing Associates Ins Agency",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Marketing Associates Ins Agency",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Marketing Associates Ins Agency",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Marketing Associates Ins Agency",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Maritime General Agency",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Maritime General Agency",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Maritime General Agency",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Maritime General Agency",
                    "contactFirstName": "Michael",
                    "contactLastName": "Kass",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "michael.kass@renaissanceins.com"
                },
                {
                    "companyName": "Maritime General Agency",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Maritime General Agency",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Litchfield Mutual",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Litchfield Mutual",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Litchfield Mutual",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Litchfield Mutual",
                    "contactFirstName": "Michael",
                    "contactLastName": "Kass",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "michael.kass@renaissanceins.com"
                },
                {
                    "companyName": "Litchfield Mutual",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Litchfield Mutual",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Pennsylvania Lumbermens Mutual",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Pennsylvania Lumbermens Mutual",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Pennsylvania Lumbermens Mutual",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Pennsylvania Lumbermens Mutual",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Pennsylvania Lumbermens Mutual",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Patrons Mutual Insurance Company of CT",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Patrons Mutual Insurance Company of CT",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Patrons Mutual Insurance Company of CT",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Patrons Mutual Insurance Company of CT",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Patrons Mutual Insurance Company of CT",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Partners Specialty Group, LLC",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Partners Specialty Group, LLC",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Partners Specialty Group, LLC",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Partners Specialty Group, LLC",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Partners Specialty Group, LLC",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "One Beacon Ins. Companies",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "One Beacon Ins. Companies",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "One Beacon Ins. Companies",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "One Beacon Ins. Companies",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "One Beacon Ins. Companies",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Number One Insurance Agency",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Number One Insurance Agency",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Number One Insurance Agency",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Number One Insurance Agency",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Number One Insurance Agency",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "NIF Services of New England, Inc.",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "NIF Services of New England, Inc.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "NIF Services of New England, Inc.",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "NIF Services of New England, Inc.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "NIF Services of New England, Inc.",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "NH Underwriters Ins.Agency",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "NH Underwriters Ins.Agency",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "NH Underwriters Ins.Agency",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "NH Underwriters Ins.Agency",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "NH Underwriters Ins.Agency",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "National Flood Services",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "National Flood Services",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "National Flood Services",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "National Flood Services",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "National Flood Services",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "NAMU",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "NAMU",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "NAMU",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "NAMU",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "NAMU",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Specialty Insurance",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Specialty Insurance",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Specialty Insurance",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Specialty Insurance",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Specialty Insurance",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Victor O. Schinnerer & Co.",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Victor O. Schinnerer & Co.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Victor O. Schinnerer & Co.",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Victor O. Schinnerer & Co.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Victor O. Schinnerer & Co.",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "RTW State Auto",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "RTW State Auto",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "RTW State Auto",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "RTW State Auto",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "RTW State Auto",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Rhode Island Joint Underwriting Assoc",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Rhode Island Joint Underwriting Assoc",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Rhode Island Joint Underwriting Assoc",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Rhode Island Joint Underwriting Assoc",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Rhode Island Joint Underwriting Assoc",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Professional Risk Solutions",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Professional Risk Solutions",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Professional Risk Solutions",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Professional Risk Solutions",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Professional Risk Solutions",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "ProMutal Group",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "ProMutal Group",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "ProMutal Group",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "ProMutal Group",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "ProMutal Group",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Plymouth Rock Assurance Corp",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Plymouth Rock Assurance Corp",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Plymouth Rock Assurance Corp",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Plymouth Rock Assurance Corp",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Plymouth Rock Assurance Corp",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "PMC Insurance Group",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "PMC Insurance Group",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "PMC Insurance Group",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "PMC Insurance Group",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "PMC Insurance Group",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "WPI Brokers",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "WPI Brokers",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "WPI Brokers",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "WPI Brokers",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "WPI Brokers",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Willis of MA",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Willis of MA",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Willis of MA",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Willis of MA",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Willis of MA",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Torus - Guy Carpenter & Company LLC",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Torus - Guy Carpenter & Company LLC",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Torus - Guy Carpenter & Company LLC",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Torus - Guy Carpenter & Company LLC",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Torus - Guy Carpenter & Company LLC",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Thomco",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Thomco",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Thomco",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Thomco",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Thomco",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Swett & Crawford of ME",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Swett & Crawford of ME",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Swett & Crawford of ME",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Swett & Crawford of ME",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Swett & Crawford of ME",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Swett & Crawford of MA",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Swett & Crawford of MA",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Swett & Crawford of MA",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Swett & Crawford of MA",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Swett & Crawford of MA",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Star Insurance Company",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Star Insurance Company",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Star Insurance Company",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Star Insurance Company",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Star Insurance Company",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Cochrane & Porter Insurance Agency, Inc.",
                    "contactFirstName": "maria",
                    "contactLastName": "Schiller",
                    "contactCompanyName": "Cochrane & Porter Insurance Agency, Inc.",
                    "contactEmail": "maria@renaissanceins.com"
                },
                {
                    "companyName": "Cochrane & Porter Insurance Agency, Inc.",
                    "contactFirstName": "George",
                    "contactLastName": "Vakalopoulos",
                    "contactCompanyName": "Cochrane & Porter Insurance Agency, Inc.",
                    "contactEmail": "deleted.george_candp@renaissanceins.com"
                },
                {
                    "companyName": "ZZ Do Not Use  AA INTERNAL FILE NOTE RFA",
                    "contactFirstName": "maria",
                    "contactLastName": "Schiller",
                    "contactCompanyName": "Cochrane & Porter Insurance Agency, Inc.",
                    "contactEmail": "maria@renaissanceins.com"
                },
                {
                    "companyName": "Bollinger Insurance Services",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Bollinger Insurance Services",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Bollinger Insurance Services",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Bollinger Insurance Services",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Bollinger Insurance Services",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "ZZZPeerless do not use",
                    "contactFirstName": "Someone",
                    "contactLastName": "Else",
                    "contactCompanyName": "NULL",
                    "contactEmail": "some@aol.com"
                },
                {
                    "companyName": "Cove Risk Services, LLC",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Cove Risk Services, LLC",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "Cove Risk Services, LLC",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Cove Risk Services, LLC",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Cove Risk Services, LLC",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Cove Risk Services, LLC",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "EZPayIns-Hanover",
                    "contactFirstName": "Joanna",
                    "contactLastName": "Christopoulos",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "joanna.christopoulos@renaissanceins.com"
                },
                {
                    "companyName": "Test Carrier (Peerless)",
                    "contactFirstName": "maria",
                    "contactLastName": "Schiller",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "maria_rais@renaissanceins.com"
                },
                {
                    "companyName": "Test Carrier (Peerless)",
                    "contactFirstName": "Maria",
                    "contactLastName": "Underwriter",
                    "contactCompanyName": "NULL",
                    "contactEmail": "afuario@hotmail.com"
                },
                {
                    "companyName": "AIG",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "AIG",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "AIG",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "AIG",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "AIG",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Foremost Insurance Group",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Foremost Insurance Group",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Foremost Insurance Group",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Foremost Insurance Group",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "Foremost Insurance Group",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Foremost Insurance Group",
                    "contactFirstName": "RSP",
                    "contactLastName": "Rating TZ",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "renrp2@renaissanceins.com"
                },
                {
                    "companyName": "Foremost Insurance Group",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "ProHost USA, Inc.",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "ProHost USA, Inc.",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "ProHost USA, Inc.",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "ProHost USA, Inc.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "ProHost USA, Inc.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "ProHost USA, Inc.",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Cowles & Connell, Inc.",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Cowles & Connell, Inc.",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "Cowles & Connell, Inc.",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Cowles & Connell, Inc.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Cowles & Connell, Inc.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Cowles & Connell, Inc.",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "AFC Insurance",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "AFC Insurance",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "AFC Insurance",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "AFC Insurance",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "AFC Insurance",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "AFC Insurance",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "AFC Insurance",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "AFC Insurance",
                    "contactFirstName": "Amy",
                    "contactLastName": "Dibari",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "amy.dibari@renaissanceins.com"
                },
                {
                    "companyName": "Pilgram Insurance Co.",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Pilgram Insurance Co.",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Pilgram Insurance Co.",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Pilgram Insurance Co.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Pilgram Insurance Co.",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Pilgram Insurance Co.",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                },
                {
                    "companyName": "Pilgram Insurance Co.",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "Pilgram Insurance Co.",
                    "contactFirstName": "Amy",
                    "contactLastName": "Dibari",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "amy.dibari@renaissanceins.com"
                },
                {
                    "companyName": "Glatfelter Insurance Group",
                    "contactFirstName": "Brenda",
                    "contactLastName": "Hovey",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "brenda.hovey@renaissanceins.com"
                },
                {
                    "companyName": "Glatfelter Insurance Group",
                    "contactFirstName": "Jim",
                    "contactLastName": "Cimino",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "jim.cimino@renaissanceins.com"
                },
                {
                    "companyName": "Glatfelter Insurance Group",
                    "contactFirstName": "Judy",
                    "contactLastName": "Cormack",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.cormack@renaissanceins.com"
                },
                {
                    "companyName": "Glatfelter Insurance Group",
                    "contactFirstName": "Judy",
                    "contactLastName": "Wehrlin",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "judy.wehrlin@renaissanceins.com"
                },
                {
                    "companyName": "Glatfelter Insurance Group",
                    "contactFirstName": "Christine",
                    "contactLastName": "Robidoux",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "christine.robidoux@renaissanceins.com"
                },
                {
                    "companyName": "Glatfelter Insurance Group",
                    "contactFirstName": "Kristen",
                    "contactLastName": "Karam",
                    "contactCompanyName": "Renaissance Alliance",
                    "contactEmail": "kristen.karam@renaissanceins.com"
                }
            ];
            
            
            
            
        }]);