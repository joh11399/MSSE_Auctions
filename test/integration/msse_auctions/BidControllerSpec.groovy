package msse_auctions

import spock.lang.Specification

class BidControllerSpec extends Specification {

    def controller = new BidController()

    void setup() {
        new Account(username: 'bidControllerTest', email: 'bidControllerTest@email.com', password: 'abc12345', name: 'Test Name', addressStreet: 'd', addressCity: 'd', addressState: 'MN', addressZip: 'd').save(failOnError: true)
        new Listing(name: 'open listing BidController test', description: 'test description 2', startDate: new Date() - 9, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: Account.findByEmail('bidControllerTest@email.com')).save(failOnError: true)
    }

    void cleanup() {
        Listing.findByName('open listing BidController test').delete()
        Account.findByEmail('bidControllerTest@email.com').delete()
    }

    def "create a new bid"() {
        given:
        def l1 = Listing.findByName('open listing BidController test')

        when:
        def b1 = new Bid(listing: l1, bidder: Account.findByEmail('bidControllerTest@email.com'), amount: 20.00)
        controller.save(b1)

        then:
        //the string format for a bid is:    <name> (<amount>)
        Bid.findByListing(l1).toString() == 'Test Name (20.0)'

        cleanup:
        Bid.findByListing(l1).delete()
    }

    def "successfully create a new bid where the amount is exactly 0.50 higher than the startingPrice"() {
        given:
        def l1 = Listing.findByName('open listing BidController test')

        when:
        def b1 = new Bid(listing: l1, bidder: Account.findByEmail('bidControllerTest@email.com'), amount: 10.50)
        controller.save(b1)

        then:
        //the string format for a bid is:    <name> (<amount>)
        Bid.findByListing(l1).toString() == 'Test Name (10.5)'

        cleanup:
        Bid.findByListing(l1).delete()
    }

    def "unsuccessfully create a new bid:  null bidder account"() {
        given:
        def l1 = Listing.findByName('open listing BidController test')

        when:
        def b1 = new Bid(listing: l1, amount: 20.00)

        controller.save(b1)

        then:
        //the string will be null if no bids exist for the listing
        Bid.findByListing(l1).toString() == 'null'
    }


    def "unsuccessfully create a new bid:  null bid amount"() {
        given:
        def l1 = Listing.findByName('open listing BidController test')

        when:
        def b1 = new Bid(listing: l1, bidder: Account.findByEmail('bidControllerTest@email.com'))

        controller.save(b1)

        then:
        //the string will be null if no bids exist for the listing
        Bid.findByListing(l1).toString() == 'null'
    }

    def "unsuccessfully create a new bid:  amount is below the startingPrice"() {
        given:
        def l1 = Listing.findByName('open listing BidController test')

        when:
        def b1 = new Bid(listing: l1, bidder: Account.findByEmail('bidControllerTest@email.com'), amount: 9.99)

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
        // the 20.25 bid was NOT accepted
        float amount2025 = 20.25
        Bid.findByListingAndAmount(l1, amount2025) == null
        float amount2000 = 20.00
        Bid.findByListingAndAmount(l1, amount2000) != null

        cleanup:
        Bid.findByListing(l1).delete()
    }

    def "successfully create a new bid:  amount is higher the highest bid + 0.50"() {
        given:
        def l1 = Listing.findByName('open listing BidController test')
        new Bid(listing: l1, bidder: Account.findByEmail('bidControllerTest@email.com'), amount: 20.00).save(failOnError: true)


        when:
        def b2 = new Bid(listing: l1, bidder: Account.findByEmail('bidControllerTest@email.com'), amount: 20.75)
        controller.save(b2)

        then:
        // the 20.75 bid was accepted
        float amount = 20.75
        Bid.findByListingAndAmount(l1, amount) != null

        cleanup:
        float amount2075 = 20.75
        Bid.findByListingAndAmount(l1, amount2075).delete()
        float amount2000 = 20.00
        Bid.findByListingAndAmount(l1, amount2000).delete()
    }
}
