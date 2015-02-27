package msse_auctions

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Account)
class AccountSpec extends Specification {

    void "successfully create an account"() {
        setup:
        when:
        new Account(email: 'dan@dan.com', password: 'danjohnson1',  name: 'Dan Johnson', addressStreet: '123', addressCity: '456', addressState: 'MN', addressZip: '54321').save(failOnError: true)

        then:
        Account.count() == 1
    }





    void "unsuccessfully create an account:  null email"() {
        setup:
        when:
        new Account(password: 'danjohnson1',  name: 'Dan Johnson', addressStreet: '123', addressCity: '456', addressState: 'MN', addressZip: '54321').save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Account.count() == 0
    }
    void "unsuccessfully create an account:  null name"() {
        setup:
        when:
        new Account(email: 'dan@dan.com', password: 'danjohnson1',  addressStreet: '123', addressCity: '456', addressState: 'MN', addressZip: '54321').save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Account.count() == 0
    }
    void "unsuccessfully create an account:  null addressStreet"() {
        setup:
        when:
        new Account(email: 'dan@dan.com', password: 'danjohnson1',  name: 'Dan Johnson', addressCity: '456', addressState: 'MN', addressZip: '54321').save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Account.count() == 0
    }
    void "unsuccessfully create an account:  null addressCity"() {
        setup:
        when:
        new Account(email: 'dan@dan.com', password: 'danjohnson1',  name: 'Dan Johnson', addressStreet: '123',  addressState: 'MN', addressZip: '54321').save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Account.count() == 0
    }
    void "unsuccessfully create an account:  null addressState"() {
        setup:
        when:
        new Account(email: 'dan@dan.com', password: 'danjohnson1',  name: 'Dan Johnson', addressStreet: '123', addressCity: '456', addressZip: '54321').save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Account.count() == 0
    }
    void "unsuccessfully create an account:  null addressZip"() {
        setup:
        when:
        new Account(email: 'dan@dan.com', password: 'danjohnson1',  name: 'Dan Johnson', addressStreet: '123', addressCity: '456', addressState: 'MN').save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Account.count() == 0
    }







    void "unsuccessfully create an account:  bad email"() {
        setup:
        when:
        new Account(email: 'dan', password: 'danjohnson1',  name: 'Dan Johnson', addressStreet: '123', addressCity: '456', addressState: 'MN', addressZip: '54321').save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Account.count() == 0
    }

    void "unsuccessfully create an account:  password too short"() {
        setup:
        when:
        new Account(email: 'dan', password: 'danjohnson1',  name: 'Dan Johnson', addressStreet: '123', addressCity: '456', addressState: 'MN', addressZip: '54321').save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Account.count() == 0
    }

    void "unsuccessfully create an account:  password doesn't contain a number"() {
        setup:
        when:
        new Account(email: 'dan@johnson.com', password: 'abcdefgh',  name: 'Dan Johnson', addressStreet: '123', addressCity: '456', addressState: 'MN', addressZip: '54321').save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Account.count() == 0
    }

    void "unsuccessfully create an account:  password doesn't contain a letter"() {
        setup:
        when:
        new Account(email: 'dan@johnson.com', password: '12345678',  name: 'Dan Johnson', addressStreet: '123', addressCity: '456', addressState: 'MN', addressZip: '54321').save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Account.count() == 0
    }
}
