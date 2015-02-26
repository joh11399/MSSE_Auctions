package msse_auctions

class BidController {

    def beforeInterceptor = [action:this.&auth]

    def auth() {

        //TODO  this bypasses the login process
        session.user = Account.findById('1')

        if(!session.user) {
            redirect(controller: 'Account', action:"login")
            return false
        }
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)

        def bids
        if(params.listingID){
            String listingID = params.listingID
            Listing listing = Listing.findById(listingID)
            bids = Bid.findAll("from Bid as b where b.listing.id = :listingID order by b.dateCreated desc", [listingID: listing.id], params)
        }else{
            bids = Bid.list(params)
        }
        respond bids, model:[bidInstanceCount: Bid.count()]
    }

    def create(String listingID) {
        def bid = new Bid(params)
        if(listingID) {
            Listing listing = Listing.findById(listingID)
            bid.listing = listing
            bid.amount = getHighestBidAmount(listing)
        }
        bid.bidder = session.user

        respond bid
    }

    static def getHighestBidAmount(Listing listing){
        def highestBidAmount
        def bids = getBids(listing)
        if(bids.size() == 0) {
            highestBidAmount = listing.startingPrice
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
        /*
        else if(bidInstance.bidder.id != session.user.id) {
            respond flash.message = 'You are not logged in as ' + bidInstance.bidder + '.  Please log in or select the correct account.', view:'create'
            return
        }*/
        else if(bidInstance.amount < highestBidAmount){
            respond flash.message = 'The minimum bid for this listing is $' + highestBidAmount, view:'create'
            return
        }
        else{
            bidInstance.save flush:true
            redirect(controller: "listing", action: "index")
        }
    }
}
