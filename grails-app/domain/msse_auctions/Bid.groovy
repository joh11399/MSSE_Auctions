package msse_auctions

class Bid {
    String id
    Listing auction
    Account bidder
    String amount
    Date bidDate
    String toString(){
        "${bidder} (${amount})"
    }
    Date dateCreated
    Date lastUpdated

    static constraints = {
        id(blank: false, unique: true)
        auction()
        bidder()
        amount()
        bidDate()
    }
}
