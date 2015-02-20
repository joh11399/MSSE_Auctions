package msse_auctions

class Review {
    Listing listing
    Account reviewer
    Account reviewee
    String revieweeType
    int rating
    String thumbs
    String description

    static constraints = {
        revieweeType(inList:["Buyer", "Seller" ])
        rating(size: 0..5)
        thumbs(inList:["up", "down" ])
        description(maxSize: 50)
    }
}
