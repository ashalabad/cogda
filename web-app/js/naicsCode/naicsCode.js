//data = [
//    {
//        "data": "Basics",
//        "state": "closed",
//        "children": [{
//            "data": "login",
//            "state": "closed",
//            "children": ["login",
//                {
//                    "data": "results",
//                    "state": "closed",
//                    "attr": {
//                        "id": "node-123"
//                    }}]},
//
//
//            {
//                "data": "Basics",
//                "state": "closed",
//                "children": ["login", "something",
//                    {
//                        "data": "results",
//                        "state": "closed",
//                        "attr": {
//                            "id": "node-456"
//                        }}]}]},
//    {
//        "data": "All",
//        "state": "closed",
//        "children": [{
//            "data": "AddCustomer",
//            "state": "closed",
//            "children": ["login", "Add",
//                {
//                    "data": "results",
//                    "state": "closed",
//                    "attr": {
//                        "id": "node-789"
//                    }}]}]}
//]


$(function() {
    $("#jstree").jstree({
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

    $("#reset").click(function () {
        $("#jstree").jstree("clear_search").jstree('close_all');
        $("#searchtext").val("");
    });
    $("#searchtext").bind('keypress', function () {

        var self = $(this);
        if(self.val().length >= 2)
        {
            clearTimeout(self.data('timeout'));

            self.data('timeout', setTimeout(function() {
                $("#jstree").jstree("search",self.val());
            }, 500));
        }

    });



});

function processChecked(){
    var checked_ids = [];
    $("#jstree").jstree("get_checked",null,true).each(function(){
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


