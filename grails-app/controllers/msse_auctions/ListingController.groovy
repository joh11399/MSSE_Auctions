package msse_auctions

class ListingController {

    def beforeInterceptor = [action:this.&auth]

    def auth() {
        if(!session.user) {
            redirect(controller: 'Account', action:"login")
            return false
        }
    }

    def index(Integer max) {

        def completedListingsCheckbox = 'off'
        if(params.completedListingsCheckbox!=null) {
            completedListingsCheckbox = params.completedListingsCheckbox
        }

        println("")
        println("params:  " + params)
        println("max   :  " + params.max)
        println("offset:  " + params.offset)
        println("sort  :  " + params.sort)
        println("order :  " + params.order)

        params.max = Math.min(max ?: 10, 100)

        def listings

        if(completedListingsCheckbox=='on'){

            //TODO user-specified sorting doesn't work.....

            listings = Listing.findAll("from Listing as l where (l.begDate + l.days) < ? order by (l.begDate + l.days) desc", [new Date()], params)
        }
        else{
            listings = Listing.findAll("from Listing as l where (l.begDate + l.days) >= ? order by (l.begDate + l.days) desc", [new Date()], params)
        }

        //max: params.max, offset: params.offset, sort: params.sort, order: params.order

        listings.each(){
            Date endDate = it.begDate + it.days
            it.endDate = endDate
            Date today = new Date()
            String timeRemaining = ""
            use(groovy.time.TimeCategory) {
                def duration = endDate - today
                int minutesRemaining = ((duration.toMilliseconds() / 1000) / 60)

                if(minutesRemaining>0) {
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

            def bids = BidController.getBids(it)
            if(bids.size() > 0){
                def bid = BidController.getHighestBid(bids)
                it.highestBidID = "\$" + bid.amount.round(2) + " - " + bid.bidder
            }
        }

        respond listings, model:[listingInstanceCount: Listing.count(), completedListingsCheckboxChecked: (completedListingsCheckbox=='on')]
    }


    def create() {
        def listing = new Listing(params)
        listing.seller = session.user
        respond listing
    }
    def save(Listing listingInstance) {
        if (listingInstance.hasErrors()) {
            respond listingInstance.errors, view:'create'
            return
        }else{
            listingInstance.save()
            redirect(action: "index")
        }
    }

    def show(Listing listingInstance) {
        respond listingInstance
    }
}
