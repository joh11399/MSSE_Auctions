package msse_auctions

class BidController {

    def index() {}

    def create() {
        respond new Bid(params)
    }
    def save(Bid bidInstance) {
        if (bidInstance.hasErrors()) {
            respond bidInstance.errors, view:'create'
            return
        }else{
            bidInstance.save()
            redirect(action: "index")
        }
    }
}
