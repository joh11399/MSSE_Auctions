package msse_auctions

import grails.rest.RestfulController

class BidRestController extends RestfulController<Bid> {

    @SuppressWarnings("GroovyUnusedDeclaration")
    static responseFormats = ['json', 'xml']

    BidRestController() {
        super(Bid)
    }

    def index(Integer max, String q) {
        if (!q) {
            return super.index(max)
        }
        params.max = Math.min(max ?: 10, 100)

        //def bids = Bid.where { name =~ "%${q.toLowerCase()}%" }.list(max: max)
        def bids = Bid.list()
        respond bids, model: [bidCount: bids.size()]
    }

}
