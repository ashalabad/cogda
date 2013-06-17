<%@ page import="com.cogda.common.UsState; com.cogda.domain.onboarding.Registration; com.cogda.domain.admin.CompanyType" %>
<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="kickstart" />
    <g:set var="entityName" value="${message(code: 'registration.label', default: 'User Registration')}" />
    <g:set var="layout_nosecondarymenu"	value="${true}" scope="request"/>
    <g:set var="layout_nomainmenu"		value="${true}" scope="request"/>
    <title><g:message code="default.create.label" args="[entityName]" /></title>
</head>
<body>

<content tag="header">
    <!-- Empty Header -->
</content>

<div id="MenuRow" class="row">
    <div class="span12">
        &nbsp;
    </div>
</div>

<section id="create-registration" class="first">

<div class="alert alert-error" id="errorMessages" style="display:none">
    <h4>Errors!</h4>
</div>

<div class="alert alert-success" id="messages" style="display:none">
</div>

<div id = "registrationFormDiv">
    <g:form class="form-horizontal" name="registrationForm" novalidate="novalidate">

        <fieldset class="form">
            <legend>${entityName}</legend>



            <div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'firstName', 'error')} required">
                <label for="firstName" class="control-label"><g:message code="registration.firstName.label" default="First Name" /><span class="required-indicator">*</span></label>
                <div class="controls">
                    <g:textField name="firstName"  value="${registrationInstance?.firstName}"/>
                    <span class="help-inline">${hasErrors(bean: registrationInstance, field: 'firstName', 'error')}</span>
                </div>
            </div>

            <div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'lastName', 'error')} required">
                <label for="lastName" class="control-label"><g:message code="registration.lastName.label" default="Last Name" /><span class="required-indicator">*</span></label>
                <div class="controls">
                    <g:textField name="lastName"  value="${registrationInstance?.lastName}"/>
                    <span class="help-inline">${hasErrors(bean: registrationInstance, field: 'lastName', 'error')}</span>
                </div>
            </div>

            <div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'username', 'error')} required">
                <label for="username" class="control-label"><g:message code="registration.username.label" default="Username" /><span class="required-indicator">*</span></label>
                <div class="controls">
                    <g:textField id="registrationUserName" name="username" required="" value="${registrationInstance?.username}"/>
                    <span class="help-inline">
                        ${hasErrors(bean: registrationInstance, field: 'username', 'error')}
                    </span>
                </div>
            </div>

            <div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'emailAddress', 'error')} required">
                <label for="emailAddress" class="control-label"><g:message code="registration.emailAddress.label" default="Email Address" /><span class="required-indicator">*</span></label>
                <div class="controls">
                    <g:field type="email" name="emailAddress" required="" value="${registrationInstance?.emailAddress}"/>
                    <span class="help-inline">${hasErrors(bean: registrationInstance, field: 'emailAddress', 'error')}</span>
                </div>
            </div>

            <div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'password', 'error')} required">
                <label for="password" class="control-label"><g:message code="registration.password.label" default="Password" />
                    <span class="required-indicator">*</span>
                </label>
                <div class="controls">
                    <g:passwordField name="password" id="passwordOne" required="" value="${registrationInstance?.password}"/>
                    <span class="help-inline">
                        ${hasErrors(bean: registrationInstance, field: 'password', 'error')}
                    </span>
                </div>
            </div>

            <div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'passwordTwo', 'error')} required">
                <label for="passwordTwo" class="control-label"><g:message code="registration.passwordTwo.label" default="Re-Type Password" />
                    <span class="required-indicator">*</span>
                </label>
                <div class="controls">
                    <g:passwordField name="passwordTwo" required="" value="${registrationInstance?.passwordTwo}"/>
                    <span class="help-inline">
                        ${hasErrors(bean: registrationInstance, field: 'passwordTwo', 'error')}
                    </span>
                </div>
            </div>

            <div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'companyName', 'error')} required">
                <label for="companyName" class="control-label">
                    <g:message code="registration.companyName.label" default="Company Name" />
                    <span class="required-indicator">*</span>
                </label>
                <div class="controls">
                    <g:textField name="companyName" autocomplete="off" required="required" value="${registrationInstance?.companyName}" />
                    <g:hiddenField name="existingCompanyId"></g:hiddenField>
                    <g:hiddenField name="newCompany"></g:hiddenField>
                    <span class="help-inline">${hasErrors(bean: registrationInstance, field: 'companyName', 'error')}</span>
                </div>
            </div>

            <div class="accordion" id="newCompanyAccordion">
                <div class="accordion-group">
                    <div class="accordion-heading">
                        <a id="newCompanyAccordionToggle" class="accordion-toggle" data-parent="#newCompanyAccordion" href="#newCompanyAccordion">
                            <g:message code="registration.companyInformation.label" />
                        </a>
                    </div>
                    <div id="collapseOne" class="accordion-body collapse">
                        <div class="accordion-inner">
                            <div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'companyTypeId', 'error')} ">
                                <label for="companyTypeId" class="control-label">
                                    <g:message code="registration.companyTypeId.label" default="Company Type" />
                                    <span class="required-indicator">*</span>
                                </label>
                                <div class="controls">
                                    <g:select id="companyTypeId" name="companyTypeId" from="${CompanyType.listOrderByIntCode()}"
                                              optionKey="id" optionValue="code" value="${registrationInstance?.companyTypeId?.id}"
                                              class="many-to-one"/>
                                    <span class="help-inline">${hasErrors(bean: registrationInstance, field: 'companyTypeId', 'error')}</span>
                                </div>
                            </div>

                            <div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'phoneNumber', 'error')} ">
                                <label for="phoneNumber" class="control-label"><g:message code="registration.phoneNumber.label" default="Phone Number" />
                                    <span class="required-indicator">*</span>
                                </label>
                                <div class="controls">
                                    <g:textField id="phoneNumber" name="phoneNumber" value="${registrationInstance?.phoneNumber}"/>
                                    <span class="help-inline">${hasErrors(bean: registrationInstance, field: 'phoneNumber', 'error')}</span>
                                </div>
                            </div>

                            <div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'streetAddressOne', 'error')} ">
                                <label for="streetAddressOne" class="control-label"><g:message code="address.streetAddressOne.label" default="Street Address One" />
                                    <span class="required-indicator">*</span>
                                </label>
                                <div class="controls">
                                    <g:textField id="streetAddressOne" name="streetAddressOne" value="${registrationInstance?.streetAddressOne}"/>
                                    <span class="help-inline">${hasErrors(bean: registrationInstance, field: 'streetAddressOne', 'error')}</span>
                                </div>
                            </div>

                            <div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'streetAddressTwo', 'error')} ">
                                <label for="streetAddressTwo" class="control-label"><g:message code="address.streetAddressTwo.label" default="Street Address Two" />

                                </label>
                                <div class="controls">
                                    <g:textField id="streetAddressTwo" name="streetAddressTwo" value="${registrationInstance?.streetAddressTwo}"/>
                                    <span class="help-inline">${hasErrors(bean: registrationInstance, field: 'streetAddressTwo', 'error')}</span>
                                </div>
                            </div>

                            <div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'zipcode', 'error')} ">
                                <label for="zipcode" class="control-label"><g:message code="address.zipcode.label" default="Zipcode" />
                                    <span class="required-indicator">*</span>
                                </label>
                                <div class="controls">
                                    <g:textField id="zipcode" name="zipcode" value="${registrationInstance?.zipcode}"/>
                                    <span class="help-inline">${hasErrors(bean: registrationInstance, field: 'zipcode', 'error')}</span>
                                </div>
                            </div>

                            <div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'city', 'error')} ">
                                <label for="city" class="control-label"><g:message code="address.city.label" default="City" />
                                    <span class="required-indicator">*</span>
                                </label>
                                <div class="controls">
                                    <g:textField id="city" name="city" value="${registrationInstance?.city}"/>
                                    <span class="help-inline">${hasErrors(bean: registrationInstance, field: 'city', 'error')}</span>
                                </div>
                            </div>

                            <div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'state', 'error')} ">
                                <label for="state" class="control-label"><g:message code="address.state.label" default="State" />
                                    <span class="required-indicator">*</span>
                                </label>
                                <div class="controls">
                                    <g:select from="${UsState.values()}" value="${UsState}" optionValue="value" optionKey="key" id="state" name="state"></g:select>

                                    <span class="help-inline">${hasErrors(bean: registrationInstance, field: 'state', 'error')}</span>
                                </div>
                            </div>

                            <div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'county', 'error')} ">
                                <label for="county" class="control-label"><g:message code="address.county.label" default="County" />
                                </label>
                                <div class="controls">
                                    <g:textField id="county" name="county" value="${registrationInstance?.county}"/>
                                    <span class="help-inline">${hasErrors(bean: registrationInstance, field: 'county', 'error')}</span>
                                </div>
                            </div>

                            <div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'country', 'error')} ">
                                <label for="country" class="control-label"><g:message code="address.country.label" default="Country" />
                                    <span class="required-indicator">*</span>
                                </label>
                                <div class="controls">
                                    <g:countrySelect id="country"
                                                     name="country"
                                                     from="${com.cogda.domain.admin.SupportedCountryCode.retrieveSupportedCountryCodes()}"
                                                     value="${registrationInstance?.country}"/>
                                    <span class="help-inline">${hasErrors(bean: registrationInstance, field: 'country', 'error')}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-actions">
                <g:submitButton name="create" class="btn btn-primary" value="${message(code: 'default.button.register.label', default: 'Register')}" />
                <button class="btn cancel" type="reset" id="reset"><g:message code="default.button.reset.label" default="Reset" /></button>
            </div>
        </fieldset>
    </g:form>
