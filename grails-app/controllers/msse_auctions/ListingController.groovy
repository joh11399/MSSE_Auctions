package msse_auctions

import grails.plugin.springsecurity.annotation.Secured

class ListingController {

    def springSecurityService

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

        if(params.completedListingsCheckbox=='on'){
            if(!params.searchDescription) {
                //get all listings using the search text
                listings = Listing.findAll("from Listing as l where l.description like :description order by (l.startDate + l.days)", [description: '%'+params.searchDescription+'%'], params)
            }else {
                //get all listings
                listings = Listing.findAll("from Listing as l order by (l.startDate + l.days)", [max: params.max, offset: params.offset])
            }
        }
        else {
            if (params.searchDescription != '') {
                //get open listings using the search text
                listings = Listing.findAll("from Listing as l where (l.startDate + l.days) >= :today and l.description like :description order by (l.startDate + l.days)", [today: new Date(), description: '%'+params.searchDescription+'%'], params)
            } else {
                //get open listings
                listings = Listing.findAll("from Listing as l where (l.startDate + l.days) >= ? order by (l.startDate + l.days)", [new Date()], params)
            }
        }

        listings.each(){
            getListingBidderAndState(it)
        }

        respond listings, model:[listingInstanceCount: Listing.count(), searchDescription: params.searchDescription, completedListingsCheckboxChecked: (params.completedListingsCheckbox=='on')]
    }

    def getListingBidderAndState(Listing it){
        Date endDate = it.startDate + it.days
        it.endDate = endDate
        Date today = new Date()
        String timeRemaining = ""
        use(groovy.time.TimeCategory) {

            //get the duration (in minutes) of time remaining in the listing
            def duration = endDate - today
            int minutesRemaining = ((duration.toMilliseconds() / 1000) / 60)

            //if the minutes remaining are greater than zero, the listing is still open
            if(minutesRemaining>0) {

                //based on the number of minutes remaining, determine the appropriate time value to return: minutes/hours/days.
                if(minutesRemaining<60){
                    timeRemaining = "${minutesRemaining} minutes"
                } else if(minutesRemaining<1440) {
                    float hours = (minutesRemaining / 60)
                    hours = hours.round(1)
                    timeRemaining = "${hours} hours"
                } else {
                    float days = (minutesRemaining / 1440)
                    days = days.round(1)
                    timeRemaining = "${days} days"
                }
            }
            else{
                timeRemaining = "completed"
            }
        }
        it.timeRemaining = timeRemaining

        //get all bids for this listing
        def bids = BidController.getBids(it)
        if(bids.size() > 0){

            //find the bid with the highest amount within bids
            def bid = BidController.getHighestBid(bids)

            it.highestBidStr = "\$" + bid.amount.round(2) + " - " + bid.bidder

            //add "Winner" to completed listings
            if(it.timeRemaining == 'completed') {
                it.highestBidStr = "Winner: " + it.highestBidStr
            }
        }
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
        getListingBidderAndState(listingInstance)
        respond listingInstance
    }
}
