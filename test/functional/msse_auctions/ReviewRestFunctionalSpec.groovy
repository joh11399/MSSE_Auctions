package msse_auctions

import grails.plugin.remotecontrol.RemoteControl
import spock.lang.Specification
import spock.lang.Stepwise
import spock.lang.Unroll

@Stepwise
class ReviewRestFunctionalSpec extends Specification {
    @Delegate
    static FunctionalTestUtils utils = new FunctionalTestUtils()

    def remote = new RemoteControl()

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

    def 'returns review detail'() {
        when:
        def resp = doGet("api/reviews/${reviewId}" as String)

        then:
        resp.status == 200
        resp.contentType == 'application/json'
        resp.data.id == reviewId
    }


    def 'unsuccessfully create a review - not logged in'() {
        given:
        setupLogOut('me')

        when:
        def resp = doJsonPost('api/reviews', [listing: [id: listingCompletedId as String], reviewer: [id: accountTest1.id], reviewee: [id: accountTest2.id], reviewOf: 'Buyer', rating: 4, thumbs: 'up' , description: 'ok.'])

        then: 'the page redirects to the login page'
        resp.status == 302
        resp.headers.toString().indexOf("Location: http://localhost:8080/MSSE_Auctions/login/auth") != -1
    }

    @Unroll
    def 'unsuccessfully create a review'() {
        given:
        setupLogIn(accountTest1.username, accountTest1.password)

        when:
        def resp = doJsonPost('api/reviews', [listing: [id: listingId], reviewer: [id: reviewerId], reviewee: [id: revieweeId], reviewOf: reviewOf, rating: rating, thumbs: thumbs, description: description])

        then:
        resp.status == respStatus

        //listingCompleted has   seller: accountId   and   winner: accountId2

        //the existing review is reviewer is 'me',  reviewee is 'test',  reviewOf: 'Buyer'
        //the new review will be reviewer is 'test',  reviewee is 'me',  reviewOf: 'Seller'

        where:
        listingId                    | reviewerId           | revieweeId           | reviewOf  | rating | thumbs    | description       | respStatus
        null                         | accountId as String  | accountId2 as String | 'Buyer'   | 1      | 'down'    | 'he never paid!!' | 400
        listingCompletedId as String | null                 | accountId2 as String | 'Buyer'   | 1      | 'down'    | 'he never paid!!' | 400
        listingCompletedId as String | accountId as String  | null                 | 'Buyer'   | 1      | 'down'    | 'he never paid!!' | 400

        0 as String                  | accountId as String  | accountId2 as String | 'Buyer'   | 1      | 'down'    | 'he never paid!!' | 400
        listingCompletedId as String | 0 as String          | accountId2 as String | 'Buyer'   | 1      | 'down'    | 'he never paid!!' | 400
        listingCompletedId as String | accountId as String  | 0 as String          | 'Buyer'   | 1      | 'down'    | 'he never paid!!' | 400

        listingCompletedId as String | accountId as String  | accountId2 as String | 'invalid' | 1      | 'down'    | 'he never paid!!' | 400
        listingCompletedId as String | accountId as String  | accountId2 as String | 'Buyer'   | 1      | 'invalid' | 'he never paid!!' | 400
        listingCompletedId as String | accountId as String  | accountId2 as String | 'Buyer'   | 1      | 'down'    | null              | 400
        listingCompletedId as String | accountId2 as String | accountId as String  | 'Seller'  | 1      | 'down'    | 'he never paid!!' | 400
        listingCompletedId as String | accountId as String  | accountId2 as String | 'Seller'  | 1      | 'down'    | 'he never paid!!' | 400

    }


