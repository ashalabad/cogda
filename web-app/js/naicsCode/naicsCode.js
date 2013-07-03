$(function () {
    $("#naicsTree").jstree({
        "json_data": {
            "ajax": {
                "url": function (node) {
                    return node == -1 ? "/naicsCode/getActiveNaicsCodes" : "/naicsCode/getActiveNaicsCodes?parentId=" + node.attr('id');
                },
                "type": "get",
                "success": function (codes) {
                    var nodes = [];
                    for (var i = 0; i < codes.length; i++) {
                        var code = codes[i];
                        code.state = "closed";
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
            "case_insensitive" : true,
            "show_only_matches": true,
            "ajax": {
                "url": '/naicsCode/search'
            }
        },

        "plugins": ["themes", "json_data", "checkbox", "search", "adv_search"]
    });
});

$(document).ready(function () {

    $("#naicsModalLauncher").click(function () {
        $("#naicsCodeModal").modal('show');
    });

    $("#resetNaics").click(function () {
        $("#naicsTree").jstree("clear_search").jstree('close_all');
        $("#searchNaics").val("");
    });
    $("#searchNaics").bind('keypress', function () {

        var self = $(this);
        if (self.val().length >= 2) {
            clearTimeout(self.data('timeout'));

            self.data('timeout', setTimeout(function () {
                $("#naicsTree").jstree("search", self.val());
            }, 500));
        }

    });


});

function processNaicsChecked() {
    var checked_ids = [];
    $("#naicsTree").jstree("get_checked", null, true).each(function () {
        checked_ids.push(this.id);
    });
    var naicsCodes = new Object();
    naicsCodes.checked = checked_ids;
    $.ajax({
        url: "/naicsCode/selectedNaicsCodes",
        type: "post",
        dataType: "json",
        data: JSON.stringify(naicsCodes),
        contentType: "application/json; charset=utf-8"
    });

}


