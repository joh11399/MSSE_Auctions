package msse_auctions

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import groovy.json.JsonSlurper
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(AccountController)
@Mock([Account])
class AccountControllerSpec extends Specification {

    def parser = new JsonSlurper()


    def "create account"() {
        given:
        request.method = 'POST'
        controller.request.json = '{"class":"msse_auctions.Account","email":"accountController@test.com","password":"danjohnson1","name":"danjohnson","addressStreet":"123","addressCity":"456","addressState":"MN","addressZip":"54321"}'

        when:
        controller.save()

        then:
        Account.list().size() == 1
        def newAccount = Account.findByEmail('accountController@test.com')
        newAccount != null

        cleanup:
        Account.findByEmail('accountController@test.com').delete()

    }
    def "create account with bad email"() {
        given:
        request.method = 'POST'
        controller.request.json = '{"class":"msse_auctions.Account","email":"dan","password":"danjohnson1","name":"danjohnson","addressStreet":"123","addressCity":"456","addressState":"MN","addressZip":"54321"}'

        when:
        controller.save()

        then:
        Account.count() == 0
    }

    def "create account via json post with too short of a password"(){
        given:
        request.method = 'POST'
        controller.request.json = '{"class":"msse_auctions.Account","email":"accountController@test.com","password":"a1","name":"Dan Johnson","addressStreet":"123","addressCity":"456","addressState":"MN","addressZip":"54321"}'

        when:
        controller.save()

        then:
        Account.count() == 0
    }

    def "unsuccessfully create account via json post with a 7 character of a password"(){
        given:
        request.method = 'POST'
        controller.request.json = '{"class":"msse_auctions.Account","email":"accountController@test.com","password":"a1b2c3d","name":"Dan Johnson","addressStreet":"123","addressCity":"456","addressState":"MN","addressZip":"54321"}'

        when:
        controller.save()

        then:
        Account.count() == 0
    }

    def "successfully create account via json post with a 8 character of a password"(){
        given:
        request.method = 'POST'
        controller.request.json = '{"class":"msse_auctions.Account","email":"accountController@test.com","password":"a1b2c3d4","name":"Dan Johnson","addressStreet":"123","addressCity":"456","addressState":"MN","addressZip":"54321"}'

        when:
        controller.save()

        then:
        Account.count() == 1
    }

    def "successfully create account via json post with a 16 character of a password"(){
        given:
        request.method = 'POST'
        controller.request.json = '{"class":"msse_auctions.Account","email":"accountController@test.com","password":"a1b2c3d4e5f6g7h8","name":"Dan Johnson","addressStreet":"123","addressCity":"456","addressState":"MN","addressZip":"54321"}'

        when:
        controller.save()

        then:
        Account.count() == 1
    }

    def "unsuccessfully create account via json post with a 17 character of a password"(){
        given:
        request.method = 'POST'
        controller.request.json = '{"class":"msse_auctions.Account","email":"accountController@test.com","password":"a1b2c3d4e5f6g7h8i","name":"Dan Johnson","addressStreet":"123","addressCity":"456","addressState":"MN","addressZip":"54321"}'

        when:
        controller.save()

        then:
        Account.count() == 0
    }

    def "create account via json post with a password without a number"(){
        given:
        request.method = 'POST'
        controller.request.json = '{"class":"msse_auctions.Account","email":"accountController@test.com","password":"abcdefgh","name":"Dan Johnson","addressStreet":"123","addressCity":"456","addressState":"MN","addressZip":"54321"}'

        when:
        controller.save()

        then:
        Account.count() == 0
    }

    def "create account via json post with a password without a letter"(){
        given:
        request.method = 'POST'
        controller.request.json = '{"class":"msse_auctions.Account","email":"accountController@test.com","password":"12345678","name":"Dan Johnson","addressStreet":"123","addressCity":"456","addressState":"MN","addressZip":"54321"}'

        when:
        controller.save()

        then:
        Account.count() == 0
    }

    def "update account"(){
        given:
        new Account(email: 'accountController@test.com', password: 'danjohnson1',  name: 'Dan Johnson', addressStreet: '123', addressCity: '456', addressState: 'MN', addressZip: '54321').save(failOnError: true)
        request.method = 'POST'
        controller.request.json = '{"class":"msse_auctions.Account","id":"1","email":"accountController@test.com","password":"danjohnson1","name":"danjohnson","addressStreet":"123","addressCity":"789","addressState":"MN","addressZip":"54321"}'

        when:
        controller.update()

        then:
        Account.list().size() == 1
        def updatedAccount = Account.findByEmail('accountController@test.com')
        updatedAccount.addressCity == '789'

        cleanup:
        Account.findByEmail('accountController@test.com').delete()
    }
}