package msse_auctions

import grails.test.mixin.Mock
import spock.lang.Ignore
import spock.lang.Specification

@Ignore
@Mock(Review)
class ReviewSpec extends Specification{

    void setup() {
        new Account(username: 'reviewTest1', email: 'reviewTest1@email.com', password: 'abc12345', name: 'Test Name', addressStreet: 'd', addressCity: 'd', addressState: 'MN', addressZip: 'd').save(failOnError: true)
        new Account(username: 'reviewTest2', email: 'reviewTest2@email.com', password: 'abc12345', name: 'Test Name', addressStreet: 'd', addressCity: 'd', addressState: 'MN', addressZip: 'd').save(failOnError: true)
        new Listing(name: 'completed listing Review test', description: 'test description 2', startDate: new Date() -11, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: Account.findByEmail('reviewTest1@email.com')).save(failOnError: true)
    }

    void cleanup() {
        Listing.findByName('completed listing Review test').delete()
        Account.findByEmail('reviewTest1@email.com').delete()
        Account.findByEmail('reviewTest2@email.com').delete()
    }

    void "successfully create a review"() {
        setup:

        when:
        new Review(listing: Listing.findByName('completed listing Review test'), reviewer: Account.findByEmail('reviewTest1@email.com'), reviewee: Account.findByEmail('reviewTest2@email.com'), reviewOf: 'Buyer', rating: 5, thumbs: 'up', description: 'great job!!').save(failOnError: true)

        then:
        def review = Review.findByListingAndReviewer(Listing.findByName('completed listing Review test'), Account.findByEmail('reviewTest1@email.com'))
        review.description == 'great job!!'
        review.rating == 5
        review.thumbs == 'up'

        cleanup:
        Review.findByListingAndReviewer(Listing.findByName('completed listing Review test'), Account.findByEmail('reviewTest1@email.com')).delete()
    }

    void "unsuccessfully create a review:  description too long"() {
        setup:

        when:
        new Review(listing: Listing.findByName('completed listing Review test'), reviewer: Account.findByEmail('reviewTest1@email.com'), reviewee: Account.findByEmail('reviewTest2@email.com'), reviewOf: 'Buyer', rating: 5, thumbs: 'up', description: 'too long.  too long.  too long.  too long.  too long.').save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Review.findByListingAndReviewer(Listing.findByName('completed listing Review test'), Account.findByEmail('reviewTest1@email.com'))==null
    }


    void "unsuccessfully create a review:  null reviewer"() {
        setup:

        when:
        new Review(listing: Listing.findByName('completed listing Review test'), reviewee: Account.findByEmail('reviewTest2@email.com'), reviewOf: 'Buyer', rating: 5, thumbs: 'up', description: 'great job!!').save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Review.findByListingAndReviewee(Listing.findByName('completed listing Review test'), Account.findByEmail('reviewTest2@email.com'))==null
    }

    void "unsuccessfully create a review:  null reviewee"() {
        setup:

        when:
        new Review(listing: Listing.findByName('completed listing Review test'), reviewer: Account.findByEmail('reviewTest1@email.com'), reviewOf: 'Buyer', rating: 5, thumbs: 'up', description: 'great job!!').save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Review.findByListingAndReviewer(Listing.findByName('completed listing Review test'), Account.findByEmail('reviewTest1@email.com'))==null
    }

    void "unsuccessfully create a review:  invalid rating"() {
        setup:

        when:
        new Review(listing: Listing.findByName('completed listing Review test'), reviewer: Account.findByEmail('reviewTest1@email.com'), reviewOf: 'Buyer', rating: -1, thumbs: 'up', description: 'great job!!').save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Review.findByListingAndReviewer(Listing.findByName('completed listing Review test'), Account.findByEmail('reviewTest1@email.com'))==null
    }

    void "unsuccessfully create a review:  invalid thumbs"() {
        setup:

        when:
        new Review(listing: Listing.findByName('completed listing Review test'), reviewer: Account.findByEmail('reviewTest1@email.com'), reviewOf: 'Buyer', rating: -1, thumbs: 'test', description: 'great job!!').save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
        Review.findByListingAndReviewer(Listing.findByName('completed listing Review test'), Account.findByEmail('reviewTest1@email.com'))==null
    }
}
