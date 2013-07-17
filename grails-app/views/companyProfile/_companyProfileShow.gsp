<div class="control-group fieldcontain ">
    <label class="control-label">
        <g:message code="companyProfile.published.label" />
    </label>
    <div class="controls">
        <span class="" data-ng-class="{true:'label label-success', false:'label '}[companyProfile.published == true]">
            {{ companyProfile.published ? 'Published' : 'Not Published' }}
        </span>
        <span class="help-inline"></span>
    </div>
</div>

<div class="control-group fieldcontain">
    <label class="control-label">
        <g:message code="company.companyName.label" />
    </label>
    <div class="controls">
        {{ companyProfile.company.companyName }}
        <span class="help-inline"></span>
    </div>
</div>

<div class="control-group fieldcontain">
    <label class="control-label">
        <g:message code="company.parentCompany.companyName.label" />
    </label>
    <div class="controls">
        {{ companyProfile.company.parentCompany.companyName }}
        <span class="help-inline"></span>
    </div>
</div>

<div class="control-group fieldcontain">
    <label class="control-label">
        <g:message code="companyProfile.companyWebsite" />
    </label>
    <div class="controls">
        {{ companyProfile.companyWebsite }}
        <span class="help-inline"></span>
    </div>
</div>

<div class="control-group fieldcontain">
    <label class="control-label">
        <g:message code="companyProfile.companyDescription" />
    </label>
    <div class="controls">
        {{ companyProfile.companyDescription }}
        <span class="help-inline"></span>
    </div>
</div>

<div class="control-group fieldcontain">
    <label class="control-label">
        <g:message code="companyProfile.businessSpecialties" />
    </label>
    <div class="controls">
        {{ companyProfile.businessSpecialties }}
        <span class="help-inline"></span>
    </div>
</div>

<div class="control-group fieldcontain">
    <label class="control-label">
        <g:message code="companyProfile.associations" />
    </label>
    <div class="controls">
        {{ companyProfile.associations }}
        <span class="help-inline"></span>
    </div>
</div>