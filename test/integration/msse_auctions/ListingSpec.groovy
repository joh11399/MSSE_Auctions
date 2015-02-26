package msse_auctions

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Listing)
class ListingSpec extends Specification {

    void setup() {
        new Account(email: 'listingTest@email.com', password: 'abc12345', name: 'Heather Johnson', addressStreet: 'd', addressCity: 'd', addressState: 'MN', addressZip: 'd').save(failOnError: true)
    }

    void cleanup() {
        Account.findByEmail('listingTest@email.com').delete()
    }

    def "create a listing"() {
        given:

        when:
        new Listing(name: 'firsta', description: 'first auction', startDate: new Date() - 11, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: Account.findByEmail('listingTest@email.com')).save(failOnError: true)

        then:
        Listing.findByName('firsta').description == 'first auction'
        Listing.count() == 1
    }

    def "unsuccessfully create a listing:  null seller"() {
        given:

        when:
        new Listing(name: 'first', description: 'first auction', startDate: new Date() - 11, days: 10, startingPrice: 10.00, deliverOption: 'US Only').save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Listing.count() == 0
    }

    def "unsuccessfully create a listing:  invalid seller"() {
        given:

        when:
        new Listing(name: 'first', description: 'first auction', startDate: new Date() - 11, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: Account.findByEmail('nonExistent@email.com')).save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Listing.count() == 0
    }

    def "unsuccessfully create a listing:  null name"() {
        given:

        when:
        new Listing(description: 'first auction', startDate: new Date() - 11, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: Account.findByEmail('listingTest@email.com')).save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Listing.count() == 0
    }

    def "unsuccessfully create a listing:  blank name"() {
        given:

        when:
        new Listing(name: '', description: 'first auction', startDate: new Date() - 11, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: Account.findByEmail('listingTest@email.com')).save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Listing.count() == 0
    }

    def "unsuccessfully create a listing:  null description"() {
        given:

        when:
        new Listing(name: 'first', startDate: new Date() - 11, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: Account.findByEmail('listingTest@email.com')).save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Listing.count() == 0
    }

    def "unsuccessfully create a listing:  blank description"() {
        given:

        when:
        new Listing(name: 'first', description: '', startDate: new Date() - 11, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: Account.findByEmail('listingTest@email.com')).save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Listing.count() == 0
    }

    def "unsuccessfully create a listing:  null start date"() {
        given:

        when:
        new Listing(name: 'firsta', description: 'first auction', days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: Account.findByEmail('listingTest@email.com')).save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Listing.count() == 0
    }

    def "unsuccessfully create a listing:  null days"() {
        given:

        when:
        new Listing(name: 'firsta', description: 'first auction', startDate: new Date() - 11, startingPrice: 10.00, deliverOption: 'US Only', seller: Account.findByEmail('listingTest@email.com')).save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Listing.count() == 0
    }

    def "unsuccessfully create a listing:  zero days"() {
        given:

        when:
        new Listing(name: 'first', description: 'first auction', startDate: new Date() - 11, days: 0, startingPrice: 10.00, deliverOption: 'US Only', seller: Account.findByEmail('listingTest@email.com')).save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Listing.count() == 0
    }

    def "unsuccessfully create a listing:  negative days"() {
        given:

        when:
        new Listing(name: 'first', description: 'first auction', startDate: new Date() - 11, days: -1, startingPrice: 10.00, deliverOption: 'US Only', seller: Account.findByEmail('listingTest@email.com')).save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Listing.count() == 0
    }

    def "unsuccessfully create a listing:  negative starting price"() {
        given:

        when:
        new Listing(name: 'firsta', description: 'first auction', startDate: new Date() - 11, days: 10, startingPrice: -1.0, deliverOption: 'US Only', seller: Account.findByEmail('listingTest@email.com')).save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Listing.count() == 0
    }

    def "unsuccessfully create a listing:  null deliver option"() {
        given:

        when:
        new Listing(name: 'firsta', description: 'first auction', startDate: new Date() - 11, days: 10, startingPrice: 10.00, seller: Account.findByEmail('listingTest@email.com')).save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Listing.count() == 0
    }

    def "unsuccessfully create a listing:  invalid deliver option"() {
        given:

        when:
        new Listing(name: 'firsta', description: 'first auction', startDate: new Date() - 11, days: 10, startingPrice: 10.00, deliverOption: 'Home', seller: Account.findByEmail('listingTest@email.com')).save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Listing.count() == 0
    }
}
