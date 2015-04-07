package msse_auctions

import grails.plugin.remotecontrol.RemoteControl
import spock.lang.Specification
import spock.lang.Stepwise
import spock.lang.Unroll

@Stepwise
class BidRestFunctionalSpec extends Specification {

@Delegate static FunctionalTestUtils utils = new FunctionalTestUtils()

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

    def 'returns bid list'() {
        given:
        setupLogIn('me', 'abcd1234')

        when:
        def resp = utils.doGet("api/bids")

        then:
        resp.status == 200
        resp.contentType == 'application/json'
        def bids = resp.data
        bids.find { it.id == bidId }
    }


    def 'returns bid detail'() {
        given:
        setupLogIn('me', 'abcd1234')

        when:
        def resp = doGet("api/bids/${bidId}" as String)

        then:
        resp.status == 200
        resp.contentType == 'application/json'
        resp.data.id == bidId
    }

    def 'unsuccessfully create a bid - not logged in'() {
        when:
        setupLogOut('me')
        def resp = doJsonPost('api/bids', [listing: [id: listingOpenId], bidder: [id: accountTest1.id], amount: 10.00])

        then: 'the page redirects to the login page'
        resp.status == 302
        resp.headers.toString().indexOf("Location: http://localhost:8080/MSSE_Auctions/login/auth") != -1
    }

    @Unroll
    def 'unsuccessfully create a bid'() {
        given:
        setupLogIn('me', 'abcd1234')

        when:
        def resp = doJsonPost('api/bids', [listing: [id: listingTestId], bidder: [id: bidderTestId], amount: amount])

        then:
        resp.status == respStatus

        where:
        listingTestId           | bidderTestId              | amount | respStatus
        listingOpenId as String | accountId as String       | 9.99   | 400
        0 as String             | accountId as String       | 10.00  | 400
        listingOpenId as String | 0 as String               | 10.00  | 400
        listingOpenId as String | (accountId + 1) as String | 10.00  | 401
    }

    def 'successfully create a bid'() {
        when:
        def resp = doJsonPost('api/bids', [listing: [id: listingOpenId], bidder: [id: accountId], amount: 10.00])

        then:
        resp.status == 201
        resp.data.id

        when:
        resp = doGet("api/bids/${resp.data.id}" as String)

        then:
        resp.status == 200
        resp.contentType == 'application/json'
        resp.data.listing.id == listingOpenId
        resp.data.bidder.id == accountId
        resp.data.amount == 10.00

        //do not delete this bid here
        //it is deleted in a subsequent test
    }


    def 'unsuccessfully create a bid - bid is less than $0.50 more than the highest bid'() {
        when:
        def resp = doJsonPost('api/bids', [listing: [id: listingOpenId], bidder: [id: accountId], amount: 10.25])

        then:
        resp.status == 400
        resp.data == 'The minimum bid for this listing is $10.5'
    }


    def 'unsuccessfully update a bid - not logged in'() {
        when:
        def bidTestId = remote {
            Bid.findByListingAndBidder( Listing.findByName('testOpen'), Account.findByUsername('me') ).id
        } as Integer
        setupLogOut('me')
        def resp = doJsonPut("api/bids/${bidTestId}", [listing: [id: listingOpenId], bidder: [id: accountTest1.id], amount: 11.00])

        then: 'the page redirects to the login page'
        resp.status == 302
        resp.headers.toString().indexOf("Location: http://localhost:8080/MSSE_Auctions/login/auth") != -1
    }


    def 'unsuccessfully delete a bid'() {
        given:
        setupLogIn(accountTest1.username, accountTest1.password)

        when:
        def resp = doJsonDelete("api/bids/" + urlBidId, [])

        then:
        resp.status == respStatus

        where:
        urlBidId  |  respStatus
        0         |   404
        bidId     |   401
        ""        |   403
    }

    def 'delete a bid'() {
        given:
        setupLogIn(accountTest1.username, accountTest1.password)

        when:
        //find the bid ID from a previous test.  Use that bid for this test
        def deleteBidId = remote {
            Bid.findByListingAndBidder(Listing.findByName('testOpen'), Account.findByUsername('me')).id
        } as Integer
        def resp = doJsonDelete("api/bids/${deleteBidId}", [])

        then:
        resp.status == 200
        resp.data == "Success!  Bid ID ${deleteBidId} has been deleted."

        when:
        resp = doGet("api/bids/${deleteBidId}" as String)

        then:
        resp.status == 404
        resp.data == "Not found"
    }

}
