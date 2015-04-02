package msse_auctions

import grails.plugin.remotecontrol.RemoteControl
import spock.lang.Specification
import spock.lang.Stepwise
import spock.lang.Unroll

@Stepwise
class ListingRestFunctionalSpec extends Specification {

    @Delegate
    static FunctionalTestUtils utils = new FunctionalTestUtils()

    def remote = new RemoteControl()

    def setupSpec() {
        if (!utils) {
            utils = new FunctionalTestUtils()
        }
        setupSampleData()
    }

    def cleanupSpec() {
        cleanupSampleData()
    }

    @Unroll
    def 'returns a list of open and completed listings'() {
        when:
        def resp = doGet('api/listings' + queryString)

        then:
        resp.status == 200
        resp.contentType == 'application/json'
        def listings = resp.data

        listings.find { it.name == "${listingName}" }

        where:
        queryString              | listingName
        ''                       | 'testOpen'
        '?includeCompleted=true' | 'testOpen'
        '?includeCompleted=true' | 'testCompleted'
    }

    @Unroll
    def 'returns listing detail'() {
        when:
        def resp = doGet("api/listings/${listingId}" as String)

        then:
        resp.status == 200
        resp.contentType == 'application/json'
        resp.data.name == listingName

        where:
        listingId                    | listingName
        listingOpenId as String      | 'testOpen'
        listingCompletedId as String | 'testCompleted'
    }




    def 'unsuccessfully create a listing - not logged in'() {
        given:
        setupLogOut('me')

        when:
        def resp = doJsonPost('api/listings', [name: 'testName', description: 'testDescription', startDate: new Date().format("yyyy-MM-dd'T'HH:mm:ss'Z'"), days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: [id: accountTest1.id]])

        then: 'the page redirects to the login page'
        resp.status == 302
        resp.headers.toString().indexOf("Location: http://localhost:8080/MSSE_Auctions/login/auth") != -1
    }


    @Unroll
    def 'unsuccessfully create a listing'() {
        given:
        setupLogIn('me', 'abcd1234')

        when:
        def resp = doJsonPost('api/listings', [name: name, description: description, startDate: startDate, days: days, startingPrice: startingPrice, deliverOption: deliverOption, seller: [id: sellerId]])

        then:
        resp.status == respStatus

        where:
        //giving a null startingPrice will simply convert it to 0.0, which is an acceptable value.  no need to test for null
        name       | description       | startDate                                     | days | startingPrice | deliverOption | sellerId        | respStatus
        null       | 'testDescription' | new Date().format("yyyy-MM-dd'T'HH:mm:ss'Z'") | 10   | 10.00         | 'US Only'     | accountTest1.id | 400
        'testName' | null              | new Date().format("yyyy-MM-dd'T'HH:mm:ss'Z'") | 10   | 10.00         | 'US Only'     | accountTest1.id | 400
        'testName' | 'testDescription' | null                                          | 10   | 10.00         | 'US Only'     | accountTest1.id | 400
        'testName' | 'testDescription' | new Date().format("yyyy-MM-dd'T'HH:mm:ss'Z'") | null | 10.00         | 'US Only'     | accountTest1.id | 400
        'testName' | 'testDescription' | new Date().format("yyyy-MM-dd'T'HH:mm:ss'Z'") | 0    | 10.00         | 'US Only'     | accountTest1.id | 400
        'testName' | 'testDescription' | new Date().format("yyyy-MM-dd'T'HH:mm:ss'Z'") | 10   | 10.00         | null          | accountTest1.id | 400
        'testName' | 'testDescription' | new Date().format("yyyy-MM-dd'T'HH:mm:ss'Z'") | 10   | 10.00         | 'US Only'     | null            | 400
        'testName' | 'testDescription' | new Date().format("yyyy-MM-dd'T'HH:mm:ss'Z'") | 10   | 10.00         | 'US Only'     | accountTest2.id | 401
    }

