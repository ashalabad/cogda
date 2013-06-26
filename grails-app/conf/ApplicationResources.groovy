modules = {

    application {
        dependsOn 'jquery,  jquery-ui'
        resource url:'js/application.js'
        resource url:'js/jqueryValidate/jquery.validate.min.js'
        resource url:'js/jqueryValidate/additional-methods.min.js'
        resource url:'js/spin/spin.min.js'
        resource url:'css/docs.css'
        resource url:'css/cogda.css'
    }

    dataTables {
        dependsOn 'jquery,  jquery-ui'
        resource url:[dir: 'js/dataTables/css', file: 'jquery.dataTables.css']
        resource url:[dir: 'js/dataTables', file: 'jquery.dataTables.js']
        resource url:[dir: 'js/dataTables', file:'dataTables.bootstrap.css']
        resource url:[dir: 'js/dataTables', file:'dataTables.bootstrap.js']
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

    naicsCodeTree {
        dependsOn 'jquery,  jquery-ui'

        resource url:[dir: 'js/naicsCode', file: 'naicsCode.js']
        resource url:[dir: 'js/jstree', file: 'jquery.jstree.js']
        resource url:[dir: 'js/jstree/themes/default', file: 'd.png']
        resource url:[dir: 'js/jstree/themes/default', file: 'd.gif']
        resource url:[dir: 'js/jstree/themes/default', file: 'style.css']
        resource url:[dir: 'js/jstree/themes/default', file: 'throbber.gif']


    }


}