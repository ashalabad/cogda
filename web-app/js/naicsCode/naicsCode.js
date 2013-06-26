
$(function() {
    $("#naicsTree").jstree({
        "json_data": {
            "ajax": {
                "url": "/naicsCode/getActiveNaicsCodes",
                "data": function(n) {
                    // get parent / grandparent node
                    var lists = $(n).parents('ul');
                    var p = $(lists[0]).prev('a');
                    var gp = $(lists[1]).prev('a');
                    // the result is fed to the AJAX request 'data' option
                    return {
                        "parent": $.trim(p.text()),
                        "grandparent": $.trim(gp.text()),
                        "id": n.attr ? n.attr("id").replace("node-", "") : 1
                    };
                }
            }
        },
        "themes" : {
            "theme" : "default",
            "dots" : false,
            "icons" : false
        },
        "search" : {
            "case_insensitive" : true,
            "show_only_matches": true
        },

        "plugins": ["themes", "json_data","checkbox", "search", "adv_search"]
    });
});

$(document).ready(function () {

    $("#naicsModalLauncher").click(function(){
        $("#naicsCodeModal").modal('show');
    });

    $("#resetNaics").click(function () {
        $("#naicsTree").jstree("clear_search").jstree('close_all');
        $("#searchNaics").val("");
    });
    $("#searchNaics").bind('keypress', function () {

        var self = $(this);
        if(self.val().length >= 2)
        {
            clearTimeout(self.data('timeout'));

            self.data('timeout', setTimeout(function() {
                $("#naicsTree").jstree("search",self.val());
            }, 500));
        }

    });



});

function processNaicsChecked(){
    var checked_ids = [];
    $("#naicsTree").jstree("get_checked",null,true).each(function(){
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


