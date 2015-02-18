package msse_auctions

class ListingController {

    def beforeInterceptor = [action:this.&auth]

    def auth() {
        if(!session.user) {
            redirect(controller: 'Account', action:"login")
            return false
        }
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Listing.list(params), model:[auctionInstanceCount: Listing.count()]
    }

    def create() {
        respond new Listing(params)
    }
    def save(Listing listingInstance) {
        if (listingInstance.hasErrors()) {
            respond listingInstance.errors, view:'create'
            return
        }else{
            listingInstance.save()
            redirect(action: "index")
        }
    }

    def show(Listing listingInstance) {
        respond listingInstance
    }
}
