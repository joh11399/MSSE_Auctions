package msse_auctions

import geb.spock.GebSpec
import grails.plugin.remotecontrol.RemoteControl
import msse_auctions.pages.BidCreatePage
import msse_auctions.pages.LoginPage

class BidFunctionalSpec  extends GebSpec {

    def remote = new RemoteControl()

    def 'bid create page sets the bidder as the account logged in'() {
        when:
        to LoginPage
        login('me' , 'abcd1234')
        to BidCreatePage, listingID: '2'

        then:
        listingLbl.text() == 'testOpen'
        listing.value() == '2'
        bidderLbl.text() == 'Me Test'
        bidder.value() == '1'
    }
}