angular.module('resources.sicCodeTree', ['resources.logger'])
    .controller('SicCodeCtrl', ['$scope', 'Logger', function ($scope, Logger) {
        $scope.collapseTree = function () {

        };
    }])
    .directive('sicCodeTree', [ '$timeout', '$http', 'Logger', function ($timeout, $http, Logger) {
        return {

            restrict: 'A',
            transclude: true,
            scope: {
                selectedNodes: '=',
                undeterminedNodes: '=',
                includeRelatedNaicsCodes: '=',
                relatedNaicsCodes: '=',
                disableCheckBoxes: '@',
                businessType: '='
            },
            templateUrl: '/js/angular/resources/SicCodeTree.html',

            link: function (scope, element, attrs) {
                var timeoutPromise;
                var loadedState = false;
                var selectedNodesCopy;
                var undeterminedNodesCopy;

                scope.$watch('filterText', function (filterText) {
                    if (filterText !== undefined && (filterText.length >= 2 || filterText.length == 0)) {
                        if (timeoutPromise !== undefined) {
                            $timeout.cancel(timeoutPromise);
                        }
                        timeoutPromise = $timeout(function () {
                            $(element).find('#tree').jstree("search", filterText);
                        }, 500);
                    }
                });

                scope.$watch('includeRelatedNaicsCodes', function (includeRelatedNaicsCodes) {
                    scope.includeRelatedNaicsCodes = includeRelatedNaicsCodes;
                });

                scope.$watch('selectedNodes', function (newValue, oldValue) {
                    if (newValue != oldValue && newValue !== undefined && scope.disableCheckBoxes === "true") {
                        loadedState = false;
                        setState();
                    }
                });

                scope.$watch('undeterminedNodes', function (newValue, oldValue) {
                    if (newValue != oldValue && newValue !== undefined && scope.disableCheckBoxes === "true") {
                        loadedState = false;
                        setState();
                    }
                });

                scope.$watch('businessType', function(newValue, oldValue) {
                    if (newValue != oldValue) {
                        $(element).find('#tree').jstree('refresh');
                    }
                });

                var getSelected = function () {
                    scope.selectedNodes = [];
                    scope.undeterminedNodes = [];
                    var $tree = $(element).find('#tree');
                    $tree.find('.jstree-checked').each(
                        function () {
                            scope.selectedNodes.push({id: Number(this.id)});
                        });
                    $tree.find('.jstree-undetermined').each(
                        function () {
                            scope.undeterminedNodes.push({id: Number(this.id)});
                        }
                    );
                    if (scope.includeRelatedNaicsCodes) {
                        $http.post("/sicCode/selectedSicCodes", JSON.stringify(scope.selectedNodes)).
                            success(function (data) {
                                scope.relatedNaicsCodes = data;
                            });
                    }
                };

                var unselectAll = function () {
                    $(element).find('#tree').jstree("uncheck_all");
                }

                var setSelected = function (selectedNodes) {
                    if (selectedNodes !== undefined) {
                        var spliceThese = [];
                        $.each(selectedNodes, function () {
                            if ($(element).find('#' + this.id).length > 0) {
                                spliceThese.push(this);
                                $(element).find('#tree').jstree('check_node', '#' + this.id);
                            }
                        });
                        $.each(spliceThese, function () {
                            var index = selectedNodes.indexOf(this);
                            selectedNodes.splice(index, 1);
                        });
                    }
                };

                var setUndetermined = function (undeterminedNodes) {
                    if (undeterminedNodes !== undefined) {
                        var spliceThese = [];
                        $.each(undeterminedNodes, function () {
                            var $target = $(element).find('#' + this.id);
                            if ($target.length > 0) {
                                spliceThese.push(this);
                                $target.removeClass('jstree-checked jstree-unchecked').addClass('jstree-undetermined');
                            }
                        });
                        $.each(spliceThese, function () {
                            var index = undeterminedNodes.indexOf(this);
                            undeterminedNodes.splice(index, 1);
                        })
                    }
                };

                var setState = function () {
                    if (!loadedState) {
                        loadedState = true;
                        selectedNodesCopy = angular.copy(scope.selectedNodes);
                        undeterminedNodesCopy = angular.copy(scope.undeterminedNodes);
                        if (scope.disableCheckBoxes === "true") {
                            unselectAll();
                        }
                    }
                    setSelected(selectedNodesCopy);
                    setUndetermined(undeterminedNodesCopy);
                };

                var setStateAndDisable = function () {
                    scope.$apply();
                    $(element).find('li').removeAttr('rel');
                    setState();
                    $(element).find('li').attr('rel', 'disabled');
                };

                var jsonData = {
                    "ajax": {
                        "url": function (node) {
                            if (scope.businessType === undefined) {
                                return node == -1 ?
                                    "/sicCode/activeSicCodes" :
                                    "/sicCode/activeSicCodes?parentId=" + node.attr('id');
                            } else {
                                var baseUrl = "/sicCode/activeSicCodesByBusinessType?businessTypeId=" + scope.businessType.id;
                                return node == -1 ? baseUrl : "/sicCode/activeSicCodes?parentId=" + node.attr('id');
                            }
                        },
                        "type": "get",
                        "success": function (codes) {
                            var nodes = [];
                            for (var i = 0; i < codes.length; i++) {
                                var code = codes[i];
                                if (code.hasChildSicCodes) {
                                    code.state = "closed";
                                }
                                nodes.push(code);
                            }
                            return nodes;
                        }
                    }
                };

                var themes = {
                    "theme": "default",
                    "dots": false,
                    "icons": false
                };

                var search = {
                    "case_insensitive": true,
                    "show_only_matches": true,
                    "ajax": {
                        "url": scope.businessType === undefined ? '/sicCode/search' : '/sicCode/search?businessTypeId=' + scope.businessType.id
                    }
                };

                var createUncheckable = function () {
                    $(element).find('[type="checkbox"]')[0].disabled = true;
                    $(element).find('#tree').jstree({
                        "json_data": jsonData,
                        "themes": themes,
                        "search": search,
                        "types": {
                            "types": {
                                "disabled": {
                                    "check_node": false,
                                    "uncheck_node": false
                                }
                            }
                        },
                        "plugins": ["themes", "json_data", "checkbox", "search", "adv_search", "types"]
                    }, false).delegate("a", "click",function (event, data) {
                            event.preventDefault();
                        }).bind("loaded.jstree",function (event, data) {
                            setStateAndDisable();
                        }).bind("load_node.jstree", function (event, data) {
                            setStateAndDisable();
                        });
                };

                var createCheckable = function () {
                    $(element).find('#tree').jstree({
                        "json_data": jsonData,
                        "themes": themes,
                        "search": search,
                        "plugins": ["themes", "json_data", "checkbox", "search", "adv_search"]
                    }, false).delegate("a", "click",function (event, data) {
                            event.preventDefault();
                        }).bind("loaded.jstree",function (event, data) {
                            setState();
                        }).bind("load_node.jstree",function (event, data) {
                            setState();
                        }).bind("change_state.jstree", function (event, data) {
                            scope.$apply(function () {
                                getSelected()
                            });
                        });
                };

                var displayTree = function () {
                if (scope.disableCheckBoxes !== undefined) {
                    createUncheckable();
                } else {
                    createCheckable();
                }
                }

                displayTree();
            }
        };
    }]);