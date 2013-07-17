<div class="control-group fieldcontain ">
    <label for="published" class="control-label">
        <g:message code="companyProfile.published.label" />
    </label>
    <div class="controls">
        <input type="checkbox" name="published" id="published" data-ng-model="companyProfile.published"/>
        <span class="help-inline" data-ng-show="errors.published">{{ errors.published }}</span>
    </div>
</div>

<div class="control-group fieldcontain">
    <label class="control-label">
        <g:message code="company.companyName.label" />
    </label>
    <div class="controls">
        {{ companyProfile.company.companyName }}
        <span class="help-inline" ></span>
    </div>
</div>

<div class="control-group fieldcontain">
    <label class="control-label" >
        <g:message code="company.parentCompany.companyName.label" />
    </label>
    <div class="controls">
        {{ companyProfile.company.parentCompany.companyName }}
        <span class="help-inline" ></span>
    </div>
</div>

<div class="control-group fieldcontain">
    <label class="control-label" for="companyWebsite">
        <g:message code="companyProfile.companyWebsite" />
    </label>
    <div class="controls">
        <input type="url"
               data-ng-model = "companyProfile.companyWebsite"
               id="companyWebsite"
               name="companyWebsite"/>
        <span class="help-inline" data-ng-show="errors.companyWebsite ">  {{ errors.companyWebsite }}</span>
        <span class="label label-important" data-ng-show="companyProfileDetailForm['companyWebsite'].$invalid"> ${message(code:'default.invalid.message')} </span>
        <span class="label label-success" data-ng-show="companyProfileDetailForm['companyWebsite'].$valid"> ${message(code:'default.valid.message')}  </span>
    </div>
</div>

<div class="control-group fieldcontain">
    <label class="control-label" for="companyDescription">
        <g:message code="companyProfile.companyDescription" />
    </label>
    <div class="controls">

        <textarea data-ng-model="companyProfile.companyDescription" name="companyDescription" id="companyDescription"></textarea>

        <span class="help-inline" data-ng-show="errors.companyDescription ">  {{ errors.companyDescription }}</span>
        <span class="label label-important" data-ng-show="companyProfileDetailForm['companyDescription'].$invalid"> ${message(code:'default.invalid.message')} </span>
        <span class="label label-success" data-ng-show="companyProfileDetailForm['companyDescription'].$valid"> ${message(code:'default.valid.message')}  </span>
    </div>
</div>

<div class="control-group fieldcontain">
    <label class="control-label" for="businessSpecialties">
        <g:message code="companyProfile.businessSpecialties" />
    </label>
    <div class="controls">
        <textarea data-ng-model="companyProfile.businessSpecialties" name="businessSpecialties" id="businessSpecialties"></textarea>
        <span class="help-inline" data-ng-show="errors.businessSpecialties ">  {{ errors.businessSpecialties }}</span>
        <span class="label label-important" data-ng-show="companyProfileDetailForm['businessSpecialties'].$invalid"> ${message(code:'default.invalid.message')} </span>
        <span class="label label-success" data-ng-show="companyProfileDetailForm['businessSpecialties'].$valid"> ${message(code:'default.valid.message')}  </span>
    </div>
</div>

<div class="control-group fieldcontain">
    <label class="control-label" for="associations">
        <g:message code="companyProfile.associations" />
    </label>
    <div class="controls">
        <textarea data-ng-model="companyProfile.associations" name="associations" id="associations"></textarea>
        <span class="help-inline" data-ng-show="errors.associations ">  {{ errors.associations }}</span>
        <span class="label label-important" data-ng-show="companyProfileDetailForm['associations'].$invalid"> ${message(code:'default.invalid.message')} </span>
        <span class="label label-success" data-ng-show="companyProfileDetailForm['associations'].$valid"> ${message(code:'default.valid.message')}  </span>
    </div>
</div>