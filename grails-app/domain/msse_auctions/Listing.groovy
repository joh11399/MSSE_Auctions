package msse_auctions

class Listing {
    String id
    Account owner
    String name
    String description
    Date begDate
    Date endDate
    String minAmount
    String buyAmount
    String toString(){
        "${name}"
    }
    Date dateCreated
    Date lastUpdated

    static constraints = {
        id(blank: false, unique: true)
        owner()
        name()
        description()
        begDate(min: new Date())
        endDate()
        minAmount()
        buyAmount()
    }
}
