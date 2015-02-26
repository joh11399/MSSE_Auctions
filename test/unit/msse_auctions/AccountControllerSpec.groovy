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

    //TODO   do you need to be logged in?...
    /*def setup(){
        def user = Account.findByEmailAndPassword(params.email, params.password)
        if(user){
            session.user = user
    }*/


    def "create account"() {
        given:
        request.method = 'POST'
        controller.request.json = '{"class":"msse_auctions.Account","email":"dan@johnson.com","password":"danjohnson1","name":"danjohnson","addressStreet":"123","addressCity":"456","addressState":"MN","addressZip":"54321"}'

        when:
        controller.save()

        then:
        Account.list().size() == 1
        def newAccount = Account.findByEmail('dan@johnson.com')
        newAccount != null

        cleanup:
        Account.findByEmail('dan@johnson.com').delete()

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
        controller.request.json = '{"class":"msse_auctions.Account","email":"dan@johnson.com","password":"a1","name":"Dan Johnson","addressStreet":"123","addressCity":"456","addressState":"MN","addressZip":"54321"}'

        when:
        controller.save()

        then:
        Account.count() == 0
    }

    def "create account via json post with a password without a number"(){
        given:
        request.method = 'POST'
        controller.request.json = '{"class":"msse_auctions.Account","email":"dan@johnson.com","password":"abcdefgh","name":"Dan Johnson","addressStreet":"123","addressCity":"456","addressState":"MN","addressZip":"54321"}'

        when:
        controller.save()

        then:
        Account.count() == 0
    }

    def "create account via json post with a password without a letter"(){
        given:
        request.method = 'POST'
        controller.request.json = '{"class":"msse_auctions.Account","email":"dan@johnson.com","password":"12345678","name":"Dan Johnson","addressStreet":"123","addressCity":"456","addressState":"MN","addressZip":"54321"}'

        when:
        controller.save()

        then:
        Account.count() == 0
    }

    def "show account details"() {
        given:
        def newAccount = new Account(email: 'dan@dan.com', password: 'danjohnson1', name: 'Dan Johnson', addressStreet: '123', addressCity: '456', addressState: 'MN', addressZip: '54321').save(failOnError: true)

        when:
        controller.show(newAccount)

        then:
        //verify the accountInstance has the correct parameters
        model.accountInstance.email == 'dan@dan.com'
        model.accountInstance.password == 'danjohnson1'
        model.accountInstance.name == 'Dan Johnson'
        model.accountInstance.addressStreet == '123'
        model.accountInstance.addressCity == '456'
        model.accountInstance.addressState == 'MN'
        model.accountInstance.addressZip == '54321'
    }


/*
        println("")
        println("response:  "+_response)
        println("")
        //println("resptext:  "+_response)
        println("")
        println("source .:  "+source)
        println("")

        //TODO  you need to assert something

    }*/

    def "update account"(){
        given:
        new Account(email: 'dan@dan.com', password: 'danjohnson1',  name: 'Dan Johnson', addressStreet: '123', addressCity: '456', addressState: 'MN', addressZip: '54321').save(failOnError: true)
        request.method = 'POST'
        controller.request.json = '{"class":"msse_auctions.Account","id":"1","email":"dan@dan.com","password":"danjohnson1","name":"danjohnson","addressStreet":"123","addressCity":"789","addressState":"MN","addressZip":"54321"}'

        when:
        controller.update()

        then:
        Account.list().size() == 1
        def updatedAccount = Account.findByEmail('dan@dan.com')
        updatedAccount.addressCity == '789'
    }
}