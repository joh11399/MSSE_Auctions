package msse_auctions

class ReviewController {

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

    def create(String listingID) {
        def review = new Review(params)
        if (listingID) {
            Listing listing = Listing.findById(listingID)
            review.listing = listing
        }

        respond review
    }


    def save(Review reviewInstance) {
        reviewInstance.save(failOnError: true) //flush:true
        redirect(controller: "review", action: "index")
    }
}
