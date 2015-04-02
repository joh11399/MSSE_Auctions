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

    def copyListingFromSource(def src, Listing dest) {
        //if the source does not have a value, attempt to use the existing destination value
        dest.seller = src?.seller?.id ? Account.findById(src.seller.id) : dest?.seller
        dest.name = src?.name ?: dest?.name
        dest.description = src?.description ?: dest?.description

        //the source is not always JSON, so try two different ways to capture the startDate
        def startDate = dest?.startDate
        try {
            dest.startDate = Date.parse("yyyy-MM-dd'T'HH:mm:ss'Z'", src?.startDate)
        }
        catch(ex1) {
            try {
                dest.startDate = src?.startDate
            }
            catch (ex2) {
            }
        }

        dest.startDate = startDate ?: dest.startDate
        dest.days = src?.days ?: dest?.days
        dest.startingPrice = src?.startingPrice ?: dest?.startingPrice
        dest.deliverOption = src?.deliverOption ?: dest?.deliverOption
        //does not return anything, the dest values have been updated
    }

    def listingIsValid(def listingInstance) {
        def isValid = (!listingInstance.hasErrors() &&
                        listingInstance?.name != null &&
                        listingInstance?.description != null &&
                        listingInstance?.days >= 1 &&
                        listingInstance?.startDate != null &&
                        listingInstance?.startingPrice != null &&
                        listingInstance?.deliverOption != null &&
                        listingInstance?.seller != null)
        return isValid
    }

}
