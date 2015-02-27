package msse_auctions

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(ListingController)
class ListingControllerSpec extends Specification {

    void setupSpec() {
        def a1 = new Account(email: 'listingControllerTest@email.com', password: 'abc12345', name: 'Test Name', addressStreet: 'd', addressCity: 'd', addressState: 'MN', addressZip: 'd').save(failOnError: true)
        new Listing(name: 'completed listing ListingController test', description: 'test description 1', startDate: new Date() - 11, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: a1).save(failOnError: true)
        new Listing(name: 'open listing ListingController test', description: 'test description 2', startDate: new Date() - 9, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: a1).save(failOnError: true)
    }

    void cleanupSpec() {
        Listing.findByName('completed listing ListingController test').delete()
        Listing.findByName('open listing ListingController test').delete()
        Account.findByEmail('listingControllerTest@email.com').delete()
    }

    def "index with no parameters returns no more than 10 OPEN listings"(){
        given:

        when:
        controller.index()

        then:
        model.listingInstanceList.each(){

            it.startDate + it.days < new Date()

            //timeRemaining is set to 'completed' if the current date is after the start date plus the value of days
            //verify that timeRemaining is NOT set to 'completed'
            it.timeRemaining != 'completed'
        }

        model.listingInstanceList.size() <= 10
    }

    def "index with 'Show Completed Listings' checked returns no more than 10 OPEN or COMPLETED listings"(){
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
                it.startDate + it.days >= new Date()
                foundCompletedListing = true
            }
            else{
                it.startDate + it.days < new Date()
            }
        }
        foundCompletedListing

        model.listingInstanceList.size() <= 10
    }

    def "index using searchDescription returns no more than 10 OPEN listings with name or description containing the searchDescription text"(){
        given:
        def searchText = 'test'

        when:
        controller.params.searchDescription = searchText
        controller.index()

        then:

        //each listingInstance return must contain the searchDescription in either the name or description fields
        //  and the listing must not be completed
        model.listingInstanceList.each(){
            it.name.contains(searchText) || it.description.contains(searchText)
            it.timeRemaining != 'completed'
        }
        model.listingInstanceList.size() <= 10
    }
    def "index with 'page 2' requested returns no more than 10 OPEN listings"(){
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

    def "index displays the highest bid (amount and account) for each listing,  and completed listings are marked as 'winner'"() {
        given:

        when:
        //include completed listings to test the 'winner' string
        controller.params.completedListingsCheckbox = 'on'
        controller.index()

        then:

        model.listingInstanceList.each() {
            if (it.highestBidStr != null) {

                //I know there's some kind of select max(..) option, but I couldn't get that to work
                //  instead I'm grabbing all bids for this listing and looping through them to find the highest value
                def bids = Bid.findAll("from Bid as b where b.listing.id = :listingID order by b.amount desc", [listingID: it.id])

                Bid highestBid = null
                if (bids.size() > 0) {
                    bids.each() {
                        if (highestBid != null) {
                            if (it.amount > highestBid.amount) {
                                highestBid = it
                            }
                        } else {
                            highestBid = it
                        }
                    }

                    if(it.timeRemaining == 'completed'){
                        it.highestBidStr == "Winner \$" + highestBid.amount.round(2) + " - " + highestBid.bidder
                    }
                    else{
                        it.highestBidStr == "\$" + highestBid.amount.round(2) + " - " + highestBid.bidder
                    }
                }
                //do not assert anything if the listing has no bids
            }
            //do not assert anything if the highestBidStr is null
        }
    }

    def "show listing returns listing values for an OPEN listing"() {
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
    def "show listing returns listing values for a COMPLETED listing"() {
        given:

        when:
        def listing = Listing.findByName('completed listing ListingController test')
        controller.show(listing)

        then:
        //verify the listing shows as completed
        model.listingInstance.timeRemaining == 'completed'
    }


    def "create a listing via the controller"(){
        given:

        when:
        def newListing = new Listing(name: 'listing created through the controller', description: 'test description', startDate: new Date() - 9, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: Account.findByEmail('listingControllerTest@email.com'))
        controller.save(newListing)

        then:
        Listing.findByName('listing created through the controller') != null

        cleanup:
        Listing.findByName('listing created through the controller').delete()
    }

    def "unsuccessfully create a listing via the controller:  null seller account"(){
        given:

        when:
        def newListing = new Listing(name: 'listing created through the controller', description: 'test description', startDate: new Date() - 9, days: 10, startingPrice: 10.00, deliverOption: 'US Only')
        controller.save(newListing)

        then:
        Listing.findByName('listing created through the controller') == null
    }
    def "unsuccessfully create a listing via the controller:  null deliver option"(){
        given:

        when:
        def newListing = new Listing(name: 'listing created through the controller', description: 'test description', startDate: new Date() - 9, days: 10, startingPrice: 10.00, seller: Account.findByEmail('listingControllerTest@email.com'))
        controller.save(newListing)

        then:
        Listing.findByName('listing created through the controller') == null
    }
    def "unsuccessfully create a listing via the controller:  invalid deliver option selected"(){
        given:

        when:
        def newListing = new Listing(name: 'listing created through the controller', description: 'test description', startDate: new Date() - 9, days: 10, startingPrice: 10.00, deliverOption: 'to my home', seller: Account.findByEmail('listingControllerTest@email.com'))
        controller.save(newListing)

        then:
        Listing.findByName('listing created through the controller') == null
    }
}