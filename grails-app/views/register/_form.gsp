<%@ page import="com.cogda.domain.onboarding.Registration" %>



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
					<span class="help-inline">${hasErrors(bean: registrationInstance, field: 'username', 'error')}</span>
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
				<label for="password" class="control-label"><g:message code="registration.password.label" default="Password" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:textField name="password" required="" value="${registrationInstance?.password}"/>
					<span class="help-inline">${hasErrors(bean: registrationInstance, field: 'password', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'companyName', 'error')} required">
				<label for="companyName" class="control-label"><g:message code="registration.companyName.label" default="Company Name" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:textField name="companyName" required="" value="${registrationInstance?.companyName}"/>
					<span class="help-inline">${hasErrors(bean: registrationInstance, field: 'companyName', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'newCompany', 'error')} ">
				<label for="newCompany" class="control-label"><g:message code="registration.newCompany.label" default="New Company" /></label>
				<div class="controls">
					<bs:checkBox name="newCompany" value="${registrationInstance?.newCompany}" />
					<span class="help-inline">${hasErrors(bean: registrationInstance, field: 'newCompany', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'companyType', 'error')} ">
				<label for="companyType" class="control-label"><g:message code="registration.companyType.label" default="Company Type" /></label>
				<div class="controls">
					<g:select id="companyType" name="companyType.id" from="${com.cogda.domain.admin.CompanyType.list()}" optionKey="id" value="${registrationInstance?.companyType?.id}" class="many-to-one" noSelection="['null': '']"/>
					<span class="help-inline">${hasErrors(bean: registrationInstance, field: 'companyType', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'companyTypeOther', 'error')} ">
				<label for="companyTypeOther" class="control-label"><g:message code="registration.companyTypeOther.label" default="Company Type Other" /></label>
				<div class="controls">
					<g:textField name="companyTypeOther" value="${registrationInstance?.companyTypeOther}"/>
					<span class="help-inline">${hasErrors(bean: registrationInstance, field: 'companyTypeOther', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'phoneNumber', 'error')} ">
				<label for="phoneNumber" class="control-label"><g:message code="registration.phoneNumber.label" default="Phone Number" /></label>
				<div class="controls">
					<g:textField name="phoneNumber" value="${registrationInstance?.phoneNumber}"/>
					<span class="help-inline">${hasErrors(bean: registrationInstance, field: 'phoneNumber', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'streetAddressOne', 'error')} ">
				<label for="streetAddressOne" class="control-label"><g:message code="registration.streetAddressOne.label" default="Street Address One" /></label>
				<div class="controls">
					<g:textField name="streetAddressOne" value="${registrationInstance?.streetAddressOne}"/>
					<span class="help-inline">${hasErrors(bean: registrationInstance, field: 'streetAddressOne', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'streetAddressTwo', 'error')} ">
				<label for="streetAddressTwo" class="control-label"><g:message code="registration.streetAddressTwo.label" default="Street Address Two" /></label>
				<div class="controls">
					<g:textField name="streetAddressTwo" value="${registrationInstance?.streetAddressTwo}"/>
					<span class="help-inline">${hasErrors(bean: registrationInstance, field: 'streetAddressTwo', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'zipcode', 'error')} ">
				<label for="zipcode" class="control-label"><g:message code="registration.zipcode.label" default="Zipcode" /></label>
				<div class="controls">
					<g:textField name="zipcode" value="${registrationInstance?.zipcode}"/>
					<span class="help-inline">${hasErrors(bean: registrationInstance, field: 'zipcode', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'city', 'error')} ">
				<label for="city" class="control-label"><g:message code="registration.city.label" default="City" /></label>
				<div class="controls">
					<g:textField name="city" value="${registrationInstance?.city}"/>
					<span class="help-inline">${hasErrors(bean: registrationInstance, field: 'city', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'state', 'error')} ">
				<label for="state" class="control-label"><g:message code="registration.state.label" default="State" /></label>
				<div class="controls">
					<g:textField name="state" value="${registrationInstance?.state}"/>
					<span class="help-inline">${hasErrors(bean: registrationInstance, field: 'state', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'county', 'error')} ">
				<label for="county" class="control-label"><g:message code="registration.county.label" default="County" /></label>
				<div class="controls">
					<g:textField name="county" value="${registrationInstance?.county}"/>
					<span class="help-inline">${hasErrors(bean: registrationInstance, field: 'county', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'country', 'error')} ">
				<label for="country" class="control-label"><g:message code="registration.country.label" default="Country" /></label>
				<div class="controls">
					<g:textField name="country" value="${registrationInstance?.country}"/>
					<span class="help-inline">${hasErrors(bean: registrationInstance, field: 'country', 'error')}</span>
				</div>
			</div>

