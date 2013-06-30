<h3>
    <g:message code="userImport.title.fileLayout"/>
</h3>
<table class="table table-bordered table-hover">
    <thead>
    <tr>
        <th>
            *
            <a class = "popoverLink"
               data-toggle="popover"
               data-placement="top"
               data-trigger="hover"
               data-content="<g:message code="userImport.firstName.instructions"/>"
               title=""
               data-original-title="<g:message code="userImport.firstName.label"/>">
                <g:message code="registration.firstName.label"/>
            </a>
        </th>
        <th>
            *
            <a href = "#"
               class = "popoverLink"
               data-toggle="popover"
               data-placement="top"
               data-trigger="hover"
               data-content="<g:message code="userImport.lastName.instructions"/>"
               title=""
               data-original-title="<g:message code="userImport.lastName.label"/>">
                <g:message code="registration.lastName.label"/>
            </a>
        </th>
        <th>
            *
            <a href = "#"
               class = "popoverLink"
               data-toggle="popover"
               data-placement="top"
               data-trigger="hover"
               data-content="<g:message code="userImport.emailAddress.instructions"/>"
               title=""
               data-original-title="<g:message code="userImport.emailAddress.label"/>">
                <g:message code="registration.emailAddress.label"/>
            </a>
        </th>
        <th>
            <a href = "#"
               class = "popoverLink"
               data-toggle="popover"
               data-placement="top"
               data-trigger="hover"
               data-content="<g:message code="userImport.securityRoles.instructions"/>"
               title=""
               data-original-title="<g:message code="userImport.securityRoles.label"/>">
                <g:message code="userImport.securityRoles.field.label"/>
            </a>
        </th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>Oscar</td>
        <td>Mighty</td>
        <td>oscarmighty@cogda.com</td>
        <td>
            "<g:each in = "${assignableRoleInstances.take(2)}" status="i" var="role"><g:if test = "${i != 0}">,</g:if>${role.authority}</g:each>"
        </td>
    </tr>
    <tr>
        <td>Jane</td>
        <td>Jaynes</td>
        <td>janejaynes@codga.com</td>
        <td>
            <g:each in = "${assignableRoleInstances.take(1)}" status="i" var="role">
                <g:if test = "${i != 0}">
                    ,
                </g:if>
                ${role.authority}
            </g:each>
        </td>
    </tr>
    </tbody>
</table>

<div class="pretty">
    <h4>
        <g:message code = "userImport.file.snippet.title"></g:message>
    </h4>
    <div class = "well">
        Oscar,Mighty,oscarmighty@cogda.com,"ROLE_BRANCH_MANAGER,ROLE_COMPANY_MANAGER"
        Jane,Jaynes,janejaynes@codga.com,ROLE_BRANCH_MANAGER
    </div>
</div>
<div class="pretty">
    <h4>
        <g:message code = "userImport.file.example.download"/>

    </h4>
    <img class="" src="${resource(dir: 'images/kudos', file: 'DownloadCrate.png')}" title="${message(code: "userImport.file.example.download")}"/>
    <a href = "https://s3-us-west-2.amazonaws.com/cogda-production/cogdaweb/documents/CogdaUserImportFileExample.csv">
        <g:message code = "userImport.file.example.label"/>
    </a>
</div>

<div class="pretty">
    <h4>
        <g:message code = "userImport.availableRoles.title"/>

    </h4>

    <dl>
        <g:each in = "${assignableRoleInstances}" status="i" var="role">
            <dt>${role.authority}</dt>
            <dd>${role.description}</dd>
        </g:each>

    </dl>
</div>
