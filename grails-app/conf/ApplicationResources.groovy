modules = {

    application {
        dependsOn 'jquery,  jquery-ui'
        resource url:'js/application.js'
        resource url:'js/jqueryValidate/jquery.validate.min.js'
        resource url:'js/jqueryValidate/additional-methods.min.js'
        resource url:'js/spin/spin.min.js'
        resource url:'js/form2js/form2js.js'
        resource url:'css/docs.css'
        resource url:'css/cogda.css'
    }

    angular {
        dependsOn 'jquery'
        resource url:'js/bundles/angular/ng-grid.css', disposition: 'head'
        resource url:'js/bundles/toastr/toastr.css', disposition: 'head'
        resource url:'css/cogda.css', disposition: 'head'
        resource url:'js/bundles/angular/angular.js', disposition: 'head'
        resource url:'js/bundles/angular/angular-resource.js', disposition: 'head'
        resource url:'js/bundles/angular/ui-bootstrap-tpls-0.4.0.min.js', disposition: 'head'
        resource url:'js/bundles/angular/ui-utils.min.js', disposition: 'head'
        resource url:'js/bundles/angular/ng-grid-2.0.7.debug.js', disposition: 'head'
        resource url:'js/bundles/toastr/toastr.js', disposition: 'head'
        resource url:'js/angular/resources/restApi.js', disposition: 'head'
        resource url:'js/angular/resources/logger.js', disposition: 'head'
        resource url:'js/angular/common/helperFuncs.js', disposition: 'head'
    }

    angularStrap {
        dependsOn 'angular'
        resource url:'js/bundles/angular/angularStrap/angular-strap.min.js', disposition: 'head'
        resource url:'js/bundles/angular/angularStrap/my-bootstrap-datepicker.css', disposition: 'head'
        resource url:'js/bundles/angular/angularStrap/my-bootstrap-datepicker.js', disposition: 'head'
    }

    company {
        dependsOn 'bootstrap', 'bootstrap_utils', 'angular'
        resource url:'js/angular/company/company.js'
        resource url:'js/angular/company/company.css'
    }

    companyProfile {
        dependsOn 'bootstrap', 'bootstrap_utils', 'angular'
        resource url:'js/angular/resources/CompanyProfile.js'
        resource url:'js/angular/resources/CompanyProfileAddress.js'
        resource url:'js/angular/resources/CompanyProfileContact.js'
        resource url:'js/angular/resources/CompanyProfilePhoneNumber.js'
        resource url:'js/angular/resources/UnitedStates.js'
        resource url:'js/angular/resources/InternalUserProfile.js'
        resource url:'js/angular/companyProfile/companyProfile.js'
        resource url:'js/angular/companyProfile/companyProfile.css'
    }

    lead {
        dependsOn 'bootstrap, bootstrap_utils, angular, angularNaicsCodeTree, angularSicCodeTree, angularStrap'
        resource url: 'js/angular/resources/LeadSubTypes.js'
        resource url: 'js/angular/resources/unitedStates.js'
        resource url: 'js/angular/resources/SupportedCountryCodes.js'
        resource url: 'js/angular/resources/NoteType.js'
        resource url: 'js/angular/resources/businessTypes.js'
        resource url: 'js/angular/resources/LeadAddress.js'
        resource url: 'js/angular/resources/LeadService.js'
        resource url: 'js/angular/resources/LeadNote.js'
        resource url: 'js/angular/resources/LeadContactPhoneNumber.js'
        resource url: 'js/angular/resources/LeadContactEmailAddress.js'
        resource url: 'js/angular/resources/LeadContactAddress.js'
        resource url: 'js/angular/resources/LeadContact.js'
        resource url: 'js/angular/resources/Lead.js'
        resource url: 'js/angular/resources/LeadLineOfBusiness.js'
        resource url: 'js/angular/resources/LineOfBusiness.js'
        resource url: 'js/angular/resources/LeadUtils.js'
    }

    suspect {
        dependsOn 'bootstrap', 'bootstrap_utils', 'angular', 'lead'
        resource url: 'js/angular/resources/Suspect.js'
        resource url: 'js/angular/lead/suspect/suspect.js'
        resource url: 'css/suspect.css'
    }

    prospect {
        dependsOn 'bootstrap', 'bootstrap_utils', 'angular', 'lead'
        resource url: 'js/angular/resources/Prospect.js'
        resource url: 'js/angular/lead/prospect/prospect.js'
        resource url: 'css/prospect.css'
    }

    dataTables {
        dependsOn 'jquery,  jquery-ui'
        resource url:[dir: 'js/dataTables/css', file: 'jquery.dataTables.css']
        resource url:[dir: 'js/dataTables', file: 'jquery.dataTables.js']
        resource url:[dir: 'js/dataTables', file:'dataTables.bootstrap.css']
        resource url:[dir: 'js/dataTables', file:'dataTables.bootstrap.js']
        resource url:[dir: 'js/dataTables', file:'jquery.dataTables.refresh.js']
        resource url:[dir: 'js/dataTables', file:'ColReorderWithResize.js']
    }

    notifications {
        dependsOn 'jquery'
        resource url:'js/pinesNotify/jquery.pnotify.default.css'
        resource url:'js/pinesNotify/jquery.pnotify.js'
    }

    account {
        dependsOn 'bootstrap', 'bootstrap_utils', 'angular'
        resource url:'js/angular/resources/Account.js', disposition: 'head'
        resource url:'js/angular/resources/AccountContact.js', disposition: 'head'
        resource url:'js/angular/resources/AccountContactEmailAddress.js', disposition: 'head'
        resource url:'js/angular/resources/AccountContactPhoneNumber.js', disposition: 'head'
        resource url:'js/angular/resources/AccountContactAddress.js', disposition: 'head'
        resource url:'js/angular/resources/AccountContactLink.js', disposition: 'head'
        resource url:'js/angular/account/account.js'
        resource url:'js/angular/account/account.css'
    }

    //Contact
    contactPage {
        dependsOn 'dataTables'
        resource url:[dir: 'css', file: 'contact.css']
        resource url:[dir: 'js/contact', file: 'contact.js']
    }

    jstree {
        dependsOn 'jquery,  jquery-ui'
        resource url:[dir: 'js/jstree', file: 'jquery.jstree.js']
        resource url:[dir: 'js/jstree/themes/default', file: 'd.png']
        resource url:[dir: 'js/jstree/themes/default', file: 'd.gif']
        resource url:[dir: 'js/jstree/themes/default', file: 'style.css']
        resource url:[dir: 'js/jstree/themes/default', file: 'throbber.gif']
    }

    angularNaicsCodeTree {
        dependsOn 'jstree', 'angular'
        resource url:'js/angular/resources/NaicsCodeTree.js', disposition: 'head'
    }

    angularSicCodeTree {
        dependsOn 'jstree, angular'
        resource url:'js/angular/resources/SicCodeTree.js', disposition: 'head'
    }

    naicsCodeTree {
        dependsOn ' jstree'
        resource url:[dir: 'js/naicsCode', file: 'naicsCode.js']
    }

    sicCodeTree {
        dependsOn ' jstree'
        resource url:[dir: 'js/sicCode', file: 'sicCode.js']
    }

    //jQuery File Upload - https://github.com/blueimp/jQuery-File-Upload
    jqueryFileUpload {
        dependsOn 'jquery'
        resource url:[dir: 'js/jqueryFileUpload/css',        file:'jquery.fileupload-ui.css']
//        resource url:[dir: 'js/jqueryFileUpload/css',        file:'jquery.fileupload-ui-noscript.css']
        resource url:[dir: 'js/jqueryFileUpload/js/vendor',  file:'jquery.ui.widget.js']
        resource url:[dir: 'js/jqueryFileUpload/js',         file:'jquery.iframe-transport.js']

        resource url:[dir: 'js/jqueryFileUpload/js',         file:'jquery.fileupload.js']
        resource url:[dir: 'js/jqueryFileUpload/js',         file:'jquery.fileupload-process.js']
        resource url:[dir: 'js/jqueryFileUpload/js',         file:'jquery.fileupload-validate.js']
    }

    submissionBuilder {
        dependsOn 'bootstrap', 'bootstrap_utils', 'angular'
        resource url:'js/angular/resources/AccountContactLink.js', disposition: 'head'
        resource url:'js/angular/resources/Lead.js', disposition: 'head'
        resource url:'js/angular/resources/SubmissionBuilder.js', disposition: 'head'
        resource url:'js/angular/submissionBuilder/submissionBuilder.js'
        resource url:'js/angular/submissionBuilder/submissionBuilder.css'
    }
}