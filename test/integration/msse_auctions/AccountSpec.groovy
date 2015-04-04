
package msse_auctions

import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

@TestFor(Account)
class AccountSpec extends Specification {

    def originalAccountSize
    void setup(){
        originalAccountSize = Account.count()
    }

    @Unroll
    void "unsuccessfully create an account:  null or invalid parameters"() {
        when:
        new Account(username: username, email: email, password: password, name: name, addressStreet: addressStreet, addressCity: addressCity, addressState: addressState, addressZip: addressZip).save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Account.count() == originalAccountSize

        where:
        username | email         | password      | name          | addressStreet | addressCity | addressState | addressZip
        null     | 'dan@dan.com' | 'danjohnson1' | 'Dan Johnson' | '123'         | '456'       | 'MN'         | '54321'
        'Dan1'   | null          | 'danjohnson1' | 'Dan Johnson' | '123'         | '456'       | 'MN'         | '54321'
        'Dan1'   | 'dan@dan.com' | null          | 'Dan Johnson' | '123'         | '456'       | 'MN'         | '54321'
        'Dan1'   | 'dan@dan.com' | 'danjohnson1' | null          | '123'         | '456'       | 'MN'         | '54321'
        'Dan1'   | 'dan@dan.com' | 'danjohnson1' | 'Dan Johnson' | null          | '456'       | 'MN'         | '54321'
        'Dan1'   | 'dan@dan.com' | 'danjohnson1' | 'Dan Johnson' | '123'         | null        | 'MN'         | '54321'
        'Dan1'   | 'dan@dan.com' | 'danjohnson1' | 'Dan Johnson' | '123'         | '456'       | null         | '54321'
        'Dan1'   | 'dan@dan.com' | 'danjohnson1' | 'Dan Johnson' | '123'         | '456'       | 'MN'         | null

        //invalid email
        'Dan'    | 'dan'         | 'danjohnson1' | 'Dan Johnson' | '123'         | '456'       | 'MN'         | '54321'
    }

    void "successfully create an account"() {
        when:
        new Account(username: 'danAccount', email: 'dan@account.com', password: 'danjohnson1',  name: 'Dan Johnson', addressStreet: '123', addressCity: '456', addressState: 'MN', addressZip: '54321').save(failOnError: true)

        then:
        Account.count() == originalAccountSize + 1

        cleanup:
        Account.findByUsername('danAccount').delete(flush: true)
    }
}
