$(document).ready(function () {
    $('#suspectForm').off('submit');

    $('#suspectForm').on("submit", function (e) {
        e.preventDefault();
        if (validator.valid()) {
            $.ajax(
                {
                    type: 'POST',
                    data: $(this).serialize(),
                    url: '/suspect/update',
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

    if (xhr.status == 200) {
        var errorMessages = $("#errorMessages");
        errorMessages.html("");
        errorMessages.hide();
        $.pnotify({
            title: 'Update Successful',
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