<div class="row-fluid">
    <div class="span5">
        <div class="well well-small" data-ng-hide="editingLead">
            <button type="button"
                    class="btn btn-info btn-mini pull-right"
                    data-ng-click="editLead()"
                    data-ng-hide="editingLead">
                <i class="icon-edit icon-white"></i>
                <g:message code="lead.edit.label"/> {{title}}
            </button>
            <table>
                <tr>
                    <td><g:message code="lead.businessType.label"
                                   default="Business Type"/>:</td>
                    <td>{{ lead.businessType.code }}</td>
                </tr>
                <tr>
                    <td><g:message code="lead.clientId.label"
                                   default="Client Id"/>:</td>
                    <td>{{ lead.clientId }}</td></tr>
                <tr>
                    <td><g:message code="lead.clientName.label"
                                   default="Client Name"/>:</td>
                    <td>{{ lead.clientName }}</td></tr>
                <tr>
                    <td><g:message code="lead.subType.label" default="Sub Type"/>:</td>
                    <td>{{ lead.subType }}</td></tr>
                <tr>
                    <td><g:message code="lead.customerServiceRepresentative.label"
                                   default="Customer Service Representative"/>:</td>
                    <td>{{ lead.customerServiceRepresentative }}</td></tr>
            </table>
        </div>
        <div data-ng-show="editingLead">
            <div data-ng-include="" src="'/lead/editPartial'"></div>
            %{--<g:render template="/lead/partials/editPartial"/>--}%

        </div>
    </div>
</div>