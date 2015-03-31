package msse_auctions

import spock.lang.Specification
import spock.lang.Stepwise
import spock.lang.Unroll

@Stepwise
class ListingRestFunctionalSpec extends Specification {

    @Delegate
    static FunctionalTestUtils utils = new FunctionalTestUtils()

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


    def 'returns listing detail'() {
        when:
        def resp = doGet("api/listings/${listingId}" as String)

        then:
        resp.status == 200
        resp.contentType == 'application/json'
        resp.data.name == 'testCompleted'
    }
}
