<%@ page import="com.cogda.domain.Contact" %>



			<div class="control-group fieldcontain ${hasErrors(bean: contactInstance, field: 'jobTitle', 'error')} ">
				<label for="jobTitle" class="control-label"><g:message code="contact.jobTitle.label" default="Job Title" /></label>
				<div class="controls">
					<g:textField name="jobTitle" value="${contactInstance?.jobTitle}"/>
					<span class="help-inline">${hasErrors(bean: contactInstance, field: 'jobTitle', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: contactInstance, field: 'website', 'error')} ">
				<label for="website" class="control-label"><g:message code="contact.website.label" default="Website" /></label>
				<div class="controls">
					<g:textField name="website" value="${contactInstance?.website}"/>
					<span class="help-inline">${hasErrors(bean: contactInstance, field: 'website', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: contactInstance, field: 'companyName', 'error')} ">
				<label for="companyName" class="control-label"><g:message code="contact.companyName.label" default="Company Name" /></label>
				<div class="controls">
					<g:textField name="companyName" value="${contactInstance?.companyName}"/>
					<span class="help-inline">${hasErrors(bean: contactInstance, field: 'companyName', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: contactInstance, field: 'firstName', 'error')} required">
				<label for="firstName" class="control-label"><g:message code="contact.firstName.label" default="First Name" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:textField name="firstName" required="" value="${contactInstance?.firstName}"/>
					<span class="help-inline">${hasErrors(bean: contactInstance, field: 'firstName', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: contactInstance, field: 'middleName', 'error')} ">
				<label for="middleName" class="control-label"><g:message code="contact.middleName.label" default="Middle Name" /></label>
				<div class="controls">
					<g:textField name="middleName" value="${contactInstance?.middleName}"/>
					<span class="help-inline">${hasErrors(bean: contactInstance, field: 'middleName', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: contactInstance, field: 'lastName', 'error')} required">
				<label for="lastName" class="control-label"><g:message code="contact.lastName.label" default="Last Name" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:textField name="lastName" required="" value="${contactInstance?.lastName}"/>
					<span class="help-inline">${hasErrors(bean: contactInstance, field: 'lastName', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: contactInstance, field: 'dateOfBirth', 'error')} ">
				<label for="dateOfBirth" class="control-label"><g:message code="contact.dateOfBirth.label" default="Date Of Birth" /></label>
				<div class="controls">
					<bs:datePicker name="dateOfBirth" precision="day"  value="${contactInstance?.dateOfBirth}" default="none" noSelection="['': '']" />
					<span class="help-inline">${hasErrors(bean: contactInstance, field: 'dateOfBirth', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: contactInstance, field: 'gender', 'error')} ">
				<label for="gender" class="control-label"><g:message code="contact.gender.label" default="Gender" /></label>
				<div class="controls">
					<g:select name="gender" from="${com.cogda.common.GenderEnum?.values()}" keys="${com.cogda.common.GenderEnum.values()*.name()}" value="${contactInstance?.gender?.name()}" noSelection="['': '']"/>
					<span class="help-inline">${hasErrors(bean: contactInstance, field: 'gender', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: contactInstance, field: 'initials', 'error')} ">
				<label for="initials" class="control-label"><g:message code="contact.initials.label" default="Initials" /></label>
				<div class="controls">
					<g:textField name="initials" value="${contactInstance?.initials}"/>
					<span class="help-inline">${hasErrors(bean: contactInstance, field: 'initials', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: contactInstance, field: 'title', 'error')} ">
				<label for="title" class="control-label"><g:message code="contact.title.label" default="Title" /></label>
				<div class="controls">
					<g:textField name="title" value="${contactInstance?.title}"/>
					<span class="help-inline">${hasErrors(bean: contactInstance, field: 'title', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: contactInstance, field: 'userProfile', 'error')} ">
				<label for="userProfile" class="control-label"><g:message code="contact.userProfile.label" default="User Profile" /></label>
				<div class="controls">
					<g:select id="userProfile" name="userProfile.id" from="${com.cogda.domain.UserProfile.list()}" optionKey="id" value="${contactInstance?.userProfile?.id}" class="many-to-one" noSelection="['null': '']"/>
					<span class="help-inline">${hasErrors(bean: contactInstance, field: 'userProfile', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: contactInstance, field: 'contactAddresses', 'error')} ">
				<label for="contactAddresses" class="control-label"><g:message code="contact.contactAddresses.label" default="Contact Addresses" /></label>
				<div class="controls">
					
<ul class="one-to-many">
<g:each in="${contactInstance?.contactAddresses?}" var="c">
    <li><g:link controller="contactAddress" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="contactAddress" action="create" params="['contact.id': contactInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'contactAddress.label', default: 'ContactAddress')])}</g:link>
</li>
</ul>

					<span class="help-inline">${hasErrors(bean: contactInstance, field: 'contactAddresses', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: contactInstance, field: 'contactEmailAddresses', 'error')} ">
				<label for="contactEmailAddresses" class="control-label"><g:message code="contact.contactEmailAddresses.label" default="Contact Email Addresses" /></label>
				<div class="controls">
					
<ul class="one-to-many">
<g:each in="${contactInstance?.contactEmailAddresses?}" var="c">
    <li><g:link controller="contactEmailAddress" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="contactEmailAddress" action="create" params="['contact.id': contactInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'contactEmailAddress.label', default: 'ContactEmailAddress')])}</g:link>
</li>
</ul>

					<span class="help-inline">${hasErrors(bean: contactInstance, field: 'contactEmailAddresses', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: contactInstance, field: 'contactPhoneNumbers', 'error')} ">
				<label for="contactPhoneNumbers" class="control-label"><g:message code="contact.contactPhoneNumbers.label" default="Contact Phone Numbers" /></label>
				<div class="controls">
					
<ul class="one-to-many">
<g:each in="${contactInstance?.contactPhoneNumbers?}" var="c">
    <li><g:link controller="contactPhoneNumber" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="contactPhoneNumber" action="create" params="['contact.id': contactInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'contactPhoneNumber.label', default: 'ContactPhoneNumber')])}</g:link>
</li>
</ul>

					<span class="help-inline">${hasErrors(bean: contactInstance, field: 'contactPhoneNumbers', 'error')}</span>
				</div>
			</div>

