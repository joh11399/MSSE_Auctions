package msse_auctions

class Account {
    transient springSecurityService

    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired
    String username
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

    static transients = ['springSecurityService']

    static constraints = {
		username blank: false, unique: true
        email(email: true, blank: false, unique: true)
        password(blank: false)//, password: true, size: 8..16, validator: {val ->
            //def containsNumber = val.matches(".*\\d.*")
            //def containsLetter = val ==~ /.*[a-zA-Z].*/

            //return containsNumber && containsLetter
        //})

        name(nullable: false)
        addressStreet(nullable: false)
        addressCity(nullable: false)
        addressState(inList:["AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA",
                             "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD",
                             "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ",
                             "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC",
                             "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY" ], nullable: false)

        addressZip(nullable: false)

        dateCreated()
        lastUpdated()
    }

	static mapping = {
		password column: '`password`'
	}

	Set<Role> getAuthorities() {
		AccountRole.findAllByAccount(this).collect { it.role }
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}
}
