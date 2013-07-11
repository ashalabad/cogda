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
        resource url:'js/bundles/angular/ng-grid-2.0.7.debug.js', disposition: 'head'
        resource url:'js/bundles/toastr/toastr.js', disposition: 'head'
        resource url:'js/angular/resources/restApi.js', disposition: 'head'
        resource url:'js/angular/resources/logger.js', disposition: 'head'
        resource url:'js/angular/common/helperFuncs.js', disposition: 'head'
    }

    company {
        dependsOn 'bootstrap', 'bootstrap_utils', 'angular'
        resource url:'js/angular/company/company.js'
        resource url:'js/angular/company/company.css'
    }

    companyProfile {
        dependsOn 'bootstrap', 'bootstrap_utils', 'angular'
        resource url:'js/angular/companyProfile/companyProfile.js'
        resource url:'js/angular/companyProfile/companyProfile.css'
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

    //Account
    account {
        dependsOn 'dataTables'
        resource url:[dir: 'css', file: 'account.css']
        resource url:[dir: 'js/account', file: 'account.js']
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


}