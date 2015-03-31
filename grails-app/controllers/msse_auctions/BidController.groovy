package msse_auctions

import grails.plugin.springsecurity.annotation.Secured

class BidController {
    
    def springSecurityService
    def BidService

    @Secured('permitAll()')
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)

        def bids
        if(params.listingID){
            int listingID = params.listingID
            Listing listing = Listing.findById(listingID)
            bids = Bid.findAll("from Bid as b where b.listing.id = :listingID order by b.dateCreated desc", [listingID: listing.id], params)
        }else{
            bids = Bid.list(params)
        }
        respond bids, model:[bidInstanceCount: Bid.count()]
    }

    @Secured(['ROLE_USER'])
    def create(String listingID) {
        def bid = new Bid(params)
        if(listingID) {
            Listing listing = Listing.findById(listingID)
            bid.listing = listing
            bid.amount = BidService.getHighestBidAmount(listing)
        }
        bid.bidder = springSecurityService.currentUser as Account

        respond bid
    }

    @Secured(['ROLE_USER'])
    def save(Bid bidInstance) {
        def highestBidAmount = BidService.getHighestBidAmount(bidInstance.listing)

        if (bidInstance.hasErrors()) {
            respond bidInstance.errors, view:'create'
        }
        else if(bidInstance.amount < highestBidAmount){
            respond flash.message = 'The minimum bid for this listing is $' + highestBidAmount, view:'create'
        }
        else{
            bidInstance.save flush:true
            redirect(controller: "listing", action: "index")
        }
    }
}
