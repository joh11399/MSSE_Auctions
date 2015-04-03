package msse_auctions

class ReviewService {

    def BidService

    def copyReviewFromSource(def src, Review dest) {
        //if the source does not have a value, attempt to use the existing destination value
        dest.listing = src?.listing?.id ? Listing.findById(src.listing.id) : dest?.listing
        dest.reviewer = src?.reviewer?.id ? Account.findById(src.reviewer.id) : dest?.reviewer
        dest.reviewee = src?.reviewee?.id ? Account.findById(src.reviewee.id) : dest?.reviewee
        dest.reviewOf = src?.reviewOf ?: dest?.reviewOf
        dest.rating = src?.rating ?: dest?.rating
        dest.thumbs = src?.thumbs ?: dest?.thumbs
        dest.description = src.description ?: dest?.description
        //does not return anything, the dest values have been updated
    }

    def isValidReview(Account account, Review reviewInstance) {
        def listing = reviewInstance?.listing
        def seller = listing?.seller
        def winningBid = BidService.getHighestBid(listing)
        def winner = winningBid.bidder

        def reviewerId = reviewInstance?.reviewer?.id
        def revieweeId = reviewInstance?.reviewee?.id
        def reviewOf = reviewInstance?.reviewOf

        //make sure the following is true:
        //  - the account logged in is authorized to submit this review as the "reviewer"
        //  - if this is a review of the Buyer, the account logged in should be the seller, and the review should be of the winner
        //  - if this is a review of the Seller, the account logged in should be the winner, and the review should be of the seller
        def isValid = account.id == reviewerId &&
                     ((reviewOf == 'Buyer' && account.id == seller.id && revieweeId == winner.id) ||
                      (reviewOf == 'Seller' && account.id == winner.id && revieweeId == seller.id))

        return isValid
    }
}
