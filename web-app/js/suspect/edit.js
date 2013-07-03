var isNew;
$(document).ready(function () {
    $('#suspectForm').off('submit');
    isNew = ($('form').hasClass('new'));

    var validator = $("#suspectForm").validate({
        rules: {
            clientName: {
                minlength: 1,
                maxlength: 50,
                required: true
            },
            firstName: {
                minlength: 1,
                maxlength: 50,
                required: true
            },
            lastName: {
                minlength: 1,
                maxlength: 50,
                required: true
            },
            emailAddress: {
                required: true,
                email: true
            },
            country: {
                required: true
            },
            clientId: {
                minlength: 1,
                maxlength: 50,
                required: true
            },
            phoneNumber: {
                minlength: 1,
                required: true
            },
            businessTypeId: {
                required: true
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

    $('#suspectForm').on("submit", function (e) {
        e.preventDefault();
        var route = isNew ? 'save' : 'update/' + $("#suspectForm input[name='id']").val();
        if (validator.valid()) {
            var form = form2js('suspectForm', '.', true);
            var subType = getChecked('subType');
            var noteType = getChecked('leadNoteInstance[0].noteType');
            var noteInstance = new Object();
            if (noteType != null) {
                noteInstance.noteType = noteType;
                form.leadNoteInstances = [];
                form.leadNoteInstances.push(noteInstance);
            }
            if (subType != null) {
                form.subType = subType;
            }
            if (naicsCodes != null && naicsCodes.length) {
                form.naicsCodes = naicsCodes;
            }
            if (sicCodes != null && sicCodes.length) {
                form.sicCodes = sicCodes;
            }
            $.ajax(
                {
                    type: 'POST',
                    data: JSON.stringify(form),
                    contentType: 'application/json; charset=utf-8',
                    url: '/suspect/' + route,
                    success: function (data, textStatus, xhr) {
                        suspectHandler(data, textStatus, xhr);
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        var messages;
                        switch (XMLHttpRequest.status) {
                            case 406:
                                messages = {errors: {error: "JSON not sent to server."}};
                                break;
                            case 500:
                                messages = {errors: {error: 'Server error. Cannot parse JSON response.'} };
                                break;
                            default:
                                try {
                                    messages = JSON.parse(XMLHttpRequest.responseText)
                                } catch (e) {
                                    messages = {errors: {error: 'Unknown error occurred'}}
                                }
                        }
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
});

function suspectHandler(data, textStatus, xhr) {
    var message = isNew ? "Save " : "Update ";
    if (xhr.status == 201 || xhr.status == 200) {
        var errorMessages = $("#errorMessages");
        errorMessages.html("");
        errorMessages.hide();
        $.pnotify({
            title: message + 'Successful',
            type: 'success',
            opacity: 0.8,
            delay: 10000
        });
        if (isNew) {
            window.location.replace("../suspect");
        }
    } else {
        var errorMessages = $("#errorMessages");
        errorMessages.html("<h4>Errors!</h4>");
        errorMessages.append('<ul id="errorsList">');
        errorMessages.append('<li>');
        errorMessages.append(data.message);
        errorMessages.append('</li>');
        errorMessages.append('</ul>');
        errorMessages.show();
        errorMessages.focus();
    }
}

var childCount;
var prefix;

function init(p, count) {
    childCount = isNaN(count) ? 0 : count;
    prefix = p;
}

function addPhone() {
    var clone = $("#leadContactPhoneNumber_clone").clone();
    var htmlId = prefix + 'leadContactPhoneNumbers[' + childCount + ']';
    var phoneDescriptionInput = clone.find("input[id$=description]");
    var phoneInput = clone.find("input[id$=phoneNumber]");
    var phonePrimaryInput = clone.find("input[id$=primaryPhoneNumber]");
    var phonePrimaryHiddenInput = clone.find("input[type=hidden]");

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
    updateAttributes(phoneInput, htmlId + '.phoneNumber');
    updateAttributes(phoneDescriptionInput, htmlId + '.description');
    updateAttributes(phonePrimaryInput, htmlId + '.primaryPhoneNumber');
    phonePrimaryHiddenInput.attr('name', htmlId + '.primaryPhoneNumber');
    clone.find("select[id$=type]")
        .attr('id', htmlId + 'type')
        .attr('name', htmlId + 'type');

    clone.attr('id', 'phone' + childCount);
    $("#childLeadContactPhoneNumbers").append(clone);
    clone.show();
    phoneInput.focus();
    childCount++;
}

function updateAttributes(attribute, newValue) {
    attribute.attr('id', newValue)
        .attr('name', newValue);
}
var sicCodes;
function leadSicChecked(){
    sicCodes = leadCodeChecked($("#sicTree"));
}

var naicsCodes;

function leadNaicsChecked(){
    naicsCodes = leadCodeChecked($("#naicsTree"));
}

function leadCodeChecked($tree) {
    var container = [];
    $tree.jstree("get_checked",null,true).each(function(index, value){
        var code = new Object()
        code.id = value.id
        container.push(code);
    });
    return container;
}

function getChecked(name) {
    var $checked = $("input[name='" + name + "']:checked");
    if ($checked.length)
        return $checked.val();
    else
        return null;
}