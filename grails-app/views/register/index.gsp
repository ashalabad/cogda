<%@ page import="com.cogda.domain.onboarding.Registration" %>
<%@ page import="com.cogda.domain.admin.CompanyType" %>

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

    </div>
</div>

<section id="create-registration" class="first">

	<g:hasErrors bean="${registrationInstance}">
	<div class="alert alert-error">
		<g:renderErrors bean="${registrationInstance}" as="list" />
	</div>
	</g:hasErrors>

    <g:formRemote class = "form-horizontal" name="registrationForm" url="[controller:'register', action:'save']"
                  method="POST" onSuccess="registrationHandler(data, textStatus)"
                  novalidate="novalidate">

		<fieldset class="form">
         <legend>${entityName}</legend>

		<div class="form-actions">

            <div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'firstName', 'error')} required">
                <label for="firstName" class="control-label"><g:message code="registration.firstName.label" default="First Name" /><span class="required-indicator">*</span></label>
                <div class="controls">
                    <g:textField name="firstName" required="" value="${registrationInstance?.firstName}"/>
                    <span class="help-inline">${hasErrors(bean: registrationInstance, field: 'firstName', 'error')}</span>
                </div>
            </div>

            <div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'lastName', 'error')} required">
                <label for="lastName" class="control-label"><g:message code="registration.lastName.label" default="Last Name" /><span class="required-indicator">*</span></label>
                <div class="controls">
                    <g:textField name="lastName" required="" value="${registrationInstance?.lastName}"/>
                    <span class="help-inline">${hasErrors(bean: registrationInstance, field: 'lastName', 'error')}</span>
                </div>
            </div>

            <div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'username', 'error')} required">
                <label for="username" class="control-label"><g:message code="registration.username.label" default="Username" /><span class="required-indicator">*</span></label>
                <div class="controls">
                    <g:textField name="username" required="" value="${registrationInstance?.username}"/>
                    <span class="help-inline">
                        This username is available script.
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
                    <g:passwordField name="password" required="" value="${registrationInstance?.password}"/>
                    <span class="help-inline">
                        Password must be between 6 to 20 characters in length.
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
                        Password does not match.
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
                    <g:textField name="companyName" required="" value="${registrationInstance?.companyName}"/>
                    <span class="help-inline">${hasErrors(bean: registrationInstance, field: 'companyName', 'error')}</span>
                </div>
            </div>

            <div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'newCompany', 'error')} ">
                <label for="newCompany" class="control-label"><g:message code="registration.newCompany.label" default="New Company?" /></label>
                <div class="controls">
                    <bs:checkBox name="newCompany" value="${registrationInstance?.newCompany}" />
                    <span class="help-inline">${hasErrors(bean: registrationInstance, field: 'newCompany', 'error')}</span>
                </div>
            </div>

            <div class="accordion" id="accordion2">
                <div class="accordion-group">
                    <div class="accordion-heading">
                        <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseOne">
                            <g:message code="registration.companyInformation.label" default="Company Information" />
                        </a>
                    </div>
                    <div id="collapseOne" class="accordion-body collapse">
                        <div class="accordion-inner">
                            <div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'companyType', 'error')} ">
                                <label for="companyType" class="control-label">
                                    <span class="required-indicator">*</span>
                                    <g:message code="registration.companyType.label" default="Company Type" /></label>
                                <div class="controls">
                                    <g:select id="companyType" name="companyType" from="${CompanyType.listOrderByIntCode()}"
                                              optionKey="intCode" optionValue="code" value="${registrationInstance?.companyType?.id}"
                                              class="many-to-one"/>
                                    <span class="help-inline">${hasErrors(bean: registrationInstance, field: 'companyType', 'error')}</span>
                                </div>
                            </div>

                            <div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'phoneNumber', 'error')} ">
                                <label for="phoneNumber" class="control-label"><g:message code="registration.phoneNumber.label" default="Phone Number" />
                                    <span class="required-indicator">*</span>
                                </label>
                                <div class="controls">
                                    <g:textField name="phoneNumber" value="${registrationInstance?.phoneNumber}"/>
                                    <span class="help-inline">${hasErrors(bean: registrationInstance, field: 'phoneNumber', 'error')}</span>
                                </div>
                            </div>

                            <div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'streetAddressOne', 'error')} ">
                                <label for="streetAddressOne" class="control-label"><g:message code="address.streetAddressOne.label" default="Street Address One" />
                                    <span class="required-indicator">*</span>
                                </label>
                                <div class="controls">
                                    <g:textField name="streetAddressOne" value="${registrationInstance?.streetAddressOne}"/>
                                    <span class="help-inline">${hasErrors(bean: registrationInstance, field: 'streetAddressOne', 'error')}</span>
                                </div>
                            </div>

                            <div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'streetAddressTwo', 'error')} ">
                                <label for="streetAddressTwo" class="control-label"><g:message code="address.streetAddressTwo.label" default="Street Address Two" />

                                </label>
                                <div class="controls">
                                    <g:textField name="streetAddressTwo" value="${registrationInstance?.streetAddressTwo}"/>
                                    <span class="help-inline">${hasErrors(bean: registrationInstance, field: 'streetAddressTwo', 'error')}</span>
                                </div>
                            </div>

                            <div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'zipcode', 'error')} ">
                                <label for="zipcode" class="control-label"><g:message code="registration.zipcode.label" default="Zipcode" />
                                    <span class="required-indicator">*</span>
                                </label>
                                <div class="controls">
                                    <g:textField name="zipcode" value="${registrationInstance?.zipcode}"/>
                                    <span class="help-inline">${hasErrors(bean: registrationInstance, field: 'zipcode', 'error')}</span>
                                </div>
                            </div>

                            <div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'city', 'error')} ">
                                <label for="city" class="control-label"><g:message code="registration.city.label" default="City" />
                                    <span class="required-indicator">*</span>
                                </label>
                                <div class="controls">
                                    <g:textField name="city" value="${registrationInstance?.city}"/>
                                    <span class="help-inline">${hasErrors(bean: registrationInstance, field: 'city', 'error')}</span>
                                </div>
                            </div>

                            <div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'state', 'error')} ">
                                <label for="state" class="control-label"><g:message code="registration.state.label" default="State" />
                                    <span class="required-indicator">*</span>
                                </label>
                                <div class="controls">
                                    <g:textField name="state" value="${registrationInstance?.state}"/>
                                    <span class="help-inline">${hasErrors(bean: registrationInstance, field: 'state', 'error')}</span>
                                </div>
                            </div>

                            <div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'county', 'error')} ">
                                <label for="county" class="control-label"><g:message code="registration.county.label" default="County" />
                                </label>
                                <div class="controls">
                                    <g:textField name="county" value="${registrationInstance?.county}"/>
                                    <span class="help-inline">${hasErrors(bean: registrationInstance, field: 'county', 'error')}</span>
                                </div>
                            </div>

                            <div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'country', 'error')} ">
                                <label for="country" class="control-label"><g:message code="registration.country.label" default="Country" />
                                    <span class="required-indicator">*</span>
                                </label>
                                <div class="controls">
                                    <g:countrySelect name="country"
                                                     from="${com.cogda.domain.admin.SupportedCountryCode.retrieveSupportedCountryCodes()}"
                                                     value="${registrationInstance?.country}"/>
                                    <span class="help-inline">${hasErrors(bean: registrationInstance, field: 'country', 'error')}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <g:submitButton name="create" class="btn btn-primary" value="${message(code: 'default.button.register.label', default: 'Register')}" />
            <button class="btn" type="reset"><g:message code="default.button.reset.label" default="Reset" /></button>
		</div>
        </fieldset>
	</g:formRemote>
	
</section>

<g:javascript>
    $(document).ready(function(){
        $("#registerForm").validate({
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
                    minlength: 3,
                    required: true,
                    remote: {
                        url:'<g:createLink controller="register" action="availableUsername"/>',
                        type: "POST",
                        data: {
                            username: function() {
                                return $("#username").val();
                            }
                        }
                    }
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

        var registrationHandler = function(data, textStatus) {
            if(data.success){
                for(var i=0; i<data.messages.length; i++){
                    $.pnotify({
                        title: 'Save Successful',
                        text: data.messages[i],
                        type: 'success',
                        opacity: .8,
                        delay: 10000
                    });
                }

            }else{
                for(i in data.errors){
                    $.pnotify({
                        title: 'Save Failed',
                        text: data.errors[i],
                        type: 'error',
                        opacity: .8,
                        hide: false
                    });
                }
            }
        }
    });

    // After the successful submit of the registration data:
    // Email Confirmation: Your account has been created! A confirmation email will be sent to your email address. Please click on the link in the email to verify your account. After you confirm your email address, an email will be sent to the company admin to approve the account.
</g:javascript>



</body>

</html>
