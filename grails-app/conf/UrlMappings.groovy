class UrlMappings {

	static mappings = {
		/* 
		 * Pages with controller
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
		 * System Pages
		 */
		"403"	(controller:"error")
		"404"	(controller:"error")
		"500"	(controller:"error")
		"503"	(controller:"error")
	}
}
