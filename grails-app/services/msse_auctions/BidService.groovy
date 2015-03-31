package msse_auctions

class BidService {





    def setListingHighestBid(Listing listing){

        //get all bids for this listing
        def bids = getBids(listing)
        if(bids.size() > 0){

            //find the bid with the highest amount within bids
            def bid = getHighestBid(bids)

            listing.highestBidStr = "\$" + bid.amount.round(2) + " - " + bid.bidder

            //add "Winner" to completed listings
            if(listing.timeRemaining == 'completed') {
                listing.highestBidStr = "Winner: " + listing.highestBidStr
            }
        }
    }

    def getHighestBid(Listing listing){

        /*

        TODO  you need to rewrite this.....

        Bid.get {
            listing == listing &&  max(amount)
        } as Bid
        */

        def bids = Bid.findAll("from Bid as b where b.listing.id=:listingId order by dateCreated desc", [listingId: listing?.id])

        def j = 0;
        Bid returnBid
        bids.each(){
            if(j==0){
               returnBid = it
            }
            j++
        }

        returnBid
    }

    /*
    TODO   finish these services and move the methods off of the BidController

    def getHighestBid(){

//TODO...
    //remember that a bidder can change his or her bid to be lower than the highest
    // however..  that doesn't change the date created.  hmmmmmm...

        return Bid.
    }

    def isValidBid(Bid bidInstance){
        def isValid = false

        def highestBid = getHighestBid()
        float highestBidAmount = highestBid.amount
        if( bidInstance.amount > (highestBidAmount + 0.25) ){

        }

        return isValid
    }
     */


    def getHighestBidAmount(Listing listing){
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

    def getBids(Listing listing){
        //get all bids for the specified listing
        Bid.findAll("from Bid as b where b.listing.id=:listingId", [listingId: listing.id])
    }

    def getHighestBid(def bids){
        Bid highestBid = null
        if(bids.size() > 0){
            //loop through the collection of bids and assign the bid with the highest amount as the highestBid
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

    def getBidFromJson(Bid bidInstance, def jsonObject) {
        bidInstance.listing = Listing.findById(jsonObject.listing.id) ?: bidInstance.listing
        bidInstance.bidder = Account.findById(jsonObject.bidder.id) ?: bidInstance.bidder
        bidInstance.amount = jsonObject.amount ?: bidInstance.amount

        //does not return anything, the bidInstance values have been updated
    }
}