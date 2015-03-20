package msse_auctions

import grails.plugin.springsecurity.annotation.Secured

class ReviewController {

    def springSecurityService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)

        def searchBuyerSeller = ''

        def reviews
        if (params.searchBuyerSeller) {
            searchBuyerSeller = params.searchBuyerSeller
            reviews = Review.findAll("from Review as r where r.reviewer.name like :searchBuyerSeller or r.reviewee.name like :searchBuyerSeller order by r.dateCreated desc", [searchBuyerSeller: "%" + searchBuyerSeller + "%"], params)
        } else {
            reviews = Review.list(params)
        }

        respond reviews, model: [reviewInstanceCount: Review.count(), searchBuyerSeller: searchBuyerSeller]
    }
    @Secured(['ROLE_USER'])
    def create(String listingID) {
        def review = new Review(params)

        if (listingID) {
            Listing listing = Listing.findById(listingID)
            review.listing = listing
        }

        def account = springSecurityService.currentUser as Account
        def seller = review?.listing?.seller
        def winningBid = Bid.findAll("from Bid as b where b.listing.id=:listingId order by dateCreated desc", [listingId: review?.listing?.id])
        def winner = winningBid.bidder
        if (account.id != seller.id &&
            account.id != winner.id) {
            redirect(controller: "listing", action: "show", id: review?.listing?.id)
        } else {
            review.reviewOf = account.id == seller.id?'Buyer':'Seller'
            review.reviewer = account
            review.reviewee = account.id == seller.id ? winner : seller

            respond review
        }
    }

    @Secured(['ROLE_USER'])
    def save(Review reviewInstance) {
        reviewInstance.save(failOnError: true)
        redirect(controller: "review", action: "index")
    }
}
