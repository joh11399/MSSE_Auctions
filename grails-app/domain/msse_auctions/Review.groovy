package msse_auctions

class Review {
    Listing listing
    Account reviewer
    Account reviewee
    String reviewOf
    int rating
    String thumbs
    String description

    Date dateCreated
    Date lastUpdated

    static constraints = {
        rating(size: 0..5)
        reviewOf(inList:["Buyer", "Seller" ])
        thumbs(inList:["up", "down" ])
        description(maxSize: 50)
    }
}
