package msse_auctions

class Bid {
    Date dateCreated
    Date lastUpdated

    Listing listing
    Account bidder
    float amount
    String toString(){
        "${bidder} (${amount})"
    }

    static constraints = {
        listing(nullable: false)
        bidder(nullable: false)
        amount(nullable: false)
    }
}
