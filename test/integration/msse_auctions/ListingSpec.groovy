package msse_auctions

import spock.lang.Specification

class ListingSpec extends Specification {

    void setup() {
        new Account(username: 'listingTest', email: 'listingTest@email.com', password: 'abc12345', name: 'Heather Johnson', addressStreet: 'd', addressCity: 'd', addressState: 'MN', addressZip: 'd').save(failOnError: true)
    }

    void cleanup() {
        Account.findByEmail('listingTest@email.com').delete()
    }

    def "successfully create a listing"() {
        given:

        when:
        new Listing(name: 'firsta', description: 'first auction', startDate: new Date() - 11, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: Account.findByEmail('listingTest@email.com')).save(failOnError: true)

        then:
        Listing.findByName('firsta').description == 'first auction'
    }

    def "unsuccessfully create a listing:  null seller"() {
        given:

        when:
        new Listing(name: 'first', description: 'first auction', startDate: new Date() - 11, days: 10, startingPrice: 10.00, deliverOption: 'US Only').save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
    }

    def "unsuccessfully create a listing:  invalid seller"() {
        given:

        when:
        new Listing(name: 'first', description: 'first auction', startDate: new Date() - 11, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: Account.findByEmail('nonExistent@email.com')).save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
    }

    def "unsuccessfully create a listing:  null name"() {
        given:

        when:
        new Listing(description: 'first auction', startDate: new Date() - 11, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: Account.findByEmail('listingTest@email.com')).save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
    }

    def "unsuccessfully create a listing:  blank name"() {
        given:

        when:
        new Listing(name: '', description: 'first auction', startDate: new Date() - 11, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: Account.findByEmail('listingTest@email.com')).save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
    }

    def "unsuccessfully create a listing:  null description"() {
        given:

        when:
        new Listing(name: 'first', startDate: new Date() - 11, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: Account.findByEmail('listingTest@email.com')).save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
    }

    def "unsuccessfully create a listing:  blank description"() {
        given:

        when:
        new Listing(name: 'first', description: '', startDate: new Date() - 11, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: Account.findByEmail('listingTest@email.com')).save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
    }

    def "unsuccessfully create a listing:  null start date"() {
        given:

        when:
        new Listing(name: 'firsta', description: 'first auction', days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: Account.findByEmail('listingTest@email.com')).save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
    }

    def "unsuccessfully create a listing:  null days"() {
        given:

        when:
        new Listing(name: 'firsta', description: 'first auction', startDate: new Date() - 11, startingPrice: 10.00, deliverOption: 'US Only', seller: Account.findByEmail('listingTest@email.com')).save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
    }

    def "unsuccessfully create a listing:  zero days"() {
        given:

        when:
        new Listing(name: 'first', description: 'first auction', startDate: new Date() - 11, days: 0, startingPrice: 10.00, deliverOption: 'US Only', seller: Account.findByEmail('listingTest@email.com')).save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
    }

    def "unsuccessfully create a listing:  negative days"() {
        given:

        when:
        new Listing(name: 'first', description: 'first auction', startDate: new Date() - 11, days: -1, startingPrice: 10.00, deliverOption: 'US Only', seller: Account.findByEmail('listingTest@email.com')).save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
    }

    def "unsuccessfully create a listing:  negative starting price"() {
        given:

        when:
        new Listing(name: 'firsta', description: 'first auction', startDate: new Date() - 11, days: 10, startingPrice: -1.0, deliverOption: 'US Only', seller: Account.findByEmail('listingTest@email.com')).save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
    }

    def "unsuccessfully create a listing:  null deliver option"() {
        given:

        when:
        new Listing(name: 'firsta', description: 'first auction', startDate: new Date() - 11, days: 10, startingPrice: 10.00, seller: Account.findByEmail('listingTest@email.com')).save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
    }

    def "unsuccessfully create a listing:  invalid deliver option"() {
        given:

        when:
        new Listing(name: 'firsta', description: 'first auction', startDate: new Date() - 11, days: 10, startingPrice: 10.00, deliverOption: 'My Home', seller: Account.findByEmail('listingTest@email.com')).save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
    }
}
