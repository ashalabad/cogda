$(document).ready(function () {

    $('.popoverLink').popover();

    $('.popoverLink').click(function (e) {
        e.preventDefault();
    });

    $('#fileImportTab a[href="#stepThree"]').addClass('disabled');
    $('#fileImportTab a[href="#stepThree"]').click( function(e) { e.preventDefault(); } );
    $('#stepThreeItem').click( function(e) { e.preventDefault(); } );
    // Change this to the location of your server-side upload handler:
    var url = '/userImport/importFromUserFile',
        uploadButton = $('<button/>')
            .addClass('btn')
            .prop('disabled', true)
            .text('Processing...')
            .on('click', function () {
                var $this = $(this),
                    data = $this.data();
                $this.off('click')
                    .text('Abort')
                    .on('click', function () {
                        $this.remove();
                        data.abort();
                    });
                data.submit().always(function () {
                    $this.remove();
                });
            });
    $('#fileupload').fileupload({
        url: url,
        dataType: 'json',
        singleFileUploads: "true",
        autoUpload: true,
        acceptFileTypes: /(\.|\/)(csv|txt)$/i,
        maxFileSize: 5000000, // 5 MB
        minFileSize: 1,
        processData: false,
        start: function (e) {
            $('#progress').removeAttr("style");
            $('#progress').show();
            $('#fileInputButtonSpan').hide();
            $('#progress').addClass('active');
            $('#files').html('<strong>Processing file data...please wait.</strong>');
        },
        done: function(e, data){
            createLogTable(data.result);
            $('#progress').removeClass('active');
            $('#files').append('<br><strong>File upload complete!</strong>');
            $('#fileImportTab a[href="#stepThree"]').tab('show');
            $('#stepThreeItem').removeClass('disabled');
            $('#fileImportTab a[href="#stepTwo"]').addClass('disabled');
            $('#fileImportTab a[href="#stepTwo"]').click(function(e) {
                e.preventDefault();
            });
        }
    }).on('fileuploadadd',function (e, data) {
            $('#files').html("");
            data.context = $('<div/>').appendTo('#files');
            $.each(data.files, function (index, file) {
                var node = $('<p/>').append($('<span/>').text(file.name));
                node.appendTo(data.context);
            });
        }).on('fileuploadprocessalways',function (e, data) {
            var index = data.index,
                file = data.files[index],
                node = $(data.context.children()[index]);
            if (file.error) {
                node.append('<br>').append(file.error);
            }
        }).on('fileuploadprogressall',function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress').show();
            $('#progress .bar').css(
                'width',
                progress + '%'
            );
        });

    function createLogTable(result) {
        // create table
        var $table = $('<table>');
        $table.addClass('table').addClass('table-bordered').addClass('table-condensed');

        // thead
        $table.append('<thead>').children('thead')
            .append('<tr/>').append('<th>Status</th><th>Name</th><th>Email Address</th><th>Security Roles</th><th>Error Messages</th>');

        // tbody
        var $tbody = $table.append('<tbody />').children('tbody');

        var hasErrors = false;
        $.each(result, function (i, item) {
            var successMessage = "Yes";
            var rowClass = "info";
            var statusIcon = "<span class='badge badge-info'><i class = 'icon-ok'></i></span>";
            if (!result[i].success) {

                successMessage = "No";
                rowClass = "error"
                statusIcon = "<span class='badge badge-important'><i class = 'icon-remove'></i></span>";

                if (!hasErrors) {
                    hasErrors = true;
                }
            }
            var $row = $tbody.append('<tr />').children('tr:last').addClass(rowClass)
                .append("<td>" + statusIcon + "</td>")
                .append("<td>" + result[i].lastName + ", " + result[i].firstName + "</td>")
                .append("<td>" + result[i].emailAddress + "</td>")
                .append("<td>" + result[i].securityRoles + "</td>");

            if (!result[i].success) {
                var $tdlast = $row.append("<td />").children('td:last');
                var $ul = $tdlast.append("<ul />").children('ul').addClass('unstyled');
                $.each(result[i].errors, function () {
                    $.each(this, function (key, value) {
                        $ul.append("<li><strong>" + key + "</strong>: " + value + "</li>");
                    });
                });
                $ul.append("</ul>");
            } else {
                $row.append('<td> </td>');
            }
        });

        $('#stepThreeContent').html($table);
    }
});