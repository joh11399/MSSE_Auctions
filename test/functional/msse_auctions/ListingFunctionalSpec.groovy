package msse_auctions

import geb.spock.GebSpec
import grails.plugin.remotecontrol.RemoteControl
import msse_auctions.pages.ListingIndexPage

class ListingFunctionalSpec extends GebSpec {

    def remote = new RemoteControl()

    def "listing index displays bid for OPEN listings and rate buyer/seller for COMPLETED listing"() {
        when:
        to ListingIndexPage, completedListingsCheckbox: 'on'

        then:
        int i=0
        listing_timeRemaining.each() {
            if (it.text() == 'completed') {
                $('.bidLink', i).css('display') == 'none'
                $('.rateSellerLink', i).css('display') == 'block'
                $('.rateBuyerLink', i).css('display') == 'block'
            }
            else{
                $('.bidLink', i).css('display') == 'block'
                $('.rateSellerLink', i).css('display') == 'none'
                $('.rateBuyerLink', i).css('display') == 'none'
            }
            i++
        }
    }
}