</div>
</section>

<g:javascript>
    $(document).ready(function() {

        $('.ajax-typeahead').typeahead({
            source: function(query, process) {
                return $.ajax({
                    url: $(this)[0].$element.data('link'),
                    type: 'get',
                    data: {query: query},
                    dataType: 'json',
                    success: function(json) {
                        return typeof json.options == 'undefined' ? false : process(json.options);
                    }
                });
            }
        });

        // the "Canada exception"
        $("#country").change(function() {
            if ($("select#country option:selected").val() === "can") {
                $("#state").val("").attr("disabled", "disabled");
                $("#city").val("").attr("disabled", "disabled");
            } else if ($("#state").attr("disabled") === "disabled") {
                $("#state").val("").removeAttr("disabled");
                $("#city").val("").removeAttr("disabled");
            }
        });

        // Whether or not the new company section of the form should be required
        function newCompanySectionRequired() {
            return $("#newCompany").val("true");
        }

        $('#newCompanyAccordion').hide();

        var companyNameMap, companyNames;
        $("#companyName").typeahead({

            minLength: 2,
            source: function(query, process) {
                companyNames = [];
                companyNameMap = {};
                $.getJSON(
                        '${g.createLink(controller:'company', action:'typeahead')}',  // /company/typeahead
                        { q: query, limit: 10 },
                        function(data) {
                            $.each(data, function(i, company) {
                                companyNameMap[company.companyName] = company;
                                companyNames.push(company.companyName);
                            });
                            return process(companyNames);  // bootstrap process the companyNames
                        });

            },
            matcher: function (item) {
                if (item.toLowerCase().indexOf(this.query.trim().toLowerCase()) != -1) {
                    return true;
                }
            },
            updater: function(item) {
                $("#existingCompanyId").val(companyNameMap[item].companyId);
                $("#newCompany").val("");
                $('#newCompanyAccordion').hide();


                return item;
            }
        });

        /**
         * The Bootstrap Typeahead spits out change events for the selection or non-selection
         * of one of the Typeahead suggestions. We can use these change events to modify the screen
         * in the event that the user is typing in a new company.
         * @param event
         */
        var typeaheadChangeHandler = function(event){
            var item = event.target.value;
            if(companyNameMap[item] == null){
                $('#newCompanyAccordion').show();
                $('#collapseOne').collapse('show');
                $('#newCompany').val("true");
                $("#existingCompanyId").val("");
                $("#companyTypeId").focus();
            }
        }

        $("#companyName").on('change', typeaheadChangeHandler)

        var validator = $("#registrationForm").validate({
            rules: {
                firstName: {
                    minlength: 1,
                    required: true
                },
                lastName: {
                    minlength: 1,
                    required: true
                },
                username: {
                    minlength: 2,
                    required: true,
                    remote: {
                        url:'<g:createLink controller="register" action="availableUsername" />',
                        type: "POST",
                        data: {
                            username: function() {
                                return $("#registrationUserName").val();
                            }
                        }
                    }
                },
                password: {
                    minlength: 6,
                    maxlength: 84,
                    required: true
                },
                passwordTwo: {
                    equalTo: "#passwordOne"
                },
                companyTypeId: {
                    required: newCompanySectionRequired
                },
                phoneNumber: {
                    required: newCompanySectionRequired,
                    phoneUS: true
                },
                streetAddressOne: {
                    required: newCompanySectionRequired
                },
                streetAddressTwo: {

                },
                zipcode: {
                    required: newCompanySectionRequired,
                    minlength: 5
                },
                city: {
                    required: newCompanySectionRequired
                },
                state: {
                    required: newCompanySectionRequired
                },
                county: {
                    
                },
                country: {
                    required: newCompanySectionRequired
                }
            },
            highlight: function(element) {
                $(element).closest('.control-group').removeClass('success').addClass('error');
            },
            success: function(element) {
                element.text('OK!').addClass('valid')
                        .closest('.control-group').removeClass('error').addClass('success');
            }
        });

        // Reset handler
        $("#reset").click(function() {
            validator.resetForm();
            $("#newCompanyControlGroup div.off").click();
            $("#registrationForm").find("div.control-group").removeClass("success error")
            $("#firstName").focus();
        });

        $('#registrationForm').on("submit", function(e){
            e.preventDefault();
            if(validator.valid()){
                $.ajax(
                    {
                        type:'POST',
                        data:$(this).serialize(),
                        url:'${g.createLink(controller:'register', action:'save')}',
                        success:function(data, textStatus){
                            registrationHandler(data, textStatus);
                        },
                        error:function(XMLHttpRequest,textStatus,errorThrown){
                            $.pnotify({
                                title: 'Error Saving',
                                text: textStatus + " " + errorThrown,
                                type: 'error',
                                opacity: 0.8,
                                delay: 10000
                            });
                        }
                    }
                );
            }
        });

    });

    function registrationHandler(data, textStatus) {
        if (data.success) {
            var errorMessages =  $("#errorMessages");
            errorMessages.html("");
            errorMessages.hide();

            for(var i = 0; i < data.messages.length; i++) {
                if(data.htmlTemplate){
                    $('#registrationFormDiv').html(data.htmlTemplate);
                    $('#successMessagesDiv').focus();
                }

                $.pnotify({
                    title: 'Save Successful',
                    text: data.messages[i],
                    type: 'success',
                    opacity: 0.8,
                    delay: 10000
                });
            }


        } else {
            var errorMessages =  $("#errorMessages");
            errorMessages.html("<h4>Errors!</h4>");
            errorMessages.append('<ul id="errorsList">');
            for (var i in data.errors) {
                errorMessages.append('<li>');
                errorMessages.append(data.errors[i]);
                errorMessages.append('</li>');
            }
            errorMessages.append('</ul>');
            errorMessages.show();
            errorMessages.focus();
        }
    }



    // After the successful submit of the registration data:
    // Email Confirmation: Your account has been created! A confirmation email will be sent to your email address. Please click on the link in the email to verify your account. After you confirm your email address, an email will be sent to the company admin to approve the account.
</g:javascript>



</body>

</html>
