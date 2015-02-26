package msse_auctions

class Account {
    String email
    String password
    String name
    String addressStreet
    String addressCity
    String addressState
    String addressZip
    String toString(){
        "${name}"
    }
    Date dateCreated
    Date lastUpdated



    static constraints = {
        email(email: true, blank: false, unique: true)
        password(blank: false, password: true, size: 8..16, validator: {val ->
            def containsNumber = val.matches(".*\\d.*")
            def containsLetter = val ==~ /.*[a-zA-Z].*/

            return containsNumber && containsLetter
        })
        name()
        addressState(inList:["AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA",
                             "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD",
                             "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ",
                             "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC",
                             "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY" ])
        dateCreated()
        lastUpdated()
    }
}
