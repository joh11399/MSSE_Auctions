package msse_auctions

class ReviewService {

    def BidService

    def getReviewFromJson(Review reviewInstance, def jsonObject) {
        reviewInstance.listing = jsonObject?.listing?.id ? Listing.findById(jsonObject.listing.id) : reviewInstance?.listing
        reviewInstance.reviewer = jsonObject?.reviewer?.id ? Account.findById(jsonObject.reviewer.id) : reviewInstance?.reviewer
        reviewInstance.reviewee = jsonObject?.reviewee?.id ? Account.findById(jsonObject.reviewee.id) : reviewInstance?.reviewee
        reviewInstance.reviewOf = jsonObject?.reviewOf ?: reviewInstance?.reviewOf
        reviewInstance.rating = jsonObject?.rating ?: reviewInstance?.rating
        reviewInstance.thumbs = jsonObject?.thumbs ?: reviewInstance?.thumbs
        reviewInstance.description = jsonObject.description ?: reviewInstance?.description
        //does not return anything, the reviewInstance values have been updated
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
