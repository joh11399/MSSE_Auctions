package msse_auctions

class Account {
    String username
    String email
    String password
    String fullName
    String addressStreet
    String addressCity
    String addressState
    String addressZip
    String toString(){
        "${fullName} (${username})"
    }
    Date dateCreated
    Date lastUpdated

    static constraints = {
        username(blank: false, unique: true)
        fullName()
        password(password: true)
        email(email: true)
        addressState(inList:["AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA",
                             "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD",
                             "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ",
                             "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC",
                             "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY" ])
        dateCreated()
        lastUpdated()
    }
}
