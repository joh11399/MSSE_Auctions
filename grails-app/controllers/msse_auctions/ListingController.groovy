package msse_auctions

import grails.plugin.springsecurity.annotation.Secured

class ListingController {

    def springSecurityService
    def ListingService
    def BidService

    @Secured(['permitAll'])
    def index(){

        //set a default value for completedListingsCheckbox
        params.completedListingsCheckbox = params.completedListingsCheckbox ?: 'off'
        //set a default value for searchDescription
        params.searchDescription = params.searchDescription ?: ''
        //if the max is changed, use that value. otherwise set it to the default: 10
        params.max = params.max ?: 10
        //set the offset for paging
        params.offset = params.offset?: 0

        def listings


        //TODO..  this should be moved to a service..
        if(params.completedListingsCheckbox=='on'){
            if(!params.searchDescription) {
                //get all listings using the search text

                //TODO,  use this format instead
                //book = Book.findByTitleLikeOrReleaseDateLessThan("%Something%", someDate)

                listings = Listing.findAll("from Listing as l where l.description like :description order by (l.startDate + l.days)", [description: '%'+params.searchDescription+'%'], params)
            }else {
                //get all listings

                //TODO,  use this format instead
                //book = Book.findByTitleLikeOrReleaseDateLessThan("%Something%", someDate)

                listings = Listing.findAll("from Listing as l order by (l.startDate + l.days)", [max: params.max, offset: params.offset])
            }
        }
        else {
            if (params.searchDescription != '') {
                //get open listings using the search text

                //TODO,  use this format instead
                //book = Book.findByTitleLikeOrReleaseDateLessThan("%Something%", someDate)

                listings = Listing.findAll("from Listing as l where (l.startDate + l.days) >= :today and l.description like :description order by (l.startDate + l.days)", [today: new Date(), description: '%'+params.searchDescription+'%'], params)
            } else {
                //get open listings

                //TODO,  use this format instead
                //book = Book.findByTitleLikeOrReleaseDateLessThan("%Something%", someDate)

                listings = Listing.findAll("from Listing as l where (l.startDate + l.days) >= ? order by (l.startDate + l.days)", [new Date()], params)
            }
        }

        listings.each(){
            ListingService.getListingTimeRemaining(it)
            BidService.setListingHighestBid(it)
        }

        respond listings, model:[listingInstanceCount: Listing.count(), searchDescription: params.searchDescription, completedListingsCheckboxChecked: (params.completedListingsCheckbox=='on')]
    }

    @Secured(['ROLE_USER'])
    def create() {
        def listing = new Listing(params)
        listing.seller = springSecurityService.currentUser as Account
        respond listing
    }

    @Secured(['ROLE_USER'])
    def save(Listing listingInstance) {
        if (listingInstance.hasErrors()) {
            respond listingInstance.errors, view:'create'
        }else{
            listingInstance.save()
            redirect(action: "index")
        }
    }
    @Secured(['permitAll'])
    def show(Listing listingInstance) {
        ListingService.getListingTimeRemaining(listingInstance)
        respond listingInstance
    }
}
