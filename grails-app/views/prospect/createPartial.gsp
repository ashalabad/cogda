<%--
  Created by IntelliJ IDEA.
  User: chewy
  Date: 7/13/13
  Time: 4:03 PM
  To change this template use File | Settings | File Templates.
--%>

<section id="edit-prospect" class="first">
    <div class="span12">
        <form class="form-horizontal new" name="leadForm" autocomplete="off" novalidate >
            <legend>
                <g:message code="prospect.label"/>
            </legend>
            <g:render template="/lead/partials/createPartial"/>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary" id="prospectFormSave" data-ng-click="saveProspect(lead)"
                        %{--data-ng-disabled="!canSave()"--}%
                ><i class="icon-save"></i>
                    <g:message code="default.button.save.label" default="Save"/>
                </button>
                <button class="btn" id="cancelFormSave" data-ng-click="resetProspect()" >
                    <g:message code="default.button.reset.label" default="Reset"/>
                </button>
            </div>
        </form>
    </div>
</section>