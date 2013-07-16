angular.module('resources.leadService', ['$rootScope']);
angular.module('resources.leadService', []).factory('LeadService', function ($rootScope) {
    var leadService = {};
    leadService.entityToBroadCast = {};
    leadService.entityPath = '';
    leadService.entityIdx = -1;
    leadService.parentIdx = -1;

    leadService.save = function (handler) {
        $rootScope.$broadcast(handler);
    };

    leadService.addEntityBroadcast = function (entity, handler, parentIdx) {
        this.parentIdx = parentIdx;
        this.entityToBroadCast = entity;
        $rootScope.$broadcast(handler);
    };

    leadService.deleteEntity = function (handler, idx, parentIdx) {
        this.entityIdx = idx;
        this.parentIdx = parentIdx;
        $rootScope.$broadcast(handler);
    };

    return leadService;
});