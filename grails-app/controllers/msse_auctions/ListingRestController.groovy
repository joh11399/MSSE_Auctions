package msse_auctions

import grails.plugin.springsecurity.annotation.Secured

class ListingRestController {

    def springSecurityService
    def ListingService
    def BidService

    static allowedMethods = [index: 'GET', save: 'POST', update: 'PUT', delete: 'DELETE']

    @SuppressWarnings("GroovyUnusedDeclaration")
    static responseFormats = ['json', 'xml']

    @Secured(['permitAll()'])
    def index(Integer max, String description, boolean includeCompleted) {
        params.max = Math.min(max ?: 10, 100)

        includeCompleted = includeCompleted ?: false

        def listings


        //TODO..  this should be moved to a service..
        if(description&& !includeCompleted) {
            listings = Listing.findAll("from Listing as l where (l.startDate + l.days) >= :today and l.description like :description order by (l.startDate + l.days)", [today: new Date(), description: '%'+description+'%'], params)

            //TODO..  can you use the .where notation for this query?
            //listings = Listing.where { description =~ "%${description.toLowerCase()}%" && (startDate + days) >= new Date() }.list(max: max)
        }
        else if(!description && !includeCompleted) {
            listings = Listing.findAll("from Listing as l where (l.startDate + l.days) >= :today order by (l.startDate + l.days)", [today: new Date()], params)

            //TODO..  can you use the .where notation for this query?
            //listings = Listing.where {  (startDate + days) >= new Date() }.list(max: max)
        }
        else if(description && includeCompleted) {
            listings = Listing.where { description =~ "%${description.toLowerCase()}%" }.list(max: max)
        }
        else {
            listings = Listing.list(max: max)
        }

        listings.each(){
            ListingService.getListingTimeRemaining(it)
            BidService.setListingHighestBid(it)
        }

        respond listings
    }

    @Secured('permitAll()')
    def show() {
        def listingInstance = Listing.findById(params.id)

        if (!listingInstance) {
            response.status = 404;
            render "Not found"
        } else {
            respond listingInstance
        }
    }

    @Secured(['ROLE_USER'])
    def save() {
        def account = springSecurityService.currentUser as Account

        Listing listingInstance = new Listing()
        ListingService.copyListingFromSource(request.JSON, listingInstance)


        if (!ListingService.listingIsValid(listingInstance)) {
            response.status = 400;
            render "Bad request.  The parameters provided caused an error: " + listingInstance.errors
        }
        else if(listingInstance?.seller?.username != account?.username) {
            response.status = 401;
            render "Not authorized to save a listing for Account ID ${listingInstance.seller.id}"
        }else {
            listingInstance.save(flush: true, failOnError: true)
            response.status = 201;
            render(contentType: 'text/json') {
                [
                        'responseText': "Success!  Listing ID ${listingInstance.id} has been created.",
                        'id'    : listingInstance.id
                ]
            }
        }
    }

    @Secured(['ROLE_USER'])
    def update() {
        if (!params.id) {
            response.status = 400;
            render "Bad request.  No Listing ID provided."
            return
        }

        def account = springSecurityService.currentUser as Account
        Listing listingInstance = Listing.findById(params.id)

        if (listingInstance.seller.username != account.username) {
            response.status = 401;
            render "Not authorized to update Listing ID ${listingInstance.id}"
        } else {
            def listingClone = new Listing()
            ListingService.copyListingFromSource(request.JSON, listingClone)

            if (!ListingService.listingIsValid(listingClone)) {
                response.status = 400;
                render "Bad request.  The parameters provided caused an error: " + listingClone.errors
            } else if (listingClone.seller.username != account.username) {
                response.status = 401;
                render "Not authorized to set Account ID ${listingClone.seller} as the seller"
            } else {
                ListingService.copyListingFromSource(listingClone, listingInstance)
                listingInstance.save(flush: true, failOnError: true)
                render "Success!  Listing ID ${listingInstance.id} has been updated."
            }
        }
    }

    @Secured(['ROLE_USER'])
    def delete() {
        if (!params.id) {
            response.status = 400;
            render "Bad request.  No Listing ID provided."
            return
        }

        def account = springSecurityService.currentUser as Account
        def listingInstance = Listing.findById(params.id)
        if (!listingInstance) {
            response.status = 404
            render "Not found"
            return
        }
        if (listingInstance.seller.username != account.username) {
            response.status = 401;
            render "Not authorized to delete Listing ID ${listingInstance.id}"
        } else {
            def listingId = listingInstance.id

            //all foreign key associations must be deleted as well..
            Bid.findByListing(listingInstance)*.delete(flush: true)
            Review.findByListing(listingInstance)*.delete(flush: true)
            listingInstance.delete(flush: true)

            render "Success!  Listing ID ${listingId} has been deleted as well as all related Bids and Reviews."
        }
    }
}
