package msse_auctions

import grails.test.mixin.TestFor
import groovy.json.JsonSlurper
import spock.lang.Specification

@TestFor(AccountController)
class AccountControllerSpec extends Specification {

    def parser = new JsonSlurper()

    def "create account"() {
        given:
        def accountSizeOrig = Account.count()
        def newAccount = new Account(username: 'accountController', email: 'accountController@test.com', password: 'danjohnson1',  name: 'Dan Johnson', addressStreet: '123', addressCity: '456', addressState: 'MN', addressZip: '54321')

        when:
        controller.save(newAccount)

        then:
        Account.list().size() == accountSizeOrig + 1
        Account.findByEmail('accountController@test.com') != null

        cleanup:
        Account.findByEmail('accountController@test.com').delete()
    }

    def "unsuccessfully create account with bad email"() {
        given:
        def accountSizeOrig = Account.count()
        def newAccount = new Account(username: 'accountController', email: 'dan', password: 'danjohnson1',  name: 'Dan Johnson', addressStreet: '123', addressCity: '456', addressState: 'MN', addressZip: '54321')

        when:
        controller.save(newAccount)

        then:
        thrown(grails.validation.ValidationException)
        Account.count() == accountSizeOrig
    }

    def "unsuccessfully create account - invalid password"(){
        given:
        def accountSizeOrig = Account.count()
        def newAccount = new Account(username: 'accountController', email: 'accountController@test.com', password: password,  name: 'Dan Johnson', addressStreet: '123', addressCity: '456', addressState: 'MN', addressZip: '54321')

        when:
        controller.save(newAccount)

        then:
        Account.count() == accountSizeOrig

        where:
        password      |  notUsed
        'danjohnson'  |  0
        '12345678'    |  0
        'abc123'      |  0
        'abc'         |  0
        '123'         |  0
        '1tooLongTooLongTooLongTooLongTooLong' |  0
        'a1234567891011121314151617181920'     |  0
    }
}
