package msse_auctions

class Listing {
    Date dateCreated
    Date lastUpdated

    Account seller
    String name
    String description
    Date startDate
    int days
    float startingPrice
    String deliverOption
    String toString(){
        "${name}"
    }

    //these are calculated columns that are updated by the controller
    String timeRemaining
    String highestBidStr
    Date endDate

    static constraints = {
        seller(nullable: false)
        name(blank: false, nullable: false)
        description(blank: false, nullable: false)
        startDate(nullable: false)
        days(nullable: false, min: 1)

        float minStartingPrice = 0.0
        startingPrice(min: minStartingPrice)

        deliverOption(inList: ["US Only", "Worldwide", "Pick Up Only"])

        timeRemaining(blank: false, nullable: true)
        highestBidStr(blank: false, nullable: true)
        endDate(nullable: true)
    }
}
