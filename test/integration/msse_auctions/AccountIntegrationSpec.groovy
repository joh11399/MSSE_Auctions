package msse_auctions

import spock.lang.Specification

class AccountIntegrationSpec extends Specification {

    void "unsuccessfully create an account:  duplicate email"() {
        given:
        new Account(username: 'accountIntegration', email: 'accountIntegration@test.com', password: 'danjohnson1',  name: 'Dan Johnson', addressStreet: '123', addressCity: '456', addressState: 'MN', addressZip: '54321').save(failOnError: true)

        when:
        new Account(username: 'accountIntegration', email: 'accountIntegration@test.com', password: 'danjohnson1',  name: 'Dan Johnson', addressStreet: '123', addressCity: '456', addressState: 'MN', addressZip: '54321').save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
    }
}
