package msse_auctions

class ListingService {

    def getListingTimeRemaining(Listing listing){
        Date endDate = listing.startDate + listing.days
        listing.endDate = endDate
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
        listing.timeRemaining = timeRemaining
    }

    def getListingFromJson(Listing listingInstance, def jsonObject) {
        listingInstance.seller = Account.findById(jsonObject.seller.id) ?: listingInstance.seller
        listingInstance.name = jsonObject.name ?: listingInstance.name
        listingInstance.description = jsonObject.description ?: listingInstance.description
        listingInstance.startDate = jsonObject.startDate ?: listingInstance.startDate
        listingInstance.days = jsonObject.days ?: listingInstance.days
        listingInstance.startingPrice = jsonObject.startingPrice ?: listingInstance.startingPrice
        listingInstance.deliverOption = jsonObject.deliverOption ?: listingInstance.deliverOption
        //does not return anything, the listingInstance values have been updated
    }
}
