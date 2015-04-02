package msse_auctions

import geb.spock.GebSpec
import grails.plugin.remotecontrol.RemoteControl
import msse_auctions.pages.BidCreatePage
import msse_auctions.pages.LoginPage

class BidFunctionalSpec  extends GebSpec {

    def remote = new RemoteControl()

    def 'bid create page sets bidder as account logged in'() {
        when:
        to LoginPage
        login(username, password)
        to BidCreatePage, listingID: listingId

        then:
        listingLbl.text() == listingName
        listing.value() == listingId
        bidderLbl.text() == bidderName
        bidder.value() == bidderId

        where:
        username | password   | listingId | listingName | bidderId | bidderName
        'me'     | 'abcd1234' | '2'  | 'testOpen' | '1' | 'Me Test'
        /*
        TODO..  this doesn't need to be a Data Driven Test if you can't figure out the next two..........
        'test'   | 'abcd1234' | 'Test 2'   | '2'
        'dan'    | 'johnson1' | 'dan'      | '3'
        */
    }
}