    @Unroll
    def 'successfully create reviews for the buyer and seller'() {
        given:
        setupLogIn(loginUsername, loginPassword)

        when:
        def resp = doJsonPost('api/reviews', [listing: [id: listingId], reviewer: [id: reviewerId], reviewee: [id: revieweeId], reviewOf: reviewOf, rating: rating, thumbs: thumbs, description: description])

        then:
        resp.status == respStatus
        resp.data.id

        when:
        resp = doGet("api/reviews/${resp.data.id}" as String)

        then:
        resp.status == 200
        resp.contentType == 'application/json'
        resp.data.description == description

        //listingCompleted has   seller: accountId   and   winner: accountId2

        //the existing review is reviewer is 'me',  reviewee is 'test',  reviewOf: 'Buyer'
        //the new review will be reviewer is 'test',  reviewee is 'me',  reviewOf: 'Seller'

        where:
        loginUsername         | loginPassword         | listingId                    | reviewerId                | revieweeId                | reviewOf | rating | thumbs | description | respStatus
        accountTest1.username | accountTest1.password | listingCompletedId as String | accountTest1.id as String | accountTest2.id as String | 'Buyer'  | 4      | 'up'   | 'ok.'       | 201
        accountTest2.username | accountTest2.password | listingCompletedId as String | accountTest2.id as String | accountTest1.id as String | 'Seller' | 2      | 'down' | 'eh.'       | 201
    }



    def 'unsuccessfully update a review - not logged in'() {
        given:
        def reviewTestId = remote { Review.findByReviewerAndDescription(Account.findByUsername('me'), 'ok.').id } as Integer
        setupLogOut('me')

        when:
        def resp = doJsonPut("api/reviews/${reviewTestId}", [listing: [id: 5], reviewer: [id: accountTest1.id], reviewee: [id: accountTest2.id], reviewOf: 'Buyer', rating: 4, thumbs: 'up', description: 'ok.'])

        then: 'the page redirects to the login page'
        resp.status == 302
        resp.headers.toString().indexOf("Location: http://localhost:8080/MSSE_Auctions/login/auth") != -1
    }


    def 'unsuccessfully update a review'() {
        given:
        def reviewTestId = remote { Review.findByReviewerAndDescription(Account.findByUsername('me'), 'ok.').id } as Integer
        setupLogIn(accountTest1.username, accountTest1.password)

        when:
        def resp = doJsonPut("api/reviews/${reviewTestId}", [listing: [id: 5], reviewer: [id: accountTest1.id], reviewee: [id: accountTest2.id], reviewOf: 'Buyer', rating: 4, thumbs: 'up', description: 'ok.'])

        then:
        resp.status == 400
        resp.data == "Sorry.  You need to be either the seller or the winner of the listing to update this review."
    }

    def 'successfully update a review'() {
        given:
        def reviewTestId = remote {
            Review.findByReviewerAndDescription(Account.findByUsername('me'), 'ok.').id
        } as Integer
        setupLogIn(accountTest1.username, accountTest1.password)

        when:
        def resp = doJsonPut("api/reviews/${reviewTestId}", [listing: [id: listingCompletedId], reviewer: [id: accountTest1.id], reviewee: [id: accountTest2.id], reviewOf: 'Buyer', rating: 5, thumbs: 'up', description: 'great job!!'])

        then:
        resp.status == 200

        when:
        resp = doGet("api/reviews/${reviewTestId}" as String)

        then:
        resp.status == 200
        resp.contentType == 'application/json'
        resp.data.id == reviewTestId
        resp.data.reviewer.id == accountTest1.id
        resp.data.reviewee.id == accountTest2.id
        resp.data.rating == 5
        resp.data.description == 'great job!!'
    }



    def 'unsuccessfully delete a review'() {
        when:
        def resp = doJsonDelete("api/reviews/${urlReviewId}", [])

        then:
        resp.status == respStatus

        //cannot successfully delete any of the following
        // 0 and "" for obvious reasons, and username:'me' isn't the owner of reviewId
        where:
        urlReviewId | respStatus
        0           | 404
        reviewId    | 401
        ""          | 403
    }


    def 'successfully delete a review'() {
        given:
        def reviewTestId = remote {
            Review.findByReviewerAndDescription(Account.findByUsername('me'), 'great job!!').id
        } as Integer

        when:
        def resp = doJsonDelete("api/reviews/${reviewTestId}", [])

        then:
        resp.status == 200
        resp.data == "Success!  Review ID ${reviewTestId} has been deleted."

        when:
        resp = doGet("api/reviews/${reviewTestId}" as String)

        then:
        resp.status == 404
        resp.data == "Not found"
    }
}