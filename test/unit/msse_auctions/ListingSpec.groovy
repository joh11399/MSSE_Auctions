package msse_auctions

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Listing)
@Mock(Account)
class ListingSpec extends Specification {

    def 'create a listing'() {
        setup:
        new Account(username: 'joh11399', password: 'P@$sW*rD', email: 'dan@dan.com', fullName: 'Dan Johnson', addressStreet: 'd', addressCity: 'd', addressState: 'MN', addressZip: 'd').save(failOnError: true)

        when:
        new Listing(id: 'auct-1', name:'first', description: 'first auction', begDate: Date.parse('MM/dd/yyyy', '03/01/2015'), endDate: Date.parse('MM/dd/yyyy', '03/01/2015'), minAmount: '10', buyAmount: '100', owner: Account.findByUsername('joh11399') ).save(failOnError: true)

        then:
        Listing.count() == 1
    }
}
