$(function () {
    $("#sicTree").jstree({
        "json_data": {
            "ajax": {
                "url": function (node) {
                    return node == -1 ? "/sicCode/getActiveSicCodes" : "/sicCode/getActiveSicCodes?parentId=" + node.attr('id');
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
                "url": '/sicCode/search'
            }
        },

        "plugins": ["themes", "json_data", "checkbox", "search", "adv_search"]
    });
});

$(document).ready(function () {

    $("#sicModalLauncher").click(function () {
        $("#sicCodeModal").modal('show');
    });

    $("#resetSic").click(function () {
        $("#sicTree").jstree("clear_search").jstree('close_all');
        $("#searchSic").val("");
    });
    $("#searchSic").bind('keypress', function () {

        var self = $(this);
        if (self.val().length >= 2) {
            clearTimeout(self.data('timeout'));

            self.data('timeout', setTimeout(function () {
                $("#sicTree").jstree("search", self.val());
            }, 500));
        }

    });


});

function processSicChecked() {
    var checked_ids = [];
    $("#sicTree").jstree("get_checked", null, true).each(function () {
        checked_ids.push(this.id);
    });
    var sicCodes = new Object();
    sicCodes.checked = checked_ids;
    $.ajax({
        url: "/sicCode/selectedSicCodes",
        type: "post",
        dataType: "json",
        data: JSON.stringify(sicCodes),
        contentType: "application/json; charset=utf-8"
    });

}


