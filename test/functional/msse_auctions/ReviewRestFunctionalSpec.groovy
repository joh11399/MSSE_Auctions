package msse_auctions

import spock.lang.Specification
import spock.lang.Stepwise

@Stepwise
class ReviewRestFunctionalSpec extends Specification {
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

    def 'returns review list'() {
        when:
        def resp = doGet('api/reviews')

        then:
        resp.status == 200
        resp.contentType == 'application/json'
        def reviews = resp.data
        reviews.find { it.id == reviewId }
    }
}
