<%@ page import="com.cogda.domain.onboarding.Registration;"%>
<div class="big-message">
    <div class="container">

        <h2><g:message code="registration.successful"/></h2>

        <g:if test = "${registrationInstance?.newCompany}">

            <div class = "alert alert-success ">
                <g:message code = "registration.successful.newCompany" args="[registrationInstance.emailAddress, grailsApplication.config.application.name]"/>
            </div>

        </g:if>
        <g:else>

            <div class = "alert alert-success ">
                <g:message code = "registration.successful.existingCompany" args="[registrationInstance.emailAddress, registrationInstance?.existingCompany?.companyName, grailsApplication.config.application.name]"/>
            </div>

        </g:else>

    </div>
</div>