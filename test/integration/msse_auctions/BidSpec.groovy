package msse_auctions

import spock.lang.Specification

class BidSpec extends Specification {
        void setup() {
            new Account(email: 'bidTest@email.com', password: 'abc12345', name: 'Test Name', addressStreet: 'd', addressCity: 'd', addressState: 'MN', addressZip: 'd').save(failOnError: true)
            new Listing(name: 'open listing Bid test', description: 'test description 2', startDate: new Date() - 9, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: Account.findByEmail('bidTest@email.com')).save(failOnError: true)
        }

        void cleanup() {
            Account.findByEmail('bidTest@email.com').delete()
            Listing.findByName('open listing Bid test').delete()
        }


    def "create a bid"() {
        given:

        when:
        new Bid(listing: Listing.findByName('open listing Bid test') , bidder: Account.findByEmail('bidTest@email.com'), amount: 20.00).save(failOnError: true)

        then:
        println(Bid.findByListing(Listing.findByName('open listing Bid test')))
    }

    def "unsuccessfully create a bid:  null listing"() {
        given:

        when:
        new Bid(bidder: Account.findByEmail('bidTest@email.com'), amount: 20.00).save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
    }

    def "unsuccessfully create a bid:  null bidder"() {
        given:

        when:
        new Bid(listing: Listing.findByName('open listing Bid test'), amount: 20.00).save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
    }

    /*

    //TODO  find out why this isn't throwing a null exception

    def "unsuccessfully create a bid:  null amount"() {
        given:

        when:
        new Bid(listing: Listing.findByName('open listing Bid test'), bidder: Account.findByEmail('bidTest@email.com')).save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Bid.count() == 0
    }
    */

}
