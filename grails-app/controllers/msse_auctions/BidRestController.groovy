package msse_auctions

import grails.plugin.springsecurity.annotation.Secured

class BidRestController {

    def springSecurityService
    def BidService

    static allowedMethods = [index: 'GET', save: 'POST', update: 'PUT', delete: 'DELETE']

    @SuppressWarnings("GroovyUnusedDeclaration")
    static responseFormats = ['json', 'xml']


    //@Secured(['ROLE_USER'])
    @Secured('permitAll()')
    def index(Integer max, int listingId) {
        params.max = Math.min(max ?: 10, 100)

        def bids
        if (listingId) {
            bids = Bid.where { listing.id == listingId }.list(max: max)
        }else{
            bids = Bid.list()
        }
        respond bids
    }

    @Secured('permitAll()')
    def show() {
        Bid bidInstance = Bid.findById(params.id)

        if (!bidInstance) {
            response.status = 404;
            render "Not found"
        } else {
            respond bidInstance
        }
    }

    @Secured(['ROLE_USER'])
    def save() {
        def account = springSecurityService.currentUser as Account

        Bid bidInstance = new Bid()
        BidService.getBidFromJson(bidInstance, request.JSON)

        if (bidInstance.hasErrors() ||
            bidInstance?.listing == null ||
            bidInstance?.bidder == null ||
            bidInstance?.amount == null) {
            response.status = 400;
            render "Bad request.  The parameters provided caused an error: " + bidInstance.errors
            return
        }
        if(bidInstance.bidder.username != account?.username) {
            response.status = 401;
            render "Not authorized to save a bid for Account ID ${bidInstance.bidder.id}"
            return
        }

        def highestBidAmount = BidService.getHighestBidAmount(bidInstance.listing)
        if(bidInstance.amount < highestBidAmount){
            response.status = 400;
            render "The minimum bid for this listing is \$${highestBidAmount}"
        }
        else {
            bidInstance.save(flush: true, failOnError: true)
            response.status = 201;
            render(contentType: 'text/json') {
                [
                        'responseText': "Success!  Bid ID ${bidInstance.id} has been created.",
                        'id'    : bidInstance.id
                ]
            }
        }
    }

    @Secured(['ROLE_USER'])
    def update() {
        if (!params.id) {

            //TODO..  is the illegalargumentexception worth using??
            //throw new IllegalArgumentException('Missing id parameter')

            response.status = 400;
            render "Bad request.  No Bid ID provided."
            return
        }

        def account = springSecurityService.currentUser as Account
        Bid bidInstance = Bid.findById(params.id)

        if (bidInstance.bidder.username != account.username) {
            response.status = 401;
            render "Not authorized to update Bid ID ${bidInstance.id}"
        } else {
            BidService.getBidFromJson(bidInstance, request.JSON)

            //although this is the same if statement, the bidder may have changed with getBidFromJson
            //make sure the user is not setting the bidder to a different account
            if (bidInstance.bidder.username != account.username) {
                response.status = 401;
                render "Not authorized to update as Account ID ${bidInstance.bidder.id}"
            }else if (bidInstance.hasErrors()) {
                response.status = 400;
                render "Bad request.  The parameters provided caused an error: " + bidInstance.errors
            }
            else{
                bidInstance.save(flush: true, failOnError: true)
                response.status = 200;
                render "Success!  Bid ID ${bidInstance.id} has been updated."
            }
        }
    }


    @Secured(['ROLE_USER'])
    def delete() {
        def account = springSecurityService.currentUser as Account
        if (!params.id) {
            response.status = 400;
            render "Bad request.  No Bid ID provided."
            return
        }

        Bid bidInstance = Bid.findById(params.id)
        if (!bidInstance) {
            response.status = 404
            render "Not found"
            return
        }

        //if the user's account doesn't match the bidInstance.bidder  return a 401
        if (bidInstance?.bidder?.username != account?.username) {
            response.status = 401;
            render "Not authorized to delete bids for Account ID ${bidInstance.bidder.id}"
            return
        }
        def bidId = bidInstance.id

        bidInstance.delete(flush: true)
        render "Success!  Bid ID ${bidId} has been deleted."
    }
}
