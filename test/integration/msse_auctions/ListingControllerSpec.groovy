package msse_auctions

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(ListingController)
class ListingControllerSpec extends Specification {

    void setupSpec() {
        def a1 = new Account(email: 'listingControllerTest@email.com', password: 'abc12345', name: 'Test Name', addressStreet: 'd', addressCity: 'd', addressState: 'MN', addressZip: 'd').save(failOnError: true)
        new Listing(name: 'completed listing ListingController test', description: 'test description 1', startDate: new Date() - 11, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: a1).save(failOnError: true)
        new Listing(name: 'open listing ListingController test', description: 'test description 2', startDate: new Date() - 9, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: a1).save(failOnError: true)
    }

    void cleanupSpec() {
        Account.findByEmail('listingControllerTest@email.com').delete()
        Listing.findByName('completed listing ListingController test').delete()
        Listing.findByName('open listing ListingController test').delete()
    }

    def "index with no parameters returns no more than 10 open listings"(){
        given:

        when:
        controller.index()

        then:
        model.listingInstanceList.each(){
            it.timeRemaining != 'completed'
        }

        model.listingInstanceList.size() != Listing.count()
        model.listingInstanceList.size() <= 10
    }

    def "index with Show Completed Listings checked returns no more than 10 open or completed listings"(){
        given:

        when:
        controller.params.completedListingsCheckbox = 'on'
        controller.index()

        then:

        //I could not find a better, or more "Groovy", way to do this
        // the completed listings are always at the top of the list,
        // so there should be at least one completed listing within the top 10
        boolean foundCompletedListing = false
        model.listingInstanceList.each(){
            if(it.timeRemaining == 'completed'){
                foundCompletedListing = true
            }
        }
        foundCompletedListing

        model.listingInstanceList.size() <= 10
    }


    def "index with search text entered returns no more than 10 open listings with name or description containing the search text"(){
        given:
        def searchText = 'test'

        when:
        controller.params.searchDescription = searchText
        controller.index()

        then:

        model.listingInstanceList.each(){
            it.name.contains(searchText) || it.description.contains(searchText)
            it.timeRemaining != 'completed'
        }
        model.listingInstanceList.size() <= 10
    }


    def "index with 'page 2' requested returns no more than 10 open listings"(){
        given:

        when:
        controller.params.offset = 10
        controller.index()

        then:

        // I could not think of a better way to test that these listings were not previously shown on the first page
        model.listingInstanceList.each(){
            it.name != 'open listing ListingController test'
        }
        model.listingInstanceList.size() <= 10
    }

    def "show listing returns listing values for an open listing"() {
        given:

        when:
        def listing = Listing.findByName('open listing ListingController test')
        controller.show(listing)

        then:
        //verify the listing values are displayed
        model.listingInstance.name == 'open listing ListingController test'
        model.listingInstance.description == 'test description 2'
        model.listingInstance.days == 10
        model.listingInstance.startingPrice == 10.00
        model.listingInstance.deliverOption == 'US Only'
        model.listingInstance.seller.name == 'Test Name'
        model.listingInstance.timeRemaining != 'completed'
    }

    def "show listing returns listing values for a completed listing"() {
        given:

        when:
        def listing = Listing.findByName('completed listing ListingController test')
        controller.show(listing)

        then:
        //verify the listing shows as completed
        model.listingInstance.timeRemaining == 'completed'
    }
}
