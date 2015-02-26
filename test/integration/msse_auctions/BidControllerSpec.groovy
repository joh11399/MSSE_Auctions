package msse_auctions

import spock.lang.Specification

class BidControllerSpec extends Specification {

    def controller = new BidController()

    void setup() {
        new Account(email: 'bidControllerTest@email.com', password: 'abc12345', name: 'Test Name', addressStreet: 'd', addressCity: 'd', addressState: 'MN', addressZip: 'd').save(failOnError: true)
        new Listing(name: 'open listing BidController test', description: 'test description 2', startDate: new Date() - 9, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: Account.findByEmail('bidControllerTest@email.com')).save(failOnError: true)
    }

    void cleanup() {
        Account.findByEmail('bidControllerTest@email.com').delete()
        Listing.findByName('open listing BidController test').delete()
    }

    /*
    def "create a new bid"() {
        given:
        def l1 = Listing.findByName('open listing BidController test')
        def b1 = new Bid(listing: l1, bidder: Account.findByEmail('bidControllerTest@email.com'), amount: 20.00)

        when:
        controller.save(b1)

        then:
        //the string format for a bid is:    <name> (<amount>)
        Bid.findByListing(l1).toString() == 'Test Name (20.0)'

        cleanup:
        Bid.findByListing(l1).delete()
    }
    */

    def "unsuccessfully create a new bid:  amount is below the startingPrice"() {
        given:
        def l1 = Listing.findByName('open listing BidController test')
        def b1 = new Bid(listing: l1, bidder: Account.findByEmail('bidControllerTest@email.com'), amount: 9.99)

        when:
        controller.save(b1)

        then:
        //the string will be null if no bids exist for the listing
        Bid.findByListing(l1).toString() == 'null'
    }

    def "unsuccessfully create a new bid:  amount is below the highest bid + 0.50"() {
        given:
        def l1 = Listing.findByName('open listing BidController test')
        new Bid(listing: l1, bidder: Account.findByEmail('bidControllerTest@email.com'), amount: 20.00).save(failOnError: true)


        when:
        def b2 = new Bid(listing: l1, bidder: Account.findByEmail('bidControllerTest@email.com'), amount: 20.25)
        controller.save(b2)

        then:
        //the string will still show the original bid and price, because the 20.25 bid was not accepted
        Bid.findByListing(l1).toString() == 'Test Name (20.0)'
    }
}
