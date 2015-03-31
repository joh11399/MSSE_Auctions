package msse_auctions

import grails.plugin.remotecontrol.RemoteControl
import spock.lang.Specification
import spock.lang.Stepwise
import spock.lang.Unroll

@Stepwise
class AccountRestFunctionalSpec extends Specification {

    @Delegate static FunctionalTestUtils utils = new FunctionalTestUtils()

    def remote = new RemoteControl()

    def setupSpec(){
        if (!utils) {
            utils = new FunctionalTestUtils()
        }
        setupSampleData()
    }
    def cleanupSpec(){
        cleanupSampleData()
    }

    def 'returns account list'() {
        when:
        def resp = doGet("api/accounts")

        then:
        resp.status == 200
        resp.contentType == 'application/json'
        def accounts = resp.data
        accounts.find { it.username == 'me' }
    }

    def 'returns account detail'() {
        when:
        def resp = doGet("api/accounts/${accountId}" as String)

        then:
        resp.status == 200
        resp.contentType == 'application/json'
        resp.data.username == 'me'
    }


    def 'not authorized to view account detail'() {
        when:
        def unauthAccountId = 2
        //accountId is logged in.  Make sure unauthAccount isn't actually logged in
        if(unauthAccountId == accountId){
            unauthAccountId = 3
        }
        def resp = doGet("api/accounts/${unauthAccountId}" as String)

        then:
        resp.status == 401
        resp.statusText == 'Unauthorized'
    }

    @Unroll
    def 'unsuccessfully create an account'() {
        when:
        def resp = doJsonPost('api/accounts', [username: username, password: password, email: email, name: name, addressStreet: addressStreet, addressCity: addressCity, addressState: addressState, addressZip: addressZip ])

        then:
        resp.status == respStatus

        where:
        username | email         | password      | name          | addressStreet | addressCity | addressState | addressZip | respStatus
        'me'     | 'me@test.com' | 'danjohnson1' | 'Dan Johnson' | '123'         | '456'       | 'MN'         | '54321'    | 409
        null     | 'dan@dan.com' | 'danjohnson1' | 'Dan Johnson' | '123'         | '456'       | 'MN'         | '54321'    | 400
        'Dan'    | null          | 'danjohnson1' | 'Dan Johnson' | '123'         | '456'       | 'MN'         | '54321'    | 400
        'Dan'    | 'dan@dan.com' | null          | 'Dan Johnson' | '123'         | '456'       | 'MN'         | '54321'    | 400
        'Dan'    | 'dan@dan.com' | 'danjohnson1' | null          | '123'         | '456'       | 'MN'         | '54321'    | 400
        'Dan'    | 'dan@dan.com' | 'danjohnson1' | 'Dan Johnson' | null          | '456'       | 'MN'         | '54321'    | 400
        'Dan'    | 'dan@dan.com' | 'danjohnson1' | 'Dan Johnson' | '123'         | null        | 'MN'         | '54321'    | 400
        'Dan'    | 'dan@dan.com' | 'danjohnson1' | 'Dan Johnson' | '123'         | '456'       | null         | '54321'    | 400
        'Dan'    | 'dan@dan.com' | 'danjohnson1' | 'Dan Johnson' | '123'         | '456'       | 'MN'         | null       | 400

        /*

        TODO  add bad passwords....

        */
    }

    def 'successfully creates a account'() {
        when:
        def resp = doJsonPost('api/accounts', [username: 'testAccount', password: 'password', email: 'testAccount@johnson.com', name: 'Dan', addressStreet: '123 Test St', addressCity: '456', addressState: 'MN', addressZip: '12345' ])

        then:
        resp.status == 201
        resp.data.id

        when:
        //now log in as this new user before viewing the account details
        setupLogIn('testAccount', 'password')
        resp = doGet("api/accounts/${resp.data.id}" as String)

        then:
        resp.status == 200
        resp.contentType == 'application/json'
        resp.data.username == 'testAccount'

        //do not delete this account here
        //it is deleted in the next test
    }
    def 'delete an account'() {
        when:
        //find the account ID from the previous test.  Use that account for this test
         def deleteAccountId = remote {
             Account.findByUsername('testAccount').id
        }
        def resp = doJsonDelete("api/accounts/" + deleteAccountId, [])

        then:
        resp.status == 200
        resp.data == "Success!  Account ID ${deleteAccountId} has been deleted."

        when:
        resp = doGet("api/accounts/${deleteAccountId}" as String)

        then:
        resp.status == 404
        resp.data == "Not found"
    }

    def 'updates an account'() {
        when:
        //make sure the test account is logged in
        setupLogIn('me', 'password')
        def resp = doJsonPut('api/accounts/' + accountId, [username: 'me', password: 'password', email: 'me@test.com', name: 'Test', addressStreet: '456 Test St', addressCity: '789', addressState: 'MN', addressZip: '12345' ])

        then:
        resp.status == 200
        resp.data.id

        when:
        resp = doGet("api/accounts/${resp.data.id}" as String)

        then:
        resp.status == 200
        resp.contentType == 'application/json'
        resp.data.username == 'me'
        resp.data.name == 'Test'
        resp.data.addressStreet == '456 Test St'
        resp.data.addressCity == '789'
    }


    @Unroll
    def 'unsuccessfully update an account'() {
        when:
        def resp = doJsonPut("api/accounts/" + account, [username: username, password: password, email: email, name: name, addressStreet: addressStreet, addressCity: addressCity, addressState: addressState, addressZip: addressZip ])

        then:
        resp.status == respStatus

        //I couldn't figure out how to declare a variable that can be passed in for accountId
        where:
        account                   | username | email         | password      | name          | addressStreet | addressCity | addressState | addressZip | respStatus
        (accountId + 1) as String | 'me'     | 'me@test.com' | 'danjohnson1' | 'Dan Johnson' | '123'         | '456'       | 'MN'         | '54321'    | 401
        accountId as String       | 'me'     | 'me@test.com' | 'danjohnson1' | 'Dan Johnson' | '123'         | '456'       | 'invalid'    | '54321'    | 400/*

        TODO  add bad passwords here...

        accountId as String       | 'me'     | null          | 'danjohnson1' | 'Dan Johnson' | '123'         | '456'       | 'MN'         | '54321'    | 400
        accountId as String       | 'me'     | 'me@test.com' | null          | 'Dan Johnson' | '123'         | '456'       | 'MN'         | '54321'    | 400
        accountId as String       | 'me'     | 'me@test.com' | 'danjohnson1' | null          | '123'         | '456'       | 'MN'         | '54321'    | 400
        accountId as String       | 'me'     | 'me@test.com' | 'danjohnson1' | 'Dan Johnson' | null          | '456'       | 'MN'         | '54321'    | 400
        accountId as String       | 'me'     | 'me@test.com' | 'danjohnson1' | 'Dan Johnson' | '123'         | null        | 'MN'         | '54321'    | 400
        accountId as String       | 'me'     | 'me@test.com' | 'danjohnson1' | 'Dan Johnson' | '123'         | '456'       | null         | '54321'    | 400
        accountId as String       | 'me'     | 'me@test.com' | 'danjohnson1' | 'Dan Johnson' | '123'         | '456'       | 'MN'         | null       | 400*/
    }
}