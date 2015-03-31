package msse_auctions

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(BidController)
class BidIndexSpec extends Specification {


    void setupSpec() {
        def a1 = new Account(username: 'bidIndexTest', email: 'bidIndexTest@email.com', password: 'abc12345', name: 'Test Name', addressStreet: 'd', addressCity: 'd', addressState: 'MN', addressZip: 'd').save(failOnError: true)
        def l1 = new Listing(name: 'open listing bidIndex test', description: 'test description', startDate: new Date() - 9, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: a1).save(failOnError: true)
        new Bid(listing: l1, bidder: a1, amount: 12.00).save(failOnError: true)
        new Bid(listing: l1, bidder: a1, amount: 20.00).save(failOnError: true)
        new Bid(listing: l1, bidder: a1, amount: 25.00).save(failOnError: true)
    }

    void cleanupSpec() {
        def l1 = Listing.findByName('open listing bidIndex test')
        float amount1200 = 12.00
        Bid.findByListingAndAmount(l1, amount1200).delete()
        float amount2000 = 20.00
        Bid.findByListingAndAmount(l1, amount2000).delete()
        float amount2500 = 25.00
        Bid.findByListingAndAmount(l1, amount2500).delete()
        l1.delete()
        Account.findByEmail( 'bidIndexTest@email.com').delete()
    }

    def "index with no parameters returns no more than 10 bids"() {
        given:

        when:
        controller.index()

        then:
        model.bidInstanceList.size() > 0
        model.bidInstanceList.size() <= 10
    }

    def "index with a specific listing will return the bid history for that listing"() {
        given:
        def listingId = Listing.findByName('open listing bidIndex test').id

        when:
        params.listingId = listingId
        controller.index()

        then:
        model.bidInstanceList.each() {
            it.listing.id == listingId
        }

        model.bidInstanceList.size() > 0
        model.bidInstanceList.size() <= 10
    }

}
