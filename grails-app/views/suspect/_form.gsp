<%@ page import="com.cogda.multitenant.Lead" %>



			<div class="control-group fieldcontain ${hasErrors(bean: suspectInstance, field: 'clientId', 'error')} ">
				<label for="clientId" class="control-label"><g:message code="lead.clientId.label" default="Client Id" /></label>
				<div class="controls">
					<g:textField name="clientId" value="${suspectInstance?.clientId}"/>
					<span class="help-inline">${hasErrors(bean: suspectInstance, field: 'clientId', 'error')}</span>
				</div>
			</div>

            <div class="control-group fieldcontain ${hasErrors(bean: suspectInstance, field: 'clientName', 'error')} ">
                <label for="ownerName" class="control-label"><g:message code="lead.clientName.label" default="Client Name" /></label>
                <div class="controls">
                    <g:textField name="clientName" value="${suspectInstance?.clientName}"/>
                    <span class="help-inline">${hasErrors(bean: suspectInstance, field: 'clientName', 'error')}</span>
                </div>
            </div>

            <div class="control-group fieldcontain ${hasErrors(bean: suspectInstance, field: 'address1', 'error')} ">
                <label for="ownerName" class="control-label"><g:message code="address.streetAddressOne.label" default="Address Line 1" /></label>
                <div class="controls">
                    <g:textField name="address1" value="${suspectInstance?.address1}"/>
                    <span class="help-inline">${hasErrors(bean: suspectInstance, field: 'address1', 'error')}</span>
                </div>
            </div>

            <div class="control-group fieldcontain ${hasErrors(bean: suspectInstance, field: 'address2', 'error')} ">
                <label for="ownerName" class="control-label"><g:message code="address.streetAddressTwo.label" default="Address Line 2" /></label>
                <div class="controls">
                    <g:textField name="address2" value="${suspectInstance?.address2}"/>
                    <span class="help-inline">${hasErrors(bean: suspectInstance, field: 'address2', 'error')}</span>
                </div>
            </div>

            <div class="control-group fieldcontain ${hasErrors(bean: suspectInstance, field: 'city', 'error')} ">
                <label for="ownerName" class="control-label"><g:message code="address.city.label" default="City" /></label>
                <div class="controls">
                    <g:textField name="city" value="${suspectInstance?.city}"/>
                    <span class="help-inline">${hasErrors(bean: suspectInstance, field: 'city', 'error')}</span>
                </div>
            </div>

            <div class="control-group fieldcontain ${hasErrors(bean: suspectInstance, field: 'zipCode', 'error')} ">
                <label for="ownerName" class="control-label"><g:message code="address.zipcode.label" default="Zip Code" /></label>
                <div class="controls">
                    <g:textField name="zipCode" value="${suspectInstance?.zipCode}"/>
                    <span class="help-inline">${hasErrors(bean: suspectInstance, field: 'zipCode', 'error')}</span>
                </div>
            </div>

            <div class="control-group fieldcontain ${hasErrors(bean: suspectInstance, field: 'county', 'error')} ">
                <label for="ownerName" class="control-label"><g:message code="address.county.label" default="County" /></label>
                <div class="controls">
                    <g:textField name="county" value="${suspectInstance?.county}"/>
                    <span class="help-inline">${hasErrors(bean: suspectInstance, field: 'county', 'error')}</span>
                </div>
            </div>

            <div class="control-group fieldcontain ${hasErrors(bean: suspectInstance, field: 'country', 'error')} required">
                <label for="country" class="control-label"><g:message code="address.country.label" default="Country" />
                    <span class="required-indicator">*</span>
                </label>
                <div class="controls">
                    <g:countrySelect id="country"
                                     name="country"
                                     from="${com.cogda.domain.admin.SupportedCountryCode.retrieveSupportedCountryCodes()}"
                                     value="${suspectInstance?.country}"/>
                    <span class="help-inline">${hasErrors(bean: suspectInstance, field: 'country', 'error')}</span>
                </div>
            </div>

            <div class="control-group fieldcontain ${hasErrors(bean: suspectInstance, field: 'firstName', 'error')} required">
                <label for="ownerName" class="control-label"><g:message code="registration.firstName.label" default="First Name" />
                    <span class="required-indicator">*</span></label>
                <div class="controls">
                    <g:textField name="firstName" value="${suspectInstance?.firstName}"/>
                    <span class="help-inline">${hasErrors(bean: suspectInstance, field: 'firstName', 'error')}</span>
                </div>
            </div>

            <div class="control-group fieldcontain ${hasErrors(bean: suspectInstance, field: 'lastName', 'error')} required">
                <label for="ownerName" class="control-label"><g:message code="registration.lastName.label" default="Last Name" />
                    <span class="required-indicator">*</span></label>
                <div class="controls">
                    <g:textField name="lastName" value="${suspectInstance?.lastName}"/>
                    <span class="help-inline">${hasErrors(bean: suspectInstance, field: 'lastName', 'error')}</span>
                </div>
            </div>

            <div class="control-group fieldcontain ${hasErrors(bean: suspectInstance, field: 'emailAddress', 'error')} required">
                <label for="ownerName" class="control-label"><g:message code="registration.emailAddress.label" default="Email Address" /><span class="required-indicator">*</span></label>
                <div class="controls">
                    <g:textField name="emailAddress" value="${suspectInstance?.emailAddress}"/>
                    <span class="help-inline">${hasErrors(bean: suspectInstance, field: 'emailAddress', 'error')}</span>
                </div>
            </div>

            <div class="control-group fieldcontain ${hasErrors(bean: suspectInstance, field: 'phoneNumber', 'error')} ">
                <label for="ownerName" class="control-label"><g:message code="registration.phoneNumber.label" default="Phone Number" /></label>
                <div class="controls">
                    <g:textField name="phoneNumber" value="${suspectInstance?.phoneNumber}"/>
                    <span class="help-inline">${hasErrors(bean: suspectInstance, field: 'phoneNumber', 'error')}</span>
                </div>
            </div>

			<div class="control-group fieldcontain ${hasErrors(bean: suspectInstance, field: 'ownerName', 'error')} ">
				<label for="ownerName" class="control-label"><g:message code="lead.ownerName.label" default="Owner Name" /></label>
				<div class="controls">
					<g:textField name="ownerName" value="${suspectInstance?.ownerName}"/>
					<span class="help-inline">${hasErrors(bean: suspectInstance, field: 'ownerName', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: suspectInstance, field: 'account', 'error')} ">
				<label for="account" class="control-label"><g:message code="lead.account.label" default="Account" /></label>
				<div class="controls">
					<g:select id="account" name="account.id" from="${com.cogda.multitenant.Account.list()}" optionKey="id" value="${suspectInstance?.account?.id}" class="many-to-one" noSelection="['null': '']"/>
					<span class="help-inline">${hasErrors(bean: suspectInstance, field: 'account', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: suspectInstance, field: 'businessType', 'error')} ">
				<label for="businessType" class="control-label"><g:message code="lead.businessType.label" default="Business Type" /></label>
				<div class="controls">
					<g:select id="businessType" name="businessType.id" from="${com.cogda.domain.admin.BusinessType.list().sort {it.description}}" optionValue="${{it.description}}" optionKey="id" value="${suspectInstance?.businessType?.id}" class="many-to-one" noSelection="['null': '']"/>
					<span class="help-inline">${hasErrors(bean: suspectInstance, field: 'businessType', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: suspectInstance, field: 'naicsCode', 'error')} ">
				<label for="naicsCode" class="control-label"><g:message code="lead.naicsCode.label" default="Naics Code" /></label>
				<div class="controls">
					<g:select id="naicsCode" name="naicsCode.id" from="${com.cogda.domain.admin.NaicsCode.list()}"  optionKey="id" value="${suspectInstance?.naicsCode?.id}" class="many-to-one" noSelection="['null': '']"/>
					<span class="help-inline">${hasErrors(bean: suspectInstance, field: 'naicsCode', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: suspectInstance, field: 'sicCode', 'error')} ">
				<label for="sicCode" class="control-label"><g:message code="lead.sicCode.label" default="Sic Code" /></label>
				<div class="controls">
					<g:select id="sicCode" name="sicCode.id" from="${com.cogda.domain.admin.SicCode.list()}" optionKey="id" value="${suspectInstance?.sicCode?.id}" class="many-to-one" noSelection="['null': '']"/>
					<span class="help-inline">${hasErrors(bean: suspectInstance, field: 'sicCode', 'error')}</span>
				</div>
			</div>