    def 'successfully create a listing'() {
        when:
        def resp = doJsonPost('api/listings', [name: 'testName', description: 'testDescription', startDate: new Date().format("yyyy-MM-dd'T'HH:mm:ss'Z'"), days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: [id: accountTest1.id]])

        then:
        resp.status == 201
        resp.data.id

        when:
        resp = doGet("api/listings/${resp.data.id}" as String)

        then:
        resp.status == 200
        resp.contentType == 'application/json'
        resp.data.name == 'testName'
        resp.data.description == 'testDescription'

        //do not delete this listing here
        //it is deleted in a subsequent test
    }

    def 'unsuccessfully update a listing - not logged in'() {
        when:
        def listingTestId = remote { Listing.findByName('testName').id } as Integer

        setupLogOut('me')
        def resp = doJsonPost("api/listings/${listingTestId}" as String, [name: 'testName', description: 'testDescription', startDate: new Date().format("yyyy-MM-dd'T'HH:mm:ss'Z'"), days: 10, startingPrice: 100.00, deliverOption: 'US Only', seller: [id: accountTest1.id]])

        then: 'the page redirects to the login page'
        resp.status == 302
        resp.headers.toString().indexOf("Location: http://localhost:8080/MSSE_Auctions/login/auth") != -1
    }

    @Unroll
    def 'unsuccessfully update a listing'() {
        given:
        def listingTestId = remote { Listing.findByName('testName').id } as Integer
        setupLogIn('me', 'abcd1234')

        when:
        def resp = doJsonPut("api/listings/${listingTestId}" as String, [name: name, description: description, startDate: startDate, days: days, startingPrice: startingPrice, deliverOption: deliverOption, seller: [id: sellerId]])

        then:
        resp.status == respStatus

        where:
        name       | description       | startDate                                     | days | startingPrice | deliverOption | sellerId  | respStatus
        //'testName' | 'testDescription' | new Date().format("yyyy-MM-dd'T'HH:mm:ss'Z'") | 'fail'    | 10.00         | 'US Only'     | accountTest1.id | 400
        'testName' | 'testDescription' | new Date().format("yyyy-MM-dd'T'HH:mm:ss'Z'") | 10   | 10.00         | 'US Only'     | accountTest2.id | 401
    }


    def 'successfully update a listing'() {
        given:
        def listingTestId = remote { Listing.findByName('testName').id } as Integer
        setupLogIn('me', 'abcd1234')

        when:
        def resp = doJsonPut("api/listings/${listingTestId}", [name: 'testName', description: 'a better description', startDate: new Date().format("yyyy-MM-dd'T'HH:mm:ss'Z'"), days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: [id: accountTest1.id]])

        then:
        resp.status == 200

        when:
        resp = doGet("api/listings/${listingTestId}" as String)

        then:
        resp.status == 200
        resp.contentType == 'application/json'
        resp.data.description == 'a better description'
    }




    def 'unsuccessfully delete a listing'() {
        given:
        setupLogIn('me', 'abcd1234')

        when:
        def resp = doJsonDelete("api/listings/" + urlListingId, [])

        then:
        resp.status == respStatus


        //cannot successfully delete any of the following
        // 0 and "" for obvious reasons, and username:'me' isn't the seller for listingOpenId
        where:
        urlListingId       | respStatus
        0                  | 404
        listingOpenId      | 401
        ""                 | 403
    }



    def 'successfully delete a listing'() {
        given:
        def listingTestId = remote { Listing.findByName('testName').id } as Integer
        setupLogIn('me', 'abcd1234')

        when:
        def resp = doJsonDelete("api/listings/${listingTestId}" as String, [])

        then:
        resp.status == 200
        resp.data == "Success!  Listing ID ${listingTestId} has been deleted as well as all related Bids and Reviews."

        when:
        resp = doGet("api/listings/${listingTestId}" as String)

        then:
        resp.status == 404
        resp.data == "Not found"
    }
}
