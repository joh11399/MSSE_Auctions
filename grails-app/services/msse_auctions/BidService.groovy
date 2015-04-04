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
        Bid.findAll("from Bid as b where b.listing.id=:bidListingId order by amount desc", [bidListingId: listing.id])
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

    def copyBidFromSource(def src, Bid dest) {
        //if the source does not have a value, attempt to use the existing destination value
        dest.listing = Listing.findById(src.listing.id) ?: dest.listing
        dest.bidder = Account.findById(src.bidder.id) ?: dest.bidder
        dest.amount = src.amount ?: dest.amount
        //does not return anything, the dest values have been updated
    }
}