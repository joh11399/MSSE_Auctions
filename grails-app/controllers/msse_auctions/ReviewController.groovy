package msse_auctions

class ReviewController {

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)

        println(Review.list())

        respond Review.list(params), model:[reviewInstanceCount: Review.count()]
    }
}
