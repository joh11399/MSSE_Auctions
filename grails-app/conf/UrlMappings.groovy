class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/api/accounts"(resources: 'accountRest')
        "/api/bids"(resources: 'bidRest')
        "/api/listings"(resources: 'listingRest')
        "/api/reviews"(resources: 'reviewRest')

        "/"(view:"/index")
        "500"(view:'/error')
	}
}
