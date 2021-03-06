package msse_auctions

import geb.spock.GebSpec
import grails.plugin.remotecontrol.RemoteControl
import msse_auctions.pages.ListingCreatePage
import msse_auctions.pages.ListingIndexPage
import msse_auctions.pages.LoginPage
import spock.lang.Stepwise

@Stepwise
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

    def 'listing create page is not accessible to users who are not logged in' () {
        when:
        to ListingCreatePage

        then:
        $('body').text().toString().indexOf('Please Login')!=-1

        when:
        to LoginPage
        login('me', 'abcd1234')
        to ListingCreatePage

        then:
        $('body').text().toString().indexOf('Please Login')==-1
    }

    def 'listing create page sets the seller as the account logged in'() {
        when:
        to ListingCreatePage

        then:
        sellerLbl.text() == 'Me Test'
        seller.value() == '1'
    }
}