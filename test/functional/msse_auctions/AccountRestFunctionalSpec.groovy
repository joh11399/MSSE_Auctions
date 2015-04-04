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
        given:
        //logging in here is unnecessary, but I was running into problems relying solely on the setupSampleData() for login
        setupLogIn('me', 'abcd1234')

        when:
        def resp = doGet("api/accounts")

        then:
        resp.status == 200
        resp.contentType == 'application/json'
        def accounts = resp.data
        accounts.find { it.username == 'me' }
    }

    def 'returns account detail'() {
        given:
        setupLogIn('me', 'abcd1234')

        when:
        def resp = doGet("api/accounts/${accountId}" as String)

        then:
        resp.status == 200
        resp.contentType == 'application/json'
        resp.data.username == 'me'
    }

    def 'not authorized to view account detail'() {
        given:
        setupLogIn(accountTest1.username, accountTest1.password)

        when:
        //accountTest1 is logged in.  accountTest2 cannot access this account
        def resp = doGet("api/accounts/${accountTest2.id}" as String)

        then:
        resp.status == 401
        resp.statusText == 'Unauthorized'
    }

    @Unroll
    def 'unsuccessfully create an account'() {
        when:
        def resp = doJsonPost('api/accounts', [username: username, password: password, email: email, name: name, addressStreet: addressStreet, addressCity: addressCity, addressState: addressState, addressZip: addressZip])

        then:
        resp.status == respStatus
        resp.data.toString().indexOf(respText) != -1


        where:
        username | email          | password      | name          | addressStreet | addressCity | addressState | addressZip | respStatus | respText
        'me'     | 'me@test.com'  | 'danjohnson1' | 'Dan Johnson' | '123'         | '456'       | 'MN'         | '54321'    | 409        | 'Duplicate account.  The username and email must be unique.'
        null     | 'me2@test.com' | 'danjohnson1' | 'Dan Johnson' | '123'         | '456'       | 'MN'         | '54321'    | 400        | 'Bad request.  The parameters provided caused an error: '
        'me2'    | null           | 'danjohnson1' | 'Dan Johnson' | '123'         | '456'       | 'MN'         | '54321'    | 400        | 'Bad request.  The parameters provided caused an error: '
        'me2'    | 'me2@test.com' | null          | 'Dan Johnson' | '123'         | '456'       | 'MN'         | '54321'    | 400        | 'Bad request.  The parameters provided caused an error: '
        'me2'    | 'me2@test.com' | 'danjohnson1' | null          | '123'         | '456'       | 'MN'         | '54321'    | 400        | 'Bad request.  The parameters provided caused an error: '
        'me2'    | 'me2@test.com' | 'danjohnson1' | 'Dan Johnson' | null          | '456'       | 'MN'         | '54321'    | 400        | 'Bad request.  The parameters provided caused an error: '
        'me2'    | 'me2@test.com' | 'danjohnson1' | 'Dan Johnson' | '123'         | null        | 'MN'         | '54321'    | 400        | 'Bad request.  The parameters provided caused an error: '
        'me2'    | 'me2@test.com' | 'danjohnson1' | 'Dan Johnson' | '123'         | '456'       | null         | '54321'    | 400        | 'Bad request.  The parameters provided caused an error: '
        'me2'    | 'me2@test.com' | 'danjohnson1' | 'Dan Johnson' | '123'         | '456'       | 'MN'         | null       | 400        | 'Bad request.  The parameters provided caused an error: '

        //bad passwords
        'me2' | 'me2@test.com' | 'danjohnson' | 'Dan Johnson' | '123' | '456' | 'MN' | '54321'  | 400 | 'Invalid password.  Passwords must be between 8-16 characters, containing a number and a letter.'
        'me2' | 'me2@test.com' | '12345678'   | 'Dan Johnson' | '123' | '456' | 'MN' | '54321'  | 400 | 'Invalid password.  Passwords must be between 8-16 characters, containing a number and a letter.'

        'me2' | 'me2@test.com' | '1tooLongTooLongTooLongTooLongTooLong' | 'Dan Johnson' | '123' | '456' | 'MN' | '54321'  | 400 | 'Invalid password.  Passwords must be between 8-16 characters, containing a number and a letter.'
        'me2' | 'me2@test.com' | 'a1234567891011121314151617181920' | 'Dan Johnson' | '123' | '456' | 'MN' | '54321'  | 400 | 'Invalid password.  Passwords must be between 8-16 characters, containing a number and a letter.'

        'me2' | 'me2@test.com' | 'abc123'     | 'Dan Johnson' | '123' | '456' | 'MN' | '54321'  | 400 | 'Invalid password.  Passwords must be between 8-16 characters, containing a number and a letter.'
        'me2' | 'me2@test.com' | 'abc'        | 'Dan Johnson' | '123' | '456' | 'MN' | '54321'  | 400 | 'Invalid password.  Passwords must be between 8-16 characters, containing a number and a letter.'
        'me2' | 'me2@test.com' | '123'        | 'Dan Johnson' | '123' | '456' | 'MN' | '54321'  | 400 | 'Invalid password.  Passwords must be between 8-16 characters, containing a number and a letter.'
        'me2' | 'me2@test.com' | ''           | 'Dan Johnson' | '123' | '456' | 'MN' | '54321'  | 400 | 'Bad request.  The parameters provided caused an error: '
    }


    /*
    *
    *
    *  it is intentional that there is no test for unauthorized account creation
    *    user's don't need to be (can't be) logged in to create an account
    *
    *
    */


    def 'successfully create an account'() {
        when:
        def resp = doJsonPost('api/accounts', [username: 'testAccount', password: 'abcd1234', email: 'testAccount@johnson.com', name: 'Dan', addressStreet: '123 Test St', addressCity: '456', addressState: 'MN', addressZip: '12345' ])

        then:
        resp.status == 201
        resp.data.id

        when:
        //now log in as this new user before viewing the account details
        setupLogIn('testAccount', 'abcd1234')
        resp = doGet("api/accounts/${resp.data.id}" as String)

        then:
        resp.status == 200
        resp.contentType == 'application/json'
        resp.data.username == 'testAccount'

        //do not delete this account here
        //it is deleted in a subsequent test
    }


    def 'unsuccessfully update an account - not logged in'() {
        given:
        setupLogOut('me')

        when:
        def resp = doJsonPut("api/accounts/${accountTest1.id}", [username: 'me', password: 'abcd1234', email: 'updatedEmail@test.com', name: 'Me Test', addressStreet: '456 Test St', addressCity: '789', addressState: 'MN', addressZip: '12345'])

        then: 'the page redirects to the login page'
        resp.status == 302
        resp.headers.toString().indexOf("Location: http://localhost:8080/MSSE_Auctions/login/auth") != -1
    }


    @Unroll
    def 'unsuccessfully update an account'() {
        given:
        setupLogIn('me', 'abcd1234')

        when:
        def resp = doJsonPut("api/accounts/${account}", [username: username, password: password, email: email, name: name, addressStreet: addressStreet, addressCity: addressCity, addressState: addressState, addressZip: addressZip])

        then:
        resp.status == respStatus
        resp.data.toString().indexOf(respText) != -1

        where:
        account                   | username | email | password | name | addressStreet | addressCity | addressState | addressZip | respStatus | respText
        //logged as username:'me'  and attempt to update the wrong account ID
        accountTest2.id as String | 'me'  | 'me@test.com' | 'danjohnson1' | 'Dan Johnson' | '123' | '456' | 'MN' | '54321' | 401 | 'Not authorized to update Account ID '

        //attempt to change username:'me' to a non-unique username
        accountTest1.id as String | 'test' | 'me@test.com' | 'danjohnson1' | 'Dan Johnson' | '123' | '456' | 'MN' | '54321' | 400 | 'Bad request.  The parameters provided caused an error: '

        //attempt to change email:'me@test.com' to a non-unique email
        accountTest1.id as String | 'me' | 'test@test.com' | 'danjohnson1' | 'Dan Johnson' | '123' | '456' | 'MN' | '54321' | 400 | 'Bad request.  The parameters provided caused an error: '
    }

    def 'successfully update an account'() {
        when:
        //make sure the test account is logged in
        setupLogIn('testAccount', 'abcd1234')
        def testAccountId = remote {
            Account.findByUsername('testAccount').id
        }
        def resp = doJsonPut("api/accounts/${testAccountId}", [username: 'testAccount', password: 'abcd1234', email: 'testAccount@test.com', name: 'Test Account', addressStreet: '456 Test St', addressCity: '789', addressState: 'MN', addressZip: '12345' ])

        then:
        resp.status == 200
        resp.data.id

        when:
        resp = doGet("api/accounts/${resp.data.id}" as String)

        then:
        resp.status == 200
        resp.contentType == 'application/json'
        resp.data.id == testAccountId
        resp.data.username == 'testAccount'
        resp.data.name == 'Test Account'
        resp.data.addressStreet == '456 Test St'
        resp.data.addressCity == '789'
    }

    def 'unsuccessfully delete an account'() {
        when:
        def resp = doJsonDelete("api/accounts/" + urlAccountId, [])

        then:
        resp.status == respStatus

        //the second line will only work if it is run in sequence.  The account logged in must be 'testAccount'
        where:
        urlAccountId    | respStatus
        0               | 404
        accountTest1.id | 401
        ""              | 403
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
}