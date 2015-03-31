package msse_auctions

import grails.plugin.springsecurity.annotation.Secured

class ReviewController {

    def springSecurityService
    def BidService

    @Secured(['ROLE_USER'])
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
        def winningBid = BidService.getHighestBid(review?.listing)
        def winner = winningBid.bidder

        //you're not using ReviewService.isVaildReviewer(..) because you need the seller and winner variables below
        //  it's less complicated if you just grab the values and perform the comparison here..
        if (account.id == seller.id ||
            account.id == winner.id) {
            //setting the reviewer and reviewee Accounts as default  (the user cannot change these)
            review.reviewOf = account.id == seller.id?'Buyer':'Seller'
            review.reviewer = account
            review.reviewee = account.id == seller.id ? winner : seller
            respond review
        } else {
            flash.message = 'Not authorized to submit a review for Listing ID ' + review?.listing?.id
            redirect(controller: "listing", action: "show", id: review?.listing?.id)
        }
    }

    @Secured(['ROLE_USER'])
    def save(Review reviewInstance) {
        reviewInstance.save(failOnError: true)
        redirect(controller: "review", action: "index")
    }
}
