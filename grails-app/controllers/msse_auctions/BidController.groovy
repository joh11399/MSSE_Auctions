package msse_auctions

class BidController {

    def beforeInterceptor = [action:this.&auth]

    def auth() {
        if(!session.user) {
            redirect(controller: 'Account', action:"login")
            return false
        }
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Bid.list(params), model:[bidInstanceCount: Bid.count()]
    }

    def create(String listingID) {
        def bid = new Bid(params)
        Listing listing = Listing.findById(listingID)
        bid.listing = listing
        bid.bidder = session.user
        bid.amount = getHighestBidAmount(listing)

        respond bid
    }

    static def getHighestBidAmount(Listing listing){
        def highestBidAmount
        def bids = getBids(listing)
        if(bids.size() == 0) {
            highestBidAmount = listing.minAmount
        }
        else{
            def highestBid = getHighestBid(bids)
            highestBidAmount = highestBid.amount + 0.5
        }
        highestBidAmount
    }

    static def getBids(Listing listing){
        Bid.findAll("from Bid as b where b.listing.id=:listingId", [listingId: listing.id])
    }

    static def getHighestBid(def bids){
        Bid highestBid = null
        if(bids.size() > 0){
            bids.each(){
                if(highestBid!=null){
                    if(it.amount > highestBid.amount){
                        highestBid = it
                    }
                }
                else{
                    highestBid = it
                }
            }
        }
        highestBid
    }

    def save(Bid bidInstance) {
        def highestBidAmount = getHighestBidAmount(bidInstance.listing)

        if (bidInstance.hasErrors()) {
            respond bidInstance.errors, view:'create'
            return
        }
        else if(bidInstance.bidder.id != session.user.id) {
            respond flash.message = 'You are not logged in as ' + bidInstance.bidder + '.  Please log in or select the correct account.', view:'create'
            return
        }
        else if(bidInstance.amount < highestBidAmount){
            respond flash.message = 'The minimum bid for this listing is $' + highestBidAmount, view:'create'
            return
        }
        else{
            bidInstance.save flush:true
            //redirect(controller: "Listing", action: "show", id: bidInstance.listing.id)
            redirect(controller: "listing", action: "index")
        }
    }
}
