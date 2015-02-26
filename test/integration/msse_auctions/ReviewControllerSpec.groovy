package msse_auctions

import spock.lang.Specification

class ReviewControllerSpec extends Specification {


def controller = new ReviewController()

    void setup() {
        new Account(email: 'reviewControllerTest1@email.com', password: 'abc12345', name: 'Test Name', addressStreet: 'd', addressCity: 'd', addressState: 'MN', addressZip: 'd').save(failOnError: true)
        new Account(email: 'reviewControllerTest2@email.com', password: 'abc12345', name: 'Test Name', addressStreet: 'd', addressCity: 'd', addressState: 'MN', addressZip: 'd').save(failOnError: true)
        new Listing(name: 'completed listing Review Controller test', description: 'test description 2', startDate: new Date() -11, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: Account.findByEmail('reviewControllerTest1@email.com')).save(failOnError: true)
    }

    void cleanup() {
        Listing.findByName('completed listing Review Controller test').delete()
        Account.findByEmail('reviewControllerTest1@email.com').delete()
        Account.findByEmail('reviewControllerTest2@email.com').delete()
    }

    //TODO
    //try saving (overwriting) an existing review    make sure it cannot be updated once created
/*

//TODO   this doesn't work.......

    void "unsuccessfully update an existing review"() {
        setup:
        def review = new Review(listing: Listing.findByName('completed listing Review Controller test'), reviewer: Account.findByEmail('reviewControllerTest1@email.com'), reviewee: Account.findByEmail('reviewControllerTest2@email.com'), reviewOf: 'Buyer', rating: 5, thumbs: 'up', description: 'great job!!').save(failOnError: true)

        //rather than returning an error when a duplicate id save is attempted, it creates a new review
        //  the original review remains unchanged.
        //There is no "update" action, so controller.save() is the only way the user can modify a review.
        //  because the original is never overwritten, each review is Read Only.
        when:
        review.description='pooor'
        ///def updatedReview = new Review(id: reviewId, listing: Listing.findByName('completed listing Review Controller test'), reviewer: Account.findByEmail('reviewControllerTest1@email.com'), reviewee: Account.findByEmail('reviewControllerTest2@email.com'), reviewOf: 'Buyer', rating: 0, thumbs: 'down', description: 'not great job!!')
        controller.save(review)

        then:
        def originalReview = Review.findByListingAndReviewer(Listing.findByName('completed listing Review Controller test'), Account.findByEmail('reviewControllerTest1@email.com'))
        originalReview.description == 'great job!!'
        originalReview.rating == 5
        originalReview.thumbs == 'up'

        println('-----------------------------------------------------')
        println(originalReview)
        println(Review.findByListingAndReviewer(Listing.findByName('completed listing Review Controller test'), Account.findByEmail('reviewControllerTest1@email.com')))
        println('-----------------------------------------------------')

        cleanup:

        println('-----------------------------------------------------')
        println(originalReview)
        println('null?::: '+Review.findByListingAndReviewer(Listing.findByName('completed listing Review Controller test'), Account.findByEmail('reviewControllerTest1@email.com')))
        println('-----------------------------------------------------')

        Review.findByListingAndReviewer(Listing.findByName('completed listing Review Controller test'), Account.findByEmail('reviewControllerTest1@email.com')).delete()

        println('-----------------------------------------------------')
        println('null?::: '+Review.findByListingAndReviewer(Listing.findByName('completed listing Review Controller test'), Account.findByEmail('reviewControllerTest1@email.com')))
        println('-----------------------------------------------------')

    }
*/
}
