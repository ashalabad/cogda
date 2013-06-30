var isNew;
$(document).ready(function () {
    $('#suspectForm').off('submit');
    isNew = ($('form').hasClass('new'));

    $('#suspectForm').on("submit", function (e) {
        e.preventDefault();
        var route = isNew ? 'save' : 'update';
        if (validator.valid()) {
            $.ajax(
                {
                    type: 'POST',
                    data: $(this).serialize(),
                    url: '/suspect/' + route,
                    success: function (data, textStatus, xhr) {
                        suspectHandler(data, textStatus, xhr);
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        var messages = JSON.parse(XMLHttpRequest.responseText)
                        var errorMessages = $("#errorMessages");
                        errorMessages.html("<h4>Errors!</h4>");
                        errorMessages.append('<ul id="errorsList">');
                        for (var i in messages.errors) {
                            errorMessages.append('<li>');
                            errorMessages.append(messages.errors[i]);
                            errorMessages.append('</li>');
                            $.pnotify({
                                title: 'Error Saving',
                                text: messages.errors[i],
                                type: 'error',
                                opacity: 0.8,
                                delay: 10000
                            });
                        }
                        errorMessages.append('</ul>');
                        errorMessages.show();
                        errorMessages.focus();

                    }
                }
            );
        }
    });

    //bind click event on delete buttons using jquery live
    $('body').on('click', '.del-phone', function (event) {
        //find the parent div
        event.preventDefault();
        var prnt = $(this).parents(".phone-div");
        //find the deleted hidden input
        var delInput = prnt.find("input[id$=deleted]");
        //check if this is still not persisted
        var newValue = prnt.find("input[id$=new]").attr('value');
        //if it is new then i can safely remove from dom
        if (newValue == 'true') {
            prnt.remove();
        } else {
            //set the deletedFlag to true
            delInput.attr('value', 'true');
            //hide the div
            prnt.hide();
        }
    });

    var validator = $("#suspectForm").validate({
        rules: {
            firstName: {
                minlength: 1,
                required: true
            },
            lastName: {
                minlength: 1,
                required: true
            },
            emailAddress: {
                required: true,
                email: true
            },
            country: {
                required:true
            }
        },
        highlight: function (element) {
            $(element).closest('.control-group').removeClass('success').addClass('error');
        },
        success: function (element) {
            element.text('OK!').addClass('valid')
                .closest('.control-group').removeClass('error').addClass('success');
        }
    });
});

function suspectHandler(data, textStatus, xhr) {
    var message = isNew ? "Save " : "Update "
    if (xhr.status == 200) {
        var errorMessages = $("#errorMessages");
        errorMessages.html("");
        errorMessages.hide();
        $.pnotify({
            title: message + 'Successful',
            type: 'success',
            opacity: 0.8,
            delay: 10000
        });
    } else {
        var errorMessages = $("#errorMessages");
        errorMessages.html("<h4>Errors!</h4>");
        errorMessages.append('<ul id="errorsList">');
        errorMessages.append('<li>')
        errorMessages.append(data.message)
        errorMessages.append('</li>')
        errorMessages.append('</ul>');
        errorMessages.show();
        errorMessages.focus();
    }
}

var childCount;
function setChildCount(count) {
    childCount = isNaN(count) ? 0 : count;
}

function addPhone() {
    var clone = $("#leadContactPhoneNumber_clone").clone()
    var htmlId = 'leadContactPhoneNumbers[' + childCount + '].';
    var phoneInput = clone.find("input[id$=number]");

    clone.find("input[id$=id]")
        .attr('id', htmlId + 'id')
        .attr('name', htmlId + 'id');
    clone.find("input[id$=deleted]")
        .attr('id', htmlId + 'deleted')
        .attr('name', htmlId + 'deleted');
    clone.find("input[id$=new]")
        .attr('id', htmlId + 'new')
        .attr('name', htmlId + 'new')
        .attr('value', 'true');
    phoneInput.attr('id', htmlId + 'number')
        .attr('name', htmlId + 'number');
    clone.find("select[id$=type]")
        .attr('id', htmlId + 'type')
        .attr('name', htmlId + 'type');

    clone.attr('id', 'phone' + childCount);
    $("#childList").append(clone);
    clone.show();
    phoneInput.focus();
    childCount++;
}

