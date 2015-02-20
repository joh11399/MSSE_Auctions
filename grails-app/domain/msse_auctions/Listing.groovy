package msse_auctions

class Listing {
    Date dateCreated
    Date lastUpdated

    Account seller
    String name
    String description
    Date begDate
    int days
    float minAmount
    String deliverOption
    String toString(){
        "${name}"
    }

    //these are calculated columns that are updated by the controller
    String timeRemaining
    String highestBidID
    Date endDate

    static constraints = {
        seller()
        name()
        description()
        begDate()
        days()
        minAmount()
        deliverOption(inList:["US Only", "Worldwide", "Pick Up Only"])

        timeRemaining(blank: false, nullable: true)
        highestBidID(blank: false, nullable: true)
        endDate(nullable: true)
    }
}
