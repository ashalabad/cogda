class UrlMappings {

	static mappings = {
		
		/* 
		 * Pages without controller 
		 */
//		"/"				(view:"/index")
//		"/about"		(view:"/siteinfo/about")
//		"/blog"			(view:"/siteinfo/blog")
//		"/systeminfo"	(view:"/siteinfo/systeminfo")
//		"/contact"		(view:"/siteinfo/contact")
//		"/terms"		(view:"/siteinfo/terms")
//		"/imprint"		(view:"/siteinfo/imprint")
//		"/nextSteps"	(view:"/home/nextSteps")
		
		/* 
		 * Pages with controller
		 * WARN: No domain/controller should be named "api" or "mobile" or "web"!
		 */
        "/"	{
			controller	= 'home'
			action		= { 'index' }
            view		= { 'index' }
        }

		"/$controller/$action?/$id?"{
			constraints {
				controller(matches:/^((?!(api|mobile|web)).*)$/)
		  	}
		}

        name api0: "/api/admin/$controller/$id"(parseRequest:true){
            action = [GET: "show", PUT: "update"]
            constraints {
                id(matches:/\d+/)
            }
        }

        name api1: "/api/admin/$controller"(parseRequest:true){
            action = [GET: "list", POST: "save", PUT: "update"]
        }

        name api2: "/api/admin/$controller/$action"(parseRequest:true)

        name api3: "/api/admin/$controller/$action/$id"(parseRequest:true)
		
		/* 
		 * System Pages without controller 
		 */
//		"403"	(view:'/_errors/403')
//		"404"	(view:'/_errors/404')
//		"500"	(view:'/_errors/error')
//		"503"	(view:'/_errors/503')
	}
}
