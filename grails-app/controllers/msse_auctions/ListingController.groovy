package msse_auctions

class ListingController {

    def beforeInterceptor = [action:this.&auth]

    def auth() {


        //TODO  this bypasses the login process
        session.user = Account.findById(1)

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
        def searchDescription = ''
        if(params.searchDescription!=null) {
            searchDescription = params.searchDescription
        }

        println("")
        println("descr:  "+ params.searchDescription)
        println("descr:  "+ searchDescription)
        println("params:  " + params)
        println("max   :  " + params.max)
        println("offset:  " + params.offset)
        println("sort  :  " + params.sort)
        println("order :  " + params.order)

        params.max = Math.min(max ?: 10, 100)
        if(params.offset==null){
            params.offset = 0
        }


        def listings

        if(completedListingsCheckbox=='on'){

            //TODO user-specified sorting doesn't work.....

            if(searchDescription!='') {
                //listings = Listing.findAll("from Listing as l where (l.startDate + l.days) < :today and l.description like :description order by (l.startDate + l.days) desc", [today: new Date(), description: searchDescription], params)
                listings = Listing.findAll("from Listing as l where l.description like :description order by (l.startDate + l.days)", [description: '%'+searchDescription+'%'], params)
            }else {
                //listings = Listing.findAll("from Listing as l where (l.startDate + l.days) < ? order by (l.startDate + l.days) desc", [new Date()], params)
                listings = Listing.findAll("from Listing as l order by (l.startDate + l.days)", [max:10, offset:0])
            }
        }
        else {
            if (searchDescription != '') {
                listings = Listing.findAll("from Listing as l where (l.startDate + l.days) >= :today and l.description like :description order by (l.startDate + l.days)", [today: new Date(), description: '%'+searchDescription+'%'], params)
            } else {
                listings = Listing.findAll("from Listing as l where (l.startDate + l.days) >= ? order by (l.startDate + l.days)", [new Date()], params)
            }
        }

        //max: params.max, offset: params.offset, sort: params.sort, order: params.order

        listings.each(){
            getListingBidderAndState(it)
        }

        respond listings, model:[listingInstanceCount: Listing.count(), searchDescription: searchDescription, completedListingsCheckboxChecked: (completedListingsCheckbox=='on')]
    }

    def getListingBidderAndState(Listing it){
        Date endDate = it.startDate + it.days
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
        getListingBidderAndState(listingInstance)
        respond listingInstance
    }
}
