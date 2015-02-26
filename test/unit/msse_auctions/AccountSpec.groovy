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





    //TODO  these three are calvo's examples..
    /*def 'saving user persists new user in database'() {
        setup:
        def account = new Account(password: 'P@$sW0rD', email: 'dan@dan.com', name: 'Dan Johnson', addressStreet: 'd', addressCity: 'd', addressState: 'MN', addressZip: 'd')

        when:
        account.save(failOnError: true)

        then:

        println(account)
        println(account.id)
        println(account.username)
        println(account.email)
        println(account.dateCreated)
        println(account.lastUpdated)

        account.errors.errorCount == 0
        account.id
        //account.dateCreated
        Account.get(account.id).email == 'dan@dan.com'
    }
    def 'updating a user changes data'() {
        setup:
        def account = new Account(email: 'joe@smith.com', password: '7654321').save(failOnError: true)
        account.save(failOnError: true)

        when:
        def foundUser = Account.get(account.id)
        foundUser.password = 'secure'
        foundUser.save(failOnError: true)

        then:
        Account.get(account.id).password == 'secure'
    }
    def 'deleting an existing user removes it from the database'() {
        setup:
        def account = new Account(email: 'pat@ska.com', password: 'skillz')
        account.save(failOnError: true)

        when:
        account.delete()

        then:
        !Account.exists(account.id)
    }*/
}
