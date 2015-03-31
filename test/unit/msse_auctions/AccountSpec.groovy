package msse_auctions

import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Account)
class AccountSpec extends Specification {

    void "successfully create an account"() {
        setup:
        when:
        new Account(username: 'dan', email: 'dan@dan.com', password: 'danjohnson1',  name: 'Dan Johnson', addressStreet: '123', addressCity: '456', addressState: 'MN', addressZip: '54321').save(failOnError: true)

        then:
        Account.count() == 1

        cleanup:
        Account.findByUsername('dan').delete(flush: true)
    }




    @Unroll
    void "unsuccessfully create an account:  null values"() {
        setup:
        when:
        new Account(username: username, email: email, password: password, name: name, addressStreet: addressStreet, addressCity: addressCity, addressState: addressState, addressZip: addressZip).save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Account.count() == accountSize

        where:
        username | email         | password      | name          | addressStreet | addressCity | addressState | addressZip | accountSize
        null     | 'dan@dan.com' | 'danjohnson1' | 'Dan Johnson' | '123'         | '456'       | 'MN'         | '54321'    | 0
        'Dan'    | null          | 'danjohnson1' | 'Dan Johnson' | '123'         | '456'       | 'MN'         | '54321'    | 0
        'Dan'    | 'dan@dan.com' | null          | 'Dan Johnson' | '123'         | '456'       | 'MN'         | '54321'    | 0
        'Dan'    | 'dan@dan.com' | 'danjohnson1' | null          | '123'         | '456'       | 'MN'         | '54321'    | 0
        'Dan'    | 'dan@dan.com' | 'danjohnson1' | 'Dan Johnson' | null          | '456'       | 'MN'         | '54321'    | 0
        'Dan'    | 'dan@dan.com' | 'danjohnson1' | 'Dan Johnson' | '123'         | null        | 'MN'         | '54321'    | 0
        'Dan'    | 'dan@dan.com' | 'danjohnson1' | 'Dan Johnson' | '123'         | '456'       | null         | '54321'    | 0
        'Dan'    | 'dan@dan.com' | 'danjohnson1' | 'Dan Johnson' | '123'         | '456'       | 'MN'         | null       | 0
    }
    void "unsuccessfully create an account:  null name"() {
        setup:
        when:
        new Account(username: 'dan', email: 'dan@dan.com', password: 'danjohnson1',  addressStreet: '123', addressCity: '456', addressState: 'MN', addressZip: '54321').save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Account.count() == 0
    }
    void "unsuccessfully create an account:  null addressStreet"() {
        setup:
        when:
        new Account(username: 'dan', email: 'dan@dan.com', password: 'danjohnson1',  name: 'Dan Johnson', addressCity: '456', addressState: 'MN', addressZip: '54321').save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Account.count() == 0
    }
    void "unsuccessfully create an account:  null addressCity"() {
        setup:
        when:
        new Account(username: 'dan', email: 'dan@dan.com', password: 'danjohnson1',  name: 'Dan Johnson', addressStreet: '123',  addressState: 'MN', addressZip: '54321').save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Account.count() == 0
    }
    void "unsuccessfully create an account:  null addressState"() {
        setup:
        when:
        new Account(username: 'dan', email: 'dan@dan.com', password: 'danjohnson1',  name: 'Dan Johnson', addressStreet: '123', addressCity: '456', addressZip: '54321').save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Account.count() == 0
    }
    void "unsuccessfully create an account:  null addressZip"() {
        setup:
        when:
        new Account(username: 'dan', email: 'dan@dan.com', password: 'danjohnson1',  name: 'Dan Johnson', addressStreet: '123', addressCity: '456', addressState: 'MN').save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Account.count() == 0
    }







    void "unsuccessfully create an account:  bad email"() {
        setup:
        when:
        new Account(username: 'dan', email: 'dan', password: 'danjohnson1',  name: 'Dan Johnson', addressStreet: '123', addressCity: '456', addressState: 'MN', addressZip: '54321').save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Account.count() == 0
    }

    /*

    TODO  password complexity was removed for SpringSecurity.. these don't work anymore


    void "unsuccessfully create an account:  password too short"() {
        setup:
        when:
        new Account(username: 'dan', email: 'dan', password: 'danjohnson1',  name: 'Dan Johnson', addressStreet: '123', addressCity: '456', addressState: 'MN', addressZip: '54321').save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Account.count() == 0
    }


    void "unsuccessfully create an account:  password doesn't contain a number"() {
        setup:
        when:
        new Account(username: 'dan', email: 'dan@johnson.com', password: 'abcdefgh',  name: 'Dan Johnson', addressStreet: '123', addressCity: '456', addressState: 'MN', addressZip: '54321').save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Account.count() == 0
    }

    void "unsuccessfully create an account:  password doesn't contain a letter"() {
        setup:
        when:
        new Account(username: 'dan', email: 'dan@johnson.com', password: '12345678',  name: 'Dan Johnson', addressStreet: '123', addressCity: '456', addressState: 'MN', addressZip: '54321').save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Account.count() == 0
    }
    */
}
