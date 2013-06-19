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
}