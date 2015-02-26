package msse_auctions

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(AccountController)
class AccountIntegrationSpec extends Specification {


    void "unsuccessfully create an account:  duplicate email"() {
        given:
        new Account(email: 'dan@johnson.com', password: 'danjohnson1',  name: 'Dan Johnson', addressStreet: '123', addressCity: '456', addressState: 'MN', addressZip: '54321').save(failOnError: true)

        when:
        new Account(email: 'dan@johnson.com', password: 'danjohnson1',  name: 'Dan Johnson', addressStreet: '123', addressCity: '456', addressState: 'MN', addressZip: '54321').save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
    }

}
