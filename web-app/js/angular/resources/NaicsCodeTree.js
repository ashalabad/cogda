angular.module('resources.naicsCodeTree', ['resources.logger'])
    .controller('NaicsCodeCtrl', ['$scope', 'Logger', function ($scope, Logger) {
        $scope.collapseTree = function () {

        };
    }])
    .directive('naicsCodeTree', [ '$timeout', '$http', 'Logger', function ($timeout, $http, Logger) {
        return {

            restrict: 'A',
            transclude: true,
            scope: {
                selectedNodes: '=',
                undeterminedNodes: '=',
                includeRelatedSicCodes: '=',
                relatedSicCodes: '=',
                disableCheckBoxes: '@'
            },
            templateUrl: '/js/angular/resources/NaicsCodeTree.html',

            link: function (scope, element, attrs) {
                var timeoutPromise;
                var loadedState = false;
                var selectedNodesCopy;
                var undeterminedNodesCopy;

                scope.$watch('filterText', function (filterText) {
                    if (filterText !== undefined && (filterText.length >= 2 || filterText.length == 0))
                        if (timeoutPromise !== undefined) {
                            $timeout.cancel(timeoutPromise);
                        }
                    timeoutPromise = $timeout(function () {
                        $(element).find('#tree').jstree("search", filterText);
                    }, 500);
                });

                scope.$watch('includeRelatedSicCodes', function (includeRelatedSicCodes) {
                    scope.includeRelatedSicCodes = includeRelatedSicCodes;
                });

                var getSelected = function () {
                    scope.selectedNodes = [];
                    scope.undeterminedNodes = [];
                    var $tree = $('#tree');
                    $tree.find('.jstree-checked').each(
                        function () {
                            scope.selectedNodes.push({id: Number(this.id)})
                        });
                    $tree.find('.jstree-undetermined').each(
                        function () {
                            scope.undeterminedNodes.push({id: Number(this.id)});
                        }
                    );
                    if (scope.includeRelatedSicCodes) {
                        $http.post("/naicsCode/selectedNaicsCodes", JSON.stringify(scope.selectedNodes)).
                            success(function (data) {
                                scope.relatedSicCodes = data;
                            });
                    }
                };

                var setSelected = function (selectedNodes) {
                    if (selectedNodes !== undefined) {
                        var spliceThese = [];
                        $.each(selectedNodes, function () {
                            if ($('#' + this.id).length > 0) {
                                spliceThese.push(this);
                                $('#tree').jstree('check_node', '#' + this.id);
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
                            var $target = $('#' + this.id);
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
                    }
                    setSelected(selectedNodesCopy);
                    setUndetermined(undeterminedNodesCopy);
                };

                scope.$watch('tree', function () {
                    if (scope.disableCheckBoxes) {
                        createUncheckable();
                    } else {
                        createCheckable();
                    }
                });

                var createUncheckable = function () {
                    $(element).find('#tree').jstree({
                        "json_data": {
                            "ajax": {
                                "url": function (node) {
                                    return node == -1 ? "/naicsCode/activeNaicsCodes" : "/naicsCode/activeNaicsCodes?parentId=" + node.attr('id');
                                },
                                "type": "get",
                                "success": function (codes) {
                                    var nodes = [];
                                    for (var i = 0; i < codes.length; i++) {
                                        var code = codes[i];
                                        if (code.hasChildNaicsCodes) {
                                            code.state = "closed";
                                        }
                                        nodes.push(code);
                                    }
                                    return nodes;
                                }
                            }
                        },
                        "themes": {
                            "theme": "default",
                            "dots": false,
                            "icons": false
                        },
                        "search": {
                            "case_insensitive": true,
                            "show_only_matches": true,
                            "ajax": {
                                "url": '/naicsCode/search'
                            }
                        },

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
                            setState();
                            $('#tree').find('li').attr('rel', 'disabled');
                        }).bind("load_node.jstree", function (event, data) {
                            setState();
                            $('#tree').find('li').attr('rel', 'disabled');
                        });
                };

                var createCheckable = function () {
                    $(element).find('#tree').jstree({
                        "json_data": {
                            "ajax": {
                                "url": function (node) {
                                    return node == -1 ? "/naicsCode/activeNaicsCodes" : "/naicsCode/activeNaicsCodes?parentId=" + node.attr('id');
                                },
                                "type": "get",
                                "success": function (codes) {
                                    var nodes = [];
                                    for (var i = 0; i < codes.length; i++) {
                                        var code = codes[i];
                                        if (code.hasChildNaicsCodes) {
                                            code.state = "closed";
                                        }
                                        nodes.push(code);
                                    }
                                    return nodes;
                                }
                            }
                        },
                        "themes": {
                            "theme": "default",
                            "dots": false,
                            "icons": false
                        },
                        "search": {
                            "case_insensitive": true,
                            "show_only_matches": true,
                            "ajax": {
                                "url": '/naicsCode/search'
                            }
                        },

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
                }
            }
        };
    }]);