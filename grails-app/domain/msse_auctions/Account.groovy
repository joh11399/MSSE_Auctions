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
		username(nullable: false, blank: false, unique: true)
        email(nullable: false, email: true, blank: false, unique: true)
        password(nullable: false, blank: false)
        //the password validation is performed in validatePasswordComplexity(),
        // the encoding seems to happen before the validation,
        //  so the unencoded password is intercepted and validated before it get encoded

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
        if(validatePasswordComplexity(password)){
            encodePassword()
        }
        else{
            throw new IllegalArgumentException('Invalid password complexity')
        }
	}

	def beforeUpdate() {
		if (isDirty('password')) {
            if(validatePasswordComplexity(password)){
                encodePassword()
            }
            else{
                throw new IllegalArgumentException('Invalid password complexity')
            }
		}
	}

    def validatePasswordComplexity(def pw){
        def validPasswordLength = pw.length() > 7 && pw.length() < 17
        def containsNumber = pw.matches(".*\\d.*")
        def containsLetter = pw ==~ /.*[a-zA-Z].*/

        return (validPasswordLength && containsNumber && containsLetter)
    }

	protected void encodePassword() {
        password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}
}
