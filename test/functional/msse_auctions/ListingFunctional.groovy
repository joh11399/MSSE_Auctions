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


        //there has to be a better way to do this.
        // I ran out of time and couldn't make this any prettier...


        //this looks at the first three rows of listings and it checks the timeRemaining
        //  if the listing is completed, it should display links for 'Rate Seller' and 'Rate Buyer'
        //  if the listing is still open, it displays a link for 'bid'

        //I used the text() field because I couldn't figure out how to get the css display value in Geb.

        if (listing_0_timeRemaining.text() == 'completed') {
            listing_0_bidLink.text() == ''
            listing_0_rateSellerLink.text() == 'Rate Seller'
            listing_0_rateBuyerLink.text() == 'Rate Buyer'
        }
        else{
            listing_0_bidLink.text() == 'bid'
            listing_0_rateSellerLink.text() == ''
            listing_0_rateBuyerLink.text() == ''
        }

        if (listing_1_timeRemaining.text() == 'completed') {
            listing_1_bidLink.text() == ''
            listing_1_rateSellerLink.text() == 'Rate Seller'
            listing_1_rateBuyerLink.text() == 'Rate Buyer'
        }
        else{
            listing_1_bidLink.text() == 'bid'
            listing_1_rateSellerLink.text() == ''
            listing_1_rateBuyerLink.text() == ''
        }

        if (listing_2_timeRemaining.text() == 'completed') {
            listing_2_bidLink.text() == ''
            listing_2_rateSellerLink.text() == 'Rate Seller'
            listing_2_rateBuyerLink.text() == 'Rate Buyer'
        }
        else{
            listing_2_bidLink.text() == 'bid'
            listing_2_rateSellerLink.text() == ''
            listing_2_rateBuyerLink.text() == ''
        }
    }
}