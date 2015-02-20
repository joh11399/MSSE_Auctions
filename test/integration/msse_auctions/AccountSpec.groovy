package msse_auctions

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Account)
class AccountSpec extends Specification {
    void "successfully create a user"() {
        setup:
        when:
        new Account(username: 'joh11399', password: 'P@$sW*rD', email: 'dan@dan.com', fullName: 'Dan Johnson', addressStreet: 'd', addressCity: 'd', addressState: 'MN', addressZip: 'd').save(failOnError: true)

        then:
        Account.count() == 1
    }

    void "unsuccessfully create a user:  bad username"() {
        setup:
        when:
        new Account(username: '', password: 'P@$sW*rD', email: 'dan@dan.com', fullName: 'Dan Johnson', addressStreet: 'd', addressCity: 'd', addressState: 'MN', addressZip: 'd').save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Account.count() == 0
    }

    void "unsuccessfully create a user:  bad email"() {
        setup:
        when:
        new Account(username: 'joh11399', password: 'P@$sW*rD', email: 'd', fullName: 'Dan Johnson', addressStreet: 'd', addressCity: 'd', addressState: 'MN', addressZip: 'd').save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Account.count() == 0
    }
}
